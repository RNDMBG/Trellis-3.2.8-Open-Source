            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.database.DataSetObserver;
            import android.os.Parcelable;
            import android.os.SystemClock;
            import android.util.AttributeSet;
            import android.util.SparseArray;
            import android.view.ContextMenu;
            import android.view.View;
            import android.view.ViewDebug;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import android.widget.Adapter;
            import android.widget.AdapterView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    abstract class AdapterViewICS<T extends Adapter> extends ViewGroup {
                public static final int INVALID_POSITION = -1;
                public static final long INVALID_ROW_ID = Long.MIN_VALUE;
                static final int ITEM_VIEW_TYPE_HEADER_OR_FOOTER = -2;
                static final int ITEM_VIEW_TYPE_IGNORE = -1;
                static final int SYNC_FIRST_POSITION = 1;
                static final int SYNC_MAX_DURATION_MILLIS = 100;
                static final int SYNC_SELECTED_POSITION = 0;
                boolean mBlockLayoutRequests;
                boolean mDataChanged;
                private boolean mDesiredFocusableInTouchModeState;
                private boolean mDesiredFocusableState;
                private View mEmptyView;

                @ViewDebug.ExportedProperty(category = "scrolling")
                int mFirstPosition;
                boolean mInLayout;

                @ViewDebug.ExportedProperty(category = "list")
                int mItemCount;
                private int mLayoutHeight;
                boolean mNeedSync;

                @ViewDebug.ExportedProperty(category = "list")
                int mNextSelectedPosition;
                long mNextSelectedRowId;
                int mOldItemCount;
                int mOldSelectedPosition;
                long mOldSelectedRowId;
                OnItemClickListener mOnItemClickListener;
                OnItemLongClickListener mOnItemLongClickListener;
                OnItemSelectedListener mOnItemSelectedListener;

                @ViewDebug.ExportedProperty(category = "list")
                int mSelectedPosition;
                long mSelectedRowId;
                private AdapterViewICS<T>.SelectionNotifier mSelectionNotifier;
                int mSpecificTop;
                long mSyncHeight;
                int mSyncMode;
                int mSyncPosition;
                long mSyncRowId;

                public interface OnItemClickListener {
                    void onItemClick(AdapterViewICS<?> adapterViewICS, View view, int i, long j);
                }

                public interface OnItemLongClickListener {
                    boolean onItemLongClick(AdapterViewICS<?> adapterViewICS, View view, int i, long j);
                }

                public interface OnItemSelectedListener {
                    void onItemSelected(AdapterViewICS<?> adapterViewICS, View view, int i, long j);

                    void onNothingSelected(AdapterViewICS<?> adapterViewICS);
                }

                public abstract T getAdapter();

                public abstract View getSelectedView();

                public abstract void setAdapter(T t);

                public abstract void setSelection(int i);

/* 49 */        static /* synthetic */ void access$000(AdapterViewICS x0, Parcelable x1) {
/* 50 */            x0.onRestoreInstanceState(x1);
                }

/* 49 */        static /* synthetic */ Parcelable access$100(AdapterViewICS x0) {
/* 50 */            return x0.onSaveInstanceState();
                }

/* 49 */        static /* synthetic */ void access$200(AdapterViewICS x0) {
/* 50 */            x0.fireOnSelected();
                }

/* 227 */       AdapterViewICS(Context context) {
/* 228 */           super(context);
/* 67 */            this.mFirstPosition = 0;
/* 84 */            this.mSyncRowId = Long.MIN_VALUE;
/* 94 */            this.mNeedSync = false;
/* 126 */           this.mInLayout = false;
/* 152 */           this.mNextSelectedPosition = -1;
/* 158 */           this.mNextSelectedRowId = Long.MIN_VALUE;
/* 163 */           this.mSelectedPosition = -1;
/* 169 */           this.mSelectedRowId = Long.MIN_VALUE;
/* 201 */           this.mOldSelectedPosition = -1;
/* 206 */           this.mOldSelectedRowId = Long.MIN_VALUE;
/* 225 */           this.mBlockLayoutRequests = false;
                }

/* 231 */       AdapterViewICS(Context context, AttributeSet attrs) {
/* 232 */           super(context, attrs);
/* 67 */            this.mFirstPosition = 0;
/* 84 */            this.mSyncRowId = Long.MIN_VALUE;
/* 94 */            this.mNeedSync = false;
/* 126 */           this.mInLayout = false;
/* 152 */           this.mNextSelectedPosition = -1;
/* 158 */           this.mNextSelectedRowId = Long.MIN_VALUE;
/* 163 */           this.mSelectedPosition = -1;
/* 169 */           this.mSelectedRowId = Long.MIN_VALUE;
/* 201 */           this.mOldSelectedPosition = -1;
/* 206 */           this.mOldSelectedRowId = Long.MIN_VALUE;
/* 225 */           this.mBlockLayoutRequests = false;
                }

/* 235 */       AdapterViewICS(Context context, AttributeSet attrs, int defStyle) {
/* 236 */           super(context, attrs, defStyle);
/* 67 */            this.mFirstPosition = 0;
/* 84 */            this.mSyncRowId = Long.MIN_VALUE;
/* 94 */            this.mNeedSync = false;
/* 126 */           this.mInLayout = false;
/* 152 */           this.mNextSelectedPosition = -1;
/* 158 */           this.mNextSelectedRowId = Long.MIN_VALUE;
/* 163 */           this.mSelectedPosition = -1;
/* 169 */           this.mSelectedRowId = Long.MIN_VALUE;
/* 201 */           this.mOldSelectedPosition = -1;
/* 206 */           this.mOldSelectedRowId = Long.MIN_VALUE;
/* 225 */           this.mBlockLayoutRequests = false;
                }

/* 263 */       class OnItemClickListenerWrapper implements AdapterView.OnItemClickListener {
                    private final OnItemClickListener mWrappedListener;

/* 264 */           public OnItemClickListenerWrapper(OnItemClickListener listener) {
/* 266 */               this.mWrappedListener = listener;
                    }

                    @Override // android.widget.AdapterView.OnItemClickListener
/* 270 */           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 271 */               this.mWrappedListener.onItemClick(AdapterViewICS.this, view, position, id);
                    }
                }

/* 281 */       public void setOnItemClickListener(OnItemClickListener listener) {
/* 282 */           this.mOnItemClickListener = listener;
                }

/* 289 */       public final OnItemClickListener getOnItemClickListener() {
/* 290 */           return this.mOnItemClickListener;
                }

/* 302 */       public boolean performItemClick(View view, int position, long id) {
/* 303 */           if (this.mOnItemClickListener == null) {
                        return false;
                    }
/* 304 */           playSoundEffect(0);
/* 305 */           if (view != null) {
/* 306 */               view.sendAccessibilityEvent(1);
                    }
/* 308 */           this.mOnItemClickListener.onItemClick(this, view, position, id);
                    return true;
                }

/* 344 */       public void setOnItemLongClickListener(OnItemLongClickListener listener) {
/* 345 */           if (!isLongClickable()) {
/* 346 */               setLongClickable(true);
                    }
/* 348 */           this.mOnItemLongClickListener = listener;
                }

/* 355 */       public final OnItemLongClickListener getOnItemLongClickListener() {
/* 356 */           return this.mOnItemLongClickListener;
                }

/* 397 */       public void setOnItemSelectedListener(OnItemSelectedListener listener) {
/* 398 */           this.mOnItemSelectedListener = listener;
                }

/* 401 */       public final OnItemSelectedListener getOnItemSelectedListener() {
/* 402 */           return this.mOnItemSelectedListener;
                }

/* 411 */       public static class AdapterContextMenuInfo implements ContextMenu.ContextMenuInfo {

                    /* JADX INFO: renamed from: id */
                    public long f2id;
                    public int position;
                    public View targetView;

/* 412 */           public AdapterContextMenuInfo(View targetView, int position, long id) {
/* 414 */               this.targetView = targetView;
/* 415 */               this.position = position;
/* 416 */               this.f2id = id;
                    }
                }

                @Override // android.view.ViewGroup
/* 460 */       public void addView(View child) {
/* 461 */           throw new UnsupportedOperationException("addView(View) is not supported in AdapterView");
                }

                @Override // android.view.ViewGroup
/* 473 */       public void addView(View child, int index) {
/* 474 */           throw new UnsupportedOperationException("addView(View, int) is not supported in AdapterView");
                }

                @Override // android.view.ViewGroup, android.view.ViewManager
/* 486 */       public void addView(View child, ViewGroup.LayoutParams params) {
/* 487 */           throw new UnsupportedOperationException("addView(View, LayoutParams) is not supported in AdapterView");
                }

                @Override // android.view.ViewGroup
/* 501 */       public void addView(View child, int index, ViewGroup.LayoutParams params) {
/* 502 */           throw new UnsupportedOperationException("addView(View, int, LayoutParams) is not supported in AdapterView");
                }

                @Override // android.view.ViewGroup, android.view.ViewManager
/* 514 */       public void removeView(View child) {
/* 515 */           throw new UnsupportedOperationException("removeView(View) is not supported in AdapterView");
                }

                @Override // android.view.ViewGroup
/* 526 */       public void removeViewAt(int index) {
/* 527 */           throw new UnsupportedOperationException("removeViewAt(int) is not supported in AdapterView");
                }

                @Override // android.view.ViewGroup
/* 536 */       public void removeAllViews() {
/* 537 */           throw new UnsupportedOperationException("removeAllViews() is not supported in AdapterView");
                }

                @Override // android.view.ViewGroup, android.view.View
/* 541 */       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 542 */           this.mLayoutHeight = getHeight();
                }

                @ViewDebug.CapturedViewProperty
/* 551 */       public int getSelectedItemPosition() {
/* 552 */           return this.mNextSelectedPosition;
                }

                @ViewDebug.CapturedViewProperty
/* 560 */       public long getSelectedItemId() {
/* 561 */           return this.mNextSelectedRowId;
                }

/* 574 */       public Object getSelectedItem() {
/* 575 */           Adapter adapter = getAdapter();
/* 576 */           int selection = getSelectedItemPosition();
/* 577 */           if (adapter == null || adapter.getCount() <= 0 || selection < 0) {
                        return null;
                    }
/* 578 */           return adapter.getItem(selection);
                }

                @ViewDebug.CapturedViewProperty
/* 590 */       public int getCount() {
/* 591 */           return this.mItemCount;
                }

/* 603 */       public int getPositionForView(View view) {
/* 604 */           View listItem = view;
                    while (true) {
                        try {
/* 607 */                   View v = (View) listItem.getParent();
                            if (v.equals(this)) {
                                break;
                            }
/* 608 */                   listItem = v;
                        } catch (ClassCastException e) {
                            return -1;
                        }
                    }
/* 616 */           int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 618 */               if (getChildAt(i).equals(listItem)) {
/* 619 */                   return this.mFirstPosition + i;
                        }
                    }
                    return -1;
                }

/* 633 */       public int getFirstVisiblePosition() {
/* 634 */           return this.mFirstPosition;
                }

/* 643 */       public int getLastVisiblePosition() {
/* 644 */           return (this.mFirstPosition + getChildCount()) - 1;
                }

/* 658 */       public void setEmptyView(View emptyView) {
/* 659 */           this.mEmptyView = emptyView;
/* 661 */           Adapter adapter = getAdapter();
/* 662 */           boolean empty = adapter == null || adapter.isEmpty();
/* 663 */           updateEmptyStatus(empty);
                }

/* 673 */       public View getEmptyView() {
/* 674 */           return this.mEmptyView;
                }

/* 683 */       boolean isInFilterMode() {
/* 684 */           return false;
                }

                @Override // android.view.View
/* 688 */       public void setFocusable(boolean focusable) {
                    boolean z = true;
/* 689 */           Adapter adapter = getAdapter();
/* 690 */           boolean empty = adapter == null || adapter.getCount() == 0;
/* 692 */           this.mDesiredFocusableState = focusable;
/* 693 */           if (!focusable) {
/* 694 */               this.mDesiredFocusableInTouchModeState = false;
                    }
/* 697 */           if (!focusable || (empty && !isInFilterMode())) {
                        z = false;
                    }
                    super.setFocusable(z);
                }

                @Override // android.view.View
/* 701 */       public void setFocusableInTouchMode(boolean focusable) {
                    boolean z = true;
/* 702 */           Adapter adapter = getAdapter();
/* 703 */           boolean empty = adapter == null || adapter.getCount() == 0;
/* 705 */           this.mDesiredFocusableInTouchModeState = focusable;
/* 706 */           if (focusable) {
/* 707 */               this.mDesiredFocusableState = true;
                    }
/* 710 */           if (!focusable || (empty && !isInFilterMode())) {
                        z = false;
                    }
                    super.setFocusableInTouchMode(z);
                }

/* 713 */       void checkFocus() {
/* 714 */           Adapter adapter = getAdapter();
/* 715 */           boolean empty = adapter == null || adapter.getCount() == 0;
/* 716 */           boolean focusable = !empty || isInFilterMode();
/* 720 */           super.setFocusableInTouchMode(focusable && this.mDesiredFocusableInTouchModeState);
/* 721 */           super.setFocusable(focusable && this.mDesiredFocusableState);
/* 722 */           if (this.mEmptyView != null) {
/* 723 */               updateEmptyStatus(adapter == null || adapter.isEmpty());
                    }
                }

/* 732 */       private void updateEmptyStatus(boolean empty) {
/* 733 */           if (isInFilterMode()) {
/* 734 */               empty = false;
                    }
/* 737 */           if (empty) {
/* 738 */               if (this.mEmptyView != null) {
/* 739 */                   this.mEmptyView.setVisibility(0);
/* 740 */                   setVisibility(8);
                        } else {
/* 743 */                   setVisibility(0);
                        }
/* 749 */               if (this.mDataChanged) {
/* 750 */                   onLayout(false, getLeft(), getTop(), getRight(), getBottom());
/* 756 */                   return;
                        }
/* 756 */               return;
                    }
/* 753 */           if (this.mEmptyView != null) {
                        this.mEmptyView.setVisibility(8);
                    }
/* 754 */           setVisibility(0);
                }

/* 764 */       public Object getItemAtPosition(int position) {
/* 765 */           Adapter adapter = getAdapter();
/* 766 */           if (adapter == null || position < 0) {
                        return null;
                    }
                    return adapter.getItem(position);
                }

/* 769 */       public long getItemIdAtPosition(int position) {
/* 770 */           Adapter adapter = getAdapter();
/* 771 */           if (adapter == null || position < 0) {
                        return Long.MIN_VALUE;
                    }
                    return adapter.getItemId(position);
                }

                @Override // android.view.View
/* 775 */       public void setOnClickListener(View.OnClickListener l) {
/* 776 */           throw new RuntimeException("Don't call setOnClickListener for an AdapterView. You probably want setOnItemClickListener instead");
                }

                @Override // android.view.ViewGroup, android.view.View
/* 784 */       protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
/* 785 */           dispatchFreezeSelfOnly(container);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 792 */       protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
/* 793 */           dispatchThawSelfOnly(container);
                }

/* 794 */       class AdapterDataSetObserver extends DataSetObserver {
                    private Parcelable mInstanceState = null;

/* 795 */           AdapterDataSetObserver() {
                    }

                    @Override // android.database.DataSetObserver
/* 801 */           public void onChanged() {
/* 802 */               AdapterViewICS.this.mDataChanged = true;
/* 803 */               AdapterViewICS.this.mOldItemCount = AdapterViewICS.this.mItemCount;
/* 804 */               AdapterViewICS.this.mItemCount = AdapterViewICS.this.getAdapter().getCount();
/* 808 */               if (AdapterViewICS.this.getAdapter().hasStableIds() && this.mInstanceState != null && AdapterViewICS.this.mOldItemCount == 0 && AdapterViewICS.this.mItemCount > 0) {
/* 810 */                   AdapterViewICS.access$000(AdapterViewICS.this, this.mInstanceState);
/* 811 */                   this.mInstanceState = null;
                        } else {
/* 813 */                   AdapterViewICS.this.rememberSyncState();
                        }
/* 815 */               AdapterViewICS.this.checkFocus();
/* 816 */               AdapterViewICS.this.requestLayout();
                    }

                    @Override // android.database.DataSetObserver
/* 820 */           public void onInvalidated() {
/* 821 */               AdapterViewICS.this.mDataChanged = true;
/* 823 */               if (AdapterViewICS.this.getAdapter().hasStableIds()) {
/* 826 */                   this.mInstanceState = AdapterViewICS.access$100(AdapterViewICS.this);
                        }
/* 830 */               AdapterViewICS.this.mOldItemCount = AdapterViewICS.this.mItemCount;
/* 831 */               AdapterViewICS.this.mItemCount = 0;
/* 832 */               AdapterViewICS.this.mSelectedPosition = -1;
/* 833 */               AdapterViewICS.this.mSelectedRowId = Long.MIN_VALUE;
/* 834 */               AdapterViewICS.this.mNextSelectedPosition = -1;
/* 835 */               AdapterViewICS.this.mNextSelectedRowId = Long.MIN_VALUE;
/* 836 */               AdapterViewICS.this.mNeedSync = false;
/* 838 */               AdapterViewICS.this.checkFocus();
/* 839 */               AdapterViewICS.this.requestLayout();
                    }

/* 842 */           public void clearSavedState() {
/* 843 */               this.mInstanceState = null;
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 848 */       protected void onDetachedFromWindow() {
/* 849 */           super.onDetachedFromWindow();
/* 850 */           removeCallbacks(this.mSelectionNotifier);
                }

/* 851 */       private class SelectionNotifier implements Runnable {
/* 852 */           private SelectionNotifier() {
                    }

/* 852 */           /* synthetic */ SelectionNotifier(AdapterViewICS x0, C01011 x1) {
/* 853 */               this();
                    }

                    @Override // java.lang.Runnable
/* 854 */           public void run() {
/* 855 */               if (AdapterViewICS.this.mDataChanged) {
/* 859 */                   if (AdapterViewICS.this.getAdapter() != null) {
/* 860 */                       AdapterViewICS.this.post(this);
/* 865 */                       return;
                            }
/* 865 */                   return;
                        }
/* 863 */               AdapterViewICS.access$200(AdapterViewICS.this);
                    }
                }

/* 868 */       void selectionChanged() {
/* 869 */           if (this.mOnItemSelectedListener != null) {
/* 870 */               if (this.mInLayout || this.mBlockLayoutRequests) {
/* 875 */                   if (this.mSelectionNotifier == null) {
/* 876 */                       this.mSelectionNotifier = new SelectionNotifier(this, null);
                            }
/* 878 */                   post(this.mSelectionNotifier);
                        } else {
/* 880 */                   fireOnSelected();
                        }
                    }
/* 885 */           if (this.mSelectedPosition != -1 && isShown() && !isInTouchMode()) {
/* 886 */               sendAccessibilityEvent(4);
                    }
                }

/* 890 */       private void fireOnSelected() {
/* 891 */           if (this.mOnItemSelectedListener != null) {
/* 894 */               int selection = getSelectedItemPosition();
/* 895 */               if (selection >= 0) {
/* 896 */                   View v = getSelectedView();
/* 897 */                   this.mOnItemSelectedListener.onItemSelected(this, v, selection, getAdapter().getItemId(selection));
                        } else {
/* 900 */                   this.mOnItemSelectedListener.onNothingSelected(this);
                        }
                    }
                }

                @Override // android.view.View
/* 905 */       public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
/* 906 */           View selectedView = getSelectedView();
                    return selectedView != null && selectedView.getVisibility() == 0 && selectedView.dispatchPopulateAccessibilityEvent(event);
                }

                @Override // android.view.ViewGroup
/* 915 */       protected boolean canAnimate() {
                    return super.canAnimate() && this.mItemCount > 0;
                }

/* 919 */       void handleDataChanged() {
/* 920 */           int count = this.mItemCount;
/* 921 */           boolean found = false;
/* 923 */           if (count > 0) {
/* 928 */               if (this.mNeedSync) {
/* 931 */                   this.mNeedSync = false;
/* 935 */                   int newPos = findSyncPosition();
/* 936 */                   if (newPos >= 0 && lookForSelectablePosition(newPos, true) == newPos) {
/* 941 */                       setNextSelectedPositionInt(newPos);
/* 942 */                       found = true;
                            }
                        }
/* 946 */               if (!found) {
/* 948 */                   int newPos2 = getSelectedItemPosition();
/* 951 */                   if (newPos2 >= count) {
/* 952 */                       newPos2 = count - 1;
                            }
/* 954 */                   if (newPos2 < 0) {
/* 955 */                       newPos2 = 0;
                            }
/* 959 */                   int selectablePos = lookForSelectablePosition(newPos2, true);
/* 960 */                   if (selectablePos < 0) {
/* 962 */                       selectablePos = lookForSelectablePosition(newPos2, false);
                            }
/* 964 */                   if (selectablePos >= 0) {
/* 965 */                       setNextSelectedPositionInt(selectablePos);
/* 966 */                       checkSelectionChanged();
/* 967 */                       found = true;
                            }
                        }
                    }
/* 971 */           if (!found) {
/* 973 */               this.mSelectedPosition = -1;
/* 974 */               this.mSelectedRowId = Long.MIN_VALUE;
/* 975 */               this.mNextSelectedPosition = -1;
/* 976 */               this.mNextSelectedRowId = Long.MIN_VALUE;
/* 977 */               this.mNeedSync = false;
/* 978 */               checkSelectionChanged();
                    }
                }

/* 982 */       void checkSelectionChanged() {
/* 983 */           if (this.mSelectedPosition != this.mOldSelectedPosition || this.mSelectedRowId != this.mOldSelectedRowId) {
/* 984 */               selectionChanged();
/* 985 */               this.mOldSelectedPosition = this.mSelectedPosition;
/* 986 */               this.mOldSelectedRowId = this.mSelectedRowId;
                    }
                }

/* 998 */       int findSyncPosition() {
/* 999 */           int count = this.mItemCount;
/* 1001 */          if (count == 0) {
/* 1002 */              return -1;
                    }
/* 1005 */          long idToMatch = this.mSyncRowId;
/* 1006 */          int seed = this.mSyncPosition;
                    if (idToMatch == Long.MIN_VALUE) {
/* 1010 */              return -1;
                    }
/* 1014 */          int seed2 = Math.min(count - 1, Math.max(0, seed));
/* 1017 */          long endTime = SystemClock.uptimeMillis() + 100;
/* 1022 */          int first = seed2;
/* 1025 */          int last = seed2;
/* 1028 */          boolean next = false;
/* 1038 */          Adapter adapter = getAdapter();
/* 1039 */          if (adapter == null) {
/* 1040 */              return -1;
                    }
                    while (SystemClock.uptimeMillis() <= endTime) {
/* 1044 */              long rowId = adapter.getItemId(seed2);
                        if (rowId != idToMatch) {
/* 1050 */                  boolean hitLast = last == count + (-1);
/* 1051 */                  boolean hitFirst = first == 0;
/* 1053 */                  if (hitLast && hitFirst) {
                                break;
                            }
/* 1058 */                  if (hitFirst || (next && !hitLast)) {
/* 1060 */                      last++;
/* 1061 */                      seed2 = last;
/* 1063 */                      next = false;
                            } else if (hitLast || (!next && !hitFirst)) {
/* 1066 */                      first--;
/* 1067 */                      seed2 = first;
/* 1069 */                      next = true;
                            }
                        } else {
/* 1074 */                  return seed2;
                        }
                    }
/* 1074 */          return -1;
                }

/* 1085 */      int lookForSelectablePosition(int position, boolean lookDown) {
/* 1086 */          return position;
                }

/* 1093 */      void setSelectedPositionInt(int position) {
/* 1094 */          this.mSelectedPosition = position;
/* 1095 */          this.mSelectedRowId = getItemIdAtPosition(position);
                }

/* 1103 */      void setNextSelectedPositionInt(int position) {
/* 1104 */          this.mNextSelectedPosition = position;
/* 1105 */          this.mNextSelectedRowId = getItemIdAtPosition(position);
/* 1107 */          if (this.mNeedSync && this.mSyncMode == 0 && position >= 0) {
/* 1108 */              this.mSyncPosition = position;
/* 1109 */              this.mSyncRowId = this.mNextSelectedRowId;
                    }
                }

/* 1118 */      void rememberSyncState() {
/* 1119 */          if (getChildCount() > 0) {
/* 1120 */              this.mNeedSync = true;
/* 1121 */              this.mSyncHeight = this.mLayoutHeight;
/* 1122 */              if (this.mSelectedPosition >= 0) {
/* 1124 */                  View v = getChildAt(this.mSelectedPosition - this.mFirstPosition);
/* 1125 */                  this.mSyncRowId = this.mNextSelectedRowId;
/* 1126 */                  this.mSyncPosition = this.mNextSelectedPosition;
/* 1127 */                  if (v != null) {
/* 1128 */                      this.mSpecificTop = v.getTop();
                            }
/* 1130 */                  this.mSyncMode = 0;
/* 1147 */                  return;
                        }
/* 1133 */              View v2 = getChildAt(0);
/* 1134 */              Adapter adapter = getAdapter();
/* 1135 */              if (this.mFirstPosition >= 0 && this.mFirstPosition < adapter.getCount()) {
/* 1136 */                  this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
                        } else {
/* 1138 */                  this.mSyncRowId = -1L;
                        }
/* 1140 */              this.mSyncPosition = this.mFirstPosition;
/* 1141 */              if (v2 != null) {
/* 1142 */                  this.mSpecificTop = v2.getTop();
                        }
/* 1144 */              this.mSyncMode = 1;
                    }
                }
            }
