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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
/* 38 */    public abstract class MediaCodecTrackRenderer extends TrackRenderer {
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

/* 39 */        static /* synthetic */ EventListener access$000(MediaCodecTrackRenderer x0) {
/* 40 */            return x0.eventListener;
                }

/* 73 */        public static class DecoderInitializationException extends Exception {
                    public final String decoderName;

/* 74 */            public DecoderInitializationException(String decoderName, MediaFormat mediaFormat, Exception cause) {
/* 75 */                super("Decoder init failed: " + decoderName + ", " + mediaFormat, cause);
/* 76 */                this.decoderName = decoderName;
                    }
                }

/* 167 */       public MediaCodecTrackRenderer(SampleSource source, DrmSessionManager drmSessionManager, boolean playClearSamplesWithoutKeys, Handler eventHandler, EventListener eventListener) {
/* 169 */           Assertions.checkState(Util.SDK_INT >= 16);
/* 170 */           this.source = source;
/* 171 */           this.drmSessionManager = drmSessionManager;
/* 172 */           this.playClearSamplesWithoutKeys = playClearSamplesWithoutKeys;
/* 173 */           this.eventHandler = eventHandler;
/* 174 */           this.eventListener = eventListener;
/* 175 */           this.codecCounters = new CodecCounters();
/* 176 */           this.sampleHolder = new SampleHolder(false);
/* 177 */           this.formatHolder = new MediaFormatHolder();
/* 178 */           this.decodeOnlyPresentationTimestamps = new HashSet<>();
/* 179 */           this.outputBufferInfo = new MediaCodec.BufferInfo();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 184 */       protected int doPrepare() throws ExoPlaybackException {
                    try {
/* 185 */               boolean sourcePrepared = this.source.prepare();
/* 186 */               if (!sourcePrepared) {
/* 187 */                   return 0;
                        }
                        for (int i = 0; i < this.source.getTrackCount(); i++) {
/* 197 */                   if (handlesMimeType(this.source.getTrackInfo(i).mimeType)) {
/* 198 */                       this.trackIndex = i;
/* 199 */                       return 1;
                            }
                        }
/* 203 */               return -1;
                    } catch (IOException e) {
/* 190 */               throw new ExoPlaybackException(e);
                    }
                }

/* 212 */       protected boolean handlesMimeType(String mimeType) {
/* 213 */           return true;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 219 */       protected void onEnabled(long timeUs, boolean joining) {
/* 220 */           this.source.enable(this.trackIndex, timeUs);
/* 221 */           this.sourceState = 0;
/* 222 */           this.inputStreamEnded = false;
/* 223 */           this.outputStreamEnded = false;
/* 224 */           this.waitingForKeys = false;
/* 225 */           this.currentPositionUs = timeUs;
                }

/* 233 */       protected void configureCodec(MediaCodec codec, android.media.MediaFormat x, MediaCrypto crypto) {
/* 234 */           codec.configure(x, (Surface) null, crypto, 0);
                }

/* 237 */       protected final void maybeInitCodec() throws ExoPlaybackException {
/* 238 */           if (shouldInitCodec()) {
/* 242 */               String mimeType = this.format.mimeType;
/* 243 */               MediaCrypto mediaCrypto = null;
/* 244 */               boolean requiresSecureDecoder = false;
/* 245 */               if (this.drmInitData != null) {
/* 246 */                   if (this.drmSessionManager == null) {
/* 247 */                       throw new ExoPlaybackException("Media requires a DrmSessionManager");
                            }
/* 249 */                   if (!this.openedDrmSession) {
/* 250 */                       this.drmSessionManager.open(this.drmInitData, mimeType);
/* 251 */                       this.openedDrmSession = true;
                            }
/* 253 */                   int drmSessionState = this.drmSessionManager.getState();
/* 254 */                   if (drmSessionState == 0) {
/* 255 */                       throw new ExoPlaybackException(this.drmSessionManager.getError());
                            }
/* 256 */                   if (drmSessionState == 3 || drmSessionState == 4) {
/* 258 */                       mediaCrypto = this.drmSessionManager.getMediaCrypto();
/* 259 */                       requiresSecureDecoder = this.drmSessionManager.requiresSecureDecoderComponent(mimeType);
                            } else {
/* 290 */                       return;
                            }
                        }
/* 266 */               DecoderInfo selectedDecoderInfo = MediaCodecUtil.getDecoderInfo(mimeType);
/* 267 */               String selectedDecoderName = selectedDecoderInfo.name;
/* 268 */               if (requiresSecureDecoder) {
/* 269 */                   selectedDecoderName = getSecureDecoderName(selectedDecoderName);
                        }
/* 271 */               this.codecIsAdaptive = selectedDecoderInfo.adaptive;
                        try {
/* 273 */                   this.codec = MediaCodec.createByCodecName(selectedDecoderName);
/* 274 */                   configureCodec(this.codec, this.format.getFrameworkMediaFormatV16(), mediaCrypto);
/* 275 */                   this.codec.start();
/* 276 */                   this.inputBuffers = this.codec.getInputBuffers();
/* 277 */                   this.outputBuffers = this.codec.getOutputBuffers();
/* 284 */                   this.codecHotswapTimeMs = getState() == 3 ? SystemClock.elapsedRealtime() : -1L;
/* 286 */                   this.inputIndex = -1;
/* 287 */                   this.outputIndex = -1;
/* 288 */                   this.waitingForFirstSyncFrame = true;
                            this.codecCounters.codecInitCount++;
                        } catch (Exception e) {
/* 279 */                   DecoderInitializationException exception = new DecoderInitializationException(selectedDecoderName, this.format, e);
/* 281 */                   notifyDecoderInitializationError(exception);
/* 282 */                   throw new ExoPlaybackException(exception);
                        }
                    }
                }

/* 292 */       protected boolean shouldInitCodec() {
                    return this.codec == null && this.format != null;
                }

/* 296 */       protected final boolean codecInitialized() {
                    return this.codec != null;
                }

/* 300 */       protected final boolean haveFormat() {
                    return this.format != null;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 305 */       protected void onDisabled() {
/* 306 */           this.format = null;
/* 307 */           this.drmInitData = null;
                    try {
/* 309 */               releaseCodec();
                        try {
/* 312 */                   if (this.openedDrmSession) {
/* 313 */                       this.drmSessionManager.close();
/* 314 */                       this.openedDrmSession = false;
                            }
/* 317 */                   this.source.disable(this.trackIndex);
                        } catch (Throwable th) {
                            this.source.disable(this.trackIndex);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        try {
/* 312 */                   if (this.openedDrmSession) {
/* 313 */                       this.drmSessionManager.close();
/* 314 */                       this.openedDrmSession = false;
                            }
/* 317 */                   this.source.disable(this.trackIndex);
                            throw th2;
                        } catch (Throwable th3) {
                            this.source.disable(this.trackIndex);
                            throw th3;
                        }
                    }
                }

/* 322 */       protected void releaseCodec() {
/* 323 */           if (this.codec != null) {
/* 324 */               this.codecHotswapTimeMs = -1L;
/* 325 */               this.inputIndex = -1;
/* 326 */               this.outputIndex = -1;
/* 327 */               this.decodeOnlyPresentationTimestamps.clear();
/* 328 */               this.inputBuffers = null;
/* 329 */               this.outputBuffers = null;
/* 330 */               this.codecReconfigured = false;
/* 331 */               this.codecIsAdaptive = false;
/* 332 */               this.codecReconfigurationState = 0;
                        this.codecCounters.codecReleaseCount++;
                        try {
/* 335 */                   this.codec.stop();
                            try {
/* 338 */                       this.codec.release();
/* 340 */                       this.codec = null;
                            } catch (Throwable th) {
                                this.codec = null;
                                throw th;
                            }
                        } catch (Throwable th2) {
                            try {
/* 338 */                       this.codec.release();
/* 340 */                       this.codec = null;
                                throw th2;
                            } catch (Throwable th3) {
                                this.codec = null;
                                throw th3;
                            }
                        }
                    }
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 347 */       protected void onReleased() {
/* 348 */           this.source.release();
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 352 */       protected long getCurrentPositionUs() {
/* 353 */           return this.currentPositionUs;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 357 */       protected long getDurationUs() {
/* 358 */           return this.source.getTrackInfo(this.trackIndex).durationUs;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 362 */       protected long getBufferedPositionUs() {
/* 363 */           long sourceBufferedPosition = this.source.getBufferedPositionUs();
                    return (sourceBufferedPosition == -1 || sourceBufferedPosition == -3) ? sourceBufferedPosition : Math.max(sourceBufferedPosition, getCurrentPositionUs());
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 369 */       protected void seekTo(long timeUs) throws ExoPlaybackException {
/* 370 */           this.currentPositionUs = timeUs;
/* 371 */           this.source.seekToUs(timeUs);
/* 372 */           this.sourceState = 0;
/* 373 */           this.inputStreamEnded = false;
/* 374 */           this.outputStreamEnded = false;
/* 375 */           this.waitingForKeys = false;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 380 */       protected void onStarted() {
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 385 */       protected void onStopped() {
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 390 */       protected void doSomeWork(long timeUs) throws ExoPlaybackException {
                    int i = 1;
                    try {
/* 391 */               if (!this.source.continueBuffering(timeUs)) {
                            i = 0;
                        } else if (this.sourceState != 0) {
/* 391 */                   i = this.sourceState;
                        }
                        this.sourceState = i;
/* 394 */               checkForDiscontinuity();
/* 395 */               if (this.format == null) {
/* 396 */                   readFormat();
                        } else if (this.codec == null && !shouldInitCodec() && getState() == 3) {
/* 398 */                   discardSamples(timeUs);
                        } else {
/* 400 */                   if (this.codec == null && shouldInitCodec()) {
/* 401 */                       maybeInitCodec();
                            }
/* 403 */                   if (this.codec != null) {
/* 404 */                       while (drainOutputBuffer(timeUs)) {
                                }
/* 405 */                       if (feedInputBuffer(true)) {
/* 406 */                           while (feedInputBuffer(false)) {
                                    }
                                }
                            }
                        }
/* 410 */               this.codecCounters.ensureUpdated();
                    } catch (IOException e) {
/* 412 */               throw new ExoPlaybackException(e);
                    }
                }

/* 416 */       private void readFormat() throws ExoPlaybackException, IOException {
/* 417 */           int result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, false);
/* 418 */           if (result == -4) {
/* 419 */               onInputFormatChanged(this.formatHolder);
                    }
                }

/* 423 */       private void discardSamples(long timeUs) throws ExoPlaybackException, IOException {
/* 424 */           this.sampleHolder.data = null;
/* 425 */           int result = -3;
/* 426 */           while (result == -3 && this.currentPositionUs <= timeUs) {
/* 427 */               result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, false);
/* 428 */               if (result == -3) {
/* 429 */                   if (!this.sampleHolder.decodeOnly) {
/* 430 */                       this.currentPositionUs = this.sampleHolder.timeUs;
                            }
                        } else if (result == -4) {
/* 433 */                   onInputFormatChanged(this.formatHolder);
                        }
                    }
                }

/* 438 */       private void checkForDiscontinuity() throws ExoPlaybackException, IOException {
/* 439 */           if (this.codec != null) {
/* 442 */               int result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, true);
/* 443 */               if (result == -5) {
/* 444 */                   flushCodec();
                        }
                    }
                }

/* 448 */       private void flushCodec() throws ExoPlaybackException {
/* 449 */           this.codecHotswapTimeMs = -1L;
/* 450 */           this.inputIndex = -1;
/* 451 */           this.outputIndex = -1;
/* 452 */           this.waitingForFirstSyncFrame = true;
/* 453 */           this.decodeOnlyPresentationTimestamps.clear();
/* 456 */           if (Util.SDK_INT >= 18) {
/* 457 */               this.codec.flush();
                    } else {
/* 459 */               releaseCodec();
/* 460 */               maybeInitCodec();
                    }
/* 462 */           if (this.codecReconfigured && this.format != null) {
/* 465 */               this.codecReconfigurationState = 1;
                    }
                }

/* 476 */       private boolean feedInputBuffer(boolean firstFeed) throws ExoPlaybackException, IOException {
                    int result;
/* 477 */           if (this.inputStreamEnded) {
/* 478 */               return false;
                    }
/* 480 */           if (this.inputIndex < 0) {
/* 481 */               this.inputIndex = this.codec.dequeueInputBuffer(0L);
/* 482 */               if (this.inputIndex < 0) {
/* 483 */                   return false;
                        }
/* 485 */               this.sampleHolder.data = this.inputBuffers[this.inputIndex];
/* 486 */               this.sampleHolder.data.clear();
                    }
/* 490 */           if (this.waitingForKeys) {
/* 492 */               result = -3;
                    } else {
/* 496 */               if (this.codecReconfigurationState == 1) {
                            for (int i = 0; i < this.format.initializationData.size(); i++) {
/* 498 */                       byte[] data = this.format.initializationData.get(i);
/* 499 */                       this.sampleHolder.data.put(data);
                            }
/* 501 */                   this.codecReconfigurationState = 2;
                        }
/* 503 */               result = this.source.readData(this.trackIndex, this.currentPositionUs, this.formatHolder, this.sampleHolder, false);
/* 504 */               if (firstFeed && this.sourceState == 1 && result == -2) {
/* 505 */                   this.sourceState = 2;
                        }
                    }
/* 509 */           if (result == -2) {
/* 510 */               return false;
                    }
/* 512 */           if (result == -5) {
/* 513 */               flushCodec();
/* 514 */               return true;
                    }
/* 516 */           if (result == -4) {
/* 517 */               if (this.codecReconfigurationState == 2) {
/* 520 */                   this.sampleHolder.data.clear();
/* 521 */                   this.codecReconfigurationState = 1;
                        }
/* 523 */               onInputFormatChanged(this.formatHolder);
/* 524 */               return true;
                    }
/* 526 */           if (result == -1) {
/* 527 */               if (this.codecReconfigurationState == 2) {
/* 531 */                   this.sampleHolder.data.clear();
/* 532 */                   this.codecReconfigurationState = 1;
                        }
/* 534 */               this.inputStreamEnded = true;
                        try {
/* 536 */                   this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0L, 4);
/* 537 */                   this.inputIndex = -1;
/* 542 */                   return false;
                        } catch (MediaCodec.CryptoException e) {
/* 539 */                   notifyCryptoError(e);
/* 540 */                   throw new ExoPlaybackException(e);
                        }
                    }
/* 544 */           if (this.waitingForFirstSyncFrame) {
/* 547 */               if ((this.sampleHolder.flags & 1) == 0) {
/* 548 */                   this.sampleHolder.data.clear();
/* 549 */                   if (this.codecReconfigurationState == 2) {
/* 552 */                       this.codecReconfigurationState = 1;
                            }
/* 554 */                   return true;
                        }
/* 556 */               this.waitingForFirstSyncFrame = false;
                    }
/* 558 */           boolean sampleEncrypted = (this.sampleHolder.flags & 2) != 0;
/* 559 */           this.waitingForKeys = shouldWaitForKeys(sampleEncrypted);
/* 560 */           if (this.waitingForKeys) {
/* 561 */               return false;
                    }
                    try {
/* 564 */               int bufferSize = this.sampleHolder.data.position();
/* 565 */               int adaptiveReconfigurationBytes = bufferSize - this.sampleHolder.size;
/* 566 */               long presentationTimeUs = this.sampleHolder.timeUs;
/* 567 */               if (this.sampleHolder.decodeOnly) {
/* 568 */                   this.decodeOnlyPresentationTimestamps.add(Long.valueOf(presentationTimeUs));
                        }
/* 570 */               if (sampleEncrypted) {
/* 571 */                   MediaCodec.CryptoInfo cryptoInfo = getFrameworkCryptoInfo(this.sampleHolder, adaptiveReconfigurationBytes);
/* 573 */                   this.codec.queueSecureInputBuffer(this.inputIndex, 0, cryptoInfo, presentationTimeUs, 0);
                        } else {
/* 575 */                   this.codec.queueInputBuffer(this.inputIndex, 0, bufferSize, presentationTimeUs, 0);
                        }
/* 577 */               this.inputIndex = -1;
/* 578 */               this.codecReconfigurationState = 0;
/* 583 */               return true;
                    } catch (MediaCodec.CryptoException e2) {
/* 580 */               notifyCryptoError(e2);
/* 581 */               throw new ExoPlaybackException(e2);
                    }
                }

/* 587 */       private static MediaCodec.CryptoInfo getFrameworkCryptoInfo(SampleHolder sampleHolder, int adaptiveReconfigurationBytes) {
/* 588 */           MediaCodec.CryptoInfo cryptoInfo = sampleHolder.cryptoInfo.getFrameworkCryptoInfoV16();
/* 589 */           if (adaptiveReconfigurationBytes != 0) {
/* 595 */               if (cryptoInfo.numBytesOfClearData == null) {
/* 596 */                   cryptoInfo.numBytesOfClearData = new int[1];
                        }
/* 598 */               int[] iArr = cryptoInfo.numBytesOfClearData;
                        iArr[0] = iArr[0] + adaptiveReconfigurationBytes;
                    }
/* 599 */           return cryptoInfo;
                }

/* 602 */       private boolean shouldWaitForKeys(boolean sampleEncrypted) throws ExoPlaybackException {
/* 603 */           if (!this.openedDrmSession) {
                        return false;
                    }
/* 606 */           int drmManagerState = this.drmSessionManager.getState();
/* 607 */           if (drmManagerState == 0) {
/* 608 */               throw new ExoPlaybackException(this.drmSessionManager.getError());
                    }
/* 610 */           if (drmManagerState != 4) {
                        return sampleEncrypted || !this.playClearSamplesWithoutKeys;
                    }
                    return false;
                }

/* 623 */       private void onInputFormatChanged(MediaFormatHolder formatHolder) throws ExoPlaybackException {
/* 624 */           MediaFormat oldFormat = this.format;
/* 625 */           this.format = formatHolder.format;
/* 626 */           this.drmInitData = formatHolder.drmInitData;
/* 627 */           if (this.codec != null && canReconfigureCodec(this.codec, this.codecIsAdaptive, oldFormat, this.format)) {
/* 628 */               this.codecReconfigured = true;
/* 629 */               this.codecReconfigurationState = 1;
                    } else {
/* 631 */               releaseCodec();
/* 632 */               maybeInitCodec();
                    }
                }

/* 644 */       protected void onOutputFormatChanged(android.media.MediaFormat format) {
                }

/* 663 */       protected boolean canReconfigureCodec(MediaCodec codec, boolean codecIsAdaptive, MediaFormat oldFormat, MediaFormat newFormat) {
/* 664 */           return false;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 668 */       protected boolean isEnded() {
/* 669 */           return this.outputStreamEnded;
                }

                @Override // com.google.android.exoplayer.TrackRenderer
/* 673 */       protected boolean isReady() {
                    return !(this.format == null || this.waitingForKeys || this.sourceState == 0) || this.outputIndex >= 0 || isWithinHotswapPeriod();
                }

/* 684 */       protected final int getSourceState() {
/* 685 */           return this.sourceState;
                }

/* 688 */       private boolean isWithinHotswapPeriod() {
                    return SystemClock.elapsedRealtime() < this.codecHotswapTimeMs + 1000;
                }

/* 696 */       private boolean drainOutputBuffer(long timeUs) throws ExoPlaybackException {
/* 697 */           if (this.outputStreamEnded) {
                        return false;
                    }
/* 701 */           if (this.outputIndex < 0) {
/* 702 */               this.outputIndex = this.codec.dequeueOutputBuffer(this.outputBufferInfo, 0L);
                    }
/* 705 */           if (this.outputIndex == -2) {
/* 706 */               onOutputFormatChanged(this.codec.getOutputFormat());
                        this.codecCounters.outputFormatChangedCount++;
                        return true;
                    }
/* 709 */           if (this.outputIndex == -3) {
/* 710 */               this.outputBuffers = this.codec.getOutputBuffers();
                        this.codecCounters.outputBuffersChangedCount++;
                        return true;
                    }
/* 713 */           if (this.outputIndex < 0) {
                        return false;
                    }
/* 717 */           if ((this.outputBufferInfo.flags & 4) != 0) {
/* 718 */               this.outputStreamEnded = true;
                        return false;
                    }
/* 722 */           boolean decodeOnly = this.decodeOnlyPresentationTimestamps.contains(Long.valueOf(this.outputBufferInfo.presentationTimeUs));
/* 724 */           if (!processOutputBuffer(timeUs, this.codec, this.outputBuffers[this.outputIndex], this.outputBufferInfo, this.outputIndex, decodeOnly)) {
                        return false;
                    }
/* 726 */           if (decodeOnly) {
/* 727 */               this.decodeOnlyPresentationTimestamps.remove(Long.valueOf(this.outputBufferInfo.presentationTimeUs));
                    } else {
/* 729 */               this.currentPositionUs = this.outputBufferInfo.presentationTimeUs;
                    }
/* 731 */           this.outputIndex = -1;
                    return true;
                }

/* 752 */       private static String getSecureDecoderName(String rawDecoderName) {
/* 753 */           return rawDecoderName + ".secure";
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.MediaCodecTrackRenderer$1 */
/* 756 */       class RunnableC08561 implements Runnable {
                    final /* synthetic */ DecoderInitializationException val$e;

/* 757 */           RunnableC08561(DecoderInitializationException decoderInitializationException) {
                        this.val$e = decoderInitializationException;
                    }

                    @Override // java.lang.Runnable
/* 760 */           public void run() {
/* 761 */               MediaCodecTrackRenderer.access$000(MediaCodecTrackRenderer.this).onDecoderInitializationError(this.val$e);
                    }
                }

/* 756 */       private void notifyDecoderInitializationError(DecoderInitializationException e) {
/* 757 */           if (this.eventHandler != null && this.eventListener != null) {
/* 758 */               this.eventHandler.post(new RunnableC08561(e));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.MediaCodecTrackRenderer$2 */
/* 767 */       class RunnableC08572 implements Runnable {
                    final /* synthetic */ MediaCodec.CryptoException val$e;

/* 768 */           RunnableC08572(MediaCodec.CryptoException cryptoException) {
                        this.val$e = cryptoException;
                    }

                    @Override // java.lang.Runnable
/* 771 */           public void run() {
/* 772 */               MediaCodecTrackRenderer.access$000(MediaCodecTrackRenderer.this).onCryptoError(this.val$e);
                    }
                }

/* 767 */       private void notifyCryptoError(MediaCodec.CryptoException e) {
/* 768 */           if (this.eventHandler != null && this.eventListener != null) {
/* 769 */               this.eventHandler.post(new RunnableC08572(e));
                    }
                }
            }
