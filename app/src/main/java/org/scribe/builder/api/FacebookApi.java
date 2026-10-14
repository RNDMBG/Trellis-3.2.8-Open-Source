            package org.scribe.builder.api;

            import org.scribe.model.OAuthConfig;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class FacebookApi extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s";
                private static final String SCOPED_AUTHORIZE_URL = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s";

                @Override // org.scribe.builder.api.DefaultApi20
/* 14 */        public String getAccessTokenEndpoint() {
/* 15 */            return "https://graph.facebook.com/oauth/access_token";
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 20 */        public String getAuthorizationUrl(OAuthConfig config) {
/* 21 */            Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Facebook does not support OOB");
                    return config.hasScope() ? String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope())) : String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }
            }
