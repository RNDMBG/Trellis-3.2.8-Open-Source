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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class RecordingFragment extends BaseFragment implements RecorderPluginSupportedFragment {
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

/* 47 */        static /* synthetic */ VineRecorder access$000(RecordingFragment x0) {
/* 48 */            return x0.mRecorder;
                }

/* 47 */        static /* synthetic */ void access$100(RecordingFragment x0, String x1, boolean x2, MediaUtil.GenerateThumbnailsRunnable x3) {
/* 48 */            x0.notifyActivity(x1, x2, x3);
                }

                @Override // android.support.v4.app.Fragment
/* 75 */        public void onPause() {
/* 76 */            super.onPause();
/* 77 */            if (this.mRecorder != null) {
/* 78 */                this.mRecorder.onUiPaused();
/* 79 */                this.mRecorder.playStopRecordingSound();
                    }
                }

                @Override // co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 84 */        public void onResume() {
                    RecordingFile file;
/* 85 */            super.onResume();
/* 86 */            if (this.mRecorder != null) {
/* 87 */                AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
/* 88 */                boolean isDraftsShowing = activity.isDraftsShowing();
/* 90 */                if (!isDraftsShowing && (file = this.mRecorder.getFile()) != null && !file.folder.exists()) {
/* 94 */                    CrashUtil.logException(new VineLoggingException("Invalid folder"));
/* 95 */                    setFileFileToUse(null);
/* 96 */                    this.mRecorder.swapSession("Resume invalid.", determineSessionFileForSwap(false));
                        }
/* 101 */               this.mRecorder.onUiResumed(activity, new OnRecordingFinishRunnable(new WeakReference(this)), isDraftsShowing);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 109 */       public void onDestroy() {
/* 110 */           super.onDestroy();
/* 111 */           release();
                }

/* 115 */       public void release() {
/* 116 */           if (this.mRecorder != null) {
/* 117 */               if (this.mRecorder.release()) {
/* 118 */                   FlurryUtils.trackAbandonedStage(BasicVineRecorder.PREFS_CAPTURE);
/* 119 */                   FlurryUtils.trackAbandonedTier(String.valueOf((int) (this.mTier / 64.0d)));
                        }
/* 121 */               this.mRecorder = null;
                    }
/* 123 */           this.mFileFileToUse = null;
                }

/* 126 */       private RecordingFile determineSessionFileForSwap(boolean useFinalFile) {
/* 127 */           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
                    try {
/* 129 */               ArrayList<RecordSessionManager.RecordSessionInfo> sessions = RecordSessionManager.getValidSessions(activity, this.mVersion);
/* 130 */               File extraFolder = null;
/* 131 */               if (sessions.size() > 9) {
/* 132 */                   extraFolder = sessions.get(sessions.size() - 1).folder;
                        }
/* 134 */               File folder = null;
/* 135 */               RecordingFile file = this.mFileFileToUse;
/* 136 */               if (useFinalFile && file != null) {
/* 137 */                   SLog.m464i("Is from preview, use existing final file.");
/* 138 */                   file.isDirty = !file.isSavedSession;
/* 203 */                   return file;
                        }
/* 141 */               boolean savedSession = this.mFolder != null;
/* 142 */               boolean dirty = false;
/* 143 */               RecordSession session = null;
/* 144 */               if (file != null) {
/* 145 */                   session = file.getSession();
/* 146 */                   folder = file.folder;
                        } else {
/* 148 */                   boolean makeNewSession = extraFolder == null && !savedSession;
/* 149 */                   if (!makeNewSession && ((savedSession && !this.mFolder.exists()) || (extraFolder != null && !extraFolder.exists()))) {
/* 151 */                       makeNewSession = true;
/* 152 */                       savedSession = false;
                            }
/* 156 */                   if (makeNewSession) {
                                try {
/* 158 */                           HashMap<RecordSession, File> map = this.mRsm.getCrashedSession();
/* 159 */                           if (map != null) {
/* 160 */                               session = map.keySet().iterator().next();
/* 161 */                               session.setConfig(new RecordConfigUtils.RecordConfig(activity));
/* 162 */                               folder = map.get(session);
/* 163 */                               dirty = session.getSegments().size() > 0;
/* 164 */                               CrashUtil.log("Restore from crashed session.");
                                    }
                                } catch (IOException e) {
/* 167 */                           SLog.m455e("Failed to get a crashed session.");
                                }
/* 169 */                       if (session == null) {
/* 170 */                           CrashUtil.log("Creating new session.");
                                    try {
/* 172 */                               folder = this.mRsm.createFolderForSession();
/* 173 */                               session = RecordSession.newSession(new RecordConfigUtils.RecordConfig(activity), this.mVersion);
/* 175 */                               if (sessions.size() == 9) {
/* 176 */                                   extraFolder = folder;
                                        }
                                    } catch (IOException e2) {
/* 179 */                               CrashUtil.log("Cannot create folder.", e2);
/* 180 */                               getActivity().finish();
/* 181 */                               return null;
                                    }
                                }
                            } else {
/* 185 */                       if (savedSession) {
/* 186 */                           folder = this.mFolder;
                                } else {
/* 188 */                           folder = extraFolder;
/* 189 */                           dirty = true;
                                }
/* 191 */                       session = RecordSessionManager.readDataObject(folder);
/* 192 */                       session.setConfig(new RecordConfigUtils.RecordConfig(activity));
/* 193 */                       session.setAudioDataCount(session.calculateAudioCount());
/* 194 */                       session.setVideoDataCount(session.calculateVideoCount());
/* 195 */                       SLog.m465i("Resume session {}.", folder.getAbsolutePath());
                            }
                        }
/* 198 */               return new RecordingFile(folder, session, savedSession, dirty, folder == extraFolder);
                    } catch (IOException e3) {
/* 200 */               SLog.m459e("Error creating folder: " + this.mFolder, (Throwable) e3);
/* 201 */               Toast.makeText(activity, C0257R.string.storage_not_ready_start, 0).show();
/* 202 */               activity.finish();
/* 203 */               return null;
                    }
                }

/* 207 */       public boolean onBackPressed(boolean isEditing) {
                    return this.mRecorder != null && this.mRecorder.onBackPressed(isEditing);
                }

                @Override // co.vine.android.plugin.RecorderPluginSupportedFragment
/* 212 */       public int getColor(boolean primary) {
                    return primary ? this.mColor : this.mSecondaryColor;
                }

                @Override // android.support.v4.app.Fragment
                @SuppressLint({"NewApi"})
/* 218 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 219 */           boolean handledByPlugin = this.mPluginManager.onActivityResult(getActivity(), requestCode, resultCode, data);
/* 220 */           SLog.m447d("Activity result handled by plugin? {}.", Boolean.valueOf(handledByPlugin));
                }

                @Override // android.support.v4.app.Fragment
/* 224 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 225 */           super.onActivityCreated(savedInstanceState);
/* 226 */           this.mTier = SystemUtil.getMemoryRatio(getActivity(), true);
/* 227 */           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
                    try {
/* 229 */               this.mRsm = this.mVersion.getManager(activity);
/* 230 */               Resources res = getResources();
/* 232 */               this.mPluginManager = new BaseRecorderPluginManager(activity.getPluginList());
/* 233 */               LinearLayout optionView = (LinearLayout) activity.findViewById(C0257R.id.recording_options);
/* 234 */               if (optionView == null) {
/* 235 */                   throw new IllegalArgumentException("Invalid activity");
                        }
/* 237 */               this.mPluginManager.createLayouts(optionView, activity.getLayoutInflater(), this);
/* 239 */               this.mPluginManager.onActivityCreated(activity);
/* 240 */               this.mPluginManager.setMessagingMode(this.mIsMessaging);
/* 241 */               this.mRecorder = new VineRecorder(this.mVersion, this.mPluginManager, this.mStartWithEdit, activity.getScreenSize(), this.mFileFileToUse != null && this.mStartWithEdit, activity.hasPreviewedAlready(), determineSessionFileForSwap(true), activity, (ViewGroup) activity.findViewById(C0257R.id.videoViewContainer), C0257R.id.recording_options, C0257R.id.thumbnail_list, C0257R.id.progress, C0257R.id.cameraView, C0257R.id.finishButton, C0257R.id.top_mask, C0257R.id.bottom_mask, C0257R.string.finish_processing_last, C0257R.string.opening_camera, C0257R.id.play_button_container, C0257R.id.thumbnail_overlay, C0257R.id.root_layout, res.getDimensionPixelSize(C0257R.dimen.editor_segment_padding), res.getDimensionPixelSize(C0257R.dimen.editor_buttons_height), C0257R.id.finish_loading_overlay, C0257R.id.preview_loading_overlay, C0257R.id.progress_overlay, activity.getResources().getDrawable(C0257R.drawable.progress_horizontal), AbstractRecordingActivity.getDeviceIssueStringGetter(activity), false, this.mIsMessaging, this.mMediaActionSound, getString(C0257R.string.importing), getText(C0257R.string.getting_ready), getText(C0257R.string.finish_processing_1), getText(C0257R.string.finish_processing_2), getText(C0257R.string.finish_processing_3));
                        try {
/* 275 */                   this.mRecorder.playStartRecordingSound();
                        } catch (NullPointerException e) {
/* 277 */                   CrashUtil.logException(e, "NPE when playing sound", new Object[0]);
                        }
                    } catch (IOException e2) {
/* 280 */               SLog.m459e("Error creating folder. ", (Throwable) e2);
/* 281 */               Toast.makeText(activity, C0257R.string.storage_not_ready_start, 0).show();
/* 282 */               activity.finish();
                    } catch (VerifyError e3) {
/* 284 */               SLog.m455e("Invalid recorder object.");
/* 285 */               activity.finish();
                    }
                }

/* 289 */       private void notifyActivity(String source, boolean detectedInvalidSession, MediaUtil.GenerateThumbnailsRunnable r) {
                    AbstractRecordingActivity activity;
/* 290 */           if (this.mRecorder.finalFile != null && (activity = (AbstractRecordingActivity) getActivity()) != null) {
/* 293 */               if (detectedInvalidSession) {
/* 294 */                   CrashUtil.logException(new IllegalStateException("Detected invalid session."));
/* 295 */                   Util.showCenteredToast(activity, C0257R.string.invalid_recording);
/* 296 */                   activity.finish();
                        } else {
/* 298 */                   activity.mProgressContainerWidth = ((ViewGroup) this.mRecorder.getCameraView().getParent()).getMeasuredWidth();
/* 300 */                   activity.mCurrentDuration = this.mRecorder.getCurrentDuration();
                            try {
/* 302 */                       activity.toPreview(source, this.mRecorder.finalFile, this.mRecorder.getThumbnailPath(), r);
                            } catch (AbstractRecordingActivity.InvalidStateException e) {
/* 304 */                       CrashUtil.logException(e);
                            }
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 312 */       public void onCreate(Bundle savedInstanceState) {
/* 313 */           super.onCreate(savedInstanceState);
/* 314 */           Bundle args = getArguments();
/* 315 */           this.mVersion = (RecordSessionVersion) args.getSerializable("arg_encoder_version");
/* 316 */           if (this.mVersion == null) {
/* 317 */               this.mVersion = RecordSessionManager.getCurrentVersion(getActivity());
                    }
/* 319 */           this.mTopOverlay = (Bitmap) args.getParcelable(ARG_TOP_OVERLAY);
/* 320 */           this.mScreenSize = (Point) args.getParcelable(ARG_SCREEN_SIZE);
/* 321 */           this.mIsMessaging = args.getBoolean(ARG_IS_MESSAGING, false);
/* 322 */           this.mRecipientUsername = args.getString("recipient_username");
/* 323 */           this.mColor = args.getInt("color", 0);
/* 324 */           if (this.mColor == Settings.DEFAULT_PROFILE_COLOR || this.mColor <= 0) {
/* 325 */               this.mColor = getResources().getColor(C0257R.color.vine_green) & ViewCompat.MEASURED_SIZE_MASK;
                    }
/* 328 */           if (!this.mIsMessaging) {
/* 329 */               this.mColor = ViewCompat.MEASURED_SIZE_MASK;
/* 330 */               this.mSecondaryColor = getResources().getColor(C0257R.color.vine_green) | (-16777216);
                    } else {
/* 332 */               this.mSecondaryColor = this.mColor | (-16777216);
                    }
/* 334 */           this.mHalfColor = this.mColor | CommonUtil.THIRTY_FIVE_ALPHA_MASK;
/* 335 */           this.mColor |= -16777216;
/* 337 */           if (Build.VERSION.SDK_INT >= 16) {
/* 338 */               this.mMediaActionSound = new MediaActionSound();
/* 339 */               this.mMediaActionSound.load(2);
/* 340 */               this.mMediaActionSound.load(3);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 346 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 347 */           View view = inflater.inflate(C0257R.layout.record, container, false);
/* 348 */           View topMask = view.findViewById(C0257R.id.top_mask);
/* 349 */           View bottomMask = view.findViewById(C0257R.id.bottom_mask);
/* 351 */           Resources res = getResources();
/* 352 */           if (this.mIsMessaging) {
/* 353 */               int color = res.getColor(C0257R.color.capture_background_vm);
/* 354 */               topMask.setBackgroundColor(color);
/* 355 */               bottomMask.setBackgroundColor(color);
/* 357 */               ProgressView progress = (ProgressView) view.findViewById(C0257R.id.progress);
/* 358 */               progress.setBackgroundColor(color);
/* 359 */               progress.setColor(this.mHalfColor);
                    }
/* 362 */           View xButton = view.findViewById(C0257R.id.capture_x);
/* 363 */           ViewUtil.fillColor(res, this.mColor, C0257R.drawable.ic_x_large, xButton);
/* 365 */           View arrow = view.findViewById(C0257R.id.finish_arrow);
/* 366 */           ViewUtil.fillColor(res, this.mColor, C0257R.drawable.ic_arrow_large, arrow);
/* 368 */           if (this.mIsMessaging) {
/* 369 */               TypefacesTextView name = (TypefacesTextView) view.findViewById(C0257R.id.recipient_name);
/* 370 */               name.setWeight(3);
/* 371 */               name.setTypeface(Typefaces.get(getActivity()).mediumContent);
/* 372 */               name.setVisibility(0);
/* 373 */               if (!TextUtils.isEmpty(this.mRecipientUsername) && !TextUtils.isEmpty(this.mRecipientUsername)) {
/* 375 */                   name.setText(this.mRecipientUsername);
                        }
                    }
/* 380 */           View topOverlayView = view.findViewById(C0257R.id.progress_overlay);
/* 381 */           if (this.mTopOverlay != null) {
/* 382 */               topOverlayView.setVisibility(0);
/* 383 */               ViewUtil.setBackground(getResources(), topOverlayView, this.mTopOverlay);
                    } else {
/* 385 */               topOverlayView.setVisibility(8);
                    }
/* 388 */           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
/* 390 */           activity.initMasks(topMask, bottomMask);
/* 392 */           return view;
                }

                @Override // android.support.v4.app.Fragment
/* 396 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 397 */           super.onViewCreated(view, savedInstanceState);
/* 398 */           if (this.mIsMessaging) {
/* 399 */               View options = getActivity().findViewById(C0257R.id.recording_options);
/* 400 */               ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) options.getLayoutParams();
/* 401 */               int margin = (int) (((double) this.mScreenSize.x) * 0.1d);
/* 402 */               params.setMargins(margin, 0, margin, 0);
/* 403 */               options.setLayoutParams(params);
                    }
                }

/* 407 */       public void setDiscardChangesOnStop() {
/* 408 */           if (this.mRecorder != null) {
/* 409 */               this.mRecorder.setDiscardChanges(true);
                    }
                }

/* 413 */       public void playStopSound() {
/* 414 */           if (this.mRecorder != null) {
/* 415 */               this.mRecorder.playStopRecordingSound();
                    }
                }

/* 419 */       public boolean isEditing() {
                    return this.mRecorder != null && this.mRecorder.isEditing();
                }

/* 423 */       public boolean isEditingDirty() {
                    return this.mRecorder != null && this.mRecorder.isEditingDirty();
                }

/* 427 */       public void onFinishPressed(View v) {
/* 428 */           if (this.mRecorder != null) {
/* 429 */               this.mRecorder.onFinishPressed();
                    }
                }

/* 433 */       public boolean isSavedSession() {
/* 434 */           return this.mRecorder.isSavedSession();
                }

/* 437 */       public void discardEditing() {
/* 438 */           if (this.mRecorder != null) {
/* 439 */               this.mRecorder.setEditMode(false, true);
                    }
                }

/* 443 */       public void saveSessionAndQuit() {
/* 444 */           if (this.mRecorder != null) {
/* 445 */               FlurryUtils.trackSaveSession("quit");
/* 446 */               this.mRecorder.saveSession(new RunnableC02611(), true);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.RecordingFragment$1 */
/* 444 */       class RunnableC02611 implements Runnable {
/* 445 */           RunnableC02611() {
                    }

                    @Override // java.lang.Runnable
/* 448 */           public void run() {
/* 449 */               Activity activity = RecordingFragment.this.getActivity();
/* 450 */               if (activity != null) {
/* 451 */                   activity.finish();
                        }
                    }
                }

/* 461 */       public void saveSession() {
/* 462 */           if (this.mRecorder != null) {
/* 463 */               FlurryUtils.trackSaveSession("saveNoQuit");
/* 464 */               this.mRecorder.saveSession(null, true);
                    }
                }

/* 468 */       public void setFileFileToUse(RecordingFile fileFileToUse) {
/* 469 */           this.mFileFileToUse = fileFileToUse;
                }

/* 472 */       public void setStartWithEdit(boolean startWithEdit) {
/* 473 */           this.mStartWithEdit = startWithEdit;
                }

/* 476 */       public boolean isSessionModified() {
                    return this.mRecorder != null && this.mRecorder.isSessionDirty();
                }

/* 480 */       public void swapFolder(String tag, File folder) {
/* 481 */           this.mFolder = folder;
/* 482 */           if (this.mRecorder != null) {
/* 483 */               setFileFileToUse(null);
/* 484 */               this.mRecorder.swapSession(tag + " swap", determineSessionFileForSwap(false));
/* 485 */               this.mRecorder.setDelayDialog(true);
/* 486 */               this.mRecorder.onResume("Swap folder");
                    }
                }

/* 490 */       public boolean isResuming() {
                    return this.mRecorder != null && this.mRecorder.isResuming();
                }

/* 494 */       public void resumeFromDraft() {
                    RecordingFile newFile;
/* 495 */           if (this.mRecorder != null && !this.mRecorder.isResuming()) {
/* 497 */               RecordingFile resumeFile = this.mRecorder.getFile();
/* 499 */               if (resumeFile.folder.exists()) {
/* 500 */                   newFile = this.mRecorder.swapSession("BackPress, Resume from draft using resumeFile.", resumeFile);
                        } else {
/* 502 */                   newFile = this.mRecorder.swapSession("BackPress, Resume from draft using determined.", determineSessionFileForSwap(false));
                        }
/* 504 */               if (newFile != null) {
/* 505 */                   this.mRecorder.setDelayDialog(true);
/* 506 */                   this.mRecorder.onResume("Resume draft");
                        }
                    }
                }

/* 512 */       public BaseRecorderPluginManager getPluginManager() {
/* 513 */           return this.mPluginManager;
                }

/* 516 */       public String getThumbnailPath() {
/* 517 */           return this.mRecorder.getThumbnailPath();
                }

/* 520 */       public View getProgressView() {
/* 521 */           return this.mRecorder.getProgressView();
                }

/* 524 */       public boolean startRelativeTime() {
                    return this.mRecorder != null && this.mRecorder.canKeepRecording() && !this.mRecorder.isCurrentlyRecording() && this.mRecorder.startRelativeTime();
                }

/* 529 */       public boolean endRelativeTime() {
                    return this.mRecorder != null && this.mRecorder.canKeepRecording() && this.mRecorder.isCurrentlyRecording() && this.mRecorder.endRelativeTime();
                }

/* 536 */       public static class OnRecordingFinishRunnable implements Runnable {
                    private WeakReference<RecordingFragment> mFragment;

/* 537 */           public OnRecordingFinishRunnable(WeakReference<RecordingFragment> fragment) {
/* 539 */               this.mFragment = fragment;
                    }

                    @Override // java.lang.Runnable
/* 542 */           public void run() {
                        RecordingFile ff;
/* 543 */               RecordingFragment fragment = this.mFragment.get();
/* 544 */               if (fragment != null && (ff = RecordingFragment.access$000(fragment).finalFile) != null && ff.isValid()) {
/* 547 */                   MediaUtil.GenerateThumbnailsRunnable r = RecordingFragment.access$000(fragment).grabThumbnailsRunnable;
/* 548 */                   RecordingFragment.access$000(fragment).grabThumbnailsRunnable = null;
/* 549 */                   RecordingFragment.access$100(fragment, "OnRecordingFinishRunnable", RecordingFragment.access$000(fragment).detectedInvalidSession, r);
                        }
                    }
                }
            }
