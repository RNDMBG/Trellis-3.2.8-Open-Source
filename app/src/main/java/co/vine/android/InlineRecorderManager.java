            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.graphics.Rect;
            import android.os.Handler;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.recorder.InlineVineRecorder;
            import co.vine.android.recorder.ProgressView;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSession;
            import co.vine.android.recorder.RecordSessionManager;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.recorder.ViewGoneAnimationListener;
            import co.vine.android.recorder.camera.PreviewManager;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class InlineRecorderManager extends RelativeLayout implements InlineVineRecorder.ProcessingListener {
                private static final long CLICK_LENGTH = 200;
                private View mCameraView;
                private boolean mCanInline;
                private boolean mDownWasInRecordButton;
                private ProgressView mFinishProgressView;
                private FinishRunnable mFinishRunnable;
                private ConversationFragment mFragment;
                private Handler mHandler;
                private boolean mIsWaitingForResumeThread;
                private boolean mProcessing;
                private InlineVineRecorder mRecorder;
                private View mRecordingButtonContainer;
                private View mRecordingContainer;
                private ResumeThread mResumeThread;
                private Runnable mStartInlineRecording;
                private TextView mSwipeUpText;
                private View mTextToggle;
                private View mTopMask;
                private RecordSessionVersion mVersion;

/* 35 */        static /* synthetic */ void access$200(InlineRecorderManager x0, boolean x1) {
/* 36 */            x0.toggleRecorder(x1);
                }

/* 35 */        static /* synthetic */ ProgressView access$300(InlineRecorderManager x0) {
/* 36 */            return x0.mFinishProgressView;
                }

/* 35 */        static /* synthetic */ View access$400(InlineRecorderManager x0) {
/* 36 */            return x0.mRecordingButtonContainer;
                }

/* 35 */        static /* synthetic */ ConversationFragment access$500(InlineRecorderManager x0) {
/* 36 */            return x0.mFragment;
                }

/* 35 */        static /* synthetic */ InlineVineRecorder access$600(InlineRecorderManager x0) {
/* 36 */            return x0.mRecorder;
                }

/* 35 */        static /* synthetic */ RecordingFile access$700(InlineRecorderManager x0) {
/* 36 */            return x0.getNewMessageRecordingFile();
                }

/* 35 */        static /* synthetic */ View access$800(InlineRecorderManager x0) {
/* 36 */            return x0.mTextToggle;
                }

/* 35 */        static /* synthetic */ boolean access$902(InlineRecorderManager x0, boolean x1) {
/* 36 */            x0.mIsWaitingForResumeThread = x1;
                    return x1;
                }

/* 57 */        public InlineRecorderManager(Context context) {
/* 58 */            super(context);
/* 41 */            this.mStartInlineRecording = new StartInlineRecording(this, null);
/* 42 */            this.mFinishRunnable = new FinishRunnable(this, null);
                }

/* 61 */        public InlineRecorderManager(Context context, AttributeSet attrs) {
/* 62 */            super(context, attrs);
/* 41 */            this.mStartInlineRecording = new StartInlineRecording(this, null);
/* 42 */            this.mFinishRunnable = new FinishRunnable(this, null);
                }

/* 65 */        public InlineRecorderManager(Context context, AttributeSet attrs, int defStyle) {
/* 66 */            super(context, attrs, defStyle);
/* 41 */            this.mStartInlineRecording = new StartInlineRecording(this, null);
/* 42 */            this.mFinishRunnable = new FinishRunnable(this, null);
                }

                @Override // android.view.View
/* 69 */        protected void onFinishInflate() {
/* 70 */            super.onFinishInflate();
/* 71 */            this.mRecordingButtonContainer = findViewById(C0257R.id.record_button_container);
/* 72 */            this.mRecordingContainer = findViewById(C0257R.id.inline_record_root);
/* 73 */            this.mFinishProgressView = (ProgressView) findViewById(C0257R.id.finishing_progress);
/* 74 */            this.mTopMask = findViewById(C0257R.id.top_mask);
/* 75 */            this.mCameraView = findViewById(C0257R.id.cameraView);
                }

/* 78 */        public void initDisabled(ConversationFragment fragment) {
/* 79 */            this.mFragment = fragment;
                }

/* 83 */        public void init(ConversationFragment fragment, ConversationActivity activity, RecordSessionVersion version) {
/* 84 */            this.mFragment = fragment;
/* 85 */            this.mCanInline = RecordConfigUtils.isCapableOfInline(activity);
/* 86 */            View root = this.mFragment.getView();
/* 87 */            this.mTextToggle = root.findViewById(C0257R.id.text_input_toggle);
/* 88 */            this.mSwipeUpText = (TextView) root.findViewById(C0257R.id.swipe_up);
/* 89 */            this.mVersion = version;
/* 90 */            if (this.mCanInline) {
/* 91 */                this.mRecorder = new InlineVineRecorder(version, new BaseRecorderPluginManager(), SystemUtil.getDisplaySize((Activity) activity), activity, this, activity.getResources().getDimensionPixelSize(C0257R.dimen.vm_record_margin), C0257R.id.progress_inline, C0257R.id.finishing_progress, C0257R.id.cameraView, C0257R.id.top_mask, C0257R.id.bottom_mask, C0257R.string.opening_camera, C0257R.id.layout_root, activity.getResources().getDrawable(C0257R.drawable.progress_horizontal), AbstractRecordingActivity.getDeviceIssueStringGetter(activity), this.mFragment.getText(C0257R.string.getting_ready), this.mFragment.getText(C0257R.string.finish_processing_1), this.mFragment.getText(C0257R.string.finish_processing_2), this.mFragment.getText(C0257R.string.finish_processing_3));
                    }
                }

/* 104 */       private RecordingFile getNewMessageRecordingFile() {
                    try {
/* 105 */               RecordSessionManager rsm = this.mVersion.getManager(this.mFragment.getActivity());
/* 106 */               File folder = rsm.createFolderForSession();
/* 107 */               return new RecordingFile(folder, RecordSession.newSession(new RecordConfigUtils.RecordConfig(this.mFragment.getActivity()), this.mVersion), false, false, false);
                    } catch (IOException e) {
/* 111 */               throw new RuntimeException(e);
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 116 */       protected void onAttachedToWindow() {
/* 117 */           this.mHandler = getHandler();
                }

                @Override // android.view.ViewGroup
/* 121 */       public boolean onInterceptTouchEvent(MotionEvent event) {
/* 122 */           return isInRecordButton(event);
                }

/* 125 */       private boolean isInRecordButton(MotionEvent event) {
/* 126 */           if (this.mFragment == null || this.mFragment.isInTextInputMode()) {
/* 127 */               return false;
                    }
/* 130 */           ViewGroup buttonParent = (ViewGroup) this.mRecordingButtonContainer.getParent();
/* 131 */           Rect hitRect = new Rect();
/* 132 */           this.mRecordingButtonContainer.getHitRect(hitRect);
/* 133 */           hitRect.offset(buttonParent.getLeft(), buttonParent.getTop());
/* 134 */           return hitRect.contains((int) event.getX(), (int) event.getY());
                }

                @Override // android.view.View
/* 140 */       public boolean onTouchEvent(@NonNull MotionEvent event) {
                    switch (event.getAction()) {
                        case 0:
/* 143 */                   if (!this.mProcessing && isInRecordButton(event) && this.mRecordingContainer.getVisibility() != 0) {
/* 144 */                       this.mDownWasInRecordButton = true;
/* 145 */                       if (this.mRecorder == null) {
                                    return true;
                                }
/* 148 */                       this.mSwipeUpText.setTextColor(getResources().getColor(C0257R.color.solid_light_gray_vm));
/* 149 */                       this.mRecorder.setStarted(true);
/* 150 */                       waitForResume();
/* 151 */                       this.mRecorder.openCamera();
/* 152 */                       this.mHandler.postDelayed(this.mStartInlineRecording, 200L);
                                return true;
                            }
/* 154 */                   if (this.mRecordingContainer.getVisibility() != 0) {
                                return false;
                            }
/* 155 */                   this.mDownWasInRecordButton = false;
/* 156 */                   this.mRecorder.setStarted(false);
                            return true;
                        case 1:
/* 175 */                   if (this.mDownWasInRecordButton && this.mRecorder == null) {
/* 176 */                       this.mDownWasInRecordButton = false;
/* 177 */                       this.mFragment.launchFullRecord();
                                return true;
                            }
/* 180 */                   waitForResume();
/* 181 */                   this.mHandler.removeCallbacks(this.mStartInlineRecording);
/* 182 */                   if (this.mDownWasInRecordButton && event.getEventTime() - event.getDownTime() <= 200 && this.mRecordingContainer.getVisibility() != 0) {
/* 184 */                       this.mDownWasInRecordButton = false;
/* 185 */                       this.mRecorder.setStarted(false);
/* 186 */                       this.mFragment.launchFullRecord();
                                return true;
                            }
/* 188 */                   if (this.mRecordingContainer.getVisibility() != 0 || !this.mDownWasInRecordButton || this.mRecorder.isFinished()) {
                                return false;
                            }
/* 190 */                   if (isInCancelZone(event.getY())) {
/* 191 */                       if (this.mRecorder.isRecordingSegment()) {
/* 192 */                           this.mRecorder.stopRecording(true);
                                } else {
/* 194 */                           this.mRecorder.cancelStartCallback();
                                }
/* 196 */                       this.mRecorder.deleteCurrentDraftFolder("inline action up.");
/* 197 */                       this.mRecorder.swapSession("Inline record", getNewMessageRecordingFile());
/* 198 */                       toggleRecorder(false);
/* 199 */                       this.mTextToggle.setEnabled(true);
/* 200 */                       this.mFinishProgressView.animate().alpha(0.0f).setListener(new ViewGoneAnimationListener(this.mFinishProgressView)).start();
                            } else if (this.mRecorder.isRecordingSegment()) {
/* 204 */                       this.mRecorder.stopRecording(false);
/* 205 */                       this.mRecorder.onFinishPressed();
                            } else {
/* 207 */                       this.mRecorder.stopRecording(true);
/* 208 */                       if (this.mRecorder.hasSessionFile()) {
/* 209 */                           this.mRecorder.deleteCurrentDraftFolder("inline action up.");
                                }
/* 211 */                       this.mRecorder.swapSession("Inline record", getNewMessageRecordingFile());
/* 212 */                       toggleRecorder(false);
/* 213 */                       this.mTextToggle.setEnabled(true);
/* 214 */                       this.mFinishProgressView.animate().alpha(0.0f).setListener(new ViewGoneAnimationListener(this.mFinishProgressView)).start();
                            }
/* 218 */                   this.mTextToggle.setVisibility(0);
                            return true;
                        case 2:
/* 163 */                   if (this.mRecorder == null) {
/* 164 */                       return this.mDownWasInRecordButton;
                            }
/* 166 */                   if (isInCancelZone(event.getY())) {
/* 167 */                       this.mSwipeUpText.setTextColor(this.mFragment.getMyColor());
                            } else {
/* 169 */                       this.mSwipeUpText.setTextColor(getResources().getColor(C0257R.color.solid_light_gray_vm));
                            }
/* 172 */                   return this.mDownWasInRecordButton;
                        default:
                            return false;
                    }
                }

/* 227 */       private boolean isInCancelZone(float y) {
/* 228 */           ViewGroup buttonParent = (ViewGroup) this.mRecordingButtonContainer.getParent();
                    return y < ((float) (getMeasuredHeight() - (buttonParent.getHeight() * 2)));
                }

                @Override // co.vine.android.recorder.InlineVineRecorder.ProcessingListener
/* 233 */       public void onProcessingStart() {
/* 234 */           toggleRecorder(false);
/* 235 */           this.mProcessing = true;
/* 236 */           this.mRecordingButtonContainer.animate().alpha(0.0f).setListener(new ViewGoneAnimationListener(this.mRecordingButtonContainer));
                }

                @Override // co.vine.android.recorder.InlineVineRecorder.ProcessingListener
/* 241 */       public void onProcessingFinish() {
/* 242 */           this.mProcessing = false;
/* 243 */           this.mRecordingButtonContainer.setAlpha(0.0f);
/* 244 */           this.mRecordingButtonContainer.setVisibility(0);
/* 245 */           this.mRecordingButtonContainer.animate().alpha(1.0f).start();
                }

/* 246 */       private class StartInlineRecording implements Runnable {
/* 247 */           private StartInlineRecording() {
                    }

/* 247 */           /* synthetic */ StartInlineRecording(InlineRecorderManager x0, Object x1) {
/* 248 */               this();
                    }

                    @Override // java.lang.Runnable
/* 250 */           public void run() {
/* 251 */               InlineRecorderManager.access$200(InlineRecorderManager.this, true);
/* 252 */               InlineRecorderManager.access$300(InlineRecorderManager.this).setVisibility(0);
/* 253 */               InlineRecorderManager.access$300(InlineRecorderManager.this).setAlpha(1.0f);
/* 254 */               InlineRecorderManager.access$300(InlineRecorderManager.this).setProgressRatio(0.0f);
                    }
                }

/* 258 */       private void toggleRecorder(boolean show) {
/* 259 */           if (this.mCanInline) {
/* 260 */               if (show) {
/* 264 */                   this.mCameraView.setVisibility(0);
/* 265 */                   InlineVineRecorder.startnanos = System.nanoTime();
/* 266 */                   this.mRecordingContainer.setAlpha(0.01f);
/* 267 */                   this.mRecordingContainer.setVisibility(0);
/* 268 */                   this.mRecordingContainer.animate().alpha(1.0f).setDuration(250L).start();
/* 269 */                   this.mTopMask.setVisibility(0);
/* 270 */                   this.mTextToggle.setVisibility(8);
/* 271 */                   this.mTextToggle.setEnabled(false);
/* 272 */                   this.mRecorder.setDelayDialog(true);
/* 273 */                   this.mRecorder.onResume("start");
/* 274 */                   this.mFragment.onRecorderShown();
/* 281 */                   return;
                        }
/* 276 */               this.mRecordingContainer.animate().alpha(0.0f).setListener(new ViewGoneAnimationListener(this.mRecordingContainer)).start();
/* 277 */               this.mFragment.onRecorderHidden();
/* 278 */               this.mRecordingButtonContainer.setVisibility(0);
                    }
                }

/* 283 */       private void waitForResume() {
/* 284 */           if (this.mIsWaitingForResumeThread) {
                        try {
/* 286 */                   this.mResumeThread.join();
                        } catch (InterruptedException e) {
/* 288 */                   SLog.m455e("You can't interrupt this.");
                        }
                    }
                }

/* 291 */       private class FinishRunnable implements Runnable {
/* 292 */           private FinishRunnable() {
                    }

/* 292 */           /* synthetic */ FinishRunnable(InlineRecorderManager x0, Object x1) {
/* 293 */               this();
                    }

                    @Override // java.lang.Runnable
/* 295 */           public void run() {
/* 296 */               InlineRecorderManager.access$400(InlineRecorderManager.this).setVisibility(0);
/* 297 */               InlineRecorderManager.access$500(InlineRecorderManager.this).prepareUpload();
/* 298 */               InlineRecorderManager.access$600(InlineRecorderManager.this).deleteCurrentDraftFolder("inline finish.");
/* 299 */               InlineRecorderManager.access$600(InlineRecorderManager.this).swapSession("Inline record", InlineRecorderManager.access$700(InlineRecorderManager.this));
/* 300 */               InlineRecorderManager.access$800(InlineRecorderManager.this).setVisibility(0);
/* 301 */               InlineRecorderManager.access$800(InlineRecorderManager.this).setEnabled(true);
/* 302 */               InlineRecorderManager.access$300(InlineRecorderManager.this).animate().alpha(0.0f).setListener(new ViewGoneAnimationListener(InlineRecorderManager.access$300(InlineRecorderManager.this))).start();
                    }
                }

/* 307 */       InlineVineRecorder getRecorder() {
/* 308 */           return this.mRecorder;
                }

/* 311 */       public void onResume() throws IOException {
/* 312 */           if (this.mCanInline) {
                        try {
/* 314 */                   this.mResumeThread = new ResumeThread();
/* 321 */                   this.mRecorder.setCameraView(this.mCameraView);
/* 322 */                   this.mRecorder.onUiResumed(this.mFragment.getActivity(), this.mFinishRunnable, true);
/* 323 */                   this.mIsWaitingForResumeThread = true;
/* 324 */                   this.mResumeThread.start();
                        } catch (IOException e) {
/* 316 */                   this.mCanInline = false;
/* 317 */                   this.mRecorder.release();
/* 318 */                   this.mRecorder = null;
/* 319 */                   throw e;
                        }
                    }
                }

/* 330 */       public class ResumeThread extends Thread {
                    private RecordingFile mFileToResume;

/* 331 */           public ResumeThread() throws IOException {
                        try {
/* 334 */                   this.mFileToResume = InlineRecorderManager.access$700(InlineRecorderManager.this);
                        } catch (RuntimeException e) {
/* 336 */                   throw new IOException("Failed to create new recording file.");
                        }
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 342 */           public void run() {
                        try {
/* 343 */                   Thread.sleep(1500L);
                        } catch (InterruptedException e) {
/* 345 */                   SLog.m455e("Failed to sleep wait.");
                        }
/* 347 */               long start = System.currentTimeMillis();
/* 348 */               SLog.m446d("Start resuming for inline.");
/* 349 */               InlineRecorderManager.access$600(InlineRecorderManager.this).initPreviewSurface();
/* 350 */               InlineRecorderManager.access$600(InlineRecorderManager.this).swapSession("Inline record", this.mFileToResume);
/* 351 */               this.mFileToResume = null;
/* 352 */               InlineRecorderManager.access$600(InlineRecorderManager.this).start("Inline record", false, false);
/* 353 */               InlineRecorderManager.access$902(InlineRecorderManager.this, false);
/* 354 */               CrashUtil.log("Inline resume took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                    }
                }

/* 359 */       public void onPaused() {
/* 360 */           PreviewManager.getInstance().cancelPendingPreviews();
/* 361 */           if (this.mCanInline) {
/* 362 */               waitForResume();
/* 363 */               if (this.mRecorder != null) {
/* 364 */                   this.mRecorder.setStarted(false);
/* 365 */                   this.mRecorder.onUiPaused();
                        }
                    }
                }

/* 370 */       public void release() {
/* 371 */           if (this.mCanInline) {
/* 372 */               waitForResume();
/* 373 */               this.mRecorder.stopAndDiscardChanges("release", null, true);
/* 374 */               this.mRecorder.release();
                    }
                }
            }
