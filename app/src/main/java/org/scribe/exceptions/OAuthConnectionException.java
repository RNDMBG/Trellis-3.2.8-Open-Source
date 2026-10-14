            package org.scribe.exceptions;
            
            
    public class OAuthConnectionException extends OAuthException {
                private static final String MSG = "There was a problem while creating a connection to the remote service.";

        public OAuthConnectionException(Exception e) {
            super(MSG, e);
                }
            }
