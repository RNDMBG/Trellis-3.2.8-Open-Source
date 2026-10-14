            package com.squareup.okhttp.internal.http;
            
            
    public final class HeaderParser {
        public static int skipUntil(String input, int pos, String characters) {
            while (pos < input.length() && characters.indexOf(input.charAt(pos)) == -1) {
                pos++;
                    }
            return pos;
                }

        public static int skipWhitespace(String input, int pos) {
                    char c;
            while (pos < input.length() && ((c = input.charAt(pos)) == ' ' || c == '\t')) {
                pos++;
                    }
            return pos;
                }

        public static int parseSeconds(String value, int defaultValue) {
                    try {
                long seconds = Long.parseLong(value);
                        if (seconds > 2147483647L) {
                    return Integer.MAX_VALUE;
                        }
                        if (seconds < 0) {
                    return 0;
                        }
                int defaultValue2 = (int) seconds;
                return defaultValue2;
                    } catch (NumberFormatException e) {
                return defaultValue;
                    }
                }

        private HeaderParser() {
                }
            }
