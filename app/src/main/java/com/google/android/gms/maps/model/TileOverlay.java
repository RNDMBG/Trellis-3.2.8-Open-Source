            package com.google.android.gms.maps.model;

            import android.os.RemoteException;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.internal.InterfaceC1192f;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class TileOverlay {

                /* JADX INFO: renamed from: qO */
                private final InterfaceC1192f f2350qO;

                public TileOverlay(InterfaceC1192f delegate) {
                    this.f2350qO = (InterfaceC1192f) C1028dm.m1605e(delegate);
                }

                public void clearTileCache() {
                    try {
                        this.f2350qO.clearTileCache();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean equals(Object other) {
                    if (!(other instanceof TileOverlay)) {
                        return false;
                    }
                    try {
                        return this.f2350qO.mo2324a(((TileOverlay) other).f2350qO);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public String getId() {
                    try {
                        return this.f2350qO.getId();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public float getZIndex() {
                    try {
                        return this.f2350qO.getZIndex();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public int hashCode() {
                    try {
                        return this.f2350qO.hashCodeRemote();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isVisible() {
                    try {
                        return this.f2350qO.isVisible();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void remove() {
                    try {
                        this.f2350qO.remove();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setVisible(boolean visible) {
                    try {
                        this.f2350qO.setVisible(visible);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setZIndex(float zIndex) {
                    try {
                        this.f2350qO.setZIndex(zIndex);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
