            package org.scribe.exceptions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class OAuthConnectionException extends OAuthException {
                private static final String MSG = "There was a problem while creating a connection to the remote service.";

/* 11 */        public OAuthConnectionException(Exception e) {
/* 12 */            super(MSG, e);
                }
            }
