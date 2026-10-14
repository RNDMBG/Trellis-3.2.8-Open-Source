            package org.scribe.exceptions;
            
            
    public class OAuthSignatureException extends OAuthException {
                private static final String MSG = "Error while signing string: %s";
                private static final long serialVersionUID = 1;

        public OAuthSignatureException(String stringToSign, Exception e) {
            super(String.format(MSG, stringToSign), e);
                }
            }
