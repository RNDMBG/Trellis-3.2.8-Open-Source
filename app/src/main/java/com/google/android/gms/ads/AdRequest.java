            package com.google.android.gms.ads;

            import android.content.Context;
            import com.google.android.gms.ads.mediation.NetworkExtras;
            import com.google.android.gms.internal.C1001cm;
            import java.util.Collections;
            import java.util.Date;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Map;
            import java.util.Set;
            
            
            public final class AdRequest {
                public static final String DEVICE_ID_EMULATOR = C1001cm.m1450l("emulator");
                public static final int ERROR_CODE_INTERNAL_ERROR = 0;
                public static final int ERROR_CODE_INVALID_REQUEST = 1;
                public static final int ERROR_CODE_NETWORK_ERROR = 2;
                public static final int ERROR_CODE_NO_FILL = 3;
                public static final int GENDER_FEMALE = 2;
                public static final int GENDER_MALE = 1;
                public static final int GENDER_UNKNOWN = 0;

                
                private final Date f880d;

                
                private final int f881dI;

                
                private final Map<Class<? extends NetworkExtras>, NetworkExtras> f882dJ;

                
                private final int f883dK;

                
                private final Set<String> f884dL;

                
                private final Set<String> f885f;

                public static final class Builder {

                    
                    private Date f886d;

                    
                    private final HashSet<String> f889dM = new HashSet<>();

                    
                    private final HashMap<Class<? extends NetworkExtras>, NetworkExtras> f890dN = new HashMap<>();

                    
                    private final HashSet<String> f891dO = new HashSet<>();

                    
                    private int f887dI = -1;

                    
                    private int f888dK = -1;

                    
                    static  Date m984a(Builder builder) {
                        return builder.f886d;
                    }

                    
                    static  int m985b(Builder builder) {
                        return builder.f887dI;
                    }

                    
                    static  HashSet m986c(Builder builder) {
                        return builder.f889dM;
                    }

                    
                    static  HashMap m987d(Builder builder) {
                        return builder.f890dN;
                    }

                    
                    static  int m988e(Builder builder) {
                        return builder.f888dK;
                    }

                    
                    static  HashSet m989f(Builder builder) {
                        return builder.f891dO;
                    }

                    public Builder addKeyword(String keyword) {
                        this.f889dM.add(keyword);
                        return this;
                    }

                    public Builder addNetworkExtras(NetworkExtras networkExtras) {
                        this.f890dN.put((Class<? extends NetworkExtras>) networkExtras.getClass(), networkExtras);
                        return this;
                    }

                    public Builder addTestDevice(String deviceId) {
                        this.f891dO.add(deviceId);
                        return this;
                    }

                    public AdRequest build() {
                        return new AdRequest(this, null);
                    }

                    public Builder setBirthday(Date birthday) {
                        this.f886d = birthday;
                        return this;
                    }

                    public Builder setGender(int gender) {
                        this.f887dI = gender;
                        return this;
                    }

                    public Builder tagForChildDirectedTreatment(boolean tagForChildDirectedTreatment) {
                        this.f888dK = tagForChildDirectedTreatment ? 1 : 0;
                        return this;
                    }
                }

                private AdRequest(Builder builder) {
                    this.f880d = Builder.m984a(builder);
                    this.f881dI = Builder.m985b(builder);
                    this.f885f = Collections.unmodifiableSet(Builder.m986c(builder));
                    this.f882dJ = Collections.unmodifiableMap(Builder.m987d(builder));
                    this.f883dK = Builder.m988e(builder);
                    this.f884dL = Collections.unmodifiableSet(Builder.m989f(builder));
                }

                 AdRequest(Builder x0, Object x1) {
                    this(x0);
                }

                public Date getBirthday() {
                    return this.f880d;
                }

                public int getGender() {
                    return this.f881dI;
                }

                public Set<String> getKeywords() {
                    return this.f885f;
                }

                public <T extends NetworkExtras> T getNetworkExtras(Class<T> networkExtrasClass) {
                    return (T) this.f882dJ.get(networkExtrasClass);
                }

                public boolean isTestDevice(Context context) {
                    return this.f884dL.contains(C1001cm.m1449l(context));
                }

                
                Map<Class<? extends NetworkExtras>, NetworkExtras> m982v() {
                    return this.f882dJ;
                }

                
                public int m983w() {
                    return this.f883dK;
                }
            }
