            package com.google.android.exoplayer;

            import android.annotation.SuppressLint;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.util.Log;
            import java.util.concurrent.CopyOnWriteArraySet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    final class ExoPlayerImpl implements ExoPlayer {
                private static final String TAG = "ExoPlayerImpl";
                private final Handler eventHandler;
                private final ExoPlayerImplInternal internalPlayer;
                private final CopyOnWriteArraySet<ExoPlayer.Listener> listeners;
                private int pendingPlayWhenReadyAcks;
                private boolean playWhenReady;
                private int playbackState;
                private final boolean[] rendererEnabledFlags;

                @SuppressLint({"HandlerLeak"})
/* 53 */        public ExoPlayerImpl(int rendererCount, int minBufferMs, int minRebufferMs) {
/* 55 */            Log.i(TAG, "Init 1.0.13");
/* 56 */            this.playbackState = 1;
/* 57 */            this.listeners = new CopyOnWriteArraySet<>();
/* 58 */            this.rendererEnabledFlags = new boolean[rendererCount];
                    for (int i = 0; i < this.rendererEnabledFlags.length; i++) {
/* 60 */                this.rendererEnabledFlags[i] = true;
                    }
/* 62 */            this.eventHandler = new HandlerC08521();
/* 68 */            this.internalPlayer = new ExoPlayerImplInternal(this.eventHandler, this.playWhenReady, this.rendererEnabledFlags, minBufferMs, minRebufferMs);
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.ExoPlayerImpl$1 */
/* 60 */        class HandlerC08521 extends Handler {
/* 61 */            HandlerC08521() {
                    }

                    @Override // android.os.Handler
/* 64 */            public void handleMessage(Message msg) {
/* 65 */                ExoPlayerImpl.this.handleEvent(msg);
                    }
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 73 */        public Looper getPlaybackLooper() {
/* 74 */            return this.internalPlayer.getPlaybackLooper();
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 78 */        public void addListener(ExoPlayer.Listener listener) {
/* 79 */            this.listeners.add(listener);
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 83 */        public void removeListener(ExoPlayer.Listener listener) {
/* 84 */            this.listeners.remove(listener);
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 88 */        public int getPlaybackState() {
/* 89 */            return this.playbackState;
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 93 */        public void prepare(TrackRenderer... renderers) {
/* 94 */            this.internalPlayer.prepare(renderers);
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 98 */        public void setRendererEnabled(int index, boolean enabled) {
/* 99 */            if (this.rendererEnabledFlags[index] != enabled) {
/* 100 */               this.rendererEnabledFlags[index] = enabled;
/* 101 */               this.internalPlayer.setRendererEnabled(index, enabled);
                    }
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 106 */       public boolean getRendererEnabled(int index) {
/* 107 */           return this.rendererEnabledFlags[index];
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 111 */       public void setPlayWhenReady(boolean playWhenReady) {
/* 112 */           if (this.playWhenReady != playWhenReady) {
/* 113 */               this.playWhenReady = playWhenReady;
/* 114 */               this.pendingPlayWhenReadyAcks++;
/* 115 */               this.internalPlayer.setPlayWhenReady(playWhenReady);
                        for (ExoPlayer.Listener listener : this.listeners) {
/* 117 */                   listener.onPlayerStateChanged(playWhenReady, this.playbackState);
                        }
                    }
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 123 */       public boolean getPlayWhenReady() {
/* 124 */           return this.playWhenReady;
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 128 */       public boolean isPlayWhenReadyCommitted() {
                    return this.pendingPlayWhenReadyAcks == 0;
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 133 */       public void seekTo(int positionMs) {
/* 134 */           this.internalPlayer.seekTo(positionMs);
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 138 */       public void stop() {
/* 139 */           this.internalPlayer.stop();
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 143 */       public void release() {
/* 144 */           this.internalPlayer.release();
/* 145 */           this.eventHandler.removeCallbacksAndMessages(null);
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 149 */       public void sendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
/* 150 */           this.internalPlayer.sendMessage(target, messageType, message);
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 154 */       public void blockingSendMessage(ExoPlayer.ExoPlayerComponent target, int messageType, Object message) {
/* 155 */           this.internalPlayer.blockingSendMessage(target, messageType, message);
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 159 */       public int getDuration() {
/* 160 */           return this.internalPlayer.getDuration();
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 164 */       public int getCurrentPosition() {
/* 165 */           return this.internalPlayer.getCurrentPosition();
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 169 */       public int getBufferedPosition() {
/* 170 */           return this.internalPlayer.getBufferedPosition();
                }

                @Override // com.google.android.exoplayer.ExoPlayer
/* 174 */       public int getBufferedPercentage() {
/* 175 */           int bufferedPosition = getBufferedPosition();
/* 176 */           int duration = getDuration();
/* 177 */           if (bufferedPosition == -1 || duration == -1) {
                        return 0;
                    }
                    if (duration == 0) {
                        return 100;
                    }
                    return (bufferedPosition * 100) / duration;
                }

/* 182 */       void handleEvent(Message msg) {
                    switch (msg.what) {
                        case 1:
/* 185 */                   this.playbackState = msg.arg1;
                            for (ExoPlayer.Listener listener : this.listeners) {
/* 187 */                       listener.onPlayerStateChanged(this.playWhenReady, this.playbackState);
                            }
                            break;
                        case 2:
/* 192 */                   this.pendingPlayWhenReadyAcks--;
/* 193 */                   if (this.pendingPlayWhenReadyAcks == 0) {
                                for (ExoPlayer.Listener listener2 : this.listeners) {
/* 195 */                           listener2.onPlayWhenReadyCommitted();
                                }
                            }
                            break;
                        case 3:
/* 201 */                   ExoPlaybackException exception = (ExoPlaybackException) msg.obj;
                            for (ExoPlayer.Listener listener3 : this.listeners) {
/* 203 */                       listener3.onPlayerError(exception);
                            }
                            break;
                    }
                }
            }
