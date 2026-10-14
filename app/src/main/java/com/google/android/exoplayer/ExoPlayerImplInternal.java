            package com.google.android.exoplayer;

            import android.annotation.SuppressLint;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.os.Looper;
            import android.os.Message;
            import android.os.Process;
            import android.os.SystemClock;
            import android.util.Log;
            import android.util.Pair;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.TraceUtil;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 85 */    final class ExoPlayerImplInternal implements Handler.Callback {
                private static final int IDLE_INTERVAL_MS = 1000;
                private static final int MSG_CUSTOM = 9;
                private static final int MSG_DO_SOME_WORK = 7;
                public static final int MSG_ERROR = 3;
                private static final int MSG_INCREMENTAL_PREPARE = 2;
                private static final int MSG_PREPARE = 1;
                private static final int MSG_RELEASE = 5;
                private static final int MSG_SEEK_TO = 6;
                private static final int MSG_SET_PLAY_WHEN_READY = 3;
                public static final int MSG_SET_PLAY_WHEN_READY_ACK = 2;
                private static final int MSG_SET_RENDERER_ENABLED = 8;
                public static final int MSG_STATE_CHANGED = 1;
                private static final int MSG_STOP = 4;
                private static final int PREPARE_INTERVAL_MS = 10;
                private static final int RENDERING_INTERVAL_MS = 10;
                private static final String TAG = "ExoPlayerImplInternal";
                private volatile long bufferedPositionUs;
                private volatile long durationUs;
                private final List<TrackRenderer> enabledRenderers;
                private final Handler eventHandler;
                private final Handler handler;
                private final HandlerThread internalPlaybackThread;
                private final MediaClock mediaClock;
                private final long minBufferUs;
                private final long minRebufferUs;
                private boolean playWhenReady;
                private volatile long positionUs;
                private boolean rebuffering;
                private boolean released;
                private final boolean[] rendererEnabledFlags;
                private TrackRenderer[] renderers;
                private int state;
                private TrackRenderer timeSourceTrackRenderer;
                private int customMessagesSent = 0;
                private int customMessagesProcessed = 0;

                @SuppressLint({"HandlerLeak"})
/* 86 */        public ExoPlayerImplInternal(Handler eventHandler, boolean playWhenReady, boolean[] rendererEnabledFlags, int minBufferMs, int minRebufferMs) {
/* 88 */            this.eventHandler = eventHandler;
/* 89 */            this.playWhenReady = playWhenReady;
/* 90 */            this.rendererEnabledFlags = new boolean[rendererEnabledFlags.length];
/* 91 */            this.minBufferUs = ((long) minBufferMs) * 1000;
/* 92 */            this.minRebufferUs = ((long) minRebufferMs) * 1000;
                    for (int i = 0; i < rendererEnabledFlags.length; i++) {
/* 94 */                this.rendererEnabledFlags[i] = rendererEnabledFlags[i];
                    }
/* 97 */            this.state = 1;
/* 98 */            this.durationUs = -1L;
/* 99 */            this.bufferedPositionUs = -1L;
/* 101 */           this.mediaClock = new MediaClock();
/* 102 */           this.enabledRenderers = new ArrayList(rendererEnabledFlags.length);
/* 103 */           this.internalPlaybackThread = new HandlerThreadC08531(getClass().getSimpleName() + ":Handler");
/* 112 */           this.internalPlaybackThread.start();
/* 113 */           this.handler = new Handler(this.internalPlaybackThread.getLooper(), this);
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.ExoPlayerImplInternal$1 */
/* 101 */       class HandlerThreadC08531 extends HandlerThread {
/* 102 */           HandlerThreadC08531(String x0) {
                        super(x0);
                    }

                    @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
/* 107 */           public void run() {
/* 108 */               Process.setThreadPriority(-16);
/* 109 */               super.run();
                    }
                }

/* 116 */       public Looper getPlaybackLooper() {
/* 117 */           return this.internalPlaybackThread.getLooper();
                }

/* 120 */       public int getCurrentPosition() {
/* 121 */           return (int) (this.positionUs / 1000);
                }

/* 124 */       public int getBufferedPosition() {
                    if (this.bufferedPositionUs == -1) {
                        return -1;
                    }
                    return (int) (this.bufferedPositionUs / 1000);
                }

/* 129 */       public int getDuration() {
                    if (this.durationUs == -1) {
                        return -1;
                    }
                    return (int) (this.durationUs / 1000);
                }

/* 134 */       public void prepare(TrackRenderer... renderers) {
/* 135 */           this.handler.obtainMessage(1, renderers).sendToTarget();
                }

/* 138 */       public void setPlayWhenReady(boolean playWhenReady) {
                    this.handler.obtainMessage(3, playWhenReady ? 1 : 0, 0).sendToTarget();
                }

/* 142 */       public void seekTo(int positionMs) {
/* 143 */           this.handler.obtainMessage(6, positionMs, 0).sendToTarget();
                }

/* 146 */       public void stop() {
/* 147 */           this.handler.sendEmptyMessage(4);
                }

/* 150 */       public void setRendererEnabled(int index, boolean enabled) {
                    this.handler.obtainMessage(8, index, enabled ? 1 : 0).sendToTarget();
                }

/* 154 */       public void sendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
/* 155 */           this.customMessagesSent++;
/* 156 */           this.handler.obtainMessage(9, messageType, 0, Pair.create(target, message)).sendToTarget();
                }

/* 160 */       public synchronized void blockingSendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
                    try {
                        int messageNumber = this.customMessagesSent;
                        this.customMessagesSent = messageNumber + 1;
/* 162 */               this.handler.obtainMessage(9, messageType, 0, Pair.create(target, message)).sendToTarget();
/* 163 */               while (this.customMessagesProcessed <= messageNumber) {
                            try {
/* 165 */                       wait();
                            } catch (InterruptedException e) {
/* 167 */                       Thread.currentThread().interrupt();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 172 */       public synchronized void release() {
                    try {
                        if (!this.released) {
/* 174 */                   this.handler.sendEmptyMessage(5);
/* 175 */                   while (!this.released) {
                                try {
/* 177 */                           wait();
                                } catch (InterruptedException e) {
/* 179 */                           Thread.currentThread().interrupt();
                                }
                            }
/* 182 */                   this.internalPlaybackThread.quit();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.os.Handler.Callback
/* 188 */       public boolean handleMessage(Message msg) {
                    boolean z = false;
                    try {
                        switch (msg.what) {
                            case 1:
/* 191 */                       prepareInternal((TrackRenderer[]) msg.obj);
                                z = true;
                                break;
                            case 2:
/* 195 */                       incrementalPrepareInternal();
                                z = true;
                                break;
                            case 3:
/* 199 */                       setPlayWhenReadyInternal(msg.arg1 != 0);
                                z = true;
                                break;
                            case 4:
/* 211 */                       stopInternal();
                                z = true;
                                break;
                            case 5:
/* 215 */                       releaseInternal();
                                z = true;
                                break;
                            case 6:
/* 207 */                       seekToInternal(msg.arg1);
                                z = true;
                                break;
                            case 7:
/* 203 */                       doSomeWork();
                                z = true;
                                break;
                            case 8:
/* 223 */                       setRendererEnabledInternal(msg.arg1, msg.arg2 != 0);
                                z = true;
                                break;
                            case 9:
/* 219 */                       sendMessageInternal(msg.arg1, msg.obj);
                                z = true;
                                break;
                        }
/* 238 */               return z;
                    } catch (ExoPlaybackException e) {
/* 230 */               Log.e(TAG, "Internal track renderer error.", e);
/* 231 */               this.eventHandler.obtainMessage(3, e).sendToTarget();
/* 232 */               stopInternal();
                        return true;
                    } catch (RuntimeException e2) {
/* 235 */               Log.e(TAG, "Internal runtime error.", e2);
/* 236 */               this.eventHandler.obtainMessage(3, new ExoPlaybackException(e2)).sendToTarget();
/* 237 */               stopInternal();
                        return true;
                    }
                }

/* 242 */       private void setState(int state) {
/* 243 */           if (this.state != state) {
/* 244 */               this.state = state;
/* 245 */               this.eventHandler.obtainMessage(1, state, 0).sendToTarget();
                    }
                }

/* 249 */       private void prepareInternal(TrackRenderer[] renderers) {
/* 250 */           this.rebuffering = false;
/* 251 */           this.renderers = renderers;
                    for (int i = 0; i < renderers.length; i++) {
/* 253 */               if (renderers[i].isTimeSource()) {
/* 254 */                   Assertions.checkState(this.timeSourceTrackRenderer == null);
/* 255 */                   this.timeSourceTrackRenderer = renderers[i];
                        }
                    }
/* 258 */           setState(2);
/* 259 */           this.handler.sendEmptyMessage(2);
                }

/* 262 */       private void incrementalPrepareInternal() throws ExoPlaybackException {
/* 263 */           long operationStartTimeMs = SystemClock.elapsedRealtime();
/* 264 */           boolean prepared = true;
                    for (int i = 0; i < this.renderers.length; i++) {
/* 266 */               if (this.renderers[i].getState() == 0) {
/* 267 */                   int state = this.renderers[i].prepare();
/* 268 */                   if (state == 0) {
/* 269 */                       prepared = false;
                            }
                        }
                    }
/* 274 */           if (!prepared) {
/* 276 */               scheduleNextOperation(2, operationStartTimeMs, 10L);
/* 318 */               return;
                    }
/* 280 */           long durationUs = 0;
/* 281 */           boolean isEnded = true;
/* 282 */           boolean allRenderersReadyOrEnded = true;
                    for (int i2 = 0; i2 < this.renderers.length; i2++) {
/* 284 */               TrackRenderer renderer = this.renderers[i2];
/* 285 */               if (this.rendererEnabledFlags[i2] && renderer.getState() == 1) {
/* 286 */                   renderer.enable(this.positionUs, false);
/* 287 */                   this.enabledRenderers.add(renderer);
/* 288 */                   isEnded = isEnded && renderer.isEnded();
/* 289 */                   allRenderersReadyOrEnded = allRenderersReadyOrEnded && rendererReadyOrEnded(renderer);
                            if (durationUs != -1) {
/* 294 */                       long trackDurationUs = renderer.getDurationUs();
                                if (trackDurationUs == -1) {
/* 296 */                           durationUs = -1;
                                } else if (trackDurationUs != -2) {
/* 300 */                           durationUs = Math.max(durationUs, trackDurationUs);
                                }
                            }
                        }
                    }
/* 305 */           this.durationUs = durationUs;
/* 307 */           if (isEnded) {
/* 309 */               setState(5);
                    } else {
/* 311 */               setState(allRenderersReadyOrEnded ? 4 : 3);
/* 312 */               if (this.playWhenReady && this.state == 4) {
/* 313 */                   startRenderers();
                        }
                    }
/* 317 */           this.handler.sendEmptyMessage(7);
                }

/* 320 */       private boolean rendererReadyOrEnded(TrackRenderer renderer) {
                    boolean z = false;
/* 321 */           if (renderer.isEnded()) {
                        return true;
                    }
/* 324 */           if (!renderer.isReady()) {
                        return false;
                    }
/* 327 */           if (this.state == 4) {
                        return true;
                    }
/* 330 */           long rendererDurationUs = renderer.getDurationUs();
/* 331 */           long rendererBufferedPositionUs = renderer.getBufferedPositionUs();
/* 332 */           long minBufferDurationUs = this.rebuffering ? this.minRebufferUs : this.minBufferUs;
                    if (minBufferDurationUs <= 0 || rendererBufferedPositionUs == -1 || rendererBufferedPositionUs == -3 || rendererBufferedPositionUs >= this.positionUs + minBufferDurationUs || (rendererDurationUs != -1 && rendererDurationUs != -2 && rendererBufferedPositionUs >= rendererDurationUs)) {
                        z = true;
                    }
                    return z;
                }

/* 343 */       private void setPlayWhenReadyInternal(boolean playWhenReady) throws ExoPlaybackException {
                    try {
/* 344 */               this.rebuffering = false;
/* 345 */               this.playWhenReady = playWhenReady;
/* 346 */               if (!playWhenReady) {
/* 347 */                   stopRenderers();
/* 348 */                   updatePositionUs();
                        } else if (this.state == 4) {
/* 351 */                   startRenderers();
/* 352 */                   this.handler.sendEmptyMessage(7);
                        } else if (this.state == 3) {
/* 354 */                   this.handler.sendEmptyMessage(7);
                        }
/* 358 */               this.eventHandler.obtainMessage(2).sendToTarget();
                    } catch (Throwable th) {
                        this.eventHandler.obtainMessage(2).sendToTarget();
                        throw th;
                    }
                }

/* 362 */       private void startRenderers() throws ExoPlaybackException {
/* 363 */           this.rebuffering = false;
/* 364 */           this.mediaClock.start();
                    for (int i = 0; i < this.enabledRenderers.size(); i++) {
/* 366 */               this.enabledRenderers.get(i).start();
                    }
                }

/* 370 */       private void stopRenderers() throws ExoPlaybackException {
/* 371 */           this.mediaClock.stop();
                    for (int i = 0; i < this.enabledRenderers.size(); i++) {
/* 373 */               ensureStopped(this.enabledRenderers.get(i));
                    }
                }

/* 377 */       private void updatePositionUs() {
/* 378 */           this.positionUs = (this.timeSourceTrackRenderer == null || !this.enabledRenderers.contains(this.timeSourceTrackRenderer)) ? this.mediaClock.getTimeUs() : this.timeSourceTrackRenderer.getCurrentPositionUs();
                }

/* 384 */       private void doSomeWork() throws ExoPlaybackException {
/* 385 */           TraceUtil.beginSection("doSomeWork");
/* 386 */           long operationStartTimeMs = SystemClock.elapsedRealtime();
/* 387 */           long bufferedPositionUs = this.durationUs != -1 ? this.durationUs : Long.MAX_VALUE;
/* 389 */           boolean isEnded = true;
/* 390 */           boolean allRenderersReadyOrEnded = true;
/* 391 */           updatePositionUs();
                    for (int i = 0; i < this.enabledRenderers.size(); i++) {
/* 393 */               TrackRenderer renderer = this.enabledRenderers.get(i);
/* 397 */               renderer.doSomeWork(this.positionUs);
/* 398 */               isEnded = isEnded && renderer.isEnded();
/* 399 */               allRenderersReadyOrEnded = allRenderersReadyOrEnded && rendererReadyOrEnded(renderer);
                        if (bufferedPositionUs != -1) {
/* 405 */                   long rendererDurationUs = renderer.getDurationUs();
/* 406 */                   long rendererBufferedPositionUs = renderer.getBufferedPositionUs();
                            if (rendererBufferedPositionUs == -1) {
/* 408 */                       bufferedPositionUs = -1;
                            } else if (rendererBufferedPositionUs != -3 && (rendererDurationUs == -1 || rendererDurationUs == -2 || rendererBufferedPositionUs < rendererDurationUs)) {
/* 415 */                       bufferedPositionUs = Math.min(bufferedPositionUs, rendererBufferedPositionUs);
                            }
                        }
                    }
/* 419 */           this.bufferedPositionUs = bufferedPositionUs;
/* 421 */           if (isEnded) {
/* 422 */               setState(5);
/* 423 */               stopRenderers();
                    } else if (this.state == 3 && allRenderersReadyOrEnded) {
/* 425 */               setState(4);
/* 426 */               if (this.playWhenReady) {
/* 427 */                   startRenderers();
                        }
                    } else if (this.state == 4 && !allRenderersReadyOrEnded) {
/* 430 */               this.rebuffering = this.playWhenReady;
/* 431 */               setState(3);
/* 432 */               stopRenderers();
                    }
/* 435 */           this.handler.removeMessages(7);
/* 436 */           if ((this.playWhenReady && this.state == 4) || this.state == 3 || !this.enabledRenderers.isEmpty()) {
/* 437 */               scheduleNextOperation(7, operationStartTimeMs, 10L);
                    }
/* 440 */           TraceUtil.endSection();
                }

/* 444 */       private void scheduleNextOperation(int operationType, long thisOperationStartTimeMs, long intervalMs) {
/* 445 */           long nextOperationStartTimeMs = thisOperationStartTimeMs + intervalMs;
/* 446 */           long nextOperationDelayMs = nextOperationStartTimeMs - SystemClock.elapsedRealtime();
                    if (nextOperationDelayMs <= 0) {
/* 448 */               this.handler.sendEmptyMessage(operationType);
                    } else {
/* 450 */               this.handler.sendEmptyMessageDelayed(operationType, nextOperationDelayMs);
                    }
                }

/* 454 */       private void seekToInternal(int positionMs) throws ExoPlaybackException {
/* 455 */           this.rebuffering = false;
/* 456 */           this.positionUs = ((long) positionMs) * 1000;
/* 457 */           this.mediaClock.stop();
/* 458 */           this.mediaClock.setTimeUs(this.positionUs);
/* 459 */           if (this.state != 1 && this.state != 2) {
                        for (int i = 0; i < this.enabledRenderers.size(); i++) {
/* 463 */                   TrackRenderer renderer = this.enabledRenderers.get(i);
/* 464 */                   ensureStopped(renderer);
/* 465 */                   renderer.seekTo(this.positionUs);
                        }
/* 467 */               setState(3);
/* 468 */               this.handler.sendEmptyMessage(7);
                    }
                }

/* 471 */       private void stopInternal() {
/* 472 */           this.rebuffering = false;
/* 473 */           resetInternal();
                }

/* 476 */       private void releaseInternal() {
/* 477 */           resetInternal();
                    synchronized (this) {
                        try {
/* 479 */                   this.released = true;
/* 480 */                   notifyAll();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 484 */       private void resetInternal() {
/* 485 */           this.handler.removeMessages(7);
/* 486 */           this.handler.removeMessages(2);
/* 487 */           this.mediaClock.stop();
/* 488 */           if (this.renderers != null) {
                        for (int i = 0; i < this.renderers.length; i++) {
                            try {
/* 493 */                       TrackRenderer renderer = this.renderers[i];
/* 494 */                       ensureStopped(renderer);
/* 495 */                       if (renderer.getState() == 2) {
/* 496 */                           renderer.disable();
                                }
/* 498 */                       renderer.release();
                            } catch (ExoPlaybackException e) {
/* 502 */                       Log.e(TAG, "Stop failed.", e);
                            } catch (RuntimeException e2) {
/* 505 */                       Log.e(TAG, "Stop failed.", e2);
                            }
                        }
/* 508 */               this.renderers = null;
/* 509 */               this.timeSourceTrackRenderer = null;
/* 510 */               this.enabledRenderers.clear();
/* 511 */               setState(1);
                    }
                }

/* 517 */       private <T> void sendMessageInternal(int what, Object obj) throws ExoPlaybackException {
                    try {
/* 518 */               Pair<ExoPlayer.ExoPlayerComponent, Object> targetAndMessage = (Pair) obj;
/* 519 */               ((ExoPlayer.ExoPlayerComponent) targetAndMessage.first).handleMessage(what, targetAndMessage.second);
                        synchronized (this) {
                            try {
/* 522 */                       this.customMessagesProcessed++;
/* 523 */                       notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 526 */               if (this.state != 1 && this.state != 2) {
/* 528 */                   this.handler.sendEmptyMessage(7);
                        }
                    } catch (Throwable th2) {
                        synchronized (this) {
                            try {
/* 522 */                       this.customMessagesProcessed++;
/* 523 */                       notifyAll();
                                throw th2;
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }
                }

/* 533 */       private void setRendererEnabledInternal(int index, boolean enabled) throws ExoPlaybackException {
/* 534 */           if (this.rendererEnabledFlags[index] != enabled) {
/* 538 */               this.rendererEnabledFlags[index] = enabled;
/* 539 */               if (this.state != 1 && this.state != 2) {
/* 543 */                   TrackRenderer renderer = this.renderers[index];
/* 544 */                   int rendererState = renderer.getState();
/* 545 */                   if (rendererState == 1 || rendererState == 2 || rendererState == 3) {
/* 551 */                       if (enabled) {
/* 552 */                           boolean playing = this.playWhenReady && this.state == 4;
/* 553 */                           renderer.enable(this.positionUs, playing);
/* 554 */                           this.enabledRenderers.add(renderer);
/* 555 */                           if (playing) {
/* 556 */                               renderer.start();
                                    }
/* 558 */                           this.handler.sendEmptyMessage(7);
/* 569 */                           return;
                                }
/* 560 */                       if (renderer == this.timeSourceTrackRenderer) {
/* 563 */                           this.mediaClock.setTimeUs(renderer.getCurrentPositionUs());
                                }
/* 565 */                       ensureStopped(renderer);
/* 566 */                       this.enabledRenderers.remove(renderer);
/* 567 */                       renderer.disable();
                            }
                        }
                    }
                }

/* 571 */       private void ensureStopped(TrackRenderer renderer) throws ExoPlaybackException {
/* 572 */           if (renderer.getState() == 3) {
/* 573 */               renderer.stop();
                    }
                }
            }
