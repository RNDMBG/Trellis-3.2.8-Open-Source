            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public abstract class BaseMenuPresenter implements MenuPresenter {
                private MenuPresenter.Callback mCallback;
                protected Context mContext;
                private int mId;
                protected LayoutInflater mInflater;
                private int mItemLayoutRes;
                protected MenuBuilder mMenu;
                private int mMenuLayoutRes;
                protected MenuView mMenuView;
                protected Context mSystemContext;
                protected LayoutInflater mSystemInflater;

                public abstract void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView);

/* 55 */        public BaseMenuPresenter(Context context, int menuLayoutRes, int itemLayoutRes) {
/* 57 */            this.mSystemContext = context;
/* 58 */            this.mSystemInflater = LayoutInflater.from(context);
/* 59 */            this.mMenuLayoutRes = menuLayoutRes;
/* 60 */            this.mItemLayoutRes = itemLayoutRes;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 64 */        public void initForMenu(Context context, MenuBuilder menu) {
/* 65 */            this.mContext = context;
/* 66 */            this.mInflater = LayoutInflater.from(this.mContext);
/* 67 */            this.mMenu = menu;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 70 */        public MenuView getMenuView(ViewGroup root) {
/* 71 */            if (this.mMenuView == null) {
/* 72 */                this.mMenuView = (MenuView) this.mSystemInflater.inflate(this.mMenuLayoutRes, root, false);
/* 73 */                this.mMenuView.initialize(this.mMenu);
/* 74 */                updateMenuView(true);
                    }
/* 77 */            return this.mMenuView;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 83 */        public void updateMenuView(boolean cleared) {
/* 84 */            ViewGroup parent = (ViewGroup) this.mMenuView;
/* 85 */            if (parent != null) {
/* 89 */                int childIndex = 0;
/* 90 */                if (this.mMenu != null) {
/* 91 */                    this.mMenu.flagActionItems();
/* 92 */                    ArrayList<MenuItemImpl> visibleItems = this.mMenu.getVisibleItems();
/* 93 */                    int itemCount = visibleItems.size();
                            for (int i = 0; i < itemCount; i++) {
/* 95 */                        MenuItemImpl item = visibleItems.get(i);
/* 96 */                        if (shouldIncludeItem(childIndex, item)) {
/* 97 */                            View childAt = parent.getChildAt(childIndex);
/* 98 */                            MenuItemImpl oldItem = childAt instanceof MenuView.ItemView ? ((MenuView.ItemView) childAt).getItemData() : null;
/* 100 */                           View itemView = getItemView(item, childAt, parent);
/* 101 */                           if (item != oldItem) {
/* 103 */                               itemView.setPressed(false);
                                    }
/* 107 */                           if (itemView != childAt) {
/* 108 */                               addItemView(itemView, childIndex);
                                    }
/* 110 */                           childIndex++;
                                }
                            }
                        }
/* 116 */               while (childIndex < parent.getChildCount()) {
/* 117 */                   if (!filterLeftoverView(parent, childIndex)) {
/* 118 */                       childIndex++;
                            }
                        }
                    }
                }

/* 129 */       protected void addItemView(View itemView, int childIndex) {
/* 130 */           ViewGroup currentParent = (ViewGroup) itemView.getParent();
/* 131 */           if (currentParent != null) {
/* 132 */               currentParent.removeView(itemView);
                    }
/* 134 */           ((ViewGroup) this.mMenuView).addView(itemView, childIndex);
                }

/* 144 */       protected boolean filterLeftoverView(ViewGroup parent, int childIndex) {
/* 145 */           parent.removeViewAt(childIndex);
/* 146 */           return true;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 149 */       public void setCallback(MenuPresenter.Callback cb) {
/* 150 */           this.mCallback = cb;
                }

/* 158 */       public MenuView.ItemView createItemView(ViewGroup parent) {
/* 159 */           return (MenuView.ItemView) this.mSystemInflater.inflate(this.mItemLayoutRes, parent, false);
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 174 */       public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
                    MenuView.ItemView itemViewCreateItemView;
/* 175 */           if (view instanceof MenuView.ItemView) {
/* 176 */               itemViewCreateItemView = (MenuView.ItemView) view;
                    } else {
/* 178 */               itemViewCreateItemView = createItemView(viewGroup);
                    }
/* 180 */           bindItemView(menuItemImpl, itemViewCreateItemView);
/* 181 */           return (View) itemViewCreateItemView;
                }

/* 199 */       public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
/* 200 */           return true;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 203 */       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 204 */           if (this.mCallback != null) {
/* 205 */               this.mCallback.onCloseMenu(menu, allMenusAreClosing);
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 209 */       public boolean onSubMenuSelected(SubMenuBuilder menu) {
/* 210 */           if (this.mCallback != null) {
/* 211 */               return this.mCallback.onOpenSubMenu(menu);
                    }
                    return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 216 */       public boolean flagActionItems() {
/* 217 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 220 */       public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 221 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 224 */       public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 225 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 228 */       public int getId() {
/* 229 */           return this.mId;
                }

/* 232 */       public void setId(int id) {
/* 233 */           this.mId = id;
                }
            }
