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
            
            /* JADX INFO: renamed from: com.google.android.gms.dynamic.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class AbstractC0903a<T extends LifecycleDelegate> {

                /* JADX INFO: renamed from: lV */
                private T f1006lV;

                /* JADX INFO: renamed from: lW */
                private Bundle f1007lW;

                /* JADX INFO: renamed from: lX */
                private LinkedList<a> f1008lX;

                /* JADX INFO: renamed from: lY */
                private final InterfaceC0906d<T> f1009lY = new AnonymousClass1();

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$1, reason: invalid class name */
                class AnonymousClass1 implements InterfaceC0906d<T> {
                    AnonymousClass1() {
                    }

                    @Override // com.google.android.gms.dynamic.InterfaceC0906d
                    /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$2, reason: invalid class name */
                class AnonymousClass2 implements a {

                    /* JADX INFO: renamed from: ma */
                    final /* synthetic */ Activity f1012ma;

                    /* JADX INFO: renamed from: mb */
                    final /* synthetic */ Bundle f1013mb;

                    /* JADX INFO: renamed from: mc */
                    final /* synthetic */ Bundle f1014mc;

                    AnonymousClass2(Activity activity, Bundle bundle, Bundle bundle2) {
                        this.f1012ma = activity;
                        this.f1013mb = bundle;
                        this.f1014mc = bundle2;
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    /* JADX INFO: renamed from: b */
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        AbstractC0903a.m1158b(AbstractC0903a.this).onInflate(this.f1012ma, this.f1013mb, this.f1014mc);
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    public int getState() {
                        return 0;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$3, reason: invalid class name */
                class AnonymousClass3 implements a {

                    /* JADX INFO: renamed from: mc */
                    final /* synthetic */ Bundle f1016mc;

                    AnonymousClass3(Bundle bundle) {
                        this.f1016mc = bundle;
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    /* JADX INFO: renamed from: b */
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        AbstractC0903a.m1158b(AbstractC0903a.this).onCreate(this.f1016mc);
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    public int getState() {
                        return 1;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$4, reason: invalid class name */
                class AnonymousClass4 implements a {

                    /* JADX INFO: renamed from: mc */
                    final /* synthetic */ Bundle f1018mc;

                    /* JADX INFO: renamed from: md */
                    final /* synthetic */ FrameLayout f1019md;

                    /* JADX INFO: renamed from: me */
                    final /* synthetic */ LayoutInflater f1020me;

                    /* JADX INFO: renamed from: mf */
                    final /* synthetic */ ViewGroup f1021mf;

                    AnonymousClass4(FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
                        this.f1019md = frameLayout;
                        this.f1020me = layoutInflater;
                        this.f1021mf = viewGroup;
                        this.f1018mc = bundle;
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    /* JADX INFO: renamed from: b */
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        this.f1019md.removeAllViews();
                        this.f1019md.addView(AbstractC0903a.m1158b(AbstractC0903a.this).onCreateView(this.f1020me, this.f1021mf, this.f1018mc));
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    public int getState() {
                        return 2;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$5, reason: invalid class name */
                class AnonymousClass5 implements View.OnClickListener {

                    /* JADX INFO: renamed from: gO */
                    final /* synthetic */ Context f1022gO;

                    /* JADX INFO: renamed from: mg */
                    final /* synthetic */ int f1024mg;

                    AnonymousClass5(Context context, int i) {
                        this.f1022gO = context;
                        this.f1024mg = i;
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        this.f1022gO.startActivity(GooglePlayServicesUtil.m1012a(this.f1022gO, this.f1024mg, -1));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$6, reason: invalid class name */
                class AnonymousClass6 implements a {
                    AnonymousClass6() {
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    /* JADX INFO: renamed from: b */
                    public void mo1163b(LifecycleDelegate lifecycleDelegate) {
                        AbstractC0903a.m1158b(AbstractC0903a.this).onResume();
                    }

                    @Override // com.google.android.gms.dynamic.AbstractC0903a.a
                    public int getState() {
                        return 3;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.a$a */
                private interface a {
                    /* JADX INFO: renamed from: b */
                    void mo1163b(LifecycleDelegate lifecycleDelegate);

                    int getState();
                }

                /* JADX INFO: renamed from: J */
                private void m1153J(int i) {
                    while (!this.f1008lX.isEmpty() && this.f1008lX.getLast().getState() >= i) {
                        this.f1008lX.removeLast();
                    }
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Bundle m1154a(AbstractC0903a abstractC0903a, Bundle bundle) {
                    abstractC0903a.f1007lW = bundle;
                    return bundle;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: a */
                static /* synthetic */ LifecycleDelegate m1155a(AbstractC0903a abstractC0903a, LifecycleDelegate lifecycleDelegate) {
                    abstractC0903a.f1006lV = lifecycleDelegate;
                    return lifecycleDelegate;
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ LinkedList m1156a(AbstractC0903a abstractC0903a) {
                    return abstractC0903a.f1008lX;
                }

                /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: b */
                static /* synthetic */ LifecycleDelegate m1158b(AbstractC0903a abstractC0903a) {
                    return abstractC0903a.f1006lV;
                }

                /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: a */
                protected abstract void mo1160a(InterfaceC0906d<T> interfaceC0906d);

                /* JADX INFO: renamed from: bP */
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
