            package co.vine.android.widget;

            import android.view.View;
            import android.widget.ImageView;
            import co.vine.android.player.OnListVideoClickListener;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.views.MultiPhaseBitmapView;
            
            
    public class FeedViewHolder extends GenericFeedViewHolder {
                public MultiPhaseBitmapView doubleTapView;
                public OnListVideoClickListener listener;
                public ImageView videoImage;
                public ImageView videoImageTop;
                public SdkVideoView videoView;

                @Override 
        public View getViewForVideoImage() {
            return this.videoImage;
                }

        public FeedViewHolder(View view) {
            super(view);
                }
            }
