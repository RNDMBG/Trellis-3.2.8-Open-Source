            package co.vine.android.views;
            
            
    public class ListState {
                public int firstVisibleItem;
                public boolean hasNewScrollState;
                private boolean mDirty;
                private int mScrollState;
                public int totalItemCount;
                public int visibleItemCount;

        public void setScrollState(int scrollState) {
            this.mScrollState = scrollState;
                }

        public boolean isFling() {
                    return this.mScrollState == 2;
                }

        public void setDirty(boolean dirty) {
            this.mDirty = dirty;
                }

        public boolean isDirty() {
            return this.mDirty;
                }

        public boolean shouldUpdate() {
                    return this.mScrollState != 2 && this.mDirty;
                }
            }
