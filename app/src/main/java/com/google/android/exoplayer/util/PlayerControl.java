            package com.google.android.exoplayer.util;

            import android.widget.MediaController;
            import com.google.android.exoplayer.ExoPlayer;
            
            
    public class PlayerControl implements MediaController.MediaPlayerControl {
                private final ExoPlayer exoPlayer;

        public PlayerControl(ExoPlayer exoPlayer) {
            this.exoPlayer = exoPlayer;
                }

                @Override 
        public boolean canPause() {
            return true;
                }

                @Override 
        public boolean canSeekBackward() {
            return true;
                }

                @Override 
        public boolean canSeekForward() {
            return true;
                }

                @Override 
        public int getAudioSessionId() {
            throw new UnsupportedOperationException();
                }

                @Override 
        public int getBufferPercentage() {
            return this.exoPlayer.getBufferedPercentage();
                }

                @Override 
        public int getCurrentPosition() {
            return this.exoPlayer.getCurrentPosition();
                }

                @Override 
        public int getDuration() {
            return this.exoPlayer.getDuration();
                }

                @Override 
        public boolean isPlaying() {
            return this.exoPlayer.getPlayWhenReady();
                }

                @Override 
        public void start() {
            this.exoPlayer.setPlayWhenReady(true);
                }

                @Override 
        public void pause() {
            this.exoPlayer.setPlayWhenReady(false);
                }

                @Override 
        public void seekTo(int timeMillis) {
            this.exoPlayer.seekTo(Math.min(Math.max(0, timeMillis), getDuration()));
                }
            }
