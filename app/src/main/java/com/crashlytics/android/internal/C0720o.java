            package com.crashlytics.android.internal;

            import android.os.Looper;
            
            
            
    final class C0720o implements InterfaceC0718m {
        C0720o() {
                }

                @Override 
                
        public final void mo350a(C0706b c0706b) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw new IllegalStateException("Event bus " + c0706b + " accessed from non-main thread " + Looper.myLooper());
                    }
                }
            }
