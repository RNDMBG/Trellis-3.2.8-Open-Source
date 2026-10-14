            package com.google.android.gms.appstate;

            import com.google.android.gms.internal.C1027dl;
            
            
            
            public final class C0885a implements AppState {

                
                private final int f916iu;

                
                private final String f917iv;

                
                private final byte[] f918iw;

                
                private final boolean f919ix;

                
                private final String f920iy;

                
                private final byte[] f921iz;

                public C0885a(AppState appState) {
                    this.f916iu = appState.getKey();
                    this.f917iv = appState.getLocalVersion();
                    this.f918iw = appState.getLocalData();
                    this.f919ix = appState.hasConflict();
                    this.f920iy = appState.getConflictVersion();
                    this.f921iz = appState.getConflictData();
                }

                
                static int m997a(AppState appState) {
                    return C1027dl.hashCode(Integer.valueOf(appState.getKey()), appState.getLocalVersion(), appState.getLocalData(), Boolean.valueOf(appState.hasConflict()), appState.getConflictVersion(), appState.getConflictData());
                }

                
                static boolean m998a(AppState appState, Object obj) {
                    if (!(obj instanceof AppState)) {
                        return false;
                    }
                    if (appState == obj) {
                        return true;
                    }
                    AppState appState2 = (AppState) obj;
                    return C1027dl.equal(Integer.valueOf(appState2.getKey()), Integer.valueOf(appState.getKey())) && C1027dl.equal(appState2.getLocalVersion(), appState.getLocalVersion()) && C1027dl.equal(appState2.getLocalData(), appState.getLocalData()) && C1027dl.equal(Boolean.valueOf(appState2.hasConflict()), Boolean.valueOf(appState.hasConflict())) && C1027dl.equal(appState2.getConflictVersion(), appState.getConflictVersion()) && C1027dl.equal(appState2.getConflictData(), appState.getConflictData());
                }

                
                static String m999b(AppState appState) {
                    return C1027dl.m1599d(appState).m1600a("Key", Integer.valueOf(appState.getKey())).m1600a("LocalVersion", appState.getLocalVersion()).m1600a("LocalData", appState.getLocalData()).m1600a("HasConflict", Boolean.valueOf(appState.hasConflict())).m1600a("ConflictVersion", appState.getConflictVersion()).m1600a("ConflictData", appState.getConflictData()).toString();
                }

                
                public AppState m1000aE() {
                    return this;
                }

                public boolean equals(Object obj) {
                    return m998a(this, obj);
                }

                @Override 
                public  AppState freeze() {
                    return m1000aE();
                }

                @Override 
                public byte[] getConflictData() {
                    return this.f921iz;
                }

                @Override 
                public String getConflictVersion() {
                    return this.f920iy;
                }

                @Override 
                public int getKey() {
                    return this.f916iu;
                }

                @Override 
                public byte[] getLocalData() {
                    return this.f918iw;
                }

                @Override 
                public String getLocalVersion() {
                    return this.f917iv;
                }

                @Override 
                public boolean hasConflict() {
                    return this.f919ix;
                }

                public int hashCode() {
                    return m997a(this);
                }

                @Override 
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m999b(this);
                }
            }
