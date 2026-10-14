            package com.google.ads.mediation.customevent;

            import com.google.ads.mediation.MediationServerParameters;
            import com.google.android.gms.plus.PlusShare;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class CustomEventServerParameters extends MediationServerParameters {

                @MediationServerParameters.Parameter(name = "class_name", required = true)
                public String className;

                @MediationServerParameters.Parameter(name = PlusShare.KEY_CALL_TO_ACTION_LABEL, required = true)
                public String label;

                @MediationServerParameters.Parameter(name = "parameter", required = false)
                public String parameter = null;
            }
