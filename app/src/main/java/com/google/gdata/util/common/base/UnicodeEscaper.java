            package com.google.gdata.util.common.base;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 105 */   public abstract class UnicodeEscaper implements Escaper {
                private static final int DEST_PAD = 32;
                private static final ThreadLocal<char[]> DEST_TL = new C12192();

                protected abstract char[] escape(int i);

/* 106 */       protected int nextEscapeIndex(CharSequence csq, int start, int end) {
/* 107 */           int index = start;
/* 108 */           while (index < end) {
/* 109 */               int cp = codePointAt(csq, index, end);
/* 110 */               if (cp < 0 || escape(cp) != null) {
                            break;
                        }
/* 113 */               index += Character.isSupplementaryCodePoint(cp) ? 2 : 1;
                    }
/* 115 */           return index;
                }

                @Override // com.google.gdata.util.common.base.Escaper
/* 141 */       public String escape(String string) {
/* 142 */           int end = string.length();
/* 143 */           int index = nextEscapeIndex(string, 0, end);
                    return index == end ? string : escapeSlow(string, index);
                }

/* 164 */       protected final String escapeSlow(String s, int index) {
/* 165 */           int end = s.length();
/* 168 */           char[] dest = DEST_TL.get();
/* 169 */           int destIndex = 0;
/* 170 */           int unescapedChunkStart = 0;
/* 172 */           while (index < end) {
/* 173 */               int cp = codePointAt(s, index, end);
/* 174 */               if (cp < 0) {
/* 175 */                   throw new IllegalArgumentException("Trailing high surrogate at end of input");
                        }
/* 178 */               char[] escaped = escape(cp);
/* 179 */               if (escaped != null) {
/* 180 */                   int charsSkipped = index - unescapedChunkStart;
/* 184 */                   int sizeNeeded = destIndex + charsSkipped + escaped.length;
/* 185 */                   if (dest.length < sizeNeeded) {
/* 186 */                       int destLength = (end - index) + sizeNeeded + 32;
/* 187 */                       dest = growBuffer(dest, destIndex, destLength);
                            }
/* 190 */                   if (charsSkipped > 0) {
/* 191 */                       s.getChars(unescapedChunkStart, index, dest, destIndex);
/* 192 */                       destIndex += charsSkipped;
                            }
/* 194 */                   if (escaped.length > 0) {
/* 195 */                       System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
/* 196 */                       destIndex += escaped.length;
                            }
                        }
/* 199 */               unescapedChunkStart = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
/* 201 */               index = nextEscapeIndex(s, unescapedChunkStart, end);
                    }
/* 206 */           int charsSkipped2 = end - unescapedChunkStart;
/* 207 */           if (charsSkipped2 > 0) {
/* 208 */               int endIndex = destIndex + charsSkipped2;
/* 209 */               if (dest.length < endIndex) {
/* 210 */                   dest = growBuffer(dest, destIndex, endIndex);
                        }
/* 212 */               s.getChars(unescapedChunkStart, end, dest, destIndex);
/* 213 */               destIndex = endIndex;
                    }
/* 215 */           return new String(dest, 0, destIndex);
                }

                /* JADX INFO: renamed from: com.google.gdata.util.common.base.UnicodeEscaper$1 */
/* 253 */       class C12181 implements Appendable {
                    final /* synthetic */ Appendable val$out;
                    int pendingHighSurrogate = -1;
                    final char[] decodedChars = new char[2];

/* 254 */           C12181(Appendable appendable) {
                        this.val$out = appendable;
                    }

                    @Override // java.lang.Appendable
/* 259 */           public Appendable append(CharSequence csq) throws IOException {
/* 260 */               return append(csq, 0, csq.length());
                    }

                    @Override // java.lang.Appendable
/* 264 */           public Appendable append(CharSequence csq, int start, int end) throws IOException {
/* 265 */               int index = start;
/* 266 */               if (index < end) {
/* 270 */                   int unescapedChunkStart = index;
/* 271 */                   if (this.pendingHighSurrogate != -1) {
/* 274 */                       int index2 = index + 1;
                                char c = csq.charAt(index);
/* 275 */                       if (!Character.isLowSurrogate(c)) {
/* 276 */                           throw new IllegalArgumentException("Expected low surrogate character but got " + c);
                                }
/* 279 */                       char[] escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char) this.pendingHighSurrogate, c));
/* 281 */                       if (escaped != null) {
/* 284 */                           outputChars(escaped, escaped.length);
/* 285 */                           unescapedChunkStart++;
                                } else {
/* 289 */                           this.val$out.append((char) this.pendingHighSurrogate);
                                }
/* 291 */                       this.pendingHighSurrogate = -1;
                                index = index2;
                            }
                            while (true) {
/* 295 */                       int index3 = UnicodeEscaper.this.nextEscapeIndex(csq, index, end);
/* 296 */                       if (index3 > unescapedChunkStart) {
/* 297 */                           this.val$out.append(csq, unescapedChunkStart, index3);
                                }
/* 299 */                       if (index3 == end) {
                                    break;
                                }
/* 303 */                       int cp = UnicodeEscaper.codePointAt(csq, index3, end);
/* 304 */                       if (cp < 0) {
/* 307 */                           this.pendingHighSurrogate = -cp;
                                    break;
                                }
/* 311 */                       char[] escaped2 = UnicodeEscaper.this.escape(cp);
/* 312 */                       if (escaped2 != null) {
/* 313 */                           outputChars(escaped2, escaped2.length);
                                } else {
/* 317 */                           int len = Character.toChars(cp, this.decodedChars, 0);
/* 318 */                           outputChars(this.decodedChars, len);
                                }
/* 321 */                       index = index3 + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
/* 322 */                       unescapedChunkStart = index;
                            }
                        }
/* 325 */               return this;
                    }

                    @Override // java.lang.Appendable
/* 328 */           public Appendable append(char c) throws IOException {
/* 329 */               if (this.pendingHighSurrogate != -1) {
/* 332 */                   if (!Character.isLowSurrogate(c)) {
/* 333 */                       throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + ((int) c));
                            }
/* 337 */                   char[] escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char) this.pendingHighSurrogate, c));
/* 339 */                   if (escaped != null) {
/* 340 */                       outputChars(escaped, escaped.length);
                            } else {
/* 342 */                       this.val$out.append((char) this.pendingHighSurrogate);
/* 343 */                       this.val$out.append(c);
                            }
/* 345 */                   this.pendingHighSurrogate = -1;
                        } else if (Character.isHighSurrogate(c)) {
/* 348 */                   this.pendingHighSurrogate = c;
                        } else {
/* 350 */                   if (Character.isLowSurrogate(c)) {
/* 351 */                       throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + ((int) c));
                            }
/* 356 */                   char[] escaped2 = UnicodeEscaper.this.escape(c);
/* 357 */                   if (escaped2 != null) {
/* 358 */                       outputChars(escaped2, escaped2.length);
                            } else {
/* 360 */                       this.val$out.append(c);
                            }
                        }
/* 363 */               return this;
                    }

/* 366 */           private void outputChars(char[] chars, int len) throws IOException {
                        for (int n = 0; n < len; n++) {
/* 368 */                   this.val$out.append(chars[n]);
                        }
                    }
                }

                @Override // com.google.gdata.util.common.base.Escaper
/* 254 */       public Appendable escape(Appendable out) {
/* 255 */           return new C12181(out);
                }

/* 406 */       protected static final int codePointAt(CharSequence seq, int index, int end) {
/* 407 */           if (index < end) {
/* 408 */               int index2 = index + 1;
                        char c1 = seq.charAt(index);
/* 409 */               if (c1 >= 55296 && c1 <= 57343) {
/* 413 */                   if (c1 <= 56319) {
/* 415 */                       if (index2 == end) {
/* 416 */                           return -c1;
                                }
/* 419 */                       char c2 = seq.charAt(index2);
/* 420 */                       if (Character.isLowSurrogate(c2)) {
/* 421 */                           return Character.toCodePoint(c1, c2);
                                }
/* 423 */                       throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + ((int) c2) + " at index " + index2);
                            }
/* 427 */                   throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + ((int) c1) + " at index " + (index2 - 1));
                        }
/* 421 */               return c1;
                    }
/* 432 */           throw new IndexOutOfBoundsException("Index exceeds specified range");
                }

/* 440 */       private static final char[] growBuffer(char[] dest, int index, int size) {
/* 441 */           char[] copy = new char[size];
/* 442 */           if (index > 0) {
/* 443 */               System.arraycopy(dest, 0, copy, 0, index);
                    }
/* 445 */           return copy;
                }

                /* JADX INFO: renamed from: com.google.gdata.util.common.base.UnicodeEscaper$2 */
/* 451 */       static class C12192 extends ThreadLocal<char[]> {
/* 452 */           C12192() {
                    }


                    @Override // java.lang.ThreadLocal
                    /* JADX INFO: renamed from: initialValue, reason: avoid collision after fix types in other method */
/* 455 */           protected char[] initialValue() {
/* 456 */               return new char[1024];
                    }
                }
            }
