            package org.scribe.services;

            import java.security.PrivateKey;
            import java.security.Signature;
            import java.security.SignatureException;
            import org.scribe.exceptions.OAuthSignatureException;
            
            
    public class RSASha1SignatureService implements SignatureService {
                private static final String METHOD = "RSA-SHA1";
                private static final String RSA_SHA1 = "SHA1withRSA";
                private static final String UTF8 = "UTF-8";
                private PrivateKey privateKey;

        public RSASha1SignatureService(PrivateKey privateKey) {
            this.privateKey = privateKey;
                }

                @Override 
        public String getSignature(String baseString, String apiSecret, String tokenSecret) {
                    try {
                Signature signature = Signature.getInstance(RSA_SHA1);
                signature.initSign(this.privateKey);
                signature.update(baseString.getBytes("UTF-8"));
                return bytesToBase64String(signature);
                    } catch (Exception e) {
                throw new OAuthSignatureException(baseString, e);
                    }
                }

        private String bytesToBase64String(Signature signature) throws SignatureException {
            return Base64Encoder.getInstance().encode(signature.sign());
                }

                @Override 
        public String getSignatureMethod() {
            return METHOD;
                }
            }
