            package com.fasterxml.jackson.core.json;

            import android.support.v4.internal.view.SupportMenu;
            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.p003io.CharTypes;
            import com.fasterxml.jackson.core.p003io.CharacterEscapes;
            import com.fasterxml.jackson.core.p003io.IOContext;
            import com.fasterxml.jackson.core.p003io.NumberOutput;
            import com.flurry.android.Constants;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 114 */   public class UTF8JsonGenerator extends JsonGeneratorImpl {
                private static final byte BYTE_0 = 48;
                private static final byte BYTE_BACKSLASH = 92;
                private static final byte BYTE_COLON = 58;
                private static final byte BYTE_COMMA = 44;
                private static final byte BYTE_LBRACKET = 91;
                private static final byte BYTE_LCURLY = 123;
                private static final byte BYTE_QUOTE = 34;
                private static final byte BYTE_RBRACKET = 93;
                private static final byte BYTE_RCURLY = 125;
                private static final int MAX_BYTES_TO_BUFFER = 512;
                protected static final int SURR1_FIRST = 55296;
                protected static final int SURR1_LAST = 56319;
                protected static final int SURR2_FIRST = 56320;
                protected static final int SURR2_LAST = 57343;
                protected boolean _bufferRecyclable;
                protected final boolean _cfgQuoteNames;
                protected char[] _charBuffer;
                protected final int _charBufferLength;
                protected byte[] _entityBuffer;
                protected byte[] _outputBuffer;
                protected final int _outputEnd;
                protected final int _outputMaxContiguous;
                protected final OutputStream _outputStream;
                protected int _outputTail;
                static final byte[] HEX_CHARS = CharTypes.copyHexBytes();
                private static final byte BYTE_u = 117;
                private static final byte[] NULL_BYTES = {110, BYTE_u, 108, 108};
                private static final byte[] TRUE_BYTES = {116, 114, BYTE_u, 101};
                private static final byte[] FALSE_BYTES = {102, 97, 108, 115, 101};

/* 115 */       public UTF8JsonGenerator(IOContext iOContext, int i, ObjectCodec objectCodec, OutputStream outputStream) {
/* 116 */           super(iOContext, i, objectCodec);
/* 62 */            this._outputTail = 0;
/* 117 */           this._outputStream = outputStream;
/* 118 */           this._bufferRecyclable = true;
/* 119 */           this._outputBuffer = iOContext.allocWriteEncodingBuffer();
/* 120 */           this._outputEnd = this._outputBuffer.length;
/* 126 */           this._outputMaxContiguous = this._outputEnd >> 3;
/* 127 */           this._charBuffer = iOContext.allocConcatBuffer();
/* 128 */           this._charBufferLength = this._charBuffer.length;
/* 131 */           if (isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
/* 132 */               setHighestNonEscapedChar(127);
                    }
/* 134 */           this._cfgQuoteNames = JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(i);
                }

/* 141 */       public UTF8JsonGenerator(IOContext iOContext, int i, ObjectCodec objectCodec, OutputStream outputStream, byte[] bArr, int i2, boolean z) {
/* 142 */           super(iOContext, i, objectCodec);
/* 62 */            this._outputTail = 0;
/* 143 */           this._outputStream = outputStream;
/* 144 */           this._bufferRecyclable = z;
/* 145 */           this._outputTail = i2;
/* 146 */           this._outputBuffer = bArr;
/* 147 */           this._outputEnd = this._outputBuffer.length;
/* 149 */           this._outputMaxContiguous = this._outputEnd >> 3;
/* 150 */           this._charBuffer = iOContext.allocConcatBuffer();
/* 151 */           this._charBufferLength = this._charBuffer.length;
/* 152 */           this._cfgQuoteNames = JsonGenerator.Feature.QUOTE_FIELD_NAMES.enabledIn(i);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 162 */       public Object getOutputTarget() {
/* 163 */           return this._outputStream;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 174 */       public void writeFieldName(String str) throws IOException {
/* 175 */           int iWriteFieldName = this._writeContext.writeFieldName(str);
/* 176 */           if (iWriteFieldName == 4) {
/* 177 */               _reportError("Can not write a field name, expecting a value");
                    }
/* 179 */           if (this._cfgPrettyPrinter != null) {
/* 180 */               _writePPFieldName(str, iWriteFieldName == 1);
/* 190 */               return;
                    }
/* 183 */           if (iWriteFieldName == 1) {
/* 184 */               if (this._outputTail >= this._outputEnd) {
/* 185 */                   _flushBuffer();
                        }
/* 187 */               byte[] bArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        bArr[i] = BYTE_COMMA;
                    }
/* 189 */           _writeFieldName(str);
                }

/* 197 */       protected final void _writeFieldName(String str) throws IOException {
/* 198 */           if (!this._cfgQuoteNames) {
/* 199 */               _writeStringSegments(str);
/* 228 */               return;
                    }
/* 202 */           if (this._outputTail >= this._outputEnd) {
/* 203 */               _flushBuffer();
                    }
/* 205 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 207 */           int length = str.length();
/* 208 */           if (length <= this._charBufferLength) {
/* 209 */               str.getChars(0, length, this._charBuffer, 0);
/* 211 */               if (length <= this._outputMaxContiguous) {
/* 212 */                   if (this._outputTail + length > this._outputEnd) {
/* 213 */                       _flushBuffer();
                            }
/* 215 */                   _writeStringSegment(this._charBuffer, 0, length);
                        } else {
/* 217 */                   _writeStringSegments(this._charBuffer, 0, length);
                        }
                    } else {
/* 220 */               _writeStringSegments(str);
                    }
/* 224 */           if (this._outputTail >= this._outputEnd) {
/* 225 */               _flushBuffer();
                    }
/* 227 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
/* 234 */       public void writeFieldName(SerializableString serializableString) throws IOException {
/* 235 */           int iWriteFieldName = this._writeContext.writeFieldName(serializableString.getValue());
/* 236 */           if (iWriteFieldName == 4) {
/* 237 */               _reportError("Can not write a field name, expecting a value");
                    }
/* 239 */           if (this._cfgPrettyPrinter != null) {
/* 240 */               _writePPFieldName(serializableString, iWriteFieldName == 1);
/* 250 */               return;
                    }
/* 243 */           if (iWriteFieldName == 1) {
/* 244 */               if (this._outputTail >= this._outputEnd) {
/* 245 */                   _flushBuffer();
                        }
/* 247 */               byte[] bArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        bArr[i] = BYTE_COMMA;
                    }
/* 249 */           _writeFieldName(serializableString);
                }

/* 254 */       protected final void _writeFieldName(SerializableString serializableString) throws IOException {
/* 255 */           if (!this._cfgQuoteNames) {
/* 256 */               int iAppendQuotedUTF8 = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/* 257 */               if (iAppendQuotedUTF8 < 0) {
/* 258 */                   _writeBytes(serializableString.asQuotedUTF8());
/* 278 */                   return;
                        } else {
/* 260 */                   this._outputTail = iAppendQuotedUTF8 + this._outputTail;
/* 278 */                   return;
                        }
                    }
/* 264 */           if (this._outputTail >= this._outputEnd) {
/* 265 */               _flushBuffer();
                    }
/* 267 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 268 */           int iAppendQuotedUTF9 = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/* 269 */           if (iAppendQuotedUTF9 < 0) {
/* 270 */               _writeBytes(serializableString.asQuotedUTF8());
                    } else {
/* 272 */               this._outputTail = iAppendQuotedUTF9 + this._outputTail;
                    }
/* 274 */           if (this._outputTail >= this._outputEnd) {
/* 275 */               _flushBuffer();
                    }
/* 277 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 288 */       public final void writeStartArray() throws IOException {
/* 289 */           _verifyValueWrite("start an array");
/* 290 */           this._writeContext = this._writeContext.createChildArrayContext();
/* 291 */           if (this._cfgPrettyPrinter != null) {
/* 292 */               this._cfgPrettyPrinter.writeStartArray(this);
/* 299 */               return;
                    }
/* 294 */           if (this._outputTail >= this._outputEnd) {
/* 295 */               _flushBuffer();
                    }
/* 297 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_LBRACKET;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 303 */       public final void writeEndArray() throws IOException {
/* 304 */           if (!this._writeContext.inArray()) {
/* 305 */               _reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
                    }
/* 307 */           if (this._cfgPrettyPrinter != null) {
/* 308 */               this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
                    } else {
/* 310 */               if (this._outputTail >= this._outputEnd) {
/* 311 */                   _flushBuffer();
                        }
/* 313 */               byte[] bArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        bArr[i] = BYTE_RBRACKET;
                    }
/* 315 */           this._writeContext = this._writeContext.getParent();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 320 */       public final void writeStartObject() throws IOException {
/* 321 */           _verifyValueWrite("start an object");
/* 322 */           this._writeContext = this._writeContext.createChildObjectContext();
/* 323 */           if (this._cfgPrettyPrinter != null) {
/* 324 */               this._cfgPrettyPrinter.writeStartObject(this);
/* 331 */               return;
                    }
/* 326 */           if (this._outputTail >= this._outputEnd) {
/* 327 */               _flushBuffer();
                    }
/* 329 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_LCURLY;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 335 */       public final void writeEndObject() throws IOException {
/* 336 */           if (!this._writeContext.inObject()) {
/* 337 */               _reportError("Current context not an object but " + this._writeContext.getTypeDesc());
                    }
/* 339 */           if (this._cfgPrettyPrinter != null) {
/* 340 */               this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
                    } else {
/* 342 */               if (this._outputTail >= this._outputEnd) {
/* 343 */                   _flushBuffer();
                        }
/* 345 */               byte[] bArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        bArr[i] = BYTE_RCURLY;
                    }
/* 347 */           this._writeContext = this._writeContext.getParent();
                }

/* 356 */       protected final void _writePPFieldName(String str, boolean z) throws IOException {
/* 357 */           if (z) {
/* 358 */               this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
                    } else {
/* 360 */               this._cfgPrettyPrinter.beforeObjectEntries(this);
                    }
/* 363 */           if (this._cfgQuoteNames) {
/* 364 */               if (this._outputTail >= this._outputEnd) {
/* 365 */                   _flushBuffer();
                        }
/* 367 */               byte[] bArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        bArr[i] = BYTE_QUOTE;
/* 368 */               int length = str.length();
/* 369 */               if (length <= this._charBufferLength) {
/* 370 */                   str.getChars(0, length, this._charBuffer, 0);
/* 372 */                   if (length <= this._outputMaxContiguous) {
/* 373 */                       if (this._outputTail + length > this._outputEnd) {
/* 374 */                           _flushBuffer();
                                }
/* 376 */                       _writeStringSegment(this._charBuffer, 0, length);
                            } else {
/* 378 */                       _writeStringSegments(this._charBuffer, 0, length);
                            }
                        } else {
/* 381 */                   _writeStringSegments(str);
                        }
/* 383 */               if (this._outputTail >= this._outputEnd) {
/* 384 */                   _flushBuffer();
                        }
/* 386 */               byte[] bArr2 = this._outputBuffer;
                        int i2 = this._outputTail;
                        this._outputTail = i2 + 1;
                        bArr2[i2] = BYTE_QUOTE;
/* 390 */               return;
                    }
/* 388 */           _writeStringSegments(str);
                }

/* 394 */       protected final void _writePPFieldName(SerializableString serializableString, boolean z) throws IOException {
/* 395 */           if (z) {
/* 396 */               this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
                    } else {
/* 398 */               this._cfgPrettyPrinter.beforeObjectEntries(this);
                    }
/* 401 */           boolean z2 = this._cfgQuoteNames;
/* 402 */           if (z2) {
/* 403 */               if (this._outputTail >= this._outputEnd) {
/* 404 */                   _flushBuffer();
                        }
/* 406 */               byte[] bArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        bArr[i] = BYTE_QUOTE;
                    }
/* 408 */           _writeBytes(serializableString.asQuotedUTF8());
/* 409 */           if (z2) {
/* 410 */               if (this._outputTail >= this._outputEnd) {
/* 411 */                   _flushBuffer();
                        }
/* 413 */               byte[] bArr2 = this._outputBuffer;
                        int i2 = this._outputTail;
                        this._outputTail = i2 + 1;
                        bArr2[i2] = BYTE_QUOTE;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 426 */       public void writeString(String str) throws IOException {
/* 427 */           _verifyValueWrite("write text value");
/* 428 */           if (str == null) {
/* 429 */               _writeNull();
/* 457 */               return;
                    }
/* 433 */           int length = str.length();
/* 434 */           if (length > this._charBufferLength) {
/* 435 */               _writeLongString(str);
/* 457 */               return;
                    }
/* 439 */           str.getChars(0, length, this._charBuffer, 0);
/* 441 */           if (length > this._outputMaxContiguous) {
/* 442 */               _writeLongString(this._charBuffer, 0, length);
/* 457 */               return;
                    }
/* 445 */           if (this._outputTail + length >= this._outputEnd) {
/* 446 */               _flushBuffer();
                    }
/* 448 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 449 */           _writeStringSegment(this._charBuffer, 0, length);
/* 453 */           if (this._outputTail >= this._outputEnd) {
/* 454 */               _flushBuffer();
                    }
/* 456 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

/* 461 */       private void _writeLongString(String str) throws IOException {
/* 462 */           if (this._outputTail >= this._outputEnd) {
/* 463 */               _flushBuffer();
                    }
/* 465 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 466 */           _writeStringSegments(str);
/* 467 */           if (this._outputTail >= this._outputEnd) {
/* 468 */               _flushBuffer();
                    }
/* 470 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

/* 475 */       private void _writeLongString(char[] cArr, int i, int i2) throws IOException {
/* 476 */           if (this._outputTail >= this._outputEnd) {
/* 477 */               _flushBuffer();
                    }
/* 479 */           byte[] bArr = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    bArr[i3] = BYTE_QUOTE;
/* 480 */           _writeStringSegments(this._charBuffer, 0, i2);
/* 481 */           if (this._outputTail >= this._outputEnd) {
/* 482 */               _flushBuffer();
                    }
/* 484 */           byte[] bArr2 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    bArr2[i4] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 490 */       public void writeString(char[] cArr, int i, int i2) throws IOException {
/* 491 */           _verifyValueWrite("write text value");
/* 492 */           if (this._outputTail >= this._outputEnd) {
/* 493 */               _flushBuffer();
                    }
/* 495 */           byte[] bArr = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    bArr[i3] = BYTE_QUOTE;
/* 497 */           if (i2 <= this._outputMaxContiguous) {
/* 498 */               if (this._outputTail + i2 > this._outputEnd) {
/* 499 */                   _flushBuffer();
                        }
/* 501 */               _writeStringSegment(cArr, i, i2);
                    } else {
/* 503 */               _writeStringSegments(cArr, i, i2);
                    }
/* 506 */           if (this._outputTail >= this._outputEnd) {
/* 507 */               _flushBuffer();
                    }
/* 509 */           byte[] bArr2 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    bArr2[i4] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
/* 515 */       public final void writeString(SerializableString serializableString) throws IOException {
/* 516 */           _verifyValueWrite("write text value");
/* 517 */           if (this._outputTail >= this._outputEnd) {
/* 518 */               _flushBuffer();
                    }
/* 520 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 521 */           int iAppendQuotedUTF8 = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
/* 522 */           if (iAppendQuotedUTF8 < 0) {
/* 523 */               _writeBytes(serializableString.asQuotedUTF8());
                    } else {
/* 525 */               this._outputTail = iAppendQuotedUTF8 + this._outputTail;
                    }
/* 527 */           if (this._outputTail >= this._outputEnd) {
/* 528 */               _flushBuffer();
                    }
/* 530 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 536 */       public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
/* 537 */           _verifyValueWrite("write text value");
/* 538 */           if (this._outputTail >= this._outputEnd) {
/* 539 */               _flushBuffer();
                    }
/* 541 */           byte[] bArr2 = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    bArr2[i3] = BYTE_QUOTE;
/* 542 */           _writeBytes(bArr, i, i2);
/* 543 */           if (this._outputTail >= this._outputEnd) {
/* 544 */               _flushBuffer();
                    }
/* 546 */           byte[] bArr3 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    bArr3[i4] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 552 */       public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
/* 553 */           _verifyValueWrite("write text value");
/* 554 */           if (this._outputTail >= this._outputEnd) {
/* 555 */               _flushBuffer();
                    }
/* 557 */           byte[] bArr2 = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    bArr2[i3] = BYTE_QUOTE;
/* 559 */           if (i2 <= this._outputMaxContiguous) {
/* 560 */               _writeUTF8Segment(bArr, i, i2);
                    } else {
/* 562 */               _writeUTF8Segments(bArr, i, i2);
                    }
/* 564 */           if (this._outputTail >= this._outputEnd) {
/* 565 */               _flushBuffer();
                    }
/* 567 */           byte[] bArr3 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    bArr3[i4] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 580 */       public void writeRaw(String str) throws IOException {
/* 581 */           int length = str.length();
                    int i = 0;
/* 582 */           while (length > 0) {
/* 583 */               char[] cArr = this._charBuffer;
/* 584 */               int length2 = cArr.length;
/* 585 */               if (length < length2) {
                            length2 = length;
                        }
/* 586 */               str.getChars(i, i + length2, cArr, 0);
/* 587 */               writeRaw(cArr, 0, length2);
/* 588 */               i += length2;
/* 589 */               length -= length2;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 596 */       public void writeRaw(String str, int i, int i2) throws IOException {
/* 597 */           int i3 = i2;
                    while (i3 > 0) {
/* 598 */               char[] cArr = this._charBuffer;
/* 599 */               int length = cArr.length;
/* 600 */               if (i3 < length) {
                            length = i3;
                        }
/* 601 */               str.getChars(i, i + length, cArr, 0);
/* 602 */               writeRaw(cArr, 0, length);
/* 603 */               i += length;
/* 604 */               i3 -= length;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 610 */       public void writeRaw(SerializableString serializableString) throws IOException {
/* 611 */           byte[] bArrAsUnquotedUTF8 = serializableString.asUnquotedUTF8();
/* 612 */           if (bArrAsUnquotedUTF8.length > 0) {
/* 613 */               _writeBytes(bArrAsUnquotedUTF8);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 623 */       public final void writeRaw(char[] cArr, int i, int i2) throws IOException {
/* 624 */           int i3 = i2 + i2 + i2;
/* 625 */           if (this._outputTail + i3 > this._outputEnd) {
/* 627 */               if (this._outputEnd < i3) {
/* 628 */                   _writeSegmentedRaw(cArr, i, i2);
/* 659 */                   return;
                        }
/* 632 */               _flushBuffer();
                    }
/* 635 */           int i4 = i2 + i;
                    int i_outputRawMultiByteChar = i;
/* 639 */           while (i_outputRawMultiByteChar < i4) {
                        do {
/* 642 */                   char c = cArr[i_outputRawMultiByteChar];
/* 643 */                   if (c <= 127) {
/* 646 */                       byte[] bArr = this._outputBuffer;
                                int i5 = this._outputTail;
                                this._outputTail = i5 + 1;
                                bArr[i5] = (byte) c;
/* 647 */                       i_outputRawMultiByteChar++;
                            } else {
/* 651 */                       int i6 = i_outputRawMultiByteChar + 1;
                                char c2 = cArr[i_outputRawMultiByteChar];
/* 652 */                       if (c2 < 2048) {
/* 653 */                           byte[] bArr2 = this._outputBuffer;
                                    int i7 = this._outputTail;
                                    this._outputTail = i7 + 1;
                                    bArr2[i7] = (byte) ((c2 >> 6) | 192);
/* 654 */                           byte[] bArr3 = this._outputBuffer;
                                    int i8 = this._outputTail;
                                    this._outputTail = i8 + 1;
                                    bArr3[i8] = (byte) ((c2 & '?') | 128);
                                    i_outputRawMultiByteChar = i6;
                                } else {
/* 656 */                           i_outputRawMultiByteChar = _outputRawMultiByteChar(c2, cArr, i6, i4);
                                }
                            }
                        } while (i_outputRawMultiByteChar < i4);
/* 659 */               return;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 664 */       public void writeRaw(char c) throws IOException {
/* 665 */           if (this._outputTail + 3 >= this._outputEnd) {
/* 666 */               _flushBuffer();
                    }
/* 668 */           byte[] bArr = this._outputBuffer;
/* 669 */           if (c <= 127) {
/* 670 */               int i = this._outputTail;
                        this._outputTail = i + 1;
                        bArr[i] = (byte) c;
                    } else {
/* 671 */               if (c < 2048) {
/* 672 */                   int i2 = this._outputTail;
                            this._outputTail = i2 + 1;
                            bArr[i2] = (byte) ((c >> 6) | 192);
/* 673 */                   int i3 = this._outputTail;
                            this._outputTail = i3 + 1;
                            bArr[i3] = (byte) ((c & '?') | 128);
/* 677 */                   return;
                        }
/* 675 */               _outputRawMultiByteChar(c, null, 0, 0);
                    }
                }

/* 685 */       private final void _writeSegmentedRaw(char[] cArr, int i, int i2) throws IOException {
/* 686 */           int i3 = this._outputEnd;
/* 687 */           byte[] bArr = this._outputBuffer;
                    int i_outputRawMultiByteChar = i;
/* 690 */           while (i_outputRawMultiByteChar < i2) {
                        do {
/* 693 */                   char c = cArr[i_outputRawMultiByteChar];
/* 694 */                   if (c < 128) {
/* 698 */                       if (this._outputTail >= i3) {
/* 699 */                           _flushBuffer();
                                }
/* 701 */                       int i4 = this._outputTail;
                                this._outputTail = i4 + 1;
                                bArr[i4] = (byte) c;
/* 702 */                       i_outputRawMultiByteChar++;
                            } else {
/* 706 */                       if (this._outputTail + 3 >= this._outputEnd) {
/* 707 */                           _flushBuffer();
                                }
/* 709 */                       int i5 = i_outputRawMultiByteChar + 1;
                                char c2 = cArr[i_outputRawMultiByteChar];
/* 710 */                       if (c2 < 2048) {
/* 711 */                           int i6 = this._outputTail;
                                    this._outputTail = i6 + 1;
                                    bArr[i6] = (byte) ((c2 >> 6) | 192);
/* 712 */                           int i7 = this._outputTail;
                                    this._outputTail = i7 + 1;
                                    bArr[i7] = (byte) ((c2 & '?') | 128);
                                    i_outputRawMultiByteChar = i5;
                                } else {
/* 714 */                           i_outputRawMultiByteChar = _outputRawMultiByteChar(c2, cArr, i5, i2);
                                }
                            }
                        } while (i_outputRawMultiByteChar < i2);
/* 717 */               return;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 729 */       public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
/* 730 */           _verifyValueWrite("write binary value");
/* 732 */           if (this._outputTail >= this._outputEnd) {
/* 733 */               _flushBuffer();
                    }
/* 735 */           byte[] bArr2 = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    bArr2[i3] = BYTE_QUOTE;
/* 736 */           _writeBinary(base64Variant, bArr, i, i + i2);
/* 738 */           if (this._outputTail >= this._outputEnd) {
/* 739 */               _flushBuffer();
                    }
/* 741 */           byte[] bArr3 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    bArr3[i4] = BYTE_QUOTE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r1v2, types: [byte[]] */
                /* JADX WARN: Type inference failed for: r1v4, types: [int] */
                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
/* 748 */       public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
/* 749 */           _verifyValueWrite("write binary value");
/* 751 */           if (this._outputTail >= this._outputEnd) {
/* 752 */               _flushBuffer();
                    }
/* 754 */           byte[] bArr = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr[i2] = BYTE_QUOTE;
/* 755 */           byte[] bArrAllocBase64Buffer = this._ioContext.allocBase64Buffer();
                    try {
/* 758 */               if (i < 0) {
/* 759 */                   i = _writeBinary(base64Variant, inputStream, bArrAllocBase64Buffer);
                        } else {
/* 761 */                   int i_writeBinary = _writeBinary(base64Variant, inputStream, (byte[]) bArrAllocBase64Buffer, i);
/* 762 */                   if (i_writeBinary > 0) {
/* 763 */                       _reportError("Too few bytes available: missing " + i_writeBinary + " bytes (out of " + i + ")");
                            }
                        }
/* 768 */               this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
/* 771 */               int i3 = this._outputTail;
                        bArrAllocBase64Buffer = this._outputEnd;
                        if (i3 >= bArrAllocBase64Buffer) {
/* 772 */                   _flushBuffer();
                        }
/* 774 */               byte[] bArr2 = this._outputBuffer;
                        int i4 = this._outputTail;
                        this._outputTail = i4 + 1;
                        bArr2[i4] = BYTE_QUOTE;
/* 775 */               return i;
                    } catch (Throwable th) {
                        this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
                        throw th;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 787 */       public void writeNumber(short s) throws IOException {
/* 788 */           _verifyValueWrite("write number");
/* 790 */           if (this._outputTail + 6 >= this._outputEnd) {
/* 791 */               _flushBuffer();
                    }
/* 793 */           if (this._cfgNumbersAsStrings) {
/* 794 */               _writeQuotedShort(s);
                    } else {
/* 797 */               this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
                    }
                }

/* 800 */       private final void _writeQuotedShort(short s) throws IOException {
/* 801 */           if (this._outputTail + 8 >= this._outputEnd) {
/* 802 */               _flushBuffer();
                    }
/* 804 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 805 */           this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
/* 806 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 812 */       public void writeNumber(int i) throws IOException {
/* 813 */           _verifyValueWrite("write number");
/* 815 */           if (this._outputTail + 11 >= this._outputEnd) {
/* 816 */               _flushBuffer();
                    }
/* 818 */           if (this._cfgNumbersAsStrings) {
/* 819 */               _writeQuotedInt(i);
                    } else {
/* 822 */               this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
                    }
                }

/* 826 */       private final void _writeQuotedInt(int i) throws IOException {
/* 827 */           if (this._outputTail + 13 >= this._outputEnd) {
/* 828 */               _flushBuffer();
                    }
/* 830 */           byte[] bArr = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr[i2] = BYTE_QUOTE;
/* 831 */           this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
/* 832 */           byte[] bArr2 = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    bArr2[i3] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 838 */       public void writeNumber(long j) throws IOException {
/* 839 */           _verifyValueWrite("write number");
/* 840 */           if (this._cfgNumbersAsStrings) {
/* 841 */               _writeQuotedLong(j);
/* 849 */               return;
                    }
/* 844 */           if (this._outputTail + 21 >= this._outputEnd) {
/* 846 */               _flushBuffer();
                    }
/* 848 */           this._outputTail = NumberOutput.outputLong(j, this._outputBuffer, this._outputTail);
                }

/* 852 */       private final void _writeQuotedLong(long j) throws IOException {
/* 853 */           if (this._outputTail + 23 >= this._outputEnd) {
/* 854 */               _flushBuffer();
                    }
/* 856 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 857 */           this._outputTail = NumberOutput.outputLong(j, this._outputBuffer, this._outputTail);
/* 858 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 864 */       public void writeNumber(BigInteger bigInteger) throws IOException {
/* 865 */           _verifyValueWrite("write number");
/* 866 */           if (bigInteger == null) {
/* 867 */               _writeNull();
                    } else if (this._cfgNumbersAsStrings) {
/* 869 */               _writeQuotedRaw(bigInteger);
                    } else {
/* 871 */               writeRaw(bigInteger.toString());
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 879 */       public void writeNumber(double d) throws IOException {
/* 880 */           if (this._cfgNumbersAsStrings || ((Double.isNaN(d) || Double.isInfinite(d)) && isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
/* 884 */               writeString(String.valueOf(d));
                    } else {
/* 888 */               _verifyValueWrite("write number");
/* 889 */               writeRaw(String.valueOf(d));
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 895 */       public void writeNumber(float f) throws IOException {
/* 896 */           if (this._cfgNumbersAsStrings || ((Float.isNaN(f) || Float.isInfinite(f)) && isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
/* 900 */               writeString(String.valueOf(f));
                    } else {
/* 904 */               _verifyValueWrite("write number");
/* 905 */               writeRaw(String.valueOf(f));
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 912 */       public void writeNumber(BigDecimal bigDecimal) throws IOException {
/* 913 */           _verifyValueWrite("write number");
/* 914 */           if (bigDecimal == null) {
/* 915 */               _writeNull();
/* 923 */               return;
                    }
/* 916 */           if (this._cfgNumbersAsStrings) {
/* 917 */               _writeQuotedRaw(bigDecimal);
                    } else if (isEnabled(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)) {
/* 919 */               writeRaw(bigDecimal.toPlainString());
                    } else {
/* 921 */               writeRaw(bigDecimal.toString());
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 928 */       public void writeNumber(String str) throws IOException {
/* 929 */           _verifyValueWrite("write number");
/* 930 */           if (this._cfgNumbersAsStrings) {
/* 931 */               _writeQuotedRaw(str);
                    } else {
/* 933 */               writeRaw(str);
                    }
                }

/* 938 */       private final void _writeQuotedRaw(Object obj) throws IOException {
/* 939 */           if (this._outputTail >= this._outputEnd) {
/* 940 */               _flushBuffer();
                    }
/* 942 */           byte[] bArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    bArr[i] = BYTE_QUOTE;
/* 943 */           writeRaw(obj.toString());
/* 944 */           if (this._outputTail >= this._outputEnd) {
/* 945 */               _flushBuffer();
                    }
/* 947 */           byte[] bArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    bArr2[i2] = BYTE_QUOTE;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 953 */       public void writeBoolean(boolean z) throws IOException {
/* 954 */           _verifyValueWrite("write boolean value");
/* 955 */           if (this._outputTail + 5 >= this._outputEnd) {
/* 956 */               _flushBuffer();
                    }
/* 958 */           byte[] bArr = z ? TRUE_BYTES : FALSE_BYTES;
/* 959 */           int length = bArr.length;
/* 960 */           System.arraycopy(bArr, 0, this._outputBuffer, this._outputTail, length);
/* 961 */           this._outputTail += length;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 967 */       public void writeNull() throws IOException {
/* 968 */           _verifyValueWrite("write null value");
/* 969 */           _writeNull();
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase
/* 981 */       protected final void _verifyValueWrite(String str) throws IOException {
                    byte b;
/* 982 */           int iWriteValue = this._writeContext.writeValue();
/* 983 */           if (iWriteValue == 5) {
/* 984 */               _reportError("Can not " + str + ", expecting field name");
                    }
/* 986 */           if (this._cfgPrettyPrinter == null) {
                        switch (iWriteValue) {
                            case 1:
/* 990 */                       b = BYTE_COMMA;
                                break;
                            case 2:
/* 993 */                       b = BYTE_COLON;
                                break;
                            case 3:
/* 996 */                       if (this._rootValueSeparator != null) {
/* 997 */                           byte[] bArrAsUnquotedUTF8 = this._rootValueSeparator.asUnquotedUTF8();
/* 998 */                           if (bArrAsUnquotedUTF8.length > 0) {
/* 999 */                               _writeBytes(bArrAsUnquotedUTF8);
/* 1016 */                              return;
                                    }
/* 1016 */                          return;
                                }
/* 1016 */                      return;
                            default:
/* 1016 */                      return;
                        }
/* 1007 */              if (this._outputTail >= this._outputEnd) {
/* 1008 */                  _flushBuffer();
                        }
/* 1010 */              this._outputBuffer[this._outputTail] = b;
/* 1011 */              this._outputTail++;
/* 1016 */              return;
                    }
/* 1015 */          _verifyPrettyValueWrite(str, iWriteValue);
                }

/* 1021 */      protected final void _verifyPrettyValueWrite(String str, int i) throws IOException {
                    switch (i) {
                        case 0:
/* 1034 */                  if (this._writeContext.inArray()) {
/* 1035 */                      this._cfgPrettyPrinter.beforeArrayValues(this);
                            } else if (this._writeContext.inObject()) {
/* 1037 */                      this._cfgPrettyPrinter.beforeObjectEntries(this);
                            }
                            break;
                        case 1:
/* 1024 */                  this._cfgPrettyPrinter.writeArrayValueSeparator(this);
                            break;
                        case 2:
/* 1027 */                  this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
                            break;
                        case 3:
/* 1030 */                  this._cfgPrettyPrinter.writeRootValueSeparator(this);
                            break;
                        default:
/* 1041 */                  _throwInternal();
                            break;
                    }
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Flushable
/* 1055 */      public void flush() throws IOException {
/* 1056 */          _flushBuffer();
/* 1057 */          if (this._outputStream != null && isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/* 1059 */              this._outputStream.flush();
                    }
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
/* 1067 */      public void close() throws IOException {
/* 1068 */          super.close();
/* 1074 */          if (this._outputBuffer != null && isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
                        while (true) {
/* 1077 */                  JsonWriteContext outputContext = getOutputContext();
/* 1078 */                  if (outputContext.inArray()) {
/* 1079 */                      writeEndArray();
                            } else if (!outputContext.inObject()) {
                                break;
                            } else {
/* 1081 */                      writeEndObject();
                            }
                        }
                    }
/* 1087 */          _flushBuffer();
/* 1095 */          if (this._outputStream != null) {
/* 1096 */              if (this._ioContext.isResourceManaged() || isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
/* 1097 */                  this._outputStream.close();
                        } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/* 1100 */                  this._outputStream.flush();
                        }
                    }
/* 1104 */          _releaseBuffers();
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase
/* 1109 */      protected void _releaseBuffers() {
/* 1110 */          byte[] bArr = this._outputBuffer;
/* 1111 */          if (bArr != null && this._bufferRecyclable) {
/* 1112 */              this._outputBuffer = null;
/* 1113 */              this._ioContext.releaseWriteEncodingBuffer(bArr);
                    }
/* 1115 */          char[] cArr = this._charBuffer;
/* 1116 */          if (cArr != null) {
/* 1117 */              this._charBuffer = null;
/* 1118 */              this._ioContext.releaseConcatBuffer(cArr);
                    }
                }

/* 1129 */      private final void _writeBytes(byte[] bArr) throws IOException {
/* 1130 */          int length = bArr.length;
/* 1131 */          if (this._outputTail + length > this._outputEnd) {
/* 1132 */              _flushBuffer();
/* 1134 */              if (length > 512) {
/* 1135 */                  this._outputStream.write(bArr, 0, length);
/* 1141 */                  return;
                        }
                    }
/* 1139 */          System.arraycopy(bArr, 0, this._outputBuffer, this._outputTail, length);
/* 1140 */          this._outputTail = length + this._outputTail;
                }

/* 1144 */      private final void _writeBytes(byte[] bArr, int i, int i2) throws IOException {
/* 1145 */          if (this._outputTail + i2 > this._outputEnd) {
/* 1146 */              _flushBuffer();
/* 1148 */              if (i2 > 512) {
/* 1149 */                  this._outputStream.write(bArr, i, i2);
/* 1155 */                  return;
                        }
                    }
/* 1153 */          System.arraycopy(bArr, i, this._outputBuffer, this._outputTail, i2);
/* 1154 */          this._outputTail += i2;
                }

/* 1172 */      private final void _writeStringSegments(String str) throws IOException {
/* 1173 */          int length = str.length();
/* 1175 */          char[] cArr = this._charBuffer;
                    int i = length;
                    int i2 = 0;
/* 1177 */          while (i > 0) {
/* 1178 */              int iMin = Math.min(this._outputMaxContiguous, i);
/* 1179 */              str.getChars(i2, i2 + iMin, cArr, 0);
/* 1180 */              if (this._outputTail + iMin > this._outputEnd) {
/* 1181 */                  _flushBuffer();
                        }
/* 1183 */              _writeStringSegment(cArr, 0, iMin);
/* 1184 */              i2 += iMin;
/* 1185 */              i -= iMin;
                    }
                }

/* 1198 */      private final void _writeStringSegments(char[] cArr, int i, int i2) throws IOException {
/* 1206 */          do {
/* 1199 */              int iMin = Math.min(this._outputMaxContiguous, i2);
/* 1200 */              if (this._outputTail + iMin > this._outputEnd) {
/* 1201 */                  _flushBuffer();
                        }
/* 1203 */              _writeStringSegment(cArr, i, iMin);
/* 1204 */              i += iMin;
/* 1205 */              i2 -= iMin;
/* 1206 */          } while (i2 > 0);
                }

/* 1228 */      private final void _writeStringSegment(char[] cArr, int i, int i2) throws IOException {
/* 1229 */          int i3 = i2 + i;
/* 1231 */          int i4 = this._outputTail;
/* 1232 */          byte[] bArr = this._outputBuffer;
/* 1233 */          int[] iArr = this._outputEscapes;
/* 1235 */          while (i < i3) {
/* 1236 */              char c = cArr[i];
/* 1238 */              if (c > 127 || iArr[c] != 0) {
                            break;
                        }
                        bArr[i4] = (byte) c;
/* 1242 */              i++;
/* 1241 */              i4++;
                    }
/* 1244 */          this._outputTail = i4;
/* 1245 */          if (i < i3) {
/* 1247 */              if (this._characterEscapes != null) {
/* 1248 */                  _writeCustomStringSegment2(cArr, i, i3);
                        } else if (this._maximumNonEscapedChar == 0) {
/* 1251 */                  _writeStringSegment2(cArr, i, i3);
                        } else {
/* 1253 */                  _writeStringSegmentASCII2(cArr, i, i3);
                        }
                    }
                }

/* 1266 */      private final void _writeStringSegment2(char[] cArr, int i, int i2) throws IOException {
/* 1267 */          if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
/* 1268 */              _flushBuffer();
                    }
/* 1271 */          int i_writeGenericEscape = this._outputTail;
/* 1273 */          byte[] bArr = this._outputBuffer;
/* 1274 */          int[] iArr = this._outputEscapes;
/* 1276 */          while (i < i2) {
/* 1277 */              i++;
                        char c = cArr[i];
/* 1278 */              if (c <= 127) {
/* 1279 */                  if (iArr[c] == 0) {
                                bArr[i_writeGenericEscape] = (byte) c;
/* 1280 */                      i_writeGenericEscape++;
                            } else {
/* 1283 */                      int i3 = iArr[c];
/* 1284 */                      if (i3 > 0) {
/* 1285 */                          int i4 = i_writeGenericEscape + 1;
                                    bArr[i_writeGenericEscape] = BYTE_BACKSLASH;
/* 1286 */                          i_writeGenericEscape = i4 + 1;
                                    bArr[i4] = (byte) i3;
                                } else {
/* 1289 */                          i_writeGenericEscape = _writeGenericEscape(c, i_writeGenericEscape);
                                }
                            }
                        } else if (c <= 2047) {
/* 1294 */                  int i5 = i_writeGenericEscape + 1;
                            bArr[i_writeGenericEscape] = (byte) ((c >> 6) | 192);
/* 1295 */                  i_writeGenericEscape = i5 + 1;
                            bArr[i5] = (byte) ((c & '?') | 128);
                        } else {
/* 1297 */                  i_writeGenericEscape = _outputMultiByteChar(c, i_writeGenericEscape);
                        }
                    }
/* 1300 */          this._outputTail = i_writeGenericEscape;
                }

/* 1317 */      private final void _writeStringSegmentASCII2(char[] cArr, int i, int i2) throws IOException {
/* 1318 */          if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
/* 1319 */              _flushBuffer();
                    }
/* 1322 */          int i_writeGenericEscape = this._outputTail;
/* 1324 */          byte[] bArr = this._outputBuffer;
/* 1325 */          int[] iArr = this._outputEscapes;
/* 1326 */          int i3 = this._maximumNonEscapedChar;
/* 1328 */          while (i < i2) {
/* 1329 */              i++;
                        char c = cArr[i];
/* 1330 */              if (c <= 127) {
/* 1331 */                  if (iArr[c] == 0) {
                                bArr[i_writeGenericEscape] = (byte) c;
/* 1332 */                      i_writeGenericEscape++;
                            } else {
/* 1335 */                      int i4 = iArr[c];
/* 1336 */                      if (i4 > 0) {
/* 1337 */                          int i5 = i_writeGenericEscape + 1;
                                    bArr[i_writeGenericEscape] = BYTE_BACKSLASH;
/* 1338 */                          i_writeGenericEscape = i5 + 1;
                                    bArr[i5] = (byte) i4;
                                } else {
/* 1341 */                          i_writeGenericEscape = _writeGenericEscape(c, i_writeGenericEscape);
                                }
                            }
                        } else if (c > i3) {
/* 1346 */                  i_writeGenericEscape = _writeGenericEscape(c, i_writeGenericEscape);
                        } else if (c <= 2047) {
/* 1350 */                  int i6 = i_writeGenericEscape + 1;
                            bArr[i_writeGenericEscape] = (byte) ((c >> 6) | 192);
/* 1351 */                  i_writeGenericEscape = i6 + 1;
                            bArr[i6] = (byte) ((c & '?') | 128);
                        } else {
/* 1353 */                  i_writeGenericEscape = _outputMultiByteChar(c, i_writeGenericEscape);
                        }
                    }
/* 1356 */          this._outputTail = i_writeGenericEscape;
                }

/* 1373 */      private final void _writeCustomStringSegment2(char[] cArr, int i, int i2) throws IOException {
/* 1374 */          if (this._outputTail + ((i2 - i) * 6) > this._outputEnd) {
/* 1375 */              _flushBuffer();
                    }
/* 1377 */          int i_writeCustomEscape = this._outputTail;
/* 1379 */          byte[] bArr = this._outputBuffer;
/* 1380 */          int[] iArr = this._outputEscapes;
/* 1382 */          int i3 = this._maximumNonEscapedChar <= 0 ? SupportMenu.USER_MASK : this._maximumNonEscapedChar;
/* 1383 */          CharacterEscapes characterEscapes = this._characterEscapes;
/* 1385 */          while (i < i2) {
/* 1386 */              i++;
                        char c = cArr[i];
/* 1387 */              if (c <= 127) {
/* 1388 */                  if (iArr[c] == 0) {
                                bArr[i_writeCustomEscape] = (byte) c;
/* 1389 */                      i_writeCustomEscape++;
                            } else {
/* 1392 */                      int i4 = iArr[c];
/* 1393 */                      if (i4 > 0) {
/* 1394 */                          int i5 = i_writeCustomEscape + 1;
                                    bArr[i_writeCustomEscape] = BYTE_BACKSLASH;
/* 1395 */                          i_writeCustomEscape = i5 + 1;
                                    bArr[i5] = (byte) i4;
                                } else if (i4 == -2) {
/* 1397 */                          SerializableString escapeSequence = characterEscapes.getEscapeSequence(c);
/* 1398 */                          if (escapeSequence == null) {
/* 1399 */                              _reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(c) + ", although was supposed to have one");
                                    }
/* 1402 */                          i_writeCustomEscape = _writeCustomEscape(bArr, i_writeCustomEscape, escapeSequence, i2 - i);
                                } else {
/* 1405 */                          i_writeCustomEscape = _writeGenericEscape(c, i_writeCustomEscape);
                                }
                            }
                        } else if (c > i3) {
/* 1410 */                  i_writeCustomEscape = _writeGenericEscape(c, i_writeCustomEscape);
                        } else {
/* 1413 */                  SerializableString escapeSequence2 = characterEscapes.getEscapeSequence(c);
/* 1414 */                  if (escapeSequence2 != null) {
/* 1415 */                      i_writeCustomEscape = _writeCustomEscape(bArr, i_writeCustomEscape, escapeSequence2, i2 - i);
                            } else if (c <= 2047) {
/* 1419 */                      int i6 = i_writeCustomEscape + 1;
                                bArr[i_writeCustomEscape] = (byte) ((c >> 6) | 192);
/* 1420 */                      i_writeCustomEscape = i6 + 1;
                                bArr[i6] = (byte) ((c & '?') | 128);
                            } else {
/* 1422 */                      i_writeCustomEscape = _outputMultiByteChar(c, i_writeCustomEscape);
                            }
                        }
                    }
/* 1425 */          this._outputTail = i_writeCustomEscape;
                }

/* 1430 */      private final int _writeCustomEscape(byte[] bArr, int i, SerializableString serializableString, int i2) throws IOException {
/* 1431 */          byte[] bArrAsUnquotedUTF8 = serializableString.asUnquotedUTF8();
/* 1432 */          int length = bArrAsUnquotedUTF8.length;
/* 1433 */          if (length > 6) {
/* 1434 */              return _handleLongCustomEscape(bArr, i, this._outputEnd, bArrAsUnquotedUTF8, i2);
                    }
/* 1437 */          System.arraycopy(bArrAsUnquotedUTF8, 0, bArr, i, length);
/* 1438 */          return length + i;
                }

/* 1444 */      private final int _handleLongCustomEscape(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IOException {
                    int i4;
/* 1445 */          int length = bArr2.length;
/* 1446 */          if (i + length > i2) {
/* 1447 */              this._outputTail = i;
/* 1448 */              _flushBuffer();
/* 1449 */              int i5 = this._outputTail;
/* 1450 */              if (length > bArr.length) {
/* 1451 */                  this._outputStream.write(bArr2, 0, length);
/* 1462 */                  return i5;
                        }
/* 1454 */              System.arraycopy(bArr2, 0, bArr, i5, length);
/* 1455 */              i4 = i5 + length;
                    } else {
                        i4 = i;
                    }
/* 1458 */          if ((i3 * 6) + i4 > i2) {
/* 1459 */              _flushBuffer();
/* 1460 */              return this._outputTail;
                    }
/* 1462 */          return i4;
                }

/* 1479 */      private final void _writeUTF8Segments(byte[] bArr, int i, int i2) throws IOException {
/* 1484 */          do {
/* 1480 */              int iMin = Math.min(this._outputMaxContiguous, i2);
/* 1481 */              _writeUTF8Segment(bArr, i, iMin);
/* 1482 */              i += iMin;
/* 1483 */              i2 -= iMin;
/* 1484 */          } while (i2 > 0);
                }

/* 1490 */      private final void _writeUTF8Segment(byte[] bArr, int i, int i2) throws IOException {
/* 1491 */          int[] iArr = this._outputEscapes;
/* 1493 */          int i3 = i + i2;
                    int i4 = i;
                    while (i4 < i3) {
/* 1495 */              int i5 = i4 + 1;
                        byte b = bArr[i4];
/* 1496 */              if (b >= 0 && iArr[b] != 0) {
/* 1497 */                  _writeUTF8Segment2(bArr, i, i2);
/* 1508 */                  return;
                        }
                        i4 = i5;
                    }
/* 1503 */          if (this._outputTail + i2 > this._outputEnd) {
/* 1504 */              _flushBuffer();
                    }
/* 1506 */          System.arraycopy(bArr, i, this._outputBuffer, this._outputTail, i2);
/* 1507 */          this._outputTail += i2;
                }

/* 1512 */      private final void _writeUTF8Segment2(byte[] bArr, int i, int i2) throws IOException {
/* 1513 */          int i_writeGenericEscape = this._outputTail;
/* 1516 */          if ((i2 * 6) + i_writeGenericEscape > this._outputEnd) {
/* 1517 */              _flushBuffer();
/* 1518 */              i_writeGenericEscape = this._outputTail;
                    }
/* 1521 */          byte[] bArr2 = this._outputBuffer;
/* 1522 */          int[] iArr = this._outputEscapes;
/* 1523 */          int i3 = i2 + i;
/* 1525 */          while (i < i3) {
/* 1526 */              i++;
                        byte b = bArr[i];
/* 1528 */              if (b < 0 || iArr[b] == 0) {
                            bArr2[i_writeGenericEscape] = b;
/* 1529 */                  i_writeGenericEscape++;
                        } else {
/* 1532 */                  int i4 = iArr[b];
/* 1533 */                  if (i4 > 0) {
/* 1534 */                      int i5 = i_writeGenericEscape + 1;
                                bArr2[i_writeGenericEscape] = BYTE_BACKSLASH;
/* 1535 */                      i_writeGenericEscape = i5 + 1;
                                bArr2[i5] = (byte) i4;
                            } else {
/* 1538 */                      i_writeGenericEscape = _writeGenericEscape(b, i_writeGenericEscape);
                            }
                        }
                    }
/* 1541 */          this._outputTail = i_writeGenericEscape;
                }

/* 1554 */      protected final void _writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
/* 1555 */          int i3 = i2 - 3;
/* 1557 */          int i4 = this._outputEnd - 6;
/* 1558 */          int maxLineLength = base64Variant.getMaxLineLength() >> 2;
/* 1561 */          while (i <= i3) {
/* 1562 */              if (this._outputTail > i4) {
/* 1563 */                  _flushBuffer();
                        }
/* 1566 */              int i5 = i + 1;
                        int i6 = bArr[i] << 8;
/* 1567 */              int i7 = i5 + 1;
                        i = i7 + 1;
/* 1569 */              this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i5] & Constants.UNKNOWN) | i6) << 8) | (bArr[i7] & Constants.UNKNOWN), this._outputBuffer, this._outputTail);
/* 1570 */              maxLineLength--;
                        if (maxLineLength <= 0) {
/* 1572 */                  byte[] bArr2 = this._outputBuffer;
                            int i8 = this._outputTail;
                            this._outputTail = i8 + 1;
                            bArr2[i8] = BYTE_BACKSLASH;
/* 1573 */                  byte[] bArr3 = this._outputBuffer;
                            int i9 = this._outputTail;
                            this._outputTail = i9 + 1;
                            bArr3[i9] = 110;
/* 1574 */                  maxLineLength = base64Variant.getMaxLineLength() >> 2;
                        }
                    }
/* 1579 */          int i10 = i2 - i;
/* 1580 */          if (i10 > 0) {
/* 1581 */              if (this._outputTail > i4) {
/* 1582 */                  _flushBuffer();
                        }
/* 1584 */              int i11 = i + 1;
                        int i12 = bArr[i] << 16;
/* 1585 */              if (i10 == 2) {
/* 1586 */                  int i13 = i11 + 1;
                            i12 |= (bArr[i11] & Constants.UNKNOWN) << 8;
                        }
/* 1588 */              this._outputTail = base64Variant.encodeBase64Partial(i12, i10, this._outputBuffer, this._outputTail);
                    }
                }

/* 1596 */      protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr, int i) throws IOException {
                    int i_readMore;
                    int i2;
/* 1597 */          int i3 = 0;
/* 1598 */          int i_readMore2 = 0;
/* 1599 */          int i4 = -3;
/* 1602 */          int i5 = this._outputEnd - 6;
/* 1603 */          int maxLineLength = base64Variant.getMaxLineLength() >> 2;
                    int i6 = i;
/* 1605 */          while (i6 > 2) {
/* 1606 */              if (i3 > i4) {
/* 1607 */                  i_readMore2 = _readMore(inputStream, bArr, i3, i_readMore2, i6);
/* 1608 */                  i3 = 0;
/* 1609 */                  if (i_readMore2 < 3) {
                                break;
                            }
/* 1612 */                  i4 = i_readMore2 - 3;
                        }
/* 1614 */              if (this._outputTail > i5) {
/* 1615 */                  _flushBuffer();
                        }
/* 1617 */              int i7 = i3 + 1;
                        int i8 = bArr[i3] << 8;
/* 1618 */              int i9 = i7 + 1;
                        i3 = i9 + 1;
/* 1620 */              i6 -= 3;
/* 1621 */              this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i7] & Constants.UNKNOWN) | i8) << 8) | (bArr[i9] & Constants.UNKNOWN), this._outputBuffer, this._outputTail);
/* 1622 */              int maxLineLength2 = maxLineLength - 1;
                        if (maxLineLength2 <= 0) {
/* 1623 */                  byte[] bArr2 = this._outputBuffer;
                            int i10 = this._outputTail;
                            this._outputTail = i10 + 1;
                            bArr2[i10] = BYTE_BACKSLASH;
/* 1624 */                  byte[] bArr3 = this._outputBuffer;
                            int i11 = this._outputTail;
                            this._outputTail = i11 + 1;
                            bArr3[i11] = 110;
/* 1625 */                  maxLineLength2 = base64Variant.getMaxLineLength() >> 2;
                        }
                        maxLineLength = maxLineLength2;
                    }
/* 1630 */          if (i6 > 0 && (i_readMore = _readMore(inputStream, bArr, i3, i_readMore2, i6)) > 0) {
/* 1634 */              if (this._outputTail > i5) {
/* 1635 */                  _flushBuffer();
                        }
/* 1632 */              int i12 = bArr[0] << 16;
/* 1639 */              if (1 < i_readMore) {
/* 1640 */                  i12 |= (bArr[1] & Constants.UNKNOWN) << 8;
/* 1641 */                  i2 = 2;
                        } else {
/* 1643 */                  i2 = 1;
                        }
/* 1645 */              this._outputTail = base64Variant.encodeBase64Partial(i12, i2, this._outputBuffer, this._outputTail);
/* 1646 */              return i6 - i2;
                    }
/* 1649 */          return i6;
                }

/* 1658 */      protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr) throws IOException {
                    int i;
                    int i2;
/* 1659 */          int i3 = -3;
/* 1663 */          int i4 = this._outputEnd - 6;
/* 1664 */          int maxLineLength = base64Variant.getMaxLineLength() >> 2;
                    int i5 = 0;
                    int i_readMore = 0;
                    int i6 = 0;
                    while (true) {
/* 1668 */              if (i6 > i3) {
/* 1669 */                  i_readMore = _readMore(inputStream, bArr, i6, i_readMore, bArr.length);
/* 1671 */                  if (i_readMore < 3) {
                                break;
                            }
/* 1674 */                  i3 = i_readMore - 3;
                            i6 = 0;
                        }
/* 1676 */              if (this._outputTail > i4) {
/* 1677 */                  _flushBuffer();
                        }
/* 1680 */              int i7 = i6 + 1;
                        int i8 = bArr[i6] << 8;
/* 1681 */              int i9 = i7 + 1;
                        i6 = i9 + 1;
/* 1683 */              i5 += 3;
/* 1684 */              this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i7] & Constants.UNKNOWN) | i8) << 8) | (bArr[i9] & Constants.UNKNOWN), this._outputBuffer, this._outputTail);
/* 1685 */              int maxLineLength2 = maxLineLength - 1;
                        if (maxLineLength2 <= 0) {
/* 1686 */                  byte[] bArr2 = this._outputBuffer;
                            int i10 = this._outputTail;
                            this._outputTail = i10 + 1;
                            bArr2[i10] = BYTE_BACKSLASH;
/* 1687 */                  byte[] bArr3 = this._outputBuffer;
                            int i11 = this._outputTail;
                            this._outputTail = i11 + 1;
                            bArr3[i11] = 110;
/* 1688 */                  maxLineLength2 = base64Variant.getMaxLineLength() >> 2;
                        }
                        maxLineLength = maxLineLength2;
                    }
/* 1693 */          if (0 >= i_readMore) {
                        return i5;
                    }
/* 1694 */          if (this._outputTail > i4) {
/* 1695 */              _flushBuffer();
                    }
/* 1697 */          int i12 = bArr[0] << 16;
/* 1699 */          if (1 < i_readMore) {
/* 1700 */              i = ((bArr[1] & Constants.UNKNOWN) << 8) | i12;
/* 1701 */              i2 = 2;
                    } else {
                        i = i12;
                        i2 = 1;
                    }
/* 1703 */          int i13 = i5 + i2;
/* 1704 */          this._outputTail = base64Variant.encodeBase64Partial(i, i2, this._outputBuffer, this._outputTail);
                    return i13;
                }

/* 1713 */      private final int _readMore(InputStream inputStream, byte[] bArr, int i, int i2, int i3) throws IOException {
                    int i4;
/* 1714 */          int i5 = 0;
/* 1715 */          while (i < i2) {
                        bArr[i5] = bArr[i];
/* 1716 */              i5++;
                        i++;
                    }
/* 1720 */          int iMin = Math.min(i3, bArr.length);
/* 1732 */          do {
/* 1723 */              int i6 = iMin - i5;
/* 1724 */              if (i6 == 0 || (i4 = inputStream.read(bArr, i5, i6)) < 0) {
                            break;
                        }
/* 1731 */              i5 += i4;
/* 1732 */          } while (i5 < 3);
/* 1733 */          return i5;
                }

/* 1750 */      private final int _outputRawMultiByteChar(int i, char[] cArr, int i2, int i3) throws IOException {
/* 1751 */          if (i >= SURR1_FIRST && i <= SURR2_LAST) {
/* 1754 */              if (i2 >= i3 || cArr == null) {
/* 1755 */                  _reportError("Split surrogate on writeRaw() input (last character)");
                        }
/* 1757 */              _outputSurrogates(i, cArr[i2]);
/* 1758 */              return i2 + 1;
                    }
/* 1761 */          byte[] bArr = this._outputBuffer;
/* 1762 */          int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    bArr[i4] = (byte) ((i >> 12) | 224);
/* 1763 */          int i5 = this._outputTail;
                    this._outputTail = i5 + 1;
                    bArr[i5] = (byte) (((i >> 6) & 63) | 128);
/* 1764 */          int i6 = this._outputTail;
                    this._outputTail = i6 + 1;
                    bArr[i6] = (byte) ((i & 63) | 128);
/* 1765 */          return i2;
                }

/* 1770 */      protected final void _outputSurrogates(int i, int i2) throws IOException {
/* 1771 */          int i_decodeSurrogate = _decodeSurrogate(i, i2);
/* 1772 */          if (this._outputTail + 4 > this._outputEnd) {
/* 1773 */              _flushBuffer();
                    }
/* 1775 */          byte[] bArr = this._outputBuffer;
/* 1776 */          int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    bArr[i3] = (byte) ((i_decodeSurrogate >> 18) | 240);
/* 1777 */          int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    bArr[i4] = (byte) (((i_decodeSurrogate >> 12) & 63) | 128);
/* 1778 */          int i5 = this._outputTail;
                    this._outputTail = i5 + 1;
                    bArr[i5] = (byte) (((i_decodeSurrogate >> 6) & 63) | 128);
/* 1779 */          int i6 = this._outputTail;
                    this._outputTail = i6 + 1;
                    bArr[i6] = (byte) ((i_decodeSurrogate & 63) | 128);
                }

/* 1793 */      private final int _outputMultiByteChar(int i, int i2) throws IOException {
/* 1794 */          byte[] bArr = this._outputBuffer;
/* 1795 */          if (i >= SURR1_FIRST && i <= SURR2_LAST) {
/* 1796 */              int i3 = i2 + 1;
                        bArr[i2] = BYTE_BACKSLASH;
/* 1797 */              int i4 = i3 + 1;
                        bArr[i3] = BYTE_u;
/* 1799 */              int i5 = i4 + 1;
                        bArr[i4] = HEX_CHARS[(i >> 12) & 15];
/* 1800 */              int i6 = i5 + 1;
                        bArr[i5] = HEX_CHARS[(i >> 8) & 15];
/* 1801 */              int i7 = i6 + 1;
                        bArr[i6] = HEX_CHARS[(i >> 4) & 15];
/* 1802 */              int i8 = i7 + 1;
                        bArr[i7] = HEX_CHARS[i & 15];
/* 1808 */              return i8;
                    }
/* 1804 */          int i9 = i2 + 1;
                    bArr[i2] = (byte) ((i >> 12) | 224);
/* 1805 */          int i10 = i9 + 1;
                    bArr[i9] = (byte) (((i >> 6) & 63) | 128);
/* 1806 */          int i11 = i10 + 1;
                    bArr[i10] = (byte) ((i & 63) | 128);
/* 1808 */          return i11;
                }

/* 1813 */      protected final int _decodeSurrogate(int i, int i2) throws IOException {
/* 1814 */          if (i2 < SURR2_FIRST || i2 > SURR2_LAST) {
/* 1816 */              _reportError("Incomplete surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(i2));
                    }
/* 1818 */          return 65536 + ((i - SURR1_FIRST) << 10) + (i2 - SURR2_FIRST);
                }

/* 1823 */      private final void _writeNull() throws IOException {
/* 1824 */          if (this._outputTail + 4 >= this._outputEnd) {
/* 1825 */              _flushBuffer();
                    }
/* 1827 */          System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
/* 1828 */          this._outputTail += 4;
                }

/* 1838 */      private int _writeGenericEscape(int i, int i2) throws IOException {
                    int i3;
/* 1839 */          byte[] bArr = this._outputBuffer;
/* 1840 */          int i4 = i2 + 1;
                    bArr[i2] = BYTE_BACKSLASH;
/* 1841 */          int i5 = i4 + 1;
                    bArr[i4] = BYTE_u;
/* 1842 */          if (i > 255) {
/* 1843 */              int i6 = (i >> 8) & 255;
/* 1844 */              int i7 = i5 + 1;
                        bArr[i5] = HEX_CHARS[i6 >> 4];
/* 1845 */              i3 = i7 + 1;
                        bArr[i7] = HEX_CHARS[i6 & 15];
/* 1846 */              i &= 255;
                    } else {
/* 1848 */              int i8 = i5 + 1;
                        bArr[i5] = BYTE_0;
/* 1849 */              i3 = i8 + 1;
                        bArr[i8] = BYTE_0;
                    }
/* 1852 */          int i9 = i3 + 1;
                    bArr[i3] = HEX_CHARS[i >> 4];
/* 1853 */          int i10 = i9 + 1;
                    bArr[i9] = HEX_CHARS[i & 15];
/* 1854 */          return i10;
                }

/* 1858 */      protected final void _flushBuffer() throws IOException {
/* 1859 */          int i = this._outputTail;
/* 1860 */          if (i > 0) {
/* 1861 */              this._outputTail = 0;
/* 1862 */              this._outputStream.write(this._outputBuffer, 0, i);
                    }
                }
            }
