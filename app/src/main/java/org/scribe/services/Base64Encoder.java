            package org.scribe.services;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 7 */     public abstract class Base64Encoder {
                private static Base64Encoder instance;

                public abstract String encode(byte[] bArr);

                public abstract String getType();

/* 8 */         public static synchronized Base64Encoder getInstance() {
                    try {
                        if (instance == null) {
/* 11 */                    instance = createEncoderInstance();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 13 */            return instance;
                }

/* 17 */        private static Base64Encoder createEncoderInstance() {
                    return CommonsEncoder.isPresent() ? new CommonsEncoder() : new DatatypeConverterEncoder();
                }

/* 29 */        public static String type() {
/* 30 */            return getInstance().getType();
                }
            }
