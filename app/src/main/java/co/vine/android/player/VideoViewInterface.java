            package co.vine.android.player;

            import android.content.Context;
            import co.vine.android.cache.CacheKey;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface VideoViewInterface {

                public interface OnCompletionListener {
                    void onCompletion(VideoViewInterface videoViewInterface);
                }

                public interface OnErrorListener {
                    boolean onError(VideoViewInterface videoViewInterface, int i, int i2);
                }

                public interface OnPreparedListener {
                    void onPrepared(VideoViewInterface videoViewInterface);
                }

                CacheKey getCacheKey();

                Context getContext();

                int getCurrentPosition();

                int getDuration();

                int getPlayingPosition();

                Object getTag();

                boolean hasStarted();

                boolean isInPlaybackState();

                boolean isPaused();

                boolean isPlaying();

                void pause();

                void resume();

                void seekTo(int i);

                void setAutoPlayOnPrepared(boolean z);

                void setCacheKey(CacheKey cacheKey);

                void setKeepScreenOn(boolean z);

                void setLooping(boolean z);

                void setOnCompletionListener(OnCompletionListener onCompletionListener);

                void setOnErrorListener(OnErrorListener onErrorListener);

                void setOnPreparedListener(OnPreparedListener onPreparedListener);

                void setVideoPath(String str);

                void setVideoPathDirect(String str);

                void start();

                void stopPlayback();

                void suspend();
            }
