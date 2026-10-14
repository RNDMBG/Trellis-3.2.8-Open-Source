            package co.vine.android.views;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.graphics.RectF;
            import android.graphics.drawable.NinePatchDrawable;
            import android.os.Bundle;
            import android.os.Parcelable;
            import android.support.annotation.NonNull;
            import android.support.v4.view.MotionEventCompat;
            import android.view.GestureDetector;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class RangeSeekBar extends View {
                private static final int CENTER_PRESSED = 2;
                public static final int INVALID_POINTER_ID = 255;
                private static final int LEFT_PRESSED = 0;
                private static final int NONE_PRESSED = -1;
                private static final int RIGHT_PRESSED = 1;
                private final double mAbsoluteMaxValuePrim;
                private final double mAbsoluteMinValuePrim;
                private int mActivePointerId;
                private final int mBackgroundColor;
                private final float mDoublePadding;
                private float mDownMotionX;
                private final GestureDetector mGestureDetector;
                private boolean mIsDragging;
                private float mLastX;
                private OnRangeSeekBarChangeListener mListener;
                private double mNormalizedMaxPercentage;
                private double mNormalizedMaxValue;
                private double mNormalizedMinValue;
                private final float mPadding;
                private final Paint mPaint;
                private int mPressedThumb;
                private final int mPrimaryColor;
                private final RectF mRectF;
                private int mScaledTouchSlop;
                private final Rect mScrubRect;
                private final float mThumbDragWidth;
                private final float mThumbHalfWidth;
                private final NinePatchDrawable mThumbImageLeft;
                private final NinePatchDrawable mThumbImageRight;
                private final float mTopBottomPadding;

                public interface OnRangeSeekBarChangeListener {
                    void onRangeSeekBarLongPressHappened();

                    void onRangeSeekBarTouchDown(RangeSeekBar rangeSeekBar, int i, int i2);

                    void onRangeSeekBarTouchUp(RangeSeekBar rangeSeekBar, int i, int i2);

                    void onRangeSeekBarValuesChanged(RangeSeekBar rangeSeekBar, int i, int i2, boolean z);
                }

/* 25 */        static /* synthetic */ OnRangeSeekBarChangeListener access$000(RangeSeekBar x0) {
/* 26 */            return x0.mListener;
                }

                /* JADX INFO: renamed from: co.vine.android.views.RangeSeekBar$1 */
/* 48 */        class C05451 extends GestureDetector.SimpleOnGestureListener {
/* 49 */            C05451() {
                    }

                    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
/* 51 */            public void onLongPress(MotionEvent e) {
/* 52 */                if (RangeSeekBar.access$000(RangeSeekBar.this) != null) {
/* 53 */                    RangeSeekBar.access$000(RangeSeekBar.this).onRangeSeekBarLongPressHappened();
                        }
                    }
                }

/* 79 */        public RangeSeekBar(NinePatchDrawable left, NinePatchDrawable right, Integer absoluteMinValue, Integer absoluteMaxValue, Context context, int primaryColor, int backgroundColor) throws IllegalArgumentException {
/* 80 */            super(context);
/* 31 */            this.mPaint = new Paint(1);
/* 40 */            this.mRectF = new RectF();
/* 41 */            this.mScrubRect = new Rect();
/* 44 */            this.mNormalizedMinValue = 0.0d;
/* 45 */            this.mNormalizedMaxValue = 1.0d;
/* 46 */            this.mPressedThumb = -1;
/* 48 */            this.mNormalizedMaxPercentage = 1.0d;
/* 50 */            this.mGestureDetector = new GestureDetector(new C05451());
/* 65 */            this.mActivePointerId = 255;
/* 69 */            this.mLastX = 0.0f;
/* 81 */            this.mPaint.setAntiAlias(true);
/* 82 */            this.mPaint.setStyle(Paint.Style.FILL);
/* 83 */            int thumbWidth = left.getIntrinsicWidth();
/* 84 */            this.mPrimaryColor = primaryColor;
/* 85 */            this.mBackgroundColor = backgroundColor;
/* 86 */            this.mThumbImageLeft = left;
/* 87 */            this.mThumbImageRight = right;
/* 88 */            this.mThumbDragWidth = thumbWidth * 2;
/* 89 */            this.mThumbHalfWidth = 0.5f * thumbWidth;
/* 90 */            this.mPadding = this.mThumbHalfWidth;
/* 91 */            this.mDoublePadding = this.mPadding * 2.0f;
/* 92 */            this.mTopBottomPadding = this.mPadding * 0.2f;
/* 93 */            this.mAbsoluteMinValuePrim = absoluteMinValue.doubleValue();
/* 94 */            this.mAbsoluteMaxValuePrim = absoluteMaxValue.doubleValue();
/* 95 */            setFocusable(true);
/* 96 */            setFocusableInTouchMode(true);
/* 97 */            this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                }

/* 105 */       public int getSelectedMinValue() {
/* 106 */           return normalizedToValue(this.mNormalizedMinValue);
                }

/* 115 */       public void setSelectedMinValue(int value) {
                    if (0.0d == this.mAbsoluteMaxValuePrim - this.mAbsoluteMinValuePrim) {
/* 117 */               setNormalizedMinValue(0.0d);
                    } else {
/* 119 */               setNormalizedMinValue(valueToNormalized(Integer.valueOf(value)));
                    }
                }

/* 128 */       public int getSelectedMaxValue() {
/* 129 */           return normalizedToValue(this.mNormalizedMaxValue);
                }

/* 138 */       public void setSelectedMaxValue(int value) {
                    if (0.0d == this.mAbsoluteMaxValuePrim - this.mAbsoluteMinValuePrim) {
/* 140 */               setNormalizedMaxValue(1.0d);
                    } else {
/* 142 */               setNormalizedMaxValue(valueToNormalized(Integer.valueOf(value)));
                    }
                }

/* 151 */       public void setOnRangeSeekBarChangeListener(OnRangeSeekBarChangeListener listener) {
/* 152 */           this.mListener = listener;
                }

/* 155 */       public boolean seekToLeft() {
                    return this.mPressedThumb == 0 || this.mPressedThumb == 2;
                }

/* 159 */       public void notifyListenerOfValueChange() {
/* 160 */           if (this.mListener != null) {
/* 161 */               this.mListener.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue(), false);
                    }
                }

                @Override // android.view.View
/* 169 */       public boolean onTouchEvent(@NonNull MotionEvent event) {
/* 170 */           if (!isEnabled()) {
                        return false;
                    }
/* 174 */           if (!this.mGestureDetector.onTouchEvent(event)) {
/* 177 */               int action = event.getAction();
                        switch (action & 255) {
                            case 0:
/* 182 */                       this.mActivePointerId = event.getPointerId(event.getPointerCount() - 1);
/* 183 */                       int pointerIndex = event.findPointerIndex(this.mActivePointerId);
/* 184 */                       this.mDownMotionX = event.getX(pointerIndex);
/* 185 */                       this.mLastX = this.mDownMotionX;
/* 187 */                       this.mPressedThumb = evalPressedThumb(this.mDownMotionX);
/* 189 */                       if (this.mPressedThumb == -1) {
                                    return true;
                                }
/* 194 */                       setPressed(true);
/* 195 */                       invalidate();
/* 196 */                       onStartTrackingTouch();
/* 197 */                       trackTouchEvent(event);
/* 198 */                       attemptClaimDrag();
/* 200 */                       if (this.mListener != null) {
/* 201 */                           this.mListener.onRangeSeekBarTouchDown(this, getSelectedMinValue(), getSelectedMaxValue());
                                }
                                break;
                            case 1:
/* 229 */                       if (this.mIsDragging) {
/* 230 */                           trackTouchEvent(event);
/* 231 */                           onStopTrackingTouch();
/* 232 */                           setPressed(false);
                                } else {
/* 236 */                           onStartTrackingTouch();
/* 237 */                           trackTouchEvent(event);
/* 238 */                           onStopTrackingTouch();
                                }
/* 241 */                       this.mPressedThumb = -1;
/* 242 */                       invalidate();
/* 243 */                       if (this.mListener != null) {
/* 244 */                           this.mListener.onRangeSeekBarTouchUp(this, getSelectedMinValue(), getSelectedMaxValue());
                                }
                                break;
                            case 2:
/* 206 */                       if (this.mPressedThumb != -1) {
/* 208 */                           if (this.mIsDragging) {
/* 209 */                               trackTouchEvent(event);
                                    } else {
/* 212 */                               int pointerIndex2 = event.findPointerIndex(this.mActivePointerId);
/* 213 */                               float x = event.getX(pointerIndex2);
                                        if (Math.abs(x - this.mDownMotionX) > this.mScaledTouchSlop) {
/* 216 */                                   setPressed(true);
/* 217 */                                   invalidate();
/* 218 */                                   onStartTrackingTouch();
/* 219 */                                   trackTouchEvent(event);
/* 220 */                                   attemptClaimDrag();
                                        }
                                    }
/* 223 */                           if (this.mListener != null) {
/* 224 */                               this.mListener.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue(), true);
                                    }
                                }
                                break;
                            case 3:
/* 260 */                       if (this.mIsDragging) {
/* 261 */                           onStopTrackingTouch();
/* 262 */                           setPressed(false);
                                }
/* 264 */                       invalidate();
                                break;
                            case 5:
/* 248 */                       int index = event.getPointerCount() - 1;
/* 250 */                       this.mDownMotionX = event.getX(index);
/* 251 */                       this.mActivePointerId = event.getPointerId(index);
/* 252 */                       invalidate();
                                break;
                            case 6:
/* 256 */                       onSecondaryPointerUp(event);
/* 257 */                       invalidate();
                                break;
                        }
                    }
                    return true;
                }

/* 271 */       private void onSecondaryPointerUp(MotionEvent ev) {
/* 272 */           int pointerIndex = (ev.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
/* 274 */           int pointerId = ev.getPointerId(pointerIndex);
/* 275 */           if (pointerId == this.mActivePointerId) {
/* 279 */               int newPointerIndex = pointerIndex == 0 ? 1 : 0;
/* 280 */               this.mDownMotionX = ev.getX(newPointerIndex);
/* 281 */               this.mActivePointerId = ev.getPointerId(newPointerIndex);
                    }
                }

/* 285 */       private void trackTouchEvent(MotionEvent event) {
/* 286 */           int pointerIndex = event.findPointerIndex(this.mActivePointerId);
/* 287 */           float x = event.getX(pointerIndex);
/* 289 */           if (this.mPressedThumb == 0) {
/* 290 */               setNormalizedMinValue(screenToNormalized(x));
                    } else if (1 == this.mPressedThumb) {
/* 292 */               setNormalizedMaxValue(screenToNormalized(x));
                    } else if (2 == this.mPressedThumb) {
/* 294 */               double delta = screenToNormalized(x) - screenToNormalized(this.mLastX);
                        if (this.mNormalizedMaxValue + delta <= 1.0d && this.mNormalizedMinValue + delta >= 0.0d) {
/* 296 */                   setNormalizedMaxValue(this.mNormalizedMaxValue + delta);
/* 297 */                   setNormalizedMinValue(this.mNormalizedMinValue + delta);
                        }
                    }
/* 300 */           this.mLastX = x;
                }

/* 303 */       private void attemptClaimDrag() {
/* 304 */           if (getParent() != null) {
/* 305 */               getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

/* 312 */       void onStartTrackingTouch() {
/* 313 */           this.mIsDragging = true;
                }

/* 319 */       void onStopTrackingTouch() {
/* 320 */           this.mIsDragging = false;
                }

                @Override // android.view.View
/* 327 */       protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    try {
/* 330 */               int width = View.MeasureSpec.getMode(widthMeasureSpec) != 0 ? View.MeasureSpec.getSize(widthMeasureSpec) : 200;
/* 332 */               int height = this.mThumbImageLeft.getIntrinsicHeight();
/* 333 */               if (View.MeasureSpec.getMode(heightMeasureSpec) != 0) {
/* 334 */                   height = View.MeasureSpec.getSize(heightMeasureSpec);
                        }
/* 336 */               setMeasuredDimension(width, height);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.view.View
/* 343 */       protected synchronized void onDraw(@NonNull Canvas canvas) {
                    try {
                        super.onDraw(canvas);
/* 345 */               this.mPaint.setColor(this.mBackgroundColor);
/* 347 */               this.mRectF.set(0.0f, this.mTopBottomPadding, normalizedToScreen(this.mNormalizedMinValue), getHeight() - this.mTopBottomPadding);
/* 348 */               canvas.drawRect(this.mRectF, this.mPaint);
/* 350 */               this.mRectF.set(normalizedToScreen(this.mNormalizedMaxValue), this.mTopBottomPadding, getWidth(), getHeight() - this.mTopBottomPadding);
/* 351 */               canvas.drawRect(this.mRectF, this.mPaint);
/* 353 */               this.mRectF.left = normalizedToScreen(this.mNormalizedMinValue);
/* 354 */               this.mRectF.right = normalizedToScreen(this.mNormalizedMaxValue);
/* 355 */               this.mPaint.setColor(this.mPrimaryColor);
/* 356 */               canvas.drawRect(this.mRectF, this.mPaint);
/* 357 */               this.mPaint.setAlpha(255);
/* 358 */               float left = normalizedToScreen(this.mNormalizedMinValue) - this.mThumbHalfWidth;
/* 359 */               float right = normalizedToScreen(this.mNormalizedMaxValue) - this.mThumbHalfWidth;
/* 361 */               this.mScrubRect.set((int) left, 0, (int) ((2.0f * this.mThumbHalfWidth) + left), getHeight());
/* 362 */               this.mThumbImageLeft.setBounds(this.mScrubRect);
/* 363 */               this.mThumbImageLeft.draw(canvas);
/* 365 */               this.mScrubRect.offset((int) (right - left), 0);
/* 366 */               this.mThumbImageRight.setBounds(this.mScrubRect);
/* 367 */               this.mThumbImageRight.draw(canvas);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.view.View
/* 374 */       protected Parcelable onSaveInstanceState() {
/* 375 */           Bundle bundle = new Bundle();
/* 376 */           bundle.putParcelable("SUPER", super.onSaveInstanceState());
/* 377 */           bundle.putDouble("MIN", this.mNormalizedMinValue);
/* 378 */           bundle.putDouble("MAX", this.mNormalizedMaxValue);
/* 379 */           return bundle;
                }

                @Override // android.view.View
/* 386 */       protected void onRestoreInstanceState(Parcelable parcel) {
/* 387 */           Bundle bundle = (Bundle) parcel;
/* 388 */           super.onRestoreInstanceState(bundle.getParcelable("SUPER"));
/* 389 */           this.mNormalizedMinValue = bundle.getDouble("MIN");
/* 390 */           this.mNormalizedMaxValue = bundle.getDouble("MAX");
                }

/* 399 */       private int evalPressedThumb(float touchX) {
/* 401 */           boolean minThumbPressed = isInThumbRange(touchX, this.mNormalizedMinValue);
/* 402 */           boolean maxThumbPressed = isInThumbRange(touchX, this.mNormalizedMaxValue);
/* 403 */           boolean isInCenter = isInCenter(touchX, this.mNormalizedMinValue, this.mNormalizedMaxValue);
/* 404 */           if (minThumbPressed && maxThumbPressed) {
                        return touchX / ((float) getWidth()) > 0.5f ? 0 : 1;
                    }
/* 407 */           if (minThumbPressed) {
/* 408 */               return 0;
                    }
/* 409 */           if (maxThumbPressed) {
/* 410 */               return 1;
                    }
/* 411 */           if (!isInCenter) {
/* 400 */               return -1;
                    }
/* 412 */           return 2;
                }

/* 417 */       private boolean isInCenter(float touchX, double normalizedMinValue, double normalizedMaxValue) {
                    return normalizedToScreen(normalizedMinValue) < touchX && normalizedToScreen(normalizedMaxValue) > touchX;
                }

/* 421 */       private boolean isInThumbRange(float touchX, double normalizedThumbValue) {
                    return Math.abs(touchX - normalizedToScreen(normalizedThumbValue)) <= this.mThumbDragWidth;
                }

/* 430 */       public void setNormalizedMinValue(double value) {
/* 431 */           this.mNormalizedMinValue = Math.max(0.0d, Math.min(1.0d, Math.min(value, this.mNormalizedMaxValue)));
/* 433 */           double diff = (this.mNormalizedMaxValue - this.mNormalizedMinValue) - this.mNormalizedMaxPercentage;
                    if (diff > 0.0d) {
/* 436 */               this.mNormalizedMinValue += diff;
                    }
/* 439 */           invalidate();
                }

/* 442 */       public void forceResetNormalizedValues(double min, double max) {
/* 443 */           this.mNormalizedMinValue = Math.max(0.0d, Math.min(1.0d, Math.min(min, this.mNormalizedMaxValue)));
/* 444 */           this.mNormalizedMaxValue = Math.max(0.0d, Math.min(1.0d, Math.max(max, this.mNormalizedMinValue)));
/* 445 */           invalidate();
                }

/* 453 */       public void setNormalizedMaxValue(double value) {
/* 454 */           this.mNormalizedMaxValue = Math.max(0.0d, Math.min(1.0d, Math.max(value, this.mNormalizedMinValue)));
/* 456 */           double diff = (this.mNormalizedMaxValue - this.mNormalizedMinValue) - this.mNormalizedMaxPercentage;
                    if (diff > 0.0d) {
/* 459 */               this.mNormalizedMaxValue -= diff;
                    }
/* 462 */           invalidate();
                }

/* 471 */       private int normalizedToValue(double normalized) {
/* 472 */           return (int) (this.mAbsoluteMinValuePrim + ((this.mAbsoluteMaxValuePrim - this.mAbsoluteMinValuePrim) * normalized));
                }

/* 475 */       private double valueToNormalized(Integer value) {
                    if (0.0d == this.mAbsoluteMaxValuePrim - this.mAbsoluteMinValuePrim) {
                        return 0.0d;
                    }
                    return (value.doubleValue() - this.mAbsoluteMinValuePrim) / (this.mAbsoluteMaxValuePrim - this.mAbsoluteMinValuePrim);
                }

/* 483 */       private float normalizedToScreen(double normalizedCoord) {
/* 484 */           return (float) (((double) this.mPadding) + (((double) (getWidth() - this.mDoublePadding)) * normalizedCoord));
                }

/* 487 */       private double screenToNormalized(float screenCoord) {
/* 488 */           int width = getWidth();
                    if (width <= this.mDoublePadding) {
                        return 0.0d;
                    }
/* 493 */           double result = (screenCoord - this.mPadding) / (width - this.mDoublePadding);
/* 494 */           return Math.min(1.0d, Math.max(0.0d, result));
                }
            }
