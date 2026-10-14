            package co.vine.android;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.content.ActivityNotFoundException;
            import android.database.Cursor;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.graphics.Point;
            import android.media.MediaMetadataRetriever;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.util.Log;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            import android.widget.HorizontalScrollView;
            import android.widget.ImageButton;
            import android.widget.LinearLayout;
            import android.widget.ScrollView;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.InputStream;
            import co.vine.android.player.StaticSizeExoPlayerTextureView;
            import co.vine.android.recorder.RegularProgressView;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.widget.trimControls.ThumbnailRangeFinderLayout;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.exoplayer.ExoPlaybackException;
            import com.google.android.exoplayer.ExoPlayer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class ImportTrimCropActivity extends BaseActionBarActivity implements View.OnClickListener, ThumbnailRangeFinderLayout.OnVideoTrimmedListener {
                public static final String ACTION_VIDEO_PICKED = "video_picked";
                public static final String EXTRA_GALLERY_ONLY = "gallery_only";
                public static final String EXTRA_DURATION = "current_duration";
                public static final String EXTRA_MAX_DURATION = "max_duration";
                public static final String EXTRA_TRIM_CROP_ORIGIN = "crop_origin";
                public static final String EXTRA_TRIM_END_TIME_USEC = "trim_end_time_usec";
                public static final String EXTRA_TRIM_START_TIME_USEC = "trim_start_time_usec";
                private static final int REQUEST_PICK_VIDEO = 2;
                private long mAddRequestTime;
                private Uri mFileUri;
                private Handler mHandler;
                private int mMaxDurationMs;
                private ImageButton mPlayButton;
                private int mPlayerHeight;
                private int mPlayerWidth;
                private RegularProgressView mProgressView;
                private Point mSize;
                private int mStartDurationMs;
                private float mStartRatio;
                private StaticSizeExoPlayerTextureView mTextureView;
                private ThumbnailRangeFinderLayout mThumbnailRangeFinder;
                private FrameLayout mVideoContainer;
                private int mVideoHeight;
                private ViewGroup mVideoViewPanner;
                private int mVideoWidth;
                private boolean mHasRetried = false;
                private boolean mAddInProgress = false;
                private Runnable mProgressChecker = new RunnableC02307();
                private View.OnTouchListener mVideoViewTouchListener = new ViewOnTouchListenerC02318();

/* 44 */        static /* synthetic */ void access$000(ImportTrimCropActivity x0) {
/* 45 */            x0.add();
                }

/* 44 */        static /* synthetic */ int access$100(ImportTrimCropActivity x0) {
/* 45 */            return x0.mMaxDurationMs;
                }

/* 44 */        static /* synthetic */ Runnable access$1000(ImportTrimCropActivity x0) {
/* 45 */            return x0.mProgressChecker;
                }

/* 44 */        static /* synthetic */ Handler access$1100(ImportTrimCropActivity x0) {
/* 45 */            return x0.mHandler;
                }

/* 44 */        static /* synthetic */ void access$1200(ImportTrimCropActivity x0) {
/* 45 */            x0.playPauseVideo();
                }

/* 44 */        static /* synthetic */ ThumbnailRangeFinderLayout access$200(ImportTrimCropActivity x0) {
/* 45 */            return x0.mThumbnailRangeFinder;
                }

/* 44 */        static /* synthetic */ void access$300(ImportTrimCropActivity x0, int x1, int x2, int x3, long x4) {
/* 45 */            x0.prepareVideoView(x1, x2, x3, x4);
                }

/* 44 */        static /* synthetic */ boolean access$400(ImportTrimCropActivity x0) {
/* 45 */            return x0.mHasRetried;
                }

/* 44 */        static /* synthetic */ void access$500(ImportTrimCropActivity x0) {
/* 45 */            x0.release();
                }

/* 44 */        static /* synthetic */ Uri access$600(ImportTrimCropActivity x0) {
/* 45 */            return x0.mFileUri;
                }

/* 44 */        static /* synthetic */ void access$700(ImportTrimCropActivity x0, Uri x1, int x2, Object x3) {
/* 45 */            x0.showInvalidVideoError(x1, x2, x3);
                }

/* 44 */        static /* synthetic */ StaticSizeExoPlayerTextureView access$800(ImportTrimCropActivity x0) {
/* 45 */            return x0.mTextureView;
                }

/* 44 */        static /* synthetic */ int access$900(ImportTrimCropActivity x0) {
/* 45 */            return x0.checkTimeBar();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 79 */        protected void onCreate(Bundle savedInstanceState) {
/* 80 */            onCreate(savedInstanceState, C0257R.layout.import_trim_crop_activity, true, true);
                }

                @Override // co.vine.android.BaseActionBarActivity
/* 84 */        public void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired, boolean readOnly) {
/* 85 */            super.onCreate(savedInstanceState, layout, loginRequired, readOnly);
/* 86 */            this.mStartDurationMs = 0;
/* 87 */            this.mMaxDurationMs = 6000;
/* 87 */            if (getIntent() != null && getIntent().getExtras() != null) {
/* 88 */                Bundle extras = getIntent().getExtras();
/* 89 */                this.mStartDurationMs = extras.getInt(EXTRA_DURATION);
/* 90 */                this.mMaxDurationMs = extras.getInt(EXTRA_MAX_DURATION);
                    }
/* 91 */            if (this.mMaxDurationMs <= 0) {
/* 92 */                this.mMaxDurationMs = 6000;
                    }
/* 93 */            if (this.mStartDurationMs < 0) {
/* 94 */                this.mStartDurationMs = 0;
                    } else if (this.mStartDurationMs > this.mMaxDurationMs) {
/* 96 */                this.mStartDurationMs = this.mMaxDurationMs;
                    }
/* 95 */            Point size = SystemUtil.getDisplaySize((Activity) this);
/* 96 */            this.mHandler = new Handler(Looper.getMainLooper());
/* 97 */            ViewGroup rootView = (ViewGroup) findViewById(C0257R.id.import_view_root);
/* 98 */            this.mVideoContainer = (FrameLayout) findViewById(C0257R.id.video_container);
/* 99 */            this.mPlayButton = (ImageButton) findViewById(C0257R.id.import_play_button);
/* 100 */           this.mProgressView = (RegularProgressView) findViewById(C0257R.id.import_progress_view);
/* 101 */           this.mProgressView.setSelectedColor(getResources().getColor(C0257R.color.vine_green));
/* 102 */           this.mProgressView.setColor(getResources().getColor(C0257R.color.trim_yellow));
/* 103 */           this.mStartRatio = this.mStartDurationMs / this.mMaxDurationMs;
/* 104 */           this.mProgressView.setProgressRatio(this.mStartRatio);
/* 105 */           this.mProgressView.setSelectedSection(0.0f, this.mStartRatio);
/* 107 */           findViewById(C0257R.id.importTitle).setOnClickListener(new ViewOnClickListenerC02241());
/* 114 */           findViewById(C0257R.id.trimCropBackButton).setOnClickListener(new ViewOnClickListenerC02252());
/* 121 */           findViewById(C0257R.id.add).setOnClickListener(new ViewOnClickListenerC02263());
/* 128 */           int height = getResources().getDimensionPixelSize(C0257R.dimen.carousel_height);
/* 129 */           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, height);
/* 132 */           ThumbnailRangeFinderLayout thumbnailRangeFinder = new ThumbnailRangeFinderLayout(this, (int) (this.mStartRatio * size.x));
/* 133 */           thumbnailRangeFinder.setListener(this);
/* 134 */           rootView.addView(thumbnailRangeFinder, params);
/* 135 */           this.mThumbnailRangeFinder = thumbnailRangeFinder;
/* 137 */           StaticSizeExoPlayerTextureView videoView = new StaticSizeExoPlayerTextureView(this);
/* 138 */           videoView.setOnTouchListener(this.mVideoViewTouchListener);
/* 139 */           videoView.setSize(size.x, size.x);
/* 140 */           this.mTextureView = videoView;
/* 141 */           this.mSize = size;
/* 143 */           if (getIntent().getData() == null) {
                            Intent pickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            pickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                            pickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
/* 145 */               pickerIntent.setType("video/*");
/* 146 */               pickerIntent.putExtra("android.intent.extra.LOCAL_ONLY", true);
                        try {
/* 148 */                   startActivityForResult(Intent.createChooser(pickerIntent, getString(C0257R.string.pick_video)), 2);
/* 157 */                   return;
                        } catch (ActivityNotFoundException e) {
/* 151 */                   Util.showCenteredToast(this, getString(C0257R.string.unsupported_feature) + " \n (" + getString(C0257R.string.pick_video) + ") ");
/* 157 */                   return;
                        }
                    }
/* 154 */           this.mHasRetried = true;
                    try {
/* 155 */               onUriResult(getIntent().getData(), true);
                    } catch (Throwable e) {
                        Log.e("ImportTrimCropActivity", "Unable to open selected video", e);
                        finish();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$1 */
/* 105 */       class ViewOnClickListenerC02241 implements View.OnClickListener {
/* 106 */           ViewOnClickListenerC02241() {
                    }

                    @Override // android.view.View.OnClickListener
/* 109 */           public void onClick(View v) {
/* 110 */               ImportTrimCropActivity.this.onBackPressed();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$2 */
/* 112 */       class ViewOnClickListenerC02252 implements View.OnClickListener {
/* 113 */           ViewOnClickListenerC02252() {
                    }

                    @Override // android.view.View.OnClickListener
/* 116 */           public void onClick(View v) {
/* 117 */               ImportTrimCropActivity.this.onBackPressed();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$3 */
/* 119 */       class ViewOnClickListenerC02263 implements View.OnClickListener {
/* 120 */           ViewOnClickListenerC02263() {
                    }

                    @Override // android.view.View.OnClickListener
/* 123 */           public void onClick(View v) {
/* 124 */               ImportTrimCropActivity.access$000(ImportTrimCropActivity.this);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 160 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 161 */           super.onActivityResult(requestCode, resultCode, data);
/* 162 */           if (resultCode == -1) {
/* 163 */               if (requestCode == 2 && data != null) {
/* 164 */                   Uri uri = data.getData();
                            if (uri == null) {
                                Util.showCenteredToast(this, getString(C0257R.string.error_import_video_unknown_error));
                                finish();
                                return;
                            }
                            if (Build.VERSION.SDK_INT >= 19 && (data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION) != 0) {
                                try {
                                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                } catch (Throwable ignored) {
                                    // Some providers grant only a temporary read permission.
                                }
                            }
                            try {
/* 166 */                       onUriResult(uri, false);
/* 178 */                       return;
                            } catch (Throwable th) {
/* 168 */                       showInvalidVideoError(uri, C0257R.string.error_import_video_unknown_error, "Failed to validate video.");
/* 178 */                       return;
                            }
                        }
/* 170 */               if (requestCode == 1) {
/* 171 */                   setResult(resultCode, data);
/* 172 */                   release();
/* 173 */                   finish();
/* 178 */                   return;
                        }
/* 178 */               return;
                    }
/* 176 */           finish();
                }

                @TargetApi(17)
/* 181 */       private void onUriResult(Uri uri, boolean forceMetadataRetriever) throws Throwable {
                    uri = materializeVideoUri(uri);
                    long videoDurationMs;
                    int width;
                    int height;
/* 182 */           String filePath = SdkFileUtils.getPath(this, uri);
/* 183 */           if (filePath == null) {
/* 184 */               throw new IllegalArgumentException("File path was null: " + uri);
                    }
/* 186 */           MediaMetadataRetriever retriever = null;
                    try {
/* 188 */               MediaMetadataRetriever retriever2 = new MediaMetadataRetriever();
                        try {
/* 189 */                   retriever2.setDataSource(filePath);
/* 191 */                   String durationMsStr = retriever2.extractMetadata(9);
/* 192 */                   String widthStr = retriever2.extractMetadata(18);
/* 193 */                   String heightStr = retriever2.extractMetadata(19);
/* 198 */                   if (durationMsStr != null) {
/* 199 */                       videoDurationMs = Long.parseLong(durationMsStr);
                            } else {
/* 201 */                       videoDurationMs = -1;
                            }
/* 203 */                   if (widthStr != null) {
/* 204 */                       width = Integer.parseInt(widthStr);
                            } else {
/* 206 */                       width = -1;
                            }
/* 208 */                   if (heightStr != null) {
/* 209 */                       height = Integer.parseInt(heightStr);
                            } else {
/* 211 */                       height = -1;
                            }
/* 214 */                   int rotation = 0;
/* 215 */                   if (Build.VERSION.SDK_INT > 16) {
/* 216 */                       String rotationStr = retriever2.extractMetadata(24);
/* 217 */                       if (rotationStr != null) {
/* 218 */                           rotation = Integer.parseInt(rotationStr);
                                }
                            } else {
/* 221 */                       Bitmap b = retriever2.getFrameAtTime(0L);
/* 222 */                       if (b != null && width != height && height == b.getWidth()) {
/* 224 */                           rotation = 90;
                                }
                            }
/* 229 */                   if (width != -1 && height != -1 && videoDurationMs != -1) {
/* 230 */                       SLog.m464i("Width and height are avail from metadata.");
/* 231 */                       this.mThumbnailRangeFinder.setVideoPath(uri, videoDurationMs, this.mMaxDurationMs, forceMetadataRetriever);
/* 232 */                       prepareVideoView(width, height, rotation, videoDurationMs);
                            } else {
/* 234 */                       prepareVideoView(480, 480, rotation, videoDurationMs);
/* 235 */                       this.mTextureView.setVideoSizeChangedListener(new C02274(uri, forceMetadataRetriever));
                            }
/* 245 */                   this.mTextureView.setExoPlayerErrorListener(new C02285(uri));
/* 259 */                   this.mTextureView.setPlaybackEndedListener(new C02296());
/* 265 */                   this.mFileUri = uri;
/* 266 */                   preparePlayer(uri);
/* 267 */                   this.mPlayButton.setVisibility(4);
/* 270 */                   if (retriever2 != null) {
/* 271 */                       retriever2.release();
                            }
                        } catch (Throwable th) {
/* 270 */                   th = th;
                            retriever = retriever2;
                            if (retriever != null) {
/* 271 */                       retriever.release();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

                private Uri materializeVideoUri(Uri source) throws Exception {
                    if (source == null) {
                        throw new IllegalArgumentException("Selected video URI was null");
                    }
                    if ("file".equalsIgnoreCase(source.getScheme())) {
                        String path = source.getPath();
                        if (path != null && new File(path).isFile()) {
                            return source;
                        }
                        throw new IllegalArgumentException("Selected video file does not exist: " + source);
                    }

                    InputStream input = getContentResolver().openInputStream(source);
                    if (input == null) {
                        throw new IllegalArgumentException("Could not open selected video: " + source);
                    }
                    File directory = new File(getCacheDir(), "imported_videos");
                    if (!directory.exists() && !directory.mkdirs() && !directory.isDirectory()) {
                        throw new IllegalStateException("Could not create video import cache");
                    }
                    String extension = ".mp4";
                    String mime = getContentResolver().getType(source);
                    if (mime != null && mime.toLowerCase().contains("webm")) {
                        extension = ".webm";
                    } else if (mime != null && mime.toLowerCase().contains("3gpp")) {
                        extension = ".3gp";
                    } else {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(source, new String[]{"_display_name"}, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                String name = cursor.getString(0);
                                if (name != null) {
                                    int dot = name.lastIndexOf('.');
                                    if (dot >= 0 && dot < name.length() - 1) {
                                        extension = name.substring(dot).toLowerCase();
                                    }
                                }
                            }
                        } catch (Throwable ignored) {
                            // Some providers reject metadata queries; the stream is enough.
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }
                    File target = new File(directory, "selected_" + System.currentTimeMillis() + extension);
                    FileOutputStream output = null;
                    try {
                        output = new FileOutputStream(target);
                        byte[] buffer = new byte[32768];
                        int count;
                        long total = 0;
                        while ((count = input.read(buffer)) != -1) {
                            output.write(buffer, 0, count);
                            total += count;
                        }
                        output.flush();
                        if (total == 0 || !target.isFile()) {
                            throw new IllegalArgumentException("Selected video was empty");
                        }
                    } finally {
                        try {
                            input.close();
                        } catch (Throwable ignored2) {
                        }
                        if (output != null) {
                            try {
                                output.close();
                            } catch (Throwable ignored3) {
                            }
                        }
                    }
                    return Uri.fromFile(target);
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$4 */
/* 233 */       class C02274 implements StaticSizeExoPlayerTextureView.VideoSizeChangedListener {
                    final /* synthetic */ boolean val$forceMetadataRetriever;
                    final /* synthetic */ Uri val$uri;

/* 234 */           C02274(Uri uri, boolean z) {
                        this.val$uri = uri;
                        this.val$forceMetadataRetriever = z;
                    }

                    @Override // co.vine.android.player.StaticSizeExoPlayerTextureView.VideoSizeChangedListener
/* 237 */           public void onVideoSizeChanged(ExoPlayer player, int width, int height) {
/* 238 */               SLog.m464i("Width and height are avail from media player.");
/* 239 */               int duration = player.getDuration();
/* 240 */               ImportTrimCropActivity.access$200(ImportTrimCropActivity.this).setVideoPath(this.val$uri, duration, ImportTrimCropActivity.access$100(ImportTrimCropActivity.this), this.val$forceMetadataRetriever);
/* 241 */               ImportTrimCropActivity.access$300(ImportTrimCropActivity.this, width, height, 0, duration);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$5 */
/* 243 */       class C02285 implements StaticSizeExoPlayerTextureView.ExoPlayerErrorListener {
                    final /* synthetic */ Uri val$uri;

/* 244 */           C02285(Uri uri) {
                        this.val$uri = uri;
                    }

                    @Override // co.vine.android.player.StaticSizeExoPlayerTextureView.ExoPlayerErrorListener
/* 247 */           public void onError(ExoPlaybackException error) {
/* 248 */               SLog.m446d("Video preview is unsupported; continuing with the import pipeline: " + String.valueOf(error));
/* 256 */               ImportTrimCropActivity.this.mHasRetried = true;
/* 257 */               ImportTrimCropActivity.this.mTextureView.setPlayWhenReady(false);
/* 258 */               ImportTrimCropActivity.this.mPlayButton.setVisibility(8);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$6 */
/* 257 */       class C02296 implements StaticSizeExoPlayerTextureView.PlaybackEndedListener {
/* 258 */           C02296() {
                    }

                    @Override // co.vine.android.player.StaticSizeExoPlayerTextureView.PlaybackEndedListener
/* 261 */           public void onPlaybackEnded() {
/* 262 */               ImportTrimCropActivity.access$800(ImportTrimCropActivity.this).seekTo((int) (ImportTrimCropActivity.access$200(ImportTrimCropActivity.this).trimStartTimeUsec / 1000));
                    }
                }

/* 276 */       private void showInvalidVideoError(Uri uri, int userFacingStringRes, Object error) {
/* 277 */           CrashUtil.logException(new IllegalArgumentException(), "Failed to select video: {} {}.", uri, error);
/* 278 */           Util.showCenteredToast(this, getString(userFacingStringRes));
/* 279 */           setResult(0);
/* 280 */           finish();
                }

/* 283 */       private void preparePlayer(Uri uri) {
/* 284 */           if (uri != null) {
/* 285 */               this.mTextureView.openVideo(this.mFileUri);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 289 */       protected void onResume() {
/* 290 */           super.onResume();
/* 291 */           this.mHandler.post(this.mProgressChecker);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 295 */       protected void onPause() {
/* 296 */           super.onPause();
/* 297 */           this.mHandler.removeCallbacks(this.mProgressChecker);
/* 298 */           if (this.mTextureView.getPlayWhenReady()) {
/* 299 */               playPauseVideo();
                    }
                }

/* 303 */       private void prepareVideoView(int videoWidth, int videoHeight, int rotation, long videoDurationMs) {
                    ViewGroup videoViewPanner;
                    if (videoWidth <= 0 || videoHeight <= 0) {
                        videoWidth = 480;
                        videoHeight = 480;
                    }
/* 304 */           if (this.mVideoViewPanner != null) {
/* 305 */               this.mVideoViewPanner.removeView(this.mTextureView);
/* 306 */               this.mVideoContainer.removeView(this.mVideoViewPanner);
/* 307 */               this.mVideoViewPanner = null;
                    }
/* 310 */           StaticSizeExoPlayerTextureView textureView = this.mTextureView;
/* 314 */           if (rotation == 90 || rotation == 270) {
                        int rotatedWidth = videoHeight;
                        videoHeight = videoWidth;
                        videoWidth = rotatedWidth;
                    }
/* 320 */           if (videoWidth > videoHeight) {
/* 321 */               videoViewPanner = new HorizontalScrollView(this);
                        this.mPlayerWidth = (int) (((float) videoWidth / (float) videoHeight) * this.mSize.x);
/* 323 */               this.mPlayerHeight = this.mSize.x;
                    } else {
/* 325 */               videoViewPanner = new ScrollView(this);
/* 326 */               this.mPlayerWidth = this.mSize.x;
                        this.mPlayerHeight = (int) (((float) videoHeight / (float) videoWidth) * this.mSize.x);
                    }
/* 329 */           textureView.setSize(this.mPlayerWidth, this.mPlayerHeight);
/* 330 */           textureView.setSurfaceRotation(rotation);
/* 332 */           videoViewPanner.addView(textureView);
/* 333 */           videoViewPanner.setHorizontalScrollBarEnabled(false);
/* 334 */           videoViewPanner.setVerticalScrollBarEnabled(false);
/* 335 */           this.mVideoContainer.addView(videoViewPanner, 0, new LinearLayout.LayoutParams(this.mSize.x, this.mSize.x));
/* 337 */           this.mVideoViewPanner = videoViewPanner;
/* 338 */           this.mVideoWidth = videoWidth;
                    SLog.m449d("mjama  width={}, height={}, ratio={}", Integer.valueOf(this.mPlayerWidth), Integer.valueOf(this.mPlayerHeight), Float.valueOf((float) videoWidth / (float) videoHeight));
/* 341 */           this.mVideoHeight = videoHeight;
/* 342 */           this.mProgressView.setProgressRatio(this.mStartRatio + (videoDurationMs / this.mMaxDurationMs));
                }

                @Override // android.view.View.OnClickListener
/* 346 */       public void onClick(View view) {
/* 347 */           if (view.getId() == 2131427603) {
/* 348 */               playPauseVideo();
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 353 */       public boolean onCreateOptionsMenu(Menu menu) {
/* 354 */           getMenuInflater().inflate(C0257R.menu.import_trim, menu);
/* 355 */           return true;
                }

                @Override // co.vine.android.widget.trimControls.ThumbnailRangeFinderLayout.OnVideoTrimmedListener
/* 359 */       public void onVideoTrimmedByScrubber() {
/* 360 */           updatePosition(((int) this.mThumbnailRangeFinder.trimEndTimeUsec) / 1000);
                }

                @Override // co.vine.android.widget.trimControls.ThumbnailRangeFinderLayout.OnVideoTrimmedListener
/* 364 */       public void onVideoTrimmedByCarousel() {
/* 365 */           updatePosition(((int) this.mThumbnailRangeFinder.trimStartTimeUsec) / 1000);
                }

                @Override // co.vine.android.widget.trimControls.ThumbnailRangeFinderLayout.OnVideoTrimmedListener
/* 369 */       public void onStartEndTimeChanged(long trimStartTimeUsec, long trimEndTimeUsec) {
/* 370 */           this.mProgressView.setProgressRatio(this.mStartRatio + (((trimEndTimeUsec - trimStartTimeUsec) / 1000.0f) / this.mMaxDurationMs));
                }

/* 373 */       private void updatePosition(int position) {
/* 374 */           this.mTextureView.setPlayWhenReady(this.mTextureView.getPlayWhenReady());
/* 375 */           this.mTextureView.seekTo(position);
/* 376 */           this.mPlayButton.setVisibility(this.mTextureView.getPlayWhenReady() ? 8 : 0);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 380 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 381 */           int id = item.getItemId();
/* 382 */           if (id == 2131427600) {
/* 383 */               add();
                    } else if (id == 16908332) {
/* 385 */               release();
                    }
/* 387 */           return super.onOptionsItemSelected(item);
                }

/* 390 */       private void release() {
/* 391 */           if (this.mThumbnailRangeFinder != null) {
/* 392 */               this.mThumbnailRangeFinder.stop();
                    }
/* 394 */           if (this.mTextureView != null) {
/* 395 */               this.mTextureView.release();
                    }
                }

/* 401 */       private void add() {
                    if (this.mAddInProgress) {
                        return;
                    }
/* 402 */           if (this.mVideoViewPanner != null || System.currentTimeMillis() - this.mAddRequestTime < 200) {
                        this.mAddInProgress = true;
                        if (getIntent().getBooleanExtra(EXTRA_GALLERY_ONLY, false)) {
                            completeGalleryUpload();
                            return;
                        }
/* 403 */               this.mThumbnailRangeFinder.computeStartEndTimes();
/* 404 */               Intent data = new Intent(ACTION_VIDEO_PICKED, this.mFileUri);
/* 405 */               data.putExtra(EXTRA_TRIM_START_TIME_USEC, this.mThumbnailRangeFinder.trimStartTimeUsec);
/* 406 */               data.putExtra(EXTRA_TRIM_END_TIME_USEC, this.mThumbnailRangeFinder.trimEndTimeUsec);
/* 407 */               Point point = new Point();
/* 408 */               if (this.mVideoViewPanner != null) {
/* 409 */                   point.x = (int) ((this.mVideoViewPanner.getScrollX() / this.mPlayerWidth) * this.mVideoWidth);
/* 410 */                   point.y = (int) ((this.mVideoViewPanner.getScrollY() / this.mPlayerHeight) * this.mVideoHeight);
                        }
/* 412 */               SLog.m448d("mjama  origin.x={}, origin.y={}", Integer.valueOf(point.x), Integer.valueOf(point.y));
/* 413 */               data.putExtra(EXTRA_TRIM_CROP_ORIGIN, point);
/* 414 */               setResult(-1, data);
/* 415 */               release();
/* 416 */               finish();
/* 417 */               SLog.m448d("mjama  trimStart={}, trimEnd={}", Long.valueOf(this.mThumbnailRangeFinder.trimStartTimeUsec), Long.valueOf(this.mThumbnailRangeFinder.trimEndTimeUsec));
/* 422 */               return;
                    }
/* 420 */           this.mAddRequestTime = System.currentTimeMillis();
                }

                private void completeGalleryUpload() {
                    if (this.mFileUri == null || this.mFileUri.getPath() == null) {
                        Util.showCenteredToast(this, getString(C0257R.string.error_import_video_unknown_error));
                        return;
                    }
                    File thumbnail = new File(getCacheDir(), "gallery_thumb_" + System.currentTimeMillis() + ".jpg");
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    Bitmap frame = null;
                    try {
                        retriever.setDataSource(this, this.mFileUri);
                        frame = retriever.getFrameAtTime(0L, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                        if (frame == null) {
                            throw new IllegalStateException("No video frame available");
                        }
                        FileOutputStream out = new FileOutputStream(thumbnail);
                        frame.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.close();
                        String path = UploadManager.addGalleryUpload(this, this.mFileUri.getPath(), thumbnail.getPath(), "gallery");
                        startActivity(PostActivity.getIntent(this, path, RecordConfigUtils.getThumbnailPath(path), null, false, false));
                        release();
                        finish();
                    } catch (Throwable error) {
                        CrashUtil.logException(error, "Failed to queue gallery video", new Object[0]);
                        this.mAddInProgress = false;
                        Util.showCenteredToast(this, getString(C0257R.string.error_import_video_unknown_error));
                    } finally {
                        if (frame != null) {
                            frame.recycle();
                        }
                        try {
                            retriever.release();
                        } catch (Throwable ignored) {
                        }
                        if (thumbnail.exists()) {
                            thumbnail.delete();
                        }
                    }
                }

/* 424 */       private void playPauseVideo() {
/* 425 */           if (this.mTextureView != null) {
/* 426 */               if (this.mTextureView.getPlayWhenReady()) {
/* 427 */                   this.mTextureView.setPlayWhenReady(false);
/* 428 */                   this.mPlayButton.setVisibility(0);
                        } else {
/* 430 */                   this.mTextureView.seekTo(((int) this.mThumbnailRangeFinder.trimStartTimeUsec) / 1000);
/* 431 */                   this.mTextureView.setPlayWhenReady(true);
/* 432 */                   this.mPlayButton.setVisibility(8);
                        }
                    }
                }

/* 437 */       private int checkTimeBar() {
/* 438 */           int trimEndTimeMs = ((int) this.mThumbnailRangeFinder.trimEndTimeUsec) / 1000;
/* 439 */           int position = this.mTextureView.getCurrentPosition();
/* 440 */           if (position >= trimEndTimeMs && trimEndTimeMs > 0) {
/* 441 */               this.mTextureView.seekTo((int) (this.mThumbnailRangeFinder.trimStartTimeUsec / 1000));
/* 442 */               this.mTextureView.setPlayWhenReady(true);
                    }
/* 444 */           return position;
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$7 */
/* 445 */       class RunnableC02307 implements Runnable {
/* 446 */           RunnableC02307() {
                    }

                    @Override // java.lang.Runnable
/* 449 */           public void run() {
/* 450 */               int position = ImportTrimCropActivity.access$900(ImportTrimCropActivity.this);
/* 451 */               ImportTrimCropActivity.access$1100(ImportTrimCropActivity.this).postDelayed(ImportTrimCropActivity.access$1000(ImportTrimCropActivity.this), Math.max(66, 200 - (position % 200)));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ImportTrimCropActivity$8 */
/* 453 */       class ViewOnTouchListenerC02318 implements View.OnTouchListener {
                    private long mTouchTime;

/* 454 */           ViewOnTouchListenerC02318() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 459 */           public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case 0:
/* 462 */                       this.mTouchTime = System.currentTimeMillis();
                                break;
                            case 1:
                                if (System.currentTimeMillis() - this.mTouchTime < 200) {
/* 468 */                           ImportTrimCropActivity.access$1200(ImportTrimCropActivity.this);
                                }
                                break;
                        }
/* 473 */               return true;
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 478 */       public void onBackPressed() {
/* 479 */           release();
/* 480 */           super.onBackPressed();
                }
            }
