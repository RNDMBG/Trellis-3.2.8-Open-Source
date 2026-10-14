            package com.google.android.gms.ads.mediation.customevent;

            import com.google.ads.mediation.NetworkExtras;
            import java.util.HashMap;
            
            
            public final class CustomEventExtras implements NetworkExtras {

                
                private final HashMap<String, Object> f909in = new HashMap<>();

                public Object getExtra(String label) {
                    return this.f909in.get(label);
                }

                public void setExtra(String label, Object value) {
                    this.f909in.put(label, value);
                }
            }
