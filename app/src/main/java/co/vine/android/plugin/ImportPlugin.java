            package co.vine.android.plugin;

            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.graphics.Point;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import co.vine.android.C0257R;
            import co.vine.android.CameraOnboardHelper;
            import co.vine.android.ImportTrimCropActivity;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.ExtractorTranscoder;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.widget.TooltipView;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            import java.util.ArrayList;
            
            
    public class ImportPlugin extends BaseToolPlugin<ImageButton> implements View.OnClickListener, TooltipView.Listener {
                private boolean mBlockEditMode;
                private ImageButton mImportButton;
                private final int[] mLock;
                private ViewGroup mParentView;
                private ProgressDialog mProgressDialog;
                private final Runnable mToolTipRunnable;
                private TooltipView mTooltip;
                private ExtractorTranscoder mTranscoder;
                private boolean mWasToolTipHidden;

        static  ViewGroup access$000(ImportPlugin x0) {
            return x0.mParentView;
                }

        static  TooltipView access$100(ImportPlugin x0) {
            return x0.mTooltip;
                }

        static  ImageButton access$200(ImportPlugin x0) {
            return x0.mImportButton;
                }

        static  int[] access$300(ImportPlugin x0) {
            return x0.mLock;
                }

        static  ProgressDialog access$400(ImportPlugin x0) {
            return x0.mProgressDialog;
                }

        static  ProgressDialog access$402(ImportPlugin x0, ProgressDialog x1) {
            x0.mProgressDialog = x1;
                    return x1;
                }

        static  ExtractorTranscoder access$500(ImportPlugin x0) {
            return x0.mTranscoder;
                }

        static  ExtractorTranscoder access$502(ImportPlugin x0, ExtractorTranscoder x1) {
            x0.mTranscoder = x1;
                    return x1;
                }

        static  boolean access$602(ImportPlugin x0, boolean x1) {
            x0.mBlockEditMode = x1;
                    return x1;
                }


        public ImportPlugin() {
            super("Import");
            this.mLock = new int[0];
           this.mToolTipRunnable = new RunnableC04492();
                }

                
                @Override 
        protected ImageButton onLayoutInflated(LinearLayout parent, LayoutInflater inflater, Fragment fragment) {
            this.mActivity = fragment.getActivity();
            ImageButton button = (ImageButton) inflater.inflate(C0257R.layout.plugin_option_image_button, (ViewGroup) parent, false);
            button.setOnClickListener(this);
            button.setImageResource(C0257R.drawable.ic_import);
            if (fragment instanceof RecorderPluginSupportedFragment) {
                ViewUtil.fillColor(fragment.getResources(), ((RecorderPluginSupportedFragment) fragment).getColor(true), C0257R.drawable.ic_import, button);
                    }
            button.setAlpha(0.35f);
            button.setOnTouchListener(new ViewOnTouchListenerC04481());
            this.mShouldOnboard = CameraOnboardHelper.getLastCompletedStep(this.mActivity) == null;
            if (this.mShouldOnboard && fragment.getView() != null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
                params.addRule(3, C0257R.id.recording_options);
                TooltipView tooltip = new TooltipView(fragment.getActivity());
                tooltip.setText(C0257R.string.camera_onboarding_grab_video_from_camera_roll);
                tooltip.setListener(this);
                RelativeLayout bottomMask = (RelativeLayout) fragment.getView().findViewById(C0257R.id.bottom_mask);
               bottomMask.addView(tooltip, params);
               this.mTooltip = tooltip;
               this.mParentView = parent;
                    }
           this.mImportButton = button;
           return button;
                }

                
        class ViewOnTouchListenerC04481 implements View.OnTouchListener {
            ViewOnTouchListenerC04481() {
                    }

                    @Override 
            public boolean onTouch(View view, MotionEvent event) {
                        if (view.getAlpha() != 0.175f) {
                            switch (event.getAction()) {
                                case 0:
                            view.setAlpha(1.0f);
                                    break;
                                case 1:
                            view.setAlpha(0.35f);
                                    break;
                            }
                    return false;
                        }
                return false;
                    }
                }

                @Override 
       public void onClick(View v) {
           BasicVineRecorder recorder = getRecorder();
           if (this.mActivity != null && (recorder == null || !recorder.isCurrentlyRecording())) {
               int totalDurationMs = recorder == null ? 0 : recorder.getEditedDurationMs();
               RecordConfigUtils.RecordConfig config = recorder == null
                       ? RecordConfigUtils.getGenericConfig(this.mActivity)
                       : recorder.getConfig();
                            if (config == null) {
                                config = RecordConfigUtils.getGenericConfig(this.mActivity);
                            }
               if (config != null) {
                   int maxDurationMs = config.maxDuration;
                            if (maxDurationMs - totalDurationMs > 200) {
                       Intent intent = new Intent(this.mActivity, (Class<?>) ImportTrimCropActivity.class);
                       intent.putExtra(ImportTrimCropActivity.EXTRA_DURATION, totalDurationMs);
                       intent.putExtra(ImportTrimCropActivity.EXTRA_MAX_DURATION, maxDurationMs);
                       this.mBlockEditMode = true;
                       this.mActivity.startActivityForResult(intent, 1);
                       if (hideToolTip()) {
                           String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
                           if (!"delete".equals(lastStep) && !CameraOnboardHelper.STEP_DELETE_2.equals(lastStep) && !CameraOnboardHelper.STEP_MORE_TOOLS.equals(lastStep)) {
                               CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_GRAB);
                                    }
                           this.mShouldOnboard = false;
                                }
                            }
                        }
                    }
                }

                @Override 
       public void onTooltipTapped() {
           CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_GRAB);
                }

       private boolean hideToolTip() {
           if (this.mTooltip == null || this.mTooltip.hasBeenHidden()) {
                        return false;
                    }
           this.mTooltip.hide();
           return true;
                }

                @Override 
       public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
                    synchronized (this.mLock) {
                        try {
                   if (this.mProgressDialog != null) {
                       this.mProgressDialog.show();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
       public void onResume(Activity activity) {
                    VineRecorder recorder;
           super.onResume(activity);
                    synchronized (this.mLock) {
                        try {
                   if (this.mProgressDialog != null && (recorder = getRecorder()) != null) {
                       recorder.setDelayDialog(true);
                            }
                   if (this.mShouldOnboard) {
                       showToopTip(2000L);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private void showToopTip(long delay) {
           ViewGroup parent = this.mParentView;
           if (parent != null) {
               parent.removeCallbacks(this.mToolTipRunnable);
               parent.postDelayed(this.mToolTipRunnable, delay);
                    }
                }

                
       class RunnableC04492 implements Runnable {
           RunnableC04492() {
                    }

                    @Override 
           public void run() {
               ViewGroup parent = ImportPlugin.access$000(ImportPlugin.this);
               TooltipView tooltip = ImportPlugin.access$100(ImportPlugin.this);
               if (parent != null && tooltip != null && !tooltip.hasBeenHidden()) {
                   tooltip.updatePosition(parent, parent.getWidth() / 4);
                   ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tooltip.getLayoutParams();
                   params.topMargin = tooltip.getResources().getDimensionPixelOffset(C0257R.dimen.tooltipNegativeMargin);
                   tooltip.setLayoutParams(params);
                   tooltip.show();
                        }
                    }
                }

                @Override 
       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
               recorder.postOnHandlerLoop(new RunnableC04503(recorder));
                    }
                }

                
       class RunnableC04503 implements Runnable {
                    final  VineRecorder val$recorder;

           RunnableC04503(VineRecorder vineRecorder) {
                        this.val$recorder = vineRecorder;
                    }

                    @Override 
           public void run() {
               int duration = this.val$recorder.getEditedDurationMs();
               RecordConfigUtils.RecordConfig config = this.val$recorder.getConfig();
               if (config != null) {
                            if (config.maxDuration - duration < 200) {
                       ImportPlugin.access$200(ImportPlugin.this).setAlpha(0.175f);
                            } else {
                       ImportPlugin.access$200(ImportPlugin.this).setAlpha(0.35f);
                            }
                        }
                    }
                }

                @Override 
       public boolean onSetEditMode(boolean editing, boolean hasData) {
           if (editing) {
               if (hideToolTip()) {
                   this.mWasToolTipHidden = true;
                        }
                    } else if (this.mWasToolTipHidden) {
               showToopTip(500L);
                    }
           return this.mBlockEditMode;
                }

                @Override 
       public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
           if (resultCode == -1 && requestCode == 1) {
               VineRecorder recorder = getRecorder();
               if (recorder == null) {
                   throw new IllegalStateException("No recorder to handle this import!");
                        }
               ImportAsyncTask importTask = new ImportAsyncTask(activity, recorder, data);
               importTask.execute(new Void[0]);
                        return true;
                    }
           this.mBlockEditMode = false;
                    return false;
                }

                @Override 
       public void onPause() {
           super.onPause();
                    synchronized (this.mLock) {
                        try {
                   if (this.mProgressDialog != null) {
                       this.mProgressDialog.dismiss();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public class ImportAsyncTask extends AsyncTask<Void, Void, Void> implements ExtractorTranscoder.ExtractorTranscoderListener, DialogInterface.OnCancelListener {
                    private final Activity context;
                    private final Intent data;
                    private int mLastProgress;
                    private final VineRecorder recorder;
                    private volatile boolean mImportFailed;

           Activity getContext() {
               return this.context;
                    }


           public ImportAsyncTask(Activity activity, VineRecorder r, Intent intent) {
               this.context = activity;
               this.recorder = r;
               this.data = intent;
                        synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                            try {
                       ImportPlugin.access$402(ImportPlugin.this, new ProgressDialog(this.context, 2));
                       ImportPlugin.access$400(ImportPlugin.this).setIndeterminate(false);
                       ImportPlugin.access$400(ImportPlugin.this).setCancelable(true);
                       ImportPlugin.access$400(ImportPlugin.this).setProgressStyle(1);
                       ImportPlugin.access$400(ImportPlugin.this).setProgressDrawable(activity.getResources().getDrawable(C0257R.drawable.progress_horizontal));
                       ImportPlugin.access$400(ImportPlugin.this).setOnCancelListener(this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override 
           protected void onPreExecute() {
                        synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                            try {
                       if (ImportPlugin.access$400(ImportPlugin.this) != null) {
                           ImportPlugin.access$400(ImportPlugin.this).setMax(100);
                           ImportPlugin.access$400(ImportPlugin.this).setMessage(this.recorder.getImportingString());
                           ImportPlugin.access$400(ImportPlugin.this).show();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    
           protected Void doInBackground(Void... params) {
                        try {
                   RecordingFile file = this.recorder.getFile();
                   long startMicros = this.data.getLongExtra(ImportTrimCropActivity.EXTRA_TRIM_START_TIME_USEC, 0L);
                   long endMicros = this.data.getLongExtra(ImportTrimCropActivity.EXTRA_TRIM_END_TIME_USEC, 0L);
                   Point cropOrigin = (Point) this.data.getParcelableExtra(ImportTrimCropActivity.EXTRA_TRIM_CROP_ORIGIN);
                   Uri inUri = this.data.getData();
                   SLog.m449d("Import result detected: url {} startMicros {}, endMicros {}", inUri, Long.valueOf(startMicros), Long.valueOf(endMicros));
                   RecordSegment segment = new RecordSegment(this.recorder.getCurrentDuration()).setCameraSetting(this.recorder.getCurrentCameraSetting());
                   ImportPlugin.access$502(ImportPlugin.this, new ExtractorTranscoder());
                   ImportPlugin.access$500(ImportPlugin.this).transcode(this.context, file.folder, this.recorder.getSesion(), segment, inUri, file.createSegmentVideoPath(), startMicros, endMicros, cropOrigin, this);
                   ImportPlugin.access$602(ImportPlugin.this, false);
                   return null;
                        } catch (Throwable e) {
                            
                            
                            
                            onError(e instanceof Exception ? (Exception) e : new RuntimeException(e));
                            return null;
                        }
                    }

                    @Override 
           public void onProgressUpdate(int progressOverOneHundred) {
                        if (this.mImportFailed) {
                            return;
                        }
               if (this.mLastProgress < progressOverOneHundred) {
                   this.mLastProgress = progressOverOneHundred;
                            synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                                try {
                           if (ImportPlugin.access$400(ImportPlugin.this) != null) {
                               ImportPlugin.access$400(ImportPlugin.this).setProgress(progressOverOneHundred);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override 
           public void onFinishedTranscoding(RecordSegment segment, Bitmap lastBitmap) {
                        if (this.mImportFailed) {
                            return;
                        }
               onProgressUpdate(100);
               segment.setIsImported(true);
               this.recorder.addExtraSegment(segment, lastBitmap);
                        synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                            try {
                       if (ImportPlugin.access$400(ImportPlugin.this) != null) {
                           ImportPlugin.access$400(ImportPlugin.this).dismiss();
                           ImportPlugin.access$402(ImportPlugin.this, null);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    
           class RunnableC04511 implements Runnable {
               RunnableC04511() {
                        }

                        @Override 
               public void run() {
                   if (ImportPlugin.access$400(ImportPlugin.this) != null) {
                       ImportPlugin.access$400(ImportPlugin.this).dismiss();
                       ImportPlugin.access$402(ImportPlugin.this, null);
                            }
                   Util.showCenteredToast(ImportAsyncTask.this.getContext(), ImportAsyncTask.this.getContext().getString(C0257R.string.error_import_video_unknown_error));
                        }
                    }

                    @Override 
           public void onError(Exception e) {
                        if (this.mImportFailed) {
                            return;
                        }
                        this.mImportFailed = true;
                        ImportPlugin.this.mBlockEditMode = false;
               CrashUtil.logException(e, "Import exception", new Object[0]);
               ImportPlugin.this.getHandler().post(new RunnableC04511());
                    }

                    @Override 
           public void onCancel(DialogInterface dialog) {
               if (ImportPlugin.access$400(ImportPlugin.this) != null) {
                   ImportPlugin.access$400(ImportPlugin.this).dismiss();
                   ImportPlugin.access$402(ImportPlugin.this, null);
                        }
               if (ImportPlugin.access$500(ImportPlugin.this) != null) {
                   ImportPlugin.access$500(ImportPlugin.this).cancel();
                        }
               ImportPlugin.access$602(ImportPlugin.this, false);
                    }
                }
            }
