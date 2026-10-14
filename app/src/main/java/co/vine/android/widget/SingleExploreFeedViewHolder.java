            package co.vine.android.widget;

            import android.view.View;
            import co.vine.android.C0257R;
            import co.vine.android.player.OldSdkVideoView;
            
            
    public class SingleExploreFeedViewHolder extends GenericFeedViewHolder {
                public final OldSdkVideoView videoView;

        public SingleExploreFeedViewHolder(View view) {
            super(view);
            this.videoView = (OldSdkVideoView) view.findViewById(C0257R.id.videoView);
                }

                @Override 
        public View getViewForVideoImage() {
            return this.videoView;
                }
            }
