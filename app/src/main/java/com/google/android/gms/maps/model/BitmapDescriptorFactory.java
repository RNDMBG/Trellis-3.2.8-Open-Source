            package com.google.android.gms.maps.model;

            import android.graphics.Bitmap;
            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.internal.InterfaceC1187a;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class BitmapDescriptorFactory {
                public static final float HUE_AZURE = 210.0f;
                public static final float HUE_BLUE = 240.0f;
                public static final float HUE_CYAN = 180.0f;
                public static final float HUE_GREEN = 120.0f;
                public static final float HUE_MAGENTA = 300.0f;
                public static final float HUE_ORANGE = 30.0f;
                public static final float HUE_RED = 0.0f;
                public static final float HUE_ROSE = 330.0f;
                public static final float HUE_VIOLET = 270.0f;
                public static final float HUE_YELLOW = 60.0f;

                /* JADX INFO: renamed from: pZ */
                private static InterfaceC1187a f2283pZ;

                private BitmapDescriptorFactory() {
                }

                /* JADX INFO: renamed from: a */
                public static void m2267a(InterfaceC1187a interfaceC1187a) {
                    if (f2283pZ != null) {
                        return;
                    }
                    f2283pZ = (InterfaceC1187a) C1028dm.m1605e(interfaceC1187a);
                }

                /* JADX INFO: renamed from: cL */
                private static InterfaceC1187a m2268cL() {
                    return (InterfaceC1187a) C1028dm.m1601a(f2283pZ, "IBitmapDescriptorFactory is not initialized");
                }

                public static BitmapDescriptor defaultMarker() {
                    try {
                        return new BitmapDescriptor(m2268cL().mo2311cQ());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static BitmapDescriptor defaultMarker(float hue) {
                    try {
                        return new BitmapDescriptor(m2268cL().mo2310c(hue));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static BitmapDescriptor fromAsset(String assetName) {
                    try {
                        return new BitmapDescriptor(m2268cL().mo2305S(assetName));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static BitmapDescriptor fromBitmap(Bitmap image) {
                    try {
                        return new BitmapDescriptor(m2268cL().mo2308a(image));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static BitmapDescriptor fromFile(String fileName) {
                    try {
                        return new BitmapDescriptor(m2268cL().mo2306T(fileName));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static BitmapDescriptor fromPath(String absolutePath) {
                    try {
                        return new BitmapDescriptor(m2268cL().mo2307U(absolutePath));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static BitmapDescriptor fromResource(int resourceId) {
                    try {
                        return new BitmapDescriptor(m2268cL().mo2309ad(resourceId));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
