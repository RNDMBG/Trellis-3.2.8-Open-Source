            package co.vine.android.service;
            
            
     public class PostResult {
                public String captchaUrl;
                public boolean shouldRefreshSessionKey;
                public boolean success;

         public PostResult(boolean success) {
             this.success = success;
                }

        public PostResult(boolean success, boolean shouldRefreshSessionKey, String captchaUrl) {
            this.success = success;
            this.captchaUrl = captchaUrl;
            this.shouldRefreshSessionKey = shouldRefreshSessionKey;
                }
            }
