            package co.vine.android.embed.player;

            import android.content.Context;
            import android.net.Uri;
            import android.os.Looper;
            import android.view.Surface;
            
            
    public class VinePlayer {
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

        static  EventListener access$100(VinePlayer x0) {
            return x0.mListener;
                }

        public VinePlayer(Context context) {
            this.mPlayer = new VinePlayerInternal(context, new VineInternalListener(this, null));
                }

        public void setEventListener(EventListener listener) {
            this.mListener = listener;
                }

        public void setSurface(Surface surface) {
            this.mPlayer.setSurface(surface);
                }

        public void prepareAsync(Uri uri, boolean playWhenReady) {
            this.mPlayer.setUri(uri);
            this.mPlayer.setPlayWhenReady(playWhenReady);
            this.mPlayer.prepareAsync();
                }

        public boolean isPlaying() {
            return this.mPlayer.isPlaying();
                }

       public boolean isPaused() {
                    return this.mPlayer.getState() == PlayerState.PLAYABLE && !this.mPlayer.playWhenReady();
                }

       public int getCurrentPosition() {
           return this.mPlayer.getCurrentPositionMs();
                }

       public void release() throws IllegalAccessException {
           if (Looper.myLooper() == Looper.getMainLooper()) {
               throw new IllegalAccessException("You cannot call this from UI thread.");
                    }
           this.mPlayer.release();
                }

       public void resume() {
           this.mPlayer.setPlayWhenReady(true);
                }

       public boolean isIdle() {
                    return this.mPlayer.getState() == PlayerState.IDLE && !this.mPlayer.isReleased();
                }

       public int getDuration() {
           return (int) this.mPlayer.getDurationMs();
                }

       public void pause() {
           this.mPlayer.setPlayWhenReady(false);
                }

       public void setVolume(float gain) {
           this.mPlayer.setVolume(gain);
                }

       private class VineInternalListener implements VinePlayerInternal.EventListener {
           private VineInternalListener() {
                    }

            VineInternalListener(VinePlayer x0, Object x1) {
               this();
                    }

                    @Override 
           public void onError(PlayerState state, int operation, Throwable e) {
               VinePlayer.access$100(VinePlayer.this).onError(state, operation, e);
                    }

                    @Override 
           public void onVideoSizeChanged(int newWidth, int newHeight) {
               VinePlayer.access$100(VinePlayer.this).onVideoSizeChanged(newWidth, newHeight);
                    }

                    @Override 
           public void onLoopCompleted() {
               VinePlayer.access$100(VinePlayer.this).onLoopCompleted();
                    }

                    @Override 
           public void onPrepared() {
               VinePlayer.access$100(VinePlayer.this).onPrepared();
                    }

                    @Override 
           public void onFirstFrameRendered() {
               VinePlayer.access$100(VinePlayer.this).onFirstFrameRendererd();
                    }

                    @Override 
           public void onPastError(Exception error) {
               VinePlayer.access$100(VinePlayer.this).onPastError(error);
                    }
                }
            }
