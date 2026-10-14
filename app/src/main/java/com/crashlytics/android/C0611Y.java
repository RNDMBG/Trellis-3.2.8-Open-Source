            package com.crashlytics.android;

            import android.content.Context;
            import android.graphics.BitmapFactory;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0727v;
            
            /* JADX INFO: renamed from: com.crashlytics.android.Y */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    final class C0611Y {

                /* JADX INFO: renamed from: a */
                public final String f172a;

                /* JADX INFO: renamed from: b */
                public final int f173b;

                /* JADX INFO: renamed from: c */
                public final int f174c;

                /* JADX INFO: renamed from: d */
                public final int f175d;

/* 19 */        private C0611Y(String str, int i, int i2, int i3) {
/* 21 */            this.f172a = str;
/* 22 */            this.f173b = i;
/* 23 */            this.f174c = i2;
/* 24 */            this.f175d = i3;
                }

                /* JADX INFO: renamed from: a */
/* 32 */        public static C0611Y m46a(Context context, String str) {
/* 37 */            if (str != null) {
                        try {
/* 39 */                    int iM232h = C0681ab.m232h(context);
/* 40 */                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "App icon resource ID is " + iM232h);
/* 45 */                    BitmapFactory.Options options = new BitmapFactory.Options();
/* 46 */                    options.inJustDecodeBounds = true;
/* 48 */                    BitmapFactory.decodeResource(context.getResources(), iM232h, options);
/* 50 */                    return new C0611Y(str, iM232h, options.outWidth, options.outHeight);
                        } catch (Exception e) {
/* 52 */                    C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to load icon", e);
                        }
                    }
/* 33 */            return null;
                }
            }
