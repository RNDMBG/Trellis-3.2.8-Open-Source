            package org.scribe.oauth;

            import java.util.Map;
            import java.util.concurrent.TimeUnit;
            import org.scribe.builder.api.DefaultApi10a;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.OAuthConstants;
            import org.scribe.model.OAuthRequest;
            import org.scribe.model.Request;
            import org.scribe.model.RequestTuner;
            import org.scribe.model.Response;
            import org.scribe.model.Token;
            import org.scribe.model.Verifier;
            import org.scribe.services.Base64Encoder;
            import org.scribe.utils.MapUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class OAuth10aServiceImpl implements OAuthService {
                private static final String VERSION = "1.0";
                private DefaultApi10a api;
                private OAuthConfig config;

/* 29 */        public OAuth10aServiceImpl(DefaultApi10a api, OAuthConfig config) {
/* 31 */            this.api = api;
/* 32 */            this.config = config;
                }

/* 39 */        public Token getRequestToken(int timeout, TimeUnit unit) {
/* 40 */            return getRequestToken(new TimeoutTuner(timeout, unit));
                }

                @Override // org.scribe.oauth.OAuthService
/* 44 */        public Token getRequestToken() {
/* 45 */            return getRequestToken(2, TimeUnit.SECONDS);
                }

/* 49 */        public Token getRequestToken(RequestTuner tuner) {
/* 50 */            this.config.log("obtaining request token from " + this.api.getRequestTokenEndpoint());
/* 51 */            OAuthRequest request = new OAuthRequest(this.api.getRequestTokenVerb(), this.api.getRequestTokenEndpoint());
/* 53 */            this.config.log("setting oauth_callback to " + this.config.getCallback());
/* 54 */            request.addOAuthParameter(OAuthConstants.CALLBACK, this.config.getCallback());
/* 55 */            addOAuthParams(request, OAuthConstants.EMPTY_TOKEN);
/* 56 */            appendSignature(request);
/* 58 */            this.config.log("sending request...");
/* 59 */            Response response = request.send(tuner);
/* 60 */            String body = response.getBody();
/* 62 */            this.config.log("response status code: " + response.getCode());
/* 63 */            this.config.log("response body: " + body);
/* 64 */            return this.api.getRequestTokenExtractor().extract(body);
                }

/* 68 */        private void addOAuthParams(OAuthRequest request, Token token) {
/* 69 */            request.addOAuthParameter(OAuthConstants.TIMESTAMP, this.api.getTimestampService().getTimestampInSeconds());
/* 70 */            request.addOAuthParameter(OAuthConstants.NONCE, this.api.getTimestampService().getNonce());
/* 71 */            request.addOAuthParameter(OAuthConstants.CONSUMER_KEY, this.config.getApiKey());
/* 72 */            request.addOAuthParameter(OAuthConstants.SIGN_METHOD, this.api.getSignatureService().getSignatureMethod());
/* 73 */            request.addOAuthParameter(OAuthConstants.VERSION, getVersion());
/* 74 */            if (this.config.hasScope()) {
                        request.addOAuthParameter("scope", this.config.getScope());
                    }
/* 75 */            request.addOAuthParameter(OAuthConstants.SIGNATURE, getSignature(request, token));
/* 77 */            this.config.log("appended additional OAuth parameters: " + MapUtils.toString(request.getOauthParameters()));
                }

/* 84 */        public Token getAccessToken(Token requestToken, Verifier verifier, int timeout, TimeUnit unit) {
/* 85 */            return getAccessToken(requestToken, verifier, new TimeoutTuner(timeout, unit));
                }

                @Override // org.scribe.oauth.OAuthService
/* 89 */        public Token getAccessToken(Token requestToken, Verifier verifier) {
/* 90 */            return getAccessToken(requestToken, verifier, 2, TimeUnit.SECONDS);
                }

/* 94 */        public Token getAccessToken(Token requestToken, Verifier verifier, RequestTuner tuner) {
/* 95 */            this.config.log("obtaining access token from " + this.api.getAccessTokenEndpoint());
/* 96 */            OAuthRequest request = new OAuthRequest(this.api.getAccessTokenVerb(), this.api.getAccessTokenEndpoint());
/* 97 */            request.addOAuthParameter(OAuthConstants.TOKEN, requestToken.getToken());
/* 98 */            request.addOAuthParameter(OAuthConstants.VERIFIER, verifier.getValue());
/* 100 */           this.config.log("setting token to: " + requestToken + " and verifier to: " + verifier);
/* 101 */           addOAuthParams(request, requestToken);
/* 102 */           appendSignature(request);
/* 103 */           Response response = request.send(tuner);
/* 104 */           return this.api.getAccessTokenExtractor().extract(response.getBody());
                }

                @Override // org.scribe.oauth.OAuthService
/* 111 */       public void signRequest(Token token, OAuthRequest request) {
/* 112 */           this.config.log("signing request: " + request.getCompleteUrl());
/* 115 */           if (!token.isEmpty()) {
/* 117 */               request.addOAuthParameter(OAuthConstants.TOKEN, token.getToken());
                    }
/* 119 */           this.config.log("setting token to: " + token);
/* 120 */           addOAuthParams(request, token);
/* 121 */           appendSignature(request);
                }

                @Override // org.scribe.oauth.OAuthService
/* 128 */       public String getVersion() {
/* 129 */           return "1.0";
                }

                @Override // org.scribe.oauth.OAuthService
/* 136 */       public String getAuthorizationUrl(Token requestToken) {
/* 137 */           return this.api.getAuthorizationUrl(requestToken);
                }

/* 141 */       private String getSignature(OAuthRequest request, Token token) {
/* 142 */           this.config.log("generating signature...");
/* 143 */           this.config.log("using base64 encoder: " + Base64Encoder.type());
/* 144 */           String baseString = this.api.getBaseStringExtractor().extract(request);
/* 145 */           String signature = this.api.getSignatureService().getSignature(baseString, this.config.getApiSecret(), token.getSecret());
/* 147 */           this.config.log("base string is: " + baseString);
/* 148 */           this.config.log("signature is: " + signature);
/* 149 */           return signature;
                }

/* 153 */       private void appendSignature(OAuthRequest request) {
                    switch (this.config.getSignatureType()) {
                        case Header:
/* 157 */                   this.config.log("using Http Header signature");
/* 159 */                   String oauthHeader = this.api.getHeaderExtractor().extract(request);
/* 160 */                   request.addHeader(OAuthConstants.HEADER, oauthHeader);
                            break;
                        case QueryString:
/* 163 */                   this.config.log("using Querystring signature");
                            for (Map.Entry<String, String> entry : request.getOauthParameters().entrySet()) {
/* 167 */                       request.addQuerystringParameter(entry.getKey(), entry.getValue());
                            }
                            break;
                    }
                }

/* 177 */       private static class TimeoutTuner extends RequestTuner {
                    private final int duration;
                    private final TimeUnit unit;

/* 178 */           public TimeoutTuner(int duration, TimeUnit unit) {
/* 180 */               this.duration = duration;
/* 181 */               this.unit = unit;
                    }

                    @Override // org.scribe.model.RequestTuner
/* 186 */           public void tune(Request request) {
/* 187 */               request.setReadTimeout(this.duration, this.unit);
                    }
                }
            }
