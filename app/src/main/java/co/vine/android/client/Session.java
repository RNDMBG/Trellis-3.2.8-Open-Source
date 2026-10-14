            package co.vine.android.client;

            import android.accounts.Account;
            import co.vine.android.api.VineUser;
            import com.flurry.android.FlurryAgent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class Session {
                private Account mAccount;
                private String mAvatarUrl;
                private long mLastLogin;
                private String mLoginEmail;
                private LoginStatus mLoginStatus = LoginStatus.LOGGED_OUT;
                private String mName;
                private String mSessionKey;
                private VineUser mUser;
                private long mUserId;

                public enum LoginStatus {
                    LOGGED_OUT,
                    LOGGING_IN,
                    LOGGED_IN,
                    LOGGING_OUT
                }

/* 25 */        public Session(String sessionKey, long userId, String name, String avatar, String edition) {
/* 27 */            this.mSessionKey = sessionKey;
/* 28 */            this.mUserId = userId;
/* 29 */            this.mName = name;
/* 31 */            this.mAvatarUrl = avatar;
                }

/* 34 */        public String getSessionKey() {
/* 35 */            return this.mSessionKey;
                }

/* 38 */        public void setSessionKey(String key) {
/* 39 */            this.mSessionKey = key;
                }

/* 42 */        public String getName() {
/* 43 */            return this.mName;
                }

/* 46 */        public long getUserId() {
/* 47 */            return this.mUserId;
                }

/* 50 */        public String getAvatarUrl() {
/* 51 */            return this.mAvatarUrl;
                }

/* 55 */        public void setAvatarUrl(String url) {
/* 56 */            this.mAvatarUrl = url;
                }

/* 59 */        public void setLoginStatus(LoginStatus loginStatus) {
/* 60 */            this.mLoginStatus = loginStatus;
                }

/* 63 */        public LoginStatus getLoginStatus() {
/* 64 */            return this.mLoginStatus;
                }

/* 67 */        public boolean isLoggedIn() {
/* 68 */            return this.mLoginStatus.equals(LoginStatus.LOGGED_IN);
                }

/* 75 */        public void setName(String login) {
/* 76 */            this.mName = login;
                }

/* 79 */        public void setUserId(long userId) {
/* 80 */            FlurryAgent.setUserId(String.valueOf(userId));
/* 81 */            this.mUserId = userId;
                }

/* 84 */        public String getLoginEmail() {
/* 85 */            return this.mLoginEmail;
                }

/* 88 */        public void setLoginEmail(String loginEmail) {
/* 89 */            this.mLoginEmail = loginEmail;
                }

/* 92 */        public void logout() {
/* 93 */            this.mLoginEmail = null;
/* 94 */            this.mSessionKey = null;
/* 95 */            this.mName = null;
/* 96 */            this.mUserId = 0L;
/* 97 */            this.mAvatarUrl = null;
/* 98 */            this.mLoginStatus = LoginStatus.LOGGED_OUT;
                }
            }
