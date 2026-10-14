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
            
            
    public class ConstantContactApi2 extends DefaultApi20 {
                private static final String AUTHORIZE_URL = "https://oauth2.constantcontact.com/oauth2/oauth/siteowner/authorize?client_id=%s&response_type=code&redirect_uri=%s";

                @Override 
        public String getAccessTokenEndpoint() {
            return "https://oauth2.constantcontact.com/oauth2/oauth/token?grant_type=authorization_code";
                }

                @Override 
        public String getAuthorizationUrl(OAuthConfig config) {
            return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
                }

                @Override 
        public Verb getAccessTokenVerb() {
            return Verb.POST;
                }

                
        class C13071 implements AccessTokenExtractor {
            C13071() {
                    }

                    @Override 
            public Token extract(String response) {
                Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");
                Matcher matcher = Pattern.compile("\"access_token\"\\s*:\\s*\"([^&\"]+)\"").matcher(response);
                if (matcher.find()) {
                    String token = OAuthEncoder.decode(matcher.group(1));
                    return new Token(token, "", response);
                        }
                throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
                    }
                }

                @Override 
        public AccessTokenExtractor getAccessTokenExtractor() {
            return new C13071();
                }
            }
