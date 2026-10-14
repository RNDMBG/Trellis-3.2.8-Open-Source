            package com.fasterxml.jackson.core.p003io;

            import java.math.BigDecimal;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public final class NumberInput {
                static final long L_BILLION = 1000000000;
                public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";
                static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
                static final String MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);

/* 29 */        public static int parseInt(char[] cArr, int i, int i2) {
/* 30 */            int i3 = cArr[i] - '0';
/* 31 */            int i4 = i2 + i;
/* 33 */            int i5 = i + 1;
                    if (i5 < i4) {
/* 34 */                int i6 = (i3 * 10) + (cArr[i5] - '0');
/* 35 */                int i7 = i5 + 1;
                        if (i7 < i4) {
/* 36 */                    int i8 = (i6 * 10) + (cArr[i7] - '0');
/* 37 */                    int i9 = i7 + 1;
                            if (i9 < i4) {
/* 38 */                        int i10 = (i8 * 10) + (cArr[i9] - '0');
/* 39 */                        int i11 = i9 + 1;
                                if (i11 < i4) {
/* 40 */                            int i12 = (i10 * 10) + (cArr[i11] - '0');
/* 41 */                            int i13 = i11 + 1;
                                    if (i13 < i4) {
/* 42 */                                int i14 = (i12 * 10) + (cArr[i13] - '0');
/* 43 */                                int i15 = i13 + 1;
                                        if (i15 < i4) {
/* 44 */                                    int i16 = (i14 * 10) + (cArr[i15] - '0');
/* 45 */                                    int i17 = i15 + 1;
                                            if (i17 < i4) {
/* 46 */                                        int i18 = (i16 * 10) + (cArr[i17] - '0');
/* 47 */                                        int i19 = i17 + 1;
                                                if (i19 < i4) {
/* 48 */                                            return (i18 * 10) + (cArr[i19] - '0');
                                                }
/* 57 */                                        return i18;
                                            }
/* 57 */                                    return i16;
                                        }
/* 57 */                                return i14;
                                    }
/* 57 */                            return i12;
                                }
/* 57 */                        return i10;
                            }
/* 57 */                    return i8;
                        }
/* 57 */                return i6;
                    }
/* 57 */            return i3;
                }

/* 69 */        public static int parseInt(String str) {
                    int i = 1;
/* 70 */            char cCharAt = str.charAt(0);
/* 71 */            int length = str.length();
/* 72 */            boolean z = cCharAt == '-';
/* 76 */            if (z) {
/* 77 */                if (length == 1 || length > 10) {
/* 78 */                    return Integer.parseInt(str);
                        }
                        cCharAt = str.charAt(1);
/* 80 */                i = 2;
                    } else if (length > 9) {
/* 83 */                return Integer.parseInt(str);
                    }
/* 86 */            if (cCharAt > '9' || cCharAt < '0') {
/* 87 */                return Integer.parseInt(str);
                    }
/* 89 */            int i2 = cCharAt - '0';
/* 90 */            if (i < length) {
/* 91 */                int i3 = i + 1;
                        char cCharAt2 = str.charAt(i);
/* 92 */                if (cCharAt2 > '9' || cCharAt2 < '0') {
/* 93 */                    return Integer.parseInt(str);
                        }
/* 95 */                i2 = (i2 * 10) + (cCharAt2 - '0');
/* 96 */                if (i3 < length) {
/* 97 */                    int i4 = i3 + 1;
                            char cCharAt3 = str.charAt(i3);
/* 98 */                    if (cCharAt3 > '9' || cCharAt3 < '0') {
/* 99 */                        return Integer.parseInt(str);
                            }
/* 101 */                   i2 = (i2 * 10) + (cCharAt3 - '0');
/* 103 */                   if (i4 < length) {
                                while (true) {
/* 105 */                           int i5 = i4 + 1;
                                    char cCharAt4 = str.charAt(i4);
/* 106 */                           if (cCharAt4 > '9' || cCharAt4 < '0') {
                                        break;
                                    }
/* 109 */                           i2 = (i2 * 10) + (cCharAt4 - '0');
/* 110 */                           if (i5 < length) {
                                        i4 = i5;
                                    }
                                }
/* 107 */                       return Integer.parseInt(str);
                            }
                        }
                    }
                    return z ? -i2 : i2;
                }

/* 119 */       public static long parseLong(char[] cArr, int i, int i2) {
/* 120 */           int i3 = i2 - 9;
/* 122 */           return ((long) parseInt(cArr, i3 + i, 9)) + (((long) parseInt(cArr, i, i3)) * L_BILLION);
                }

/* 129 */       public static long parseLong(String str) {
                    return str.length() <= 9 ? parseInt(str) : Long.parseLong(str);
                }

/* 149 */       public static boolean inLongRange(char[] cArr, int i, int i2, boolean z) {
/* 150 */           String str = z ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
/* 151 */           int length = str.length();
/* 152 */           if (i2 < length) {
                        return true;
                    }
/* 153 */           if (i2 > length) {
                        return false;
                    }
/* 155 */           for (int i3 = 0; i3 < length; i3++) {
/* 156 */               int iCharAt = cArr[i + i3] - str.charAt(i3);
/* 157 */               if (iCharAt != 0) {
/* 158 */                   return iCharAt < 0;
                        }
                    }
                    return true;
                }

/* 172 */       public static boolean inLongRange(String str, boolean z) {
/* 173 */           String str2 = z ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
/* 174 */           int length = str2.length();
/* 175 */           int length2 = str.length();
/* 176 */           if (length2 < length) {
                        return true;
                    }
/* 177 */           if (length2 > length) {
                        return false;
                    }
/* 180 */           for (int i = 0; i < length; i++) {
/* 181 */               int iCharAt = str.charAt(i) - str2.charAt(i);
/* 182 */               if (iCharAt != 0) {
/* 183 */                   return iCharAt < 0;
                        }
                    }
                    return true;
                }

                /* JADX WARN: Code duplicated, block: B:28:0x0049  */
/* 190 */       public static int parseAsInt(String str, int i) {
                    String strTrim;
                    int length;
                    int length2;
                    String strSubstring;
                    int i2 = 0;
/* 191 */           if (str != null && (length = (strTrim = str.trim()).length()) != 0) {
/* 201 */               if (0 >= length) {
                            length2 = length;
                            strSubstring = strTrim;
                        } else {
/* 202 */                   char cCharAt = strTrim.charAt(0);
/* 203 */                   if (cCharAt == '+') {
/* 204 */                       strSubstring = strTrim.substring(1);
/* 205 */                       length2 = strSubstring.length();
                            } else if (cCharAt == '-') {
                                i2 = 1;
                                length2 = length;
                                strSubstring = strTrim;
                            } else {
                                length2 = length;
                                strSubstring = strTrim;
                            }
                        }
/* 210 */               while (i2 < length2) {
/* 211 */                   char cCharAt2 = strSubstring.charAt(i2);
/* 213 */                   if (cCharAt2 <= '9' && cCharAt2 >= '0') {
/* 210 */                       i2++;
                            } else {
                                try {
/* 215 */                           return (int) parseDouble(strSubstring);
                                } catch (NumberFormatException e) {
/* 224 */                           return i;
                                }
                            }
                        }
                        try {
/* 222 */                   return Integer.parseInt(strSubstring);
                        } catch (NumberFormatException e2) {
/* 224 */                   return i;
                        }
                    }
/* 224 */           return i;
                }

                /* JADX WARN: Code duplicated, block: B:28:0x0049  */
/* 228 */       public static long parseAsLong(String str, long j) {
                    String strTrim;
                    int length;
                    int length2;
                    String strSubstring;
                    int i = 0;
/* 229 */           if (str != null && (length = (strTrim = str.trim()).length()) != 0) {
/* 239 */               if (0 >= length) {
                            length2 = length;
                            strSubstring = strTrim;
                        } else {
/* 240 */                   char cCharAt = strTrim.charAt(0);
/* 241 */                   if (cCharAt == '+') {
/* 242 */                       strSubstring = strTrim.substring(1);
/* 243 */                       length2 = strSubstring.length();
                            } else if (cCharAt == '-') {
                                i = 1;
                                length2 = length;
                                strSubstring = strTrim;
                            } else {
                                length2 = length;
                                strSubstring = strTrim;
                            }
                        }
/* 248 */               while (i < length2) {
/* 249 */                   char cCharAt2 = strSubstring.charAt(i);
/* 251 */                   if (cCharAt2 <= '9' && cCharAt2 >= '0') {
/* 248 */                       i++;
                            } else {
                                try {
/* 253 */                           return (long) parseDouble(strSubstring);
                                } catch (NumberFormatException e) {
/* 262 */                           return j;
                                }
                            }
                        }
                        try {
/* 260 */                   return Long.parseLong(strSubstring);
                        } catch (NumberFormatException e2) {
/* 262 */                   return j;
                        }
                    }
/* 262 */           return j;
                }

/* 266 */       public static double parseAsDouble(String str, double d) {
/* 267 */           if (str != null) {
/* 270 */               String strTrim = str.trim();
/* 272 */               if (strTrim.length() != 0) {
                            try {
/* 276 */                       return parseDouble(strTrim);
                            } catch (NumberFormatException e) {
/* 278 */                       return d;
                            }
                        }
/* 278 */               return d;
                    }
/* 278 */           return d;
                }

/* 286 */       public static double parseDouble(String str) throws NumberFormatException {
/* 287 */           if (NASTY_SMALL_DOUBLE.equals(str)) {
/* 288 */               return Double.MIN_VALUE;
                    }
                    return Double.parseDouble(str);
                }

/* 295 */       public static BigDecimal parseBigDecimal(String str) throws NumberFormatException {
                    try {
/* 296 */               return new BigDecimal(str);
                    } catch (NumberFormatException e) {
/* 298 */               throw _badBigDecimal(str);
                    }
                }

/* 302 */       public static BigDecimal parseBigDecimal(char[] cArr) throws NumberFormatException {
/* 303 */           return parseBigDecimal(cArr, 0, cArr.length);
                }

/* 309 */       public static BigDecimal parseBigDecimal(char[] cArr, int i, int i2) throws NumberFormatException {
                    try {
/* 310 */               return new BigDecimal(cArr, i, i2);
                    } catch (NumberFormatException e) {
/* 312 */               throw _badBigDecimal(new String(cArr, i, i2));
                    }
                }

/* 316 */       private static NumberFormatException _badBigDecimal(String str) {
/* 317 */           return new NumberFormatException("Value \"" + str + "\" can not be represented as BigDecimal");
                }
            }
