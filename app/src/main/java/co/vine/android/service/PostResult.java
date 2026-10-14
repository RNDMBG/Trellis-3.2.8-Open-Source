            package co.vine.android.service;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 6 */     public class PostResult {
                public String captchaUrl;
                public boolean shouldRefreshSessionKey;
                public boolean success;

/* 7 */         public PostResult(boolean success) {
/* 9 */             this.success = success;
                }

/* 11 */        public PostResult(boolean success, boolean shouldRefreshSessionKey, String captchaUrl) {
/* 13 */            this.success = success;
/* 14 */            this.captchaUrl = captchaUrl;
/* 15 */            this.shouldRefreshSessionKey = shouldRefreshSessionKey;
                }
            }
