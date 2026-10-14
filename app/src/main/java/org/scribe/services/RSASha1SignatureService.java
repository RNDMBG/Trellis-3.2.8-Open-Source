            package org.scribe.services;

            import java.security.PrivateKey;
            import java.security.Signature;
            import java.security.SignatureException;
            import org.scribe.exceptions.OAuthSignatureException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class RSASha1SignatureService implements SignatureService {
                private static final String METHOD = "RSA-SHA1";
                private static final String RSA_SHA1 = "SHA1withRSA";
                private static final String UTF8 = "UTF-8";
                private PrivateKey privateKey;

/* 17 */        public RSASha1SignatureService(PrivateKey privateKey) {
/* 19 */            this.privateKey = privateKey;
                }

                @Override // org.scribe.services.SignatureService
/* 28 */        public String getSignature(String baseString, String apiSecret, String tokenSecret) {
                    try {
/* 29 */                Signature signature = Signature.getInstance(RSA_SHA1);
/* 30 */                signature.initSign(this.privateKey);
/* 31 */                signature.update(baseString.getBytes("UTF-8"));
/* 32 */                return bytesToBase64String(signature);
                    } catch (Exception e) {
/* 36 */                throw new OAuthSignatureException(baseString, e);
                    }
                }

/* 41 */        private String bytesToBase64String(Signature signature) throws SignatureException {
/* 42 */            return Base64Encoder.getInstance().encode(signature.sign());
                }

                @Override // org.scribe.services.SignatureService
/* 49 */        public String getSignatureMethod() {
/* 50 */            return METHOD;
                }
            }
