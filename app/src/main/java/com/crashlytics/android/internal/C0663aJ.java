            package com.crashlytics.android.internal;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.os.Build;
            
            
            
    public class C0663aJ {

                
                private final String f303a;

                
                private final Context f304b;

        public C0663aJ(AbstractC0726u abstractC0726u) {
            if (abstractC0726u.getContext() == null) {
                throw new IllegalStateException("Cannot get directory before context has been set. Call Sdk.start() first");
                    }
            this.f304b = abstractC0726u.getContext();
            this.f303a = abstractC0726u.getClass().getName();
                }

                
        public SharedPreferences m174a() {
            return this.f304b.getSharedPreferences(this.f303a, 0);
                }

                
        public SharedPreferences.Editor m176b() {
            return m174a().edit();
                }

                
        public boolean m175a(SharedPreferences.Editor editor) {
            if (Build.VERSION.SDK_INT < 9) {
                        return editor.commit();
                    }
            editor.apply();
            return true;
                }
            }
