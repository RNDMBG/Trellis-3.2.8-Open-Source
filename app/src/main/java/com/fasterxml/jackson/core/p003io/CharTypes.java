            package com.fasterxml.jackson.core.p003io;

            import java.util.Arrays;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 5 */     public final class CharTypes {
                private static final byte[] HEX_BYTES;
                private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
                static final int[] sHexValues;
                static final int[] sInputCodes;
                static final int[] sInputCodesComment;
                static final int[] sInputCodesJsNames;
                static final int[] sInputCodesUTF8;
                static final int[] sInputCodesUtf8JsNames;
                static final int[] sInputCodesWS;
                static final int[] sOutputEscapes128;

                static {
                    int i;
/* 10 */            int length = HEX_CHARS.length;
/* 11 */            HEX_BYTES = new byte[length];
/* 12 */            for (int i2 = 0; i2 < length; i2++) {
/* 13 */                HEX_BYTES[i2] = (byte) HEX_CHARS[i2];
                    }
/* 28 */            int[] iArr = new int[256];
/* 30 */            for (int i3 = 0; i3 < 32; i3++) {
/* 31 */                iArr[i3] = -1;
                    }
/* 34 */            iArr[34] = 1;
/* 35 */            iArr[92] = 1;
/* 36 */            sInputCodes = iArr;
/* 45 */            int[] iArr2 = new int[sInputCodes.length];
/* 46 */            System.arraycopy(sInputCodes, 0, iArr2, 0, iArr2.length);
/* 47 */            for (int i4 = 128; i4 < 256; i4++) {
/* 51 */                if ((i4 & 224) == 192) {
/* 52 */                    i = 2;
                        } else if ((i4 & 240) == 224) {
/* 54 */                    i = 3;
                        } else {
/* 57 */                    i = (i4 & 248) == 240 ? 4 : -1;
                        }
/* 62 */                iArr2[i4] = i;
                    }
/* 64 */            sInputCodesUTF8 = iArr2;
/* 75 */            int[] iArr3 = new int[256];
/* 77 */            Arrays.fill(iArr3, -1);
                    for (int i5 = 33; i5 < 256; i5++) {
/* 80 */                if (Character.isJavaIdentifierPart((char) i5)) {
/* 81 */                    iArr3[i5] = 0;
                        }
                    }
/* 87 */            iArr3[64] = 0;
/* 88 */            iArr3[35] = 0;
/* 89 */            iArr3[42] = 0;
/* 90 */            iArr3[45] = 0;
/* 91 */            iArr3[43] = 0;
/* 92 */            sInputCodesJsNames = iArr3;
/* 102 */           int[] iArr4 = new int[256];
/* 104 */           System.arraycopy(sInputCodesJsNames, 0, iArr4, 0, iArr4.length);
/* 105 */           Arrays.fill(iArr4, 128, 128, 0);
/* 106 */           sInputCodesUtf8JsNames = iArr4;
/* 115 */           int[] iArr5 = new int[256];
/* 117 */           System.arraycopy(sInputCodesUTF8, 128, iArr5, 128, 128);
/* 120 */           Arrays.fill(iArr5, 0, 32, -1);
/* 121 */           iArr5[9] = 0;
/* 122 */           iArr5[10] = 10;
/* 123 */           iArr5[13] = 13;
/* 124 */           iArr5[42] = 42;
/* 125 */           sInputCodesComment = iArr5;
/* 136 */           int[] iArr6 = new int[256];
/* 137 */           System.arraycopy(sInputCodesUTF8, 128, iArr6, 128, 128);
/* 142 */           Arrays.fill(iArr6, 0, 32, -1);
/* 143 */           iArr6[32] = 1;
/* 144 */           iArr6[9] = 1;
/* 145 */           iArr6[10] = 10;
/* 146 */           iArr6[13] = 13;
/* 147 */           iArr6[47] = 47;
/* 148 */           iArr6[35] = 35;
/* 149 */           sInputCodesWS = iArr6;
/* 158 */           int[] iArr7 = new int[128];
/* 160 */           for (int i6 = 0; i6 < 32; i6++) {
/* 162 */               iArr7[i6] = -1;
                    }
/* 167 */           iArr7[34] = 34;
/* 168 */           iArr7[92] = 92;
/* 170 */           iArr7[8] = 98;
/* 171 */           iArr7[9] = 116;
/* 172 */           iArr7[12] = 102;
/* 173 */           iArr7[10] = 110;
/* 174 */           iArr7[13] = 114;
/* 175 */           sOutputEscapes128 = iArr7;
/* 183 */           sHexValues = new int[128];
/* 185 */           Arrays.fill(sHexValues, -1);
/* 186 */           for (int i7 = 0; i7 < 10; i7++) {
/* 187 */               sHexValues[i7 + 48] = i7;
                    }
/* 189 */           for (int i8 = 0; i8 < 6; i8++) {
/* 190 */               sHexValues[i8 + 97] = i8 + 10;
/* 191 */               sHexValues[i8 + 65] = i8 + 10;
                    }
                }

/* 194 */       public static int[] getInputCodeLatin1() {
/* 195 */           return sInputCodes;
                }

/* 195 */       public static int[] getInputCodeUtf8() {
/* 196 */           return sInputCodesUTF8;
                }

/* 197 */       public static int[] getInputCodeLatin1JsNames() {
/* 198 */           return sInputCodesJsNames;
                }

/* 198 */       public static int[] getInputCodeUtf8JsNames() {
/* 199 */           return sInputCodesUtf8JsNames;
                }

/* 200 */       public static int[] getInputCodeComment() {
/* 201 */           return sInputCodesComment;
                }

/* 201 */       public static int[] getInputCodeWS() {
/* 202 */           return sInputCodesWS;
                }

/* 210 */       public static int[] get7BitOutputEscapes() {
/* 211 */           return sOutputEscapes128;
                }

/* 214 */       public static int charToHex(int i) {
/* 215 */           if (i > 127) {
                        return -1;
                    }
                    return sHexValues[i];
                }

/* 219 */       public static void appendQuoted(StringBuilder sb, String str) {
/* 220 */           int[] iArr = sOutputEscapes128;
/* 221 */           int length = iArr.length;
                    int length2 = str.length();
                    for (int i = 0; i < length2; i++) {
/* 223 */               char cCharAt = str.charAt(i);
/* 224 */               if (cCharAt >= length || iArr[cCharAt] == 0) {
/* 225 */                   sb.append(cCharAt);
                        } else {
/* 228 */                   sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
/* 229 */                   int i2 = iArr[cCharAt];
/* 230 */                   if (i2 < 0) {
/* 239 */                       sb.append('u');
/* 240 */                       sb.append('0');
/* 241 */                       sb.append('0');
/* 243 */                       sb.append(HEX_CHARS[cCharAt >> 4]);
/* 244 */                       sb.append(HEX_CHARS[cCharAt & 15]);
                            } else {
/* 246 */                       sb.append((char) i2);
                            }
                        }
                    }
                }

/* 252 */       public static char[] copyHexChars() {
/* 253 */           return (char[]) HEX_CHARS.clone();
                }

/* 257 */       public static byte[] copyHexBytes() {
/* 258 */           return (byte[]) HEX_BYTES.clone();
                }
            }
