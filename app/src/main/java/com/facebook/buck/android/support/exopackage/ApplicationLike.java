            package com.facebook.buck.android.support.exopackage;

            import android.content.res.Configuration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface ApplicationLike {
                void onConfigurationChanged(Configuration configuration);

                void onCreate();

                void onLowMemory();

                void onTerminate();

                void onTrimMemory(int i);
            }
