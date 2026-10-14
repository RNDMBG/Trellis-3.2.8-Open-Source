            package com.google.android.gms.dynamic;

            import android.app.Activity;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface LifecycleDelegate {
                void onCreate(Bundle bundle);

                View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

                void onDestroy();

                void onDestroyView();

                void onInflate(Activity activity, Bundle bundle, Bundle bundle2);

                void onLowMemory();

                void onPause();

                void onResume();

                void onSaveInstanceState(Bundle bundle);
            }
