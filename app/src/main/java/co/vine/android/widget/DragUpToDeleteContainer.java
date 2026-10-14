            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import co.vine.android.animation.SmoothAnimator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class DragUpToDeleteContainer extends RelativeLayout {
                private static final int ALPHA_AT_TOP = 77;
                private static final int DESTROY_TIME = 350;
                private static final int DROP_TIME = 150;
                private static final float SMOOTHNESS = 0.05f;
                private float mCurY;
                private boolean mDestroying;
                private float mDownX;
                private float mDownY;
                private Bitmap mDragBitmap;
                private DragUpListener mDragUpListener;
                private ImageView mDragView;
                private float mDragViewAlpha;
                private final DragViewDestroyAnimator mDragViewDestroyAnimator;
                private final DropAnimator mDragViewDropAnimator;
                private int mDragViewHeight;
                private int mDragViewWidth;
                private int mDragViewX;
                private int mDragViewY;
                private boolean mDropping;
                private boolean mLockAlpha;
                private final int mTouchSlop;
                private View mViewToPickUp;
                private float mYOffsetFromTouch;

                public interface DragUpListener {
                    boolean prepareForPickup();

                    void viewDestroyFinished();

                    boolean viewDropped(int i);

                    void viewLanded();

                    boolean viewMoved(int i);

                    void viewPickedUp();
                }

/* 20 */        static /* synthetic */ int access$000(DragUpToDeleteContainer x0) {
/* 21 */            return x0.mDragViewY;
                }

/* 20 */        static /* synthetic */ int access$002(DragUpToDeleteContainer x0, int x1) {
/* 21 */            x0.mDragViewY = x1;
                    return x1;
                }

/* 20 */        static /* synthetic */ int access$100(DragUpToDeleteContainer x0) {
/* 21 */            return x0.mDragViewHeight;
                }

/* 20 */        static /* synthetic */ int access$1000(DragUpToDeleteContainer x0) {
/* 21 */            return x0.mDragViewX;
                }

/* 20 */        static /* synthetic */ int access$1002(DragUpToDeleteContainer x0, int x1) {
/* 21 */            x0.mDragViewX = x1;
                    return x1;
                }

/* 20 */        static /* synthetic */ boolean access$202(DragUpToDeleteContainer x0, boolean x1) {
/* 21 */            x0.mDestroying = x1;
                    return x1;
                }

/* 20 */        static /* synthetic */ float access$302(DragUpToDeleteContainer x0, float x1) {
/* 21 */            x0.mDragViewAlpha = x1;
                    return x1;
                }

/* 20 */        static /* synthetic */ void access$400(DragUpToDeleteContainer x0) {
/* 21 */            x0.destroyDragView();
                }

/* 20 */        static /* synthetic */ DragUpListener access$500(DragUpToDeleteContainer x0) {
/* 21 */            return x0.mDragUpListener;
                }

/* 20 */        static /* synthetic */ boolean access$602(DragUpToDeleteContainer x0, boolean x1) {
/* 21 */            x0.mDropping = x1;
                    return x1;
                }

/* 20 */        static /* synthetic */ ImageView access$700(DragUpToDeleteContainer x0) {
/* 21 */            return x0.mDragView;
                }

/* 20 */        static /* synthetic */ float access$800(DragUpToDeleteContainer x0) {
/* 21 */            return x0.mDownY;
                }

/* 20 */        static /* synthetic */ float access$900(DragUpToDeleteContainer x0) {
/* 21 */            return x0.mYOffsetFromTouch;
                }

/* 48 */        public DragUpToDeleteContainer(Context context) {
/* 49 */            this(context, null);
                }

/* 52 */        public DragUpToDeleteContainer(Context context, AttributeSet attrs) {
/* 53 */            this(context, attrs, 0);
                }

/* 56 */        public DragUpToDeleteContainer(Context context, AttributeSet attrs, int defStyle) {
/* 57 */            super(context, attrs, defStyle);
/* 46 */            this.mLockAlpha = false;
/* 58 */            this.mDragViewDestroyAnimator = new DragViewDestroyAnimator(SMOOTHNESS, DESTROY_TIME);
/* 59 */            this.mDragViewDropAnimator = new DropAnimator(SMOOTHNESS, 150);
/* 60 */            this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
                }

/* 63 */        public void setView(View v, int leftMargin) {
/* 64 */            this.mViewToPickUp = v;
/* 65 */            this.mDragViewX = leftMargin;
                }

                @Override // android.view.ViewGroup
/* 69 */        public boolean onInterceptTouchEvent(MotionEvent event) {
/* 70 */            int action = event.getAction();
                    switch (action) {
                        case 0:
/* 73 */                    this.mDownY = event.getY();
/* 74 */                    this.mDownX = event.getX();
                            return false;
                        case 1:
                        default:
                            return false;
                        case 2:
                            if (this.mDownY - event.getY() < this.mTouchSlop || Math.abs(event.getX() - this.mDownX) >= this.mTouchSlop) {
                                return false;
                            }
/* 78 */                    this.mCurY = event.getY();
/* 79 */                    boolean canPickUp = true;
/* 80 */                    if (this.mDragUpListener != null) {
/* 81 */                        canPickUp = this.mDragUpListener.prepareForPickup();
                            }
/* 83 */                    if (canPickUp) {
/* 84 */                        createDragView();
/* 85 */                        if (this.mDragUpListener != null) {
/* 86 */                            this.mDragUpListener.viewPickedUp();
                                }
/* 88 */                        invalidate();
                            }
/* 90 */                    return true;
                    }
                }

                @Override // android.view.View
/* 100 */       public boolean onTouchEvent(@NonNull MotionEvent event) {
/* 101 */           int action = event.getAction();
                    switch (action) {
                        case 1:
/* 114 */                   boolean delete = false;
/* 115 */                   if (this.mDragUpListener != null) {
/* 116 */                       delete = this.mDragUpListener.viewDropped(this.mDragViewY);
                            }
/* 118 */                   if (delete) {
/* 119 */                       this.mDragViewDestroyAnimator.start();
                            } else {
/* 121 */                       this.mDragViewDropAnimator.start();
                            }
/* 123 */                   invalidate();
                            return true;
                        case 2:
/* 104 */                   this.mCurY = event.getY();
/* 105 */                   this.mDragViewY = (int) (this.mCurY - this.mYOffsetFromTouch);
/* 106 */                   int startY = (int) (this.mDownY - this.mYOffsetFromTouch);
/* 107 */                   if (this.mDragViewY <= startY) {
/* 107 */                       startY = this.mDragViewY;
                            }
                            this.mDragViewY = startY;
/* 108 */                   if (this.mDragUpListener != null) {
/* 109 */                       this.mLockAlpha = this.mDragUpListener.viewMoved(this.mDragViewY);
                            }
/* 111 */                   invalidate();
                            return true;
                        default:
/* 126 */                   return false;
                    }
                }

/* 129 */       public void setInteractionListner(DragUpListener dragUpListener) {
/* 130 */           this.mDragUpListener = dragUpListener;
                }

/* 133 */       private void destroyDragView() {
/* 134 */           if (this.mDragView != null) {
/* 135 */               this.mDragView.setImageDrawable(null);
/* 137 */               this.mDragBitmap = null;
/* 139 */               this.mDragView = null;
                    }
                }

/* 143 */       private void createDragView() {
/* 144 */           if (this.mViewToPickUp != null) {
/* 148 */               this.mViewToPickUp.setDrawingCacheQuality(1048576);
/* 149 */               this.mViewToPickUp.setDrawingCacheEnabled(true);
/* 150 */               this.mDragBitmap = this.mViewToPickUp.getDrawingCache();
/* 152 */               if (this.mDragView == null) {
/* 153 */                   this.mDragView = new ImageView(getContext());
                        }
/* 155 */               this.mDragViewAlpha = 255.0f;
/* 157 */               this.mDragView.setImageBitmap(this.mDragBitmap);
/* 158 */               this.mDragView.setLayoutParams(new ViewGroup.LayoutParams(this.mViewToPickUp.getWidth(), this.mViewToPickUp.getHeight()));
/* 159 */               int[] myPosition = new int[2];
/* 160 */               int[] itsPosition = new int[2];
/* 161 */               getLocationInWindow(myPosition);
/* 162 */               this.mViewToPickUp.getLocationInWindow(itsPosition);
/* 163 */               this.mYOffsetFromTouch = (this.mDownY - itsPosition[1]) + myPosition[1];
/* 165 */               this.mDragViewWidth = this.mViewToPickUp.getWidth();
/* 166 */               this.mDragViewHeight = this.mViewToPickUp.getHeight();
/* 168 */               this.mDragViewY = (int) (this.mCurY - this.mYOffsetFromTouch);
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 172 */       public void dispatchDraw(@NonNull Canvas canvas) {
/* 173 */           super.dispatchDraw(canvas);
/* 174 */           if (this.mDragView != null) {
/* 175 */               if (!this.mDestroying && !this.mLockAlpha) {
/* 176 */                   int dragViewStartY = (int) (this.mDownY - this.mYOffsetFromTouch);
/* 177 */                   this.mDragViewAlpha = (((1.0f * this.mDragViewY) / dragViewStartY) * 178.0f) + 77.0f;
                        }
                        if (this.mDragViewAlpha < 0.0f) {
/* 180 */                   this.mDragViewAlpha = 0.0f;
                        }
/* 182 */               canvas.save();
/* 183 */               canvas.translate(this.mDragViewX, this.mDragViewY);
/* 184 */               canvas.clipRect(0, 0, this.mDragViewWidth, this.mDragViewHeight);
/* 185 */               canvas.saveLayerAlpha(0.0f, 0.0f, this.mDragViewWidth, this.mDragViewHeight, (int) this.mDragViewAlpha, 31);
/* 187 */               this.mDragView.layout(0, 0, this.mDragViewWidth, this.mDragViewHeight);
/* 188 */               this.mDragView.draw(canvas);
/* 189 */               canvas.restore();
/* 190 */               canvas.restore();
                    }
                }

/* 204 */       private class DragViewDestroyAnimator extends SmoothAnimator {
                    private int mDeltaY;
                    private int mStartY;

/* 205 */           public DragViewDestroyAnimator(float smoothness, int duration) {
/* 207 */               super(DragUpToDeleteContainer.this, smoothness, duration);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 210 */           public void onStart() {
/* 211 */               this.mStartY = DragUpToDeleteContainer.access$000(DragUpToDeleteContainer.this);
/* 212 */               this.mDeltaY = DragUpToDeleteContainer.access$000(DragUpToDeleteContainer.this) + DragUpToDeleteContainer.access$100(DragUpToDeleteContainer.this);
/* 213 */               DragUpToDeleteContainer.access$202(DragUpToDeleteContainer.this, true);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 216 */           public void onUpdate(float frac, float smoothFrac) {
/* 217 */               DragUpToDeleteContainer.access$002(DragUpToDeleteContainer.this, (int) (this.mStartY - (this.mDeltaY * smoothFrac)));
/* 218 */               DragUpToDeleteContainer.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 222 */           public void onStop() {
/* 223 */               DragUpToDeleteContainer.access$302(DragUpToDeleteContainer.this, 0.0f);
/* 224 */               DragUpToDeleteContainer.access$202(DragUpToDeleteContainer.this, false);
/* 225 */               DragUpToDeleteContainer.access$400(DragUpToDeleteContainer.this);
/* 226 */               if (DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this) != null) {
/* 227 */                   DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this).viewDestroyFinished();
                        }
/* 229 */               DragUpToDeleteContainer.this.invalidate();
                    }
                }

/* 241 */       private class DropAnimator extends SmoothAnimator {
                    private float mDeltaX;
                    private float mDeltaY;
                    private int mTargetX;
                    private int mTargetY;

/* 242 */           public DropAnimator(float smoothness, int duration) {
/* 244 */               super(DragUpToDeleteContainer.this, smoothness, duration);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 248 */           public void onStart() {
/* 249 */               DragUpToDeleteContainer.access$602(DragUpToDeleteContainer.this, true);
/* 250 */               if (DragUpToDeleteContainer.access$700(DragUpToDeleteContainer.this) != null) {
/* 251 */                   this.mTargetY = (int) (DragUpToDeleteContainer.access$800(DragUpToDeleteContainer.this) - DragUpToDeleteContainer.access$900(DragUpToDeleteContainer.this));
                        }
/* 253 */               this.mTargetX = DragUpToDeleteContainer.access$1000(DragUpToDeleteContainer.this);
/* 254 */               this.mDeltaX = DragUpToDeleteContainer.access$1000(DragUpToDeleteContainer.this) - this.mTargetX;
/* 255 */               this.mDeltaY = DragUpToDeleteContainer.access$000(DragUpToDeleteContainer.this) - this.mTargetY;
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 260 */           public void onUpdate(float frac, float smoothFrac) {
/* 261 */               float f = 1.0f - smoothFrac;
/* 262 */               DragUpToDeleteContainer.access$1002(DragUpToDeleteContainer.this, this.mTargetX + ((int) (this.mDeltaX * f)));
/* 263 */               DragUpToDeleteContainer.access$002(DragUpToDeleteContainer.this, this.mTargetY + ((int) (this.mDeltaY * f)));
/* 264 */               DragUpToDeleteContainer.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 268 */           public void onStop() {
/* 269 */               if (DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this) != null) {
/* 270 */                   DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this).viewLanded();
                        }
/* 272 */               DragUpToDeleteContainer.access$400(DragUpToDeleteContainer.this);
/* 273 */               DragUpToDeleteContainer.this.invalidate();
                    }
                }
            }
