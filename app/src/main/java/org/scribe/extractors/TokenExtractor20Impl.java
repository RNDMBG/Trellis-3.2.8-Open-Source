            package org.scribe.extractors;

            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.model.Token;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class TokenExtractor20Impl implements AccessTokenExtractor {
                private static final String EMPTY_SECRET = "";
                private static final String TOKEN_REGEX = "access_token=([^&]+)";

                @Override // org.scribe.extractors.AccessTokenExtractor
/* 22 */        public Token extract(String response) {
/* 23 */            Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");
/* 25 */            Matcher matcher = Pattern.compile(TOKEN_REGEX).matcher(response);
/* 26 */            if (matcher.find()) {
/* 28 */                String token = OAuthEncoder.decode(matcher.group(1));
/* 29 */                return new Token(token, "", response);
                    }
/* 33 */            throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
                }
            }
