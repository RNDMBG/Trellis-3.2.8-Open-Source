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
            
            
    public final class ByteSourceJsonBootstrapper {
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

        public ByteSourceJsonBootstrapper(IOContext iOContext, InputStream inputStream) {
            this._bigEndian = true;
            this._bytesPerChar = 0;
            this._context = iOContext;
            this._in = inputStream;
            this._inputBuffer = iOContext.allocReadIOBuffer();
            this._inputPtr = 0;
                    this._inputEnd = 0;
            this._inputProcessed = 0;
            this._bufferRecyclable = true;
                }

        public ByteSourceJsonBootstrapper(IOContext iOContext, byte[] bArr, int i, int i2) {
            this._bigEndian = true;
            this._bytesPerChar = 0;
            this._context = iOContext;
            this._in = null;
            this._inputBuffer = bArr;
            this._inputPtr = i;
           this._inputEnd = i + i2;
           this._inputProcessed = -i;
           this._bufferRecyclable = false;
                }

                
       public JsonEncoding detectEncoding() throws IOException {
                    JsonEncoding jsonEncoding;
                    boolean z = true;
           if (ensureLoaded(4)) {
               int i = (this._inputBuffer[this._inputPtr] << 24) | ((this._inputBuffer[this._inputPtr + 1] & Constants.UNKNOWN) << 16) | ((this._inputBuffer[this._inputPtr + 2] & Constants.UNKNOWN) << 8) | (this._inputBuffer[this._inputPtr + 3] & Constants.UNKNOWN);
               if (!handleBOM(i) && !checkUTF32(i) && !checkUTF16(i >>> 16)) {
                   z = false;
                        }
                    } else if (!ensureLoaded(2) || !checkUTF16(((this._inputBuffer[this._inputPtr] & Constants.UNKNOWN) << 8) | (this._inputBuffer[this._inputPtr + 1] & Constants.UNKNOWN))) {
               z = false;
                    }
           if (!z) {
               jsonEncoding = JsonEncoding.UTF8;
                    } else {
                        switch (this._bytesPerChar) {
                            case 1:
                       jsonEncoding = JsonEncoding.UTF8;
                                break;
                            case 2:
                       jsonEncoding = !this._bigEndian ? JsonEncoding.UTF16_LE : JsonEncoding.UTF16_BE;
                                break;
                            case 3:
                            default:
                       throw new RuntimeException("Internal error");
                            case 4:
                       jsonEncoding = !this._bigEndian ? JsonEncoding.UTF32_LE : JsonEncoding.UTF32_BE;
                                break;
                        }
                    }
           this._context.setEncoding(jsonEncoding);
           return jsonEncoding;
                }

       public Reader constructReader() throws IOException {
                    InputStream mergedStream;
           JsonEncoding encoding = this._context.getEncoding();
                    switch (encoding.bits()) {
                        case 8:
                        case 16:
                   InputStream inputStream = this._in;
                   if (inputStream == null) {
                       mergedStream = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
                            } else {
                       mergedStream = this._inputPtr < this._inputEnd ? new MergedStream(this._context, inputStream, this._inputBuffer, this._inputPtr, this._inputEnd) : inputStream;
                            }
                   return new InputStreamReader(mergedStream, encoding.getJavaName());
                        case 32:
                   return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
                        default:
                   throw new RuntimeException("Internal error");
                    }
                }

       public JsonParser constructParser(int i, ObjectCodec objectCodec, BytesToNameCanonicalizer bytesToNameCanonicalizer, CharsToNameCanonicalizer charsToNameCanonicalizer, boolean z, boolean z2) throws IOException {
           if (detectEncoding() != JsonEncoding.UTF8 || !z) {
                        return new ReaderBasedJsonParser(this._context, i, constructReader(), objectCodec, charsToNameCanonicalizer.makeChild(z, z2));
                    }
           return new UTF8StreamJsonParser(this._context, i, this._in, objectCodec, bytesToNameCanonicalizer.makeChild(z, z2), this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
                }

       public static MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
           if (!inputAccessor.hasMoreBytes()) {
               return MatchStrength.INCONCLUSIVE;
                    }
           byte bNextByte = inputAccessor.nextByte();
           if (bNextByte == -17) {
               if (!inputAccessor.hasMoreBytes()) {
                   return MatchStrength.INCONCLUSIVE;
                        }
               if (inputAccessor.nextByte() != -69) {
                   return MatchStrength.NO_MATCH;
                        }
               if (!inputAccessor.hasMoreBytes()) {
                   return MatchStrength.INCONCLUSIVE;
                        }
               if (inputAccessor.nextByte() != -65) {
                   return MatchStrength.NO_MATCH;
                        }
               if (!inputAccessor.hasMoreBytes()) {
                   return MatchStrength.INCONCLUSIVE;
                        }
               bNextByte = inputAccessor.nextByte();
                    }
           int iSkipSpace = skipSpace(inputAccessor, bNextByte);
           if (iSkipSpace < 0) {
               return MatchStrength.INCONCLUSIVE;
                    }
           if (iSkipSpace == 123) {
               int iSkipSpace2 = skipSpace(inputAccessor);
               if (iSkipSpace2 < 0) {
                   return MatchStrength.INCONCLUSIVE;
                        }
               if (iSkipSpace2 == 34 || iSkipSpace2 == 125) {
                   return MatchStrength.SOLID_MATCH;
                        }
               return MatchStrength.NO_MATCH;
                    }
           if (iSkipSpace == 91) {
               int iSkipSpace3 = skipSpace(inputAccessor);
               if (iSkipSpace3 < 0) {
                   return MatchStrength.INCONCLUSIVE;
                        }
               if (iSkipSpace3 == 93 || iSkipSpace3 == 91) {
                   return MatchStrength.SOLID_MATCH;
                        }
               return MatchStrength.SOLID_MATCH;
                    }
           MatchStrength matchStrength = MatchStrength.WEAK_MATCH;
           if (iSkipSpace != 34) {
               if (iSkipSpace > 57 || iSkipSpace < 48) {
                   if (iSkipSpace == 45) {
                       int iSkipSpace4 = skipSpace(inputAccessor);
                       if (iSkipSpace4 < 0) {
                           return MatchStrength.INCONCLUSIVE;
                                }
                                return (iSkipSpace4 > 57 || iSkipSpace4 < 48) ? MatchStrength.NO_MATCH : matchStrength;
                            }
                   if (iSkipSpace == 110) {
                       return tryMatch(inputAccessor, "ull", matchStrength);
                            }
                   if (iSkipSpace == 116) {
                       return tryMatch(inputAccessor, "rue", matchStrength);
                            }
                   if (iSkipSpace == 102) {
                       return tryMatch(inputAccessor, "alse", matchStrength);
                            }
                   return MatchStrength.NO_MATCH;
                        }
               return matchStrength;
                    }
           return matchStrength;
                }

       private static MatchStrength tryMatch(InputAccessor inputAccessor, String str, MatchStrength matchStrength) throws IOException {
                    int length = str.length();
                    for (int i = 0; i < length; i++) {
               if (!inputAccessor.hasMoreBytes()) {
                   return MatchStrength.INCONCLUSIVE;
                        }
               if (inputAccessor.nextByte() != str.charAt(i)) {
                   return MatchStrength.NO_MATCH;
                        }
                    }
           return matchStrength;
                }

       private static int skipSpace(InputAccessor inputAccessor) throws IOException {
           if (inputAccessor.hasMoreBytes()) {
                        return skipSpace(inputAccessor, inputAccessor.nextByte());
                    }
           return -1;
                }

       private static int skipSpace(InputAccessor inputAccessor, byte b) throws IOException {
                    while (true) {
               int i = b & Constants.UNKNOWN;
               if (i == 32 || i == 13 || i == 10 || i == 9) {
                   if (!inputAccessor.hasMoreBytes()) {
                       return -1;
                            }
                   b = inputAccessor.nextByte();
                   int i2 = b & Constants.UNKNOWN;
                        } else {
                   return i;
                        }
                    }
                }

                
                
                
                
                
                
                
       private boolean handleBOM(int i) throws IOException {
                    int i2;
                    switch (i) {
                        case -16842752:
                   reportWeirdUCS4("3412");
                   i2 = i >>> 16;
                   if (i2 == 65279) {
                       this._inputPtr += 2;
                       this._bytesPerChar = 2;
                       this._bigEndian = true;
                                return true;
                            }
                   if (i2 == 65534) {
                       this._inputPtr += 2;
                       this._bytesPerChar = 2;
                       this._bigEndian = false;
                                return true;
                            }
                   if ((i >>> 8) != 15711167) {
                                return false;
                            }
                   this._inputPtr += 3;
                   this._bytesPerChar = 1;
                   this._bigEndian = true;
                            return true;
                        case -131072:
                   this._inputPtr += 4;
                   this._bytesPerChar = 4;
                   this._bigEndian = false;
                            return true;
                        case 65279:
                   this._bigEndian = true;
                   this._inputPtr += 4;
                   this._bytesPerChar = 4;
                            return true;
                        case 65534:
                   reportWeirdUCS4("2143");
                   reportWeirdUCS4("3412");
                   i2 = i >>> 16;
                   if (i2 == 65279) {
                       this._inputPtr += 2;
                       this._bytesPerChar = 2;
                       this._bigEndian = true;
                                return true;
                            }
                   if (i2 == 65534) {
                       this._inputPtr += 2;
                       this._bytesPerChar = 2;
                       this._bigEndian = false;
                                return true;
                            }
                   if ((i >>> 8) != 15711167) {
                                return false;
                            }
                   this._inputPtr += 3;
                   this._bytesPerChar = 1;
                   this._bigEndian = true;
                            return true;
                        default:
                   i2 = i >>> 16;
                   if (i2 == 65279) {
                       this._inputPtr += 2;
                       this._bytesPerChar = 2;
                       this._bigEndian = true;
                                return true;
                            }
                   if (i2 == 65534) {
                       this._inputPtr += 2;
                       this._bytesPerChar = 2;
                       this._bigEndian = false;
                                return true;
                            }
                   if ((i >>> 8) != 15711167) {
                                return false;
                            }
                   this._inputPtr += 3;
                   this._bytesPerChar = 1;
                   this._bigEndian = true;
                            return true;
                    }
                }

       private boolean checkUTF32(int i) throws IOException {
           if ((i >> 8) == 0) {
               this._bigEndian = true;
                    } else if ((16777215 & i) == 0) {
               this._bigEndian = false;
                    } else if (((-16711681) & i) == 0) {
               reportWeirdUCS4("3412");
                    } else {
               if (((-65281) & i) != 0) {
                            return false;
                        }
               reportWeirdUCS4("2143");
                    }
           this._bytesPerChar = 4;
                    return true;
                }

       private boolean checkUTF16(int i) {
           if ((65280 & i) == 0) {
               this._bigEndian = true;
                    } else {
               if ((i & 255) != 0) {
                            return false;
                        }
               this._bigEndian = false;
                    }
           this._bytesPerChar = 2;
                    return true;
                }

       private void reportWeirdUCS4(String str) throws IOException {
           throw new CharConversionException("Unsupported UCS-4 endianness (" + str + ") detected");
                }

       protected boolean ensureLoaded(int i) throws IOException {
                    int i2;
           int i3 = this._inputEnd - this._inputPtr;
           while (i3 < i) {
               if (this._in == null) {
                   i2 = -1;
                        } else {
                   i2 = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
                        }
               if (i2 < 1) {
                   return false;
                        }
               this._inputEnd += i2;
               i3 = i2 + i3;
                    }
                    return true;
                }
            }
