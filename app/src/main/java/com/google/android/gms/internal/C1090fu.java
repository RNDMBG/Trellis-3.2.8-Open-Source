            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.plus.model.moments.ItemScope;
            import com.google.android.gms.plus.model.moments.Moment;
            
            
            
            public final class C1090fu extends AbstractC0889b implements Moment {

                
                private C1088fs f2016sI;

                public C1090fu(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                
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

                
                public C1088fs m2025dB() {
                    return m2024dC();
                }

                @Override 
                public  Moment freeze() {
                    return m2025dB();
                }

                @Override 
                public String getId() {
                    return m2024dC().getId();
                }

                @Override 
                public ItemScope getResult() {
                    return m2024dC().getResult();
                }

                @Override 
                public String getStartDate() {
                    return m2024dC().getStartDate();
                }

                @Override 
                public ItemScope getTarget() {
                    return m2024dC().getTarget();
                }

                @Override 
                public String getType() {
                    return m2024dC().getType();
                }

                @Override 
                public boolean hasId() {
                    return m2024dC().hasId();
                }

                @Override 
                public boolean hasResult() {
                    return m2024dC().hasId();
                }

                @Override 
                public boolean hasStartDate() {
                    return m2024dC().hasStartDate();
                }

                @Override 
                public boolean hasTarget() {
                    return m2024dC().hasTarget();
                }

                @Override 
                public boolean hasType() {
                    return m2024dC().hasType();
                }
            }
