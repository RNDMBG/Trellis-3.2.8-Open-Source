            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.AdapterView;
            import android.widget.ListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public final class ExpandedMenuView extends ListView implements MenuBuilder.ItemInvoker, MenuView, AdapterView.OnItemClickListener {
                private int mAnimations;
                private MenuBuilder mMenu;

/* 42 */        public ExpandedMenuView(Context context, AttributeSet attrs) {
/* 43 */            super(context, attrs);
/* 44 */            setOnItemClickListener(this);
                }

                @Override // android.support.v7.internal.view.menu.MenuView
/* 48 */        public void initialize(MenuBuilder menu) {
/* 49 */            this.mMenu = menu;
                }

                @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
/* 53 */        protected void onDetachedFromWindow() {
/* 54 */            super.onDetachedFromWindow();
/* 57 */            setChildrenDrawingCacheEnabled(false);
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder.ItemInvoker
/* 61 */        public boolean invokeItem(MenuItemImpl item) {
/* 62 */            return this.mMenu.performItemAction(item, 0);
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 67 */        public void onItemClick(AdapterView parent, View v, int position, long id) {
/* 68 */            invokeItem((MenuItemImpl) getAdapter().getItem(position));
                }

                @Override // android.support.v7.internal.view.menu.MenuView
/* 72 */        public int getWindowAnimations() {
/* 73 */            return this.mAnimations;
                }
            }
