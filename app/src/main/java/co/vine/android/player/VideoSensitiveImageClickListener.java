            package co.vine.android.player;

            import android.view.View;
            import java.util.HashSet;
            
            
    public class VideoSensitiveImageClickListener extends SensitiveImageClickListener {
                private HasVideoPlayerAdapter mAdapter;

        public VideoSensitiveImageClickListener(HasVideoPlayerAdapter adapter, HashSet<Long> sensitiveContentWarningsAcknowledged) {
            super(sensitiveContentWarningsAcknowledged);
            this.mAdapter = adapter;
                }

                @Override 
        public void onClick(View view) {
            SdkVideoView player = this.mAdapter.getPlayer(this.mPosition, false);
            if (this.mAdapter.getLastPlayer() != player) {
                this.mAdapter.pauseCurrentPlayer();
                    }
            if (player != null && player.hasStarted()) {
                if (this.mPosition == player.getPlayingPosition()) {
                    if (player.isPaused()) {
                        player.resume();
                        return;
                            } else {
                        player.pause();
                        return;
                            }
                        }
                this.mSensitiveContentWarningsAcknowledged.add(Long.valueOf(this.mPostId));
                this.mAdapter.playFile(this.mPosition, false);
                return;
                    }
            this.mSensitiveContentWarningsAcknowledged.add(Long.valueOf(this.mPostId));
            this.mAdapter.getVideoPathAndPlayForPosition(this.mPosition, false);
                }
            }
