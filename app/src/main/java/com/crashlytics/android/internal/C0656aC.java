            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.net.HttpURLConnection;
            import java.net.URL;
            
            
            
   final class C0656aC implements InterfaceC0655aB {
       C0656aC() {
                }

                @Override 
                
       public final HttpURLConnection mo161a(URL url) throws IOException {
           return (HttpURLConnection) url.openConnection();
                }
            }
