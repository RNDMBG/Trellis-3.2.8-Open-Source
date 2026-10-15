            package com.jeremyfeinstein.slidingmenu.lib;

            import android.app.Activity;
            import android.os.Bundle;
            import android.os.Handler;
            import android.view.DragEvent;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.R;
            
            
    public class SlidingActivityHelper {
                private Activity mActivity;
                private SlidingMenu mSlidingMenu;
                private View mViewAbove;
                private View mViewBehind;
                private boolean mBroadcasting = false;
                private boolean mOnPostCreateCalled = false;
                private boolean mEnableSlide = true;

        static  SlidingMenu access$000(SlidingActivityHelper x0) {
            return x0.mSlidingMenu;
                }

        public SlidingActivityHelper(Activity activity) {
            this.mActivity = activity;
                }

        public void onCreate(Bundle savedInstanceState) {
            this.mSlidingMenu = (SlidingMenu) LayoutInflater.from(this.mActivity).inflate(R.layout.slidingmenumain, (ViewGroup) null);
                }

        public void onPostCreate(Bundle savedInstanceState) {
                    boolean open;
                    boolean secondary;
            if (this.mViewBehind == null || this.mViewAbove == null) {
                throw new IllegalStateException("Both setBehindContentView must be called in onCreate in addition to setContentView.");
                    }
            this.mOnPostCreateCalled = true;
            this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1);
            if (savedInstanceState != null) {
                open = savedInstanceState.getBoolean("SlidingActivityHelper.open");
                secondary = savedInstanceState.getBoolean("SlidingActivityHelper.secondary");
                    } else {
                open = false;
                secondary = false;
                    }
            new Handler().post(new RunnableC12431(open, secondary));
                }

                
        class RunnableC12431 implements Runnable {
                    final  boolean val$open;
                    final  boolean val$secondary;

            RunnableC12431(boolean z, boolean z2) {
                        this.val$open = z;
                        this.val$secondary = z2;
                    }

                    @Override 
            public void run() {
                if (this.val$open) {
                    if (this.val$secondary) {
                        SlidingActivityHelper.access$000(SlidingActivityHelper.this).showSecondaryMenu(false);
                        return;
                            } else {
                        SlidingActivityHelper.access$000(SlidingActivityHelper.this).showMenu(false);
                        return;
                            }
                        }
                SlidingActivityHelper.access$000(SlidingActivityHelper.this).showContent(false);
                    }
                }

        public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled) {
            if (this.mOnPostCreateCalled) {
                throw new IllegalStateException("enableSlidingActionBar must be called in onCreate.");
                    }
            this.mEnableSlide = slidingActionBarEnabled;
                }

       public View findViewById(int id) {
                    View v;
           if (this.mSlidingMenu == null || (v = this.mSlidingMenu.findViewById(id)) == null) {
                        return null;
                    }
           return v;
                }

       public void onSaveInstanceState(Bundle outState) {
           outState.putBoolean("SlidingActivityHelper.open", this.mSlidingMenu.isMenuShowing());
           outState.putBoolean("SlidingActivityHelper.secondary", this.mSlidingMenu.isSecondaryMenuShowing());
                }

       public void registerAboveContentView(View v, ViewGroup.LayoutParams params) {
           if (!this.mBroadcasting) {
               this.mViewAbove = v;
                    }
                }

       public void setContentView(View v) {
           this.mBroadcasting = true;
           this.mActivity.setContentView(v);
                }

       public void setBehindContentView(View view, ViewGroup.LayoutParams layoutParams) {
           this.mViewBehind = view;
           this.mSlidingMenu.setMenu(this.mViewBehind);
                }

       public SlidingMenu getSlidingMenu() {
           return this.mSlidingMenu;
                }

       public void toggle() {
           this.mSlidingMenu.toggle();
                }

       public void showContent() {
           this.mSlidingMenu.showContent();
                }

       public void showMenu() {
           this.mSlidingMenu.showMenu();
                }

       public void showSecondaryMenu() {
           this.mSlidingMenu.showSecondaryMenu();
                }

       public boolean onKeyUp(int keyCode, KeyEvent event) {
           if (keyCode != 4 || !this.mSlidingMenu.isMenuShowing()) {
                        return false;
                    }
           showContent();
           return true;
                }

       public static class MenuStateHandler implements View.OnDragListener, SlidingMenu.OnClosedListener, SlidingMenu.OnCloseListener, SlidingMenu.OnOpenedListener, SlidingMenu.OnOpenListener, View.OnClickListener {
                    private final MenuStateListener mListener;

                    public interface MenuStateListener {
                        void onMenuClick(View view);

                        void onMenuClose();

                        void onMenuClosed();

                        boolean onMenuDrag(View view, DragEvent dragEvent);

                        void onMenuOpen();

                        void onMenuOpened();
                    }

           public MenuStateHandler(MenuStateListener listener) {
               this.mListener = listener;
                    }

                    @Override 
           public boolean onDrag(View v, DragEvent event) {
               return this.mListener.onMenuDrag(v, event);
                    }

                    @Override 
           public void onClosed() {
               this.mListener.onMenuClosed();
                    }

                    @Override 
           public void onClose() {
               this.mListener.onMenuClose();
                    }

                    @Override 
           public void onOpened() {
               this.mListener.onMenuOpened();
                    }

                    @Override 
           public void onOpen() {
               this.mListener.onMenuOpen();
                    }

                    @Override 
           public void onClick(View v) {
               this.mListener.onMenuClick(v);
                    }
                }

       public void setMenuStateListener(MenuStateHandler.MenuStateListener listener) {
           MenuStateHandler handler = new MenuStateHandler(listener);
           this.mSlidingMenu.setOnClosedListener(handler);
           this.mSlidingMenu.setOnCloseListener(handler);
           this.mSlidingMenu.setOnOpenedListener(handler);
           this.mSlidingMenu.setOnOpenListener(handler);
           this.mSlidingMenu.setOnClickListener(handler);
           this.mSlidingMenu.setOnDragListener(handler);
                }
            }
