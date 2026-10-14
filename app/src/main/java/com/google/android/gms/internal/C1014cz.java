            package com.google.android.gms.internal;

            import android.graphics.Canvas;
            import android.graphics.ColorFilter;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.os.SystemClock;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cz */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1014cz extends Drawable implements Drawable.Callback {

                /* JADX INFO: renamed from: kb */
                private boolean f1648kb;

                /* JADX INFO: renamed from: kd */
                private int f1649kd;

                /* JADX INFO: renamed from: ke */
                private long f1650ke;

                /* JADX INFO: renamed from: kf */
                private int f1651kf;

                /* JADX INFO: renamed from: kg */
                private int f1652kg;

                /* JADX INFO: renamed from: kh */
                private int f1653kh;

                /* JADX INFO: renamed from: ki */
                private int f1654ki;

                /* JADX INFO: renamed from: kj */
                private int f1655kj;

                /* JADX INFO: renamed from: kk */
                private boolean f1656kk;

                /* JADX INFO: renamed from: kl */
                private b f1657kl;

                /* JADX INFO: renamed from: km */
                private Drawable f1658km;

                /* JADX INFO: renamed from: kn */
                private Drawable f1659kn;

                /* JADX INFO: renamed from: ko */
                private boolean f1660ko;

                /* JADX INFO: renamed from: kp */
                private boolean f1661kp;

                /* JADX INFO: renamed from: kq */
                private boolean f1662kq;

                /* JADX INFO: renamed from: kr */
                private int f1663kr;

                /* JADX INFO: renamed from: com.google.android.gms.internal.cz$a */
                private static final class a extends Drawable {

                    /* JADX INFO: renamed from: ks */
                    private static final a f1664ks = new a();

                    /* JADX INFO: renamed from: kt */
                    private static final C1328a f1665kt = new C1328a(null);

                    /* JADX INFO: renamed from: com.google.android.gms.internal.cz$a$a, reason: collision with other inner class name */
                    private static final class C1328a extends Drawable.ConstantState {
                        private C1328a() {
                        }

                        /* synthetic */ C1328a(Object anonymousClass1) {
                            this();
                        }

                        @Override // android.graphics.drawable.Drawable.ConstantState
                        public int getChangingConfigurations() {
                            return 0;
                        }

                        @Override // android.graphics.drawable.Drawable.ConstantState
                        public Drawable newDrawable() {
                            return a.m1515aT();
                        }
                    }

                    private a() {
                    }

                    /* JADX INFO: renamed from: aT */
                    static /* synthetic */ a m1515aT() {
                        return f1664ks;
                    }

                    @Override // android.graphics.drawable.Drawable
                    public void draw(Canvas canvas) {
                    }

                    @Override // android.graphics.drawable.Drawable
                    public Drawable.ConstantState getConstantState() {
                        return f1665kt;
                    }

                    @Override // android.graphics.drawable.Drawable
                    public int getOpacity() {
                        return -2;
                    }

                    @Override // android.graphics.drawable.Drawable
                    public void setAlpha(int alpha) {
                    }

                    @Override // android.graphics.drawable.Drawable
                    public void setColorFilter(ColorFilter cf) {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.cz$b */
                static final class b extends Drawable.ConstantState {

                    /* JADX INFO: renamed from: ku */
                    int f1666ku;

                    /* JADX INFO: renamed from: kv */
                    int f1667kv;

                    b(b bVar) {
                        if (bVar != null) {
                            this.f1666ku = bVar.f1666ku;
                            this.f1667kv = bVar.f1667kv;
                        }
                    }

                    @Override // android.graphics.drawable.Drawable.ConstantState
                    public int getChangingConfigurations() {
                        return this.f1666ku;
                    }

                    @Override // android.graphics.drawable.Drawable.ConstantState
                    public Drawable newDrawable() {
                        return new C1014cz(this);
                    }
                }

                public C1014cz(Drawable drawable, Drawable drawable2) {
                    this(null);
                    drawable = drawable == null ? a.m1515aT() : drawable;
                    this.f1658km = drawable;
                    drawable.setCallback(this);
                    this.f1657kl.f1667kv |= drawable.getChangingConfigurations();
                    drawable2 = drawable2 == null ? a.m1515aT() : drawable2;
                    this.f1659kn = drawable2;
                    drawable2.setCallback(this);
                    this.f1657kl.f1667kv |= drawable2.getChangingConfigurations();
                }

                C1014cz(b bVar) {
                    this.f1649kd = 0;
                    this.f1653kh = 255;
                    this.f1655kj = 0;
                    this.f1648kb = true;
                    this.f1657kl = new b(bVar);
                }

                /* JADX INFO: renamed from: aS */
                public Drawable m1514aS() {
                    return this.f1659kn;
                }

                public boolean canConstantState() {
                    if (!this.f1660ko) {
                        this.f1661kp = (this.f1658km.getConstantState() == null || this.f1659kn.getConstantState() == null) ? false : true;
                        this.f1660ko = true;
                    }
                    return this.f1661kp;
                }

                @Override // android.graphics.drawable.Drawable
                public void draw(Canvas canvas) {
                    boolean z = true;
                    boolean z2 = false;
                    switch (this.f1649kd) {
                        case 1:
                            this.f1650ke = SystemClock.uptimeMillis();
                            this.f1649kd = 2;
                            break;
                        case 2:
                            if (this.f1650ke >= 0) {
                                float fUptimeMillis = (SystemClock.uptimeMillis() - this.f1650ke) / this.f1654ki;
                                z = fUptimeMillis >= 1.0f;
                                if (z) {
                                    this.f1649kd = 0;
                                }
                                this.f1655kj = (int) ((Math.min(fUptimeMillis, 1.0f) * (this.f1652kg - this.f1651kf)) + this.f1651kf);
                                break;
                            }
                        default:
                            z2 = z;
                            break;
                    }
                    int i = this.f1655kj;
                    boolean z3 = this.f1648kb;
                    Drawable drawable = this.f1658km;
                    Drawable drawable2 = this.f1659kn;
                    if (z2) {
                        if (!z3 || i == 0) {
                            drawable.draw(canvas);
                        }
                        if (i == this.f1653kh) {
                            drawable2.setAlpha(this.f1653kh);
                            drawable2.draw(canvas);
                            return;
                        }
                        return;
                    }
                    if (z3) {
                        drawable.setAlpha(this.f1653kh - i);
                    }
                    drawable.draw(canvas);
                    if (z3) {
                        drawable.setAlpha(this.f1653kh);
                    }
                    if (i > 0) {
                        drawable2.setAlpha(i);
                        drawable2.draw(canvas);
                        drawable2.setAlpha(this.f1653kh);
                    }
                    invalidateSelf();
                }

                @Override // android.graphics.drawable.Drawable
                public int getChangingConfigurations() {
                    return super.getChangingConfigurations() | this.f1657kl.f1666ku | this.f1657kl.f1667kv;
                }

                @Override // android.graphics.drawable.Drawable
                public Drawable.ConstantState getConstantState() {
                    if (!canConstantState()) {
                        return null;
                    }
                    this.f1657kl.f1666ku = getChangingConfigurations();
                    return this.f1657kl;
                }

                @Override // android.graphics.drawable.Drawable
                public int getIntrinsicHeight() {
                    return Math.max(this.f1658km.getIntrinsicHeight(), this.f1659kn.getIntrinsicHeight());
                }

                @Override // android.graphics.drawable.Drawable
                public int getIntrinsicWidth() {
                    return Math.max(this.f1658km.getIntrinsicWidth(), this.f1659kn.getIntrinsicWidth());
                }

                @Override // android.graphics.drawable.Drawable
                public int getOpacity() {
                    if (!this.f1662kq) {
                        this.f1663kr = Drawable.resolveOpacity(this.f1658km.getOpacity(), this.f1659kn.getOpacity());
                        this.f1662kq = true;
                    }
                    return this.f1663kr;
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void invalidateDrawable(Drawable who) {
                    Drawable.Callback callback;
                    if (!C1053ek.m1736bJ() || (callback = getCallback()) == null) {
                        return;
                    }
                    callback.invalidateDrawable(this);
                }

                @Override // android.graphics.drawable.Drawable
                public Drawable mutate() {
                    if (!this.f1656kk && super.mutate() == this) {
                        if (!canConstantState()) {
                            throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
                        }
                        this.f1658km.mutate();
                        this.f1659kn.mutate();
                        this.f1656kk = true;
                    }
                    return this;
                }

                @Override // android.graphics.drawable.Drawable
                protected void onBoundsChange(Rect bounds) {
                    this.f1658km.setBounds(bounds);
                    this.f1659kn.setBounds(bounds);
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void scheduleDrawable(Drawable who, Runnable what, long when) {
                    Drawable.Callback callback;
                    if (!C1053ek.m1736bJ() || (callback = getCallback()) == null) {
                        return;
                    }
                    callback.scheduleDrawable(this, what, when);
                }

                @Override // android.graphics.drawable.Drawable
                public void setAlpha(int alpha) {
                    if (this.f1655kj == this.f1653kh) {
                        this.f1655kj = alpha;
                    }
                    this.f1653kh = alpha;
                    invalidateSelf();
                }

                @Override // android.graphics.drawable.Drawable
                public void setColorFilter(ColorFilter cf) {
                    this.f1658km.setColorFilter(cf);
                    this.f1659kn.setColorFilter(cf);
                }

                public void startTransition(int durationMillis) {
                    this.f1651kf = 0;
                    this.f1652kg = this.f1653kh;
                    this.f1655kj = 0;
                    this.f1654ki = durationMillis;
                    this.f1649kd = 1;
                    invalidateSelf();
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void unscheduleDrawable(Drawable who, Runnable what) {
                    Drawable.Callback callback;
                    if (!C1053ek.m1736bJ() || (callback = getCallback()) == null) {
                        return;
                    }
                    callback.unscheduleDrawable(this, what);
                }
            }
