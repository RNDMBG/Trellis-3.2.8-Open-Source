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
            import co.vine.android.C0257R;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.util.ViewUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class CameraSwitcherPlugin extends BaseToolPlugin<Button> {

/* 22 */        public CameraSwitcherPlugin() {
/* 23 */            super("Camera Switcher");
                }

/* 28 */        public static class CameraSwitcherPluginTouchListener implements View.OnTouchListener {
                    private final int color;
                    private final Resources res;

/* 29 */            public CameraSwitcherPluginTouchListener(Resources res, int color) {
/* 31 */                this.res = res;
/* 32 */                this.color = color;
                    }

                    @Override // android.view.View.OnTouchListener
/* 36 */            public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
/* 39 */                        ViewUtil.fillColor(this.res, this.color, C0257R.drawable.ic_front_facing, v);
/* 41 */                        v.setAlpha(1.0f);
                                break;
                            case 1:
/* 45 */                        ViewUtil.fillColor(this.res, this.color, C0257R.drawable.ic_front_facing, v);
/* 47 */                        v.setAlpha(0.35f);
                                break;
                        }
/* 52 */                return false;
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // co.vine.android.plugin.BaseToolPlugin
/* 57 */        protected Button onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
/* 58 */            Button button = (Button) inflater.inflate(C0257R.layout.plugin_tool_regular_button, (ViewGroup) view, false);
/* 59 */            button.setBackgroundResource(C0257R.drawable.ic_front_facing);
/* 61 */            if (fragment instanceof RecorderPluginSupportedFragment) {
/* 62 */                int color = ((RecorderPluginSupportedFragment) fragment).getColor(true);
/* 63 */                Resources res = fragment.getResources();
/* 64 */                ViewUtil.fillColor(res, color, C0257R.drawable.ic_front_facing, button);
/* 65 */                button.setOnTouchListener(new CameraSwitcherPluginTouchListener(res, color));
                    } else {
/* 67 */                button.setOnTouchListener(BaseToolPlugin.createOnToolTouchedListener());
                    }
/* 69 */            button.setAlpha(0.35f);
/* 70 */            return button;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 74 */        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
/* 75 */            Button child = getInflatedChild();
/* 76 */            if (child != null) {
/* 77 */                child.setAlpha(frontFacing ? 1.0f : 0.35f);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 85 */        public void onResume(Activity activity) {
/* 86 */            super.onResume(activity);
/* 87 */            LinearLayout view = getView();
/* 88 */            BasicVineRecorder recorder = getRecorder();
/* 89 */            if (view != null && recorder != null) {
/* 90 */                if (!RecordConfigUtils.getGenericConfig(activity).cameraSwitchEnabled) {
/* 91 */                    view.setVisibility(8);
                        } else {
/* 93 */                    view.setOnClickListener(this);
                        }
/* 95 */                if (recorder.canSwitchCamera() && view.getVisibility() != 8) {
/* 97 */                    view.setVisibility(0);
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
/* 104 */       public void onClick(View v) {
/* 105 */           BasicVineRecorder recorder = getRecorder();
/* 106 */           if (recorder != null) {
/* 107 */               recorder.switchCamera();
                    }
                }
            }
