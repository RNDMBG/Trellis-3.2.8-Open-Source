            package com.google.android.gms.internal;

            import android.content.Context;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            
            
            
            public final class C0979br {

                
                public interface a {
                    
                    void mo1357a(C0984bw c0984bw);
                }

                
                public static AbstractC0995cg m1359a(Context context, C0982bu c0982bu, a aVar) {
                    return c0982bu.f1494eg.f1599hS ? m1360b(context, c0982bu, aVar) : m1361c(context, c0982bu, aVar);
                }

                
                private static AbstractC0995cg m1360b(Context context, C0982bu c0982bu, a aVar) {
                    C1002cn.m1454m("Fetching ad response from local ad request service.");
                    AbstractC0980bs.a aVar2 = new AbstractC0980bs.a(context, c0982bu, aVar);
                    aVar2.start();
                    return aVar2;
                }

                
                private static AbstractC0995cg m1361c(Context context, C0982bu c0982bu, a aVar) {
                    C1002cn.m1454m("Fetching ad response from remote ad request service.");
                    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
                        return new AbstractC0980bs.b(context, c0982bu, aVar);
                    }
                    C1002cn.m1458q("Failed to connect to remote ad request service.");
                    return null;
                }
            }
