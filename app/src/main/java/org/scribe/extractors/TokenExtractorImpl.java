            package org.scribe.extractors;

            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.model.Token;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class TokenExtractorImpl implements RequestTokenExtractor, AccessTokenExtractor {
                private static final Pattern TOKEN_REGEX = Pattern.compile("oauth_token=([^&]+)");
                private static final Pattern SECRET_REGEX = Pattern.compile("oauth_token_secret=([^&]*)");

                @Override // org.scribe.extractors.RequestTokenExtractor, org.scribe.extractors.AccessTokenExtractor
/* 25 */        public Token extract(String response) {
/* 26 */            Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");
/* 27 */            String token = extract(response, TOKEN_REGEX);
/* 28 */            String secret = extract(response, SECRET_REGEX);
/* 29 */            return new Token(token, secret, response);
                }

/* 33 */        private String extract(String response, Pattern p) {
/* 34 */            Matcher matcher = p.matcher(response);
/* 35 */            if (matcher.find() && matcher.groupCount() >= 1) {
/* 37 */                return OAuthEncoder.decode(matcher.group(1));
                    }
/* 41 */            throw new OAuthException("Response body is incorrect. Can't extract token and secret from this: '" + response + "'", null);
                }
            }
