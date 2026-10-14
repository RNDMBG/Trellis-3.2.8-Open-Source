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
            
            
    public class ListMenuPresenter implements MenuPresenter, AdapterView.OnItemClickListener {
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

        static  int access$000(ListMenuPresenter x0) {
            return x0.mItemIndexOffset;
                }

        public ListMenuPresenter(Context context, int itemLayoutRes) {
            this(itemLayoutRes, 0);
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
                }

        public ListMenuPresenter(int itemLayoutRes, int themeRes) {
            this.mItemLayoutRes = itemLayoutRes;
            this.mThemeRes = themeRes;
                }

                @Override 
        public void initForMenu(Context context, MenuBuilder menu) {
            if (this.mThemeRes != 0) {
                this.mContext = new ContextThemeWrapper(context, this.mThemeRes);
                this.mInflater = LayoutInflater.from(this.mContext);
                    } else if (this.mContext != null) {
                this.mContext = context;
                if (this.mInflater == null) {
                    this.mInflater = LayoutInflater.from(this.mContext);
                        }
                    }
            this.mMenu = menu;
            if (this.mAdapter != null) {
                this.mAdapter.notifyDataSetChanged();
                    }
                }

                @Override 
       public MenuView getMenuView(ViewGroup root) {
           if (this.mAdapter == null) {
               this.mAdapter = new MenuAdapter();
                    }
           if (this.mAdapter.isEmpty()) {
                        return null;
                    }
           if (this.mMenuView == null) {
               this.mMenuView = (ExpandedMenuView) this.mInflater.inflate(C0084R.layout.abc_expanded_menu_layout, root, false);
               this.mMenuView.setAdapter((ListAdapter) this.mAdapter);
               this.mMenuView.setOnItemClickListener(this);
                    }
           return this.mMenuView;
                }

       public ListAdapter getAdapter() {
           if (this.mAdapter == null) {
               this.mAdapter = new MenuAdapter();
                    }
           return this.mAdapter;
                }

                @Override 
       public void updateMenuView(boolean cleared) {
           if (this.mAdapter != null) {
                        this.mAdapter.notifyDataSetChanged();
                    }
                }

                @Override 
       public void setCallback(MenuPresenter.Callback cb) {
           this.mCallback = cb;
                }

                @Override 
       public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
           if (!subMenu.hasVisibleItems()) {
                        return false;
                    }
           new MenuDialogHelper(subMenu).show(null);
           if (this.mCallback != null) {
               this.mCallback.onOpenSubMenu(subMenu);
                    }
           return true;
                }

                @Override 
       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
           if (this.mCallback != null) {
               this.mCallback.onCloseMenu(menu, allMenusAreClosing);
                    }
                }

       int getItemIndexOffset() {
           return this.mItemIndexOffset;
                }

       public void setItemIndexOffset(int offset) {
           this.mItemIndexOffset = offset;
           if (this.mMenuView != null) {
               updateMenuView(false);
                    }
                }

                @Override 
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           this.mMenu.performItemAction(this.mAdapter.getItem(position), 0);
                }

                @Override 
       public boolean flagActionItems() {
           return false;
                }

                @Override 
       public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
           return false;
                }

                @Override 
       public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
           return false;
                }

       public void saveHierarchyState(Bundle outState) {
           SparseArray<Parcelable> viewStates = new SparseArray<>();
           if (this.mMenuView != null) {
               this.mMenuView.saveHierarchyState(viewStates);
                    }
           outState.putSparseParcelableArray(VIEWS_TAG, viewStates);
                }

       public void restoreHierarchyState(Bundle inState) {
           SparseArray<Parcelable> viewStates = inState.getSparseParcelableArray(VIEWS_TAG);
           if (viewStates != null) {
               this.mMenuView.restoreHierarchyState(viewStates);
                    }
                }

       public void setId(int id) {
           this.mId = id;
                }

                @Override 
       public int getId() {
           return this.mId;
                }

                @Override 
       public Parcelable onSaveInstanceState() {
           if (this.mMenuView == null) {
               return null;
                    }
           Bundle state = new Bundle();
           saveHierarchyState(state);
           return state;
                }

                @Override 
       public void onRestoreInstanceState(Parcelable state) {
           restoreHierarchyState((Bundle) state);
                }

       private class MenuAdapter extends BaseAdapter {
                    private int mExpandedIndex = -1;

                    @Override 
           public   Object getItem(int x0) {
               return getItem(x0);
                    }

           public MenuAdapter() {
               findExpandedIndex();
                    }

                    @Override 
           public int getCount() {
               ArrayList<MenuItemImpl> items = ListMenuPresenter.this.mMenu.getNonActionItems();
               int count = items.size() - ListMenuPresenter.access$000(ListMenuPresenter.this);
                        return this.mExpandedIndex < 0 ? count : count - 1;
                    }

                    @Override 
           public MenuItemImpl getItem(int position) {
               ArrayList<MenuItemImpl> items = ListMenuPresenter.this.mMenu.getNonActionItems();
               int position2 = position + ListMenuPresenter.access$000(ListMenuPresenter.this);
               if (this.mExpandedIndex >= 0 && position2 >= this.mExpandedIndex) {
                   position2++;
                        }
               return items.get(position2);
                    }

                    @Override 
           public long getItemId(int position) {
               return position;
                    }

                    @Override 
           public View getView(int position, View convertView, ViewGroup parent) {
               if (convertView == null) {
                   convertView = ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, parent, false);
                        }
               MenuView.ItemView itemView = (MenuView.ItemView) convertView;
               itemView.initialize(getItem(position), 0);
               return convertView;
                    }

           void findExpandedIndex() {
               MenuItemImpl expandedItem = ListMenuPresenter.this.mMenu.getExpandedItem();
               if (expandedItem != null) {
                   ArrayList<MenuItemImpl> items = ListMenuPresenter.this.mMenu.getNonActionItems();
                   int count = items.size();
                            for (int i = 0; i < count; i++) {
                       MenuItemImpl item = items.get(i);
                       if (item == expandedItem) {
                           this.mExpandedIndex = i;
                           return;
                                }
                            }
                        }
               this.mExpandedIndex = -1;
                    }

                    @Override 
           public void notifyDataSetChanged() {
               findExpandedIndex();
               super.notifyDataSetChanged();
                    }
                }
            }
