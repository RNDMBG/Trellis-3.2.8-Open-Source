            package co.vine.android;

            import android.content.DialogInterface;
            import android.content.res.Resources;
            import android.os.Bundle;
            import android.support.annotation.Nullable;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.widgets.PromptDialogFragment;
            import com.sonymobile.camera.addon.capturingmode.CapturingModeSelector;
            
            
    public class SonyRecordingActivity extends AbstractRecordingActivity {
                private final PromptDialogFragment.OnDialogDoneListener mListener = new C02965();

                @Nullable
                private CapturingModeSelector mSonyCapturingModeSelector;
                private int mSonyFilterColor;
                private String mSonyModeName;
                private ImageView mSonyModeSelectorButton;
                private ViewGroup mSonySelectorContainer;

        static  CapturingModeSelector access$000(SonyRecordingActivity x0) {
            return x0.mSonyCapturingModeSelector;
                }

        static  void access$100(SonyRecordingActivity x0) {
            x0.showSonyOverlay();
                }

        static  PromptDialogFragment.OnDialogDoneListener access$200(SonyRecordingActivity x0) {
            return x0.mListener;
                }

        static  ImageView access$300(SonyRecordingActivity x0) {
            return x0.mSonyModeSelectorButton;
                }

        static  int access$400(SonyRecordingActivity x0) {
            return x0.mSonyFilterColor;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Resources res = getResources();
            this.mSonyFilterColor = res.getColor(C0257R.color.sony_filtered_color);
            this.mSonyModeName = res.getString(C0257R.string.app_name);
                }

                @Override 
        protected void onResume() {
            super.onResume();
            setupSonyOverlay();
                }

                @Override 
        public void onBackPressed() {
            if (this.mSonyCapturingModeSelector != null && this.mSonyCapturingModeSelector.isOpened()) {
                this.mSonyCapturingModeSelector.close();
                this.mSonyModeSelectorButton.setVisibility(0);
                onHideSonyOverlay();
                return;
                    }
            super.onBackPressed();
                }

                @Override 
        protected void onPause() {
            if (this.mSonyCapturingModeSelector != null) {
                this.mSonyCapturingModeSelector.release();
                this.mSonyCapturingModeSelector = null;
                    }
            super.onPause();
                }

                @Override 
        protected void onDestroy() {
            if (this.mSonyModeSelectorButton != null) {
                this.mSonyModeSelectorButton.setOnClickListener(null);
                this.mSonyModeSelectorButton.setOnTouchListener(null);
                    }
            super.onDestroy();
                }

                @Override 
        public boolean isFromSony() {
            return true;
                }

        public void setupSonyOverlay() {
            this.mSonyModeSelectorButton = (ImageView) findViewById(C0257R.id.sony_button);
            if (this.mSonyModeSelectorButton != null) {
                ViewGroup vg = (ViewGroup) findViewById(C0257R.id.root_layout);
                if (vg != null && this.mSonySelectorContainer == null) {
                    this.mSonySelectorContainer = (ViewGroup) getLayoutInflater().inflate(C0257R.layout.sony_container, vg, false);
                    vg.addView(this.mSonySelectorContainer);
                        }
                this.mSonyModeSelectorButton.setVisibility(0);
                this.mSonyModeSelectorButton.setOnClickListener(new ViewOnClickListenerC02921());
               this.mSonyModeSelectorButton.setOnTouchListener(new ViewOnTouchListenerC02932());
                        try {
                   this.mSonyCapturingModeSelector = new CapturingModeSelector(this, this.mSonySelectorContainer);
                        } catch (IllegalAccessError | NoClassDefFoundError e) {
                   CrashUtil.logException(new VineLoggingException("Unable to create Sony Capture Mode selector."));
                        }
               if (this.mSonyCapturingModeSelector != null) {
                   this.mSonyCapturingModeSelector.setOnModeSelectListener(new C02943());
                   this.mSonyCapturingModeSelector.setOnModeFinishListener(new C02954());
                   this.mSonyModeSelectorButton.setVisibility(0);
                   return;
                        }
               this.mSonyModeSelectorButton.setVisibility(8);
                    }
                }

                
        class ViewOnClickListenerC02921 implements View.OnClickListener {
            ViewOnClickListenerC02921() {
                    }

                    @Override 
            public void onClick(View v) {
                        RecordingFragment fragment;
                if (SonyRecordingActivity.access$000(SonyRecordingActivity.this) != null && (fragment = (RecordingFragment) SonyRecordingActivity.this.mCurrentFragment) != null) {
                    boolean isEditing = fragment.isEditing();
                   if (!isEditing && !fragment.isSessionModified()) {
                       fragment.setDiscardChangesOnStop();
                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                            } else if (isEditing && !fragment.isEditingDirty()) {
                       fragment.discardEditing();
                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                            } else {
                       SonyRecordingActivity.this.showUnSavedChangesToSessionDialog(SonyRecordingActivity.access$200(SonyRecordingActivity.this));
                            }
                        }
                    }
                }

                
       class ViewOnTouchListenerC02932 implements View.OnTouchListener {
           ViewOnTouchListenerC02932() {
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
               SonyRecordingActivity.access$300(SonyRecordingActivity.this).onTouchEvent(event);
               if (SonyRecordingActivity.access$300(SonyRecordingActivity.this).isPressed()) {
                   SonyRecordingActivity.access$300(SonyRecordingActivity.this).setColorFilter(SonyRecordingActivity.access$400(SonyRecordingActivity.this));
                   return true;
                        }
               SonyRecordingActivity.access$300(SonyRecordingActivity.this).clearColorFilter();
               return true;
                    }
                }

                
       class C02943 implements CapturingModeSelector.OnModeSelectListener {
           C02943() {
                    }

           public void onModeSelect(String s) {
               if (SonyRecordingActivity.access$000(SonyRecordingActivity.this) != null) {
                   SonyRecordingActivity.access$000(SonyRecordingActivity.this).close();
                        }
               SonyRecordingActivity.this.finish();
                    }
                }

                
       class C02954 implements CapturingModeSelector.OnModeFinishListener {
           C02954() {
                    }

           public void onModeFinish() {
               if (SonyRecordingActivity.access$000(SonyRecordingActivity.this) != null) {
                   SonyRecordingActivity.access$000(SonyRecordingActivity.this).close();
                   SonyRecordingActivity.access$300(SonyRecordingActivity.this).setVisibility(0);
                        }
                    }
                }

       private void showSonyOverlay() {
                    try {
               if (this.mSonyCapturingModeSelector != null) {
                   this.mSonyCapturingModeSelector.open(this.mSonyModeName);
                        }
               if (this.mSonyModeSelectorButton != null) {
                   this.mSonyModeSelectorButton.setVisibility(4);
                   this.mSonyModeSelectorButton.clearColorFilter();
                        }
               onShowSonyOverlay();
                    } catch (IllegalAccessError | NoClassDefFoundError e) {
               Util.showCenteredToast(this, C0257R.string.unsupported_feature);
               CrashUtil.logOrThrowInDebug(e);
                    }
                }

                
       class C02965 implements PromptDialogFragment.OnDialogDoneListener {
           C02965() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -3:
                            case -2:
                       if (SonyRecordingActivity.this.currentlyHoldsRecordingFragment()) {
                           ((RecordingFragment) SonyRecordingActivity.this.mCurrentFragment).setDiscardChangesOnStop();
                                }
                       SonyRecordingActivity.this.discardUpload();
                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                                break;
                            default:
                       if (SonyRecordingActivity.this.currentlyHoldsRecordingFragment()) {
                           ((RecordingFragment) SonyRecordingActivity.this.mCurrentFragment).saveSession();
                                }
                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                                break;
                        }
                    }
                }

                @Override 
       protected RecordingFragment createRecordingFragment() {
           return new SonyRecordingFragment();
                }

       private void onHideSonyOverlay() {
           View recordingOptions = findViewById(C0257R.id.recording_options);
           View captureX = findViewById(C0257R.id.capture_x);
           View progressBar = findViewById(C0257R.id.progress);
           if (recordingOptions != null) {
               recordingOptions.setVisibility(0);
                    }
           if (captureX != null) {
               captureX.setVisibility(0);
                    }
           if (progressBar != null) {
               progressBar.setVisibility(0);
                    }
                }

       private void onShowSonyOverlay() {
           View recordingOptions = findViewById(C0257R.id.recording_options);
           View captureX = findViewById(C0257R.id.capture_x);
           View progressBar = findViewById(C0257R.id.progress);
           if (recordingOptions != null) {
               recordingOptions.setVisibility(4);
                    }
           if (captureX != null) {
               captureX.setVisibility(4);
                    }
           if (progressBar != null) {
               progressBar.setVisibility(4);
                    }
                }
            }
