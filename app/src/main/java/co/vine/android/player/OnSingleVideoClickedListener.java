            package co.vine.android.player;

            import android.view.View;
            import com.edisonwang.android.slog.SLog;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class OnSingleVideoClickedListener implements View.OnClickListener {
                private final WeakReference<VideoViewInterface> mViewPlayer;

/* 15 */        public OnSingleVideoClickedListener(VideoViewInterface videoPlayer) {
/* 17 */            this.mViewPlayer = new WeakReference<>(videoPlayer);
                }

/* 20 */        public void onClick() {
/* 21 */            VideoViewInterface player = this.mViewPlayer.get();
/* 22 */            if (player != null && player.hasStarted()) {
/* 23 */                SLog.m446d("Change player state.");
/* 24 */                if (player.isPaused()) {
/* 25 */                    player.resume();
/* 32 */                    return;
                        } else {
/* 27 */                    player.pause();
/* 32 */                    return;
                        }
                    }
/* 30 */            SLog.m446d("Ignore because it is not in playing state.");
                }

                @Override // android.view.View.OnClickListener
/* 35 */        public void onClick(View v) {
/* 36 */            onClick();
                }
            }
