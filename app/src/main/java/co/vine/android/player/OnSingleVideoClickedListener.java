            package co.vine.android.player;

            import android.view.View;
            import com.edisonwang.android.slog.SLog;
            import java.lang.ref.WeakReference;
            
            
    public class OnSingleVideoClickedListener implements View.OnClickListener {
                private final WeakReference<VideoViewInterface> mViewPlayer;

        public OnSingleVideoClickedListener(VideoViewInterface videoPlayer) {
            this.mViewPlayer = new WeakReference<>(videoPlayer);
                }

        public void onClick() {
            VideoViewInterface player = this.mViewPlayer.get();
            if (player != null && player.hasStarted()) {
                SLog.m446d("Change player state.");
                if (player.isPaused()) {
                    player.resume();
                    return;
                        } else {
                    player.pause();
                    return;
                        }
                    }
            SLog.m446d("Ignore because it is not in playing state.");
                }

                @Override 
        public void onClick(View v) {
            onClick();
                }
            }
