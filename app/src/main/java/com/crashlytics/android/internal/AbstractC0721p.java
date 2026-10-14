            package com.crashlytics.android.internal;

            import android.content.Context;
            
            
            
    public abstract class AbstractC0721p {

                
                private Context f459a;

                
                private boolean f460b;

                
                protected abstract void mo28c();

                public abstract String getVersion();

                
        protected final synchronized void m351a(Context context) {
                    try {
                        if (!this.f460b) {
                    if (context == null) {
                        throw new IllegalArgumentException("context cannot be null.");
                            }
                    this.f459a = new C0731z(context.getApplicationContext(), getName());
                    this.f460b = true;
                    mo28c();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        public final Context getContext() {
            return this.f459a;
                }

        public final synchronized boolean isInitialized() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f460b;
                }

        public final String getName() {
            return getClass().getSimpleName().toLowerCase();
                }

       public String getComponentPath() {
           return ".TwitterSdk/" + getName();
                }
            }
