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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class SonyRecordingActivity extends AbstractRecordingActivity {
                private final PromptDialogFragment.OnDialogDoneListener mListener = new C02965();

                @Nullable
                private CapturingModeSelector mSonyCapturingModeSelector;
                private int mSonyFilterColor;
                private String mSonyModeName;
                private ImageView mSonyModeSelectorButton;
                private ViewGroup mSonySelectorContainer;

/* 20 */        static /* synthetic */ CapturingModeSelector access$000(SonyRecordingActivity x0) {
/* 21 */            return x0.mSonyCapturingModeSelector;
                }

/* 20 */        static /* synthetic */ void access$100(SonyRecordingActivity x0) {
/* 21 */            x0.showSonyOverlay();
                }

/* 20 */        static /* synthetic */ PromptDialogFragment.OnDialogDoneListener access$200(SonyRecordingActivity x0) {
/* 21 */            return x0.mListener;
                }

/* 20 */        static /* synthetic */ ImageView access$300(SonyRecordingActivity x0) {
/* 21 */            return x0.mSonyModeSelectorButton;
                }

/* 20 */        static /* synthetic */ int access$400(SonyRecordingActivity x0) {
/* 21 */            return x0.mSonyFilterColor;
                }

                @Override // co.vine.android.AbstractRecordingActivity, co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 31 */        public void onCreate(Bundle savedInstanceState) {
/* 32 */            super.onCreate(savedInstanceState);
/* 33 */            Resources res = getResources();
/* 34 */            this.mSonyFilterColor = res.getColor(C0257R.color.sony_filtered_color);
/* 35 */            this.mSonyModeName = res.getString(C0257R.string.app_name);
                }

                @Override // co.vine.android.AbstractRecordingActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 39 */        protected void onResume() {
/* 40 */            super.onResume();
/* 41 */            setupSonyOverlay();
                }

                @Override // co.vine.android.AbstractRecordingActivity, co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 45 */        public void onBackPressed() {
/* 46 */            if (this.mSonyCapturingModeSelector != null && this.mSonyCapturingModeSelector.isOpened()) {
/* 47 */                this.mSonyCapturingModeSelector.close();
/* 48 */                this.mSonyModeSelectorButton.setVisibility(0);
/* 49 */                onHideSonyOverlay();
/* 53 */                return;
                    }
/* 51 */            super.onBackPressed();
                }

                @Override // co.vine.android.AbstractRecordingActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 57 */        protected void onPause() {
/* 58 */            if (this.mSonyCapturingModeSelector != null) {
/* 59 */                this.mSonyCapturingModeSelector.release();
/* 60 */                this.mSonyCapturingModeSelector = null;
                    }
/* 62 */            super.onPause();
                }

                @Override // co.vine.android.AbstractRecordingActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 66 */        protected void onDestroy() {
/* 67 */            if (this.mSonyModeSelectorButton != null) {
/* 68 */                this.mSonyModeSelectorButton.setOnClickListener(null);
/* 69 */                this.mSonyModeSelectorButton.setOnTouchListener(null);
                    }
/* 71 */            super.onDestroy();
                }

                @Override // co.vine.android.AbstractRecordingActivity
/* 75 */        public boolean isFromSony() {
/* 76 */            return true;
                }

/* 79 */        public void setupSonyOverlay() {
/* 80 */            this.mSonyModeSelectorButton = (ImageView) findViewById(C0257R.id.sony_button);
/* 82 */            if (this.mSonyModeSelectorButton != null) {
/* 86 */                ViewGroup vg = (ViewGroup) findViewById(C0257R.id.root_layout);
/* 87 */                if (vg != null && this.mSonySelectorContainer == null) {
/* 88 */                    this.mSonySelectorContainer = (ViewGroup) getLayoutInflater().inflate(C0257R.layout.sony_container, vg, false);
/* 89 */                    vg.addView(this.mSonySelectorContainer);
                        }
/* 92 */                this.mSonyModeSelectorButton.setVisibility(0);
/* 93 */                this.mSonyModeSelectorButton.setOnClickListener(new ViewOnClickListenerC02921());
/* 115 */               this.mSonyModeSelectorButton.setOnTouchListener(new ViewOnTouchListenerC02932());
                        try {
/* 131 */                   this.mSonyCapturingModeSelector = new CapturingModeSelector(this, this.mSonySelectorContainer);
                        } catch (IllegalAccessError | NoClassDefFoundError e) {
/* 135 */                   CrashUtil.logException(new VineLoggingException("Unable to create Sony Capture Mode selector."));
                        }
/* 139 */               if (this.mSonyCapturingModeSelector != null) {
/* 141 */                   this.mSonyCapturingModeSelector.setOnModeSelectListener(new C02943());
/* 151 */                   this.mSonyCapturingModeSelector.setOnModeFinishListener(new C02954());
/* 161 */                   this.mSonyModeSelectorButton.setVisibility(0);
/* 167 */                   return;
                        }
/* 165 */               this.mSonyModeSelectorButton.setVisibility(8);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SonyRecordingActivity$1 */
/* 91 */        class ViewOnClickListenerC02921 implements View.OnClickListener {
/* 92 */            ViewOnClickListenerC02921() {
                    }

                    @Override // android.view.View.OnClickListener
/* 95 */            public void onClick(View v) {
                        RecordingFragment fragment;
/* 96 */                if (SonyRecordingActivity.access$000(SonyRecordingActivity.this) != null && (fragment = (RecordingFragment) SonyRecordingActivity.this.mCurrentFragment) != null) {
/* 99 */                    boolean isEditing = fragment.isEditing();
/* 100 */                   if (!isEditing && !fragment.isSessionModified()) {
/* 101 */                       fragment.setDiscardChangesOnStop();
/* 102 */                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                            } else if (isEditing && !fragment.isEditingDirty()) {
/* 105 */                       fragment.discardEditing();
/* 106 */                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                            } else {
/* 108 */                       SonyRecordingActivity.this.showUnSavedChangesToSessionDialog(SonyRecordingActivity.access$200(SonyRecordingActivity.this));
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SonyRecordingActivity$2 */
/* 113 */       class ViewOnTouchListenerC02932 implements View.OnTouchListener {
/* 114 */           ViewOnTouchListenerC02932() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 117 */           public boolean onTouch(View v, MotionEvent event) {
/* 118 */               SonyRecordingActivity.access$300(SonyRecordingActivity.this).onTouchEvent(event);
/* 119 */               if (SonyRecordingActivity.access$300(SonyRecordingActivity.this).isPressed()) {
/* 120 */                   SonyRecordingActivity.access$300(SonyRecordingActivity.this).setColorFilter(SonyRecordingActivity.access$400(SonyRecordingActivity.this));
/* 124 */                   return true;
                        }
/* 122 */               SonyRecordingActivity.access$300(SonyRecordingActivity.this).clearColorFilter();
/* 124 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SonyRecordingActivity$3 */
/* 140 */       class C02943 implements CapturingModeSelector.OnModeSelectListener {
/* 141 */           C02943() {
                    }

/* 144 */           public void onModeSelect(String s) {
/* 145 */               if (SonyRecordingActivity.access$000(SonyRecordingActivity.this) != null) {
/* 146 */                   SonyRecordingActivity.access$000(SonyRecordingActivity.this).close();
                        }
/* 148 */               SonyRecordingActivity.this.finish();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SonyRecordingActivity$4 */
/* 150 */       class C02954 implements CapturingModeSelector.OnModeFinishListener {
/* 151 */           C02954() {
                    }

/* 154 */           public void onModeFinish() {
/* 155 */               if (SonyRecordingActivity.access$000(SonyRecordingActivity.this) != null) {
/* 156 */                   SonyRecordingActivity.access$000(SonyRecordingActivity.this).close();
/* 157 */                   SonyRecordingActivity.access$300(SonyRecordingActivity.this).setVisibility(0);
                        }
                    }
                }

/* 173 */       private void showSonyOverlay() {
                    try {
/* 174 */               if (this.mSonyCapturingModeSelector != null) {
/* 175 */                   this.mSonyCapturingModeSelector.open(this.mSonyModeName);
                        }
/* 177 */               if (this.mSonyModeSelectorButton != null) {
/* 178 */                   this.mSonyModeSelectorButton.setVisibility(4);
/* 179 */                   this.mSonyModeSelectorButton.clearColorFilter();
                        }
/* 181 */               onShowSonyOverlay();
                    } catch (IllegalAccessError | NoClassDefFoundError e) {
/* 183 */               Util.showCenteredToast(this, C0257R.string.unsupported_feature);
/* 184 */               CrashUtil.logOrThrowInDebug(e);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SonyRecordingActivity$5 */
/* 186 */       class C02965 implements PromptDialogFragment.OnDialogDoneListener {
/* 187 */           C02965() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 190 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -3:
                            case -2:
/* 194 */                       if (SonyRecordingActivity.this.currentlyHoldsRecordingFragment()) {
/* 195 */                           ((RecordingFragment) SonyRecordingActivity.this.mCurrentFragment).setDiscardChangesOnStop();
                                }
/* 197 */                       SonyRecordingActivity.this.discardUpload();
/* 198 */                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                                break;
                            default:
/* 202 */                       if (SonyRecordingActivity.this.currentlyHoldsRecordingFragment()) {
/* 203 */                           ((RecordingFragment) SonyRecordingActivity.this.mCurrentFragment).saveSession();
                                }
/* 205 */                       SonyRecordingActivity.access$100(SonyRecordingActivity.this);
                                break;
                        }
                    }
                }

                @Override // co.vine.android.AbstractRecordingActivity
/* 213 */       protected RecordingFragment createRecordingFragment() {
/* 214 */           return new SonyRecordingFragment();
                }

/* 217 */       private void onHideSonyOverlay() {
/* 218 */           View recordingOptions = findViewById(C0257R.id.recording_options);
/* 219 */           View captureX = findViewById(C0257R.id.capture_x);
/* 220 */           View progressBar = findViewById(C0257R.id.progress);
/* 222 */           if (recordingOptions != null) {
/* 223 */               recordingOptions.setVisibility(0);
                    }
/* 225 */           if (captureX != null) {
/* 226 */               captureX.setVisibility(0);
                    }
/* 228 */           if (progressBar != null) {
/* 229 */               progressBar.setVisibility(0);
                    }
                }

/* 233 */       private void onShowSonyOverlay() {
/* 234 */           View recordingOptions = findViewById(C0257R.id.recording_options);
/* 235 */           View captureX = findViewById(C0257R.id.capture_x);
/* 236 */           View progressBar = findViewById(C0257R.id.progress);
/* 238 */           if (recordingOptions != null) {
/* 239 */               recordingOptions.setVisibility(4);
                    }
/* 241 */           if (captureX != null) {
/* 242 */               captureX.setVisibility(4);
                    }
/* 244 */           if (progressBar != null) {
/* 245 */               progressBar.setVisibility(4);
                    }
                }
            }
