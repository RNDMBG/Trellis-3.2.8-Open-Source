            package com.fasterxml.jackson.core.json;

            import android.support.v4.internal.view.SupportMenu;
            import co.vine.android.util.CommonUtil;
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
            import java.io.Writer;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 72 */    public final class WriterBasedJsonGenerator extends JsonGeneratorImpl {
                protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
                protected static final int SHORT_WRITE = 32;
                protected SerializableString _currentEscape;
                protected char[] _entityBuffer;
                protected char[] _outputBuffer;
                protected int _outputEnd;
                protected int _outputHead;
                protected int _outputTail;
                protected final Writer _writer;

/* 73 */        public WriterBasedJsonGenerator(IOContext iOContext, int i, ObjectCodec objectCodec, Writer writer) {
/* 74 */            super(iOContext, i, objectCodec);
/* 38 */            this._outputHead = 0;
/* 44 */            this._outputTail = 0;
/* 75 */            this._writer = writer;
/* 76 */            this._outputBuffer = iOContext.allocConcatBuffer();
/* 77 */            this._outputEnd = this._outputBuffer.length;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 87 */        public Object getOutputTarget() {
/* 88 */            return this._writer;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 99 */        public void writeFieldName(String str) throws IOException {
/* 100 */           int iWriteFieldName = this._writeContext.writeFieldName(str);
/* 101 */           if (iWriteFieldName == 4) {
/* 102 */               _reportError("Can not write a field name, expecting a value");
                    }
/* 104 */           _writeFieldName(str, iWriteFieldName == 1);
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
/* 111 */       public void writeFieldName(SerializableString serializableString) throws IOException {
/* 112 */           int iWriteFieldName = this._writeContext.writeFieldName(serializableString.getValue());
/* 113 */           if (iWriteFieldName == 4) {
/* 114 */               _reportError("Can not write a field name, expecting a value");
                    }
/* 116 */           _writeFieldName(serializableString, iWriteFieldName == 1);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 127 */       public void writeStartArray() throws IOException {
/* 128 */           _verifyValueWrite("start an array");
/* 129 */           this._writeContext = this._writeContext.createChildArrayContext();
/* 130 */           if (this._cfgPrettyPrinter != null) {
/* 131 */               this._cfgPrettyPrinter.writeStartArray(this);
/* 138 */               return;
                    }
/* 133 */           if (this._outputTail >= this._outputEnd) {
/* 134 */               _flushBuffer();
                    }
/* 136 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = '[';
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 142 */       public void writeEndArray() throws IOException {
/* 143 */           if (!this._writeContext.inArray()) {
/* 144 */               _reportError("Current context not an ARRAY but " + this._writeContext.getTypeDesc());
                    }
/* 146 */           if (this._cfgPrettyPrinter != null) {
/* 147 */               this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
                    } else {
/* 149 */               if (this._outputTail >= this._outputEnd) {
/* 150 */                   _flushBuffer();
                        }
/* 152 */               char[] cArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        cArr[i] = ']';
                    }
/* 154 */           this._writeContext = this._writeContext.getParent();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 159 */       public void writeStartObject() throws IOException {
/* 160 */           _verifyValueWrite("start an object");
/* 161 */           this._writeContext = this._writeContext.createChildObjectContext();
/* 162 */           if (this._cfgPrettyPrinter != null) {
/* 163 */               this._cfgPrettyPrinter.writeStartObject(this);
/* 170 */               return;
                    }
/* 165 */           if (this._outputTail >= this._outputEnd) {
/* 166 */               _flushBuffer();
                    }
/* 168 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = '{';
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 174 */       public void writeEndObject() throws IOException {
/* 175 */           if (!this._writeContext.inObject()) {
/* 176 */               _reportError("Current context not an object but " + this._writeContext.getTypeDesc());
                    }
/* 178 */           if (this._cfgPrettyPrinter != null) {
/* 179 */               this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
                    } else {
/* 181 */               if (this._outputTail >= this._outputEnd) {
/* 182 */                   _flushBuffer();
                        }
/* 184 */               char[] cArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        cArr[i] = '}';
                    }
/* 186 */           this._writeContext = this._writeContext.getParent();
                }

/* 191 */       protected void _writeFieldName(String str, boolean z) throws IOException {
/* 192 */           if (this._cfgPrettyPrinter != null) {
/* 193 */               _writePPFieldName(str, z);
/* 221 */               return;
                    }
/* 197 */           if (this._outputTail + 1 >= this._outputEnd) {
/* 198 */               _flushBuffer();
                    }
/* 200 */           if (z) {
/* 201 */               char[] cArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        cArr[i] = ',';
                    }
/* 207 */           if (!isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/* 208 */               _writeString(str);
/* 221 */               return;
                    }
/* 213 */           char[] cArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr2[i2] = CommonUtil.MARKER_QUOTES;
/* 215 */           _writeString(str);
/* 217 */           if (this._outputTail >= this._outputEnd) {
/* 218 */               _flushBuffer();
                    }
/* 220 */           char[] cArr3 = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    cArr3[i3] = CommonUtil.MARKER_QUOTES;
                }

/* 225 */       public void _writeFieldName(SerializableString serializableString, boolean z) throws IOException {
/* 226 */           if (this._cfgPrettyPrinter != null) {
/* 227 */               _writePPFieldName(serializableString, z);
/* 261 */               return;
                    }
/* 231 */           if (this._outputTail + 1 >= this._outputEnd) {
/* 232 */               _flushBuffer();
                    }
/* 234 */           if (z) {
/* 235 */               char[] cArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        cArr[i] = ',';
                    }
/* 240 */           char[] cArrAsQuotedChars = serializableString.asQuotedChars();
/* 241 */           if (!isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/* 242 */               writeRaw(cArrAsQuotedChars, 0, cArrAsQuotedChars.length);
/* 261 */               return;
                    }
/* 246 */           char[] cArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr2[i2] = CommonUtil.MARKER_QUOTES;
/* 248 */           int length = cArrAsQuotedChars.length;
/* 249 */           if (this._outputTail + length + 1 >= this._outputEnd) {
/* 250 */               writeRaw(cArrAsQuotedChars, 0, length);
/* 252 */               if (this._outputTail >= this._outputEnd) {
/* 253 */                   _flushBuffer();
                        }
/* 255 */               char[] cArr3 = this._outputBuffer;
                        int i3 = this._outputTail;
                        this._outputTail = i3 + 1;
                        cArr3[i3] = CommonUtil.MARKER_QUOTES;
/* 261 */               return;
                    }
/* 257 */           System.arraycopy(cArrAsQuotedChars, 0, this._outputBuffer, this._outputTail, length);
/* 258 */           this._outputTail += length;
/* 259 */           char[] cArr4 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    cArr4[i4] = CommonUtil.MARKER_QUOTES;
                }

/* 269 */       protected void _writePPFieldName(String str, boolean z) throws IOException {
/* 270 */           if (z) {
/* 271 */               this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
                    } else {
/* 273 */               this._cfgPrettyPrinter.beforeObjectEntries(this);
                    }
/* 276 */           if (isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/* 277 */               if (this._outputTail >= this._outputEnd) {
/* 278 */                   _flushBuffer();
                        }
/* 280 */               char[] cArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        cArr[i] = CommonUtil.MARKER_QUOTES;
/* 281 */               _writeString(str);
/* 282 */               if (this._outputTail >= this._outputEnd) {
/* 283 */                   _flushBuffer();
                        }
/* 285 */               char[] cArr2 = this._outputBuffer;
                        int i2 = this._outputTail;
                        this._outputTail = i2 + 1;
                        cArr2[i2] = CommonUtil.MARKER_QUOTES;
/* 289 */               return;
                    }
/* 287 */           _writeString(str);
                }

/* 293 */       protected void _writePPFieldName(SerializableString serializableString, boolean z) throws IOException {
/* 294 */           if (z) {
/* 295 */               this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
                    } else {
/* 297 */               this._cfgPrettyPrinter.beforeObjectEntries(this);
                    }
/* 300 */           char[] cArrAsQuotedChars = serializableString.asQuotedChars();
/* 301 */           if (isEnabled(JsonGenerator.Feature.QUOTE_FIELD_NAMES)) {
/* 302 */               if (this._outputTail >= this._outputEnd) {
/* 303 */                   _flushBuffer();
                        }
/* 305 */               char[] cArr = this._outputBuffer;
                        int i = this._outputTail;
                        this._outputTail = i + 1;
                        cArr[i] = CommonUtil.MARKER_QUOTES;
/* 306 */               writeRaw(cArrAsQuotedChars, 0, cArrAsQuotedChars.length);
/* 307 */               if (this._outputTail >= this._outputEnd) {
/* 308 */                   _flushBuffer();
                        }
/* 310 */               char[] cArr2 = this._outputBuffer;
                        int i2 = this._outputTail;
                        this._outputTail = i2 + 1;
                        cArr2[i2] = CommonUtil.MARKER_QUOTES;
/* 314 */               return;
                    }
/* 312 */           writeRaw(cArrAsQuotedChars, 0, cArrAsQuotedChars.length);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 325 */       public void writeString(String str) throws IOException {
/* 326 */           _verifyValueWrite("write text value");
/* 327 */           if (str == null) {
/* 328 */               _writeNull();
/* 341 */               return;
                    }
/* 331 */           if (this._outputTail >= this._outputEnd) {
/* 332 */               _flushBuffer();
                    }
/* 334 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = CommonUtil.MARKER_QUOTES;
/* 335 */           _writeString(str);
/* 337 */           if (this._outputTail >= this._outputEnd) {
/* 338 */               _flushBuffer();
                    }
/* 340 */           char[] cArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr2[i2] = CommonUtil.MARKER_QUOTES;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 346 */       public void writeString(char[] cArr, int i, int i2) throws IOException {
/* 347 */           _verifyValueWrite("write text value");
/* 348 */           if (this._outputTail >= this._outputEnd) {
/* 349 */               _flushBuffer();
                    }
/* 351 */           char[] cArr2 = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    cArr2[i3] = CommonUtil.MARKER_QUOTES;
/* 352 */           _writeString(cArr, i, i2);
/* 354 */           if (this._outputTail >= this._outputEnd) {
/* 355 */               _flushBuffer();
                    }
/* 357 */           char[] cArr3 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    cArr3[i4] = CommonUtil.MARKER_QUOTES;
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator
/* 363 */       public void writeString(SerializableString serializableString) throws IOException {
/* 364 */           _verifyValueWrite("write text value");
/* 365 */           if (this._outputTail >= this._outputEnd) {
/* 366 */               _flushBuffer();
                    }
/* 368 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = CommonUtil.MARKER_QUOTES;
/* 370 */           char[] cArrAsQuotedChars = serializableString.asQuotedChars();
/* 371 */           int length = cArrAsQuotedChars.length;
/* 373 */           if (length < 32) {
/* 375 */               if (length > this._outputEnd - this._outputTail) {
/* 376 */                   _flushBuffer();
                        }
/* 378 */               System.arraycopy(cArrAsQuotedChars, 0, this._outputBuffer, this._outputTail, length);
/* 379 */               this._outputTail += length;
                    } else {
/* 382 */               _flushBuffer();
/* 383 */               this._writer.write(cArrAsQuotedChars, 0, length);
                    }
/* 385 */           if (this._outputTail >= this._outputEnd) {
/* 386 */               _flushBuffer();
                    }
/* 388 */           char[] cArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr2[i2] = CommonUtil.MARKER_QUOTES;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 395 */       public void writeRawUTF8String(byte[] bArr, int i, int i2) throws IOException {
/* 396 */           _reportUnsupportedOperation();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 403 */       public void writeUTF8String(byte[] bArr, int i, int i2) throws IOException {
/* 404 */           _reportUnsupportedOperation();
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 417 */       public void writeRaw(String str) throws IOException {
/* 418 */           int length = str.length();
/* 419 */           int i = this._outputEnd - this._outputTail;
/* 421 */           if (i == 0) {
/* 422 */               _flushBuffer();
/* 423 */               i = this._outputEnd - this._outputTail;
                    }
/* 426 */           if (i >= length) {
/* 427 */               str.getChars(0, length, this._outputBuffer, this._outputTail);
/* 428 */               this._outputTail += length;
                    } else {
/* 430 */               writeRawLong(str);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 438 */       public void writeRaw(String str, int i, int i2) throws IOException {
/* 439 */           int i3 = this._outputEnd - this._outputTail;
/* 441 */           if (i3 < i2) {
/* 442 */               _flushBuffer();
/* 443 */               i3 = this._outputEnd - this._outputTail;
                    }
/* 446 */           if (i3 >= i2) {
/* 447 */               str.getChars(i, i + i2, this._outputBuffer, this._outputTail);
/* 448 */               this._outputTail += i2;
                    } else {
/* 450 */               writeRawLong(str.substring(i, i + i2));
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 456 */       public void writeRaw(SerializableString serializableString) throws IOException {
/* 457 */           writeRaw(serializableString.getValue());
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 464 */       public void writeRaw(char[] cArr, int i, int i2) throws IOException {
/* 465 */           if (i2 < 32) {
/* 467 */               if (i2 > this._outputEnd - this._outputTail) {
/* 468 */                   _flushBuffer();
                        }
/* 470 */               System.arraycopy(cArr, i, this._outputBuffer, this._outputTail, i2);
/* 471 */               this._outputTail += i2;
/* 477 */               return;
                    }
/* 475 */           _flushBuffer();
/* 476 */           this._writer.write(cArr, i, i2);
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 482 */       public void writeRaw(char c) throws IOException {
/* 483 */           if (this._outputTail >= this._outputEnd) {
/* 484 */               _flushBuffer();
                    }
/* 486 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = c;
                }

/* 491 */       private void writeRawLong(String str) throws IOException {
/* 492 */           int i = this._outputEnd - this._outputTail;
/* 494 */           str.getChars(0, i, this._outputBuffer, this._outputTail);
/* 495 */           this._outputTail += i;
/* 496 */           _flushBuffer();
/* 498 */           int length = str.length() - i;
/* 500 */           while (length > this._outputEnd) {
/* 501 */               int i2 = this._outputEnd;
/* 502 */               str.getChars(i, i + i2, this._outputBuffer, 0);
/* 503 */               this._outputHead = 0;
/* 504 */               this._outputTail = i2;
/* 505 */               _flushBuffer();
/* 506 */               i += i2;
/* 507 */               length -= i2;
                    }
/* 510 */           str.getChars(i, i + length, this._outputBuffer, 0);
/* 511 */           this._outputHead = 0;
/* 512 */           this._outputTail = length;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 524 */       public void writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
/* 525 */           _verifyValueWrite("write binary value");
/* 527 */           if (this._outputTail >= this._outputEnd) {
/* 528 */               _flushBuffer();
                    }
/* 530 */           char[] cArr = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    cArr[i3] = CommonUtil.MARKER_QUOTES;
/* 531 */           _writeBinary(base64Variant, bArr, i, i + i2);
/* 533 */           if (this._outputTail >= this._outputEnd) {
/* 534 */               _flushBuffer();
                    }
/* 536 */           char[] cArr2 = this._outputBuffer;
                    int i4 = this._outputTail;
                    this._outputTail = i4 + 1;
                    cArr2[i4] = CommonUtil.MARKER_QUOTES;
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
/* 543 */       public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int i) throws IOException {
/* 544 */           _verifyValueWrite("write binary value");
/* 546 */           if (this._outputTail >= this._outputEnd) {
/* 547 */               _flushBuffer();
                    }
/* 549 */           char[] cArr = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr[i2] = CommonUtil.MARKER_QUOTES;
/* 550 */           byte[] bArrAllocBase64Buffer = this._ioContext.allocBase64Buffer();
                    try {
/* 553 */               if (i < 0) {
/* 554 */                   i = _writeBinary(base64Variant, inputStream, bArrAllocBase64Buffer);
                        } else {
/* 556 */                   int i_writeBinary = _writeBinary(base64Variant, inputStream, (byte[]) bArrAllocBase64Buffer, i);
/* 557 */                   if (i_writeBinary > 0) {
/* 558 */                       _reportError("Too few bytes available: missing " + i_writeBinary + " bytes (out of " + i + ")");
                            }
                        }
/* 563 */               this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
/* 566 */               int i3 = this._outputTail;
                        bArrAllocBase64Buffer = this._outputEnd;
                        if (i3 >= bArrAllocBase64Buffer) {
/* 567 */                   _flushBuffer();
                        }
/* 569 */               char[] cArr2 = this._outputBuffer;
                        int i4 = this._outputTail;
                        this._outputTail = i4 + 1;
                        cArr2[i4] = CommonUtil.MARKER_QUOTES;
/* 570 */               return i;
                    } catch (Throwable th) {
                        this._ioContext.releaseBase64Buffer(bArrAllocBase64Buffer);
                        throw th;
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 582 */       public void writeNumber(short s) throws IOException {
/* 583 */           _verifyValueWrite("write number");
/* 584 */           if (this._cfgNumbersAsStrings) {
/* 585 */               _writeQuotedShort(s);
/* 593 */               return;
                    }
/* 589 */           if (this._outputTail + 6 >= this._outputEnd) {
/* 590 */               _flushBuffer();
                    }
/* 592 */           this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
                }

/* 595 */       private void _writeQuotedShort(short s) throws IOException {
/* 596 */           if (this._outputTail + 8 >= this._outputEnd) {
/* 597 */               _flushBuffer();
                    }
/* 599 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = CommonUtil.MARKER_QUOTES;
/* 600 */           this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
/* 601 */           char[] cArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr2[i2] = CommonUtil.MARKER_QUOTES;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 607 */       public void writeNumber(int i) throws IOException {
/* 608 */           _verifyValueWrite("write number");
/* 609 */           if (this._cfgNumbersAsStrings) {
/* 610 */               _writeQuotedInt(i);
/* 618 */               return;
                    }
/* 614 */           if (this._outputTail + 11 >= this._outputEnd) {
/* 615 */               _flushBuffer();
                    }
/* 617 */           this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
                }

/* 620 */       private void _writeQuotedInt(int i) throws IOException {
/* 621 */           if (this._outputTail + 13 >= this._outputEnd) {
/* 622 */               _flushBuffer();
                    }
/* 624 */           char[] cArr = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr[i2] = CommonUtil.MARKER_QUOTES;
/* 625 */           this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
/* 626 */           char[] cArr2 = this._outputBuffer;
                    int i3 = this._outputTail;
                    this._outputTail = i3 + 1;
                    cArr2[i3] = CommonUtil.MARKER_QUOTES;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 632 */       public void writeNumber(long j) throws IOException {
/* 633 */           _verifyValueWrite("write number");
/* 634 */           if (this._cfgNumbersAsStrings) {
/* 635 */               _writeQuotedLong(j);
/* 643 */               return;
                    }
/* 638 */           if (this._outputTail + 21 >= this._outputEnd) {
/* 640 */               _flushBuffer();
                    }
/* 642 */           this._outputTail = NumberOutput.outputLong(j, this._outputBuffer, this._outputTail);
                }

/* 645 */       private void _writeQuotedLong(long j) throws IOException {
/* 646 */           if (this._outputTail + 23 >= this._outputEnd) {
/* 647 */               _flushBuffer();
                    }
/* 649 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = CommonUtil.MARKER_QUOTES;
/* 650 */           this._outputTail = NumberOutput.outputLong(j, this._outputBuffer, this._outputTail);
/* 651 */           char[] cArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr2[i2] = CommonUtil.MARKER_QUOTES;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 659 */       public void writeNumber(BigInteger bigInteger) throws IOException {
/* 660 */           _verifyValueWrite("write number");
/* 661 */           if (bigInteger == null) {
/* 662 */               _writeNull();
                    } else if (this._cfgNumbersAsStrings) {
/* 664 */               _writeQuotedRaw(bigInteger);
                    } else {
/* 666 */               writeRaw(bigInteger.toString());
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 674 */       public void writeNumber(double d) throws IOException {
/* 675 */           if (this._cfgNumbersAsStrings || ((Double.isNaN(d) || Double.isInfinite(d)) && isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
/* 679 */               writeString(String.valueOf(d));
                    } else {
/* 683 */               _verifyValueWrite("write number");
/* 684 */               writeRaw(String.valueOf(d));
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 690 */       public void writeNumber(float f) throws IOException {
/* 691 */           if (this._cfgNumbersAsStrings || ((Float.isNaN(f) || Float.isInfinite(f)) && isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
/* 695 */               writeString(String.valueOf(f));
                    } else {
/* 699 */               _verifyValueWrite("write number");
/* 700 */               writeRaw(String.valueOf(f));
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 707 */       public void writeNumber(BigDecimal bigDecimal) throws IOException {
/* 708 */           _verifyValueWrite("write number");
/* 709 */           if (bigDecimal == null) {
/* 710 */               _writeNull();
/* 718 */               return;
                    }
/* 711 */           if (this._cfgNumbersAsStrings) {
/* 712 */               _writeQuotedRaw(bigDecimal);
                    } else if (isEnabled(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)) {
/* 714 */               writeRaw(bigDecimal.toPlainString());
                    } else {
/* 716 */               writeRaw(bigDecimal.toString());
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 723 */       public void writeNumber(String str) throws IOException {
/* 724 */           _verifyValueWrite("write number");
/* 725 */           if (this._cfgNumbersAsStrings) {
/* 726 */               _writeQuotedRaw(str);
                    } else {
/* 728 */               writeRaw(str);
                    }
                }

/* 733 */       private void _writeQuotedRaw(Object obj) throws IOException {
/* 734 */           if (this._outputTail >= this._outputEnd) {
/* 735 */               _flushBuffer();
                    }
/* 737 */           char[] cArr = this._outputBuffer;
                    int i = this._outputTail;
                    this._outputTail = i + 1;
                    cArr[i] = CommonUtil.MARKER_QUOTES;
/* 738 */           writeRaw(obj.toString());
/* 739 */           if (this._outputTail >= this._outputEnd) {
/* 740 */               _flushBuffer();
                    }
/* 742 */           char[] cArr2 = this._outputBuffer;
                    int i2 = this._outputTail;
                    this._outputTail = i2 + 1;
                    cArr2[i2] = CommonUtil.MARKER_QUOTES;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 748 */       public void writeBoolean(boolean z) throws IOException {
                    int i;
/* 749 */           _verifyValueWrite("write boolean value");
/* 750 */           if (this._outputTail + 5 >= this._outputEnd) {
/* 751 */               _flushBuffer();
                    }
/* 753 */           int i2 = this._outputTail;
/* 754 */           char[] cArr = this._outputBuffer;
/* 755 */           if (z) {
/* 756 */               cArr[i2] = 't';
/* 757 */               int i3 = i2 + 1;
                        cArr[i3] = 'r';
/* 758 */               int i4 = i3 + 1;
                        cArr[i4] = 'u';
/* 759 */               i = i4 + 1;
                        cArr[i] = 'e';
                    } else {
/* 761 */               cArr[i2] = 'f';
/* 762 */               int i5 = i2 + 1;
                        cArr[i5] = 'a';
/* 763 */               int i6 = i5 + 1;
                        cArr[i6] = 'l';
/* 764 */               int i7 = i6 + 1;
                        cArr[i7] = 's';
/* 765 */               i = i7 + 1;
                        cArr[i] = 'e';
                    }
/* 767 */           this._outputTail = i + 1;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 773 */       public void writeNull() throws IOException {
/* 774 */           _verifyValueWrite("write null value");
/* 775 */           _writeNull();
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase
/* 787 */       protected void _verifyValueWrite(String str) throws IOException {
                    char c;
/* 788 */           int iWriteValue = this._writeContext.writeValue();
/* 789 */           if (iWriteValue == 5) {
/* 790 */               _reportError("Can not " + str + ", expecting field name");
                    }
/* 792 */           if (this._cfgPrettyPrinter == null) {
                        switch (iWriteValue) {
                            case 1:
/* 796 */                       c = ',';
                                break;
                            case 2:
/* 799 */                       c = CommonUtil.MARKER_COLON;
                                break;
                            case 3:
/* 802 */                       if (this._rootValueSeparator != null) {
/* 803 */                           writeRaw(this._rootValueSeparator.getValue());
/* 819 */                           return;
                                }
/* 819 */                       return;
                            default:
/* 819 */                       return;
                        }
/* 810 */               if (this._outputTail >= this._outputEnd) {
/* 811 */                   _flushBuffer();
                        }
/* 813 */               this._outputBuffer[this._outputTail] = c;
/* 814 */               this._outputTail++;
/* 819 */               return;
                    }
/* 818 */           _verifyPrettyValueWrite(str, iWriteValue);
                }

/* 824 */       protected void _verifyPrettyValueWrite(String str, int i) throws IOException {
                    switch (i) {
                        case 0:
/* 837 */                   if (this._writeContext.inArray()) {
/* 838 */                       this._cfgPrettyPrinter.beforeArrayValues(this);
                            } else if (this._writeContext.inObject()) {
/* 840 */                       this._cfgPrettyPrinter.beforeObjectEntries(this);
                            }
                            break;
                        case 1:
/* 827 */                   this._cfgPrettyPrinter.writeArrayValueSeparator(this);
                            break;
                        case 2:
/* 830 */                   this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
                            break;
                        case 3:
/* 833 */                   this._cfgPrettyPrinter.writeRootValueSeparator(this);
                            break;
                        default:
/* 844 */                   _throwInternal();
                            break;
                    }
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Flushable
/* 858 */       public void flush() throws IOException {
/* 859 */           _flushBuffer();
/* 860 */           if (this._writer != null && isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/* 862 */               this._writer.flush();
                    }
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
/* 870 */       public void close() throws IOException {
/* 871 */           super.close();
/* 877 */           if (this._outputBuffer != null && isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
                        while (true) {
/* 880 */                   JsonWriteContext outputContext = getOutputContext();
/* 881 */                   if (outputContext.inArray()) {
/* 882 */                       writeEndArray();
                            } else if (!outputContext.inObject()) {
                                break;
                            } else {
/* 884 */                       writeEndObject();
                            }
                        }
                    }
/* 890 */           _flushBuffer();
/* 898 */           if (this._writer != null) {
/* 899 */               if (this._ioContext.isResourceManaged() || isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
/* 900 */                   this._writer.close();
                        } else if (isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
/* 903 */                   this._writer.flush();
                        }
                    }
/* 907 */           _releaseBuffers();
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase
/* 912 */       protected void _releaseBuffers() {
/* 913 */           char[] cArr = this._outputBuffer;
/* 914 */           if (cArr != null) {
/* 915 */               this._outputBuffer = null;
/* 916 */               this._ioContext.releaseConcatBuffer(cArr);
                    }
                }

/* 933 */       private void _writeString(String str) throws IOException {
/* 934 */           int length = str.length();
/* 935 */           if (length > this._outputEnd) {
/* 936 */               _writeLongString(str);
/* 954 */               return;
                    }
/* 942 */           if (this._outputTail + length > this._outputEnd) {
/* 943 */               _flushBuffer();
                    }
/* 945 */           str.getChars(0, length, this._outputBuffer, this._outputTail);
/* 947 */           if (this._characterEscapes != null) {
/* 948 */               _writeStringCustom(length);
                    } else if (this._maximumNonEscapedChar != 0) {
/* 950 */               _writeStringASCII(length, this._maximumNonEscapedChar);
                    } else {
/* 952 */               _writeString2(length);
                    }
                }

/* 959 */       private void _writeString2(int i) throws IOException {
                    int i2;
/* 960 */           int i3 = this._outputTail + i;
/* 961 */           int[] iArr = this._outputEscapes;
/* 962 */           int length = iArr.length;
/* 965 */           while (this._outputTail < i3) {
                        do {
/* 969 */                   char c = this._outputBuffer[this._outputTail];
/* 970 */                   if (c >= length || iArr[c] == 0) {
/* 973 */                       i2 = this._outputTail + 1;
                                this._outputTail = i2;
                            } else {
/* 982 */                       int i4 = this._outputTail - this._outputHead;
/* 983 */                       if (i4 > 0) {
/* 984 */                           this._writer.write(this._outputBuffer, this._outputHead, i4);
                                }
/* 989 */                       char[] cArr = this._outputBuffer;
                                int i5 = this._outputTail;
                                this._outputTail = i5 + 1;
                                char c2 = cArr[i5];
/* 990 */                       _prependOrWriteCharacterEscape(c2, iArr[c2]);
                            }
                        } while (i2 < i3);
/* 992 */               return;
                    }
                }

/* 1001 */      private void _writeLongString(String str) throws IOException {
/* 1002 */          _flushBuffer();
/* 1005 */          int length = str.length();
                    int i = 0;
/* 1020 */          do {
/* 1008 */              int i2 = this._outputEnd;
/* 1009 */              if (i + i2 > length) {
                            i2 = length - i;
                        }
/* 1011 */              str.getChars(i, i + i2, this._outputBuffer, 0);
/* 1012 */              if (this._characterEscapes != null) {
/* 1013 */                  _writeSegmentCustom(i2);
                        } else if (this._maximumNonEscapedChar != 0) {
/* 1015 */                  _writeSegmentASCII(i2, this._maximumNonEscapedChar);
                        } else {
/* 1017 */                  _writeSegment(i2);
                        }
/* 1019 */              i += i2;
/* 1020 */          } while (i < length);
                }

/* 1034 */      private void _writeSegment(int i) throws IOException {
                    char c;
/* 1035 */          int[] iArr = this._outputEscapes;
/* 1036 */          int length = iArr.length;
/* 1038 */          int i2 = 0;
/* 1038 */          int i_prependOrWriteCharacterEscape = 0;
/* 1042 */          while (i2 < i) {
                        do {
/* 1046 */                  c = this._outputBuffer[i2];
/* 1047 */                  if (c < length && iArr[c] != 0) {
                                break;
                            } else {
/* 1050 */                      i2++;
                            }
                        } while (i2 < i);
/* 1059 */              int i3 = i2 - i_prependOrWriteCharacterEscape;
/* 1060 */              if (i3 > 0) {
/* 1061 */                  this._writer.write(this._outputBuffer, i_prependOrWriteCharacterEscape, i3);
/* 1062 */                  if (i2 >= i) {
/* 1070 */                      return;
                            }
                        }
/* 1066 */              int i4 = i2 + 1;
/* 1068 */              i_prependOrWriteCharacterEscape = _prependOrWriteCharacterEscape(this._outputBuffer, i4, i, c, iArr[c]);
                        i2 = i4;
                    }
                }

/* 1078 */      private void _writeString(char[] cArr, int i, int i2) throws IOException {
/* 1079 */          if (this._characterEscapes != null) {
/* 1080 */              _writeStringCustom(cArr, i, i2);
/* 1131 */              return;
                    }
/* 1083 */          if (this._maximumNonEscapedChar != 0) {
/* 1084 */              _writeStringASCII(cArr, i, i2, this._maximumNonEscapedChar);
/* 1131 */              return;
                    }
/* 1092 */          int i3 = i2 + i;
/* 1093 */          int[] iArr = this._outputEscapes;
/* 1094 */          int length = iArr.length;
                    int i4 = i;
/* 1095 */          while (i4 < i3) {
                        int i5 = i4;
                        do {
/* 1099 */                  char c = cArr[i5];
/* 1100 */                  if (c < length && iArr[c] != 0) {
                                break;
                            } else {
/* 1103 */                      i5++;
                            }
                        } while (i5 < i3);
/* 1109 */              int i6 = i5 - i4;
/* 1110 */              if (i6 < 32) {
/* 1112 */                  if (this._outputTail + i6 > this._outputEnd) {
/* 1113 */                      _flushBuffer();
                            }
/* 1115 */                  if (i6 > 0) {
/* 1116 */                      System.arraycopy(cArr, i4, this._outputBuffer, this._outputTail, i6);
/* 1117 */                      this._outputTail += i6;
                            }
                        } else {
/* 1120 */                  _flushBuffer();
/* 1121 */                  this._writer.write(cArr, i4, i6);
                        }
/* 1124 */              if (i5 < i3) {
/* 1128 */                  i4 = i5 + 1;
                            char c2 = cArr[i5];
/* 1129 */                  _appendCharacterEscape(c2, iArr[c2]);
                        } else {
/* 1131 */                  return;
                        }
                    }
                }

                /* JADX WARN: Code duplicated, block: B:11:0x0024  */
                /* JADX WARN: Code duplicated, block: B:21:0x002d A[SYNTHETIC] */
/* 1146 */      private void _writeStringASCII(int i, int i2) throws IOException {
                    int i3;
                    int i4;
                    int i5;
/* 1147 */          int i6 = this._outputTail + i;
/* 1148 */          int[] iArr = this._outputEscapes;
/* 1149 */          int iMin = Math.min(iArr.length, i2 + 1);
/* 1153 */          while (this._outputTail < i6) {
                        do {
/* 1158 */                  char c = this._outputBuffer[this._outputTail];
/* 1159 */                  if (c < iMin) {
/* 1160 */                      i3 = iArr[c];
/* 1161 */                      if (i3 != 0) {
/* 1172 */                          i4 = this._outputTail - this._outputHead;
/* 1173 */                          if (i4 > 0) {
/* 1174 */                              this._writer.write(this._outputBuffer, this._outputHead, i4);
                                    }
/* 1176 */                          this._outputTail++;
/* 1177 */                          _prependOrWriteCharacterEscape(c, i3);
                                }
/* 1168 */                      i5 = this._outputTail + 1;
                                this._outputTail = i5;
                            } else {
/* 1164 */                      if (c > i2) {
/* 1165 */                          i3 = -1;
/* 1172 */                          i4 = this._outputTail - this._outputHead;
/* 1173 */                          if (i4 > 0) {
/* 1174 */                              this._writer.write(this._outputBuffer, this._outputHead, i4);
                                    }
/* 1176 */                          this._outputTail++;
/* 1177 */                          _prependOrWriteCharacterEscape(c, i3);
                                }
/* 1168 */                      i5 = this._outputTail + 1;
                                this._outputTail = i5;
                            }
                        } while (i5 < i6);
/* 1179 */              return;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:24:0x002f A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:27:? A[LOOP:1: B:4:0x000e->B:27:?, LOOP_END, SYNTHETIC] */
/* 1183 */      private void _writeSegmentASCII(int i, int i2) throws IOException {
                    char c;
                    int i3;
                    int i4 = 0;
/* 1184 */          int[] iArr = this._outputEscapes;
/* 1185 */          int iMin = Math.min(iArr.length, i2 + 1);
                    int i_prependOrWriteCharacterEscape = 0;
                    int i5 = 0;
/* 1192 */          while (i5 < i) {
                        while (true) {
/* 1196 */                  c = this._outputBuffer[i5];
/* 1197 */                  if (c < iMin) {
/* 1198 */                      i3 = iArr[c];
/* 1199 */                      if (i3 != 0) {
                                    break;
                                }
                                i4 = i3;
/* 1206 */                      i5++;
                                if (i5 >= i) {
                                    i3 = i4;
                                    break;
                                }
                            } else {
/* 1202 */                      if (c > i2) {
/* 1203 */                          i3 = -1;
                                    break;
                                }
/* 1206 */                      i5++;
                                if (i5 >= i) {
                                    i3 = i4;
                                    break;
                                }
                            }
                        }
/* 1210 */              int i6 = i5 - i_prependOrWriteCharacterEscape;
/* 1211 */              if (i6 > 0) {
/* 1212 */                  this._writer.write(this._outputBuffer, i_prependOrWriteCharacterEscape, i6);
/* 1213 */                  if (i5 >= i) {
/* 1220 */                      return;
                            }
                        }
/* 1217 */              int i7 = i5 + 1;
                        i5 = i7;
/* 1218 */              i_prependOrWriteCharacterEscape = _prependOrWriteCharacterEscape(this._outputBuffer, i7, i, c, i3);
                        i4 = i3;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:20:0x003d A[PHI: r0
  0x003d: PHI (r0v6 int) = (r0v3 int), (r0v7 int) binds: [B:18:0x0039, B:8:0x0016] A[DONT_GENERATE, DONT_INLINE]] */
/* 1225 */      private void _writeStringASCII(char[] cArr, int i, int i2, int i3) throws IOException {
                    char c;
/* 1226 */          int i4 = i2 + i;
/* 1227 */          int[] iArr = this._outputEscapes;
/* 1228 */          int iMin = Math.min(iArr.length, i3 + 1);
/* 1230 */          int i5 = 0;
                    int i6 = i;
/* 1232 */          while (i6 < i4) {
                        int i7 = i6;
                        do {
/* 1237 */                  c = cArr[i7];
/* 1238 */                  if (c < iMin) {
/* 1239 */                      i5 = iArr[c];
/* 1240 */                      if (i5 != 0) {
                                    break;
                                } else {
/* 1247 */                          i7++;
                                }
                            } else {
/* 1243 */                      if (c > i3) {
/* 1244 */                          i5 = -1;
                                    break;
                                }
/* 1247 */                      i7++;
                            }
                        } while (i7 < i4);
/* 1253 */              int i8 = i7 - i6;
/* 1254 */              if (i8 < 32) {
/* 1256 */                  if (this._outputTail + i8 > this._outputEnd) {
/* 1257 */                      _flushBuffer();
                            }
/* 1259 */                  if (i8 > 0) {
/* 1260 */                      System.arraycopy(cArr, i6, this._outputBuffer, this._outputTail, i8);
/* 1261 */                      this._outputTail += i8;
                            }
                        } else {
/* 1264 */                  _flushBuffer();
/* 1265 */                  this._writer.write(cArr, i6, i8);
                        }
/* 1268 */              if (i7 < i4) {
/* 1272 */                  i6 = i7 + 1;
/* 1273 */                  _appendCharacterEscape(c, i5);
                        } else {
/* 1275 */                  return;
                        }
                    }
                }

                /* JADX WARN: Code duplicated, block: B:14:0x002e  */
                /* JADX WARN: Code duplicated, block: B:28:0x0037 A[SYNTHETIC] */
/* 1290 */      private void _writeStringCustom(int i) throws IOException {
                    int i2;
                    int i3;
                    int i4;
/* 1291 */          int i5 = this._outputTail + i;
/* 1292 */          int[] iArr = this._outputEscapes;
/* 1293 */          int i6 = this._maximumNonEscapedChar < 1 ? SupportMenu.USER_MASK : this._maximumNonEscapedChar;
/* 1294 */          int iMin = Math.min(iArr.length, i6 + 1);
/* 1296 */          CharacterEscapes characterEscapes = this._characterEscapes;
/* 1299 */          while (this._outputTail < i5) {
                        do {
/* 1304 */                  char c = this._outputBuffer[this._outputTail];
/* 1305 */                  if (c < iMin) {
/* 1306 */                      i2 = iArr[c];
/* 1307 */                      if (i2 != 0) {
/* 1323 */                          i3 = this._outputTail - this._outputHead;
/* 1324 */                          if (i3 > 0) {
/* 1325 */                              this._writer.write(this._outputBuffer, this._outputHead, i3);
                                    }
/* 1327 */                          this._outputTail++;
/* 1328 */                          _prependOrWriteCharacterEscape(c, i2);
                                }
/* 1319 */                      i4 = this._outputTail + 1;
                                this._outputTail = i4;
                            } else {
/* 1310 */                      if (c > i6) {
/* 1311 */                          i2 = -1;
                                } else {
/* 1314 */                          SerializableString escapeSequence = characterEscapes.getEscapeSequence(c);
                                    this._currentEscape = escapeSequence;
                                    if (escapeSequence != null) {
/* 1315 */                              i2 = -2;
                                    }
/* 1319 */                          i4 = this._outputTail + 1;
                                    this._outputTail = i4;
                                }
/* 1323 */                      i3 = this._outputTail - this._outputHead;
/* 1324 */                      if (i3 > 0) {
/* 1325 */                          this._writer.write(this._outputBuffer, this._outputHead, i3);
                                }
/* 1327 */                      this._outputTail++;
/* 1328 */                      _prependOrWriteCharacterEscape(c, i2);
                            }
                        } while (i4 < i5);
/* 1330 */              return;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:33:0x0048 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:35:? A[LOOP:1: B:7:0x0019->B:35:?, LOOP_END, SYNTHETIC] */
/* 1334 */      private void _writeSegmentCustom(int i) throws IOException {
                    char c;
                    int i2;
                    int i3 = 0;
/* 1335 */          int[] iArr = this._outputEscapes;
/* 1336 */          int i4 = this._maximumNonEscapedChar < 1 ? 65535 : this._maximumNonEscapedChar;
/* 1337 */          int iMin = Math.min(iArr.length, i4 + 1);
/* 1338 */          CharacterEscapes characterEscapes = this._characterEscapes;
                    int i_prependOrWriteCharacterEscape = 0;
                    int i5 = 0;
/* 1345 */          while (i3 < i) {
                        while (true) {
/* 1349 */                  c = this._outputBuffer[i3];
/* 1350 */                  if (c < iMin) {
/* 1351 */                      i2 = iArr[c];
/* 1352 */                      if (i2 != 0) {
                                    break;
                                }
                                i5 = i2;
/* 1364 */                      i3++;
                                if (i3 >= i) {
                                    i2 = i5;
                                    break;
                                }
                            } else {
/* 1355 */                      if (c > i4) {
/* 1356 */                          i2 = -1;
                                    break;
                                }
/* 1359 */                      SerializableString escapeSequence = characterEscapes.getEscapeSequence(c);
                                this._currentEscape = escapeSequence;
                                if (escapeSequence != null) {
/* 1360 */                          i2 = -2;
                                    break;
                                }
/* 1364 */                      i3++;
                                if (i3 >= i) {
                                    i2 = i5;
                                    break;
                                }
                            }
                        }
/* 1368 */              int i6 = i3 - i_prependOrWriteCharacterEscape;
/* 1369 */              if (i6 > 0) {
/* 1370 */                  this._writer.write(this._outputBuffer, i_prependOrWriteCharacterEscape, i6);
/* 1371 */                  if (i3 >= i) {
/* 1378 */                      return;
                            }
                        }
/* 1375 */              int i7 = i3 + 1;
                        i3 = i7;
/* 1376 */              i_prependOrWriteCharacterEscape = _prependOrWriteCharacterEscape(this._outputBuffer, i7, i, c, i2);
                        i5 = i2;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:27:0x0054 A[PHI: r1
  0x0054: PHI (r1v8 int) = (r1v4 int), (r1v9 int) binds: [B:25:0x0050, B:11:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
/* 1382 */      private void _writeStringCustom(char[] cArr, int i, int i2) throws IOException {
                    char c;
/* 1383 */          int i3 = i2 + i;
/* 1384 */          int[] iArr = this._outputEscapes;
/* 1385 */          int i4 = this._maximumNonEscapedChar < 1 ? SupportMenu.USER_MASK : this._maximumNonEscapedChar;
/* 1386 */          int iMin = Math.min(iArr.length, i4 + 1);
/* 1387 */          CharacterEscapes characterEscapes = this._characterEscapes;
/* 1389 */          int i5 = 0;
                    int i6 = i;
/* 1391 */          while (i6 < i3) {
                        int i7 = i6;
                        do {
/* 1396 */                  c = cArr[i7];
/* 1397 */                  if (c < iMin) {
/* 1398 */                      i5 = iArr[c];
/* 1399 */                      if (i5 != 0) {
                                    break;
                                } else {
/* 1411 */                          i7++;
                                }
                            } else {
/* 1402 */                      if (c > i4) {
/* 1403 */                          i5 = -1;
                                    break;
                                }
/* 1406 */                      SerializableString escapeSequence = characterEscapes.getEscapeSequence(c);
                                this._currentEscape = escapeSequence;
                                if (escapeSequence != null) {
/* 1407 */                          i5 = -2;
                                    break;
                                }
/* 1411 */                      i7++;
                            }
                        } while (i7 < i3);
/* 1417 */              int i8 = i7 - i6;
/* 1418 */              if (i8 < 32) {
/* 1420 */                  if (this._outputTail + i8 > this._outputEnd) {
/* 1421 */                      _flushBuffer();
                            }
/* 1423 */                  if (i8 > 0) {
/* 1424 */                      System.arraycopy(cArr, i6, this._outputBuffer, this._outputTail, i8);
/* 1425 */                      this._outputTail += i8;
                            }
                        } else {
/* 1428 */                  _flushBuffer();
/* 1429 */                  this._writer.write(cArr, i6, i8);
                        }
/* 1432 */              if (i7 < i3) {
/* 1436 */                  i6 = i7 + 1;
/* 1437 */                  _appendCharacterEscape(c, i5);
                        } else {
/* 1439 */                  return;
                        }
                    }
                }

/* 1450 */      protected void _writeBinary(Base64Variant base64Variant, byte[] bArr, int i, int i2) throws IOException {
/* 1451 */          int i3 = i2 - 3;
/* 1453 */          int i4 = this._outputEnd - 6;
/* 1454 */          int maxLineLength = base64Variant.getMaxLineLength() >> 2;
/* 1457 */          while (i <= i3) {
/* 1458 */              if (this._outputTail > i4) {
/* 1459 */                  _flushBuffer();
                        }
/* 1462 */              int i5 = i + 1;
                        int i6 = bArr[i] << 8;
/* 1463 */              int i7 = i5 + 1;
                        i = i7 + 1;
/* 1465 */              this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i5] & Constants.UNKNOWN) | i6) << 8) | (bArr[i7] & Constants.UNKNOWN), this._outputBuffer, this._outputTail);
/* 1466 */              maxLineLength--;
                        if (maxLineLength <= 0) {
/* 1468 */                  char[] cArr = this._outputBuffer;
                            int i8 = this._outputTail;
                            this._outputTail = i8 + 1;
                            cArr[i8] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1469 */                  char[] cArr2 = this._outputBuffer;
                            int i9 = this._outputTail;
                            this._outputTail = i9 + 1;
                            cArr2[i9] = 'n';
/* 1470 */                  maxLineLength = base64Variant.getMaxLineLength() >> 2;
                        }
                    }
/* 1475 */          int i10 = i2 - i;
/* 1476 */          if (i10 > 0) {
/* 1477 */              if (this._outputTail > i4) {
/* 1478 */                  _flushBuffer();
                        }
/* 1480 */              int i11 = i + 1;
                        int i12 = bArr[i] << 16;
/* 1481 */              if (i10 == 2) {
/* 1482 */                  int i13 = i11 + 1;
                            i12 |= (bArr[i11] & Constants.UNKNOWN) << 8;
                        }
/* 1484 */              this._outputTail = base64Variant.encodeBase64Partial(i12, i10, this._outputBuffer, this._outputTail);
                    }
                }

/* 1492 */      protected int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr, int i) throws IOException {
                    int i_readMore;
                    int i2;
/* 1493 */          int i3 = 0;
/* 1494 */          int i_readMore2 = 0;
/* 1495 */          int i4 = -3;
/* 1498 */          int i5 = this._outputEnd - 6;
/* 1499 */          int maxLineLength = base64Variant.getMaxLineLength() >> 2;
                    int i6 = i;
/* 1501 */          while (i6 > 2) {
/* 1502 */              if (i3 > i4) {
/* 1503 */                  i_readMore2 = _readMore(inputStream, bArr, i3, i_readMore2, i6);
/* 1504 */                  i3 = 0;
/* 1505 */                  if (i_readMore2 < 3) {
                                break;
                            }
/* 1508 */                  i4 = i_readMore2 - 3;
                        }
/* 1510 */              if (this._outputTail > i5) {
/* 1511 */                  _flushBuffer();
                        }
/* 1513 */              int i7 = i3 + 1;
                        int i8 = bArr[i3] << 8;
/* 1514 */              int i9 = i7 + 1;
                        i3 = i9 + 1;
/* 1516 */              i6 -= 3;
/* 1517 */              this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i7] & Constants.UNKNOWN) | i8) << 8) | (bArr[i9] & Constants.UNKNOWN), this._outputBuffer, this._outputTail);
/* 1518 */              int maxLineLength2 = maxLineLength - 1;
                        if (maxLineLength2 <= 0) {
/* 1519 */                  char[] cArr = this._outputBuffer;
                            int i10 = this._outputTail;
                            this._outputTail = i10 + 1;
                            cArr[i10] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1520 */                  char[] cArr2 = this._outputBuffer;
                            int i11 = this._outputTail;
                            this._outputTail = i11 + 1;
                            cArr2[i11] = 'n';
/* 1521 */                  maxLineLength2 = base64Variant.getMaxLineLength() >> 2;
                        }
                        maxLineLength = maxLineLength2;
                    }
/* 1526 */          if (i6 > 0 && (i_readMore = _readMore(inputStream, bArr, i3, i_readMore2, i6)) > 0) {
/* 1530 */              if (this._outputTail > i5) {
/* 1531 */                  _flushBuffer();
                        }
/* 1528 */              int i12 = bArr[0] << 16;
/* 1535 */              if (1 < i_readMore) {
/* 1536 */                  i12 |= (bArr[1] & Constants.UNKNOWN) << 8;
/* 1537 */                  i2 = 2;
                        } else {
/* 1539 */                  i2 = 1;
                        }
/* 1541 */              this._outputTail = base64Variant.encodeBase64Partial(i12, i2, this._outputBuffer, this._outputTail);
/* 1542 */              return i6 - i2;
                    }
/* 1545 */          return i6;
                }

/* 1554 */      protected int _writeBinary(Base64Variant base64Variant, InputStream inputStream, byte[] bArr) throws IOException {
                    int i;
                    int i2;
/* 1555 */          int i3 = -3;
/* 1559 */          int i4 = this._outputEnd - 6;
/* 1560 */          int maxLineLength = base64Variant.getMaxLineLength() >> 2;
                    int i5 = 0;
                    int i_readMore = 0;
                    int i6 = 0;
                    while (true) {
/* 1564 */              if (i6 > i3) {
/* 1565 */                  i_readMore = _readMore(inputStream, bArr, i6, i_readMore, bArr.length);
/* 1567 */                  if (i_readMore < 3) {
                                break;
                            }
/* 1570 */                  i3 = i_readMore - 3;
                            i6 = 0;
                        }
/* 1572 */              if (this._outputTail > i4) {
/* 1573 */                  _flushBuffer();
                        }
/* 1576 */              int i7 = i6 + 1;
                        int i8 = bArr[i6] << 8;
/* 1577 */              int i9 = i7 + 1;
                        i6 = i9 + 1;
/* 1579 */              i5 += 3;
/* 1580 */              this._outputTail = base64Variant.encodeBase64Chunk((((bArr[i7] & Constants.UNKNOWN) | i8) << 8) | (bArr[i9] & Constants.UNKNOWN), this._outputBuffer, this._outputTail);
/* 1581 */              int maxLineLength2 = maxLineLength - 1;
                        if (maxLineLength2 <= 0) {
/* 1582 */                  char[] cArr = this._outputBuffer;
                            int i10 = this._outputTail;
                            this._outputTail = i10 + 1;
                            cArr[i10] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1583 */                  char[] cArr2 = this._outputBuffer;
                            int i11 = this._outputTail;
                            this._outputTail = i11 + 1;
                            cArr2[i11] = 'n';
/* 1584 */                  maxLineLength2 = base64Variant.getMaxLineLength() >> 2;
                        }
                        maxLineLength = maxLineLength2;
                    }
/* 1589 */          if (0 >= i_readMore) {
                        return i5;
                    }
/* 1590 */          if (this._outputTail > i4) {
/* 1591 */              _flushBuffer();
                    }
/* 1593 */          int i12 = bArr[0] << 16;
/* 1595 */          if (1 < i_readMore) {
/* 1596 */              i = ((bArr[1] & Constants.UNKNOWN) << 8) | i12;
/* 1597 */              i2 = 2;
                    } else {
                        i = i12;
                        i2 = 1;
                    }
/* 1599 */          int i13 = i5 + i2;
/* 1600 */          this._outputTail = base64Variant.encodeBase64Partial(i, i2, this._outputBuffer, this._outputTail);
                    return i13;
                }

/* 1609 */      private int _readMore(InputStream inputStream, byte[] bArr, int i, int i2, int i3) throws IOException {
                    int i4;
/* 1610 */          int i5 = 0;
/* 1611 */          while (i < i2) {
                        bArr[i5] = bArr[i];
/* 1612 */              i5++;
                        i++;
                    }
/* 1616 */          int iMin = Math.min(i3, bArr.length);
/* 1628 */          do {
/* 1619 */              int i6 = iMin - i5;
/* 1620 */              if (i6 == 0 || (i4 = inputStream.read(bArr, i5, i6)) < 0) {
                            break;
                        }
/* 1627 */              i5 += i4;
/* 1628 */          } while (i5 < 3);
/* 1629 */          return i5;
                }

/* 1639 */      private void _writeNull() throws IOException {
/* 1640 */          if (this._outputTail + 4 >= this._outputEnd) {
/* 1641 */              _flushBuffer();
                    }
/* 1643 */          int i = this._outputTail;
/* 1644 */          char[] cArr = this._outputBuffer;
/* 1645 */          cArr[i] = 'n';
/* 1646 */          int i2 = i + 1;
                    cArr[i2] = 'u';
/* 1647 */          int i3 = i2 + 1;
                    cArr[i3] = 'l';
/* 1648 */          int i4 = i3 + 1;
                    cArr[i4] = 'l';
/* 1649 */          this._outputTail = i4 + 1;
                }

/* 1665 */      private void _prependOrWriteCharacterEscape(char c, int i) throws IOException {
                    String value;
                    int i2;
/* 1666 */          if (i >= 0) {
/* 1667 */              if (this._outputTail >= 2) {
/* 1668 */                  int i3 = this._outputTail - 2;
/* 1669 */                  this._outputHead = i3;
/* 1670 */                  this._outputBuffer[i3] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1671 */                  this._outputBuffer[i3 + 1] = (char) i;
/* 1744 */                  return;
                        }
/* 1675 */              char[] cArr_allocateEntityBuffer = this._entityBuffer;
/* 1676 */              if (cArr_allocateEntityBuffer == null) {
/* 1677 */                  cArr_allocateEntityBuffer = _allocateEntityBuffer();
                        }
/* 1679 */              this._outputHead = this._outputTail;
/* 1680 */              cArr_allocateEntityBuffer[1] = (char) i;
/* 1681 */              this._writer.write(cArr_allocateEntityBuffer, 0, 2);
/* 1744 */              return;
                    }
/* 1684 */          if (i != -2) {
/* 1685 */              if (this._outputTail >= 6) {
/* 1686 */                  char[] cArr = this._outputBuffer;
/* 1687 */                  int i4 = this._outputTail - 6;
/* 1688 */                  this._outputHead = i4;
/* 1689 */                  cArr[i4] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1690 */                  int i5 = i4 + 1;
                            cArr[i5] = 'u';
/* 1692 */                  if (c > 255) {
/* 1693 */                      int i6 = (c >> '\b') & 255;
/* 1694 */                      int i7 = i5 + 1;
                                cArr[i7] = HEX_CHARS[i6 >> 4];
/* 1695 */                      i2 = i7 + 1;
                                cArr[i2] = HEX_CHARS[i6 & 15];
/* 1696 */                      c = (char) (c & 255);
                            } else {
/* 1698 */                      int i8 = i5 + 1;
                                cArr[i8] = '0';
/* 1699 */                      i2 = i8 + 1;
                                cArr[i2] = '0';
                            }
/* 1701 */                  int i9 = i2 + 1;
                            cArr[i9] = HEX_CHARS[c >> 4];
/* 1702 */                  cArr[i9 + 1] = HEX_CHARS[c & 15];
/* 1744 */                  return;
                        }
/* 1706 */              char[] cArr_allocateEntityBuffer2 = this._entityBuffer;
/* 1707 */              if (cArr_allocateEntityBuffer2 == null) {
/* 1708 */                  cArr_allocateEntityBuffer2 = _allocateEntityBuffer();
                        }
/* 1710 */              this._outputHead = this._outputTail;
/* 1711 */              if (c > 255) {
/* 1712 */                  int i10 = (c >> '\b') & 255;
/* 1713 */                  int i11 = c & 255;
/* 1714 */                  cArr_allocateEntityBuffer2[10] = HEX_CHARS[i10 >> 4];
/* 1715 */                  cArr_allocateEntityBuffer2[11] = HEX_CHARS[i10 & 15];
/* 1716 */                  cArr_allocateEntityBuffer2[12] = HEX_CHARS[i11 >> 4];
/* 1717 */                  cArr_allocateEntityBuffer2[13] = HEX_CHARS[i11 & 15];
/* 1718 */                  this._writer.write(cArr_allocateEntityBuffer2, 8, 6);
/* 1744 */                  return;
                        }
/* 1720 */              cArr_allocateEntityBuffer2[6] = HEX_CHARS[c >> 4];
/* 1721 */              cArr_allocateEntityBuffer2[7] = HEX_CHARS[c & 15];
/* 1722 */              this._writer.write(cArr_allocateEntityBuffer2, 2, 6);
/* 1744 */              return;
                    }
/* 1728 */          if (this._currentEscape == null) {
/* 1729 */              value = this._characterEscapes.getEscapeSequence(c).getValue();
                    } else {
/* 1731 */              value = this._currentEscape.getValue();
/* 1732 */              this._currentEscape = null;
                    }
/* 1734 */          int length = value.length();
/* 1735 */          if (this._outputTail >= length) {
/* 1736 */              int i12 = this._outputTail - length;
/* 1737 */              this._outputHead = i12;
/* 1738 */              value.getChars(0, length, this._outputBuffer, i12);
                    } else {
/* 1742 */              this._outputHead = this._outputTail;
/* 1743 */              this._writer.write(value);
                    }
                }

/* 1756 */      private int _prependOrWriteCharacterEscape(char[] cArr, int i, int i2, char c, int i3) throws IOException {
                    String value;
                    int i4;
/* 1757 */          if (i3 >= 0) {
/* 1758 */              if (i > 1 && i < i2) {
/* 1759 */                  int i5 = i - 2;
/* 1760 */                  cArr[i5] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1761 */                  cArr[i5 + 1] = (char) i3;
/* 1827 */                  return i5;
                        }
/* 1763 */              char[] cArr_allocateEntityBuffer = this._entityBuffer;
/* 1764 */              if (cArr_allocateEntityBuffer == null) {
/* 1765 */                  cArr_allocateEntityBuffer = _allocateEntityBuffer();
                        }
/* 1767 */              cArr_allocateEntityBuffer[1] = (char) i3;
/* 1768 */              this._writer.write(cArr_allocateEntityBuffer, 0, 2);
/* 1827 */              return i;
                    }
/* 1772 */          if (i3 != -2) {
/* 1773 */              if (i > 5 && i < i2) {
/* 1774 */                  int i6 = i - 6;
/* 1775 */                  int i7 = i6 + 1;
                            cArr[i6] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1776 */                  int i8 = i7 + 1;
                            cArr[i7] = 'u';
/* 1778 */                  if (c > 255) {
/* 1779 */                      int i9 = (c >> '\b') & 255;
/* 1780 */                      int i10 = i8 + 1;
                                cArr[i8] = HEX_CHARS[i9 >> 4];
/* 1781 */                      i4 = i10 + 1;
                                cArr[i10] = HEX_CHARS[i9 & 15];
/* 1782 */                      c = (char) (c & 255);
                            } else {
/* 1784 */                      int i11 = i8 + 1;
                                cArr[i8] = '0';
/* 1785 */                      i4 = i11 + 1;
                                cArr[i11] = '0';
                            }
/* 1787 */                  int i12 = i4 + 1;
                            cArr[i4] = HEX_CHARS[c >> 4];
/* 1788 */                  cArr[i12] = HEX_CHARS[c & 15];
/* 1789 */                  return i12 - 5;
                        }
/* 1792 */              char[] cArr_allocateEntityBuffer2 = this._entityBuffer;
/* 1793 */              if (cArr_allocateEntityBuffer2 == null) {
/* 1794 */                  cArr_allocateEntityBuffer2 = _allocateEntityBuffer();
                        }
/* 1796 */              this._outputHead = this._outputTail;
/* 1797 */              if (c > 255) {
/* 1798 */                  int i13 = (c >> '\b') & 255;
/* 1799 */                  int i14 = c & 255;
/* 1800 */                  cArr_allocateEntityBuffer2[10] = HEX_CHARS[i13 >> 4];
/* 1801 */                  cArr_allocateEntityBuffer2[11] = HEX_CHARS[i13 & 15];
/* 1802 */                  cArr_allocateEntityBuffer2[12] = HEX_CHARS[i14 >> 4];
/* 1803 */                  cArr_allocateEntityBuffer2[13] = HEX_CHARS[i14 & 15];
/* 1804 */                  this._writer.write(cArr_allocateEntityBuffer2, 8, 6);
/* 1827 */                  return i;
                        }
/* 1806 */              cArr_allocateEntityBuffer2[6] = HEX_CHARS[c >> 4];
/* 1807 */              cArr_allocateEntityBuffer2[7] = HEX_CHARS[c & 15];
/* 1808 */              this._writer.write(cArr_allocateEntityBuffer2, 2, 6);
/* 1827 */              return i;
                    }
/* 1814 */          if (this._currentEscape == null) {
/* 1815 */              value = this._characterEscapes.getEscapeSequence(c).getValue();
                    } else {
/* 1817 */              value = this._currentEscape.getValue();
/* 1818 */              this._currentEscape = null;
                    }
/* 1820 */          int length = value.length();
/* 1821 */          if (i >= length && i < i2) {
/* 1822 */              int i15 = i - length;
/* 1823 */              value.getChars(0, length, cArr, i15);
/* 1827 */              return i15;
                    }
/* 1825 */          this._writer.write(value);
/* 1827 */          return i;
                }

/* 1836 */      private void _appendCharacterEscape(char c, int i) throws IOException {
                    String value;
                    int i2;
/* 1837 */          if (i >= 0) {
/* 1838 */              if (this._outputTail + 2 > this._outputEnd) {
/* 1839 */                  _flushBuffer();
                        }
/* 1841 */              char[] cArr = this._outputBuffer;
                        int i3 = this._outputTail;
                        this._outputTail = i3 + 1;
                        cArr[i3] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1842 */              char[] cArr2 = this._outputBuffer;
                        int i4 = this._outputTail;
                        this._outputTail = i4 + 1;
                        cArr2[i4] = (char) i;
/* 1885 */              return;
                    }
/* 1845 */          if (i != -2) {
/* 1846 */              if (this._outputTail + 2 > this._outputEnd) {
/* 1847 */                  _flushBuffer();
                        }
/* 1849 */              int i5 = this._outputTail;
/* 1850 */              char[] cArr3 = this._outputBuffer;
/* 1851 */              int i6 = i5 + 1;
                        cArr3[i5] = IOUtils.DIR_SEPARATOR_WINDOWS;
/* 1852 */              int i7 = i6 + 1;
                        cArr3[i6] = 'u';
/* 1854 */              if (c > 255) {
/* 1855 */                  int i8 = (c >> '\b') & 255;
/* 1856 */                  int i9 = i7 + 1;
                            cArr3[i7] = HEX_CHARS[i8 >> 4];
/* 1857 */                  i2 = i9 + 1;
                            cArr3[i9] = HEX_CHARS[i8 & 15];
/* 1858 */                  c = (char) (c & 255);
                        } else {
/* 1860 */                  int i10 = i7 + 1;
                            cArr3[i7] = '0';
/* 1861 */                  i2 = i10 + 1;
                            cArr3[i10] = '0';
                        }
/* 1863 */              int i11 = i2 + 1;
                        cArr3[i2] = HEX_CHARS[c >> 4];
                        cArr3[i11] = HEX_CHARS[c & 15];
/* 1865 */              this._outputTail = i11 + 1;
/* 1885 */              return;
                    }
/* 1869 */          if (this._currentEscape == null) {
/* 1870 */              value = this._characterEscapes.getEscapeSequence(c).getValue();
                    } else {
/* 1872 */              value = this._currentEscape.getValue();
/* 1873 */              this._currentEscape = null;
                    }
/* 1875 */          int length = value.length();
/* 1876 */          if (this._outputTail + length > this._outputEnd) {
/* 1877 */              _flushBuffer();
/* 1878 */              if (length > this._outputEnd) {
/* 1879 */                  this._writer.write(value);
/* 1885 */                  return;
                        }
                    }
/* 1883 */          value.getChars(0, length, this._outputBuffer, this._outputTail);
/* 1884 */          this._outputTail += length;
                }

/* 1888 */      private char[] _allocateEntityBuffer() {
/* 1889 */          char[] cArr = {IOUtils.DIR_SEPARATOR_WINDOWS, 0, IOUtils.DIR_SEPARATOR_WINDOWS, 'u', '0', '0', 0, 0, IOUtils.DIR_SEPARATOR_WINDOWS, 'u', 0, 0, 0, 0};
/* 1900 */          this._entityBuffer = cArr;
/* 1901 */          return cArr;
                }

/* 1905 */      protected void _flushBuffer() throws IOException {
/* 1906 */          int i = this._outputTail - this._outputHead;
/* 1907 */          if (i > 0) {
/* 1908 */              int i2 = this._outputHead;
/* 1909 */              this._outputHead = 0;
/* 1909 */              this._outputTail = 0;
/* 1910 */              this._writer.write(this._outputBuffer, i2, i);
                    }
                }
            }
