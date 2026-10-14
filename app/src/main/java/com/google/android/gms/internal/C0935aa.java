            package com.google.android.gms.internal;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.text.TextUtils;
            import android.util.AttributeSet;
            import com.google.android.gms.C0881R;
            import com.google.android.gms.ads.AdSize;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.aa */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0935aa {

                /* JADX INFO: renamed from: c */
                private final AdSize f1138c;

                /* JADX INFO: renamed from: dV */
                private final String f1139dV;

                public C0935aa(Context context, AttributeSet attributeSet) {
                    TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attributeSet, C0881R.styleable.AdsAttrs);
                    String string = typedArrayObtainAttributes.getString(0);
                    if (TextUtils.isEmpty(string)) {
                        throw new IllegalArgumentException("Required XML attribute \"adSize\" was missing.");
                    }
                    this.f1138c = m1238f(string)[0];
                    this.f1139dV = typedArrayObtainAttributes.getString(1);
                    if (TextUtils.isEmpty(this.f1139dV)) {
                        throw new IllegalArgumentException("Required XML attribute \"adUnitId\" was missing.");
                    }
                }

                /* JADX INFO: renamed from: f */
                private static AdSize[] m1238f(String str) {
                    String[] strArrSplit = str.split("\\s*,\\s*");
                    AdSize[] adSizeArr = new AdSize[strArrSplit.length];
                    for (int i = 0; i < strArrSplit.length; i++) {
                        String strTrim = strArrSplit[i].trim();
                        if (strTrim.matches("^(\\d+|FULL_WIDTH)\\s*[xX]\\s*(\\d+|AUTO_HEIGHT)$")) {
                            String[] strArrSplit2 = strTrim.split("[xX]");
                            strArrSplit2[0] = strArrSplit2[0].trim();
                            strArrSplit2[1] = strArrSplit2[1].trim();
                            try {
                                adSizeArr[i] = new AdSize("FULL_WIDTH".equals(strArrSplit2[0]) ? -1 : Integer.parseInt(strArrSplit2[0]), "AUTO_HEIGHT".equals(strArrSplit2[1]) ? -2 : Integer.parseInt(strArrSplit2[1]));
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + strTrim);
                            }
                        } else if ("BANNER".equals(strTrim)) {
                            adSizeArr[i] = AdSize.BANNER;
                        } else if ("FULL_BANNER".equals(strTrim)) {
                            adSizeArr[i] = AdSize.FULL_BANNER;
                        } else if ("LEADERBOARD".equals(strTrim)) {
                            adSizeArr[i] = AdSize.LEADERBOARD;
                        } else if ("MEDIUM_RECTANGLE".equals(strTrim)) {
                            adSizeArr[i] = AdSize.MEDIUM_RECTANGLE;
                        } else if ("SMART_BANNER".equals(strTrim)) {
                            adSizeArr[i] = AdSize.SMART_BANNER;
                        } else {
                            if (!"WIDE_SKYSCRAPER".equals(strTrim)) {
                                throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + strTrim);
                            }
                            adSizeArr[i] = AdSize.WIDE_SKYSCRAPER;
                        }
                    }
                    if (adSizeArr.length == 0) {
                        throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + str);
                    }
                    return adSizeArr;
                }

                public AdSize getAdSize() {
                    return this.f1138c;
                }

                public String getAdUnitId() {
                    return this.f1139dV;
                }
            }
