            package com.crashlytics.android;

            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.Callable;
            
            /* JADX INFO: Add missing generic type declarations: [T] */
            /* JADX INFO: renamed from: com.crashlytics.android.I */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 1984 */  final class CallableC0595I<T> implements Callable<T> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Callable f154a;

/* 1985 */      CallableC0595I(C0744v c0744v, Callable callable) {
/* 1986 */          this.f154a = callable;
                }

                @Override // java.util.concurrent.Callable
/* 1989 */      public final T call() throws Exception {
                    try {
/* 1990 */              return (T) this.f154a.call();
                    } catch (Exception e) {
/* 1992 */              C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to execute task.", e);
/* 1993 */              return null;
                    }
                }
            }
