            package org.scribe.services;

            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class DatatypeConverterEncoder extends Base64Encoder {
                @Override // org.scribe.services.Base64Encoder
/* 9 */         public String encode(byte[] bytes) {
/* 10 */            return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP);
                }

                @Override // org.scribe.services.Base64Encoder
/* 15 */        public String getType() {
/* 16 */            return "DatatypeConverter";
                }
            }
