            package org.scribe.exceptions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class OAuthException extends RuntimeException {
                private static final long serialVersionUID = 1;

/* 18 */        public OAuthException(String message, Exception e) {
/* 19 */            super(message, e);
                }

/* 28 */        public OAuthException(String message) {
/* 29 */            super(message, null);
                }
            }
