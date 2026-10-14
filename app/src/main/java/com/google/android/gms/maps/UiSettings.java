            package com.google.android.gms.maps;

            import android.os.RemoteException;
            import com.google.android.gms.maps.internal.IUiSettingsDelegate;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class UiSettings {

                /* JADX INFO: renamed from: pV */
                private final IUiSettingsDelegate f2257pV;

                UiSettings(IUiSettingsDelegate delegate) {
                    this.f2257pV = delegate;
                }

                public boolean isCompassEnabled() {
                    try {
                        return this.f2257pV.isCompassEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isMyLocationButtonEnabled() {
                    try {
                        return this.f2257pV.isMyLocationButtonEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isRotateGesturesEnabled() {
                    try {
                        return this.f2257pV.isRotateGesturesEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isScrollGesturesEnabled() {
                    try {
                        return this.f2257pV.isScrollGesturesEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isTiltGesturesEnabled() {
                    try {
                        return this.f2257pV.isTiltGesturesEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isZoomControlsEnabled() {
                    try {
                        return this.f2257pV.isZoomControlsEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public boolean isZoomGesturesEnabled() {
                    try {
                        return this.f2257pV.isZoomGesturesEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setAllGesturesEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setAllGesturesEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setCompassEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setCompassEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setMyLocationButtonEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setMyLocationButtonEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setRotateGesturesEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setRotateGesturesEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setScrollGesturesEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setScrollGesturesEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setTiltGesturesEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setTiltGesturesEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setZoomControlsEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setZoomControlsEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setZoomGesturesEnabled(boolean enabled) {
                    try {
                        this.f2257pV.setZoomGesturesEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
