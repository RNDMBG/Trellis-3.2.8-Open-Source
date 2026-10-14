            package co.vine.android.widget;

            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.view.ViewGroup;
            import co.vine.android.C0257R;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.player.StaticSizeVideoView;
            import co.vine.android.player.VideoViewInterface;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class TrimVideo implements VideoViewInterface.OnErrorListener, VideoViewInterface.OnCompletionListener, TrimControllerOverlay.OverlayListener {
                public static final String KEY_TRIM_END = "trim_end";
                public static final String KEY_TRIM_START = "trim_start";
                public static final String KEY_VIDEO_POSITION = "video_position";
                private TrimControllerOverlay mControllerView;
                private ProgressDialog mProgress;
                private StaticSizeVideoView mVideoView;
                private final Handler mHandler = new Handler();
                private boolean mHasPaused = false;
                private int mVideoPositionMs = 0;
                public int trimStartTime = 0;
                public int trimEndTime = 0;
                private final Runnable mProgressChecker = new RunnableC05751();

/* 14 */        static /* synthetic */ int access$000(TrimVideo x0) {
/* 15 */            return x0.setTimeBar();
                }

/* 14 */        static /* synthetic */ Runnable access$100(TrimVideo x0) {
/* 15 */            return x0.mProgressChecker;
                }

/* 14 */        static /* synthetic */ Handler access$200(TrimVideo x0) {
/* 15 */            return x0.mHandler;
                }

/* 30 */        public TrimVideo(ViewGroup rootView, Activity activity, Uri videoUri) {
/* 32 */            StaticSizeVideoView videoView = (StaticSizeVideoView) rootView.findViewById(C0257R.id.sdkVideoView);
/* 33 */            videoView.setUseVinePlayer(false);
/* 34 */            videoView.setOnErrorListener(this);
/* 35 */            videoView.setOnCompletionListener(this);
/* 36 */            videoView.setVideoURI(videoUri);
/* 37 */            this.mVideoView = videoView;
/* 39 */            TrimControllerOverlay controllerView = new TrimControllerOverlay(activity, videoView.getHeight());
/* 40 */            controllerView.setListener(this);
/* 41 */            rootView.addView(controllerView);
/* 42 */            this.mControllerView = controllerView;
/* 44 */            playVideo();
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TrimVideo$1 */
/* 45 */        class RunnableC05751 implements Runnable {
/* 46 */            RunnableC05751() {
                    }

                    @Override // java.lang.Runnable
/* 49 */            public void run() {
/* 50 */                int pos = TrimVideo.access$000(TrimVideo.this);
/* 51 */                TrimVideo.access$200(TrimVideo.this).postDelayed(TrimVideo.access$100(TrimVideo.this), 200 - (pos % 200));
                    }
                }

/* 55 */        public void onSaveInstanceState(Bundle savedInstanceState) {
/* 56 */            savedInstanceState.putInt(KEY_TRIM_START, this.trimStartTime);
/* 57 */            savedInstanceState.putInt(KEY_TRIM_END, this.trimEndTime);
/* 58 */            savedInstanceState.putInt(KEY_VIDEO_POSITION, this.mVideoPositionMs);
                }

/* 61 */        public void onRestoreInstanceState(Bundle savedInstanceState) {
/* 62 */            this.trimStartTime = savedInstanceState.getInt(KEY_TRIM_START, 0);
/* 63 */            this.trimEndTime = savedInstanceState.getInt(KEY_TRIM_END, 0);
/* 64 */            this.mVideoPositionMs = savedInstanceState.getInt(KEY_VIDEO_POSITION, 0);
                }

/* 67 */        private int setTimeBar() {
/* 68 */            SdkVideoView videoView = this.mVideoView;
/* 69 */            int duration = videoView.getDuration();
/* 70 */            int videoPositionMs = videoView.getCurrentPosition();
/* 72 */            if (duration > 0 && this.trimEndTime == 0) {
/* 73 */                this.trimEndTime = duration;
                    }
/* 76 */            if (this.mVideoPositionMs < this.trimStartTime) {
/* 77 */                videoView.seekTo(this.trimStartTime);
/* 78 */                videoPositionMs = this.trimStartTime;
                    }
/* 81 */            if (videoPositionMs >= this.trimEndTime && this.trimEndTime > 0) {
/* 82 */                if (videoPositionMs > this.trimEndTime) {
/* 83 */                    videoPositionMs = this.trimEndTime;
/* 84 */                    videoView.seekTo(videoPositionMs);
                        }
/* 86 */                this.mControllerView.showEnded();
/* 87 */                videoView.pause();
                    }
/* 90 */            this.mControllerView.setTimes(videoPositionMs, duration, this.trimStartTime, this.trimEndTime);
/* 91 */            this.mVideoPositionMs = videoPositionMs;
/* 92 */            return this.mVideoPositionMs;
                }

/* 95 */        private void playVideo() {
/* 96 */            this.mVideoView.start();
/* 97 */            this.mControllerView.showPlaying();
/* 98 */            setTimeBar();
                }

/* 101 */       private void pauseVideo() {
/* 102 */           this.mVideoView.pause();
/* 103 */           this.mControllerView.showPaused();
                }

/* 106 */       public void onResume() {
/* 107 */           if (this.mHasPaused) {
/* 108 */               this.mVideoView.seekTo(this.mVideoPositionMs);
/* 109 */               this.mVideoView.resume();
/* 110 */               this.mHasPaused = false;
                    }
/* 112 */           this.mHandler.post(this.mProgressChecker);
                }

/* 115 */       public void onPause() {
/* 116 */           this.mHasPaused = true;
/* 117 */           this.mHandler.removeCallbacksAndMessages(null);
/* 118 */           this.mVideoPositionMs = this.mVideoView.getCurrentPosition();
/* 119 */           this.mVideoView.suspend();
                }

/* 122 */       public void onStop() {
/* 123 */           if (this.mProgress != null) {
/* 124 */               this.mProgress.dismiss();
/* 125 */               this.mProgress = null;
                    }
                }

/* 129 */       public void onDestroy() {
/* 130 */           this.mVideoView.stopPlayback();
                }

                @Override // co.vine.android.widget.TrimControllerOverlay.OverlayListener
/* 134 */       public void onPlayPause() {
/* 135 */           if (this.mVideoView.isPlaying()) {
/* 136 */               pauseVideo();
                    } else {
/* 138 */               playVideo();
                    }
                }

                @Override // co.vine.android.widget.TrimControllerOverlay.OverlayListener
/* 143 */       public void onSeekStart() {
/* 144 */           pauseVideo();
                }

                @Override // co.vine.android.widget.TrimControllerOverlay.OverlayListener
/* 148 */       public void onSeekMove(int time) {
/* 149 */           this.mVideoView.seekTo((time / 100) * 100);
                }

                @Override // co.vine.android.widget.TrimControllerOverlay.OverlayListener
/* 153 */       public void onSeekEnd(int time, int start, int end) {
/* 154 */           this.mVideoView.seekTo(time);
/* 155 */           this.trimStartTime = start;
/* 156 */           this.trimEndTime = end;
/* 157 */           setTimeBar();
                }

                @Override // co.vine.android.widget.TrimControllerOverlay.OverlayListener
/* 161 */       public void onReplay() {
/* 162 */           this.mVideoView.seekTo(this.trimStartTime);
/* 163 */           playVideo();
                }

                @Override // co.vine.android.player.VideoViewInterface.OnCompletionListener
/* 167 */       public void onCompletion(VideoViewInterface view) {
/* 168 */           this.mControllerView.showEnded();
                }

                @Override // co.vine.android.player.VideoViewInterface.OnErrorListener
/* 172 */       public boolean onError(VideoViewInterface view, int what, int extra) {
/* 173 */           return false;
                }
            }
