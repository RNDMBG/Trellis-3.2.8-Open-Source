            package co.vine.android.widget;

            import android.view.View;
            import android.widget.ImageView;
            import co.vine.android.player.OnListVideoClickListener;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.views.MultiPhaseBitmapView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class FeedViewHolder extends GenericFeedViewHolder {
                public MultiPhaseBitmapView doubleTapView;
                public OnListVideoClickListener listener;
                public ImageView videoImage;
                public ImageView videoImageTop;
                public SdkVideoView videoView;

                @Override // co.vine.android.widget.GenericFeedViewHolder
/* 22 */        public View getViewForVideoImage() {
/* 23 */            return this.videoImage;
                }

/* 26 */        public FeedViewHolder(View view) {
/* 27 */            super(view);
                }
            }
