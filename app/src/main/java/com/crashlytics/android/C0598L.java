            package com.crashlytics.android;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            
            /* JADX INFO: renamed from: com.crashlytics.android.L */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 262 */   final class C0598L extends BroadcastReceiver {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ C0744v f155a;

/* 263 */       C0598L(C0744v c0744v) {
/* 264 */           this.f155a = c0744v;
                }

                @Override // android.content.BroadcastReceiver
/* 266 */       public final void onReceive(Context context, Intent intent) {
/* 267 */           C0744v.m407a(this.f155a, true);
                }
            }
