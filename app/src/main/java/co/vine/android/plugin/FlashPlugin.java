            package co.vine.android.plugin;

            import android.graphics.PorterDuff;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.LinearLayout;
            import co.vine.android.C0257R;
            import co.vine.android.recorder.VineRecorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class FlashPlugin extends BaseToolPlugin<Button> {
                private boolean mIsMessaging;


/* 20 */        protected FlashPlugin() {
/* 21 */            super("Flash");
/* 18 */            this.mIsMessaging = false;
                }

                @Override // android.view.View.OnClickListener
/* 25 */        public void onClick(View v) {
/* 26 */            VineRecorder recorder = getRecorder();
/* 27 */            Button child = getInflatedChild();
/* 28 */            if (recorder != null && child != null && recorder.canSwitchFlash()) {
/* 30 */                recorder.switchFlash();
/* 31 */                if (recorder.isFlashOn()) {
/* 32 */                    child.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
/* 33 */                    child.setAlpha(1.0f);
                        } else {
/* 35 */                    if (!this.mIsMessaging) {
/* 36 */                        child.getBackground().setColorFilter(null);
                            }
/* 38 */                    child.setAlpha(0.35f);
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 45 */        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
/* 46 */            super.onCameraReady(frontFacing, autoFocusSet);
/* 47 */            VineRecorder recorder = getRecorder();
/* 48 */            Button child = getInflatedChild();
/* 49 */            if (recorder != null && child != null) {
/* 50 */                child.setAlpha(recorder.canSwitchFlash() ? 0.35f : 0.175f);
/* 51 */                child.getBackground().setColorFilter(null);
                    }
                }

                @Override // co.vine.android.plugin.BaseToolPlugin
/* 56 */        protected Button onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
/* 57 */            Button button = (Button) inflater.inflate(C0257R.layout.plugin_tool_regular_button, (ViewGroup) view, false);
/* 58 */            button.setBackgroundResource(C0257R.drawable.ic_flash);
/* 59 */            button.setAlpha(0.35f);
/* 60 */            return button;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 64 */        public void setMessagingMode(boolean messagingMode) {
/* 65 */            super.setMessagingMode(messagingMode);
/* 66 */            this.mIsMessaging = messagingMode;
/* 67 */            if (messagingMode) {
/* 68 */                Button button = getInflatedChild();
/* 69 */                button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    }
                }
            }
