            package com.google.android.gms.maps;

            import android.graphics.Point;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.maps.internal.IProjectionDelegate;
            import com.google.android.gms.maps.model.LatLng;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            import com.google.android.gms.maps.model.VisibleRegion;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class Projection {

                /* JADX INFO: renamed from: pS */
                private final IProjectionDelegate f2249pS;

                Projection(IProjectionDelegate delegate) {
                    this.f2249pS = delegate;
                }

                public LatLng fromScreenLocation(Point point) {
                    try {
                        return this.f2249pS.fromScreenLocation(BinderC0905c.m1166g(point));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public VisibleRegion getVisibleRegion() {
                    try {
                        return this.f2249pS.getVisibleRegion();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public Point toScreenLocation(LatLng location) {
                    try {
                        return (Point) BinderC0905c.m1165b(this.f2249pS.toScreenLocation(location));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
