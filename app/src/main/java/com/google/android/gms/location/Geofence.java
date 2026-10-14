            package com.google.android.gms.location;

            import android.os.SystemClock;
            import com.google.android.gms.internal.C1070fa;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Geofence {
                public static final int GEOFENCE_TRANSITION_DWELL = 4;
                public static final int GEOFENCE_TRANSITION_ENTER = 1;
                public static final int GEOFENCE_TRANSITION_EXIT = 2;
                public static final long NEVER_EXPIRE = -1;

                public static final class Builder {

                    /* JADX INFO: renamed from: oE */
                    private double f2178oE;

                    /* JADX INFO: renamed from: oF */
                    private double f2179oF;

                    /* JADX INFO: renamed from: oG */
                    private float f2180oG;

                    /* JADX INFO: renamed from: oA */
                    private String f2174oA = null;

                    /* JADX INFO: renamed from: oB */
                    private int f2175oB = 0;

                    /* JADX INFO: renamed from: oC */
                    private long f2176oC = Long.MIN_VALUE;

                    /* JADX INFO: renamed from: oD */
                    private short f2177oD = -1;

                    /* JADX INFO: renamed from: oH */
                    private int f2181oH = 0;

                    /* JADX INFO: renamed from: oI */
                    private int f2182oI = -1;

                    public Geofence build() {
                        if (this.f2174oA == null) {
                            throw new IllegalArgumentException("Request ID not set.");
                        }
                        if (this.f2175oB == 0) {
                            throw new IllegalArgumentException("Transitions types not set.");
                        }
                        if ((this.f2175oB & 4) != 0 && this.f2182oI < 0) {
                            throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
                        }
                        if (this.f2176oC == Long.MIN_VALUE) {
                            throw new IllegalArgumentException("Expiration not set.");
                        }
                        if (this.f2177oD == -1) {
                            throw new IllegalArgumentException("Geofence region not set.");
                        }
                        if (this.f2181oH < 0) {
                            throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
                        }
                        return new C1070fa(this.f2174oA, this.f2175oB, (short) 1, this.f2178oE, this.f2179oF, this.f2180oG, this.f2176oC, this.f2181oH, this.f2182oI);
                    }

                    public Builder setCircularRegion(double latitude, double longitude, float radius) {
                        this.f2177oD = (short) 1;
                        this.f2178oE = latitude;
                        this.f2179oF = longitude;
                        this.f2180oG = radius;
                        return this;
                    }

                    public Builder setExpirationDuration(long durationMillis) {
                        if (durationMillis < 0) {
                            this.f2176oC = -1L;
                        } else {
                            this.f2176oC = SystemClock.elapsedRealtime() + durationMillis;
                        }
                        return this;
                    }

                    public Builder setLoiteringDelay(int loiteringDelayMs) {
                        this.f2182oI = loiteringDelayMs;
                        return this;
                    }

                    public Builder setNotificationResponsiveness(int notificationResponsivenessMs) {
                        this.f2181oH = notificationResponsivenessMs;
                        return this;
                    }

                    public Builder setRequestId(String requestId) {
                        this.f2174oA = requestId;
                        return this;
                    }

                    public Builder setTransitionTypes(int transitionTypes) {
                        this.f2175oB = transitionTypes;
                        return this;
                    }
                }

                String getRequestId();
            }
