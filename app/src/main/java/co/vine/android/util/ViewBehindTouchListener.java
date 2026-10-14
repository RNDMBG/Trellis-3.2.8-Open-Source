            package co.vine.android.util;

            import android.content.Context;
            import android.support.v4.view.MotionEventCompat;
            import android.support.v4.view.ViewConfigurationCompat;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewConfiguration;
            import co.vine.android.views.TouchableRelativeLayout;
            import com.edisonwang.android.slog.SLog;
            import com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class ViewBehindTouchListener implements TouchableRelativeLayout.TouchListener {
                private int mActivePointerId;
                private float mInitialX;
                private final View mInvalidateTarget;
                private boolean mIsScrolling;
                private boolean mSetInitials;
                private final CustomViewAbove mSlideController;
                private final int mTouchSlop;

/* 28 */        public ViewBehindTouchListener(Context context, SlidingMenu sm) {
/* 30 */            ViewConfiguration configuration = ViewConfiguration.get(context);
/* 31 */            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
/* 32 */            this.mSlideController = sm.getViewAbove();
/* 33 */            this.mInvalidateTarget = sm.getViewBehind();
                }

                @Override // co.vine.android.views.TouchableRelativeLayout.TouchListener
/* 37 */        public boolean onInterceptTouchEvent(MotionEvent ev) {
/* 38 */            SLog.m446d("onInterceptTouchEvent!!!!");
/* 46 */            int action = MotionEventCompat.getActionMasked(ev);
/* 49 */            if (action == 3 || action == 1) {
/* 51 */                this.mIsScrolling = false;
                        return false;
                    }
                    switch (action) {
                        case 0:
/* 80 */                    this.mInitialX = ev.getX();
/* 81 */                    int index = MotionEventCompat.getActionIndex(ev);
/* 82 */                    this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
/* 83 */                    this.mSetInitials = true;
                            break;
                        case 2:
/* 57 */                    if (this.mIsScrolling) {
/* 58 */                        SLog.m446d("Scrolling 2");
                                return true;
                            }
/* 67 */                    int xDiff = Math.abs((int) (ev.getX() - this.mInitialX));
/* 71 */                    if (xDiff > this.mTouchSlop) {
/* 73 */                        SLog.m446d("Scrolling");
/* 74 */                        this.mIsScrolling = true;
                                return true;
                            }
                            break;
                    }
/* 88 */            this.mSlideController.addMovement(ev);
/* 90 */            this.mInvalidateTarget.postInvalidateDelayed(100L);
                    return false;
                }

                @Override // co.vine.android.views.TouchableRelativeLayout.TouchListener
/* 98 */        public boolean onTouchEvent(MotionEvent ev) {
/* 99 */            SLog.m446d("onTouchEvent!!");
/* 100 */           if (this.mSetInitials) {
/* 101 */               this.mSlideController.setInitialTouchEvents(ev.getX(), this.mActivePointerId);
/* 102 */               this.mSlideController.determineDrag(ev);
/* 103 */               this.mSetInitials = false;
                    }
/* 105 */           this.mSlideController.onTouchEvent(ev);
                    switch (ev.getAction()) {
                        case 1:
                        case 3:
/* 114 */                   this.mIsScrolling = false;
                            break;
                    }
/* 120 */           return true;
                }
            }
