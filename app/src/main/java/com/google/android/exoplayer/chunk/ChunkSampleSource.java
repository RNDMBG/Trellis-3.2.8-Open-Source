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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class ChunkSampleSource implements SampleSource, Loader.Listener {
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

/* 41 */        static /* synthetic */ int access$000(ChunkSampleSource x0) {
/* 42 */            return x0.eventSourceId;
                }

/* 41 */        static /* synthetic */ EventListener access$100(ChunkSampleSource x0) {
/* 42 */            return x0.eventListener;
                }

/* 170 */       public ChunkSampleSource(ChunkSource chunkSource, LoadControl loadControl, int bufferSizeContribution, boolean frameAccurateSeeking) {
/* 171 */           this(chunkSource, loadControl, bufferSizeContribution, frameAccurateSeeking, null, null, 0);
                }

/* 175 */       public ChunkSampleSource(ChunkSource chunkSource, LoadControl loadControl, int bufferSizeContribution, boolean frameAccurateSeeking, Handler eventHandler, EventListener eventListener, int eventSourceId) {
/* 177 */           this.chunkSource = chunkSource;
/* 178 */           this.loadControl = loadControl;
/* 179 */           this.bufferSizeContribution = bufferSizeContribution;
/* 180 */           this.frameAccurateSeeking = frameAccurateSeeking;
/* 181 */           this.eventHandler = eventHandler;
/* 182 */           this.eventListener = eventListener;
/* 183 */           this.eventSourceId = eventSourceId;
/* 184 */           this.currentLoadableHolder = new ChunkOperationHolder();
/* 185 */           this.mediaChunks = new LinkedList<>();
/* 186 */           this.readOnlyMediaChunks = Collections.unmodifiableList(this.mediaChunks);
/* 187 */           this.state = 0;
                }

/* 195 */       public Format getFormat() {
/* 196 */           return this.downstreamFormat;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 200 */       public boolean prepare() {
/* 201 */           Assertions.checkState(this.state == 0);
/* 202 */           this.loader = new Loader("Loader:" + this.chunkSource.getTrackInfo().mimeType, this);
/* 203 */           this.state = 1;
                    return true;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 208 */       public int getTrackCount() {
/* 209 */           Assertions.checkState(this.state != 0);
                    return 1;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 214 */       public TrackInfo getTrackInfo(int track) {
/* 215 */           Assertions.checkState(this.state != 0);
/* 216 */           Assertions.checkState(track == 0);
/* 217 */           return this.chunkSource.getTrackInfo();
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 221 */       public void enable(int track, long timeUs) {
/* 222 */           Assertions.checkState(this.state == 1);
/* 223 */           Assertions.checkState(track == 0);
/* 224 */           this.state = 2;
/* 225 */           this.chunkSource.enable();
/* 226 */           this.loadControl.register(this, this.bufferSizeContribution);
/* 227 */           this.downstreamFormat = null;
/* 228 */           this.downstreamMediaFormat = null;
/* 229 */           this.downstreamPositionUs = timeUs;
/* 230 */           this.lastSeekPositionUs = timeUs;
/* 231 */           restartFrom(timeUs);
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 235 */       public void disable(int track) {
/* 236 */           Assertions.checkState(this.state == 2);
/* 237 */           Assertions.checkState(track == 0);
/* 238 */           this.pendingDiscontinuity = false;
/* 239 */           this.state = 1;
/* 240 */           this.loadControl.unregister(this);
/* 241 */           this.chunkSource.disable(this.mediaChunks);
/* 242 */           if (this.loader.isLoading()) {
/* 243 */               this.loader.cancelLoading();
/* 249 */               return;
                    }
/* 245 */           clearMediaChunks();
/* 246 */           clearCurrentLoadable();
/* 247 */           this.loadControl.trimAllocator();
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 252 */       public boolean continueBuffering(long playbackPositionUs) throws IOException {
/* 253 */           Assertions.checkState(this.state == 2);
/* 254 */           this.downstreamPositionUs = playbackPositionUs;
/* 255 */           this.chunkSource.continueBuffering(playbackPositionUs);
/* 256 */           updateLoadControl();
/* 257 */           if (isPendingReset() || this.mediaChunks.isEmpty()) {
                        return false;
                    }
/* 259 */           if (this.mediaChunks.getFirst().sampleAvailable()) {
                        return true;
                    }
                    return this.mediaChunks.size() > 1 && this.mediaChunks.get(1).sampleAvailable();
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 271 */       public int readData(int track, long playbackPositionUs, MediaFormatHolder formatHolder, SampleHolder sampleHolder, boolean onlyReadDiscontinuity) throws IOException {
/* 272 */           Assertions.checkState(this.state == 2);
/* 273 */           Assertions.checkState(track == 0);
/* 275 */           if (this.pendingDiscontinuity) {
/* 276 */               this.pendingDiscontinuity = false;
/* 277 */               return -5;
                    }
/* 280 */           if (onlyReadDiscontinuity) {
/* 281 */               return -2;
                    }
/* 284 */           this.downstreamPositionUs = playbackPositionUs;
/* 285 */           if (isPendingReset()) {
/* 286 */               if (this.currentLoadableException != null) {
/* 287 */                   throw this.currentLoadableException;
                        }
/* 289 */               IOException chunkSourceException = this.chunkSource.getError();
/* 290 */               if (chunkSourceException != null) {
/* 291 */                   throw chunkSourceException;
                        }
/* 293 */               return -2;
                    }
/* 296 */           MediaChunk mediaChunk = this.mediaChunks.getFirst();
/* 297 */           if (mediaChunk.isReadFinished()) {
/* 299 */               if (this.mediaChunks.size() > 1) {
/* 300 */                   discardDownstreamMediaChunk();
/* 302 */                   this.mediaChunks.getFirst().seekToStart();
/* 303 */                   return readData(track, playbackPositionUs, formatHolder, sampleHolder, false);
                        }
/* 304 */               if (mediaChunk.isLastChunk()) {
/* 305 */                   return -1;
                        }
/* 307 */               IOException chunkSourceException2 = this.chunkSource.getError();
/* 308 */               if (chunkSourceException2 != null) {
/* 309 */                   throw chunkSourceException2;
                        }
/* 311 */               return -2;
                    }
/* 314 */           if (this.downstreamFormat == null || !this.downstreamFormat.equals(mediaChunk.format)) {
/* 315 */               notifyDownstreamFormatChanged(mediaChunk.format.f875id, mediaChunk.trigger, mediaChunk.startTimeUs);
/* 317 */               this.downstreamFormat = mediaChunk.format;
                    }
/* 320 */           if (!mediaChunk.prepare()) {
/* 321 */               if (this.currentLoadableException != null) {
/* 322 */                   throw this.currentLoadableException;
                        }
/* 324 */               return -2;
                    }
/* 327 */           MediaFormat mediaFormat = mediaChunk.getMediaFormat();
/* 328 */           if (mediaFormat != null && !mediaFormat.equals(this.downstreamMediaFormat, true)) {
/* 329 */               this.chunkSource.getMaxVideoDimensions(mediaFormat);
/* 330 */               formatHolder.format = mediaFormat;
/* 331 */               formatHolder.drmInitData = mediaChunk.getPsshInfo();
/* 332 */               this.downstreamMediaFormat = mediaFormat;
/* 333 */               return -4;
                    }
/* 336 */           if (mediaChunk.read(sampleHolder)) {
/* 337 */               sampleHolder.decodeOnly = this.frameAccurateSeeking && sampleHolder.timeUs < this.lastSeekPositionUs;
/* 338 */               onSampleRead(mediaChunk, sampleHolder);
/* 339 */               return -3;
                    }
/* 341 */           if (this.currentLoadableException != null) {
/* 342 */               throw this.currentLoadableException;
                    }
/* 344 */           return -2;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 349 */       public void seekToUs(long timeUs) {
/* 350 */           Assertions.checkState(this.state == 2);
/* 351 */           this.downstreamPositionUs = timeUs;
/* 352 */           this.lastSeekPositionUs = timeUs;
                    if (this.pendingResetTime != timeUs) {
/* 357 */               MediaChunk mediaChunk = getMediaChunk(timeUs);
/* 358 */               if (mediaChunk == null) {
/* 359 */                   restartFrom(timeUs);
/* 360 */                   this.pendingDiscontinuity = true;
                        } else {
                            this.pendingDiscontinuity |= mediaChunk.seekTo(timeUs, mediaChunk == this.mediaChunks.getFirst());
/* 363 */                   discardDownstreamMediaChunks(mediaChunk);
/* 364 */                   updateLoadControl();
                        }
                    }
                }

/* 368 */       private MediaChunk getMediaChunk(long timeUs) {
/* 369 */           Iterator<MediaChunk> mediaChunkIterator = this.mediaChunks.iterator();
/* 370 */           while (mediaChunkIterator.hasNext()) {
/* 371 */               MediaChunk mediaChunk = mediaChunkIterator.next();
                        if (timeUs < mediaChunk.startTimeUs) {
                            return null;
                        }
/* 374 */               if (mediaChunk.isLastChunk() || timeUs < mediaChunk.endTimeUs) {
/* 378 */                   return mediaChunk;
                        }
                    }
                    return null;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 382 */       public long getBufferedPositionUs() {
/* 383 */           Assertions.checkState(this.state == 2);
/* 384 */           if (isPendingReset()) {
/* 385 */               return this.pendingResetTime;
                    }
/* 387 */           MediaChunk mediaChunk = this.mediaChunks.getLast();
/* 388 */           Chunk currentLoadable = this.currentLoadableHolder.chunk;
/* 389 */           if (currentLoadable != null && mediaChunk == currentLoadable) {
/* 391 */               long chunkLength = mediaChunk.getLength();
                        if (chunkLength != -1) {
/* 393 */                   return mediaChunk.startTimeUs + (((mediaChunk.endTimeUs - mediaChunk.startTimeUs) * mediaChunk.bytesLoaded()) / chunkLength);
                        }
/* 396 */               return mediaChunk.startTimeUs;
                    }
/* 398 */           if (mediaChunk.isLastChunk()) {
/* 399 */               return -3L;
                    }
/* 401 */           return mediaChunk.endTimeUs;
                }

                @Override // com.google.android.exoplayer.SampleSource
/* 406 */       public void release() {
/* 407 */           Assertions.checkState(this.state != 2);
/* 408 */           if (this.loader != null) {
/* 409 */               this.loader.release();
/* 410 */               this.loader = null;
                    }
/* 412 */           this.state = 0;
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Listener
/* 416 */       public void onLoaded() {
/* 417 */           Chunk currentLoadable = this.currentLoadableHolder.chunk;
/* 418 */           notifyLoadCompleted(currentLoadable.bytesLoaded());
                    try {
                        try {
/* 420 */                   currentLoadable.consume();
/* 428 */                   if (!isMediaChunk(currentLoadable)) {
/* 429 */                       currentLoadable.release();
                            }
/* 431 */                   if (!this.currentLoadableExceptionFatal) {
/* 432 */                       clearCurrentLoadable();
                            }
/* 434 */                   updateLoadControl();
                        } catch (IOException e) {
/* 422 */                   this.currentLoadableException = e;
/* 423 */                   this.currentLoadableExceptionCount++;
/* 424 */                   this.currentLoadableExceptionTimestamp = SystemClock.elapsedRealtime();
/* 425 */                   this.currentLoadableExceptionFatal = true;
/* 426 */                   notifyConsumptionError(e);
/* 428 */                   if (!isMediaChunk(currentLoadable)) {
/* 429 */                       currentLoadable.release();
                            }
/* 431 */                   if (!this.currentLoadableExceptionFatal) {
/* 432 */                       clearCurrentLoadable();
                            }
/* 434 */                   updateLoadControl();
                        }
                    } catch (Throwable th) {
                        if (!isMediaChunk(currentLoadable)) {
/* 429 */                   currentLoadable.release();
                        }
/* 431 */               if (!this.currentLoadableExceptionFatal) {
/* 432 */                   clearCurrentLoadable();
                        }
/* 434 */               updateLoadControl();
                        throw th;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Listener
/* 439 */       public void onCanceled() {
/* 440 */           Chunk currentLoadable = this.currentLoadableHolder.chunk;
/* 441 */           notifyLoadCanceled(currentLoadable.bytesLoaded());
/* 442 */           if (!isMediaChunk(currentLoadable)) {
/* 443 */               currentLoadable.release();
                    }
/* 445 */           clearCurrentLoadable();
/* 446 */           if (this.state == 2) {
/* 447 */               restartFrom(this.pendingResetTime);
                    } else {
/* 449 */               clearMediaChunks();
/* 450 */               this.loadControl.trimAllocator();
                    }
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Listener
/* 455 */       public void onError(IOException e) {
/* 456 */           this.currentLoadableException = e;
/* 457 */           this.currentLoadableExceptionCount++;
/* 458 */           this.currentLoadableExceptionTimestamp = SystemClock.elapsedRealtime();
/* 459 */           notifyUpstreamError(e);
/* 460 */           this.chunkSource.onChunkLoadError(this.currentLoadableHolder.chunk, e);
/* 461 */           updateLoadControl();
                }

/* 472 */       protected void onSampleRead(MediaChunk mediaChunk, SampleHolder sampleHolder) {
                }

/* 475 */       private void restartFrom(long timeUs) {
/* 476 */           this.pendingResetTime = timeUs;
/* 477 */           if (this.loader.isLoading()) {
/* 478 */               this.loader.cancelLoading();
/* 484 */               return;
                    }
/* 480 */           clearMediaChunks();
/* 481 */           clearCurrentLoadable();
/* 482 */           updateLoadControl();
                }

/* 486 */       private void clearMediaChunks() {
/* 487 */           discardDownstreamMediaChunks(null);
                }

/* 490 */       private void clearCurrentLoadable() {
/* 491 */           this.currentLoadableHolder.chunk = null;
/* 492 */           this.currentLoadableException = null;
/* 493 */           this.currentLoadableExceptionCount = 0;
/* 494 */           this.currentLoadableExceptionFatal = false;
                }

/* 498 */       private void updateLoadControl() {
                    long loadPositionUs;
/* 499 */           if (isPendingReset()) {
/* 500 */               loadPositionUs = this.pendingResetTime;
                    } else {
/* 502 */               MediaChunk lastMediaChunk = this.mediaChunks.getLast();
/* 503 */               loadPositionUs = lastMediaChunk.nextChunkIndex == -1 ? -1L : lastMediaChunk.endTimeUs;
                    }
/* 506 */           boolean isBackedOff = (this.currentLoadableException == null || this.currentLoadableExceptionFatal) ? false : true;
/* 507 */           boolean nextLoader = this.loadControl.update(this, this.downstreamPositionUs, loadPositionUs, isBackedOff || this.loader.isLoading(), this.currentLoadableExceptionFatal);
/* 510 */           if (!this.currentLoadableExceptionFatal) {
/* 514 */               long now = SystemClock.elapsedRealtime();
/* 516 */               if (isBackedOff) {
/* 517 */                   long elapsedMillis = now - this.currentLoadableExceptionTimestamp;
                            if (elapsedMillis >= getRetryDelayMillis(this.currentLoadableExceptionCount)) {
/* 519 */                       resumeFromBackOff();
/* 536 */                       return;
                            }
/* 536 */                   return;
                        }
/* 524 */               if (!this.loader.isLoading()) {
/* 525 */                   if (this.currentLoadableHolder.chunk == null || now - this.lastPerformedBufferOperation > 1000) {
/* 526 */                       this.lastPerformedBufferOperation = now;
/* 527 */                       this.currentLoadableHolder.queueSize = this.readOnlyMediaChunks.size();
/* 528 */                       this.chunkSource.getChunkOperation(this.readOnlyMediaChunks, this.pendingResetTime, this.downstreamPositionUs, this.currentLoadableHolder);
/* 530 */                       discardUpstreamMediaChunks(this.currentLoadableHolder.queueSize);
                            }
/* 532 */                   if (nextLoader) {
/* 533 */                       maybeStartLoading();
                            }
                        }
                    }
                }

/* 545 */       private void resumeFromBackOff() {
/* 546 */           this.currentLoadableException = null;
/* 548 */           Chunk backedOffChunk = this.currentLoadableHolder.chunk;
/* 549 */           if (!isMediaChunk(backedOffChunk)) {
/* 550 */               this.currentLoadableHolder.queueSize = this.readOnlyMediaChunks.size();
/* 551 */               this.chunkSource.getChunkOperation(this.readOnlyMediaChunks, this.pendingResetTime, this.downstreamPositionUs, this.currentLoadableHolder);
/* 553 */               discardUpstreamMediaChunks(this.currentLoadableHolder.queueSize);
/* 554 */               if (this.currentLoadableHolder.chunk == backedOffChunk) {
/* 556 */                   this.loader.startLoading(backedOffChunk);
/* 590 */                   return;
                        } else {
/* 558 */                   backedOffChunk.release();
/* 559 */                   maybeStartLoading();
/* 590 */                   return;
                        }
                    }
/* 564 */           if (backedOffChunk == this.mediaChunks.getFirst()) {
/* 567 */               this.loader.startLoading(backedOffChunk);
/* 590 */               return;
                    }
/* 573 */           MediaChunk removedChunk = this.mediaChunks.removeLast();
/* 574 */           Assertions.checkState(backedOffChunk == removedChunk);
/* 575 */           this.currentLoadableHolder.queueSize = this.readOnlyMediaChunks.size();
/* 576 */           this.chunkSource.getChunkOperation(this.readOnlyMediaChunks, this.pendingResetTime, this.downstreamPositionUs, this.currentLoadableHolder);
/* 578 */           this.mediaChunks.add(removedChunk);
/* 580 */           if (this.currentLoadableHolder.chunk == backedOffChunk) {
/* 582 */               this.loader.startLoading(backedOffChunk);
/* 590 */               return;
                    }
/* 586 */           discardUpstreamMediaChunks(this.currentLoadableHolder.queueSize);
/* 587 */           clearCurrentLoadable();
/* 588 */           maybeStartLoading();
                }

/* 592 */       private void maybeStartLoading() {
/* 593 */           Chunk currentLoadable = this.currentLoadableHolder.chunk;
/* 594 */           if (currentLoadable != null) {
/* 598 */               currentLoadable.init(this.loadControl.getAllocator());
/* 599 */               if (isMediaChunk(currentLoadable)) {
/* 600 */                   MediaChunk mediaChunk = (MediaChunk) currentLoadable;
/* 601 */                   if (isPendingReset()) {
/* 602 */                       mediaChunk.seekTo(this.pendingResetTime, false);
/* 603 */                       this.pendingResetTime = -1L;
                            }
/* 605 */                   this.mediaChunks.add(mediaChunk);
/* 606 */                   notifyLoadStarted(mediaChunk.format.f875id, mediaChunk.trigger, false, mediaChunk.startTimeUs, mediaChunk.endTimeUs, mediaChunk.getLength());
                        } else {
/* 609 */                   notifyLoadStarted(currentLoadable.format.f875id, currentLoadable.trigger, true, -1L, -1L, currentLoadable.getLength());
                        }
/* 612 */               this.loader.startLoading(currentLoadable);
                    }
                }

/* 621 */       private void discardDownstreamMediaChunks(MediaChunk untilChunk) {
/* 622 */           if (!this.mediaChunks.isEmpty() && untilChunk != this.mediaChunks.getFirst()) {
/* 625 */               long totalBytes = 0;
/* 626 */               long startTimeUs = this.mediaChunks.getFirst().startTimeUs;
/* 627 */               long endTimeUs = 0;
/* 628 */               while (!this.mediaChunks.isEmpty() && untilChunk != this.mediaChunks.getFirst()) {
/* 629 */                   MediaChunk removed = this.mediaChunks.removeFirst();
/* 630 */                   totalBytes += removed.bytesLoaded();
/* 631 */                   endTimeUs = removed.endTimeUs;
/* 632 */                   removed.release();
                        }
/* 634 */               notifyDownstreamDiscarded(startTimeUs, endTimeUs, totalBytes);
                    }
                }

/* 640 */       private void discardDownstreamMediaChunk() {
/* 641 */           MediaChunk removed = this.mediaChunks.removeFirst();
/* 642 */           long totalBytes = removed.bytesLoaded();
/* 643 */           removed.release();
/* 644 */           notifyDownstreamDiscarded(removed.startTimeUs, removed.endTimeUs, totalBytes);
                }

/* 652 */       private void discardUpstreamMediaChunks(int queueLength) {
/* 653 */           if (this.mediaChunks.size() > queueLength) {
/* 656 */               long totalBytes = 0;
/* 657 */               long startTimeUs = 0;
/* 658 */               long endTimeUs = this.mediaChunks.getLast().endTimeUs;
/* 659 */               while (this.mediaChunks.size() > queueLength) {
/* 660 */                   MediaChunk removed = this.mediaChunks.removeLast();
/* 661 */                   totalBytes += removed.bytesLoaded();
/* 662 */                   startTimeUs = removed.startTimeUs;
/* 663 */                   removed.release();
                        }
/* 665 */               notifyUpstreamDiscarded(startTimeUs, endTimeUs, totalBytes);
                    }
                }

/* 668 */       private boolean isMediaChunk(Chunk chunk) {
/* 669 */           return chunk instanceof MediaChunk;
                }

/* 672 */       private boolean isPendingReset() {
                    return this.pendingResetTime != -1;
                }

/* 676 */       private long getRetryDelayMillis(long errorCount) {
/* 677 */           return Math.min((errorCount - 1) * 1000, 5000L);
                }

/* 680 */       protected final int usToMs(long timeUs) {
/* 681 */           return (int) (timeUs / 1000);
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$1 */
/* 686 */       class RunnableC08611 implements Runnable {
                    final /* synthetic */ String val$formatId;
                    final /* synthetic */ boolean val$isInitialization;
                    final /* synthetic */ long val$length;
                    final /* synthetic */ long val$mediaEndTimeUs;
                    final /* synthetic */ long val$mediaStartTimeUs;
                    final /* synthetic */ int val$trigger;

/* 687 */           RunnableC08611(String str, int i, boolean z, long j, long j2, long j3) {
                        this.val$formatId = str;
                        this.val$trigger = i;
                        this.val$isInitialization = z;
                        this.val$mediaStartTimeUs = j;
                        this.val$mediaEndTimeUs = j2;
                        this.val$length = j3;
                    }

                    @Override // java.lang.Runnable
/* 690 */           public void run() {
/* 691 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onLoadStarted(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$formatId, this.val$trigger, this.val$isInitialization, ChunkSampleSource.this.usToMs(this.val$mediaStartTimeUs), ChunkSampleSource.this.usToMs(this.val$mediaEndTimeUs), this.val$length);
                    }
                }

/* 686 */       private void notifyLoadStarted(String formatId, int trigger, boolean isInitialization, long mediaStartTimeUs, long mediaEndTimeUs, long length) {
/* 687 */           if (this.eventHandler != null && this.eventListener != null) {
/* 688 */               this.eventHandler.post(new RunnableC08611(formatId, trigger, isInitialization, mediaStartTimeUs, mediaEndTimeUs, length));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$2 */
/* 698 */       class RunnableC08622 implements Runnable {
                    final /* synthetic */ long val$bytesLoaded;

/* 699 */           RunnableC08622(long j) {
                        this.val$bytesLoaded = j;
                    }

                    @Override // java.lang.Runnable
/* 702 */           public void run() {
/* 703 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onLoadCompleted(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$bytesLoaded);
                    }
                }

/* 698 */       private void notifyLoadCompleted(long bytesLoaded) {
/* 699 */           if (this.eventHandler != null && this.eventListener != null) {
/* 700 */               this.eventHandler.post(new RunnableC08622(bytesLoaded));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$3 */
/* 709 */       class RunnableC08633 implements Runnable {
                    final /* synthetic */ long val$bytesLoaded;

/* 710 */           RunnableC08633(long j) {
                        this.val$bytesLoaded = j;
                    }

                    @Override // java.lang.Runnable
/* 713 */           public void run() {
/* 714 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onLoadCanceled(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$bytesLoaded);
                    }
                }

/* 709 */       private void notifyLoadCanceled(long bytesLoaded) {
/* 710 */           if (this.eventHandler != null && this.eventListener != null) {
/* 711 */               this.eventHandler.post(new RunnableC08633(bytesLoaded));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$4 */
/* 720 */       class RunnableC08644 implements Runnable {
                    final /* synthetic */ IOException val$e;

/* 721 */           RunnableC08644(IOException iOException) {
                        this.val$e = iOException;
                    }

                    @Override // java.lang.Runnable
/* 724 */           public void run() {
/* 725 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onUpstreamError(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$e);
                    }
                }

/* 720 */       private void notifyUpstreamError(IOException e) {
/* 721 */           if (this.eventHandler != null && this.eventListener != null) {
/* 722 */               this.eventHandler.post(new RunnableC08644(e));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$5 */
/* 731 */       class RunnableC08655 implements Runnable {
                    final /* synthetic */ IOException val$e;

/* 732 */           RunnableC08655(IOException iOException) {
                        this.val$e = iOException;
                    }

                    @Override // java.lang.Runnable
/* 735 */           public void run() {
/* 736 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onConsumptionError(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$e);
                    }
                }

/* 731 */       private void notifyConsumptionError(IOException e) {
/* 732 */           if (this.eventHandler != null && this.eventListener != null) {
/* 733 */               this.eventHandler.post(new RunnableC08655(e));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$6 */
/* 743 */       class RunnableC08666 implements Runnable {
                    final /* synthetic */ long val$mediaEndTimeUs;
                    final /* synthetic */ long val$mediaStartTimeUs;
                    final /* synthetic */ long val$totalBytes;

/* 744 */           RunnableC08666(long j, long j2, long j3) {
                        this.val$mediaStartTimeUs = j;
                        this.val$mediaEndTimeUs = j2;
                        this.val$totalBytes = j3;
                    }

                    @Override // java.lang.Runnable
/* 747 */           public void run() {
/* 748 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onUpstreamDiscarded(ChunkSampleSource.access$000(ChunkSampleSource.this), ChunkSampleSource.this.usToMs(this.val$mediaStartTimeUs), ChunkSampleSource.this.usToMs(this.val$mediaEndTimeUs), this.val$totalBytes);
                    }
                }

/* 743 */       private void notifyUpstreamDiscarded(long mediaStartTimeUs, long mediaEndTimeUs, long totalBytes) {
/* 744 */           if (this.eventHandler != null && this.eventListener != null) {
/* 745 */               this.eventHandler.post(new RunnableC08666(mediaStartTimeUs, mediaEndTimeUs, totalBytes));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$7 */
/* 756 */       class RunnableC08677 implements Runnable {
                    final /* synthetic */ String val$formatId;
                    final /* synthetic */ long val$mediaTimeUs;
                    final /* synthetic */ int val$trigger;

/* 757 */           RunnableC08677(String str, int i, long j) {
                        this.val$formatId = str;
                        this.val$trigger = i;
                        this.val$mediaTimeUs = j;
                    }

                    @Override // java.lang.Runnable
/* 760 */           public void run() {
/* 761 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onDownstreamFormatChanged(ChunkSampleSource.access$000(ChunkSampleSource.this), this.val$formatId, this.val$trigger, ChunkSampleSource.this.usToMs(this.val$mediaTimeUs));
                    }
                }

/* 756 */       private void notifyDownstreamFormatChanged(String formatId, int trigger, long mediaTimeUs) {
/* 757 */           if (this.eventHandler != null && this.eventListener != null) {
/* 758 */               this.eventHandler.post(new RunnableC08677(formatId, trigger, mediaTimeUs));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.chunk.ChunkSampleSource$8 */
/* 769 */       class RunnableC08688 implements Runnable {
                    final /* synthetic */ long val$bytesDiscarded;
                    final /* synthetic */ long val$mediaEndTimeUs;
                    final /* synthetic */ long val$mediaStartTimeUs;

/* 770 */           RunnableC08688(long j, long j2, long j3) {
                        this.val$mediaStartTimeUs = j;
                        this.val$mediaEndTimeUs = j2;
                        this.val$bytesDiscarded = j3;
                    }

                    @Override // java.lang.Runnable
/* 773 */           public void run() {
/* 774 */               ChunkSampleSource.access$100(ChunkSampleSource.this).onDownstreamDiscarded(ChunkSampleSource.access$000(ChunkSampleSource.this), ChunkSampleSource.this.usToMs(this.val$mediaStartTimeUs), ChunkSampleSource.this.usToMs(this.val$mediaEndTimeUs), this.val$bytesDiscarded);
                    }
                }

/* 769 */       private void notifyDownstreamDiscarded(long mediaStartTimeUs, long mediaEndTimeUs, long bytesDiscarded) {
/* 770 */           if (this.eventHandler != null && this.eventListener != null) {
/* 771 */               this.eventHandler.post(new RunnableC08688(mediaStartTimeUs, mediaEndTimeUs, bytesDiscarded));
                    }
                }
            }
