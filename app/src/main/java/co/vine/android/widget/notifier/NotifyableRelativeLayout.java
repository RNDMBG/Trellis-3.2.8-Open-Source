            package co.vine.android.widget.notifier;

            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.Drawable;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.R;
            import co.vine.android.recorder.ViewGoneAnimationListener;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            
            
    public class NotifyableRelativeLayout extends RelativeLayout {
                private int mCount;
                private NotifierClickListener mListener;
                private int mNotificationMargin;
                private int mVineGreen;

                public interface NotifierClickListener {
                    void onNotifyClick();
                }

        static  NotifierClickListener access$000(NotifyableRelativeLayout x0) {
            return x0.mListener;
                }

        static  void access$100(NotifyableRelativeLayout x0) {
            x0.hideNotification();
                }

        public NotifyableRelativeLayout(Context context) {
            super(context);
            init(context);
                }

        public NotifyableRelativeLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
                }

        public NotifyableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(context);
                }

        public void init(Context context) {
            Resources res = context.getResources();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
            View v = inflater.inflate(R.layout.floating_notification, (ViewGroup) null);
            TextView t = (TextView) v.findViewById(R.id.floating_view);
            Drawable d = res.getDrawable(R.drawable.vm_notification);
            this.mVineGreen = res.getColor(R.color.vine_green);
            d.setColorFilter(this.mVineGreen, PorterDuff.Mode.SRC_ATOP);
            ViewUtil.setBackground(t, d);
            addView(v);
            this.mNotificationMargin = res.getDimensionPixelOffset(R.dimen.tabbar_height) + res.getDimensionPixelOffset(R.dimen.notification_margin);
            t.setOnClickListener(new ViewOnClickListenerC05771());
                }

                
        class ViewOnClickListenerC05771 implements View.OnClickListener {
            ViewOnClickListenerC05771() {
                    }

                    @Override 
            public void onClick(View v) {
                if (NotifyableRelativeLayout.access$000(NotifyableRelativeLayout.this) != null) {
                    NotifyableRelativeLayout.access$000(NotifyableRelativeLayout.this).onNotifyClick();
                        }
                NotifyableRelativeLayout.access$100(NotifyableRelativeLayout.this);
                    }
                }

                @Override 
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
                    for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getId() == 2131427584) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) child.getLayoutParams();
                    params.addRule(10);
                    params.topMargin = this.mNotificationMargin;
                    bringChildToFront(child);
                        }
                    }
            super.onLayout(changed, l, t, r, b);
                }

        public void setNotifierListener(NotifierClickListener listener) {
            this.mListener = listener;
                }

        public void indicate(int count) {
           if (count <= 0) {
               hideNotification();
                    } else {
               TextView t = (TextView) findViewById(R.id.floating_view);
                        t.setText(getResources().getQuantityString(R.plurals.new_messages, count, Integer.valueOf(count)));
               showNotification();
                    }
           this.mCount = count;
                }

       private void showNotification() {
           View v = findViewById(R.id.floating_view);
           v.setVisibility(0);
                }

       private void hideNotification() {
           View v = findViewById(R.id.floating_view);
           v.animate().alpha(0.0f).setDuration(1000L).setListener(new ViewGoneAnimationListener(v));
           v.setVisibility(8);
                }

       public void personalizeColor(int color) {
           TextView t = (TextView) findViewById(R.id.floating_view);
           Drawable d = getResources().getDrawable(R.drawable.vm_notification);
           if (color != this.mVineGreen) {
               color = Util.getColorWithBlackPercentage(color, 0.24f);
                    }
           d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
           ViewUtil.setBackground(t, d);
                }
            }
