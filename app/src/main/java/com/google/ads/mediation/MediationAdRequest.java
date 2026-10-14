            package com.google.ads.mediation;

            import android.location.Location;
            import com.google.ads.AdRequest;
            import java.util.Date;
            import java.util.Set;
            
            
            @Deprecated
            public final class MediationAdRequest {

                
                private final Date f856d;

                
                private final AdRequest.Gender f857e;

                
                private final Set<String> f858f;

                
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
