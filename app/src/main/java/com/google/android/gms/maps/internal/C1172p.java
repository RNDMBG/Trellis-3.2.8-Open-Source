            package com.google.android.gms.maps.internal;

            import android.os.Bundle;
            import android.os.Parcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.p */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1172p {
                private C1172p() {
                }

                /* JADX INFO: renamed from: a */
                public static void m2257a(Bundle bundle, String str, Parcelable parcelable) {
                    bundle.setClassLoader(C1172p.class.getClassLoader());
                    Bundle bundle2 = bundle.getBundle("map_state");
                    if (bundle2 == null) {
                        bundle2 = new Bundle();
                    }
                    bundle2.setClassLoader(C1172p.class.getClassLoader());
                    bundle2.putParcelable(str, parcelable);
                    bundle.putBundle("map_state", bundle2);
                }
            }
