            package com.flurry.sdk;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import java.util.Iterator;
            import java.util.List;
            
            
            
    public class C0834es extends BroadcastReceiver {

                
                private static C0834es f825e;

                
                boolean f826a;

                
                Boolean f827b;

                
                private boolean f829d = false;

                
                private final C0806dq<InterfaceC0833er> f828c = new C0806dq<>();

                
        public enum a {
                    NONE_OR_UNKNOWN(0),
                    WIFI(1),
                    CELL(2);


                    
                    private int f834d;

            a(int i) {
                this.f834d = i;
                    }

                    
            public int m915a() {
                return this.f834d;
                    }
                }

                
        public static synchronized C0834es m907a() {
                    try {
                        if (f825e == null) {
                    f825e = new C0834es();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f825e;
                }

                
        public synchronized void m911b() {
                    try {
                        Context contextM736b = C0801dl.m732a().m736b();
                this.f829d = contextM736b.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
                this.f826a = m908a(contextM736b);
                if (this.f829d) {
                    m913d();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized void m910a(InterfaceC0833er interfaceC0833er) {
                    try {
                        this.f828c.m760a(interfaceC0833er);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        private synchronized List<InterfaceC0833er> m909f() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f828c.m759a();
                }

                
       public boolean m912c() {
                    return this.f827b != null ? this.f827b.booleanValue() : this.f826a;
                }

                
       void m913d() {
           Context contextM736b = C0801dl.m732a().m736b();
           this.f826a = m908a(contextM736b);
           contextM736b.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                }

                @Override 
       public void onReceive(Context context, Intent intent) {
           boolean zM908a = m908a(context);
           if (this.f826a != zM908a) {
               this.f826a = zM908a;
               Iterator<InterfaceC0833er> it = m909f().iterator();
                        while (it.hasNext()) {
                   it.next().mo581a(this.f826a);
                        }
                    }
                }

                
       private boolean m908a(Context context) {
           if (!this.f829d || context == null) {
                        return true;
                    }
           NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
                }

                
       public a m914e() {
           if (!this.f829d) {
               return a.NONE_OR_UNKNOWN;
                    }
           NetworkInfo activeNetworkInfo = ((ConnectivityManager) C0801dl.m732a().m736b().getSystemService("connectivity")).getActiveNetworkInfo();
           if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
               return a.NONE_OR_UNKNOWN;
                    }
                    switch (activeNetworkInfo.getType()) {
                        case 0:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                   return a.CELL;
                        case 1:
                        case 9:
                   return a.WIFI;
                        case 8:
                   return a.NONE_OR_UNKNOWN;
                        default:
                   return a.CELL;
                    }
                }
            }
