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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AdRequest {
                public static final String DEVICE_ID_EMULATOR = C1001cm.m1450l("emulator");
                public static final int ERROR_CODE_INTERNAL_ERROR = 0;
                public static final int ERROR_CODE_INVALID_REQUEST = 1;
                public static final int ERROR_CODE_NETWORK_ERROR = 2;
                public static final int ERROR_CODE_NO_FILL = 3;
                public static final int GENDER_FEMALE = 2;
                public static final int GENDER_MALE = 1;
                public static final int GENDER_UNKNOWN = 0;

                /* JADX INFO: renamed from: d */
                private final Date f880d;

                /* JADX INFO: renamed from: dI */
                private final int f881dI;

                /* JADX INFO: renamed from: dJ */
                private final Map<Class<? extends NetworkExtras>, NetworkExtras> f882dJ;

                /* JADX INFO: renamed from: dK */
                private final int f883dK;

                /* JADX INFO: renamed from: dL */
                private final Set<String> f884dL;

                /* JADX INFO: renamed from: f */
                private final Set<String> f885f;

                public static final class Builder {

                    /* JADX INFO: renamed from: d */
                    private Date f886d;

                    /* JADX INFO: renamed from: dM */
                    private final HashSet<String> f889dM = new HashSet<>();

                    /* JADX INFO: renamed from: dN */
                    private final HashMap<Class<? extends NetworkExtras>, NetworkExtras> f890dN = new HashMap<>();

                    /* JADX INFO: renamed from: dO */
                    private final HashSet<String> f891dO = new HashSet<>();

                    /* JADX INFO: renamed from: dI */
                    private int f887dI = -1;

                    /* JADX INFO: renamed from: dK */
                    private int f888dK = -1;

                    /* JADX INFO: renamed from: a */
                    static /* synthetic */ Date m984a(Builder builder) {
                        return builder.f886d;
                    }

                    /* JADX INFO: renamed from: b */
                    static /* synthetic */ int m985b(Builder builder) {
                        return builder.f887dI;
                    }

                    /* JADX INFO: renamed from: c */
                    static /* synthetic */ HashSet m986c(Builder builder) {
                        return builder.f889dM;
                    }

                    /* JADX INFO: renamed from: d */
                    static /* synthetic */ HashMap m987d(Builder builder) {
                        return builder.f890dN;
                    }

                    /* JADX INFO: renamed from: e */
                    static /* synthetic */ int m988e(Builder builder) {
                        return builder.f888dK;
                    }

                    /* JADX INFO: renamed from: f */
                    static /* synthetic */ HashSet m989f(Builder builder) {
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

                /* synthetic */ AdRequest(Builder x0, Object x1) {
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

                /* JADX INFO: renamed from: v */
                Map<Class<? extends NetworkExtras>, NetworkExtras> m982v() {
                    return this.f882dJ;
                }

                /* JADX INFO: renamed from: w */
                public int m983w() {
                    return this.f883dK;
                }
            }
