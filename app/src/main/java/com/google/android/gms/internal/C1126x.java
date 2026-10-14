            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.Parcel;
            import android.util.DisplayMetrics;
            import com.google.android.gms.ads.AdSize;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.x */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1126x implements SafeParcelable {
                public static final C1127y CREATOR = new C1127y();

                /* JADX INFO: renamed from: ew */
                public final String f2163ew;

                /* JADX INFO: renamed from: ex */
                public final boolean f2164ex;
                public final int height;
                public final int heightPixels;
                public final int versionCode;
                public final int width;
                public final int widthPixels;

                public C1126x() {
                    this(1, "interstitial_mb", 0, 0, true, 0, 0);
                }

                C1126x(int i, String str, int i2, int i3, boolean z, int i4, int i5) {
                    this.versionCode = i;
                    this.f2163ew = str;
                    this.height = i2;
                    this.heightPixels = i3;
                    this.f2164ex = z;
                    this.width = i4;
                    this.widthPixels = i5;
                }

                public C1126x(Context context, AdSize adSize) {
                    int i;
                    this.versionCode = 1;
                    this.f2164ex = false;
                    this.width = adSize.getWidth();
                    this.height = adSize.getHeight();
                    boolean z = this.width == -1;
                    boolean z2 = this.height == -2;
                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    if (z) {
                        this.widthPixels = m2178a(displayMetrics);
                        i = (int) (this.widthPixels / displayMetrics.density);
                    } else {
                        int i2 = this.width;
                        this.widthPixels = C1001cm.m1443a(displayMetrics, this.width);
                        i = i2;
                    }
                    int iM2180c = z2 ? m2180c(displayMetrics) : this.height;
                    this.heightPixels = C1001cm.m1443a(displayMetrics, iM2180c);
                    if (z || z2) {
                        this.f2163ew = i + "x" + iM2180c + "_as";
                    } else {
                        this.f2163ew = adSize.toString();
                    }
                }

                /* JADX INFO: renamed from: a */
                public static int m2178a(DisplayMetrics displayMetrics) {
                    return displayMetrics.widthPixels;
                }

                /* JADX INFO: renamed from: b */
                public static int m2179b(DisplayMetrics displayMetrics) {
                    return (int) (m2180c(displayMetrics) * displayMetrics.density);
                }

                /* JADX INFO: renamed from: c */
                private static int m2180c(DisplayMetrics displayMetrics) {
                    int i = (int) (displayMetrics.heightPixels / displayMetrics.density);
                    if (i <= 400) {
                        return 32;
                    }
                    return i <= 720 ? 50 : 90;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1127y.m2181a(this, out, flags);
                }
            }
