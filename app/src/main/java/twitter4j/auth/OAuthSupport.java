            package twitter4j.auth;

            import twitter4j.TwitterException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface OAuthSupport {
                AccessToken getOAuthAccessToken() throws TwitterException;

                AccessToken getOAuthAccessToken(String str) throws TwitterException;

                AccessToken getOAuthAccessToken(String str, String str2) throws TwitterException;

                AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException;

                AccessToken getOAuthAccessToken(RequestToken requestToken, String str) throws TwitterException;

                RequestToken getOAuthRequestToken() throws TwitterException;

                RequestToken getOAuthRequestToken(String str) throws TwitterException;

                RequestToken getOAuthRequestToken(String str, String str2) throws TwitterException;

                void setOAuthAccessToken(AccessToken accessToken);

                void setOAuthConsumer(String str, String str2);
            }
