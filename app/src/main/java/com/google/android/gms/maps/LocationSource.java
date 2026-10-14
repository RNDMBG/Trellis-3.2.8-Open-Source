            package com.google.android.gms.maps;

            import android.location.Location;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface LocationSource {

                public interface OnLocationChangedListener {
                    void onLocationChanged(Location location);
                }

                void activate(OnLocationChangedListener onLocationChangedListener);

                void deactivate();
            }
