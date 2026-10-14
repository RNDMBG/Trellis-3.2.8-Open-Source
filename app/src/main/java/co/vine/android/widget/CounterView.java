            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.Typeface;
            import android.util.AttributeSet;
            import android.view.View;
            import co.vine.android.C0257R;
            import java.util.ArrayList;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class CounterView extends View implements Runnable {
                private static final int ANIMATION_DURATION_MAX = (int) TimeUnit.NANOSECONDS.convert(150, TimeUnit.MILLISECONDS);
                private static final int ANIMATION_DURATION_MIN = (int) TimeUnit.NANOSECONDS.convert(20, TimeUnit.MILLISECONDS);
                private static final int FRAME_DELAY = 20;
                private static final int K_INDEX = 10;
                private static final String K_SEPARATOR = ",";
                private static final boolean NO_ANIMATE_TO_NEXT_NUMBER = false;
                private static final String TAG = "CounterView";
                private AnimationMode mAnimationMode;
                private Rect[] mBounds;
                private volatile boolean mCanAnimate;
                private final ArrayList<DigitAnimation> mDigitAnimations;
                private long mDrawingCount;
                private long mExtraCount;
                private int mInterCharacterSpacingX;
                private int mInterCharacterSpacingY;
                private boolean mIsPaused;
                private long mKnownCount;
                private long mKnownCountRefreshTime;
                private final int[] mLOCK;
                private long mLastAnimationTime;
                private int mLastDigitCount;
                private String mLastPrint;
                private long mMaxAnimationSeparation;
                private int mMaxTextHeight;
                private int mMaxTextWidth;
                private long mMinAnimationSeparation;
                private final Paint mPaint;
                private double mVelocityPerMS;

/* 19 */        static /* synthetic */ AnimationMode access$000(CounterView x0) {
/* 20 */            return x0.mAnimationMode;
                }

/* 19 */        static /* synthetic */ int access$100() {
/* 20 */            return ANIMATION_DURATION_MAX;
                }

/* 19 */        static /* synthetic */ int access$200() {
/* 20 */            return ANIMATION_DURATION_MIN;
                }

/* 55 */        public void setAnimationMode(AnimationMode animationMode) {
/* 56 */            this.mAnimationMode = animationMode;
                }

/* 59 */        public void setMinAnimationSeparation(long minAnimationSeparation) {
/* 60 */            this.mMinAnimationSeparation = minAnimationSeparation;
                }

/* 63 */        public void setMaxAnimationSeparation(long maxAnimationSeparation) {
/* 64 */            this.mMaxAnimationSeparation = maxAnimationSeparation;
                }

/* 67 */        public void incrementExtraCount(int n) {
/* 68 */            adjustExtraCount(this.mExtraCount + ((long) n));
                }

/* 74 */        public static class AnimationMode {
                    public final boolean alphaAnimation;
                    public final boolean continuousAnimation;
                    public final boolean pedometerAnimation;

/* 75 */            public AnimationMode(boolean continuousAnimation, boolean pedometerAnimation, boolean alphaAnimation) {
/* 77 */                this.continuousAnimation = continuousAnimation;
/* 78 */                this.pedometerAnimation = pedometerAnimation;
/* 79 */                this.alphaAnimation = alphaAnimation;
                    }
                }

/* 92 */        public class DigitAnimation {
                    public long mAnimatingCount;
                    public long mAnimationDuration;
                    public long mAnimationStartTime;
                    public int mCurrentAnimatingToDigit;
                    private final double mDivider;
                    public int mDrawingDigit;
                    public final int mIndexFromRight;
                    public boolean mIsAnimating;
                    private final double mNextDivider;

/* 93 */            public DigitAnimation(int indexFromRight, double velocityPerMS, long drawingCount) {
/* 95 */                this.mIndexFromRight = indexFromRight;
/* 96 */                this.mDivider = Math.pow(10.0d, this.mIndexFromRight);
/* 97 */                this.mNextDivider = Math.pow(10.0d, this.mIndexFromRight + 1);
/* 98 */                if (this.mIndexFromRight > 0) {
/* 99 */                    this.mDrawingDigit = (int) ((drawingCount / this.mDivider) % 10.0d);
                        } else {
/* 101 */                   this.mDrawingDigit = (int) (drawingCount % 10);
                        }
/* 103 */               this.mAnimatingCount = drawingCount;
/* 105 */               if (velocityPerMS > 0.0d && CounterView.access$000(CounterView.this).continuousAnimation) {
/* 106 */                   if (this.mIndexFromRight > 0) {
/* 107 */                       this.mAnimationDuration = (long) (1.0d / (velocityPerMS / ((double) (this.mIndexFromRight * 10))));
                            } else {
/* 109 */                       this.mAnimationDuration = (long) (1.0d / velocityPerMS);
                            }
                        } else {
/* 112 */                   this.mAnimationDuration = 2147483647L;
                        }
/* 117 */               this.mAnimationDuration *= 1000;
/* 118 */               this.mAnimationDuration = Math.min(this.mAnimationDuration, CounterView.access$100());
/* 119 */               this.mAnimationDuration = Math.max(this.mAnimationDuration, CounterView.access$200());
                    }

/* 122 */           public boolean invalidate(long currentTime, long currentCount, boolean shouldAnimate) {
/* 123 */               int currentRealDigit = this.mDrawingDigit;
/* 124 */               if (this.mIsAnimating && currentTime - this.mAnimationStartTime > this.mAnimationDuration) {
/* 126 */                   if (this.mIndexFromRight > 0) {
/* 127 */                       currentRealDigit = (int) ((this.mAnimatingCount / this.mDivider) % 10.0d);
                            } else {
/* 129 */                       currentRealDigit = (int) (this.mAnimatingCount % 10);
                            }
/* 131 */                   if (CounterView.access$000(CounterView.this).pedometerAnimation) {
/* 132 */                       this.mDrawingDigit = (this.mDrawingDigit + 1) % 10;
                            } else {
/* 134 */                       this.mDrawingDigit = this.mCurrentAnimatingToDigit;
                            }
/* 136 */                   this.mIsAnimating = false;
                        }
/* 139 */               boolean shouldAnimateNext = false;
/* 140 */               if ((shouldAnimate || currentRealDigit != this.mDrawingDigit) && !this.mIsAnimating) {
/* 142 */                   this.mIsAnimating = true;
/* 143 */                   this.mAnimationStartTime = System.nanoTime();
/* 144 */                   int currentRest = (int) (this.mAnimatingCount / this.mNextDivider);
/* 145 */                   int nextRest = (int) (currentCount / this.mNextDivider);
/* 146 */                   shouldAnimateNext = currentRest != nextRest;
/* 147 */                   this.mAnimatingCount = currentCount;
/* 148 */                   if (CounterView.access$000(CounterView.this).pedometerAnimation) {
/* 149 */                       this.mCurrentAnimatingToDigit = (this.mDrawingDigit + 1) % 10;
                            } else if (this.mIndexFromRight > 0) {
/* 152 */                       this.mCurrentAnimatingToDigit = (int) ((this.mAnimatingCount / this.mDivider) % 10.0d);
                            } else {
/* 154 */                       this.mCurrentAnimatingToDigit = (int) (this.mAnimatingCount % 10);
                            }
                        }
/* 159 */               return shouldAnimateNext;
                    }
                }

/* 163 */       public CounterView(Context context) {
/* 164 */           super(context);
/* 42 */            this.mDigitAnimations = new ArrayList<>();
/* 45 */            this.mAnimationMode = new AnimationMode(false, false, true);
/* 49 */            this.mLOCK = new int[0];
/* 165 */           this.mPaint = init();
/* 166 */           updateTextSizes();
                }

/* 169 */       private Paint init() {
/* 170 */           Paint paint = new Paint(65);
/* 171 */           this.mBounds = new Rect[]{new Rect(), new Rect(), new Rect(), new Rect(), new Rect(), new Rect(), new Rect(), new Rect(), new Rect(), new Rect(), new Rect()};
/* 184 */           paint.setTextSize(32.0f);
/* 185 */           paint.setColor(-16777216);
/* 186 */           paint.setStyle(Paint.Style.FILL);
/* 187 */           paint.setTextAlign(Paint.Align.RIGHT);
/* 188 */           return paint;
                }

/* 191 */       private void updateTextSizes() {
/* 192 */           this.mMaxTextWidth = 0;
/* 193 */           this.mMaxTextHeight = 0;
                    for (int i = 0; i < this.mBounds.length; i++) {
/* 195 */               if (i != 10) {
/* 196 */                   this.mPaint.getTextBounds(String.valueOf(i), 0, 1, this.mBounds[i]);
                        } else {
/* 198 */                   this.mPaint.getTextBounds(K_SEPARATOR, 0, 1, this.mBounds[i]);
                        }
/* 200 */               if (this.mMaxTextWidth < this.mBounds[i].width()) {
/* 201 */                   this.mMaxTextWidth = this.mBounds[i].width();
                        }
/* 203 */               if (this.mMaxTextHeight < this.mBounds[i].height()) {
/* 204 */                   this.mMaxTextHeight = this.mBounds[i].height();
                        }
                    }
/* 207 */           this.mInterCharacterSpacingX = getResources().getDimensionPixelSize(C0257R.dimen.counter_horizontal_text_spacing);
/* 208 */           this.mInterCharacterSpacingY = getResources().getDimensionPixelSize(C0257R.dimen.counter_vertical_text_spacing);
                }

/* 211 */       public CounterView(Context context, AttributeSet attrs) {
/* 212 */           super(context, attrs);
/* 42 */            this.mDigitAnimations = new ArrayList<>();
/* 45 */            this.mAnimationMode = new AnimationMode(false, false, true);
/* 49 */            this.mLOCK = new int[0];
/* 213 */           this.mPaint = init();
/* 214 */           updateTextSizes();
                }

/* 217 */       public CounterView(Context context, AttributeSet attrs, int defStyle) {
/* 218 */           super(context, attrs, defStyle);
/* 42 */            this.mDigitAnimations = new ArrayList<>();
/* 45 */            this.mAnimationMode = new AnimationMode(false, false, true);
/* 49 */            this.mLOCK = new int[0];
/* 219 */           this.mPaint = init();
/* 220 */           updateTextSizes();
                }

/* 223 */       public void setTypeFace(Typeface typeFace) {
/* 224 */           this.mPaint.setTypeface(typeFace);
/* 225 */           requestLayout();
                }

/* 228 */       public void setTextSize(float textSize) {
/* 229 */           this.mPaint.setTextSize(textSize);
/* 230 */           requestLayout();
                }

/* 233 */       public void reset() {
/* 234 */           this.mLastDigitCount = 0;
/* 235 */           this.mKnownCount = 0L;
/* 236 */           this.mKnownCountRefreshTime = -1L;
/* 237 */           this.mVelocityPerMS = 0.0d;
/* 238 */           this.mDrawingCount = 0L;
/* 239 */           this.mDigitAnimations.clear();
                }

/* 242 */       public long setKnownCount(long knownCount, double velocityPerMS, long knownCountRefreshTime) {
                    if (knownCountRefreshTime > 0) {
/* 244 */               long time = System.currentTimeMillis();
/* 245 */               this.mKnownCount = (long) (knownCount + ((time - knownCountRefreshTime) * velocityPerMS));
/* 246 */               this.mKnownCountRefreshTime = time;
                    } else {
/* 248 */               this.mKnownCount = knownCount;
                    }
/* 250 */           this.mVelocityPerMS = velocityPerMS;
                    synchronized (this.mLOCK) {
                        try {
/* 252 */                   this.mLastDigitCount = 0;
/* 253 */                   this.mDigitAnimations.clear();
/* 254 */                   invalidateDigitSize(true);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 256 */           return this.mKnownCount;
                }

/* 259 */       public void pause() {
/* 260 */           this.mIsPaused = true;
/* 261 */           removeCallbacks(this);
                }

/* 264 */       public void resume() {
/* 265 */           if (this.mIsPaused) {
/* 266 */               this.mIsPaused = false;
/* 267 */               postInvalidate();
                    }
                }

/* 271 */       public long getCount() {
                    return (long) ((this.mKnownCountRefreshTime > 0 ? this.mVelocityPerMS * (System.currentTimeMillis() - this.mKnownCountRefreshTime) : 0.0d) + this.mKnownCount);
                }

                @Override // android.view.View
/* 276 */       public void onDraw(Canvas canvas) {
/* 277 */           int x = getMeasuredWidth();
/* 278 */           int measuredHeight = getMeasuredHeight();
                    synchronized (this.mLOCK) {
                        try {
/* 280 */                   int length = this.mDigitAnimations.size();
/* 281 */                   String num = "";
                            for (int i = 0; i < length; i++) {
/* 283 */                       if (i >= 3 && i % 3 == 0) {
/* 284 */                           this.mPaint.setAlpha(255);
/* 285 */                           canvas.drawText(K_SEPARATOR, x, (measuredHeight >> 1) + this.mBounds[10].height() + this.mInterCharacterSpacingY, this.mPaint);
/* 286 */                           x -= this.mBounds[10].width();
                                }
/* 288 */                       DigitAnimation digitAnimation = this.mDigitAnimations.get(i);
/* 289 */                       int digit = digitAnimation.mDrawingDigit;
/* 290 */                       String digitText = String.valueOf(digit);
/* 291 */                       int y = getIntrinsicHeightForDigit(measuredHeight, digit);
/* 292 */                       if (digitAnimation.mIsAnimating) {
/* 293 */                           long diff = System.nanoTime() - digitAnimation.mAnimationStartTime;
/* 294 */                           double progress = diff / digitAnimation.mAnimationDuration;
                                    if (diff >= digitAnimation.mAnimationDuration || (digitAnimation.mCurrentAnimatingToDigit == digit && !this.mAnimationMode.pedometerAnimation)) {
/* 296 */                               num = num + digitAnimation.mCurrentAnimatingToDigit;
/* 297 */                               this.mPaint.setAlpha(255);
/* 298 */                               canvas.drawText(String.valueOf(digitAnimation.mCurrentAnimatingToDigit), x, y, this.mPaint);
                                    } else {
/* 300 */                               int topY = (int) (((double) y) - (((double) (this.mBounds[digit].height() + this.mInterCharacterSpacingY)) * progress));
/* 301 */                               if (digit != 0 || i != length - 1 || length == 1) {
/* 302 */                                   if (this.mAnimationMode.alphaAnimation) {
/* 303 */                                       this.mPaint.setAlpha((int) ((1.0d - progress) * 255.0d));
                                            }
/* 305 */                                   canvas.drawText(digitText, x, topY, this.mPaint);
                                        }
/* 307 */                               num = num + digitText;
/* 308 */                               int botY = this.mBounds[digit].height() + topY + this.mInterCharacterSpacingY;
/* 309 */                               if (this.mAnimationMode.alphaAnimation) {
/* 310 */                                   this.mPaint.setAlpha((int) (255.0d * progress));
                                        }
/* 312 */                               canvas.drawText(String.valueOf(digitAnimation.mCurrentAnimatingToDigit), x, botY, this.mPaint);
                                    }
                                } else {
/* 315 */                           num = num + digitText;
/* 316 */                           this.mPaint.setAlpha(255);
/* 317 */                           canvas.drawText(digitText, x, y, this.mPaint);
                                }
/* 319 */                       x -= this.mMaxTextWidth + this.mInterCharacterSpacingX;
                            }
/* 321 */                   if (!num.equals(this.mLastPrint)) {
/* 322 */                       this.mLastPrint = num;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 325 */           removeCallbacks(this);
/* 326 */           if (!this.mIsPaused) {
/* 327 */               postDelayed(this, 20L);
                    }
                }

                @Override // android.view.View
/* 332 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int width;
                    int height;
/* 333 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 334 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 335 */           int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 336 */           int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
/* 340 */           updateTextSizes();
/* 342 */           if (widthMode == 1073741824) {
/* 343 */               width = widthSize;
                    } else {
/* 345 */               int digits = Math.max(this.mLastDigitCount, String.valueOf(this.mDrawingCount).length());
/* 346 */               width = ((this.mMaxTextWidth + this.mInterCharacterSpacingX) * digits) + ((digits / 3) * this.mBounds[10].width()) + getPaddingLeft() + getPaddingRight();
/* 349 */               if (heightMode == Integer.MIN_VALUE) {
/* 350 */                   width = Math.min(width, widthSize);
                        }
                    }
/* 353 */           if (heightMode == 1073741824) {
/* 354 */               height = heightSize;
                    } else {
/* 356 */               height = this.mMaxTextHeight + getPaddingTop() + getPaddingBottom() + this.mInterCharacterSpacingY;
/* 357 */               if (heightMode == Integer.MIN_VALUE) {
/* 358 */                   height = Math.min(height, heightSize);
                        }
                    }
/* 361 */           setMeasuredDimension(width, height);
                }

/* 364 */       private int getIntrinsicHeightForDigit(int measuredHeight, int digit) {
/* 365 */           return (this.mBounds[digit].height() + measuredHeight) >> 1;
                }

/* 368 */       public void invalidateCounterUI() {
/* 369 */           this.mCanAnimate = true;
                }

/* 376 */       public void adjustExtraCount(long extraCount) {
/* 377 */           this.mExtraCount = extraCount;
                }

/* 380 */       private void invalidateDigitSize(boolean forceDigitSizeRecal) {
                    synchronized (this.mLOCK) {
                        try {
/* 382 */                   if (this.mDigitAnimations.size() == 0) {
/* 383 */                       this.mDigitAnimations.add(new DigitAnimation(0, this.mVelocityPerMS, this.mDrawingCount));
                            }
/* 385 */                   long currentCount = getCount() + this.mExtraCount;
/* 386 */                   long currentTime = System.nanoTime();
/* 387 */                   int digitCount = String.valueOf(this.mDrawingCount).length();
/* 388 */                   long diffTimeMs = System.currentTimeMillis() - this.mLastAnimationTime;
/* 389 */                   boolean shouldAnimateNext = currentCount > this.mDigitAnimations.get(0).mAnimatingCount;
/* 390 */                   if (forceDigitSizeRecal || (diffTimeMs >= this.mMinAnimationSeparation && (this.mCanAnimate || diffTimeMs >= this.mMaxAnimationSeparation))) {
/* 393 */                       this.mLastAnimationTime = System.currentTimeMillis();
/* 394 */                       int i = 0;
/* 395 */                       long newDrawingCount = 0;
                                while (true) {
/* 396 */                           if (i >= digitCount && !shouldAnimateNext) {
                                        break;
                                    }
/* 397 */                           if (this.mDigitAnimations.size() <= i) {
/* 398 */                               this.mDigitAnimations.add(new DigitAnimation(i, this.mVelocityPerMS, this.mDrawingCount));
                                    }
/* 400 */                           DigitAnimation digitAnim = this.mDigitAnimations.get(i);
/* 401 */                           shouldAnimateNext = digitAnim.invalidate(currentTime, currentCount, shouldAnimateNext);
                                    newDrawingCount = (long) ((i == 0 ? digitAnim.mDrawingDigit : ((double) digitAnim.mDrawingDigit) * Math.pow(10.0d, i)) + newDrawingCount);
/* 406 */                           i++;
                                }
/* 408 */                       setDrawingCount(newDrawingCount, forceDigitSizeRecal);
/* 409 */                       if (this.mLastDigitCount != this.mDigitAnimations.size()) {
/* 410 */                           this.mLastDigitCount = this.mDigitAnimations.size();
/* 411 */                           requestLayout();
                                }
/* 413 */                       if (!this.mAnimationMode.continuousAnimation) {
/* 414 */                           this.mCanAnimate = false;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 420 */       private void setDrawingCount(long newDrawingCount, boolean invalidation) {
/* 421 */           this.mDrawingCount = newDrawingCount;
                }

                @Override // java.lang.Runnable
/* 425 */       public void run() {
/* 426 */           invalidateDigitSize(false);
/* 427 */           if (!this.mIsPaused && isShown()) {
/* 428 */               postInvalidate();
                    }
                }
            }
