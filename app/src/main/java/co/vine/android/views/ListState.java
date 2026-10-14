            package co.vine.android.views;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ListState {
                public int firstVisibleItem;
                public boolean hasNewScrollState;
                private boolean mDirty;
                private int mScrollState;
                public int totalItemCount;
                public int visibleItemCount;

/* 14 */        public void setScrollState(int scrollState) {
/* 15 */            this.mScrollState = scrollState;
                }

/* 18 */        public boolean isFling() {
                    return this.mScrollState == 2;
                }

/* 22 */        public void setDirty(boolean dirty) {
/* 23 */            this.mDirty = dirty;
                }

/* 26 */        public boolean isDirty() {
/* 27 */            return this.mDirty;
                }

/* 30 */        public boolean shouldUpdate() {
                    return this.mScrollState != 2 && this.mDirty;
                }
            }
