            package org.scribe.exceptions;

            import org.scribe.model.OAuthRequest;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class OAuthParametersMissingException extends OAuthException {
                private static final String MSG = "Could not find oauth parameters in request: %s. OAuth parameters must be specified with the addOAuthParameter() method";
                private static final long serialVersionUID = 1745308760111976671L;

/* 23 */        public OAuthParametersMissingException(OAuthRequest request) {
/* 24 */            super(String.format(MSG, request));
                }
            }
