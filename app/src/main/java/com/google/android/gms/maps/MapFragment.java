            package com.google.android.gms.maps;

            import android.app.Activity;
            import android.app.Fragment;
            import android.os.Bundle;
            import android.os.RemoteException;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.dynamic.AbstractC0903a;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0906d;
            import com.google.android.gms.dynamic.LifecycleDelegate;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.C1172p;
            import com.google.android.gms.maps.internal.C1173q;
            import com.google.android.gms.maps.internal.IGoogleMapDelegate;
            import com.google.android.gms.maps.internal.IMapFragmentDelegate;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            
            
            public class MapFragment extends Fragment {

                
                private final C1144b f2234pH = new C1144b(this);

                
                private GoogleMap f2235pI;

                
                static class C1143a implements LifecycleDelegate {

                    
                    private final Fragment f2236pJ;

                    
                    private final IMapFragmentDelegate f2237pK;

                    public C1143a(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
                        this.f2237pK = (IMapFragmentDelegate) C1028dm.m1605e(iMapFragmentDelegate);
                        this.f2236pJ = (Fragment) C1028dm.m1605e(fragment);
                    }

                    
                    public IMapFragmentDelegate m2217cD() {
                        return this.f2237pK;
                    }

                    @Override 
                    public void onCreate(Bundle savedInstanceState) {
                        if (savedInstanceState == null) {
                            try {
                                savedInstanceState = new Bundle();
                            } catch (RemoteException e) {
                                throw new RuntimeRemoteException(e);
                            }
                        }
                        Bundle arguments = this.f2236pJ.getArguments();
                        if (arguments != null && arguments.containsKey("MapOptions")) {
                            C1172p.m2257a(savedInstanceState, "MapOptions", arguments.getParcelable("MapOptions"));
                        }
                        this.f2237pK.onCreate(savedInstanceState);
                    }

                    @Override 
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        try {
                            return (View) BinderC0905c.m1165b(this.f2237pK.onCreateView(BinderC0905c.m1166g(inflater), BinderC0905c.m1166g(container), savedInstanceState));
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onDestroy() {
                        try {
                            this.f2237pK.onDestroy();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onDestroyView() {
                        try {
                            this.f2237pK.onDestroyView();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
                        try {
                            this.f2237pK.onInflate(BinderC0905c.m1166g(activity), (GoogleMapOptions) attrs.getParcelable("MapOptions"), savedInstanceState);
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onLowMemory() {
                        try {
                            this.f2237pK.onLowMemory();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onPause() {
                        try {
                            this.f2237pK.onPause();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onResume() {
                        try {
                            this.f2237pK.onResume();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onSaveInstanceState(Bundle outState) {
                        try {
                            this.f2237pK.onSaveInstanceState(outState);
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }
                }

                
                static class C1144b extends AbstractC0903a<C1143a> {

                    
                    private Activity f2238fD;

                    
                    private final Fragment f2239pJ;

                    
                    protected InterfaceC0906d<C1143a> f2240pL;

                    C1144b(Fragment fragment) {
                        this.f2239pJ = fragment;
                    }

                    
                    static  void m2218a(C1144b c1144b, Activity activity) {
                        c1144b.setActivity(activity);
                    }

                    private void setActivity(Activity activity) {
                        this.f2238fD = activity;
                        m2219cE();
                    }

                    @Override 
                    
                    protected void mo1160a(InterfaceC0906d<C1143a> interfaceC0906d) {
                        this.f2240pL = interfaceC0906d;
                        m2219cE();
                    }

                    
                    public void m2219cE() {
                        if (this.f2238fD == null || this.f2240pL == null || m1161bP() != null) {
                            return;
                        }
                        try {
                            MapsInitializer.initialize(this.f2238fD);
                            this.f2240pL.mo1162a(new C1143a(this.f2239pJ, C1173q.m2262u(this.f2238fD).mo2242f(BinderC0905c.m1166g(this.f2238fD))));
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        } catch (GooglePlayServicesNotAvailableException e2) {
                        }
                    }
                }

                public static MapFragment newInstance() {
                    return new MapFragment();
                }

                public static MapFragment newInstance(GoogleMapOptions options) {
                    MapFragment mapFragment = new MapFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("MapOptions", options);
                    mapFragment.setArguments(bundle);
                    return mapFragment;
                }

                
                protected IMapFragmentDelegate m2216cD() {
                    this.f2234pH.m2219cE();
                    if (this.f2234pH.m1161bP() == null) {
                        return null;
                    }
                    return this.f2234pH.m1161bP().m2217cD();
                }

                public final GoogleMap getMap() {
                    IMapFragmentDelegate iMapFragmentDelegateM2216cD = m2216cD();
                    if (iMapFragmentDelegateM2216cD == null) {
                        return null;
                    }
                    try {
                        IGoogleMapDelegate map = iMapFragmentDelegateM2216cD.getMap();
                        if (map == null) {
                            return null;
                        }
                        if (this.f2235pI == null || this.f2235pI.m2197cu().asBinder() != map.asBinder()) {
                            this.f2235pI = new GoogleMap(map);
                        }
                        return this.f2235pI;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                @Override 
                public void onActivityCreated(Bundle savedInstanceState) {
                    if (savedInstanceState != null) {
                        savedInstanceState.setClassLoader(MapFragment.class.getClassLoader());
                    }
                    super.onActivityCreated(savedInstanceState);
                }

                @Override 
                public void onAttach(Activity activity) {
                    super.onAttach(activity);
                    C1144b.m2218a(this.f2234pH, activity);
                }

                @Override 
                public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    this.f2234pH.onCreate(savedInstanceState);
                }

                @Override 
                public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    return this.f2234pH.onCreateView(inflater, container, savedInstanceState);
                }

                @Override 
                public void onDestroy() {
                    this.f2234pH.onDestroy();
                    super.onDestroy();
                }

                @Override 
                public void onDestroyView() {
                    this.f2234pH.onDestroyView();
                    super.onDestroyView();
                }

                @Override 
                public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
                    super.onInflate(activity, attrs, savedInstanceState);
                    C1144b.m2218a(this.f2234pH, activity);
                    GoogleMapOptions googleMapOptionsCreateFromAttributes = GoogleMapOptions.createFromAttributes(activity, attrs);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("MapOptions", googleMapOptionsCreateFromAttributes);
                    this.f2234pH.onInflate(activity, bundle, savedInstanceState);
                }

                @Override 
                public void onLowMemory() {
                    this.f2234pH.onLowMemory();
                    super.onLowMemory();
                }

                @Override 
                public void onPause() {
                    this.f2234pH.onPause();
                    super.onPause();
                }

                @Override 
                public void onResume() {
                    super.onResume();
                    this.f2234pH.onResume();
                }

                @Override 
                public void onSaveInstanceState(Bundle outState) {
                    if (outState != null) {
                        outState.setClassLoader(MapFragment.class.getClassLoader());
                    }
                    super.onSaveInstanceState(outState);
                    this.f2234pH.onSaveInstanceState(outState);
                }

                @Override 
                public void setArguments(Bundle args) {
                    super.setArguments(args);
                }
            }
