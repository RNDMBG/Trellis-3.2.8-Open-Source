            package com.crashlytics.android.internal;

            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            
            
            
    final class C0713h {

                
                final Object f452a;

                
                private final Method f453b;

                
                private final int f454c;

                
                private boolean f455d = true;

        C0713h(Object obj, Method method) {
            if (obj == null) {
                throw new NullPointerException("EventProducer target cannot be null.");
                    }
            if (method == null) {
                throw new NullPointerException("EventProducer method cannot be null.");
                    }
            this.f452a = obj;
            this.f453b = method;
            method.setAccessible(true);
            this.f454c = ((method.hashCode() + 31) * 31) + obj.hashCode();
                }

                
        public final boolean m345a() {
            return this.f455d;
                }

                
        public final void m346b() {
            this.f455d = false;
                }

                
        public final Object m347c() throws InvocationTargetException {
            if (!this.f455d) {
                throw new IllegalStateException(toString() + " has been invalidated and can no longer produce events.");
                    }
                    try {
                return this.f453b.invoke(this.f452a, new Object[0]);
                    } catch (IllegalAccessException e) {
                throw new AssertionError(e);
                    } catch (InvocationTargetException e2) {
                if (e2.getCause() instanceof Error) {
                    throw ((Error) e2.getCause());
                        }
                throw e2;
                    }
                }

        public final String toString() {
            return "[EventProducer " + this.f453b + "]";
                }

        public final int hashCode() {
           return this.f454c;
                }

       public final boolean equals(Object obj) {
           if (this == obj) {
                        return true;
                    }
           if (obj != null && getClass() == obj.getClass()) {
               C0713h c0713h = (C0713h) obj;
                        return this.f453b.equals(c0713h.f453b) && this.f452a == c0713h.f452a;
                    }
                    return false;
                }
            }
