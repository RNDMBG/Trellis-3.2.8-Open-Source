            package com.fasterxml.jackson.core.base;

            import com.fasterxml.jackson.core.Base64Variant;
            import com.fasterxml.jackson.core.JsonLocation;
            import com.fasterxml.jackson.core.JsonParseException;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonStreamContext;
            import com.fasterxml.jackson.core.JsonToken;
            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.json.DupDetector;
            import com.fasterxml.jackson.core.json.JsonReadContext;
            import com.fasterxml.jackson.core.json.PackageVersion;
            import com.fasterxml.jackson.core.p003io.IOContext;
            import com.fasterxml.jackson.core.p003io.NumberInput;
            import com.fasterxml.jackson.core.util.ByteArrayBuilder;
            import com.fasterxml.jackson.core.util.TextBuffer;
            import java.io.IOException;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public abstract class ParserBase extends ParserMinimalBase {
                protected static final char CHAR_NULL = 0;
                protected static final int INT_0 = 48;
                protected static final int INT_9 = 57;
                protected static final int INT_MINUS = 45;
                protected static final int INT_PLUS = 43;
                static final double MAX_INT_D = 2.147483647E9d;
                static final double MAX_LONG_D = 9.223372036854776E18d;
                static final double MIN_INT_D = -2.147483648E9d;
                static final double MIN_LONG_D = -9.223372036854776E18d;
                protected static final int NR_BIGDECIMAL = 16;
                protected static final int NR_BIGINT = 4;
                protected static final int NR_DOUBLE = 8;
                protected static final int NR_INT = 1;
                protected static final int NR_LONG = 2;
                protected static final int NR_UNKNOWN = 0;
                protected byte[] _binaryValue;
                protected boolean _closed;
                protected int _expLength;
                protected int _fractLength;
                protected int _intLength;
                protected final IOContext _ioContext;
                protected JsonToken _nextToken;
                protected BigDecimal _numberBigDecimal;
                protected BigInteger _numberBigInt;
                protected double _numberDouble;
                protected int _numberInt;
                protected long _numberLong;
                protected boolean _numberNegative;
                protected JsonReadContext _parsingContext;
                protected final TextBuffer _textBuffer;
                static final long MIN_INT_L = -2147483648L;
                static final BigInteger BI_MIN_INT = BigInteger.valueOf(MIN_INT_L);
                static final long MAX_INT_L = 2147483647L;
                static final BigInteger BI_MAX_INT = BigInteger.valueOf(MAX_INT_L);
                static final BigInteger BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
                static final BigInteger BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
                static final BigDecimal BD_MIN_LONG = new BigDecimal(BI_MIN_LONG);
                static final BigDecimal BD_MAX_LONG = new BigDecimal(BI_MAX_LONG);
                static final BigDecimal BD_MIN_INT = new BigDecimal(BI_MIN_INT);
                static final BigDecimal BD_MAX_INT = new BigDecimal(BI_MAX_INT);
                protected int _inputPtr = 0;
                protected int _inputEnd = 0;
                protected long _currInputProcessed = 0;
                protected int _currInputRow = 1;
                protected int _currInputRowStart = 0;
                protected long _tokenInputTotal = 0;
                protected int _tokenInputRow = 1;
                protected int _tokenInputCol = 0;
                protected char[] _nameCopyBuffer = null;
                protected boolean _nameCopied = false;
                protected ByteArrayBuilder _byteArrayBuilder = null;
                protected int _numTypesValid = 0;

                protected abstract void _closeInput() throws IOException;

                protected abstract void _finishString() throws IOException;

                protected abstract boolean loadMore() throws IOException;


/* 285 */       protected ParserBase(IOContext iOContext, int i) {
/* 287 */           this._features = i;
/* 288 */           this._ioContext = iOContext;
/* 289 */           this._textBuffer = iOContext.constructTextBuffer();
/* 293 */           this._parsingContext = JsonReadContext.createRootContext(JsonParser.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(i) ? DupDetector.rootDetector(this) : null);
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.core.Versioned
/* 297 */       public Version version() {
/* 298 */           return PackageVersion.VERSION;
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 315 */       public String getCurrentName() throws IOException {
                    return (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) ? this._parsingContext.getParent().getCurrentName() : this._parsingContext.getCurrentName();
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 326 */       public void overrideCurrentName(String str) {
/* 327 */           JsonReadContext parent = this._parsingContext;
/* 328 */           if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
/* 329 */               parent = parent.getParent();
                    }
                    try {
/* 335 */               parent.setCurrentName(str);
                    } catch (IOException e) {
/* 337 */               throw new IllegalStateException(e);
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser, java.io.Closeable, java.lang.AutoCloseable
/* 343 */       public void close() throws IOException {
/* 344 */           if (!this._closed) {
/* 345 */               this._closed = true;
                        try {
/* 347 */                   _closeInput();
/* 351 */                   _releaseBuffers();
                        } catch (Throwable th) {
                            _releaseBuffers();
                            throw th;
                        }
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 356 */       public boolean isClosed() {
/* 357 */           return this._closed;
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 361 */       public JsonReadContext getParsingContext() {
/* 362 */           return this._parsingContext;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 372 */       public JsonLocation getTokenLocation() {
/* 373 */           return new JsonLocation(this._ioContext.getSourceReference(), -1L, getTokenCharacterOffset(), getTokenLineNr(), getTokenColumnNr());
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 385 */       public JsonLocation getCurrentLocation() {
/* 387 */           return new JsonLocation(this._ioContext.getSourceReference(), -1L, this._currInputProcessed + ((long) this._inputPtr), this._currInputRow, (this._inputPtr - this._currInputRowStart) + 1);
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
/* 400 */       public boolean hasTextCharacters() {
/* 401 */           if (this._currToken == JsonToken.VALUE_STRING) {
/* 402 */               return true;
                    }
/* 404 */           if (this._currToken == JsonToken.FIELD_NAME) {
/* 405 */               return this._nameCopied;
                    }
/* 407 */           return false;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 412 */       public Object getEmbeddedObject() throws IOException {
/* 413 */           return null;
                }

/* 421 */       public long getTokenCharacterOffset() {
/* 422 */           return this._tokenInputTotal;
                }

/* 422 */       public int getTokenLineNr() {
/* 423 */           return this._tokenInputRow;
                }

/* 425 */       public int getTokenColumnNr() {
/* 426 */           int i = this._tokenInputCol;
                    return i < 0 ? i : i + 1;
                }

/* 438 */       protected final void loadMoreGuaranteed() throws IOException {
/* 439 */           if (!loadMore()) {
/* 440 */               _reportInvalidEOF();
                    }
                }

/* 469 */       protected void _releaseBuffers() throws IOException {
/* 470 */           this._textBuffer.releaseBuffers();
/* 471 */           char[] cArr = this._nameCopyBuffer;
/* 472 */           if (cArr != null) {
/* 473 */               this._nameCopyBuffer = null;
/* 474 */               this._ioContext.releaseNameCopyBuffer(cArr);
                    }
                }

                @Override // com.fasterxml.jackson.core.base.ParserMinimalBase
/* 485 */       protected void _handleEOF() throws JsonParseException {
/* 486 */           if (!this._parsingContext.inRoot()) {
/* 487 */               _reportInvalidEOF(": expected close marker for " + this._parsingContext.getTypeDesc() + " (from " + this._parsingContext.getStartLocation(this._ioContext.getSourceReference()) + ")");
                    }
                }

/* 499 */       protected void _reportMismatchedEndMarker(int i, char c) throws JsonParseException {
/* 501 */           _reportError("Unexpected close marker '" + ((char) i) + "': expected '" + c + "' (for " + this._parsingContext.getTypeDesc() + " starting at " + ("" + this._parsingContext.getStartLocation(this._ioContext.getSourceReference())) + ")");
                }

/* 511 */       public ByteArrayBuilder _getByteArrayBuilder() {
/* 512 */           if (this._byteArrayBuilder == null) {
/* 513 */               this._byteArrayBuilder = new ByteArrayBuilder();
                    } else {
/* 515 */               this._byteArrayBuilder.reset();
                    }
/* 517 */           return this._byteArrayBuilder;
                }

/* 529 */       protected final JsonToken reset(boolean z, int i, int i2, int i3) {
                    return (i2 >= 1 || i3 >= 1) ? resetFloat(z, i, i2, i3) : resetInt(z, i);
                }

/* 537 */       protected final JsonToken resetInt(boolean z, int i) {
/* 538 */           this._numberNegative = z;
/* 539 */           this._intLength = i;
/* 540 */           this._fractLength = 0;
/* 541 */           this._expLength = 0;
/* 542 */           this._numTypesValid = 0;
/* 543 */           return JsonToken.VALUE_NUMBER_INT;
                }

/* 547 */       protected final JsonToken resetFloat(boolean z, int i, int i2, int i3) {
/* 548 */           this._numberNegative = z;
/* 549 */           this._intLength = i;
/* 550 */           this._fractLength = i2;
/* 551 */           this._expLength = i3;
/* 552 */           this._numTypesValid = 0;
/* 553 */           return JsonToken.VALUE_NUMBER_FLOAT;
                }

/* 557 */       protected final JsonToken resetAsNaN(String str, double d) {
/* 558 */           this._textBuffer.resetWithString(str);
/* 559 */           this._numberDouble = d;
/* 560 */           this._numTypesValid = 8;
/* 561 */           return JsonToken.VALUE_NUMBER_FLOAT;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 572 */       public Number getNumberValue() throws IOException {
/* 573 */           if (this._numTypesValid == 0) {
/* 574 */               _parseNumericValue(0);
                    }
/* 577 */           if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/* 578 */               if ((this._numTypesValid & 1) != 0) {
/* 579 */                   return Integer.valueOf(this._numberInt);
                        }
/* 581 */               if ((this._numTypesValid & 2) != 0) {
/* 582 */                   return Long.valueOf(this._numberLong);
                        }
/* 584 */               if ((this._numTypesValid & 4) != 0) {
/* 585 */                   return this._numberBigInt;
                        }
/* 588 */               return this._numberBigDecimal;
                    }
/* 594 */           if ((this._numTypesValid & 16) != 0) {
/* 595 */               return this._numberBigDecimal;
                    }
/* 597 */           if ((this._numTypesValid & 8) == 0) {
/* 598 */               _throwInternal();
                    }
/* 600 */           return Double.valueOf(this._numberDouble);
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 605 */       public JsonParser.NumberType getNumberType() throws IOException {
/* 606 */           if (this._numTypesValid == 0) {
/* 607 */               _parseNumericValue(0);
                    }
/* 609 */           if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/* 610 */               if ((this._numTypesValid & 1) != 0) {
/* 611 */                   return JsonParser.NumberType.INT;
                        }
/* 613 */               if ((this._numTypesValid & 2) != 0) {
/* 614 */                   return JsonParser.NumberType.LONG;
                        }
/* 616 */               return JsonParser.NumberType.BIG_INTEGER;
                    }
/* 625 */           if ((this._numTypesValid & 16) != 0) {
/* 626 */               return JsonParser.NumberType.BIG_DECIMAL;
                    }
/* 628 */           return JsonParser.NumberType.DOUBLE;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 633 */       public int getIntValue() throws IOException {
/* 634 */           if ((this._numTypesValid & 1) == 0) {
/* 635 */               if (this._numTypesValid == 0) {
/* 636 */                   _parseNumericValue(1);
                        }
/* 638 */               if ((this._numTypesValid & 1) == 0) {
/* 639 */                   convertNumberToInt();
                        }
                    }
/* 642 */           return this._numberInt;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 647 */       public long getLongValue() throws IOException {
/* 648 */           if ((this._numTypesValid & 2) == 0) {
/* 649 */               if (this._numTypesValid == 0) {
/* 650 */                   _parseNumericValue(2);
                        }
/* 652 */               if ((this._numTypesValid & 2) == 0) {
/* 653 */                   convertNumberToLong();
                        }
                    }
/* 656 */           return this._numberLong;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 661 */       public BigInteger getBigIntegerValue() throws IOException {
/* 662 */           if ((this._numTypesValid & 4) == 0) {
/* 663 */               if (this._numTypesValid == 0) {
/* 664 */                   _parseNumericValue(4);
                        }
/* 666 */               if ((this._numTypesValid & 4) == 0) {
/* 667 */                   convertNumberToBigInteger();
                        }
                    }
/* 670 */           return this._numberBigInt;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 675 */       public float getFloatValue() throws IOException {
/* 685 */           return (float) getDoubleValue();
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 690 */       public double getDoubleValue() throws IOException {
/* 691 */           if ((this._numTypesValid & 8) == 0) {
/* 692 */               if (this._numTypesValid == 0) {
/* 693 */                   _parseNumericValue(8);
                        }
/* 695 */               if ((this._numTypesValid & 8) == 0) {
/* 696 */                   convertNumberToDouble();
                        }
                    }
/* 699 */           return this._numberDouble;
                }

                @Override // com.fasterxml.jackson.core.JsonParser
/* 704 */       public BigDecimal getDecimalValue() throws IOException {
/* 705 */           if ((this._numTypesValid & 16) == 0) {
/* 706 */               if (this._numTypesValid == 0) {
/* 707 */                   _parseNumericValue(16);
                        }
/* 709 */               if ((this._numTypesValid & 16) == 0) {
/* 710 */                   convertNumberToBigDecimal();
                        }
                    }
/* 713 */           return this._numberBigDecimal;
                }

/* 734 */       protected void _parseNumericValue(int i) throws IOException {
/* 735 */           if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/* 736 */               char[] textBuffer = this._textBuffer.getTextBuffer();
/* 737 */               int textOffset = this._textBuffer.getTextOffset();
/* 738 */               int i2 = this._intLength;
/* 739 */               if (this._numberNegative) {
/* 740 */                   textOffset++;
                        }
/* 742 */               if (i2 <= 9) {
/* 743 */                   int i3 = NumberInput.parseInt(textBuffer, textOffset, i2);
/* 744 */                   if (this._numberNegative) {
                                i3 = -i3;
                            }
                            this._numberInt = i3;
/* 745 */                   this._numTypesValid = 1;
/* 781 */                   return;
                        }
/* 748 */               if (i2 <= 18) {
/* 749 */                   long j = NumberInput.parseLong(textBuffer, textOffset, i2);
/* 750 */                   if (this._numberNegative) {
/* 751 */                       j = -j;
                            }
/* 754 */                   if (i2 == 10) {
/* 755 */                       if (this._numberNegative) {
                                    if (j >= MIN_INT_L) {
/* 757 */                               this._numberInt = (int) j;
/* 758 */                               this._numTypesValid = 1;
/* 781 */                               return;
                                    }
                                } else if (j <= MAX_INT_L) {
/* 763 */                           this._numberInt = (int) j;
/* 764 */                           this._numTypesValid = 1;
/* 781 */                           return;
                                }
                            }
/* 769 */                   this._numberLong = j;
/* 770 */                   this._numTypesValid = 2;
/* 781 */                   return;
                        }
/* 773 */               _parseSlowInt(i, textBuffer, textOffset, i2);
/* 781 */               return;
                    }
/* 776 */           if (this._currToken == JsonToken.VALUE_NUMBER_FLOAT) {
/* 777 */               _parseSlowFloat(i);
                    } else {
/* 780 */               _reportError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
                    }
                }

/* 793 */       private void _parseSlowFloat(int i) throws IOException {
                    try {
/* 794 */               if (i == 16) {
/* 795 */                   this._numberBigDecimal = this._textBuffer.contentsAsDecimal();
/* 796 */                   this._numTypesValid = 16;
                        } else {
/* 799 */                   this._numberDouble = this._textBuffer.contentsAsDouble();
/* 800 */                   this._numTypesValid = 8;
                        }
                    } catch (NumberFormatException e) {
/* 804 */               _wrapError("Malformed numeric value '" + this._textBuffer.contentsAsString() + "'", e);
                    }
                }

/* 810 */       private void _parseSlowInt(int i, char[] cArr, int i2, int i3) throws IOException {
/* 811 */           String strContentsAsString = this._textBuffer.contentsAsString();
                    try {
/* 814 */               if (NumberInput.inLongRange(cArr, i2, i3, this._numberNegative)) {
/* 816 */                   this._numberLong = Long.parseLong(strContentsAsString);
/* 817 */                   this._numTypesValid = 2;
                        } else {
/* 820 */                   this._numberBigInt = new BigInteger(strContentsAsString);
/* 821 */                   this._numTypesValid = 4;
                        }
                    } catch (NumberFormatException e) {
/* 825 */               _wrapError("Malformed numeric value '" + strContentsAsString + "'", e);
                    }
                }

/* 838 */       protected void convertNumberToInt() throws IOException {
/* 839 */           if ((this._numTypesValid & 2) != 0) {
/* 841 */               int i = (int) this._numberLong;
                        if (i != this._numberLong) {
/* 843 */                   _reportError("Numeric value (" + getText() + ") out of range of int");
                        }
/* 845 */               this._numberInt = i;
                    } else if ((this._numTypesValid & 4) != 0) {
/* 847 */               if (BI_MIN_INT.compareTo(this._numberBigInt) > 0 || BI_MAX_INT.compareTo(this._numberBigInt) < 0) {
/* 849 */                   reportOverflowInt();
                        }
/* 851 */               this._numberInt = this._numberBigInt.intValue();
                    } else if ((this._numTypesValid & 8) != 0) {
                        if (this._numberDouble < MIN_INT_D || this._numberDouble > MAX_INT_D) {
/* 855 */                   reportOverflowInt();
                        }
/* 857 */               this._numberInt = (int) this._numberDouble;
                    } else if ((this._numTypesValid & 16) != 0) {
/* 859 */               if (BD_MIN_INT.compareTo(this._numberBigDecimal) > 0 || BD_MAX_INT.compareTo(this._numberBigDecimal) < 0) {
/* 861 */                   reportOverflowInt();
                        }
/* 863 */               this._numberInt = this._numberBigDecimal.intValue();
                    } else {
/* 865 */               _throwInternal();
                    }
/* 867 */           this._numTypesValid |= 1;
                }

/* 872 */       protected void convertNumberToLong() throws IOException {
/* 873 */           if ((this._numTypesValid & 1) != 0) {
/* 874 */               this._numberLong = this._numberInt;
                    } else if ((this._numTypesValid & 4) != 0) {
/* 876 */               if (BI_MIN_LONG.compareTo(this._numberBigInt) > 0 || BI_MAX_LONG.compareTo(this._numberBigInt) < 0) {
/* 878 */                   reportOverflowLong();
                        }
/* 880 */               this._numberLong = this._numberBigInt.longValue();
                    } else if ((this._numTypesValid & 8) != 0) {
                        if (this._numberDouble < MIN_LONG_D || this._numberDouble > MAX_LONG_D) {
/* 884 */                   reportOverflowLong();
                        }
/* 886 */               this._numberLong = (long) this._numberDouble;
                    } else if ((this._numTypesValid & 16) != 0) {
/* 888 */               if (BD_MIN_LONG.compareTo(this._numberBigDecimal) > 0 || BD_MAX_LONG.compareTo(this._numberBigDecimal) < 0) {
/* 890 */                   reportOverflowLong();
                        }
/* 892 */               this._numberLong = this._numberBigDecimal.longValue();
                    } else {
/* 894 */               _throwInternal();
                    }
/* 896 */           this._numTypesValid |= 2;
                }

/* 901 */       protected void convertNumberToBigInteger() throws IOException {
/* 902 */           if ((this._numTypesValid & 16) != 0) {
/* 904 */               this._numberBigInt = this._numberBigDecimal.toBigInteger();
                    } else if ((this._numTypesValid & 2) != 0) {
/* 906 */               this._numberBigInt = BigInteger.valueOf(this._numberLong);
                    } else if ((this._numTypesValid & 1) != 0) {
/* 908 */               this._numberBigInt = BigInteger.valueOf(this._numberInt);
                    } else if ((this._numTypesValid & 8) != 0) {
/* 910 */               this._numberBigInt = BigDecimal.valueOf(this._numberDouble).toBigInteger();
                    } else {
/* 912 */               _throwInternal();
                    }
/* 914 */           this._numTypesValid |= 4;
                }

/* 925 */       protected void convertNumberToDouble() throws IOException {
/* 926 */           if ((this._numTypesValid & 16) != 0) {
/* 927 */               this._numberDouble = this._numberBigDecimal.doubleValue();
                    } else if ((this._numTypesValid & 4) != 0) {
/* 929 */               this._numberDouble = this._numberBigInt.doubleValue();
                    } else if ((this._numTypesValid & 2) != 0) {
/* 931 */               this._numberDouble = this._numberLong;
                    } else if ((this._numTypesValid & 1) != 0) {
/* 933 */               this._numberDouble = this._numberInt;
                    } else {
/* 935 */               _throwInternal();
                    }
/* 937 */           this._numTypesValid |= 8;
                }

/* 948 */       protected void convertNumberToBigDecimal() throws IOException {
/* 949 */           if ((this._numTypesValid & 8) != 0) {
/* 954 */               this._numberBigDecimal = NumberInput.parseBigDecimal(getText());
                    } else if ((this._numTypesValid & 4) != 0) {
/* 956 */               this._numberBigDecimal = new BigDecimal(this._numberBigInt);
                    } else if ((this._numTypesValid & 2) != 0) {
/* 958 */               this._numberBigDecimal = BigDecimal.valueOf(this._numberLong);
                    } else if ((this._numTypesValid & 1) != 0) {
/* 960 */               this._numberBigDecimal = BigDecimal.valueOf(this._numberInt);
                    } else {
/* 962 */               _throwInternal();
                    }
/* 964 */           this._numTypesValid |= 16;
                }

/* 975 */       protected void reportUnexpectedNumberChar(int i, String str) throws JsonParseException {
/* 976 */           String str2 = "Unexpected character (" + _getCharDesc(i) + ") in numeric value";
/* 977 */           if (str != null) {
/* 978 */               str2 = str2 + ": " + str;
                    }
/* 980 */           _reportError(str2);
                }

/* 985 */       protected void reportInvalidNumber(String str) throws JsonParseException {
/* 986 */           _reportError("Invalid numeric value: " + str);
                }

/* 991 */       protected void reportOverflowInt() throws IOException {
/* 992 */           _reportError("Numeric value (" + getText() + ") out of range of int (-2147483648 - 2147483647)");
                }

/* 997 */       protected void reportOverflowLong() throws IOException {
/* 998 */           _reportError("Numeric value (" + getText() + ") out of range of long (-9223372036854775808 - 9223372036854775807)");
                }

/* 1013 */      protected char _decodeEscaped() throws IOException {
/* 1014 */          throw new UnsupportedOperationException();
                }

/* 1020 */      protected final int _decodeBase64Escape(Base64Variant base64Variant, int i, int i2) throws IOException {
/* 1021 */          if (i != 92) {
/* 1022 */              throw reportInvalidBase64Char(base64Variant, i, i2);
                    }
/* 1024 */          char c_decodeEscaped = _decodeEscaped();
/* 1026 */          if (c_decodeEscaped <= ' ' && i2 == 0) {
/* 1028 */              return -1;
                    }
/* 1032 */          int iDecodeBase64Char = base64Variant.decodeBase64Char((int) c_decodeEscaped);
/* 1033 */          if (iDecodeBase64Char < 0) {
/* 1034 */              throw reportInvalidBase64Char(base64Variant, c_decodeEscaped, i2);
                    }
/* 1036 */          return iDecodeBase64Char;
                }

/* 1042 */      protected final int _decodeBase64Escape(Base64Variant base64Variant, char c, int i) throws IOException {
/* 1043 */          if (c != '\\') {
/* 1044 */              throw reportInvalidBase64Char(base64Variant, c, i);
                    }
/* 1046 */          char c_decodeEscaped = _decodeEscaped();
/* 1048 */          if (c_decodeEscaped <= ' ' && i == 0) {
/* 1050 */              return -1;
                    }
/* 1054 */          int iDecodeBase64Char = base64Variant.decodeBase64Char(c_decodeEscaped);
/* 1055 */          if (iDecodeBase64Char < 0) {
/* 1056 */              throw reportInvalidBase64Char(base64Variant, c_decodeEscaped, i);
                    }
/* 1058 */          return iDecodeBase64Char;
                }

/* 1063 */      protected IllegalArgumentException reportInvalidBase64Char(Base64Variant base64Variant, int i, int i2) throws IllegalArgumentException {
/* 1064 */          return reportInvalidBase64Char(base64Variant, i, i2, null);
                }

/* 1074 */      protected IllegalArgumentException reportInvalidBase64Char(Base64Variant base64Variant, int i, int i2, String str) throws IllegalArgumentException {
                    String str2;
/* 1075 */          if (i <= 32) {
/* 1076 */              str2 = "Illegal white space character (code 0x" + Integer.toHexString(i) + ") as character #" + (i2 + 1) + " of 4-char base64 unit: can only used between units";
                    } else if (base64Variant.usesPaddingChar(i)) {
/* 1078 */              str2 = "Unexpected padding character ('" + base64Variant.getPaddingChar() + "') as character #" + (i2 + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
                    } else if (!Character.isDefined(i) || Character.isISOControl(i)) {
/* 1081 */              str2 = "Illegal character (code 0x" + Integer.toHexString(i) + ") in base64 content";
                    } else {
/* 1083 */              str2 = "Illegal character '" + ((char) i) + "' (code 0x" + Integer.toHexString(i) + ") in base64 content";
                    }
/* 1085 */          if (str != null) {
/* 1086 */              str2 = str2 + ": " + str;
                    }
/* 1088 */          return new IllegalArgumentException(str2);
                }
            }
