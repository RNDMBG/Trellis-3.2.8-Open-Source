            package com.google.ads.mediation;

            import android.location.Location;
            import com.google.ads.AdRequest;
            import java.util.Date;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
            public final class MediationAdRequest {

                /* JADX INFO: renamed from: d */
                private final Date f856d;

                /* JADX INFO: renamed from: e */
                private final AdRequest.Gender f857e;

                /* JADX INFO: renamed from: f */
                private final Set<String> f858f;

                /* JADX INFO: renamed from: g */
                private final boolean f859g;

                public MediationAdRequest(Date birthday, AdRequest.Gender gender, Set<String> keywords, boolean isTesting) {
                    this.f856d = birthday;
                    this.f857e = gender;
                    this.f858f = keywords;
                    this.f859g = isTesting;
                }

                public Integer getAgeInYears() {
                    return null;
                }

                public Date getBirthday() {
                    return this.f856d;
                }

                public AdRequest.Gender getGender() {
                    return this.f857e;
                }

                public Set<String> getKeywords() {
                    return this.f858f;
                }

                public Location getLocation() {
                    return null;
                }

                public boolean isTesting() {
                    return this.f859g;
                }
            }
