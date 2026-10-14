            package com.google.android.exoplayer;

            import android.os.Looper;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 110 */   public interface ExoPlayer {
                public static final int STATE_BUFFERING = 3;
                public static final int STATE_ENDED = 5;
                public static final int STATE_IDLE = 1;
                public static final int STATE_PREPARING = 2;
                public static final int STATE_READY = 4;
                public static final int UNKNOWN_TIME = -1;

                public interface ExoPlayerComponent {
                    void handleMessage(int i, Object obj) throws ExoPlaybackException;
                }

                public interface Listener {
                    void onPlayWhenReadyCommitted();

                    void onPlayerError(ExoPlaybackException exoPlaybackException);

                    void onPlayerStateChanged(boolean z, int i);
                }

                void addListener(Listener listener);

                void blockingSendMessage(ExoPlayerComponent exoPlayerComponent, int i, Object obj);

                int getBufferedPercentage();

                int getBufferedPosition();

                int getCurrentPosition();

                int getDuration();

                boolean getPlayWhenReady();

                Looper getPlaybackLooper();

                int getPlaybackState();

                boolean getRendererEnabled(int i);

                boolean isPlayWhenReadyCommitted();

                void prepare(TrackRenderer... trackRendererArr);

                void release();

                void removeListener(Listener listener);

                void seekTo(int i);

                void sendMessage(ExoPlayerComponent exoPlayerComponent, int i, Object obj);

                void setPlayWhenReady(boolean z);

                void setRendererEnabled(int i, boolean z);

                void stop();

/* 111 */       public static final class Factory {
                    public static final int DEFAULT_MIN_BUFFER_MS = 500;
                    public static final int DEFAULT_MIN_REBUFFER_MS = 5000;

/* 112 */           private Factory() {
                    }

/* 128 */           public static ExoPlayer newInstance(int rendererCount, int minBufferMs, int minRebufferMs) {
/* 129 */               return new ExoPlayerImpl(rendererCount, minBufferMs, minRebufferMs);
                    }

/* 140 */           public static ExoPlayer newInstance(int rendererCount) {
/* 141 */               return new ExoPlayerImpl(rendererCount, 500, DEFAULT_MIN_REBUFFER_MS);
                    }

                    @Deprecated
/* 148 */           public static ExoPlayer newInstance(int rendererCount, int minRebufferMs) {
/* 149 */               return new ExoPlayerImpl(rendererCount, 500, minRebufferMs);
                    }
                }
            }
