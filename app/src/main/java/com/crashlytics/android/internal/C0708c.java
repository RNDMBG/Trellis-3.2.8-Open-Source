            package com.crashlytics.android.internal;

            import java.util.concurrent.ConcurrentLinkedQueue;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 108 */   final class C0708c extends ThreadLocal<ConcurrentLinkedQueue<C0710e>> {
/* 109 */       C0708c(C0706b c0706b) {
                }

                @Override // java.lang.ThreadLocal
/* 109 */       protected final /* synthetic */ ConcurrentLinkedQueue<C0710e> initialValue() {
/* 110 */           return new ConcurrentLinkedQueue<>();
                }
            }
