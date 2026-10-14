            package com.google.android.gms.maps.model;

            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.internal.InterfaceC1188b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class Circle {

                /* JADX INFO: renamed from: qe */
                private final InterfaceC1188b f2289qe;

                public Circle(InterfaceC1188b delegate) {
                    this.f2289qe = (InterfaceC1188b) C1028dm.m1605e(delegate);
                }

                public boolean equals(Object other) {
                    if (!(other instanceof Circle)) {
                        return false;
                    }
                    try {
                        return this.f2289qe.mo2313a(((Circle) other).f2289qe);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public LatLng getCenter() {
                    try {
                        return this.f2289qe.getCenter();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int getFillColor() {
                    try {
                        return this.f2289qe.getFillColor();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getId() {
                    try {
                        return this.f2289qe.getId();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public double getRadius() {
                    try {
                        return this.f2289qe.getRadius();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int getStrokeColor() {
                    try {
                        return this.f2289qe.getStrokeColor();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getStrokeWidth() {
                    try {
                        return this.f2289qe.getStrokeWidth();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getZIndex() {
                    try {
                        return this.f2289qe.getZIndex();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int hashCode() {
                    try {
                        return this.f2289qe.hashCodeRemote();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isVisible() {
                    try {
                        return this.f2289qe.isVisible();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void remove() {
                    try {
                        this.f2289qe.remove();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setCenter(LatLng center) {
                    try {
                        this.f2289qe.setCenter(center);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setFillColor(int color) {
                    try {
                        this.f2289qe.setFillColor(color);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setRadius(double radius) {
                    try {
                        this.f2289qe.setRadius(radius);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setStrokeColor(int color) {
                    try {
                        this.f2289qe.setStrokeColor(color);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setStrokeWidth(float width) {
                    try {
                        this.f2289qe.setStrokeWidth(width);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setVisible(boolean visible) {
                    try {
                        this.f2289qe.setVisible(visible);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setZIndex(float zIndex) {
                    try {
                        this.f2289qe.setZIndex(zIndex);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
