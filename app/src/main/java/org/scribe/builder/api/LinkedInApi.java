            package org.scribe.builder.api;

            import java.util.Arrays;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.Set;
            import org.scribe.model.Token;
            
            
    public class LinkedInApi extends DefaultApi10a {
                private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authenticate?oauth_token=%s";
                private static final String REQUEST_TOKEN_URL = "https://api.linkedin.com/uas/oauth/requestToken";
                private final Set<String> scopes;

        public LinkedInApi() {
            this.scopes = Collections.emptySet();
                }

        public LinkedInApi(Set<String> scopes) {
            this.scopes = Collections.unmodifiableSet(scopes);
                }

                @Override 
        public String getAccessTokenEndpoint() {
            return "https://api.linkedin.com/uas/oauth/accessToken";
                }

                @Override 
        public String getRequestTokenEndpoint() {
                    return this.scopes.isEmpty() ? REQUEST_TOKEN_URL : "https://api.linkedin.com/uas/oauth/requestToken?scope=" + scopesAsString();
                }

        private String scopesAsString() {
            StringBuilder builder = new StringBuilder();
                    for (String scope : this.scopes) {
                builder.append("+" + scope);
                    }
            return builder.substring(1);
                }

                @Override 
        public String getAuthorizationUrl(Token requestToken) {
            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }

        public static LinkedInApi withScopes(String... scopes) {
            Set<String> scopeSet = new HashSet<>(Arrays.asList(scopes));
            return new LinkedInApi(scopeSet);
                }
            }
