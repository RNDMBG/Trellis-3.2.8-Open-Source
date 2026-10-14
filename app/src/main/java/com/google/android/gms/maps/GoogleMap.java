            package com.google.android.gms.maps;

            import android.graphics.Bitmap;
            import android.location.Location;
            import android.os.RemoteException;
            import android.view.View;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.IGoogleMapDelegate;
            import com.google.android.gms.maps.internal.ILocationSourceDelegate;
            import com.google.android.gms.maps.internal.InterfaceC1158b;
            import com.google.android.gms.maps.internal.InterfaceC1160d;
            import com.google.android.gms.maps.internal.InterfaceC1161e;
            import com.google.android.gms.maps.internal.InterfaceC1162f;
            import com.google.android.gms.maps.internal.InterfaceC1163g;
            import com.google.android.gms.maps.internal.InterfaceC1164h;
            import com.google.android.gms.maps.internal.InterfaceC1165i;
            import com.google.android.gms.maps.internal.InterfaceC1166j;
            import com.google.android.gms.maps.internal.InterfaceC1167k;
            import com.google.android.gms.maps.internal.InterfaceC1168l;
            import com.google.android.gms.maps.internal.InterfaceC1169m;
            import com.google.android.gms.maps.internal.InterfaceC1170n;
            import com.google.android.gms.maps.internal.InterfaceC1171o;
            import com.google.android.gms.maps.model.CameraPosition;
            import com.google.android.gms.maps.model.Circle;
            import com.google.android.gms.maps.model.CircleOptions;
            import com.google.android.gms.maps.model.GroundOverlay;
            import com.google.android.gms.maps.model.GroundOverlayOptions;
            import com.google.android.gms.maps.model.LatLng;
            import com.google.android.gms.maps.model.Marker;
            import com.google.android.gms.maps.model.MarkerOptions;
            import com.google.android.gms.maps.model.Polygon;
            import com.google.android.gms.maps.model.PolygonOptions;
            import com.google.android.gms.maps.model.Polyline;
            import com.google.android.gms.maps.model.PolylineOptions;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            import com.google.android.gms.maps.model.TileOverlay;
            import com.google.android.gms.maps.model.TileOverlayOptions;
            import com.google.android.gms.maps.model.internal.InterfaceC1189c;
            import com.google.android.gms.maps.model.internal.InterfaceC1190d;
            import com.google.android.gms.maps.model.internal.InterfaceC1192f;
            
            
            public final class GoogleMap {
                public static final int MAP_TYPE_HYBRID = 4;
                public static final int MAP_TYPE_NONE = 0;
                public static final int MAP_TYPE_NORMAL = 1;
                public static final int MAP_TYPE_SATELLITE = 2;
                public static final int MAP_TYPE_TERRAIN = 3;

                
                private final IGoogleMapDelegate f2194pf;

                
                private UiSettings f2195pg;

                
                class BinderC11301 extends ILocationSourceDelegate.AbstractBinderC1152a {

                    
                    final  LocationSource f2196ph;

                    
                    class AnonymousClass1 implements LocationSource.OnLocationChangedListener {

                        
                        final  InterfaceC1163g f2198pj;

                        AnonymousClass1(InterfaceC1163g interfaceC1163g) {
                            this.f2198pj = interfaceC1163g;
                        }

                        @Override 
                        public void onLocationChanged(Location location) {
                            try {
                                this.f2198pj.mo2247g(BinderC0905c.m1166g(location));
                            } catch (RemoteException e) {
                                throw new RuntimeRemoteException(e);
                            }
                        }
                    }

                    BinderC11301(LocationSource locationSource) {
                        this.f2196ph = locationSource;
                    }

                    @Override 
                    public void activate(InterfaceC1163g listener) {
                        this.f2196ph.activate(new AnonymousClass1(listener));
                    }

                    @Override 
                    public void deactivate() {
                        this.f2196ph.deactivate();
                    }
                }

                
                class BinderC113110 extends InterfaceC1162f.a {

                    
                    final  OnInfoWindowClickListener f2201pt;

                    BinderC113110(OnInfoWindowClickListener onInfoWindowClickListener) {
                        this.f2201pt = onInfoWindowClickListener;
                    }

                    @Override 
                    
                    public void mo2198e(InterfaceC1190d interfaceC1190d) {
                        this.f2201pt.onInfoWindowClick(new Marker(interfaceC1190d));
                    }
                }

                
                class BinderC113211 extends InterfaceC1160d.a {

                    
                    final  InfoWindowAdapter f2203pu;

                    BinderC113211(InfoWindowAdapter infoWindowAdapter) {
                        this.f2203pu = infoWindowAdapter;
                    }

                    @Override 
                    
                    public InterfaceC0904b mo2199f(InterfaceC1190d interfaceC1190d) {
                        return BinderC0905c.m1166g(this.f2203pu.getInfoWindow(new Marker(interfaceC1190d)));
                    }

                    @Override 
                    
                    public InterfaceC0904b mo2200g(InterfaceC1190d interfaceC1190d) {
                        return BinderC0905c.m1166g(this.f2203pu.getInfoContents(new Marker(interfaceC1190d)));
                    }
                }

                
                class BinderC113312 extends InterfaceC1170n.a {

                    
                    final  OnMyLocationChangeListener f2205pv;

                    BinderC113312(OnMyLocationChangeListener onMyLocationChangeListener) {
                        this.f2205pv = onMyLocationChangeListener;
                    }

                    @Override 
                    
                    public void mo2201d(InterfaceC0904b interfaceC0904b) {
                        this.f2205pv.onMyLocationChange((Location) BinderC0905c.m1165b(interfaceC0904b));
                    }
                }

                
                class BinderC11342 extends InterfaceC1169m.a {

                    
                    final  OnMyLocationButtonClickListener f2207pl;

                    BinderC11342(OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
                        this.f2207pl = onMyLocationButtonClickListener;
                    }

                    @Override 
                    public boolean onMyLocationButtonClick() throws RemoteException {
                        return this.f2207pl.onMyLocationButtonClick();
                    }
                }

                
                class BinderC11353 extends InterfaceC1165i.a {

                    
                    final  OnMapLoadedCallback f2209pm;

                    BinderC11353(OnMapLoadedCallback onMapLoadedCallback) {
                        this.f2209pm = onMapLoadedCallback;
                    }

                    @Override 
                    public void onMapLoaded() throws RemoteException {
                        this.f2209pm.onMapLoaded();
                    }
                }

                
                class BinderC11364 extends InterfaceC1171o.a {

                    
                    final  SnapshotReadyCallback f2211pn;

                    BinderC11364(SnapshotReadyCallback snapshotReadyCallback) {
                        this.f2211pn = snapshotReadyCallback;
                    }

                    @Override 
                    
                    public void mo2202c(InterfaceC0904b interfaceC0904b) throws RemoteException {
                        this.f2211pn.onSnapshotReady((Bitmap) BinderC0905c.m1165b(interfaceC0904b));
                    }

                    @Override 
                    public void onSnapshotReady(Bitmap snapshot) throws RemoteException {
                        this.f2211pn.onSnapshotReady(snapshot);
                    }
                }

                
                class BinderC11375 extends InterfaceC1161e.a {

                    
                    final  OnCameraChangeListener f2213po;

                    BinderC11375(OnCameraChangeListener onCameraChangeListener) {
                        this.f2213po = onCameraChangeListener;
                    }

                    @Override 
                    public void onCameraChange(CameraPosition position) {
                        this.f2213po.onCameraChange(position);
                    }
                }

                
                class BinderC11386 extends InterfaceC1164h.a {

                    
                    final  OnMapClickListener f2215pp;

                    BinderC11386(OnMapClickListener onMapClickListener) {
                        this.f2215pp = onMapClickListener;
                    }

                    @Override 
                    public void onMapClick(LatLng point) {
                        this.f2215pp.onMapClick(point);
                    }
                }

                
                class BinderC11397 extends InterfaceC1166j.a {

                    
                    final  OnMapLongClickListener f2217pq;

                    BinderC11397(OnMapLongClickListener onMapLongClickListener) {
                        this.f2217pq = onMapLongClickListener;
                    }

                    @Override 
                    public void onMapLongClick(LatLng point) {
                        this.f2217pq.onMapLongClick(point);
                    }
                }

                
                class BinderC11408 extends InterfaceC1167k.a {

                    
                    final  OnMarkerClickListener f2219pr;

                    BinderC11408(OnMarkerClickListener onMarkerClickListener) {
                        this.f2219pr = onMarkerClickListener;
                    }

                    @Override 
                    
                    public boolean mo2203a(InterfaceC1190d interfaceC1190d) {
                        return this.f2219pr.onMarkerClick(new Marker(interfaceC1190d));
                    }
                }

                
                class BinderC11419 extends InterfaceC1168l.a {

                    
                    final  OnMarkerDragListener f2221ps;

                    BinderC11419(OnMarkerDragListener onMarkerDragListener) {
                        this.f2221ps = onMarkerDragListener;
                    }

                    @Override 
                    
                    public void mo2204b(InterfaceC1190d interfaceC1190d) {
                        this.f2221ps.onMarkerDragStart(new Marker(interfaceC1190d));
                    }

                    @Override 
                    
                    public void mo2205c(InterfaceC1190d interfaceC1190d) {
                        this.f2221ps.onMarkerDragEnd(new Marker(interfaceC1190d));
                    }

                    @Override 
                    
                    public void mo2206d(InterfaceC1190d interfaceC1190d) {
                        this.f2221ps.onMarkerDrag(new Marker(interfaceC1190d));
                    }
                }

                public interface CancelableCallback {
                    void onCancel();

                    void onFinish();
                }

                public interface InfoWindowAdapter {
                    View getInfoContents(Marker marker);

                    View getInfoWindow(Marker marker);
                }

                public interface OnCameraChangeListener {
                    void onCameraChange(CameraPosition cameraPosition);
                }

                public interface OnInfoWindowClickListener {
                    void onInfoWindowClick(Marker marker);
                }

                public interface OnMapClickListener {
                    void onMapClick(LatLng latLng);
                }

                public interface OnMapLoadedCallback {
                    void onMapLoaded();
                }

                public interface OnMapLongClickListener {
                    void onMapLongClick(LatLng latLng);
                }

                public interface OnMarkerClickListener {
                    boolean onMarkerClick(Marker marker);
                }

                public interface OnMarkerDragListener {
                    void onMarkerDrag(Marker marker);

                    void onMarkerDragEnd(Marker marker);

                    void onMarkerDragStart(Marker marker);
                }

                public interface OnMyLocationButtonClickListener {
                    boolean onMyLocationButtonClick();
                }

                @Deprecated
                public interface OnMyLocationChangeListener {
                    void onMyLocationChange(Location location);
                }

                public interface SnapshotReadyCallback {
                    void onSnapshotReady(Bitmap bitmap);
                }

                
                private static final class BinderC1142a extends InterfaceC1158b.a {

                    
                    private final CancelableCallback f2222pw;

                    BinderC1142a(CancelableCallback cancelableCallback) {
                        this.f2222pw = cancelableCallback;
                    }

                    @Override 
                    public void onCancel() {
                        this.f2222pw.onCancel();
                    }

                    @Override 
                    public void onFinish() {
                        this.f2222pw.onFinish();
                    }
                }

                protected GoogleMap(IGoogleMapDelegate map) {
                    this.f2194pf = (IGoogleMapDelegate) C1028dm.m1605e(map);
                }

                public final Circle addCircle(CircleOptions options) {
                    try {
                        return new Circle(this.f2194pf.addCircle(options));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final GroundOverlay addGroundOverlay(GroundOverlayOptions options) {
                    try {
                        InterfaceC1189c interfaceC1189cAddGroundOverlay = this.f2194pf.addGroundOverlay(options);
                        if (interfaceC1189cAddGroundOverlay != null) {
                            return new GroundOverlay(interfaceC1189cAddGroundOverlay);
                        }
                        return null;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final Marker addMarker(MarkerOptions options) {
                    try {
                        InterfaceC1190d interfaceC1190dAddMarker = this.f2194pf.addMarker(options);
                        if (interfaceC1190dAddMarker != null) {
                            return new Marker(interfaceC1190dAddMarker);
                        }
                        return null;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final Polygon addPolygon(PolygonOptions options) {
                    try {
                        return new Polygon(this.f2194pf.addPolygon(options));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final Polyline addPolyline(PolylineOptions options) {
                    try {
                        return new Polyline(this.f2194pf.addPolyline(options));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final TileOverlay addTileOverlay(TileOverlayOptions options) {
                    try {
                        InterfaceC1192f interfaceC1192fAddTileOverlay = this.f2194pf.addTileOverlay(options);
                        if (interfaceC1192fAddTileOverlay != null) {
                            return new TileOverlay(interfaceC1192fAddTileOverlay);
                        }
                        return null;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void animateCamera(CameraUpdate update) {
                    try {
                        this.f2194pf.animateCamera(update.m2194cs());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void animateCamera(CameraUpdate update, int durationMs, CancelableCallback callback) {
                    try {
                        this.f2194pf.animateCameraWithDurationAndCallback(update.m2194cs(), durationMs, callback == null ? null : new BinderC1142a(callback));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void animateCamera(CameraUpdate update, CancelableCallback callback) {
                    try {
                        this.f2194pf.animateCameraWithCallback(update.m2194cs(), callback == null ? null : new BinderC1142a(callback));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void clear() {
                    try {
                        this.f2194pf.clear();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                
                IGoogleMapDelegate m2197cu() {
                    return this.f2194pf;
                }

                public final CameraPosition getCameraPosition() {
                    try {
                        return this.f2194pf.getCameraPosition();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final int getMapType() {
                    try {
                        return this.f2194pf.getMapType();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final float getMaxZoomLevel() {
                    try {
                        return this.f2194pf.getMaxZoomLevel();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final float getMinZoomLevel() {
                    try {
                        return this.f2194pf.getMinZoomLevel();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                @Deprecated
                public final Location getMyLocation() {
                    try {
                        return this.f2194pf.getMyLocation();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final Projection getProjection() {
                    try {
                        return new Projection(this.f2194pf.getProjection());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final UiSettings getUiSettings() {
                    try {
                        if (this.f2195pg == null) {
                            this.f2195pg = new UiSettings(this.f2194pf.getUiSettings());
                        }
                        return this.f2195pg;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final boolean isBuildingsEnabled() {
                    try {
                        return this.f2194pf.isBuildingsEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final boolean isIndoorEnabled() {
                    try {
                        return this.f2194pf.isIndoorEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final boolean isMyLocationEnabled() {
                    try {
                        return this.f2194pf.isMyLocationEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final boolean isTrafficEnabled() {
                    try {
                        return this.f2194pf.isTrafficEnabled();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void moveCamera(CameraUpdate update) {
                    try {
                        this.f2194pf.moveCamera(update.m2194cs());
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setBuildingsEnabled(boolean enabled) {
                    try {
                        this.f2194pf.setBuildingsEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final boolean setIndoorEnabled(boolean enabled) {
                    try {
                        return this.f2194pf.setIndoorEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setInfoWindowAdapter(InfoWindowAdapter adapter) {
                    try {
                        if (adapter == null) {
                            this.f2194pf.setInfoWindowAdapter(null);
                        } else {
                            this.f2194pf.setInfoWindowAdapter(new BinderC113211(adapter));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setLocationSource(LocationSource source) {
                    try {
                        if (source == null) {
                            this.f2194pf.setLocationSource(null);
                        } else {
                            this.f2194pf.setLocationSource(new BinderC11301(source));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setMapType(int type) {
                    try {
                        this.f2194pf.setMapType(type);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setMyLocationEnabled(boolean enabled) {
                    try {
                        this.f2194pf.setMyLocationEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setOnCameraChangeListener(OnCameraChangeListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnCameraChangeListener(null);
                        } else {
                            this.f2194pf.setOnCameraChangeListener(new BinderC11375(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setOnInfoWindowClickListener(OnInfoWindowClickListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnInfoWindowClickListener(null);
                        } else {
                            this.f2194pf.setOnInfoWindowClickListener(new BinderC113110(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setOnMapClickListener(OnMapClickListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnMapClickListener(null);
                        } else {
                            this.f2194pf.setOnMapClickListener(new BinderC11386(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public void setOnMapLoadedCallback(OnMapLoadedCallback callback) {
                    try {
                        if (callback == null) {
                            this.f2194pf.setOnMapLoadedCallback(null);
                        } else {
                            this.f2194pf.setOnMapLoadedCallback(new BinderC11353(callback));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setOnMapLongClickListener(OnMapLongClickListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnMapLongClickListener(null);
                        } else {
                            this.f2194pf.setOnMapLongClickListener(new BinderC11397(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setOnMarkerClickListener(OnMarkerClickListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnMarkerClickListener(null);
                        } else {
                            this.f2194pf.setOnMarkerClickListener(new BinderC11408(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setOnMarkerDragListener(OnMarkerDragListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnMarkerDragListener(null);
                        } else {
                            this.f2194pf.setOnMarkerDragListener(new BinderC11419(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnMyLocationButtonClickListener(null);
                        } else {
                            this.f2194pf.setOnMyLocationButtonClickListener(new BinderC11342(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                @Deprecated
                public final void setOnMyLocationChangeListener(OnMyLocationChangeListener listener) {
                    try {
                        if (listener == null) {
                            this.f2194pf.setOnMyLocationChangeListener(null);
                        } else {
                            this.f2194pf.setOnMyLocationChangeListener(new BinderC113312(listener));
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setPadding(int left, int top, int right, int bottom) {
                    try {
                        this.f2194pf.setPadding(left, top, right, bottom);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void setTrafficEnabled(boolean enabled) {
                    try {
                        this.f2194pf.setTrafficEnabled(enabled);
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void snapshot(SnapshotReadyCallback callback) {
                    snapshot(callback, null);
                }

                public final void snapshot(SnapshotReadyCallback callback, Bitmap bitmap) {
                    try {
                        this.f2194pf.snapshot(new BinderC11364(callback), (BinderC0905c) (bitmap != null ? BinderC0905c.m1166g(bitmap) : null));
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void stopAnimation() {
                    try {
                        this.f2194pf.stopAnimation();
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }
            }
