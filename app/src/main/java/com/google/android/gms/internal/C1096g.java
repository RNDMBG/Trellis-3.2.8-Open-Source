            package com.google.android.gms.internal;

            import android.content.Context;
            import com.google.android.gms.ads.identifier.AdvertisingIdClient;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.common.GooglePlayServicesRepairableException;
            import java.io.IOException;
            
            
            
            public class C1096g extends AbstractC1069f {

                
                class a {

                    
                    private String f2100dt;

                    
                    private boolean f2101du;

                    public a(String str, boolean z) {
                        this.f2100dt = str;
                        this.f2101du = z;
                    }

                    public String getId() {
                        return this.f2100dt;
                    }

                    public boolean isLimitAdTrackingEnabled() {
                        return this.f2101du;
                    }
                }

                private C1096g(Context context, InterfaceC1112j interfaceC1112j, InterfaceC1113k interfaceC1113k) {
                    super(context, interfaceC1112j, interfaceC1113k);
                }

                
                public static C1096g m2070a(String str, Context context) {
                    C0934a c0934a = new C0934a();
                    m1918a(str, context, c0934a);
                    return new C1096g(context, c0934a, new C1115m(239));
                }

                @Override 
                
                protected void mo1697b(Context context) {
                    super.mo1697b(context);
                    try {
                        try {
                            a aVarM2071f = m2071f(context);
                            m1694a(28, aVarM2071f.isLimitAdTrackingEnabled() ? 1L : 0L);
                            String id = aVarM2071f.getId();
                            if (id != null) {
                                m1695a(30, id);
                            }
                        } catch (GooglePlayServicesNotAvailableException e) {
                        } catch (IOException e2) {
                            m1694a(28, 1L);
                        }
                    } catch (IOException e3) {
                    }
                }

                
                a m2071f(Context context) throws GooglePlayServicesNotAvailableException, IOException {
                    String strMo1236a;
                    int i = 0;
                    try {
                        AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                        String id = advertisingIdInfo.getId();
                        if (id == null || !id.matches("^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$")) {
                            strMo1236a = id;
                        } else {
                            byte[] bArr = new byte[16];
                            int i2 = 0;
                            while (i < id.length()) {
                                if (i == 8 || i == 13 || i == 18 || i == 23) {
                                    i++;
                                }
                                bArr[i2] = (byte) ((Character.digit(id.charAt(i), 16) << 4) + Character.digit(id.charAt(i + 1), 16));
                                i2++;
                                i += 2;
                            }
                            strMo1236a = this.f1755di.mo1236a(bArr, true);
                        }
                        return new a(strMo1236a, advertisingIdInfo.isLimitAdTrackingEnabled());
                    } catch (GooglePlayServicesRepairableException e) {
                        throw new IOException(e);
                    }
                }
            }
