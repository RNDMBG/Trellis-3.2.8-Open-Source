            package co.vine.android.player;

            import android.view.View;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class VideoSensitiveImageClickListener extends SensitiveImageClickListener {
                private HasVideoPlayerAdapter mAdapter;

/* 19 */        public VideoSensitiveImageClickListener(HasVideoPlayerAdapter adapter, HashSet<Long> sensitiveContentWarningsAcknowledged) {
/* 20 */            super(sensitiveContentWarningsAcknowledged);
/* 21 */            this.mAdapter = adapter;
                }

                @Override // android.view.View.OnClickListener
/* 25 */        public void onClick(View view) {
/* 26 */            SdkVideoView player = this.mAdapter.getPlayer(this.mPosition, false);
/* 27 */            if (this.mAdapter.getLastPlayer() != player) {
/* 28 */                this.mAdapter.pauseCurrentPlayer();
                    }
/* 30 */            if (player != null && player.hasStarted()) {
/* 31 */                if (this.mPosition == player.getPlayingPosition()) {
/* 32 */                    if (player.isPaused()) {
/* 33 */                        player.resume();
/* 45 */                        return;
                            } else {
/* 35 */                        player.pause();
/* 45 */                        return;
                            }
                        }
/* 38 */                this.mSensitiveContentWarningsAcknowledged.add(Long.valueOf(this.mPostId));
/* 39 */                this.mAdapter.playFile(this.mPosition, false);
/* 45 */                return;
                    }
/* 42 */            this.mSensitiveContentWarningsAcknowledged.add(Long.valueOf(this.mPostId));
/* 43 */            this.mAdapter.getVideoPathAndPlayForPosition(this.mPosition, false);
                }
            }
