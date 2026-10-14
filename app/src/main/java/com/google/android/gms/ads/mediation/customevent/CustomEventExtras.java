            package com.google.android.gms.ads.mediation.customevent;

            import com.google.ads.mediation.NetworkExtras;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class CustomEventExtras implements NetworkExtras {

                /* JADX INFO: renamed from: in */
                private final HashMap<String, Object> f909in = new HashMap<>();

                public Object getExtra(String label) {
                    return this.f909in.get(label);
                }

                public void setExtra(String label, Object value) {
                    this.f909in.put(label, value);
                }
            }
