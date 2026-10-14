            package com.squareup.okhttp.internal.tls;

            import javax.security.auth.x500.X500Principal;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    final class DistinguishedNameParser {
                private int beg;
                private char[] chars;
                private int cur;

                /* JADX INFO: renamed from: dn */
                private final String f2534dn;
                private int end;
                private final int length;
                private int pos;

/* 38 */        public DistinguishedNameParser(X500Principal principal) {
/* 43 */            this.f2534dn = principal.getName("RFC2253");
/* 44 */            this.length = this.f2534dn.length();
                }

/* 50 */        private String nextAT() {
/* 51 */            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                        this.pos++;
                    }
/* 53 */            if (this.pos == this.length) {
/* 54 */                return null;
                    }
/* 58 */            this.beg = this.pos;
/* 61 */            this.pos++;
/* 62 */            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
                        this.pos++;
                    }
/* 66 */            if (this.pos >= this.length) {
/* 67 */                throw new IllegalStateException("Unexpected end of DN: " + this.f2534dn);
                    }
/* 71 */            this.end = this.pos;
/* 75 */            if (this.chars[this.pos] == ' ') {
/* 76 */                while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                            this.pos++;
                        }
/* 79 */                if (this.chars[this.pos] != '=' || this.pos == this.length) {
/* 80 */                    throw new IllegalStateException("Unexpected end of DN: " + this.f2534dn);
                        }
                    }
/* 84 */            this.pos++;
/* 88 */            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                        this.pos++;
                    }
/* 93 */            if (this.end - this.beg > 4 && this.chars[this.beg + 3] == '.' && ((this.chars[this.beg] == 'O' || this.chars[this.beg] == 'o') && ((this.chars[this.beg + 1] == 'I' || this.chars[this.beg + 1] == 'i') && (this.chars[this.beg + 2] == 'D' || this.chars[this.beg + 2] == 'd')))) {
/* 97 */                this.beg += 4;
                    }
/* 100 */           return new String(this.chars, this.beg, this.end - this.beg);
                }

/* 104 */       private String quotedAV() {
/* 105 */           this.pos++;
/* 106 */           this.beg = this.pos;
/* 107 */           this.end = this.beg;
/* 110 */           while (this.pos != this.length) {
/* 114 */               if (this.chars[this.pos] == '\"') {
/* 116 */                   this.pos++;
/* 130 */                   while (this.pos < this.length && this.chars[this.pos] == ' ') {
                                this.pos++;
                            }
/* 133 */                   return new String(this.chars, this.beg, this.end - this.beg);
                        }
/* 118 */               if (this.chars[this.pos] == '\\') {
/* 119 */                   this.chars[this.end] = getEscaped();
                        } else {
/* 122 */                   this.chars[this.end] = this.chars[this.pos];
                        }
/* 124 */               this.pos++;
/* 125 */               this.end++;
                    }
/* 111 */           throw new IllegalStateException("Unexpected end of DN: " + this.f2534dn);
                }

/* 137 */       private String hexAV() {
/* 138 */           if (this.pos + 4 >= this.length) {
/* 140 */               throw new IllegalStateException("Unexpected end of DN: " + this.f2534dn);
                    }
/* 143 */           this.beg = this.pos;
/* 144 */           this.pos++;
                    while (true) {
/* 149 */               if (this.pos == this.length || this.chars[this.pos] == '+' || this.chars[this.pos] == ',' || this.chars[this.pos] == ';') {
/* 151 */                   this.end = this.pos;
                            break;
                        }
/* 155 */               if (this.chars[this.pos] == ' ') {
/* 156 */                   this.end = this.pos;
/* 157 */                   this.pos++;
/* 160 */                   while (this.pos < this.length && this.chars[this.pos] == ' ') {
                                this.pos++;
                            }
                            break;
                        }
/* 163 */               if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
/* 164 */                   char[] cArr = this.chars;
                            int i = this.pos;
                            cArr[i] = (char) (cArr[i] + ' ');
                        }
/* 167 */               this.pos++;
                    }
/* 172 */           int hexLen = this.end - this.beg;
/* 173 */           if (hexLen < 5 || (hexLen & 1) == 0) {
/* 174 */               throw new IllegalStateException("Unexpected end of DN: " + this.f2534dn);
                    }
/* 178 */           byte[] encoded = new byte[hexLen / 2];
                    int p = this.beg + 1;
                    for (int i2 = 0; i2 < encoded.length; i2++) {
/* 180 */               encoded[i2] = (byte) getByte(p);
/* 179 */               p += 2;
                    }
/* 183 */           return new String(this.chars, this.beg, hexLen);
                }

/* 187 */       private String escapedAV() {
/* 188 */           this.beg = this.pos;
/* 189 */           this.end = this.pos;
/* 191 */           while (this.pos < this.length) {
                        switch (this.chars[this.pos]) {
                            case ' ':
/* 210 */                       this.cur = this.end;
/* 212 */                       this.pos++;
/* 213 */                       char[] cArr = this.chars;
                                int i = this.end;
                                this.end = i + 1;
                                cArr[i] = ' ';
/* 215 */                       while (this.pos < this.length && this.chars[this.pos] == ' ') {
/* 216 */                           char[] cArr2 = this.chars;
                                    int i2 = this.end;
                                    this.end = i2 + 1;
                                    cArr2[i2] = ' ';
/* 215 */                           this.pos++;
                                }
/* 218 */                       if (this.pos == this.length || this.chars[this.pos] == ',' || this.chars[this.pos] == '+' || this.chars[this.pos] == ';') {
/* 221 */                           return new String(this.chars, this.beg, this.cur - this.beg);
                                }
                                break;
                            case '+':
                            case ',':
                            case ';':
/* 201 */                       return new String(this.chars, this.beg, this.end - this.beg);
                            case '\\':
/* 204 */                       char[] cArr3 = this.chars;
                                int i3 = this.end;
                                this.end = i3 + 1;
                                cArr3[i3] = getEscaped();
/* 205 */                       this.pos++;
                                break;
                            default:
/* 225 */                       char[] cArr4 = this.chars;
                                int i4 = this.end;
                                this.end = i4 + 1;
                                cArr4[i4] = this.chars[this.pos];
/* 226 */                       this.pos++;
                                break;
                        }
                    }
/* 193 */           return new String(this.chars, this.beg, this.end - this.beg);
                }

/* 232 */       private char getEscaped() {
/* 233 */           this.pos++;
/* 234 */           if (this.pos == this.length) {
/* 235 */               throw new IllegalStateException("Unexpected end of DN: " + this.f2534dn);
                    }
                    switch (this.chars[this.pos]) {
                        case ' ':
                        case '\"':
                        case '#':
                        case '%':
                        case '*':
                        case '+':
                        case ',':
                        case ';':
                        case '<':
                        case '=':
                        case '>':
                        case '\\':
                        case '_':
/* 253 */                   return this.chars[this.pos];
                        default:
/* 257 */                   return getUTF8();
                    }
                }

/* 263 */       private char getUTF8() {
                    int count;
                    int res;
/* 264 */           int res2 = getByte(this.pos);
/* 265 */           this.pos++;
/* 267 */           if (res2 < 128) {
/* 268 */               return (char) res2;
                    }
/* 269 */           if (res2 < 192 || res2 > 247) {
                        return '?';
                    }
/* 272 */           if (res2 <= 223) {
/* 273 */               count = 1;
/* 274 */               res = res2 & 31;
                    } else if (res2 <= 239) {
/* 276 */               count = 2;
/* 277 */               res = res2 & 15;
                    } else {
/* 279 */               count = 3;
/* 280 */               res = res2 & 7;
                    }
                    for (int i = 0; i < count; i++) {
/* 285 */               this.pos++;
/* 286 */               if (this.pos == this.length || this.chars[this.pos] != '\\') {
                            return '?';
                        }
/* 289 */               this.pos++;
/* 291 */               int b = getByte(this.pos);
/* 292 */               this.pos++;
/* 293 */               if ((b & 192) != 128) {
                            return '?';
                        }
/* 297 */               res = (res << 6) + (b & 63);
                    }
/* 299 */           return (char) res;
                }

/* 311 */       private int getByte(int position) {
                    int b1;
                    int b2;
/* 312 */           if (position + 1 >= this.length) {
/* 313 */               throw new IllegalStateException("Malformed DN: " + this.f2534dn);
                    }
/* 318 */           char c = this.chars[position];
/* 319 */           if (c >= '0' && c <= '9') {
/* 320 */               b1 = c - '0';
                    } else if (c >= 'a' && c <= 'f') {
/* 322 */               b1 = c - 'W';
                    } else if (c >= 'A' && c <= 'F') {
/* 324 */               b1 = c - '7';
                    } else {
/* 326 */               throw new IllegalStateException("Malformed DN: " + this.f2534dn);
                    }
/* 329 */           char c2 = this.chars[position + 1];
/* 330 */           if (c2 >= '0' && c2 <= '9') {
/* 331 */               b2 = c2 - '0';
                    } else if (c2 >= 'a' && c2 <= 'f') {
/* 333 */               b2 = c2 - 'W';
                    } else if (c2 >= 'A' && c2 <= 'F') {
/* 335 */               b2 = c2 - '7';
                    } else {
/* 337 */               throw new IllegalStateException("Malformed DN: " + this.f2534dn);
                    }
/* 340 */           return (b1 << 4) + b2;
                }

/* 350 */       public String findMostSpecific(String attributeType) {
/* 351 */           this.pos = 0;
/* 352 */           this.beg = 0;
/* 353 */           this.end = 0;
/* 354 */           this.cur = 0;
/* 355 */           this.chars = this.f2534dn.toCharArray();
/* 357 */           String attType = nextAT();
/* 358 */           if (attType == null) {
                        return null;
                    }
/* 402 */           do {
/* 362 */               String attValue = "";
/* 364 */               if (this.pos == this.length) {
                            return null;
                        }
                        switch (this.chars[this.pos]) {
                            case '\"':
/* 370 */                       attValue = quotedAV();
                                break;
                            case '#':
/* 373 */                       attValue = hexAV();
                                break;
                            case '+':
                            case ',':
                            case ';':
                                break;
                            default:
/* 381 */                       attValue = escapedAV();
                                break;
                        }
/* 387 */               if (!attributeType.equalsIgnoreCase(attType)) {
/* 391 */                   if (this.pos >= this.length) {
                                return null;
                            }
/* 395 */                   if (this.chars[this.pos] != ',' && this.chars[this.pos] != ';' && this.chars[this.pos] != '+') {
/* 397 */                       throw new IllegalStateException("Malformed DN: " + this.f2534dn);
                            }
/* 400 */                   this.pos++;
/* 401 */                   attType = nextAT();
                        } else {
/* 392 */                   return attValue;
                        }
/* 402 */           } while (attType != null);
/* 403 */           throw new IllegalStateException("Malformed DN: " + this.f2534dn);
                }
            }
