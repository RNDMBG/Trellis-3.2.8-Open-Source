            package co.vine.android.widget;

            import android.graphics.Rect;
            import android.text.Layout;
            import android.text.Spannable;
            import android.util.Log;
            import android.view.MotionEvent;
            import android.view.View;
            import android.widget.TextView;
            import co.vine.android.client.VineAPI;
            
            
    public class LinkTouchListener implements View.OnTouchListener {
                private final Rect mContentRect;
                private int mLastUrlDownX;
                private int mLastUrlDownY;
                private final Layout mLayout;
                private StyledClickableSpan mSpan;
                private Spannable mSpannable;
                private TextView mTextView;
                private final int mTouchSlop;

        public LinkTouchListener(TextView tv, int slop, Spannable spannable) {
            Log.v(VineAPI.RESOURCE_SHARED, "rectangle is " + tv.getLeft() + " | " + tv.getTop() + " | " + tv.getRight() + " | " + tv.getBottom());
            this.mContentRect = new Rect(tv.getLeft(), tv.getTop(), tv.getRight(), tv.getBottom());
            this.mLayout = tv.getLayout();
            this.mTextView = tv;
            this.mTouchSlop = slop;
            this.mSpannable = spannable;
                }

                @Override 
        public boolean onTouch(View view, MotionEvent event) {
            int action = event.getAction() & 255;
            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.v(VineAPI.RESOURCE_SHARED, "on touch, action: " + action + " |x: " + x + " |y: " + y);
            Log.v(VineAPI.RESOURCE_SHARED, "on touch, view:  |left: " + view.getLeft() + " |right: " + view.getRight());
            Rect r = this.mContentRect;
            Layout layout = this.mLayout;
            int translatedX = r.left + x;
            int translatedY = r.top + y;
            int line = layout.getLineForVertical(translatedY);
            Log.v(VineAPI.RESOURCE_SHARED, "translated x: " + translatedX + " | " + translatedY);
            int lineMaxX = (int) layout.getLineMax(line);
            if (translatedX > lineMaxX) {
                deselectSpan();
                return false;
                    }
            if (action == 0) {
                this.mLastUrlDownX = x;
                this.mLastUrlDownY = y;
                    }
            int offset = layout.getOffsetForHorizontal(line, translatedX);
            StyledClickableSpan[] link = (StyledClickableSpan[]) this.mSpannable.getSpans(offset, offset, StyledClickableSpan.class);
            Log.v(VineAPI.RESOURCE_SHARED, "size is " + link.length);
            StyledClickableSpan selectedLink = this.mSpan;
            TextView textView = this.mTextView;
            Log.v(VineAPI.RESOURCE_SHARED, "contains?" + r.contains(translatedX, translatedY));
            if (r.contains(translatedX, translatedY) && (action == 1 || action == 0)) {
                if (action == 1) {
                    if (selectedLink != null) {
                        selectedLink.onClick(textView);
                        deselectSpan();
                            }
                        } else {
                    Log.v(VineAPI.RESOURCE_SHARED, "selecting span");
                    selectedLink.select(true);
                    this.mSpan = link[0];
                    textView.invalidate();
                        }
                return true;
                    }
            if (selectedLink != null && (Math.abs(x - this.mLastUrlDownX) > this.mTouchSlop || Math.abs(y - this.mLastUrlDownY) > this.mTouchSlop)) {
                Log.v(VineAPI.RESOURCE_SHARED, "deselecting due to slop");
                deselectSpan();
                    }
            return false;
                }

       private void deselectSpan() {
           if (this.mSpan != null && this.mSpan.isSelected()) {
               Log.v(VineAPI.RESOURCE_SHARED, "deselecting span");
               this.mSpan.select(false);
               this.mLastUrlDownX = 0;
               this.mLastUrlDownY = 0;
               this.mTextView.invalidate();
                    }
                }
            }
