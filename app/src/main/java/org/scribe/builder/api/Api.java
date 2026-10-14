            package org.scribe.builder.api;

            import org.scribe.model.OAuthConfig;
            import org.scribe.oauth.OAuthService;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Api {
                OAuthService createService(OAuthConfig oAuthConfig);
            }
