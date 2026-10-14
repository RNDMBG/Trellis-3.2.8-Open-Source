            package co.vine.android.widget;

            import android.view.View;
            import co.vine.android.C0257R;
            import co.vine.android.player.OldSdkVideoView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class SingleExploreFeedViewHolder extends GenericFeedViewHolder {
                public final OldSdkVideoView videoView;

/* 15 */        public SingleExploreFeedViewHolder(View view) {
/* 16 */            super(view);
/* 17 */            this.videoView = (OldSdkVideoView) view.findViewById(C0257R.id.videoView);
                }

                @Override // co.vine.android.widget.GenericFeedViewHolder
/* 21 */        public View getViewForVideoImage() {
/* 22 */            return this.videoView;
                }
            }
