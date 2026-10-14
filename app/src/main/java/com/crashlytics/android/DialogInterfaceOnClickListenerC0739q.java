            package com.crashlytics.android;

            import android.content.DialogInterface;
            
            /* JADX INFO: renamed from: com.crashlytics.android.q */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 672 */   final class DialogInterfaceOnClickListenerC0739q implements DialogInterface.OnClickListener {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ RunnableC0738p f484a;

/* 673 */       DialogInterfaceOnClickListenerC0739q(RunnableC0738p runnableC0738p) {
/* 674 */           this.f484a = runnableC0738p;
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 676 */       public final void onClick(DialogInterface dialog, int i) {
/* 677 */           this.f484a.f479a.m375a(true);
/* 678 */           dialog.dismiss();
                }
            }
