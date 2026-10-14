            package com.crashlytics.android.internal;

            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            
            
            
    final class C0712g {

                
                private final Object f448a;

                
                private final Method f449b;

                
                private final int f450c;

                
                private boolean f451d = true;

        C0712g(Object obj, Method method) {
            if (obj == null) {
                throw new NullPointerException("EventHandler target cannot be null.");
                    }
            if (method == null) {
                throw new NullPointerException("EventHandler method cannot be null.");
                    }
            this.f448a = obj;
            this.f449b = method;
            method.setAccessible(true);
            this.f450c = ((method.hashCode() + 31) * 31) + obj.hashCode();
                }

                
        public final boolean m343a() {
            return this.f451d;
                }

                
        public final void m344b() {
            this.f451d = false;
                }

                
        public final void m342a(Object obj) throws InvocationTargetException {
            if (!this.f451d) {
                throw new IllegalStateException(toString() + " has been invalidated and can no longer handle events.");
                    }
                    try {
                this.f449b.invoke(this.f448a, obj);
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
           return "[EventHandler " + this.f449b + "]";
                }

       public final int hashCode() {
           return this.f450c;
                }

       public final boolean equals(Object obj) {
           if (this == obj) {
                        return true;
                    }
           if (obj != null && getClass() == obj.getClass()) {
               C0712g c0712g = (C0712g) obj;
                        return this.f449b.equals(c0712g.f449b) && this.f448a == c0712g.f448a;
                    }
                    return false;
                }
            }
