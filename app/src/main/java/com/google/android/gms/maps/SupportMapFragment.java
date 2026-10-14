            package com.google.android.gms.maps;

            import android.app.Activity;
            import android.os.Bundle;
            import android.os.RemoteException;
            import android.support.v4.app.Fragment;
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
            
            
            public class SupportMapFragment extends Fragment {

                
                private GoogleMap f2250pI;

                
                private final C1148b f2251pT = new C1148b(this);

                
                static class C1147a implements LifecycleDelegate {

                    
                    private final IMapFragmentDelegate f2252pK;

                    
                    private final Fragment f2253pU;

                    public C1147a(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
                        this.f2252pK = (IMapFragmentDelegate) C1028dm.m1605e(iMapFragmentDelegate);
                        this.f2253pU = (Fragment) C1028dm.m1605e(fragment);
                    }

                    
                    public IMapFragmentDelegate m2223cD() {
                        return this.f2252pK;
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
                        Bundle arguments = this.f2253pU.getArguments();
                        if (arguments != null && arguments.containsKey("MapOptions")) {
                            C1172p.m2257a(savedInstanceState, "MapOptions", arguments.getParcelable("MapOptions"));
                        }
                        this.f2252pK.onCreate(savedInstanceState);
                    }

                    @Override 
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        try {
                            return (View) BinderC0905c.m1165b(this.f2252pK.onCreateView(BinderC0905c.m1166g(inflater), BinderC0905c.m1166g(container), savedInstanceState));
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onDestroy() {
                        try {
                            this.f2252pK.onDestroy();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onDestroyView() {
                        try {
                            this.f2252pK.onDestroyView();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
                        try {
                            this.f2252pK.onInflate(BinderC0905c.m1166g(activity), (GoogleMapOptions) attrs.getParcelable("MapOptions"), savedInstanceState);
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onLowMemory() {
                        try {
                            this.f2252pK.onLowMemory();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onPause() {
                        try {
                            this.f2252pK.onPause();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onResume() {
                        try {
                            this.f2252pK.onResume();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onSaveInstanceState(Bundle outState) {
                        try {
                            this.f2252pK.onSaveInstanceState(outState);
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }
                }

                
                static class C1148b extends AbstractC0903a<C1147a> {

                    
                    private Activity f2254fD;

                    
                    protected InterfaceC0906d<C1147a> f2255pL;

                    
                    private final Fragment f2256pU;

                    C1148b(Fragment fragment) {
                        this.f2256pU = fragment;
                    }

                    
                    static  void m2224a(C1148b c1148b, Activity activity) {
                        c1148b.setActivity(activity);
                    }

                    private void setActivity(Activity activity) {
                        this.f2254fD = activity;
                        m2225cE();
                    }

                    @Override 
                    
                    protected void mo1160a(InterfaceC0906d<C1147a> interfaceC0906d) {
                        this.f2255pL = interfaceC0906d;
                        m2225cE();
                    }

                    
                    public void m2225cE() {
                        if (this.f2254fD == null || this.f2255pL == null || m1161bP() != null) {
                            return;
                        }
                        try {
                            MapsInitializer.initialize(this.f2254fD);
                            this.f2255pL.mo1162a(new C1147a(this.f2256pU, C1173q.m2262u(this.f2254fD).mo2242f(BinderC0905c.m1166g(this.f2254fD))));
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        } catch (GooglePlayServicesNotAvailableException e2) {
                        }
                    }
                }

                public static SupportMapFragment newInstance() {
                    return new SupportMapFragment();
                }

                public static SupportMapFragment newInstance(GoogleMapOptions options) {
                    SupportMapFragment supportMapFragment = new SupportMapFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("MapOptions", options);
                    supportMapFragment.setArguments(bundle);
                    return supportMapFragment;
                }

                
                protected IMapFragmentDelegate m2222cD() {
                    this.f2251pT.m2225cE();
                    if (this.f2251pT.m1161bP() == null) {
                        return null;
                    }
                    return this.f2251pT.m1161bP().m2223cD();
                }

                public final GoogleMap getMap() {
                    IMapFragmentDelegate iMapFragmentDelegateM2222cD = m2222cD();
                    if (iMapFragmentDelegateM2222cD == null) {
                        return null;
                    }
                    try {
                        IGoogleMapDelegate map = iMapFragmentDelegateM2222cD.getMap();
                        if (map == null) {
                            return null;
                        }
                        if (this.f2250pI == null || this.f2250pI.m2197cu().asBinder() != map.asBinder()) {
                            this.f2250pI = new GoogleMap(map);
                        }
                        return this.f2250pI;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                @Override 
                public void onActivityCreated(Bundle savedInstanceState) {
                    if (savedInstanceState != null) {
                        savedInstanceState.setClassLoader(SupportMapFragment.class.getClassLoader());
                    }
                    super.onActivityCreated(savedInstanceState);
                }

                @Override 
                public void onAttach(Activity activity) {
                    super.onAttach(activity);
                    C1148b.m2224a(this.f2251pT, activity);
                }

                @Override 
                public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    this.f2251pT.onCreate(savedInstanceState);
                }

                @Override 
                public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    return this.f2251pT.onCreateView(inflater, container, savedInstanceState);
                }

                @Override 
                public void onDestroy() {
                    this.f2251pT.onDestroy();
                    super.onDestroy();
                }

                @Override 
                public void onDestroyView() {
                    this.f2251pT.onDestroyView();
                    super.onDestroyView();
                }

                @Override 
                public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
                    super.onInflate(activity, attrs, savedInstanceState);
                    C1148b.m2224a(this.f2251pT, activity);
                    GoogleMapOptions googleMapOptionsCreateFromAttributes = GoogleMapOptions.createFromAttributes(activity, attrs);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("MapOptions", googleMapOptionsCreateFromAttributes);
                    this.f2251pT.onInflate(activity, bundle, savedInstanceState);
                }

                @Override 
                public void onLowMemory() {
                    this.f2251pT.onLowMemory();
                    super.onLowMemory();
                }

                @Override 
                public void onPause() {
                    this.f2251pT.onPause();
                    super.onPause();
                }

                @Override 
                public void onResume() {
                    super.onResume();
                    this.f2251pT.onResume();
                }

                @Override 
                public void onSaveInstanceState(Bundle outState) {
                    if (outState != null) {
                        outState.setClassLoader(SupportMapFragment.class.getClassLoader());
                    }
                    super.onSaveInstanceState(outState);
                    this.f2251pT.onSaveInstanceState(outState);
                }

                @Override 
                public void setArguments(Bundle args) {
                    super.setArguments(args);
                }
            }
