            package com.google.android.gms.plus.model.people;

            import com.google.android.gms.common.data.C0890c;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            import com.google.android.gms.internal.C1091fv;
            import com.google.android.gms.internal.C1103gg;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class PersonBuffer extends DataBuffer<Person> {

                /* JADX INFO: renamed from: tt */
                private final C0890c<C1091fv> f2451tt;

                public PersonBuffer(C0891d dataHolder) {
                    super(dataHolder);
                    if (dataHolder.m1042aM() == null || !dataHolder.m1042aM().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
                        this.f2451tt = null;
                    } else {
                        this.f2451tt = new C0890c<>(dataHolder, C1091fv.CREATOR);
                    }
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.DataBuffer
                public Person get(int position) {
                    return this.f2451tt != null ? (Person) this.f2451tt.m1032p(position) : new C1103gg(this.f948jf, position);
                }

            }
