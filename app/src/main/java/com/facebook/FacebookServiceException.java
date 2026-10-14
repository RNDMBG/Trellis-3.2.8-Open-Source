            package com.facebook;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class FacebookServiceException extends FacebookException {
                private static final long serialVersionUID = 1;
                private final FacebookRequestError error;

/* 33 */        public FacebookServiceException(FacebookRequestError error, String errorMessage) {
/* 34 */            super(errorMessage);
/* 35 */            this.error = error;
                }

/* 43 */        public final FacebookRequestError getRequestError() {
/* 44 */            return this.error;
                }

                @Override // java.lang.Throwable
/* 48 */        public final String toString() {
/* 49 */            return "{FacebookServiceException: httpResponseCode: " + this.error.getRequestStatusCode() + ", facebookErrorCode: " + this.error.getErrorCode() + ", facebookErrorType: " + this.error.getErrorType() + ", message: " + this.error.getErrorMessage() + "}";
                }
            }
