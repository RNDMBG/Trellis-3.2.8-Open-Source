            package co.vine.android.widget;

            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.view.ViewGroup;
            import co.vine.android.R;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.player.StaticSizeVideoView;
            import co.vine.android.player.VideoViewInterface;
            
            
    public class TrimVideo implements VideoViewInterface.OnErrorListener, VideoViewInterface.OnCompletionListener, TrimControllerOverlay.OverlayListener {
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

        static  int access$000(TrimVideo x0) {
            return x0.setTimeBar();
                }

        static  Runnable access$100(TrimVideo x0) {
            return x0.mProgressChecker;
                }

        static  Handler access$200(TrimVideo x0) {
            return x0.mHandler;
                }

        public TrimVideo(ViewGroup rootView, Activity activity, Uri videoUri) {
            StaticSizeVideoView videoView = (StaticSizeVideoView) rootView.findViewById(R.id.sdkVideoView);
            videoView.setUseVinePlayer(false);
            videoView.setOnErrorListener(this);
            videoView.setOnCompletionListener(this);
            videoView.setVideoURI(videoUri);
            this.mVideoView = videoView;
            TrimControllerOverlay controllerView = new TrimControllerOverlay(activity, videoView.getHeight());
            controllerView.setListener(this);
            rootView.addView(controllerView);
            this.mControllerView = controllerView;
            playVideo();
                }

                
        class RunnableC05751 implements Runnable {
            RunnableC05751() {
                    }

                    @Override 
            public void run() {
                int pos = TrimVideo.access$000(TrimVideo.this);
                TrimVideo.access$200(TrimVideo.this).postDelayed(TrimVideo.access$100(TrimVideo.this), 200 - (pos % 200));
                    }
                }

        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putInt(KEY_TRIM_START, this.trimStartTime);
            savedInstanceState.putInt(KEY_TRIM_END, this.trimEndTime);
            savedInstanceState.putInt(KEY_VIDEO_POSITION, this.mVideoPositionMs);
                }

        public void onRestoreInstanceState(Bundle savedInstanceState) {
            this.trimStartTime = savedInstanceState.getInt(KEY_TRIM_START, 0);
            this.trimEndTime = savedInstanceState.getInt(KEY_TRIM_END, 0);
            this.mVideoPositionMs = savedInstanceState.getInt(KEY_VIDEO_POSITION, 0);
                }

        private int setTimeBar() {
            SdkVideoView videoView = this.mVideoView;
            int duration = videoView.getDuration();
            int videoPositionMs = videoView.getCurrentPosition();
            if (duration > 0 && this.trimEndTime == 0) {
                this.trimEndTime = duration;
                    }
            if (this.mVideoPositionMs < this.trimStartTime) {
                videoView.seekTo(this.trimStartTime);
                videoPositionMs = this.trimStartTime;
                    }
            if (videoPositionMs >= this.trimEndTime && this.trimEndTime > 0) {
                if (videoPositionMs > this.trimEndTime) {
                    videoPositionMs = this.trimEndTime;
                    videoView.seekTo(videoPositionMs);
                        }
                this.mControllerView.showEnded();
                videoView.pause();
                    }
            this.mControllerView.setTimes(videoPositionMs, duration, this.trimStartTime, this.trimEndTime);
            this.mVideoPositionMs = videoPositionMs;
            return this.mVideoPositionMs;
                }

        private void playVideo() {
            this.mVideoView.start();
            this.mControllerView.showPlaying();
            setTimeBar();
                }

       private void pauseVideo() {
           this.mVideoView.pause();
           this.mControllerView.showPaused();
                }

       public void onResume() {
           if (this.mHasPaused) {
               this.mVideoView.seekTo(this.mVideoPositionMs);
               this.mVideoView.resume();
               this.mHasPaused = false;
                    }
           this.mHandler.post(this.mProgressChecker);
                }

       public void onPause() {
           this.mHasPaused = true;
           this.mHandler.removeCallbacksAndMessages(null);
           this.mVideoPositionMs = this.mVideoView.getCurrentPosition();
           this.mVideoView.suspend();
                }

       public void onStop() {
           if (this.mProgress != null) {
               this.mProgress.dismiss();
               this.mProgress = null;
                    }
                }

       public void onDestroy() {
           this.mVideoView.stopPlayback();
                }

                @Override 
       public void onPlayPause() {
           if (this.mVideoView.isPlaying()) {
               pauseVideo();
                    } else {
               playVideo();
                    }
                }

                @Override 
       public void onSeekStart() {
           pauseVideo();
                }

                @Override 
       public void onSeekMove(int time) {
           this.mVideoView.seekTo((time / 100) * 100);
                }

                @Override 
       public void onSeekEnd(int time, int start, int end) {
           this.mVideoView.seekTo(time);
           this.trimStartTime = start;
           this.trimEndTime = end;
           setTimeBar();
                }

                @Override 
       public void onReplay() {
           this.mVideoView.seekTo(this.trimStartTime);
           playVideo();
                }

                @Override 
       public void onCompletion(VideoViewInterface view) {
           this.mControllerView.showEnded();
                }

                @Override 
       public boolean onError(VideoViewInterface view, int what, int extra) {
           return false;
                }
            }
