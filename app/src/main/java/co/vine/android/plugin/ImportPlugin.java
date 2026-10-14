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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class ImportPlugin extends BaseToolPlugin<ImageButton> implements View.OnClickListener, TooltipView.Listener {
                private boolean mBlockEditMode;
                private ImageButton mImportButton;
                private final int[] mLock;
                private ViewGroup mParentView;
                private ProgressDialog mProgressDialog;
                private final Runnable mToolTipRunnable;
                private TooltipView mTooltip;
                private ExtractorTranscoder mTranscoder;
                private boolean mWasToolTipHidden;

/* 42 */        static /* synthetic */ ViewGroup access$000(ImportPlugin x0) {
/* 43 */            return x0.mParentView;
                }

/* 42 */        static /* synthetic */ TooltipView access$100(ImportPlugin x0) {
/* 43 */            return x0.mTooltip;
                }

/* 42 */        static /* synthetic */ ImageButton access$200(ImportPlugin x0) {
/* 43 */            return x0.mImportButton;
                }

/* 42 */        static /* synthetic */ int[] access$300(ImportPlugin x0) {
/* 43 */            return x0.mLock;
                }

/* 42 */        static /* synthetic */ ProgressDialog access$400(ImportPlugin x0) {
/* 43 */            return x0.mProgressDialog;
                }

/* 42 */        static /* synthetic */ ProgressDialog access$402(ImportPlugin x0, ProgressDialog x1) {
/* 43 */            x0.mProgressDialog = x1;
                    return x1;
                }

/* 42 */        static /* synthetic */ ExtractorTranscoder access$500(ImportPlugin x0) {
/* 43 */            return x0.mTranscoder;
                }

/* 42 */        static /* synthetic */ ExtractorTranscoder access$502(ImportPlugin x0, ExtractorTranscoder x1) {
/* 43 */            x0.mTranscoder = x1;
                    return x1;
                }

/* 42 */        static /* synthetic */ boolean access$602(ImportPlugin x0, boolean x1) {
/* 43 */            x0.mBlockEditMode = x1;
                    return x1;
                }


/* 56 */        public ImportPlugin() {
/* 57 */            super("Import");
/* 50 */            this.mLock = new int[0];
/* 184 */           this.mToolTipRunnable = new RunnableC04492();
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // co.vine.android.plugin.BaseToolPlugin
/* 61 */        protected ImageButton onLayoutInflated(LinearLayout parent, LayoutInflater inflater, Fragment fragment) {
/* 62 */            this.mActivity = fragment.getActivity();
/* 63 */            ImageButton button = (ImageButton) inflater.inflate(C0257R.layout.plugin_option_image_button, (ViewGroup) parent, false);
/* 64 */            button.setOnClickListener(this);
/* 65 */            button.setImageResource(C0257R.drawable.ic_import);
/* 66 */            if (fragment instanceof RecorderPluginSupportedFragment) {
/* 67 */                ViewUtil.fillColor(fragment.getResources(), ((RecorderPluginSupportedFragment) fragment).getColor(true), C0257R.drawable.ic_import, button);
                    }
/* 69 */            button.setAlpha(0.35f);
/* 70 */            button.setOnTouchListener(new ViewOnTouchListenerC04481());
/* 89 */            this.mShouldOnboard = CameraOnboardHelper.getLastCompletedStep(this.mActivity) == null;
/* 90 */            if (this.mShouldOnboard && fragment.getView() != null) {
/* 91 */                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
/* 93 */                params.addRule(3, C0257R.id.recording_options);
/* 94 */                TooltipView tooltip = new TooltipView(fragment.getActivity());
/* 95 */                tooltip.setText(C0257R.string.camera_onboarding_grab_video_from_camera_roll);
/* 96 */                tooltip.setListener(this);
/* 98 */                RelativeLayout bottomMask = (RelativeLayout) fragment.getView().findViewById(C0257R.id.bottom_mask);
/* 100 */               bottomMask.addView(tooltip, params);
/* 101 */               this.mTooltip = tooltip;
/* 102 */               this.mParentView = parent;
                    }
/* 105 */           this.mImportButton = button;
/* 107 */           return button;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.ImportPlugin$1 */
/* 68 */        class ViewOnTouchListenerC04481 implements View.OnTouchListener {
/* 69 */            ViewOnTouchListenerC04481() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 72 */            public boolean onTouch(View view, MotionEvent event) {
                        if (view.getAlpha() != 0.175f) {
                            switch (event.getAction()) {
                                case 0:
/* 76 */                            view.setAlpha(1.0f);
                                    break;
                                case 1:
/* 80 */                            view.setAlpha(0.35f);
                                    break;
                            }
/* 85 */                    return false;
                        }
/* 85 */                return false;
                    }
                }

                @Override // android.view.View.OnClickListener
/* 111 */       public void onClick(View v) {
/* 112 */           BasicVineRecorder recorder = getRecorder();
/* 113 */           if (this.mActivity != null && (recorder == null || !recorder.isCurrentlyRecording())) {
/* 114 */               int totalDurationMs = recorder == null ? 0 : recorder.getEditedDurationMs();
/* 115 */               RecordConfigUtils.RecordConfig config = recorder == null
/* 116 */                       ? RecordConfigUtils.getGenericConfig(this.mActivity)
/* 117 */                       : recorder.getConfig();
                            if (config == null) {
                                config = RecordConfigUtils.getGenericConfig(this.mActivity);
                            }
/* 116 */               if (config != null) {
/* 117 */                   int maxDurationMs = config.maxDuration;
                            if (maxDurationMs - totalDurationMs > 200) {
/* 119 */                       Intent intent = new Intent(this.mActivity, (Class<?>) ImportTrimCropActivity.class);
/* 120 */                       intent.putExtra(ImportTrimCropActivity.EXTRA_DURATION, totalDurationMs);
/* 121 */                       intent.putExtra(ImportTrimCropActivity.EXTRA_MAX_DURATION, maxDurationMs);
/* 122 */                       this.mBlockEditMode = true;
/* 123 */                       this.mActivity.startActivityForResult(intent, 1);
/* 124 */                       if (hideToolTip()) {
/* 125 */                           String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
/* 126 */                           if (!"delete".equals(lastStep) && !CameraOnboardHelper.STEP_DELETE_2.equals(lastStep) && !CameraOnboardHelper.STEP_MORE_TOOLS.equals(lastStep)) {
/* 129 */                               CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_GRAB);
                                    }
/* 131 */                           this.mShouldOnboard = false;
                                }
                            }
                        }
                    }
                }

                @Override // co.vine.android.widget.TooltipView.Listener
/* 139 */       public void onTooltipTapped() {
/* 140 */           CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_GRAB);
                }

/* 143 */       private boolean hideToolTip() {
/* 144 */           if (this.mTooltip == null || this.mTooltip.hasBeenHidden()) {
                        return false;
                    }
/* 145 */           this.mTooltip.hide();
/* 146 */           return true;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 152 */       public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
                    synchronized (this.mLock) {
                        try {
/* 154 */                   if (this.mProgressDialog != null) {
/* 155 */                       this.mProgressDialog.show();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 161 */       public void onResume(Activity activity) {
                    VineRecorder recorder;
/* 162 */           super.onResume(activity);
                    synchronized (this.mLock) {
                        try {
/* 164 */                   if (this.mProgressDialog != null && (recorder = getRecorder()) != null) {
/* 167 */                       recorder.setDelayDialog(true);
                            }
/* 170 */                   if (this.mShouldOnboard) {
/* 171 */                       showToopTip(2000L);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 176 */       private void showToopTip(long delay) {
/* 177 */           ViewGroup parent = this.mParentView;
/* 178 */           if (parent != null) {
/* 179 */               parent.removeCallbacks(this.mToolTipRunnable);
/* 180 */               parent.postDelayed(this.mToolTipRunnable, delay);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.ImportPlugin$2 */
/* 182 */       class RunnableC04492 implements Runnable {
/* 183 */           RunnableC04492() {
                    }

                    @Override // java.lang.Runnable
/* 186 */           public void run() {
/* 187 */               ViewGroup parent = ImportPlugin.access$000(ImportPlugin.this);
/* 188 */               TooltipView tooltip = ImportPlugin.access$100(ImportPlugin.this);
/* 189 */               if (parent != null && tooltip != null && !tooltip.hasBeenHidden()) {
/* 190 */                   tooltip.updatePosition(parent, parent.getWidth() / 4);
/* 191 */                   ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tooltip.getLayoutParams();
/* 192 */                   params.topMargin = tooltip.getResources().getDimensionPixelOffset(C0257R.dimen.tooltipNegativeMargin);
/* 193 */                   tooltip.setLayoutParams(params);
/* 194 */                   tooltip.show();
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 200 */       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
/* 201 */           VineRecorder recorder = getRecorder();
/* 202 */           if (recorder != null) {
/* 203 */               recorder.postOnHandlerLoop(new RunnableC04503(recorder));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.ImportPlugin$3 */
/* 201 */       class RunnableC04503 implements Runnable {
                    final /* synthetic */ VineRecorder val$recorder;

/* 202 */           RunnableC04503(VineRecorder vineRecorder) {
                        this.val$recorder = vineRecorder;
                    }

                    @Override // java.lang.Runnable
/* 205 */           public void run() {
/* 206 */               int duration = this.val$recorder.getEditedDurationMs();
/* 207 */               RecordConfigUtils.RecordConfig config = this.val$recorder.getConfig();
/* 208 */               if (config != null) {
                            if (config.maxDuration - duration < 200) {
/* 210 */                       ImportPlugin.access$200(ImportPlugin.this).setAlpha(0.175f);
                            } else {
/* 212 */                       ImportPlugin.access$200(ImportPlugin.this).setAlpha(0.35f);
                            }
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 221 */       public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 222 */           if (editing) {
/* 223 */               if (hideToolTip()) {
/* 224 */                   this.mWasToolTipHidden = true;
                        }
                    } else if (this.mWasToolTipHidden) {
/* 228 */               showToopTip(500L);
                    }
/* 231 */           return this.mBlockEditMode;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 235 */       public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
/* 236 */           if (resultCode == -1 && requestCode == 1) {
/* 238 */               VineRecorder recorder = getRecorder();
/* 239 */               if (recorder == null) {
/* 240 */                   throw new IllegalStateException("No recorder to handle this import!");
                        }
/* 242 */               ImportAsyncTask importTask = new ImportAsyncTask(activity, recorder, data);
/* 243 */               importTask.execute(new Void[0]);
                        return true;
                    }
/* 246 */           this.mBlockEditMode = false;
                    return false;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 252 */       public void onPause() {
/* 253 */           super.onPause();
                    synchronized (this.mLock) {
                        try {
/* 255 */                   if (this.mProgressDialog != null) {
/* 256 */                       this.mProgressDialog.dismiss();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 259 */       public class ImportAsyncTask extends AsyncTask<Void, Void, Void> implements ExtractorTranscoder.ExtractorTranscoderListener, DialogInterface.OnCancelListener {
                    private final Activity context;
                    private final Intent data;
                    private int mLastProgress;
                    private final VineRecorder recorder;
                    private volatile boolean mImportFailed;

/* 260 */           Activity getContext() {
/* 261 */               return this.context;
                    }


/* 268 */           public ImportAsyncTask(Activity activity, VineRecorder r, Intent intent) {
/* 270 */               this.context = activity;
/* 271 */               this.recorder = r;
/* 272 */               this.data = intent;
                        synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                            try {
/* 274 */                       ImportPlugin.access$402(ImportPlugin.this, new ProgressDialog(this.context, 2));
/* 275 */                       ImportPlugin.access$400(ImportPlugin.this).setIndeterminate(false);
/* 276 */                       ImportPlugin.access$400(ImportPlugin.this).setCancelable(true);
/* 277 */                       ImportPlugin.access$400(ImportPlugin.this).setProgressStyle(1);
/* 278 */                       ImportPlugin.access$400(ImportPlugin.this).setProgressDrawable(activity.getResources().getDrawable(C0257R.drawable.progress_horizontal));
/* 279 */                       ImportPlugin.access$400(ImportPlugin.this).setOnCancelListener(this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.os.AsyncTask
/* 284 */           protected void onPreExecute() {
                        synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                            try {
/* 286 */                       if (ImportPlugin.access$400(ImportPlugin.this) != null) {
/* 287 */                           ImportPlugin.access$400(ImportPlugin.this).setMax(100);
/* 288 */                           ImportPlugin.access$400(ImportPlugin.this).setMessage(this.recorder.getImportingString());
/* 289 */                           ImportPlugin.access$400(ImportPlugin.this).show();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 296 */           protected Void doInBackground(Void... params) {
                        try {
/* 297 */                   RecordingFile file = this.recorder.getFile();
/* 298 */                   long startMicros = this.data.getLongExtra(ImportTrimCropActivity.EXTRA_TRIM_START_TIME_USEC, 0L);
/* 299 */                   long endMicros = this.data.getLongExtra(ImportTrimCropActivity.EXTRA_TRIM_END_TIME_USEC, 0L);
/* 300 */                   Point cropOrigin = (Point) this.data.getParcelableExtra(ImportTrimCropActivity.EXTRA_TRIM_CROP_ORIGIN);
/* 301 */                   Uri inUri = this.data.getData();
/* 302 */                   SLog.m449d("Import result detected: url {} startMicros {}, endMicros {}", inUri, Long.valueOf(startMicros), Long.valueOf(endMicros));
/* 303 */                   RecordSegment segment = new RecordSegment(this.recorder.getCurrentDuration()).setCameraSetting(this.recorder.getCurrentCameraSetting());
/* 305 */                   ImportPlugin.access$502(ImportPlugin.this, new ExtractorTranscoder());
/* 306 */                   ImportPlugin.access$500(ImportPlugin.this).transcode(this.context, file.folder, this.recorder.getSesion(), segment, inUri, file.createSegmentVideoPath(), startMicros, endMicros, cropOrigin, this);
/* 311 */                   ImportPlugin.access$602(ImportPlugin.this, false);
/* 312 */                   return null;
                        } catch (Throwable e) {
                            // A phone gallery video can fail transcoding because of its codec,
                            // rotation metadata, or audio track. Do not rethrow from AsyncTask:
                            // that becomes an uncaught worker-thread exception and crashes Vine.
                            onError(e instanceof Exception ? (Exception) e : new RuntimeException(e));
                            return null;
                        }
                    }

                    @Override // co.vine.android.recorder.ExtractorTranscoder.ExtractorTranscoderListener
/* 316 */           public void onProgressUpdate(int progressOverOneHundred) {
                        if (this.mImportFailed) {
                            return;
                        }
/* 317 */               if (this.mLastProgress < progressOverOneHundred) {
/* 318 */                   this.mLastProgress = progressOverOneHundred;
                            synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                                try {
/* 320 */                           if (ImportPlugin.access$400(ImportPlugin.this) != null) {
/* 321 */                               ImportPlugin.access$400(ImportPlugin.this).setProgress(progressOverOneHundred);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // co.vine.android.recorder.ExtractorTranscoder.ExtractorTranscoderListener
/* 328 */           public void onFinishedTranscoding(RecordSegment segment, Bitmap lastBitmap) {
                        if (this.mImportFailed) {
                            return;
                        }
/* 329 */               onProgressUpdate(100);
/* 330 */               segment.setIsImported(true);
/* 331 */               this.recorder.addExtraSegment(segment, lastBitmap);
                        synchronized (ImportPlugin.access$300(ImportPlugin.this)) {
                            try {
/* 333 */                       if (ImportPlugin.access$400(ImportPlugin.this) != null) {
/* 334 */                           ImportPlugin.access$400(ImportPlugin.this).dismiss();
/* 335 */                           ImportPlugin.access$402(ImportPlugin.this, null);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.plugin.ImportPlugin$ImportAsyncTask$1 */
/* 341 */           class RunnableC04511 implements Runnable {
/* 342 */               RunnableC04511() {
                        }

                        @Override // java.lang.Runnable
/* 345 */               public void run() {
/* 346 */                   if (ImportPlugin.access$400(ImportPlugin.this) != null) {
/* 347 */                       ImportPlugin.access$400(ImportPlugin.this).dismiss();
/* 348 */                       ImportPlugin.access$402(ImportPlugin.this, null);
                            }
/* 350 */                   Util.showCenteredToast(ImportAsyncTask.this.getContext(), ImportAsyncTask.this.getContext().getString(C0257R.string.error_import_video_unknown_error));
                        }
                    }

                    @Override // co.vine.android.recorder.ExtractorTranscoder.ExtractorTranscoderListener
/* 341 */           public void onError(Exception e) {
                        if (this.mImportFailed) {
                            return;
                        }
                        this.mImportFailed = true;
                        ImportPlugin.this.mBlockEditMode = false;
/* 342 */               CrashUtil.logException(e, "Import exception", new Object[0]);
/* 343 */               ImportPlugin.this.getHandler().post(new RunnableC04511());
                    }

                    @Override // android.content.DialogInterface.OnCancelListener
/* 356 */           public void onCancel(DialogInterface dialog) {
/* 357 */               if (ImportPlugin.access$400(ImportPlugin.this) != null) {
/* 358 */                   ImportPlugin.access$400(ImportPlugin.this).dismiss();
/* 359 */                   ImportPlugin.access$402(ImportPlugin.this, null);
                        }
/* 361 */               if (ImportPlugin.access$500(ImportPlugin.this) != null) {
/* 362 */                   ImportPlugin.access$500(ImportPlugin.this).cancel();
                        }
/* 364 */               ImportPlugin.access$602(ImportPlugin.this, false);
                    }
                }
            }
