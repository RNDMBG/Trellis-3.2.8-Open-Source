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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class DraftFragment extends BaseFragment implements VideoViewInterface.OnPreparedListener, VideoViewInterface.OnErrorListener, SdkVideoView.SurfaceReadyListener {
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

/* 26 */        static /* synthetic */ SdkVideoView access$000(DraftFragment x0) {
/* 27 */            return x0.mVideoView;
                }

                /* JADX INFO: renamed from: co.vine.android.DraftFragment$1 */
/* 46 */        class RunnableC02031 implements Runnable {
/* 47 */            RunnableC02031() {
                    }

                    @Override // java.lang.Runnable
/* 50 */            public void run() {
/* 51 */                if (DraftFragment.access$000(DraftFragment.this).getVisibility() != 0 && DraftFragment.this.mCanUnhide) {
/* 52 */                    DraftFragment.access$000(DraftFragment.this).setVisibility(0);
                        }
                    }
                }

                @Override // co.vine.android.player.SdkVideoView.SurfaceReadyListener
/* 59 */        public void onSurfaceTextureAvailable(SdkVideoView videoView) {
/* 60 */            videoView.setVisibility(4);
/* 61 */            this.mVideoView.startOpenVideo();
                }

/* 70 */        public static class DraftPageViewHolder {
                    public ViewGroup containerView;
                    public RelativeLayout draftContainer;
                    public ProgressView progressView;
                    public ImageView thumb;
                    public String videoUrl;
                    public SdkVideoView videoView;

/* 71 */            public DraftPageViewHolder(View v, String videoUrl) {
/* 73 */                this.videoView = (SdkVideoView) v.findViewById(C0257R.id.videoView);
/* 74 */                this.videoUrl = videoUrl;
/* 75 */                this.containerView = (ViewGroup) v.findViewById(C0257R.id.videoViewContainer);
/* 76 */                this.progressView = (ProgressView) v.findViewById(C0257R.id.progress);
/* 77 */                this.thumb = (ImageView) v.findViewById(C0257R.id.thumb);
/* 78 */                this.draftContainer = (RelativeLayout) v.findViewById(C0257R.id.draft_container);
                    }
                }

/* 82 */        public DraftFragment() {
/* 46 */            this.mCanUnhide = true;
/* 48 */            this.mStartPlayingRunnable = new RunnableC02031();
                }

/* 98 */        public DraftFragment(boolean selected, int index, String videoUrl, String thumbUrl, File folder, int progress, int dimension) {
/* 46 */            this.mCanUnhide = true;
/* 48 */            this.mStartPlayingRunnable = new RunnableC02031();
/* 100 */           this.mIndex = index;
/* 101 */           this.mSelected = selected;
/* 102 */           this.mVideoUrl = videoUrl;
/* 103 */           this.mFolder = folder;
/* 104 */           this.mProgress = progress;
/* 105 */           this.mThumbUrl = thumbUrl;
/* 106 */           this.mDimen = dimension;
/* 107 */           this.mExpired = false;
                }

/* 110 */       public void setListener(View.OnClickListener listener) {
/* 111 */           this.mListener = listener;
                }

                @Override // android.support.v4.app.Fragment
/* 115 */       public void onPause() {
/* 116 */           super.onPause();
/* 117 */           if (this.mVideoView != null) {
/* 118 */               this.mVideoView.pause();
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 123 */       public void onDestroyView() {
/* 124 */           super.onDestroyView();
/* 125 */           if (this.mBitmap != null) {
/* 126 */               this.mBitmap.recycle();
                    }
/* 128 */           if (this.mVideoView != null) {
/* 129 */               this.mVideoView.suspend();
                    }
                }

/* 133 */       public void showImage() {
/* 134 */           this.mHolder.thumb.setVisibility(0);
/* 135 */           this.mHolder.videoView.setVisibility(8);
                }

/* 138 */       public View getProgressView() {
/* 139 */           return this.mHolder.progressView;
                }

/* 142 */       public View getThumbnailView() {
/* 143 */           return this.mHolder.thumb;
                }

                @Override // android.support.v4.app.Fragment
/* 148 */       public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
/* 149 */           Resources res = getResources();
/* 150 */           View v = inflater.inflate(C0257R.layout.draft_page, root, false);
/* 151 */           DraftPageViewHolder holder = new DraftPageViewHolder(v, this.mVideoUrl);
/* 152 */           int progressHeight = res.getDimensionPixelOffset(C0257R.dimen.progress_view_height);
/* 153 */           int draftProgressHeight = res.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
/* 155 */           RelativeLayout.LayoutParams containerParams = (RelativeLayout.LayoutParams) holder.draftContainer.getLayoutParams();
/* 157 */           containerParams.topMargin = progressHeight + draftProgressHeight;
/* 158 */           holder.draftContainer.setLayoutParams(containerParams);
/* 160 */           RelativeLayout videoContainer = (RelativeLayout) v.findViewById(C0257R.id.videoViewContainer);
/* 162 */           videoContainer.setDrawingCacheEnabled(true);
/* 164 */           ViewGroup.LayoutParams params = videoContainer.getLayoutParams();
/* 165 */           params.height = this.mDimen;
/* 166 */           params.width = this.mDimen;
/* 167 */           videoContainer.setLayoutParams(params);
/* 168 */           videoContainer.setTag(this.mFolder);
/* 170 */           videoContainer.setOnClickListener(this.mListener);
/* 173 */           ViewGroup.LayoutParams progressLayoutParams = holder.progressView.getLayoutParams();
/* 174 */           progressLayoutParams.width = this.mDimen;
/* 175 */           holder.progressView.setLayoutParams(progressLayoutParams);
/* 177 */           holder.progressView.setProgressRatio(this.mProgress / 6000.0f);
/* 179 */           View mask = v.findViewById(C0257R.id.draftPageMask);
/* 181 */           ViewGroup.LayoutParams params2 = mask.getLayoutParams();
/* 182 */           params2.width = this.mDimen;
/* 183 */           params2.height = ((AbstractRecordingActivity) getActivity()).getScreenSize().y;
/* 185 */           mask.setLayoutParams(params2);
/* 187 */           this.mBitmap = BitmapFactory.decodeFile(this.mThumbUrl);
/* 189 */           ViewGroup.LayoutParams thumbLayoutParams = holder.thumb.getLayoutParams();
/* 190 */           thumbLayoutParams.height = this.mDimen;
/* 191 */           thumbLayoutParams.width = this.mDimen;
/* 192 */           holder.thumb.setLayoutParams(thumbLayoutParams);
/* 193 */           holder.thumb.setImageDrawable(new RecyclableBitmapDrawable(getActivity().getResources(), this.mBitmap));
/* 194 */           holder.thumb.setVisibility(0);
/* 196 */           this.mHolder = holder;
/* 197 */           this.mVideoView = holder.videoView;
/* 198 */           this.mVideoView.setUseVinePlayer(false);
/* 200 */           ((AbstractRecordingActivity) getActivity()).addPlayerToPool(this.mVideoView);
/* 201 */           this.mVideoView.setOnPreparedListener(this);
/* 202 */           this.mVideoView.setKeepScreenOn(true);
/* 203 */           this.mVideoView.setLooping(true);
/* 204 */           this.mVideoView.setOnErrorListener(this);
/* 205 */           this.mVideoView.setSurfaceReadyListener(this);
/* 207 */           this.mFadeIn = AnimationUtils.loadAnimation(getActivity(), C0257R.anim.fade_in);
/* 208 */           this.mQuickFadeOut = AnimationUtils.loadAnimation(getActivity(), C0257R.anim.quick_fade_out);
/* 210 */           v.setDrawingCacheEnabled(true);
/* 211 */           v.setDrawingCacheQuality(1048576);
/* 213 */           return v;
                }

/* 216 */       public void setSelected(boolean selected) {
/* 217 */           this.mSelected = selected;
/* 218 */           if (selected) {
/* 219 */               onSelected();
                    } else {
/* 221 */               onUnselected();
                    }
                }

/* 225 */       public void release() {
/* 226 */           this.mVideoView.suspend();
                }

/* 229 */       private void onUnselected() {
/* 230 */           this.mVideoView.pause();
                }

/* 233 */       private void onSelected() {
/* 234 */           resumePlayer();
                }

/* 237 */       public void resumePlayer() {
/* 238 */           if (this.mVideoView.isInPlaybackState() && this.mVideoView.isPathPlaying(this.mVideoUrl)) {
/* 239 */               this.mVideoView.start();
                    } else if (this.mVideoView.isSurfaceReady()) {
/* 242 */               this.mVideoView.setVisibility(4);
/* 243 */               this.mVideoView.setVideoPath(this.mVideoUrl);
                    } else {
/* 245 */               this.mVideoView.setVideoPath(this.mVideoUrl);
/* 246 */               this.mVideoView.setVisibility(0);
                    }
                }

                @Override // co.vine.android.player.VideoViewInterface.OnPreparedListener
/* 252 */       public void onPrepared(VideoViewInterface view) {
/* 253 */           if (this.mSelected && !view.isPlaying()) {
/* 254 */               view.start();
/* 255 */               this.mVideoView.postDelayed(this.mStartPlayingRunnable, 100L);
                    }
                }

/* 259 */       public void setExpired(boolean expired) {
/* 260 */           this.mExpired = expired;
                }

/* 263 */       public boolean isExpired() {
/* 264 */           return this.mExpired;
                }

                @Override // co.vine.android.player.VideoViewInterface.OnErrorListener
/* 268 */       public boolean onError(VideoViewInterface view, int what, int extra) {
/* 269 */           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
/* 270 */           if (activity != null) {
/* 271 */               activity.releaseOtherPlayers(this.mVideoView);
/* 272 */               this.mVideoView.retryOpenVideo(false);
/* 274 */               return true;
                    }
/* 274 */           return true;
                }

/* 277 */       public void pausePlayer() {
/* 278 */           if (this.mSelected && this.mVideoView.canPause()) {
/* 279 */               this.mVideoView.pause();
                    }
                }
            }
