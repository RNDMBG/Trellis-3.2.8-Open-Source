            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.plus.model.moments.ItemScope;
            import com.google.android.gms.plus.model.moments.Moment;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fu */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1090fu extends AbstractC0889b implements Moment {

                /* JADX INFO: renamed from: sI */
                private C1088fs f2016sI;

                public C1090fu(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                /* JADX INFO: renamed from: dC */
                private C1088fs m2024dC() {
                    synchronized (this) {
                        try {
                            if (this.f2016sI == null) {
                                byte[] byteArray = getByteArray("momentImpl");
                                Parcel parcelObtain = Parcel.obtain();
                                parcelObtain.unmarshall(byteArray, 0, byteArray.length);
                                parcelObtain.setDataPosition(0);
                                this.f2016sI = C1088fs.CREATOR.m2022C(parcelObtain);
                                parcelObtain.recycle();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return this.f2016sI;
                }

                /* JADX INFO: renamed from: dB */
                public C1088fs m2025dB() {
                    return m2024dC();
                }

                @Override // com.google.android.gms.common.data.Freezable
                public /* synthetic */ Moment freeze() {
                    return m2025dB();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public String getId() {
                    return m2024dC().getId();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public ItemScope getResult() {
                    return m2024dC().getResult();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public String getStartDate() {
                    return m2024dC().getStartDate();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public ItemScope getTarget() {
                    return m2024dC().getTarget();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public String getType() {
                    return m2024dC().getType();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasId() {
                    return m2024dC().hasId();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasResult() {
                    return m2024dC().hasId();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasStartDate() {
                    return m2024dC().hasStartDate();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasTarget() {
                    return m2024dC().hasTarget();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasType() {
                    return m2024dC().hasType();
                }
            }
