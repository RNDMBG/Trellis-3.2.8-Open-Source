            package org.scribe.services;

            import javax.crypto.Mac;
            import javax.crypto.spec.SecretKeySpec;
            import org.scribe.exceptions.OAuthSignatureException;
            import org.scribe.utils.OAuthEncoder;
            import org.scribe.utils.Preconditions;
            
            
    public class HMACSha1SignatureService implements SignatureService {
                private static final String CARRIAGE_RETURN = "\r\n";
                private static final String EMPTY_STRING = "";
                private static final String HMAC_SHA1 = "HmacSHA1";
                private static final String METHOD = "HMAC-SHA1";
                private static final String UTF8 = "UTF-8";

                @Override 
        public String getSignature(String baseString, String apiSecret, String tokenSecret) {
                    try {
                Preconditions.checkEmptyString(baseString, "Base string cant be null or empty string");
                Preconditions.checkEmptyString(apiSecret, "Api secret cant be null or empty string");
                return doSign(baseString, OAuthEncoder.encode(apiSecret) + '&' + OAuthEncoder.encode(tokenSecret));
                    } catch (Exception e) {
                throw new OAuthSignatureException(baseString, e);
                    }
                }

        private String doSign(String toSign, String keyString) throws Exception {
            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(key);
            byte[] bytes = mac.doFinal(toSign.getBytes("UTF-8"));
            return bytesToBase64String(bytes).replace("\r\n", "");
                }

        private String bytesToBase64String(byte[] bytes) {
            return Base64Encoder.getInstance().encode(bytes);
                }

                @Override 
        public String getSignatureMethod() {
            return METHOD;
                }
            }
