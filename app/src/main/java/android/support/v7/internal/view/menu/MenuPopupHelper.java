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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class MenuPopupHelper implements AdapterView.OnItemClickListener, View.OnKeyListener, ViewTreeObserver.OnGlobalLayoutListener, PopupWindow.OnDismissListener, MenuPresenter {
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

/* 43 */        static /* synthetic */ boolean access$100(MenuPopupHelper x0) {
/* 44 */            return x0.mOverflowOnly;
                }

/* 43 */        static /* synthetic */ LayoutInflater access$200(MenuPopupHelper x0) {
/* 44 */            return x0.mInflater;
                }

/* 43 */        static /* synthetic */ MenuBuilder access$300(MenuPopupHelper x0) {
/* 44 */            return x0.mMenu;
                }

/* 69 */        public MenuPopupHelper(Context context, MenuBuilder menu) {
/* 70 */            this(context, menu, null, false);
                }

/* 73 */        public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView) {
/* 74 */            this(context, menu, anchorView, false);
                }

/* 77 */        public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly) {
/* 79 */            this.mContext = context;
/* 80 */            this.mInflater = LayoutInflater.from(context);
/* 81 */            this.mMenu = menu;
/* 82 */            this.mOverflowOnly = overflowOnly;
/* 84 */            Resources res = context.getResources();
/* 85 */            this.mPopupMaxWidth = Math.max(res.getDisplayMetrics().widthPixels / 2, res.getDimensionPixelSize(C0084R.dimen.abc_config_prefDialogWidth));
/* 88 */            this.mAnchorView = anchorView;
/* 90 */            menu.addMenuPresenter(this);
                }

/* 93 */        public void setAnchorView(View anchor) {
/* 94 */            this.mAnchorView = anchor;
                }

/* 97 */        public void setForceShowIcon(boolean forceShow) {
/* 98 */            this.mForceShowIcon = forceShow;
                }

/* 101 */       public void show() {
/* 102 */           if (!tryShow()) {
/* 103 */               throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
                    }
                }

/* 107 */       public boolean tryShow() {
/* 108 */           this.mPopup = new ListPopupWindow(this.mContext, null, C0084R.attr.popupMenuStyle);
/* 109 */           this.mPopup.setOnDismissListener(this);
/* 110 */           this.mPopup.setOnItemClickListener(this);
/* 112 */           this.mAdapter = new MenuAdapter(this.mMenu);
/* 113 */           this.mPopup.setAdapter(this.mAdapter);
/* 114 */           this.mPopup.setModal(true);
/* 116 */           View anchor = this.mAnchorView;
/* 117 */           if (anchor == null) {
                        return false;
                    }
/* 118 */           boolean addGlobalListener = this.mTreeObserver == null;
/* 119 */           this.mTreeObserver = anchor.getViewTreeObserver();
/* 120 */           if (addGlobalListener) {
/* 121 */               this.mTreeObserver.addOnGlobalLayoutListener(this);
                    }
/* 123 */           this.mPopup.setAnchorView(anchor);
/* 128 */           this.mPopup.setContentWidth(Math.min(measureContentWidth(this.mAdapter), this.mPopupMaxWidth));
/* 129 */           this.mPopup.setInputMethodMode(2);
/* 130 */           this.mPopup.show();
/* 131 */           this.mPopup.getListView().setOnKeyListener(this);
                    return true;
                }

/* 135 */       public void dismiss() {
/* 136 */           if (isShowing()) {
/* 137 */               this.mPopup.dismiss();
                    }
                }

/* 141 */       public void onDismiss() {
/* 142 */           this.mPopup = null;
/* 143 */           this.mMenu.close();
/* 144 */           if (this.mTreeObserver != null) {
/* 145 */               if (!this.mTreeObserver.isAlive()) {
/* 146 */                   this.mTreeObserver = this.mAnchorView.getViewTreeObserver();
                        }
/* 148 */               this.mTreeObserver.removeGlobalOnLayoutListener(this);
/* 149 */               this.mTreeObserver = null;
                    }
                }

/* 153 */       public boolean isShowing() {
                    return this.mPopup != null && this.mPopup.isShowing();
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 158 */       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 159 */           MenuAdapter adapter = this.mAdapter;
/* 160 */           MenuAdapter.access$000(adapter).performItemAction(adapter.getItem(position), 0);
                }

                @Override // android.view.View.OnKeyListener
/* 163 */       public boolean onKey(View v, int keyCode, KeyEvent event) {
/* 164 */           if (event.getAction() != 1 || keyCode != 82) {
                        return false;
                    }
/* 165 */           dismiss();
                    return true;
                }

/* 172 */       private int measureContentWidth(ListAdapter adapter) {
/* 173 */           int width = 0;
/* 174 */           View itemView = null;
/* 175 */           int itemType = 0;
/* 176 */           int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 178 */           int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 180 */           int count = adapter.getCount();
                    for (int i = 0; i < count; i++) {
/* 182 */               int positionType = adapter.getItemViewType(i);
/* 183 */               if (positionType != itemType) {
/* 184 */                   itemType = positionType;
/* 185 */                   itemView = null;
                        }
/* 187 */               if (this.mMeasureParent == null) {
/* 188 */                   this.mMeasureParent = new FrameLayout(this.mContext);
                        }
/* 190 */               itemView = adapter.getView(i, itemView, this.mMeasureParent);
/* 191 */               itemView.measure(widthMeasureSpec, heightMeasureSpec);
/* 192 */               width = Math.max(width, itemView.getMeasuredWidth());
                    }
/* 194 */           return width;
                }

                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
/* 198 */       public void onGlobalLayout() {
/* 199 */           if (isShowing()) {
/* 200 */               View anchor = this.mAnchorView;
/* 201 */               if (anchor == null || !anchor.isShown()) {
/* 202 */                   dismiss();
                        } else if (isShowing()) {
/* 205 */                   this.mPopup.show();
                        }
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 212 */       public void initForMenu(Context context, MenuBuilder menu) {
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 216 */       public MenuView getMenuView(ViewGroup root) {
/* 217 */           throw new UnsupportedOperationException("MenuPopupHelpers manage their own views");
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 221 */       public void updateMenuView(boolean cleared) {
/* 222 */           if (this.mAdapter != null) {
/* 223 */               this.mAdapter.notifyDataSetChanged();
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 228 */       public void setCallback(MenuPresenter.Callback cb) {
/* 229 */           this.mPresenterCallback = cb;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 233 */       public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
/* 234 */           if (!subMenu.hasVisibleItems()) {
                        return false;
                    }
/* 235 */           MenuPopupHelper subPopup = new MenuPopupHelper(this.mContext, subMenu, this.mAnchorView, false);
/* 236 */           subPopup.setCallback(this.mPresenterCallback);
/* 238 */           boolean preserveIconSpacing = false;
/* 239 */           int count = subMenu.size();
                    for (int i = 0; i < count; i++) {
/* 241 */               MenuItem childItem = subMenu.getItem(i);
/* 242 */               if (childItem.isVisible() && childItem.getIcon() != null) {
/* 243 */                   preserveIconSpacing = true;
                            break;
                        }
                    }
/* 247 */           subPopup.setForceShowIcon(preserveIconSpacing);
/* 249 */           if (!subPopup.tryShow()) {
                        return false;
                    }
/* 250 */           if (this.mPresenterCallback != null) {
/* 251 */               this.mPresenterCallback.onOpenSubMenu(subMenu);
                    }
/* 253 */           return true;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 261 */       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 262 */           if (menu == this.mMenu) {
/* 266 */               dismiss();
/* 267 */               if (this.mPresenterCallback != null) {
/* 268 */                   this.mPresenterCallback.onCloseMenu(menu, allMenusAreClosing);
                        }
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 273 */       public boolean flagActionItems() {
/* 274 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 277 */       public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 278 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 281 */       public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
/* 282 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 286 */       public int getId() {
/* 287 */           return 0;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 291 */       public Parcelable onSaveInstanceState() {
/* 292 */           return null;
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter
/* 296 */       public void onRestoreInstanceState(Parcelable state) {
                }

/* 297 */       private class MenuAdapter extends BaseAdapter {
                    private MenuBuilder mAdapterMenu;
                    private int mExpandedIndex = -1;

/* 298 */           static /* synthetic */ MenuBuilder access$000(MenuAdapter x0) {
/* 299 */               return x0.mAdapterMenu;
                    }

                    @Override // android.widget.Adapter
/* 298 */           public /* bridge */ /* synthetic */ Object getItem(int x0) {
/* 299 */               return getItem(x0);
                    }

/* 303 */           public MenuAdapter(MenuBuilder menu) {
/* 305 */               this.mAdapterMenu = menu;
/* 306 */               findExpandedIndex();
                    }

                    @Override // android.widget.Adapter
/* 309 */           public int getCount() {
/* 310 */               ArrayList<MenuItemImpl> items = MenuPopupHelper.access$100(MenuPopupHelper.this) ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
/* 312 */               if (this.mExpandedIndex < 0) {
/* 313 */                   return items.size();
                        }
/* 315 */               return items.size() - 1;
                    }

                    @Override // android.widget.Adapter
/* 318 */           public MenuItemImpl getItem(int position) {
/* 319 */               ArrayList<MenuItemImpl> items = MenuPopupHelper.access$100(MenuPopupHelper.this) ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
/* 321 */               if (this.mExpandedIndex >= 0 && position >= this.mExpandedIndex) {
/* 322 */                   position++;
                        }
/* 324 */               return items.get(position);
                    }

                    @Override // android.widget.Adapter
/* 329 */           public long getItemId(int position) {
/* 330 */               return position;
                    }

                    @Override // android.widget.Adapter
/* 333 */           public View getView(int position, View convertView, ViewGroup parent) {
/* 334 */               if (convertView == null) {
/* 335 */                   convertView = MenuPopupHelper.access$200(MenuPopupHelper.this).inflate(MenuPopupHelper.ITEM_LAYOUT, parent, false);
                        }
/* 338 */               MenuView.ItemView itemView = (MenuView.ItemView) convertView;
/* 339 */               if (MenuPopupHelper.this.mForceShowIcon) {
/* 340 */                   ((ListMenuItemView) convertView).setForceShowIcon(true);
                        }
/* 342 */               itemView.initialize(getItem(position), 0);
/* 343 */               return convertView;
                    }

/* 346 */           void findExpandedIndex() {
/* 347 */               MenuItemImpl expandedItem = MenuPopupHelper.access$300(MenuPopupHelper.this).getExpandedItem();
/* 348 */               if (expandedItem != null) {
/* 349 */                   ArrayList<MenuItemImpl> items = MenuPopupHelper.access$300(MenuPopupHelper.this).getNonActionItems();
/* 350 */                   int count = items.size();
                            for (int i = 0; i < count; i++) {
/* 352 */                       MenuItemImpl item = items.get(i);
/* 353 */                       if (item == expandedItem) {
/* 354 */                           this.mExpandedIndex = i;
/* 360 */                           return;
                                }
                            }
                        }
/* 359 */               this.mExpandedIndex = -1;
                    }

                    @Override // android.widget.BaseAdapter
/* 363 */           public void notifyDataSetChanged() {
/* 364 */               findExpandedIndex();
/* 365 */               super.notifyDataSetChanged();
                    }
                }
            }
