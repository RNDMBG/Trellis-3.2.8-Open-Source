            package co.vine.android.plugin;

            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.LinearLayout;
            import co.vine.android.C0257R;
            import co.vine.android.recorder.VineRecorder;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public abstract class BaseToolPlugin<T extends View> extends BaseRecorderPlugin<LinearLayout, VineRecorder> implements View.OnClickListener {
                public static final float DISABLED_ALPHA = 0.35f;
                public static final float ENABLED_ALPHA = 1.0f;
                public static final float SUPER_DISABLED_ALPHA = 0.175f;
                private WeakReference<T> mInflatedChild;
                protected int mPrimaryColor;
                protected int mSecondaryColor;
                protected boolean mShouldOnboard;

                protected abstract T onLayoutInflated(LinearLayout linearLayout, LayoutInflater layoutInflater, Fragment fragment);


/* 29 */        BaseToolPlugin(String name) {
/* 30 */            super(name);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 34 */        public LinearLayout onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 35 */            if (fragment instanceof RecorderPluginSupportedFragment) {
/* 36 */                this.mPrimaryColor = ((RecorderPluginSupportedFragment) fragment).getColor(true);
/* 37 */                this.mSecondaryColor = ((RecorderPluginSupportedFragment) fragment).getColor(false);
                    }
/* 39 */            LinearLayout view = (LinearLayout) inflater.inflate(C0257R.layout.plugin_base_tool, parent, false);
/* 40 */            view.setOnClickListener(this);
/* 41 */            T viewOnLayoutInflated = onLayoutInflated(view, inflater, fragment);
/* 42 */            if (viewOnLayoutInflated != null) {
/* 43 */                viewOnLayoutInflated.setOnClickListener(this);
/* 44 */                view.addView(viewOnLayoutInflated);
/* 45 */                this.mInflatedChild = new WeakReference<>(viewOnLayoutInflated);
                    }
/* 47 */            return view;
                }

/* 50 */        public T getInflatedChild() {
/* 52 */            if (this.mInflatedChild == null) {
/* 51 */                return null;
                    }
/* 53 */            T child = this.mInflatedChild.get();
                    return child;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.BaseToolPlugin$1 */
/* 57 */        static class ViewOnTouchListenerC04171 implements View.OnTouchListener {
/* 58 */            ViewOnTouchListenerC04171() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 61 */            public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
/* 64 */                        view.setAlpha(1.0f);
                                break;
                            case 1:
/* 68 */                        view.setAlpha(0.35f);
                                break;
                        }
/* 72 */                return false;
                    }
                }

/* 58 */        public static View.OnTouchListener createOnToolTouchedListener() {
/* 59 */            return new ViewOnTouchListenerC04171();
                }
            }
