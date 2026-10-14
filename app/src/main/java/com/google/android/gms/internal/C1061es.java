            package com.google.android.gms.internal;

            import android.R;
            import android.app.Activity;
            import android.content.Context;
            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.view.Display;
            import android.view.View;
            import android.view.ViewTreeObserver;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.es */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1061es {

                /* JADX INFO: renamed from: mz */
                protected C1055em f1850mz;

                /* JADX INFO: renamed from: np */
                protected a f1851np;

                /* JADX INFO: renamed from: com.google.android.gms.internal.es$a */
                public static final class a {
                    public int bottom;
                    public int gravity;
                    public int left;

                    /* JADX INFO: renamed from: nq */
                    public IBinder f1852nq;

                    /* JADX INFO: renamed from: nr */
                    public int f1853nr;
                    public int right;
                    public int top;

                    private a(int i, IBinder iBinder) {
                        this.f1853nr = -1;
                        this.left = 0;
                        this.top = 0;
                        this.right = 0;
                        this.bottom = 0;
                        this.gravity = i;
                        this.f1852nq = iBinder;
                    }

                    /* synthetic */ a(int i, IBinder iBinder, Object anonymousClass1) {
                        this(i, iBinder);
                    }

                    /* JADX INFO: renamed from: ca */
                    public Bundle m1892ca() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("popupLocationInfo.gravity", this.gravity);
                        bundle.putInt("popupLocationInfo.displayId", this.f1853nr);
                        bundle.putInt("popupLocationInfo.left", this.left);
                        bundle.putInt("popupLocationInfo.top", this.top);
                        bundle.putInt("popupLocationInfo.right", this.right);
                        bundle.putInt("popupLocationInfo.bottom", this.bottom);
                        return bundle;
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.es$b */
                private static final class b extends C1061es implements View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener {

                    /* JADX INFO: renamed from: mK */
                    private boolean f1854mK;

                    /* JADX INFO: renamed from: ns */
                    private WeakReference<View> f1855ns;

                    protected b(C1055em c1055em, int i) {
                        super(c1055em, i, null);
                        this.f1854mK = false;
                    }

                    /* JADX INFO: renamed from: f */
                    private void m1893f(View view) {
                        Display display;
                        int displayId = -1;
                        if (C1053ek.m1741bO() && (display = view.getDisplay()) != null) {
                            displayId = display.getDisplayId();
                        }
                        IBinder windowToken = view.getWindowToken();
                        int[] iArr = new int[2];
                        view.getLocationInWindow(iArr);
                        int width = view.getWidth();
                        int height = view.getHeight();
                        this.f1851np.f1853nr = displayId;
                        this.f1851np.f1852nq = windowToken;
                        this.f1851np.left = iArr[0];
                        this.f1851np.top = iArr[1];
                        this.f1851np.right = iArr[0] + width;
                        this.f1851np.bottom = iArr[1] + height;
                        if (this.f1854mK) {
                            mo1888bX();
                            this.f1854mK = false;
                        }
                    }

                    @Override // com.google.android.gms.internal.C1061es
                    /* JADX INFO: renamed from: Q */
                    protected void mo1887Q(int i) {
                        this.f1851np = new a(i, null, null);
                    }

                    @Override // com.google.android.gms.internal.C1061es
                    /* JADX INFO: renamed from: bX */
                    public void mo1888bX() {
                        if (this.f1851np.f1852nq != null) {
                            super.mo1888bX();
                        } else {
                            this.f1854mK = this.f1855ns != null;
                        }
                    }

                    @Override // com.google.android.gms.internal.C1061es
                    /* JADX INFO: renamed from: e */
                    public void mo1891e(View view) {
                        this.f1850mz.m1792bT();
                        if (this.f1855ns != null) {
                            View decorView = this.f1855ns.get();
                            Context context = this.f1850mz.getContext();
                            if (decorView == null && (context instanceof Activity)) {
                                decorView = ((Activity) context).getWindow().getDecorView();
                            }
                            if (decorView != null) {
                                decorView.removeOnAttachStateChangeListener(this);
                                ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
                                if (C1053ek.m1740bN()) {
                                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                                } else {
                                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                                }
                            }
                        }
                        this.f1855ns = null;
                        Context context2 = this.f1850mz.getContext();
                        if (view == null && (context2 instanceof Activity)) {
                            View viewFindViewById = ((Activity) context2).findViewById(R.id.content);
                            if (viewFindViewById == null) {
                                viewFindViewById = ((Activity) context2).getWindow().getDecorView();
                            }
                            C1058ep.m1802c("PopupManager", "You have not specified a View to use as content view for popups. Falling back to the Activity content view which may not work properly in future versions of the API. Use setViewForPopups() to set your content view.");
                            view = viewFindViewById;
                        }
                        if (view == null) {
                            C1058ep.m1803d("PopupManager", "No content view usable to display popups. Popups will not be displayed in response to this client's calls. Use setViewForPopups() to set your content view.");
                            return;
                        }
                        m1893f(view);
                        this.f1855ns = new WeakReference<>(view);
                        view.addOnAttachStateChangeListener(this);
                        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        View view;
                        if (this.f1855ns == null || (view = this.f1855ns.get()) == null) {
                            return;
                        }
                        m1893f(view);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View v) {
                        m1893f(v);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View v) {
                        this.f1850mz.m1792bT();
                        v.removeOnAttachStateChangeListener(this);
                    }
                }

                private C1061es(C1055em c1055em, int i) {
                    this.f1850mz = c1055em;
                    mo1887Q(i);
                }

                /* synthetic */ C1061es(C1055em c1055em, int i, Object anonymousClass1) {
                    this(c1055em, i);
                }

                /* JADX INFO: renamed from: a */
                public static C1061es m1886a(C1055em c1055em, int i) {
                    return C1053ek.m1737bK() ? new b(c1055em, i) : new C1061es(c1055em, i);
                }

                /* JADX INFO: renamed from: Q */
                protected void mo1887Q(int i) {
                    this.f1851np = new a(i, new Binder(), null);
                }

                /* JADX INFO: renamed from: bX */
                public void mo1888bX() {
                    this.f1850mz.m1785a(this.f1851np.f1852nq, this.f1851np.m1892ca());
                }

                /* JADX INFO: renamed from: bY */
                public Bundle m1889bY() {
                    return this.f1851np.m1892ca();
                }

                /* JADX INFO: renamed from: bZ */
                public IBinder m1890bZ() {
                    return this.f1851np.f1852nq;
                }

                /* JADX INFO: renamed from: e */
                public void mo1891e(View view) {
                }

                public void setGravity(int gravity) {
                    this.f1851np.gravity = gravity;
                }
            }
