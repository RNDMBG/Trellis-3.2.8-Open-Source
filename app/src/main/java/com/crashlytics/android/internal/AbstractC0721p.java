            package com.crashlytics.android.internal;

            import android.content.Context;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.p */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public abstract class AbstractC0721p {

                /* JADX INFO: renamed from: a */
                private Context f459a;

                /* JADX INFO: renamed from: b */
                private boolean f460b;

                /* JADX INFO: renamed from: c */
                protected abstract void mo28c();

                public abstract String getVersion();

                /* JADX INFO: renamed from: a */
/* 55 */        protected final synchronized void m351a(Context context) {
                    try {
                        if (!this.f460b) {
/* 60 */                    if (context == null) {
/* 61 */                        throw new IllegalArgumentException("context cannot be null.");
                            }
/* 64 */                    this.f459a = new C0731z(context.getApplicationContext(), getName());
/* 71 */                    this.f460b = true;
/* 72 */                    mo28c();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 79 */        public final Context getContext() {
/* 80 */            return this.f459a;
                }

/* 86 */        public final synchronized boolean isInitialized() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f460b;
                }

/* 94 */        public final String getName() {
/* 95 */            return getClass().getSimpleName().toLowerCase();
                }

/* 102 */       public String getComponentPath() {
/* 103 */           return ".TwitterSdk/" + getName();
                }
            }
