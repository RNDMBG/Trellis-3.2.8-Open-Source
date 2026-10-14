            package twitter4j.internal.http;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class BASE64Encoder {
                private static final char last2byte = (char) Integer.parseInt("00000011", 2);
                private static final char last4byte = (char) Integer.parseInt("00001111", 2);
                private static final char last6byte = (char) Integer.parseInt("00111111", 2);
                private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
                private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
                private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
                private static final char[] encodeTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

/* 33 */        private BASE64Encoder() {
                }

/* 37 */        public static String encode(byte[] from) {
/* 38 */            StringBuilder to = new StringBuilder(((int) (((double) from.length) * 1.34d)) + 3);
/* 39 */            int num = 0;
/* 40 */            char currentByte = 0;
                    for (int i = 0; i < from.length; i++) {
/* 42 */                num %= 8;
/* 43 */                while (num < 8) {
                            switch (num) {
                                case 0:
/* 46 */                            char currentByte2 = (char) (from[i] & lead6byte);
/* 47 */                            currentByte = (char) (currentByte2 >>> 2);
                                    break;
                                case 2:
/* 50 */                            currentByte = (char) (from[i] & last6byte);
                                    break;
                                case 4:
/* 53 */                            char currentByte3 = (char) (from[i] & last4byte);
/* 54 */                            currentByte = (char) (currentByte3 << 2);
/* 55 */                            if (i + 1 < from.length) {
/* 56 */                                currentByte = (char) (((from[i + 1] & lead2byte) >>> 6) | currentByte);
                                    }
                                    break;
                                case 6:
/* 60 */                            char currentByte4 = (char) (from[i] & last2byte);
/* 61 */                            currentByte = (char) (currentByte4 << 4);
/* 62 */                            if (i + 1 < from.length) {
/* 63 */                                currentByte = (char) (((from[i + 1] & lead4byte) >>> 4) | currentByte);
                                    }
                                    break;
                            }
/* 67 */                    to.append(encodeTable[currentByte]);
/* 68 */                    num += 6;
                        }
                    }
/* 71 */            if (to.length() % 4 != 0) {
                        for (int i2 = 4 - (to.length() % 4); i2 > 0; i2--) {
/* 73 */                    to.append("=");
                        }
                    }
/* 76 */            return to.toString();
                }
            }
