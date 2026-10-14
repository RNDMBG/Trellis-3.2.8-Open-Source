            package org.scribe.services;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import org.apache.commons.codec.binary.Base64;
            import org.scribe.exceptions.OAuthSignatureException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class CommonsEncoder extends Base64Encoder {
                @Override // org.scribe.services.Base64Encoder
/* 15 */        public String encode(byte[] bytes) {
                    try {
/* 16 */                return new String(Base64.encodeBase64(bytes), IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
/* 20 */                throw new OAuthSignatureException("Can't perform base64 encoding", e);
                    }
                }

                @Override // org.scribe.services.Base64Encoder
/* 26 */        public String getType() {
/* 27 */            return "CommonsCodec";
                }

/* 33 */        public static boolean isPresent() {
                    try {
/* 34 */                Class.forName("org.apache.commons.codec.binary.Base64");
/* 35 */                return true;
                    } catch (ClassNotFoundException e) {
/* 39 */                return false;
                    }
                }
            }
