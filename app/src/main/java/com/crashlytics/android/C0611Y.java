            package com.crashlytics.android;

            import android.content.Context;
            import android.graphics.BitmapFactory;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0727v;
            
            
            
    final class C0611Y {

                
                public final String f172a;

                
                public final int f173b;

                
                public final int f174c;

                
                public final int f175d;

        private C0611Y(String str, int i, int i2, int i3) {
            this.f172a = str;
            this.f173b = i;
            this.f174c = i2;
            this.f175d = i3;
                }

                
        public static C0611Y m46a(Context context, String str) {
            if (str != null) {
                        try {
                    int iM232h = C0681ab.m232h(context);
                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "App icon resource ID is " + iM232h);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeResource(context.getResources(), iM232h, options);
                    return new C0611Y(str, iM232h, options.outWidth, options.outHeight);
                        } catch (Exception e) {
                    C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to load icon", e);
                        }
                    }
            return null;
                }
            }
