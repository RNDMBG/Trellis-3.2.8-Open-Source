            package com.google.android.exoplayer.chunk;

            import android.os.Handler;
            import android.os.SystemClock;
            import com.google.android.exoplayer.LoadControl;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.MediaFormatHolder;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.SampleSource;
            import com.google.android.exoplayer.TrackInfo;
            import com.google.android.exoplayer.upstream.Loader;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            import java.util.Collections;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            
            
    public class ChunkSampleSource implements SampleSource, Loader.Listener {
                private static final int NO_RESET_PENDING = -1;
                private static final int STATE_ENABLED = 2;
                private static final int STATE_PREPARED = 1;
                private static final int STATE_UNPREPARED = 0;
                private final int bufferSizeContribution;
                private final ChunkSource chunkSource;
                private IOException currentLoadableException;
                private int currentLoadableExceptionCount;
                private boolean currentLoadableExceptionFatal;
                private long currentLoadableExceptionTimestamp;
                private final ChunkOperationHolder currentLoadableHolder;
                private volatile Format downstreamFormat;
                private MediaFormat downstreamMediaFormat;
                private long downstreamPositionUs;
                private final Handler eventHandler;
                private final EventListener eventListener;
                private final int eventSourceId;
                private final boolean frameAccurateSeeking;
                private long lastPerformedBufferOperation;
                private long lastSeekPositionUs;
                private final LoadControl loadControl;
                private Loader loader;
                private final LinkedList<MediaChunk> mediaChunks;
                private boolean pendingDiscontinuity;
                private long pendingResetTime;
                private final List<MediaChunk> readOnlyMediaChunks;
                private int state;

                public interface EventListener {
                    void onConsumptionError(int i, IOException iOException);

                    void onDownstreamDiscarded(int i, int i2, int i3, long j);

                    void onDownstreamFormatChanged(int i, String str, int i2, int i3);

                    void onLoadCanceled(int i, long j);

                    void onLoadCompleted(int i, long j);

                    void onLoadStarted(int i, String str, int i2, boolean z, int i3, int i4, long j);

                    void onUpstreamDiscarded(int i, int i2, int i3, long j);

                    void onUpstreamError(int i, IOException iOException);
                }

        static  int access$000(ChunkSampleSource x0) {
            return x0.eventSourceId;
                }

        static  EventListener access$100(ChunkSampleSource x0) {
            return x0.eventListener;
                }

       public ChunkSampleSource(ChunkSource chunkSource, LoadControl loadControl, int bufferSizeContribution, boolean frameAccurateSeeking) {
           this(chunkSource, loadControl, bufferSizeContribution, frameAccurateSeeking, null, null, 0);
                }

       public ChunkSampleSource(ChunkSource chunkSource, LoadControl loadControl, int bufferSizeContribution, boolean frameAccurateSeeking, Handler eventHandler, EventListener eventListener, int eventSourceId) {
           this.chunkSource = chunkSource;
           this.loadControl = loadControl;
           this.bufferSizeContribution = bufferSizeContribution;
           this.frameAccurateSeeking = frameAccurateSeeking;
           this.eventHandler = eventHandler;
           this.eventListener = eventListener;
           this.eventSourceId = eventSourceId;
           this.currentLoadableHolder = new ChunkOperationHolder();
           this.mediaChunks = new LinkedList<>();
           this.readOnlyMediaChunks = Collections.unmodifiableList(this.mediaChunks);
           this.state = 0;
                }

       public Format getFormat() {
           return this.downstreamFormat;
                }

                @Override 
       public boolean prepare() {
           Assertions.checkState(this.state == 0);
           this.loader = new Loader("Loader:" + this.chunkSource.getTrackInfo().mimeType, this);
           this.state = 1;
                    return true;
                }

                @Override 
       public int getTrackCount() {
           Assertions.checkState(this.state != 0);
                    return 1;
                }

                @Override 
       public TrackInfo getTrackInfo(int track) {
           Assertions.checkState(this.state != 0);
           Assertions.checkState(track == 0);
           return this.chunkSource.getTrackInfo();
                }

                @Override 
       public void enable(int track, long timeUs) {
           Assertions.checkState(this.state == 1);
           Assertions.checkState(track == 0);
           this.state = 2;
           this.chunkSource.enable();
           this.loadControl.register(this, this.bufferSizeContribution);
           this.downstreamFormat = null;
           this.downstreamMediaFormat = null;
           this.downstreamPositionUs = timeUs;
           this.lastSeekPositionUs = timeUs;
           restartFrom(timeUs);
                }

                @Override 
       public void disable(int track) {
           Assertions.checkState(this.state == 2);
           Assertions.checkState(track == 0);
           this.pendingDiscontinuity = false;
           this.state = 1;
           this.loadControl.unregister(this);
           this.chunkSource.disable(this.mediaChunks);
           if (this.loader.isLoading()) {
               this.loader.cancelLoading();
               return;
                    }
           clearMediaChunks();
           clearCurrentLoadable();
           this.loadControl.trimAllocator();
                }

                @Override 
       public boolean continueBuffering(long playbackPositionUs) throws IOException {
           Assertions.checkState(this.state == 2);
           this.downstreamPositionUs = playbackPositionUs;
           this.chunkSource.continueBuffering(playbackPositionUs);
           updateLoadControl();
           if (isPendingReset() || this.mediaChunks.isEmpty()) {
                        return false;
                    }
           if (this.mediaChunks.getFirst().sampleAvailable()) {
                        return true;
                    }
                    return this.mediaChunks.size() > 1 && this.mediaChunks.get(1).sampleAvailable();
                }

                @Override 
       public int readData(int track, long playbackPositionUs, MediaFormatHolder formatHolder, SampleHolder sampleHolder, boolean onlyReadDiscontinuity) throws IOException {
           Assertions.checkState(this.state == 2);
           Assertions.checkState(track == 0);
           if (this.pendingDiscontinuity) {
               this.pendingDiscontinuity = false;
               return -5;
                    }
           if (onlyReadDiscontinuity) {
               return -2;
                    }
           this.downstreamPositionUs = playbackPositionUs;
           if (isPendingReset()) {
               if (this.currentLoadableException != null) {
                   throw this.currentLoadableException;
                        }
               IOException chunkSourceException = this.chunkSource.getError();
               if (chunkSourceException != null) {
                   throw chunkSourceException;
                        }
               return -2;
                    }
           MediaChunk mediaChunk = this.mediaChunks.getFirst();
           if (mediaChunk.isReadFinished()) {
               if (this.mediaChunks.size() > 1) {
                   discardDownstreamMediaChunk();
                   this.mediaChunks.getFirst().seekToStart();
                   return readData(track, playbackPositionUs, formatHolder, sampleHolder, false);
                        }
               if (mediaChunk.isLastChunk()) {
                   return -1;
                        }
               IOException chunkSourceException2 = this.chunkSource.getError();
               if (chunkSourceException2 != null) {
                   throw chunkSourceException2;
                        }
               return -2;
                    }
           if (this.downstreamFormat == null || !this.downstreamFormat.equals(mediaChunk.format)) {
               notifyDownstreamFormatChanged(mediaChunk.format.f875id, mediaChunk.trigger, mediaChunk.startTimeUs);
               this.downstreamFormat = mediaChunk.format;
                    }
           if (!mediaChunk.prepare()) {
               if (this.currentLoadableException != null) {
                   throw this.currentLoadableException;
                        }
               return -2;
                    }
           MediaFormat mediaFormat = mediaChunk.getMediaFormat();
           if (mediaFormat != null && !mediaFormat.equals(this.downstreamMediaFormat, true)) {
               this.chunkSource.getMaxVideoDimensions(mediaFormat);
               formatHolder.format = mediaFormat;
               formatHolder.drmInitData = mediaChunk.getPsshInfo();
               this.downstreamMediaFormat = mediaFormat;
               return -4;
                    }
           if (mediaChunk.read(sampleHolder)) {
               sampleHolder.decodeOnly = this.frameAccurateSeeking && sampleHolder.timeUs < this.lastSeekPositionUs;
               onSampleRead(mediaChunk, sampleHolder);
               return -3;
                    }
           if (this.currentLoadableException != null) {
               throw this.currentLoadableException;
                    }
           return -2;
                }

                @Override 
       public void seekToUs(long timeUs) {
           Assertions.checkState(this.state == 2);
           this.downstreamPositionUs = timeUs;
           this.lastSeekPositionUs = timeUs;
                    if (this.pendingResetTime != timeUs) {
               MediaChunk mediaChunk = getMediaChunk(timeUs);
               if (mediaChunk == null) {
                   restartFrom(timeUs);
                   this.pendingDiscontinuity = true;
                        } else {
                            this.pendingDiscontinuity |= mediaChunk.seekTo(timeUs, mediaChunk == this.mediaChunks.getFirst());
                   discardDownstreamMediaChunks(mediaChunk);
                   updateLoadControl();
                        }
                    }
                }

       private MediaChunk getMediaChunk(long timeUs) {
           Iterator<MediaChunk> mediaChunkIterator = this.mediaChunks.iterator();
           while (mediaChunkIterator.hasNext()) {
               MediaChunk mediaChunk = mediaChunkIterator.next();
                        if (timeUs < mediaChunk.startTimeUs) {
                            return null;
                        }
               if (mediaChunk.isLastChunk() || timeUs < mediaChunk.endTimeUs) {
                   return mediaChunk;
                        }
                    }
                    return null;
                }

                @Override 
       public long getBufferedPositionUs() {
           Assertions.checkState(this.state == 2);
           if (isPendingReset()) {
               return this.pendingResetTime;
                    }
           MediaChunk mediaChunk = this.mediaChunks.getLast();
           Chunk currentLoadable = this.currentLoadableHolder.chunk;
           if (currentLoadable != null && mediaChunk == currentLoadable) {
               long chunkLength = mediaChunk.getLength();
                        if (chunkLength != -1) {
                   return mediaChunk.startTimeUs + (((mediaChunk.endTimeUs - mediaChunk.startTimeUs) * mediaChunk.bytesLoaded()) / chunkLength);
                        }
               return mediaChunk.startTimeUs;
                    }
           if (mediaChunk.isLastChunk()) {
               return -3L;
                    }
           return mediaChunk.endTimeUs;
                }

                @Override 
       public void release() {
           Assertions.checkState(this.state != 2);
           if (this.loader != null) {
               this.loader.release();
               this.loader = null;
                    }
           this.state = 0;
                }

                @Override 
       public void onLoaded() {
           Chunk currentLoadable = this.currentLoadableHolder.chunk;
           notifyLoadCompleted(currentLoadable.bytesLoaded());
                    try {
                        try {
                   currentLoadable.consume();
                   if (!isMediaChunk(currentLoadable)) {
                       currentLoadable.release();
                            }
                   if (!this.currentLoadableExceptionFatal) {
                       clearCurrentLoadable();
                            }
                   updateLoadControl();
                        } catch (IOException e) {
                   this.currentLoadableException = e;
                   this.currentLoadableExceptionCount++;
                   this.currentLoadableExceptionTimestamp = SystemClock.elapsedRealtime();
                   this.currentLoadableExceptionFatal = true;
                   notifyConsumptionError(e);
                   if (!isMediaChunk(currentLoadable)) {
                       currentLoadable.release();
                            }
                   if (!this.currentLoadableExceptionFatal) {
                       clearCurrentLoadable();
                            }
                   updateLoadControl();
                        }
                    } catch (Throwable th) {
                        if (!isMediaChunk(currentLoadable)) {
                   currentLoadable.release();
                        }
               if (!this.currentLoadableExceptionFatal) {
                   clearCurrentLoadable();
                        }
               updateLoadControl();
                        throw th;
                    }
                }

                @Override 
       public void onCanceled() {
           Chunk currentLoadable = this.currentLoadableHolder.chunk;
           notifyLoadCanceled(currentLoadable.bytesLoaded());
           if (!isMediaChunk(currentLoadable)) {
               currentLoadable.release();
                    }
           clearCurrentLoadable();
           if (this.state == 2) {
               restartFrom(this.pendingResetTime);
                    } else {
               clearMediaChunks();
               this.loadControl.trimAllocator();
                    }
                }

                @Override 
       public void onError(IOException e) {
           this.currentLoadableException = e;
           this.currentLoadableExceptionCount++;
           this.currentLoadableExceptionTimestamp = SystemClock.elapsedRealtime();
           notifyUpstreamError(e);
           this.chunkSource.onChunkLoadError(this.currentLoadableHolder.chunk, e);
           updateLoadControl();
                }

       protected void onSampleRead(MediaChunk mediaChunk, SampleHolder sampleHolder) {
                }

       private void restartFrom(long timeUs) {
           this.pendingResetTime = timeUs;
           if (this.loader.isLoading()) {
               this.loader.cancelLoading();
               return;
                    }
           clearMediaChunks();
           clearCurrentLoadable();
           updateLoadControl();
                }

       private void clearMediaChunks() {
           discardDownstreamMediaChunks(null);
                }

       private void clearCurrentLoadable() {
           this.currentLoadableHolder.chunk = null;
           this.currentLoadableException = null;
           this.currentLoadableExceptionCount = 0;
           this.currentLoadableExceptionFatal = false;
                }

       private void updateLoadControl() {
                    long loadPositionUs;
           if (isPendingReset()) {
               loadPositionUs = this.pendingResetTime;
                    } else {
               MediaChunk lastMediaChunk = this.mediaChunks.getLast();
               loadPositionUs = lastMediaChunk.nextChunkIndex == -1 ? -1L : lastMediaChunk.endTimeUs;
                    }
           boolean isBackedOff = (this.currentLoadableException == null || this.currentLoadableExceptionFatal) ? false : true;
           boolean nextLoader = this.loadControl.update(this, this.downstreamPositionUs, loadPositionUs, isBackedOff || this.loader.isLoading(), this.currentLoadableExceptionFatal);
           if (!this.currentLoadableExceptionFatal) {
               long now = SystemClock.elapsedRealtime();
               if (isBackedOff) {
                   long elapsedMillis = now - this.currentLoadableExceptionTimestamp;
                            if (elapsedMillis >= getRetryDelayMillis(this.currentLoadableExceptionCount)) {
                       resumeFromBackOff();
                       return;
                            }
                   return;
                        }
               if (!this.loader.isLoading()) {
                   if (this.currentLoadableHolder.chunk == null || now - this.lastPerformedBufferOperation > 1000) {
                       this.lastPerformedBufferOperation = now;
                       this.currentLoadableHolder.queueSize = this.readOnlyMediaChunks.size();
                       this.chunkSource.getChunkOperation(this.readOnlyMediaChunks, this.pendingResetTime, this.downstreamPositionUs, this.currentLoadableHolder);
                       discardUpstreamMediaChunks(this.currentLoadableHolder.queueSize);
                            }
                   if (nextLoader) {
                       maybeStartLoading();
                            }
                        }
                    }
                }

       private void resumeFromBackOff() {
           this.currentLoadableException = null;
           Chunk backedOffChunk = this.currentLoadableHolder.chunk;
           if (!isMediaChunk(backedOffChunk)) {
               this.currentLoadableHolder.queueSize = this.readOnlyMediaChunks.size();
               this.chunkSource.getChunkOperation(this.readOnlyMediaChunks, this.pendingResetTime, this.downstreamPositionUs, this.currentLoadableHolder);
               discardUpstreamMediaChunks(this.currentLoadableHolder.queueSize);
               if (this.currentLoadableHolder.chunk == backedOffChunk) {
                   this.loader.startLoading(backedOffChunk);
                   return;
                        } else {
                   backedOffChunk.release();
                   maybeStartLoading();
                   return;
                        }
                    }
           if (backedOffChunk == this.mediaChunks.getFirst()) {
               this.loader.startLoading(backedOffChunk);
               return;
                    }
           MediaChunk removedChunk = this.mediaChunks.removeLast();
           Assertions.checkState(backedOffChunk == removedChunk);
           this.currentLoadableHolder.queueSize = this.readOnlyMediaChunks.size();
           this.chunkSource.getChunkOperation(this.readOnlyMediaChunks, this.pendingResetTime, this.downstreamPositionUs, this.currentLoadableHolder);
           this.mediaChunks.add(removedChunk);
           if (this.currentLoadableHolder.chunk == backedOffChunk) {
               this.loader.startLoading(backedOffChunk);
               return;
                    }
           discardUpstreamMediaChunks(this.currentLoadableHolder.queueSize);
           clearCurrentLoadable();
           maybeStartLoading();
                }

       private void maybeStartLoading() {
           Chunk currentLoadable = this.currentLoadableHolder.chunk;
           if (currentLoadable != null) {
               currentLoadable.init(this.loadControl.getAllocator());
               if (isMediaChunk(currentLoadable)) {
                   MediaChunk mediaChunk = (MediaChunk) currentLoadable;
                   if (isPendingReset()) {
                       mediaChunk.seekTo(this.pendingResetTime, false);
                       this.pendingResetTime = -1L;
                            }
                   this.mediaChunks.add(mediaChunk);
                   notifyLoadStarted(mediaChunk.format.f875id, mediaChunk.trigger, false, mediaChunk.startTimeUs, mediaChunk.endTimeUs, mediaChunk.getLength());
                        } else {
                   notifyLoadStarted(currentLoadable.format.f875id, currentLoadable.trigger, true, -1L, -1L, currentLoadable.getLength());
                        }
               this.loader.startLoading(currentLoadable);
                    }
                }

       private void discardDownstreamMediaChunks(MediaChunk untilChunk) {
           if (!this.mediaChunks.isEmpty() && untilChunk != this.mediaChunks.getFirst()) {
               long totalBytes = 0;
               long startTimeUs = this.mediaChunks.getFirst().startTimeUs;
               long endTimeUs = 0;
               while (!this.mediaChunks.isEmpty() && untilChunk != this.mediaChunks.getFirst()) {
                   MediaChunk removed = this.mediaChunks.removeFirst();
                   totalBytes += removed.bytesLoaded();
                   endTimeUs = removed.endTimeUs;
                   removed.release();
                        }
               notifyDownstreamDiscarded(startTimeUs, endTimeUs, totalBytes);
                    }
                }

       private void discardDownstreamMediaChunk() {
           MediaChunk removed = this.mediaChunks.removeFirst();
           long totalBytes = removed.bytesLoaded();
           removed.release();
           notifyDownstreamDiscarded(removed.startTimeUs, removed.endTimeUs, totalBytes);
                }

       private void discardUpstreamMediaChunks(int queueLength) {
           if (this.mediaChunks.size() > queueLength) {
               long totalBytes = 0;
               long startTimeUs = 0;
               long endTimeUs = this.mediaChunks.getLast().endTimeUs;
               while (this.mediaChunks.size() > queueLength) {
                   MediaChunk removed = this.mediaChunks.removeLast();
                   totalBytes += removed.bytesLoaded();
                   startTimeUs = removed.startTimeUs;
                   removed.release();
                        }
               notifyUpstreamDiscarded(startTimeUs, endTimeUs, totalBytes);
                    }
                }

       private boolean isMediaChunk(Chunk chunk) {
           return chunk instanceof MediaChunk;
                }

       private boolean isPendingReset() {
                    return this.pendingResetTime != -1;
                }

       private long getRetryDelayMillis(long errorCount) {
           return Math.min((errorCount - 1) * 1000, 5000L);
                }

       protected final int usToMs(long timeUs) {
           return (int) (timeUs / 1000);
                }

                
       class RunnableC08611 implements Runnable {
                    final  String val$formatId;
                    final  boolean val$isInitialization;
                    final  long val$length;
                    final  long val$mediaEndTimeUs;
                    final  long val$mediaStartTimeUs;
                    final  int val$trigger;

           RunnableC08611(String str, int i, boolean z, long j, long j2, long j3) {
                        this.val$formatId = str;
                        this.val$trigger = i;
                        this.val$isInitialization = z;
                        this.val$mediaStartTimeUs = j;
                        this.val$mediaEndTimeUs = j2;
                        this.val$length = j3;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onLoadStarted(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$formatId, this.val$trigger, this.val$isInitialization, ChunkSampleSource.this.usToMs(this.val$mediaStartTimeUs), ChunkSampleSource.this.usToMs(this.val$mediaEndTimeUs), this.val$length);
                    }
                }

       private void notifyLoadStarted(String formatId, int trigger, boolean isInitialization, long mediaStartTimeUs, long mediaEndTimeUs, long length) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08611(formatId, trigger, isInitialization, mediaStartTimeUs, mediaEndTimeUs, length));
                    }
                }

                
       class RunnableC08622 implements Runnable {
                    final  long val$bytesLoaded;

           RunnableC08622(long j) {
                        this.val$bytesLoaded = j;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onLoadCompleted(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$bytesLoaded);
                    }
                }

       private void notifyLoadCompleted(long bytesLoaded) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08622(bytesLoaded));
                    }
                }

                
       class RunnableC08633 implements Runnable {
                    final  long val$bytesLoaded;

           RunnableC08633(long j) {
                        this.val$bytesLoaded = j;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onLoadCanceled(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$bytesLoaded);
                    }
                }

       private void notifyLoadCanceled(long bytesLoaded) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08633(bytesLoaded));
                    }
                }

                
       class RunnableC08644 implements Runnable {
                    final  IOException val$e;

           RunnableC08644(IOException iOException) {
                        this.val$e = iOException;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onUpstreamError(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$e);
                    }
                }

       private void notifyUpstreamError(IOException e) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08644(e));
                    }
                }

                
       class RunnableC08655 implements Runnable {
                    final  IOException val$e;

           RunnableC08655(IOException iOException) {
                        this.val$e = iOException;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onConsumptionError(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$e);
                    }
                }

       private void notifyConsumptionError(IOException e) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08655(e));
                    }
                }

                
       class RunnableC08666 implements Runnable {
                    final  long val$mediaEndTimeUs;
                    final  long val$mediaStartTimeUs;
                    final  long val$totalBytes;

           RunnableC08666(long j, long j2, long j3) {
                        this.val$mediaStartTimeUs = j;
                        this.val$mediaEndTimeUs = j2;
                        this.val$totalBytes = j3;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onUpstreamDiscarded(ChunkSampleSource.access$000(ChunkSampleSource.this), ChunkSampleSource.this.usToMs(this.val$mediaStartTimeUs), ChunkSampleSource.this.usToMs(this.val$mediaEndTimeUs), this.val$totalBytes);
                    }
                }

       private void notifyUpstreamDiscarded(long mediaStartTimeUs, long mediaEndTimeUs, long totalBytes) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08666(mediaStartTimeUs, mediaEndTimeUs, totalBytes));
                    }
                }

                
       class RunnableC08677 implements Runnable {
                    final  String val$formatId;
                    final  long val$mediaTimeUs;
                    final  int val$trigger;

           RunnableC08677(String str, int i, long j) {
                        this.val$formatId = str;
                        this.val$trigger = i;
                        this.val$mediaTimeUs = j;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onDownstreamFormatChanged(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$formatId, this.val$trigger, ChunkSampleSource.this.usToMs(this.val$mediaTimeUs));
                    }
                }

       private void notifyDownstreamFormatChanged(String formatId, int trigger, long mediaTimeUs) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08677(formatId, trigger, mediaTimeUs));
                    }
                }

                
       class RunnableC08688 implements Runnable {
                    final  long val$bytesDiscarded;
                    final  long val$mediaEndTimeUs;
                    final  long val$mediaStartTimeUs;

           RunnableC08688(long j, long j2, long j3) {
                        this.val$mediaStartTimeUs = j;
                        this.val$mediaEndTimeUs = j2;
                        this.val$bytesDiscarded = j3;
                    }

                    @Override 
           public void run() {
               ChunkSampleSource.access$100(ChunkSampleSource.this).onDownstreamDiscarded(ChunkSampleSource.access$000(ChunkSampleSource.this), ChunkSampleSource.this.usToMs(this.val$mediaStartTimeUs), ChunkSampleSource.this.usToMs(this.val$mediaEndTimeUs), this.val$bytesDiscarded);
                    }
                }

       private void notifyDownstreamDiscarded(long mediaStartTimeUs, long mediaEndTimeUs, long bytesDiscarded) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08688(mediaStartTimeUs, mediaEndTimeUs, bytesDiscarded));
                    }
                }
            }
