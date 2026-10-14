            package co.vine.android.widget;

            import android.graphics.Rect;
            import android.text.Layout;
            import android.text.Spannable;
            import android.util.Log;
            import android.view.MotionEvent;
            import android.view.View;
            import android.widget.TextView;
            import co.vine.android.client.VineAPI;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class LinkTouchListener implements View.OnTouchListener {
                private final Rect mContentRect;
                private int mLastUrlDownX;
                private int mLastUrlDownY;
                private final Layout mLayout;
                private StyledClickableSpan mSpan;
                private Spannable mSpannable;
                private TextView mTextView;
                private final int mTouchSlop;

/* 24 */        public LinkTouchListener(TextView tv, int slop, Spannable spannable) {
/* 26 */            Log.v(VineAPI.RESOURCE_SHARED, "rectangle is " + tv.getLeft() + " | " + tv.getTop() + " | " + tv.getRight() + " | " + tv.getBottom());
/* 27 */            this.mContentRect = new Rect(tv.getLeft(), tv.getTop(), tv.getRight(), tv.getBottom());
/* 28 */            this.mLayout = tv.getLayout();
/* 29 */            this.mTextView = tv;
/* 30 */            this.mTouchSlop = slop;
/* 31 */            this.mSpannable = spannable;
                }

                @Override // android.view.View.OnTouchListener
/* 35 */        public boolean onTouch(View view, MotionEvent event) {
/* 36 */            int action = event.getAction() & 255;
/* 37 */            int x = (int) event.getX();
/* 38 */            int y = (int) event.getY();
/* 40 */            Log.v(VineAPI.RESOURCE_SHARED, "on touch, action: " + action + " |x: " + x + " |y: " + y);
/* 41 */            Log.v(VineAPI.RESOURCE_SHARED, "on touch, view:  |left: " + view.getLeft() + " |right: " + view.getRight());
/* 43 */            Rect r = this.mContentRect;
/* 44 */            Layout layout = this.mLayout;
/* 47 */            int translatedX = r.left + x;
/* 48 */            int translatedY = r.top + y;
/* 49 */            int line = layout.getLineForVertical(translatedY);
/* 51 */            Log.v(VineAPI.RESOURCE_SHARED, "translated x: " + translatedX + " | " + translatedY);
/* 54 */            int lineMaxX = (int) layout.getLineMax(line);
/* 58 */            if (translatedX > lineMaxX) {
/* 59 */                deselectSpan();
/* 60 */                return false;
                    }
/* 64 */            if (action == 0) {
/* 65 */                this.mLastUrlDownX = x;
/* 66 */                this.mLastUrlDownY = y;
                    }
/* 69 */            int offset = layout.getOffsetForHorizontal(line, translatedX);
/* 70 */            StyledClickableSpan[] link = (StyledClickableSpan[]) this.mSpannable.getSpans(offset, offset, StyledClickableSpan.class);
/* 71 */            Log.v(VineAPI.RESOURCE_SHARED, "size is " + link.length);
/* 72 */            StyledClickableSpan selectedLink = this.mSpan;
/* 74 */            TextView textView = this.mTextView;
/* 76 */            Log.v(VineAPI.RESOURCE_SHARED, "contains?" + r.contains(translatedX, translatedY));
/* 78 */            if (r.contains(translatedX, translatedY) && (action == 1 || action == 0)) {
/* 80 */                if (action == 1) {
/* 81 */                    if (selectedLink != null) {
/* 82 */                        selectedLink.onClick(textView);
/* 83 */                        deselectSpan();
                            }
                        } else {
/* 86 */                    Log.v(VineAPI.RESOURCE_SHARED, "selecting span");
/* 87 */                    selectedLink.select(true);
/* 88 */                    this.mSpan = link[0];
/* 89 */                    textView.invalidate();
                        }
/* 91 */                return true;
                    }
/* 92 */            if (selectedLink != null && (Math.abs(x - this.mLastUrlDownX) > this.mTouchSlop || Math.abs(y - this.mLastUrlDownY) > this.mTouchSlop)) {
/* 94 */                Log.v(VineAPI.RESOURCE_SHARED, "deselecting due to slop");
/* 95 */                deselectSpan();
                    }
/* 97 */            return false;
                }

/* 100 */       private void deselectSpan() {
/* 101 */           if (this.mSpan != null && this.mSpan.isSelected()) {
/* 102 */               Log.v(VineAPI.RESOURCE_SHARED, "deselecting span");
/* 103 */               this.mSpan.select(false);
/* 104 */               this.mLastUrlDownX = 0;
/* 105 */               this.mLastUrlDownY = 0;
/* 106 */               this.mTextView.invalidate();
                    }
                }
            }
