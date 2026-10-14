            package com.google.android.gms.appstate;

            import co.vine.android.service.VineService;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: renamed from: com.google.android.gms.appstate.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0886b extends AbstractC0889b implements AppState {
                C0886b(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                /* JADX INFO: renamed from: aE */
                public AppState m1001aE() {
                    return new C0885a(this);
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public boolean equals(Object obj) {
                    return C0885a.m998a(this, obj);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public /* synthetic */ AppState freeze() {
                    return m1001aE();
                }

                @Override // com.google.android.gms.appstate.AppState
                public byte[] getConflictData() {
                    return getByteArray("conflict_data");
                }

                @Override // com.google.android.gms.appstate.AppState
                public String getConflictVersion() {
                    return getString("conflict_version");
                }

                @Override // com.google.android.gms.appstate.AppState
                public int getKey() {
                    return getInteger(VineService.EXTRA_KEY);
                }

                @Override // com.google.android.gms.appstate.AppState
                public byte[] getLocalData() {
                    return getByteArray("local_data");
                }

                @Override // com.google.android.gms.appstate.AppState
                public String getLocalVersion() {
                    return getString("local_version");
                }

                @Override // com.google.android.gms.appstate.AppState
                public boolean hasConflict() {
                    return !m1031v("conflict_version");
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public int hashCode() {
                    return C0885a.m997a(this);
                }

                public String toString() {
                    return C0885a.m999b(this);
                }
            }
