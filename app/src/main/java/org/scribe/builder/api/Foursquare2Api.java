            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.JsonTokenExtractor;
            import org.scribe.model.OAuthConfig;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class Foursquare2Api extends DefaultApi20 {
                private static final String AUTHORIZATION_URL = "https://foursquare.com/oauth2/authenticate?client_id=%s&response_type=code&redirect_uri=%s";

                @Override // org.scribe.builder.api.DefaultApi20
/* 13 */        public String getAccessTokenEndpoint() {
/* 14 */            return "https://foursquare.com/oauth2/access_token?grant_type=authorization_code";
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 19 */        public String getAuthorizationUrl(OAuthConfig config) {
/* 20 */            Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Foursquare2 does not support OOB");
/* 21 */            return String.format(AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 26 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 27 */            return new JsonTokenExtractor();
                }
            }
