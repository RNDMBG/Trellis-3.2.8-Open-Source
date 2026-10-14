            package co.vine.android.player;

            import android.widget.AbsListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class OnVideoListScrollListener implements AbsListView.OnScrollListener {
                private final HasVideoPlayerAdapter adapter;

/* 12 */        public OnVideoListScrollListener(HasVideoPlayerAdapter adapter) {
/* 14 */            this.adapter = adapter;
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 18 */        public synchronized void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == 0) {
                        try {
/* 20 */                    this.adapter.playCurrentPosition();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 26 */        public synchronized void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                }
            }
