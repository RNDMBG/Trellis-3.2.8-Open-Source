            package com.google.android.gms.internal;

            import android.content.Context;
            import java.io.IOException;
            import java.net.HttpURLConnection;
            import java.net.URL;
            
            
            
            public final class C1000cl extends AbstractC0995cg {

                
                private final String f1593hM;

                
                private final String f1594hN;
                private final Context mContext;

                public C1000cl(Context context, String str, String str2) {
                    this.mContext = context;
                    this.f1593hM = str;
                    this.f1594hN = str2;
                }

                @Override 
                
                public void mo1358ac() {
                    try {
                        C1002cn.m1457p("Pinging URL: " + this.f1594hN);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.f1594hN).openConnection();
                        try {
                            C0997ci.m1416a(this.mContext, this.f1593hM, true, httpURLConnection);
                            int responseCode = httpURLConnection.getResponseCode();
                            if (responseCode < 200 || responseCode >= 300) {
                                C1002cn.m1458q("Received non-success response code " + responseCode + " from pinging URL: " + this.f1594hN);
                            }
                            httpURLConnection.disconnect();
                        } catch (Throwable th) {
                            httpURLConnection.disconnect();
                            throw th;
                        }
                    } catch (IOException e) {
                        C1002cn.m1458q("Error while pinging URL: " + this.f1594hN + ". " + e.getMessage());
                    }
                }

                @Override 
                public void onStop() {
                }
            }
