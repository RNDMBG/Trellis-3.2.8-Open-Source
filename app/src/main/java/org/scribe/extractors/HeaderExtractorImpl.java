            package org.scribe.extractors;

            import java.util.Map;
            import org.scribe.exceptions.OAuthParametersMissingException;
            import org.scribe.model.OAuthRequest;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class HeaderExtractorImpl implements HeaderExtractor {
                public static final int ESTIMATED_PARAM_LENGTH = 20;
                private static final String PARAM_SEPARATOR = ", ";
                private static final String PREAMBLE = "OAuth ";

                @Override // org.scribe.extractors.HeaderExtractor
/* 25 */        public String extract(OAuthRequest request) {
/* 26 */            checkPreconditions(request);
/* 27 */            Map<String, String> parameters = request.getOauthParameters();
/* 28 */            StringBuilder header = new StringBuilder(parameters.size() * 20);
/* 29 */            header.append(PREAMBLE);
                    for (Map.Entry<String, String> entry : parameters.entrySet()) {
/* 32 */                if (header.length() > PREAMBLE.length()) {
/* 34 */                    header.append(PARAM_SEPARATOR);
                        }
/* 36 */                header.append(String.format("%s=\"%s\"", entry.getKey(), OAuthEncoder.encode(entry.getValue())));
                    }
/* 38 */            return header.toString();
                }

/* 42 */        private void checkPreconditions(OAuthRequest request) {
/* 43 */            Preconditions.checkNotNull(request, "Cannot extract a header from a null object");
/* 45 */            if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0) {
/* 47 */                throw new OAuthParametersMissingException(request);
                    }
                }
            }
