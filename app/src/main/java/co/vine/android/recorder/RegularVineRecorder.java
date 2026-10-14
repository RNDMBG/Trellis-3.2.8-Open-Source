            package co.vine.android.recorder;

            import android.annotation.SuppressLint;
            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.graphics.Point;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            import android.view.MotionEvent;
            import android.view.View;
            import android.widget.Toast;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            
            
    public abstract class RegularVineRecorder extends BasicVineRecorder implements View.OnTouchListener {
                private static final String PREF_BOTTOM_MASK_HEIGHT = "bottomMaskHeightPx";
                protected static final float TOUCH_EDGE_BOUNDARY = 0.05f;
                public float density;
                protected int mBottomMaskId;
                protected View mBottomMaskView;
                protected int mCameraViewResourceId;
                protected DeviceIssueStringGetter mDeviceNotSupportedString;
                protected CharSequence mFinishLastSegmentString;
                protected ProgressDialog mFinishProgressDialog;
                protected CharSequence[] mFinishProgressDialogMessage;
                private CharSequence mImportingString;
                private boolean mIsHidingFinishProgressDialog;
                protected ProgressDialog mOpenCameraDialog;
                protected CharSequence mOpenCameraResource;
                private Drawable mProgressDrawable;
                protected ProgressView mProgressView;
                protected int mProgressViewResourceId;
                protected int mRootLayoutId;
                protected View mRootLayoutView;
                protected Point mSize;
                protected ProgressDialog mStartProgressDialog;
                protected CharSequence mStartProgressDialogMessage;
                protected Toast mToast;
                protected int mTopMaskId;
                protected View mTopMaskView;

                public interface DeviceIssueStringGetter {
                    String getString(DeviceIssue deviceIssue);
                }

        static  boolean access$002(RegularVineRecorder x0, boolean x1) {
            x0.mIsHidingFinishProgressDialog = x1;
                    return x1;
                }

        public RegularVineRecorder(int thresholdMs, BaseRecorderPluginManager manager, Point screenSize, Activity activity, int progressViewResourceId, int cameraViewResourceId, int topMaskId, int bottomMaskId, int openCameraString, int rootLayoutId, Drawable progressDrawable, DeviceIssueStringGetter deviceNotSupportedString, boolean startWithFrontFacingCamera, RecordSessionVersion version, CharSequence importingString, CharSequence... messages) {
            super(thresholdMs, manager, activity, startWithFrontFacingCamera, version == RecordSessionVersion.HW, version);
            this.mStartProgressDialogMessage = messages[0];
            this.mFinishProgressDialogMessage = messages;
            this.mOpenCameraResource = activity.getText(openCameraString);
            this.mSize = screenSize;
            this.density = activity.getResources().getDisplayMetrics().density;
            this.mProgressViewResourceId = progressViewResourceId;
            this.mCameraViewResourceId = cameraViewResourceId;
            this.mRootLayoutId = rootLayoutId;
            this.mDeviceNotSupportedString = deviceNotSupportedString;
            this.mTopMaskId = topMaskId;
            this.mBottomMaskId = bottomMaskId;
            this.mImportingString = importingString;
            this.mProgressDrawable = progressDrawable;
                }

                
        class RunnableC04831 implements Runnable {
            RunnableC04831() {
                    }

                    @Override 
            public void run() {
                if (RegularVineRecorder.this.mToast != null) {
                    RegularVineRecorder.this.mToast.show();
                        }
                    }
                }

                @Override 
        protected void showCameraFailedToast() {
            this.mHandler.post(new RunnableC04831());
                }

        protected class ResumeCameraAsyncTask extends BasicVineRecorder.ResumeCameraAsyncTask {

            public ResumeCameraAsyncTask(boolean switchCamera) {
               super(switchCamera);
                    }

                    @Override 
           protected void onPreExecute() {
               super.onPreExecute();
               if (RegularVineRecorder.this.mOpenCameraDialog != null) {
                   RegularVineRecorder.this.mOpenCameraDialog.show();
                        }
                    }

                    @Override 
                    
           protected void onPostExecute(Void aVoid) {
               super.onPostExecute(aVoid);
               if (RegularVineRecorder.this.mOpenCameraDialog != null) {
                   RegularVineRecorder.this.mOpenCameraDialog.dismiss();
                        }
                    }
                }

       protected class OnResumeTask extends BasicVineRecorder.OnResumeTask {


           public OnResumeTask(View view, String tag) {
               super(view, tag);
                    }

                    @Override 
           protected void publishFinishLastSegmentProgress() {
               publishProgress(new CharSequence[]{RegularVineRecorder.this.mFinishLastSegmentString});
                    }

                    @Override 
           protected void publishStartProgres() {
               publishProgress(new CharSequence[]{RegularVineRecorder.this.mStartProgressDialogMessage});
                    }

                    
           protected void onProgressUpdate(CharSequence... values) {
               if (RegularVineRecorder.this.mStartProgressDialog != null) {
                   if (!this.showDialogDelayed && !RegularVineRecorder.this.mStartProgressDialog.isShowing()) {
                       RegularVineRecorder.this.mStartProgressDialog.show();
                            }
                   RegularVineRecorder.this.mStartProgressDialog.setMessage(values[0]);
                        }
                    }

                    
                    @Override 
           protected void onPostExecute(RecordingFile needHashTask) {
               super.onPostExecute(needHashTask);
               if (RegularVineRecorder.this.mStartProgressDialog != null && !isCancelled()) {
                   RegularVineRecorder.this.mStartProgressDialog.dismiss();
                        }
               RegularVineRecorder.this.onResumeTaskComplete();
                    }
                }

       protected void onResumeTaskComplete() {
                }

       public CharSequence getFinishLastSegmentString() {
           return this.mFinishLastSegmentString;
                }

       public CharSequence getImportingString() {
           return this.mImportingString;
                }

       public Point getSize() {
           return this.mSize;
                }

                @Override 
       public void changeProgress(long progress) {
           ProgressView view = this.mProgressView;
           if (view != null && this.mSession != null) {
               view.setProgressRatio(progress / (1.0f * this.mSession.getConfig().maxDuration));
               if (SystemUtil.isOnMainThread()) {
                   view.invalidate();
                        } else {
                   view.postInvalidate();
                        }
                    }
                }

       public static String getBottomMaskHeightPref(boolean frontFacing) {
           return PREF_BOTTOM_MASK_HEIGHT + frontFacing;
                }

                @Override 
       public synchronized boolean onTouch(View v, MotionEvent event) {
                    boolean z = true;
                    synchronized (this) {
                        try {
                            int action = event.getAction();
                   if (this.mCurrentRecordingFile == null || !this.mEnabled) {
                                z = false;
                            } else if (this.mAutoFocusing) {
                                switch (action) {
                                    case 0:
                               float axisX = event.getAxisValue(0);
                                        if (axisX > TOUCH_EDGE_BOUNDARY * this.mSize.x && axisX < 0.95f * this.mSize.x) {
                                   startRelativeTime();
                                        }
                                        break;
                                    case 1:
                               endRelativeTime();
                                        break;
                                }
                            } else {
                                switch (action) {
                                    case 1:
                               changeFocusTo(event.getAxisValue(0), event.getAxisValue(1));
                                        break;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                @Override 
       public void onUiPaused() {
           if (this.mCameraView != null) {
               this.mCameraView.setOnTouchListener(null);
                    }
                    try {
               if (this.mFinishProgressDialog != null) {
                   this.mFinishProgressDialog.dismiss();
                        }
               if (this.mStartProgressDialog != null) {
                   this.mStartProgressDialog.dismiss();
                        }
               if (this.mOpenCameraDialog != null) {
                   this.mOpenCameraDialog.dismiss();
                        }
                    } catch (Exception e) {
               SLog.m459e("It's probably detached already.", (Throwable) e);
                    }
           this.mStartProgressDialog = null;
           this.mFinishProgressDialog = null;
           this.mOpenCameraDialog = null;
           this.mTopMaskView = null;
           this.mBottomMaskView = null;
           this.mProgressView = null;
           this.mToast = null;
           super.onUiPaused();
                }

                @Override 
       protected boolean onStop() {
           if (this.mFastEncoding) {
               if (!this.mIsHidingFinishProgressDialog) {
                   this.mIsHidingFinishProgressDialog = true;
                            return true;
                        }
               SLog.m446d("Stop twice? wtf.");
                        return false;
                    }
           ProgressDialog dialog = this.mFinishProgressDialog;
           if (dialog != null && !dialog.isShowing()) {
               this.mActivity.runOnUiThread(new RunnableC04842(dialog));
                        return true;
                    }
           SLog.m446d("Stop twice? wtf.");
                    return false;
                }

                
       class RunnableC04842 implements Runnable {
                    final  ProgressDialog val$dialog;

           RunnableC04842(ProgressDialog progressDialog) {
                        this.val$dialog = progressDialog;
                    }

                    @Override 
           public void run() {
               this.val$dialog.show();
                    }
                }

                @Override 
       public BaseFinishProcessTask getFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
           return new FinishProcessTask(tag, onComplete, releasePreview, saveSession);
                }

       public class FinishProcessTask extends BasicVineRecorder.FinishProcessTask {


           public FinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
               super(tag, onComplete, releasePreview, saveSession);
                    }

                    @Override 
                    
           protected void onPostExecute(Void aVoid) {
               super.onPostExecute(aVoid);
               RegularVineRecorder.access$002(RegularVineRecorder.this, false);
               ProgressDialog dialog = RegularVineRecorder.this.mFinishProgressDialog;
               if (dialog != null && dialog.isShowing()) {
                   dialog.dismiss();
                        }
                    }

                    
           protected void onProgressUpdate(Integer... values) {
               ProgressDialog dialog = RegularVineRecorder.this.mFinishProgressDialog;
               if (dialog != null && dialog.isShowing()) {
                   if (RegularVineRecorder.this.mFinishProgressDialogMessage != null && RegularVineRecorder.this.mFinishProgressDialogMessage.length > 2 && values[0].intValue() >= (100 / (RegularVineRecorder.this.mFinishProgressDialogMessage.length - 1)) * this.mCurrentMessage) {
                       if (this.mCurrentMessage + 1 < RegularVineRecorder.this.mFinishProgressDialogMessage.length) {
                           dialog.setMessage(RegularVineRecorder.this.mFinishProgressDialogMessage[this.mCurrentMessage + 1]);
                                }
                       this.mCurrentMessage++;
                            }
                   dialog.setProgress(values[0].intValue());
                        }
                    }
                }

                
       class RunnableC04853 implements Runnable {
                    final  DeviceIssue val$issue;

           RunnableC04853(DeviceIssue deviceIssue) {
                        this.val$issue = deviceIssue;
                    }

                    
           class AnonymousClass1 implements Runnable {
               AnonymousClass1() {
                        }

                        @Override 
               public void run() {
                   if (RegularVineRecorder.this.mActivity != null) {
                       Toast.makeText(RegularVineRecorder.this.mActivity, RegularVineRecorder.this.mDeviceNotSupportedString.getString(RunnableC04853.this.val$issue), 0).show();
                       RegularVineRecorder.this.mActivity.finish();
                            }
                        }
                    }

                    @Override 
           public void run() {
               RegularVineRecorder.this.mHandler.post(new AnonymousClass1());
                    }
                }

                @Override 
                @NonNull
       protected Runnable getOnDeviceIssueAction(DeviceIssue issue) {
           return new RunnableC04853(issue);
                }

                @Override 
       public void stopAndDiscardChanges(String tag, Runnable onComplete, boolean releasePreview) {
           setDiscardChanges(true);
           this.mFinishProcessRunnable.run(tag, releasePreview, false);
           if (onComplete != null) {
               onComplete.run();
                    }
                }

                @Override 
                @SuppressLint({"ShowToast"})
       public void onUiResumed(Activity activity, Runnable onCompleteAsyncTask, boolean doNotResumeRecorder) {
           super.onUiResumed(activity, onCompleteAsyncTask, doNotResumeRecorder);
           this.mFinishProgressDialog = new ProgressDialog(activity, 2);
           this.mFinishProgressDialog.setMessage(this.mFinishProgressDialogMessage[1]);
           this.mFinishProgressDialog.setMax(100);
           this.mFinishProgressDialog.setCancelable(false);
           this.mFinishProgressDialog.setIndeterminate(false);
           this.mFinishProgressDialog.setProgressStyle(1);
           this.mFinishProgressDialog.setProgressDrawable(this.mProgressDrawable);
           this.mStartProgressDialog = new ProgressDialog(activity, 2);
           this.mOpenCameraDialog = new ProgressDialog(activity, 2);
           this.mOpenCameraDialog.setMessage(this.mOpenCameraResource);
           this.mOpenCameraDialog.setCancelable(false);
           this.mRootLayoutView = activity.findViewById(this.mRootLayoutId);
           this.mCameraView = activity.findViewById(this.mCameraViewResourceId);
           this.mCameraView.setVisibility(0);
           this.mCameraView.setOnTouchListener(this);
           this.mTopMaskView = activity.findViewById(this.mTopMaskId);
           this.mBottomMaskView = activity.findViewById(this.mBottomMaskId);
           this.mProgressView = (ProgressView) activity.findViewById(this.mProgressViewResourceId);
           this.mCameraView.setOnTouchListener(this);
           this.mProgressView.setKeepScreenOn(true);
           this.mToast = Toast.makeText(activity, this.mDeviceNotSupportedString.getString(DeviceIssue.CANNOT_CONNECT_TO_CAMERA), 0);
                }

                @Override 
       public BasicVineRecorder.OnResumeTask getOnResumeTask(View view, String tag) {
           return new OnResumeTask(view, tag);
                }
            }
