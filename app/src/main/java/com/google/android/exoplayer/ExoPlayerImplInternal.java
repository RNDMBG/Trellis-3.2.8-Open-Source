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
            
            
    final class ExoPlayerImplInternal implements Handler.Callback {
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
        public ExoPlayerImplInternal(Handler eventHandler, boolean playWhenReady, boolean[] rendererEnabledFlags, int minBufferMs, int minRebufferMs) {
            this.eventHandler = eventHandler;
            this.playWhenReady = playWhenReady;
            this.rendererEnabledFlags = new boolean[rendererEnabledFlags.length];
            this.minBufferUs = ((long) minBufferMs) * 1000;
            this.minRebufferUs = ((long) minRebufferMs) * 1000;
                    for (int i = 0; i < rendererEnabledFlags.length; i++) {
                this.rendererEnabledFlags[i] = rendererEnabledFlags[i];
                    }
            this.state = 1;
            this.durationUs = -1L;
            this.bufferedPositionUs = -1L;
           this.mediaClock = new MediaClock();
           this.enabledRenderers = new ArrayList(rendererEnabledFlags.length);
           this.internalPlaybackThread = new HandlerThreadC08531(getClass().getSimpleName() + ":Handler");
           this.internalPlaybackThread.start();
           this.handler = new Handler(this.internalPlaybackThread.getLooper(), this);
                }

                
       class HandlerThreadC08531 extends HandlerThread {
           HandlerThreadC08531(String x0) {
                        super(x0);
                    }

                    @Override 
           public void run() {
               Process.setThreadPriority(-16);
               super.run();
                    }
                }

       public Looper getPlaybackLooper() {
           return this.internalPlaybackThread.getLooper();
                }

       public int getCurrentPosition() {
           return (int) (this.positionUs / 1000);
                }

       public int getBufferedPosition() {
                    if (this.bufferedPositionUs == -1) {
                        return -1;
                    }
                    return (int) (this.bufferedPositionUs / 1000);
                }

       public int getDuration() {
                    if (this.durationUs == -1) {
                        return -1;
                    }
                    return (int) (this.durationUs / 1000);
                }

       public void prepare(TrackRenderer... renderers) {
           this.handler.obtainMessage(1, renderers).sendToTarget();
                }

       public void setPlayWhenReady(boolean playWhenReady) {
                    this.handler.obtainMessage(3, playWhenReady ? 1 : 0, 0).sendToTarget();
                }

       public void seekTo(int positionMs) {
           this.handler.obtainMessage(6, positionMs, 0).sendToTarget();
                }

       public void stop() {
           this.handler.sendEmptyMessage(4);
                }

       public void setRendererEnabled(int index, boolean enabled) {
                    this.handler.obtainMessage(8, index, enabled ? 1 : 0).sendToTarget();
                }

       public void sendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
           this.customMessagesSent++;
           this.handler.obtainMessage(9, messageType, 0, Pair.create(target, message)).sendToTarget();
                }

       public synchronized void blockingSendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
                    try {
                        int messageNumber = this.customMessagesSent;
                        this.customMessagesSent = messageNumber + 1;
               this.handler.obtainMessage(9, messageType, 0, Pair.create(target, message)).sendToTarget();
               while (this.customMessagesProcessed <= messageNumber) {
                            try {
                       wait();
                            } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized void release() {
                    try {
                        if (!this.released) {
                   this.handler.sendEmptyMessage(5);
                   while (!this.released) {
                                try {
                           wait();
                                } catch (InterruptedException e) {
                           Thread.currentThread().interrupt();
                                }
                            }
                   this.internalPlaybackThread.quit();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public boolean handleMessage(Message msg) {
                    boolean z = false;
                    try {
                        switch (msg.what) {
                            case 1:
                       prepareInternal((TrackRenderer[]) msg.obj);
                                z = true;
                                break;
                            case 2:
                       incrementalPrepareInternal();
                                z = true;
                                break;
                            case 3:
                       setPlayWhenReadyInternal(msg.arg1 != 0);
                                z = true;
                                break;
                            case 4:
                       stopInternal();
                                z = true;
                                break;
                            case 5:
                       releaseInternal();
                                z = true;
                                break;
                            case 6:
                       seekToInternal(msg.arg1);
                                z = true;
                                break;
                            case 7:
                       doSomeWork();
                                z = true;
                                break;
                            case 8:
                       setRendererEnabledInternal(msg.arg1, msg.arg2 != 0);
                                z = true;
                                break;
                            case 9:
                       sendMessageInternal(msg.arg1, msg.obj);
                                z = true;
                                break;
                        }
               return z;
                    } catch (ExoPlaybackException e) {
               Log.e(TAG, "Internal track renderer error.", e);
               this.eventHandler.obtainMessage(3, e).sendToTarget();
               stopInternal();
                        return true;
                    } catch (RuntimeException e2) {
               Log.e(TAG, "Internal runtime error.", e2);
               this.eventHandler.obtainMessage(3, new ExoPlaybackException(e2)).sendToTarget();
               stopInternal();
                        return true;
                    }
                }

       private void setState(int state) {
           if (this.state != state) {
               this.state = state;
               this.eventHandler.obtainMessage(1, state, 0).sendToTarget();
                    }
                }

       private void prepareInternal(TrackRenderer[] renderers) {
           this.rebuffering = false;
           this.renderers = renderers;
                    for (int i = 0; i < renderers.length; i++) {
               if (renderers[i].isTimeSource()) {
                   Assertions.checkState(this.timeSourceTrackRenderer == null);
                   this.timeSourceTrackRenderer = renderers[i];
                        }
                    }
           setState(2);
           this.handler.sendEmptyMessage(2);
                }

       private void incrementalPrepareInternal() throws ExoPlaybackException {
           long operationStartTimeMs = SystemClock.elapsedRealtime();
           boolean prepared = true;
                    for (int i = 0; i < this.renderers.length; i++) {
               if (this.renderers[i].getState() == 0) {
                   int state = this.renderers[i].prepare();
                   if (state == 0) {
                       prepared = false;
                            }
                        }
                    }
           if (!prepared) {
               scheduleNextOperation(2, operationStartTimeMs, 10L);
               return;
                    }
           long durationUs = 0;
           boolean isEnded = true;
           boolean allRenderersReadyOrEnded = true;
                    for (int i2 = 0; i2 < this.renderers.length; i2++) {
               TrackRenderer renderer = this.renderers[i2];
               if (this.rendererEnabledFlags[i2] && renderer.getState() == 1) {
                   renderer.enable(this.positionUs, false);
                   this.enabledRenderers.add(renderer);
                   isEnded = isEnded && renderer.isEnded();
                   allRenderersReadyOrEnded = allRenderersReadyOrEnded && rendererReadyOrEnded(renderer);
                            if (durationUs != -1) {
                       long trackDurationUs = renderer.getDurationUs();
                                if (trackDurationUs == -1) {
                           durationUs = -1;
                                } else if (trackDurationUs != -2) {
                           durationUs = Math.max(durationUs, trackDurationUs);
                                }
                            }
                        }
                    }
           this.durationUs = durationUs;
           if (isEnded) {
               setState(5);
                    } else {
               setState(allRenderersReadyOrEnded ? 4 : 3);
               if (this.playWhenReady && this.state == 4) {
                   startRenderers();
                        }
                    }
           this.handler.sendEmptyMessage(7);
                }

       private boolean rendererReadyOrEnded(TrackRenderer renderer) {
                    boolean z = false;
           if (renderer.isEnded()) {
                        return true;
                    }
           if (!renderer.isReady()) {
                        return false;
                    }
           if (this.state == 4) {
                        return true;
                    }
           long rendererDurationUs = renderer.getDurationUs();
           long rendererBufferedPositionUs = renderer.getBufferedPositionUs();
           long minBufferDurationUs = this.rebuffering ? this.minRebufferUs : this.minBufferUs;
                    if (minBufferDurationUs <= 0 || rendererBufferedPositionUs == -1 || rendererBufferedPositionUs == -3 || rendererBufferedPositionUs >= this.positionUs + minBufferDurationUs || (rendererDurationUs != -1 && rendererDurationUs != -2 && rendererBufferedPositionUs >= rendererDurationUs)) {
                        z = true;
                    }
                    return z;
                }

       private void setPlayWhenReadyInternal(boolean playWhenReady) throws ExoPlaybackException {
                    try {
               this.rebuffering = false;
               this.playWhenReady = playWhenReady;
               if (!playWhenReady) {
                   stopRenderers();
                   updatePositionUs();
                        } else if (this.state == 4) {
                   startRenderers();
                   this.handler.sendEmptyMessage(7);
                        } else if (this.state == 3) {
                   this.handler.sendEmptyMessage(7);
                        }
               this.eventHandler.obtainMessage(2).sendToTarget();
                    } catch (Throwable th) {
                        this.eventHandler.obtainMessage(2).sendToTarget();
                        throw th;
                    }
                }

       private void startRenderers() throws ExoPlaybackException {
           this.rebuffering = false;
           this.mediaClock.start();
                    for (int i = 0; i < this.enabledRenderers.size(); i++) {
               this.enabledRenderers.get(i).start();
                    }
                }

       private void stopRenderers() throws ExoPlaybackException {
           this.mediaClock.stop();
                    for (int i = 0; i < this.enabledRenderers.size(); i++) {
               ensureStopped(this.enabledRenderers.get(i));
                    }
                }

       private void updatePositionUs() {
           this.positionUs = (this.timeSourceTrackRenderer == null || !this.enabledRenderers.contains(this.timeSourceTrackRenderer)) ? this.mediaClock.getTimeUs() : this.timeSourceTrackRenderer.getCurrentPositionUs();
                }

       private void doSomeWork() throws ExoPlaybackException {
           TraceUtil.beginSection("doSomeWork");
           long operationStartTimeMs = SystemClock.elapsedRealtime();
           long bufferedPositionUs = this.durationUs != -1 ? this.durationUs : Long.MAX_VALUE;
           boolean isEnded = true;
           boolean allRenderersReadyOrEnded = true;
           updatePositionUs();
                    for (int i = 0; i < this.enabledRenderers.size(); i++) {
               TrackRenderer renderer = this.enabledRenderers.get(i);
               renderer.doSomeWork(this.positionUs);
               isEnded = isEnded && renderer.isEnded();
               allRenderersReadyOrEnded = allRenderersReadyOrEnded && rendererReadyOrEnded(renderer);
                        if (bufferedPositionUs != -1) {
                   long rendererDurationUs = renderer.getDurationUs();
                   long rendererBufferedPositionUs = renderer.getBufferedPositionUs();
                            if (rendererBufferedPositionUs == -1) {
                       bufferedPositionUs = -1;
                            } else if (rendererBufferedPositionUs != -3 && (rendererDurationUs == -1 || rendererDurationUs == -2 || rendererBufferedPositionUs < rendererDurationUs)) {
                       bufferedPositionUs = Math.min(bufferedPositionUs, rendererBufferedPositionUs);
                            }
                        }
                    }
           this.bufferedPositionUs = bufferedPositionUs;
           if (isEnded) {
               setState(5);
               stopRenderers();
                    } else if (this.state == 3 && allRenderersReadyOrEnded) {
               setState(4);
               if (this.playWhenReady) {
                   startRenderers();
                        }
                    } else if (this.state == 4 && !allRenderersReadyOrEnded) {
               this.rebuffering = this.playWhenReady;
               setState(3);
               stopRenderers();
                    }
           this.handler.removeMessages(7);
           if ((this.playWhenReady && this.state == 4) || this.state == 3 || !this.enabledRenderers.isEmpty()) {
               scheduleNextOperation(7, operationStartTimeMs, 10L);
                    }
           TraceUtil.endSection();
                }

       private void scheduleNextOperation(int operationType, long thisOperationStartTimeMs, long intervalMs) {
           long nextOperationStartTimeMs = thisOperationStartTimeMs + intervalMs;
           long nextOperationDelayMs = nextOperationStartTimeMs - SystemClock.elapsedRealtime();
                    if (nextOperationDelayMs <= 0) {
               this.handler.sendEmptyMessage(operationType);
                    } else {
               this.handler.sendEmptyMessageDelayed(operationType, nextOperationDelayMs);
                    }
                }

       private void seekToInternal(int positionMs) throws ExoPlaybackException {
           this.rebuffering = false;
           this.positionUs = ((long) positionMs) * 1000;
           this.mediaClock.stop();
           this.mediaClock.setTimeUs(this.positionUs);
           if (this.state != 1 && this.state != 2) {
                        for (int i = 0; i < this.enabledRenderers.size(); i++) {
                   TrackRenderer renderer = this.enabledRenderers.get(i);
                   ensureStopped(renderer);
                   renderer.seekTo(this.positionUs);
                        }
               setState(3);
               this.handler.sendEmptyMessage(7);
                    }
                }

       private void stopInternal() {
           this.rebuffering = false;
           resetInternal();
                }

       private void releaseInternal() {
           resetInternal();
                    synchronized (this) {
                        try {
                   this.released = true;
                   notifyAll();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private void resetInternal() {
           this.handler.removeMessages(7);
           this.handler.removeMessages(2);
           this.mediaClock.stop();
           if (this.renderers != null) {
                        for (int i = 0; i < this.renderers.length; i++) {
                            try {
                       TrackRenderer renderer = this.renderers[i];
                       ensureStopped(renderer);
                       if (renderer.getState() == 2) {
                           renderer.disable();
                                }
                       renderer.release();
                            } catch (ExoPlaybackException e) {
                       Log.e(TAG, "Stop failed.", e);
                            } catch (RuntimeException e2) {
                       Log.e(TAG, "Stop failed.", e2);
                            }
                        }
               this.renderers = null;
               this.timeSourceTrackRenderer = null;
               this.enabledRenderers.clear();
               setState(1);
                    }
                }

       private <T> void sendMessageInternal(int what, Object obj) throws ExoPlaybackException {
                    try {
               Pair<ExoPlayer.ExoPlayerComponent, Object> targetAndMessage = (Pair) obj;
               ((ExoPlayer.ExoPlayerComponent) targetAndMessage.first).handleMessage(what, targetAndMessage.second);
                        synchronized (this) {
                            try {
                       this.customMessagesProcessed++;
                       notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               if (this.state != 1 && this.state != 2) {
                   this.handler.sendEmptyMessage(7);
                        }
                    } catch (Throwable th2) {
                        synchronized (this) {
                            try {
                       this.customMessagesProcessed++;
                       notifyAll();
                                throw th2;
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }
                }

       private void setRendererEnabledInternal(int index, boolean enabled) throws ExoPlaybackException {
           if (this.rendererEnabledFlags[index] != enabled) {
               this.rendererEnabledFlags[index] = enabled;
               if (this.state != 1 && this.state != 2) {
                   TrackRenderer renderer = this.renderers[index];
                   int rendererState = renderer.getState();
                   if (rendererState == 1 || rendererState == 2 || rendererState == 3) {
                       if (enabled) {
                           boolean playing = this.playWhenReady && this.state == 4;
                           renderer.enable(this.positionUs, playing);
                           this.enabledRenderers.add(renderer);
                           if (playing) {
                               renderer.start();
                                    }
                           this.handler.sendEmptyMessage(7);
                           return;
                                }
                       if (renderer == this.timeSourceTrackRenderer) {
                           this.mediaClock.setTimeUs(renderer.getCurrentPositionUs());
                                }
                       ensureStopped(renderer);
                       this.enabledRenderers.remove(renderer);
                       renderer.disable();
                            }
                        }
                    }
                }

       private void ensureStopped(TrackRenderer renderer) throws ExoPlaybackException {
           if (renderer.getState() == 3) {
               renderer.stop();
                    }
                }
            }
