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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public abstract class RegularVineRecorder extends BasicVineRecorder implements View.OnTouchListener {
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

/* 20 */        static /* synthetic */ boolean access$002(RegularVineRecorder x0, boolean x1) {
/* 21 */            x0.mIsHidingFinishProgressDialog = x1;
                    return x1;
                }

/* 65 */        public RegularVineRecorder(int thresholdMs, BaseRecorderPluginManager manager, Point screenSize, Activity activity, int progressViewResourceId, int cameraViewResourceId, int topMaskId, int bottomMaskId, int openCameraString, int rootLayoutId, Drawable progressDrawable, DeviceIssueStringGetter deviceNotSupportedString, boolean startWithFrontFacingCamera, RecordSessionVersion version, CharSequence importingString, CharSequence... messages) {
/* 66 */            super(thresholdMs, manager, activity, startWithFrontFacingCamera, version == RecordSessionVersion.HW, version);
/* 68 */            this.mStartProgressDialogMessage = messages[0];
/* 69 */            this.mFinishProgressDialogMessage = messages;
/* 70 */            this.mOpenCameraResource = activity.getText(openCameraString);
/* 71 */            this.mSize = screenSize;
/* 72 */            this.density = activity.getResources().getDisplayMetrics().density;
/* 73 */            this.mProgressViewResourceId = progressViewResourceId;
/* 74 */            this.mCameraViewResourceId = cameraViewResourceId;
/* 75 */            this.mRootLayoutId = rootLayoutId;
/* 76 */            this.mDeviceNotSupportedString = deviceNotSupportedString;
/* 77 */            this.mTopMaskId = topMaskId;
/* 78 */            this.mBottomMaskId = bottomMaskId;
/* 79 */            this.mImportingString = importingString;
/* 80 */            this.mProgressDrawable = progressDrawable;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.RegularVineRecorder$1 */
/* 83 */        class RunnableC04831 implements Runnable {
/* 84 */            RunnableC04831() {
                    }

                    @Override // java.lang.Runnable
/* 87 */            public void run() {
/* 88 */                if (RegularVineRecorder.this.mToast != null) {
/* 89 */                    RegularVineRecorder.this.mToast.show();
                        }
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 84 */        protected void showCameraFailedToast() {
/* 85 */            this.mHandler.post(new RunnableC04831());
                }

/* 96 */        protected class ResumeCameraAsyncTask extends BasicVineRecorder.ResumeCameraAsyncTask {

/* 99 */            public ResumeCameraAsyncTask(boolean switchCamera) {
/* 101 */               super(switchCamera);
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.ResumeCameraAsyncTask, android.os.AsyncTask
/* 105 */           protected void onPreExecute() {
/* 106 */               super.onPreExecute();
/* 107 */               if (RegularVineRecorder.this.mOpenCameraDialog != null) {
/* 108 */                   RegularVineRecorder.this.mOpenCameraDialog.show();
                        }
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.ResumeCameraAsyncTask
                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 113 */           protected void onPostExecute(Void aVoid) {
/* 114 */               super.onPostExecute(aVoid);
/* 115 */               if (RegularVineRecorder.this.mOpenCameraDialog != null) {
/* 116 */                   RegularVineRecorder.this.mOpenCameraDialog.dismiss();
                        }
                    }
                }

/* 122 */       protected class OnResumeTask extends BasicVineRecorder.OnResumeTask {


/* 125 */           public OnResumeTask(View view, String tag) {
/* 127 */               super(view, tag);
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.OnResumeTask
/* 131 */           protected void publishFinishLastSegmentProgress() {
/* 132 */               publishProgress(new CharSequence[]{RegularVineRecorder.this.mFinishLastSegmentString});
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.OnResumeTask
/* 136 */           protected void publishStartProgres() {
/* 137 */               publishProgress(new CharSequence[]{RegularVineRecorder.this.mStartProgressDialogMessage});
                    }

                    /* JADX INFO: renamed from: onProgressUpdate, reason: avoid collision after fix types in other method */
/* 141 */           protected void onProgressUpdate(CharSequence... values) {
/* 142 */               if (RegularVineRecorder.this.mStartProgressDialog != null) {
/* 143 */                   if (!this.showDialogDelayed && !RegularVineRecorder.this.mStartProgressDialog.isShowing()) {
/* 145 */                       RegularVineRecorder.this.mStartProgressDialog.show();
                            }
/* 148 */                   RegularVineRecorder.this.mStartProgressDialog.setMessage(values[0]);
                        }
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.recorder.BasicVineRecorder.OnResumeTask
/* 153 */           protected void onPostExecute(RecordingFile needHashTask) {
/* 154 */               super.onPostExecute(needHashTask);
/* 155 */               if (RegularVineRecorder.this.mStartProgressDialog != null && !isCancelled()) {
/* 156 */                   RegularVineRecorder.this.mStartProgressDialog.dismiss();
                        }
/* 158 */               RegularVineRecorder.this.onResumeTaskComplete();
                    }
                }

/* 161 */       protected void onResumeTaskComplete() {
                }

/* 164 */       public CharSequence getFinishLastSegmentString() {
/* 165 */           return this.mFinishLastSegmentString;
                }

/* 167 */       public CharSequence getImportingString() {
/* 168 */           return this.mImportingString;
                }

/* 171 */       public Point getSize() {
/* 172 */           return this.mSize;
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 176 */       public void changeProgress(long progress) {
/* 177 */           ProgressView view = this.mProgressView;
/* 178 */           if (view != null && this.mSession != null) {
/* 179 */               view.setProgressRatio(progress / (1.0f * this.mSession.getConfig().maxDuration));
/* 180 */               if (SystemUtil.isOnMainThread()) {
/* 181 */                   view.invalidate();
                        } else {
/* 183 */                   view.postInvalidate();
                        }
                    }
                }

/* 188 */       public static String getBottomMaskHeightPref(boolean frontFacing) {
/* 189 */           return PREF_BOTTOM_MASK_HEIGHT + frontFacing;
                }

                @Override // android.view.View.OnTouchListener
/* 193 */       public synchronized boolean onTouch(View v, MotionEvent event) {
                    boolean z = true;
                    synchronized (this) {
                        try {
                            int action = event.getAction();
/* 195 */                   if (this.mCurrentRecordingFile == null || !this.mEnabled) {
                                z = false;
                            } else if (this.mAutoFocusing) {
                                switch (action) {
                                    case 0:
/* 200 */                               float axisX = event.getAxisValue(0);
                                        if (axisX > TOUCH_EDGE_BOUNDARY * this.mSize.x && axisX < 0.95f * this.mSize.x) {
/* 203 */                                   startRelativeTime();
                                        }
                                        break;
                                    case 1:
/* 207 */                               endRelativeTime();
                                        break;
                                }
                            } else {
                                switch (action) {
                                    case 1:
/* 213 */                               changeFocusTo(event.getAxisValue(0), event.getAxisValue(1));
                                        break;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 222 */       public void onUiPaused() {
/* 223 */           if (this.mCameraView != null) {
/* 224 */               this.mCameraView.setOnTouchListener(null);
                    }
                    try {
/* 227 */               if (this.mFinishProgressDialog != null) {
/* 228 */                   this.mFinishProgressDialog.dismiss();
                        }
/* 230 */               if (this.mStartProgressDialog != null) {
/* 231 */                   this.mStartProgressDialog.dismiss();
                        }
/* 233 */               if (this.mOpenCameraDialog != null) {
/* 234 */                   this.mOpenCameraDialog.dismiss();
                        }
                    } catch (Exception e) {
/* 237 */               SLog.m459e("It's probably detached already.", (Throwable) e);
                    }
/* 239 */           this.mStartProgressDialog = null;
/* 240 */           this.mFinishProgressDialog = null;
/* 241 */           this.mOpenCameraDialog = null;
/* 242 */           this.mTopMaskView = null;
/* 243 */           this.mBottomMaskView = null;
/* 244 */           this.mProgressView = null;
/* 245 */           this.mToast = null;
/* 246 */           super.onUiPaused();
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 249 */       protected boolean onStop() {
/* 250 */           if (this.mFastEncoding) {
/* 251 */               if (!this.mIsHidingFinishProgressDialog) {
/* 252 */                   this.mIsHidingFinishProgressDialog = true;
                            return true;
                        }
/* 255 */               SLog.m446d("Stop twice? wtf.");
                        return false;
                    }
/* 259 */           ProgressDialog dialog = this.mFinishProgressDialog;
/* 260 */           if (dialog != null && !dialog.isShowing()) {
/* 261 */               this.mActivity.runOnUiThread(new RunnableC04842(dialog));
                        return true;
                    }
/* 269 */           SLog.m446d("Stop twice? wtf.");
                    return false;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.RegularVineRecorder$2 */
/* 259 */       class RunnableC04842 implements Runnable {
                    final /* synthetic */ ProgressDialog val$dialog;

/* 260 */           RunnableC04842(ProgressDialog progressDialog) {
                        this.val$dialog = progressDialog;
                    }

                    @Override // java.lang.Runnable
/* 263 */           public void run() {
/* 264 */               this.val$dialog.show();
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 276 */       public BaseFinishProcessTask getFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 277 */           return new FinishProcessTask(tag, onComplete, releasePreview, saveSession);
                }

/* 278 */       public class FinishProcessTask extends BasicVineRecorder.FinishProcessTask {


/* 281 */           public FinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 283 */               super(tag, onComplete, releasePreview, saveSession);
                    }

                    @Override // co.vine.android.recorder.BaseFinishProcessTask
                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 287 */           protected void onPostExecute(Void aVoid) {
/* 288 */               super.onPostExecute(aVoid);
/* 289 */               RegularVineRecorder.access$002(RegularVineRecorder.this, false);
/* 290 */               ProgressDialog dialog = RegularVineRecorder.this.mFinishProgressDialog;
/* 291 */               if (dialog != null && dialog.isShowing()) {
/* 292 */                   dialog.dismiss();
                        }
                    }

                    /* JADX INFO: renamed from: onProgressUpdate, reason: avoid collision after fix types in other method */
/* 297 */           protected void onProgressUpdate(Integer... values) {
/* 298 */               ProgressDialog dialog = RegularVineRecorder.this.mFinishProgressDialog;
/* 299 */               if (dialog != null && dialog.isShowing()) {
/* 300 */                   if (RegularVineRecorder.this.mFinishProgressDialogMessage != null && RegularVineRecorder.this.mFinishProgressDialogMessage.length > 2 && values[0].intValue() >= (100 / (RegularVineRecorder.this.mFinishProgressDialogMessage.length - 1)) * this.mCurrentMessage) {
/* 302 */                       if (this.mCurrentMessage + 1 < RegularVineRecorder.this.mFinishProgressDialogMessage.length) {
/* 303 */                           dialog.setMessage(RegularVineRecorder.this.mFinishProgressDialogMessage[this.mCurrentMessage + 1]);
                                }
/* 305 */                       this.mCurrentMessage++;
                            }
/* 308 */                   dialog.setProgress(values[0].intValue());
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.RegularVineRecorder$3 */
/* 314 */       class RunnableC04853 implements Runnable {
                    final /* synthetic */ DeviceIssue val$issue;

/* 315 */           RunnableC04853(DeviceIssue deviceIssue) {
                        this.val$issue = deviceIssue;
                    }

                    /* JADX INFO: renamed from: co.vine.android.recorder.RegularVineRecorder$3$1, reason: invalid class name */
/* 317 */           class AnonymousClass1 implements Runnable {
/* 318 */               AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
/* 321 */               public void run() {
/* 322 */                   if (RegularVineRecorder.this.mActivity != null) {
/* 323 */                       Toast.makeText(RegularVineRecorder.this.mActivity, RegularVineRecorder.this.mDeviceNotSupportedString.getString(RunnableC04853.this.val$issue), 0).show();
/* 324 */                       RegularVineRecorder.this.mActivity.finish();
                            }
                        }
                    }

                    @Override // java.lang.Runnable
/* 318 */           public void run() {
/* 319 */               RegularVineRecorder.this.mHandler.post(new AnonymousClass1());
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
                @NonNull
/* 315 */       protected Runnable getOnDeviceIssueAction(DeviceIssue issue) {
/* 316 */           return new RunnableC04853(issue);
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 332 */       public void stopAndDiscardChanges(String tag, Runnable onComplete, boolean releasePreview) {
/* 333 */           setDiscardChanges(true);
/* 334 */           this.mFinishProcessRunnable.run(tag, releasePreview, false);
/* 335 */           if (onComplete != null) {
/* 336 */               onComplete.run();
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
                @SuppressLint({"ShowToast"})
/* 341 */       public void onUiResumed(Activity activity, Runnable onCompleteAsyncTask, boolean doNotResumeRecorder) {
/* 342 */           super.onUiResumed(activity, onCompleteAsyncTask, doNotResumeRecorder);
/* 343 */           this.mFinishProgressDialog = new ProgressDialog(activity, 2);
/* 344 */           this.mFinishProgressDialog.setMessage(this.mFinishProgressDialogMessage[1]);
/* 345 */           this.mFinishProgressDialog.setMax(100);
/* 346 */           this.mFinishProgressDialog.setCancelable(false);
/* 347 */           this.mFinishProgressDialog.setIndeterminate(false);
/* 348 */           this.mFinishProgressDialog.setProgressStyle(1);
/* 349 */           this.mFinishProgressDialog.setProgressDrawable(this.mProgressDrawable);
/* 350 */           this.mStartProgressDialog = new ProgressDialog(activity, 2);
/* 351 */           this.mOpenCameraDialog = new ProgressDialog(activity, 2);
/* 352 */           this.mOpenCameraDialog.setMessage(this.mOpenCameraResource);
/* 353 */           this.mOpenCameraDialog.setCancelable(false);
/* 354 */           this.mRootLayoutView = activity.findViewById(this.mRootLayoutId);
/* 355 */           this.mCameraView = activity.findViewById(this.mCameraViewResourceId);
/* 356 */           this.mCameraView.setVisibility(0);
/* 357 */           this.mCameraView.setOnTouchListener(this);
/* 358 */           this.mTopMaskView = activity.findViewById(this.mTopMaskId);
/* 359 */           this.mBottomMaskView = activity.findViewById(this.mBottomMaskId);
/* 360 */           this.mProgressView = (ProgressView) activity.findViewById(this.mProgressViewResourceId);
/* 361 */           this.mCameraView.setOnTouchListener(this);
/* 362 */           this.mProgressView.setKeepScreenOn(true);
/* 363 */           this.mToast = Toast.makeText(activity, this.mDeviceNotSupportedString.getString(DeviceIssue.CANNOT_CONNECT_TO_CAMERA), 0);
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 367 */       public BasicVineRecorder.OnResumeTask getOnResumeTask(View view, String tag) {
/* 368 */           return new OnResumeTask(view, tag);
                }
            }
