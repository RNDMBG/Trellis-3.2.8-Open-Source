            package com.google.android.gms.dynamic;

            import android.app.Activity;
            import android.content.Context;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.FrameLayout;
            import android.widget.LinearLayout;
            import android.widget.TextView;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.dynamic.LifecycleDelegate;
            import java.util.Iterator;
            import java.util.LinkedList;
            
            
            
            public abstract class AbstractC0903a<T extends LifecycleDelegate> {

                
                private T f1006lV;

                
                private Bundle f1007lW;

                
                private LinkedList<a> f1008lX;

                
                private final InterfaceC0906d<T> f1009lY = new AnonymousClass1();

                
                class AnonymousClass1 implements InterfaceC0906d<T> {
                    AnonymousClass1() {
                    }

                    @Override 
                    
                    public void mo1162a(T t) {
                        AbstractC0903a.m1155a(AbstractC0903a.this, t);
                        Iterator it = AbstractC0903a.m1156a(AbstractC0903a.this).iterator();
                        while (it.hasNext()) {
                            ((a) it.next()).mo1163b(AbstractC0903a.m1158b(AbstractC0903a.this));
                        }
                        AbstractC0903a.m1156a(AbstractC0903a.this).clear();
                        AbstractC0903a.m1154a(AbstractC0903a.this, (Bundle) null);
                    }
                }

                
                class AnonymousClass2 implements a {

                    
                    final  Activity f1012ma;

                    
                    final  Bundle f1013mb;

                    
                    final  Bundle f1014mc;

                    AnonymousClass2(Activity activity, Bundle bundle, Bundle bundle2) {
                        this.f1012ma = activity;
                        this.f1013mb = bundle;
                        this.f1014mc = bundle2;
                    }

                    @Override 
                    
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        AbstractC0903a.m1158b(AbstractC0903a.this).onInflate(this.f1012ma, this.f1013mb, this.f1014mc);
                    }

                    @Override 
                    public int getState() {
                        return 0;
                    }
                }

                
                class AnonymousClass3 implements a {

                    
                    final  Bundle f1016mc;

                    AnonymousClass3(Bundle bundle) {
                        this.f1016mc = bundle;
                    }

                    @Override 
                    
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        AbstractC0903a.m1158b(AbstractC0903a.this).onCreate(this.f1016mc);
                    }

                    @Override 
                    public int getState() {
                        return 1;
                    }
                }

                
                class AnonymousClass4 implements a {

                    
                    final  Bundle f1018mc;

                    
                    final  FrameLayout f1019md;

                    
                    final  LayoutInflater f1020me;

                    
                    final  ViewGroup f1021mf;

                    AnonymousClass4(FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
                        this.f1019md = frameLayout;
                        this.f1020me = layoutInflater;
                        this.f1021mf = viewGroup;
                        this.f1018mc = bundle;
                    }

                    @Override 
                    
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        this.f1019md.removeAllViews();
                        this.f1019md.addView(AbstractC0903a.m1158b(AbstractC0903a.this).onCreateView(this.f1020me, this.f1021mf, this.f1018mc));
                    }

                    @Override 
                    public int getState() {
                        return 2;
                    }
                }

                
                class AnonymousClass5 implements View.OnClickListener {

                    
                    final  Context f1022gO;

                    
                    final  int f1024mg;

                    AnonymousClass5(Context context, int i) {
                        this.f1022gO = context;
                        this.f1024mg = i;
                    }

                    @Override 
                    public void onClick(View v) {
                        this.f1022gO.startActivity(GooglePlayServicesUtil.m1012a(this.f1022gO, this.f1024mg, -1));
                    }
                }

                
                class AnonymousClass6 implements a {
                    AnonymousClass6() {
                    }

                    @Override 
                    
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        AbstractC0903a.m1158b(AbstractC0903a.this).onResume();
                    }

                    @Override 
                    public int getState() {
                        return 3;
                    }
                }

                
                private interface a {
                    
                    void mo1163b(LifecycleDelegate lifecycleDelegate);

                    int getState();
                }

                
                private void m1153J(int i) {
                    while (!this.f1008lX.isEmpty() && this.f1008lX.getLast().getState() >= i) {
                        this.f1008lX.removeLast();
                    }
                }

                
                static  Bundle m1154a(AbstractC0903a abstractC0903a, Bundle bundle) {
                    abstractC0903a.f1007lW = bundle;
                    return bundle;
                }

                
                
                static  LifecycleDelegate m1155a(AbstractC0903a abstractC0903a, LifecycleDelegate lifecycleDelegate) {
                    abstractC0903a.f1006lV = lifecycleDelegate;
                    return lifecycleDelegate;
                }

                
                static  LinkedList m1156a(AbstractC0903a abstractC0903a) {
                    return abstractC0903a.f1008lX;
                }

                
                private void m1157a(Bundle bundle, a aVar) {
                    if (this.f1006lV != null) {
                        aVar.mo1163b(this.f1006lV);
                        return;
                    }
                    if (this.f1008lX == null) {
                        this.f1008lX = new LinkedList<>();
                    }
                    this.f1008lX.add(aVar);
                    if (bundle != null) {
                        if (this.f1007lW == null) {
                            this.f1007lW = (Bundle) bundle.clone();
                        } else {
                            this.f1007lW.putAll(bundle);
                        }
                    }
                    mo1160a(this.f1009lY);
                }

                
                static  LifecycleDelegate m1158b(AbstractC0903a abstractC0903a) {
                    return abstractC0903a.f1006lV;
                }

                
                public void m1159a(FrameLayout frameLayout) {
                    Context context = frameLayout.getContext();
                    int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
                    String strM1018b = GooglePlayServicesUtil.m1018b(context, iIsGooglePlayServicesAvailable, -1);
                    String strM1017b = GooglePlayServicesUtil.m1017b(context, iIsGooglePlayServicesAvailable);
                    LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
                    linearLayout.setOrientation(1);
                    linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
                    frameLayout.addView(linearLayout);
                    TextView textView = new TextView(frameLayout.getContext());
                    textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
                    textView.setText(strM1018b);
                    linearLayout.addView(textView);
                    if (strM1017b != null) {
                        Button button = new Button(context);
                        button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
                        button.setText(strM1017b);
                        linearLayout.addView(button);
                        button.setOnClickListener(new AnonymousClass5(context, iIsGooglePlayServicesAvailable));
                    }
                }

                
                protected abstract void mo1160a(InterfaceC0906d<T> interfaceC0906d);

                
                public T m1161bP() {
                    return this.f1006lV;
                }

                public void onCreate(Bundle savedInstanceState) {
                    m1157a(savedInstanceState, new AnonymousClass3(savedInstanceState));
                }

                public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    FrameLayout frameLayout = new FrameLayout(inflater.getContext());
                    m1157a(savedInstanceState, new AnonymousClass4(frameLayout, inflater, container, savedInstanceState));
                    if (this.f1006lV == null) {
                        m1159a(frameLayout);
                    }
                    return frameLayout;
                }

                public void onDestroy() {
                    if (this.f1006lV != null) {
                        this.f1006lV.onDestroy();
                    } else {
                        m1153J(1);
                    }
                }

                public void onDestroyView() {
                    if (this.f1006lV != null) {
                        this.f1006lV.onDestroyView();
                    } else {
                        m1153J(2);
                    }
                }

                public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
                    m1157a(savedInstanceState, new AnonymousClass2(activity, attrs, savedInstanceState));
                }

                public void onLowMemory() {
                    if (this.f1006lV != null) {
                        this.f1006lV.onLowMemory();
                    }
                }

                public void onPause() {
                    if (this.f1006lV != null) {
                        this.f1006lV.onPause();
                    } else {
                        m1153J(3);
                    }
                }

                public void onResume() {
                    m1157a((Bundle) null, new AnonymousClass6());
                }

                public void onSaveInstanceState(Bundle outState) {
                    if (this.f1006lV != null) {
                        this.f1006lV.onSaveInstanceState(outState);
                    } else if (this.f1007lW != null) {
                        outState.putAll(this.f1007lW);
                    }
                }
            }
