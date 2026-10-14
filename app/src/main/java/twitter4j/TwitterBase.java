            package twitter4j;

            import twitter4j.auth.Authorization;
            import twitter4j.conf.Configuration;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface TwitterBase {
                void createFriendship(long j) throws TwitterException;

                void createFriendship(String str) throws TwitterException;

                Authorization getAuthorization();

                Configuration getConfiguration();

                void shutdown();
            }
