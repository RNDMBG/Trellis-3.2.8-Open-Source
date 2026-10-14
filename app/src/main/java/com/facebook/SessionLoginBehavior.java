            package com.facebook;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public enum SessionLoginBehavior {
                SSO_WITH_FALLBACK(true, true),
                SSO_ONLY(true, false),
                SUPPRESS_SSO(false, true);

                private final boolean allowsKatanaAuth;
                private final boolean allowsWebViewAuth;

/* 46 */        SessionLoginBehavior(boolean allowsKatanaAuth, boolean allowsWebViewAuth) {
/* 48 */            this.allowsKatanaAuth = allowsKatanaAuth;
/* 49 */            this.allowsWebViewAuth = allowsWebViewAuth;
                }

/* 52 */        boolean allowsKatanaAuth() {
/* 53 */            return this.allowsKatanaAuth;
                }

/* 56 */        boolean allowsWebViewAuth() {
/* 57 */            return this.allowsWebViewAuth;
                }
            }
