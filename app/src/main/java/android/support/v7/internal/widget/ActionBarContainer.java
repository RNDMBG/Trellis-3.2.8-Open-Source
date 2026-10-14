            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.graphics.drawable.ColorDrawable;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.view.ActionMode;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class ActionBarContainer extends FrameLayout {
                private ActionBarView mActionBarView;
                private Drawable mBackground;
                private boolean mIsSplit;
                private boolean mIsStacked;
                private boolean mIsTransitioning;
                private Drawable mSplitBackground;
                private Drawable mStackedBackground;
                private View mTabContainer;

/* 53 */        public ActionBarContainer(Context context) {
/* 54 */            this(context, null);
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 57 */        public ActionBarContainer(Context context, AttributeSet attrs) {
/* 58 */            super(context, attrs);
                    boolean z = true;
/* 60 */            setBackgroundDrawable(null);
/* 62 */            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.ActionBar);
/* 64 */            this.mBackground = a.getDrawable(10);
/* 65 */            this.mStackedBackground = a.getDrawable(11);
/* 68 */            if (getId() == C0084R.id.split_action_bar) {
/* 69 */                this.mIsSplit = true;
/* 70 */                this.mSplitBackground = a.getDrawable(12);
                    }
/* 73 */            a.recycle();
/* 75 */            if (this.mIsSplit) {
                        if (this.mSplitBackground != null) {
                            z = false;
                        }
                    } else if (this.mBackground != null || this.mStackedBackground != null) {
                        z = false;
                    }
                    setWillNotDraw(z);
                }

                @Override // android.view.View
/* 80 */        public void onFinishInflate() {
/* 81 */            super.onFinishInflate();
/* 82 */            this.mActionBarView = (ActionBarView) findViewById(C0084R.id.action_bar);
                }

/* 85 */        public void setPrimaryBackground(Drawable bg) {
                    boolean z = true;
/* 86 */            if (this.mBackground != null) {
/* 87 */                this.mBackground.setCallback(null);
/* 88 */                unscheduleDrawable(this.mBackground);
                    }
/* 90 */            this.mBackground = bg;
/* 91 */            if (bg != null) {
/* 92 */                bg.setCallback(this);
/* 93 */                if (this.mActionBarView != null) {
/* 94 */                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
                        }
                    }
/* 99 */            if (this.mIsSplit) {
                        if (this.mSplitBackground != null) {
                            z = false;
                        }
                    } else if (this.mBackground != null || this.mStackedBackground != null) {
                        z = false;
                    }
                    setWillNotDraw(z);
/* 101 */           invalidate();
                }

/* 104 */       public void setStackedBackground(Drawable bg) {
                    boolean z = true;
/* 105 */           if (this.mStackedBackground != null) {
/* 106 */               this.mStackedBackground.setCallback(null);
/* 107 */               unscheduleDrawable(this.mStackedBackground);
                    }
/* 109 */           this.mStackedBackground = bg;
/* 110 */           if (bg != null) {
/* 111 */               bg.setCallback(this);
/* 112 */               if (this.mIsStacked && this.mStackedBackground != null) {
/* 113 */                   this.mStackedBackground.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
                        }
                    }
/* 117 */           if (this.mIsSplit) {
                        if (this.mSplitBackground != null) {
                            z = false;
                        }
                    } else if (this.mBackground != null || this.mStackedBackground != null) {
                        z = false;
                    }
                    setWillNotDraw(z);
/* 119 */           invalidate();
                }

/* 122 */       public void setSplitBackground(Drawable bg) {
                    boolean z = true;
/* 123 */           if (this.mSplitBackground != null) {
/* 124 */               this.mSplitBackground.setCallback(null);
/* 125 */               unscheduleDrawable(this.mSplitBackground);
                    }
/* 127 */           this.mSplitBackground = bg;
/* 128 */           if (bg != null) {
/* 129 */               bg.setCallback(this);
/* 130 */               if (this.mIsSplit && this.mSplitBackground != null) {
/* 131 */                   this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                        }
                    }
/* 134 */           if (this.mIsSplit) {
                        if (this.mSplitBackground != null) {
                            z = false;
                        }
                    } else if (this.mBackground != null || this.mStackedBackground != null) {
                        z = false;
                    }
                    setWillNotDraw(z);
/* 136 */           invalidate();
                }

                @Override // android.view.View
/* 140 */       public void setVisibility(int visibility) {
/* 141 */           super.setVisibility(visibility);
/* 142 */           boolean isVisible = visibility == 0;
/* 143 */           if (this.mBackground != null) {
                        this.mBackground.setVisible(isVisible, false);
                    }
/* 144 */           if (this.mStackedBackground != null) {
                        this.mStackedBackground.setVisible(isVisible, false);
                    }
/* 145 */           if (this.mSplitBackground != null) {
                        this.mSplitBackground.setVisible(isVisible, false);
                    }
                }

                @Override // android.view.View
/* 149 */       protected boolean verifyDrawable(Drawable who) {
                    return (who == this.mBackground && !this.mIsSplit) || (who == this.mStackedBackground && this.mIsStacked) || ((who == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(who));
                }

                @Override // android.view.ViewGroup, android.view.View
/* 155 */       protected void drawableStateChanged() {
/* 156 */           super.drawableStateChanged();
/* 157 */           if (this.mBackground != null && this.mBackground.isStateful()) {
/* 158 */               this.mBackground.setState(getDrawableState());
                    }
/* 160 */           if (this.mStackedBackground != null && this.mStackedBackground.isStateful()) {
/* 161 */               this.mStackedBackground.setState(getDrawableState());
                    }
/* 163 */           if (this.mSplitBackground != null && this.mSplitBackground.isStateful()) {
/* 164 */               this.mSplitBackground.setState(getDrawableState());
                    }
                }

/* 175 */       public void setTransitioning(boolean isTransitioning) {
/* 176 */           this.mIsTransitioning = isTransitioning;
/* 177 */           setDescendantFocusability(isTransitioning ? 393216 : 262144);
                }

                @Override // android.view.ViewGroup
/* 182 */       public boolean onInterceptTouchEvent(MotionEvent ev) {
                    return this.mIsTransitioning || super.onInterceptTouchEvent(ev);
                }

                @Override // android.view.View
/* 187 */       public boolean onTouchEvent(MotionEvent ev) {
/* 188 */           super.onTouchEvent(ev);
/* 191 */           return true;
                }

                @Override // android.view.View
/* 198 */       public boolean onHoverEvent(MotionEvent ev) {
/* 199 */           return true;
                }

/* 202 */       public void setTabContainer(ScrollingTabContainerView tabView) {
/* 203 */           if (this.mTabContainer != null) {
/* 204 */               removeView(this.mTabContainer);
                    }
/* 206 */           this.mTabContainer = tabView;
/* 207 */           if (tabView != null) {
/* 208 */               addView(tabView);
/* 209 */               ViewGroup.LayoutParams lp = tabView.getLayoutParams();
/* 210 */               lp.width = -1;
/* 211 */               lp.height = -2;
/* 212 */               tabView.setAllowCollapse(false);
                    }
                }

/* 216 */       public View getTabContainer() {
/* 217 */           return this.mTabContainer;
                }

                @Override // android.view.View
/* 221 */       public void onDraw(Canvas canvas) {
/* 222 */           if (getWidth() != 0 && getHeight() != 0) {
/* 226 */               if (this.mIsSplit) {
/* 227 */                   if (this.mSplitBackground != null) {
/* 228 */                       drawBackgroundDrawable(this.mSplitBackground, canvas);
                            }
                        } else {
/* 231 */                   if (this.mBackground != null) {
/* 232 */                       drawBackgroundDrawable(this.mBackground, canvas);
                            }
/* 234 */                   if (this.mStackedBackground != null && this.mIsStacked) {
/* 235 */                       drawBackgroundDrawable(this.mStackedBackground, canvas);
                            }
                        }
                    }
                }

/* 242 */       public ActionMode startActionModeForChild(View child, ActionMode.Callback callback) {
/* 243 */           return null;
                }

                @Override // android.widget.FrameLayout, android.view.View
/* 247 */       public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 248 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 250 */           if (this.mActionBarView != null) {
/* 254 */               FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.mActionBarView.getLayoutParams();
/* 255 */               int actionBarViewHeight = this.mActionBarView.isCollapsed() ? 0 : this.mActionBarView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
/* 258 */               if (this.mTabContainer != null && this.mTabContainer.getVisibility() != 8) {
/* 259 */                   int mode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 260 */                   if (mode == Integer.MIN_VALUE) {
/* 261 */                       int maxHeight = View.MeasureSpec.getSize(heightMeasureSpec);
/* 262 */                       setMeasuredDimension(getMeasuredWidth(), Math.min(this.mTabContainer.getMeasuredHeight() + actionBarViewHeight, maxHeight));
                            }
                        }
                    }
                }

                @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
/* 270 */       public void onLayout(boolean changed, int l, int t, int r, int b) {
/* 271 */           super.onLayout(changed, l, t, r, b);
/* 273 */           boolean hasTabs = (this.mTabContainer == null || this.mTabContainer.getVisibility() == 8) ? false : true;
/* 275 */           if (this.mTabContainer != null && this.mTabContainer.getVisibility() != 8) {
/* 276 */               int containerHeight = getMeasuredHeight();
/* 277 */               int tabHeight = this.mTabContainer.getMeasuredHeight();
/* 279 */               if ((this.mActionBarView.getDisplayOptions() & 2) == 0) {
/* 281 */                   int count = getChildCount();
                            for (int i = 0; i < count; i++) {
/* 283 */                       View child = getChildAt(i);
/* 285 */                       if (child != this.mTabContainer && !this.mActionBarView.isCollapsed()) {
/* 290 */                           child.offsetTopAndBottom(tabHeight);
                                }
                            }
/* 293 */                   this.mTabContainer.layout(l, 0, r, tabHeight);
                        } else {
/* 295 */                   this.mTabContainer.layout(l, containerHeight - tabHeight, r, containerHeight);
                        }
                    }
/* 299 */           boolean needsInvalidate = false;
/* 300 */           if (this.mIsSplit) {
/* 301 */               if (this.mSplitBackground != null) {
/* 302 */                   this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
/* 303 */                   needsInvalidate = true;
                        }
                    } else {
/* 306 */               if (this.mBackground != null) {
/* 307 */                   this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
/* 309 */                   needsInvalidate = true;
                        }
/* 311 */               boolean z = hasTabs && this.mStackedBackground != null;
                        this.mIsStacked = z;
                        if (z) {
/* 312 */                   this.mStackedBackground.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
/* 314 */                   needsInvalidate = true;
                        }
                    }
/* 318 */           if (needsInvalidate) {
/* 319 */               invalidate();
                    }
                }

/* 323 */       private void drawBackgroundDrawable(Drawable d, Canvas canvas) {
/* 324 */           Rect bounds = d.getBounds();
/* 325 */           if ((d instanceof ColorDrawable) && !bounds.isEmpty() && Build.VERSION.SDK_INT < 11) {
/* 327 */               canvas.save();
/* 328 */               canvas.clipRect(bounds);
/* 329 */               d.draw(canvas);
/* 330 */               canvas.restore();
/* 334 */               return;
                    }
/* 332 */           d.draw(canvas);
                }
            }
