            package com.google.android.gms.maps.model;

            import android.os.IBinder;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.maps.internal.C1174r;
            import com.google.android.gms.maps.model.internal.InterfaceC1193g;
            
            
            public final class TileOverlayOptions implements SafeParcelable {
                public static final TileOverlayOptionsCreator CREATOR = new TileOverlayOptionsCreator();

                
                private final int f2351iM;

                
                private InterfaceC1193g f2352qP;

                
                private TileProvider f2353qQ;

                
                private float f2354qk;

                
                private boolean f2355ql;

                
                class C11751 implements TileProvider {

                    
                    private final InterfaceC1193g f2356qR;

                    C11751() {
                        this.f2356qR = TileOverlayOptions.m2289a(TileOverlayOptions.this);
                    }

                    @Override 
                    public Tile getTile(int x, int y, int zoom) {
                        try {
                            return this.f2356qR.getTile(x, y, zoom);
                        } catch (RemoteException e) {
                            return null;
                        }
                    }
                }

                
                class BinderC11762 extends InterfaceC1193g.a {

                    
                    final  TileProvider f2359qT;

                    BinderC11762(TileProvider tileProvider) {
                        this.f2359qT = tileProvider;
                    }

                    @Override 
                    public Tile getTile(int x, int y, int zoom) {
                        return this.f2359qT.getTile(x, y, zoom);
                    }
                }

                public TileOverlayOptions() {
                    this.f2355ql = true;
                    this.f2351iM = 1;
                }

                TileOverlayOptions(int versionCode, IBinder delegate, boolean visible, float zIndex) {
                    this.f2355ql = true;
                    this.f2351iM = versionCode;
                    this.f2352qP = InterfaceC1193g.a.m2326aj(delegate);
                    this.f2353qQ = this.f2352qP == null ? null : new C11751();
                    this.f2355ql = visible;
                    this.f2354qk = zIndex;
                }

                
                static  InterfaceC1193g m2289a(TileOverlayOptions tileOverlayOptions) {
                    return tileOverlayOptions.f2352qP;
                }

                
                IBinder m2290cP() {
                    return this.f2352qP.asBinder();
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public TileProvider getTileProvider() {
                    return this.f2353qQ;
                }

                int getVersionCode() {
                    return this.f2351iM;
                }

                public float getZIndex() {
                    return this.f2354qk;
                }

                public boolean isVisible() {
                    return this.f2355ql;
                }

                public TileOverlayOptions tileProvider(TileProvider tileProvider) {
                    this.f2353qQ = tileProvider;
                    this.f2352qP = this.f2353qQ == null ? null : new BinderC11762(tileProvider);
                    return this;
                }

                public TileOverlayOptions visible(boolean visible) {
                    this.f2355ql = visible;
                    return this;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1194j.m2327a(this, out, flags);
                    } else {
                        TileOverlayOptionsCreator.m2291a(this, out, flags);
                    }
                }

                public TileOverlayOptions zIndex(float zIndex) {
                    this.f2354qk = zIndex;
                    return this;
                }
            }
