            package org.scribe.builder.api;

            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.Token;
            import org.scribe.model.Verb;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class ConstantContactApi2 extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://oauth2.constantcontact.com/oauth2/oauth/siteowner/authorize?client_id=%s&response_type=code&redirect_uri=%s";

                @Override // org.scribe.builder.api.DefaultApi20
/* 15 */        public String getAccessTokenEndpoint() {
/* 16 */            return "https://oauth2.constantcontact.com/oauth2/oauth/token?grant_type=authorization_code";
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 21 */        public String getAuthorizationUrl(OAuthConfig config) {
/* 22 */            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 27 */        public Verb getAccessTokenVerb() {
/* 28 */            return Verb.POST;
                }

                /* JADX INFO: renamed from: org.scribe.builder.api.ConstantContactApi2$1 */
/* 33 */        class C13071 implements AccessTokenExtractor {
/* 34 */            C13071() {
                    }

                    @Override // org.scribe.extractors.AccessTokenExtractor
/* 38 */            public Token extract(String response) {
/* 39 */                Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");
/* 42 */                Matcher matcher = Pattern.compile("\"access_token\"\\s*:\\s*\"([^&\"]+)\"").matcher(response);
/* 43 */                if (matcher.find()) {
/* 45 */                    String token = OAuthEncoder.decode(matcher.group(1));
/* 46 */                    return new Token(token, "", response);
                        }
/* 50 */                throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
                    }
                }

                @Override // org.scribe.builder.api.DefaultApi20
/* 33 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 34 */            return new C13071();
                }
            }
