            package com.google.android.gms.ads;

            import android.content.Context;
            import android.os.RemoteException;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.internal.BinderC0956av;
            import com.google.android.gms.internal.BinderC1122t;
            import com.google.android.gms.internal.BinderC1128z;
            import com.google.android.gms.internal.C0935aa;
            import com.google.android.gms.internal.C1001cm;
            import com.google.android.gms.internal.C1002cn;
            import com.google.android.gms.internal.C1123u;
            import com.google.android.gms.internal.C1124v;
            import com.google.android.gms.internal.C1126x;
            import com.google.android.gms.internal.InterfaceC0937ac;
            
            
            public final class AdView extends ViewGroup {

                
                private AdSize f895c;

                
                private final BinderC0956av f896dS;

                
                private AdListener f897dT;

                
                private InterfaceC0937ac f898dU;

                
                private String f899dV;

                
                private InterfaceC0883a f900dW;

                public AdView(Context context) {
                    super(context);
                    this.f896dS = new BinderC0956av();
                }

                public AdView(Context context, AttributeSet attrs) {
                    this(context, attrs, 0);
                }

                public AdView(Context context, AttributeSet attrs, int defStyle) {
                    super(context, attrs, defStyle);
                    this.f896dS = new BinderC0956av();
                    try {
                        C0935aa c0935aa = new C0935aa(context, attrs);
                        this.f895c = c0935aa.getAdSize();
                        this.f899dV = c0935aa.getAdUnitId();
                        if (isInEditMode()) {
                            C1001cm.m1448b(this, new C1126x(context, this.f895c), "Ads by Google");
                        }
                    } catch (IllegalArgumentException e) {
                        C1001cm.m1444a(this, new C1126x(context, AdSize.BANNER), e.getMessage());
                    }
                }

                
                private void m990c(String str) throws RemoteException {
                    if (this.f895c == null || this.f899dV == null) {
                        m991d(str);
                    }
                    Context context = getContext();
                    this.f898dU = C1123u.m2172a(context, new C1126x(context, this.f895c), this.f899dV, this.f896dS);
                    if (this.f897dT != null) {
                        this.f898dU.mo1240a(new BinderC1122t(this.f897dT));
                    }
                    if (this.f900dW != null) {
                        this.f898dU.mo1241a(new BinderC1128z(this.f900dW));
                    }
                    m992x();
                }

                
                private void m991d(String str) {
                    if (this.f898dU == null) {
                        throw new IllegalStateException("The ad size and ad unit ID must be set on AdView before " + str + " is called.");
                    }
                }

                
                private void m992x() {
                    try {
                        InterfaceC0904b interfaceC0904bMo1243z = this.f898dU.mo1243z();
                        if (interfaceC0904bMo1243z == null) {
                            return;
                        }
                        addView((View) BinderC0905c.m1165b(interfaceC0904bMo1243z));
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to get an ad frame.", e);
                    }
                }

                public void destroy() {
                    try {
                        if (this.f898dU != null) {
                            this.f898dU.destroy();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to destroy AdView.", e);
                    }
                }

                public AdListener getAdListener() {
                    return this.f897dT;
                }

                public AdSize getAdSize() {
                    return this.f895c;
                }

                public String getAdUnitId() {
                    return this.f899dV;
                }

                public void loadAd(AdRequest adRequest) {
                    try {
                        if (this.f898dU == null) {
                            m990c("loadAd");
                        }
                        if (this.f898dU.mo1242a(new C1124v(getContext(), adRequest))) {
                            this.f896dS.m1279c(adRequest.m982v());
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to load ad.", e);
                    }
                }

                @Override 
                protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                    View childAt = getChildAt(0);
                    if (childAt == null || childAt.getVisibility() == 8) {
                        return;
                    }
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    int i = ((right - left) - measuredWidth) / 2;
                    int i2 = ((bottom - top) - measuredHeight) / 2;
                    childAt.layout(i, i2, measuredWidth + i, measuredHeight + i2);
                }

                @Override 
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int widthInPixels;
                    int heightInPixels = 0;
                    View childAt = getChildAt(0);
                    if (childAt != null && childAt.getVisibility() != 8) {
                        measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
                        widthInPixels = childAt.getMeasuredWidth();
                        heightInPixels = childAt.getMeasuredHeight();
                    } else if (this.f895c != null) {
                        Context context = getContext();
                        widthInPixels = this.f895c.getWidthInPixels(context);
                        heightInPixels = this.f895c.getHeightInPixels(context);
                    } else {
                        widthInPixels = 0;
                    }
                    setMeasuredDimension(resolveSize(Math.max(widthInPixels, getSuggestedMinimumWidth()), widthMeasureSpec), resolveSize(Math.max(heightInPixels, getSuggestedMinimumHeight()), heightMeasureSpec));
                }

                public void pause() {
                    try {
                        if (this.f898dU != null) {
                            this.f898dU.pause();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to call pause.", e);
                    }
                }

                public void resume() {
                    try {
                        if (this.f898dU != null) {
                            this.f898dU.resume();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to call resume.", e);
                    }
                }

                public void setAdListener(AdListener adListener) {
                    try {
                        this.f897dT = adListener;
                        if (this.f898dU != null) {
                            this.f898dU.mo1240a(adListener != null ? new BinderC1122t(adListener) : null);
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Failed to set the AdListener.", e);
                    }
                }

                public void setAdSize(AdSize adSize) {
                    if (this.f895c != null) {
                        throw new IllegalStateException("The ad size can only be set once on AdView.");
                    }
                    this.f895c = adSize;
                    requestLayout();
                }

                public void setAdUnitId(String adUnitId) {
                    if (this.f899dV != null) {
                        throw new IllegalStateException("The ad unit ID can only be set once on AdView.");
                    }
                    this.f899dV = adUnitId;
                }
            }
