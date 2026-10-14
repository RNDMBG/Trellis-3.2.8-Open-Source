            package org.scribe.model;

            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class OAuthRequest extends Request {
                private static final String OAUTH_PREFIX = "oauth_";
                private Map<String, String> oauthParameters;

/* 24 */        public OAuthRequest(Verb verb, String url) {
/* 25 */            super(verb, url);
/* 26 */            this.oauthParameters = new HashMap();
                }

/* 38 */        public void addOAuthParameter(String key, String value) {
/* 39 */            this.oauthParameters.put(checkKey(key), value);
                }

/* 43 */        private String checkKey(String key) {
/* 44 */            if (key.startsWith("oauth_") || key.equals("scope")) {
/* 46 */                return key;
                    }
/* 50 */            throw new IllegalArgumentException(String.format("OAuth parameters must either be '%s' or start with '%s'", "scope", "oauth_"));
                }

/* 60 */        public Map<String, String> getOauthParameters() {
/* 61 */            return this.oauthParameters;
                }

                @Override // org.scribe.model.Request
/* 66 */        public String toString() {
/* 67 */            return String.format("@OAuthRequest(%s, %s)", getVerb(), getUrl());
                }
            }
