            package com.google.android.gms.location;

            import android.os.Parcel;
            import android.os.SystemClock;
            import co.vine.android.util.CommonUtil;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.internal.C1027dl;
            
            
            public final class LocationRequest implements SafeParcelable {
                public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
                public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
                public static final int PRIORITY_HIGH_ACCURACY = 100;
                public static final int PRIORITY_LOW_POWER = 104;
                public static final int PRIORITY_NO_POWER = 105;

                
                private final int f2184iM;
                int mPriority;

                
                long f2185oC;

                
                long f2186oJ;

                
                long f2187oK;

                
                boolean f2188oL;

                
                int f2189oM;

                
                float f2190oN;

                public LocationRequest() {
                    this.f2184iM = 1;
                    this.mPriority = 102;
                    this.f2186oJ = CommonUtil.HOUR_MILLIS;
                    this.f2187oK = 600000L;
                    this.f2188oL = false;
                    this.f2185oC = Long.MAX_VALUE;
                    this.f2189oM = Integer.MAX_VALUE;
                    this.f2190oN = 0.0f;
                }

                LocationRequest(int versionCode, int priority, long interval, long fastestInterval, boolean explicitFastestInterval, long expireAt, int numUpdates, float smallestDisplacement) {
                    this.f2184iM = versionCode;
                    this.mPriority = priority;
                    this.f2186oJ = interval;
                    this.f2187oK = fastestInterval;
                    this.f2188oL = explicitFastestInterval;
                    this.f2185oC = expireAt;
                    this.f2189oM = numUpdates;
                    this.f2190oN = smallestDisplacement;
                }

                
                private static void m2187X(int i) {
                    switch (i) {
                        case 100:
                        case 102:
                        case 104:
                        case 105:
                            return;
                        case 101:
                        case 103:
                        default:
                            throw new IllegalArgumentException("invalid quality: " + i);
                    }
                }

                
                public static String m2188Y(int i) {
                    switch (i) {
                        case 100:
                            return "PRIORITY_HIGH_ACCURACY";
                        case 101:
                        case 103:
                        default:
                            return "???";
                        case 102:
                            return "PRIORITY_BALANCED_POWER_ACCURACY";
                        case 104:
                            return "PRIORITY_LOW_POWER";
                        case 105:
                            return "PRIORITY_NO_POWER";
                    }
                }

                
                private static void m2189a(float f) {
                    if (f < 0.0f) {
                        throw new IllegalArgumentException("invalid displacement: " + f);
                    }
                }

                public static LocationRequest create() {
                    return new LocationRequest();
                }

                
                private static void m2190h(long j) {
                    if (j < 0) {
                        throw new IllegalArgumentException("invalid interval: " + j);
                    }
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (!(object instanceof LocationRequest)) {
                        return false;
                    }
                    LocationRequest locationRequest = (LocationRequest) object;
                    return this.mPriority == locationRequest.mPriority && this.f2186oJ == locationRequest.f2186oJ && this.f2187oK == locationRequest.f2187oK && this.f2188oL == locationRequest.f2188oL && this.f2185oC == locationRequest.f2185oC && this.f2189oM == locationRequest.f2189oM && this.f2190oN == locationRequest.f2190oN;
                }

                public long getExpirationTime() {
                    return this.f2185oC;
                }

                public long getFastestInterval() {
                    return this.f2187oK;
                }

                public long getInterval() {
                    return this.f2186oJ;
                }

                public int getNumUpdates() {
                    return this.f2189oM;
                }

                public int getPriority() {
                    return this.mPriority;
                }

                public float getSmallestDisplacement() {
                    return this.f2190oN;
                }

                int getVersionCode() {
                    return this.f2184iM;
                }

                public int hashCode() {
                    return C1027dl.hashCode(Integer.valueOf(this.mPriority), Long.valueOf(this.f2186oJ), Long.valueOf(this.f2187oK), Boolean.valueOf(this.f2188oL), Long.valueOf(this.f2185oC), Integer.valueOf(this.f2189oM), Float.valueOf(this.f2190oN));
                }

                public LocationRequest setExpirationDuration(long millis) {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    if (millis > Long.MAX_VALUE - jElapsedRealtime) {
                        this.f2185oC = Long.MAX_VALUE;
                    } else {
                        this.f2185oC = jElapsedRealtime + millis;
                    }
                    if (this.f2185oC < 0) {
                        this.f2185oC = 0L;
                    }
                    return this;
                }

                public LocationRequest setExpirationTime(long millis) {
                    this.f2185oC = millis;
                    if (this.f2185oC < 0) {
                        this.f2185oC = 0L;
                    }
                    return this;
                }

                public LocationRequest setFastestInterval(long millis) {
                    m2190h(millis);
                    this.f2188oL = true;
                    this.f2187oK = millis;
                    return this;
                }

                public LocationRequest setInterval(long millis) {
                    m2190h(millis);
                    this.f2186oJ = millis;
                    if (!this.f2188oL) {
                        this.f2187oK = (long) (this.f2186oJ / 6.0d);
                    }
                    return this;
                }

                public LocationRequest setNumUpdates(int numUpdates) {
                    if (numUpdates <= 0) {
                        throw new IllegalArgumentException("invalid numUpdates: " + numUpdates);
                    }
                    this.f2189oM = numUpdates;
                    return this;
                }

                public LocationRequest setPriority(int priority) {
                    m2187X(priority);
                    this.mPriority = priority;
                    return this;
                }

                public LocationRequest setSmallestDisplacement(float smallestDisplacementMeters) {
                    m2189a(smallestDisplacementMeters);
                    this.f2190oN = smallestDisplacementMeters;
                    return this;
                }

                public String toString() {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Request[").append(m2188Y(this.mPriority));
                    if (this.mPriority != 105) {
                        sb.append(" requested=");
                        sb.append(this.f2186oJ + "ms");
                    }
                    sb.append(" fastest=");
                    sb.append(this.f2187oK + "ms");
                    if (this.f2185oC != Long.MAX_VALUE) {
                        long jElapsedRealtime = this.f2185oC - SystemClock.elapsedRealtime();
                        sb.append(" expireIn=");
                        sb.append(jElapsedRealtime + "ms");
                    }
                    if (this.f2189oM != Integer.MAX_VALUE) {
                        sb.append(" num=").append(this.f2189oM);
                    }
                    sb.append(']');
                    return sb.toString();
                }

                @Override 
                public void writeToParcel(Parcel parcel, int flags) {
                    LocationRequestCreator.m2191a(this, parcel, flags);
                }
            }
