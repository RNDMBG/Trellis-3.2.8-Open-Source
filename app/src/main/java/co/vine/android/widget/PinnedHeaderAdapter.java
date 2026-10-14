            package co.vine.android.widget;

            import android.view.View;
            
            
    public interface PinnedHeaderAdapter {
                int getPinnedHeaderHeight();

                PinnedHeaderStatus getPinnedHeaderStatus(int i);

                View getPinnedHeaderView(View view);

                void layoutPinnedHeader(View view, int i, int i2, int i3, int i4);

        public static class PinnedHeaderStatus {
                    public boolean pinnedHeaderIsVisible;
                    public int pinnedHeaderOffset;
                    public boolean shouldRequestNewView;

            public PinnedHeaderStatus(int offset, boolean newView, boolean isVisible) {
                this.pinnedHeaderOffset = offset;
                this.shouldRequestNewView = newView;
                this.pinnedHeaderIsVisible = isVisible;
                    }
                }
            }
