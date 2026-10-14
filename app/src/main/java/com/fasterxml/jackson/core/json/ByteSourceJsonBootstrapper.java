            package com.fasterxml.jackson.core.json;

            import com.fasterxml.jackson.core.JsonEncoding;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.format.InputAccessor;
            import com.fasterxml.jackson.core.format.MatchStrength;
            import com.fasterxml.jackson.core.p003io.IOContext;
            import com.fasterxml.jackson.core.p003io.MergedStream;
            import com.fasterxml.jackson.core.p003io.UTF32Reader;
            import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
            import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
            import com.flurry.android.Constants;
            import java.io.ByteArrayInputStream;
            import java.io.CharConversionException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.Reader;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 83 */    public final class ByteSourceJsonBootstrapper {
                static final byte UTF8_BOM_1 = -17;
                static final byte UTF8_BOM_2 = -69;
                static final byte UTF8_BOM_3 = -65;
                protected boolean _bigEndian;
                private final boolean _bufferRecyclable;
                protected int _bytesPerChar;
                protected final IOContext _context;
                protected final InputStream _in;
                protected final byte[] _inputBuffer;
                private int _inputEnd;
                protected int _inputProcessed;
                private int _inputPtr;

/* 84 */        public ByteSourceJsonBootstrapper(IOContext iOContext, InputStream inputStream) {
/* 74 */            this._bigEndian = true;
/* 76 */            this._bytesPerChar = 0;
/* 86 */            this._context = iOContext;
/* 87 */            this._in = inputStream;
/* 88 */            this._inputBuffer = iOContext.allocReadIOBuffer();
/* 89 */            this._inputPtr = 0;
                    this._inputEnd = 0;
/* 90 */            this._inputProcessed = 0;
/* 91 */            this._bufferRecyclable = true;
                }

/* 94 */        public ByteSourceJsonBootstrapper(IOContext iOContext, byte[] bArr, int i, int i2) {
/* 74 */            this._bigEndian = true;
/* 76 */            this._bytesPerChar = 0;
/* 96 */            this._context = iOContext;
/* 97 */            this._in = null;
/* 98 */            this._inputBuffer = bArr;
/* 99 */            this._inputPtr = i;
/* 100 */           this._inputEnd = i + i2;
/* 102 */           this._inputProcessed = -i;
/* 103 */           this._bufferRecyclable = false;
                }

                /* JADX WARN: Code duplicated, block: B:14:0x0054  */
/* 119 */       public JsonEncoding detectEncoding() throws IOException {
                    JsonEncoding jsonEncoding;
                    boolean z = true;
/* 129 */           if (ensureLoaded(4)) {
/* 130 */               int i = (this._inputBuffer[this._inputPtr] << 24) | ((this._inputBuffer[this._inputPtr + 1] & Constants.UNKNOWN) << 16) | ((this._inputBuffer[this._inputPtr + 2] & Constants.UNKNOWN) << 8) | (this._inputBuffer[this._inputPtr + 3] & Constants.UNKNOWN);
/* 135 */               if (!handleBOM(i) && !checkUTF32(i) && !checkUTF16(i >>> 16)) {
/* 120 */                   z = false;
                        }
                    } else if (!ensureLoaded(2) || !checkUTF16(((this._inputBuffer[this._inputPtr] & Constants.UNKNOWN) << 8) | (this._inputBuffer[this._inputPtr + 1] & Constants.UNKNOWN))) {
/* 120 */               z = false;
                    }
/* 161 */           if (!z) {
/* 162 */               jsonEncoding = JsonEncoding.UTF8;
                    } else {
                        switch (this._bytesPerChar) {
                            case 1:
/* 166 */                       jsonEncoding = JsonEncoding.UTF8;
                                break;
                            case 2:
/* 169 */                       jsonEncoding = !this._bigEndian ? JsonEncoding.UTF16_LE : JsonEncoding.UTF16_BE;
                                break;
                            case 3:
                            default:
/* 175 */                       throw new RuntimeException("Internal error");
                            case 4:
/* 172 */                       jsonEncoding = !this._bigEndian ? JsonEncoding.UTF32_LE : JsonEncoding.UTF32_BE;
                                break;
                        }
                    }
/* 178 */           this._context.setEncoding(jsonEncoding);
/* 179 */           return jsonEncoding;
                }

/* 191 */       public Reader constructReader() throws IOException {
                    InputStream mergedStream;
/* 192 */           JsonEncoding encoding = this._context.getEncoding();
                    switch (encoding.bits()) {
                        case 8:
                        case 16:
/* 198 */                   InputStream inputStream = this._in;
/* 200 */                   if (inputStream == null) {
/* 201 */                       mergedStream = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
                            } else {
/* 207 */                       mergedStream = this._inputPtr < this._inputEnd ? new MergedStream(this._context, inputStream, this._inputBuffer, this._inputPtr, this._inputEnd) : inputStream;
                            }
/* 210 */                   return new InputStreamReader(mergedStream, encoding.getJavaName());
                        case 32:
/* 213 */                   return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
                        default:
/* 216 */                   throw new RuntimeException("Internal error");
                    }
                }

/* 223 */       public JsonParser constructParser(int i, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, CharsToNameCanonicalizer charsToNameCanonicalizer, boolean z, boolean z2) throws IOException {
/* 226 */           if (detectEncoding() != JsonEncoding.UTF8 || !z) {
                        return new ReaderBasedJsonParser(this._context, i, constructReader(), objectCodec, charsToNameCanonicalizer.makeChild(z, z2));
                    }
/* 232 */           return new UTF8StreamJsonParser(this._context, i, this._in, objectCodec, bytesToNameCanonicalizer.makeChild(z, z2), this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
                }

/* 255 */       public static MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
/* 256 */           if (!inputAccessor.hasMoreBytes()) {
/* 257 */               return MatchStrength.INCONCLUSIVE;
                    }
/* 259 */           byte bNextByte = inputAccessor.nextByte();
/* 261 */           if (bNextByte == -17) {
/* 262 */               if (!inputAccessor.hasMoreBytes()) {
/* 263 */                   return MatchStrength.INCONCLUSIVE;
                        }
/* 265 */               if (inputAccessor.nextByte() != -69) {
/* 266 */                   return MatchStrength.NO_MATCH;
                        }
/* 268 */               if (!inputAccessor.hasMoreBytes()) {
/* 269 */                   return MatchStrength.INCONCLUSIVE;
                        }
/* 271 */               if (inputAccessor.nextByte() != -65) {
/* 272 */                   return MatchStrength.NO_MATCH;
                        }
/* 274 */               if (!inputAccessor.hasMoreBytes()) {
/* 275 */                   return MatchStrength.INCONCLUSIVE;
                        }
/* 277 */               bNextByte = inputAccessor.nextByte();
                    }
/* 280 */           int iSkipSpace = skipSpace(inputAccessor, bNextByte);
/* 281 */           if (iSkipSpace < 0) {
/* 282 */               return MatchStrength.INCONCLUSIVE;
                    }
/* 285 */           if (iSkipSpace == 123) {
/* 287 */               int iSkipSpace2 = skipSpace(inputAccessor);
/* 288 */               if (iSkipSpace2 < 0) {
/* 289 */                   return MatchStrength.INCONCLUSIVE;
                        }
/* 291 */               if (iSkipSpace2 == 34 || iSkipSpace2 == 125) {
/* 292 */                   return MatchStrength.SOLID_MATCH;
                        }
/* 295 */               return MatchStrength.NO_MATCH;
                    }
/* 299 */           if (iSkipSpace == 91) {
/* 300 */               int iSkipSpace3 = skipSpace(inputAccessor);
/* 301 */               if (iSkipSpace3 < 0) {
/* 302 */                   return MatchStrength.INCONCLUSIVE;
                        }
/* 305 */               if (iSkipSpace3 == 93 || iSkipSpace3 == 91) {
/* 306 */                   return MatchStrength.SOLID_MATCH;
                        }
/* 308 */               return MatchStrength.SOLID_MATCH;
                    }
/* 311 */           MatchStrength matchStrength = MatchStrength.WEAK_MATCH;
/* 314 */           if (iSkipSpace != 34) {
/* 317 */               if (iSkipSpace > 57 || iSkipSpace < 48) {
/* 320 */                   if (iSkipSpace == 45) {
/* 321 */                       int iSkipSpace4 = skipSpace(inputAccessor);
/* 322 */                       if (iSkipSpace4 < 0) {
/* 323 */                           return MatchStrength.INCONCLUSIVE;
                                }
                                return (iSkipSpace4 > 57 || iSkipSpace4 < 48) ? MatchStrength.NO_MATCH : matchStrength;
                            }
/* 328 */                   if (iSkipSpace == 110) {
/* 329 */                       return tryMatch(inputAccessor, "ull", matchStrength);
                            }
/* 331 */                   if (iSkipSpace == 116) {
/* 332 */                       return tryMatch(inputAccessor, "rue", matchStrength);
                            }
/* 334 */                   if (iSkipSpace == 102) {
/* 335 */                       return tryMatch(inputAccessor, "alse", matchStrength);
                            }
/* 337 */                   return MatchStrength.NO_MATCH;
                        }
/* 337 */               return matchStrength;
                    }
/* 337 */           return matchStrength;
                }

/* 342 */       private static MatchStrength tryMatch(InputAccessor inputAccessor, String str, MatchStrength matchStrength) throws IOException {
                    int length = str.length();
                    for (int i = 0; i < length; i++) {
/* 344 */               if (!inputAccessor.hasMoreBytes()) {
/* 345 */                   return MatchStrength.INCONCLUSIVE;
                        }
/* 347 */               if (inputAccessor.nextByte() != str.charAt(i)) {
/* 348 */                   return MatchStrength.NO_MATCH;
                        }
                    }
/* 351 */           return matchStrength;
                }

/* 355 */       private static int skipSpace(InputAccessor inputAccessor) throws IOException {
/* 356 */           if (inputAccessor.hasMoreBytes()) {
                        return skipSpace(inputAccessor, inputAccessor.nextByte());
                    }
/* 357 */           return -1;
                }

/* 364 */       private static int skipSpace(InputAccessor inputAccessor, byte b) throws IOException {
                    while (true) {
/* 365 */               int i = b & Constants.UNKNOWN;
/* 366 */               if (i == 32 || i == 13 || i == 10 || i == 9) {
/* 369 */                   if (!inputAccessor.hasMoreBytes()) {
/* 370 */                       return -1;
                            }
/* 372 */                   b = inputAccessor.nextByte();
/* 373 */                   int i2 = b & Constants.UNKNOWN;
                        } else {
/* 370 */                   return i;
                        }
                    }
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code duplicated, block: B:12:0x003a  */
                /* JADX WARN: Code duplicated, block: B:14:0x003f  */
                /* JADX WARN: Code duplicated, block: B:15:0x004a  */
                /* JADX WARN: Code duplicated, block: B:17:0x0051  */
                /* JADX WARN: Code duplicated, block: B:18:0x005c  */
                /* JADX WARN: Code duplicated, block: B:6:0x000e  */
/* 392 */       private boolean handleBOM(int i) throws IOException {
                    int i2;
                    switch (i) {
                        case -16842752:
/* 407 */                   reportWeirdUCS4("3412");
/* 410 */                   i2 = i >>> 16;
/* 411 */                   if (i2 == 65279) {
/* 412 */                       this._inputPtr += 2;
/* 413 */                       this._bytesPerChar = 2;
/* 414 */                       this._bigEndian = true;
                                return true;
                            }
/* 417 */                   if (i2 == 65534) {
/* 418 */                       this._inputPtr += 2;
/* 419 */                       this._bytesPerChar = 2;
/* 420 */                       this._bigEndian = false;
                                return true;
                            }
/* 424 */                   if ((i >>> 8) != 15711167) {
                                return false;
                            }
/* 425 */                   this._inputPtr += 3;
/* 426 */                   this._bytesPerChar = 1;
/* 427 */                   this._bigEndian = true;
                            return true;
                        case -131072:
/* 400 */                   this._inputPtr += 4;
/* 401 */                   this._bytesPerChar = 4;
/* 402 */                   this._bigEndian = false;
                            return true;
                        case 65279:
/* 395 */                   this._bigEndian = true;
/* 396 */                   this._inputPtr += 4;
/* 397 */                   this._bytesPerChar = 4;
                            return true;
                        case 65534:
/* 405 */                   reportWeirdUCS4("2143");
/* 407 */                   reportWeirdUCS4("3412");
/* 410 */                   i2 = i >>> 16;
/* 411 */                   if (i2 == 65279) {
/* 412 */                       this._inputPtr += 2;
/* 413 */                       this._bytesPerChar = 2;
/* 414 */                       this._bigEndian = true;
                                return true;
                            }
/* 417 */                   if (i2 == 65534) {
/* 418 */                       this._inputPtr += 2;
/* 419 */                       this._bytesPerChar = 2;
/* 420 */                       this._bigEndian = false;
                                return true;
                            }
/* 424 */                   if ((i >>> 8) != 15711167) {
                                return false;
                            }
/* 425 */                   this._inputPtr += 3;
/* 426 */                   this._bytesPerChar = 1;
/* 427 */                   this._bigEndian = true;
                            return true;
                        default:
/* 410 */                   i2 = i >>> 16;
/* 411 */                   if (i2 == 65279) {
/* 412 */                       this._inputPtr += 2;
/* 413 */                       this._bytesPerChar = 2;
/* 414 */                       this._bigEndian = true;
                                return true;
                            }
/* 417 */                   if (i2 == 65534) {
/* 418 */                       this._inputPtr += 2;
/* 419 */                       this._bytesPerChar = 2;
/* 420 */                       this._bigEndian = false;
                                return true;
                            }
/* 424 */                   if ((i >>> 8) != 15711167) {
                                return false;
                            }
/* 425 */                   this._inputPtr += 3;
/* 426 */                   this._bytesPerChar = 1;
/* 427 */                   this._bigEndian = true;
                            return true;
                    }
                }

/* 438 */       private boolean checkUTF32(int i) throws IOException {
/* 439 */           if ((i >> 8) == 0) {
/* 440 */               this._bigEndian = true;
                    } else if ((16777215 & i) == 0) {
/* 442 */               this._bigEndian = false;
                    } else if (((-16711681) & i) == 0) {
/* 444 */               reportWeirdUCS4("3412");
                    } else {
/* 445 */               if (((-65281) & i) != 0) {
                            return false;
                        }
/* 446 */               reportWeirdUCS4("2143");
                    }
/* 453 */           this._bytesPerChar = 4;
                    return true;
                }

/* 458 */       private boolean checkUTF16(int i) {
/* 459 */           if ((65280 & i) == 0) {
/* 460 */               this._bigEndian = true;
                    } else {
/* 461 */               if ((i & 255) != 0) {
                            return false;
                        }
/* 462 */               this._bigEndian = false;
                    }
/* 468 */           this._bytesPerChar = 2;
                    return true;
                }

/* 480 */       private void reportWeirdUCS4(String str) throws IOException {
/* 481 */           throw new CharConversionException("Unsupported UCS-4 endianness (" + str + ") detected");
                }

/* 495 */       protected boolean ensureLoaded(int i) throws IOException {
                    int i2;
/* 496 */           int i3 = this._inputEnd - this._inputPtr;
/* 497 */           while (i3 < i) {
/* 500 */               if (this._in == null) {
/* 501 */                   i2 = -1;
                        } else {
/* 503 */                   i2 = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
                        }
/* 505 */               if (i2 < 1) {
/* 506 */                   return false;
                        }
/* 508 */               this._inputEnd += i2;
/* 509 */               i3 = i2 + i3;
                    }
                    return true;
                }
            }
