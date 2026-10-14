            package org.scribe.builder.api;

            import java.util.Arrays;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.Set;
            import org.scribe.model.Token;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class LinkedInApi extends DefaultApi10a {
                private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authenticate?oauth_token=%s";
                private static final String REQUEST_TOKEN_URL = "https://api.linkedin.com/uas/oauth/requestToken";
                private final Set<String> scopes;

/* 13 */        public LinkedInApi() {
/* 15 */            this.scopes = Collections.emptySet();
                }

/* 18 */        public LinkedInApi(Set<String> scopes) {
/* 20 */            this.scopes = Collections.unmodifiableSet(scopes);
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 25 */        public String getAccessTokenEndpoint() {
/* 26 */            return "https://api.linkedin.com/uas/oauth/accessToken";
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 31 */        public String getRequestTokenEndpoint() {
                    return this.scopes.isEmpty() ? REQUEST_TOKEN_URL : "https://api.linkedin.com/uas/oauth/requestToken?scope=" + scopesAsString();
                }

/* 36 */        private String scopesAsString() {
/* 37 */            StringBuilder builder = new StringBuilder();
                    for (String scope : this.scopes) {
/* 40 */                builder.append("+" + scope);
                    }
/* 42 */            return builder.substring(1);
                }

                @Override // org.scribe.builder.api.DefaultApi10a
/* 47 */        public String getAuthorizationUrl(Token requestToken) {
/* 48 */            return String.format(AUTHORIZE_URL, requestToken.getToken());
                }

/* 52 */        public static LinkedInApi withScopes(String... scopes) {
/* 53 */            Set<String> scopeSet = new HashSet<>(Arrays.asList(scopes));
/* 54 */            return new LinkedInApi(scopeSet);
                }
            }
