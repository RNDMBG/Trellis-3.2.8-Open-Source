            package com.flurry.sdk;

            import javax.net.ssl.HostnameVerifier;
            import javax.net.ssl.SSLSession;
            
            
            
     public class C0820ee implements HostnameVerifier {
                @Override 
         public boolean verify(String str, SSLSession sSLSession) {
            return true;
                }
            }
