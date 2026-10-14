            package com.facebook;
            
            
    public enum SessionLoginBehavior {
                SSO_WITH_FALLBACK(true, true),
                SSO_ONLY(true, false),
                SUPPRESS_SSO(false, true);

                private final boolean allowsKatanaAuth;
                private final boolean allowsWebViewAuth;

        SessionLoginBehavior(boolean allowsKatanaAuth, boolean allowsWebViewAuth) {
            this.allowsKatanaAuth = allowsKatanaAuth;
            this.allowsWebViewAuth = allowsWebViewAuth;
                }

        boolean allowsKatanaAuth() {
            return this.allowsKatanaAuth;
                }

        boolean allowsWebViewAuth() {
            return this.allowsWebViewAuth;
                }
            }
