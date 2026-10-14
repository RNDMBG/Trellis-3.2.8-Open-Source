            package android.support.v4.widget;

            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.RectF;
            import android.support.v4.view.ViewCompat;
            import android.view.View;
            import android.view.animation.AnimationUtils;
            import android.view.animation.Interpolator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 67 */    final class SwipeProgressBar {
                private static final int ANIMATION_DURATION_MS = 2000;
                private static final int COLOR1 = -1291845632;
                private static final int COLOR2 = Integer.MIN_VALUE;
                private static final int COLOR3 = 1291845632;
                private static final int COLOR4 = 436207616;
                private static final int FINISH_ANIMATION_DURATION_MS = 1000;
                private static final Interpolator INTERPOLATOR = BakedBezierInterpolator.getInstance();
                private long mFinishTime;
                private View mParent;
                private boolean mRunning;
                private long mStartTime;
                private float mTriggerPercentage;
                private final Paint mPaint = new Paint();
                private final RectF mClipRect = new RectF();
                private Rect mBounds = new Rect();
                private int mColor1 = COLOR1;
                private int mColor2 = Integer.MIN_VALUE;
                private int mColor3 = COLOR3;
                private int mColor4 = COLOR4;

/* 68 */        public SwipeProgressBar(View parent) {
/* 70 */            this.mParent = parent;
                }

/* 87 */        void setColorScheme(int color1, int color2, int color3, int color4) {
/* 88 */            this.mColor1 = color1;
/* 89 */            this.mColor2 = color2;
/* 90 */            this.mColor3 = color3;
/* 91 */            this.mColor4 = color4;
                }

/* 99 */        void setTriggerPercentage(float triggerPercentage) {
/* 100 */           this.mTriggerPercentage = triggerPercentage;
/* 101 */           this.mStartTime = 0L;
/* 102 */           ViewCompat.postInvalidateOnAnimation(this.mParent);
                }

/* 108 */       void start() {
/* 109 */           if (!this.mRunning) {
/* 110 */               this.mTriggerPercentage = 0.0f;
/* 111 */               this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
/* 112 */               this.mRunning = true;
/* 113 */               this.mParent.postInvalidate();
                    }
                }

/* 120 */       void stop() {
/* 121 */           if (this.mRunning) {
/* 122 */               this.mTriggerPercentage = 0.0f;
/* 123 */               this.mFinishTime = AnimationUtils.currentAnimationTimeMillis();
/* 124 */               this.mRunning = false;
/* 125 */               this.mParent.postInvalidate();
                    }
                }

/* 132 */       boolean isRunning() {
                    return this.mRunning || this.mFinishTime > 0;
                }

/* 136 */       void draw(Canvas canvas) {
/* 137 */           int width = this.mBounds.width();
/* 138 */           int height = this.mBounds.height();
/* 139 */           int cx = width / 2;
/* 140 */           int cy = height / 2;
/* 141 */           boolean drawTriggerWhileFinishing = false;
/* 142 */           int restoreCount = canvas.save();
/* 143 */           canvas.clipRect(this.mBounds);
/* 145 */           if (this.mRunning || this.mFinishTime > 0) {
/* 146 */               long now = AnimationUtils.currentAnimationTimeMillis();
/* 147 */               long elapsed = (now - this.mStartTime) % 2000;
/* 148 */               long iterations = (now - this.mStartTime) / 2000;
/* 149 */               float rawProgress = elapsed / 20.0f;
/* 153 */               if (!this.mRunning) {
                            if (now - this.mFinishTime >= 1000) {
/* 157 */                       this.mFinishTime = 0L;
/* 238 */                       return;
                            }
/* 164 */                   long finishElapsed = (now - this.mFinishTime) % 1000;
/* 165 */                   float finishProgress = finishElapsed / 10.0f;
/* 166 */                   float pct = finishProgress / 100.0f;
/* 168 */                   float clearRadius = (width / 2) * INTERPOLATOR.getInterpolation(pct);
/* 169 */                   this.mClipRect.set(cx - clearRadius, 0.0f, cx + clearRadius, height);
/* 170 */                   canvas.saveLayerAlpha(this.mClipRect, 0, 0);
/* 175 */                   drawTriggerWhileFinishing = true;
                        }
                        if (iterations == 0) {
/* 180 */                   canvas.drawColor(this.mColor1);
                        } else if (rawProgress >= 0.0f && rawProgress < 25.0f) {
/* 183 */                   canvas.drawColor(this.mColor4);
                        } else if (rawProgress >= 25.0f && rawProgress < 50.0f) {
/* 185 */                   canvas.drawColor(this.mColor1);
                        } else if (rawProgress >= 50.0f && rawProgress < 75.0f) {
/* 187 */                   canvas.drawColor(this.mColor2);
                        } else {
/* 189 */                   canvas.drawColor(this.mColor3);
                        }
                        if (rawProgress >= 0.0f && rawProgress <= 25.0f) {
/* 200 */                   float pct2 = ((25.0f + rawProgress) * 2.0f) / 100.0f;
/* 201 */                   drawCircle(canvas, cx, cy, this.mColor1, pct2);
                        }
                        if (rawProgress >= 0.0f && rawProgress <= 50.0f) {
/* 204 */                   float pct3 = (2.0f * rawProgress) / 100.0f;
/* 205 */                   drawCircle(canvas, cx, cy, this.mColor2, pct3);
                        }
                        if (rawProgress >= 25.0f && rawProgress <= 75.0f) {
/* 208 */                   float pct4 = ((rawProgress - 25.0f) * 2.0f) / 100.0f;
/* 209 */                   drawCircle(canvas, cx, cy, this.mColor3, pct4);
                        }
                        if (rawProgress >= 50.0f && rawProgress <= 100.0f) {
/* 212 */                   float pct5 = ((rawProgress - 50.0f) * 2.0f) / 100.0f;
/* 213 */                   drawCircle(canvas, cx, cy, this.mColor4, pct5);
                        }
                        if (rawProgress >= 75.0f && rawProgress <= 100.0f) {
/* 216 */                   float pct6 = ((rawProgress - 75.0f) * 2.0f) / 100.0f;
/* 217 */                   drawCircle(canvas, cx, cy, this.mColor1, pct6);
                        }
                        if (this.mTriggerPercentage > 0.0f && drawTriggerWhileFinishing) {
/* 224 */                   canvas.restoreToCount(restoreCount);
/* 225 */                   restoreCount = canvas.save();
/* 226 */                   canvas.clipRect(this.mBounds);
/* 227 */                   drawTrigger(canvas, cx, cy);
                        }
/* 230 */               ViewCompat.postInvalidateOnAnimation(this.mParent);
                    } else if (this.mTriggerPercentage > 0.0f && this.mTriggerPercentage <= 1.0d) {
/* 234 */               drawTrigger(canvas, cx, cy);
                    }
/* 237 */           canvas.restoreToCount(restoreCount);
                }

/* 240 */       private void drawTrigger(Canvas canvas, int cx, int cy) {
/* 241 */           this.mPaint.setColor(this.mColor1);
/* 242 */           canvas.drawCircle(cx, cy, cx * this.mTriggerPercentage, this.mPaint);
                }

/* 254 */       private void drawCircle(Canvas canvas, float cx, float cy, int color, float pct) {
/* 255 */           this.mPaint.setColor(color);
/* 256 */           canvas.save();
/* 257 */           canvas.translate(cx, cy);
/* 258 */           float radiusScale = INTERPOLATOR.getInterpolation(pct);
/* 259 */           canvas.scale(radiusScale, radiusScale);
/* 260 */           canvas.drawCircle(0.0f, 0.0f, cx, this.mPaint);
/* 261 */           canvas.restore();
                }

/* 267 */       void setBounds(int left, int top, int right, int bottom) {
/* 268 */           this.mBounds.left = left;
/* 269 */           this.mBounds.top = top;
/* 270 */           this.mBounds.right = right;
/* 271 */           this.mBounds.bottom = bottom;
                }
            }
