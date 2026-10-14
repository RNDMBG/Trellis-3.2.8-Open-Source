            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.os.Bundle;
            import android.os.Parcelable;
            import android.support.v7.appcompat.C0084R;
            import android.util.SparseArray;
            import android.view.ContextThemeWrapper;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.BaseAdapter;
            import android.widget.ListAdapter;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class ListMenuPresenter implements MenuPresenter, AdapterView.OnItemClickListener {
                private static final String TAG = "ListMenuPresenter";
                public static final String VIEWS_TAG = "android:menu:list";
                MenuAdapter mAdapter;
                private MenuPresenter.Callback mCallback;
                Context mContext;
                private int mId;
                LayoutInflater mInflater;
                private int mItemIndexOffset;
                int mItemLayoutRes;
                MenuBuilder mMenu;
                ExpandedMenuView mMenuView;
                int mThemeRes;

/* 39 */        static /* synthetic */ int access$000(ListMenuPresenter x0) {
/* 40 */            return x0.mItemIndexOffset;
                }

/* 66 */        public ListMenuPresenter(Context context, int itemLayoutRes) {
/* 67 */            this(itemLayoutRes, 0);
/* 68 */            this.mContext = context;
/* 69 */            this.mInflater = LayoutInflater.from(this.mContext);
                }

/* 76 */        public ListMenuPresenter(int itemLayoutRes, int themeRes) {
/* 78 */            this.mItemLayoutRes = itemLayoutRes;
/* 79 */            this.mThemeRes = themeRes;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 83 */        public void initForMenu(Context context, MenuBuilder menu) {
/* 84 */            if (this.mThemeRes != 0) {
/* 85 */                this.mContext = new ContextThemeWrapper(context, this.mThemeRes);
/* 86 */                this.mInflater = LayoutInflater.from(this.mContext);
                    } else if (this.mContext != null) {
/* 88 */                this.mContext = context;
/* 89 */                if (this.mInflater == null) {
/* 90 */                    this.mInflater = LayoutInflater.from(this.mContext);
                        }
                    }
/* 93 */            this.mMenu = menu;
/* 94 */            if (this.mAdapter != null) {
/* 95 */                this.mAdapter.notifyDataSetChanged();
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 100 */       public MenuView getMenuView(ViewGroup root) {
/* 101 */           if (this.mAdapter == null) {
/* 102 */               this.mAdapter = new MenuAdapter();
                    }
/* 105 */           if (this.mAdapter.isEmpty()) {
                        return null;
                    }
/* 106 */           if (this.mMenuView == null) {
/* 107 */               this.mMenuView = (ExpandedMenuView) this.mInflater.inflate(C0084R.layout.abc_expanded_menu_layout, root, false);
/* 109 */               this.mMenuView.setAdapter((ListAdapter) this.mAdapter);
/* 110 */               this.mMenuView.setOnItemClickListener(this);
                    }
/* 112 */           return this.mMenuView;
                }

/* 126 */       public ListAdapter getAdapter() {
/* 127 */           if (this.mAdapter == null) {
/* 128 */               this.mAdapter = new MenuAdapter();
                    }
/* 130 */           return this.mAdapter;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 134 */       public void updateMenuView(boolean cleared) {
/* 135 */           if (this.mAdapter != null) {
                        this.mAdapter.notifyDataSetChanged();
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 139 */       public void setCallback(MenuPresenter.Callback cb) {
/* 140 */           this.mCallback = cb;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 144 */       public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
/* 145 */           if (!subMenu.hasVisibleItems()) {
                        return false;
                    }
/* 148 */           new MenuDialogHelper(subMenu).show(null);
/* 149 */           if (this.mCallback != null) {
/* 150 */               this.mCallback.onOpenSubMenu(subMenu);
                    }
/* 152 */           return true;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 156 */       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 157 */           if (this.mCallback != null) {
/* 158 */               this.mCallback.onCloseMenu(menu, allMenusAreClosing);
                    }
                }

/* 162 */       int getItemIndexOffset() {
/* 163 */           return this.mItemIndexOffset;
                }

/* 166 */       public void setItemIndexOffset(int offset) {
/* 167 */           this.mItemIndexOffset = offset;
/* 168 */           if (this.mMenuView != null) {
/* 169 */               updateMenuView(false);
                    }
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 174 */       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 175 */           this.mMenu.performItemAction(this.mAdapter.getItem(position), 0);
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 179 */       public boolean flagActionItems() {
/* 180 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 183 */       public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 184 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 187 */       public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 188 */           return false;
                }

/* 191 */       public void saveHierarchyState(Bundle outState) {
/* 192 */           SparseArray<Parcelable> viewStates = new SparseArray<>();
/* 193 */           if (this.mMenuView != null) {
/* 194 */               this.mMenuView.saveHierarchyState(viewStates);
                    }
/* 196 */           outState.putSparseParcelableArray(VIEWS_TAG, viewStates);
                }

/* 199 */       public void restoreHierarchyState(Bundle inState) {
/* 200 */           SparseArray<Parcelable> viewStates = inState.getSparseParcelableArray(VIEWS_TAG);
/* 201 */           if (viewStates != null) {
/* 202 */               this.mMenuView.restoreHierarchyState(viewStates);
                    }
                }

/* 206 */       public void setId(int id) {
/* 207 */           this.mId = id;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 211 */       public int getId() {
/* 212 */           return this.mId;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 216 */       public Parcelable onSaveInstanceState() {
/* 217 */           if (this.mMenuView == null) {
/* 218 */               return null;
                    }
/* 221 */           Bundle state = new Bundle();
/* 222 */           saveHierarchyState(state);
/* 223 */           return state;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 227 */       public void onRestoreInstanceState(Parcelable state) {
/* 228 */           restoreHierarchyState((Bundle) state);
                }

/* 229 */       private class MenuAdapter extends BaseAdapter {
                    private int mExpandedIndex = -1;

                    @Override // android.widget.Adapter
/* 230 */           public /* bridge */ /* synthetic */ Object getItem(int x0) {
/* 231 */               return getItem(x0);
                    }

/* 233 */           public MenuAdapter() {
/* 235 */               findExpandedIndex();
                    }

                    @Override // android.widget.Adapter
/* 238 */           public int getCount() {
/* 239 */               ArrayList<MenuItemImpl> items = ListMenuPresenter.this.mMenu.getNonActionItems();
/* 240 */               int count = items.size() - ListMenuPresenter.access$000(ListMenuPresenter.this);
                        return this.mExpandedIndex < 0 ? count : count - 1;
                    }

                    @Override // android.widget.Adapter
/* 247 */           public MenuItemImpl getItem(int position) {
/* 248 */               ArrayList<MenuItemImpl> items = ListMenuPresenter.this.mMenu.getNonActionItems();
/* 249 */               int position2 = position + ListMenuPresenter.access$000(ListMenuPresenter.this);
/* 250 */               if (this.mExpandedIndex >= 0 && position2 >= this.mExpandedIndex) {
/* 251 */                   position2++;
                        }
/* 253 */               return items.get(position2);
                    }

                    @Override // android.widget.Adapter
/* 258 */           public long getItemId(int position) {
/* 259 */               return position;
                    }

                    @Override // android.widget.Adapter
/* 262 */           public View getView(int position, View convertView, ViewGroup parent) {
/* 263 */               if (convertView == null) {
/* 264 */                   convertView = ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, parent, false);
                        }
/* 267 */               MenuView.ItemView itemView = (MenuView.ItemView) convertView;
/* 268 */               itemView.initialize(getItem(position), 0);
/* 269 */               return convertView;
                    }

/* 272 */           void findExpandedIndex() {
/* 273 */               MenuItemImpl expandedItem = ListMenuPresenter.this.mMenu.getExpandedItem();
/* 274 */               if (expandedItem != null) {
/* 275 */                   ArrayList<MenuItemImpl> items = ListMenuPresenter.this.mMenu.getNonActionItems();
/* 276 */                   int count = items.size();
                            for (int i = 0; i < count; i++) {
/* 278 */                       MenuItemImpl item = items.get(i);
/* 279 */                       if (item == expandedItem) {
/* 280 */                           this.mExpandedIndex = i;
/* 286 */                           return;
                                }
                            }
                        }
/* 285 */               this.mExpandedIndex = -1;
                    }

                    @Override // android.widget.BaseAdapter
/* 289 */           public void notifyDataSetChanged() {
/* 290 */               findExpandedIndex();
/* 291 */               super.notifyDataSetChanged();
                    }
                }
            }
