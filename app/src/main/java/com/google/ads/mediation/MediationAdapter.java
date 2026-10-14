            package com.google.ads.mediation;

            import com.google.ads.mediation.MediationServerParameters;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
            public interface MediationAdapter<ADDITIONAL_PARAMETERS, SERVER_PARAMETERS extends MediationServerParameters> {
                void destroy();

                Class<ADDITIONAL_PARAMETERS> getAdditionalParametersType();

                Class<SERVER_PARAMETERS> getServerParametersType();
            }
