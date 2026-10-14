            package com.squareup.okhttp;

            import java.io.UnsupportedEncodingException;
            import okio.ByteString;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public final class Credentials {
/* 22 */        private Credentials() {
                }

/* 28 */        public static String basic(String userName, String password) {
                    try {
/* 29 */                String usernameAndPassword = userName + ":" + password;
/* 30 */                byte[] bytes = usernameAndPassword.getBytes("ISO-8859-1");
/* 31 */                String encoded = ByteString.m2468of(bytes).base64();
/* 32 */                return "Basic " + encoded;
                    } catch (UnsupportedEncodingException e) {
/* 34 */                throw new AssertionError();
                    }
                }
            }
