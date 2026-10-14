            package co.vine.android.player;

            import android.widget.AbsListView;
            
            
    public class OnVideoListScrollListener implements AbsListView.OnScrollListener {
                private final HasVideoPlayerAdapter adapter;

        public OnVideoListScrollListener(HasVideoPlayerAdapter adapter) {
            this.adapter = adapter;
                }

                @Override 
        public synchronized void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == 0) {
                        try {
                    this.adapter.playCurrentPosition();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
        public synchronized void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                }
            }
