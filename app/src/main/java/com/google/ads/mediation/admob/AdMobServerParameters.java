            package com.google.ads.mediation.admob;

            import com.google.ads.mediation.MediationServerParameters;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AdMobServerParameters extends MediationServerParameters {

                @MediationServerParameters.Parameter(name = "pubid")
                public String adUnitId;

                @MediationServerParameters.Parameter(name = "mad_hac", required = false)
                public String allowHouseAds = null;
                public int tagForChildDirectedTreatment = -1;
            }
