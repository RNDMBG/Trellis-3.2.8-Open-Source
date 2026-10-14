            package okio;

            import com.flurry.android.Constants;
            import java.io.UnsupportedEncodingException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    final class Base64 {
                private static final byte[] MAP = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

/* 25 */        private Base64() {
                }

                /* JADX WARN: Code duplicated, block: B:66:0x00d8 A[PHI: r4 r12
  0x00d8: PHI (r4v2 'inCount' int) = (r4v1 'inCount' int), (r4v1 'inCount' int), (r4v1 'inCount' int), (r4v4 'inCount' int) binds: [B:43:0x0090, B:45:0x0094, B:47:0x0098, B:22:0x004e] A[DONT_GENERATE, DONT_INLINE]
  0x00d8: PHI (r12v4 'word' int) = (r12v1 'word' int), (r12v1 'word' int), (r12v1 'word' int), (r12v6 'word' int) binds: [B:43:0x0090, B:45:0x0094, B:47:0x0098, B:22:0x004e] A[DONT_GENERATE, DONT_INLINE]] */
/* 30 */        public static byte[] decode(String in) {
                    int outCount;
                    int outCount2 = 0;
                    int bits = 0;
/* 31 */            int limit = in.length();
/* 32 */            while (limit > 0) {
/* 33 */                char c = in.charAt(limit - 1);
/* 34 */                if (c != '=' && c != '\n' && c != '\r' && c != ' ' && c != '\t') {
                            break;
                        }
/* 32 */                limit--;
                    }
/* 40 */            byte[] out = new byte[(int) ((((long) limit) * 6) / 8)];
/* 42 */            int inCount = 0;
/* 44 */            int word = 0;
/* 45 */            int pos = 0;
/* 41 */            int outCount3 = 0;
                    while (pos < limit) {
/* 46 */                char c2 = in.charAt(pos);
/* 49 */                if (c2 >= 'A' && c2 <= 'Z') {
/* 53 */                    bits = c2 - 'A';
                        } else if (c2 >= 'a' && c2 <= 'z') {
/* 58 */                    bits = c2 - 'G';
                        } else if (c2 >= '0' && c2 <= '9') {
/* 63 */                    bits = c2 + 4;
                        } else if (c2 == '+') {
/* 65 */                    bits = 62;
                        } else {
/* 66 */                    if (c2 == '/') {
/* 67 */                        bits = 63;
                            } else if (c2 == '\n' || c2 == '\r' || c2 == ' ') {
                                outCount2 = outCount3;
                            } else {
                                if (c2 != '\t') {
/* 71 */                            return null;
                                }
                                outCount2 = outCount3;
                            }
/* 45 */                    pos++;
                            outCount3 = outCount2;
                        }
/* 75 */                word = (word << 6) | ((byte) bits);
/* 78 */                inCount++;
/* 79 */                if (inCount % 4 == 0) {
/* 80 */                    int outCount4 = outCount3 + 1;
                            out[outCount3] = (byte) (word >> 16);
/* 81 */                    int outCount5 = outCount4 + 1;
                            out[outCount4] = (byte) (word >> 8);
/* 82 */                    outCount2 = outCount5 + 1;
                            out[outCount5] = (byte) word;
                        } else {
                            outCount2 = outCount3;
                        }
/* 45 */                pos++;
                        outCount3 = outCount2;
                    }
/* 86 */            int lastWordChars = inCount % 4;
/* 87 */            if (lastWordChars == 1) {
/* 89 */                return null;
                    }
/* 90 */            if (lastWordChars == 2) {
/* 93 */                outCount = outCount3 + 1;
/* 92 */                out[outCount3] = (byte) ((word << 12) >> 16);
                    } else {
/* 94 */                if (lastWordChars == 3) {
/* 96 */                    int word2 = word << 6;
/* 97 */                    int outCount6 = outCount3 + 1;
                            out[outCount3] = (byte) (word2 >> 16);
/* 98 */                    outCount3 = outCount6 + 1;
                            out[outCount6] = (byte) (word2 >> 8);
                        }
                        outCount = outCount3;
                    }
/* 102 */           if (outCount != out.length) {
/* 105 */               byte[] prefix = new byte[outCount];
/* 106 */               System.arraycopy(out, 0, prefix, 0, outCount);
                        return prefix;
                    }
/* 107 */           return out;
                }

/* 117 */       public static String encode(byte[] in) {
                    int index;
/* 118 */           int length = ((in.length + 2) * 4) / 3;
/* 119 */           byte[] out = new byte[length];
                    int end = in.length - (in.length % 3);
/* 120 */           int index2 = 0;
                    for (int i = 0; i < end; i += 3) {
/* 122 */               int index3 = index2 + 1;
                        out[index2] = MAP[(in[i] & Constants.UNKNOWN) >> 2];
/* 123 */               int index4 = index3 + 1;
                        out[index3] = MAP[((in[i] & 3) << 4) | ((in[i + 1] & Constants.UNKNOWN) >> 4)];
/* 124 */               int index5 = index4 + 1;
                        out[index4] = MAP[((in[i + 1] & 15) << 2) | ((in[i + 2] & Constants.UNKNOWN) >> 6)];
/* 125 */               index2 = index5 + 1;
                        out[index5] = MAP[in[i + 2] & 63];
                    }
                    switch (in.length % 3) {
                        case 1:
/* 129 */                   int index6 = index2 + 1;
                            out[index2] = MAP[(in[end] & Constants.UNKNOWN) >> 2];
/* 130 */                   int index7 = index6 + 1;
                            out[index6] = MAP[(in[end] & 3) << 4];
/* 131 */                   int index8 = index7 + 1;
                            out[index7] = 61;
                            out[index8] = 61;
/* 132 */                   index = index8 + 1;
                            break;
                        case 2:
/* 135 */                   int index9 = index2 + 1;
                            out[index2] = MAP[(in[end] & Constants.UNKNOWN) >> 2];
/* 136 */                   int index10 = index9 + 1;
                            out[index9] = MAP[((in[end] & 3) << 4) | ((in[end + 1] & Constants.UNKNOWN) >> 4)];
/* 137 */                   int index11 = index10 + 1;
                            out[index10] = MAP[(in[end + 1] & 15) << 2];
/* 138 */                   index2 = index11 + 1;
                            out[index11] = 61;
                        default:
                            index = index2;
                            break;
                    }
                    try {
/* 142 */               return new String(out, 0, index, "US-ASCII");
                    } catch (UnsupportedEncodingException e) {
/* 144 */               throw new AssertionError(e);
                    }
                }
            }
