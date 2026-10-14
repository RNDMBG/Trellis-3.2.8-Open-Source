            package co.vine.android.plugin;

            import android.app.Activity;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.AnimationUtils;
            import co.vine.android.C0257R;
            import co.vine.android.CameraOnboardHelper;
            import co.vine.android.animation.SimpleAnimationListener;
            import co.vine.android.recorder.VineRecorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class ImportOnboardPlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private ViewGroup mImportOnboardOverlay;
                private final Animation.AnimationListener mImportOnboardingFadeListener;
                private boolean mIsFadingOutAlready;
                private boolean mIsOnboarding;

/* 19 */        static /* synthetic */ void access$000(ImportOnboardPlugin x0) {
/* 20 */            x0.fadeOutOnboardOverlay();
                }

/* 19 */        static /* synthetic */ boolean access$102(ImportOnboardPlugin x0, boolean x1) {
/* 20 */            x0.mIsFadingOutAlready = x1;
                    return x1;
                }

/* 19 */        static /* synthetic */ ViewGroup access$200(ImportOnboardPlugin x0) {
/* 20 */            return x0.mImportOnboardOverlay;
                }

/* 26 */        public ImportOnboardPlugin() {
/* 27 */            super("Import Onboard");
/* 90 */            this.mImportOnboardingFadeListener = new C04472();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 31 */        public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 32 */            this.mActivity = fragment.getActivity();
/* 33 */            this.mIsOnboarding = !CameraOnboardHelper.getHasShownOverlay(this.mActivity);
/* 34 */            if (this.mIsOnboarding) {
/* 35 */                ViewGroup rootView = (ViewGroup) fragment.getView().findViewById(C0257R.id.root_layout);
/* 36 */                this.mImportOnboardOverlay = (ViewGroup) inflater.inflate(C0257R.layout.plugin_import_onboard, rootView, false);
/* 37 */                rootView.addView(this.mImportOnboardOverlay);
/* 38 */                CameraOnboardHelper.setHasShownOverlay(this.mActivity, true);
/* 40 */                return null;
                    }
/* 40 */            return null;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 44 */        public void onPause() {
/* 45 */            super.onPause();
/* 46 */            if (this.mIsOnboarding) {
/* 47 */                fadeOutOnboardOverlay();
/* 48 */                CameraOnboardHelper.setHasShownOverlay(this.mActivity, true);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 53 */        public void onActivityCreated(Activity activity) {
/* 54 */            super.onActivityCreated(activity);
/* 55 */            if (this.mIsOnboarding) {
/* 56 */                this.mImportOnboardOverlay.setVisibility(0);
/* 57 */                this.mIsFadingOutAlready = false;
/* 58 */                this.mImportOnboardOverlay.setOnTouchListener(new ViewOnTouchListenerC04461());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.ImportOnboardPlugin$1 */
/* 56 */        class ViewOnTouchListenerC04461 implements View.OnTouchListener {
/* 57 */            ViewOnTouchListenerC04461() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 60 */            public boolean onTouch(View v, MotionEvent event) {
/* 61 */                ImportOnboardPlugin.access$000(ImportOnboardPlugin.this);
/* 62 */                return true;
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 69 */        public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 70 */            if (editing) {
/* 71 */                fadeOutOnboardOverlay();
                    }
/* 73 */            return super.onSetEditMode(editing, hasData);
                }

/* 76 */        private void fadeOutOnboardOverlay() {
/* 77 */            if (this.mIsOnboarding && this.mImportOnboardOverlay != null && this.mImportOnboardOverlay.getVisibility() == 0 && !this.mIsFadingOutAlready) {
/* 80 */                if (this.mActivity != null) {
/* 81 */                    Animation fadeOut = AnimationUtils.loadAnimation(this.mActivity, C0257R.anim.fade_out);
/* 82 */                    fadeOut.setAnimationListener(this.mImportOnboardingFadeListener);
/* 83 */                    this.mImportOnboardOverlay.startAnimation(fadeOut);
/* 88 */                    return;
                        }
/* 85 */                this.mImportOnboardingFadeListener.onAnimationEnd(null);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.ImportOnboardPlugin$2 */
/* 88 */        class C04472 extends SimpleAnimationListener {
/* 89 */            C04472() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 92 */            public void onAnimationStart(Animation animation) {
/* 93 */                ImportOnboardPlugin.access$102(ImportOnboardPlugin.this, true);
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 97 */            public void onAnimationEnd(Animation animation) {
/* 98 */                if (ImportOnboardPlugin.access$200(ImportOnboardPlugin.this) != null) {
/* 99 */                    ImportOnboardPlugin.access$200(ImportOnboardPlugin.this).setVisibility(8);
/* 100 */                   ImportOnboardPlugin.access$200(ImportOnboardPlugin.this).setOnTouchListener(null);
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 106 */       public void onOnboardingStepFinished() {
/* 107 */           fadeOutOnboardOverlay();
                }
            }
