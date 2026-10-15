            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.res.Resources;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.LinearLayout;
            import co.vine.android.R;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.util.ViewUtil;
            
            
    public class CameraSwitcherPlugin extends BaseToolPlugin<Button> {

        public CameraSwitcherPlugin() {
            super("Camera Switcher");
                }

        public static class CameraSwitcherPluginTouchListener implements View.OnTouchListener {
                    private final int color;
                    private final Resources res;

            public CameraSwitcherPluginTouchListener(Resources res, int color) {
                this.res = res;
                this.color = color;
                    }

                    @Override 
            public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
                        ViewUtil.fillColor(this.res, this.color, R.drawable.ic_front_facing, v);
                        v.setAlpha(1.0f);
                                break;
                            case 1:
                        ViewUtil.fillColor(this.res, this.color, R.drawable.ic_front_facing, v);
                        v.setAlpha(0.35f);
                                break;
                        }
                return false;
                    }
                }

                
                @Override 
        protected Button onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
            Button button = (Button) inflater.inflate(R.layout.plugin_tool_regular_button, (ViewGroup) view, false);
            button.setBackgroundResource(R.drawable.ic_front_facing);
            if (fragment instanceof RecorderPluginSupportedFragment) {
                int color = ((RecorderPluginSupportedFragment) fragment).getColor(true);
                Resources res = fragment.getResources();
                ViewUtil.fillColor(res, color, R.drawable.ic_front_facing, button);
                button.setOnTouchListener(new CameraSwitcherPluginTouchListener(res, color));
                    } else {
                button.setOnTouchListener(BaseToolPlugin.createOnToolTouchedListener());
                    }
            button.setAlpha(0.35f);
            return button;
                }

                @Override 
        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
            Button child = getInflatedChild();
            if (child != null) {
                child.setAlpha(frontFacing ? 1.0f : 0.35f);
                    }
                }

                @Override 
        public void onResume(Activity activity) {
            super.onResume(activity);
            LinearLayout view = getView();
            BasicVineRecorder recorder = getRecorder();
            if (view != null && recorder != null) {
                if (!RecordConfigUtils.getGenericConfig(activity).cameraSwitchEnabled) {
                    view.setVisibility(8);
                        } else {
                    view.setOnClickListener(this);
                        }
                if (recorder.canSwitchCamera() && view.getVisibility() != 8) {
                    view.setVisibility(0);
                        }
                    }
                }

                @Override 
       public void onClick(View v) {
           BasicVineRecorder recorder = getRecorder();
           if (recorder != null) {
               recorder.switchCamera();
                    }
                }
            }
