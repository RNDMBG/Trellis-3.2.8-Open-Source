            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.Bundle;
            import android.os.Parcel;
            import com.google.android.gms.ads.AdRequest;
            import com.google.android.gms.ads.mediation.admob.AdMobExtras;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Date;
            import java.util.List;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.v */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1124v implements SafeParcelable {
                public static final C1125w CREATOR = new C1125w();

                /* JADX INFO: renamed from: es */
                public final long f2159es;

                /* JADX INFO: renamed from: et */
                public final int f2160et;

                /* JADX INFO: renamed from: eu */
                public final List<String> f2161eu;

                /* JADX INFO: renamed from: ev */
                public final boolean f2162ev;
                public final Bundle extras;
                public final int tagForChildDirectedTreatment;
                public final int versionCode;

                C1124v(int i, long j, Bundle bundle, int i2, List<String> list, boolean z, int i3) {
                    this.versionCode = i;
                    this.f2159es = j;
                    this.extras = bundle;
                    this.f2160et = i2;
                    this.f2161eu = list;
                    this.f2162ev = z;
                    this.tagForChildDirectedTreatment = i3;
                }

                public C1124v(Context context, AdRequest adRequest) {
                    this.versionCode = 1;
                    Date birthday = adRequest.getBirthday();
                    this.f2159es = birthday != null ? birthday.getTime() : -1L;
                    this.f2160et = adRequest.getGender();
                    Set<String> keywords = adRequest.getKeywords();
                    this.f2161eu = !keywords.isEmpty() ? Collections.unmodifiableList(new ArrayList(keywords)) : null;
                    this.f2162ev = adRequest.isTestDevice(context);
                    this.tagForChildDirectedTreatment = adRequest.m983w();
                    AdMobExtras adMobExtras = (AdMobExtras) adRequest.getNetworkExtras(AdMobExtras.class);
                    this.extras = adMobExtras != null ? adMobExtras.getExtras() : null;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1125w.m2175a(this, out, flags);
                }
            }
