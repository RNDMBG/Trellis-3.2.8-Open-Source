            package co.vine.android.widget;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AbsListView;
            import co.vine.android.C0257R;
            import co.vine.android.views.SdkListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class ConversationList extends SdkListView implements AbsListView.OnScrollListener {
                private View mGetMoreHeaderProgressContainer;
                private GetMoreListener mGetMoreListener;
                private View mProgressView;
                private AbsListView.OnScrollListener mScrollListener;

                public interface GetMoreListener {
                    void getMore();
                }

/* 18 */        public ConversationList(Context context) {
/* 19 */            super(context);
                }

/* 22 */        public ConversationList(Context context, AttributeSet attrs) {
/* 23 */            super(context, attrs);
                }

/* 26 */        public ConversationList(Context context, AttributeSet attrs, int defStyle) {
/* 27 */            super(context, attrs, defStyle);
                }

                @Override // android.widget.ListView, android.view.View
/* 31 */        protected void onFinishInflate() {
/* 32 */            super.onFinishInflate();
/* 33 */            super.setOnScrollListener(this);
/* 35 */            LayoutInflater inflater = LayoutInflater.from(getContext());
/* 36 */            this.mGetMoreHeaderProgressContainer = inflater.inflate(C0257R.layout.conversation_header, (ViewGroup) null);
/* 37 */            this.mProgressView = this.mGetMoreHeaderProgressContainer.findViewById(C0257R.id.header_content);
/* 38 */            addHeaderView(this.mGetMoreHeaderProgressContainer, null, false);
                }

                @Override // android.widget.AbsListView
/* 42 */        public void setOnScrollListener(AbsListView.OnScrollListener l) {
/* 43 */            this.mScrollListener = l;
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 47 */        public void onScrollStateChanged(AbsListView view, int scrollState) {
/* 48 */            if (this.mScrollListener != null) {
/* 49 */                this.mScrollListener.onScrollStateChanged(view, scrollState);
                    }
                }

                @Override // android.widget.AbsListView.OnScrollListener
/* 54 */        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
/* 55 */            if (visibleItemCount != 0) {
/* 59 */                if (this.mGetMoreListener != null && firstVisibleItem == 0 && this.mProgressView.getVisibility() != 8) {
/* 60 */                    View child = getChildAt(0);
/* 61 */                    if (child == this.mGetMoreHeaderProgressContainer) {
/* 62 */                        this.mGetMoreListener.getMore();
                            }
                        }
/* 65 */                if (this.mScrollListener != null) {
/* 66 */                    this.mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                        }
                    }
                }

/* 70 */        public void setGetMoreListener(GetMoreListener listener) {
/* 71 */            this.mGetMoreListener = listener;
                }

/* 74 */        public void deactivateRefresh(boolean permanently) {
/* 75 */            this.mProgressView.setVisibility(8);
/* 76 */            if (permanently) {
/* 77 */                removeHeaderView(this.mGetMoreHeaderProgressContainer);
                    }
                }

/* 81 */        public void deactivateRefresh() {
/* 82 */            deactivateRefresh(false);
                }

/* 85 */        public void reactivateRefresh() {
/* 86 */            this.mProgressView.setVisibility(4);
                }

/* 93 */        public void showProgress() {
/* 94 */            if (this.mProgressView.getVisibility() != 8) {
/* 95 */                this.mProgressView.setVisibility(0);
                    }
                }

/* 99 */        public void hideProgress() {
/* 100 */           this.mProgressView.setVisibility(4);
                }

                @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
/* 104 */       protected void onSizeChanged(int w, int h, int oldW, int oldH) {
/* 105 */           super.onSizeChanged(w, h, oldW, oldH);
/* 106 */           if (getCount() > 0) {
/* 107 */               setSelection(getCount() - 1);
                    }
                }
            }
