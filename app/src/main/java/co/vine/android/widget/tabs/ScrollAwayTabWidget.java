            package co.vine.android.widget.tabs;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.util.AttributeSet;
            import android.view.MotionEvent;
            import android.widget.TabWidget;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class ScrollAwayTabWidget extends TabWidget {
                private int mCurrentDelta;

/* 15 */        public ScrollAwayTabWidget(Context context) {
/* 16 */            super(context);
/* 13 */            this.mCurrentDelta = 0;
                }

/* 19 */        public ScrollAwayTabWidget(Context context, AttributeSet attrs) {
/* 20 */            super(context, attrs);
/* 13 */            this.mCurrentDelta = 0;
                }

/* 23 */        public ScrollAwayTabWidget(Context context, AttributeSet attrs, int defStyle) {
/* 24 */            super(context, attrs, defStyle);
/* 13 */            this.mCurrentDelta = 0;
                }

                @Override // android.view.View
/* 28 */        public void draw(Canvas c) {
/* 29 */            c.save();
/* 30 */            c.translate(0.0f, this.mCurrentDelta);
/* 31 */            super.draw(c);
/* 32 */            c.restore();
                }

/* 35 */        public void onScroll(int delta) {
/* 36 */            int oldDelta = this.mCurrentDelta;
/* 37 */            this.mCurrentDelta += delta;
/* 38 */            if (this.mCurrentDelta < (-getHeight())) {
/* 39 */                this.mCurrentDelta = -getHeight();
                    } else if (this.mCurrentDelta > 0) {
/* 41 */                this.mCurrentDelta = 0;
                    }
/* 43 */            if (this.mCurrentDelta != oldDelta) {
/* 44 */                invalidate();
                    }
                }

/* 48 */        public void resetScroll() {
/* 49 */            this.mCurrentDelta = 0;
                }

/* 52 */        public int getNavBottom() {
/* 53 */            return this.mCurrentDelta + getHeight();
                }

                @Override // android.view.ViewGroup
/* 57 */        public boolean onInterceptTouchEvent(MotionEvent event) {
                    return event.getY() > ((float) getNavBottom());
                }

                @Override // android.view.View
/* 62 */        public boolean onTouchEvent(MotionEvent event) {
                    if (event.getY() > getNavBottom()) {
/* 64 */                return false;
                    }
                    return super.onTouchEvent(event);
                }
            }
