            package com.google.android.gms.maps;

            import android.graphics.Point;
            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
            import com.google.android.gms.maps.model.CameraPosition;
            import com.google.android.gms.maps.model.LatLng;
            import com.google.android.gms.maps.model.LatLngBounds;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            
            
            public final class CameraUpdateFactory {

                
                private static ICameraUpdateFactoryDelegate f2193pe;

                private CameraUpdateFactory() {
                }

                
                static void m2195a(ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegate) {
                    if (f2193pe != null) {
                        return;
                    }
                    f2193pe = (ICameraUpdateFactoryDelegate) C1028dm.m1605e(iCameraUpdateFactoryDelegate);
                }

                
                private static ICameraUpdateFactoryDelegate m2196ct() {
                    return (ICameraUpdateFactoryDelegate) C1028dm.m1601a(f2193pe, "CameraUpdateFactory is not initialized");
                }

                public static CameraUpdate newCameraPosition(CameraPosition cameraPosition) {
                    try {
                        return new CameraUpdate(m2196ct().newCameraPosition(cameraPosition));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate newLatLng(LatLng latLng) {
                    try {
                        return new CameraUpdate(m2196ct().newLatLng(latLng));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate newLatLngBounds(LatLngBounds bounds, int padding) {
                    try {
                        return new CameraUpdate(m2196ct().newLatLngBounds(bounds, padding));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate newLatLngBounds(LatLngBounds bounds, int width, int height, int padding) {
                    try {
                        return new CameraUpdate(m2196ct().newLatLngBoundsWithSize(bounds, width, height, padding));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate newLatLngZoom(LatLng latLng, float zoom) {
                    try {
                        return new CameraUpdate(m2196ct().newLatLngZoom(latLng, zoom));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate scrollBy(float xPixel, float yPixel) {
                    try {
                        return new CameraUpdate(m2196ct().scrollBy(xPixel, yPixel));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate zoomBy(float amount) {
                    try {
                        return new CameraUpdate(m2196ct().zoomBy(amount));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate zoomBy(float amount, Point focus) {
                    try {
                        return new CameraUpdate(m2196ct().zoomByWithFocus(amount, focus.x, focus.y));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate zoomIn() {
                    try {
                        return new CameraUpdate(m2196ct().zoomIn());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate zoomOut() {
                    try {
                        return new CameraUpdate(m2196ct().zoomOut());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public static CameraUpdate zoomTo(float zoom) {
                    try {
                        return new CameraUpdate(m2196ct().zoomTo(zoom));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
