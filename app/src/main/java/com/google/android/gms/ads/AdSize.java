            package com.google.android.gms.ads;

            import android.content.Context;
            import com.google.android.gms.internal.C1001cm;
            import com.google.android.gms.internal.C1126x;
            import com.googlecode.javacv.cpp.avcodec;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AdSize {
                public static final int AUTO_HEIGHT = -2;
                public static final int FULL_WIDTH = -1;

                /* JADX INFO: renamed from: dP */
                private final int f892dP;

                /* JADX INFO: renamed from: dQ */
                private final int f893dQ;

                /* JADX INFO: renamed from: dR */
                private final String f894dR;
                public static final AdSize BANNER = new AdSize(320, 50, "320x50_mb");
                public static final AdSize FULL_BANNER = new AdSize(468, 60, "468x60_as");
                public static final AdSize LEADERBOARD = new AdSize(728, 90, "728x90_as");
                public static final AdSize MEDIUM_RECTANGLE = new AdSize(300, 250, "300x250_as");
                public static final AdSize WIDE_SKYSCRAPER = new AdSize(avcodec.AV_CODEC_ID_CDXL, 600, "160x600_as");
                public static final AdSize SMART_BANNER = new AdSize(-1, -2, "smart_banner");

                public AdSize(int width, int height) {
                    this(width, height, (width == -1 ? "FULL" : String.valueOf(width)) + "x" + (height == -2 ? "AUTO" : String.valueOf(height)) + "_as");
                }

                public AdSize(int width, int height, String formatString) {
                    if (width < 0 && width != -1) {
                        throw new IllegalArgumentException("Invalid width for AdSize: " + width);
                    }
                    if (height < 0 && height != -2) {
                        throw new IllegalArgumentException("Invalid height for AdSize: " + height);
                    }
                    this.f892dP = width;
                    this.f893dQ = height;
                    this.f894dR = formatString;
                }

                public boolean equals(Object other) {
                    if (other == this) {
                        return true;
                    }
                    if (!(other instanceof AdSize)) {
                        return false;
                    }
                    AdSize adSize = (AdSize) other;
                    return this.f892dP == adSize.f892dP && this.f893dQ == adSize.f893dQ && this.f894dR.equals(adSize.f894dR);
                }

                public int getHeight() {
                    return this.f893dQ;
                }

                public int getHeightInPixels(Context context) {
                    return this.f893dQ == -2 ? C1126x.m2179b(context.getResources().getDisplayMetrics()) : C1001cm.m1442a(context, this.f893dQ);
                }

                public int getWidth() {
                    return this.f892dP;
                }

                public int getWidthInPixels(Context context) {
                    return this.f892dP == -1 ? C1126x.m2178a(context.getResources().getDisplayMetrics()) : C1001cm.m1442a(context, this.f892dP);
                }

                public int hashCode() {
                    return this.f894dR.hashCode();
                }

                public boolean isAutoHeight() {
                    return this.f893dQ == -2;
                }

                public boolean isFullWidth() {
                    return this.f892dP == -1;
                }

                public String toString() {
                    return this.f894dR;
                }
            }
