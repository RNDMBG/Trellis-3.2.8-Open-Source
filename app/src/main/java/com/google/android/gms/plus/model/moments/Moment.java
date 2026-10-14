            package com.google.android.gms.plus.model.moments;

            import com.google.android.gms.common.data.Freezable;
            import com.google.android.gms.internal.C1086fq;
            import com.google.android.gms.internal.C1088fs;
            import java.util.HashSet;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Moment extends Freezable<Moment> {

                public static class Builder {

                    /* JADX INFO: renamed from: rI */
                    private final Set<Integer> f2445rI = new HashSet();

                    /* JADX INFO: renamed from: sD */
                    private String f2446sD;

                    /* JADX INFO: renamed from: sG */
                    private C1086fq f2447sG;

                    /* JADX INFO: renamed from: sH */
                    private C1086fq f2448sH;

                    /* JADX INFO: renamed from: sm */
                    private String f2449sm;

                    /* JADX INFO: renamed from: sx */
                    private String f2450sx;

                    public Moment build() {
                        return new C1088fs(this.f2445rI, this.f2449sm, this.f2447sG, this.f2450sx, this.f2448sH, this.f2446sD);
                    }

                    public Builder setId(String id) {
                        this.f2449sm = id;
                        this.f2445rI.add(2);
                        return this;
                    }

                    public Builder setResult(ItemScope result) {
                        this.f2447sG = (C1086fq) result;
                        this.f2445rI.add(4);
                        return this;
                    }

                    public Builder setStartDate(String startDate) {
                        this.f2450sx = startDate;
                        this.f2445rI.add(5);
                        return this;
                    }

                    public Builder setTarget(ItemScope target) {
                        this.f2448sH = (C1086fq) target;
                        this.f2445rI.add(6);
                        return this;
                    }

                    public Builder setType(String type) {
                        this.f2446sD = type;
                        this.f2445rI.add(7);
                        return this;
                    }
                }

                String getId();

                ItemScope getResult();

                String getStartDate();

                ItemScope getTarget();

                String getType();

                boolean hasId();

                boolean hasResult();

                boolean hasStartDate();

                boolean hasTarget();

                boolean hasType();
            }
