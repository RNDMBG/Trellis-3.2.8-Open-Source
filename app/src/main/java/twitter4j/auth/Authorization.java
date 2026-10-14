            package twitter4j.auth;

            import java.io.Serializable;
            import twitter4j.internal.http.HttpRequest;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Authorization extends Serializable {
                String getAuthorizationHeader(HttpRequest httpRequest);

                boolean isEnabled();
            }
