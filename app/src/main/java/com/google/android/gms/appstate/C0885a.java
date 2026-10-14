            package com.google.android.gms.appstate;

            import com.google.android.gms.internal.C1027dl;
            
            /* JADX INFO: renamed from: com.google.android.gms.appstate.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0885a implements AppState {

                /* JADX INFO: renamed from: iu */
                private final int f916iu;

                /* JADX INFO: renamed from: iv */
                private final String f917iv;

                /* JADX INFO: renamed from: iw */
                private final byte[] f918iw;

                /* JADX INFO: renamed from: ix */
                private final boolean f919ix;

                /* JADX INFO: renamed from: iy */
                private final String f920iy;

                /* JADX INFO: renamed from: iz */
                private final byte[] f921iz;

                public C0885a(AppState appState) {
                    this.f916iu = appState.getKey();
                    this.f917iv = appState.getLocalVersion();
                    this.f918iw = appState.getLocalData();
                    this.f919ix = appState.hasConflict();
                    this.f920iy = appState.getConflictVersion();
                    this.f921iz = appState.getConflictData();
                }

                /* JADX INFO: renamed from: a */
                static int m997a(AppState appState) {
                    return C1027dl.hashCode(Integer.valueOf(appState.getKey()), appState.getLocalVersion(), appState.getLocalData(), Boolean.valueOf(appState.hasConflict()), appState.getConflictVersion(), appState.getConflictData());
                }

                /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: b */
                static String m999b(AppState appState) {
                    return C1027dl.m1599d(appState).m1600a("Key", Integer.valueOf(appState.getKey())).m1600a("LocalVersion", appState.getLocalVersion()).m1600a("LocalData", appState.getLocalData()).m1600a("HasConflict", Boolean.valueOf(appState.hasConflict())).m1600a("ConflictVersion", appState.getConflictVersion()).m1600a("ConflictData", appState.getConflictData()).toString();
                }

                /* JADX INFO: renamed from: aE */
                public AppState m1000aE() {
                    return this;
                }

                public boolean equals(Object obj) {
                    return m998a(this, obj);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public /* synthetic */ AppState freeze() {
                    return m1000aE();
                }

                @Override // com.google.android.gms.appstate.AppState
                public byte[] getConflictData() {
                    return this.f921iz;
                }

                @Override // com.google.android.gms.appstate.AppState
                public String getConflictVersion() {
                    return this.f920iy;
                }

                @Override // com.google.android.gms.appstate.AppState
                public int getKey() {
                    return this.f916iu;
                }

                @Override // com.google.android.gms.appstate.AppState
                public byte[] getLocalData() {
                    return this.f918iw;
                }

                @Override // com.google.android.gms.appstate.AppState
                public String getLocalVersion() {
                    return this.f917iv;
                }

                @Override // com.google.android.gms.appstate.AppState
                public boolean hasConflict() {
                    return this.f919ix;
                }

                public int hashCode() {
                    return m997a(this);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m999b(this);
                }
            }
