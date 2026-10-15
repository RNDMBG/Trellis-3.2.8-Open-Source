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
            
            
    public class ImportTrimCropActivity extends BaseActionBarActivity implements View.OnClickListener, ThumbnailRangeFinderLayout.OnVideoTrimmedListener {
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

        static  void access$000(ImportTrimCropActivity x0) {
            x0.add();
                }

        static  int access$100(ImportTrimCropActivity x0) {
            return x0.mMaxDurationMs;
                }

        static  Runnable access$1000(ImportTrimCropActivity x0) {
            return x0.mProgressChecker;
                }

        static  Handler access$1100(ImportTrimCropActivity x0) {
            return x0.mHandler;
                }

        static  void access$1200(ImportTrimCropActivity x0) {
            x0.playPauseVideo();
                }

        static  ThumbnailRangeFinderLayout access$200(ImportTrimCropActivity x0) {
            return x0.mThumbnailRangeFinder;
                }

        static  void access$300(ImportTrimCropActivity x0, int x1, int x2, int x3, long x4) {
            x0.prepareVideoView(x1, x2, x3, x4);
                }

        static  boolean access$400(ImportTrimCropActivity x0) {
            return x0.mHasRetried;
                }

        static  void access$500(ImportTrimCropActivity x0) {
            x0.release();
                }

        static  Uri access$600(ImportTrimCropActivity x0) {
            return x0.mFileUri;
                }

        static  void access$700(ImportTrimCropActivity x0, Uri x1, int x2, Object x3) {
            x0.showInvalidVideoError(x1, x2, x3);
                }

        static  StaticSizeExoPlayerTextureView access$800(ImportTrimCropActivity x0) {
            return x0.mTextureView;
                }

        static  int access$900(ImportTrimCropActivity x0) {
            return x0.checkTimeBar();
                }

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            onCreate(savedInstanceState, R.layout.import_trim_crop_activity, true, true);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired, boolean readOnly) {
            super.onCreate(savedInstanceState, layout, loginRequired, readOnly);
            this.mStartDurationMs = 0;
            this.mMaxDurationMs = 6000;
            if (getIntent() != null && getIntent().getExtras() != null) {
                Bundle extras = getIntent().getExtras();
                this.mStartDurationMs = extras.getInt(EXTRA_DURATION);
                this.mMaxDurationMs = extras.getInt(EXTRA_MAX_DURATION);
                    }
            if (this.mMaxDurationMs <= 0) {
                this.mMaxDurationMs = 6000;
                    }
            if (this.mStartDurationMs < 0) {
                this.mStartDurationMs = 0;
                    } else if (this.mStartDurationMs > this.mMaxDurationMs) {
                this.mStartDurationMs = this.mMaxDurationMs;
                    }
            Point size = SystemUtil.getDisplaySize((Activity) this);
            this.mHandler = new Handler(Looper.getMainLooper());
            ViewGroup rootView = (ViewGroup) findViewById(R.id.import_view_root);
            this.mVideoContainer = (FrameLayout) findViewById(R.id.video_container);
            this.mPlayButton = (ImageButton) findViewById(R.id.import_play_button);
           this.mProgressView = (RegularProgressView) findViewById(R.id.import_progress_view);
           this.mProgressView.setSelectedColor(getResources().getColor(R.color.vine_green));
           this.mProgressView.setColor(getResources().getColor(R.color.trim_yellow));
           this.mStartRatio = this.mStartDurationMs / this.mMaxDurationMs;
           this.mProgressView.setProgressRatio(this.mStartRatio);
           this.mProgressView.setSelectedSection(0.0f, this.mStartRatio);
           findViewById(R.id.importTitle).setOnClickListener(new ViewOnClickListenerC02241());
           findViewById(R.id.trimCropBackButton).setOnClickListener(new ViewOnClickListenerC02252());
           findViewById(R.id.add).setOnClickListener(new ViewOnClickListenerC02263());
           int height = getResources().getDimensionPixelSize(R.dimen.carousel_height);
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, height);
           ThumbnailRangeFinderLayout thumbnailRangeFinder = new ThumbnailRangeFinderLayout(this, (int) (this.mStartRatio * size.x));
           thumbnailRangeFinder.setListener(this);
           rootView.addView(thumbnailRangeFinder, params);
           this.mThumbnailRangeFinder = thumbnailRangeFinder;
           StaticSizeExoPlayerTextureView videoView = new StaticSizeExoPlayerTextureView(this);
           videoView.setOnTouchListener(this.mVideoViewTouchListener);
           videoView.setSize(size.x, size.x);
           this.mTextureView = videoView;
           this.mSize = size;
           if (getIntent().getData() == null) {
                            Intent pickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            pickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                            pickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               pickerIntent.setType("video/*");
               pickerIntent.putExtra("android.intent.extra.LOCAL_ONLY", true);
                        try {
                   startActivityForResult(Intent.createChooser(pickerIntent, getString(R.string.pick_video)), 2);
                   return;
                        } catch (ActivityNotFoundException e) {
                   Util.showCenteredToast(this, getString(R.string.unsupported_feature) + " \n (" + getString(R.string.pick_video) + ") ");
                   return;
                        }
                    }
           this.mHasRetried = true;
                    try {
               onUriResult(getIntent().getData(), true);
                    } catch (Throwable e) {
                        Log.e("ImportTrimCropActivity", "Unable to open selected video", e);
                        finish();
                    }
                }

                
       class ViewOnClickListenerC02241 implements View.OnClickListener {
           ViewOnClickListenerC02241() {
                    }

                    @Override 
           public void onClick(View v) {
               ImportTrimCropActivity.this.onBackPressed();
                    }
                }

                
       class ViewOnClickListenerC02252 implements View.OnClickListener {
           ViewOnClickListenerC02252() {
                    }

                    @Override 
           public void onClick(View v) {
               ImportTrimCropActivity.this.onBackPressed();
                    }
                }

                
       class ViewOnClickListenerC02263 implements View.OnClickListener {
           ViewOnClickListenerC02263() {
                    }

                    @Override 
           public void onClick(View v) {
               ImportTrimCropActivity.access$000(ImportTrimCropActivity.this);
                    }
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           if (resultCode == -1) {
               if (requestCode == 2 && data != null) {
                   Uri uri = data.getData();
                            if (uri == null) {
                                Util.showCenteredToast(this, getString(R.string.error_import_video_unknown_error));
                                finish();
                                return;
                            }
                            if (Build.VERSION.SDK_INT >= 19 && (data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION) != 0) {
                                try {
                                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                } catch (Throwable ignored) {
                                    
                                }
                            }
                            try {
                       onUriResult(uri, false);
                       return;
                            } catch (Throwable th) {
                       showInvalidVideoError(uri, R.string.error_import_video_unknown_error, "Failed to validate video.");
                       return;
                            }
                        }
               if (requestCode == 1) {
                   setResult(resultCode, data);
                   release();
                   finish();
                   return;
                        }
               return;
                    }
           finish();
                }

                @TargetApi(17)
       private void onUriResult(Uri uri, boolean forceMetadataRetriever) throws Throwable {
                    uri = materializeVideoUri(uri);
                    long videoDurationMs;
                    int width;
                    int height;
           String filePath = SdkFileUtils.getPath(this, uri);
           if (filePath == null) {
               throw new IllegalArgumentException("File path was null: " + uri);
                    }
           MediaMetadataRetriever retriever = null;
                    try {
               MediaMetadataRetriever retriever2 = new MediaMetadataRetriever();
                        try {
                   retriever2.setDataSource(filePath);
                   String durationMsStr = retriever2.extractMetadata(9);
                   String widthStr = retriever2.extractMetadata(18);
                   String heightStr = retriever2.extractMetadata(19);
                   if (durationMsStr != null) {
                       videoDurationMs = Long.parseLong(durationMsStr);
                            } else {
                       videoDurationMs = -1;
                            }
                   if (widthStr != null) {
                       width = Integer.parseInt(widthStr);
                            } else {
                       width = -1;
                            }
                   if (heightStr != null) {
                       height = Integer.parseInt(heightStr);
                            } else {
                       height = -1;
                            }
                   int rotation = 0;
                   if (Build.VERSION.SDK_INT > 16) {
                       String rotationStr = retriever2.extractMetadata(24);
                       if (rotationStr != null) {
                           rotation = Integer.parseInt(rotationStr);
                                }
                            } else {
                       Bitmap b = retriever2.getFrameAtTime(0L);
                       if (b != null && width != height && height == b.getWidth()) {
                           rotation = 90;
                                }
                            }
                   if (width != -1 && height != -1 && videoDurationMs != -1) {
                       SLog.m464i("Width and height are avail from metadata.");
                       this.mThumbnailRangeFinder.setVideoPath(uri, videoDurationMs, this.mMaxDurationMs, forceMetadataRetriever);
                       prepareVideoView(width, height, rotation, videoDurationMs);
                            } else {
                       prepareVideoView(480, 480, rotation, videoDurationMs);
                       this.mTextureView.setVideoSizeChangedListener(new C02274(uri, forceMetadataRetriever));
                            }
                   this.mTextureView.setExoPlayerErrorListener(new C02285(uri));
                   this.mTextureView.setPlaybackEndedListener(new C02296());
                   this.mFileUri = uri;
                   preparePlayer(uri);
                   this.mPlayButton.setVisibility(4);
                   if (retriever2 != null) {
                       retriever2.release();
                            }
                        } catch (Throwable th) {
                   th = th;
                            retriever = retriever2;
                            if (retriever != null) {
                       retriever.release();
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

                
       class C02274 implements StaticSizeExoPlayerTextureView.VideoSizeChangedListener {
                    final  boolean val$forceMetadataRetriever;
                    final  Uri val$uri;

           C02274(Uri uri, boolean z) {
                        this.val$uri = uri;
                        this.val$forceMetadataRetriever = z;
                    }

                    @Override 
           public void onVideoSizeChanged(ExoPlayer player, int width, int height) {
               SLog.m464i("Width and height are avail from media player.");
               int duration = player.getDuration();
               ImportTrimCropActivity.access$200(ImportTrimCropActivity.this).setVideoPath(this.val$uri, duration, ImportTrimCropActivity.access$100(ImportTrimCropActivity.this), this.val$forceMetadataRetriever);
               ImportTrimCropActivity.access$300(ImportTrimCropActivity.this, width, height, 0, duration);
                    }
                }

                
       class C02285 implements StaticSizeExoPlayerTextureView.ExoPlayerErrorListener {
                    final  Uri val$uri;

           C02285(Uri uri) {
                        this.val$uri = uri;
                    }

                    @Override 
           public void onError(ExoPlaybackException error) {
               SLog.m446d("Video preview is unsupported; continuing with the import pipeline: " + String.valueOf(error));
               ImportTrimCropActivity.this.mHasRetried = true;
               ImportTrimCropActivity.this.mTextureView.setPlayWhenReady(false);
               ImportTrimCropActivity.this.mPlayButton.setVisibility(8);
                    }
                }

                
       class C02296 implements StaticSizeExoPlayerTextureView.PlaybackEndedListener {
           C02296() {
                    }

                    @Override 
           public void onPlaybackEnded() {
               ImportTrimCropActivity.access$800(ImportTrimCropActivity.this).seekTo((int) (ImportTrimCropActivity.access$200(ImportTrimCropActivity.this).trimStartTimeUsec / 1000));
                    }
                }

       private void showInvalidVideoError(Uri uri, int userFacingStringRes, Object error) {
           CrashUtil.logException(new IllegalArgumentException(), "Failed to select video: {} {}.", uri, error);
           Util.showCenteredToast(this, getString(userFacingStringRes));
           setResult(0);
           finish();
                }

       private void preparePlayer(Uri uri) {
           if (uri != null) {
               this.mTextureView.openVideo(this.mFileUri);
                    }
                }

                @Override 
       protected void onResume() {
           super.onResume();
           this.mHandler.post(this.mProgressChecker);
                }

                @Override 
       protected void onPause() {
           super.onPause();
           this.mHandler.removeCallbacks(this.mProgressChecker);
           if (this.mTextureView.getPlayWhenReady()) {
               playPauseVideo();
                    }
                }

       private void prepareVideoView(int videoWidth, int videoHeight, int rotation, long videoDurationMs) {
                    ViewGroup videoViewPanner;
                    if (videoWidth <= 0 || videoHeight <= 0) {
                        videoWidth = 480;
                        videoHeight = 480;
                    }
           if (this.mVideoViewPanner != null) {
               this.mVideoViewPanner.removeView(this.mTextureView);
               this.mVideoContainer.removeView(this.mVideoViewPanner);
               this.mVideoViewPanner = null;
                    }
           StaticSizeExoPlayerTextureView textureView = this.mTextureView;
           if (rotation == 90 || rotation == 270) {
                        int rotatedWidth = videoHeight;
                        videoHeight = videoWidth;
                        videoWidth = rotatedWidth;
                    }
           if (videoWidth > videoHeight) {
               videoViewPanner = new HorizontalScrollView(this);
                        this.mPlayerWidth = (int) (((float) videoWidth / (float) videoHeight) * this.mSize.x);
               this.mPlayerHeight = this.mSize.x;
                    } else {
               videoViewPanner = new ScrollView(this);
               this.mPlayerWidth = this.mSize.x;
                        this.mPlayerHeight = (int) (((float) videoHeight / (float) videoWidth) * this.mSize.x);
                    }
           textureView.setSize(this.mPlayerWidth, this.mPlayerHeight);
           textureView.setSurfaceRotation(rotation);
           videoViewPanner.addView(textureView);
           videoViewPanner.setHorizontalScrollBarEnabled(false);
           videoViewPanner.setVerticalScrollBarEnabled(false);
           this.mVideoContainer.addView(videoViewPanner, 0, new LinearLayout.LayoutParams(this.mSize.x, this.mSize.x));
           this.mVideoViewPanner = videoViewPanner;
           this.mVideoWidth = videoWidth;
                    SLog.m449d("mjama  width={}, height={}, ratio={}", Integer.valueOf(this.mPlayerWidth), Integer.valueOf(this.mPlayerHeight), Float.valueOf((float) videoWidth / (float) videoHeight));
           this.mVideoHeight = videoHeight;
           this.mProgressView.setProgressRatio(this.mStartRatio + (videoDurationMs / this.mMaxDurationMs));
                }

                @Override 
       public void onClick(View view) {
           if (view.getId() == 2131427603) {
               playPauseVideo();
                    }
                }

                @Override 
       public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.import_trim, menu);
           return true;
                }

                @Override 
       public void onVideoTrimmedByScrubber() {
           updatePosition(((int) this.mThumbnailRangeFinder.trimEndTimeUsec) / 1000);
                }

                @Override 
       public void onVideoTrimmedByCarousel() {
           updatePosition(((int) this.mThumbnailRangeFinder.trimStartTimeUsec) / 1000);
                }

                @Override 
       public void onStartEndTimeChanged(long trimStartTimeUsec, long trimEndTimeUsec) {
           this.mProgressView.setProgressRatio(this.mStartRatio + (((trimEndTimeUsec - trimStartTimeUsec) / 1000.0f) / this.mMaxDurationMs));
                }

       private void updatePosition(int position) {
           this.mTextureView.setPlayWhenReady(this.mTextureView.getPlayWhenReady());
           this.mTextureView.seekTo(position);
           this.mPlayButton.setVisibility(this.mTextureView.getPlayWhenReady() ? 8 : 0);
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id == 2131427600) {
               add();
                    } else if (id == 16908332) {
               release();
                    }
           return super.onOptionsItemSelected(item);
                }

       private void release() {
           if (this.mThumbnailRangeFinder != null) {
               this.mThumbnailRangeFinder.stop();
                    }
           if (this.mTextureView != null) {
               this.mTextureView.release();
                    }
                }

       private void add() {
                    if (this.mAddInProgress) {
                        return;
                    }
           if (this.mVideoViewPanner != null || System.currentTimeMillis() - this.mAddRequestTime < 200) {
                        this.mAddInProgress = true;
                        if (getIntent().getBooleanExtra(EXTRA_GALLERY_ONLY, false)) {
                            completeGalleryUpload();
                            return;
                        }
               this.mThumbnailRangeFinder.computeStartEndTimes();
               Intent data = new Intent(ACTION_VIDEO_PICKED, this.mFileUri);
               data.putExtra(EXTRA_TRIM_START_TIME_USEC, this.mThumbnailRangeFinder.trimStartTimeUsec);
               data.putExtra(EXTRA_TRIM_END_TIME_USEC, this.mThumbnailRangeFinder.trimEndTimeUsec);
               Point point = new Point();
               if (this.mVideoViewPanner != null) {
                   point.x = (int) ((this.mVideoViewPanner.getScrollX() / this.mPlayerWidth) * this.mVideoWidth);
                   point.y = (int) ((this.mVideoViewPanner.getScrollY() / this.mPlayerHeight) * this.mVideoHeight);
                        }
               SLog.m448d("mjama  origin.x={}, origin.y={}", Integer.valueOf(point.x), Integer.valueOf(point.y));
               data.putExtra(EXTRA_TRIM_CROP_ORIGIN, point);
               setResult(-1, data);
               release();
               finish();
               SLog.m448d("mjama  trimStart={}, trimEnd={}", Long.valueOf(this.mThumbnailRangeFinder.trimStartTimeUsec), Long.valueOf(this.mThumbnailRangeFinder.trimEndTimeUsec));
               return;
                    }
           this.mAddRequestTime = System.currentTimeMillis();
                }

                private void completeGalleryUpload() {
                    if (this.mFileUri == null || this.mFileUri.getPath() == null) {
                        Util.showCenteredToast(this, getString(R.string.error_import_video_unknown_error));
                        this.mAddInProgress = false;
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
                        Util.showCenteredToast(this, getString(R.string.error_import_video_unknown_error));
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

       private void playPauseVideo() {
           if (this.mTextureView != null) {
               if (this.mTextureView.getPlayWhenReady()) {
                   this.mTextureView.setPlayWhenReady(false);
                   this.mPlayButton.setVisibility(0);
                        } else {
                   this.mTextureView.seekTo(((int) this.mThumbnailRangeFinder.trimStartTimeUsec) / 1000);
                   this.mTextureView.setPlayWhenReady(true);
                   this.mPlayButton.setVisibility(8);
                        }
                    }
                }

       private int checkTimeBar() {
           int trimEndTimeMs = ((int) this.mThumbnailRangeFinder.trimEndTimeUsec) / 1000;
           int position = this.mTextureView.getCurrentPosition();
           if (position >= trimEndTimeMs && trimEndTimeMs > 0) {
               this.mTextureView.seekTo((int) (this.mThumbnailRangeFinder.trimStartTimeUsec / 1000));
               this.mTextureView.setPlayWhenReady(true);
                    }
           return position;
                }

                
       class RunnableC02307 implements Runnable {
           RunnableC02307() {
                    }

                    @Override 
           public void run() {
               int position = ImportTrimCropActivity.access$900(ImportTrimCropActivity.this);
               ImportTrimCropActivity.access$1100(ImportTrimCropActivity.this).postDelayed(ImportTrimCropActivity.access$1000(ImportTrimCropActivity.this), Math.max(66, 200 - (position % 200)));
                    }
                }

                
       class ViewOnTouchListenerC02318 implements View.OnTouchListener {
                    private long mTouchTime;

           ViewOnTouchListenerC02318() {
                    }

                    @Override 
           public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case 0:
                       this.mTouchTime = System.currentTimeMillis();
                                break;
                            case 1:
                                if (System.currentTimeMillis() - this.mTouchTime < 200) {
                           ImportTrimCropActivity.access$1200(ImportTrimCropActivity.this);
                                }
                                break;
                        }
               return true;
                    }
                }

                @Override 
       public void onBackPressed() {
           release();
           super.onBackPressed();
                }
            }
