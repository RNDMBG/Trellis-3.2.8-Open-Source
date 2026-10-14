            package com.crashlytics.android;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            
            /* JADX INFO: renamed from: com.crashlytics.android.M */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 271 */   final class C0599M extends BroadcastReceiver {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0744v f156a;

/* 272 */       C0599M(C0744v c0744v) {
/* 273 */           this.f156a = c0744v;
                }

                @Override // android.content.BroadcastReceiver
/* 275 */       public final void onReceive(Context context, Intent intent) {
/* 276 */           C0744v.m407a(this.f156a, false);
                }
            }
