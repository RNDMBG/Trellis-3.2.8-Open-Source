            package android.support.v7.internal.widget;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Bitmap;
            import android.graphics.BitmapShader;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.graphics.Shader;
            import android.graphics.drawable.Animatable;
            import android.graphics.drawable.AnimationDrawable;
            import android.graphics.drawable.BitmapDrawable;
            import android.graphics.drawable.ClipDrawable;
            import android.graphics.drawable.Drawable;
            import android.graphics.drawable.LayerDrawable;
            import android.graphics.drawable.ShapeDrawable;
            import android.graphics.drawable.shapes.RoundRectShape;
            import android.graphics.drawable.shapes.Shape;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.os.SystemClock;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.AnimationUtils;
            import android.view.animation.Interpolator;
            import android.view.animation.LinearInterpolator;
            import android.view.animation.Transformation;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class ProgressBarICS extends View {
                private static final int ANIMATION_RESOLUTION = 200;
                private static final int MAX_LEVEL = 10000;
                private static final int[] android_R_styleable_ProgressBar = {R.attr.max, R.attr.progress, R.attr.secondaryProgress, R.attr.indeterminate, R.attr.indeterminateOnly, R.attr.indeterminateDrawable, R.attr.progressDrawable, R.attr.indeterminateDuration, R.attr.indeterminateBehavior, R.attr.minWidth, R.attr.maxWidth, R.attr.minHeight, R.attr.maxHeight, R.attr.interpolator};
                private AlphaAnimation mAnimation;
                private int mBehavior;
                private Drawable mCurrentDrawable;
                private int mDuration;
                private boolean mInDrawing;
                private boolean mIndeterminate;
                private Drawable mIndeterminateDrawable;
                private Interpolator mInterpolator;
                private long mLastDrawTime;
                private int mMax;
                int mMaxHeight;
                int mMaxWidth;
                int mMinHeight;
                int mMinWidth;
                private boolean mNoInvalidate;
                private boolean mOnlyIndeterminate;
                private int mProgress;
                private Drawable mProgressDrawable;
                private RefreshProgressRunnable mRefreshProgressRunnable;
                Bitmap mSampleTile;
                private int mSecondaryProgress;
                private boolean mShouldStartAnimationDrawable;
                private Transformation mTransformation;
                private long mUiThreadId;

/* 51 */        static /* synthetic */ void access$000(ProgressBarICS x0, int x1, int x2, boolean x3, boolean x4) {
/* 52 */            x0.doRefreshProgress(x1, x2, x3, x4);
                }

/* 51 */        static /* synthetic */ RefreshProgressRunnable access$102(ProgressBarICS x0, RefreshProgressRunnable x1) {
/* 52 */            x0.mRefreshProgressRunnable = x1;
                    return x1;
                }

/* 108 */       public ProgressBarICS(Context context, AttributeSet attrs, int defStyle, int styleRes) {
/* 109 */           super(context, attrs, defStyle);
/* 110 */           this.mUiThreadId = Thread.currentThread().getId();
/* 111 */           initProgressBar();
/* 113 */           TypedArray a = context.obtainStyledAttributes(attrs, android_R_styleable_ProgressBar, defStyle, styleRes);
/* 116 */           this.mNoInvalidate = true;
/* 118 */           setMax(a.getInt(0, this.mMax));
/* 119 */           setProgress(a.getInt(1, this.mProgress));
/* 120 */           setSecondaryProgress(a.getInt(2, this.mSecondaryProgress));
/* 122 */           boolean indeterminate = a.getBoolean(3, this.mIndeterminate);
/* 123 */           this.mOnlyIndeterminate = a.getBoolean(4, this.mOnlyIndeterminate);
/* 125 */           Drawable drawable = a.getDrawable(5);
/* 126 */           if (drawable != null) {
/* 128 */               setIndeterminateDrawable(tileifyIndeterminate(drawable));
                    }
/* 131 */           Drawable drawable2 = a.getDrawable(6);
/* 132 */           if (drawable2 != null) {
/* 136 */               setProgressDrawable(tileify(drawable2, false));
                    }
/* 139 */           this.mDuration = a.getInt(7, this.mDuration);
/* 140 */           this.mBehavior = a.getInt(8, this.mBehavior);
/* 141 */           this.mMinWidth = a.getDimensionPixelSize(9, this.mMinWidth);
/* 142 */           this.mMaxWidth = a.getDimensionPixelSize(10, this.mMaxWidth);
/* 143 */           this.mMinHeight = a.getDimensionPixelSize(11, this.mMinHeight);
/* 144 */           this.mMaxHeight = a.getDimensionPixelSize(12, this.mMaxHeight);
/* 146 */           int resID = a.getResourceId(13, R.anim.linear_interpolator);
/* 147 */           if (resID > 0) {
/* 148 */               setInterpolator(context, resID);
                    }
/* 151 */           a.recycle();
/* 153 */           this.mNoInvalidate = false;
/* 154 */           setIndeterminate(this.mOnlyIndeterminate || indeterminate);
                }

/* 162 */       private Drawable tileify(Drawable drawable, boolean z) {
/* 163 */           if (drawable instanceof LayerDrawable) {
/* 164 */               LayerDrawable layerDrawable = (LayerDrawable) drawable;
/* 165 */               int numberOfLayers = layerDrawable.getNumberOfLayers();
/* 166 */               Drawable[] drawableArr = new Drawable[numberOfLayers];
                        for (int i = 0; i < numberOfLayers; i++) {
/* 169 */                   int id = layerDrawable.getId(i);
                            drawableArr[i] = tileify(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303);
                        }
/* 174 */               LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                        for (int i2 = 0; i2 < numberOfLayers; i2++) {
/* 177 */                   layerDrawable2.setId(i2, layerDrawable.getId(i2));
                        }
/* 198 */               return layerDrawable2;
                    }
/* 182 */           if (!(drawable instanceof BitmapDrawable)) {
                        return drawable;
                    }
/* 183 */           Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
/* 184 */           if (this.mSampleTile == null) {
/* 185 */               this.mSampleTile = bitmap;
                    }
/* 188 */           ShapeDrawable shapeDrawable = new ShapeDrawable(getDrawableShape());
/* 192 */           shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                    Drawable clipDrawable = shapeDrawable;
/* 194 */           if (z) {
                        clipDrawable = new ClipDrawable(shapeDrawable, 3, 1);
                    }
                    return clipDrawable;
                }

/* 201 */       Shape getDrawableShape() {
/* 202 */           float[] roundedCorners = {5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
/* 203 */           return new RoundRectShape(roundedCorners, null, null);
                }

/* 211 */       private Drawable tileifyIndeterminate(Drawable drawable) {
/* 212 */           if (drawable instanceof AnimationDrawable) {
/* 213 */               AnimationDrawable background = (AnimationDrawable) drawable;
/* 214 */               int N = background.getNumberOfFrames();
/* 215 */               AnimationDrawable newBg = new AnimationDrawable();
/* 216 */               newBg.setOneShot(background.isOneShot());
                        for (int i = 0; i < N; i++) {
/* 219 */                   Drawable frame = tileify(background.getFrame(i), true);
/* 220 */                   frame.setLevel(10000);
/* 221 */                   newBg.addFrame(frame, background.getDuration(i));
                        }
/* 223 */               newBg.setLevel(10000);
/* 224 */               return newBg;
                    }
/* 226 */           return drawable;
                }

/* 241 */       private void initProgressBar() {
/* 242 */           this.mMax = 100;
/* 243 */           this.mProgress = 0;
/* 244 */           this.mSecondaryProgress = 0;
/* 245 */           this.mIndeterminate = false;
/* 246 */           this.mOnlyIndeterminate = false;
/* 247 */           this.mDuration = 4000;
/* 248 */           this.mBehavior = 1;
/* 249 */           this.mMinWidth = 24;
/* 250 */           this.mMaxWidth = 48;
/* 251 */           this.mMinHeight = 24;
/* 252 */           this.mMaxHeight = 48;
                }

/* 260 */       public synchronized boolean isIndeterminate() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mIndeterminate;
                }

/* 274 */       public synchronized void setIndeterminate(boolean indeterminate) {
                    try {
                        if ((!this.mOnlyIndeterminate || !this.mIndeterminate) && indeterminate != this.mIndeterminate) {
/* 276 */                   this.mIndeterminate = indeterminate;
/* 278 */                   if (indeterminate) {
/* 280 */                       this.mCurrentDrawable = this.mIndeterminateDrawable;
/* 281 */                       startAnimation();
                            } else {
/* 283 */                       this.mCurrentDrawable = this.mProgressDrawable;
/* 284 */                       stopAnimation();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 298 */       public Drawable getIndeterminateDrawable() {
/* 299 */           return this.mIndeterminateDrawable;
                }

/* 311 */       public void setIndeterminateDrawable(Drawable d) {
/* 312 */           if (d != null) {
/* 313 */               d.setCallback(this);
                    }
/* 315 */           this.mIndeterminateDrawable = d;
/* 316 */           if (this.mIndeterminate) {
/* 317 */               this.mCurrentDrawable = d;
/* 318 */               postInvalidate();
                    }
                }

/* 331 */       public Drawable getProgressDrawable() {
/* 332 */           return this.mProgressDrawable;
                }

/* 345 */       public void setProgressDrawable(Drawable d) {
                    boolean needUpdate;
/* 346 */           if (this.mProgressDrawable != null && d != this.mProgressDrawable) {
/* 347 */               this.mProgressDrawable.setCallback(null);
/* 348 */               needUpdate = true;
                    } else {
/* 350 */               needUpdate = false;
                    }
/* 353 */           if (d != null) {
/* 354 */               d.setCallback(this);
/* 357 */               int drawableHeight = d.getMinimumHeight();
/* 358 */               if (this.mMaxHeight < drawableHeight) {
/* 359 */                   this.mMaxHeight = drawableHeight;
/* 360 */                   requestLayout();
                        }
                    }
/* 363 */           this.mProgressDrawable = d;
/* 364 */           if (!this.mIndeterminate) {
/* 365 */               this.mCurrentDrawable = d;
/* 366 */               postInvalidate();
                    }
/* 369 */           if (needUpdate) {
/* 370 */               updateDrawableBounds(getWidth(), getHeight());
/* 371 */               updateDrawableState();
/* 372 */               doRefreshProgress(R.id.progress, this.mProgress, false, false);
/* 373 */               doRefreshProgress(R.id.secondaryProgress, this.mSecondaryProgress, false, false);
                    }
                }

                @Override // android.view.View
/* 378 */       protected boolean verifyDrawable(Drawable who) {
                    return who == this.mProgressDrawable || who == this.mIndeterminateDrawable || super.verifyDrawable(who);
                }

                @Override // android.view.View
/* 384 */       public void postInvalidate() {
/* 385 */           if (!this.mNoInvalidate) {
/* 386 */               super.postInvalidate();
                    }
                }

/* 394 */       private class RefreshProgressRunnable implements Runnable {
                    private boolean mFromUser;
                    private int mId;
                    private int mProgress;

/* 395 */           RefreshProgressRunnable(int id, int progress, boolean fromUser) {
/* 397 */               this.mId = id;
/* 398 */               this.mProgress = progress;
/* 399 */               this.mFromUser = fromUser;
                    }

                    @Override // java.lang.Runnable
/* 402 */           public void run() {
/* 403 */               ProgressBarICS.access$000(ProgressBarICS.this, this.mId, this.mProgress, this.mFromUser, true);
/* 405 */               ProgressBarICS.access$102(ProgressBarICS.this, this);
                    }

/* 408 */           public void setup(int id, int progress, boolean fromUser) {
/* 409 */               this.mId = id;
/* 410 */               this.mProgress = progress;
/* 411 */               this.mFromUser = fromUser;
                    }
                }

/* 417 */       private synchronized void doRefreshProgress(int id, int progress, boolean fromUser, boolean callBackToApp) {
                    try {
/* 418 */               float scale = this.mMax > 0 ? progress / this.mMax : 0.0f;
/* 419 */               Drawable d = this.mCurrentDrawable;
/* 420 */               if (d != null) {
/* 421 */                   Drawable progressDrawable = null;
/* 423 */                   if (d instanceof LayerDrawable) {
/* 424 */                       progressDrawable = ((LayerDrawable) d).findDrawableByLayerId(id);
                            }
/* 427 */                   int level = (int) (10000.0f * scale);
/* 428 */                   if (progressDrawable == null) {
                                progressDrawable = d;
                            }
                            progressDrawable.setLevel(level);
                        } else {
/* 430 */                   invalidate();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 434 */       private synchronized void refreshProgress(int id, int progress, boolean fromUser) {
                    RefreshProgressRunnable r;
                    try {
                        if (this.mUiThreadId == Thread.currentThread().getId()) {
/* 436 */                   doRefreshProgress(id, progress, fromUser, true);
                        } else {
/* 439 */                   if (this.mRefreshProgressRunnable != null) {
/* 441 */                       r = this.mRefreshProgressRunnable;
/* 443 */                       this.mRefreshProgressRunnable = null;
/* 444 */                       r.setup(id, progress, fromUser);
                            } else {
/* 447 */                       r = new RefreshProgressRunnable(id, progress, fromUser);
                            }
/* 449 */                   post(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 464 */       public synchronized void setProgress(int progress) {
                    try {
                        setProgress(progress, false);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 468 */       synchronized void setProgress(int progress, boolean fromUser) {
                    try {
                        if (!this.mIndeterminate) {
/* 473 */                   if (progress < 0) {
/* 474 */                       progress = 0;
                            }
/* 477 */                   if (progress > this.mMax) {
/* 478 */                       progress = this.mMax;
                            }
/* 481 */                   if (progress != this.mProgress) {
/* 482 */                       this.mProgress = progress;
/* 483 */                       refreshProgress(R.id.progress, this.mProgress, fromUser);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 499 */       public synchronized void setSecondaryProgress(int secondaryProgress) {
                    try {
                        if (!this.mIndeterminate) {
/* 504 */                   if (secondaryProgress < 0) {
/* 505 */                       secondaryProgress = 0;
                            }
/* 508 */                   if (secondaryProgress > this.mMax) {
/* 509 */                       secondaryProgress = this.mMax;
                            }
/* 512 */                   if (secondaryProgress != this.mSecondaryProgress) {
/* 513 */                       this.mSecondaryProgress = secondaryProgress;
/* 514 */                       refreshProgress(R.id.secondaryProgress, this.mSecondaryProgress, false);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 530 */       public synchronized int getProgress() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mIndeterminate ? 0 : this.mProgress;
                }

/* 546 */       public synchronized int getSecondaryProgress() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mIndeterminate ? 0 : this.mSecondaryProgress;
                }

/* 559 */       public synchronized int getMax() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mMax;
                }

/* 572 */       public synchronized void setMax(int max) {
                    if (max < 0) {
/* 574 */               max = 0;
                    }
                    try {
/* 576 */               if (max != this.mMax) {
/* 577 */                   this.mMax = max;
/* 578 */                   postInvalidate();
/* 580 */                   if (this.mProgress > max) {
/* 581 */                       this.mProgress = max;
                            }
/* 583 */                   refreshProgress(R.id.progress, this.mProgress, false);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 594 */       public final synchronized void incrementProgressBy(int diff) {
                    try {
                        setProgress(this.mProgress + diff);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 605 */       public final synchronized void incrementSecondaryProgressBy(int diff) {
                    try {
                        setSecondaryProgress(this.mSecondaryProgress + diff);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 612 */       void startAnimation() {
/* 613 */           if (getVisibility() == 0) {
/* 617 */               if (this.mIndeterminateDrawable instanceof Animatable) {
/* 618 */                   this.mShouldStartAnimationDrawable = true;
/* 619 */                   this.mAnimation = null;
                        } else {
/* 621 */                   if (this.mInterpolator == null) {
/* 622 */                       this.mInterpolator = new LinearInterpolator();
                            }
/* 625 */                   this.mTransformation = new Transformation();
/* 626 */                   this.mAnimation = new AlphaAnimation(0.0f, 1.0f);
/* 627 */                   this.mAnimation.setRepeatMode(this.mBehavior);
/* 628 */                   this.mAnimation.setRepeatCount(-1);
/* 629 */                   this.mAnimation.setDuration(this.mDuration);
/* 630 */                   this.mAnimation.setInterpolator(this.mInterpolator);
/* 631 */                   this.mAnimation.setStartTime(-1L);
                        }
/* 633 */               postInvalidate();
                    }
                }

/* 639 */       void stopAnimation() {
/* 640 */           this.mAnimation = null;
/* 641 */           this.mTransformation = null;
/* 642 */           if (this.mIndeterminateDrawable instanceof Animatable) {
/* 643 */               ((Animatable) this.mIndeterminateDrawable).stop();
/* 644 */               this.mShouldStartAnimationDrawable = false;
                    }
/* 646 */           postInvalidate();
                }

/* 656 */       public void setInterpolator(Context context, int resID) {
/* 657 */           setInterpolator(AnimationUtils.loadInterpolator(context, resID));
                }

/* 666 */       public void setInterpolator(Interpolator interpolator) {
/* 667 */           this.mInterpolator = interpolator;
                }

/* 675 */       public Interpolator getInterpolator() {
/* 676 */           return this.mInterpolator;
                }

                @Override // android.view.View
/* 680 */       public void setVisibility(int v) {
/* 681 */           if (getVisibility() != v) {
/* 682 */               super.setVisibility(v);
/* 684 */               if (this.mIndeterminate) {
/* 686 */                   if (v == 8 || v == 4) {
/* 687 */                       stopAnimation();
                            } else {
/* 689 */                       startAnimation();
                            }
                        }
                    }
                }

                @Override // android.view.View
/* 696 */       protected void onVisibilityChanged(View changedView, int visibility) {
/* 697 */           super.onVisibilityChanged(changedView, visibility);
/* 699 */           if (this.mIndeterminate) {
/* 701 */               if (visibility == 8 || visibility == 4) {
/* 702 */                   stopAnimation();
                        } else {
/* 704 */                   startAnimation();
                        }
                    }
                }

                @Override // android.view.View, android.graphics.drawable.Drawable.Callback
/* 710 */       public void invalidateDrawable(Drawable dr) {
/* 711 */           if (!this.mInDrawing) {
/* 712 */               if (verifyDrawable(dr)) {
/* 713 */                   Rect dirty = dr.getBounds();
/* 714 */                   int scrollX = getScrollX() + getPaddingLeft();
/* 715 */                   int scrollY = getScrollY() + getPaddingTop();
/* 717 */                   invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
/* 723 */                   return;
                        }
/* 720 */               super.invalidateDrawable(dr);
                    }
                }

                @Override // android.view.View
/* 726 */       protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 727 */           updateDrawableBounds(w, h);
                }

/* 731 */       private void updateDrawableBounds(int w, int h) {
/* 732 */           int right = (w - getPaddingRight()) - getPaddingLeft();
/* 733 */           int bottom = (h - getPaddingBottom()) - getPaddingTop();
/* 734 */           int top = 0;
/* 735 */           int left = 0;
/* 737 */           if (this.mIndeterminateDrawable != null) {
/* 739 */               if (this.mOnlyIndeterminate && !(this.mIndeterminateDrawable instanceof AnimationDrawable)) {
/* 742 */                   int intrinsicWidth = this.mIndeterminateDrawable.getIntrinsicWidth();
/* 743 */                   int intrinsicHeight = this.mIndeterminateDrawable.getIntrinsicHeight();
/* 744 */                   float intrinsicAspect = intrinsicWidth / intrinsicHeight;
/* 745 */                   float boundAspect = w / h;
                            if (intrinsicAspect != boundAspect) {
                                if (boundAspect > intrinsicAspect) {
/* 749 */                           int width = (int) (h * intrinsicAspect);
/* 750 */                           left = (w - width) / 2;
/* 751 */                           right = left + width;
                                } else {
/* 754 */                           int height = (int) (w * (1.0f / intrinsicAspect));
/* 755 */                           top = (h - height) / 2;
/* 756 */                           bottom = top + height;
                                }
                            }
                        }
/* 760 */               this.mIndeterminateDrawable.setBounds(left, top, right, bottom);
                    }
/* 763 */           if (this.mProgressDrawable != null) {
/* 764 */               this.mProgressDrawable.setBounds(0, 0, right, bottom);
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View
/* 769 */       protected synchronized void onDraw(Canvas canvas) {
                    try {
                        super.onDraw(canvas);
/* 772 */               Drawable drawable = this.mCurrentDrawable;
/* 773 */               if (drawable != 0) {
/* 776 */                   canvas.save();
/* 777 */                   canvas.translate(getPaddingLeft(), getPaddingTop());
/* 778 */                   long time = getDrawingTime();
/* 779 */                   if (this.mAnimation != null) {
/* 780 */                       this.mAnimation.getTransformation(time, this.mTransformation);
/* 781 */                       float scale = this.mTransformation.getAlpha();
                                try {
/* 783 */                           this.mInDrawing = true;
/* 784 */                           drawable.setLevel((int) (10000.0f * scale));
/* 786 */                           this.mInDrawing = false;
                                    if (SystemClock.uptimeMillis() - this.mLastDrawTime >= 200) {
/* 789 */                               this.mLastDrawTime = SystemClock.uptimeMillis();
/* 790 */                               postInvalidateDelayed(200L);
                                    }
                                } catch (Throwable th) {
                                    this.mInDrawing = false;
                                    throw th;
                                }
                            }
/* 793 */                   drawable.draw(canvas);
/* 794 */                   canvas.restore();
/* 795 */                   if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
/* 796 */                       ((Animatable) drawable).start();
/* 797 */                       this.mShouldStartAnimationDrawable = false;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

                @Override // android.view.View
/* 803 */       protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    try {
                        Drawable d = this.mCurrentDrawable;
/* 806 */               int dw = 0;
/* 807 */               int dh = 0;
/* 808 */               if (d != null) {
/* 809 */                   dw = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, d.getIntrinsicWidth()));
/* 810 */                   dh = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, d.getIntrinsicHeight()));
                        }
/* 812 */               updateDrawableState();
/* 816 */               setMeasuredDimension(resolveSize(dw + getPaddingLeft() + getPaddingRight(), widthMeasureSpec), resolveSize(dh + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.view.View
/* 821 */       protected void drawableStateChanged() {
/* 822 */           super.drawableStateChanged();
/* 823 */           updateDrawableState();
                }

/* 826 */       private void updateDrawableState() {
/* 827 */           int[] state = getDrawableState();
/* 829 */           if (this.mProgressDrawable != null && this.mProgressDrawable.isStateful()) {
/* 830 */               this.mProgressDrawable.setState(state);
                    }
/* 833 */           if (this.mIndeterminateDrawable != null && this.mIndeterminateDrawable.isStateful()) {
/* 834 */               this.mIndeterminateDrawable.setState(state);
                    }
                }

/* 836 */       static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C01051();
                    int progress;
                    int secondaryProgress;

/* 837 */           /* synthetic */ SavedState(Parcel x0, C01041 x1) {
/* 838 */               this(x0);
                    }

/* 845 */           SavedState(Parcelable superState) {
/* 846 */               super(superState);
                    }

/* 852 */           private SavedState(Parcel in) {
/* 853 */               super(in);
/* 854 */               this.progress = in.readInt();
/* 855 */               this.secondaryProgress = in.readInt();
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 859 */           public void writeToParcel(Parcel out, int flags) {
/* 860 */               super.writeToParcel(out, flags);
/* 861 */               out.writeInt(this.progress);
/* 862 */               out.writeInt(this.secondaryProgress);
                    }

                    /* JADX INFO: renamed from: android.support.v7.internal.widget.ProgressBarICS$SavedState$1 */
/* 864 */           static class C01051 implements Parcelable.Creator<SavedState> {
/* 865 */               C01051() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 865 */               public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 866 */                   return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 865 */               public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 866 */                   return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 867 */               public SavedState createFromParcel(Parcel in) {
/* 868 */                   return new SavedState(in, null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 871 */               public SavedState[] newArray(int size) {
/* 872 */                   return new SavedState[size];
                        }
                    }
                }

                @Override // android.view.View
/* 879 */       public Parcelable onSaveInstanceState() {
/* 880 */           Parcelable superState = super.onSaveInstanceState();
/* 881 */           SavedState ss = new SavedState(superState);
/* 883 */           ss.progress = this.mProgress;
/* 884 */           ss.secondaryProgress = this.mSecondaryProgress;
/* 886 */           return ss;
                }

                @Override // android.view.View
/* 890 */       public void onRestoreInstanceState(Parcelable state) {
/* 891 */           SavedState ss = (SavedState) state;
/* 892 */           super.onRestoreInstanceState(ss.getSuperState());
/* 894 */           setProgress(ss.progress);
/* 895 */           setSecondaryProgress(ss.secondaryProgress);
                }

                @Override // android.view.View
/* 899 */       protected void onAttachedToWindow() {
/* 900 */           super.onAttachedToWindow();
/* 901 */           if (this.mIndeterminate) {
/* 902 */               startAnimation();
                    }
                }

                @Override // android.view.View
/* 907 */       protected void onDetachedFromWindow() {
/* 908 */           if (this.mIndeterminate) {
/* 909 */               stopAnimation();
                    }
/* 911 */           if (this.mRefreshProgressRunnable != null) {
/* 912 */               removeCallbacks(this.mRefreshProgressRunnable);
                    }
/* 917 */           super.onDetachedFromWindow();
                }
            }
