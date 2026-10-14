            package android.support.v7.internal.widget;

            import android.app.AlertDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.res.TypedArray;
            import android.database.DataSetObserver;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Adapter;
            import android.widget.AdapterView;
            import android.widget.ListAdapter;
            import android.widget.SpinnerAdapter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    class SpinnerICS extends AbsSpinnerICS implements DialogInterface.OnClickListener {
                private static final int MAX_ITEMS_MEASURED = 15;
                static final int MODE_DIALOG = 0;
                static final int MODE_DROPDOWN = 1;
                private static final int MODE_THEME = -1;
                private static final String TAG = "Spinner";
                int mDropDownWidth;
                private int mGravity;
                private SpinnerPopup mPopup;
                private DropDownAdapter mTempAdapter;
                private Rect mTempRect;

                private interface SpinnerPopup {
                    void dismiss();

                    CharSequence getHintText();

                    boolean isShowing();

                    void setAdapter(ListAdapter listAdapter);

                    void setPromptText(CharSequence charSequence);

                    void show();
                }

/* 47 */        static /* synthetic */ Rect access$200(SpinnerICS x0) {
/* 48 */            return x0.mTempRect;
                }

                @Override // android.support.v7.internal.widget.AbsSpinnerICS, android.support.v7.internal.widget.AdapterViewICS
/* 47 */        public /* bridge */ /* synthetic */ void setAdapter(Adapter x0) {
/* 48 */            setAdapter((SpinnerAdapter) x0);
                }

/* 83 */        SpinnerICS(Context context) {
/* 84 */            this(context, (AttributeSet) null);
                }

/* 99 */        SpinnerICS(Context context, int mode) {
/* 100 */           this(context, null, C0084R.attr.spinnerStyle, mode);
                }

/* 110 */       SpinnerICS(Context context, AttributeSet attrs) {
/* 111 */           this(context, attrs, C0084R.attr.spinnerStyle);
                }

/* 126 */       SpinnerICS(Context context, AttributeSet attrs, int defStyle) {
/* 127 */           this(context, attrs, defStyle, -1);
                }

/* 147 */       SpinnerICS(Context context, AttributeSet attrs, int defStyle, int mode) {
/* 148 */           super(context, attrs, defStyle);
/* 75 */            this.mTempRect = new Rect();
/* 150 */           TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.Spinner, defStyle, 0);
                    switch (mode == -1 ? a.getInt(7, 0) : mode) {
                        case 0:
/* 159 */                   this.mPopup = new DialogPopup(this, null);
                            break;
                        case 1:
/* 164 */                   DropdownPopup popup = new DropdownPopup(context, attrs, defStyle);
/* 166 */                   this.mDropDownWidth = a.getLayoutDimension(3, -2);
/* 169 */                   popup.setBackgroundDrawable(a.getDrawable(2));
/* 172 */                   int verticalOffset = a.getDimensionPixelOffset(5, 0);
/* 174 */                   if (verticalOffset != 0) {
/* 175 */                       popup.setVerticalOffset(verticalOffset);
                            }
/* 178 */                   int horizontalOffset = a.getDimensionPixelOffset(4, 0);
/* 180 */                   if (horizontalOffset != 0) {
/* 181 */                       popup.setHorizontalOffset(horizontalOffset);
                            }
/* 184 */                   this.mPopup = popup;
                            break;
                    }
/* 189 */           this.mGravity = a.getInt(0, 17);
/* 191 */           this.mPopup.setPromptText(a.getString(6));
/* 193 */           a.recycle();
/* 197 */           if (this.mTempAdapter != null) {
/* 198 */               this.mPopup.setAdapter(this.mTempAdapter);
/* 199 */               this.mTempAdapter = null;
                    }
                }

/* 211 */       public void setGravity(int gravity) {
/* 212 */           if (this.mGravity != gravity) {
/* 213 */               if ((gravity & 7) == 0) {
/* 214 */                   gravity |= 3;
                        }
/* 216 */               this.mGravity = gravity;
/* 217 */               requestLayout();
                    }
                }

                @Override // android.support.v7.internal.widget.AbsSpinnerICS
/* 222 */       public void setAdapter(SpinnerAdapter adapter) {
/* 223 */           super.setAdapter(adapter);
/* 225 */           if (this.mPopup != null) {
/* 226 */               this.mPopup.setAdapter(new DropDownAdapter(adapter));
                    } else {
/* 228 */               this.mTempAdapter = new DropDownAdapter(adapter);
                    }
                }

                @Override // android.view.View
/* 233 */       public int getBaseline() {
                    int childBaseline;
/* 234 */           View child = null;
/* 236 */           if (getChildCount() > 0) {
/* 237 */               child = getChildAt(0);
                    } else if (this.mAdapter != null && this.mAdapter.getCount() > 0) {
/* 239 */               child = makeAndAddView(0);
/* 240 */               this.mRecycler.put(0, child);
/* 241 */               removeAllViewsInLayout();
                    }
/* 244 */           if (child == null || (childBaseline = child.getBaseline()) < 0) {
                        return -1;
                    }
                    return child.getTop() + childBaseline;
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS, android.view.ViewGroup, android.view.View
/* 253 */       protected void onDetachedFromWindow() {
/* 254 */           super.onDetachedFromWindow();
/* 256 */           if (this.mPopup != null && this.mPopup.isShowing()) {
/* 257 */               this.mPopup.dismiss();
                    }
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS
/* 268 */       public void setOnItemClickListener(AdapterViewICS.OnItemClickListener l) {
/* 269 */           throw new RuntimeException("setOnItemClickListener cannot be used with a spinner.");
                }

/* 272 */       void setOnItemClickListenerInt(AdapterViewICS.OnItemClickListener l) {
/* 273 */           super.setOnItemClickListener(l);
                }

                @Override // android.support.v7.internal.widget.AbsSpinnerICS, android.view.View
/* 277 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 278 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 279 */           if (this.mPopup != null && View.MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
/* 280 */               int measuredWidth = getMeasuredWidth();
/* 281 */               setMeasuredDimension(Math.min(Math.max(measuredWidth, measureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(widthMeasureSpec)), getMeasuredHeight());
                    }
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS, android.view.ViewGroup, android.view.View
/* 295 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 296 */           super.onLayout(changed, l, t, r, b);
/* 297 */           this.mInLayout = true;
/* 298 */           layout(0, false);
/* 299 */           this.mInLayout = false;
                }

                @Override // android.support.v7.internal.widget.AbsSpinnerICS
/* 309 */       void layout(int delta, boolean animate) {
/* 310 */           int childrenLeft = this.mSpinnerPadding.left;
/* 311 */           int childrenWidth = ((getRight() - getLeft()) - this.mSpinnerPadding.left) - this.mSpinnerPadding.right;
/* 313 */           if (this.mDataChanged) {
/* 314 */               handleDataChanged();
                    }
/* 318 */           if (this.mItemCount == 0) {
/* 319 */               resetList();
/* 357 */               return;
                    }
/* 323 */           if (this.mNextSelectedPosition >= 0) {
/* 324 */               setSelectedPositionInt(this.mNextSelectedPosition);
                    }
/* 327 */           recycleAllViews();
/* 330 */           removeAllViewsInLayout();
/* 333 */           this.mFirstPosition = this.mSelectedPosition;
/* 334 */           View sel = makeAndAddView(this.mSelectedPosition);
/* 335 */           int width = sel.getMeasuredWidth();
/* 336 */           int selectedOffset = childrenLeft;
                    switch (this.mGravity & 7) {
                        case 1:
/* 339 */                   selectedOffset = ((childrenWidth / 2) + childrenLeft) - (width / 2);
                            break;
                        case 5:
/* 342 */                   selectedOffset = (childrenLeft + childrenWidth) - width;
                            break;
                    }
/* 345 */           sel.offsetLeftAndRight(selectedOffset);
/* 348 */           this.mRecycler.clear();
/* 350 */           invalidate();
/* 352 */           checkSelectionChanged();
/* 354 */           this.mDataChanged = false;
/* 355 */           this.mNeedSync = false;
/* 356 */           setNextSelectedPositionInt(this.mSelectedPosition);
                }

/* 371 */       private View makeAndAddView(int position) {
                    View child;
/* 372 */           if (!this.mDataChanged && (child = this.mRecycler.get(position)) != null) {
/* 376 */               setUpChild(child);
                        return child;
                    }
/* 383 */           View child2 = this.mAdapter.getView(position, null, this);
/* 386 */           setUpChild(child2);
                    return child2;
                }

/* 400 */       private void setUpChild(View child) {
/* 401 */           ViewGroup.LayoutParams lp = child.getLayoutParams();
/* 402 */           if (lp == null) {
/* 403 */               lp = generateDefaultLayoutParams();
                    }
/* 406 */           addViewInLayout(child, 0, lp);
/* 408 */           child.setSelected(hasFocus());
/* 411 */           int childHeightSpec = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, lp.height);
/* 413 */           int childWidthSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, lp.width);
/* 417 */           child.measure(childWidthSpec, childHeightSpec);
/* 423 */           int childTop = this.mSpinnerPadding.top + ((((getMeasuredHeight() - this.mSpinnerPadding.bottom) - this.mSpinnerPadding.top) - child.getMeasuredHeight()) / 2);
/* 426 */           int childBottom = childTop + child.getMeasuredHeight();
/* 428 */           int width = child.getMeasuredWidth();
/* 430 */           int childRight = 0 + width;
/* 432 */           child.layout(0, childTop, childRight, childBottom);
                }

                @Override // android.view.View
/* 436 */       public boolean performClick() {
/* 437 */           boolean handled = super.performClick();
/* 439 */           if (!handled) {
/* 440 */               handled = true;
/* 442 */               if (!this.mPopup.isShowing()) {
/* 443 */                   this.mPopup.show();
                        }
                    }
/* 447 */           return handled;
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 450 */       public void onClick(DialogInterface dialog, int which) {
/* 451 */           setSelection(which);
/* 452 */           dialog.dismiss();
                }

/* 459 */       public void setPrompt(CharSequence prompt) {
/* 460 */           this.mPopup.setPromptText(prompt);
                }

/* 467 */       public void setPromptId(int promptId) {
/* 468 */           setPrompt(getContext().getText(promptId));
                }

/* 474 */       public CharSequence getPrompt() {
/* 475 */           return this.mPopup.getHintText();
                }

/* 478 */       int measureContentWidth(SpinnerAdapter adapter, Drawable background) {
/* 479 */           if (adapter == null) {
/* 480 */               return 0;
                    }
/* 483 */           int width = 0;
/* 484 */           View itemView = null;
/* 485 */           int itemType = 0;
/* 486 */           int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 488 */           int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 493 */           int start = Math.max(0, getSelectedItemPosition());
/* 494 */           int end = Math.min(adapter.getCount(), start + 15);
/* 495 */           int count = end - start;
                    for (int i = Math.max(0, start - (15 - count)); i < end; i++) {
/* 498 */               int positionType = adapter.getItemViewType(i);
/* 499 */               if (positionType != itemType) {
/* 500 */                   itemType = positionType;
/* 501 */                   itemView = null;
                        }
/* 503 */               itemView = adapter.getView(i, itemView, this);
/* 504 */               if (itemView.getLayoutParams() == null) {
/* 505 */                   itemView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                        }
/* 509 */               itemView.measure(widthMeasureSpec, heightMeasureSpec);
/* 510 */               width = Math.max(width, itemView.getMeasuredWidth());
                    }
/* 514 */           if (background != null) {
/* 515 */               background.getPadding(this.mTempRect);
/* 516 */               return width + this.mTempRect.left + this.mTempRect.right;
                    }
/* 519 */           return width;
                }

/* 533 */       private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
                    private SpinnerAdapter mAdapter;
                    private ListAdapter mListAdapter;

/* 534 */           public DropDownAdapter(SpinnerAdapter adapter) {
/* 536 */               this.mAdapter = adapter;
/* 537 */               if (adapter instanceof ListAdapter) {
/* 538 */                   this.mListAdapter = (ListAdapter) adapter;
                        }
                    }

                    @Override // android.widget.Adapter
/* 542 */           public int getCount() {
/* 543 */               if (this.mAdapter == null) {
                            return 0;
                        }
                        return this.mAdapter.getCount();
                    }

                    @Override // android.widget.Adapter
/* 546 */           public Object getItem(int position) {
/* 547 */               if (this.mAdapter == null) {
                            return null;
                        }
                        return this.mAdapter.getItem(position);
                    }

                    @Override // android.widget.Adapter
/* 550 */           public long getItemId(int position) {
/* 551 */               if (this.mAdapter == null) {
                            return -1L;
                        }
                        return this.mAdapter.getItemId(position);
                    }

                    @Override // android.widget.Adapter
/* 554 */           public View getView(int position, View convertView, ViewGroup parent) {
/* 555 */               return getDropDownView(position, convertView, parent);
                    }

                    @Override // android.widget.SpinnerAdapter
/* 558 */           public View getDropDownView(int position, View convertView, ViewGroup parent) {
/* 559 */               if (this.mAdapter == null) {
                            return null;
                        }
                        return this.mAdapter.getDropDownView(position, convertView, parent);
                    }

                    @Override // android.widget.Adapter
/* 563 */           public boolean hasStableIds() {
                        return this.mAdapter != null && this.mAdapter.hasStableIds();
                    }

                    @Override // android.widget.Adapter
/* 567 */           public void registerDataSetObserver(DataSetObserver observer) {
/* 568 */               if (this.mAdapter != null) {
/* 569 */                   this.mAdapter.registerDataSetObserver(observer);
                        }
                    }

                    @Override // android.widget.Adapter
/* 573 */           public void unregisterDataSetObserver(DataSetObserver observer) {
/* 574 */               if (this.mAdapter != null) {
/* 575 */                   this.mAdapter.unregisterDataSetObserver(observer);
                        }
                    }

                    @Override // android.widget.ListAdapter
/* 583 */           public boolean areAllItemsEnabled() {
/* 584 */               ListAdapter adapter = this.mListAdapter;
/* 585 */               if (adapter != null) {
/* 586 */                   return adapter.areAllItemsEnabled();
                        }
                        return true;
                    }

                    @Override // android.widget.ListAdapter
/* 596 */           public boolean isEnabled(int position) {
/* 597 */               ListAdapter adapter = this.mListAdapter;
/* 598 */               if (adapter != null) {
/* 599 */                   return adapter.isEnabled(position);
                        }
                        return true;
                    }

                    @Override // android.widget.Adapter
/* 605 */           public int getItemViewType(int position) {
/* 606 */               return 0;
                    }

                    @Override // android.widget.Adapter
/* 609 */           public int getViewTypeCount() {
/* 610 */               return 1;
                    }

                    @Override // android.widget.Adapter
/* 613 */           public boolean isEmpty() {
                        return getCount() == 0;
                    }
                }

/* 647 */       private class DialogPopup implements SpinnerPopup, DialogInterface.OnClickListener {
                    private ListAdapter mListAdapter;
                    private AlertDialog mPopup;
                    private CharSequence mPrompt;

/* 648 */           private DialogPopup() {
                    }

/* 648 */           /* synthetic */ DialogPopup(SpinnerICS x0, C01071 x1) {
/* 649 */               this();
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 654 */           public void dismiss() {
/* 655 */               this.mPopup.dismiss();
/* 656 */               this.mPopup = null;
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 659 */           public boolean isShowing() {
/* 660 */               if (this.mPopup != null) {
                            return this.mPopup.isShowing();
                        }
                        return false;
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 663 */           public void setAdapter(ListAdapter adapter) {
/* 664 */               this.mListAdapter = adapter;
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 667 */           public void setPromptText(CharSequence hintText) {
/* 668 */               this.mPrompt = hintText;
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 671 */           public CharSequence getHintText() {
/* 672 */               return this.mPrompt;
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 675 */           public void show() {
/* 676 */               AlertDialog.Builder builder = new AlertDialog.Builder(SpinnerICS.this.getContext());
/* 677 */               if (this.mPrompt != null) {
/* 678 */                   builder.setTitle(this.mPrompt);
                        }
/* 680 */               this.mPopup = builder.setSingleChoiceItems(this.mListAdapter, SpinnerICS.this.getSelectedItemPosition(), this).show();
                    }

                    @Override // android.content.DialogInterface.OnClickListener
/* 684 */           public void onClick(DialogInterface dialog, int which) {
/* 685 */               SpinnerICS.this.setSelection(which);
/* 686 */               if (SpinnerICS.this.mOnItemClickListener != null) {
/* 687 */                   SpinnerICS.this.performItemClick(null, which, this.mListAdapter.getItemId(which));
                        }
/* 689 */               dismiss();
                    }
                }

/* 691 */       private class DropdownPopup extends ListPopupWindow implements SpinnerPopup {
                    private ListAdapter mAdapter;
                    private CharSequence mHintText;

/* 692 */           static /* synthetic */ ListAdapter access$100(DropdownPopup x0) {
/* 693 */               return x0.mAdapter;
                    }

/* 697 */           public DropdownPopup(Context context, AttributeSet attrs, int defStyleRes) {
/* 699 */               super(context, attrs, defStyleRes);
/* 701 */               setAnchorView(SpinnerICS.this);
/* 702 */               setModal(true);
/* 703 */               setPromptPosition(0);
/* 705 */               AdapterView.OnItemClickListener listener = new AdapterViewICS.OnItemClickListenerWrapper(new C01081(SpinnerICS.this));
/* 716 */               setOnItemClickListener(listener);
                    }

                    /* JADX INFO: renamed from: android.support.v7.internal.widget.SpinnerICS$DropdownPopup$1 */
/* 704 */           class C01081 implements AdapterViewICS.OnItemClickListener {
                        final /* synthetic */ SpinnerICS val$this$0;

/* 705 */               C01081(SpinnerICS spinnerICS) {
                            this.val$this$0 = spinnerICS;
                        }

                        @Override // android.support.v7.internal.widget.AdapterViewICS.OnItemClickListener
/* 707 */               public void onItemClick(AdapterViewICS parent, View v, int position, long id) {
/* 708 */                   SpinnerICS.this.setSelection(position);
/* 709 */                   if (SpinnerICS.this.mOnItemClickListener != null) {
/* 710 */                       SpinnerICS.this.performItemClick(v, position, DropdownPopup.access$100(DropdownPopup.this).getItemId(position));
                            }
/* 712 */                   DropdownPopup.this.dismiss();
                        }
                    }

                    @Override // android.support.v7.internal.widget.ListPopupWindow, android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 720 */           public void setAdapter(ListAdapter adapter) {
/* 721 */               super.setAdapter(adapter);
/* 722 */               this.mAdapter = adapter;
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 725 */           public CharSequence getHintText() {
/* 726 */               return this.mHintText;
                    }

                    @Override // android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 730 */           public void setPromptText(CharSequence hintText) {
/* 731 */               this.mHintText = hintText;
                    }

                    @Override // android.support.v7.internal.widget.ListPopupWindow, android.support.v7.internal.widget.SpinnerICS.SpinnerPopup
/* 735 */           public void show() {
/* 736 */               int spinnerPaddingLeft = SpinnerICS.this.getPaddingLeft();
/* 737 */               if (SpinnerICS.this.mDropDownWidth == -2) {
/* 738 */                   int spinnerWidth = SpinnerICS.this.getWidth();
/* 739 */                   int spinnerPaddingRight = SpinnerICS.this.getPaddingRight();
/* 740 */                   setContentWidth(Math.max(SpinnerICS.this.measureContentWidth((SpinnerAdapter) this.mAdapter, getBackground()), (spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight));
                        } else if (SpinnerICS.this.mDropDownWidth == -1) {
/* 744 */                   int spinnerWidth2 = SpinnerICS.this.getWidth();
/* 745 */                   int spinnerPaddingRight2 = SpinnerICS.this.getPaddingRight();
/* 746 */                   setContentWidth((spinnerWidth2 - spinnerPaddingLeft) - spinnerPaddingRight2);
                        } else {
/* 748 */                   setContentWidth(SpinnerICS.this.mDropDownWidth);
                        }
/* 750 */               Drawable background = getBackground();
/* 751 */               int bgOffset = 0;
/* 752 */               if (background != null) {
/* 753 */                   background.getPadding(SpinnerICS.access$200(SpinnerICS.this));
/* 754 */                   bgOffset = -SpinnerICS.access$200(SpinnerICS.this).left;
                        }
/* 756 */               setHorizontalOffset(bgOffset + spinnerPaddingLeft);
/* 757 */               setInputMethodMode(2);
/* 758 */               super.show();
/* 759 */               getListView().setChoiceMode(1);
/* 760 */               setSelection(SpinnerICS.this.getSelectedItemPosition());
                    }
                }
            }
