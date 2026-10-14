            package com.squareup.okhttp.internal.http;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public final class HeaderParser {
/* 25 */        public static int skipUntil(String input, int pos, String characters) {
/* 26 */            while (pos < input.length() && characters.indexOf(input.charAt(pos)) == -1) {
/* 26 */                pos++;
                    }
/* 31 */            return pos;
                }

/* 38 */        public static int skipWhitespace(String input, int pos) {
                    char c;
/* 39 */            while (pos < input.length() && ((c = input.charAt(pos)) == ' ' || c == '\t')) {
/* 39 */                pos++;
                    }
/* 45 */            return pos;
                }

/* 53 */        public static int parseSeconds(String value, int defaultValue) {
                    try {
/* 54 */                long seconds = Long.parseLong(value);
                        if (seconds > 2147483647L) {
/* 56 */                    return Integer.MAX_VALUE;
                        }
                        if (seconds < 0) {
/* 58 */                    return 0;
                        }
/* 60 */                int defaultValue2 = (int) seconds;
/* 63 */                return defaultValue2;
                    } catch (NumberFormatException e) {
/* 63 */                return defaultValue;
                    }
                }

/* 66 */        private HeaderParser() {
                }
            }
