            package com.crashlytics.android.internal;

            import android.os.Handler;
            import android.os.Looper;
            
            
            
    public final class C0628B extends C0706b {

                
                private final Handler f210b;

                
        static  void m101a(C0628B c0628b, Object obj) {
            super.mo102c(obj);
                }

        public C0628B(InterfaceC0718m interfaceC0718m) {
            super(interfaceC0718m);
            this.f210b = new Handler(Looper.getMainLooper());
                }

                @Override 
                
        public final void mo102c(Object obj) {
            if (obj instanceof InterfaceC0725t) {
                InterfaceC0725t interfaceC0725t = (InterfaceC0725t) obj;
                        if (Looper.myLooper() != Looper.getMainLooper()) {
                    this.f210b.post(new RunnableC0629C(this, interfaceC0725t));
                    return;
                        } else {
                            super.mo102c(interfaceC0725t);
                    return;
                        }
                    }
            if ((obj instanceof InterfaceC0724s) || (obj instanceof C0711f)) {
                super.mo102c(obj);
                return;
                    }
            throw new IllegalArgumentException("Posted argument must implement Event interface");
                }
            }
