            package com.flurry.sdk;

            import android.content.Context;
            import android.location.Criteria;
            import android.location.Location;
            import android.location.LocationListener;
            import android.location.LocationManager;
            import android.os.Bundle;
            import android.os.Looper;
            import android.text.TextUtils;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dw */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class C0812dw implements C0803dn.a {

                /* JADX INFO: renamed from: c */
                private static final String f733c = C0812dw.class.getSimpleName();

                /* JADX INFO: renamed from: o */
                private static C0812dw f734o;

                /* JADX INFO: renamed from: b */
                boolean f736b;

                /* JADX INFO: renamed from: g */
                private LocationManager f740g;

                /* JADX INFO: renamed from: h */
                private Criteria f741h;

                /* JADX INFO: renamed from: i */
                private Location f742i;

                /* JADX INFO: renamed from: k */
                private String f744k;

                /* JADX INFO: renamed from: n */
                private volatile Location f747n;

                /* JADX INFO: renamed from: d */
                private final int f737d = 3;

                /* JADX INFO: renamed from: e */
                private final long f738e = 10000;

                /* JADX INFO: renamed from: f */
                private final long f739f = 0;

                /* JADX INFO: renamed from: a */
                boolean f735a = false;

                /* JADX INFO: renamed from: l */
                private int f745l = 0;

                /* JADX INFO: renamed from: m */
                private int f746m = 0;

                /* JADX INFO: renamed from: j */
                private a f743j = new a();

                /* JADX INFO: renamed from: a */
/* 19 */        static /* synthetic */ int m779a(C0812dw c0812dw) {
/* 20 */            int i = c0812dw.f746m + 1;
                    c0812dw.f746m = i;
                    return i;
                }

                /* JADX INFO: renamed from: a */
/* 19 */        static /* synthetic */ Location m780a(C0812dw c0812dw, Location location) {
/* 20 */            c0812dw.f742i = location;
                    return location;
                }

                /* JADX INFO: renamed from: b */
/* 19 */        static /* synthetic */ void m784b(C0812dw c0812dw) {
/* 20 */            c0812dw.m786i();
                }

                /* JADX INFO: renamed from: h */
/* 19 */        static /* synthetic */ String m785h() {
/* 20 */            return f733c;
                }

/* 46 */        private C0812dw() {
/* 50 */            C0803dn c0803dnM742a = C0802dm.m742a();
/* 52 */            this.f741h = (Criteria) c0803dnM742a.m746a("LocationCriteria");
/* 53 */            c0803dnM742a.m747a("LocationCriteria", (C0803dn.a) this);
/* 54 */            C0827el.m885a(4, f733c, "initSettings, LocationCriteria = " + this.f741h);
/* 56 */            this.f736b = ((Boolean) c0803dnM742a.m746a("ReportLocation")).booleanValue();
/* 57 */            c0803dnM742a.m747a("ReportLocation", (C0803dn.a) this);
/* 58 */            C0827el.m885a(4, f733c, "initSettings, ReportLocation = " + this.f736b);
                }

                /* JADX INFO: renamed from: a */
/* 61 */        public static synchronized C0812dw m781a() {
                    try {
                        if (f734o == null) {
/* 63 */                    f734o = new C0812dw();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 65 */            return f734o;
                }

                /* JADX INFO: renamed from: b */
/* 78 */        public synchronized void m790b() {
                    try {
                        if (this.f740g == null) {
/* 84 */                    this.f740g = (LocationManager) C0801dl.m732a().m736b().getSystemService("location");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 87 */        public synchronized void m791c() {
                    try {
                        C0827el.m885a(4, f733c, "Location provider subscribed");
/* 89 */                this.f745l++;
/* 90 */                if (!this.f735a && this.f746m < 3) {
/* 91 */                    m787j();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: d */
/* 95 */        public synchronized void m792d() {
                    try {
                        C0827el.m885a(4, f733c, "Location provider unsubscribed");
/* 98 */                if (this.f745l <= 0) {
/* 99 */                    C0827el.m885a(6, f733c, "Error! Unsubscribed too many times!");
                        } else {
/* 104 */                   this.f745l--;
/* 106 */                   if (this.f745l == 0) {
/* 107 */                       m786i();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 119 */       public void m789a(float f, float f2) {
/* 120 */           this.f747n = new Location("Explicit");
/* 121 */           this.f747n.setLatitude(f);
/* 122 */           this.f747n.setLongitude(f2);
                }

                /* JADX INFO: renamed from: e */
/* 125 */       public void m793e() {
/* 126 */           this.f747n = null;
                }

                /* JADX INFO: renamed from: i */
/* 129 */       private void m786i() {
/* 130 */           this.f740g.removeUpdates(this.f743j);
/* 131 */           this.f735a = false;
/* 132 */           this.f746m = 0;
/* 133 */           C0827el.m885a(4, f733c, "LocationProvider stopped");
                }

                /* JADX INFO: renamed from: j */
/* 141 */       private void m787j() {
/* 142 */           if (this.f736b && this.f747n == null) {
/* 150 */               Context contextM736b = C0801dl.m732a().m736b();
/* 151 */               if (contextM736b.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 || contextM736b.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
/* 156 */                   m786i();
/* 158 */                   String strM788k = m788k();
/* 159 */                   m782a(strM788k);
/* 160 */                   this.f742i = m783b(strM788k);
/* 162 */                   this.f735a = true;
/* 163 */                   C0827el.m885a(4, f733c, "LocationProvider started");
                        }
                    }
                }

                /* JADX INFO: renamed from: k */
/* 166 */       private String m788k() {
                    String bestProvider;
/* 167 */           Criteria criteria = this.f741h;
/* 168 */           if (criteria == null) {
/* 169 */               criteria = new Criteria();
                    }
/* 173 */           if (TextUtils.isEmpty(this.f744k)) {
/* 174 */               bestProvider = this.f740g.getBestProvider(criteria, true);
                    } else {
/* 176 */               bestProvider = this.f744k;
                    }
/* 178 */           C0827el.m885a(4, f733c, "provider = " + bestProvider);
/* 179 */           return bestProvider;
                }

                /* JADX INFO: renamed from: a */
/* 182 */       private void m782a(String str) {
/* 183 */           if (!TextUtils.isEmpty(str)) {
/* 184 */               this.f740g.requestLocationUpdates(str, 10000L, 0.0f, this.f743j, Looper.getMainLooper());
                    }
                }

                /* JADX INFO: renamed from: b */
/* 189 */       private Location m783b(String str) {
/* 191 */           if (TextUtils.isEmpty(str)) {
/* 190 */               return null;
                    }
/* 192 */           return this.f740g.getLastKnownLocation(str);
                }

                /* JADX INFO: renamed from: f */
/* 197 */       public Location m794f() {
/* 198 */           Location location = null;
/* 200 */           if (this.f747n != null) {
/* 201 */               return this.f747n;
                    }
/* 204 */           if (this.f736b) {
/* 206 */               Location locationM783b = m783b(m788k());
/* 208 */               if (locationM783b != null) {
/* 209 */                   this.f742i = locationM783b;
                        }
/* 212 */               location = this.f742i;
                    }
/* 215 */           C0827el.m885a(4, f733c, "getLocation() = " + location);
/* 216 */           return location;
                }

                /* JADX INFO: renamed from: g */
/* 219 */       public void m795g() {
/* 220 */           this.f745l = 0;
/* 221 */           m786i();
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dw$a */
/* 223 */       class a implements LocationListener {
/* 224 */           public a() {
                    }

                    @Override // android.location.LocationListener
/* 231 */           public void onStatusChanged(String str, int i, Bundle bundle) {
                    }

                    @Override // android.location.LocationListener
/* 236 */           public void onProviderEnabled(String str) {
                    }

                    @Override // android.location.LocationListener
/* 241 */           public void onProviderDisabled(String str) {
                    }

                    @Override // android.location.LocationListener
/* 245 */           public void onLocationChanged(Location location) {
/* 246 */               if (location != null) {
/* 247 */                   C0812dw.m780a(C0812dw.this, location);
                        }
/* 250 */               if (C0812dw.m779a(C0812dw.this) >= 3) {
/* 251 */                   C0827el.m885a(4, C0812dw.m785h(), "Max location reports reached, stopping");
/* 252 */                   C0812dw.m784b(C0812dw.this);
                        }
                    }
                }

                @Override // com.flurry.sdk.C0803dn.a
                /* JADX INFO: renamed from: a */
/* 259 */       public void mo535a(String str, Object obj) {
/* 260 */           if (str.equals("LocationCriteria")) {
/* 261 */               this.f741h = (Criteria) obj;
/* 262 */               C0827el.m885a(4, f733c, "onSettingUpdate, LocationCriteria = " + this.f741h);
/* 264 */               if (this.f735a) {
/* 265 */                   m787j();
/* 285 */                   return;
                        }
/* 285 */               return;
                    }
/* 268 */           if (str.equals("ReportLocation")) {
/* 269 */               this.f736b = ((Boolean) obj).booleanValue();
/* 270 */               C0827el.m885a(4, f733c, "onSettingUpdate, ReportLocation = " + this.f736b);
/* 272 */               if (this.f736b) {
/* 273 */                   if (!this.f735a && this.f745l > 0) {
/* 274 */                       m787j();
/* 285 */                       return;
                            }
/* 285 */                   return;
                        }
/* 278 */               m786i();
/* 285 */               return;
                    }
/* 282 */           C0827el.m885a(6, f733c, "LocationProvider internal error! Had to be LocationCriteria or ReportLocation key.");
                }
            }
