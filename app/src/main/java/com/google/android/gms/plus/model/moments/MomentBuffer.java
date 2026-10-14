            package com.google.android.gms.plus.model.moments;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            import com.google.android.gms.internal.C1090fu;
            
            
            public final class MomentBuffer extends DataBuffer<Moment> {
                public MomentBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                
                @Override 
                public Moment get(int position) {
                    return new C1090fu(this.f948jf, position);
                }

            }
