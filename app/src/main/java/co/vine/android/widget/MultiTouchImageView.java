            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Matrix;
            import android.graphics.RectF;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.util.FloatMath;
            import android.view.MotionEvent;
            import android.widget.ImageView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public class MultiTouchImageView extends ImageView {
                private static final int GESTURE_NONE = 0;
                private static final int GESTURE_PAN = 1;
                private static final int GESTURE_ZOOM = 2;
                private static final float MIN_SCALE_FACTOR = 1.0f;
                protected float mBitmapHeight;
                protected float mBitmapWidth;
                private float mCurrentMinScale;
                private int mGesture;
                private float mLastDownX;
                private float mLastDownY;
                private float mLastZoomDistance;
                protected final Matrix mMatrix;
                private final RectF mMatrixBounds;
                private final float[] mMatrixValues;
                private float mMidTouchX;
                private float mMidTouchY;
                private final RectF mStartBounds;
                private float mTotalScaleFactor;
                protected RectF mTransformBounds;
                private float mViewHeight;
                private float mViewWidth;

/* 62 */        public MultiTouchImageView(Context context) {
/* 63 */            super(context);
/* 39 */            this.mMatrix = new Matrix();
/* 53 */            this.mLastZoomDistance = 1.0f;
/* 56 */            this.mTotalScaleFactor = 1.0f;
/* 57 */            this.mCurrentMinScale = 1.0f;
/* 58 */            this.mStartBounds = new RectF();
/* 59 */            this.mMatrixBounds = new RectF();
/* 60 */            this.mMatrixValues = new float[9];
/* 64 */            initMultiTouchImageView();
                }

/* 67 */        public MultiTouchImageView(Context context, AttributeSet attrs) {
/* 68 */            super(context, attrs);
/* 39 */            this.mMatrix = new Matrix();
/* 53 */            this.mLastZoomDistance = 1.0f;
/* 56 */            this.mTotalScaleFactor = 1.0f;
/* 57 */            this.mCurrentMinScale = 1.0f;
/* 58 */            this.mStartBounds = new RectF();
/* 59 */            this.mMatrixBounds = new RectF();
/* 60 */            this.mMatrixValues = new float[9];
/* 69 */            initMultiTouchImageView();
                }

/* 72 */        public MultiTouchImageView(Context context, AttributeSet attrs, int defStyle) {
/* 73 */            super(context, attrs, defStyle);
/* 39 */            this.mMatrix = new Matrix();
/* 53 */            this.mLastZoomDistance = 1.0f;
/* 56 */            this.mTotalScaleFactor = 1.0f;
/* 57 */            this.mCurrentMinScale = 1.0f;
/* 58 */            this.mStartBounds = new RectF();
/* 59 */            this.mMatrixBounds = new RectF();
/* 60 */            this.mMatrixValues = new float[9];
/* 74 */            initMultiTouchImageView();
                }

/* 77 */        void initMultiTouchImageView() {
/* 78 */            this.mGesture = 0;
/* 79 */            setScaleType(ImageView.ScaleType.MATRIX);
                }

                @Override // android.view.View
/* 83 */        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
/* 84 */            super.onLayout(changed, left, top, right, bottom);
/* 85 */            this.mViewWidth = getMeasuredWidth();
/* 86 */            this.mViewHeight = getMeasuredHeight();
/* 87 */            if (changed) {
/* 88 */                setTransformDimensions(this.mViewWidth, this.mViewHeight, ImageView.ScaleType.FIT_CENTER, true);
                    }
                }

                @Override // android.widget.ImageView
/* 93 */        public void setImageBitmap(Bitmap bitmap) {
/* 94 */            super.setImageBitmap(bitmap);
/* 95 */            if (bitmap != null) {
/* 96 */                this.mBitmapWidth = bitmap.getWidth();
/* 97 */                this.mBitmapHeight = bitmap.getHeight();
/* 98 */                setTransformDimensions(this.mViewWidth, this.mViewHeight, ImageView.ScaleType.FIT_CENTER, true);
                    }
                }

                @Override // android.view.View
/* 102 */       public boolean onTouchEvent(@NonNull MotionEvent event) {
                    switch (event.getAction() & 255) {
                        case 0:
                            if (this.mTotalScaleFactor > 1.0f) {
/* 106 */                       getParent().requestDisallowInterceptTouchEvent(true);
                            } else {
/* 108 */                       getParent().requestDisallowInterceptTouchEvent(false);
                            }
/* 110 */                   if (this.mGesture == 0) {
/* 111 */                       this.mLastDownX = event.getX();
/* 112 */                       this.mLastDownY = event.getY();
/* 113 */                       this.mGesture = 1;
                            }
/* 222 */                   return true;
                        case 1:
/* 199 */                   if (this.mGesture == 1) {
/* 200 */                       resetGesture();
                            }
/* 222 */                   return true;
                        case 2:
/* 127 */                   if (this.mGesture == 1) {
/* 128 */                       float evX = event.getX();
/* 129 */                       float evY = event.getY();
/* 130 */                       float deltaX = evX - this.mLastDownX;
/* 131 */                       float deltaY = evY - this.mLastDownY;
/* 133 */                       float leftBound = this.mMatrixBounds.left;
/* 134 */                       float rightBound = this.mMatrixBounds.right;
/* 135 */                       float topBound = this.mMatrixBounds.top;
/* 136 */                       float bottomBound = this.mMatrixBounds.bottom;
/* 138 */                       RectF panBounds = this.mTransformBounds;
/* 139 */                       float panLeft = panBounds.left;
/* 140 */                       float panTop = panBounds.top;
/* 141 */                       float panRight = panBounds.right;
/* 142 */                       float panBottom = panBounds.bottom;
/* 143 */                       boolean directionDown = this.mLastDownY > evY;
/* 144 */                       boolean directionRight = this.mLastDownX > evX;
/* 145 */                       if (directionRight) {
                                    if (rightBound > panRight && rightBound + deltaX < panRight) {
/* 147 */                               deltaX = panRight - rightBound;
                                    } else if (rightBound <= panRight) {
/* 149 */                               deltaX = 0.0f;
/* 150 */                               getParent().requestDisallowInterceptTouchEvent(false);
                                    }
                                } else if (leftBound < panLeft && leftBound + deltaX > panLeft) {
/* 154 */                           deltaX = panLeft - leftBound;
                                } else if (leftBound >= panLeft) {
/* 156 */                           deltaX = 0.0f;
/* 157 */                           getParent().requestDisallowInterceptTouchEvent(false);
                                }
/* 160 */                       if (directionDown) {
                                    if (bottomBound > panBottom && bottomBound + deltaY < panBottom) {
/* 162 */                               deltaY = panBottom - bottomBound;
                                    } else if (bottomBound <= panBottom) {
/* 164 */                               deltaY = 0.0f;
                                    }
                                } else if (topBound < panTop && topBound + deltaY > panTop) {
/* 168 */                           deltaY = panTop - topBound;
                                } else if (topBound >= panTop) {
/* 170 */                           deltaY = 0.0f;
                                }
                                if (deltaX != 0.0f || deltaY != 0.0f) {
/* 174 */                           this.mMatrix.postTranslate(deltaX, deltaY);
/* 175 */                           this.mMatrixBounds.offset(deltaX, deltaY);
/* 176 */                           setImageMatrix(this.mMatrix);
                                }
/* 178 */                       this.mLastDownX = evX;
/* 179 */                       this.mLastDownY = evY;
                            } else if (this.mGesture == 2) {
/* 181 */                       float touchDistance = touchDistance(event);
/* 182 */                       float scaleRatio = touchDistance / this.mLastZoomDistance;
/* 183 */                       this.mTotalScaleFactor *= scaleRatio;
/* 184 */                       RectF bounds = updateBounds();
                                if (bounds.left < 0.0f && bounds.top < 0.0f && bounds.right > this.mViewWidth && bounds.bottom > this.mViewHeight) {
/* 187 */                           this.mMatrix.postScale(scaleRatio, scaleRatio, this.mMidTouchX, this.mMidTouchY);
                                } else {
/* 189 */                           this.mMatrix.postScale(scaleRatio, scaleRatio, this.mViewWidth / 2.0f, this.mViewHeight / 2.0f);
                                }
/* 191 */                       this.mLastZoomDistance = touchDistance;
/* 192 */                       setImageMatrix(this.mMatrix);
                            } else {
/* 194 */                       return super.onTouchEvent(event);
                            }
/* 222 */                   return true;
                        case 3:
/* 205 */                   if (this.mGesture == 2) {
/* 206 */                       resetScale();
                            }
/* 208 */                   resetGesture();
/* 222 */                   return true;
                        case 4:
                        default:
/* 219 */                   return super.onTouchEvent(event);
                        case 5:
/* 118 */                   if (this.mGesture == 0 || this.mGesture == 1) {
/* 119 */                       this.mMidTouchX = (event.getX(0) + event.getX(1)) / 2.0f;
/* 120 */                       this.mMidTouchY = (event.getY(0) + event.getY(1)) / 2.0f;
/* 121 */                       this.mLastZoomDistance = touchDistance(event);
/* 122 */                       this.mGesture = 2;
                            }
/* 222 */                   return true;
                        case 6:
/* 212 */                   if (this.mGesture == 2) {
/* 213 */                       resetScale();
/* 214 */                       resetGesture();
                            }
/* 222 */                   return true;
                    }
                }

/* 226 */       private void resetScale() {
/* 227 */           RectF bounds = updateBounds();
/* 228 */           RectF transformBounds = this.mTransformBounds;
                    if (bounds.left > transformBounds.left || bounds.top > transformBounds.top || bounds.right < transformBounds.right || bounds.bottom < transformBounds.bottom) {
/* 232 */               float centerX = transformBounds.left + (transformBounds.width() / 2.0f);
/* 233 */               float centerY = transformBounds.top + (transformBounds.height() / 2.0f);
/* 234 */               float imageCenterX = bounds.left + (bounds.width() / 2.0f);
/* 235 */               float imageCenterY = bounds.top + (bounds.height() / 2.0f);
/* 236 */               float deltaX = centerX - imageCenterX;
/* 237 */               float deltaY = centerY - imageCenterY;
                        if (deltaX != 0.0f || deltaY != 0.0f) {
/* 244 */                   this.mMatrix.postTranslate(deltaX, deltaY);
/* 245 */                   this.mMatrixBounds.offset(deltaX, deltaY);
                        }
/* 248 */               float currentMinScale = this.mCurrentMinScale;
                        if (this.mTotalScaleFactor < currentMinScale) {
/* 250 */                   float scaleRatio = currentMinScale / this.mTotalScaleFactor;
/* 251 */                   this.mTotalScaleFactor = currentMinScale;
/* 252 */                   this.mMatrix.postScale(scaleRatio, scaleRatio, this.mViewWidth / 2.0f, this.mViewHeight / 2.0f);
/* 255 */                   float bitmapWidth = this.mBitmapWidth;
/* 256 */                   float bitmapHeight = this.mBitmapHeight;
/* 257 */                   float tbWidth = this.mTransformBounds.width();
/* 258 */                   float tbHeight = this.mTransformBounds.height();
/* 259 */                   float viewScale = calculateScale(bitmapWidth, tbWidth, bitmapHeight, tbHeight, ImageView.ScaleType.CENTER_CROP);
/* 262 */                   RectF newBounds = new RectF(this.mTransformBounds);
/* 263 */                   if (((int) (bitmapWidth * viewScale)) > ((int) tbWidth)) {
/* 264 */                       float offset = ((bitmapWidth * viewScale) - tbWidth) / 2.0f;
/* 265 */                       newBounds.left -= offset;
/* 266 */                       newBounds.right += offset;
                            }
/* 268 */                   if (((int) (bitmapHeight * viewScale)) > ((int) tbHeight)) {
/* 269 */                       float offset2 = ((bitmapHeight * viewScale) - tbHeight) / 2.0f;
/* 270 */                       newBounds.top -= offset2;
/* 271 */                       newBounds.bottom += offset2;
                            }
/* 273 */                   this.mMatrixBounds.set(newBounds);
                        }
/* 275 */               setImageMatrix(this.mMatrix);
                    }
                }

/* 279 */       private RectF updateBounds() {
/* 280 */           float[] values = this.mMatrixValues;
/* 281 */           this.mMatrix.getValues(values);
/* 282 */           float tX = values[2];
/* 283 */           float tY = values[5];
/* 284 */           float scaledWidth = this.mStartBounds.width() * this.mTotalScaleFactor;
/* 285 */           float scaledHeight = this.mStartBounds.height() * this.mTotalScaleFactor;
/* 286 */           float right = tX + scaledWidth;
/* 287 */           float bottom = tY + scaledHeight;
/* 288 */           this.mMatrixBounds.set(tX, tY, right, bottom);
/* 289 */           return this.mMatrixBounds;
                }

/* 292 */       private void resetGesture() {
/* 293 */           this.mGesture = 0;
/* 294 */           this.mLastDownX = Float.MIN_VALUE;
/* 295 */           this.mLastDownY = Float.MIN_VALUE;
/* 296 */           this.mMidTouchX = Float.MIN_VALUE;
/* 297 */           this.mMidTouchY = Float.MIN_VALUE;
                }

/* 300 */       private float touchDistance(MotionEvent event) {
/* 301 */           float x = event.getX(0) - event.getX(1);
/* 302 */           float y = event.getY(0) - event.getY(1);
/* 303 */           return (float) Math.sqrt((x * x) + (y * y));
                }

/* 309 */       private float calculateScale(float fromWidth, float toWidth, float fromHeight, float toHeight, ImageView.ScaleType scaleType) {
/* 310 */           if (scaleType != ImageView.ScaleType.CENTER_CROP) {
/* 317 */               float scale = Math.min(toWidth / fromWidth, toHeight / fromHeight);
/* 320 */               return scale;
                    }
                    if (fromWidth > fromHeight) {
/* 312 */               float scale2 = toHeight / fromHeight;
/* 320 */               return scale2;
                    }
/* 314 */           float scale3 = toWidth / fromWidth;
/* 320 */           return scale3;
                }

/* 333 */       public void setTransformDimensions(float width, float height, ImageView.ScaleType scaleType, boolean resetImage) {
/* 334 */           float viewWidth = this.mViewWidth;
/* 335 */           float deltaX = (viewWidth - width) / 2.0f;
/* 336 */           float viewHeight = this.mViewHeight;
/* 337 */           float deltaY = (viewHeight - height) / 2.0f;
/* 338 */           float startWidth = this.mStartBounds.width();
/* 339 */           float startHeight = this.mStartBounds.height();
/* 340 */           if (this.mTransformBounds == null) {
/* 341 */               this.mTransformBounds = new RectF(deltaX, deltaY, deltaX + width, deltaY + height);
                    } else {
/* 343 */               this.mTransformBounds.set(deltaX, deltaY, deltaX + width, deltaY + height);
                    }
                    if (startWidth != 0.0f && startHeight != 0.0f) {
/* 346 */               this.mCurrentMinScale = calculateScale(startWidth, width, startHeight, height, scaleType);
                    }
/* 348 */           if (resetImage || !this.mMatrixBounds.contains(this.mTransformBounds)) {
/* 352 */               float marginX = 0.0f;
/* 353 */               float marginY = 0.0f;
/* 354 */               float bitmapWidth = this.mBitmapWidth;
/* 355 */               float bitmapHeight = this.mBitmapHeight;
                        if (bitmapWidth > 0.0f && bitmapHeight > 0.0f) {
/* 357 */                   float initialScale = calculateScale(bitmapWidth, width, bitmapHeight, height, scaleType);
/* 360 */                   marginX = (viewWidth - (initialScale * bitmapWidth)) / 2.0f;
/* 361 */                   marginY = (viewHeight - (initialScale * bitmapHeight)) / 2.0f;
/* 362 */                   Matrix matrix = this.mMatrix;
/* 363 */                   matrix.setScale(initialScale, initialScale);
/* 364 */                   matrix.postTranslate(marginX, marginY);
/* 365 */                   setImageMatrix(matrix);
                        }
/* 367 */               this.mStartBounds.set(marginX, marginY, viewWidth - marginX, viewHeight - marginY);
/* 368 */               this.mMatrixBounds.set(this.mStartBounds);
/* 369 */               this.mTotalScaleFactor = 1.0f;
                    }
                }
            }
