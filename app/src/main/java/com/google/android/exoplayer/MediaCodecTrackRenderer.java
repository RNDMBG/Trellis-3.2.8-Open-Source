            package com.google.android.exoplayer;

            import android.annotation.TargetApi;
            import android.media.MediaCodec;
            import android.media.MediaCrypto;
            import android.os.Handler;
            import android.os.SystemClock;
            import android.view.Surface;
            import com.google.android.exoplayer.drm.DrmSessionManager;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Util;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.util.HashSet;
            import java.util.Map;
            import java.util.UUID;
            
            
            @TargetApi(16)
    public abstract class MediaCodecTrackRenderer extends TrackRenderer {
                private static final long MAX_CODEC_HOTSWAP_TIME_MS = 1000;
                private static final int RECONFIGURATION_STATE_NONE = 0;
                private static final int RECONFIGURATION_STATE_QUEUE_PENDING = 2;
                private static final int RECONFIGURATION_STATE_WRITE_PENDING = 1;
                protected static final int SOURCE_STATE_NOT_READY = 0;
                protected static final int SOURCE_STATE_READY = 1;
                protected static final int SOURCE_STATE_READY_READ_MAY_FAIL = 2;
                private MediaCodec codec;
                public final CodecCounters codecCounters;
                private long codecHotswapTimeMs;
                private boolean codecIsAdaptive;
                private int codecReconfigurationState;
                private boolean codecReconfigured;
                private long currentPositionUs;
                private final HashSet<Long> decodeOnlyPresentationTimestamps;
                private Map<UUID, byte[]> drmInitData;
                private final DrmSessionManager drmSessionManager;
                protected final Handler eventHandler;
                private final EventListener eventListener;
                private MediaFormat format;
                private final MediaFormatHolder formatHolder;
                private ByteBuffer[] inputBuffers;
                private int inputIndex;
                private boolean inputStreamEnded;
                private boolean openedDrmSession;
                private final MediaCodec.BufferInfo outputBufferInfo;
                private ByteBuffer[] outputBuffers;
                private int outputIndex;
                private boolean outputStreamEnded;
                private final boolean playClearSamplesWithoutKeys;
                private final SampleHolder sampleHolder;
                private final SampleSource source;
                private int sourceState;
                private int trackIndex;
                private boolean waitingForFirstSyncFrame;
                private boolean waitingForKeys;

                public interface EventListener {
                    void onCryptoError(MediaCodec.CryptoException cryptoException);

                    void onDecoderInitializationError(DecoderInitializationException decoderInitializationException);
                }

                protected abstract boolean processOutputBuffer(long j, MediaCodec mediaCodec, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo, int i, boolean z) throws ExoPlaybackException;

        static  EventListener access$000(MediaCodecTrackRenderer x0) {
            return x0.eventListener;
                }

        public static class DecoderInitializationException extends Exception {
                    public final String decoderName;

            public DecoderInitializationException(String decoderName, MediaFormat mediaFormat, Exception cause) {
                super("Decoder init failed: " + decoderName + ", " + mediaFormat, cause);
                this.decoderName = decoderName;
                    }
                }

       public MediaCodecTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, Handler eventHandler, EventListener eventListener) {
           Assertions.checkState(Util.SDK_INT >= 16);
           this.source = source;
           this.drmSessionManager = drmSessionManager;
           this.playClearSamplesWithoutKeys = playClearSamplesWithoutKeys;
           this.eventHandler = eventHandler;
           this.eventListener = eventListener;
           this.codecCounters = new CodecCounters();
           this.sampleHolder = new SampleHolder(false);
           this.formatHolder = new MediaFormatHolder();
           this.decodeOnlyPresentationTimestamps = new HashSet<>();
           this.outputBufferInfo = new MediaCodec.BufferInfo();
                }

                @Override 
       protected int doPrepare() throws ExoPlaybackException {
                    try {
               boolean sourcePrepared = this.source.prepare();
               if (!sourcePrepared) {
                   return 0;
                        }
                        for (int i = 0; i < this.source.getTrackCount(); i++) {
                   if (handlesMimeType(this.source.getTrackInfo(i).mimeType)) {
                       this.trackIndex = i;
                       return 1;
                            }
                        }
               return -1;
                    } catch (IOException e) {
               throw new ExoPlaybackException(e);
                    }
                }

       protected boolean handlesMimeType(String mimeType) {
           return true;
                }

                @Override 
       protected void onEnabled(long timeUs, boolean joining) {
           this.source.enable(this.trackIndex, timeUs);
           this.sourceState = 0;
           this.inputStreamEnded = false;
           this.outputStreamEnded = false;
           this.waitingForKeys = false;
           this.currentPositionUs = timeUs;
                }

       protected void configureCodec(MediaCodec codec, android.media.MediaFormat x, MediaCrypto crypto) {
           codec.configure(x, (Surface) null, crypto, 0);
                }

       protected final void maybeInitCodec() throws ExoPlaybackException {
           if (shouldInitCodec()) {
               String mimeType = this.format.mimeType;
               MediaCrypto mediaCrypto = null;
               boolean requiresSecureDecoder = false;
               if (this.drmInitData != null) {
                   if (this.drmSessionManager == null) {
                       throw new ExoPlaybackException("Media requires a DrmSessionManager");
                            }
                   if (!this.openedDrmSession) {
                       this.drmSessionManager.open(this.drmInitData, mimeType);
                       this.openedDrmSession = true;
                            }
                   int drmSessionState = this.drmSessionManager.getState();
                   if (drmSessionState == 0) {
                       throw new ExoPlaybackException(this.drmSessionManager.getError());
                            }
                   if (drmSessionState == 3 || drmSessionState == 4) {
                       mediaCrypto = this.drmSessionManager.getMediaCrypto();
                       requiresSecureDecoder = this.drmSessionManager.requiresSecureDecoderComponent(mimeType);
                            } else {
                       return;
                            }
                        }
                DecoderInfo selectedDecoderInfo = MediaCodecUtil.getDecoderInfo(mimeType);
                if (selectedDecoderInfo == null) {
                    throw new ExoPlaybackException("No decoder available for " + mimeType);
                         }
                String selectedDecoderName = selectedDecoderInfo.name;
               if (requiresSecureDecoder) {
                   selectedDecoderName = getSecureDecoderName(selectedDecoderName);
                        }
               this.codecIsAdaptive = selectedDecoderInfo.adaptive;
                        try {
                   this.codec = MediaCodec.createByCodecName(selectedDecoderName);
                   configureCodec(this.codec, this.format.getFrameworkMediaFormatV16(), mediaCrypto);
                   this.codec.start();
                   this.inputBuffers = this.codec.getInputBuffers();
                   this.outputBuffers = this.codec.getOutputBuffers();
                   this.codecHotswapTimeMs = getState() == 3 ? SystemClock.elapsedRealtime() : -1L;
                   this.inputIndex = -1;
                   this.outputIndex = -1;
                   this.waitingForFirstSyncFrame = true;
                            this.codecCounters.codecInitCount++;
                        } catch (Exception e) {
                   DecoderInitializationException exception = new DecoderInitializationException(selectedDecoderName, this.format, e);
                   notifyDecoderInitializationError(exception);
                   throw new ExoPlaybackException(exception);
                        }
                    }
                }

       protected boolean shouldInitCodec() {
                    return this.codec == null && this.format != null;
                }

       protected final boolean codecInitialized() {
                    return this.codec != null;
                }

       protected final boolean haveFormat() {
                    return this.format != null;
                }

                @Override 
       protected void onDisabled() {
           this.format = null;
           this.drmInitData = null;
                    try {
               releaseCodec();
                        try {
                   if (this.openedDrmSession) {
                       this.drmSessionManager.close();
                       this.openedDrmSession = false;
                            }
                   this.source.disable(this.trackIndex);
                        } catch (Throwable th) {
                            this.source.disable(this.trackIndex);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        try {
                   if (this.openedDrmSession) {
                       this.drmSessionManager.close();
                       this.openedDrmSession = false;
                            }
                   this.source.disable(this.trackIndex);
                            throw th2;
                        } catch (Throwable th3) {
                            this.source.disable(this.trackIndex);
                            throw th3;
                        }
                    }
                }

       protected void releaseCodec() {
           if (this.codec != null) {
               this.codecHotswapTimeMs = -1L;
               this.inputIndex = -1;
               this.outputIndex = -1;
               this.decodeOnlyPresentationTimestamps.clear();
               this.inputBuffers = null;
               this.outputBuffers = null;
               this.codecReconfigured = false;
               this.codecIsAdaptive = false;
               this.codecReconfigurationState = 0;
                        this.codecCounters.codecReleaseCount++;
                        try {
                   this.codec.stop();
                            try {
                       this.codec.release();
                       this.codec = null;
                            } catch (Throwable th) {
                                this.codec = null;
                                throw th;
                            }
                        } catch (Throwable th2) {
                            try {
                       this.codec.release();
                       this.codec = null;
                                throw th2;
                            } catch (Throwable th3) {
                                this.codec = null;
                                throw th3;
                            }
                        }
                    }
                }

                @Override 
       protected void onReleased() {
           this.source.release();
                }

                @Override 
       protected long getCurrentPositionUs() {
           return this.currentPositionUs;
                }

                @Override 
       protected long getDurationUs() {
           return this.source.getTrackInfo(this.trackIndex).durationUs;
                }

                @Override 
       protected long getBufferedPositionUs() {
           long sourceBufferedPosition = this.source.getBufferedPositionUs();
                    return (sourceBufferedPosition == -1 || sourceBufferedPosition == -3) ? sourceBufferedPosition : Math.max(sourceBufferedPosition, getCurrentPositionUs());
                }

                @Override 
       protected void seekTo(long timeUs) throws ExoPlaybackException {
           this.currentPositionUs = timeUs;
           this.source.seekToUs(timeUs);
           this.sourceState = 0;
           this.inputStreamEnded = false;
           this.outputStreamEnded = false;
           this.waitingForKeys = false;
                }

                @Override 
       protected void onStarted() {
                }

                @Override 
       protected void onStopped() {
                }

                @Override 
       protected void doSomeWork(long timeUs) throws ExoPlaybackException {
                    int i = 1;
                    try {
               if (!this.source.continueBuffering(timeUs)) {
                            i = 0;
                        } else if (this.sourceState != 0) {
                   i = this.sourceState;
                        }
                        this.sourceState = i;
               checkForDiscontinuity();
               if (this.format == null) {
                   readFormat();
                        } else if (this.codec == null && !shouldInitCodec() && getState() == 3) {
                   discardSamples(timeUs);
                        } else {
                   if (this.codec == null && shouldInitCodec()) {
                       maybeInitCodec();
                            }
                   if (this.codec != null) {
                       while (drainOutputBuffer(timeUs)) {
                                }
                       if (feedInputBuffer(true)) {
                           while (feedInputBuffer(false)) {
                                    }
                                }
                            }
                        }
               this.codecCounters.ensureUpdated();
                    } catch (IOException e) {
               throw new ExoPlaybackException(e);
                    }
                }

       private void readFormat() throws ExoPlaybackException, IOException {
           int result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, false);
           if (result == -4) {
               onInputFormatChanged(this.formatHolder);
                    }
                }

       private void discardSamples(long timeUs) throws ExoPlaybackException, IOException {
           this.sampleHolder.data = null;
           int result = -3;
           while (result == -3 && this.currentPositionUs <= timeUs) {
               result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, false);
               if (result == -3) {
                   if (!this.sampleHolder.decodeOnly) {
                       this.currentPositionUs = this.sampleHolder.timeUs;
                            }
                        } else if (result == -4) {
                   onInputFormatChanged(this.formatHolder);
                        }
                    }
                }

       private void checkForDiscontinuity() throws ExoPlaybackException, IOException {
           if (this.codec != null) {
               int result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, true);
               if (result == -5) {
                   flushCodec();
                        }
                    }
                }

       private void flushCodec() throws ExoPlaybackException {
           this.codecHotswapTimeMs = -1L;
           this.inputIndex = -1;
           this.outputIndex = -1;
           this.waitingForFirstSyncFrame = true;
           this.decodeOnlyPresentationTimestamps.clear();
           if (Util.SDK_INT >= 18) {
               this.codec.flush();
                    } else {
               releaseCodec();
               maybeInitCodec();
                    }
           if (this.codecReconfigured && this.format != null) {
               this.codecReconfigurationState = 1;
                    }
                }

       private boolean feedInputBuffer(boolean firstFeed) throws ExoPlaybackException, IOException {
                    int result;
           if (this.inputStreamEnded) {
               return false;
                    }
           if (this.inputIndex < 0) {
               this.inputIndex = this.codec.dequeueInputBuffer(0L);
               if (this.inputIndex < 0) {
                   return false;
                        }
               this.sampleHolder.data = this.inputBuffers[this.inputIndex];
               this.sampleHolder.data.clear();
                    }
           if (this.waitingForKeys) {
               result = -3;
                    } else {
               if (this.codecReconfigurationState == 1) {
                            for (int i = 0; i < this.format.initializationData.size(); i++) {
                       byte[] data = this.format.initializationData.get(i);
                       this.sampleHolder.data.put(data);
                            }
                   this.codecReconfigurationState = 2;
                        }
               result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, false);
               if (firstFeed && this.sourceState == 1 && result == -2) {
                   this.sourceState = 2;
                        }
                    }
           if (result == -2) {
               return false;
                    }
           if (result == -5) {
               flushCodec();
               return true;
                    }
           if (result == -4) {
               if (this.codecReconfigurationState == 2) {
                   this.sampleHolder.data.clear();
                   this.codecReconfigurationState = 1;
                        }
               onInputFormatChanged(this.formatHolder);
               return true;
                    }
           if (result == -1) {
               if (this.codecReconfigurationState == 2) {
                   this.sampleHolder.data.clear();
                   this.codecReconfigurationState = 1;
                        }
               this.inputStreamEnded = true;
                        try {
                   this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0L, 4);
                   this.inputIndex = -1;
                   return false;
                        } catch (MediaCodec.CryptoException e) {
                   notifyCryptoError(e);
                   throw new ExoPlaybackException(e);
                        }
                    }
           if (this.waitingForFirstSyncFrame) {
               if ((this.sampleHolder.flags & 1) == 0) {
                   this.sampleHolder.data.clear();
                   if (this.codecReconfigurationState == 2) {
                       this.codecReconfigurationState = 1;
                            }
                   return true;
                        }
               this.waitingForFirstSyncFrame = false;
                    }
           boolean sampleEncrypted = (this.sampleHolder.flags & 2) != 0;
           this.waitingForKeys = shouldWaitForKeys(sampleEncrypted);
           if (this.waitingForKeys) {
               return false;
                    }
                    try {
               int bufferSize = this.sampleHolder.data.position();
               int adaptiveReconfigurationBytes = bufferSize - this.sampleHolder.size;
               long presentationTimeUs = this.sampleHolder.timeUs;
               if (this.sampleHolder.decodeOnly) {
                   this.decodeOnlyPresentationTimestamps.add(Long.valueOf(presentationTimeUs));
                        }
               if (sampleEncrypted) {
                   MediaCodec.CryptoInfo cryptoInfo = getFrameworkCryptoInfo(this.sampleHolder, adaptiveReconfigurationBytes);
                   this.codec.queueSecureInputBuffer(this.inputIndex, 0, cryptoInfo, presentationTimeUs, 0);
                        } else {
                   this.codec.queueInputBuffer(this.inputIndex, 0, bufferSize, presentationTimeUs, 0);
                        }
               this.inputIndex = -1;
               this.codecReconfigurationState = 0;
               return true;
                    } catch (MediaCodec.CryptoException e2) {
               notifyCryptoError(e2);
               throw new ExoPlaybackException(e2);
                    }
                }

       private static MediaCodec.CryptoInfo getFrameworkCryptoInfo(SampleHolder sampleHolder, int adaptiveReconfigurationBytes) {
           MediaCodec.CryptoInfo cryptoInfo = sampleHolder.cryptoInfo.getFrameworkCryptoInfoV16();
           if (adaptiveReconfigurationBytes != 0) {
               if (cryptoInfo.numBytesOfClearData == null) {
                   cryptoInfo.numBytesOfClearData = new int[1];
                        }
               int[] iArr = cryptoInfo.numBytesOfClearData;
                        iArr[0] = iArr[0] + adaptiveReconfigurationBytes;
                    }
           return cryptoInfo;
                }

       private boolean shouldWaitForKeys(boolean sampleEncrypted) throws ExoPlaybackException {
           if (!this.openedDrmSession) {
                        return false;
                    }
           int drmManagerState = this.drmSessionManager.getState();
           if (drmManagerState == 0) {
               throw new ExoPlaybackException(this.drmSessionManager.getError());
                    }
           if (drmManagerState != 4) {
                        return sampleEncrypted || !this.playClearSamplesWithoutKeys;
                    }
                    return false;
                }

       private void onInputFormatChanged(MediaFormatHolder formatHolder) throws ExoPlaybackException {
           MediaFormat oldFormat = this.format;
           this.format = formatHolder.format;
           this.drmInitData = formatHolder.drmInitData;
           if (this.codec != null && canReconfigureCodec(this.codec, this.codecIsAdaptive, oldFormat, this.format)) {
               this.codecReconfigured = true;
               this.codecReconfigurationState = 1;
                    } else {
               releaseCodec();
               maybeInitCodec();
                    }
                }

       protected void onOutputFormatChanged(android.media.MediaFormat format) {
                }

       protected boolean canReconfigureCodec(MediaCodec codec, boolean codecIsAdaptive, MediaFormat oldFormat, MediaFormat newFormat) {
           return false;
                }

                @Override 
       protected boolean isEnded() {
           return this.outputStreamEnded;
                }

                @Override 
       protected boolean isReady() {
                    return !(this.format == null || this.waitingForKeys || this.sourceState == 0) || this.outputIndex >= 0 || isWithinHotswapPeriod();
                }

       protected final int getSourceState() {
           return this.sourceState;
                }

       private boolean isWithinHotswapPeriod() {
                    return SystemClock.elapsedRealtime() < this.codecHotswapTimeMs + 1000;
                }

       private boolean drainOutputBuffer(long timeUs) throws ExoPlaybackException {
           if (this.outputStreamEnded) {
                        return false;
                    }
           if (this.outputIndex < 0) {
               this.outputIndex = this.codec.dequeueOutputBuffer(this.outputBufferInfo, 0L);
                    }
           if (this.outputIndex == -2) {
               onOutputFormatChanged(this.codec.getOutputFormat());
                        this.codecCounters.outputFormatChangedCount++;
                        return true;
                    }
           if (this.outputIndex == -3) {
               this.outputBuffers = this.codec.getOutputBuffers();
                        this.codecCounters.outputBuffersChangedCount++;
                        return true;
                    }
           if (this.outputIndex < 0) {
                        return false;
                    }
           if ((this.outputBufferInfo.flags & 4) != 0) {
               this.outputStreamEnded = true;
                        return false;
                    }
           boolean decodeOnly = this.decodeOnlyPresentationTimestamps.contains(Long.valueOf(this.outputBufferInfo.presentationTimeUs));
           if (!processOutputBuffer(timeUs, this.codec, this.outputBuffers[this.outputIndex], this.outputBufferInfo, this.outputIndex, decodeOnly)) {
                        return false;
                    }
           if (decodeOnly) {
               this.decodeOnlyPresentationTimestamps.remove(Long.valueOf(this.outputBufferInfo.presentationTimeUs));
                    } else {
               this.currentPositionUs = this.outputBufferInfo.presentationTimeUs;
                    }
           this.outputIndex = -1;
                    return true;
                }

       private static String getSecureDecoderName(String rawDecoderName) {
           return rawDecoderName + ".secure";
                }

                
       class RunnableC08561 implements Runnable {
                    final  DecoderInitializationException val$e;

           RunnableC08561(DecoderInitializationException decoderInitializationException) {
                        this.val$e = decoderInitializationException;
                    }

                    @Override 
           public void run() {
               MediaCodecTrackRenderer.access$000(MediaCodecTrackRenderer.this).onDecoderInitializationError(this.val$e);
                    }
                }

       private void notifyDecoderInitializationError(DecoderInitializationException e) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08561(e));
                    }
                }

                
       class RunnableC08572 implements Runnable {
                    final  MediaCodec.CryptoException val$e;

           RunnableC08572(MediaCodec.CryptoException cryptoException) {
                        this.val$e = cryptoException;
                    }

                    @Override 
           public void run() {
               MediaCodecTrackRenderer.access$000(MediaCodecTrackRenderer.this).onCryptoError(this.val$e);
                    }
                }

       private void notifyCryptoError(MediaCodec.CryptoException e) {
           if (this.eventHandler != null && this.eventListener != null) {
               this.eventHandler.post(new RunnableC08572(e));
                    }
                }
            }
