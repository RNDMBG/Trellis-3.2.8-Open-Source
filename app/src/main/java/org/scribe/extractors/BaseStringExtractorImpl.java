            package org.scribe.extractors;

            import org.scribe.exceptions.OAuthParametersMissingException;
            import org.scribe.model.OAuthRequest;
            import org.scribe.model.ParameterList;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class BaseStringExtractorImpl implements BaseStringExtractor {
                private static final String AMPERSAND_SEPARATED_STRING = "%s&%s&%s";

                @Override // org.scribe.extractors.BaseStringExtractor
/* 22 */        public String extract(OAuthRequest request) {
/* 23 */            checkPreconditions(request);
/* 24 */            String verb = OAuthEncoder.encode(request.getVerb().name());
/* 25 */            String url = OAuthEncoder.encode(request.getSanitizedUrl());
/* 26 */            String params = getSortedAndEncodedParams(request);
/* 27 */            return String.format(AMPERSAND_SEPARATED_STRING, verb, url, params);
                }

/* 31 */        private String getSortedAndEncodedParams(OAuthRequest request) {
/* 32 */            ParameterList params = new ParameterList();
/* 33 */            params.addAll(request.getQueryStringParams());
/* 34 */            params.addAll(request.getBodyParams());
/* 35 */            params.addAll(new ParameterList(request.getOauthParameters()));
/* 36 */            return params.sort().asOauthBaseString();
                }

/* 40 */        private void checkPreconditions(OAuthRequest request) {
/* 41 */            Preconditions.checkNotNull(request, "Cannot extract base string from null object");
/* 43 */            if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0) {
/* 45 */                throw new OAuthParametersMissingException(request);
                    }
                }
            }
