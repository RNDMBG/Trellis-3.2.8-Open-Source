            package com.fasterxml.jackson.core.p003io;

            import com.googlecode.javacv.cpp.avutil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 5 */     public final class NumberOutput {
                static final byte[] FULL_TRIPLETS_B;
                private static final char NULL_CHAR = 0;
                static final String[] sSmallIntStrs;
                static final String[] sSmallIntStrs2;
                private static int MILLION = avutil.AV_TIME_BASE;
                private static int BILLION = 1000000000;
                private static long TEN_BILLION_L = 10000000000L;
                private static long THOUSAND_L = 1000;
                private static long MIN_INT_AS_LONG = -2147483648L;
                private static long MAX_INT_AS_LONG = 2147483647L;
                static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
                static final char[] LEADING_TRIPLETS = new char[4000];
                static final char[] FULL_TRIPLETS = new char[4000];

                static {
                    int i = 0;
                    int i2 = 0;
/* 24 */            while (i < 10) {
/* 25 */                char c = (char) (i + 48);
/* 26 */                char c2 = i == 0 ? (char) 0 : c;
                        int i3 = 0;
/* 27 */                while (i3 < 10) {
/* 28 */                    char c3 = (char) (i3 + 48);
/* 29 */                    char c4 = (i == 0 && i3 == 0) ? (char) 0 : c3;
                            int i4 = i2;
/* 30 */                    for (int i5 = 0; i5 < 10; i5++) {
/* 32 */                        char c5 = (char) (i5 + 48);
/* 33 */                        LEADING_TRIPLETS[i4] = c2;
/* 34 */                        LEADING_TRIPLETS[i4 + 1] = c4;
/* 35 */                        LEADING_TRIPLETS[i4 + 2] = c5;
/* 36 */                        FULL_TRIPLETS[i4] = c;
/* 37 */                        FULL_TRIPLETS[i4 + 1] = c3;
/* 38 */                        FULL_TRIPLETS[i4 + 2] = c5;
/* 39 */                        i4 += 4;
                            }
/* 27 */                    i3++;
                            i2 = i4;
                        }
/* 24 */                i++;
                    }
/* 45 */            FULL_TRIPLETS_B = new byte[4000];
/* 47 */            for (int i6 = 0; i6 < 4000; i6++) {
/* 48 */                FULL_TRIPLETS_B[i6] = (byte) FULL_TRIPLETS[i6];
                    }
/* 52 */            sSmallIntStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
/* 55 */            sSmallIntStrs2 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
                }

/* 69 */        public static int outputInt(int i, char[] cArr, int i2) {
                    int iOutputLeadingTriplet;
/* 70 */            if (i < 0) {
/* 71 */                if (i == Integer.MIN_VALUE) {
/* 75 */                    return outputLong(i, cArr, i2);
                        }
                        cArr[i2] = '-';
/* 78 */                i = -i;
/* 77 */                i2++;
                    }
/* 81 */            if (i < MILLION) {
/* 82 */                if (i < 1000) {
/* 83 */                    if (i < 10) {
/* 84 */                        int i3 = i2 + 1;
                                cArr[i2] = (char) (i + 48);
/* 126 */                       return i3;
                            }
/* 86 */                    return outputLeadingTriplet(i, cArr, i2);
                        }
/* 89 */                int i4 = i / 1000;
/* 92 */                return outputFullTriplet(i - (i4 * 1000), cArr, outputLeadingTriplet(i4, cArr, i2));
                    }
/* 102 */           boolean z = i >= BILLION;
/* 103 */           if (z) {
/* 104 */               i -= BILLION;
/* 105 */               if (i >= BILLION) {
/* 106 */                   i -= BILLION;
                            cArr[i2] = '2';
/* 107 */                   i2++;
                        } else {
                            cArr[i2] = '1';
/* 109 */                   i2++;
                        }
                    }
/* 112 */           int i5 = i / 1000;
/* 113 */           int i6 = i - (i5 * 1000);
/* 115 */           int i7 = i5 / 1000;
/* 116 */           int i8 = i5 - (i7 * 1000);
/* 119 */           if (z) {
/* 120 */               iOutputLeadingTriplet = outputFullTriplet(i7, cArr, i2);
                    } else {
/* 122 */               iOutputLeadingTriplet = outputLeadingTriplet(i7, cArr, i2);
                    }
/* 125 */           return outputFullTriplet(i6, cArr, outputFullTriplet(i8, cArr, iOutputLeadingTriplet));
                }

/* 130 */       public static int outputInt(int i, byte[] bArr, int i2) {
                    int iOutputLeadingTriplet;
/* 131 */           if (i < 0) {
/* 132 */               if (i == Integer.MIN_VALUE) {
/* 133 */                   return outputLong(i, bArr, i2);
                        }
                        bArr[i2] = 45;
/* 136 */               i = -i;
/* 135 */               i2++;
                    }
/* 139 */           if (i < MILLION) {
/* 140 */               if (i < 1000) {
/* 141 */                   if (i < 10) {
/* 142 */                       int i3 = i2 + 1;
                                bArr[i2] = (byte) (i + 48);
/* 177 */                       return i3;
                            }
/* 144 */                   return outputLeadingTriplet(i, bArr, i2);
                        }
/* 147 */               int i4 = i / 1000;
/* 150 */               return outputFullTriplet(i - (i4 * 1000), bArr, outputLeadingTriplet(i4, bArr, i2));
                    }
/* 154 */           boolean z = i >= BILLION;
/* 155 */           if (z) {
/* 156 */               i -= BILLION;
/* 157 */               if (i >= BILLION) {
/* 158 */                   i -= BILLION;
                            bArr[i2] = 50;
/* 159 */                   i2++;
                        } else {
                            bArr[i2] = 49;
/* 161 */                   i2++;
                        }
                    }
/* 164 */           int i5 = i / 1000;
/* 165 */           int i6 = i - (i5 * 1000);
/* 167 */           int i7 = i5 / 1000;
/* 168 */           int i8 = i5 - (i7 * 1000);
/* 170 */           if (z) {
/* 171 */               iOutputLeadingTriplet = outputFullTriplet(i7, bArr, i2);
                    } else {
/* 173 */               iOutputLeadingTriplet = outputLeadingTriplet(i7, bArr, i2);
                    }
/* 176 */           return outputFullTriplet(i6, bArr, outputFullTriplet(i8, bArr, iOutputLeadingTriplet));
                }

/* 185 */       public static int outputLong(long j, char[] cArr, int i) {
                    if (j < 0) {
                        if (j > MIN_INT_AS_LONG) {
/* 191 */                   return outputInt((int) j, cArr, i);
                        }
                        if (j == Long.MIN_VALUE) {
/* 195 */                   int length = SMALLEST_LONG.length();
/* 196 */                   SMALLEST_LONG.getChars(0, length, cArr, i);
/* 197 */                   return i + length;
                        }
                        cArr[i] = '-';
/* 200 */               j = -j;
/* 199 */               i++;
                    } else if (j <= MAX_INT_AS_LONG) {
/* 203 */               return outputInt((int) j, cArr, i);
                    }
/* 211 */           int iCalcLongStrLength = i + calcLongStrLength(j);
                    int i2 = iCalcLongStrLength;
                    while (j > MAX_INT_AS_LONG) {
/* 216 */               i2 -= 3;
/* 217 */               long j2 = j / THOUSAND_L;
/* 219 */               outputFullTriplet((int) (j - (THOUSAND_L * j2)), cArr, i2);
                        j = j2;
                    }
                    int i3 = i2;
/* 223 */           int i4 = (int) j;
/* 224 */           while (i4 >= 1000) {
/* 225 */               int i5 = i3 - 3;
/* 226 */               int i6 = i4 / 1000;
/* 228 */               outputFullTriplet(i4 - (i6 * 1000), cArr, i5);
                        i4 = i6;
                        i3 = i5;
                    }
/* 232 */           outputLeadingTriplet(i4, cArr, i);
/* 234 */           return iCalcLongStrLength;
                }

/* 238 */       public static int outputLong(long j, byte[] bArr, int i) {
                    if (j < 0) {
                        if (j > MIN_INT_AS_LONG) {
/* 241 */                   return outputInt((int) j, bArr, i);
                        }
                        if (j == Long.MIN_VALUE) {
/* 245 */                   int length = SMALLEST_LONG.length();
/* 246 */                   int i2 = 0;
                            int i3 = i;
                            while (i2 < length) {
                                bArr[i3] = (byte) SMALLEST_LONG.charAt(i2);
/* 246 */                       i2++;
/* 247 */                       i3++;
                            }
/* 280 */                   return i3;
                        }
                        bArr[i] = 45;
/* 252 */               j = -j;
/* 251 */               i++;
                    } else if (j <= MAX_INT_AS_LONG) {
/* 255 */               return outputInt((int) j, bArr, i);
                    }
/* 259 */           int iCalcLongStrLength = i + calcLongStrLength(j);
                    int i4 = iCalcLongStrLength;
                    while (j > MAX_INT_AS_LONG) {
/* 264 */               i4 -= 3;
/* 265 */               long j2 = j / THOUSAND_L;
/* 267 */               outputFullTriplet((int) (j - (THOUSAND_L * j2)), bArr, i4);
                        j = j2;
                    }
                    int i5 = i4;
/* 271 */           int i6 = (int) j;
/* 272 */           while (i6 >= 1000) {
/* 273 */               int i7 = i5 - 3;
/* 274 */               int i8 = i6 / 1000;
/* 276 */               outputFullTriplet(i6 - (i8 * 1000), bArr, i7);
                        i6 = i8;
                        i5 = i7;
                    }
/* 279 */           outputLeadingTriplet(i6, bArr, i);
/* 280 */           return iCalcLongStrLength;
                }

/* 295 */       public static String toString(int i) {
/* 296 */           if (i < sSmallIntStrs.length) {
/* 297 */               if (i >= 0) {
/* 298 */                   return sSmallIntStrs[i];
                        }
/* 300 */               int i2 = (-i) - 1;
/* 301 */               if (i2 < sSmallIntStrs2.length) {
/* 302 */                   return sSmallIntStrs2[i2];
                        }
                    }
/* 305 */           return Integer.toString(i);
                }

/* 309 */       public static String toString(long j) {
                    return (j > 2147483647L || j < -2147483648L) ? Long.toString(j) : toString((int) j);
                }

/* 318 */       public static String toString(double d) {
/* 319 */           return Double.toString(d);
                }

/* 329 */       private static int outputLeadingTriplet(int i, char[] cArr, int i2) {
/* 330 */           int i3 = i << 2;
                    int i4 = i3 + 1;
/* 331 */           char c = LEADING_TRIPLETS[i3];
/* 332 */           if (c != 0) {
                        cArr[i2] = c;
/* 333 */               i2++;
                    }
                    int i5 = i4 + 1;
/* 335 */           char c2 = LEADING_TRIPLETS[i4];
/* 336 */           if (c2 != 0) {
                        cArr[i2] = c2;
/* 337 */               i2++;
                    }
/* 340 */           int i6 = i2 + 1;
                    cArr[i2] = LEADING_TRIPLETS[i5];
/* 341 */           return i6;
                }

/* 345 */       private static int outputLeadingTriplet(int i, byte[] bArr, int i2) {
/* 346 */           int i3 = i << 2;
                    int i4 = i3 + 1;
/* 347 */           char c = LEADING_TRIPLETS[i3];
/* 348 */           if (c != 0) {
                        bArr[i2] = (byte) c;
/* 349 */               i2++;
                    }
                    int i5 = i4 + 1;
/* 351 */           char c2 = LEADING_TRIPLETS[i4];
/* 352 */           if (c2 != 0) {
                        bArr[i2] = (byte) c2;
/* 353 */               i2++;
                    }
/* 356 */           int i6 = i2 + 1;
                    bArr[i2] = (byte) LEADING_TRIPLETS[i5];
/* 357 */           return i6;
                }

/* 361 */       private static int outputFullTriplet(int i, char[] cArr, int i2) {
/* 362 */           int i3 = i << 2;
/* 363 */           int i4 = i2 + 1;
                    int i5 = i3 + 1;
                    cArr[i2] = FULL_TRIPLETS[i3];
/* 364 */           int i6 = i4 + 1;
                    cArr[i4] = FULL_TRIPLETS[i5];
/* 365 */           int i7 = i6 + 1;
                    cArr[i6] = FULL_TRIPLETS[i5 + 1];
/* 366 */           return i7;
                }

/* 370 */       private static int outputFullTriplet(int i, byte[] bArr, int i2) {
/* 371 */           int i3 = i << 2;
/* 372 */           int i4 = i2 + 1;
                    int i5 = i3 + 1;
                    bArr[i2] = FULL_TRIPLETS_B[i3];
/* 373 */           int i6 = i4 + 1;
                    bArr[i4] = FULL_TRIPLETS_B[i5];
/* 374 */           int i7 = i6 + 1;
                    bArr[i6] = FULL_TRIPLETS_B[i5 + 1];
/* 375 */           return i7;
                }

/* 384 */       private static int calcLongStrLength(long j) {
/* 385 */           int i = 10;
/* 390 */           for (long j2 = TEN_BILLION_L; j >= j2 && i != 19; j2 = (j2 << 1) + (j2 << 3)) {
/* 393 */               i++;
                    }
/* 396 */           return i;
                }
            }
