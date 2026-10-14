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
            
            
    public class DragUpToDeleteContainer extends RelativeLayout {
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

        static  int access$000(DragUpToDeleteContainer x0) {
            return x0.mDragViewY;
                }

        static  int access$002(DragUpToDeleteContainer x0, int x1) {
            x0.mDragViewY = x1;
                    return x1;
                }

        static  int access$100(DragUpToDeleteContainer x0) {
            return x0.mDragViewHeight;
                }

        static  int access$1000(DragUpToDeleteContainer x0) {
            return x0.mDragViewX;
                }

        static  int access$1002(DragUpToDeleteContainer x0, int x1) {
            x0.mDragViewX = x1;
                    return x1;
                }

        static  boolean access$202(DragUpToDeleteContainer x0, boolean x1) {
            x0.mDestroying = x1;
                    return x1;
                }

        static  float access$302(DragUpToDeleteContainer x0, float x1) {
            x0.mDragViewAlpha = x1;
                    return x1;
                }

        static  void access$400(DragUpToDeleteContainer x0) {
            x0.destroyDragView();
                }

        static  DragUpListener access$500(DragUpToDeleteContainer x0) {
            return x0.mDragUpListener;
                }

        static  boolean access$602(DragUpToDeleteContainer x0, boolean x1) {
            x0.mDropping = x1;
                    return x1;
                }

        static  ImageView access$700(DragUpToDeleteContainer x0) {
            return x0.mDragView;
                }

        static  float access$800(DragUpToDeleteContainer x0) {
            return x0.mDownY;
                }

        static  float access$900(DragUpToDeleteContainer x0) {
            return x0.mYOffsetFromTouch;
                }

        public DragUpToDeleteContainer(Context context) {
            this(context, null);
                }

        public DragUpToDeleteContainer(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public DragUpToDeleteContainer(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.mLockAlpha = false;
            this.mDragViewDestroyAnimator = new DragViewDestroyAnimator(SMOOTHNESS, DESTROY_TIME);
            this.mDragViewDropAnimator = new DropAnimator(SMOOTHNESS, 150);
            this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
                }

        public void setView(View v, int leftMargin) {
            this.mViewToPickUp = v;
            this.mDragViewX = leftMargin;
                }

                @Override 
        public boolean onInterceptTouchEvent(MotionEvent event) {
            int action = event.getAction();
                    switch (action) {
                        case 0:
                    this.mDownY = event.getY();
                    this.mDownX = event.getX();
                            return false;
                        case 1:
                        default:
                            return false;
                        case 2:
                            if (this.mDownY - event.getY() < this.mTouchSlop || Math.abs(event.getX() - this.mDownX) >= this.mTouchSlop) {
                                return false;
                            }
                    this.mCurY = event.getY();
                    boolean canPickUp = true;
                    if (this.mDragUpListener != null) {
                        canPickUp = this.mDragUpListener.prepareForPickup();
                            }
                    if (canPickUp) {
                        createDragView();
                        if (this.mDragUpListener != null) {
                            this.mDragUpListener.viewPickedUp();
                                }
                        invalidate();
                            }
                    return true;
                    }
                }

                @Override 
       public boolean onTouchEvent(@NonNull MotionEvent event) {
           int action = event.getAction();
                    switch (action) {
                        case 1:
                   boolean delete = false;
                   if (this.mDragUpListener != null) {
                       delete = this.mDragUpListener.viewDropped(this.mDragViewY);
                            }
                   if (delete) {
                       this.mDragViewDestroyAnimator.start();
                            } else {
                       this.mDragViewDropAnimator.start();
                            }
                   invalidate();
                            return true;
                        case 2:
                   this.mCurY = event.getY();
                   this.mDragViewY = (int) (this.mCurY - this.mYOffsetFromTouch);
                   int startY = (int) (this.mDownY - this.mYOffsetFromTouch);
                   if (this.mDragViewY <= startY) {
                       startY = this.mDragViewY;
                            }
                            this.mDragViewY = startY;
                   if (this.mDragUpListener != null) {
                       this.mLockAlpha = this.mDragUpListener.viewMoved(this.mDragViewY);
                            }
                   invalidate();
                            return true;
                        default:
                   return false;
                    }
                }

       public void setInteractionListner(DragUpListener dragUpListener) {
           this.mDragUpListener = dragUpListener;
                }

       private void destroyDragView() {
           if (this.mDragView != null) {
               this.mDragView.setImageDrawable(null);
               this.mDragBitmap = null;
               this.mDragView = null;
                    }
                }

       private void createDragView() {
           if (this.mViewToPickUp != null) {
               this.mViewToPickUp.setDrawingCacheQuality(1048576);
               this.mViewToPickUp.setDrawingCacheEnabled(true);
               this.mDragBitmap = this.mViewToPickUp.getDrawingCache();
               if (this.mDragView == null) {
                   this.mDragView = new ImageView(getContext());
                        }
               this.mDragViewAlpha = 255.0f;
               this.mDragView.setImageBitmap(this.mDragBitmap);
               this.mDragView.setLayoutParams(new ViewGroup.LayoutParams(this.mViewToPickUp.getWidth(), this.mViewToPickUp.getHeight()));
               int[] myPosition = new int[2];
               int[] itsPosition = new int[2];
               getLocationInWindow(myPosition);
               this.mViewToPickUp.getLocationInWindow(itsPosition);
               this.mYOffsetFromTouch = (this.mDownY - itsPosition[1]) + myPosition[1];
               this.mDragViewWidth = this.mViewToPickUp.getWidth();
               this.mDragViewHeight = this.mViewToPickUp.getHeight();
               this.mDragViewY = (int) (this.mCurY - this.mYOffsetFromTouch);
                    }
                }

                @Override 
       public void dispatchDraw(@NonNull Canvas canvas) {
           super.dispatchDraw(canvas);
           if (this.mDragView != null) {
               if (!this.mDestroying && !this.mLockAlpha) {
                   int dragViewStartY = (int) (this.mDownY - this.mYOffsetFromTouch);
                   this.mDragViewAlpha = (((1.0f * this.mDragViewY) / dragViewStartY) * 178.0f) + 77.0f;
                        }
                        if (this.mDragViewAlpha < 0.0f) {
                   this.mDragViewAlpha = 0.0f;
                        }
               canvas.save();
               canvas.translate(this.mDragViewX, this.mDragViewY);
               canvas.clipRect(0, 0, this.mDragViewWidth, this.mDragViewHeight);
               canvas.saveLayerAlpha(0.0f, 0.0f, this.mDragViewWidth, this.mDragViewHeight, (int) this.mDragViewAlpha, 31);
               this.mDragView.layout(0, 0, this.mDragViewWidth, this.mDragViewHeight);
               this.mDragView.draw(canvas);
               canvas.restore();
               canvas.restore();
                    }
                }

       private class DragViewDestroyAnimator extends SmoothAnimator {
                    private int mDeltaY;
                    private int mStartY;

           public DragViewDestroyAnimator(float smoothness, int duration) {
               super(DragUpToDeleteContainer.this, smoothness, duration);
                    }

                    @Override 
           public void onStart() {
               this.mStartY = DragUpToDeleteContainer.access$000(DragUpToDeleteContainer.this);
               this.mDeltaY = DragUpToDeleteContainer.access$000(DragUpToDeleteContainer.this) + DragUpToDeleteContainer.access$100(DragUpToDeleteContainer.this);
               DragUpToDeleteContainer.access$202(DragUpToDeleteContainer.this, true);
                    }

                    @Override 
           public void onUpdate(float frac, float smoothFrac) {
               DragUpToDeleteContainer.access$002(DragUpToDeleteContainer.this, (int) (this.mStartY - (this.mDeltaY * smoothFrac)));
               DragUpToDeleteContainer.this.invalidate();
                    }

                    @Override 
           public void onStop() {
               DragUpToDeleteContainer.access$302(DragUpToDeleteContainer.this, 0.0f);
               DragUpToDeleteContainer.access$202(DragUpToDeleteContainer.this, false);
               DragUpToDeleteContainer.access$400(DragUpToDeleteContainer.this);
               if (DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this) != null) {
                   DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this).viewDestroyFinished();
                        }
               DragUpToDeleteContainer.this.invalidate();
                    }
                }

       private class DropAnimator extends SmoothAnimator {
                    private float mDeltaX;
                    private float mDeltaY;
                    private int mTargetX;
                    private int mTargetY;

           public DropAnimator(float smoothness, int duration) {
               super(DragUpToDeleteContainer.this, smoothness, duration);
                    }

                    @Override 
           public void onStart() {
               DragUpToDeleteContainer.access$602(DragUpToDeleteContainer.this, true);
               if (DragUpToDeleteContainer.access$700(DragUpToDeleteContainer.this) != null) {
                   this.mTargetY = (int) (DragUpToDeleteContainer.access$800(DragUpToDeleteContainer.this) - DragUpToDeleteContainer.access$900(DragUpToDeleteContainer.this));
                        }
               this.mTargetX = DragUpToDeleteContainer.access$1000(DragUpToDeleteContainer.this);
               this.mDeltaX = DragUpToDeleteContainer.access$1000(DragUpToDeleteContainer.this) - this.mTargetX;
               this.mDeltaY = DragUpToDeleteContainer.access$000(DragUpToDeleteContainer.this) - this.mTargetY;
                    }

                    @Override 
           public void onUpdate(float frac, float smoothFrac) {
               float f = 1.0f - smoothFrac;
               DragUpToDeleteContainer.access$1002(DragUpToDeleteContainer.this, this.mTargetX + ((int) (this.mDeltaX * f)));
               DragUpToDeleteContainer.access$002(DragUpToDeleteContainer.this, this.mTargetY + ((int) (this.mDeltaY * f)));
               DragUpToDeleteContainer.this.invalidate();
                    }

                    @Override 
           public void onStop() {
               if (DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this) != null) {
                   DragUpToDeleteContainer.access$500(DragUpToDeleteContainer.this).viewLanded();
                        }
               DragUpToDeleteContainer.access$400(DragUpToDeleteContainer.this);
               DragUpToDeleteContainer.this.invalidate();
                    }
                }
            }
