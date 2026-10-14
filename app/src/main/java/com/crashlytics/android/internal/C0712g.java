            package com.crashlytics.android.internal;

            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.g */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    final class C0712g {

                /* JADX INFO: renamed from: a */
                private final Object f448a;

                /* JADX INFO: renamed from: b */
                private final Method f449b;

                /* JADX INFO: renamed from: c */
                private final int f450c;

                /* JADX INFO: renamed from: d */
                private boolean f451d = true;

/* 44 */        C0712g(Object obj, Method method) {
/* 46 */            if (obj == null) {
/* 47 */                throw new NullPointerException("EventHandler target cannot be null.");
                    }
/* 49 */            if (method == null) {
/* 50 */                throw new NullPointerException("EventHandler method cannot be null.");
                    }
/* 53 */            this.f448a = obj;
/* 54 */            this.f449b = method;
/* 55 */            method.setAccessible(true);
/* 59 */            this.f450c = ((method.hashCode() + 31) * 31) + obj.hashCode();
                }

                /* JADX INFO: renamed from: a */
/* 63 */        public final boolean m343a() {
/* 64 */            return this.f451d;
                }

                /* JADX INFO: renamed from: b */
/* 72 */        public final void m344b() {
/* 73 */            this.f451d = false;
                }

                /* JADX INFO: renamed from: a */
/* 84 */        public final void m342a(Object obj) throws InvocationTargetException {
/* 85 */            if (!this.f451d) {
/* 86 */                throw new IllegalStateException(toString() + " has been invalidated and can no longer handle events.");
                    }
                    try {
/* 89 */                this.f449b.invoke(this.f448a, obj);
                    } catch (IllegalAccessException e) {
/* 91 */                throw new AssertionError(e);
                    } catch (InvocationTargetException e2) {
/* 93 */                if (e2.getCause() instanceof Error) {
/* 94 */                    throw ((Error) e2.getCause());
                        }
/* 96 */                throw e2;
                    }
                }

/* 100 */       public final String toString() {
/* 101 */           return "[EventHandler " + this.f449b + "]";
                }

/* 104 */       public final int hashCode() {
/* 105 */           return this.f450c;
                }

/* 108 */       public final boolean equals(Object obj) {
/* 109 */           if (this == obj) {
                        return true;
                    }
/* 113 */           if (obj != null && getClass() == obj.getClass()) {
/* 121 */               C0712g c0712g = (C0712g) obj;
                        return this.f449b.equals(c0712g.f449b) && this.f448a == c0712g.f448a;
                    }
                    return false;
                }
            }
