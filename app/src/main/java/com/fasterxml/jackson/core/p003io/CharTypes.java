            package com.fasterxml.jackson.core.p003io;

            import java.util.Arrays;
            import org.apache.commons.io.IOUtils;
            
            
     public final class CharTypes {
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
            int length = HEX_CHARS.length;
            HEX_BYTES = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                HEX_BYTES[i2] = (byte) HEX_CHARS[i2];
                    }
            int[] iArr = new int[256];
            for (int i3 = 0; i3 < 32; i3++) {
                iArr[i3] = -1;
                    }
            iArr[34] = 1;
            iArr[92] = 1;
            sInputCodes = iArr;
            int[] iArr2 = new int[sInputCodes.length];
            System.arraycopy(sInputCodes, 0, iArr2, 0, iArr2.length);
            for (int i4 = 128; i4 < 256; i4++) {
                if ((i4 & 224) == 192) {
                    i = 2;
                        } else if ((i4 & 240) == 224) {
                    i = 3;
                        } else {
                    i = (i4 & 248) == 240 ? 4 : -1;
                        }
                iArr2[i4] = i;
                    }
            sInputCodesUTF8 = iArr2;
            int[] iArr3 = new int[256];
            Arrays.fill(iArr3, -1);
                    for (int i5 = 33; i5 < 256; i5++) {
                if (Character.isJavaIdentifierPart((char) i5)) {
                    iArr3[i5] = 0;
                        }
                    }
            iArr3[64] = 0;
            iArr3[35] = 0;
            iArr3[42] = 0;
            iArr3[45] = 0;
            iArr3[43] = 0;
            sInputCodesJsNames = iArr3;
           int[] iArr4 = new int[256];
           System.arraycopy(sInputCodesJsNames, 0, iArr4, 0, iArr4.length);
           Arrays.fill(iArr4, 128, 128, 0);
           sInputCodesUtf8JsNames = iArr4;
           int[] iArr5 = new int[256];
           System.arraycopy(sInputCodesUTF8, 128, iArr5, 128, 128);
           Arrays.fill(iArr5, 0, 32, -1);
           iArr5[9] = 0;
           iArr5[10] = 10;
           iArr5[13] = 13;
           iArr5[42] = 42;
           sInputCodesComment = iArr5;
           int[] iArr6 = new int[256];
           System.arraycopy(sInputCodesUTF8, 128, iArr6, 128, 128);
           Arrays.fill(iArr6, 0, 32, -1);
           iArr6[32] = 1;
           iArr6[9] = 1;
           iArr6[10] = 10;
           iArr6[13] = 13;
           iArr6[47] = 47;
           iArr6[35] = 35;
           sInputCodesWS = iArr6;
           int[] iArr7 = new int[128];
           for (int i6 = 0; i6 < 32; i6++) {
               iArr7[i6] = -1;
                    }
           iArr7[34] = 34;
           iArr7[92] = 92;
           iArr7[8] = 98;
           iArr7[9] = 116;
           iArr7[12] = 102;
           iArr7[10] = 110;
           iArr7[13] = 114;
           sOutputEscapes128 = iArr7;
           sHexValues = new int[128];
           Arrays.fill(sHexValues, -1);
           for (int i7 = 0; i7 < 10; i7++) {
               sHexValues[i7 + 48] = i7;
                    }
           for (int i8 = 0; i8 < 6; i8++) {
               sHexValues[i8 + 97] = i8 + 10;
               sHexValues[i8 + 65] = i8 + 10;
                    }
                }

       public static int[] getInputCodeLatin1() {
           return sInputCodes;
                }

       public static int[] getInputCodeUtf8() {
           return sInputCodesUTF8;
                }

       public static int[] getInputCodeLatin1JsNames() {
           return sInputCodesJsNames;
                }

       public static int[] getInputCodeUtf8JsNames() {
           return sInputCodesUtf8JsNames;
                }

       public static int[] getInputCodeComment() {
           return sInputCodesComment;
                }

       public static int[] getInputCodeWS() {
           return sInputCodesWS;
                }

       public static int[] get7BitOutputEscapes() {
           return sOutputEscapes128;
                }

       public static int charToHex(int i) {
           if (i > 127) {
                        return -1;
                    }
                    return sHexValues[i];
                }

       public static void appendQuoted(StringBuilder sb, String str) {
           int[] iArr = sOutputEscapes128;
           int length = iArr.length;
                    int length2 = str.length();
                    for (int i = 0; i < length2; i++) {
               char cCharAt = str.charAt(i);
               if (cCharAt >= length || iArr[cCharAt] == 0) {
                   sb.append(cCharAt);
                        } else {
                   sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                   int i2 = iArr[cCharAt];
                   if (i2 < 0) {
                       sb.append('u');
                       sb.append('0');
                       sb.append('0');
                       sb.append(HEX_CHARS[cCharAt >> 4]);
                       sb.append(HEX_CHARS[cCharAt & 15]);
                            } else {
                       sb.append((char) i2);
                            }
                        }
                    }
                }

       public static char[] copyHexChars() {
           return (char[]) HEX_CHARS.clone();
                }

       public static byte[] copyHexBytes() {
           return (byte[]) HEX_BYTES.clone();
                }
            }
