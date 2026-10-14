            package co.vine.android;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.AnimationUtils;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.recorder.ProgressView;
            import java.io.File;
            
            
    public class DraftFragment extends BaseFragment implements VideoViewInterface.OnPreparedListener, VideoViewInterface.OnErrorListener, SdkVideoView.SurfaceReadyListener {
                private static final int VISIBILITY_DELAY = 100;
                private Bitmap mBitmap;
                public boolean mCanUnhide;
                private int mDimen;
                private boolean mExpired;
                private Animation mFadeIn;
                private File mFolder;
                private DraftPageViewHolder mHolder;
                private int mIndex;
                private View.OnClickListener mListener;
                private int mProgress;
                private Animation mQuickFadeOut;
                private boolean mSelected;
                private final Runnable mStartPlayingRunnable;
                private String mThumbUrl;
                private String mVideoUrl;
                private SdkVideoView mVideoView;

        static  SdkVideoView access$000(DraftFragment x0) {
            return x0.mVideoView;
                }

                
        class RunnableC02031 implements Runnable {
            RunnableC02031() {
                    }

                    @Override 
            public void run() {
                if (DraftFragment.access$000(DraftFragment.this).getVisibility() != 0 && DraftFragment.this.mCanUnhide) {
                    DraftFragment.access$000(DraftFragment.this).setVisibility(0);
                        }
                    }
                }

                @Override 
        public void onSurfaceTextureAvailable(SdkVideoView videoView) {
            videoView.setVisibility(4);
            this.mVideoView.startOpenVideo();
                }

        public static class DraftPageViewHolder {
                    public ViewGroup containerView;
                    public RelativeLayout draftContainer;
                    public ProgressView progressView;
                    public ImageView thumb;
                    public String videoUrl;
                    public SdkVideoView videoView;

            public DraftPageViewHolder(View v, String videoUrl) {
                this.videoView = (SdkVideoView) v.findViewById(C0257R.id.videoView);
                this.videoUrl = videoUrl;
                this.containerView = (ViewGroup) v.findViewById(C0257R.id.videoViewContainer);
                this.progressView = (ProgressView) v.findViewById(C0257R.id.progress);
                this.thumb = (ImageView) v.findViewById(C0257R.id.thumb);
                this.draftContainer = (RelativeLayout) v.findViewById(C0257R.id.draft_container);
                    }
                }

        public DraftFragment() {
            this.mCanUnhide = true;
            this.mStartPlayingRunnable = new RunnableC02031();
                }

        public DraftFragment(boolean selected, int index, String videoUrl, String thumbUrl, File folder, int progress, int dimension) {
            this.mCanUnhide = true;
            this.mStartPlayingRunnable = new RunnableC02031();
           this.mIndex = index;
           this.mSelected = selected;
           this.mVideoUrl = videoUrl;
           this.mFolder = folder;
           this.mProgress = progress;
           this.mThumbUrl = thumbUrl;
           this.mDimen = dimension;
           this.mExpired = false;
                }

       public void setListener(View.OnClickListener listener) {
           this.mListener = listener;
                }

                @Override 
       public void onPause() {
           super.onPause();
           if (this.mVideoView != null) {
               this.mVideoView.pause();
                    }
                }

                @Override 
       public void onDestroyView() {
           super.onDestroyView();
           if (this.mBitmap != null) {
               this.mBitmap.recycle();
                    }
           if (this.mVideoView != null) {
               this.mVideoView.suspend();
                    }
                }

       public void showImage() {
           this.mHolder.thumb.setVisibility(0);
           this.mHolder.videoView.setVisibility(8);
                }

       public View getProgressView() {
           return this.mHolder.progressView;
                }

       public View getThumbnailView() {
           return this.mHolder.thumb;
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
           Resources res = getResources();
           View v = inflater.inflate(C0257R.layout.draft_page, root, false);
           DraftPageViewHolder holder = new DraftPageViewHolder(v, this.mVideoUrl);
           int progressHeight = res.getDimensionPixelOffset(C0257R.dimen.progress_view_height);
           int draftProgressHeight = res.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
           RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) holder.draftContainer.getLayoutParams();
           containerParams.topMargin = progressHeight + draftProgressHeight;
           holder.draftContainer.setLayoutParams(containerParams);
           RelativeLayout videoContainer = (RelativeLayout) v.findViewById(C0257R.id.videoViewContainer);
           videoContainer.setDrawingCacheEnabled(true);
           ViewGroup.LayoutParams params = videoContainer.getLayoutParams();
           params.height = this.mDimen;
           params.width = this.mDimen;
           videoContainer.setLayoutParams(params);
           videoContainer.setTag(this.mFolder);
           videoContainer.setOnClickListener(this.mListener);
           ViewGroup.LayoutParams progressLayoutParams = holder.progressView.getLayoutParams();
           progressLayoutParams.width = this.mDimen;
           holder.progressView.setLayoutParams(progressLayoutParams);
           holder.progressView.setProgressRatio(this.mProgress / 6000.0f);
           View mask = v.findViewById(C0257R.id.draftPageMask);
           ViewGroup.LayoutParams params2 = mask.getLayoutParams();
           params2.width = this.mDimen;
           params2.height = ((AbstractRecordingActivity) getActivity()).getScreenSize().y;
           mask.setLayoutParams(params2);
           this.mBitmap = BitmapFactory.decodeFile(this.mThumbUrl);
           ViewGroup.LayoutParams thumbLayoutParams = holder.thumb.getLayoutParams();
           thumbLayoutParams.height = this.mDimen;
           thumbLayoutParams.width = this.mDimen;
           holder.thumb.setLayoutParams(thumbLayoutParams);
           holder.thumb.setImageDrawable(new RecyclableBitmapDrawable(getActivity().getResources(), this.mBitmap));
           holder.thumb.setVisibility(0);
           this.mHolder = holder;
           this.mVideoView = holder.videoView;
           this.mVideoView.setUseVinePlayer(false);
           ((AbstractRecordingActivity) getActivity()).addPlayerToPool(this.mVideoView);
           this.mVideoView.setOnPreparedListener(this);
           this.mVideoView.setKeepScreenOn(true);
           this.mVideoView.setLooping(true);
           this.mVideoView.setOnErrorListener(this);
           this.mVideoView.setSurfaceReadyListener(this);
           this.mFadeIn = AnimationUtils.loadAnimation(getActivity(), C0257R.anim.fade_in);
           this.mQuickFadeOut = AnimationUtils.loadAnimation(getActivity(), C0257R.anim.quick_fade_out);
           v.setDrawingCacheEnabled(true);
           v.setDrawingCacheQuality(1048576);
           return v;
                }

       public void setSelected(boolean selected) {
           this.mSelected = selected;
           if (selected) {
               onSelected();
                    } else {
               onUnselected();
                    }
                }

       public void release() {
           this.mVideoView.suspend();
                }

       private void onUnselected() {
           this.mVideoView.pause();
                }

       private void onSelected() {
           resumePlayer();
                }

       public void resumePlayer() {
           if (this.mVideoView.isInPlaybackState() && this.mVideoView.isPathPlaying(this.mVideoUrl)) {
               this.mVideoView.start();
                    } else if (this.mVideoView.isSurfaceReady()) {
               this.mVideoView.setVisibility(4);
               this.mVideoView.setVideoPath(this.mVideoUrl);
                    } else {
               this.mVideoView.setVideoPath(this.mVideoUrl);
               this.mVideoView.setVisibility(0);
                    }
                }

                @Override 
       public void onPrepared(VideoViewInterface view) {
           if (this.mSelected && !view.isPlaying()) {
               view.start();
               this.mVideoView.postDelayed(this.mStartPlayingRunnable, 100L);
                    }
                }

       public void setExpired(boolean expired) {
           this.mExpired = expired;
                }

       public boolean isExpired() {
           return this.mExpired;
                }

                @Override 
       public boolean onError(VideoViewInterface view, int what, int extra) {
           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
           if (activity != null) {
               activity.releaseOtherPlayers(this.mVideoView);
               this.mVideoView.retryOpenVideo(false);
               return true;
                    }
           return true;
                }

       public void pausePlayer() {
           if (this.mSelected && this.mVideoView.canPause()) {
               this.mVideoView.pause();
                    }
                }
            }
