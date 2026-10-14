            package com.crashlytics.android;

            import android.os.AsyncTask;
            
            
            
    final class AsyncTaskC0732j extends AsyncTask<Void, Void, Void> {

                
                private  long f474a;

                
                private  CrashTest f475b;

        AsyncTaskC0732j(CrashTest crashTest, long j) {
            this.f475b = crashTest;
                    this.f474a = j;
                }

                @Override 
        protected final  Void doInBackground(Void[] voidArr) {
            return m374a();
                }

                
        private Void m374a() {
                    try {
                Thread.sleep(this.f474a);
                    } catch (InterruptedException e) {
                    }
            this.f475b.throwRuntimeException("Background thread crash");
            return null;
                }
            }
