            package com.google.android.gms.ads.identifier;

            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.os.RemoteException;
            import android.util.Log;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.common.GooglePlayServicesRepairableException;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.common.ServiceConnectionC0887a;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.InterfaceC1118p;
            import java.io.IOException;
            
            
            public final class AdvertisingIdClient {

                public static final class Info {

                    
                    private final String f906dX;

                    
                    private final boolean f907dY;

                    Info(String advertisingId, boolean limitAdTrackingEnabled) {
                        this.f906dX = advertisingId;
                        this.f907dY = limitAdTrackingEnabled;
                    }

                    public String getId() {
                        return this.f906dX;
                    }

                    public boolean isLimitAdTrackingEnabled() {
                        return this.f907dY;
                    }
                }

                
                private static ServiceConnectionC0887a m996g(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, IOException {
                    try {
                        context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
                        try {
                            GooglePlayServicesUtil.m1020m(context);
                            ServiceConnectionC0887a serviceConnectionC0887a = new ServiceConnectionC0887a();
                            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                            intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                            if (context.bindService(intent, serviceConnectionC0887a, 1)) {
                                return serviceConnectionC0887a;
                            }
                            throw new IOException("Connection failure");
                        } catch (GooglePlayServicesNotAvailableException e) {
                            throw new IOException(e);
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                        throw new GooglePlayServicesNotAvailableException(9);
                    }
                }

                public static Info getAdvertisingIdInfo(Context context) throws GooglePlayServicesRepairableException, IllegalStateException, GooglePlayServicesNotAvailableException, IOException {
                    C1028dm.m1609x("Calling this from your main thread can lead to deadlock");
                    ServiceConnectionC0887a serviceConnectionC0887aM996g = m996g(context);
                    try {
                        try {
                            InterfaceC1118p interfaceC1118pM2153b = InterfaceC1118p.a.m2153b(serviceConnectionC0887aM996g.m1028aG());
                            Info info = new Info(interfaceC1118pM2153b.getId(), interfaceC1118pM2153b.mo2150a(true));
                            context.unbindService(serviceConnectionC0887aM996g);
                            return info;
                        } catch (RemoteException e) {
                            Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                            throw new IOException("Remote exception");
                        } catch (InterruptedException e2) {
                            throw new IOException("Interrupted exception");
                        }
                    } catch (Throwable th) {
                        context.unbindService(serviceConnectionC0887aM996g);
                        throw th;
                    }
                }
            }
