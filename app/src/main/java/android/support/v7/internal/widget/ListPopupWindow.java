            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.database.DataSetObserver;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.Handler;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.widget.AbsListView;
            import android.widget.AdapterView;
            import android.widget.LinearLayout;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.PopupWindow;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class ListPopupWindow {
                private static final boolean DEBUG = false;
                private static final int EXPAND_LIST_TIMEOUT = 250;
                public static final int FILL_PARENT = -1;
                public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
                public static final int INPUT_METHOD_NEEDED = 1;
                public static final int INPUT_METHOD_NOT_NEEDED = 2;
                public static final int POSITION_PROMPT_ABOVE = 0;
                public static final int POSITION_PROMPT_BELOW = 1;
                private static final String TAG = "ListPopupWindow";
                public static final int WRAP_CONTENT = -2;
                private ListAdapter mAdapter;
                private Context mContext;
                private boolean mDropDownAlwaysVisible;
                private View mDropDownAnchorView;
                private int mDropDownHeight;
                private int mDropDownHorizontalOffset;
                private DropDownListView mDropDownList;
                private Drawable mDropDownListHighlight;
                private int mDropDownVerticalOffset;
                private boolean mDropDownVerticalOffsetSet;
                private int mDropDownWidth;
                private boolean mForceIgnoreOutsideTouch;
                private Handler mHandler;
                private final ListSelectorHider mHideSelector;
                private AdapterView.OnItemClickListener mItemClickListener;
                private AdapterView.OnItemSelectedListener mItemSelectedListener;
                private int mLayoutDirection;
                int mListItemExpandMaximum;
                private boolean mModal;
                private DataSetObserver mObserver;
                private PopupWindow mPopup;
                private int mPromptPosition;
                private View mPromptView;
                private final ResizePopupRunnable mResizePopupRunnable;
                private final PopupScrollListener mScrollListener;
                private Runnable mShowDropDownRunnable;
                private Rect mTempRect;
                private final PopupTouchInterceptor mTouchInterceptor;

/* 54 */        static /* synthetic */ Handler access$1000(ListPopupWindow x0) {
/* 55 */            return x0.mHandler;
                }

/* 54 */        static /* synthetic */ DropDownListView access$700(ListPopupWindow x0) {
/* 55 */            return x0.mDropDownList;
                }

/* 54 */        static /* synthetic */ PopupWindow access$800(ListPopupWindow x0) {
/* 55 */            return x0.mPopup;
                }

/* 54 */        static /* synthetic */ ResizePopupRunnable access$900(ListPopupWindow x0) {
/* 55 */            return x0.mResizePopupRunnable;
                }

/* 166 */       public ListPopupWindow(Context context) {
/* 167 */           this(context, null, C0084R.attr.listPopupWindowStyle);
                }

/* 177 */       public ListPopupWindow(Context context, AttributeSet attrs) {
/* 178 */           this(context, attrs, C0084R.attr.listPopupWindowStyle);
                }

/* 188 */       public ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
/* 71 */            this.mDropDownHeight = -2;
/* 72 */            this.mDropDownWidth = -2;
/* 77 */            this.mDropDownAlwaysVisible = false;
/* 78 */            this.mForceIgnoreOutsideTouch = false;
/* 79 */            this.mListItemExpandMaximum = Integer.MAX_VALUE;
/* 82 */            this.mPromptPosition = 0;
/* 93 */            this.mResizePopupRunnable = new ResizePopupRunnable(this, null);
/* 94 */            this.mTouchInterceptor = new PopupTouchInterceptor(this, null);
/* 95 */            this.mScrollListener = new PopupScrollListener(this, null);
/* 96 */            this.mHideSelector = new ListSelectorHider(this, null);
/* 99 */            this.mHandler = new Handler();
/* 101 */           this.mTempRect = new Rect();
/* 190 */           this.mContext = context;
/* 191 */           this.mPopup = new PopupWindow(context, attrs, defStyleAttr);
/* 192 */           this.mPopup.setInputMethodMode(1);
/* 194 */           Locale locale = this.mContext.getResources().getConfiguration().locale;
                }

/* 204 */       public void setAdapter(ListAdapter adapter) {
/* 205 */           if (this.mObserver == null) {
/* 206 */               this.mObserver = new PopupDataSetObserver(this, null);
                    } else if (this.mAdapter != null) {
/* 208 */               this.mAdapter.unregisterDataSetObserver(this.mObserver);
                    }
/* 210 */           this.mAdapter = adapter;
/* 211 */           if (this.mAdapter != null) {
/* 212 */               adapter.registerDataSetObserver(this.mObserver);
                    }
/* 215 */           if (this.mDropDownList != null) {
/* 216 */               this.mDropDownList.setAdapter(this.mAdapter);
                    }
                }

/* 228 */       public void setPromptPosition(int position) {
/* 229 */           this.mPromptPosition = position;
                }

/* 237 */       public int getPromptPosition() {
/* 238 */           return this.mPromptPosition;
                }

/* 249 */       public void setModal(boolean modal) {
/* 250 */           this.mModal = true;
/* 251 */           this.mPopup.setFocusable(modal);
                }

/* 259 */       public boolean isModal() {
/* 260 */           return this.mModal;
                }

/* 270 */       public void setForceIgnoreOutsideTouch(boolean forceIgnoreOutsideTouch) {
/* 271 */           this.mForceIgnoreOutsideTouch = forceIgnoreOutsideTouch;
                }

/* 284 */       public void setDropDownAlwaysVisible(boolean dropDownAlwaysVisible) {
/* 285 */           this.mDropDownAlwaysVisible = dropDownAlwaysVisible;
                }

/* 292 */       public boolean isDropDownAlwaysVisible() {
/* 293 */           return this.mDropDownAlwaysVisible;
                }

/* 304 */       public void setSoftInputMode(int mode) {
/* 305 */           this.mPopup.setSoftInputMode(mode);
                }

/* 314 */       public int getSoftInputMode() {
/* 315 */           return this.mPopup.getSoftInputMode();
                }

/* 323 */       public void setListSelector(Drawable selector) {
/* 324 */           this.mDropDownListHighlight = selector;
                }

/* 330 */       public Drawable getBackground() {
/* 331 */           return this.mPopup.getBackground();
                }

/* 339 */       public void setBackgroundDrawable(Drawable d) {
/* 340 */           this.mPopup.setBackgroundDrawable(d);
                }

/* 348 */       public void setAnimationStyle(int animationStyle) {
/* 349 */           this.mPopup.setAnimationStyle(animationStyle);
                }

/* 357 */       public int getAnimationStyle() {
/* 358 */           return this.mPopup.getAnimationStyle();
                }

/* 366 */       public View getAnchorView() {
/* 367 */           return this.mDropDownAnchorView;
                }

/* 376 */       public void setAnchorView(View anchor) {
/* 377 */           this.mDropDownAnchorView = anchor;
                }

/* 383 */       public int getHorizontalOffset() {
/* 384 */           return this.mDropDownHorizontalOffset;
                }

/* 392 */       public void setHorizontalOffset(int offset) {
/* 393 */           this.mDropDownHorizontalOffset = offset;
                }

/* 399 */       public int getVerticalOffset() {
/* 400 */           if (this.mDropDownVerticalOffsetSet) {
                        return this.mDropDownVerticalOffset;
                    }
/* 401 */           return 0;
                }

/* 411 */       public void setVerticalOffset(int offset) {
/* 412 */           this.mDropDownVerticalOffset = offset;
/* 413 */           this.mDropDownVerticalOffsetSet = true;
                }

/* 419 */       public int getWidth() {
/* 420 */           return this.mDropDownWidth;
                }

/* 429 */       public void setWidth(int width) {
/* 430 */           this.mDropDownWidth = width;
                }

/* 439 */       public void setContentWidth(int width) {
/* 440 */           Drawable popupBackground = this.mPopup.getBackground();
/* 441 */           if (popupBackground != null) {
/* 442 */               popupBackground.getPadding(this.mTempRect);
/* 443 */               this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + width;
                    } else {
/* 445 */               setWidth(width);
                    }
                }

/* 452 */       public int getHeight() {
/* 453 */           return this.mDropDownHeight;
                }

/* 461 */       public void setHeight(int height) {
/* 462 */           this.mDropDownHeight = height;
                }

/* 471 */       public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
/* 472 */           this.mItemClickListener = clickListener;
                }

/* 481 */       public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener selectedListener) {
/* 482 */           this.mItemSelectedListener = selectedListener;
                }

/* 491 */       public void setPromptView(View prompt) {
/* 492 */           boolean showing = isShowing();
/* 493 */           if (showing) {
/* 494 */               removePromptView();
                    }
/* 496 */           this.mPromptView = prompt;
/* 497 */           if (showing) {
/* 498 */               show();
                    }
                }

/* 505 */       public void postShow() {
/* 506 */           this.mHandler.post(this.mShowDropDownRunnable);
                }

/* 513 */       public void show() {
                    int widthSpec;
                    int heightSpec;
                    boolean z = false;
/* 514 */           int height = buildDropDown();
/* 516 */           int widthSpec2 = 0;
/* 517 */           int heightSpec2 = 0;
/* 519 */           boolean noInputMethod = isInputMethodNotNeeded();
/* 521 */           if (this.mPopup.isShowing()) {
/* 522 */               if (this.mDropDownWidth == -1) {
/* 525 */                   widthSpec = -1;
                        } else if (this.mDropDownWidth == -2) {
/* 527 */                   widthSpec = getAnchorView().getWidth();
                        } else {
/* 529 */                   widthSpec = this.mDropDownWidth;
                        }
/* 532 */               if (this.mDropDownHeight == -1) {
/* 535 */                   heightSpec = noInputMethod ? height : -1;
/* 536 */                   if (noInputMethod) {
/* 537 */                       this.mPopup.setWindowLayoutMode(this.mDropDownWidth != -1 ? 0 : -1, 0);
                            } else {
/* 541 */                       this.mPopup.setWindowLayoutMode(this.mDropDownWidth == -1 ? -1 : 0, -1);
                            }
                        } else {
/* 549 */                   heightSpec = this.mDropDownHeight == -2 ? height : this.mDropDownHeight;
                        }
/* 552 */               PopupWindow popupWindow = this.mPopup;
                        if (!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible) {
                            z = true;
                        }
                        popupWindow.setOutsideTouchable(z);
/* 554 */               this.mPopup.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, widthSpec, heightSpec);
/* 594 */               return;
                    }
/* 557 */           if (this.mDropDownWidth == -1) {
/* 558 */               widthSpec2 = -1;
                    } else if (this.mDropDownWidth == -2) {
/* 561 */               this.mPopup.setWidth(getAnchorView().getWidth());
                    } else {
/* 563 */               this.mPopup.setWidth(this.mDropDownWidth);
                    }
/* 567 */           if (this.mDropDownHeight == -1) {
/* 568 */               heightSpec2 = -1;
                    } else if (this.mDropDownHeight == -2) {
/* 571 */               this.mPopup.setHeight(height);
                    } else {
/* 573 */               this.mPopup.setHeight(this.mDropDownHeight);
                    }
/* 577 */           this.mPopup.setWindowLayoutMode(widthSpec2, heightSpec2);
/* 581 */           this.mPopup.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
/* 582 */           this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
/* 583 */           this.mPopup.showAsDropDown(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset);
/* 585 */           this.mDropDownList.setSelection(-1);
/* 587 */           if (!this.mModal || this.mDropDownList.isInTouchMode()) {
/* 588 */               clearListSelection();
                    }
/* 590 */           if (!this.mModal) {
/* 591 */               this.mHandler.post(this.mHideSelector);
                    }
                }

/* 599 */       public void dismiss() {
/* 600 */           this.mPopup.dismiss();
/* 601 */           removePromptView();
/* 602 */           this.mPopup.setContentView(null);
/* 603 */           this.mDropDownList = null;
/* 604 */           this.mHandler.removeCallbacks(this.mResizePopupRunnable);
                }

/* 612 */       public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
/* 613 */           this.mPopup.setOnDismissListener(listener);
                }

/* 616 */       private void removePromptView() {
/* 617 */           if (this.mPromptView != null) {
/* 618 */               ViewParent parent = this.mPromptView.getParent();
/* 619 */               if (parent instanceof ViewGroup) {
/* 620 */                   ViewGroup group = (ViewGroup) parent;
/* 621 */                   group.removeView(this.mPromptView);
                        }
                    }
                }

/* 637 */       public void setInputMethodMode(int mode) {
/* 638 */           this.mPopup.setInputMethodMode(mode);
                }

/* 646 */       public int getInputMethodMode() {
/* 647 */           return this.mPopup.getInputMethodMode();
                }

/* 655 */       public void setSelection(int position) {
/* 656 */           DropDownListView list = this.mDropDownList;
/* 657 */           if (isShowing() && list != null) {
/* 658 */               DropDownListView.access$502(list, false);
/* 659 */               list.setSelection(position);
/* 660 */               if (list.getChoiceMode() != 0) {
/* 661 */                   list.setItemChecked(position, true);
                        }
                    }
                }

/* 669 */       public void clearListSelection() {
/* 670 */           DropDownListView list = this.mDropDownList;
/* 671 */           if (list != null) {
/* 673 */               DropDownListView.access$502(list, true);
/* 675 */               list.requestLayout();
                    }
                }

/* 682 */       public boolean isShowing() {
/* 683 */           return this.mPopup.isShowing();
                }

/* 690 */       public boolean isInputMethodNotNeeded() {
                    return this.mPopup.getInputMethodMode() == 2;
                }

/* 701 */       public boolean performItemClick(int position) {
/* 702 */           if (!isShowing()) {
                        return false;
                    }
/* 703 */           if (this.mItemClickListener != null) {
/* 704 */               DropDownListView list = this.mDropDownList;
/* 705 */               View child = list.getChildAt(position - list.getFirstVisiblePosition());
/* 706 */               ListAdapter adapter = list.getAdapter();
/* 707 */               this.mItemClickListener.onItemClick(list, child, position, adapter.getItemId(position));
                    }
/* 709 */           return true;
                }

/* 717 */       public Object getSelectedItem() {
/* 718 */           if (isShowing()) {
                        return this.mDropDownList.getSelectedItem();
                    }
/* 719 */           return null;
                }

/* 729 */       public int getSelectedItemPosition() {
/* 730 */           if (isShowing()) {
                        return this.mDropDownList.getSelectedItemPosition();
                    }
/* 731 */           return -1;
                }

/* 741 */       public long getSelectedItemId() {
/* 742 */           if (isShowing()) {
                        return this.mDropDownList.getSelectedItemId();
                    }
/* 743 */           return Long.MIN_VALUE;
                }

/* 753 */       public View getSelectedView() {
/* 754 */           if (isShowing()) {
                        return this.mDropDownList.getSelectedView();
                    }
/* 755 */           return null;
                }

/* 764 */       public ListView getListView() {
/* 765 */           return this.mDropDownList;
                }

/* 774 */       void setListItemExpandMax(int max) {
/* 775 */           this.mListItemExpandMaximum = max;
                }

/* 788 */       public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 789 */           if (!isShowing() || keyCode == 62) {
                        return false;
                    }
                    if (this.mDropDownList.getSelectedItemPosition() < 0 && (keyCode == 66 || keyCode == 23)) {
                        return false;
                    }
/* 799 */           int curIndex = this.mDropDownList.getSelectedItemPosition();
/* 802 */           boolean below = !this.mPopup.isAboveAnchor();
/* 804 */           ListAdapter adapter = this.mAdapter;
/* 807 */           int firstItem = Integer.MAX_VALUE;
/* 808 */           int lastItem = Integer.MIN_VALUE;
/* 810 */           if (adapter != null) {
/* 811 */               boolean allEnabled = adapter.areAllItemsEnabled();
/* 812 */               firstItem = allEnabled ? 0 : DropDownListView.access$600(this.mDropDownList, 0, true);
/* 814 */               lastItem = allEnabled ? adapter.getCount() - 1 : DropDownListView.access$600(this.mDropDownList, adapter.getCount() - 1, false);
                    }
/* 818 */           if ((below && keyCode == 19 && curIndex <= firstItem) || (!below && keyCode == 20 && curIndex >= lastItem)) {
/* 822 */               clearListSelection();
/* 823 */               this.mPopup.setInputMethodMode(1);
/* 824 */               show();
                        return true;
                    }
/* 829 */           DropDownListView.access$502(this.mDropDownList, false);
/* 832 */           boolean consumed = this.mDropDownList.onKeyDown(keyCode, event);
/* 837 */           if (consumed) {
/* 840 */               this.mPopup.setInputMethodMode(2);
/* 845 */               this.mDropDownList.requestFocusFromTouch();
/* 846 */               show();
                        switch (keyCode) {
                            case 19:
                            case 20:
                            case 23:
                            case 66:
                                return true;
                            default:
                                return false;
                        }
                    }
/* 858 */           if (below && keyCode == 20) {
                        return curIndex == lastItem;
                    }
                    return !below && keyCode == 19 && curIndex == firstItem;
                }

/* 884 */       public boolean onKeyUp(int keyCode, KeyEvent event) {
/* 885 */           if (isShowing() && this.mDropDownList.getSelectedItemPosition() >= 0) {
/* 886 */               boolean consumed = this.mDropDownList.onKeyUp(keyCode, event);
/* 887 */               if (consumed) {
                            switch (keyCode) {
                                case 23:
                                case 66:
/* 894 */                           dismiss();
/* 900 */                           return consumed;
                                default:
/* 900 */                           return consumed;
                            }
                        }
/* 900 */               return consumed;
                    }
/* 900 */           return false;
                }

/* 910 */       private int buildDropDown() {
                    int iMakeMeasureSpec;
/* 911 */           int measuredHeight = 0;
/* 913 */           if (this.mDropDownList == null) {
/* 914 */               Context context = this.mContext;
/* 922 */               this.mShowDropDownRunnable = new RunnableC01021();
/* 932 */               this.mDropDownList = new DropDownListView(context, !this.mModal);
/* 933 */               if (this.mDropDownListHighlight != null) {
/* 934 */                   this.mDropDownList.setSelector(this.mDropDownListHighlight);
                        }
/* 936 */               this.mDropDownList.setAdapter(this.mAdapter);
/* 937 */               this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
/* 938 */               this.mDropDownList.setFocusable(true);
/* 939 */               this.mDropDownList.setFocusableInTouchMode(true);
/* 940 */               this.mDropDownList.setOnItemSelectedListener(new C01032());
/* 956 */               this.mDropDownList.setOnScrollListener(this.mScrollListener);
/* 958 */               if (this.mItemSelectedListener != null) {
/* 959 */                   this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener);
                        }
/* 962 */               DropDownListView dropDownListView = this.mDropDownList;
/* 964 */               View view = this.mPromptView;
                        View view2 = dropDownListView;
/* 965 */               if (view != null) {
/* 968 */                   LinearLayout linearLayout = new LinearLayout(context);
/* 969 */                   linearLayout.setOrientation(1);
/* 971 */                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                            switch (this.mPromptPosition) {
                                case 0:
/* 982 */                           linearLayout.addView(view);
/* 983 */                           linearLayout.addView(dropDownListView, layoutParams);
                                    break;
                                case 1:
/* 977 */                           linearLayout.addView(dropDownListView, layoutParams);
/* 978 */                           linearLayout.addView(view);
                                    break;
                                default:
/* 987 */                           Log.e(TAG, "Invalid hint position " + this.mPromptPosition);
                                    break;
                            }
/* 995 */                   view.measure(View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, Integer.MIN_VALUE), 0);
/* 997 */                   LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view.getLayoutParams();
/* 998 */                   measuredHeight = view.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
/* 1001 */                  view2 = linearLayout;
                        }
/* 1004 */              this.mPopup.setContentView(view2);
                    } else {
/* 1007 */              View view3 = this.mPromptView;
/* 1008 */              if (view3 != null) {
/* 1009 */                  LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
/* 1011 */                  measuredHeight = view3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
                        }
                    }
/* 1018 */          int i = 0;
/* 1019 */          Drawable background = this.mPopup.getBackground();
/* 1020 */          if (background != null) {
/* 1021 */              background.getPadding(this.mTempRect);
/* 1022 */              i = this.mTempRect.top + this.mTempRect.bottom;
/* 1026 */              if (!this.mDropDownVerticalOffsetSet) {
/* 1027 */                  this.mDropDownVerticalOffset = -this.mTempRect.top;
                        }
                    } else {
/* 1030 */              this.mTempRect.setEmpty();
                    }
/* 1036 */          int maxAvailableHeight = getMaxAvailableHeight(getAnchorView(), this.mDropDownVerticalOffset, this.mPopup.getInputMethodMode() == 2);
/* 1039 */          if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
/* 1040 */              return maxAvailableHeight + i;
                    }
                    switch (this.mDropDownWidth) {
                        case -2:
/* 1046 */                  iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), Integer.MIN_VALUE);
                            break;
                        case -1:
/* 1052 */                  iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), 1073741824);
                            break;
                        default:
/* 1058 */                  iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, 1073741824);
                            break;
                    }
/* 1062 */          int iMeasureHeightOfChildrenCompat = this.mDropDownList.measureHeightOfChildrenCompat(iMakeMeasureSpec, 0, -1, maxAvailableHeight - measuredHeight, -1);
/* 1066 */          if (iMeasureHeightOfChildrenCompat > 0) {
/* 1067 */              measuredHeight += i;
                    }
/* 1070 */          return iMeasureHeightOfChildrenCompat + measuredHeight;
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ListPopupWindow$1 */
/* 920 */       class RunnableC01021 implements Runnable {
/* 921 */           RunnableC01021() {
                    }

                    @Override // java.lang.Runnable
/* 924 */           public void run() {
/* 925 */               View view = ListPopupWindow.this.getAnchorView();
/* 926 */               if (view != null && view.getWindowToken() != null) {
/* 927 */                   ListPopupWindow.this.show();
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ListPopupWindow$2 */
/* 938 */       class C01032 implements AdapterView.OnItemSelectedListener {
/* 939 */           C01032() {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 943 */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        DropDownListView dropDownList;
/* 944 */               if (position != -1 && (dropDownList = ListPopupWindow.access$700(ListPopupWindow.this)) != null) {
/* 948 */                   DropDownListView.access$502(dropDownList, false);
                        }
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 953 */           public void onNothingSelected(AdapterView<?> parent) {
                    }
                }

/* 1088 */      public int getMaxAvailableHeight(View anchor, int yOffset, boolean ignoreBottomDecorations) {
/* 1089 */          Rect displayFrame = new Rect();
/* 1090 */          anchor.getWindowVisibleDisplayFrame(displayFrame);
/* 1092 */          int[] mDrawingLocation = new int[2];
/* 1094 */          anchor.getLocationOnScreen(mDrawingLocation);
/* 1096 */          int bottomEdge = displayFrame.bottom;
/* 1097 */          if (ignoreBottomDecorations) {
/* 1098 */              Resources res = anchor.getContext().getResources();
/* 1099 */              bottomEdge = res.getDisplayMetrics().heightPixels;
                    }
/* 1101 */          int distanceToBottom = (bottomEdge - (mDrawingLocation[1] + anchor.getHeight())) - yOffset;
/* 1102 */          int distanceToTop = (mDrawingLocation[1] - displayFrame.top) + yOffset;
/* 1105 */          int returnedHeight = Math.max(distanceToBottom, distanceToTop);
/* 1106 */          if (this.mPopup.getBackground() != null) {
/* 1107 */              this.mPopup.getBackground().getPadding(this.mTempRect);
/* 1108 */              return returnedHeight - (this.mTempRect.top + this.mTempRect.bottom);
                    }
/* 1111 */          return returnedHeight;
                }

/* 1117 */      private static class DropDownListView extends ListView {
                    public static final int INVALID_POSITION = -1;
                    static final int NO_POSITION = -1;
                    private static final String TAG = "ListPopupWindow.DropDownListView";
                    private boolean mHijackFocus;
                    private boolean mListSelectionHidden;

/* 1118 */          static /* synthetic */ boolean access$502(DropDownListView x0, boolean x1) {
/* 1119 */              x0.mListSelectionHidden = x1;
                        return x1;
                    }

/* 1118 */          static /* synthetic */ int access$600(DropDownListView x0, int x1, boolean x2) {
/* 1119 */              return x0.lookForSelectablePosition(x1, x2);
                    }

/* 1169 */          public DropDownListView(Context context, boolean hijackFocus) {
/* 1170 */              super(context, null, C0084R.attr.dropDownListViewStyle);
/* 1171 */              this.mHijackFocus = hijackFocus;
/* 1172 */              setCacheColorHint(0);
                    }

/* 1183 */          private int lookForSelectablePosition(int position, boolean lookDown) {
                        int position2;
/* 1184 */              ListAdapter adapter = getAdapter();
/* 1185 */              if (adapter == null || isInTouchMode()) {
                            return -1;
                        }
/* 1189 */              int count = adapter.getCount();
/* 1190 */              if (!getAdapter().areAllItemsEnabled()) {
/* 1191 */                  if (lookDown) {
/* 1192 */                      position2 = Math.max(0, position);
/* 1193 */                      while (position2 < count && !adapter.isEnabled(position2)) {
/* 1194 */                          position2++;
                                }
                            } else {
/* 1197 */                      position2 = Math.min(position, count - 1);
/* 1198 */                      while (position2 >= 0 && !adapter.isEnabled(position2)) {
/* 1199 */                          position2--;
                                }
                            }
/* 1203 */                  if (position2 < 0 || position2 >= count) {
                                return -1;
                            }
                            return position2;
                        }
/* 1208 */              if (position < 0 || position >= count) {
                            return -1;
                        }
                        return position;
                    }

                    @Override // android.view.View
/* 1217 */          public boolean isInTouchMode() {
                        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
                    }

                    @Override // android.view.View
/* 1227 */          public boolean hasWindowFocus() {
                        return this.mHijackFocus || super.hasWindowFocus();
                    }

                    @Override // android.view.View
/* 1237 */          public boolean isFocused() {
                        return this.mHijackFocus || super.isFocused();
                    }

                    @Override // android.view.ViewGroup, android.view.View
/* 1247 */          public boolean hasFocus() {
                        return this.mHijackFocus || super.hasFocus();
                    }

/* 1280 */          final int measureHeightOfChildrenCompat(int widthMeasureSpec, int startPosition, int endPosition, int maxHeight, int disallowPartialChildPosition) {
                        int heightMeasureSpec;
/* 1281 */              int paddingTop = getListPaddingTop();
/* 1282 */              int paddingBottom = getListPaddingBottom();
/* 1283 */              getListPaddingLeft();
/* 1284 */              getListPaddingRight();
/* 1285 */              int reportedDividerHeight = getDividerHeight();
/* 1286 */              Drawable divider = getDivider();
/* 1288 */              ListAdapter adapter = getAdapter();
/* 1290 */              if (adapter == null) {
/* 1291 */                  return paddingTop + paddingBottom;
                        }
/* 1295 */              int returnedHeight = paddingTop + paddingBottom;
/* 1296 */              int dividerHeight = (reportedDividerHeight <= 0 || divider == null) ? 0 : reportedDividerHeight;
/* 1301 */              int prevHeightWithoutPartialChild = 0;
/* 1303 */              View child = null;
/* 1304 */              int viewType = 0;
/* 1305 */              int count = adapter.getCount();
/* 1306 */              int i = 0;
                        while (i < count) {
/* 1307 */                  int newType = adapter.getItemViewType(i);
/* 1308 */                  if (newType != viewType) {
/* 1309 */                      child = null;
/* 1310 */                      viewType = newType;
                            }
/* 1312 */                  child = adapter.getView(i, child, this);
/* 1316 */                  ViewGroup.LayoutParams childLp = child.getLayoutParams();
/* 1317 */                  if (childLp != null && childLp.height > 0) {
/* 1318 */                      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childLp.height, 1073741824);
                            } else {
/* 1321 */                      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            }
/* 1323 */                  child.measure(widthMeasureSpec, heightMeasureSpec);
/* 1325 */                  if (i > 0) {
/* 1327 */                      returnedHeight += dividerHeight;
                            }
/* 1330 */                  returnedHeight += child.getMeasuredHeight();
/* 1332 */                  if (returnedHeight >= maxHeight) {
                                return (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevHeightWithoutPartialChild <= 0 || returnedHeight == maxHeight) ? maxHeight : prevHeightWithoutPartialChild;
                            }
/* 1343 */                  if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
/* 1344 */                      prevHeightWithoutPartialChild = returnedHeight;
                            }
/* 1306 */                  i++;
                        }
                        int prevHeightWithoutPartialChild2 = returnedHeight;
/* 1350 */              return prevHeightWithoutPartialChild2;
                    }
                }

/* 1353 */      private class PopupDataSetObserver extends DataSetObserver {
/* 1354 */          private PopupDataSetObserver() {
                    }

/* 1354 */          /* synthetic */ PopupDataSetObserver(ListPopupWindow x0, RunnableC01021 x1) {
/* 1355 */              this();
                    }

                    @Override // android.database.DataSetObserver
/* 1358 */          public void onChanged() {
/* 1359 */              if (ListPopupWindow.this.isShowing()) {
/* 1361 */                  ListPopupWindow.this.show();
                        }
                    }

                    @Override // android.database.DataSetObserver
/* 1366 */          public void onInvalidated() {
/* 1367 */              ListPopupWindow.this.dismiss();
                    }
                }

/* 1369 */      private class ListSelectorHider implements Runnable {
/* 1370 */          private ListSelectorHider() {
                    }

/* 1370 */          /* synthetic */ ListSelectorHider(ListPopupWindow x0, RunnableC01021 x1) {
/* 1371 */              this();
                    }

                    @Override // java.lang.Runnable
/* 1373 */          public void run() {
/* 1374 */              ListPopupWindow.this.clearListSelection();
                    }
                }

/* 1376 */      private class ResizePopupRunnable implements Runnable {
/* 1377 */          private ResizePopupRunnable() {
                    }

/* 1377 */          /* synthetic */ ResizePopupRunnable(ListPopupWindow x0, RunnableC01021 x1) {
/* 1378 */              this();
                    }

                    @Override // java.lang.Runnable
/* 1380 */          public void run() {
/* 1381 */              if (ListPopupWindow.access$700(ListPopupWindow.this) != null && ListPopupWindow.access$700(ListPopupWindow.this).getCount() > ListPopupWindow.access$700(ListPopupWindow.this).getChildCount() && ListPopupWindow.access$700(ListPopupWindow.this).getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
/* 1383 */                  ListPopupWindow.access$800(ListPopupWindow.this).setInputMethodMode(2);
/* 1384 */                  ListPopupWindow.this.show();
                        }
                    }
                }

/* 1387 */      private class PopupTouchInterceptor implements View.OnTouchListener {
/* 1388 */          private PopupTouchInterceptor() {
                    }

/* 1388 */          /* synthetic */ PopupTouchInterceptor(ListPopupWindow x0, RunnableC01021 x1) {
/* 1389 */              this();
                    }

                    @Override // android.view.View.OnTouchListener
/* 1391 */          public boolean onTouch(View v, MotionEvent event) {
/* 1392 */              int action = event.getAction();
/* 1393 */              int x = (int) event.getX();
/* 1394 */              int y = (int) event.getY();
/* 1396 */              if (action == 0 && ListPopupWindow.access$800(ListPopupWindow.this) != null && ListPopupWindow.access$800(ListPopupWindow.this).isShowing() && x >= 0 && x < ListPopupWindow.access$800(ListPopupWindow.this).getWidth() && y >= 0 && y < ListPopupWindow.access$800(ListPopupWindow.this).getHeight()) {
/* 1399 */                  ListPopupWindow.access$1000(ListPopupWindow.this).postDelayed(ListPopupWindow.access$900(ListPopupWindow.this), 250L);
/* 1403 */                  return false;
                        }
/* 1400 */              if (action == 1) {
/* 1401 */                  ListPopupWindow.access$1000(ListPopupWindow.this).removeCallbacks(ListPopupWindow.access$900(ListPopupWindow.this));
/* 1403 */                  return false;
                        }
/* 1403 */              return false;
                    }
                }

/* 1405 */      private class PopupScrollListener implements AbsListView.OnScrollListener {
/* 1406 */          private PopupScrollListener() {
                    }

/* 1406 */          /* synthetic */ PopupScrollListener(ListPopupWindow x0, RunnableC01021 x1) {
/* 1407 */              this();
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
/* 1411 */          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
/* 1414 */          public void onScrollStateChanged(AbsListView view, int scrollState) {
/* 1415 */              if (scrollState == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.access$800(ListPopupWindow.this).getContentView() != null) {
/* 1417 */                  ListPopupWindow.access$1000(ListPopupWindow.this).removeCallbacks(ListPopupWindow.access$900(ListPopupWindow.this));
/* 1418 */                  ListPopupWindow.access$900(ListPopupWindow.this).run();
                        }
                    }
                }
            }
