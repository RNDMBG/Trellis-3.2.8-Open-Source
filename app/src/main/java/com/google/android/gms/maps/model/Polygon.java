            package com.google.android.gms.maps.model;

            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.internal.InterfaceC1191e;
            import java.util.List;
            
            
            public final class Polygon {

                
                private final InterfaceC1191e f2331qJ;

                public Polygon(InterfaceC1191e delegate) {
                    this.f2331qJ = (InterfaceC1191e) C1028dm.m1605e(delegate);
                }

                public boolean equals(Object other) {
                    if (!(other instanceof Polygon)) {
                        return false;
                    }
                    try {
                        return this.f2331qJ.mo2322a(((Polygon) other).f2331qJ);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int getFillColor() {
                    try {
                        return this.f2331qJ.getFillColor();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public List<List<LatLng>> getHoles() {
                    try {
                        return this.f2331qJ.getHoles();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getId() {
                    try {
                        return this.f2331qJ.getId();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public List<LatLng> getPoints() {
                    try {
                        return this.f2331qJ.getPoints();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int getStrokeColor() {
                    try {
                        return this.f2331qJ.getStrokeColor();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getStrokeWidth() {
                    try {
                        return this.f2331qJ.getStrokeWidth();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getZIndex() {
                    try {
                        return this.f2331qJ.getZIndex();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int hashCode() {
                    try {
                        return this.f2331qJ.hashCodeRemote();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isGeodesic() {
                    try {
                        return this.f2331qJ.isGeodesic();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isVisible() {
                    try {
                        return this.f2331qJ.isVisible();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void remove() {
                    try {
                        this.f2331qJ.remove();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setFillColor(int color) {
                    try {
                        this.f2331qJ.setFillColor(color);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setGeodesic(boolean geodesic) {
                    try {
                        this.f2331qJ.setGeodesic(geodesic);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setHoles(List<? extends List<LatLng>> holes) {
                    try {
                        this.f2331qJ.setHoles(holes);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setPoints(List<LatLng> points) {
                    try {
                        this.f2331qJ.setPoints(points);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setStrokeColor(int color) {
                    try {
                        this.f2331qJ.setStrokeColor(color);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setStrokeWidth(float width) {
                    try {
                        this.f2331qJ.setStrokeWidth(width);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setVisible(boolean visible) {
                    try {
                        this.f2331qJ.setVisible(visible);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setZIndex(float zIndex) {
                    try {
                        this.f2331qJ.setZIndex(zIndex);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
