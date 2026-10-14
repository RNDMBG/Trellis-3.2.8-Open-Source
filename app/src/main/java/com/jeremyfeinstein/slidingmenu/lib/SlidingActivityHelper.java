            package com.jeremyfeinstein.slidingmenu.lib;

            import android.app.Activity;
            import android.os.Bundle;
            import android.os.Handler;
            import android.view.DragEvent;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class SlidingActivityHelper {
                private Activity mActivity;
                private SlidingMenu mSlidingMenu;
                private View mViewAbove;
                private View mViewBehind;
                private boolean mBroadcasting = false;
                private boolean mOnPostCreateCalled = false;
                private boolean mEnableSlide = true;

/* 13 */        static /* synthetic */ SlidingMenu access$000(SlidingActivityHelper x0) {
/* 14 */            return x0.mSlidingMenu;
                }

/* 34 */        public SlidingActivityHelper(Activity activity) {
/* 36 */            this.mActivity = activity;
                }

/* 44 */        public void onCreate(Bundle savedInstanceState) {
/* 45 */            this.mSlidingMenu = (SlidingMenu) LayoutInflater.from(this.mActivity).inflate(C0257R.layout.slidingmenumain, (ViewGroup) null);
                }

/* 53 */        public void onPostCreate(Bundle savedInstanceState) {
                    boolean open;
                    boolean secondary;
/* 54 */            if (this.mViewBehind == null || this.mViewAbove == null) {
/* 55 */                throw new IllegalStateException("Both setBehindContentView must be called in onCreate in addition to setContentView.");
                    }
/* 59 */            this.mOnPostCreateCalled = true;
/* 61 */            this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1);
/* 66 */            if (savedInstanceState != null) {
/* 67 */                open = savedInstanceState.getBoolean("SlidingActivityHelper.open");
/* 68 */                secondary = savedInstanceState.getBoolean("SlidingActivityHelper.secondary");
                    } else {
/* 70 */                open = false;
/* 71 */                secondary = false;
                    }
/* 73 */            new Handler().post(new RunnableC12431(open, secondary));
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper$1 */
/* 71 */        class RunnableC12431 implements Runnable {
                    final /* synthetic */ boolean val$open;
                    final /* synthetic */ boolean val$secondary;

/* 72 */            RunnableC12431(boolean z, boolean z2) {
                        this.val$open = z;
                        this.val$secondary = z2;
                    }

                    @Override // java.lang.Runnable
/* 74 */            public void run() {
/* 75 */                if (this.val$open) {
/* 76 */                    if (this.val$secondary) {
/* 77 */                        SlidingActivityHelper.access$000(SlidingActivityHelper.this).showSecondaryMenu(false);
/* 84 */                        return;
                            } else {
/* 79 */                        SlidingActivityHelper.access$000(SlidingActivityHelper.this).showMenu(false);
/* 84 */                        return;
                            }
                        }
/* 82 */                SlidingActivityHelper.access$000(SlidingActivityHelper.this).showContent(false);
                    }
                }

/* 95 */        public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled) {
/* 96 */            if (this.mOnPostCreateCalled) {
/* 97 */                throw new IllegalStateException("enableSlidingActionBar must be called in onCreate.");
                    }
/* 98 */            this.mEnableSlide = slidingActionBarEnabled;
                }

/* 108 */       public View findViewById(int id) {
                    View v;
/* 109 */           if (this.mSlidingMenu == null || (v = this.mSlidingMenu.findViewById(id)) == null) {
                        return null;
                    }
/* 114 */           return v;
                }

/* 124 */       public void onSaveInstanceState(Bundle outState) {
/* 125 */           outState.putBoolean("SlidingActivityHelper.open", this.mSlidingMenu.isMenuShowing());
/* 126 */           outState.putBoolean("SlidingActivityHelper.secondary", this.mSlidingMenu.isSecondaryMenuShowing());
                }

/* 135 */       public void registerAboveContentView(View v, ViewGroup.LayoutParams params) {
/* 136 */           if (!this.mBroadcasting) {
/* 137 */               this.mViewAbove = v;
                    }
                }

/* 149 */       public void setContentView(View v) {
/* 150 */           this.mBroadcasting = true;
/* 151 */           this.mActivity.setContentView(v);
                }

/* 161 */       public void setBehindContentView(View view, ViewGroup.LayoutParams layoutParams) {
/* 162 */           this.mViewBehind = view;
/* 163 */           this.mSlidingMenu.setMenu(this.mViewBehind);
                }

/* 171 */       public SlidingMenu getSlidingMenu() {
/* 172 */           return this.mSlidingMenu;
                }

/* 178 */       public void toggle() {
/* 179 */           this.mSlidingMenu.toggle();
                }

/* 185 */       public void showContent() {
/* 186 */           this.mSlidingMenu.showContent();
                }

/* 192 */       public void showMenu() {
/* 193 */           this.mSlidingMenu.showMenu();
                }

/* 200 */       public void showSecondaryMenu() {
/* 201 */           this.mSlidingMenu.showSecondaryMenu();
                }

/* 211 */       public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 212 */           if (keyCode != 4 || !this.mSlidingMenu.isMenuShowing()) {
                        return false;
                    }
/* 213 */           showContent();
/* 214 */           return true;
                }

/* 230 */       public static class MenuStateHandler implements View.OnDragListener, SlidingMenu.OnClosedListener, SlidingMenu.OnCloseListener, SlidingMenu.OnOpenedListener, SlidingMenu.OnOpenListener, View.OnClickListener {
                    private final MenuStateListener mListener;

                    public interface MenuStateListener {
                        void onMenuClick(View view);

                        void onMenuClose();

                        void onMenuClosed();

                        boolean onMenuDrag(View view, DragEvent dragEvent);

                        void onMenuOpen();

                        void onMenuOpened();
                    }

/* 231 */           public MenuStateHandler(MenuStateListener listener) {
/* 233 */               this.mListener = listener;
                    }

                    @Override // android.view.View.OnDragListener
/* 237 */           public boolean onDrag(View v, DragEvent event) {
/* 238 */               return this.mListener.onMenuDrag(v, event);
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener
/* 242 */           public void onClosed() {
/* 243 */               this.mListener.onMenuClosed();
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener
/* 247 */           public void onClose() {
/* 248 */               this.mListener.onMenuClose();
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener
/* 252 */           public void onOpened() {
/* 253 */               this.mListener.onMenuOpened();
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener
/* 257 */           public void onOpen() {
/* 258 */               this.mListener.onMenuOpen();
                    }

                    @Override // android.view.View.OnClickListener
/* 262 */           public void onClick(View v) {
/* 263 */               this.mListener.onMenuClick(v);
                    }
                }

/* 267 */       public void setMenuStateListener(MenuStateHandler.MenuStateListener listener) {
/* 268 */           MenuStateHandler handler = new MenuStateHandler(listener);
/* 269 */           this.mSlidingMenu.setOnClosedListener(handler);
/* 270 */           this.mSlidingMenu.setOnCloseListener(handler);
/* 271 */           this.mSlidingMenu.setOnOpenedListener(handler);
/* 272 */           this.mSlidingMenu.setOnOpenListener(handler);
/* 273 */           this.mSlidingMenu.setOnClickListener(handler);
/* 274 */           this.mSlidingMenu.setOnDragListener(handler);
                }
            }
