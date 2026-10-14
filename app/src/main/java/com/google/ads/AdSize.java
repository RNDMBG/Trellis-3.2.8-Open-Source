            package com.google.ads;

            import android.content.Context;
            import com.googlecode.javacv.cpp.avcodec;
            
            
            @Deprecated
            public final class AdSize {
                public static final int AUTO_HEIGHT = -2;
                public static final int FULL_WIDTH = -1;
                public static final int LANDSCAPE_AD_HEIGHT = 32;
                public static final int LARGE_AD_HEIGHT = 90;
                public static final int PORTRAIT_AD_HEIGHT = 50;

                
                private final com.google.android.gms.ads.AdSize f855c;
                public static final AdSize SMART_BANNER = new AdSize(-1, -2, "mb");
                public static final AdSize BANNER = new AdSize(320, 50, "mb");
                public static final AdSize IAB_MRECT = new AdSize(300, 250, "as");
                public static final AdSize IAB_BANNER = new AdSize(468, 60, "as");
                public static final AdSize IAB_LEADERBOARD = new AdSize(728, 90, "as");
                public static final AdSize IAB_WIDE_SKYSCRAPER = new AdSize(avcodec.AV_CODEC_ID_CDXL, 600, "as");

                public AdSize(int width, int height) {
                    this(new com.google.android.gms.ads.AdSize(width, height));
                }

                private AdSize(int width, int height, String type) {
                    this(new com.google.android.gms.ads.AdSize(width, height));
                }

                public AdSize(com.google.android.gms.ads.AdSize adSize) {
                    this.f855c = adSize;
                }

                public boolean equals(Object other) {
                    if (other instanceof AdSize) {
                        return this.f855c.equals(((AdSize) other).f855c);
                    }
                    return false;
                }

                
                public AdSize findBestSize(AdSize... options) {
                    float f;
                    AdSize adSize;
                    AdSize adSize2 = null;
                    if (options != null) {
                        float f2 = 0.0f;
                        int width = getWidth();
                        int height = getHeight();
                        int length = options.length;
                        int i = 0;
                        while (i < length) {
                            AdSize adSize3 = options[i];
                            int width2 = adSize3.getWidth();
                            int height2 = adSize3.getHeight();
                            if (isSizeAppropriate(width2, height2)) {
                                f = (width2 * height2) / (width * height);
                                if (f > 1.0f) {
                                    f = 1.0f / f;
                                }
                                if (f > f2) {
                                    adSize = adSize3;
                                } else {
                                    f = f2;
                                    adSize = adSize2;
                                }
                            } else {
                                f = f2;
                                adSize = adSize2;
                            }
                            i++;
                            adSize2 = adSize;
                            f2 = f;
                        }
                    }
                    return adSize2;
                }

                public int getHeight() {
                    return this.f855c.getHeight();
                }

                public int getHeightInPixels(Context context) {
                    return this.f855c.getHeightInPixels(context);
                }

                public int getWidth() {
                    return this.f855c.getWidth();
                }

                public int getWidthInPixels(Context context) {
                    return this.f855c.getWidthInPixels(context);
                }

                public int hashCode() {
                    return this.f855c.hashCode();
                }

                public boolean isAutoHeight() {
                    return this.f855c.isAutoHeight();
                }

                public boolean isCustomAdSize() {
                    return false;
                }

                public boolean isFullWidth() {
                    return this.f855c.isFullWidth();
                }

                public boolean isSizeAppropriate(int width, int height) {
                    int width2 = getWidth();
                    int height2 = getHeight();
                    return ((float) width) <= ((float) width2) * 1.25f && ((float) width) >= ((float) width2) * 0.8f && ((float) height) <= ((float) height2) * 1.25f && ((float) height) >= ((float) height2) * 0.8f;
                }

                public String toString() {
                    return this.f855c.toString();
                }
            }
