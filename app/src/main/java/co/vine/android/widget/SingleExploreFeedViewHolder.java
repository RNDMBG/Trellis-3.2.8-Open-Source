            package co.vine.android.widget;

            import android.view.View;
            import co.vine.android.R;
            import co.vine.android.player.OldSdkVideoView;
            
            
    public class SingleExploreFeedViewHolder extends GenericFeedViewHolder {
                public final OldSdkVideoView videoView;

        public SingleExploreFeedViewHolder(View view) {
            super(view);
            this.videoView = (OldSdkVideoView) view.findViewById(R.id.videoView);
                }

                @Override 
        public View getViewForVideoImage() {
            return this.videoView;
                }
            }
