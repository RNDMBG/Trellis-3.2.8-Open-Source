            package co.vine.android;

            import android.annotation.SuppressLint;
            import android.app.Activity;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.Point;
            import android.media.MediaActionSound;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.view.ViewCompat;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.LinearLayout;
            import android.widget.Toast;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.plugin.RecorderPluginSupportedFragment;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.ProgressView;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSession;
            import co.vine.android.recorder.RecordSessionManager;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MediaUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesTextView;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.IOException;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class RecordingFragment extends BaseFragment implements RecorderPluginSupportedFragment {
                public static final String ARG_COLOR = "color";
                public static final String ARG_ENCODER_VERSION = "arg_encoder_version";
                public static final String ARG_IS_MESSAGING = "is_messaging";
                public static final String ARG_RECIPIENT_USERNAME = "recipient_username";
                public static final String ARG_SCREEN_SIZE = "screen_size";
                public static final String ARG_TOP_OVERLAY = "arg_top_overlay";
                private int mColor;
                private RecordingFile mFileFileToUse;
                private File mFolder;
                private int mHalfColor;
                private boolean mIsMessaging;
                private MediaActionSound mMediaActionSound;
                private BaseRecorderPluginManager mPluginManager;
                private String mRecipientUsername;
                private VineRecorder mRecorder;
                private RecordSessionManager mRsm;
                private Point mScreenSize;
                private int mSecondaryColor;
                private boolean mStartWithEdit;
                private double mTier;
                private Bitmap mTopOverlay;
                private RecordSessionVersion mVersion;

        static  VineRecorder access$000(RecordingFragment x0) {
            return x0.mRecorder;
                }

        static  void access$100(RecordingFragment x0, String x1, boolean x2, MediaUtil.GenerateThumbnailsRunnable x3) {
            x0.notifyActivity(x1, x2, x3);
                }

                @Override 
        public void onPause() {
            super.onPause();
            if (this.mRecorder != null) {
                this.mRecorder.onUiPaused();
                this.mRecorder.playStopRecordingSound();
                    }
                }

                @Override 
        public void onResume() {
                    RecordingFile file;
            super.onResume();
            if (this.mRecorder != null) {
                AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
                boolean isDraftsShowing = activity.isDraftsShowing();
                if (!isDraftsShowing && (file = this.mRecorder.getFile()) != null && !file.folder.exists()) {
                    CrashUtil.logException(new VineLoggingException("Invalid folder"));
                    setFileFileToUse(null);
                    this.mRecorder.swapSession("Resume invalid.", determineSessionFileForSwap(false));
                        }
               this.mRecorder.onUiResumed(activity, new OnRecordingFinishRunnable(new WeakReference(this)), isDraftsShowing);
                    }
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           release();
                }

       public void release() {
           if (this.mRecorder != null) {
               if (this.mRecorder.release()) {
                   FlurryUtils.trackAbandonedStage(BasicVineRecorder.PREFS_CAPTURE);
                   FlurryUtils.trackAbandonedTier(String.valueOf((int) (this.mTier / 64.0d)));
                        }
               this.mRecorder = null;
                    }
           this.mFileFileToUse = null;
                }

       private RecordingFile determineSessionFileForSwap(boolean useFinalFile) {
           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
                    try {
               ArrayList<RecordSessionManager.RecordSessionInfo> sessions = RecordSessionManager.getValidSessions(activity, this.mVersion);
               File extraFolder = null;
               if (sessions.size() > 9) {
                   extraFolder = sessions.get(sessions.size() - 1).folder;
                        }
               File folder = null;
               RecordingFile file = this.mFileFileToUse;
               if (useFinalFile && file != null) {
                   SLog.m464i("Is from preview, use existing final file.");
                   file.isDirty = !file.isSavedSession;
                   return file;
                        }
               boolean savedSession = this.mFolder != null;
               boolean dirty = false;
               RecordSession session = null;
               if (file != null) {
                   session = file.getSession();
                   folder = file.folder;
                        } else {
                   boolean makeNewSession = extraFolder == null && !savedSession;
                   if (!makeNewSession && ((savedSession && !this.mFolder.exists()) || (extraFolder != null && !extraFolder.exists()))) {
                       makeNewSession = true;
                       savedSession = false;
                            }
                   if (makeNewSession) {
                                try {
                           HashMap<RecordSession, File> map = this.mRsm.getCrashedSession();
                           if (map != null) {
                               session = map.keySet().iterator().next();
                               session.setConfig(new RecordConfigUtils.RecordConfig(activity));
                               folder = map.get(session);
                               dirty = session.getSegments().size() > 0;
                               CrashUtil.log("Restore from crashed session.");
                                    }
                                } catch (IOException e) {
                           SLog.m455e("Failed to get a crashed session.");
                                }
                       if (session == null) {
                           CrashUtil.log("Creating new session.");
                                    try {
                               folder = this.mRsm.createFolderForSession();
                               session = RecordSession.newSession(new RecordConfigUtils.RecordConfig(activity), this.mVersion);
                               if (sessions.size() == 9) {
                                   extraFolder = folder;
                                        }
                                    } catch (IOException e2) {
                               CrashUtil.log("Cannot create folder.", e2);
                               getActivity().finish();
                               return null;
                                    }
                                }
                            } else {
                       if (savedSession) {
                           folder = this.mFolder;
                                } else {
                           folder = extraFolder;
                           dirty = true;
                                }
                       session = RecordSessionManager.readDataObject(folder);
                       session.setConfig(new RecordConfigUtils.RecordConfig(activity));
                       session.setAudioDataCount(session.calculateAudioCount());
                       session.setVideoDataCount(session.calculateVideoCount());
                       SLog.m465i("Resume session {}.", folder.getAbsolutePath());
                            }
                        }
               return new RecordingFile(folder, session, savedSession, dirty, folder == extraFolder);
                    } catch (IOException e3) {
               SLog.m459e("Error creating folder: " + this.mFolder, (Throwable) e3);
               Toast.makeText(activity, C0257R.string.storage_not_ready_start, 0).show();
               activity.finish();
               return null;
                    }
                }

       public boolean onBackPressed(boolean isEditing) {
                    return this.mRecorder != null && this.mRecorder.onBackPressed(isEditing);
                }

                @Override 
       public int getColor(boolean primary) {
                    return primary ? this.mColor : this.mSecondaryColor;
                }

                @Override 
                @SuppressLint({"NewApi"})
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           boolean handledByPlugin = this.mPluginManager.onActivityResult(getActivity(), requestCode, resultCode, data);
           SLog.m447d("Activity result handled by plugin? {}.", Boolean.valueOf(handledByPlugin));
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           this.mTier = SystemUtil.getMemoryRatio(getActivity(), true);
           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
                    try {
               this.mRsm = this.mVersion.getManager(activity);
               Resources res = getResources();
               this.mPluginManager = new BaseRecorderPluginManager(activity.getPluginList());
               LinearLayout optionView = (LinearLayout) activity.findViewById(C0257R.id.recording_options);
               if (optionView == null) {
                   throw new IllegalArgumentException("Invalid activity");
                        }
               this.mPluginManager.createLayouts(optionView, activity.getLayoutInflater(), this);
               this.mPluginManager.onActivityCreated(activity);
               this.mPluginManager.setMessagingMode(this.mIsMessaging);
               this.mRecorder = new VineRecorder(this.mVersion, this.mPluginManager, this.mStartWithEdit, activity.getScreenSize(), this.mFileFileToUse != null && this.mStartWithEdit, activity.hasPreviewedAlready(), determineSessionFileForSwap(true), activity, (ViewGroup) activity.findViewById(C0257R.id.videoViewContainer), C0257R.id.recording_options, C0257R.id.thumbnail_list, C0257R.id.progress, C0257R.id.cameraView, C0257R.id.finishButton, C0257R.id.top_mask, C0257R.id.bottom_mask, C0257R.string.finish_processing_last, C0257R.string.opening_camera, C0257R.id.play_button_container, C0257R.id.thumbnail_overlay, C0257R.id.root_layout, res.getDimensionPixelSize(C0257R.dimen.editor_segment_padding), res.getDimensionPixelSize(C0257R.dimen.editor_buttons_height), C0257R.id.finish_loading_overlay, C0257R.id.preview_loading_overlay, C0257R.id.progress_overlay, activity.getResources().getDrawable(C0257R.drawable.progress_horizontal), AbstractRecordingActivity.getDeviceIssueStringGetter(activity), false, this.mIsMessaging, this.mMediaActionSound, getString(C0257R.string.importing), getText(C0257R.string.getting_ready), getText(C0257R.string.finish_processing_1), getText(C0257R.string.finish_processing_2), getText(C0257R.string.finish_processing_3));
                        try {
                   this.mRecorder.playStartRecordingSound();
                        } catch (NullPointerException e) {
                   CrashUtil.logException(e, "NPE when playing sound", new Object[0]);
                        }
                    } catch (IOException e2) {
               SLog.m459e("Error creating folder. ", (Throwable) e2);
               Toast.makeText(activity, C0257R.string.storage_not_ready_start, 0).show();
               activity.finish();
                    } catch (VerifyError e3) {
               SLog.m455e("Invalid recorder object.");
               activity.finish();
                    }
                }

       private void notifyActivity(String source, boolean detectedInvalidSession, MediaUtil.GenerateThumbnailsRunnable r) {
                    AbstractRecordingActivity activity;
           if (this.mRecorder.finalFile != null && (activity = (AbstractRecordingActivity) getActivity()) != null) {
               if (detectedInvalidSession) {
                   CrashUtil.logException(new IllegalStateException("Detected invalid session."));
                   Util.showCenteredToast(activity, C0257R.string.invalid_recording);
                   activity.finish();
                        } else {
                   activity.mProgressContainerWidth = ((ViewGroup) this.mRecorder.getCameraView().getParent()).getMeasuredWidth();
                   activity.mCurrentDuration = this.mRecorder.getCurrentDuration();
                            try {
                       activity.toPreview(source, this.mRecorder.finalFile, this.mRecorder.getThumbnailPath(), r);
                            } catch (AbstractRecordingActivity.InvalidStateException e) {
                       CrashUtil.logException(e);
                            }
                        }
                    }
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           Bundle args = getArguments();
           this.mVersion = (RecordSessionVersion) args.getSerializable("arg_encoder_version");
           if (this.mVersion == null) {
               this.mVersion = RecordSessionManager.getCurrentVersion(getActivity());
                    }
           this.mTopOverlay = (Bitmap) args.getParcelable(ARG_TOP_OVERLAY);
           this.mScreenSize = (Point) args.getParcelable(ARG_SCREEN_SIZE);
           this.mIsMessaging = args.getBoolean(ARG_IS_MESSAGING, false);
           this.mRecipientUsername = args.getString("recipient_username");
           this.mColor = args.getInt("color", 0);
           if (this.mColor == Settings.DEFAULT_PROFILE_COLOR || this.mColor <= 0) {
               this.mColor = getResources().getColor(C0257R.color.vine_green) & ViewCompat.MEASURED_SIZE_MASK;
                    }
           if (!this.mIsMessaging) {
               this.mColor = ViewCompat.MEASURED_SIZE_MASK;
               this.mSecondaryColor = getResources().getColor(C0257R.color.vine_green) | (-16777216);
                    } else {
               this.mSecondaryColor = this.mColor | (-16777216);
                    }
           this.mHalfColor = this.mColor | CommonUtil.THIRTY_FIVE_ALPHA_MASK;
           this.mColor |= -16777216;
           if (Build.VERSION.SDK_INT >= 16) {
               this.mMediaActionSound = new MediaActionSound();
               this.mMediaActionSound.load(2);
               this.mMediaActionSound.load(3);
                    }
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View view = inflater.inflate(C0257R.layout.record, container, false);
           View topMask = view.findViewById(C0257R.id.top_mask);
           View bottomMask = view.findViewById(C0257R.id.bottom_mask);
           Resources res = getResources();
           if (this.mIsMessaging) {
               int color = res.getColor(C0257R.color.capture_background_vm);
               topMask.setBackgroundColor(color);
               bottomMask.setBackgroundColor(color);
               ProgressView progress = (ProgressView) view.findViewById(C0257R.id.progress);
               progress.setBackgroundColor(color);
               progress.setColor(this.mHalfColor);
                    }
           View xButton = view.findViewById(C0257R.id.capture_x);
           ViewUtil.fillColor(res, this.mColor, C0257R.drawable.ic_x_large, xButton);
           View arrow = view.findViewById(C0257R.id.finish_arrow);
           ViewUtil.fillColor(res, this.mColor, C0257R.drawable.ic_arrow_large, arrow);
           if (this.mIsMessaging) {
               TypefacesTextView name = (TypefacesTextView) view.findViewById(C0257R.id.recipient_name);
               name.setWeight(3);
               name.setTypeface(Typefaces.get(getActivity()).mediumContent);
               name.setVisibility(0);
               if (!TextUtils.isEmpty(this.mRecipientUsername) && !TextUtils.isEmpty(this.mRecipientUsername)) {
                   name.setText(this.mRecipientUsername);
                        }
                    }
           View topOverlayView = view.findViewById(C0257R.id.progress_overlay);
           if (this.mTopOverlay != null) {
               topOverlayView.setVisibility(0);
               ViewUtil.setBackground(getResources(), topOverlayView, this.mTopOverlay);
                    } else {
               topOverlayView.setVisibility(8);
                    }
           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
           activity.initMasks(topMask, bottomMask);
           return view;
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           super.onViewCreated(view, savedInstanceState);
           if (this.mIsMessaging) {
               View options = getActivity().findViewById(C0257R.id.recording_options);
               ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) options.getLayoutParams();
               int margin = (int) (((double) this.mScreenSize.x) * 0.1d);
               params.setMargins(margin, 0, margin, 0);
               options.setLayoutParams(params);
                    }
                }

       public void setDiscardChangesOnStop() {
           if (this.mRecorder != null) {
               this.mRecorder.setDiscardChanges(true);
                    }
                }

       public void playStopSound() {
           if (this.mRecorder != null) {
               this.mRecorder.playStopRecordingSound();
                    }
                }

       public boolean isEditing() {
                    return this.mRecorder != null && this.mRecorder.isEditing();
                }

       public boolean isEditingDirty() {
                    return this.mRecorder != null && this.mRecorder.isEditingDirty();
                }

       public void onFinishPressed(View v) {
           if (this.mRecorder != null) {
               this.mRecorder.onFinishPressed();
                    }
                }

       public boolean isSavedSession() {
           return this.mRecorder.isSavedSession();
                }

       public void discardEditing() {
           if (this.mRecorder != null) {
               this.mRecorder.setEditMode(false, true);
                    }
                }

       public void saveSessionAndQuit() {
           if (this.mRecorder != null) {
               FlurryUtils.trackSaveSession("quit");
               this.mRecorder.saveSession(new RunnableC02611(), true);
                    }
                }

                
       class RunnableC02611 implements Runnable {
           RunnableC02611() {
                    }

                    @Override 
           public void run() {
               Activity activity = RecordingFragment.this.getActivity();
               if (activity != null) {
                   activity.finish();
                        }
                    }
                }

       public void saveSession() {
           if (this.mRecorder != null) {
               FlurryUtils.trackSaveSession("saveNoQuit");
               this.mRecorder.saveSession(null, true);
                    }
                }

       public void setFileFileToUse(RecordingFile fileFileToUse) {
           this.mFileFileToUse = fileFileToUse;
                }

       public void setStartWithEdit(boolean startWithEdit) {
           this.mStartWithEdit = startWithEdit;
                }

       public boolean isSessionModified() {
                    return this.mRecorder != null && this.mRecorder.isSessionDirty();
                }

       public void swapFolder(String tag, File folder) {
           this.mFolder = folder;
           if (this.mRecorder != null) {
               setFileFileToUse(null);
               this.mRecorder.swapSession(tag + " swap", determineSessionFileForSwap(false));
               this.mRecorder.setDelayDialog(true);
               this.mRecorder.onResume("Swap folder");
                    }
                }

       public boolean isResuming() {
                    return this.mRecorder != null && this.mRecorder.isResuming();
                }

       public void resumeFromDraft() {
                    RecordingFile newFile;
           if (this.mRecorder != null && !this.mRecorder.isResuming()) {
               RecordingFile resumeFile = this.mRecorder.getFile();
               if (resumeFile.folder.exists()) {
                   newFile = this.mRecorder.swapSession("BackPress, Resume from draft using resumeFile.", resumeFile);
                        } else {
                   newFile = this.mRecorder.swapSession("BackPress, Resume from draft using determined.", determineSessionFileForSwap(false));
                        }
               if (newFile != null) {
                   this.mRecorder.setDelayDialog(true);
                   this.mRecorder.onResume("Resume draft");
                        }
                    }
                }

       public BaseRecorderPluginManager getPluginManager() {
           return this.mPluginManager;
                }

       public String getThumbnailPath() {
           return this.mRecorder.getThumbnailPath();
                }

       public View getProgressView() {
           return this.mRecorder.getProgressView();
                }

       public boolean startRelativeTime() {
                    return this.mRecorder != null && this.mRecorder.canKeepRecording() && !this.mRecorder.isCurrentlyRecording() && this.mRecorder.startRelativeTime();
                }

       public boolean endRelativeTime() {
                    return this.mRecorder != null && this.mRecorder.canKeepRecording() && this.mRecorder.isCurrentlyRecording() && this.mRecorder.endRelativeTime();
                }

       public static class OnRecordingFinishRunnable implements Runnable {
                    private WeakReference<RecordingFragment> mFragment;

           public OnRecordingFinishRunnable(WeakReference<RecordingFragment> fragment) {
               this.mFragment = fragment;
                    }

                    @Override 
           public void run() {
                        RecordingFile ff;
               RecordingFragment fragment = this.mFragment.get();
               if (fragment != null && (ff = RecordingFragment.access$000(fragment).finalFile) != null && ff.isValid()) {
                   MediaUtil.GenerateThumbnailsRunnable r = RecordingFragment.access$000(fragment).grabThumbnailsRunnable;
                   RecordingFragment.access$000(fragment).grabThumbnailsRunnable = null;
                   RecordingFragment.access$100(fragment, "OnRecordingFinishRunnable", RecordingFragment.access$000(fragment).detectedInvalidSession, r);
                        }
                    }
                }
            }
