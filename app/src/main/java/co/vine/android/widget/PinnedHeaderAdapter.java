            package co.vine.android.widget;

            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public interface PinnedHeaderAdapter {
                int getPinnedHeaderHeight();

                PinnedHeaderStatus getPinnedHeaderStatus(int i);

                View getPinnedHeaderView(View view);

                void layoutPinnedHeader(View view, int i, int i2, int i3, int i4);

/* 16 */        public static class PinnedHeaderStatus {
                    public boolean pinnedHeaderIsVisible;
                    public int pinnedHeaderOffset;
                    public boolean shouldRequestNewView;

/* 17 */            public PinnedHeaderStatus(int offset, boolean newView, boolean isVisible) {
/* 19 */                this.pinnedHeaderOffset = offset;
/* 20 */                this.shouldRequestNewView = newView;
/* 21 */                this.pinnedHeaderIsVisible = isVisible;
                    }
                }
            }
