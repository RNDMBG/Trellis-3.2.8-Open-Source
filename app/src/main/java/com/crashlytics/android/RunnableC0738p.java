            package com.crashlytics.android;

            import android.app.Activity;
            import android.app.AlertDialog;
            import android.widget.ScrollView;
            import android.widget.TextView;
            import com.crashlytics.android.internal.C0670aQ;
            
            
            
   final class RunnableC0738p implements Runnable {

                
                final  C0743u f479a;

                
                final  Crashlytics f480b;

                
                private  Activity f481c;

                
                private  C0610X f482d;

                
                private  C0670aQ f483e;

       RunnableC0738p(Crashlytics crashlytics, Activity activity, C0743u c0743u, C0610X c0610x, C0670aQ c0670aQ) {
           this.f480b = crashlytics;
                    this.f481c = activity;
                    this.f479a = c0743u;
                    this.f482d = c0610x;
                    this.f483e = c0670aQ;
                }

                @Override 
       public final void run() {
           AlertDialog.Builder builder = new AlertDialog.Builder(this.f481c);
           DialogInterfaceOnClickListenerC0739q dialogInterfaceOnClickListenerC0739q = new DialogInterfaceOnClickListenerC0739q(this);
           float f = this.f481c.getResources().getDisplayMetrics().density;
           int iM6a = Crashlytics.m6a(this.f480b, f, 5);
           TextView textView = new TextView(this.f481c);
           textView.setAutoLinkMask(15);
           textView.setText(this.f482d.m42b());
           textView.setTextAppearance(this.f481c, android.R.style.TextAppearance.Medium);
           textView.setPadding(iM6a, iM6a, iM6a, iM6a);
           textView.setFocusable(false);
           ScrollView scrollView = new ScrollView(this.f481c);
           scrollView.setPadding(Crashlytics.m6a(this.f480b, f, 14), Crashlytics.m6a(this.f480b, f, 2), Crashlytics.m6a(this.f480b, f, 10), Crashlytics.m6a(this.f480b, f, 12));
           scrollView.addView(textView);
           builder.setView(scrollView).setTitle(this.f482d.m41a()).setCancelable(false).setNeutralButton(this.f482d.m43c(), dialogInterfaceOnClickListenerC0739q);
           if (this.f483e.f318d) {
               builder.setNegativeButton(this.f482d.m45e(), new DialogInterfaceOnClickListenerC0740r(this));
                    }
           if (this.f483e.f320f) {
               builder.setPositiveButton(this.f482d.m44d(), new DialogInterfaceOnClickListenerC0741s(this));
                    }
           builder.show();
                }
            }
