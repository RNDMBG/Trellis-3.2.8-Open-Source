            package com.google.android.gms.plus;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.os.IBinder;
            import android.os.RemoteException;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.View;
            import android.view.ViewGroup;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1082fm;
            import com.google.android.gms.internal.InterfaceC1079fj;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class PlusOneButtonWithPopup extends ViewGroup {

                /* JADX INFO: renamed from: hN */
                private String f2383hN;

                /* JADX INFO: renamed from: it */
                private String f2384it;
                private int mSize;

                /* JADX INFO: renamed from: re */
                private View f2385re;

                /* JADX INFO: renamed from: rf */
                private int f2386rf;

                /* JADX INFO: renamed from: rk */
                private View.OnClickListener f2387rk;

                public PlusOneButtonWithPopup(Context context) {
                    this(context, null);
                }

                public PlusOneButtonWithPopup(Context context, AttributeSet attrs) {
                    super(context, attrs);
                    this.mSize = PlusOneButton.getSize(context, attrs);
                    this.f2386rf = PlusOneButton.getAnnotation(context, attrs);
                    this.f2385re = new PlusOneDummyView(context, this.mSize);
                    addView(this.f2385re);
                }

                /* JADX INFO: renamed from: c */
                private int m2334c(int i, int i2) {
                    int mode = View.MeasureSpec.getMode(i);
                    switch (mode) {
                        case Integer.MIN_VALUE:
                        case 1073741824:
                            return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) - i2, mode);
                        default:
                            return i;
                    }
                }

                /* JADX INFO: renamed from: cS */
                private void m2335cS() {
                    if (this.f2385re != null) {
                        removeView(this.f2385re);
                    }
                    this.f2385re = C1082fm.m1980a(getContext(), this.mSize, this.f2386rf, this.f2383hN, this.f2384it);
                    if (this.f2387rk != null) {
                        setOnClickListener(this.f2387rk);
                    }
                    addView(this.f2385re);
                }

                /* JADX INFO: renamed from: cT */
                private InterfaceC1079fj m2336cT() throws RemoteException {
                    InterfaceC1079fj interfaceC1079fjM1958ap = InterfaceC1079fj.a.m1958ap((IBinder) this.f2385re.getTag());
                    if (interfaceC1079fjM1958ap != null) {
                        return interfaceC1079fjM1958ap;
                    }
                    if (Log.isLoggable("PlusOneButtonWithPopup", 5)) {
                        Log.w("PlusOneButtonWithPopup", "Failed to get PlusOneDelegate");
                    }
                    throw new RemoteException();
                }

                public void cancelClick() {
                    if (this.f2385re != null) {
                        try {
                            m2336cT().cancelClick();
                        } catch (RemoteException e) {
                        }
                    }
                }

                public PendingIntent getResolution() {
                    if (this.f2385re != null) {
                        try {
                            return m2336cT().getResolution();
                        } catch (RemoteException e) {
                        }
                    }
                    return null;
                }

                public void initialize(String url, String accountName) {
                    C1028dm.m1601a(url, "Url must not be null");
                    this.f2383hN = url;
                    this.f2384it = accountName;
                    m2335cS();
                }

                @Override // android.view.ViewGroup, android.view.View
                protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                    this.f2385re.layout(getPaddingLeft(), getPaddingTop(), (right - left) - getPaddingRight(), (bottom - top) - getPaddingBottom());
                }

                @Override // android.view.View
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int paddingLeft = getPaddingLeft() + getPaddingRight();
                    int paddingTop = getPaddingTop() + getPaddingBottom();
                    this.f2385re.measure(m2334c(widthMeasureSpec, paddingLeft), m2334c(heightMeasureSpec, paddingTop));
                    setMeasuredDimension(paddingLeft + this.f2385re.getMeasuredWidth(), paddingTop + this.f2385re.getMeasuredHeight());
                }

                public void reinitialize() {
                    if (this.f2385re != null) {
                        try {
                            m2336cT().reinitialize();
                        } catch (RemoteException e) {
                        }
                    }
                }

                public void setAnnotation(int annotation) {
                    this.f2386rf = annotation;
                    m2335cS();
                }

                @Override // android.view.View
                public void setOnClickListener(View.OnClickListener onClickListener) {
                    this.f2387rk = onClickListener;
                    this.f2385re.setOnClickListener(onClickListener);
                }

                public void setSize(int size) {
                    this.mSize = size;
                    m2335cS();
                }
            }
