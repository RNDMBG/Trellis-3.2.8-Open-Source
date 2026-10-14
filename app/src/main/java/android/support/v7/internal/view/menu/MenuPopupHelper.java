            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.content.res.Resources;
            import android.os.Parcelable;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.widget.ListPopupWindow;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewTreeObserver;
            import android.widget.AdapterView;
            import android.widget.BaseAdapter;
            import android.widget.FrameLayout;
            import android.widget.ListAdapter;
            import android.widget.PopupWindow;
            import java.util.ArrayList;
            
            
    public class MenuPopupHelper implements AdapterView.OnItemClickListener, View.OnKeyListener, ViewTreeObserver.OnGlobalLayoutListener, PopupWindow.OnDismissListener, MenuPresenter {
                static final int ITEM_LAYOUT = C0084R.layout.abc_popup_menu_item_layout;
                private static final String TAG = "MenuPopupHelper";
                private MenuAdapter mAdapter;
                private View mAnchorView;
                private Context mContext;
                boolean mForceShowIcon;
                private LayoutInflater mInflater;
                private ViewGroup mMeasureParent;
                private MenuBuilder mMenu;
                private boolean mOverflowOnly;
                private ListPopupWindow mPopup;
                private int mPopupMaxWidth;
                private MenuPresenter.Callback mPresenterCallback;
                private ViewTreeObserver mTreeObserver;

        static  boolean access$100(MenuPopupHelper x0) {
            return x0.mOverflowOnly;
                }

        static  LayoutInflater access$200(MenuPopupHelper x0) {
            return x0.mInflater;
                }

        static  MenuBuilder access$300(MenuPopupHelper x0) {
            return x0.mMenu;
                }

        public MenuPopupHelper(Context context, MenuBuilder menu) {
            this(context, menu, null, false);
                }

        public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView) {
            this(context, menu, anchorView, false);
                }

        public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
            this.mMenu = menu;
            this.mOverflowOnly = overflowOnly;
            Resources res = context.getResources();
            this.mPopupMaxWidth = Math.max(res.getDisplayMetrics().widthPixels / 2, res.getDimensionPixelSize(C0084R.dimen.abc_config_prefDialogWidth));
            this.mAnchorView = anchorView;
            menu.addMenuPresenter(this);
                }

        public void setAnchorView(View anchor) {
            this.mAnchorView = anchor;
                }

        public void setForceShowIcon(boolean forceShow) {
            this.mForceShowIcon = forceShow;
                }

       public void show() {
           if (!tryShow()) {
               throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
                    }
                }

       public boolean tryShow() {
           this.mPopup = new ListPopupWindow(this.mContext, null, C0084R.attr.popupMenuStyle);
           this.mPopup.setOnDismissListener(this);
           this.mPopup.setOnItemClickListener(this);
           this.mAdapter = new MenuAdapter(this.mMenu);
           this.mPopup.setAdapter(this.mAdapter);
           this.mPopup.setModal(true);
           View anchor = this.mAnchorView;
           if (anchor == null) {
                        return false;
                    }
           boolean addGlobalListener = this.mTreeObserver == null;
           this.mTreeObserver = anchor.getViewTreeObserver();
           if (addGlobalListener) {
               this.mTreeObserver.addOnGlobalLayoutListener(this);
                    }
           this.mPopup.setAnchorView(anchor);
           this.mPopup.setContentWidth(Math.min(measureContentWidth(this.mAdapter), this.mPopupMaxWidth));
           this.mPopup.setInputMethodMode(2);
           this.mPopup.show();
           this.mPopup.getListView().setOnKeyListener(this);
                    return true;
                }

       public void dismiss() {
           if (isShowing()) {
               this.mPopup.dismiss();
                    }
                }

       public void onDismiss() {
           this.mPopup = null;
           this.mMenu.close();
           if (this.mTreeObserver != null) {
               if (!this.mTreeObserver.isAlive()) {
                   this.mTreeObserver = this.mAnchorView.getViewTreeObserver();
                        }
               this.mTreeObserver.removeGlobalOnLayoutListener(this);
               this.mTreeObserver = null;
                    }
                }

       public boolean isShowing() {
                    return this.mPopup != null && this.mPopup.isShowing();
                }

                @Override 
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           MenuAdapter adapter = this.mAdapter;
           MenuAdapter.access$000(adapter).performItemAction(adapter.getItem(position), 0);
                }

                @Override 
       public boolean onKey(View v, int keyCode, KeyEvent event) {
           if (event.getAction() != 1 || keyCode != 82) {
                        return false;
                    }
           dismiss();
                    return true;
                }

       private int measureContentWidth(ListAdapter adapter) {
           int width = 0;
           View itemView = null;
           int itemType = 0;
           int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
           int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
           int count = adapter.getCount();
                    for (int i = 0; i < count; i++) {
               int positionType = adapter.getItemViewType(i);
               if (positionType != itemType) {
                   itemType = positionType;
                   itemView = null;
                        }
               if (this.mMeasureParent == null) {
                   this.mMeasureParent = new FrameLayout(this.mContext);
                        }
               itemView = adapter.getView(i, itemView, this.mMeasureParent);
               itemView.measure(widthMeasureSpec, heightMeasureSpec);
               width = Math.max(width, itemView.getMeasuredWidth());
                    }
           return width;
                }

                @Override 
       public void onGlobalLayout() {
           if (isShowing()) {
               View anchor = this.mAnchorView;
               if (anchor == null || !anchor.isShown()) {
                   dismiss();
                        } else if (isShowing()) {
                   this.mPopup.show();
                        }
                    }
                }

                @Override 
       public void initForMenu(Context context, MenuBuilder menu) {
                }

                @Override 
       public MenuView getMenuView(ViewGroup root) {
           throw new UnsupportedOperationException("MenuPopupHelpers manage their own views");
                }

                @Override 
       public void updateMenuView(boolean cleared) {
           if (this.mAdapter != null) {
               this.mAdapter.notifyDataSetChanged();
                    }
                }

                @Override 
       public void setCallback(MenuPresenter.Callback cb) {
           this.mPresenterCallback = cb;
                }

                @Override 
       public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
           if (!subMenu.hasVisibleItems()) {
                        return false;
                    }
           MenuPopupHelper subPopup = new MenuPopupHelper(this.mContext, subMenu, this.mAnchorView, false);
           subPopup.setCallback(this.mPresenterCallback);
           boolean preserveIconSpacing = false;
           int count = subMenu.size();
                    for (int i = 0; i < count; i++) {
               MenuItem childItem = subMenu.getItem(i);
               if (childItem.isVisible() && childItem.getIcon() != null) {
                   preserveIconSpacing = true;
                            break;
                        }
                    }
           subPopup.setForceShowIcon(preserveIconSpacing);
           if (!subPopup.tryShow()) {
                        return false;
                    }
           if (this.mPresenterCallback != null) {
               this.mPresenterCallback.onOpenSubMenu(subMenu);
                    }
           return true;
                }

                @Override 
       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
           if (menu == this.mMenu) {
               dismiss();
               if (this.mPresenterCallback != null) {
                   this.mPresenterCallback.onCloseMenu(menu, allMenusAreClosing);
                        }
                    }
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

                @Override 
       public int getId() {
           return 0;
                }

                @Override 
       public Parcelable onSaveInstanceState() {
           return null;
                }

                @Override 
       public void onRestoreInstanceState(Parcelable state) {
                }

       private class MenuAdapter extends BaseAdapter {
                    private MenuBuilder mAdapterMenu;
                    private int mExpandedIndex = -1;

           static  MenuBuilder access$000(MenuAdapter x0) {
               return x0.mAdapterMenu;
                    }

                    @Override 
           public   Object getItem(int x0) {
               return getItem(x0);
                    }

           public MenuAdapter(MenuBuilder menu) {
               this.mAdapterMenu = menu;
               findExpandedIndex();
                    }

                    @Override 
           public int getCount() {
               ArrayList<MenuItemImpl> items = MenuPopupHelper.access$100(MenuPopupHelper.this) ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
               if (this.mExpandedIndex < 0) {
                   return items.size();
                        }
               return items.size() - 1;
                    }

                    @Override 
           public MenuItemImpl getItem(int position) {
               ArrayList<MenuItemImpl> items = MenuPopupHelper.access$100(MenuPopupHelper.this) ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
               if (this.mExpandedIndex >= 0 && position >= this.mExpandedIndex) {
                   position++;
                        }
               return items.get(position);
                    }

                    @Override 
           public long getItemId(int position) {
               return position;
                    }

                    @Override 
           public View getView(int position, View convertView, ViewGroup parent) {
               if (convertView == null) {
                   convertView = MenuPopupHelper.access$200(MenuPopupHelper.this).inflate(MenuPopupHelper.ITEM_LAYOUT, parent, false);
                        }
               MenuView.ItemView itemView = (MenuView.ItemView) convertView;
               if (MenuPopupHelper.this.mForceShowIcon) {
                   ((ListMenuItemView) convertView).setForceShowIcon(true);
                        }
               itemView.initialize(getItem(position), 0);
               return convertView;
                    }

           void findExpandedIndex() {
               MenuItemImpl expandedItem = MenuPopupHelper.access$300(MenuPopupHelper.this).getExpandedItem();
               if (expandedItem != null) {
                   ArrayList<MenuItemImpl> items = MenuPopupHelper.access$300(MenuPopupHelper.this).getNonActionItems();
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
