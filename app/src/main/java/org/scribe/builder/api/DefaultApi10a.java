            package org.scribe.builder.api;

            import org.scribe.extractors.AccessTokenExtractor;
            import org.scribe.extractors.BaseStringExtractor;
            import org.scribe.extractors.BaseStringExtractorImpl;
            import org.scribe.extractors.HeaderExtractor;
            import org.scribe.extractors.HeaderExtractorImpl;
            import org.scribe.extractors.RequestTokenExtractor;
            import org.scribe.extractors.TokenExtractorImpl;
            import org.scribe.model.OAuthConfig;
            import org.scribe.model.Token;
            import org.scribe.model.Verb;
            import org.scribe.oauth.OAuth10aServiceImpl;
            import org.scribe.oauth.OAuthService;
            import org.scribe.services.HMACSha1SignatureService;
            import org.scribe.services.SignatureService;
            import org.scribe.services.TimestampService;
            import org.scribe.services.TimestampServiceImpl;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public abstract class DefaultApi10a implements Api {
                public abstract String getAccessTokenEndpoint();

                public abstract String getAuthorizationUrl(Token token);

                public abstract String getRequestTokenEndpoint();

/* 32 */        public AccessTokenExtractor getAccessTokenExtractor() {
/* 33 */            return new TokenExtractorImpl();
                }

/* 42 */        public BaseStringExtractor getBaseStringExtractor() {
/* 43 */            return new BaseStringExtractorImpl();
                }

/* 52 */        public HeaderExtractor getHeaderExtractor() {
/* 53 */            return new HeaderExtractorImpl();
                }

/* 62 */        public RequestTokenExtractor getRequestTokenExtractor() {
/* 63 */            return new TokenExtractorImpl();
                }

/* 72 */        public SignatureService getSignatureService() {
/* 73 */            return new HMACSha1SignatureService();
                }

/* 82 */        public TimestampService getTimestampService() {
/* 83 */            return new TimestampServiceImpl();
                }

/* 92 */        public Verb getAccessTokenVerb() {
/* 93 */            return Verb.POST;
                }

/* 102 */       public Verb getRequestTokenVerb() {
/* 103 */           return Verb.POST;
                }

                @Override // org.scribe.builder.api.Api
/* 138 */       public OAuthService createService(OAuthConfig config) {
/* 139 */           return new OAuth10aServiceImpl(this, config);
                }
            }
