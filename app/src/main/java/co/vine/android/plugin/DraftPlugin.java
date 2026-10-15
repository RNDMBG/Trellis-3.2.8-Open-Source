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
            import co.vine.android.R;
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
            
            
    public class DraftPlugin extends BaseRecorderPlugin<View, VineRecorder> {
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

        static  View access$000(DraftPlugin x0) {
            return x0.mDraftOverlayIcon;
                }

        static  TextView access$100(DraftPlugin x0) {
            return x0.mDraftsButton;
                }

        static  View access$200(DraftPlugin x0) {
            return x0.mNoDraftsOverlay;
                }

        static  Animation.AnimationListener access$300(DraftPlugin x0) {
            return x0.mFadeOutListener;
                }

        static  Animation access$400(DraftPlugin x0) {
            return x0.mFadeOut;
                }

        static  void access$500(DraftPlugin x0, boolean x1) {
            x0.startDrafts(x1);
                }

        static  boolean access$602(DraftPlugin x0, boolean x1) {
            x0.mIsDraftShowing = x1;
                    return x1;
                }

        static  boolean access$700(DraftPlugin x0) {
            return x0.mIsDraftUpgradeAnimationRunning;
                }

        static  boolean access$702(DraftPlugin x0, boolean x1) {
            x0.mIsDraftUpgradeAnimationRunning = x1;
                    return x1;
                }

        static  int access$800(DraftPlugin x0) {
            return x0.mDraftUpgradeCount;
                }

                
        class AnimationAnimationListenerC04251 implements Animation.AnimationListener {
            AnimationAnimationListenerC04251() {
                    }

                    @Override 
            public void onAnimationStart(Animation animation) {
                DraftPlugin.access$000(DraftPlugin.this).setVisibility(8);
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                DraftPlugin.access$100(DraftPlugin.this).setAlpha(0.35f);
                DraftPlugin.access$200(DraftPlugin.this).setVisibility(8);
                DraftPlugin.access$200(DraftPlugin.this).setOnTouchListener(null);
                    }

                    @Override 
            public void onAnimationRepeat(Animation animation) {
                    }
                }

                @Override 
        public boolean onSetEditMode(boolean editing, boolean hasData) {
            this.mIsEditing = editing;
            return this.mIsDraftShowing;
                }

                
        class AnimationAnimationListenerC04262 implements Animation.AnimationListener {
            AnimationAnimationListenerC04262() {
                    }

                    @Override 
            public void onAnimationStart(Animation animation) {
                DraftPlugin.access$200(DraftPlugin.this).setVisibility(0);
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                    }

                    @Override 
            public void onAnimationRepeat(Animation animation) {
                    }
                }

                
        class ViewOnTouchListenerC04273 implements View.OnTouchListener {
            ViewOnTouchListenerC04273() {
                    }

                    @Override 
            public boolean onTouch(View v, MotionEvent event) {
                DraftPlugin.access$400(DraftPlugin.this).setAnimationListener(DraftPlugin.access$300(DraftPlugin.this));
                DraftPlugin.access$200(DraftPlugin.this).startAnimation(DraftPlugin.access$400(DraftPlugin.this));
                DraftPlugin.access$100(DraftPlugin.this).setAlpha(0.35f);
               return true;
                    }
                }

                @Override 
       public void onResume(Activity activity) {
           super.onResume(activity);
           if ((activity instanceof AbstractRecordingActivity) && !((AbstractRecordingActivity) activity).isDraftsShowing()) {
               refreshDraftNumber();
                    }
                }

       public DraftPlugin() {
           super("draft");
            this.mFadeOutListener = new AnimationAnimationListenerC04251();
            this.mFadeInListener = new AnimationAnimationListenerC04262();
            this.mOnNoDraftOverlayTouchListener = new ViewOnTouchListenerC04273();
           this.mDirtyDialogDoneListener = new C04295();
           this.mStartDraftsRunnable = new RunnableC04306();
                }

                @Override 
       public void onDraftUpgradeNumberChanged(int count) {
           SLog.m447d("Draft upgrade number changed with count {}", Integer.valueOf(count));
           this.mDraftUpgradeCount = count;
           if (this.mIsDraftUpgradeAnimationRunning) {
               if (count > 0) {
                   this.mUpgradeSpinner.setVisibility(0);
                   this.mDraftsButton.setVisibility(8);
                        } else {
                   this.mUpgradeSpinner.setVisibility(8);
                   this.mDraftsButton.setVisibility(0);
                        }
                    }
                }

                @Override 
       public void onHideDrafts() {
           this.mIsDraftShowing = false;
           refreshDraftNumber();
           this.mRecordingOptions.setAlpha(0.0f);
           this.mRecordingOptions.setVisibility(0);
           this.mRecordingOptions.animate().alpha(1.0f).start();
                }

                @Override 
       public void onShowDrafts() {
           this.mIsDraftShowing = true;
           this.mRecordingOptions.setVisibility(8);
                }

       private void refreshDraftNumber() {
                    try {
               BasicVineRecorder recorder = getRecorder();
               if (recorder != null && recorder.hasSessionFile()) {
                   int numDrafts = RecordSessionManager.getNumberOfValidSessions(recorder);
                   if (numDrafts > 0) {
                       if (numDrafts > 9) {
                           numDrafts--;
                                }
                       this.mDraftsButton.setText(String.valueOf(numDrafts));
                       this.mDraftsButton.setBackgroundResource(R.drawable.ic_drafts);
                            } else {
                       this.mDraftsButton.setText("");
                       this.mDraftsButton.setBackgroundResource(R.drawable.ic_save);
                            }
                   this.mNumDrafts = numDrafts;
                        }
                    } catch (IOException e) {
               CrashUtil.logException(e);
                    }
                }

                @Override 
       public void setMessagingMode(boolean isMessaging) {
                    View view;
           if (isMessaging && (view = getView()) != null) {
               view.setVisibility(8);
                    }
                }

                @Override 
       public boolean canEdit() {
                    return !this.mIsDraftShowing;
                }

                @Override 
       public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
           this.mFadeIn = AnimationUtils.loadAnimation(fragment.getActivity(), R.anim.fade_in);
           this.mFadeOut = AnimationUtils.loadAnimation(fragment.getActivity(), R.anim.fade_out);
           View draftButton = inflater.inflate(R.layout.plugin_drafts, parent, false);
           this.mUpgradeSpinner = (ProgressBar) draftButton.findViewById(R.id.upgrade_spinner);
           this.mDraftsButton = (TextView) draftButton.findViewById(R.id.drafts_button);
           this.mDraftsButton.setAlpha(0.35f);
           this.mDraftsButton.setOnTouchListener(new ViewOnTouchListenerC04284());
           View view = fragment.getView();
           ViewGroup topView = (ViewGroup) view.findViewById(R.id.thumbnail_list);
           inflater.inflate(R.layout.plugin_no_drafts_overlay, topView, true);
           this.mRecordingOptions = view.findViewById(R.id.recording_options);
           this.mNoDraftsOverlay = view.findViewById(R.id.no_drafts_overlay);
           Resources res = fragment.getResources();
           View sadface = this.mNoDraftsOverlay.findViewById(R.id.no_drafts_overlay_face);
           float progressHeight = res.getDimensionPixelOffset(R.dimen.progress_view_height);
           float sadfaceHeight = res.getDimensionPixelOffset(R.dimen.draft_sadface_height);
           sadface.setY(((SystemUtil.getDisplaySize((Activity) fragment.getActivity()).x / 2) + progressHeight) - sadfaceHeight);
           View sadfaceText = this.mNoDraftsOverlay.findViewById(R.id.no_drafts_text);
           float spacing = res.getDimensionPixelOffset(R.dimen.draft_sadface_spacing);
           sadfaceText.setY(sadface.getY() + spacing);
           this.mDraftOverlayIcon = view.findViewById(R.id.no_draft_overlay_draft_button);
           return draftButton;
                }

                
       class ViewOnTouchListenerC04284 implements View.OnTouchListener {
           ViewOnTouchListenerC04284() {
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
                       DraftPlugin.access$100(DraftPlugin.this).setAlpha(1.0f);
                                return true;
                            case 1:
                       DraftPlugin.access$100(DraftPlugin.this).setAlpha(0.35f);
                       DraftPlugin.this.onClick(null);
                                return true;
                            default:
                       return false;
                        }
                    }
                }

       private void startDrafts(boolean save) {
                    VineRecorder recorder;
           SLog.m447d("Start Drafts: {}.", Boolean.valueOf(save));
           Fragment fragment = getFragment();
           if ((fragment instanceof RecordingFragment) && (recorder = getRecorder()) != null) {
               if (save) {
                   FlurryUtils.trackSaveSession("drafts");
                   recorder.saveSession(this.mStartDraftsRunnable, false);
                        } else {
                   ((RecordingFragment) fragment).setFileFileToUse(null);
                   recorder.stopAndDiscardChanges("StartDrafts", this.mStartDraftsRunnable, false);
                        }
                    }
                }

                
       class C04295 implements PromptDialogFragment.OnDialogDoneListener {
           C04295() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
                       DraftPlugin.access$500(DraftPlugin.this, false);
                                break;
                            case -1:
                       DraftPlugin.access$500(DraftPlugin.this, true);
                                break;
                            default:
                       DraftPlugin.access$602(DraftPlugin.this, false);
                                break;
                        }
                    }
                }

                
       class RunnableC04306 implements Runnable {
           RunnableC04306() {
                    }

                    @Override 
           public void run() {
               AbstractRecordingActivity activity = (AbstractRecordingActivity) DraftPlugin.this.getActivity();
               VineRecorder recorder = DraftPlugin.this.getRecorder();
               if (activity != null && recorder != null) {
                   DraftPlugin.this.getManager().onStartDrafts();
                   if (recorder.grabThumbnailsRunnable != null) {
                       MediaUtil.GenerateThumbnailsRunnable r = recorder.grabThumbnailsRunnable;
                       r.run();
                       recorder.grabThumbnailsRunnable = null;
                            }
                   activity.showDrafts(recorder.isSavedSession() ? recorder.getFile().folder.getName() : String.valueOf(AbstractRecordingActivity.START_SESSION_ID_CAMERA));
                        }
                    }
                }

       public void onClick(View v) {
           VineRecorder recorder = getRecorder();
           if (!this.mIsEditing && recorder != null && !recorder.isEditing() && !recorder.isCurrentlyRecording()) {
               if (this.mDraftUpgradeCount > 0) {
                   FlurryUtils.trackSessionSwitchPressedOnDraftUpgrade(this.mDraftUpgradeCount);
                   Handler handler = getHandler();
                   if (handler != null) {
                       handler.post(new RunnableC04317());
                       return;
                            }
                   return;
                        }
               FlurryUtils.trackSessionSwitchPressed();
               boolean dirty = recorder.isSessionDirty();
               if (this.mNumDrafts > 0 || dirty) {
                   if (this.mActivity instanceof AbstractRecordingActivity) {
                       this.mIsDraftShowing = true;
                       if (dirty) {
                           ((AbstractRecordingActivity) this.mActivity).showUnSavedChangesToSessionDialog(this.mDirtyDialogDoneListener);
                           return;
                                } else {
                           startDrafts(false);
                           return;
                                }
                            }
                   return;
                        }
               if (this.mActivity != null) {
                   this.mDraftOverlayIcon.setVisibility(0);
                   int[] location = new int[2];
                   this.mDraftsButton.getLocationOnScreen(location);
                   int x = location[0];
                   int y = location[1];
                   this.mDraftOverlayIcon.setX(x);
                   int draftOverlayOffset = this.mActivity.getResources().getDimensionPixelOffset(R.dimen.draft_overlay_offset);
                   this.mDraftOverlayIcon.setY(y - draftOverlayOffset);
                   this.mNoDraftsOverlay.setOnTouchListener(this.mOnNoDraftOverlayTouchListener);
                   this.mFadeIn.setAnimationListener(this.mFadeInListener);
                   this.mNoDraftsOverlay.startAnimation(this.mFadeIn);
                        }
                    }
                }

                
       class RunnableC04317 implements Runnable {
           RunnableC04317() {
                    }

                    @Override 
           public void run() {
               if (DraftPlugin.this.mActivity != null) {
                   Util.showCenteredToast(DraftPlugin.this.mActivity, R.string.draft_upgrade_in_process);
                        }
               if (!DraftPlugin.access$700(DraftPlugin.this)) {
                   DraftPlugin.access$702(DraftPlugin.this, true);
                   DraftPlugin.this.onDraftUpgradeNumberChanged(DraftPlugin.access$800(DraftPlugin.this));
                        }
                    }
                }
            }
