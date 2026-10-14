            package org.scribe.services;

            import javax.crypto.Mac;
            import javax.crypto.spec.SecretKeySpec;
            import org.scribe.exceptions.OAuthSignatureException;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class HMACSha1SignatureService implements SignatureService {
                private static final String CARRIAGE_RETURN = "\r\n";
                private static final String EMPTY_STRING = "";
                private static final String HMAC_SHA1 = "HmacSHA1";
                private static final String METHOD = "HMAC-SHA1";
                private static final String UTF8 = "UTF-8";

                @Override // org.scribe.services.SignatureService
/* 29 */        public String getSignature(String baseString, String apiSecret, String tokenSecret) {
                    try {
/* 30 */                Preconditions.checkEmptyString(baseString, "Base string cant be null or empty string");
/* 31 */                Preconditions.checkEmptyString(apiSecret, "Api secret cant be null or empty string");
/* 32 */                return doSign(baseString, OAuthEncoder.encode(apiSecret) + '&' + OAuthEncoder.encode(tokenSecret));
                    } catch (Exception e) {
/* 36 */                throw new OAuthSignatureException(baseString, e);
                    }
                }

/* 41 */        private String doSign(String toSign, String keyString) throws Exception {
/* 42 */            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), HMAC_SHA1);
/* 43 */            Mac mac = Mac.getInstance(HMAC_SHA1);
/* 44 */            mac.init(key);
/* 45 */            byte[] bytes = mac.doFinal(toSign.getBytes("UTF-8"));
/* 46 */            return bytesToBase64String(bytes).replace("\r\n", "");
                }

/* 50 */        private String bytesToBase64String(byte[] bytes) {
/* 51 */            return Base64Encoder.getInstance().encode(bytes);
                }

                @Override // org.scribe.services.SignatureService
/* 58 */        public String getSignatureMethod() {
/* 59 */            return METHOD;
                }
            }
