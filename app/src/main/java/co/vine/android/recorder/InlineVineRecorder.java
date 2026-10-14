            package co.vine.android.recorder;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.graphics.Point;
            import android.graphics.drawable.Drawable;
            import android.view.View;
            import android.widget.RelativeLayout;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.util.AudioUtils;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 21 */    public class InlineVineRecorder extends RegularVineRecorder {
                public static final boolean METHOD_TRACING = false;
                private boolean mCameraSuccessfullyOpened;
                private ProgressView mFinishProgressView;
                private final int mFinishProgressViewResourceId;
                private final int mPaddingSize;
                private ProcessingListener mProcessingListener;
                private Runnable mStartRelativeTimeRunnable;
                private boolean mStarted;
                private boolean mStopping;
                private static long DELAY_WAIT_FOR_EXPOSURE = 350;
                public static long startnanos = 0;

                public interface ProcessingListener {
                    void onProcessingFinish();

                    void onProcessingStart();
                }

/* 22 */        static /* synthetic */ boolean access$000(InlineVineRecorder x0) {
/* 23 */            return x0.mCameraSuccessfullyOpened;
                }

/* 22 */        static /* synthetic */ ProcessingListener access$100(InlineVineRecorder x0) {
/* 23 */            return x0.mProcessingListener;
                }

/* 22 */        static /* synthetic */ boolean access$202(InlineVineRecorder x0, boolean x1) {
/* 23 */            x0.mStopping = x1;
                    return x1;
                }

/* 22 */        static /* synthetic */ ProgressView access$300(InlineVineRecorder x0) {
/* 23 */            return x0.mFinishProgressView;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.InlineVineRecorder$1 */
/* 34 */        class RunnableC04721 implements Runnable {
/* 35 */            RunnableC04721() {
                    }

                    @Override // java.lang.Runnable
/* 37 */            public void run() {
/* 38 */                InlineVineRecorder.this.startRelativeTime();
                    }
                }

/* 43 */        public void setStarted(boolean started) {
/* 44 */            this.mStarted = started;
                }

/* 47 */        public void cancelStartCallback() {
/* 48 */            this.mHandler.removeCallbacks(this.mStartRelativeTimeRunnable);
                }

/* 60 */        public InlineVineRecorder(RecordSessionVersion version, BaseRecorderPluginManager manager, Point screenSize, Activity activity, ProcessingListener listener, int recorderPadding, int progressViewResourceId, int finishProgressViewResourceId, int cameraViewResourceId, int topMaskId, int bottomMaskId, int openCameraString, int rootLayoutId, Drawable progressDrawable, RegularVineRecorder.DeviceIssueStringGetter deviceNotSupportedString, CharSequence... messages) {
/* 61 */            super(66, manager, screenSize, activity, progressViewResourceId, cameraViewResourceId, topMaskId, bottomMaskId, openCameraString, rootLayoutId, progressDrawable, deviceNotSupportedString, true, version, null, messages);
/* 31 */            this.mCameraSuccessfullyOpened = false;
/* 33 */            this.mStopping = false;
/* 36 */            this.mStartRelativeTimeRunnable = new RunnableC04721();
/* 66 */            this.mProcessingListener = listener;
/* 67 */            this.mPaddingSize = recorderPadding;
/* 68 */            this.mFinishProgressViewResourceId = finishProgressViewResourceId;
                }

/* 71 */        public void stopRecording(boolean cancel) {
/* 72 */            cancelStartCallback();
/* 73 */            if (this.mCurrentSegment != null) {
/* 74 */                endRelativeTime();
                    }
/* 76 */            if (cancel && this.mVideoController != null) {
/* 77 */                this.mVideoController.stop(true, true);
                    }
                }

/* 81 */        public void openCamera() {
                    boolean z = false;
/* 82 */            if (this.mVideoController != null && (this.mVideoController.openDefaultCamera(this.mFrontFacing, false) || this.mVideoController.isCameraReady())) {
                        z = true;
                    }
                    this.mCameraSuccessfullyOpened = z;
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 86 */        public void onUiResumed(Activity activity, Runnable onCompleteAsyncTask, boolean doNotResumeRecorder) {
/* 87 */            super.onUiResumed(activity, onCompleteAsyncTask, doNotResumeRecorder);
/* 88 */            this.mFinishProgressView = (ProgressView) activity.findViewById(this.mFinishProgressViewResourceId);
/* 89 */            if (!doNotResumeRecorder) {
/* 90 */                setDelayDialog(false);
/* 91 */                onResume("UI Resume");
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 96 */        public void onUiPaused() {
/* 97 */            super.onUiPaused();
/* 98 */            this.mStopping = false;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.InlineVineRecorder$2 */
/* 101 */       class AsyncTaskC04732 extends RegularVineRecorder.OnResumeTask {
/* 102 */           AsyncTaskC04732(View x0, String x1) {
                        super(x0, x1);
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.OnResumeTask
/* 105 */           protected boolean onMakingSureCameraReady(RecordController controller) {
/* 106 */               return InlineVineRecorder.access$000(InlineVineRecorder.this);
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 102 */       public BasicVineRecorder.OnResumeTask getOnResumeTask(View view, String tag) {
/* 103 */           return new AsyncTaskC04732(null, tag);
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 112 */       protected void adjustBoundaries(CameraSetting cs) {
/* 113 */           Activity activity = this.mActivity;
/* 114 */           if (activity != null && cs != null) {
/* 117 */               float previewWidth = cs.originalW;
/* 118 */               float previewHeight = cs.originalH;
/* 119 */               float aspectRatio = previewWidth / previewHeight;
/* 120 */               int cameraViewWidth = this.mSize.x - (this.mPaddingSize * 2);
/* 122 */               int cameraViewHeight = (int) (cameraViewWidth * aspectRatio);
/* 124 */               if (this.mTopMaskView != null && this.mBottomMaskView != null) {
/* 125 */                   int bottomMaskHeightPx = this.mBottomMaskView.getMeasuredHeight();
/* 126 */                   int rootHeight = this.mRootLayoutView.getMeasuredHeight();
/* 127 */                   int topMaskHeight = (rootHeight - bottomMaskHeightPx) - this.mSize.x;
/* 129 */                   RelativeLayout.LayoutParams topMaskParams = (RelativeLayout.LayoutParams) this.mTopMaskView.getLayoutParams();
/* 131 */                   topMaskParams.height = topMaskHeight;
/* 133 */                   RelativeLayout.LayoutParams cameraViewParams = (RelativeLayout.LayoutParams) this.mCameraView.getLayoutParams();
/* 135 */                   cameraViewParams.width = cameraViewWidth;
/* 136 */                   cameraViewParams.height = cameraViewHeight;
/* 137 */                   cameraViewParams.leftMargin = this.mPaddingSize;
/* 139 */                   this.mActivity.runOnUiThread(new RunnableC04743(topMaskParams, cameraViewParams));
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.InlineVineRecorder$3 */
/* 138 */       class RunnableC04743 implements Runnable {
                    final /* synthetic */ RelativeLayout.LayoutParams val$cameraViewParams;
                    final /* synthetic */ RelativeLayout.LayoutParams val$topMaskParams;

/* 139 */           RunnableC04743(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
                        this.val$topMaskParams = layoutParams;
                        this.val$cameraViewParams = layoutParams2;
                    }

                    @Override // java.lang.Runnable
/* 142 */           public void run() {
/* 143 */               View view = InlineVineRecorder.this.mTopMaskView;
/* 144 */               if (view != null) {
/* 145 */                   view.setLayoutParams(this.val$topMaskParams);
                        }
/* 148 */               View view2 = InlineVineRecorder.this.mCameraView;
/* 149 */               if (view2 != null) {
/* 150 */                   view2.setLayoutParams(this.val$cameraViewParams);
                        }
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 161 */       public void onProgressMaxReached() {
/* 162 */           finish("progressMaxReached");
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 166 */       public BaseFinishProcessTask getFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 167 */           return new InlineFinishProcessTask(tag, onComplete, releasePreview, saveSession);
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 170 */       protected boolean onStop() {
/* 171 */           boolean result = !this.mStopping;
/* 172 */           this.mStopping = true;
/* 173 */           return result;
                }

/* 175 */       public class InlineFinishProcessTask extends BasicVineRecorder.FinishProcessTask {



/* 178 */           public InlineFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 180 */               super(tag, onComplete, releasePreview, saveSession);
                    }

                    @Override // co.vine.android.recorder.BaseFinishProcessTask, android.os.AsyncTask
/* 184 */           protected void onPreExecute() {
/* 185 */               super.onPreExecute();
/* 186 */               SLog.m446d("Finishing Inline preExec");
/* 187 */               InlineVineRecorder.access$100(InlineVineRecorder.this).onProcessingStart();
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.FinishProcessTask
                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 191 */           protected Void doInBackground(Void... params) {
/* 192 */               SLog.m446d("Finishing Inline");
/* 193 */               return super.doInBackground(params);
                    }

                    @Override // co.vine.android.recorder.BaseFinishProcessTask
                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 197 */           protected void onPostExecute(Void aVoid) {
/* 198 */               super.onPostExecute(aVoid);
/* 199 */               SLog.m446d("Finishing Inline postExec");
/* 200 */               InlineVineRecorder.access$100(InlineVineRecorder.this).onProcessingFinish();
/* 201 */               InlineVineRecorder.access$202(InlineVineRecorder.this, false);
                    }

                    /* JADX INFO: renamed from: onProgressUpdate, reason: avoid collision after fix types in other method */
/* 205 */           protected void onProgressUpdate(Integer... values) {
/* 206 */               if (InlineVineRecorder.access$300(InlineVineRecorder.this) != null) {
/* 207 */                   InlineVineRecorder.access$300(InlineVineRecorder.this).setProgressRatio(values[0].intValue() / 100.0f);
                        }
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 218 */       public void receivedFirstFrameAfterStartingPreview() {
/* 219 */           if (this.mStarted && this.mCurrentSegment == null) {
/* 220 */               long endnannos = System.nanoTime();
/* 221 */               SLog.m442b("inline launch time: {}ms", Long.valueOf((endnannos - startnanos) / AudioUtils.MICROS_PER_SECOND));
/* 222 */               startnanos = endnannos;
/* 223 */               this.mStarted = false;
/* 224 */               this.mHandler.postDelayed(this.mStartRelativeTimeRunnable, DELAY_WAIT_FOR_EXPOSURE);
                    }
                }
            }
