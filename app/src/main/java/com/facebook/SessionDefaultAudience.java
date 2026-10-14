            package com.facebook;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public enum SessionDefaultAudience {
                NONE(null),
                ONLY_ME("SELF"),
                FRIENDS("ALL_FRIENDS"),
                EVERYONE("EVERYONE");

                private final String nativeProtocolAudience;

/* 47 */        SessionDefaultAudience(String protocol) {
/* 49 */            this.nativeProtocolAudience = protocol;
                }

/* 52 */        String getNativeProtocolAudience() {
/* 53 */            return this.nativeProtocolAudience;
                }
            }
