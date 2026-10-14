            package android.support.multidex;

            import android.app.Application;
            import android.content.Context;
            
            
    public class MultiDexApplication extends Application {
                @Override 
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
            MultiDex.install(this);
                }
            }
