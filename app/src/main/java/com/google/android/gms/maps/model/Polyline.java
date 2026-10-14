            package com.google.android.gms.maps.model;

            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.internal.IPolylineDelegate;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class Polyline {

                /* JADX INFO: renamed from: qN */
                private final IPolylineDelegate f2341qN;

                public Polyline(IPolylineDelegate delegate) {
                    this.f2341qN = (IPolylineDelegate) C1028dm.m1605e(delegate);
                }

                public boolean equals(Object other) {
                    if (!(other instanceof Polyline)) {
                        return false;
                    }
                    try {
                        return this.f2341qN.equalsRemote(((Polyline) other).f2341qN);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int getColor() {
                    try {
                        return this.f2341qN.getColor();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getId() {
                    try {
                        return this.f2341qN.getId();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public List<LatLng> getPoints() {
                    try {
                        return this.f2341qN.getPoints();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getWidth() {
                    try {
                        return this.f2341qN.getWidth();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getZIndex() {
                    try {
                        return this.f2341qN.getZIndex();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int hashCode() {
                    try {
                        return this.f2341qN.hashCodeRemote();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isGeodesic() {
                    try {
                        return this.f2341qN.isGeodesic();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isVisible() {
                    try {
                        return this.f2341qN.isVisible();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void remove() {
                    try {
                        this.f2341qN.remove();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setColor(int color) {
                    try {
                        this.f2341qN.setColor(color);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setGeodesic(boolean geodesic) {
                    try {
                        this.f2341qN.setGeodesic(geodesic);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setPoints(List<LatLng> points) {
                    try {
                        this.f2341qN.setPoints(points);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setVisible(boolean visible) {
                    try {
                        this.f2341qN.setVisible(visible);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setWidth(float width) {
                    try {
                        this.f2341qN.setWidth(width);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setZIndex(float zIndex) {
                    try {
                        this.f2341qN.setZIndex(zIndex);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
