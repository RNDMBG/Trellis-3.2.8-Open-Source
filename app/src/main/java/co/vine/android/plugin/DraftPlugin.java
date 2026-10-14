            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.DialogInterface;
            import android.content.res.Resources;
            import android.os.Handler;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.AnimationUtils;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.AbstractRecordingActivity;
            import co.vine.android.C0257R;
            import co.vine.android.RecordingFragment;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordSessionManager;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MediaUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widgets.PromptDialogFragment;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class DraftPlugin extends BaseRecorderPlugin<View, VineRecorder> {
                private final PromptDialogFragment.OnDialogDoneListener mDirtyDialogDoneListener;
                private View mDraftOverlayIcon;
                private int mDraftUpgradeCount;
                private TextView mDraftsButton;
                private Animation mFadeIn;
                private final Animation.AnimationListener mFadeInListener;
                private Animation mFadeOut;
                private final Animation.AnimationListener mFadeOutListener;
                private boolean mIsDraftShowing;
                private boolean mIsDraftUpgradeAnimationRunning;
                private boolean mIsEditing;
                private View mNoDraftsOverlay;
                private int mNumDrafts;
                final View.OnTouchListener mOnNoDraftOverlayTouchListener;
                private View mRecordingOptions;
                private final Runnable mStartDraftsRunnable;
                private ProgressBar mUpgradeSpinner;

/* 37 */        static /* synthetic */ View access$000(DraftPlugin x0) {
/* 38 */            return x0.mDraftOverlayIcon;
                }

/* 37 */        static /* synthetic */ TextView access$100(DraftPlugin x0) {
/* 38 */            return x0.mDraftsButton;
                }

/* 37 */        static /* synthetic */ View access$200(DraftPlugin x0) {
/* 38 */            return x0.mNoDraftsOverlay;
                }

/* 37 */        static /* synthetic */ Animation.AnimationListener access$300(DraftPlugin x0) {
/* 38 */            return x0.mFadeOutListener;
                }

/* 37 */        static /* synthetic */ Animation access$400(DraftPlugin x0) {
/* 38 */            return x0.mFadeOut;
                }

/* 37 */        static /* synthetic */ void access$500(DraftPlugin x0, boolean x1) {
/* 38 */            x0.startDrafts(x1);
                }

/* 37 */        static /* synthetic */ boolean access$602(DraftPlugin x0, boolean x1) {
/* 38 */            x0.mIsDraftShowing = x1;
                    return x1;
                }

/* 37 */        static /* synthetic */ boolean access$700(DraftPlugin x0) {
/* 38 */            return x0.mIsDraftUpgradeAnimationRunning;
                }

/* 37 */        static /* synthetic */ boolean access$702(DraftPlugin x0, boolean x1) {
/* 38 */            x0.mIsDraftUpgradeAnimationRunning = x1;
                    return x1;
                }

/* 37 */        static /* synthetic */ int access$800(DraftPlugin x0) {
/* 38 */            return x0.mDraftUpgradeCount;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DraftPlugin$1 */
/* 52 */        class AnimationAnimationListenerC04251 implements Animation.AnimationListener {
/* 53 */            AnimationAnimationListenerC04251() {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 56 */            public void onAnimationStart(Animation animation) {
/* 57 */                DraftPlugin.access$000(DraftPlugin.this).setVisibility(8);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 61 */            public void onAnimationEnd(Animation animation) {
/* 62 */                DraftPlugin.access$100(DraftPlugin.this).setAlpha(0.35f);
/* 63 */                DraftPlugin.access$200(DraftPlugin.this).setVisibility(8);
/* 64 */                DraftPlugin.access$200(DraftPlugin.this).setOnTouchListener(null);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 69 */            public void onAnimationRepeat(Animation animation) {
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 74 */        public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 75 */            this.mIsEditing = editing;
/* 76 */            return this.mIsDraftShowing;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DraftPlugin$2 */
/* 77 */        class AnimationAnimationListenerC04262 implements Animation.AnimationListener {
/* 78 */            AnimationAnimationListenerC04262() {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 81 */            public void onAnimationStart(Animation animation) {
/* 82 */                DraftPlugin.access$200(DraftPlugin.this).setVisibility(0);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 86 */            public void onAnimationEnd(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 91 */            public void onAnimationRepeat(Animation animation) {
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DraftPlugin$3 */
/* 93 */        class ViewOnTouchListenerC04273 implements View.OnTouchListener {
/* 94 */            ViewOnTouchListenerC04273() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 96 */            public boolean onTouch(View v, MotionEvent event) {
/* 97 */                DraftPlugin.access$400(DraftPlugin.this).setAnimationListener(DraftPlugin.access$300(DraftPlugin.this));
/* 98 */                DraftPlugin.access$200(DraftPlugin.this).startAnimation(DraftPlugin.access$400(DraftPlugin.this));
/* 99 */                DraftPlugin.access$100(DraftPlugin.this).setAlpha(0.35f);
/* 100 */               return true;
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 105 */       public void onResume(Activity activity) {
/* 106 */           super.onResume(activity);
/* 107 */           if ((activity instanceof AbstractRecordingActivity) && !((AbstractRecordingActivity) activity).isDraftsShowing()) {
/* 109 */               refreshDraftNumber();
                    }
                }

/* 114 */       public DraftPlugin() {
/* 115 */           super("draft");
/* 54 */            this.mFadeOutListener = new AnimationAnimationListenerC04251();
/* 79 */            this.mFadeInListener = new AnimationAnimationListenerC04262();
/* 95 */            this.mOnNoDraftOverlayTouchListener = new ViewOnTouchListenerC04273();
/* 261 */           this.mDirtyDialogDoneListener = new C04295();
/* 281 */           this.mStartDraftsRunnable = new RunnableC04306();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 119 */       public void onDraftUpgradeNumberChanged(int count) {
/* 120 */           SLog.m447d("Draft upgrade number changed with count {}", Integer.valueOf(count));
/* 121 */           this.mDraftUpgradeCount = count;
/* 122 */           if (this.mIsDraftUpgradeAnimationRunning) {
/* 123 */               if (count > 0) {
/* 124 */                   this.mUpgradeSpinner.setVisibility(0);
/* 125 */                   this.mDraftsButton.setVisibility(8);
                        } else {
/* 127 */                   this.mUpgradeSpinner.setVisibility(8);
/* 128 */                   this.mDraftsButton.setVisibility(0);
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 134 */       public void onHideDrafts() {
/* 135 */           this.mIsDraftShowing = false;
/* 136 */           refreshDraftNumber();
/* 137 */           this.mRecordingOptions.setAlpha(0.0f);
/* 138 */           this.mRecordingOptions.setVisibility(0);
/* 139 */           this.mRecordingOptions.animate().alpha(1.0f).start();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 143 */       public void onShowDrafts() {
/* 144 */           this.mIsDraftShowing = true;
/* 145 */           this.mRecordingOptions.setVisibility(8);
                }

/* 149 */       private void refreshDraftNumber() {
                    try {
/* 150 */               BasicVineRecorder recorder = getRecorder();
/* 151 */               if (recorder != null && recorder.hasSessionFile()) {
/* 152 */                   int numDrafts = RecordSessionManager.getNumberOfValidSessions(recorder);
/* 153 */                   if (numDrafts > 0) {
/* 154 */                       if (numDrafts > 9) {
/* 155 */                           numDrafts--;
                                }
/* 157 */                       this.mDraftsButton.setText(String.valueOf(numDrafts));
/* 158 */                       this.mDraftsButton.setBackgroundResource(C0257R.drawable.ic_drafts);
                            } else {
/* 160 */                       this.mDraftsButton.setText("");
/* 161 */                       this.mDraftsButton.setBackgroundResource(C0257R.drawable.ic_save);
                            }
/* 163 */                   this.mNumDrafts = numDrafts;
                        }
                    } catch (IOException e) {
/* 166 */               CrashUtil.logException(e);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 171 */       public void setMessagingMode(boolean isMessaging) {
                    View view;
/* 172 */           if (isMessaging && (view = getView()) != null) {
/* 175 */               view.setVisibility(8);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 181 */       public boolean canEdit() {
                    return !this.mIsDraftShowing;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 186 */       public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 187 */           this.mFadeIn = AnimationUtils.loadAnimation(fragment.getActivity(), C0257R.anim.fade_in);
/* 188 */           this.mFadeOut = AnimationUtils.loadAnimation(fragment.getActivity(), C0257R.anim.fade_out);
/* 190 */           View draftButton = inflater.inflate(C0257R.layout.plugin_drafts, parent, false);
/* 192 */           this.mUpgradeSpinner = (ProgressBar) draftButton.findViewById(C0257R.id.upgrade_spinner);
/* 193 */           this.mDraftsButton = (TextView) draftButton.findViewById(C0257R.id.drafts_button);
/* 194 */           this.mDraftsButton.setAlpha(0.35f);
/* 195 */           this.mDraftsButton.setOnTouchListener(new ViewOnTouchListenerC04284());
/* 213 */           View view = fragment.getView();
/* 215 */           ViewGroup topView = (ViewGroup) view.findViewById(C0257R.id.thumbnail_list);
/* 216 */           inflater.inflate(C0257R.layout.plugin_no_drafts_overlay, topView, true);
/* 218 */           this.mRecordingOptions = view.findViewById(C0257R.id.recording_options);
/* 220 */           this.mNoDraftsOverlay = view.findViewById(C0257R.id.no_drafts_overlay);
/* 222 */           Resources res = fragment.getResources();
/* 228 */           View sadface = this.mNoDraftsOverlay.findViewById(C0257R.id.no_drafts_overlay_face);
/* 229 */           float progressHeight = res.getDimensionPixelOffset(C0257R.dimen.progress_view_height);
/* 230 */           float sadfaceHeight = res.getDimensionPixelOffset(C0257R.dimen.draft_sadface_height);
/* 231 */           sadface.setY(((SystemUtil.getDisplaySize((Activity) fragment.getActivity()).x / 2) + progressHeight) - sadfaceHeight);
/* 232 */           View sadfaceText = this.mNoDraftsOverlay.findViewById(C0257R.id.no_drafts_text);
/* 235 */           float spacing = res.getDimensionPixelOffset(C0257R.dimen.draft_sadface_spacing);
/* 236 */           sadfaceText.setY(sadface.getY() + spacing);
/* 239 */           this.mDraftOverlayIcon = view.findViewById(C0257R.id.no_draft_overlay_draft_button);
/* 241 */           return draftButton;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DraftPlugin$4 */
/* 193 */       class ViewOnTouchListenerC04284 implements View.OnTouchListener {
/* 194 */           ViewOnTouchListenerC04284() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 197 */           public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
/* 200 */                       DraftPlugin.access$100(DraftPlugin.this).setAlpha(1.0f);
                                return true;
                            case 1:
/* 204 */                       DraftPlugin.access$100(DraftPlugin.this).setAlpha(0.35f);
/* 205 */                       DraftPlugin.this.onClick(null);
                                return true;
                            default:
/* 209 */                       return false;
                        }
                    }
                }

/* 244 */       private void startDrafts(boolean save) {
                    VineRecorder recorder;
/* 245 */           SLog.m447d("Start Drafts: {}.", Boolean.valueOf(save));
/* 246 */           Fragment fragment = getFragment();
/* 247 */           if ((fragment instanceof RecordingFragment) && (recorder = getRecorder()) != null) {
/* 250 */               if (save) {
/* 251 */                   FlurryUtils.trackSaveSession("drafts");
/* 252 */                   recorder.saveSession(this.mStartDraftsRunnable, false);
                        } else {
/* 254 */                   ((RecordingFragment) fragment).setFileFileToUse(null);
/* 255 */                   recorder.stopAndDiscardChanges("StartDrafts", this.mStartDraftsRunnable, false);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DraftPlugin$5 */
/* 260 */       class C04295 implements PromptDialogFragment.OnDialogDoneListener {
/* 261 */           C04295() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 264 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
/* 267 */                       DraftPlugin.access$500(DraftPlugin.this, false);
                                break;
                            case -1:
/* 271 */                       DraftPlugin.access$500(DraftPlugin.this, true);
                                break;
                            default:
/* 275 */                       DraftPlugin.access$602(DraftPlugin.this, false);
                                break;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DraftPlugin$6 */
/* 279 */       class RunnableC04306 implements Runnable {
/* 280 */           RunnableC04306() {
                    }

                    @Override // java.lang.Runnable
/* 283 */           public void run() {
/* 284 */               AbstractRecordingActivity activity = (AbstractRecordingActivity) DraftPlugin.this.getActivity();
/* 285 */               VineRecorder recorder = DraftPlugin.this.getRecorder();
/* 286 */               if (activity != null && recorder != null) {
/* 287 */                   DraftPlugin.this.getManager().onStartDrafts();
/* 288 */                   if (recorder.grabThumbnailsRunnable != null) {
/* 289 */                       MediaUtil.GenerateThumbnailsRunnable r = recorder.grabThumbnailsRunnable;
/* 290 */                       r.run();
/* 291 */                       recorder.grabThumbnailsRunnable = null;
                            }
/* 293 */                   activity.showDrafts(recorder.isSavedSession() ? recorder.getFile().folder.getName() : String.valueOf(AbstractRecordingActivity.START_SESSION_ID_CAMERA));
                        }
                    }
                }

/* 300 */       public void onClick(View v) {
/* 301 */           VineRecorder recorder = getRecorder();
/* 302 */           if (!this.mIsEditing && recorder != null && !recorder.isEditing() && !recorder.isCurrentlyRecording()) {
/* 304 */               if (this.mDraftUpgradeCount > 0) {
/* 305 */                   FlurryUtils.trackSessionSwitchPressedOnDraftUpgrade(this.mDraftUpgradeCount);
/* 306 */                   Handler handler = getHandler();
/* 307 */                   if (handler != null) {
/* 308 */                       handler.post(new RunnableC04317());
/* 356 */                       return;
                            }
/* 356 */                   return;
                        }
/* 322 */               FlurryUtils.trackSessionSwitchPressed();
/* 323 */               boolean dirty = recorder.isSessionDirty();
/* 324 */               if (this.mNumDrafts > 0 || dirty) {
/* 325 */                   if (this.mActivity instanceof AbstractRecordingActivity) {
/* 326 */                       this.mIsDraftShowing = true;
/* 327 */                       if (dirty) {
/* 328 */                           ((AbstractRecordingActivity) this.mActivity).showUnSavedChangesToSessionDialog(this.mDirtyDialogDoneListener);
/* 356 */                           return;
                                } else {
/* 330 */                           startDrafts(false);
/* 356 */                           return;
                                }
                            }
/* 356 */                   return;
                        }
/* 334 */               if (this.mActivity != null) {
/* 336 */                   this.mDraftOverlayIcon.setVisibility(0);
/* 338 */                   int[] location = new int[2];
/* 339 */                   this.mDraftsButton.getLocationOnScreen(location);
/* 340 */                   int x = location[0];
/* 341 */                   int y = location[1];
/* 342 */                   this.mDraftOverlayIcon.setX(x);
/* 343 */                   int draftOverlayOffset = this.mActivity.getResources().getDimensionPixelOffset(C0257R.dimen.draft_overlay_offset);
/* 346 */                   this.mDraftOverlayIcon.setY(y - draftOverlayOffset);
/* 348 */                   this.mNoDraftsOverlay.setOnTouchListener(this.mOnNoDraftOverlayTouchListener);
/* 349 */                   this.mFadeIn.setAnimationListener(this.mFadeInListener);
/* 350 */                   this.mNoDraftsOverlay.startAnimation(this.mFadeIn);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DraftPlugin$7 */
/* 306 */       class RunnableC04317 implements Runnable {
/* 307 */           RunnableC04317() {
                    }

                    @Override // java.lang.Runnable
/* 310 */           public void run() {
/* 311 */               if (DraftPlugin.this.mActivity != null) {
/* 312 */                   Util.showCenteredToast(DraftPlugin.this.mActivity, C0257R.string.draft_upgrade_in_process);
                        }
/* 314 */               if (!DraftPlugin.access$700(DraftPlugin.this)) {
/* 315 */                   DraftPlugin.access$702(DraftPlugin.this, true);
/* 316 */                   DraftPlugin.this.onDraftUpgradeNumberChanged(DraftPlugin.access$800(DraftPlugin.this));
                        }
                    }
                }
            }
