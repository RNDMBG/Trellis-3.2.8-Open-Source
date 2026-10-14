            package com.edisonwang.android.slog;

            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 120 */   public final class MessageFormatter {
                static final char DELIM_START = '{';
                static final char DELIM_STOP = '}';
                static final String DELIM_STR = "{}";
                private static final char ESCAPE_CHAR = '\\';

/* 121 */       public static FormattingTuple format(String messagePattern, Object arg) {
                    return arrayFormat(messagePattern, new Object[]{arg});
                }

/* 125 */       public static FormattingTuple format(String messagePattern, Object[] arg) {
/* 126 */           return arrayFormat(messagePattern, arg);
                }

/* 149 */       public static FormattingTuple format(String messagePattern, Object arg1, Object arg2) {
                    return arrayFormat(messagePattern, new Object[]{arg1, arg2});
                }

/* 175 */       public static FormattingTuple format(String messagePattern, Object arg1, Object arg2, Object arg3) {
                    return arrayFormat(messagePattern, new Object[]{arg1, arg2, arg3});
                }

/* 179 */       private static Throwable getThrowableCandidate(Object[] argArray) {
/* 180 */           if (argArray == null || argArray.length == 0) {
                        return null;
                    }
/* 184 */           Object lastEntry = argArray[argArray.length - 1];
/* 185 */           if (lastEntry instanceof Throwable) {
/* 186 */               return (Throwable) lastEntry;
                    }
                    return null;
                }

/* 203 */       public static FormattingTuple arrayFormat(String messagePattern, Object[] argArray) {
/* 204 */           Throwable throwableCandidate = getThrowableCandidate(argArray);
/* 206 */           if (messagePattern == null) {
/* 207 */               return new FormattingTuple(null, argArray, throwableCandidate);
                    }
/* 210 */           if (argArray == null) {
/* 211 */               return new FormattingTuple(messagePattern);
                    }
/* 214 */           int i = 0;
/* 216 */           StringBuffer sbuf = new StringBuffer(messagePattern.length() + 50);
/* 219 */           int L = 0;
                    while (L < argArray.length) {
/* 221 */               int j = messagePattern.indexOf(DELIM_STR, i);
/* 223 */               if (j == -1) {
/* 225 */                   if (i == 0) {
/* 226 */                       return new FormattingTuple(messagePattern, argArray, throwableCandidate);
                            }
/* 229 */                   sbuf.append(messagePattern.substring(i, messagePattern.length()));
/* 230 */                   return new FormattingTuple(sbuf.toString(), argArray, throwableCandidate);
                        }
/* 233 */               if (isEscapedDelimeter(messagePattern, j)) {
/* 234 */                   if (!isDoubleEscaped(messagePattern, j)) {
/* 235 */                       L--;
/* 236 */                       sbuf.append(messagePattern.substring(i, j - 1));
/* 237 */                       sbuf.append(DELIM_START);
/* 238 */                       i = j + 1;
                            } else {
/* 243 */                       sbuf.append(messagePattern.substring(i, j - 1));
/* 244 */                       deeplyAppendParameter(sbuf, argArray[L], new HashMap());
/* 245 */                       i = j + 2;
                            }
                        } else {
/* 249 */                   sbuf.append(messagePattern.substring(i, j));
/* 250 */                   deeplyAppendParameter(sbuf, argArray[L], new HashMap());
/* 251 */                   i = j + 2;
                        }
/* 219 */               L++;
                    }
/* 256 */           sbuf.append(messagePattern.substring(i, messagePattern.length()));
/* 257 */           if (L < argArray.length - 1) {
/* 258 */               return new FormattingTuple(sbuf.toString(), argArray, throwableCandidate);
                    }
/* 260 */           return new FormattingTuple(sbuf.toString(), argArray, null);
                }

/* 265 */       private static boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {
/* 266 */           if (delimeterStartIndex == 0) {
                        return false;
                    }
/* 269 */           char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
                    return potentialEscape == '\\';
                }

/* 273 */       private static boolean isDoubleEscaped(String messagePattern, int delimeterStartIndex) {
                    return delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex + (-2)) == '\\';
                }

/* 278 */       private static void deeplyAppendParameter(StringBuffer sbuf, Object o, Map seenMap) {
/* 279 */           if (o == null) {
/* 280 */               sbuf.append("null");
/* 308 */               return;
                    }
/* 283 */           if (!o.getClass().isArray()) {
/* 284 */               safeObjectAppend(sbuf, o);
/* 308 */               return;
                    }
/* 288 */           if (o instanceof boolean[]) {
/* 289 */               booleanArrayAppend(sbuf, (boolean[]) o);
/* 308 */               return;
                    }
/* 290 */           if (o instanceof byte[]) {
/* 291 */               byteArrayAppend(sbuf, (byte[]) o);
/* 308 */               return;
                    }
/* 292 */           if (o instanceof char[]) {
/* 293 */               charArrayAppend(sbuf, (char[]) o);
/* 308 */               return;
                    }
/* 294 */           if (o instanceof short[]) {
/* 295 */               shortArrayAppend(sbuf, (short[]) o);
/* 308 */               return;
                    }
/* 296 */           if (o instanceof int[]) {
/* 297 */               intArrayAppend(sbuf, (int[]) o);
/* 308 */               return;
                    }
/* 298 */           if (o instanceof long[]) {
/* 299 */               longArrayAppend(sbuf, (long[]) o);
/* 308 */               return;
                    }
/* 300 */           if (o instanceof float[]) {
/* 301 */               floatArrayAppend(sbuf, (float[]) o);
                    } else if (o instanceof double[]) {
/* 303 */               doubleArrayAppend(sbuf, (double[]) o);
                    } else {
/* 305 */               objectArrayAppend(sbuf, (Object[]) o, seenMap);
                    }
                }

/* 311 */       private static void safeObjectAppend(StringBuffer sbuf, Object o) {
                    try {
/* 312 */               String oAsString = o.toString();
/* 313 */               sbuf.append(oAsString);
                    } catch (Throwable t) {
/* 315 */               System.err.println("SLF4J: Failed toString() invocation on an object of type [" + o.getClass().getName() + "]");
/* 316 */               t.printStackTrace();
/* 317 */               sbuf.append("[FAILED toString()]");
                    }
                }

/* 323 */       private static void objectArrayAppend(StringBuffer sbuf, Object[] a, Map seenMap) {
/* 324 */           sbuf.append('[');
/* 325 */           if (!seenMap.containsKey(a)) {
/* 326 */               seenMap.put(a, null);
/* 327 */               int len = a.length;
                        for (int i = 0; i < len; i++) {
/* 329 */                   deeplyAppendParameter(sbuf, a[i], seenMap);
/* 330 */                   if (i != len - 1) {
/* 331 */                       sbuf.append(", ");
                            }
                        }
/* 334 */               seenMap.remove(a);
                    } else {
/* 336 */               sbuf.append("...");
                    }
/* 338 */           sbuf.append(']');
                }

/* 341 */       private static void booleanArrayAppend(StringBuffer sbuf, boolean[] a) {
/* 342 */           sbuf.append('[');
/* 343 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 345 */               sbuf.append(a[i]);
/* 346 */               if (i != len - 1) {
/* 347 */                   sbuf.append(", ");
                        }
                    }
/* 349 */           sbuf.append(']');
                }

/* 352 */       private static void byteArrayAppend(StringBuffer sbuf, byte[] a) {
/* 353 */           sbuf.append('[');
/* 354 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 356 */               sbuf.append((int) a[i]);
/* 357 */               if (i != len - 1) {
/* 358 */                   sbuf.append(", ");
                        }
                    }
/* 360 */           sbuf.append(']');
                }

/* 363 */       private static void charArrayAppend(StringBuffer sbuf, char[] a) {
/* 364 */           sbuf.append('[');
/* 365 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 367 */               sbuf.append(a[i]);
/* 368 */               if (i != len - 1) {
/* 369 */                   sbuf.append(", ");
                        }
                    }
/* 371 */           sbuf.append(']');
                }

/* 374 */       private static void shortArrayAppend(StringBuffer sbuf, short[] a) {
/* 375 */           sbuf.append('[');
/* 376 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 378 */               sbuf.append((int) a[i]);
/* 379 */               if (i != len - 1) {
/* 380 */                   sbuf.append(", ");
                        }
                    }
/* 382 */           sbuf.append(']');
                }

/* 385 */       private static void intArrayAppend(StringBuffer sbuf, int[] a) {
/* 386 */           sbuf.append('[');
/* 387 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 389 */               sbuf.append(a[i]);
/* 390 */               if (i != len - 1) {
/* 391 */                   sbuf.append(", ");
                        }
                    }
/* 393 */           sbuf.append(']');
                }

/* 396 */       private static void longArrayAppend(StringBuffer sbuf, long[] a) {
/* 397 */           sbuf.append('[');
/* 398 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 400 */               sbuf.append(a[i]);
/* 401 */               if (i != len - 1) {
/* 402 */                   sbuf.append(", ");
                        }
                    }
/* 404 */           sbuf.append(']');
                }

/* 407 */       private static void floatArrayAppend(StringBuffer sbuf, float[] a) {
/* 408 */           sbuf.append('[');
/* 409 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 411 */               sbuf.append(a[i]);
/* 412 */               if (i != len - 1) {
/* 413 */                   sbuf.append(", ");
                        }
                    }
/* 415 */           sbuf.append(']');
                }

/* 418 */       private static void doubleArrayAppend(StringBuffer sbuf, double[] a) {
/* 419 */           sbuf.append('[');
/* 420 */           int len = a.length;
                    for (int i = 0; i < len; i++) {
/* 422 */               sbuf.append(a[i]);
/* 423 */               if (i != len - 1) {
/* 424 */                   sbuf.append(", ");
                        }
                    }
/* 426 */           sbuf.append(']');
                }

/* 429 */       public static String toStringMessage(String string, Object... objects) {
/* 430 */           return format(string, objects).getMessage();
                }
            }
