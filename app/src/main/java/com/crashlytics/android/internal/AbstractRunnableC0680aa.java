            package com.crashlytics.android.internal;

            import android.os.Process;
            
            
            
    public abstract class AbstractRunnableC0680aa implements Runnable {
                
                protected abstract void mo38a();

                @Override 
        public final void run() {
            Process.setThreadPriority(10);
            mo38a();
                }
            }
