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
            
            
    public class ProgressBarICS extends View {
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

        static  void access$000(ProgressBarICS x0, int x1, int x2, boolean x3, boolean x4) {
            x0.doRefreshProgress(x1, x2, x3, x4);
                }

        static  RefreshProgressRunnable access$102(ProgressBarICS x0, RefreshProgressRunnable x1) {
            x0.mRefreshProgressRunnable = x1;
                    return x1;
                }

       public ProgressBarICS(Context context, AttributeSet attrs, int defStyle, int styleRes) {
           super(context, attrs, defStyle);
           this.mUiThreadId = Thread.currentThread().getId();
           initProgressBar();
           TypedArray a = context.obtainStyledAttributes(attrs, android_R_styleable_ProgressBar, defStyle, styleRes);
           this.mNoInvalidate = true;
           setMax(a.getInt(0, this.mMax));
           setProgress(a.getInt(1, this.mProgress));
           setSecondaryProgress(a.getInt(2, this.mSecondaryProgress));
           boolean indeterminate = a.getBoolean(3, this.mIndeterminate);
           this.mOnlyIndeterminate = a.getBoolean(4, this.mOnlyIndeterminate);
           Drawable drawable = a.getDrawable(5);
           if (drawable != null) {
               setIndeterminateDrawable(tileifyIndeterminate(drawable));
                    }
           Drawable drawable2 = a.getDrawable(6);
           if (drawable2 != null) {
               setProgressDrawable(tileify(drawable2, false));
                    }
           this.mDuration = a.getInt(7, this.mDuration);
           this.mBehavior = a.getInt(8, this.mBehavior);
           this.mMinWidth = a.getDimensionPixelSize(9, this.mMinWidth);
           this.mMaxWidth = a.getDimensionPixelSize(10, this.mMaxWidth);
           this.mMinHeight = a.getDimensionPixelSize(11, this.mMinHeight);
           this.mMaxHeight = a.getDimensionPixelSize(12, this.mMaxHeight);
           int resID = a.getResourceId(13, R.anim.linear_interpolator);
           if (resID > 0) {
               setInterpolator(context, resID);
                    }
           a.recycle();
           this.mNoInvalidate = false;
           setIndeterminate(this.mOnlyIndeterminate || indeterminate);
                }

       private Drawable tileify(Drawable drawable, boolean z) {
           if (drawable instanceof LayerDrawable) {
               LayerDrawable layerDrawable = (LayerDrawable) drawable;
               int numberOfLayers = layerDrawable.getNumberOfLayers();
               Drawable[] drawableArr = new Drawable[numberOfLayers];
                        for (int i = 0; i < numberOfLayers; i++) {
                   int id = layerDrawable.getId(i);
                            drawableArr[i] = tileify(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303);
                        }
               LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                        for (int i2 = 0; i2 < numberOfLayers; i2++) {
                   layerDrawable2.setId(i2, layerDrawable.getId(i2));
                        }
               return layerDrawable2;
                    }
           if (!(drawable instanceof BitmapDrawable)) {
                        return drawable;
                    }
           Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
           if (this.mSampleTile == null) {
               this.mSampleTile = bitmap;
                    }
           ShapeDrawable shapeDrawable = new ShapeDrawable(getDrawableShape());
           shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                    Drawable clipDrawable = shapeDrawable;
           if (z) {
                        clipDrawable = new ClipDrawable(shapeDrawable, 3, 1);
                    }
                    return clipDrawable;
                }

       Shape getDrawableShape() {
           float[] roundedCorners = {5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
           return new RoundRectShape(roundedCorners, null, null);
                }

       private Drawable tileifyIndeterminate(Drawable drawable) {
           if (drawable instanceof AnimationDrawable) {
               AnimationDrawable background = (AnimationDrawable) drawable;
               int N = background.getNumberOfFrames();
               AnimationDrawable newBg = new AnimationDrawable();
               newBg.setOneShot(background.isOneShot());
                        for (int i = 0; i < N; i++) {
                   Drawable frame = tileify(background.getFrame(i), true);
                   frame.setLevel(10000);
                   newBg.addFrame(frame, background.getDuration(i));
                        }
               newBg.setLevel(10000);
               return newBg;
                    }
           return drawable;
                }

       private void initProgressBar() {
           this.mMax = 100;
           this.mProgress = 0;
           this.mSecondaryProgress = 0;
           this.mIndeterminate = false;
           this.mOnlyIndeterminate = false;
           this.mDuration = 4000;
           this.mBehavior = 1;
           this.mMinWidth = 24;
           this.mMaxWidth = 48;
           this.mMinHeight = 24;
           this.mMaxHeight = 48;
                }

       public synchronized boolean isIndeterminate() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mIndeterminate;
                }

       public synchronized void setIndeterminate(boolean indeterminate) {
                    try {
                        if ((!this.mOnlyIndeterminate || !this.mIndeterminate) && indeterminate != this.mIndeterminate) {
                   this.mIndeterminate = indeterminate;
                   if (indeterminate) {
                       this.mCurrentDrawable = this.mIndeterminateDrawable;
                       startAnimation();
                            } else {
                       this.mCurrentDrawable = this.mProgressDrawable;
                       stopAnimation();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public Drawable getIndeterminateDrawable() {
           return this.mIndeterminateDrawable;
                }

       public void setIndeterminateDrawable(Drawable d) {
           if (d != null) {
               d.setCallback(this);
                    }
           this.mIndeterminateDrawable = d;
           if (this.mIndeterminate) {
               this.mCurrentDrawable = d;
               postInvalidate();
                    }
                }

       public Drawable getProgressDrawable() {
           return this.mProgressDrawable;
                }

       public void setProgressDrawable(Drawable d) {
                    boolean needUpdate;
           if (this.mProgressDrawable != null && d != this.mProgressDrawable) {
               this.mProgressDrawable.setCallback(null);
               needUpdate = true;
                    } else {
               needUpdate = false;
                    }
           if (d != null) {
               d.setCallback(this);
               int drawableHeight = d.getMinimumHeight();
               if (this.mMaxHeight < drawableHeight) {
                   this.mMaxHeight = drawableHeight;
                   requestLayout();
                        }
                    }
           this.mProgressDrawable = d;
           if (!this.mIndeterminate) {
               this.mCurrentDrawable = d;
               postInvalidate();
                    }
           if (needUpdate) {
               updateDrawableBounds(getWidth(), getHeight());
               updateDrawableState();
               doRefreshProgress(R.id.progress, this.mProgress, false, false);
               doRefreshProgress(R.id.secondaryProgress, this.mSecondaryProgress, false, false);
                    }
                }

                @Override 
       protected boolean verifyDrawable(Drawable who) {
                    return who == this.mProgressDrawable || who == this.mIndeterminateDrawable || super.verifyDrawable(who);
                }

                @Override 
       public void postInvalidate() {
           if (!this.mNoInvalidate) {
               super.postInvalidate();
                    }
                }

       private class RefreshProgressRunnable implements Runnable {
                    private boolean mFromUser;
                    private int mId;
                    private int mProgress;

           RefreshProgressRunnable(int id, int progress, boolean fromUser) {
               this.mId = id;
               this.mProgress = progress;
               this.mFromUser = fromUser;
                    }

                    @Override 
           public void run() {
               ProgressBarICS.access$000(ProgressBarICS.this, this.mId, this.mProgress, this.mFromUser, true);
               ProgressBarICS.access$102(ProgressBarICS.this, this);
                    }

           public void setup(int id, int progress, boolean fromUser) {
               this.mId = id;
               this.mProgress = progress;
               this.mFromUser = fromUser;
                    }
                }

       private synchronized void doRefreshProgress(int id, int progress, boolean fromUser, boolean callBackToApp) {
                    try {
               float scale = this.mMax > 0 ? progress / this.mMax : 0.0f;
               Drawable d = this.mCurrentDrawable;
               if (d != null) {
                   Drawable progressDrawable = null;
                   if (d instanceof LayerDrawable) {
                       progressDrawable = ((LayerDrawable) d).findDrawableByLayerId(id);
                            }
                   int level = (int) (10000.0f * scale);
                   if (progressDrawable == null) {
                                progressDrawable = d;
                            }
                            progressDrawable.setLevel(level);
                        } else {
                   invalidate();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private synchronized void refreshProgress(int id, int progress, boolean fromUser) {
                    RefreshProgressRunnable r;
                    try {
                        if (this.mUiThreadId == Thread.currentThread().getId()) {
                   doRefreshProgress(id, progress, fromUser, true);
                        } else {
                   if (this.mRefreshProgressRunnable != null) {
                       r = this.mRefreshProgressRunnable;
                       this.mRefreshProgressRunnable = null;
                       r.setup(id, progress, fromUser);
                            } else {
                       r = new RefreshProgressRunnable(id, progress, fromUser);
                            }
                   post(r);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized void setProgress(int progress) {
                    try {
                        setProgress(progress, false);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void setProgress(int progress, boolean fromUser) {
                    try {
                        if (!this.mIndeterminate) {
                   if (progress < 0) {
                       progress = 0;
                            }
                   if (progress > this.mMax) {
                       progress = this.mMax;
                            }
                   if (progress != this.mProgress) {
                       this.mProgress = progress;
                       refreshProgress(R.id.progress, this.mProgress, fromUser);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized void setSecondaryProgress(int secondaryProgress) {
                    try {
                        if (!this.mIndeterminate) {
                   if (secondaryProgress < 0) {
                       secondaryProgress = 0;
                            }
                   if (secondaryProgress > this.mMax) {
                       secondaryProgress = this.mMax;
                            }
                   if (secondaryProgress != this.mSecondaryProgress) {
                       this.mSecondaryProgress = secondaryProgress;
                       refreshProgress(R.id.secondaryProgress, this.mSecondaryProgress, false);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized int getProgress() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mIndeterminate ? 0 : this.mProgress;
                }

       public synchronized int getSecondaryProgress() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mIndeterminate ? 0 : this.mSecondaryProgress;
                }

       public synchronized int getMax() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.mMax;
                }

       public synchronized void setMax(int max) {
                    if (max < 0) {
               max = 0;
                    }
                    try {
               if (max != this.mMax) {
                   this.mMax = max;
                   postInvalidate();
                   if (this.mProgress > max) {
                       this.mProgress = max;
                            }
                   refreshProgress(R.id.progress, this.mProgress, false);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public final synchronized void incrementProgressBy(int diff) {
                    try {
                        setProgress(this.mProgress + diff);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public final synchronized void incrementSecondaryProgressBy(int diff) {
                    try {
                        setSecondaryProgress(this.mSecondaryProgress + diff);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       void startAnimation() {
           if (getVisibility() == 0) {
               if (this.mIndeterminateDrawable instanceof Animatable) {
                   this.mShouldStartAnimationDrawable = true;
                   this.mAnimation = null;
                        } else {
                   if (this.mInterpolator == null) {
                       this.mInterpolator = new LinearInterpolator();
                            }
                   this.mTransformation = new Transformation();
                   this.mAnimation = new AlphaAnimation(0.0f, 1.0f);
                   this.mAnimation.setRepeatMode(this.mBehavior);
                   this.mAnimation.setRepeatCount(-1);
                   this.mAnimation.setDuration(this.mDuration);
                   this.mAnimation.setInterpolator(this.mInterpolator);
                   this.mAnimation.setStartTime(-1L);
                        }
               postInvalidate();
                    }
                }

       void stopAnimation() {
           this.mAnimation = null;
           this.mTransformation = null;
           if (this.mIndeterminateDrawable instanceof Animatable) {
               ((Animatable) this.mIndeterminateDrawable).stop();
               this.mShouldStartAnimationDrawable = false;
                    }
           postInvalidate();
                }

       public void setInterpolator(Context context, int resID) {
           setInterpolator(AnimationUtils.loadInterpolator(context, resID));
                }

       public void setInterpolator(Interpolator interpolator) {
           this.mInterpolator = interpolator;
                }

       public Interpolator getInterpolator() {
           return this.mInterpolator;
                }

                @Override 
       public void setVisibility(int v) {
           if (getVisibility() != v) {
               super.setVisibility(v);
               if (this.mIndeterminate) {
                   if (v == 8 || v == 4) {
                       stopAnimation();
                            } else {
                       startAnimation();
                            }
                        }
                    }
                }

                @Override 
       protected void onVisibilityChanged(View changedView, int visibility) {
           super.onVisibilityChanged(changedView, visibility);
           if (this.mIndeterminate) {
               if (visibility == 8 || visibility == 4) {
                   stopAnimation();
                        } else {
                   startAnimation();
                        }
                    }
                }

                @Override 
       public void invalidateDrawable(Drawable dr) {
           if (!this.mInDrawing) {
               if (verifyDrawable(dr)) {
                   Rect dirty = dr.getBounds();
                   int scrollX = getScrollX() + getPaddingLeft();
                   int scrollY = getScrollY() + getPaddingTop();
                   invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
                   return;
                        }
               super.invalidateDrawable(dr);
                    }
                }

                @Override 
       protected void onSizeChanged(int w, int h, int oldw, int oldh) {
           updateDrawableBounds(w, h);
                }

       private void updateDrawableBounds(int w, int h) {
           int right = (w - getPaddingRight()) - getPaddingLeft();
           int bottom = (h - getPaddingBottom()) - getPaddingTop();
           int top = 0;
           int left = 0;
           if (this.mIndeterminateDrawable != null) {
               if (this.mOnlyIndeterminate && !(this.mIndeterminateDrawable instanceof AnimationDrawable)) {
                   int intrinsicWidth = this.mIndeterminateDrawable.getIntrinsicWidth();
                   int intrinsicHeight = this.mIndeterminateDrawable.getIntrinsicHeight();
                   float intrinsicAspect = intrinsicWidth / intrinsicHeight;
                   float boundAspect = w / h;
                            if (intrinsicAspect != boundAspect) {
                                if (boundAspect > intrinsicAspect) {
                           int width = (int) (h * intrinsicAspect);
                           left = (w - width) / 2;
                           right = left + width;
                                } else {
                           int height = (int) (w * (1.0f / intrinsicAspect));
                           top = (h - height) / 2;
                           bottom = top + height;
                                }
                            }
                        }
               this.mIndeterminateDrawable.setBounds(left, top, right, bottom);
                    }
           if (this.mProgressDrawable != null) {
               this.mProgressDrawable.setBounds(0, 0, right, bottom);
                    }
                }

                
                @Override 
       protected synchronized void onDraw(Canvas canvas) {
                    try {
                        super.onDraw(canvas);
               Drawable drawable = this.mCurrentDrawable;
               if (drawable != 0) {
                   canvas.save();
                   canvas.translate(getPaddingLeft(), getPaddingTop());
                   long time = getDrawingTime();
                   if (this.mAnimation != null) {
                       this.mAnimation.getTransformation(time, this.mTransformation);
                       float scale = this.mTransformation.getAlpha();
                                try {
                           this.mInDrawing = true;
                           drawable.setLevel((int) (10000.0f * scale));
                           this.mInDrawing = false;
                                    if (SystemClock.uptimeMillis() - this.mLastDrawTime >= 200) {
                               this.mLastDrawTime = SystemClock.uptimeMillis();
                               postInvalidateDelayed(200L);
                                    }
                                } catch (Throwable th) {
                                    this.mInDrawing = false;
                                    throw th;
                                }
                            }
                   drawable.draw(canvas);
                   canvas.restore();
                   if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
                       ((Animatable) drawable).start();
                       this.mShouldStartAnimationDrawable = false;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

                @Override 
       protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    try {
                        Drawable d = this.mCurrentDrawable;
               int dw = 0;
               int dh = 0;
               if (d != null) {
                   dw = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, d.getIntrinsicWidth()));
                   dh = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, d.getIntrinsicHeight()));
                        }
               updateDrawableState();
               setMeasuredDimension(resolveSize(dw + getPaddingLeft() + getPaddingRight(), widthMeasureSpec), resolveSize(dh + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       protected void drawableStateChanged() {
           super.drawableStateChanged();
           updateDrawableState();
                }

       private void updateDrawableState() {
           int[] state = getDrawableState();
           if (this.mProgressDrawable != null && this.mProgressDrawable.isStateful()) {
               this.mProgressDrawable.setState(state);
                    }
           if (this.mIndeterminateDrawable != null && this.mIndeterminateDrawable.isStateful()) {
               this.mIndeterminateDrawable.setState(state);
                    }
                }

       static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C01051();
                    int progress;
                    int secondaryProgress;

            SavedState(Parcel x0, C01041 x1) {
               this(x0);
                    }

           SavedState(Parcelable superState) {
               super(superState);
                    }

           private SavedState(Parcel in) {
               super(in);
               this.progress = in.readInt();
               this.secondaryProgress = in.readInt();
                    }

                    @Override 
           public void writeToParcel(Parcel out, int flags) {
               super.writeToParcel(out, flags);
               out.writeInt(this.progress);
               out.writeInt(this.secondaryProgress);
                    }

                    
           static class C01051 implements Parcelable.Creator<SavedState> {
               C01051() {
                        }

                        @Override 
               public   SavedState createFromParcel(Parcel x0) {
                   return createFromParcel(x0);
                        }

                        @Override 
               public   SavedState[] newArray(int x0) {
                   return newArray(x0);
                        }

                        
                        @Override 
               public SavedState createFromParcel(Parcel in) {
                   return new SavedState(in, null);
                        }

                        
                        @Override 
               public SavedState[] newArray(int size) {
                   return new SavedState[size];
                        }
                    }
                }

                @Override 
       public Parcelable onSaveInstanceState() {
           Parcelable superState = super.onSaveInstanceState();
           SavedState ss = new SavedState(superState);
           ss.progress = this.mProgress;
           ss.secondaryProgress = this.mSecondaryProgress;
           return ss;
                }

                @Override 
       public void onRestoreInstanceState(Parcelable state) {
           SavedState ss = (SavedState) state;
           super.onRestoreInstanceState(ss.getSuperState());
           setProgress(ss.progress);
           setSecondaryProgress(ss.secondaryProgress);
                }

                @Override 
       protected void onAttachedToWindow() {
           super.onAttachedToWindow();
           if (this.mIndeterminate) {
               startAnimation();
                    }
                }

                @Override 
       protected void onDetachedFromWindow() {
           if (this.mIndeterminate) {
               stopAnimation();
                    }
           if (this.mRefreshProgressRunnable != null) {
               removeCallbacks(this.mRefreshProgressRunnable);
                    }
           super.onDetachedFromWindow();
                }
            }
