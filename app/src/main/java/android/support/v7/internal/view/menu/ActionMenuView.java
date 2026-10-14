            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.content.res.Configuration;
            import android.content.res.TypedArray;
            import android.os.Build;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.widget.LinearLayoutICS;
            import android.util.AttributeSet;
            import android.view.KeyEvent;
            import android.view.View;
            import android.view.ViewDebug;
            import android.view.ViewGroup;
            import android.view.accessibility.AccessibilityEvent;
            import android.widget.LinearLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class ActionMenuView extends LinearLayoutICS implements MenuBuilder.ItemInvoker, MenuView {
                static final int GENERATED_ITEM_PADDING = 4;
                static final int MIN_CELL_SIZE = 56;
                private static final String TAG = "ActionMenuView";
                private boolean mFormatItems;
                private int mFormatItemsWidth;
                private int mGeneratedItemPadding;
                private int mMaxItemHeight;
                private int mMeasuredExtraWidth;
                private MenuBuilder mMenu;
                private int mMinCellSize;
                private ActionMenuPresenter mPresenter;
                private boolean mReserveOverflow;

                public interface ActionMenuChildView {
                    boolean needsDividerAfter();

                    boolean needsDividerBefore();
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 34 */        protected /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 35 */            return generateDefaultLayoutParams();
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 34 */        protected /* bridge */ /* synthetic */ LinearLayout.LayoutParams generateDefaultLayoutParams() {
/* 35 */            return generateDefaultLayoutParams();
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 34 */        public /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(AttributeSet x0) {
/* 35 */            return generateLayoutParams(x0);
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 34 */        protected /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams x0) {
/* 35 */            return generateLayoutParams(x0);
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 34 */        public /* bridge */ /* synthetic */ LinearLayout.LayoutParams generateLayoutParams(AttributeSet x0) {
/* 35 */            return generateLayoutParams(x0);
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 34 */        protected /* bridge */ /* synthetic */ LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams x0) {
/* 35 */            return generateLayoutParams(x0);
                }

/* 53 */        public ActionMenuView(Context context) {
/* 54 */            this(context, null);
                }

/* 57 */        public ActionMenuView(Context context, AttributeSet attrs) {
/* 58 */            super(context, attrs);
/* 59 */            setBaselineAligned(false);
/* 60 */            float density = context.getResources().getDisplayMetrics().density;
/* 61 */            this.mMinCellSize = (int) (56.0f * density);
/* 62 */            this.mGeneratedItemPadding = (int) (4.0f * density);
/* 64 */            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.ActionBar, C0084R.attr.actionBarStyle, 0);
/* 66 */            this.mMaxItemHeight = a.getDimensionPixelSize(0, 0);
/* 67 */            a.recycle();
                }

/* 70 */        public void setPresenter(ActionMenuPresenter presenter) {
/* 71 */            this.mPresenter = presenter;
                }

/* 74 */        public boolean isExpandedFormat() {
/* 75 */            return this.mFormatItems;
                }

                @Override // android.view.View
/* 79 */        public void onConfigurationChanged(Configuration newConfig) {
/* 80 */            if (Build.VERSION.SDK_INT >= 8) {
/* 81 */                super.onConfigurationChanged(newConfig);
                    }
/* 84 */            this.mPresenter.updateMenuView(false);
/* 86 */            if (this.mPresenter != null && this.mPresenter.isOverflowMenuShowing()) {
/* 87 */                this.mPresenter.hideOverflowMenu();
/* 88 */                this.mPresenter.showOverflowMenu();
                    }
                }

                @Override // android.widget.LinearLayout, android.view.View
/* 94 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 95 */            boolean wasFormatted = this.mFormatItems;
/* 96 */            this.mFormatItems = View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
/* 98 */            if (wasFormatted != this.mFormatItems) {
/* 99 */                this.mFormatItemsWidth = 0;
                    }
/* 104 */           int widthSize = View.MeasureSpec.getMode(widthMeasureSpec);
/* 105 */           if (this.mFormatItems && this.mMenu != null && widthSize != this.mFormatItemsWidth) {
/* 106 */               this.mFormatItemsWidth = widthSize;
/* 107 */               this.mMenu.onItemsChanged(true);
                    }
/* 110 */           if (this.mFormatItems) {
/* 111 */               onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
/* 122 */               return;
                    }
/* 114 */           int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 116 */               View child = getChildAt(i);
/* 117 */               LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 118 */               lp.rightMargin = 0;
                        lp.leftMargin = 0;
                    }
/* 120 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }

/* 125 */       private void onMeasureExactFormat(int widthMeasureSpec, int heightMeasureSpec) {
/* 126 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 127 */           int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 128 */           int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
/* 130 */           int widthPadding = getPaddingLeft() + getPaddingRight();
/* 131 */           int heightPadding = getPaddingTop() + getPaddingBottom();
/* 133 */           int itemHeightSpec = heightMode == 1073741824 ? View.MeasureSpec.makeMeasureSpec(heightSize - heightPadding, 1073741824) : View.MeasureSpec.makeMeasureSpec(Math.min(this.mMaxItemHeight, heightSize - heightPadding), Integer.MIN_VALUE);
/* 138 */           int widthSize2 = widthSize - widthPadding;
/* 141 */           int cellCount = widthSize2 / this.mMinCellSize;
/* 142 */           int cellSizeRemaining = widthSize2 % this.mMinCellSize;
/* 144 */           if (cellCount == 0) {
/* 146 */               setMeasuredDimension(widthSize2, 0);
/* 360 */               return;
                    }
/* 150 */           int cellSize = this.mMinCellSize + (cellSizeRemaining / cellCount);
/* 152 */           int cellsRemaining = cellCount;
/* 153 */           int maxChildHeight = 0;
/* 154 */           int maxCellsUsed = 0;
/* 155 */           int expandableItemCount = 0;
/* 156 */           int visibleItemCount = 0;
/* 157 */           boolean hasOverflow = false;
/* 160 */           long smallestItemsAt = 0;
/* 162 */           int childCount = getChildCount();
                    for (int i = 0; i < childCount; i++) {
/* 164 */               View child = getChildAt(i);
/* 165 */               if (child.getVisibility() != 8) {
/* 169 */                   boolean isGeneratedItem = child instanceof ActionMenuItemView;
/* 170 */                   visibleItemCount++;
/* 172 */                   if (isGeneratedItem) {
/* 175 */                       child.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                            }
/* 178 */                   LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 179 */                   lp.expanded = false;
/* 180 */                   lp.extraPixels = 0;
/* 181 */                   lp.cellsUsed = 0;
/* 182 */                   lp.expandable = false;
/* 183 */                   lp.leftMargin = 0;
/* 184 */                   lp.rightMargin = 0;
/* 185 */                   lp.preventEdgeOffset = isGeneratedItem && ((ActionMenuItemView) child).hasText();
/* 188 */                   int cellsAvailable = lp.isOverflowButton ? 1 : cellsRemaining;
/* 190 */                   int cellsUsed = measureChildForCells(child, cellSize, cellsAvailable, itemHeightSpec, heightPadding);
/* 193 */                   maxCellsUsed = Math.max(maxCellsUsed, cellsUsed);
/* 194 */                   if (lp.expandable) {
/* 195 */                       expandableItemCount++;
                            }
/* 197 */                   if (lp.isOverflowButton) {
/* 198 */                       hasOverflow = true;
                            }
/* 201 */                   cellsRemaining -= cellsUsed;
/* 202 */                   maxChildHeight = Math.max(maxChildHeight, child.getMeasuredHeight());
/* 203 */                   if (cellsUsed == 1) {
/* 204 */                       smallestItemsAt |= (long) (1 << i);
                            }
                        }
                    }
/* 210 */           boolean centerSingleExpandedItem = hasOverflow && visibleItemCount == 2;
/* 215 */           boolean needsExpansion = false;
/* 216 */           while (expandableItemCount > 0 && cellsRemaining > 0) {
/* 217 */               int minCells = Integer.MAX_VALUE;
/* 218 */               long minCellsAt = 0;
/* 219 */               int minCellsItemCount = 0;
                        for (int i2 = 0; i2 < childCount; i2++) {
/* 222 */                   LayoutParams lp2 = (LayoutParams) getChildAt(i2).getLayoutParams();
/* 225 */                   if (lp2.expandable) {
/* 230 */                       if (lp2.cellsUsed < minCells) {
/* 231 */                           minCells = lp2.cellsUsed;
/* 232 */                           minCellsAt = 1 << i2;
/* 233 */                           minCellsItemCount = 1;
                                } else if (lp2.cellsUsed == minCells) {
/* 235 */                           minCellsAt |= (long) (1 << i2);
/* 236 */                           minCellsItemCount++;
                                }
                            }
                        }
/* 241 */               smallestItemsAt |= minCellsAt;
/* 243 */               if (minCellsItemCount > cellsRemaining) {
                            break;
                        }
/* 248 */               int minCells2 = minCells + 1;
                        for (int i3 = 0; i3 < childCount; i3++) {
/* 251 */                   View child2 = getChildAt(i3);
/* 252 */                   LayoutParams lp3 = (LayoutParams) child2.getLayoutParams();
                            if ((((long) (1 << i3)) & minCellsAt) == 0) {
/* 255 */                       if (lp3.cellsUsed == minCells2) {
/* 256 */                           smallestItemsAt |= (long) (1 << i3);
                                }
                            } else {
/* 261 */                       if (centerSingleExpandedItem && lp3.preventEdgeOffset && cellsRemaining == 1) {
/* 263 */                           child2.setPadding(this.mGeneratedItemPadding + cellSize, 0, this.mGeneratedItemPadding, 0);
                                }
/* 265 */                       lp3.cellsUsed++;
/* 266 */                       lp3.expanded = true;
/* 267 */                       cellsRemaining--;
                            }
                        }
/* 270 */               needsExpansion = true;
                    }
/* 276 */           boolean singleItem = !hasOverflow && visibleItemCount == 1;
/* 277 */           if (cellsRemaining > 0 && smallestItemsAt != 0 && (cellsRemaining < visibleItemCount - 1 || singleItem || maxCellsUsed > 1)) {
/* 279 */               float expandCount = Long.bitCount(smallestItemsAt);
/* 281 */               if (!singleItem) {
/* 285 */                   if ((1 & smallestItemsAt) != 0 && !((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
/* 286 */                       expandCount -= 0.5f;
                            }
/* 291 */                   if ((((long) (1 << (childCount - 1))) & smallestItemsAt) != 0 && !((LayoutParams) getChildAt(childCount - 1).getLayoutParams()).preventEdgeOffset) {
/* 292 */                       expandCount -= 0.5f;
                            }
                        }
/* 297 */               int extraPixels = expandCount > 0.0f ? (int) ((cellsRemaining * cellSize) / expandCount) : 0;
                        for (int i4 = 0; i4 < childCount; i4++) {
                            if ((((long) (1 << i4)) & smallestItemsAt) != 0) {
/* 305 */                       View child3 = getChildAt(i4);
/* 306 */                       LayoutParams lp4 = (LayoutParams) child3.getLayoutParams();
/* 307 */                       if (child3 instanceof ActionMenuItemView) {
/* 309 */                           lp4.extraPixels = extraPixels;
/* 310 */                           lp4.expanded = true;
/* 311 */                           if (i4 == 0 && !lp4.preventEdgeOffset) {
/* 314 */                               lp4.leftMargin = (-extraPixels) / 2;
                                    }
/* 316 */                           needsExpansion = true;
                                } else if (lp4.isOverflowButton) {
/* 318 */                           lp4.extraPixels = extraPixels;
/* 319 */                           lp4.expanded = true;
/* 320 */                           lp4.rightMargin = (-extraPixels) / 2;
/* 321 */                           needsExpansion = true;
                                } else {
/* 326 */                           if (i4 != 0) {
/* 327 */                               lp4.leftMargin = extraPixels / 2;
                                    }
/* 329 */                           if (i4 != childCount - 1) {
/* 330 */                               lp4.rightMargin = extraPixels / 2;
                                    }
                                }
                            }
                        }
/* 335 */               cellsRemaining = 0;
                    }
/* 339 */           if (needsExpansion) {
                        for (int i5 = 0; i5 < childCount; i5++) {
/* 341 */                   View child4 = getChildAt(i5);
/* 342 */                   LayoutParams lp5 = (LayoutParams) child4.getLayoutParams();
/* 344 */                   if (lp5.expanded) {
/* 348 */                       int width = (lp5.cellsUsed * cellSize) + lp5.extraPixels;
/* 349 */                       child4.measure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), itemHeightSpec);
                            }
                        }
                    }
/* 354 */           if (heightMode != 1073741824) {
/* 355 */               heightSize = maxChildHeight;
                    }
/* 358 */           setMeasuredDimension(widthSize2, heightSize);
/* 359 */           this.mMeasuredExtraWidth = cellsRemaining * cellSize;
                }

/* 376 */       static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
/* 377 */           LayoutParams lp = (LayoutParams) child.getLayoutParams();
/* 379 */           int childHeightSize = View.MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding;
/* 381 */           int childHeightMode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
/* 382 */           int childHeightSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode);
/* 384 */           ActionMenuItemView itemView = child instanceof ActionMenuItemView ? (ActionMenuItemView) child : null;
/* 386 */           boolean hasText = itemView != null && itemView.hasText();
/* 388 */           int cellsUsed = 0;
/* 389 */           if (cellsRemaining > 0 && (!hasText || cellsRemaining >= 2)) {
/* 390 */               int childWidthSpec = View.MeasureSpec.makeMeasureSpec(cellSize * cellsRemaining, Integer.MIN_VALUE);
/* 392 */               child.measure(childWidthSpec, childHeightSpec);
/* 394 */               int measuredWidth = child.getMeasuredWidth();
/* 395 */               cellsUsed = measuredWidth / cellSize;
/* 396 */               if (measuredWidth % cellSize != 0) {
/* 397 */                   cellsUsed++;
                        }
/* 399 */               if (hasText && cellsUsed < 2) {
/* 400 */                   cellsUsed = 2;
                        }
                    }
/* 404 */           boolean expandable = !lp.isOverflowButton && hasText;
/* 405 */           lp.expandable = expandable;
/* 407 */           lp.cellsUsed = cellsUsed;
/* 408 */           int targetWidth = cellsUsed * cellSize;
/* 409 */           child.measure(View.MeasureSpec.makeMeasureSpec(targetWidth, 1073741824), childHeightSpec);
/* 411 */           return cellsUsed;
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
/* 415 */       protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 416 */           if (!this.mFormatItems) {
/* 417 */               super.onLayout(changed, left, top, right, bottom);
/* 491 */               return;
                    }
/* 421 */           int childCount = getChildCount();
/* 422 */           int midVertical = (top + bottom) / 2;
/* 423 */           int dividerWidth = getSupportDividerWidth();
/* 425 */           int nonOverflowWidth = 0;
/* 426 */           int nonOverflowCount = 0;
/* 427 */           int widthRemaining = ((right - left) - getPaddingRight()) - getPaddingLeft();
/* 428 */           boolean hasOverflow = false;
                    for (int i = 0; i < childCount; i++) {
/* 430 */               View v = getChildAt(i);
/* 431 */               if (v.getVisibility() != 8) {
/* 435 */                   LayoutParams p = (LayoutParams) v.getLayoutParams();
/* 436 */                   if (p.isOverflowButton) {
/* 437 */                       int overflowWidth = v.getMeasuredWidth();
/* 438 */                       if (hasSupportDividerBeforeChildAt(i)) {
/* 439 */                           overflowWidth += dividerWidth;
                                }
/* 441 */                       int height = v.getMeasuredHeight();
/* 442 */                       int r = (getWidth() - getPaddingRight()) - p.rightMargin;
/* 444 */                       int t = midVertical - (height / 2);
/* 445 */                       int b = t + height;
/* 446 */                       v.layout(r - overflowWidth, t, r, b);
/* 448 */                       widthRemaining -= overflowWidth;
/* 449 */                       hasOverflow = true;
                            } else {
/* 451 */                       int size = v.getMeasuredWidth() + p.leftMargin + p.rightMargin;
/* 452 */                       nonOverflowWidth += size;
/* 453 */                       widthRemaining -= size;
/* 454 */                       if (hasSupportDividerBeforeChildAt(i)) {
/* 455 */                           nonOverflowWidth += dividerWidth;
                                }
/* 457 */                       nonOverflowCount++;
                            }
                        }
                    }
/* 461 */           if (childCount == 1 && !hasOverflow) {
/* 463 */               View v2 = getChildAt(0);
/* 464 */               int width = v2.getMeasuredWidth();
/* 465 */               int height2 = v2.getMeasuredHeight();
/* 466 */               int midHorizontal = (right - left) / 2;
/* 467 */               int l = midHorizontal - (width / 2);
/* 468 */               int t2 = midVertical - (height2 / 2);
/* 469 */               v2.layout(l, t2, l + width, t2 + height2);
/* 491 */               return;
                    }
/* 473 */           int spacerCount = nonOverflowCount - (hasOverflow ? 0 : 1);
/* 474 */           int spacerSize = Math.max(0, spacerCount > 0 ? widthRemaining / spacerCount : 0);
/* 476 */           int startLeft = getPaddingLeft();
                    for (int i2 = 0; i2 < childCount; i2++) {
/* 478 */               View v3 = getChildAt(i2);
/* 479 */               LayoutParams lp = (LayoutParams) v3.getLayoutParams();
/* 480 */               if (v3.getVisibility() != 8 && !lp.isOverflowButton) {
/* 484 */                   int startLeft2 = startLeft + lp.leftMargin;
/* 485 */                   int width2 = v3.getMeasuredWidth();
/* 486 */                   int height3 = v3.getMeasuredHeight();
/* 487 */                   int t3 = midVertical - (height3 / 2);
/* 488 */                   v3.layout(startLeft2, t3, startLeft2 + width2, t3 + height3);
/* 489 */                   startLeft = startLeft2 + lp.rightMargin + width2 + spacerSize;
                        }
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 494 */       public void onDetachedFromWindow() {
/* 495 */           super.onDetachedFromWindow();
/* 496 */           this.mPresenter.dismissPopupMenus();
                }

/* 499 */       public boolean isOverflowReserved() {
/* 500 */           return this.mReserveOverflow;
                }

/* 503 */       public void setOverflowReserved(boolean reserveOverflow) {
/* 504 */           this.mReserveOverflow = reserveOverflow;
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 508 */       protected LayoutParams generateDefaultLayoutParams() {
/* 509 */           LayoutParams params = new LayoutParams(-2, -2);
/* 511 */           params.gravity = 16;
/* 512 */           return params;
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 516 */       public LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 517 */           return new LayoutParams(getContext(), attrs);
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 521 */       protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
/* 522 */           if (!(p instanceof LayoutParams)) {
                        return generateDefaultLayoutParams();
                    }
/* 523 */           LayoutParams result = new LayoutParams((LayoutParams) p);
/* 524 */           if (result.gravity <= 0) {
/* 525 */               result.gravity = 16;
/* 529 */               return result;
                    }
/* 529 */           return result;
                }

                @Override // android.widget.LinearLayout, android.view.ViewGroup
/* 533 */       protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
                    return p != null && (p instanceof LayoutParams);
                }

/* 537 */       public LayoutParams generateOverflowButtonLayoutParams() {
/* 538 */           LayoutParams result = generateDefaultLayoutParams();
/* 539 */           result.isOverflowButton = true;
/* 540 */           return result;
                }

                @Override // android.support.v7.internal.view.menu.MenuBuilder.ItemInvoker
/* 543 */       public boolean invokeItem(MenuItemImpl item) {
/* 544 */           return this.mMenu.performItemAction(item, 0);
                }

                @Override // android.support.v7.internal.view.menu.MenuView
/* 547 */       public int getWindowAnimations() {
/* 548 */           return 0;
                }

                @Override // android.support.v7.internal.view.menu.MenuView
/* 551 */       public void initialize(MenuBuilder menu) {
/* 552 */           this.mMenu = menu;
                }

                @Override // android.support.v7.internal.widget.LinearLayoutICS
/* 555 */       protected boolean hasSupportDividerBeforeChildAt(int childIndex) {
/* 556 */           KeyEvent.Callback childAt = getChildAt(childIndex - 1);
/* 557 */           KeyEvent.Callback childAt2 = getChildAt(childIndex);
/* 558 */           boolean result = false;
/* 559 */           if (childIndex < getChildCount() && (childAt instanceof ActionMenuChildView)) {
/* 558 */               result = false | ((ActionMenuChildView) childAt).needsDividerAfter();
                    }
/* 562 */           if (childIndex > 0 && (childAt2 instanceof ActionMenuChildView)) {
/* 563 */               return result | ((ActionMenuChildView) childAt2).needsDividerBefore();
                    }
/* 565 */           return result;
                }

                @Override // android.view.View
/* 568 */       public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
/* 569 */           return false;
                }

/* 597 */       public static class LayoutParams extends LinearLayout.LayoutParams {

                    @ViewDebug.ExportedProperty
                    public int cellsUsed;

                    @ViewDebug.ExportedProperty
                    public boolean expandable;
                    public boolean expanded;

                    @ViewDebug.ExportedProperty
                    public int extraPixels;

                    @ViewDebug.ExportedProperty
                    public boolean isOverflowButton;

                    @ViewDebug.ExportedProperty
                    public boolean preventEdgeOffset;

/* 598 */           public LayoutParams(Context c, AttributeSet attrs) {
/* 599 */               super(c, attrs);
                    }

/* 602 */           public LayoutParams(LayoutParams other) {
/* 603 */               super((LinearLayout.LayoutParams) other);
/* 604 */               this.isOverflowButton = other.isOverflowButton;
                    }

/* 607 */           public LayoutParams(int width, int height) {
/* 608 */               super(width, height);
/* 609 */               this.isOverflowButton = false;
                    }

/* 612 */           public LayoutParams(int width, int height, boolean isOverflowButton) {
/* 613 */               super(width, height);
/* 614 */               this.isOverflowButton = isOverflowButton;
                    }
                }
            }
