            package org.scribe.extractors;

            import java.util.Map;
            import org.scribe.exceptions.OAuthParametersMissingException;
            import org.scribe.model.OAuthRequest;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            
    public class HeaderExtractorImpl implements HeaderExtractor {
                public static final int ESTIMATED_PARAM_LENGTH = 20;
                private static final String PARAM_SEPARATOR = ", ";
                private static final String PREAMBLE = "OAuth ";

                @Override 
        public String extract(OAuthRequest request) {
            checkPreconditions(request);
            Map<String, String> parameters = request.getOauthParameters();
            StringBuilder header = new StringBuilder(parameters.size() * 20);
            header.append(PREAMBLE);
                    for (Map.Entry<String, String> entry : parameters.entrySet()) {
                if (header.length() > PREAMBLE.length()) {
                    header.append(PARAM_SEPARATOR);
                        }
                header.append(String.format("%s=\"%s\"", entry.getKey(), OAuthEncoder.encode(entry.getValue())));
                    }
            return header.toString();
                }

        private void checkPreconditions(OAuthRequest request) {
            Preconditions.checkNotNull(request, "Cannot extract a header from a null object");
            if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0) {
                throw new OAuthParametersMissingException(request);
                    }
                }
            }
