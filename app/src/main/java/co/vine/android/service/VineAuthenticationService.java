            package co.vine.android.service;

            import android.app.Service;
            import android.content.Intent;
            import android.os.IBinder;
            import co.vine.android.VineAccountAuthenticator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class VineAuthenticationService extends Service {
                private VineAccountAuthenticator mAuthenticator;

                @Override // android.app.Service
/* 33 */        public void onCreate() {
/* 34 */            super.onCreate();
/* 35 */            this.mAuthenticator = new VineAccountAuthenticator(this);
                }

                @Override // android.app.Service
/* 39 */        public void onDestroy() {
/* 40 */            super.onDestroy();
/* 41 */            this.mAuthenticator = null;
                }

                @Override // android.app.Service
/* 45 */        public IBinder onBind(Intent intent) {
/* 46 */            return this.mAuthenticator.getIBinder();
                }
            }
