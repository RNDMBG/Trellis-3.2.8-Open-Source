            package com.crashlytics.android;

            import android.app.Activity;
            import android.app.AlertDialog;
            import android.widget.ScrollView;
            import android.widget.TextView;
            import com.crashlytics.android.internal.C0670aQ;
            
            /* JADX INFO: renamed from: com.crashlytics.android.p */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 667 */   final class RunnableC0738p implements Runnable {

                /* JADX INFO: renamed from: a */
                final /* synthetic */ C0743u f479a;

                /* JADX INFO: renamed from: b */
                final /* synthetic */ Crashlytics f480b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ Activity f481c;

                /* JADX INFO: renamed from: d */
                private /* synthetic */ C0610X f482d;

                /* JADX INFO: renamed from: e */
                private /* synthetic */ C0670aQ f483e;

/* 668 */       RunnableC0738p(Crashlytics crashlytics, Activity activity, C0743u c0743u, C0610X c0610x, C0670aQ c0670aQ) {
/* 669 */           this.f480b = crashlytics;
                    this.f481c = activity;
                    this.f479a = c0743u;
                    this.f482d = c0610x;
                    this.f483e = c0670aQ;
                }

                @Override // java.lang.Runnable
/* 671 */       public final void run() {
/* 672 */           AlertDialog.Builder builder = new AlertDialog.Builder(this.f481c);
/* 674 */           DialogInterfaceOnClickListenerC0739q dialogInterfaceOnClickListenerC0739q = new DialogInterfaceOnClickListenerC0739q(this);
/* 682 */           float f = this.f481c.getResources().getDisplayMetrics().density;
/* 686 */           int iM6a = Crashlytics.m6a(this.f480b, f, 5);
/* 688 */           TextView textView = new TextView(this.f481c);
/* 689 */           textView.setAutoLinkMask(15);
/* 690 */           textView.setText(this.f482d.m42b());
/* 691 */           textView.setTextAppearance(this.f481c, android.R.style.TextAppearance.Medium);
/* 692 */           textView.setPadding(iM6a, iM6a, iM6a, iM6a);
/* 693 */           textView.setFocusable(false);
/* 695 */           ScrollView scrollView = new ScrollView(this.f481c);
/* 696 */           scrollView.setPadding(Crashlytics.m6a(this.f480b, f, 14), Crashlytics.m6a(this.f480b, f, 2), Crashlytics.m6a(this.f480b, f, 10), Crashlytics.m6a(this.f480b, f, 12));
/* 698 */           scrollView.addView(textView);
/* 702 */           builder.setView(scrollView).setTitle(this.f482d.m41a()).setCancelable(false).setNeutralButton(this.f482d.m43c(), dialogInterfaceOnClickListenerC0739q);
/* 705 */           if (this.f483e.f318d) {
/* 713 */               builder.setNegativeButton(this.f482d.m45e(), new DialogInterfaceOnClickListenerC0740r(this));
                    }
/* 716 */           if (this.f483e.f320f) {
/* 725 */               builder.setPositiveButton(this.f482d.m44d(), new DialogInterfaceOnClickListenerC0741s(this));
                    }
/* 728 */           builder.show();
                }
            }
