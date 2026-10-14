            package com.google.android.gms.plus.model.people;

            import com.google.android.gms.common.data.C0890c;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            import com.google.android.gms.internal.C1091fv;
            import com.google.android.gms.internal.C1103gg;
            
            
            public final class PersonBuffer extends DataBuffer<Person> {

                
                private final C0890c<C1091fv> f2451tt;

                public PersonBuffer(C0891d dataHolder) {
                    super(dataHolder);
                    if (dataHolder.m1042aM() == null || !dataHolder.m1042aM().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
                        this.f2451tt = null;
                    } else {
                        this.f2451tt = new C0890c<>(dataHolder, C1091fv.CREATOR);
                    }
                }

                
                @Override 
                public Person get(int position) {
                    return this.f2451tt != null ? (Person) this.f2451tt.m1032p(position) : new C1103gg(this.f948jf, position);
                }

            }
