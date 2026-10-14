            package twitter4j.internal.http;

            import twitter4j.TwitterException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface HttpClient {
                HttpResponse request(HttpRequest httpRequest) throws TwitterException;

                void shutdown();
            }
