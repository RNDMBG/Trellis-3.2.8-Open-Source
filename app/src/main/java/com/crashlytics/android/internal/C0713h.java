            package com.crashlytics.android.internal;

            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.h */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    final class C0713h {

                /* JADX INFO: renamed from: a */
                final Object f452a;

                /* JADX INFO: renamed from: b */
                private final Method f453b;

                /* JADX INFO: renamed from: c */
                private final int f454c;

                /* JADX INFO: renamed from: d */
                private boolean f455d = true;

/* 40 */        C0713h(Object obj, Method method) {
/* 42 */            if (obj == null) {
/* 43 */                throw new NullPointerException("EventProducer target cannot be null.");
                    }
/* 45 */            if (method == null) {
/* 46 */                throw new NullPointerException("EventProducer method cannot be null.");
                    }
/* 49 */            this.f452a = obj;
/* 50 */            this.f453b = method;
/* 51 */            method.setAccessible(true);
/* 55 */            this.f454c = ((method.hashCode() + 31) * 31) + obj.hashCode();
                }

                /* JADX INFO: renamed from: a */
/* 59 */        public final boolean m345a() {
/* 60 */            return this.f455d;
                }

                /* JADX INFO: renamed from: b */
/* 68 */        public final void m346b() {
/* 69 */            this.f455d = false;
                }

                /* JADX INFO: renamed from: c */
/* 79 */        public final Object m347c() throws InvocationTargetException {
/* 80 */            if (!this.f455d) {
/* 81 */                throw new IllegalStateException(toString() + " has been invalidated and can no longer produce events.");
                    }
                    try {
/* 84 */                return this.f453b.invoke(this.f452a, new Object[0]);
                    } catch (IllegalAccessException e) {
/* 86 */                throw new AssertionError(e);
                    } catch (InvocationTargetException e2) {
/* 88 */                if (e2.getCause() instanceof Error) {
/* 89 */                    throw ((Error) e2.getCause());
                        }
/* 91 */                throw e2;
                    }
                }

/* 95 */        public final String toString() {
/* 96 */            return "[EventProducer " + this.f453b + "]";
                }

/* 99 */        public final int hashCode() {
/* 100 */           return this.f454c;
                }

/* 103 */       public final boolean equals(Object obj) {
/* 104 */           if (this == obj) {
                        return true;
                    }
/* 108 */           if (obj != null && getClass() == obj.getClass()) {
/* 116 */               C0713h c0713h = (C0713h) obj;
                        return this.f453b.equals(c0713h.f453b) && this.f452a == c0713h.f452a;
                    }
                    return false;
                }
            }
