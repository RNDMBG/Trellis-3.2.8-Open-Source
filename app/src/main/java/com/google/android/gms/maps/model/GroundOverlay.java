            package com.google.android.gms.maps.model;

            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.internal.InterfaceC1189c;
            
            
            public final class GroundOverlay {

                
                private final InterfaceC1189c f2298qm;

                public GroundOverlay(InterfaceC1189c delegate) {
                    this.f2298qm = (InterfaceC1189c) C1028dm.m1605e(delegate);
                }

                public boolean equals(Object other) {
                    if (!(other instanceof GroundOverlay)) {
                        return false;
                    }
                    try {
                        return this.f2298qm.mo2316a(((GroundOverlay) other).f2298qm);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getBearing() {
                    try {
                        return this.f2298qm.getBearing();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public LatLngBounds getBounds() {
                    try {
                        return this.f2298qm.getBounds();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getHeight() {
                    try {
                        return this.f2298qm.getHeight();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getId() {
                    try {
                        return this.f2298qm.getId();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public LatLng getPosition() {
                    try {
                        return this.f2298qm.getPosition();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getTransparency() {
                    try {
                        return this.f2298qm.getTransparency();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getWidth() {
                    try {
                        return this.f2298qm.getWidth();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getZIndex() {
                    try {
                        return this.f2298qm.getZIndex();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int hashCode() {
                    try {
                        return this.f2298qm.hashCodeRemote();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isVisible() {
                    try {
                        return this.f2298qm.isVisible();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void remove() {
                    try {
                        this.f2298qm.remove();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setBearing(float bearing) {
                    try {
                        this.f2298qm.setBearing(bearing);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setDimensions(float width) {
                    try {
                        this.f2298qm.setDimensions(width);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setDimensions(float width, float height) {
                    try {
                        this.f2298qm.mo2315a(width, height);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setImage(BitmapDescriptor icon) {
                    try {
                        this.f2298qm.mo2317h(icon.m2266cs());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setPosition(LatLng latLng) {
                    try {
                        this.f2298qm.setPosition(latLng);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setPositionFromBounds(LatLngBounds bounds) {
                    try {
                        this.f2298qm.setPositionFromBounds(bounds);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setTransparency(float transparency) {
                    try {
                        this.f2298qm.setTransparency(transparency);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setVisible(boolean visible) {
                    try {
                        this.f2298qm.setVisible(visible);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setZIndex(float zIndex) {
                    try {
                        this.f2298qm.setZIndex(zIndex);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
