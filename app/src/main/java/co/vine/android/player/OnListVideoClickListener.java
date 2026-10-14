            package co.vine.android.player;

            import android.view.View;
            import co.vine.android.util.CommonUtil;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class OnListVideoClickListener implements View.OnClickListener {
                private final HasVideoPlayerAdapter mAdapter;
                private int mPosition;

/* 16 */        public OnListVideoClickListener(HasVideoPlayerAdapter adapter) {
/* 18 */            this.mAdapter = adapter;
                }

                @Override // android.view.View.OnClickListener
/* 22 */        public void onClick(View v) {
/* 23 */            SdkVideoView player = this.mAdapter.getPlayer(this.mPosition, false);
/* 24 */            if (this.mAdapter.getLastPlayer() != player) {
/* 25 */                this.mAdapter.pauseCurrentPlayer();
                    }
/* 27 */            if (player != null && player.hasStarted()) {
/* 28 */                if (SLog.sLogsOn && CommonUtil.DEBUG_VERBOSE) {
/* 29 */                    player.onViewClicked();
                        }
/* 31 */                if (this.mPosition == player.getPlayingPosition()) {
/* 32 */                    if (player.isPaused() || !player.isPlaying()) {
/* 33 */                        this.mAdapter.resumePlayer(player);
/* 41 */                        return;
                            } else {
/* 35 */                        this.mAdapter.pausePlayer(player);
/* 41 */                        return;
                            }
                        }
/* 41 */                return;
                    }
/* 39 */            this.mAdapter.getVideoPathAndPlayForPosition(this.mPosition, false);
                }

/* 43 */        protected void resume(SdkVideoView player) {
/* 44 */            player.resume();
                }

/* 47 */        public void setPosition(int position) {
/* 48 */            this.mPosition = position;
                }

/* 51 */        public int getPosition() {
/* 52 */            return this.mPosition;
                }
            }
