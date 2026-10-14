            package co.vine.android.player;

            import android.view.View;
            import co.vine.android.util.CommonUtil;
            import com.edisonwang.android.slog.SLog;
            
            
    public class OnListVideoClickListener implements View.OnClickListener {
                private final HasVideoPlayerAdapter mAdapter;
                private int mPosition;

        public OnListVideoClickListener(HasVideoPlayerAdapter adapter) {
            this.mAdapter = adapter;
                }

                @Override 
        public void onClick(View v) {
            SdkVideoView player = this.mAdapter.getPlayer(this.mPosition, false);
            if (this.mAdapter.getLastPlayer() != player) {
                this.mAdapter.pauseCurrentPlayer();
                    }
            if (player != null && player.hasStarted()) {
                if (SLog.sLogsOn && CommonUtil.DEBUG_VERBOSE) {
                    player.onViewClicked();
                        }
                if (this.mPosition == player.getPlayingPosition()) {
                    if (player.isPaused() || !player.isPlaying()) {
                        this.mAdapter.resumePlayer(player);
                        return;
                            } else {
                        this.mAdapter.pausePlayer(player);
                        return;
                            }
                        }
                return;
                    }
            this.mAdapter.getVideoPathAndPlayForPosition(this.mPosition, false);
                }

        protected void resume(SdkVideoView player) {
            player.resume();
                }

        public void setPosition(int position) {
            this.mPosition = position;
                }

        public int getPosition() {
            return this.mPosition;
                }
            }
