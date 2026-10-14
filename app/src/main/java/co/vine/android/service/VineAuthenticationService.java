            package co.vine.android.service;

            import android.app.Service;
            import android.content.Intent;
            import android.os.IBinder;
            import co.vine.android.VineAccountAuthenticator;
            
            
    public class VineAuthenticationService extends Service {
                private VineAccountAuthenticator mAuthenticator;

                @Override 
        public void onCreate() {
            super.onCreate();
            this.mAuthenticator = new VineAccountAuthenticator(this);
                }

                @Override 
        public void onDestroy() {
            super.onDestroy();
            this.mAuthenticator = null;
                }

                @Override 
        public IBinder onBind(Intent intent) {
            return this.mAuthenticator.getIBinder();
                }
            }
