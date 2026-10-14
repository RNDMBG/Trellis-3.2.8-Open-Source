            package com.google.android.gms.internal;

            import com.google.ads.AdRequest;
            import com.google.ads.AdSize;
            import com.google.ads.mediation.MediationAdRequest;
            import java.util.Date;
            import java.util.HashSet;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bb */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0963bb {
                /* JADX INFO: renamed from: a */
                public static int m1290a(AdRequest.ErrorCode errorCode) {
                    switch (errorCode) {
                        case INVALID_REQUEST:
                            return 1;
                        case NETWORK_ERROR:
                            return 2;
                        case NO_FILL:
                            return 3;
                        default:
                            return 0;
                    }
                }

                /* JADX INFO: renamed from: a */
                public static int m1291a(AdRequest.Gender gender) {
                    switch (gender) {
                        case FEMALE:
                            return 2;
                        case MALE:
                            return 1;
                        default:
                            return 0;
                    }
                }

                /* JADX INFO: renamed from: a */
                public static AdSize m1292a(C1126x c1126x) {
                    return new AdSize(new com.google.android.gms.ads.AdSize(c1126x.width, c1126x.height, c1126x.f2163ew));
                }

                /* JADX INFO: renamed from: e */
                public static AdRequest.Gender m1293e(int i) {
                    switch (i) {
                        case 1:
                            return AdRequest.Gender.MALE;
                        case 2:
                            return AdRequest.Gender.FEMALE;
                        default:
                            return AdRequest.Gender.UNKNOWN;
                    }
                }

                /* JADX INFO: renamed from: e */
                public static MediationAdRequest m1294e(C1124v c1124v) {
                    return new MediationAdRequest(new Date(c1124v.f2159es), m1293e(c1124v.f2160et), c1124v.f2161eu != null ? new HashSet(c1124v.f2161eu) : null, c1124v.f2162ev);
                }

                /* JADX INFO: renamed from: f */
                public static final AdRequest.ErrorCode m1295f(int i) {
                    switch (i) {
                        case 1:
                            return AdRequest.ErrorCode.INVALID_REQUEST;
                        case 2:
                            return AdRequest.ErrorCode.NETWORK_ERROR;
                        case 3:
                            return AdRequest.ErrorCode.NO_FILL;
                        default:
                            return AdRequest.ErrorCode.INTERNAL_ERROR;
                    }
                }
            }
