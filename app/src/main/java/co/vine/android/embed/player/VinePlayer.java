            package co.vine.android.embed.player;

            import android.content.Context;
            import android.net.Uri;
            import android.os.Looper;
            import android.view.Surface;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class VinePlayer {
                private EventListener mListener;
                private final VinePlayerInternal mPlayer;

                public interface EventListener {
                    void onError(PlayerState playerState, int i, Throwable th);

                    void onFirstFrameRendererd();

                    void onLoopCompleted();

                    void onPastError(Exception exc);

                    void onPrepared();

                    void onVideoSizeChanged(int i, int i2);
                }

/* 14 */        static /* synthetic */ EventListener access$100(VinePlayer x0) {
/* 15 */            return x0.mListener;
                }

/* 61 */        public VinePlayer(Context context) {
/* 63 */            this.mPlayer = new VinePlayerInternal(context, new VineInternalListener(this, null));
                }

/* 66 */        public void setEventListener(EventListener listener) {
/* 67 */            this.mListener = listener;
                }

/* 77 */        public void setSurface(Surface surface) {
/* 78 */            this.mPlayer.setSurface(surface);
                }

/* 87 */        public void prepareAsync(Uri uri, boolean playWhenReady) {
/* 88 */            this.mPlayer.setUri(uri);
/* 89 */            this.mPlayer.setPlayWhenReady(playWhenReady);
/* 90 */            this.mPlayer.prepareAsync();
                }

/* 96 */        public boolean isPlaying() {
/* 97 */            return this.mPlayer.isPlaying();
                }

/* 103 */       public boolean isPaused() {
                    return this.mPlayer.getState() == PlayerState.PLAYABLE && !this.mPlayer.playWhenReady();
                }

/* 110 */       public int getCurrentPosition() {
/* 111 */           return this.mPlayer.getCurrentPositionMs();
                }

/* 119 */       public void release() throws IllegalAccessException {
/* 120 */           if (Looper.myLooper() == Looper.getMainLooper()) {
/* 121 */               throw new IllegalAccessException("You cannot call this from UI thread.");
                    }
/* 123 */           this.mPlayer.release();
                }

/* 129 */       public void resume() {
/* 130 */           this.mPlayer.setPlayWhenReady(true);
                }

/* 136 */       public boolean isIdle() {
                    return this.mPlayer.getState() == PlayerState.IDLE && !this.mPlayer.isReleased();
                }

/* 143 */       public int getDuration() {
/* 144 */           return (int) this.mPlayer.getDurationMs();
                }

/* 150 */       public void pause() {
/* 151 */           this.mPlayer.setPlayWhenReady(false);
                }

/* 157 */       public void setVolume(float gain) {
/* 158 */           this.mPlayer.setVolume(gain);
                }

/* 159 */       private class VineInternalListener implements VinePlayerInternal.EventListener {
/* 160 */           private VineInternalListener() {
                    }

/* 160 */           /* synthetic */ VineInternalListener(VinePlayer x0, Object x1) {
/* 161 */               this();
                    }

                    @Override // co.vine.android.embed.player.VinePlayerInternal.EventListener
/* 164 */           public void onError(PlayerState state, int operation, Throwable e) {
/* 165 */               VinePlayer.access$100(VinePlayer.this).onError(state, operation, e);
                    }

                    @Override // co.vine.android.embed.player.VinePlayerInternal.EventListener
/* 169 */           public void onVideoSizeChanged(int newWidth, int newHeight) {
/* 170 */               VinePlayer.access$100(VinePlayer.this).onVideoSizeChanged(newWidth, newHeight);
                    }

                    @Override // co.vine.android.embed.player.VinePlayerInternal.EventListener
/* 174 */           public void onLoopCompleted() {
/* 175 */               VinePlayer.access$100(VinePlayer.this).onLoopCompleted();
                    }

                    @Override // co.vine.android.embed.player.VinePlayerInternal.EventListener
/* 179 */           public void onPrepared() {
/* 180 */               VinePlayer.access$100(VinePlayer.this).onPrepared();
                    }

                    @Override // co.vine.android.embed.player.VinePlayerInternal.EventListener
/* 184 */           public void onFirstFrameRendered() {
/* 185 */               VinePlayer.access$100(VinePlayer.this).onFirstFrameRendererd();
                    }

                    @Override // co.vine.android.embed.player.VinePlayerInternal.EventListener
/* 189 */           public void onPastError(Exception error) {
/* 190 */               VinePlayer.access$100(VinePlayer.this).onPastError(error);
                    }
                }
            }
