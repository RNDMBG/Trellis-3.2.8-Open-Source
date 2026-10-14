            package com.crashlytics.android.internal;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.os.Build;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aJ */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class C0663aJ {

                /* JADX INFO: renamed from: a */
                private final String f303a;

                /* JADX INFO: renamed from: b */
                private final Context f304b;

/* 12 */        public C0663aJ(AbstractC0726u abstractC0726u) {
/* 14 */            if (abstractC0726u.getContext() == null) {
/* 15 */                throw new IllegalStateException("Cannot get directory before context has been set. Call Sdk.start() first");
                    }
/* 19 */            this.f304b = abstractC0726u.getContext();
/* 20 */            this.f303a = abstractC0726u.getClass().getName();
                }

                /* JADX INFO: renamed from: a */
/* 27 */        public SharedPreferences m174a() {
/* 28 */            return this.f304b.getSharedPreferences(this.f303a, 0);
                }

                /* JADX INFO: renamed from: b */
/* 35 */        public SharedPreferences.Editor m176b() {
/* 36 */            return m174a().edit();
                }

                /* JADX INFO: renamed from: a */
/* 45 */        public boolean m175a(SharedPreferences.Editor editor) {
/* 46 */            if (Build.VERSION.SDK_INT < 9) {
                        return editor.commit();
                    }
/* 47 */            editor.apply();
/* 48 */            return true;
                }
            }
