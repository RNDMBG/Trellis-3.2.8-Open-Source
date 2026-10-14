            package org.scribe.services;

            import org.scribe.exceptions.OAuthSignatureException;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class PlaintextSignatureService implements SignatureService {
                private static final String METHOD = "PLAINTEXT";

                @Override // org.scribe.services.SignatureService
/* 22 */        public String getSignature(String baseString, String apiSecret, String tokenSecret) {
                    try {
/* 23 */                Preconditions.checkEmptyString(apiSecret, "Api secret cant be null or empty string");
/* 24 */                return OAuthEncoder.encode(apiSecret) + '&' + OAuthEncoder.encode(tokenSecret);
                    } catch (Exception e) {
/* 28 */                throw new OAuthSignatureException(baseString, e);
                    }
                }

                @Override // org.scribe.services.SignatureService
/* 36 */        public String getSignatureMethod() {
/* 37 */            return METHOD;
                }
            }
