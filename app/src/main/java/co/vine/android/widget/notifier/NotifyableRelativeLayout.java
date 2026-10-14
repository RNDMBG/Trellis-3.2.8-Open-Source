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
            import co.vine.android.C0257R;
            import co.vine.android.recorder.ViewGoneAnimationListener;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class NotifyableRelativeLayout extends RelativeLayout {
                private int mCount;
                private NotifierClickListener mListener;
                private int mNotificationMargin;
                private int mVineGreen;

                public interface NotifierClickListener {
                    void onNotifyClick();
                }

/* 20 */        static /* synthetic */ NotifierClickListener access$000(NotifyableRelativeLayout x0) {
/* 21 */            return x0.mListener;
                }

/* 20 */        static /* synthetic */ void access$100(NotifyableRelativeLayout x0) {
/* 21 */            x0.hideNotification();
                }

/* 33 */        public NotifyableRelativeLayout(Context context) {
/* 34 */            super(context);
/* 35 */            init(context);
                }

/* 39 */        public NotifyableRelativeLayout(Context context, AttributeSet attrs) {
/* 40 */            super(context, attrs);
/* 41 */            init(context);
                }

/* 45 */        public NotifyableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
/* 46 */            super(context, attrs, defStyle);
/* 47 */            init(context);
                }

/* 50 */        public void init(Context context) {
/* 51 */            Resources res = context.getResources();
/* 52 */            LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
/* 54 */            View v = inflater.inflate(C0257R.layout.floating_notification, (ViewGroup) null);
/* 55 */            TextView t = (TextView) v.findViewById(C0257R.id.floating_view);
/* 57 */            Drawable d = res.getDrawable(C0257R.drawable.vm_notification);
/* 58 */            this.mVineGreen = res.getColor(C0257R.color.vine_green);
/* 59 */            d.setColorFilter(this.mVineGreen, PorterDuff.Mode.SRC_ATOP);
/* 61 */            ViewUtil.setBackground(t, d);
/* 63 */            addView(v);
/* 65 */            this.mNotificationMargin = res.getDimensionPixelOffset(C0257R.dimen.tabbar_height) + res.getDimensionPixelOffset(C0257R.dimen.notification_margin);
/* 68 */            t.setOnClickListener(new ViewOnClickListenerC05771());
                }

                /* JADX INFO: renamed from: co.vine.android.widget.notifier.NotifyableRelativeLayout$1 */
/* 66 */        class ViewOnClickListenerC05771 implements View.OnClickListener {
/* 67 */            ViewOnClickListenerC05771() {
                    }

                    @Override // android.view.View.OnClickListener
/* 70 */            public void onClick(View v) {
/* 71 */                if (NotifyableRelativeLayout.access$000(NotifyableRelativeLayout.this) != null) {
/* 72 */                    NotifyableRelativeLayout.access$000(NotifyableRelativeLayout.this).onNotifyClick();
                        }
/* 74 */                NotifyableRelativeLayout.access$100(NotifyableRelativeLayout.this);
                    }
                }

                @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
/* 81 */        protected void onLayout(boolean changed, int l, int t, int r, int b) {
                    for (int i = 0; i < getChildCount(); i++) {
/* 83 */                View child = getChildAt(i);
/* 84 */                if (child.getId() == 2131427584) {
/* 85 */                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) child.getLayoutParams();
/* 87 */                    params.addRule(10);
/* 88 */                    params.topMargin = this.mNotificationMargin;
/* 89 */                    bringChildToFront(child);
                        }
                    }
/* 92 */            super.onLayout(changed, l, t, r, b);
                }

/* 95 */        public void setNotifierListener(NotifierClickListener listener) {
/* 96 */            this.mListener = listener;
                }

/* 99 */        public void indicate(int count) {
/* 100 */           if (count <= 0) {
/* 101 */               hideNotification();
                    } else {
/* 103 */               TextView t = (TextView) findViewById(C0257R.id.floating_view);
                        t.setText(getResources().getQuantityString(C0257R.plurals.new_messages, count, Integer.valueOf(count)));
/* 105 */               showNotification();
                    }
/* 107 */           this.mCount = count;
                }

/* 110 */       private void showNotification() {
/* 111 */           View v = findViewById(C0257R.id.floating_view);
/* 112 */           v.setVisibility(0);
                }

/* 115 */       private void hideNotification() {
/* 116 */           View v = findViewById(C0257R.id.floating_view);
/* 117 */           v.animate().alpha(0.0f).setDuration(1000L).setListener(new ViewGoneAnimationListener(v));
/* 118 */           v.setVisibility(8);
                }

/* 121 */       public void personalizeColor(int color) {
/* 122 */           TextView t = (TextView) findViewById(C0257R.id.floating_view);
/* 123 */           Drawable d = getResources().getDrawable(C0257R.drawable.vm_notification);
/* 124 */           if (color != this.mVineGreen) {
/* 125 */               color = Util.getColorWithBlackPercentage(color, 0.24f);
                    }
/* 127 */           d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
/* 128 */           ViewUtil.setBackground(t, d);
                }
            }
