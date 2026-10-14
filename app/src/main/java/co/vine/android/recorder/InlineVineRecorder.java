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
            
            
            @TargetApi(14)
    public class InlineVineRecorder extends RegularVineRecorder {
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

        static  boolean access$000(InlineVineRecorder x0) {
            return x0.mCameraSuccessfullyOpened;
                }

        static  ProcessingListener access$100(InlineVineRecorder x0) {
            return x0.mProcessingListener;
                }

        static  boolean access$202(InlineVineRecorder x0, boolean x1) {
            x0.mStopping = x1;
                    return x1;
                }

        static  ProgressView access$300(InlineVineRecorder x0) {
            return x0.mFinishProgressView;
                }

                
        class RunnableC04721 implements Runnable {
            RunnableC04721() {
                    }

                    @Override 
            public void run() {
                InlineVineRecorder.this.startRelativeTime();
                    }
                }

        public void setStarted(boolean started) {
            this.mStarted = started;
                }

        public void cancelStartCallback() {
            this.mHandler.removeCallbacks(this.mStartRelativeTimeRunnable);
                }

        public InlineVineRecorder(RecordSessionVersion version, BaseRecorderPluginManager manager, Point screenSize, Activity activity, ProcessingListener listener, int recorderPadding, int progressViewResourceId, int finishProgressViewResourceId, int cameraViewResourceId, int topMaskId, int bottomMaskId, int openCameraString, int rootLayoutId, Drawable progressDrawable, RegularVineRecorder.DeviceIssueStringGetter deviceNotSupportedString, CharSequence... messages) {
            super(66, manager, screenSize, activity, progressViewResourceId, cameraViewResourceId, topMaskId, bottomMaskId, openCameraString, rootLayoutId, progressDrawable, deviceNotSupportedString, true, version, null, messages);
            this.mCameraSuccessfullyOpened = false;
            this.mStopping = false;
            this.mStartRelativeTimeRunnable = new RunnableC04721();
            this.mProcessingListener = listener;
            this.mPaddingSize = recorderPadding;
            this.mFinishProgressViewResourceId = finishProgressViewResourceId;
                }

        public void stopRecording(boolean cancel) {
            cancelStartCallback();
            if (this.mCurrentSegment != null) {
                endRelativeTime();
                    }
            if (cancel && this.mVideoController != null) {
                this.mVideoController.stop(true, true);
                    }
                }

        public void openCamera() {
                    boolean z = false;
            if (this.mVideoController != null && (this.mVideoController.openDefaultCamera(this.mFrontFacing, false) || this.mVideoController.isCameraReady())) {
                        z = true;
                    }
                    this.mCameraSuccessfullyOpened = z;
                }

                @Override 
        public void onUiResumed(Activity activity, Runnable onCompleteAsyncTask, boolean doNotResumeRecorder) {
            super.onUiResumed(activity, onCompleteAsyncTask, doNotResumeRecorder);
            this.mFinishProgressView = (ProgressView) activity.findViewById(this.mFinishProgressViewResourceId);
            if (!doNotResumeRecorder) {
                setDelayDialog(false);
                onResume("UI Resume");
                    }
                }

                @Override 
        public void onUiPaused() {
            super.onUiPaused();
            this.mStopping = false;
                }

                
       class AsyncTaskC04732 extends RegularVineRecorder.OnResumeTask {
           AsyncTaskC04732(View x0, String x1) {
                        super(x0, x1);
                    }

                    @Override 
           protected boolean onMakingSureCameraReady(RecordController controller) {
               return InlineVineRecorder.access$000(InlineVineRecorder.this);
                    }
                }

                @Override 
       public BasicVineRecorder.OnResumeTask getOnResumeTask(View view, String tag) {
           return new AsyncTaskC04732(null, tag);
                }

                @Override 
       protected void adjustBoundaries(CameraSetting cs) {
           Activity activity = this.mActivity;
           if (activity != null && cs != null) {
               float previewWidth = cs.originalW;
               float previewHeight = cs.originalH;
               float aspectRatio = previewWidth / previewHeight;
               int cameraViewWidth = this.mSize.x - (this.mPaddingSize * 2);
               int cameraViewHeight = (int) (cameraViewWidth * aspectRatio);
               if (this.mTopMaskView != null && this.mBottomMaskView != null) {
                   int bottomMaskHeightPx = this.mBottomMaskView.getMeasuredHeight();
                   int rootHeight = this.mRootLayoutView.getMeasuredHeight();
                   int topMaskHeight = (rootHeight - bottomMaskHeightPx) - this.mSize.x;
                   RelativeLayout.LayoutParams topMaskParams = (RelativeLayout.LayoutParams) this.mTopMaskView.getLayoutParams();
                   topMaskParams.height = topMaskHeight;
                   RelativeLayout.LayoutParams cameraViewParams = (RelativeLayout.LayoutParams) this.mCameraView.getLayoutParams();
                   cameraViewParams.width = cameraViewWidth;
                   cameraViewParams.height = cameraViewHeight;
                   cameraViewParams.leftMargin = this.mPaddingSize;
                   this.mActivity.runOnUiThread(new RunnableC04743(topMaskParams, cameraViewParams));
                        }
                    }
                }

                
       class RunnableC04743 implements Runnable {
                    final  RelativeLayout.LayoutParams val$cameraViewParams;
                    final  RelativeLayout.LayoutParams val$topMaskParams;

           RunnableC04743(RelativeLayout.LayoutParams layoutParams, RelativeLayout.LayoutParams layoutParams2) {
                        this.val$topMaskParams = layoutParams;
                        this.val$cameraViewParams = layoutParams2;
                    }

                    @Override 
           public void run() {
               View view = InlineVineRecorder.this.mTopMaskView;
               if (view != null) {
                   view.setLayoutParams(this.val$topMaskParams);
                        }
               View view2 = InlineVineRecorder.this.mCameraView;
               if (view2 != null) {
                   view2.setLayoutParams(this.val$cameraViewParams);
                        }
                    }
                }

                @Override 
       public void onProgressMaxReached() {
           finish("progressMaxReached");
                }

                @Override 
       public BaseFinishProcessTask getFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
           return new InlineFinishProcessTask(tag, onComplete, releasePreview, saveSession);
                }

                @Override 
       protected boolean onStop() {
           boolean result = !this.mStopping;
           this.mStopping = true;
           return result;
                }

       public class InlineFinishProcessTask extends BasicVineRecorder.FinishProcessTask {



           public InlineFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
               super(tag, onComplete, releasePreview, saveSession);
                    }

                    @Override 
           protected void onPreExecute() {
               super.onPreExecute();
               SLog.m446d("Finishing Inline preExec");
               InlineVineRecorder.access$100(InlineVineRecorder.this).onProcessingStart();
                    }

                    @Override 
                    
           protected Void doInBackground(Void... params) {
               SLog.m446d("Finishing Inline");
               return super.doInBackground(params);
                    }

                    @Override 
                    
           protected void onPostExecute(Void aVoid) {
               super.onPostExecute(aVoid);
               SLog.m446d("Finishing Inline postExec");
               InlineVineRecorder.access$100(InlineVineRecorder.this).onProcessingFinish();
               InlineVineRecorder.access$202(InlineVineRecorder.this, false);
                    }

                    
           protected void onProgressUpdate(Integer... values) {
               if (InlineVineRecorder.access$300(InlineVineRecorder.this) != null) {
                   InlineVineRecorder.access$300(InlineVineRecorder.this).setProgressRatio(values[0].intValue() / 100.0f);
                        }
                    }
                }

                @Override 
       public void receivedFirstFrameAfterStartingPreview() {
           if (this.mStarted && this.mCurrentSegment == null) {
               long endnannos = System.nanoTime();
               SLog.m442b("inline launch time: {}ms", Long.valueOf((endnannos - startnanos) / AudioUtils.MICROS_PER_SECOND));
               startnanos = endnannos;
               this.mStarted = false;
               this.mHandler.postDelayed(this.mStartRelativeTimeRunnable, DELAY_WAIT_FOR_EXPOSURE);
                    }
                }
            }
