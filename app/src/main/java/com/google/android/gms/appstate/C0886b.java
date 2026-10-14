            package com.google.android.gms.appstate;

            import co.vine.android.service.VineService;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            
            
            
            public final class C0886b extends AbstractC0889b implements AppState {
                C0886b(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                
                public AppState m1001aE() {
                    return new C0885a(this);
                }

                @Override 
                public boolean equals(Object obj) {
                    return C0885a.m998a(this, obj);
                }

                @Override 
                public  AppState freeze() {
                    return m1001aE();
                }

                @Override 
                public byte[] getConflictData() {
                    return getByteArray("conflict_data");
                }

                @Override 
                public String getConflictVersion() {
                    return getString("conflict_version");
                }

                @Override 
                public int getKey() {
                    return getInteger(VineService.EXTRA_KEY);
                }

                @Override 
                public byte[] getLocalData() {
                    return getByteArray("local_data");
                }

                @Override 
                public String getLocalVersion() {
                    return getString("local_version");
                }

                @Override 
                public boolean hasConflict() {
                    return !m1031v("conflict_version");
                }

                @Override 
                public int hashCode() {
                    return C0885a.m997a(this);
                }

                public String toString() {
                    return C0885a.m999b(this);
                }
            }
