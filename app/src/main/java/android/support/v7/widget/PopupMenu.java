            package android.support.v7.widget;

            import android.content.Context;
            import android.support.v7.internal.view.SupportMenuInflater;
            import android.support.v7.internal.view.menu.MenuBuilder;
            import android.support.v7.internal.view.menu.MenuPopupHelper;
            import android.support.v7.internal.view.menu.MenuPresenter;
            import android.support.v7.internal.view.menu.SubMenuBuilder;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public class PopupMenu implements MenuBuilder.Callback, MenuPresenter.Callback {
                private View mAnchor;
                private Context mContext;
                private OnDismissListener mDismissListener;
                private MenuBuilder mMenu;
                private OnMenuItemClickListener mMenuItemClickListener;
                private MenuPopupHelper mPopup;

                public interface OnDismissListener {
                    void onDismiss(PopupMenu popupMenu);
                }

                public interface OnMenuItemClickListener {
                    boolean onMenuItemClick(MenuItem menuItem);
                }

/* 63 */        public PopupMenu(Context context, View anchor) {
/* 65 */            this.mContext = context;
/* 66 */            this.mMenu = new MenuBuilder(context);
/* 67 */            this.mMenu.setCallback(this);
/* 68 */            this.mAnchor = anchor;
/* 69 */            this.mPopup = new MenuPopupHelper(context, this.mMenu, anchor);
/* 70 */            this.mPopup.setCallback(this);
                }

/* 80 */        public Menu getMenu() {
/* 81 */            return this.mMenu;
                }

/* 90 */        public MenuInflater getMenuInflater() {
/* 91 */            return new SupportMenuInflater(this.mContext);
                }

/* 99 */        public void inflate(int menuRes) {
/* 100 */           getMenuInflater().inflate(menuRes, this.mMenu);
                }

/* 107 */       public void show() {
/* 108 */           this.mPopup.show();
                }

/* 115 */       public void dismiss() {
/* 116 */           this.mPopup.dismiss();
                }

/* 124 */       public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
/* 125 */           this.mMenuItemClickListener = listener;
                }

/* 133 */       public void setOnDismissListener(OnDismissListener listener) {
/* 134 */           this.mDismissListener = listener;
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder.Callback
/* 140 */       public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
/* 141 */           if (this.mMenuItemClickListener != null) {
/* 142 */               return this.mMenuItemClickListener.onMenuItemClick(item);
                    }
                    return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 150 */       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 151 */           if (this.mDismissListener != null) {
/* 152 */               this.mDismissListener.onDismiss(this);
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 159 */       public boolean onOpenSubMenu(MenuBuilder subMenu) {
/* 160 */           if (subMenu == null) {
                        return false;
                    }
/* 162 */           if (!subMenu.hasVisibleItems()) {
                        return true;
                    }
/* 167 */           new MenuPopupHelper(this.mContext, subMenu, this.mAnchor).show();
                    return true;
                }

/* 174 */       public void onCloseSubMenu(SubMenuBuilder menu) {
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder.Callback
/* 180 */       public void onMenuModeChange(MenuBuilder menu) {
                }
            }
