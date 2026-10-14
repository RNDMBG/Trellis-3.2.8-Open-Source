            package android.support.v4.view;

            import android.os.Build;
            import android.support.v4.internal.view.SupportMenuItem;
            import android.util.Log;
            import android.view.MenuItem;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 111 */   public class MenuItemCompat {
                static final MenuVersionImpl IMPL;
                public static final int SHOW_AS_ACTION_ALWAYS = 2;
                public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
                public static final int SHOW_AS_ACTION_IF_ROOM = 1;
                public static final int SHOW_AS_ACTION_NEVER = 0;
                public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
                private static final String TAG = "MenuItemCompat";

                interface MenuVersionImpl {
                    boolean collapseActionView(MenuItem menuItem);

                    boolean expandActionView(MenuItem menuItem);

                    View getActionView(MenuItem menuItem);

                    boolean isActionViewExpanded(MenuItem menuItem);

                    MenuItem setActionView(MenuItem menuItem, int i);

                    MenuItem setActionView(MenuItem menuItem, View view);

                    MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener);

                    void setShowAsAction(MenuItem menuItem, int i);
                }

                public interface OnActionExpandListener {
                    boolean onMenuItemActionCollapse(MenuItem menuItem);

                    boolean onMenuItemActionExpand(MenuItem menuItem);
                }

/* 112 */       static class BaseMenuVersionImpl implements MenuVersionImpl {
/* 113 */           BaseMenuVersionImpl() {
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 116 */           public void setShowAsAction(MenuItem item, int actionEnum) {
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 120 */           public MenuItem setActionView(MenuItem item, View view) {
/* 121 */               return item;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 125 */           public MenuItem setActionView(MenuItem item, int resId) {
/* 126 */               return item;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 130 */           public View getActionView(MenuItem item) {
/* 131 */               return null;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 135 */           public boolean expandActionView(MenuItem item) {
/* 136 */               return false;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 140 */           public boolean collapseActionView(MenuItem item) {
/* 141 */               return false;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 145 */           public boolean isActionViewExpanded(MenuItem item) {
/* 146 */               return false;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 150 */           public MenuItem setOnActionExpandListener(MenuItem item, OnActionExpandListener listener) {
/* 151 */               return item;
                    }
                }

/* 156 */       static class HoneycombMenuVersionImpl implements MenuVersionImpl {
/* 157 */           HoneycombMenuVersionImpl() {
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 160 */           public void setShowAsAction(MenuItem item, int actionEnum) {
/* 161 */               MenuItemCompatHoneycomb.setShowAsAction(item, actionEnum);
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 165 */           public MenuItem setActionView(MenuItem item, View view) {
/* 166 */               return MenuItemCompatHoneycomb.setActionView(item, view);
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 170 */           public MenuItem setActionView(MenuItem item, int resId) {
/* 171 */               return MenuItemCompatHoneycomb.setActionView(item, resId);
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 175 */           public View getActionView(MenuItem item) {
/* 176 */               return MenuItemCompatHoneycomb.getActionView(item);
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 180 */           public boolean expandActionView(MenuItem item) {
/* 181 */               return false;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 185 */           public boolean collapseActionView(MenuItem item) {
/* 186 */               return false;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 190 */           public boolean isActionViewExpanded(MenuItem item) {
/* 191 */               return false;
                    }

                    @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 195 */           public MenuItem setOnActionExpandListener(MenuItem item, OnActionExpandListener listener) {
/* 196 */               return item;
                    }
                }

/* 198 */       static class IcsMenuVersionImpl extends HoneycombMenuVersionImpl {
/* 199 */           IcsMenuVersionImpl() {
                    }

                    @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 202 */           public boolean expandActionView(MenuItem item) {
/* 203 */               return MenuItemCompatIcs.expandActionView(item);
                    }

                    @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 207 */           public boolean collapseActionView(MenuItem item) {
/* 208 */               return MenuItemCompatIcs.collapseActionView(item);
                    }

                    @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 212 */           public boolean isActionViewExpanded(MenuItem item) {
/* 213 */               return MenuItemCompatIcs.isActionViewExpanded(item);
                    }

                    @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
/* 218 */           public MenuItem setOnActionExpandListener(MenuItem item, OnActionExpandListener listener) {
                        return listener == null ? MenuItemCompatIcs.setOnActionExpandListener(item, null) : MenuItemCompatIcs.setOnActionExpandListener(item, new C00461(listener));
                    }

                    /* JADX INFO: renamed from: android.support.v4.view.MenuItemCompat$IcsMenuVersionImpl$1 */
/* 226 */           class C00461 implements MenuItemCompatIcs.SupportActionExpandProxy {
                        final /* synthetic */ OnActionExpandListener val$listener;

/* 227 */               C00461(OnActionExpandListener onActionExpandListener) {
                            this.val$listener = onActionExpandListener;
                        }

                        @Override // android.support.v4.view.MenuItemCompatIcs.SupportActionExpandProxy
/* 230 */               public boolean onMenuItemActionExpand(MenuItem item) {
/* 231 */                   return this.val$listener.onMenuItemActionExpand(item);
                        }

                        @Override // android.support.v4.view.MenuItemCompatIcs.SupportActionExpandProxy
/* 235 */               public boolean onMenuItemActionCollapse(MenuItem item) {
/* 236 */                   return this.val$listener.onMenuItemActionCollapse(item);
                        }
                    }
                }

                static {
/* 247 */           int version = Build.VERSION.SDK_INT;
/* 248 */           if (version >= 14) {
/* 249 */               IMPL = new IcsMenuVersionImpl();
                    } else if (version >= 11) {
/* 251 */               IMPL = new HoneycombMenuVersionImpl();
                    } else {
/* 253 */               IMPL = new BaseMenuVersionImpl();
                    }
                }

/* 267 */       public static void setShowAsAction(MenuItem item, int actionEnum) {
/* 268 */           if (item instanceof SupportMenuItem) {
/* 269 */               ((SupportMenuItem) item).setShowAsAction(actionEnum);
                    } else {
/* 271 */               IMPL.setShowAsAction(item, actionEnum);
                    }
                }

/* 286 */       public static MenuItem setActionView(MenuItem item, View view) {
                    return item instanceof SupportMenuItem ? ((SupportMenuItem) item).setActionView(view) : IMPL.setActionView(item, view);
                }

/* 308 */       public static MenuItem setActionView(MenuItem item, int resId) {
                    return item instanceof SupportMenuItem ? ((SupportMenuItem) item).setActionView(resId) : IMPL.setActionView(item, resId);
                }

/* 321 */       public static View getActionView(MenuItem item) {
                    return item instanceof SupportMenuItem ? ((SupportMenuItem) item).getActionView() : IMPL.getActionView(item);
                }

/* 343 */       public static MenuItem setActionProvider(MenuItem item, ActionProvider provider) {
/* 344 */           if (item instanceof SupportMenuItem) {
/* 345 */               return ((SupportMenuItem) item).setSupportActionProvider(provider);
                    }
/* 348 */           Log.w(TAG, "setActionProvider: item does not implement SupportMenuItem; ignoring");
/* 349 */           return item;
                }

/* 360 */       public static ActionProvider getActionProvider(MenuItem item) {
/* 361 */           if (item instanceof SupportMenuItem) {
/* 362 */               return ((SupportMenuItem) item).getSupportActionProvider();
                    }
/* 366 */           Log.w(TAG, "getActionProvider: item does not implement SupportMenuItem; returning null");
/* 367 */           return null;
                }

/* 382 */       public static boolean expandActionView(MenuItem item) {
                    return item instanceof SupportMenuItem ? ((SupportMenuItem) item).expandActionView() : IMPL.expandActionView(item);
                }

/* 401 */       public static boolean collapseActionView(MenuItem item) {
                    return item instanceof SupportMenuItem ? ((SupportMenuItem) item).collapseActionView() : IMPL.collapseActionView(item);
                }

/* 417 */       public static boolean isActionViewExpanded(MenuItem item) {
                    return item instanceof SupportMenuItem ? ((SupportMenuItem) item).isActionViewExpanded() : IMPL.isActionViewExpanded(item);
                }

/* 434 */       public static MenuItem setOnActionExpandListener(MenuItem item, OnActionExpandListener listener) {
                    return item instanceof SupportMenuItem ? ((SupportMenuItem) item).setSupportOnActionExpandListener(listener) : IMPL.setOnActionExpandListener(item, listener);
                }
            }
