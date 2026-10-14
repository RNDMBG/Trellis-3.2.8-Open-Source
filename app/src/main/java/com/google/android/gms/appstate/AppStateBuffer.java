            package com.google.android.gms.appstate;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            
            
            public final class AppStateBuffer extends DataBuffer<AppState> {
                public AppStateBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                
                @Override 
                public AppState get(int position) {
                    return new C0886b(this.f948jf, position);
                }

            }
