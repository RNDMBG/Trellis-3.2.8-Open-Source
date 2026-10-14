            package com.google.android.gms.appstate;

            import com.google.android.gms.common.data.Freezable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface AppState extends Freezable<AppState> {
                byte[] getConflictData();

                String getConflictVersion();

                int getKey();

                byte[] getLocalData();

                String getLocalVersion();

                boolean hasConflict();
            }
