            package com.google.android.gms.internal;

            import android.R;
            import android.app.Activity;
            import android.view.View;
            import android.widget.FrameLayout;
            import android.widget.ImageButton;
            
            
            
            public final class ViewOnClickListenerC0972bk extends FrameLayout implements View.OnClickListener {

                
                private final Activity f1465fD;

                
                private final ImageButton f1466gk;

                public ViewOnClickListenerC0972bk(Activity activity, int i) {
                    super(activity);
                    this.f1465fD = activity;
                    setOnClickListener(this);
                    this.f1466gk = new ImageButton(activity);
                    this.f1466gk.setImageResource(R.drawable.btn_dialog);
                    this.f1466gk.setBackgroundColor(0);
                    this.f1466gk.setOnClickListener(this);
                    this.f1466gk.setPadding(0, 0, 0, 0);
                    int iM1442a = C1001cm.m1442a(activity, i);
                    addView(this.f1466gk, new FrameLayout.LayoutParams(iM1442a, iM1442a, 17));
                }

                
                public void m1336d(boolean z) {
                    this.f1466gk.setVisibility(z ? 4 : 0);
                }

                @Override 
                public void onClick(View view) {
                    this.f1465fD.finish();
                }
            }
