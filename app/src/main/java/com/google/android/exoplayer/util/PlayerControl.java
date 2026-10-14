            package com.google.android.exoplayer.util;

            import android.widget.MediaController;
            import com.google.android.exoplayer.ExoPlayer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class PlayerControl implements MediaController.MediaPlayerControl {
                private final ExoPlayer exoPlayer;

/* 31 */        public PlayerControl(ExoPlayer exoPlayer) {
/* 33 */            this.exoPlayer = exoPlayer;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 37 */        public boolean canPause() {
/* 38 */            return true;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 42 */        public boolean canSeekBackward() {
/* 43 */            return true;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 47 */        public boolean canSeekForward() {
/* 48 */            return true;
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 62 */        public int getAudioSessionId() {
/* 63 */            throw new UnsupportedOperationException();
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 67 */        public int getBufferPercentage() {
/* 68 */            return this.exoPlayer.getBufferedPercentage();
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 72 */        public int getCurrentPosition() {
/* 73 */            return this.exoPlayer.getCurrentPosition();
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 77 */        public int getDuration() {
/* 78 */            return this.exoPlayer.getDuration();
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 82 */        public boolean isPlaying() {
/* 83 */            return this.exoPlayer.getPlayWhenReady();
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 87 */        public void start() {
/* 88 */            this.exoPlayer.setPlayWhenReady(true);
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 92 */        public void pause() {
/* 93 */            this.exoPlayer.setPlayWhenReady(false);
                }

                @Override // android.widget.MediaController.MediaPlayerControl
/* 98 */        public void seekTo(int timeMillis) {
/* 99 */            this.exoPlayer.seekTo(Math.min(Math.max(0, timeMillis), getDuration()));
                }
            }
