            package android.support.v7.internal.view.menu;

            import android.app.AlertDialog;
            import android.content.DialogInterface;
            import android.os.IBinder;
            import android.support.v7.appcompat.C0084R;
            import android.view.KeyEvent;
            import android.view.View;
            import android.view.Window;
            import android.view.WindowManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class MenuDialogHelper implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, MenuPresenter.Callback {
                private AlertDialog mDialog;
                private MenuBuilder mMenu;
                ListMenuPresenter mPresenter;
                private MenuPresenter.Callback mPresenterCallback;

/* 42 */        public MenuDialogHelper(MenuBuilder menu) {
/* 44 */            this.mMenu = menu;
                }

/* 53 */        public void show(IBinder windowToken) {
/* 54 */            MenuBuilder menu = this.mMenu;
/* 57 */            AlertDialog.Builder builder = new AlertDialog.Builder(menu.getContext());
/* 61 */            this.mPresenter = new ListMenuPresenter(C0084R.layout.abc_list_menu_item_layout, C0084R.style.Theme_AppCompat_CompactMenu_Dialog);
/* 64 */            this.mPresenter.setCallback(this);
/* 65 */            this.mMenu.addMenuPresenter(this.mPresenter);
/* 66 */            builder.setAdapter(this.mPresenter.getAdapter(), this);
/* 69 */            View headerView = menu.getHeaderView();
/* 70 */            if (headerView != null) {
/* 72 */                builder.setCustomTitle(headerView);
                    } else {
/* 75 */                builder.setIcon(menu.getHeaderIcon()).setTitle(menu.getHeaderTitle());
                    }
/* 79 */            builder.setOnKeyListener(this);
/* 82 */            this.mDialog = builder.create();
/* 83 */            this.mDialog.setOnDismissListener(this);
/* 85 */            WindowManager.LayoutParams lp = this.mDialog.getWindow().getAttributes();
/* 86 */            lp.type = 1003;
/* 87 */            if (windowToken != null) {
/* 88 */                lp.token = windowToken;
                    }
/* 90 */            lp.flags |= 131072;
/* 92 */            this.mDialog.show();
                }

                @Override // android.content.DialogInterface.OnKeyListener
/* 95 */        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Window win;
                    View decor;
                    KeyEvent.DispatcherState ds;
                    View decor2;
                    KeyEvent.DispatcherState ds2;
/* 96 */            if (keyCode == 82 || keyCode == 4) {
/* 97 */                if (event.getAction() == 0 && event.getRepeatCount() == 0) {
/* 99 */                    Window win2 = this.mDialog.getWindow();
/* 100 */                   if (win2 != null && (decor2 = win2.getDecorView()) != null && (ds2 = decor2.getKeyDispatcherState()) != null) {
/* 105 */                       ds2.startTracking(event, this);
                                return true;
                            }
                        } else if (event.getAction() == 1 && !event.isCanceled() && (win = this.mDialog.getWindow()) != null && (decor = win.getDecorView()) != null && (ds = decor.getKeyDispatcherState()) != null && ds.isTracking(event)) {
/* 117 */                   this.mMenu.close(true);
/* 118 */                   dialog.dismiss();
                            return true;
                        }
                    }
/* 127 */           return this.mMenu.performShortcut(keyCode, event, 0);
                }

/* 131 */       public void setPresenterCallback(MenuPresenter.Callback cb) {
/* 132 */           this.mPresenterCallback = cb;
                }

/* 140 */       public void dismiss() {
/* 141 */           if (this.mDialog != null) {
/* 142 */               this.mDialog.dismiss();
                    }
                }

/* 147 */       public void onDismiss(DialogInterface dialog) {
/* 148 */           this.mPresenter.onCloseMenu(this.mMenu, true);
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 152 */       public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
/* 153 */           if (allMenusAreClosing || menu == this.mMenu) {
/* 154 */               dismiss();
                    }
/* 156 */           if (this.mPresenterCallback != null) {
/* 157 */               this.mPresenterCallback.onCloseMenu(menu, allMenusAreClosing);
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuPresenter.Callback
/* 162 */       public boolean onOpenSubMenu(MenuBuilder subMenu) {
/* 163 */           if (this.mPresenterCallback != null) {
/* 164 */               return this.mPresenterCallback.onOpenSubMenu(subMenu);
                    }
                    return false;
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 169 */       public void onClick(DialogInterface dialog, int which) {
/* 170 */           this.mMenu.performItemAction((MenuItemImpl) this.mPresenter.getAdapter().getItem(which), 0);
                }
            }
