            package com.google.gdata.util.common.base;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 111 */   public class PercentEscaper extends UnicodeEscaper {
                public static final String SAFECHARS_URLENCODER = "-_.*";
                public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=";
                public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
                private final boolean plusForSpace;
                private final boolean[] safeOctets;
                private static final char[] URI_ESCAPED_SPACE = {'+'};
                private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();

/* 112 */       public PercentEscaper(String safeChars, boolean plusForSpace) {
/* 115 */           if (safeChars.matches(".*[0-9A-Za-z].*")) {
/* 116 */               throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
                    }
/* 122 */           if (plusForSpace && safeChars.contains(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
/* 123 */               throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
                    }
/* 126 */           if (safeChars.contains("%")) {
/* 127 */               throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
                    }
/* 130 */           this.plusForSpace = plusForSpace;
/* 131 */           this.safeOctets = createSafeOctets(safeChars);
                }

/* 139 */       private static boolean[] createSafeOctets(String safeChars) {
/* 140 */           int maxChar = 122;
/* 141 */           char[] safeCharArray = safeChars.toCharArray();
                    for (char c : safeCharArray) {
/* 143 */               maxChar = Math.max((int) c, maxChar);
                    }
/* 145 */           boolean[] octets = new boolean[maxChar + 1];
                    for (int c2 = 48; c2 <= 57; c2++) {
/* 147 */               octets[c2] = true;
                    }
                    for (int c3 = 65; c3 <= 90; c3++) {
/* 150 */               octets[c3] = true;
                    }
                    for (int c4 = 97; c4 <= 122; c4++) {
/* 153 */               octets[c4] = true;
                    }
                    for (char c5 : safeCharArray) {
/* 156 */               octets[c5] = true;
                    }
/* 158 */           return octets;
                }

                @Override // com.google.gdata.util.common.base.UnicodeEscaper
/* 167 */       protected int nextEscapeIndex(CharSequence csq, int index, int end) {
/* 168 */           while (index < end) {
/* 169 */               char c = csq.charAt(index);
/* 170 */               if (c >= this.safeOctets.length || !this.safeOctets[c]) {
                            break;
                        }
/* 168 */               index++;
                    }
/* 174 */           return index;
                }

                @Override // com.google.gdata.util.common.base.UnicodeEscaper, com.google.gdata.util.common.base.Escaper
/* 183 */       public String escape(String s) {
/* 184 */           int slen = s.length();
                    for (int index = 0; index < slen; index++) {
/* 186 */               char c = s.charAt(index);
/* 187 */               if (c >= this.safeOctets.length || !this.safeOctets[c]) {
/* 188 */                   return escapeSlow(s, index);
                        }
                    }
/* 191 */           return s;
                }

                @Override // com.google.gdata.util.common.base.UnicodeEscaper
/* 200 */       protected char[] escape(int cp) {
/* 201 */           if (cp < this.safeOctets.length && this.safeOctets[cp]) {
/* 202 */               return null;
                    }
/* 203 */           if (cp == 32 && this.plusForSpace) {
/* 204 */               return URI_ESCAPED_SPACE;
                    }
/* 205 */           if (cp <= 127) {
/* 208 */               char[] dest = {'%', UPPER_HEX_DIGITS[cp >>> 4], UPPER_HEX_DIGITS[cp & 15]};
/* 267 */               return dest;
                    }
/* 213 */           if (cp <= 2047) {
/* 216 */               char[] dest2 = {'%', UPPER_HEX_DIGITS[(cp >>> 4) | 12], UPPER_HEX_DIGITS[cp & 15], '%', UPPER_HEX_DIGITS[(cp & 3) | 8], UPPER_HEX_DIGITS[cp & 15]};
/* 220 */               int cp2 = cp >>> 4;
/* 222 */               int cp3 = cp2 >>> 2;
/* 267 */               return dest2;
                    }
/* 227 */           if (cp <= 65535) {
/* 228 */               char[] cArr = UPPER_HEX_DIGITS;
/* 230 */               char[] dest3 = {'%', 'E', cArr[cp >>> 2], '%', UPPER_HEX_DIGITS[(cp & 3) | 8], UPPER_HEX_DIGITS[cp & 15], '%', UPPER_HEX_DIGITS[(cp & 3) | 8], UPPER_HEX_DIGITS[cp & 15]};
/* 236 */               int cp4 = cp >>> 4;
/* 238 */               int cp5 = cp4 >>> 2;
/* 240 */               int cp6 = cp5 >>> 4;
/* 267 */               return dest3;
                    }
/* 245 */           if (cp <= 1114111) {
/* 246 */               char[] dest4 = {'%', 'F', UPPER_HEX_DIGITS[(cp >>> 2) & 7], '%', UPPER_HEX_DIGITS[(cp & 3) | 8], UPPER_HEX_DIGITS[cp & 15], '%', UPPER_HEX_DIGITS[(cp & 3) | 8], UPPER_HEX_DIGITS[cp & 15], '%', UPPER_HEX_DIGITS[(cp & 3) | 8], UPPER_HEX_DIGITS[cp & 15]};
/* 255 */               int cp7 = cp >>> 4;
/* 257 */               int cp8 = cp7 >>> 2;
/* 259 */               int cp9 = cp8 >>> 4;
/* 261 */               int cp10 = cp9 >>> 2;
/* 263 */               int cp11 = cp10 >>> 4;
/* 267 */               return dest4;
                    }
/* 270 */           throw new IllegalArgumentException("Invalid unicode character value " + cp);
                }
            }
