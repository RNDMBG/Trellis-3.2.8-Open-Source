            package android.support.v4.view;

            import android.content.Context;
            import android.os.Build;
            import android.os.Handler;
            import android.os.Message;
            import android.view.GestureDetector;
            import android.view.MotionEvent;
            import android.view.VelocityTracker;
            import android.view.ViewConfiguration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class GestureDetectorCompat {
                private final GestureDetectorCompatImpl mImpl;

                interface GestureDetectorCompatImpl {
                    boolean isLongpressEnabled();

                    boolean onTouchEvent(MotionEvent motionEvent);

                    void setIsLongpressEnabled(boolean z);

                    void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener);
                }

/* 54 */        static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
                    private static final int LONG_PRESS = 2;
                    private static final int SHOW_PRESS = 1;
                    private static final int TAP = 3;
                    private boolean mAlwaysInBiggerTapRegion;
                    private boolean mAlwaysInTapRegion;
                    private MotionEvent mCurrentDownEvent;
                    private boolean mDeferConfirmSingleTap;
                    private GestureDetector.OnDoubleTapListener mDoubleTapListener;
                    private int mDoubleTapSlopSquare;
                    private float mDownFocusX;
                    private float mDownFocusY;
                    private final Handler mHandler;
                    private boolean mInLongPress;
                    private boolean mIsDoubleTapping;
                    private boolean mIsLongpressEnabled;
                    private float mLastFocusX;
                    private float mLastFocusY;
                    private final GestureDetector.OnGestureListener mListener;
                    private int mMaximumFlingVelocity;
                    private int mMinimumFlingVelocity;
                    private MotionEvent mPreviousUpEvent;
                    private boolean mStillDown;
                    private int mTouchSlopSquare;
                    private VelocityTracker mVelocityTracker;
                    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
                    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
                    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();

/* 55 */            static /* synthetic */ MotionEvent access$000(GestureDetectorCompatImplBase x0) {
/* 56 */                return x0.mCurrentDownEvent;
                    }

/* 55 */            static /* synthetic */ GestureDetector.OnGestureListener access$100(GestureDetectorCompatImplBase x0) {
/* 56 */                return x0.mListener;
                    }

/* 55 */            static /* synthetic */ void access$200(GestureDetectorCompatImplBase x0) {
/* 56 */                x0.dispatchLongPress();
                    }

/* 55 */            static /* synthetic */ GestureDetector.OnDoubleTapListener access$300(GestureDetectorCompatImplBase x0) {
/* 56 */                return x0.mDoubleTapListener;
                    }

/* 55 */            static /* synthetic */ boolean access$400(GestureDetectorCompatImplBase x0) {
/* 56 */                return x0.mStillDown;
                    }

/* 55 */            static /* synthetic */ boolean access$502(GestureDetectorCompatImplBase x0, boolean x1) {
/* 56 */                x0.mDeferConfirmSingleTap = x1;
                        return x1;
                    }

/* 101 */           private class GestureHandler extends Handler {
/* 102 */               GestureHandler() {
                        }

/* 106 */               GestureHandler(Handler handler) {
/* 108 */                   super(handler.getLooper());
                        }

                        @Override // android.os.Handler
/* 112 */               public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 1:
/* 115 */                           GestureDetectorCompatImplBase.access$100(GestureDetectorCompatImplBase.this).onShowPress(GestureDetectorCompatImplBase.access$000(GestureDetectorCompatImplBase.this));
/* 136 */                           return;
                                case 2:
/* 119 */                           GestureDetectorCompatImplBase.access$200(GestureDetectorCompatImplBase.this);
/* 136 */                           return;
                                case 3:
/* 124 */                           if (GestureDetectorCompatImplBase.access$300(GestureDetectorCompatImplBase.this) != null) {
/* 125 */                               if (!GestureDetectorCompatImplBase.access$400(GestureDetectorCompatImplBase.this)) {
/* 126 */                                   GestureDetectorCompatImplBase.access$300(GestureDetectorCompatImplBase.this).onSingleTapConfirmed(GestureDetectorCompatImplBase.access$000(GestureDetectorCompatImplBase.this));
/* 136 */                                   return;
                                        } else {
/* 128 */                                   GestureDetectorCompatImplBase.access$502(GestureDetectorCompatImplBase.this, true);
/* 136 */                                   return;
                                        }
                                    }
/* 136 */                           return;
                                default:
/* 134 */                           throw new RuntimeException("Unknown message " + msg);
                            }
                        }
                    }

/* 151 */           public GestureDetectorCompatImplBase(Context context, GestureDetector.OnGestureListener listener, Handler handler) {
/* 153 */               if (handler != null) {
/* 154 */                   this.mHandler = new GestureHandler(handler);
                        } else {
/* 156 */                   this.mHandler = new GestureHandler();
                        }
/* 158 */               this.mListener = listener;
/* 159 */               if (listener instanceof GestureDetector.OnDoubleTapListener) {
/* 160 */                   setOnDoubleTapListener((GestureDetector.OnDoubleTapListener) listener);
                        }
/* 162 */               init(context);
                    }

/* 165 */           private void init(Context context) {
/* 166 */               if (context == null) {
/* 167 */                   throw new IllegalArgumentException("Context must not be null");
                        }
/* 169 */               if (this.mListener == null) {
/* 170 */                   throw new IllegalArgumentException("OnGestureListener must not be null");
                        }
/* 172 */               this.mIsLongpressEnabled = true;
/* 174 */               ViewConfiguration configuration = ViewConfiguration.get(context);
/* 175 */               int touchSlop = configuration.getScaledTouchSlop();
/* 176 */               int doubleTapSlop = configuration.getScaledDoubleTapSlop();
/* 177 */               this.mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
/* 178 */               this.mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
/* 180 */               this.mTouchSlopSquare = touchSlop * touchSlop;
/* 181 */               this.mDoubleTapSlopSquare = doubleTapSlop * doubleTapSlop;
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 191 */           public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
/* 192 */               this.mDoubleTapListener = onDoubleTapListener;
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 204 */           public void setIsLongpressEnabled(boolean isLongpressEnabled) {
/* 205 */               this.mIsLongpressEnabled = isLongpressEnabled;
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 211 */           public boolean isLongpressEnabled() {
/* 212 */               return this.mIsLongpressEnabled;
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 223 */           public boolean onTouchEvent(MotionEvent ev) {
/* 224 */               int action = ev.getAction();
/* 226 */               if (this.mVelocityTracker == null) {
/* 227 */                   this.mVelocityTracker = VelocityTracker.obtain();
                        }
/* 229 */               this.mVelocityTracker.addMovement(ev);
/* 231 */               boolean pointerUp = (action & 255) == 6;
/* 233 */               int skipIndex = pointerUp ? MotionEventCompat.getActionIndex(ev) : -1;
/* 236 */               float sumX = 0.0f;
                        float sumY = 0.0f;
/* 237 */               int count = MotionEventCompat.getPointerCount(ev);
                        for (int i = 0; i < count; i++) {
/* 239 */                   if (skipIndex != i) {
/* 240 */                       sumX += MotionEventCompat.getX(ev, i);
/* 241 */                       sumY += MotionEventCompat.getY(ev, i);
                            }
                        }
/* 243 */               int div = pointerUp ? count - 1 : count;
/* 244 */               float focusX = sumX / div;
/* 245 */               float focusY = sumY / div;
/* 247 */               boolean handled = false;
                        switch (action & 255) {
                            case 0:
/* 284 */                       if (this.mDoubleTapListener != null) {
/* 285 */                           boolean hadTapMessage = this.mHandler.hasMessages(3);
/* 286 */                           if (hadTapMessage) {
                                        this.mHandler.removeMessages(3);
                                    }
/* 287 */                           if (this.mCurrentDownEvent != null && this.mPreviousUpEvent != null && hadTapMessage && isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, ev)) {
/* 290 */                               this.mIsDoubleTapping = true;
/* 294 */                               handled = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(ev);
                                    } else {
/* 297 */                               this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
                                    }
                                }
/* 301 */                       this.mLastFocusX = focusX;
                                this.mDownFocusX = focusX;
/* 302 */                       this.mLastFocusY = focusY;
                                this.mDownFocusY = focusY;
/* 303 */                       if (this.mCurrentDownEvent != null) {
/* 304 */                           this.mCurrentDownEvent.recycle();
                                }
/* 306 */                       this.mCurrentDownEvent = MotionEvent.obtain(ev);
/* 307 */                       this.mAlwaysInTapRegion = true;
/* 308 */                       this.mAlwaysInBiggerTapRegion = true;
/* 309 */                       this.mStillDown = true;
/* 310 */                       this.mInLongPress = false;
/* 311 */                       this.mDeferConfirmSingleTap = false;
/* 313 */                       if (this.mIsLongpressEnabled) {
/* 314 */                           this.mHandler.removeMessages(2);
/* 315 */                           this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + ((long) TAP_TIMEOUT) + ((long) LONGPRESS_TIMEOUT));
                                }
/* 318 */                       this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + ((long) TAP_TIMEOUT));
/* 319 */                       return handled | this.mListener.onDown(ev);
                            case 1:
/* 355 */                       this.mStillDown = false;
/* 356 */                       MotionEvent currentUpEvent = MotionEvent.obtain(ev);
/* 357 */                       if (this.mIsDoubleTapping) {
/* 247 */                           handled = false | this.mDoubleTapListener.onDoubleTapEvent(ev);
                                } else if (this.mInLongPress) {
/* 361 */                           this.mHandler.removeMessages(3);
/* 362 */                           this.mInLongPress = false;
                                } else if (this.mAlwaysInTapRegion) {
/* 364 */                           handled = this.mListener.onSingleTapUp(ev);
/* 365 */                           if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
/* 366 */                               this.mDoubleTapListener.onSingleTapConfirmed(ev);
                                    }
                                } else {
/* 370 */                           VelocityTracker velocityTracker = this.mVelocityTracker;
/* 371 */                           int pointerId = MotionEventCompat.getPointerId(ev, 0);
/* 372 */                           velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
/* 373 */                           float velocityY = VelocityTrackerCompat.getYVelocity(velocityTracker, pointerId);
/* 375 */                           float velocityX = VelocityTrackerCompat.getXVelocity(velocityTracker, pointerId);
                                    if (Math.abs(velocityY) > this.mMinimumFlingVelocity || Math.abs(velocityX) > this.mMinimumFlingVelocity) {
/* 380 */                               handled = this.mListener.onFling(this.mCurrentDownEvent, ev, velocityX, velocityY);
                                    }
                                }
/* 383 */                       if (this.mPreviousUpEvent != null) {
/* 384 */                           this.mPreviousUpEvent.recycle();
                                }
/* 387 */                       this.mPreviousUpEvent = currentUpEvent;
/* 388 */                       if (this.mVelocityTracker != null) {
/* 391 */                           this.mVelocityTracker.recycle();
/* 392 */                           this.mVelocityTracker = null;
                                }
/* 394 */                       this.mIsDoubleTapping = false;
/* 395 */                       this.mDeferConfirmSingleTap = false;
/* 396 */                       this.mHandler.removeMessages(1);
/* 397 */                       this.mHandler.removeMessages(2);
/* 405 */                       return handled;
                            case 2:
/* 323 */                       if (this.mInLongPress) {
/* 247 */                           return false;
                                }
/* 326 */                       float scrollX = this.mLastFocusX - focusX;
/* 327 */                       float scrollY = this.mLastFocusY - focusY;
/* 328 */                       if (this.mIsDoubleTapping) {
/* 247 */                           return false | this.mDoubleTapListener.onDoubleTapEvent(ev);
                                }
/* 331 */                       if (!this.mAlwaysInTapRegion) {
                                    if (Math.abs(scrollX) < 1.0f && Math.abs(scrollY) < 1.0f) {
/* 247 */                               return false;
                                    }
/* 348 */                           boolean handled2 = this.mListener.onScroll(this.mCurrentDownEvent, ev, scrollX, scrollY);
/* 349 */                           this.mLastFocusX = focusX;
/* 350 */                           this.mLastFocusY = focusY;
/* 405 */                           return handled2;
                                }
/* 332 */                       int deltaX = (int) (focusX - this.mDownFocusX);
/* 333 */                       int deltaY = (int) (focusY - this.mDownFocusY);
/* 334 */                       int distance = (deltaX * deltaX) + (deltaY * deltaY);
/* 335 */                       if (distance > this.mTouchSlopSquare) {
/* 336 */                           handled = this.mListener.onScroll(this.mCurrentDownEvent, ev, scrollX, scrollY);
/* 337 */                           this.mLastFocusX = focusX;
/* 338 */                           this.mLastFocusY = focusY;
/* 339 */                           this.mAlwaysInTapRegion = false;
/* 340 */                           this.mHandler.removeMessages(3);
/* 341 */                           this.mHandler.removeMessages(1);
/* 342 */                           this.mHandler.removeMessages(2);
                                }
/* 344 */                       if (distance > this.mTouchSlopSquare) {
/* 345 */                           this.mAlwaysInBiggerTapRegion = false;
/* 405 */                           return handled;
                                }
/* 405 */                       return handled;
                            case 3:
/* 401 */                       cancel();
/* 247 */                       return false;
                            case 4:
                            default:
/* 247 */                       return false;
                            case 5:
/* 251 */                       this.mLastFocusX = focusX;
                                this.mDownFocusX = focusX;
/* 252 */                       this.mLastFocusY = focusY;
                                this.mDownFocusY = focusY;
/* 254 */                       cancelTaps();
/* 247 */                       return false;
                            case 6:
/* 258 */                       this.mLastFocusX = focusX;
                                this.mDownFocusX = focusX;
/* 259 */                       this.mLastFocusY = focusY;
                                this.mDownFocusY = focusY;
/* 263 */                       this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
/* 264 */                       int upIndex = MotionEventCompat.getActionIndex(ev);
/* 265 */                       int id1 = MotionEventCompat.getPointerId(ev, upIndex);
/* 266 */                       float x1 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, id1);
/* 267 */                       float y1 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, id1);
                                for (int i2 = 0; i2 < count; i2++) {
/* 269 */                           if (i2 != upIndex) {
/* 271 */                               int id2 = MotionEventCompat.getPointerId(ev, i2);
/* 272 */                               float x = x1 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, id2);
/* 273 */                               float y = y1 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, id2);
/* 275 */                               float dot = x + y;
                                        if (dot < 0.0f) {
/* 277 */                                   this.mVelocityTracker.clear();
/* 247 */                                   return false;
                                        }
                                    }
                                }
/* 247 */                       return false;
                        }
                    }

/* 408 */           private void cancel() {
/* 409 */               this.mHandler.removeMessages(1);
/* 410 */               this.mHandler.removeMessages(2);
/* 411 */               this.mHandler.removeMessages(3);
/* 412 */               this.mVelocityTracker.recycle();
/* 413 */               this.mVelocityTracker = null;
/* 414 */               this.mIsDoubleTapping = false;
/* 415 */               this.mStillDown = false;
/* 416 */               this.mAlwaysInTapRegion = false;
/* 417 */               this.mAlwaysInBiggerTapRegion = false;
/* 418 */               this.mDeferConfirmSingleTap = false;
/* 419 */               if (this.mInLongPress) {
/* 420 */                   this.mInLongPress = false;
                        }
                    }

/* 424 */           private void cancelTaps() {
/* 425 */               this.mHandler.removeMessages(1);
/* 426 */               this.mHandler.removeMessages(2);
/* 427 */               this.mHandler.removeMessages(3);
/* 428 */               this.mIsDoubleTapping = false;
/* 429 */               this.mAlwaysInTapRegion = false;
/* 430 */               this.mAlwaysInBiggerTapRegion = false;
/* 431 */               this.mDeferConfirmSingleTap = false;
/* 432 */               if (this.mInLongPress) {
/* 433 */                   this.mInLongPress = false;
                        }
                    }

/* 438 */           private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent firstUp, MotionEvent secondDown) {
/* 439 */               if (!this.mAlwaysInBiggerTapRegion || secondDown.getEventTime() - firstUp.getEventTime() > DOUBLE_TAP_TIMEOUT) {
                            return false;
                        }
/* 447 */               int deltaX = ((int) firstDown.getX()) - ((int) secondDown.getX());
/* 448 */               int deltaY = ((int) firstDown.getY()) - ((int) secondDown.getY());
                        return (deltaX * deltaX) + (deltaY * deltaY) < this.mDoubleTapSlopSquare;
                    }

/* 452 */           private void dispatchLongPress() {
/* 453 */               this.mHandler.removeMessages(3);
/* 454 */               this.mDeferConfirmSingleTap = false;
/* 455 */               this.mInLongPress = true;
/* 456 */               this.mListener.onLongPress(this.mCurrentDownEvent);
                    }
                }

/* 462 */       static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
                    private final GestureDetector mDetector;

/* 463 */           public GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener listener, Handler handler) {
/* 465 */               this.mDetector = new GestureDetector(context, listener, handler);
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 469 */           public boolean isLongpressEnabled() {
/* 470 */               return this.mDetector.isLongpressEnabled();
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 474 */           public boolean onTouchEvent(MotionEvent ev) {
/* 475 */               return this.mDetector.onTouchEvent(ev);
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 479 */           public void setIsLongpressEnabled(boolean enabled) {
/* 480 */               this.mDetector.setIsLongpressEnabled(enabled);
                    }

                    @Override // android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImpl
/* 484 */           public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener listener) {
/* 485 */               this.mDetector.setOnDoubleTapListener(listener);
                    }
                }

/* 500 */       public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener listener) {
/* 501 */           this(context, listener, null);
                }

/* 513 */       public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener listener, Handler handler) {
/* 515 */           if (Build.VERSION.SDK_INT > 17) {
/* 516 */               this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, listener, handler);
                    } else {
/* 518 */               this.mImpl = new GestureDetectorCompatImplBase(context, listener, handler);
                    }
                }

/* 525 */       public boolean isLongpressEnabled() {
/* 526 */           return this.mImpl.isLongpressEnabled();
                }

/* 537 */       public boolean onTouchEvent(MotionEvent event) {
/* 538 */           return this.mImpl.onTouchEvent(event);
                }

/* 550 */       public void setIsLongpressEnabled(boolean enabled) {
/* 551 */           this.mImpl.setIsLongpressEnabled(enabled);
                }

/* 561 */       public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener listener) {
/* 562 */           this.mImpl.setOnDoubleTapListener(listener);
                }
            }
