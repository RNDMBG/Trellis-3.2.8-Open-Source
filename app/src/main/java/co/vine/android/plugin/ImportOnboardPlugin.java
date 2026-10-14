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
            
            
    public class ImportOnboardPlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private ViewGroup mImportOnboardOverlay;
                private final Animation.AnimationListener mImportOnboardingFadeListener;
                private boolean mIsFadingOutAlready;
                private boolean mIsOnboarding;

        static  void access$000(ImportOnboardPlugin x0) {
            x0.fadeOutOnboardOverlay();
                }

        static  boolean access$102(ImportOnboardPlugin x0, boolean x1) {
            x0.mIsFadingOutAlready = x1;
                    return x1;
                }

        static  ViewGroup access$200(ImportOnboardPlugin x0) {
            return x0.mImportOnboardOverlay;
                }

        public ImportOnboardPlugin() {
            super("Import Onboard");
            this.mImportOnboardingFadeListener = new C04472();
                }

                @Override 
        public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
            this.mActivity = fragment.getActivity();
            this.mIsOnboarding = !CameraOnboardHelper.getHasShownOverlay(this.mActivity);
            if (this.mIsOnboarding) {
                ViewGroup rootView = (ViewGroup) fragment.getView().findViewById(C0257R.id.root_layout);
                this.mImportOnboardOverlay = (ViewGroup) inflater.inflate(C0257R.layout.plugin_import_onboard, rootView, false);
                rootView.addView(this.mImportOnboardOverlay);
                CameraOnboardHelper.setHasShownOverlay(this.mActivity, true);
                return null;
                    }
            return null;
                }

                @Override 
        public void onPause() {
            super.onPause();
            if (this.mIsOnboarding) {
                fadeOutOnboardOverlay();
                CameraOnboardHelper.setHasShownOverlay(this.mActivity, true);
                    }
                }

                @Override 
        public void onActivityCreated(Activity activity) {
            super.onActivityCreated(activity);
            if (this.mIsOnboarding) {
                this.mImportOnboardOverlay.setVisibility(0);
                this.mIsFadingOutAlready = false;
                this.mImportOnboardOverlay.setOnTouchListener(new ViewOnTouchListenerC04461());
                    }
                }

                
        class ViewOnTouchListenerC04461 implements View.OnTouchListener {
            ViewOnTouchListenerC04461() {
                    }

                    @Override 
            public boolean onTouch(View v, MotionEvent event) {
                ImportOnboardPlugin.access$000(ImportOnboardPlugin.this);
                return true;
                    }
                }

                @Override 
        public boolean onSetEditMode(boolean editing, boolean hasData) {
            if (editing) {
                fadeOutOnboardOverlay();
                    }
            return super.onSetEditMode(editing, hasData);
                }

        private void fadeOutOnboardOverlay() {
            if (this.mIsOnboarding && this.mImportOnboardOverlay != null && this.mImportOnboardOverlay.getVisibility() == 0 && !this.mIsFadingOutAlready) {
                if (this.mActivity != null) {
                    Animation fadeOut = AnimationUtils.loadAnimation(this.mActivity, C0257R.anim.fade_out);
                    fadeOut.setAnimationListener(this.mImportOnboardingFadeListener);
                    this.mImportOnboardOverlay.startAnimation(fadeOut);
                    return;
                        }
                this.mImportOnboardingFadeListener.onAnimationEnd(null);
                    }
                }

                
        class C04472 extends SimpleAnimationListener {
            C04472() {
                    }

                    @Override 
            public void onAnimationStart(Animation animation) {
                ImportOnboardPlugin.access$102(ImportOnboardPlugin.this, true);
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                if (ImportOnboardPlugin.access$200(ImportOnboardPlugin.this) != null) {
                    ImportOnboardPlugin.access$200(ImportOnboardPlugin.this).setVisibility(8);
                   ImportOnboardPlugin.access$200(ImportOnboardPlugin.this).setOnTouchListener(null);
                        }
                    }
                }

                @Override 
       public void onOnboardingStepFinished() {
           fadeOutOnboardOverlay();
                }
            }
