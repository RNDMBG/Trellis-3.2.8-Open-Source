            package org.scribe.exceptions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class OAuthSignatureException extends OAuthException {
                private static final String MSG = "Error while signing string: %s";
                private static final long serialVersionUID = 1;

/* 20 */        public OAuthSignatureException(String stringToSign, Exception e) {
/* 21 */            super(String.format(MSG, stringToSign), e);
                }
            }
