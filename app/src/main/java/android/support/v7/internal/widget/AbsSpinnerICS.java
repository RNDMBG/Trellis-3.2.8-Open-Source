            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.database.DataSetObserver;
            import android.graphics.Rect;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.util.AttributeSet;
            import android.util.SparseArray;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Adapter;
            import android.widget.SpinnerAdapter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    abstract class AbsSpinnerICS extends AdapterViewICS<SpinnerAdapter> {
                SpinnerAdapter mAdapter;
                boolean mBlockLayoutRequests;
                private DataSetObserver mDataSetObserver;
                int mHeightMeasureSpec;
                final RecycleBin mRecycler;
                int mSelectionBottomPadding;
                int mSelectionLeftPadding;
                int mSelectionRightPadding;
                int mSelectionTopPadding;
                final Rect mSpinnerPadding;
                private Rect mTouchFrame;
                int mWidthMeasureSpec;

                abstract void layout(int i, boolean z);

/* 33 */        static /* synthetic */ void access$100(AbsSpinnerICS x0, View x1, boolean x2) {
/* 34 */            x0.removeDetachedView(x1, x2);
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS
/* 33 */        public /* bridge */ /* synthetic */ Adapter getAdapter() {
/* 34 */            return getAdapter();
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS
/* 33 */        public /* bridge */ /* synthetic */ void setAdapter(Adapter x0) {
/* 34 */            setAdapter((SpinnerAdapter) x0);
                }

/* 53 */        AbsSpinnerICS(Context context) {
/* 54 */            super(context);
/* 41 */            this.mSelectionLeftPadding = 0;
/* 42 */            this.mSelectionTopPadding = 0;
/* 43 */            this.mSelectionRightPadding = 0;
/* 44 */            this.mSelectionBottomPadding = 0;
/* 45 */            this.mSpinnerPadding = new Rect();
/* 47 */            this.mRecycler = new RecycleBin();
/* 55 */            initAbsSpinner();
                }

/* 58 */        AbsSpinnerICS(Context context, AttributeSet attrs) {
/* 59 */            this(context, attrs, 0);
                }

/* 62 */        AbsSpinnerICS(Context context, AttributeSet attrs, int defStyle) {
/* 63 */            super(context, attrs, defStyle);
/* 41 */            this.mSelectionLeftPadding = 0;
/* 42 */            this.mSelectionTopPadding = 0;
/* 43 */            this.mSelectionRightPadding = 0;
/* 44 */            this.mSelectionBottomPadding = 0;
/* 45 */            this.mSpinnerPadding = new Rect();
/* 47 */            this.mRecycler = new RecycleBin();
/* 64 */            initAbsSpinner();
                }

/* 70 */        private void initAbsSpinner() {
/* 71 */            setFocusable(true);
/* 72 */            setWillNotDraw(false);
                }

/* 82 */        public void setAdapter(SpinnerAdapter adapter) {
/* 83 */            if (this.mAdapter != null) {
/* 84 */                this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
/* 85 */                resetList();
                    }
/* 88 */            this.mAdapter = adapter;
/* 90 */            this.mOldSelectedPosition = -1;
/* 91 */            this.mOldSelectedRowId = Long.MIN_VALUE;
/* 93 */            if (this.mAdapter != null) {
/* 94 */                this.mOldItemCount = this.mItemCount;
/* 95 */                this.mItemCount = this.mAdapter.getCount();
/* 96 */                checkFocus();
/* 98 */                this.mDataSetObserver = new AdapterViewICS.AdapterDataSetObserver();
/* 99 */                this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
/* 101 */               int position = this.mItemCount > 0 ? 0 : -1;
/* 103 */               setSelectedPositionInt(position);
/* 104 */               setNextSelectedPositionInt(position);
/* 106 */               if (this.mItemCount == 0) {
/* 108 */                   checkSelectionChanged();
                        }
                    } else {
/* 112 */               checkFocus();
/* 113 */               resetList();
/* 115 */               checkSelectionChanged();
                    }
/* 118 */           requestLayout();
                }

/* 124 */       void resetList() {
/* 125 */           this.mDataChanged = false;
/* 126 */           this.mNeedSync = false;
/* 128 */           removeAllViewsInLayout();
/* 129 */           this.mOldSelectedPosition = -1;
/* 130 */           this.mOldSelectedRowId = Long.MIN_VALUE;
/* 132 */           setSelectedPositionInt(-1);
/* 133 */           setNextSelectedPositionInt(-1);
/* 134 */           invalidate();
                }

                @Override // android.view.View
/* 146 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 147 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 151 */           int paddingLeft = getPaddingLeft();
/* 152 */           int paddingTop = getPaddingTop();
/* 153 */           int paddingRight = getPaddingRight();
/* 154 */           int paddingBottom = getPaddingBottom();
/* 156 */           Rect rect = this.mSpinnerPadding;
                    if (paddingLeft <= this.mSelectionLeftPadding) {
/* 156 */               paddingLeft = this.mSelectionLeftPadding;
                    }
                    rect.left = paddingLeft;
/* 158 */           Rect rect2 = this.mSpinnerPadding;
                    if (paddingTop <= this.mSelectionTopPadding) {
/* 158 */               paddingTop = this.mSelectionTopPadding;
                    }
                    rect2.top = paddingTop;
/* 160 */           Rect rect3 = this.mSpinnerPadding;
                    if (paddingRight <= this.mSelectionRightPadding) {
/* 160 */               paddingRight = this.mSelectionRightPadding;
                    }
                    rect3.right = paddingRight;
/* 162 */           Rect rect4 = this.mSpinnerPadding;
                    if (paddingBottom <= this.mSelectionBottomPadding) {
/* 162 */               paddingBottom = this.mSelectionBottomPadding;
                    }
                    rect4.bottom = paddingBottom;
/* 165 */           if (this.mDataChanged) {
/* 166 */               handleDataChanged();
                    }
/* 169 */           int preferredHeight = 0;
/* 170 */           int preferredWidth = 0;
/* 171 */           boolean needsMeasuring = true;
/* 173 */           int selectedPosition = getSelectedItemPosition();
/* 174 */           if (selectedPosition >= 0 && this.mAdapter != null && selectedPosition < this.mAdapter.getCount()) {
/* 176 */               View view = this.mRecycler.get(selectedPosition);
/* 177 */               if (view == null) {
/* 179 */                   view = this.mAdapter.getView(selectedPosition, null, this);
                        }
/* 182 */               if (view != null) {
/* 184 */                   this.mRecycler.put(selectedPosition, view);
                        }
/* 187 */               if (view != null) {
/* 188 */                   if (view.getLayoutParams() == null) {
/* 189 */                       this.mBlockLayoutRequests = true;
/* 190 */                       view.setLayoutParams(generateDefaultLayoutParams());
/* 191 */                       this.mBlockLayoutRequests = false;
                            }
/* 193 */                   measureChild(view, widthMeasureSpec, heightMeasureSpec);
/* 195 */                   preferredHeight = getChildHeight(view) + this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
/* 196 */                   preferredWidth = getChildWidth(view) + this.mSpinnerPadding.left + this.mSpinnerPadding.right;
/* 198 */                   needsMeasuring = false;
                        }
                    }
/* 202 */           if (needsMeasuring) {
/* 204 */               preferredHeight = this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
/* 205 */               if (widthMode == 0) {
/* 206 */                   preferredWidth = this.mSpinnerPadding.left + this.mSpinnerPadding.right;
                        }
                    }
/* 210 */           int preferredHeight2 = Math.max(preferredHeight, getSuggestedMinimumHeight());
/* 211 */           int preferredWidth2 = Math.max(preferredWidth, getSuggestedMinimumWidth());
/* 213 */           int heightSize = resolveSize(preferredHeight2, heightMeasureSpec);
/* 214 */           int widthSize = resolveSize(preferredWidth2, widthMeasureSpec);
/* 216 */           setMeasuredDimension(widthSize, heightSize);
/* 217 */           this.mHeightMeasureSpec = heightMeasureSpec;
/* 218 */           this.mWidthMeasureSpec = widthMeasureSpec;
                }

/* 221 */       int getChildHeight(View child) {
/* 222 */           return child.getMeasuredHeight();
                }

/* 225 */       int getChildWidth(View child) {
/* 226 */           return child.getMeasuredWidth();
                }

                @Override // android.view.ViewGroup
/* 230 */       protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 231 */           return new ViewGroup.LayoutParams(-1, -2);
                }

/* 236 */       void recycleAllViews() {
/* 237 */           int childCount = getChildCount();
/* 238 */           RecycleBin recycleBin = this.mRecycler;
/* 239 */           int position = this.mFirstPosition;
                    for (int i = 0; i < childCount; i++) {
/* 243 */               View v = getChildAt(i);
/* 244 */               int index = position + i;
/* 245 */               recycleBin.put(index, v);
                    }
                }

/* 253 */       public void setSelection(int position, boolean animate) {
/* 254 */           boolean shouldAnimate = animate && this.mFirstPosition <= position && position <= (this.mFirstPosition + getChildCount()) + (-1);
/* 256 */           setSelectionInt(position, shouldAnimate);
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS
/* 260 */       public void setSelection(int position) {
/* 261 */           setNextSelectedPositionInt(position);
/* 262 */           requestLayout();
/* 263 */           invalidate();
                }

/* 274 */       void setSelectionInt(int position, boolean animate) {
/* 275 */           if (position != this.mOldSelectedPosition) {
/* 276 */               this.mBlockLayoutRequests = true;
/* 277 */               int delta = position - this.mSelectedPosition;
/* 278 */               setNextSelectedPositionInt(position);
/* 279 */               layout(delta, animate);
/* 280 */               this.mBlockLayoutRequests = false;
                    }
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS
/* 287 */       public View getSelectedView() {
/* 288 */           if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
                        return null;
                    }
/* 289 */           return getChildAt(this.mSelectedPosition - this.mFirstPosition);
                }

                @Override // android.view.View, android.view.ViewParent
/* 302 */       public void requestLayout() {
/* 303 */           if (!this.mBlockLayoutRequests) {
/* 304 */               super.requestLayout();
                    }
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS
/* 309 */       public SpinnerAdapter getAdapter() {
/* 310 */           return this.mAdapter;
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS
/* 314 */       public int getCount() {
/* 315 */           return this.mItemCount;
                }

/* 326 */       public int pointToPosition(int x, int y) {
/* 327 */           Rect frame = this.mTouchFrame;
/* 328 */           if (frame == null) {
/* 329 */               this.mTouchFrame = new Rect();
/* 330 */               frame = this.mTouchFrame;
                    }
/* 333 */           int count = getChildCount();
                    for (int i = count - 1; i >= 0; i--) {
/* 335 */               View child = getChildAt(i);
/* 336 */               if (child.getVisibility() == 0) {
/* 337 */                   child.getHitRect(frame);
/* 338 */                   if (frame.contains(x, y)) {
/* 339 */                       return this.mFirstPosition + i;
                            }
                        }
                    }
/* 343 */           return -1;
                }

/* 344 */       static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00911();
                    int position;
                    long selectedId;

/* 345 */           /* synthetic */ SavedState(Parcel x0, C00901 x1) {
/* 346 */               this(x0);
                    }

/* 353 */           SavedState(Parcelable superState) {
/* 354 */               super(superState);
                    }

/* 360 */           private SavedState(Parcel in) {
/* 361 */               super(in);
/* 362 */               this.selectedId = in.readLong();
/* 363 */               this.position = in.readInt();
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 367 */           public void writeToParcel(Parcel out, int flags) {
/* 368 */               super.writeToParcel(out, flags);
/* 369 */               out.writeLong(this.selectedId);
/* 370 */               out.writeInt(this.position);
                    }

/* 374 */           public String toString() {
/* 375 */               return "AbsSpinner.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " position=" + this.position + "}";
                    }

                    /* JADX INFO: renamed from: android.support.v7.internal.widget.AbsSpinnerICS$SavedState$1 */
/* 380 */           static class C00911 implements Parcelable.Creator<SavedState> {
/* 381 */               C00911() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 381 */               public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 382 */                   return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 381 */               public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 382 */                   return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 383 */               public SavedState createFromParcel(Parcel in) {
/* 384 */                   return new SavedState(in, null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 387 */               public SavedState[] newArray(int size) {
/* 388 */                   return new SavedState[size];
                        }
                    }
                }

                @Override // android.view.View
/* 394 */       public Parcelable onSaveInstanceState() {
/* 395 */           Parcelable superState = super.onSaveInstanceState();
/* 396 */           SavedState ss = new SavedState(superState);
/* 397 */           ss.selectedId = getSelectedItemId();
                    if (ss.selectedId >= 0) {
/* 399 */               ss.position = getSelectedItemPosition();
                    } else {
/* 401 */               ss.position = -1;
                    }
/* 403 */           return ss;
                }

                @Override // android.view.View
/* 407 */       public void onRestoreInstanceState(Parcelable state) {
/* 408 */           SavedState ss = (SavedState) state;
/* 410 */           super.onRestoreInstanceState(ss.getSuperState());
                    if (ss.selectedId >= 0) {
/* 413 */               this.mDataChanged = true;
/* 414 */               this.mNeedSync = true;
/* 415 */               this.mSyncRowId = ss.selectedId;
/* 416 */               this.mSyncPosition = ss.position;
/* 417 */               this.mSyncMode = 0;
/* 418 */               requestLayout();
                    }
                }

/* 420 */       class RecycleBin {
                    private final SparseArray<View> mScrapHeap = new SparseArray<>();

/* 421 */           RecycleBin() {
                    }

/* 425 */           public void put(int position, View v) {
/* 426 */               this.mScrapHeap.put(position, v);
                    }

/* 430 */           View get(int position) {
/* 431 */               View result = this.mScrapHeap.get(position);
/* 432 */               if (result != null) {
/* 434 */                   this.mScrapHeap.delete(position);
                        }
/* 438 */               return result;
                    }

/* 441 */           void clear() {
/* 442 */               SparseArray<View> scrapHeap = this.mScrapHeap;
/* 443 */               int count = scrapHeap.size();
                        for (int i = 0; i < count; i++) {
/* 445 */                   View view = scrapHeap.valueAt(i);
/* 446 */                   if (view != null) {
/* 447 */                       AbsSpinnerICS.access$100(AbsSpinnerICS.this, view, true);
                            }
                        }
/* 450 */               scrapHeap.clear();
                    }
                }
            }
