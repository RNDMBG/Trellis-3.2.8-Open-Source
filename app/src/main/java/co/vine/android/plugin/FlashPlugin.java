            package co.vine.android.plugin;

            import android.graphics.PorterDuff;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.LinearLayout;
            import co.vine.android.R;
            import co.vine.android.recorder.VineRecorder;
            
            
    public class FlashPlugin extends BaseToolPlugin<Button> {
                private boolean mIsMessaging;


        protected FlashPlugin() {
            super("Flash");
            this.mIsMessaging = false;
                }

                @Override 
        public void onClick(View v) {
            VineRecorder recorder = getRecorder();
            Button child = getInflatedChild();
            if (recorder != null && child != null && recorder.canSwitchFlash()) {
                recorder.switchFlash();
                if (recorder.isFlashOn()) {
                    child.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    child.setAlpha(1.0f);
                        } else {
                    if (!this.mIsMessaging) {
                        child.getBackground().setColorFilter(null);
                            }
                    child.setAlpha(0.35f);
                        }
                    }
                }

                @Override 
        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
            super.onCameraReady(frontFacing, autoFocusSet);
            VineRecorder recorder = getRecorder();
            Button child = getInflatedChild();
            if (recorder != null && child != null) {
                child.setAlpha(recorder.canSwitchFlash() ? 0.35f : 0.175f);
                child.getBackground().setColorFilter(null);
                    }
                }

                @Override 
        protected Button onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
            Button button = (Button) inflater.inflate(R.layout.plugin_tool_regular_button, (ViewGroup) view, false);
            button.setBackgroundResource(R.drawable.ic_flash);
            button.setAlpha(0.35f);
            return button;
                }

                @Override 
        public void setMessagingMode(boolean messagingMode) {
            super.setMessagingMode(messagingMode);
            this.mIsMessaging = messagingMode;
            if (messagingMode) {
                Button button = getInflatedChild();
                button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    }
                }
            }
