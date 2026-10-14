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
            
            
    public class OAuth10aServiceImpl implements OAuthService {
                private static final String VERSION = "1.0";
                private DefaultApi10a api;
                private OAuthConfig config;

        public OAuth10aServiceImpl(DefaultApi10a api, OAuthConfig config) {
            this.api = api;
            this.config = config;
                }

        public Token getRequestToken(int timeout, TimeUnit unit) {
            return getRequestToken(new TimeoutTuner(timeout, unit));
                }

                @Override 
        public Token getRequestToken() {
            return getRequestToken(2, TimeUnit.SECONDS);
                }

        public Token getRequestToken(RequestTuner tuner) {
            this.config.log("obtaining request token from " + this.api.getRequestTokenEndpoint());
            OAuthRequest request = new OAuthRequest(this.api.getRequestTokenVerb(), this.api.getRequestTokenEndpoint());
            this.config.log("setting oauth_callback to " + this.config.getCallback());
            request.addOAuthParameter(OAuthConstants.CALLBACK, this.config.getCallback());
            addOAuthParams(request, OAuthConstants.EMPTY_TOKEN);
            appendSignature(request);
            this.config.log("sending request...");
            Response response = request.send(tuner);
            String body = response.getBody();
            this.config.log("response status code: " + response.getCode());
            this.config.log("response body: " + body);
            return this.api.getRequestTokenExtractor().extract(body);
                }

        private void addOAuthParams(OAuthRequest request, Token token) {
            request.addOAuthParameter(OAuthConstants.TIMESTAMP, this.api.getTimestampService().getTimestampInSeconds());
            request.addOAuthParameter(OAuthConstants.NONCE, this.api.getTimestampService().getNonce());
            request.addOAuthParameter(OAuthConstants.CONSUMER_KEY, this.config.getApiKey());
            request.addOAuthParameter(OAuthConstants.SIGN_METHOD, this.api.getSignatureService().getSignatureMethod());
            request.addOAuthParameter(OAuthConstants.VERSION, getVersion());
            if (this.config.hasScope()) {
                        request.addOAuthParameter("scope", this.config.getScope());
                    }
            request.addOAuthParameter(OAuthConstants.SIGNATURE, getSignature(request, token));
            this.config.log("appended additional OAuth parameters: " + MapUtils.toString(request.getOauthParameters()));
                }

        public Token getAccessToken(Token requestToken, Verifier verifier, int timeout, TimeUnit unit) {
            return getAccessToken(requestToken, verifier, new TimeoutTuner(timeout, unit));
                }

                @Override 
        public Token getAccessToken(Token requestToken, Verifier verifier) {
            return getAccessToken(requestToken, verifier, 2, TimeUnit.SECONDS);
                }

        public Token getAccessToken(Token requestToken, Verifier verifier, RequestTuner tuner) {
            this.config.log("obtaining access token from " + this.api.getAccessTokenEndpoint());
            OAuthRequest request = new OAuthRequest(this.api.getAccessTokenVerb(), this.api.getAccessTokenEndpoint());
            request.addOAuthParameter(OAuthConstants.TOKEN, requestToken.getToken());
            request.addOAuthParameter(OAuthConstants.VERIFIER, verifier.getValue());
           this.config.log("setting token to: " + requestToken + " and verifier to: " + verifier);
           addOAuthParams(request, requestToken);
           appendSignature(request);
           Response response = request.send(tuner);
           return this.api.getAccessTokenExtractor().extract(response.getBody());
                }

                @Override 
       public void signRequest(Token token, OAuthRequest request) {
           this.config.log("signing request: " + request.getCompleteUrl());
           if (!token.isEmpty()) {
               request.addOAuthParameter(OAuthConstants.TOKEN, token.getToken());
                    }
           this.config.log("setting token to: " + token);
           addOAuthParams(request, token);
           appendSignature(request);
                }

                @Override 
       public String getVersion() {
           return "1.0";
                }

                @Override 
       public String getAuthorizationUrl(Token requestToken) {
           return this.api.getAuthorizationUrl(requestToken);
                }

       private String getSignature(OAuthRequest request, Token token) {
           this.config.log("generating signature...");
           this.config.log("using base64 encoder: " + Base64Encoder.type());
           String baseString = this.api.getBaseStringExtractor().extract(request);
           String signature = this.api.getSignatureService().getSignature(baseString, this.config.getApiSecret(), token.getSecret());
           this.config.log("base string is: " + baseString);
           this.config.log("signature is: " + signature);
           return signature;
                }

       private void appendSignature(OAuthRequest request) {
                    switch (this.config.getSignatureType()) {
                        case Header:
                   this.config.log("using Http Header signature");
                   String oauthHeader = this.api.getHeaderExtractor().extract(request);
                   request.addHeader(OAuthConstants.HEADER, oauthHeader);
                            break;
                        case QueryString:
                   this.config.log("using Querystring signature");
                            for (Map.Entry<String, String> entry : request.getOauthParameters().entrySet()) {
                       request.addQuerystringParameter(entry.getKey(), entry.getValue());
                            }
                            break;
                    }
                }

       private static class TimeoutTuner extends RequestTuner {
                    private final int duration;
                    private final TimeUnit unit;

           public TimeoutTuner(int duration, TimeUnit unit) {
               this.duration = duration;
               this.unit = unit;
                    }

                    @Override 
           public void tune(Request request) {
               request.setReadTimeout(this.duration, this.unit);
                    }
                }
            }
