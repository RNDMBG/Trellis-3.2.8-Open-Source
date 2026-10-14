            package org.scribe.builder;

            import java.io.OutputStream;
            import org.scribe.builder.api.Api;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.OAuthConstants;
            import org.scribe.model.SignatureType;
            import org.scribe.oauth.OAuthService;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class ServiceBuilder {
                private Api api;
                private String apiKey;
                private String apiSecret;
                private String scope;
                private String callback = OAuthConstants.OUT_OF_BAND;
                private SignatureType signatureType = SignatureType.Header;
                private OutputStream debugStream = null;

/* 44 */        public ServiceBuilder provider(Class<? extends Api> apiClass) {
/* 45 */            this.api = createApi(apiClass);
/* 46 */            return this;
                }

/* 50 */        private Api createApi(Class<? extends Api> apiClass) {
/* 51 */            Preconditions.checkNotNull(apiClass, "Api class cannot be null");
                    try {
/* 55 */                Api api = apiClass.newInstance();
/* 61 */                return api;
                    } catch (Exception e) {
/* 59 */                throw new OAuthException("Error while creating the Api object", e);
                    }
                }

/* 73 */        public ServiceBuilder provider(Api api) {
/* 74 */            Preconditions.checkNotNull(api, "Api cannot be null");
/* 75 */            this.api = api;
/* 76 */            return this;
                }

/* 86 */        public ServiceBuilder callback(String callback) {
/* 87 */            Preconditions.checkNotNull(callback, "Callback can't be null");
/* 88 */            this.callback = callback;
/* 89 */            return this;
                }

/* 99 */        public ServiceBuilder apiKey(String apiKey) {
/* 100 */           Preconditions.checkEmptyString(apiKey, "Invalid Api key");
/* 101 */           this.apiKey = apiKey;
/* 102 */           return this;
                }

/* 112 */       public ServiceBuilder apiSecret(String apiSecret) {
/* 113 */           Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");
/* 114 */           this.apiSecret = apiSecret;
/* 115 */           return this;
                }

/* 125 */       public ServiceBuilder scope(String scope) {
/* 126 */           Preconditions.checkEmptyString(scope, "Invalid OAuth scope");
/* 127 */           this.scope = scope;
/* 128 */           return this;
                }

/* 138 */       public ServiceBuilder signatureType(SignatureType type) {
/* 139 */           Preconditions.checkNotNull(type, "Signature type can't be null");
/* 140 */           this.signatureType = type;
/* 141 */           return this;
                }

/* 145 */       public ServiceBuilder debugStream(OutputStream stream) {
/* 146 */           Preconditions.checkNotNull(stream, "debug stream can't be null");
/* 147 */           this.debugStream = stream;
/* 148 */           return this;
                }

/* 152 */       public ServiceBuilder debug() {
/* 153 */           debugStream(System.out);
/* 154 */           return this;
                }

/* 163 */       public OAuthService build() {
/* 164 */           Preconditions.checkNotNull(this.api, "You must specify a valid api through the provider() method");
/* 165 */           Preconditions.checkEmptyString(this.apiKey, "You must provide an api key");
/* 166 */           Preconditions.checkEmptyString(this.apiSecret, "You must provide an api secret");
/* 167 */           return this.api.createService(new OAuthConfig(this.apiKey, this.apiSecret, this.callback, this.signatureType, this.scope, this.debugStream));
                }
            }
