            package co.vine.android.client;

            import android.accounts.Account;
            import co.vine.android.api.VineUser;
            import com.flurry.android.FlurryAgent;
            
            
    public class Session {
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

        public Session(String sessionKey, long userId, String name, String avatar, String edition) {
            this.mSessionKey = sessionKey;
            this.mUserId = userId;
            this.mName = name;
            this.mAvatarUrl = avatar;
                }

        public String getSessionKey() {
            return this.mSessionKey;
                }

        public void setSessionKey(String key) {
            this.mSessionKey = key;
                }

        public String getName() {
            return this.mName;
                }

        public long getUserId() {
            return this.mUserId;
                }

        public String getAvatarUrl() {
            return this.mAvatarUrl;
                }

        public void setAvatarUrl(String url) {
            this.mAvatarUrl = url;
                }

        public void setLoginStatus(LoginStatus loginStatus) {
            this.mLoginStatus = loginStatus;
                }

        public LoginStatus getLoginStatus() {
            return this.mLoginStatus;
                }

        public boolean isLoggedIn() {
            return this.mLoginStatus.equals(LoginStatus.LOGGED_IN);
                }

        public void setName(String login) {
            this.mName = login;
                }

        public void setUserId(long userId) {
            FlurryAgent.setUserId(String.valueOf(userId));
            this.mUserId = userId;
                }

        public String getLoginEmail() {
            return this.mLoginEmail;
                }

        public void setLoginEmail(String loginEmail) {
            this.mLoginEmail = loginEmail;
                }

        public void logout() {
            this.mLoginEmail = null;
            this.mSessionKey = null;
            this.mName = null;
            this.mUserId = 0L;
            this.mAvatarUrl = null;
            this.mLoginStatus = LoginStatus.LOGGED_OUT;
                }
            }
