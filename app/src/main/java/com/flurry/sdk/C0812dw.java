            package com.flurry.sdk;

            import android.content.Context;
            import android.location.Criteria;
            import android.location.Location;
            import android.location.LocationListener;
            import android.location.LocationManager;
            import android.os.Bundle;
            import android.os.Looper;
            import android.text.TextUtils;
            
            
            
    public class C0812dw implements C0803dn.a {

                
                private static final String f733c = C0812dw.class.getSimpleName();

                
                private static C0812dw f734o;

                
                boolean f736b;

                
                private LocationManager f740g;

                
                private Criteria f741h;

                
                private Location f742i;

                
                private String f744k;

                
                private volatile Location f747n;

                
                private final int f737d = 3;

                
                private final long f738e = 10000;

                
                private final long f739f = 0;

                
                boolean f735a = false;

                
                private int f745l = 0;

                
                private int f746m = 0;

                
                private a f743j = new a();

                
        static  int m779a(C0812dw c0812dw) {
            int i = c0812dw.f746m + 1;
                    c0812dw.f746m = i;
                    return i;
                }

                
        static  Location m780a(C0812dw c0812dw, Location location) {
            c0812dw.f742i = location;
                    return location;
                }

                
        static  void m784b(C0812dw c0812dw) {
            c0812dw.m786i();
                }

                
        static  String m785h() {
            return f733c;
                }

        private C0812dw() {
            C0803dn c0803dnM742a = C0802dm.m742a();
            this.f741h = (Criteria) c0803dnM742a.m746a("LocationCriteria");
            c0803dnM742a.m747a("LocationCriteria", (C0803dn.a) this);
            C0827el.m885a(4, f733c, "initSettings, LocationCriteria = " + this.f741h);
            this.f736b = ((Boolean) c0803dnM742a.m746a("ReportLocation")).booleanValue();
            c0803dnM742a.m747a("ReportLocation", (C0803dn.a) this);
            C0827el.m885a(4, f733c, "initSettings, ReportLocation = " + this.f736b);
                }

                
        public static synchronized C0812dw m781a() {
                    try {
                        if (f734o == null) {
                    f734o = new C0812dw();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f734o;
                }

                
        public synchronized void m790b() {
                    try {
                        if (this.f740g == null) {
                    this.f740g = (LocationManager) C0801dl.m732a().m736b().getSystemService("location");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized void m791c() {
                    try {
                        C0827el.m885a(4, f733c, "Location provider subscribed");
                this.f745l++;
                if (!this.f735a && this.f746m < 3) {
                    m787j();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized void m792d() {
                    try {
                        C0827el.m885a(4, f733c, "Location provider unsubscribed");
                if (this.f745l <= 0) {
                    C0827el.m885a(6, f733c, "Error! Unsubscribed too many times!");
                        } else {
                   this.f745l--;
                   if (this.f745l == 0) {
                       m786i();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public void m789a(float f, float f2) {
           this.f747n = new Location("Explicit");
           this.f747n.setLatitude(f);
           this.f747n.setLongitude(f2);
                }

                
       public void m793e() {
           this.f747n = null;
                }

                
       private void m786i() {
           this.f740g.removeUpdates(this.f743j);
           this.f735a = false;
           this.f746m = 0;
           C0827el.m885a(4, f733c, "LocationProvider stopped");
                }

                
       private void m787j() {
           if (this.f736b && this.f747n == null) {
               Context contextM736b = C0801dl.m732a().m736b();
               if (contextM736b.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 || contextM736b.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                   m786i();
                   String strM788k = m788k();
                   m782a(strM788k);
                   this.f742i = m783b(strM788k);
                   this.f735a = true;
                   C0827el.m885a(4, f733c, "LocationProvider started");
                        }
                    }
                }

                
       private String m788k() {
                    String bestProvider;
           Criteria criteria = this.f741h;
           if (criteria == null) {
               criteria = new Criteria();
                    }
           if (TextUtils.isEmpty(this.f744k)) {
               bestProvider = this.f740g.getBestProvider(criteria, true);
                    } else {
               bestProvider = this.f744k;
                    }
           C0827el.m885a(4, f733c, "provider = " + bestProvider);
           return bestProvider;
                }

                
       private void m782a(String str) {
           if (!TextUtils.isEmpty(str)) {
               this.f740g.requestLocationUpdates(str, 10000L, 0.0f, this.f743j, Looper.getMainLooper());
                    }
                }

                
       private Location m783b(String str) {
           if (TextUtils.isEmpty(str)) {
               return null;
                    }
           return this.f740g.getLastKnownLocation(str);
                }

                
       public Location m794f() {
           Location location = null;
           if (this.f747n != null) {
               return this.f747n;
                    }
           if (this.f736b) {
               Location locationM783b = m783b(m788k());
               if (locationM783b != null) {
                   this.f742i = locationM783b;
                        }
               location = this.f742i;
                    }
           C0827el.m885a(4, f733c, "getLocation() = " + location);
           return location;
                }

                
       public void m795g() {
           this.f745l = 0;
           m786i();
                }

                
       class a implements LocationListener {
           public a() {
                    }

                    @Override 
           public void onStatusChanged(String str, int i, Bundle bundle) {
                    }

                    @Override 
           public void onProviderEnabled(String str) {
                    }

                    @Override 
           public void onProviderDisabled(String str) {
                    }

                    @Override 
           public void onLocationChanged(Location location) {
               if (location != null) {
                   C0812dw.m780a(C0812dw.this, location);
                        }
               if (C0812dw.m779a(C0812dw.this) >= 3) {
                   C0827el.m885a(4, C0812dw.m785h(), "Max location reports reached, stopping");
                   C0812dw.m784b(C0812dw.this);
                        }
                    }
                }

                @Override 
                
       public void mo535a(String str, Object obj) {
           if (str.equals("LocationCriteria")) {
               this.f741h = (Criteria) obj;
               C0827el.m885a(4, f733c, "onSettingUpdate, LocationCriteria = " + this.f741h);
               if (this.f735a) {
                   m787j();
                   return;
                        }
               return;
                    }
           if (str.equals("ReportLocation")) {
               this.f736b = ((Boolean) obj).booleanValue();
               C0827el.m885a(4, f733c, "onSettingUpdate, ReportLocation = " + this.f736b);
               if (this.f736b) {
                   if (!this.f735a && this.f745l > 0) {
                       m787j();
                       return;
                            }
                   return;
                        }
               m786i();
               return;
                    }
           C0827el.m885a(6, f733c, "LocationProvider internal error! Had to be LocationCriteria or ReportLocation key.");
                }
            }
