            package org.scribe.extractors;

            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.scribe.exceptions.OAuthException;
            import org.scribe.model.Token;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class JsonTokenExtractor implements AccessTokenExtractor {
                private Pattern accessTokenPattern = Pattern.compile("\"access_token\":\\s*\"(\\S*?)\"");

                @Override // org.scribe.extractors.AccessTokenExtractor
/* 14 */        public Token extract(String response) {
/* 15 */            Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");
/* 16 */            Matcher matcher = this.accessTokenPattern.matcher(response);
/* 17 */            if (matcher.find()) {
/* 19 */                return new Token(matcher.group(1), "", response);
                    }
/* 23 */            throw new OAuthException("Cannot extract an acces token. Response was: " + response);
                }
            }
