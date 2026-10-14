            package com.facebook.buck.android.support.exopackage;

            import android.app.Application;
            import android.content.res.Configuration;
            
            
    public class DefaultApplicationLike implements ApplicationLike {
        public DefaultApplicationLike() {
                }

        public DefaultApplicationLike(Application application) {
                }

                @Override 
        public void onCreate() {
                }

                @Override 
        public void onLowMemory() {
                }

                @Override 
        public void onTrimMemory(int level) {
                }

                @Override 
        public void onTerminate() {
                }

                @Override 
        public void onConfigurationChanged(Configuration newConfig) {
                }
            }
