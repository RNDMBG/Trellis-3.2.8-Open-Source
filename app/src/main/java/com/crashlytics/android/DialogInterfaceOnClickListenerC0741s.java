            package com.crashlytics.android;

            import android.content.DialogInterface;
            
            /* JADX INFO: renamed from: com.crashlytics.android.s */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 715 */   final class DialogInterfaceOnClickListenerC0741s implements DialogInterface.OnClickListener {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ RunnableC0738p f486a;

/* 716 */       DialogInterfaceOnClickListenerC0741s(RunnableC0738p runnableC0738p) {
/* 717 */           this.f486a = runnableC0738p;
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 719 */       public final void onClick(DialogInterface dialog, int i) {
/* 720 */           Crashlytics crashlytics = this.f486a.f480b;
                    Crashlytics.m12a(true);
/* 721 */           this.f486a.f479a.m375a(true);
/* 722 */           dialog.dismiss();
                }
            }
