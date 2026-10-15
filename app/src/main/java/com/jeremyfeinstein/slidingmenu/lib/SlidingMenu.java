            package com.jeremyfeinstein.slidingmenu.lib;

            import android.R;
            import android.annotation.SuppressLint;
            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Canvas;
            import android.graphics.Point;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.Display;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.WindowManager;
            import android.widget.FrameLayout;
            import android.widget.RelativeLayout;
            import java.lang.reflect.Method;
            
            
    public class SlidingMenu extends RelativeLayout {
                public static final int LEFT = 0;
                public static final int LEFT_RIGHT = 2;
                public static final int RIGHT = 1;
                public static final int SLIDING_CONTENT = 1;
                public static final int SLIDING_WINDOW = 0;
                private static final String TAG = SlidingMenu.class.getSimpleName();
                public static final int TOUCHMODE_FULLSCREEN = 1;
                public static final int TOUCHMODE_MARGIN = 0;
                public static final int TOUCHMODE_NONE = 2;
                private boolean mActionbarOverlay;
                private OnCloseListener mCloseListener;
                private OnOpenListener mOpenListener;
                private OnOpenListener mSecondaryOpenListner;
                private View mSlidingOverLay;
                private CustomViewAbove mViewAbove;
                private CustomViewBehind mViewBehind;

                public interface CanvasTransformer {
                    void transformCanvas(Canvas canvas, float f);
                }

                public interface OnCloseListener {
                    void onClose();
                }

                public interface OnClosedListener {
                    void onClosed();
                }

                public interface OnOpenListener {
                    void onOpen();
                }

                public interface OnOpenedListener {
                    void onOpened();
                }

        static  View access$000(SlidingMenu x0) {
            return x0.mSlidingOverLay;
                }

        static  OnOpenListener access$100(SlidingMenu x0) {
            return x0.mOpenListener;
                }

        static  OnCloseListener access$200(SlidingMenu x0) {
            return x0.mCloseListener;
                }

        static  OnOpenListener access$300(SlidingMenu x0) {
            return x0.mSecondaryOpenListner;
                }

        static  String access$500() {
            return TAG;
                }

        public View getViewBehind() {
            return this.mViewBehind;
                }

       public SlidingMenu(Context context) {
           this(context, (AttributeSet) null);
                }

       public SlidingMenu(Activity activity, int slideStyle) {
           this(activity, (AttributeSet) null);
           attachToActivity(activity, slideStyle);
                }

       public SlidingMenu(Context context, AttributeSet attrs) {
           this(context, attrs, 0);
                }

       public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
           super(context, attrs, defStyle);
            this.mActionbarOverlay = false;
           RelativeLayout.LayoutParams behindParams = new RelativeLayout.LayoutParams(-1, -1);
           this.mViewBehind = new CustomViewBehind(context);
           addView(this.mViewBehind, behindParams);
           RelativeLayout.LayoutParams aboveParams = new RelativeLayout.LayoutParams(-1, -1);
           this.mViewAbove = new CustomViewAbove(context);
           addView(this.mViewAbove, aboveParams);
           this.mViewAbove.setCustomViewBehind(this.mViewBehind);
           this.mViewBehind.setCustomViewAbove(this.mViewAbove);
           this.mViewAbove.setOnPageChangeListener(new C12441());
           TypedArray ta = context.obtainStyledAttributes(attrs, co.vine.android.R.styleable.SlidingMenu);
           int mode = ta.getInt(co.vine.android.R.styleable.SlidingMenu_mode, 0);
           setMode(mode);
           int viewAbove = ta.getResourceId(co.vine.android.R.styleable.SlidingMenu_viewAbove, -1);
           if (viewAbove != -1) {
               setContent(viewAbove);
                    } else {
               setContent(new FrameLayout(context));
                    }
           int viewBehind = ta.getResourceId(co.vine.android.R.styleable.SlidingMenu_viewBehind, -1);
           if (viewBehind != -1) {
               setMenu(viewBehind);
                    } else {
               setMenu(new FrameLayout(context));
                    }
           int touchModeAbove = ta.getInt(co.vine.android.R.styleable.SlidingMenu_touchModeAbove, 0);
           setTouchModeAbove(touchModeAbove);
           int touchModeBehind = ta.getInt(co.vine.android.R.styleable.SlidingMenu_touchModeBehind, 0);
           setTouchModeBehind(touchModeBehind);
           int offsetBehind = (int) ta.getDimension(co.vine.android.R.styleable.SlidingMenu_behindOffset, -1.0f);
           int widthBehind = (int) ta.getDimension(co.vine.android.R.styleable.SlidingMenu_behindWidth, -1.0f);
           if (offsetBehind != -1 && widthBehind != -1) {
               throw new IllegalStateException("Cannot set both behindOffset and behindWidth for a SlidingMenu");
                    }
           if (offsetBehind != -1) {
               setBehindOffset(offsetBehind);
                    } else if (widthBehind != -1) {
               setBehindWidth(widthBehind);
                    } else {
               setBehindOffset(0);
                    }
           float scrollOffsetBehind = ta.getFloat(co.vine.android.R.styleable.SlidingMenu_behindScrollScale, 0.33f);
           setBehindScrollScale(scrollOffsetBehind);
           int shadowRes = ta.getResourceId(co.vine.android.R.styleable.SlidingMenu_shadowDrawable, -1);
           if (shadowRes != -1) {
               setShadowDrawable(shadowRes);
                    }
           int shadowWidth = (int) ta.getDimension(co.vine.android.R.styleable.SlidingMenu_shadowWidth, 0.0f);
           setShadowWidth(shadowWidth);
           boolean fadeEnabled = ta.getBoolean(co.vine.android.R.styleable.SlidingMenu_fadeEnabled, true);
           setFadeEnabled(fadeEnabled);
           float fadeDeg = ta.getFloat(co.vine.android.R.styleable.SlidingMenu_fadeDegree, 0.33f);
           setFadeDegree(fadeDeg);
           boolean selectorEnabled = ta.getBoolean(co.vine.android.R.styleable.SlidingMenu_selectorEnabled, false);
           setSelectorEnabled(selectorEnabled);
           int selectorRes = ta.getResourceId(co.vine.android.R.styleable.SlidingMenu_selectorDrawable, -1);
           if (selectorRes != -1) {
               setSelectorDrawable(selectorRes);
                    }
           ta.recycle();
                }

                
       class C12441 implements CustomViewAbove.OnPageChangeListener {
                    public static final int POSITION_CLOSE = 1;
                    public static final int POSITION_OPEN = 0;
                    public static final int POSITION_SECONDARY_OPEN = 2;

           C12441() {
                    }

                    @Override 
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               if (SlidingMenu.access$000(SlidingMenu.this) != null) {
                   SlidingMenu.access$000(SlidingMenu.this).getBackground().setAlpha((int) ((0.0f - positionOffset) * 125.0f));
                        }
                    }

                    @Override 
           public void onPageSelected(int position) {
               if (position == 0 && SlidingMenu.access$100(SlidingMenu.this) != null) {
                   SlidingMenu.access$100(SlidingMenu.this).onOpen();
                   return;
                        }
               if (position == 1 && SlidingMenu.access$200(SlidingMenu.this) != null) {
                   SlidingMenu.access$200(SlidingMenu.this).onClose();
                        } else if (position == 2 && SlidingMenu.access$300(SlidingMenu.this) != null) {
                   SlidingMenu.access$300(SlidingMenu.this).onOpen();
                        }
                    }
                }

       public void attachToActivity(Activity activity, int slideStyle) {
           attachToActivity(activity, slideStyle, false);
                }

       public void attachToActivity(Activity activity, int slideStyle, boolean actionbarOverlay) {
           if (slideStyle != 0 && slideStyle != 1) {
               throw new IllegalArgumentException("slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT");
                    }
           if (getParent() != null) {
               throw new IllegalStateException("This SlidingMenu appears to already be attached");
                    }
           TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{R.attr.windowBackground});
           int background = a.getResourceId(0, 0);
           a.recycle();
                    switch (slideStyle) {
                        case 0:
                   this.mActionbarOverlay = false;
                   ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
                   ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
                   decorChild.setBackgroundResource(background);
                   decor.removeView(decorChild);
                   decor.addView(this);
                   setContent(decorChild);
                   return;
                        case 1:
                   this.mActionbarOverlay = actionbarOverlay;
                   ViewGroup contentParent = (ViewGroup) activity.findViewById(R.id.content);
                   View content = contentParent.getChildAt(0);
                   contentParent.removeView(content);
                   contentParent.addView(this);
                   setContent(content);
                   if (content.getBackground() == null) {
                       content.setBackgroundResource(background);
                       return;
                            }
                   return;
                        default:
                   return;
                    }
                }

       public void setContent(int res) {
           setContent(LayoutInflater.from(getContext()).inflate(res, (ViewGroup) null));
                }

       public void setContent(View view) {
           RelativeLayout r = new RelativeLayout(view.getContext());
           r.addView(view);
           View v = new View(view.getContext());
           ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
           v.setLayoutParams(params);
           v.setBackgroundColor(-16777216);
           v.getBackground().setAlpha(0);
           r.addView(v);
           this.mSlidingOverLay = v;
           this.mViewAbove.setContent(r);
           showContent();
                }

       public View getContent() {
           return this.mViewAbove.getContent();
                }

       public void setMenu(int res) {
           setMenu(LayoutInflater.from(getContext()).inflate(res, (ViewGroup) null));
                }

       public void setMenu(View v) {
           this.mViewBehind.setContent(v);
                }

       public View getMenu() {
           return this.mViewBehind.getContent();
                }

       public void setSecondaryMenu(int res) {
           setSecondaryMenu(LayoutInflater.from(getContext()).inflate(res, (ViewGroup) null));
                }

       public void setSecondaryMenu(View v) {
           this.mViewBehind.setSecondaryContent(v);
                }

       public View getSecondaryMenu() {
           return this.mViewBehind.getSecondaryContent();
                }

       public void setSlidingEnabled(boolean b) {
           this.mViewAbove.setSlidingEnabled(b);
                }

       public boolean isSlidingEnabled() {
           return this.mViewAbove.isSlidingEnabled();
                }

       public void setMode(int mode) {
           if (mode != 0 && mode != 1 && mode != 2) {
               throw new IllegalStateException("SlidingMenu mode must be LEFT, RIGHT, or LEFT_RIGHT");
                    }
           this.mViewBehind.setMode(mode);
                }

       public int getMode() {
           return this.mViewBehind.getMode();
                }

       public void setStatic(boolean b) {
           if (b) {
               setSlidingEnabled(false);
               this.mViewAbove.setCustomViewBehind(null);
               this.mViewAbove.setCurrentItem(1);
                    } else {
               this.mViewAbove.setCurrentItem(1);
               this.mViewAbove.setCustomViewBehind(this.mViewBehind);
               setSlidingEnabled(true);
                    }
                }

       public void showMenu() {
           showMenu(true);
                }

       public void showMenu(boolean animate) {
           this.mViewAbove.setCurrentItem(0, animate);
                }

       public void showSecondaryMenu() {
           showSecondaryMenu(true);
                }

       public void showSecondaryMenu(boolean animate) {
           this.mViewAbove.setCurrentItem(2, animate);
                }

       public void showContent() {
           showContent(true);
                }

       public void showContent(boolean animate) {
           this.mViewAbove.setCurrentItem(1, animate);
           if (this.mSlidingOverLay != null && !animate) {
               this.mSlidingOverLay.getBackground().setAlpha(0);
                    }
                }

       public void toggle() {
           toggle(true);
                }

       public void toggle(boolean animate) {
           if (isMenuShowing()) {
               showContent(animate);
                    } else {
               showMenu(animate);
                    }
                }

       public boolean isMenuShowing() {
                    return this.mViewAbove.getCurrentItem() == 0 || this.mViewAbove.getCurrentItem() == 2;
                }

       public boolean isSecondaryMenuShowing() {
                    return this.mViewAbove.getCurrentItem() == 2;
                }

       public int getBehindOffset() {
           return ((RelativeLayout.LayoutParams) this.mViewBehind.getLayoutParams()).rightMargin;
                }

       public void setBehindOffset(int i) {
           this.mViewBehind.setWidthOffset(i);
                }

       public void setBehindOffsetRes(int resID) {
           int i = (int) getContext().getResources().getDimension(resID);
           setBehindOffset(i);
                }

       public void setAboveOffset(int i) {
           this.mViewAbove.setAboveOffset(i);
                }

       public void setAboveOffsetRes(int resID) {
           int i = (int) getContext().getResources().getDimension(resID);
           setAboveOffset(i);
                }

       public void setBehindWidth(int i) {
                    int width;
           Display display = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
                    try {
               Class<?>[] parameterTypes = {Point.class};
               Point parameter = new Point();
               Method method = Display.class.getMethod("getSize", parameterTypes);
               method.invoke(display, parameter);
               width = parameter.x;
                    } catch (Exception e) {
               width = display.getWidth();
                    }
           setBehindOffset(width - i);
                }

       public void setBehindWidthRes(int res) {
           int i = (int) getContext().getResources().getDimension(res);
           setBehindWidth(i);
                }

       public float getBehindScrollScale() {
           return this.mViewBehind.getScrollScale();
                }

       public int getTouchmodeMarginThreshold() {
           return this.mViewBehind.getMarginThreshold();
                }

       public void setTouchmodeMarginThreshold(int touchmodeMarginThreshold) {
           this.mViewBehind.setMarginThreshold(touchmodeMarginThreshold);
                }

       public void setBehindScrollScale(float f) {
                    if (f < 0.0f && f > 1.0f) {
               throw new IllegalStateException("ScrollScale must be between 0 and 1");
                    }
           this.mViewBehind.setScrollScale(f);
                }

       public void setBehindCanvasTransformer(CanvasTransformer t) {
           this.mViewBehind.setCanvasTransformer(t);
                }

       public int getTouchModeAbove() {
           return this.mViewAbove.getTouchMode();
                }

       public void setTouchModeAbove(int i) {
           if (i != 1 && i != 0 && i != 2) {
               throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
                    }
           this.mViewAbove.setTouchMode(i);
                }

       public void setTouchModeBehind(int i) {
           if (i != 1 && i != 0 && i != 2) {
               throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
                    }
           this.mViewBehind.setTouchMode(i);
                }

       public void setShadowDrawable(int resId) {
           setShadowDrawable(getContext().getResources().getDrawable(resId));
                }

       public void setShadowDrawable(Drawable d) {
           this.mViewBehind.setShadowDrawable(d);
                }

       public void setSecondaryShadowDrawable(int resId) {
           setSecondaryShadowDrawable(getContext().getResources().getDrawable(resId));
                }

       public void setSecondaryShadowDrawable(Drawable d) {
           this.mViewBehind.setSecondaryShadowDrawable(d);
                }

       public void setShadowWidthRes(int resId) {
           setShadowWidth((int) getResources().getDimension(resId));
                }

       public void setShadowWidth(int pixels) {
           this.mViewBehind.setShadowWidth(pixels);
                }

       public void setFadeEnabled(boolean b) {
           this.mViewBehind.setFadeEnabled(b);
                }

       public void setFadeDegree(float f) {
           this.mViewBehind.setFadeDegree(f);
                }

       public void setSelectorEnabled(boolean b) {
           this.mViewBehind.setSelectorEnabled(true);
                }

       public void setSelectedView(View v) {
           this.mViewBehind.setSelectedView(v);
                }

       public void setSelectorDrawable(int res) {
           this.mViewBehind.setSelectorBitmap(BitmapFactory.decodeResource(getResources(), res));
                }

       public void setSelectorBitmap(Bitmap b) {
           this.mViewBehind.setSelectorBitmap(b);
                }

       public void addIgnoredView(View v) {
           this.mViewAbove.addIgnoredView(v);
                }

       public void removeIgnoredView(View v) {
           this.mViewAbove.removeIgnoredView(v);
                }

       public void clearIgnoredViews() {
           this.mViewAbove.clearIgnoredViews();
                }

       public void setOnOpenListener(OnOpenListener listener) {
           this.mOpenListener = listener;
                }

       public CustomViewAbove getViewAbove() {
           return this.mViewAbove;
                }

       public void setSecondaryOnOpenListner(OnOpenListener listener) {
           this.mSecondaryOpenListner = listener;
                }

       public void setOnCloseListener(OnCloseListener listener) {
           this.mCloseListener = listener;
                }

       public void setOnOpenedListener(OnOpenedListener listener) {
           this.mViewAbove.setOnOpenedListener(listener);
                }

       public void setOnClosedListener(OnClosedListener listener) {
           this.mViewAbove.setOnClosedListener(listener);
                }

       public static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C12461();
                    private final int mItem;

            SavedState(Parcel x0, C12441 x1) {
               this(x0);
                    }

           public SavedState(Parcelable superState, int item) {
               super(superState);
               this.mItem = item;
                    }

           private SavedState(Parcel in) {
               super(in);
               this.mItem = in.readInt();
                    }

           public int getItem() {
               return this.mItem;
                    }

                    @Override 
           public void writeToParcel(Parcel out, int flags) {
               super.writeToParcel(out, flags);
               out.writeInt(this.mItem);
                    }

                    
           static class C12461 implements Parcelable.Creator<SavedState> {
               C12461() {
                        }



                        
                        @Override 
               public SavedState createFromParcel(Parcel in) {
                   return new SavedState(in, (C12441) null);
                        }

                        
                        @Override 
               public SavedState[] newArray(int size) {
                  return new SavedState[size];
                        }
                    }
                }

                @Override 
      protected Parcelable onSaveInstanceState() {
          Parcelable superState = super.onSaveInstanceState();
          SavedState ss = new SavedState(superState, this.mViewAbove.getCurrentItem());
          return ss;
                }

                @Override 
      protected void onRestoreInstanceState(Parcelable state) {
          SavedState ss = (SavedState) state;
          super.onRestoreInstanceState(ss.getSuperState());
          this.mViewAbove.setCurrentItem(ss.getItem());
                }

                @Override 
                @SuppressLint({"NewApi"})
      protected boolean fitSystemWindows(Rect insets) {
          int leftPadding = insets.left;
          int rightPadding = insets.right;
          int topPadding = insets.top;
          int bottomPadding = insets.bottom;
          if (!this.mActionbarOverlay) {
              Log.v(TAG, "setting padding!");
              setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
              return true;
                    }
          return true;
                }

                @TargetApi(11)
      public void manageLayers(float percentOpen) {
          if (Build.VERSION.SDK_INT >= 11) {
                        boolean layer = percentOpen > 0.0f && percentOpen < 1.0f;
              int layerType = layer ? 2 : 0;
              if (layerType != getContent().getLayerType()) {
                  getHandler().post(new RunnableC12452(layerType));
                        }
                    }
                }

                
      class RunnableC12452 implements Runnable {
                    final  int val$layerType;

          RunnableC12452(int i) {
                        this.val$layerType = i;
                    }

                    @Override 
          public void run() {
              Log.v(SlidingMenu.access$500(), "changing layerType. hardware? " + (this.val$layerType == 2));
              SlidingMenu.this.getContent().setLayerType(this.val$layerType, null);
              SlidingMenu.this.getMenu().setLayerType(this.val$layerType, null);
              if (SlidingMenu.this.getSecondaryMenu() != null) {
                  SlidingMenu.this.getSecondaryMenu().setLayerType(this.val$layerType, null);
                        }
                    }
                }
            }
