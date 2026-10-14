            package com.flurry.sdk;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import java.util.Iterator;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.flurry.sdk.es */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class C0834es extends BroadcastReceiver {

                /* JADX INFO: renamed from: e */
                private static C0834es f825e;

                /* JADX INFO: renamed from: a */
                boolean f826a;

                /* JADX INFO: renamed from: b */
                Boolean f827b;

                /* JADX INFO: renamed from: d */
                private boolean f829d = false;

                /* JADX INFO: renamed from: c */
                private final C0806dq<InterfaceC0833er> f828c = new C0806dq<>();

                /* JADX INFO: renamed from: com.flurry.sdk.es$a */
/* 46 */        public enum a {
                    NONE_OR_UNKNOWN(0),
                    WIFI(1),
                    CELL(2);


                    /* JADX INFO: renamed from: d */
                    private int f834d;

/* 47 */            a(int i) {
/* 49 */                this.f834d = i;
                    }

                    /* JADX INFO: renamed from: a */
/* 52 */            public int m915a() {
/* 53 */                return this.f834d;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 61 */        public static synchronized C0834es m907a() {
                    try {
                        if (f825e == null) {
/* 63 */                    f825e = new C0834es();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 66 */            return f825e;
                }

                /* JADX INFO: renamed from: b */
/* 69 */        public synchronized void m911b() {
                    try {
                        Context contextM736b = C0801dl.m732a().m736b();
/* 71 */                this.f829d = contextM736b.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
/* 73 */                this.f826a = m908a(contextM736b);
/* 75 */                if (this.f829d) {
/* 76 */                    m913d();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 80 */        public synchronized void m910a(InterfaceC0833er interfaceC0833er) {
                    try {
                        this.f828c.m760a(interfaceC0833er);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: f */
/* 92 */        private synchronized List<InterfaceC0833er> m909f() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f828c.m759a();
                }

                /* JADX INFO: renamed from: c */
/* 112 */       public boolean m912c() {
                    return this.f827b != null ? this.f827b.booleanValue() : this.f826a;
                }

                /* JADX INFO: renamed from: d */
/* 120 */       void m913d() {
/* 121 */           Context contextM736b = C0801dl.m732a().m736b();
/* 122 */           this.f826a = m908a(contextM736b);
/* 123 */           contextM736b.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                }

                @Override // android.content.BroadcastReceiver
/* 132 */       public void onReceive(Context context, Intent intent) {
/* 133 */           boolean zM908a = m908a(context);
/* 135 */           if (this.f826a != zM908a) {
/* 136 */               this.f826a = zM908a;
/* 139 */               Iterator<InterfaceC0833er> it = m909f().iterator();
                        while (it.hasNext()) {
/* 140 */                   it.next().mo581a(this.f826a);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 146 */       private boolean m908a(Context context) {
/* 147 */           if (!this.f829d || context == null) {
                        return true;
                    }
/* 155 */           NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
                }

                /* JADX INFO: renamed from: e */
/* 159 */       public a m914e() {
/* 160 */           if (!this.f829d) {
/* 164 */               return a.NONE_OR_UNKNOWN;
                    }
/* 170 */           NetworkInfo activeNetworkInfo = ((ConnectivityManager) C0801dl.m732a().m736b().getSystemService("connectivity")).getActiveNetworkInfo();
/* 171 */           if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
/* 172 */               return a.NONE_OR_UNKNOWN;
                    }
                    switch (activeNetworkInfo.getType()) {
                        case 0:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
/* 187 */                   return a.CELL;
                        case 1:
                        case 9:
/* 178 */                   return a.WIFI;
                        case 8:
/* 190 */                   return a.NONE_OR_UNKNOWN;
                        default:
/* 193 */                   return a.CELL;
                    }
                }
            }
