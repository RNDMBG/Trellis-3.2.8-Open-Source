            package com.crashlytics.android;

            import android.content.DialogInterface;
            
            /* JADX INFO: renamed from: com.crashlytics.android.r */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 704 */   final class DialogInterfaceOnClickListenerC0740r implements DialogInterface.OnClickListener {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ RunnableC0738p f485a;

/* 705 */       DialogInterfaceOnClickListenerC0740r(RunnableC0738p runnableC0738p) {
/* 706 */           this.f485a = runnableC0738p;
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 708 */       public final void onClick(DialogInterface dialog, int i) {
/* 709 */           this.f485a.f479a.m375a(false);
/* 710 */           dialog.dismiss();
                }
            }
