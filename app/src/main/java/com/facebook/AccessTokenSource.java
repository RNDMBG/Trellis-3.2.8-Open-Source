            package com.facebook;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public enum AccessTokenSource {
                NONE(false),
                FACEBOOK_APPLICATION_WEB(true),
                FACEBOOK_APPLICATION_NATIVE(true),
                FACEBOOK_APPLICATION_SERVICE(true),
                WEB_VIEW(false),
                TEST_USER(true),
                CLIENT_TOKEN(true);

                private final boolean canExtendToken;

/* 59 */        AccessTokenSource(boolean canExtendToken) {
/* 61 */            this.canExtendToken = canExtendToken;
                }

/* 64 */        boolean canExtendToken() {
/* 65 */            return this.canExtendToken;
                }
            }
