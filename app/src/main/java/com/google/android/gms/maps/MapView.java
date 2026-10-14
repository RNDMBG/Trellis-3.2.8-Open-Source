            package com.google.android.gms.maps;

            import android.app.Activity;
            import android.content.Context;
            import android.os.Bundle;
            import android.os.RemoteException;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.dynamic.AbstractC0903a;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0906d;
            import com.google.android.gms.dynamic.LifecycleDelegate;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.C1173q;
            import com.google.android.gms.maps.internal.IMapViewDelegate;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            
            
            public class MapView extends FrameLayout {

                
                private GoogleMap f2241pI;

                
                private final C1146b f2242pM;

                
                static class C1145a implements LifecycleDelegate {

                    
                    private final ViewGroup f2243pN;

                    
                    private final IMapViewDelegate f2244pO;

                    
                    private View f2245pP;

                    public C1145a(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
                        this.f2244pO = (IMapViewDelegate) C1028dm.m1605e(iMapViewDelegate);
                        this.f2243pN = (ViewGroup) C1028dm.m1605e(viewGroup);
                    }

                    
                    public IMapViewDelegate m2220cF() {
                        return this.f2244pO;
                    }

                    @Override 
                    public void onCreate(Bundle savedInstanceState) {
                        try {
                            this.f2244pO.onCreate(savedInstanceState);
                            this.f2245pP = (View) BinderC0905c.m1165b(this.f2244pO.getView());
                            this.f2243pN.removeAllViews();
                            this.f2243pN.addView(this.f2245pP);
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
                    }

                    @Override 
                    public void onDestroy() {
                        try {
                            this.f2244pO.onDestroy();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onDestroyView() {
                        throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
                    }

                    @Override 
                    public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
                        throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
                    }

                    @Override 
                    public void onLowMemory() {
                        try {
                            this.f2244pO.onLowMemory();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onPause() {
                        try {
                            this.f2244pO.onPause();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onResume() {
                        try {
                            this.f2244pO.onResume();
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }

                    @Override 
                    public void onSaveInstanceState(Bundle outState) {
                        try {
                            this.f2244pO.onSaveInstanceState(outState);
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }
                }

                
                static class C1146b extends AbstractC0903a<C1145a> {
                    private final Context mContext;

                    
                    protected InterfaceC0906d<C1145a> f2246pL;

                    
                    private final ViewGroup f2247pQ;

                    
                    private final GoogleMapOptions f2248pR;

                    C1146b(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
                        this.f2247pQ = viewGroup;
                        this.mContext = context;
                        this.f2248pR = googleMapOptions;
                    }

                    @Override 
                    
                    protected void mo1160a(InterfaceC0906d<C1145a> interfaceC0906d) {
                        this.f2246pL = interfaceC0906d;
                        m2221cE();
                    }

                    
                    public void m2221cE() {
                        if (this.f2246pL == null || m1161bP() != null) {
                            return;
                        }
                        try {
                            this.f2246pL.mo1162a(new C1145a(this.f2247pQ, C1173q.m2262u(this.mContext).mo2237a(BinderC0905c.m1166g(this.mContext), this.f2248pR)));
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        } catch (GooglePlayServicesNotAvailableException e2) {
                        }
                    }
                }

                public MapView(Context context) {
                    super(context);
                    this.f2242pM = new C1146b(this, context, null);
                }

                public MapView(Context context, AttributeSet attrs) {
                    super(context, attrs);
                    this.f2242pM = new C1146b(this, context, GoogleMapOptions.createFromAttributes(context, attrs));
                }

                public MapView(Context context, AttributeSet attrs, int defStyle) {
                    super(context, attrs, defStyle);
                    this.f2242pM = new C1146b(this, context, GoogleMapOptions.createFromAttributes(context, attrs));
                }

                public MapView(Context context, GoogleMapOptions options) {
                    super(context);
                    this.f2242pM = new C1146b(this, context, options);
                }

                public final GoogleMap getMap() {
                    if (this.f2241pI != null) {
                        return this.f2241pI;
                    }
                    this.f2242pM.m2221cE();
                    if (this.f2242pM.m1161bP() == null) {
                        return null;
                    }
                    try {
                        this.f2241pI = new GoogleMap(this.f2242pM.m1161bP().m2220cF().getMap());
                        return this.f2241pI;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                public final void onCreate(Bundle savedInstanceState) {
                    this.f2242pM.onCreate(savedInstanceState);
                    if (this.f2242pM.m1161bP() == null) {
                        this.f2242pM.m1159a(this);
                    }
                }

                public final void onDestroy() {
                    this.f2242pM.onDestroy();
                }

                public final void onLowMemory() {
                    this.f2242pM.onLowMemory();
                }

                public final void onPause() {
                    this.f2242pM.onPause();
                }

                public final void onResume() {
                    this.f2242pM.onResume();
                }

                public final void onSaveInstanceState(Bundle outState) {
                    this.f2242pM.onSaveInstanceState(outState);
                }
            }
