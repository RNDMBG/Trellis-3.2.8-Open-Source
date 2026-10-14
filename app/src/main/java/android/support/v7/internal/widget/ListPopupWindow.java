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
            
            
    public class ListPopupWindow {
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

        static  Handler access$1000(ListPopupWindow x0) {
            return x0.mHandler;
                }

        static  DropDownListView access$700(ListPopupWindow x0) {
            return x0.mDropDownList;
                }

        static  PopupWindow access$800(ListPopupWindow x0) {
            return x0.mPopup;
                }

        static  ResizePopupRunnable access$900(ListPopupWindow x0) {
            return x0.mResizePopupRunnable;
                }

       public ListPopupWindow(Context context) {
           this(context, null, C0084R.attr.listPopupWindowStyle);
                }

       public ListPopupWindow(Context context, AttributeSet attrs) {
           this(context, attrs, C0084R.attr.listPopupWindowStyle);
                }

       public ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
            this.mDropDownHeight = -2;
            this.mDropDownWidth = -2;
            this.mDropDownAlwaysVisible = false;
            this.mForceIgnoreOutsideTouch = false;
            this.mListItemExpandMaximum = Integer.MAX_VALUE;
            this.mPromptPosition = 0;
            this.mResizePopupRunnable = new ResizePopupRunnable(this, null);
            this.mTouchInterceptor = new PopupTouchInterceptor(this, null);
            this.mScrollListener = new PopupScrollListener(this, null);
            this.mHideSelector = new ListSelectorHider(this, null);
            this.mHandler = new Handler();
           this.mTempRect = new Rect();
           this.mContext = context;
           this.mPopup = new PopupWindow(context, attrs, defStyleAttr);
           this.mPopup.setInputMethodMode(1);
           Locale locale = this.mContext.getResources().getConfiguration().locale;
                }

       public void setAdapter(ListAdapter adapter) {
           if (this.mObserver == null) {
               this.mObserver = new PopupDataSetObserver(this, null);
                    } else if (this.mAdapter != null) {
               this.mAdapter.unregisterDataSetObserver(this.mObserver);
                    }
           this.mAdapter = adapter;
           if (this.mAdapter != null) {
               adapter.registerDataSetObserver(this.mObserver);
                    }
           if (this.mDropDownList != null) {
               this.mDropDownList.setAdapter(this.mAdapter);
                    }
                }

       public void setPromptPosition(int position) {
           this.mPromptPosition = position;
                }

       public int getPromptPosition() {
           return this.mPromptPosition;
                }

       public void setModal(boolean modal) {
           this.mModal = true;
           this.mPopup.setFocusable(modal);
                }

       public boolean isModal() {
           return this.mModal;
                }

       public void setForceIgnoreOutsideTouch(boolean forceIgnoreOutsideTouch) {
           this.mForceIgnoreOutsideTouch = forceIgnoreOutsideTouch;
                }

       public void setDropDownAlwaysVisible(boolean dropDownAlwaysVisible) {
           this.mDropDownAlwaysVisible = dropDownAlwaysVisible;
                }

       public boolean isDropDownAlwaysVisible() {
           return this.mDropDownAlwaysVisible;
                }

       public void setSoftInputMode(int mode) {
           this.mPopup.setSoftInputMode(mode);
                }

       public int getSoftInputMode() {
           return this.mPopup.getSoftInputMode();
                }

       public void setListSelector(Drawable selector) {
           this.mDropDownListHighlight = selector;
                }

       public Drawable getBackground() {
           return this.mPopup.getBackground();
                }

       public void setBackgroundDrawable(Drawable d) {
           this.mPopup.setBackgroundDrawable(d);
                }

       public void setAnimationStyle(int animationStyle) {
           this.mPopup.setAnimationStyle(animationStyle);
                }

       public int getAnimationStyle() {
           return this.mPopup.getAnimationStyle();
                }

       public View getAnchorView() {
           return this.mDropDownAnchorView;
                }

       public void setAnchorView(View anchor) {
           this.mDropDownAnchorView = anchor;
                }

       public int getHorizontalOffset() {
           return this.mDropDownHorizontalOffset;
                }

       public void setHorizontalOffset(int offset) {
           this.mDropDownHorizontalOffset = offset;
                }

       public int getVerticalOffset() {
           if (this.mDropDownVerticalOffsetSet) {
                        return this.mDropDownVerticalOffset;
                    }
           return 0;
                }

       public void setVerticalOffset(int offset) {
           this.mDropDownVerticalOffset = offset;
           this.mDropDownVerticalOffsetSet = true;
                }

       public int getWidth() {
           return this.mDropDownWidth;
                }

       public void setWidth(int width) {
           this.mDropDownWidth = width;
                }

       public void setContentWidth(int width) {
           Drawable popupBackground = this.mPopup.getBackground();
           if (popupBackground != null) {
               popupBackground.getPadding(this.mTempRect);
               this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + width;
                    } else {
               setWidth(width);
                    }
                }

       public int getHeight() {
           return this.mDropDownHeight;
                }

       public void setHeight(int height) {
           this.mDropDownHeight = height;
                }

       public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
           this.mItemClickListener = clickListener;
                }

       public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener selectedListener) {
           this.mItemSelectedListener = selectedListener;
                }

       public void setPromptView(View prompt) {
           boolean showing = isShowing();
           if (showing) {
               removePromptView();
                    }
           this.mPromptView = prompt;
           if (showing) {
               show();
                    }
                }

       public void postShow() {
           this.mHandler.post(this.mShowDropDownRunnable);
                }

       public void show() {
                    int widthSpec;
                    int heightSpec;
                    boolean z = false;
           int height = buildDropDown();
           int widthSpec2 = 0;
           int heightSpec2 = 0;
           boolean noInputMethod = isInputMethodNotNeeded();
           if (this.mPopup.isShowing()) {
               if (this.mDropDownWidth == -1) {
                   widthSpec = -1;
                        } else if (this.mDropDownWidth == -2) {
                   widthSpec = getAnchorView().getWidth();
                        } else {
                   widthSpec = this.mDropDownWidth;
                        }
               if (this.mDropDownHeight == -1) {
                   heightSpec = noInputMethod ? height : -1;
                   if (noInputMethod) {
                       this.mPopup.setWindowLayoutMode(this.mDropDownWidth != -1 ? 0 : -1, 0);
                            } else {
                       this.mPopup.setWindowLayoutMode(this.mDropDownWidth == -1 ? -1 : 0, -1);
                            }
                        } else {
                   heightSpec = this.mDropDownHeight == -2 ? height : this.mDropDownHeight;
                        }
               PopupWindow popupWindow = this.mPopup;
                        if (!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible) {
                            z = true;
                        }
                        popupWindow.setOutsideTouchable(z);
               this.mPopup.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, widthSpec, heightSpec);
               return;
                    }
           if (this.mDropDownWidth == -1) {
               widthSpec2 = -1;
                    } else if (this.mDropDownWidth == -2) {
               this.mPopup.setWidth(getAnchorView().getWidth());
                    } else {
               this.mPopup.setWidth(this.mDropDownWidth);
                    }
           if (this.mDropDownHeight == -1) {
               heightSpec2 = -1;
                    } else if (this.mDropDownHeight == -2) {
               this.mPopup.setHeight(height);
                    } else {
               this.mPopup.setHeight(this.mDropDownHeight);
                    }
           this.mPopup.setWindowLayoutMode(widthSpec2, heightSpec2);
           this.mPopup.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
           this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
           this.mPopup.showAsDropDown(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset);
           this.mDropDownList.setSelection(-1);
           if (!this.mModal || this.mDropDownList.isInTouchMode()) {
               clearListSelection();
                    }
           if (!this.mModal) {
               this.mHandler.post(this.mHideSelector);
                    }
                }

       public void dismiss() {
           this.mPopup.dismiss();
           removePromptView();
           this.mPopup.setContentView(null);
           this.mDropDownList = null;
           this.mHandler.removeCallbacks(this.mResizePopupRunnable);
                }

       public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
           this.mPopup.setOnDismissListener(listener);
                }

       private void removePromptView() {
           if (this.mPromptView != null) {
               ViewParent parent = this.mPromptView.getParent();
               if (parent instanceof ViewGroup) {
                   ViewGroup group = (ViewGroup) parent;
                   group.removeView(this.mPromptView);
                        }
                    }
                }

       public void setInputMethodMode(int mode) {
           this.mPopup.setInputMethodMode(mode);
                }

       public int getInputMethodMode() {
           return this.mPopup.getInputMethodMode();
                }

       public void setSelection(int position) {
           DropDownListView list = this.mDropDownList;
           if (isShowing() && list != null) {
               DropDownListView.access$502(list, false);
               list.setSelection(position);
               if (list.getChoiceMode() != 0) {
                   list.setItemChecked(position, true);
                        }
                    }
                }

       public void clearListSelection() {
           DropDownListView list = this.mDropDownList;
           if (list != null) {
               DropDownListView.access$502(list, true);
               list.requestLayout();
                    }
                }

       public boolean isShowing() {
           return this.mPopup.isShowing();
                }

       public boolean isInputMethodNotNeeded() {
                    return this.mPopup.getInputMethodMode() == 2;
                }

       public boolean performItemClick(int position) {
           if (!isShowing()) {
                        return false;
                    }
           if (this.mItemClickListener != null) {
               DropDownListView list = this.mDropDownList;
               View child = list.getChildAt(position - list.getFirstVisiblePosition());
               ListAdapter adapter = list.getAdapter();
               this.mItemClickListener.onItemClick(list, child, position, adapter.getItemId(position));
                    }
           return true;
                }

       public Object getSelectedItem() {
           if (isShowing()) {
                        return this.mDropDownList.getSelectedItem();
                    }
           return null;
                }

       public int getSelectedItemPosition() {
           if (isShowing()) {
                        return this.mDropDownList.getSelectedItemPosition();
                    }
           return -1;
                }

       public long getSelectedItemId() {
           if (isShowing()) {
                        return this.mDropDownList.getSelectedItemId();
                    }
           return Long.MIN_VALUE;
                }

       public View getSelectedView() {
           if (isShowing()) {
                        return this.mDropDownList.getSelectedView();
                    }
           return null;
                }

       public ListView getListView() {
           return this.mDropDownList;
                }

       void setListItemExpandMax(int max) {
           this.mListItemExpandMaximum = max;
                }

       public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (!isShowing() || keyCode == 62) {
                        return false;
                    }
                    if (this.mDropDownList.getSelectedItemPosition() < 0 && (keyCode == 66 || keyCode == 23)) {
                        return false;
                    }
           int curIndex = this.mDropDownList.getSelectedItemPosition();
           boolean below = !this.mPopup.isAboveAnchor();
           ListAdapter adapter = this.mAdapter;
           int firstItem = Integer.MAX_VALUE;
           int lastItem = Integer.MIN_VALUE;
           if (adapter != null) {
               boolean allEnabled = adapter.areAllItemsEnabled();
               firstItem = allEnabled ? 0 : DropDownListView.access$600(this.mDropDownList, 0, true);
               lastItem = allEnabled ? adapter.getCount() - 1 : DropDownListView.access$600(this.mDropDownList, adapter.getCount() - 1, false);
                    }
           if ((below && keyCode == 19 && curIndex <= firstItem) || (!below && keyCode == 20 && curIndex >= lastItem)) {
               clearListSelection();
               this.mPopup.setInputMethodMode(1);
               show();
                        return true;
                    }
           DropDownListView.access$502(this.mDropDownList, false);
           boolean consumed = this.mDropDownList.onKeyDown(keyCode, event);
           if (consumed) {
               this.mPopup.setInputMethodMode(2);
               this.mDropDownList.requestFocusFromTouch();
               show();
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
           if (below && keyCode == 20) {
                        return curIndex == lastItem;
                    }
                    return !below && keyCode == 19 && curIndex == firstItem;
                }

       public boolean onKeyUp(int keyCode, KeyEvent event) {
           if (isShowing() && this.mDropDownList.getSelectedItemPosition() >= 0) {
               boolean consumed = this.mDropDownList.onKeyUp(keyCode, event);
               if (consumed) {
                            switch (keyCode) {
                                case 23:
                                case 66:
                           dismiss();
                           return consumed;
                                default:
                           return consumed;
                            }
                        }
               return consumed;
                    }
           return false;
                }

       private int buildDropDown() {
                    int iMakeMeasureSpec;
           int measuredHeight = 0;
           if (this.mDropDownList == null) {
               Context context = this.mContext;
               this.mShowDropDownRunnable = new RunnableC01021();
               this.mDropDownList = new DropDownListView(context, !this.mModal);
               if (this.mDropDownListHighlight != null) {
                   this.mDropDownList.setSelector(this.mDropDownListHighlight);
                        }
               this.mDropDownList.setAdapter(this.mAdapter);
               this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
               this.mDropDownList.setFocusable(true);
               this.mDropDownList.setFocusableInTouchMode(true);
               this.mDropDownList.setOnItemSelectedListener(new C01032());
               this.mDropDownList.setOnScrollListener(this.mScrollListener);
               if (this.mItemSelectedListener != null) {
                   this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener);
                        }
               DropDownListView dropDownListView = this.mDropDownList;
               View view = this.mPromptView;
                        View view2 = dropDownListView;
               if (view != null) {
                   LinearLayout linearLayout = new LinearLayout(context);
                   linearLayout.setOrientation(1);
                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                            switch (this.mPromptPosition) {
                                case 0:
                           linearLayout.addView(view);
                           linearLayout.addView(dropDownListView, layoutParams);
                                    break;
                                case 1:
                           linearLayout.addView(dropDownListView, layoutParams);
                           linearLayout.addView(view);
                                    break;
                                default:
                           Log.e(TAG, "Invalid hint position " + this.mPromptPosition);
                                    break;
                            }
                   view.measure(View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, Integer.MIN_VALUE), 0);
                   LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view.getLayoutParams();
                   measuredHeight = view.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                  view2 = linearLayout;
                        }
              this.mPopup.setContentView(view2);
                    } else {
              View view3 = this.mPromptView;
              if (view3 != null) {
                  LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
                  measuredHeight = view3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
                        }
                    }
          int i = 0;
          Drawable background = this.mPopup.getBackground();
          if (background != null) {
              background.getPadding(this.mTempRect);
              i = this.mTempRect.top + this.mTempRect.bottom;
              if (!this.mDropDownVerticalOffsetSet) {
                  this.mDropDownVerticalOffset = -this.mTempRect.top;
                        }
                    } else {
              this.mTempRect.setEmpty();
                    }
          int maxAvailableHeight = getMaxAvailableHeight(getAnchorView(), this.mDropDownVerticalOffset, this.mPopup.getInputMethodMode() == 2);
          if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
              return maxAvailableHeight + i;
                    }
                    switch (this.mDropDownWidth) {
                        case -2:
                  iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), Integer.MIN_VALUE);
                            break;
                        case -1:
                  iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), 1073741824);
                            break;
                        default:
                  iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, 1073741824);
                            break;
                    }
          int iMeasureHeightOfChildrenCompat = this.mDropDownList.measureHeightOfChildrenCompat(iMakeMeasureSpec, 0, -1, maxAvailableHeight - measuredHeight, -1);
          if (iMeasureHeightOfChildrenCompat > 0) {
              measuredHeight += i;
                    }
          return iMeasureHeightOfChildrenCompat + measuredHeight;
                }

                
       class RunnableC01021 implements Runnable {
           RunnableC01021() {
                    }

                    @Override 
           public void run() {
               View view = ListPopupWindow.this.getAnchorView();
               if (view != null && view.getWindowToken() != null) {
                   ListPopupWindow.this.show();
                        }
                    }
                }

                
       class C01032 implements AdapterView.OnItemSelectedListener {
           C01032() {
                    }

                    @Override 
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        DropDownListView dropDownList;
               if (position != -1 && (dropDownList = ListPopupWindow.access$700(ListPopupWindow.this)) != null) {
                   DropDownListView.access$502(dropDownList, false);
                        }
                    }

                    @Override 
           public void onNothingSelected(AdapterView<?> parent) {
                    }
                }

      public int getMaxAvailableHeight(View anchor, int yOffset, boolean ignoreBottomDecorations) {
          Rect displayFrame = new Rect();
          anchor.getWindowVisibleDisplayFrame(displayFrame);
          int[] mDrawingLocation = new int[2];
          anchor.getLocationOnScreen(mDrawingLocation);
          int bottomEdge = displayFrame.bottom;
          if (ignoreBottomDecorations) {
              Resources res = anchor.getContext().getResources();
              bottomEdge = res.getDisplayMetrics().heightPixels;
                    }
          int distanceToBottom = (bottomEdge - (mDrawingLocation[1] + anchor.getHeight())) - yOffset;
          int distanceToTop = (mDrawingLocation[1] - displayFrame.top) + yOffset;
          int returnedHeight = Math.max(distanceToBottom, distanceToTop);
          if (this.mPopup.getBackground() != null) {
              this.mPopup.getBackground().getPadding(this.mTempRect);
              return returnedHeight - (this.mTempRect.top + this.mTempRect.bottom);
                    }
          return returnedHeight;
                }

      private static class DropDownListView extends ListView {
                    public static final int INVALID_POSITION = -1;
                    static final int NO_POSITION = -1;
                    private static final String TAG = "ListPopupWindow.DropDownListView";
                    private boolean mHijackFocus;
                    private boolean mListSelectionHidden;

          static  boolean access$502(DropDownListView x0, boolean x1) {
              x0.mListSelectionHidden = x1;
                        return x1;
                    }

          static  int access$600(DropDownListView x0, int x1, boolean x2) {
              return x0.lookForSelectablePosition(x1, x2);
                    }

          public DropDownListView(Context context, boolean hijackFocus) {
              super(context, null, C0084R.attr.dropDownListViewStyle);
              this.mHijackFocus = hijackFocus;
              setCacheColorHint(0);
                    }

          private int lookForSelectablePosition(int position, boolean lookDown) {
                        int position2;
              ListAdapter adapter = getAdapter();
              if (adapter == null || isInTouchMode()) {
                            return -1;
                        }
              int count = adapter.getCount();
              if (!getAdapter().areAllItemsEnabled()) {
                  if (lookDown) {
                      position2 = Math.max(0, position);
                      while (position2 < count && !adapter.isEnabled(position2)) {
                          position2++;
                                }
                            } else {
                      position2 = Math.min(position, count - 1);
                      while (position2 >= 0 && !adapter.isEnabled(position2)) {
                          position2--;
                                }
                            }
                  if (position2 < 0 || position2 >= count) {
                                return -1;
                            }
                            return position2;
                        }
              if (position < 0 || position >= count) {
                            return -1;
                        }
                        return position;
                    }

                    @Override 
          public boolean isInTouchMode() {
                        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
                    }

                    @Override 
          public boolean hasWindowFocus() {
                        return this.mHijackFocus || super.hasWindowFocus();
                    }

                    @Override 
          public boolean isFocused() {
                        return this.mHijackFocus || super.isFocused();
                    }

                    @Override 
          public boolean hasFocus() {
                        return this.mHijackFocus || super.hasFocus();
                    }

          final int measureHeightOfChildrenCompat(int widthMeasureSpec, int startPosition, int endPosition, int maxHeight, int disallowPartialChildPosition) {
                        int heightMeasureSpec;
              int paddingTop = getListPaddingTop();
              int paddingBottom = getListPaddingBottom();
              getListPaddingLeft();
              getListPaddingRight();
              int reportedDividerHeight = getDividerHeight();
              Drawable divider = getDivider();
              ListAdapter adapter = getAdapter();
              if (adapter == null) {
                  return paddingTop + paddingBottom;
                        }
              int returnedHeight = paddingTop + paddingBottom;
              int dividerHeight = (reportedDividerHeight <= 0 || divider == null) ? 0 : reportedDividerHeight;
              int prevHeightWithoutPartialChild = 0;
              View child = null;
              int viewType = 0;
              int count = adapter.getCount();
              int i = 0;
                        while (i < count) {
                  int newType = adapter.getItemViewType(i);
                  if (newType != viewType) {
                      child = null;
                      viewType = newType;
                            }
                  child = adapter.getView(i, child, this);
                  ViewGroup.LayoutParams childLp = child.getLayoutParams();
                  if (childLp != null && childLp.height > 0) {
                      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childLp.height, 1073741824);
                            } else {
                      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            }
                  child.measure(widthMeasureSpec, heightMeasureSpec);
                  if (i > 0) {
                      returnedHeight += dividerHeight;
                            }
                  returnedHeight += child.getMeasuredHeight();
                  if (returnedHeight >= maxHeight) {
                                return (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevHeightWithoutPartialChild <= 0 || returnedHeight == maxHeight) ? maxHeight : prevHeightWithoutPartialChild;
                            }
                  if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                      prevHeightWithoutPartialChild = returnedHeight;
                            }
                  i++;
                        }
                        int prevHeightWithoutPartialChild2 = returnedHeight;
              return prevHeightWithoutPartialChild2;
                    }
                }

      private class PopupDataSetObserver extends DataSetObserver {
          private PopupDataSetObserver() {
                    }

           PopupDataSetObserver(ListPopupWindow x0, RunnableC01021 x1) {
              this();
                    }

                    @Override 
          public void onChanged() {
              if (ListPopupWindow.this.isShowing()) {
                  ListPopupWindow.this.show();
                        }
                    }

                    @Override 
          public void onInvalidated() {
              ListPopupWindow.this.dismiss();
                    }
                }

      private class ListSelectorHider implements Runnable {
          private ListSelectorHider() {
                    }

           ListSelectorHider(ListPopupWindow x0, RunnableC01021 x1) {
              this();
                    }

                    @Override 
          public void run() {
              ListPopupWindow.this.clearListSelection();
                    }
                }

      private class ResizePopupRunnable implements Runnable {
          private ResizePopupRunnable() {
                    }

           ResizePopupRunnable(ListPopupWindow x0, RunnableC01021 x1) {
              this();
                    }

                    @Override 
          public void run() {
              if (ListPopupWindow.access$700(ListPopupWindow.this) != null && ListPopupWindow.access$700(ListPopupWindow.this).getCount() > ListPopupWindow.access$700(ListPopupWindow.this).getChildCount() && ListPopupWindow.access$700(ListPopupWindow.this).getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
                  ListPopupWindow.access$800(ListPopupWindow.this).setInputMethodMode(2);
                  ListPopupWindow.this.show();
                        }
                    }
                }

      private class PopupTouchInterceptor implements View.OnTouchListener {
          private PopupTouchInterceptor() {
                    }

           PopupTouchInterceptor(ListPopupWindow x0, RunnableC01021 x1) {
              this();
                    }

                    @Override 
          public boolean onTouch(View v, MotionEvent event) {
              int action = event.getAction();
              int x = (int) event.getX();
              int y = (int) event.getY();
              if (action == 0 && ListPopupWindow.access$800(ListPopupWindow.this) != null && ListPopupWindow.access$800(ListPopupWindow.this).isShowing() && x >= 0 && x < ListPopupWindow.access$800(ListPopupWindow.this).getWidth() && y >= 0 && y < ListPopupWindow.access$800(ListPopupWindow.this).getHeight()) {
                  ListPopupWindow.access$1000(ListPopupWindow.this).postDelayed(ListPopupWindow.access$900(ListPopupWindow.this), 250L);
                  return false;
                        }
              if (action == 1) {
                  ListPopupWindow.access$1000(ListPopupWindow.this).removeCallbacks(ListPopupWindow.access$900(ListPopupWindow.this));
                  return false;
                        }
              return false;
                    }
                }

      private class PopupScrollListener implements AbsListView.OnScrollListener {
          private PopupScrollListener() {
                    }

           PopupScrollListener(ListPopupWindow x0, RunnableC01021 x1) {
              this();
                    }

                    @Override 
          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    }

                    @Override 
          public void onScrollStateChanged(AbsListView view, int scrollState) {
              if (scrollState == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.access$800(ListPopupWindow.this).getContentView() != null) {
                  ListPopupWindow.access$1000(ListPopupWindow.this).removeCallbacks(ListPopupWindow.access$900(ListPopupWindow.this));
                  ListPopupWindow.access$900(ListPopupWindow.this).run();
                        }
                    }
                }
            }
