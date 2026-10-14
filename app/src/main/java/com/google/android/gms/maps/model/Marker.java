            package com.google.android.gms.maps.model;

            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.internal.InterfaceC1190d;
            
            
            public final class Marker {

                
                private final InterfaceC1190d f2317qz;

                public Marker(InterfaceC1190d delegate) {
                    this.f2317qz = (InterfaceC1190d) C1028dm.m1605e(delegate);
                }

                public boolean equals(Object other) {
                    if (!(other instanceof Marker)) {
                        return false;
                    }
                    try {
                        return this.f2317qz.mo2319h(((Marker) other).f2317qz);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getAlpha() {
                    try {
                        return this.f2317qz.getAlpha();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getId() {
                    try {
                        return this.f2317qz.getId();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public LatLng getPosition() {
                    try {
                        return this.f2317qz.getPosition();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getRotation() {
                    try {
                        return this.f2317qz.getRotation();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getSnippet() {
                    try {
                        return this.f2317qz.getSnippet();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getTitle() {
                    try {
                        return this.f2317qz.getTitle();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int hashCode() {
                    try {
                        return this.f2317qz.hashCodeRemote();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void hideInfoWindow() {
                    try {
                        this.f2317qz.hideInfoWindow();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isDraggable() {
                    try {
                        return this.f2317qz.isDraggable();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isFlat() {
                    try {
                        return this.f2317qz.isFlat();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isInfoWindowShown() {
                    try {
                        return this.f2317qz.isInfoWindowShown();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isVisible() {
                    try {
                        return this.f2317qz.isVisible();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void remove() {
                    try {
                        this.f2317qz.remove();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setAlpha(float alpha) {
                    try {
                        this.f2317qz.setAlpha(alpha);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setAnchor(float anchorU, float anchorV) {
                    try {
                        this.f2317qz.setAnchor(anchorU, anchorV);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setDraggable(boolean draggable) {
                    try {
                        this.f2317qz.setDraggable(draggable);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setFlat(boolean flat) {
                    try {
                        this.f2317qz.setFlat(flat);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setIcon(BitmapDescriptor icon) {
                    try {
                        this.f2317qz.mo2320i(icon.m2266cs());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setInfoWindowAnchor(float anchorU, float anchorV) {
                    try {
                        this.f2317qz.setInfoWindowAnchor(anchorU, anchorV);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setPosition(LatLng latlng) {
                    try {
                        this.f2317qz.setPosition(latlng);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setRotation(float rotation) {
                    try {
                        this.f2317qz.setRotation(rotation);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setSnippet(String snippet) {
                    try {
                        this.f2317qz.setSnippet(snippet);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setTitle(String title) {
                    try {
                        this.f2317qz.setTitle(title);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setVisible(boolean visible) {
                    try {
                        this.f2317qz.setVisible(visible);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void showInfoWindow() {
                    try {
                        this.f2317qz.showInfoWindow();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
