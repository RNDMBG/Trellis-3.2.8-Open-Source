            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.AdapterView;
            import android.widget.ListView;
            
            
    public final class ExpandedMenuView extends ListView implements MenuBuilder.ItemInvoker, MenuView, AdapterView.OnItemClickListener {
                private int mAnimations;
                private MenuBuilder mMenu;

        public ExpandedMenuView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setOnItemClickListener(this);
                }

                @Override 
        public void initialize(MenuBuilder menu) {
            this.mMenu = menu;
                }

                @Override 
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            setChildrenDrawingCacheEnabled(false);
                }

                @Override 
        public boolean invokeItem(MenuItemImpl item) {
            return this.mMenu.performItemAction(item, 0);
                }

                @Override 
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            invokeItem((MenuItemImpl) getAdapter().getItem(position));
                }

                @Override 
        public int getWindowAnimations() {
            return this.mAnimations;
                }
            }
