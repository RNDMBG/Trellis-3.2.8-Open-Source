            package org.scribe.oauth;

            import org.scribe.model.OAuthRequest;
            import org.scribe.model.Token;
            import org.scribe.model.Verifier;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface OAuthService {
                Token getAccessToken(Token token, Verifier verifier);

                String getAuthorizationUrl(Token token);

                Token getRequestToken();

                String getVersion();

                void signRequest(Token token, OAuthRequest oAuthRequest);
            }
