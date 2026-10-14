            package com.crashlytics.android;

            import android.os.AsyncTask;
            
            /* JADX INFO: renamed from: com.crashlytics.android.j */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    final class AsyncTaskC0732j extends AsyncTask<Void, Void, Void> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ long f474a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ CrashTest f475b;

/* 41 */        AsyncTaskC0732j(CrashTest crashTest, long j) {
/* 42 */            this.f475b = crashTest;
                    this.f474a = j;
                }

                @Override // android.os.AsyncTask
/* 41 */        protected final /* synthetic */ Void doInBackground(Void[] voidArr) {
/* 42 */            return m374a();
                }

                /* JADX INFO: renamed from: a */
/* 45 */        private Void m374a() {
                    try {
/* 46 */                Thread.sleep(this.f474a);
                    } catch (InterruptedException e) {
                    }
/* 50 */            this.f475b.throwRuntimeException("Background thread crash");
/* 51 */            return null;
                }
            }
