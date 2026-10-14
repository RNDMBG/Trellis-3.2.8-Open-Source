            package com.facebook;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class FacebookDialogException extends FacebookException {
                static final long serialVersionUID = 1;
                private int errorCode;
                private String failingUrl;

/* 30 */        public FacebookDialogException(String message, int errorCode, String failingUrl) {
/* 31 */            super(message);
/* 32 */            this.errorCode = errorCode;
/* 33 */            this.failingUrl = failingUrl;
                }

/* 42 */        public int getErrorCode() {
/* 43 */            return this.errorCode;
                }

/* 50 */        public String getFailingUrl() {
/* 51 */            return this.failingUrl;
                }
            }
