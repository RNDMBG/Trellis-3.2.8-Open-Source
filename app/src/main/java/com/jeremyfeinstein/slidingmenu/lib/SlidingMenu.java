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
            import co.vine.android.C0257R;
            import java.lang.reflect.Method;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class SlidingMenu extends RelativeLayout {
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

/* 32 */        static /* synthetic */ View access$000(SlidingMenu x0) {
/* 33 */            return x0.mSlidingOverLay;
                }

/* 32 */        static /* synthetic */ OnOpenListener access$100(SlidingMenu x0) {
/* 33 */            return x0.mOpenListener;
                }

/* 32 */        static /* synthetic */ OnCloseListener access$200(SlidingMenu x0) {
/* 33 */            return x0.mCloseListener;
                }

/* 32 */        static /* synthetic */ OnOpenListener access$300(SlidingMenu x0) {
/* 33 */            return x0.mSecondaryOpenListner;
                }

/* 32 */        static /* synthetic */ String access$500() {
/* 33 */            return TAG;
                }

/* 84 */        public View getViewBehind() {
/* 85 */            return this.mViewBehind;
                }

/* 181 */       public SlidingMenu(Context context) {
/* 182 */           this(context, (AttributeSet) null);
                }

/* 191 */       public SlidingMenu(Activity activity, int slideStyle) {
/* 192 */           this(activity, (AttributeSet) null);
/* 193 */           attachToActivity(activity, slideStyle);
                }

/* 202 */       public SlidingMenu(Context context, AttributeSet attrs) {
/* 203 */           this(context, attrs, 0);
                }

/* 213 */       public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
/* 214 */           super(context, attrs, defStyle);
/* 39 */            this.mActionbarOverlay = false;
/* 216 */           RelativeLayout.LayoutParams behindParams = new RelativeLayout.LayoutParams(-1, -1);
/* 217 */           this.mViewBehind = new CustomViewBehind(context);
/* 218 */           addView(this.mViewBehind, behindParams);
/* 219 */           RelativeLayout.LayoutParams aboveParams = new RelativeLayout.LayoutParams(-1, -1);
/* 220 */           this.mViewAbove = new CustomViewAbove(context);
/* 221 */           addView(this.mViewAbove, aboveParams);
/* 223 */           this.mViewAbove.setCustomViewBehind(this.mViewBehind);
/* 224 */           this.mViewBehind.setCustomViewAbove(this.mViewAbove);
/* 225 */           this.mViewAbove.setOnPageChangeListener(new C12441());
/* 251 */           TypedArray ta = context.obtainStyledAttributes(attrs, C0257R.styleable.SlidingMenu);
/* 253 */           int mode = ta.getInt(C0257R.styleable.SlidingMenu_mode, 0);
/* 254 */           setMode(mode);
/* 255 */           int viewAbove = ta.getResourceId(C0257R.styleable.SlidingMenu_viewAbove, -1);
/* 256 */           if (viewAbove != -1) {
/* 257 */               setContent(viewAbove);
                    } else {
/* 259 */               setContent(new FrameLayout(context));
                    }
/* 261 */           int viewBehind = ta.getResourceId(C0257R.styleable.SlidingMenu_viewBehind, -1);
/* 262 */           if (viewBehind != -1) {
/* 263 */               setMenu(viewBehind);
                    } else {
/* 265 */               setMenu(new FrameLayout(context));
                    }
/* 267 */           int touchModeAbove = ta.getInt(C0257R.styleable.SlidingMenu_touchModeAbove, 0);
/* 268 */           setTouchModeAbove(touchModeAbove);
/* 269 */           int touchModeBehind = ta.getInt(C0257R.styleable.SlidingMenu_touchModeBehind, 0);
/* 270 */           setTouchModeBehind(touchModeBehind);
/* 272 */           int offsetBehind = (int) ta.getDimension(C0257R.styleable.SlidingMenu_behindOffset, -1.0f);
/* 273 */           int widthBehind = (int) ta.getDimension(C0257R.styleable.SlidingMenu_behindWidth, -1.0f);
/* 274 */           if (offsetBehind != -1 && widthBehind != -1) {
/* 275 */               throw new IllegalStateException("Cannot set both behindOffset and behindWidth for a SlidingMenu");
                    }
/* 276 */           if (offsetBehind != -1) {
/* 277 */               setBehindOffset(offsetBehind);
                    } else if (widthBehind != -1) {
/* 279 */               setBehindWidth(widthBehind);
                    } else {
/* 281 */               setBehindOffset(0);
                    }
/* 282 */           float scrollOffsetBehind = ta.getFloat(C0257R.styleable.SlidingMenu_behindScrollScale, 0.33f);
/* 283 */           setBehindScrollScale(scrollOffsetBehind);
/* 284 */           int shadowRes = ta.getResourceId(C0257R.styleable.SlidingMenu_shadowDrawable, -1);
/* 285 */           if (shadowRes != -1) {
/* 286 */               setShadowDrawable(shadowRes);
                    }
/* 288 */           int shadowWidth = (int) ta.getDimension(C0257R.styleable.SlidingMenu_shadowWidth, 0.0f);
/* 289 */           setShadowWidth(shadowWidth);
/* 290 */           boolean fadeEnabled = ta.getBoolean(C0257R.styleable.SlidingMenu_fadeEnabled, true);
/* 291 */           setFadeEnabled(fadeEnabled);
/* 292 */           float fadeDeg = ta.getFloat(C0257R.styleable.SlidingMenu_fadeDegree, 0.33f);
/* 293 */           setFadeDegree(fadeDeg);
/* 294 */           boolean selectorEnabled = ta.getBoolean(C0257R.styleable.SlidingMenu_selectorEnabled, false);
/* 295 */           setSelectorEnabled(selectorEnabled);
/* 296 */           int selectorRes = ta.getResourceId(C0257R.styleable.SlidingMenu_selectorDrawable, -1);
/* 297 */           if (selectorRes != -1) {
/* 298 */               setSelectorDrawable(selectorRes);
                    }
/* 299 */           ta.recycle();
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.SlidingMenu$1 */
/* 223 */       class C12441 implements CustomViewAbove.OnPageChangeListener {
                    public static final int POSITION_CLOSE = 1;
                    public static final int POSITION_OPEN = 0;
                    public static final int POSITION_SECONDARY_OPEN = 2;

/* 224 */           C12441() {
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove.OnPageChangeListener
/* 232 */           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
/* 233 */               if (SlidingMenu.access$000(SlidingMenu.this) != null) {
/* 234 */                   SlidingMenu.access$000(SlidingMenu.this).getBackground().setAlpha((int) ((0.0f - positionOffset) * 125.0f));
                        }
                    }

                    @Override // com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove.OnPageChangeListener
/* 239 */           public void onPageSelected(int position) {
/* 240 */               if (position == 0 && SlidingMenu.access$100(SlidingMenu.this) != null) {
/* 241 */                   SlidingMenu.access$100(SlidingMenu.this).onOpen();
/* 247 */                   return;
                        }
/* 242 */               if (position == 1 && SlidingMenu.access$200(SlidingMenu.this) != null) {
/* 243 */                   SlidingMenu.access$200(SlidingMenu.this).onClose();
                        } else if (position == 2 && SlidingMenu.access$300(SlidingMenu.this) != null) {
/* 245 */                   SlidingMenu.access$300(SlidingMenu.this).onOpen();
                        }
                    }
                }

/* 308 */       public void attachToActivity(Activity activity, int slideStyle) {
/* 309 */           attachToActivity(activity, slideStyle, false);
                }

/* 319 */       public void attachToActivity(Activity activity, int slideStyle, boolean actionbarOverlay) {
/* 320 */           if (slideStyle != 0 && slideStyle != 1) {
/* 321 */               throw new IllegalArgumentException("slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT");
                    }
/* 323 */           if (getParent() != null) {
/* 324 */               throw new IllegalStateException("This SlidingMenu appears to already be attached");
                    }
/* 327 */           TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{R.attr.windowBackground});
/* 328 */           int background = a.getResourceId(0, 0);
/* 329 */           a.recycle();
                    switch (slideStyle) {
                        case 0:
/* 333 */                   this.mActionbarOverlay = false;
/* 334 */                   ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
/* 335 */                   ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
/* 337 */                   decorChild.setBackgroundResource(background);
/* 338 */                   decor.removeView(decorChild);
/* 339 */                   decor.addView(this);
/* 340 */                   setContent(decorChild);
/* 355 */                   return;
                        case 1:
/* 343 */                   this.mActionbarOverlay = actionbarOverlay;
/* 345 */                   ViewGroup contentParent = (ViewGroup) activity.findViewById(R.id.content);
/* 346 */                   View content = contentParent.getChildAt(0);
/* 347 */                   contentParent.removeView(content);
/* 348 */                   contentParent.addView(this);
/* 349 */                   setContent(content);
/* 351 */                   if (content.getBackground() == null) {
/* 352 */                       content.setBackgroundResource(background);
/* 355 */                       return;
                            }
/* 355 */                   return;
                        default:
/* 355 */                   return;
                    }
                }

/* 363 */       public void setContent(int res) {
/* 364 */           setContent(LayoutInflater.from(getContext()).inflate(res, (ViewGroup) null));
                }

/* 374 */       public void setContent(View view) {
/* 375 */           RelativeLayout r = new RelativeLayout(view.getContext());
/* 376 */           r.addView(view);
/* 377 */           View v = new View(view.getContext());
/* 378 */           ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
/* 379 */           v.setLayoutParams(params);
/* 380 */           v.setBackgroundColor(-16777216);
/* 381 */           v.getBackground().setAlpha(0);
/* 382 */           r.addView(v);
/* 383 */           this.mSlidingOverLay = v;
/* 384 */           this.mViewAbove.setContent(r);
/* 385 */           showContent();
                }

/* 393 */       public View getContent() {
/* 394 */           return this.mViewAbove.getContent();
                }

/* 403 */       public void setMenu(int res) {
/* 404 */           setMenu(LayoutInflater.from(getContext()).inflate(res, (ViewGroup) null));
                }

/* 412 */       public void setMenu(View v) {
/* 413 */           this.mViewBehind.setContent(v);
                }

/* 421 */       public View getMenu() {
/* 422 */           return this.mViewBehind.getContent();
                }

/* 431 */       public void setSecondaryMenu(int res) {
/* 432 */           setSecondaryMenu(LayoutInflater.from(getContext()).inflate(res, (ViewGroup) null));
                }

/* 440 */       public void setSecondaryMenu(View v) {
/* 441 */           this.mViewBehind.setSecondaryContent(v);
                }

/* 450 */       public View getSecondaryMenu() {
/* 451 */           return this.mViewBehind.getSecondaryContent();
                }

/* 460 */       public void setSlidingEnabled(boolean b) {
/* 461 */           this.mViewAbove.setSlidingEnabled(b);
                }

/* 469 */       public boolean isSlidingEnabled() {
/* 470 */           return this.mViewAbove.isSlidingEnabled();
                }

/* 478 */       public void setMode(int mode) {
/* 479 */           if (mode != 0 && mode != 1 && mode != 2) {
/* 480 */               throw new IllegalStateException("SlidingMenu mode must be LEFT, RIGHT, or LEFT_RIGHT");
                    }
/* 482 */           this.mViewBehind.setMode(mode);
                }

/* 490 */       public int getMode() {
/* 491 */           return this.mViewBehind.getMode();
                }

/* 499 */       public void setStatic(boolean b) {
/* 500 */           if (b) {
/* 501 */               setSlidingEnabled(false);
/* 502 */               this.mViewAbove.setCustomViewBehind(null);
/* 503 */               this.mViewAbove.setCurrentItem(1);
                    } else {
/* 506 */               this.mViewAbove.setCurrentItem(1);
/* 508 */               this.mViewAbove.setCustomViewBehind(this.mViewBehind);
/* 509 */               setSlidingEnabled(true);
                    }
                }

/* 516 */       public void showMenu() {
/* 517 */           showMenu(true);
                }

/* 525 */       public void showMenu(boolean animate) {
/* 526 */           this.mViewAbove.setCurrentItem(0, animate);
                }

/* 533 */       public void showSecondaryMenu() {
/* 534 */           showSecondaryMenu(true);
                }

/* 543 */       public void showSecondaryMenu(boolean animate) {
/* 544 */           this.mViewAbove.setCurrentItem(2, animate);
                }

/* 550 */       public void showContent() {
/* 551 */           showContent(true);
                }

/* 559 */       public void showContent(boolean animate) {
/* 560 */           this.mViewAbove.setCurrentItem(1, animate);
/* 561 */           if (this.mSlidingOverLay != null && !animate) {
/* 562 */               this.mSlidingOverLay.getBackground().setAlpha(0);
                    }
                }

/* 569 */       public void toggle() {
/* 570 */           toggle(true);
                }

/* 578 */       public void toggle(boolean animate) {
/* 579 */           if (isMenuShowing()) {
/* 580 */               showContent(animate);
                    } else {
/* 582 */               showMenu(animate);
                    }
                }

/* 591 */       public boolean isMenuShowing() {
                    return this.mViewAbove.getCurrentItem() == 0 || this.mViewAbove.getCurrentItem() == 2;
                }

/* 600 */       public boolean isSecondaryMenuShowing() {
                    return this.mViewAbove.getCurrentItem() == 2;
                }

/* 609 */       public int getBehindOffset() {
/* 610 */           return ((RelativeLayout.LayoutParams) this.mViewBehind.getLayoutParams()).rightMargin;
                }

/* 623 */       public void setBehindOffset(int i) {
/* 624 */           this.mViewBehind.setWidthOffset(i);
                }

/* 633 */       public void setBehindOffsetRes(int resID) {
/* 634 */           int i = (int) getContext().getResources().getDimension(resID);
/* 635 */           setBehindOffset(i);
                }

/* 643 */       public void setAboveOffset(int i) {
/* 644 */           this.mViewAbove.setAboveOffset(i);
                }

/* 652 */       public void setAboveOffsetRes(int resID) {
/* 653 */           int i = (int) getContext().getResources().getDimension(resID);
/* 654 */           setAboveOffset(i);
                }

/* 664 */       public void setBehindWidth(int i) {
                    int width;
/* 665 */           Display display = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
                    try {
/* 669 */               Class<?>[] parameterTypes = {Point.class};
/* 670 */               Point parameter = new Point();
/* 668 */               Method method = Display.class.getMethod("getSize", parameterTypes);
/* 672 */               method.invoke(display, parameter);
/* 673 */               width = parameter.x;
                    } catch (Exception e) {
/* 675 */               width = display.getWidth();
                    }
/* 677 */           setBehindOffset(width - i);
                }

/* 686 */       public void setBehindWidthRes(int res) {
/* 687 */           int i = (int) getContext().getResources().getDimension(res);
/* 688 */           setBehindWidth(i);
                }

/* 696 */       public float getBehindScrollScale() {
/* 697 */           return this.mViewBehind.getScrollScale();
                }

/* 705 */       public int getTouchmodeMarginThreshold() {
/* 706 */           return this.mViewBehind.getMarginThreshold();
                }

/* 714 */       public void setTouchmodeMarginThreshold(int touchmodeMarginThreshold) {
/* 715 */           this.mViewBehind.setMarginThreshold(touchmodeMarginThreshold);
                }

/* 724 */       public void setBehindScrollScale(float f) {
                    if (f < 0.0f && f > 1.0f) {
/* 726 */               throw new IllegalStateException("ScrollScale must be between 0 and 1");
                    }
/* 727 */           this.mViewBehind.setScrollScale(f);
                }

/* 735 */       public void setBehindCanvasTransformer(CanvasTransformer t) {
/* 736 */           this.mViewBehind.setCanvasTransformer(t);
                }

/* 744 */       public int getTouchModeAbove() {
/* 745 */           return this.mViewAbove.getTouchMode();
                }

/* 755 */       public void setTouchModeAbove(int i) {
/* 756 */           if (i != 1 && i != 0 && i != 2) {
/* 758 */               throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
                    }
/* 761 */           this.mViewAbove.setTouchMode(i);
                }

/* 771 */       public void setTouchModeBehind(int i) {
/* 772 */           if (i != 1 && i != 0 && i != 2) {
/* 774 */               throw new IllegalStateException("TouchMode must be set to eitherTOUCHMODE_FULLSCREEN or TOUCHMODE_MARGIN or TOUCHMODE_NONE.");
                    }
/* 777 */           this.mViewBehind.setTouchMode(i);
                }

/* 785 */       public void setShadowDrawable(int resId) {
/* 786 */           setShadowDrawable(getContext().getResources().getDrawable(resId));
                }

/* 794 */       public void setShadowDrawable(Drawable d) {
/* 795 */           this.mViewBehind.setShadowDrawable(d);
                }

/* 803 */       public void setSecondaryShadowDrawable(int resId) {
/* 804 */           setSecondaryShadowDrawable(getContext().getResources().getDrawable(resId));
                }

/* 812 */       public void setSecondaryShadowDrawable(Drawable d) {
/* 813 */           this.mViewBehind.setSecondaryShadowDrawable(d);
                }

/* 821 */       public void setShadowWidthRes(int resId) {
/* 822 */           setShadowWidth((int) getResources().getDimension(resId));
                }

/* 830 */       public void setShadowWidth(int pixels) {
/* 831 */           this.mViewBehind.setShadowWidth(pixels);
                }

/* 839 */       public void setFadeEnabled(boolean b) {
/* 840 */           this.mViewBehind.setFadeEnabled(b);
                }

/* 849 */       public void setFadeDegree(float f) {
/* 850 */           this.mViewBehind.setFadeDegree(f);
                }

/* 858 */       public void setSelectorEnabled(boolean b) {
/* 859 */           this.mViewBehind.setSelectorEnabled(true);
                }

/* 867 */       public void setSelectedView(View v) {
/* 868 */           this.mViewBehind.setSelectedView(v);
                }

/* 876 */       public void setSelectorDrawable(int res) {
/* 877 */           this.mViewBehind.setSelectorBitmap(BitmapFactory.decodeResource(getResources(), res));
                }

/* 885 */       public void setSelectorBitmap(Bitmap b) {
/* 886 */           this.mViewBehind.setSelectorBitmap(b);
                }

/* 894 */       public void addIgnoredView(View v) {
/* 895 */           this.mViewAbove.addIgnoredView(v);
                }

/* 903 */       public void removeIgnoredView(View v) {
/* 904 */           this.mViewAbove.removeIgnoredView(v);
                }

/* 910 */       public void clearIgnoredViews() {
/* 911 */           this.mViewAbove.clearIgnoredViews();
                }

/* 920 */       public void setOnOpenListener(OnOpenListener listener) {
/* 921 */           this.mOpenListener = listener;
                }

/* 924 */       public CustomViewAbove getViewAbove() {
/* 925 */           return this.mViewAbove;
                }

/* 935 */       public void setSecondaryOnOpenListner(OnOpenListener listener) {
/* 936 */           this.mSecondaryOpenListner = listener;
                }

/* 945 */       public void setOnCloseListener(OnCloseListener listener) {
/* 946 */           this.mCloseListener = listener;
                }

/* 954 */       public void setOnOpenedListener(OnOpenedListener listener) {
/* 955 */           this.mViewAbove.setOnOpenedListener(listener);
                }

/* 963 */       public void setOnClosedListener(OnClosedListener listener) {
/* 964 */           this.mViewAbove.setOnClosedListener(listener);
                }

/* 965 */       public static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C12461();
                    private final int mItem;

/* 966 */           /* synthetic */ SavedState(Parcel x0, C12441 x1) {
/* 967 */               this(x0);
                    }

/* 971 */           public SavedState(Parcelable superState, int item) {
/* 972 */               super(superState);
/* 973 */               this.mItem = item;
                    }

/* 976 */           private SavedState(Parcel in) {
/* 977 */               super(in);
/* 978 */               this.mItem = in.readInt();
                    }

/* 981 */           public int getItem() {
/* 982 */               return this.mItem;
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 988 */           public void writeToParcel(Parcel out, int flags) {
/* 989 */               super.writeToParcel(out, flags);
/* 990 */               out.writeInt(this.mItem);
                    }

                    /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.SlidingMenu$SavedState$1 */
/* 992 */           static class C12461 implements Parcelable.Creator<SavedState> {
/* 993 */               C12461() {
                        }



                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 995 */               public SavedState createFromParcel(Parcel in) {
/* 996 */                   return new SavedState(in, (C12441) null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 999 */               public SavedState[] newArray(int size) {
/* 1000 */                  return new SavedState[size];
                        }
                    }
                }

                @Override // android.view.View
/* 1010 */      protected Parcelable onSaveInstanceState() {
/* 1011 */          Parcelable superState = super.onSaveInstanceState();
/* 1012 */          SavedState ss = new SavedState(superState, this.mViewAbove.getCurrentItem());
/* 1013 */          return ss;
                }

                @Override // android.view.View
/* 1020 */      protected void onRestoreInstanceState(Parcelable state) {
/* 1021 */          SavedState ss = (SavedState) state;
/* 1022 */          super.onRestoreInstanceState(ss.getSuperState());
/* 1023 */          this.mViewAbove.setCurrentItem(ss.getItem());
                }

                @Override // android.view.View
                @SuppressLint({"NewApi"})
/* 1031 */      protected boolean fitSystemWindows(Rect insets) {
/* 1032 */          int leftPadding = insets.left;
/* 1033 */          int rightPadding = insets.right;
/* 1034 */          int topPadding = insets.top;
/* 1035 */          int bottomPadding = insets.bottom;
/* 1036 */          if (!this.mActionbarOverlay) {
/* 1037 */              Log.v(TAG, "setting padding!");
/* 1038 */              setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
/* 1040 */              return true;
                    }
/* 1040 */          return true;
                }

                @TargetApi(11)
/* 1044 */      public void manageLayers(float percentOpen) {
/* 1045 */          if (Build.VERSION.SDK_INT >= 11) {
                        boolean layer = percentOpen > 0.0f && percentOpen < 1.0f;
/* 1048 */              int layerType = layer ? 2 : 0;
/* 1050 */              if (layerType != getContent().getLayerType()) {
/* 1051 */                  getHandler().post(new RunnableC12452(layerType));
                        }
                    }
                }

                /* JADX INFO: renamed from: com.jeremyfeinstein.slidingmenu.lib.SlidingMenu$2 */
/* 1049 */      class RunnableC12452 implements Runnable {
                    final /* synthetic */ int val$layerType;

/* 1050 */          RunnableC12452(int i) {
                        this.val$layerType = i;
                    }

                    @Override // java.lang.Runnable
/* 1052 */          public void run() {
/* 1053 */              Log.v(SlidingMenu.access$500(), "changing layerType. hardware? " + (this.val$layerType == 2));
/* 1054 */              SlidingMenu.this.getContent().setLayerType(this.val$layerType, null);
/* 1055 */              SlidingMenu.this.getMenu().setLayerType(this.val$layerType, null);
/* 1056 */              if (SlidingMenu.this.getSecondaryMenu() != null) {
/* 1057 */                  SlidingMenu.this.getSecondaryMenu().setLayerType(this.val$layerType, null);
                        }
                    }
                }
            }
