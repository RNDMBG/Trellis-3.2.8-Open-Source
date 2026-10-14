            package com.fasterxml.jackson.core.util;

            import com.fasterxml.jackson.core.p003io.NumberInput;
            import java.math.BigDecimal;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 118 */   public final class TextBuffer {
                static final int MAX_SEGMENT_LEN = 262144;
                static final int MIN_SEGMENT_LEN = 1000;
                static final char[] NO_CHARS = new char[0];
                private final BufferRecycler _allocator;
                private char[] _currentSegment;
                private int _currentSize;
                private boolean _hasSegments = false;
                private char[] _inputBuffer;
                private int _inputLen;
                private int _inputStart;
                private char[] _resultArray;
                private String _resultString;
                private int _segmentSize;
                private ArrayList<char[]> _segments;

/* 119 */       public TextBuffer(BufferRecycler bufferRecycler) {
/* 121 */           this._allocator = bufferRecycler;
                }

/* 134 */       public void releaseBuffers() {
/* 135 */           if (this._allocator == null) {
/* 136 */               resetWithEmpty();
                    } else if (this._currentSegment != null) {
/* 140 */               resetWithEmpty();
/* 142 */               char[] cArr = this._currentSegment;
/* 143 */               this._currentSegment = null;
/* 144 */               this._allocator.releaseCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, cArr);
                    }
                }

/* 154 */       public void resetWithEmpty() {
/* 155 */           this._inputStart = -1;
/* 156 */           this._currentSize = 0;
/* 157 */           this._inputLen = 0;
/* 159 */           this._inputBuffer = null;
/* 160 */           this._resultString = null;
/* 161 */           this._resultArray = null;
/* 164 */           if (this._hasSegments) {
/* 165 */               clearSegments();
                    }
                }

/* 177 */       public void resetWithShared(char[] cArr, int i, int i2) {
/* 178 */           this._resultString = null;
/* 179 */           this._resultArray = null;
/* 182 */           this._inputBuffer = cArr;
/* 183 */           this._inputStart = i;
/* 184 */           this._inputLen = i2;
/* 187 */           if (this._hasSegments) {
/* 188 */               clearSegments();
                    }
                }

/* 193 */       public void resetWithCopy(char[] cArr, int i, int i2) {
/* 194 */           this._inputBuffer = null;
/* 195 */           this._inputStart = -1;
/* 196 */           this._inputLen = 0;
/* 198 */           this._resultString = null;
/* 199 */           this._resultArray = null;
/* 202 */           if (this._hasSegments) {
/* 203 */               clearSegments();
                    } else if (this._currentSegment == null) {
/* 205 */               this._currentSegment = buf(i2);
                    }
/* 207 */           this._segmentSize = 0;
                    this._currentSize = 0;
/* 208 */           append(cArr, i, i2);
                }

/* 212 */       public void resetWithString(String str) {
/* 213 */           this._inputBuffer = null;
/* 214 */           this._inputStart = -1;
/* 215 */           this._inputLen = 0;
/* 217 */           this._resultString = str;
/* 218 */           this._resultArray = null;
/* 220 */           if (this._hasSegments) {
/* 221 */               clearSegments();
                    }
/* 223 */           this._currentSize = 0;
                }

/* 232 */       private char[] buf(int i) {
                    return this._allocator != null ? this._allocator.allocCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, i) : new char[Math.max(i, 1000)];
                }

/* 240 */       private void clearSegments() {
/* 241 */           this._hasSegments = false;
/* 249 */           this._segments.clear();
/* 250 */           this._segmentSize = 0;
                    this._currentSize = 0;
                }

/* 262 */       public int size() {
/* 263 */           if (this._inputStart >= 0) {
/* 264 */               return this._inputLen;
                    }
/* 266 */           if (this._resultArray != null) {
/* 267 */               return this._resultArray.length;
                    }
/* 269 */           if (this._resultString != null) {
/* 270 */               return this._resultString.length();
                    }
/* 273 */           return this._segmentSize + this._currentSize;
                }

/* 280 */       public int getTextOffset() {
/* 281 */           if (this._inputStart >= 0) {
                        return this._inputStart;
                    }
                    return 0;
                }

/* 290 */       public boolean hasTextAsCharacters() {
                    return this._inputStart >= 0 || this._resultArray != null || this._resultString == null;
                }

/* 299 */       public char[] getTextBuffer() {
/* 300 */           if (this._inputStart >= 0) {
                        return this._inputBuffer;
                    }
/* 301 */           if (this._resultArray != null) {
                        return this._resultArray;
                    }
/* 302 */           if (this._resultString == null) {
                        return !this._hasSegments ? this._currentSegment : contentsAsArray();
                    }
/* 303 */           char[] charArray = this._resultString.toCharArray();
                    this._resultArray = charArray;
/* 308 */           return charArray;
                }

/* 318 */       public String contentsAsString() {
/* 319 */           if (this._resultString == null) {
/* 321 */               if (this._resultArray != null) {
/* 322 */                   this._resultString = new String(this._resultArray);
                        } else if (this._inputStart >= 0) {
/* 326 */                   if (this._inputLen < 1) {
/* 327 */                       this._resultString = "";
/* 327 */                       return "";
                            }
/* 329 */                   this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
                        } else {
/* 332 */                   int i = this._segmentSize;
/* 333 */                   int i2 = this._currentSize;
/* 335 */                   if (i == 0) {
/* 336 */                       this._resultString = i2 == 0 ? "" : new String(this._currentSegment, 0, i2);
                            } else {
/* 338 */                       StringBuilder sb = new StringBuilder(i + i2);
/* 340 */                       if (this._segments != null) {
/* 341 */                           int size = this._segments.size();
                                    for (int i3 = 0; i3 < size; i3++) {
/* 342 */                               char[] cArr = this._segments.get(i3);
/* 343 */                               sb.append(cArr, 0, cArr.length);
                                    }
                                }
/* 347 */                       sb.append(this._currentSegment, 0, this._currentSize);
/* 348 */                       this._resultString = sb.toString();
                            }
                        }
                    }
/* 353 */           return this._resultString;
                }

/* 356 */       public char[] contentsAsArray() {
/* 357 */           char[] cArr = this._resultArray;
/* 358 */           if (cArr == null) {
/* 359 */               char[] cArrResultArray = resultArray();
                        this._resultArray = cArrResultArray;
/* 361 */               return cArrResultArray;
                    }
/* 361 */           return cArr;
                }

/* 370 */       public BigDecimal contentsAsDecimal() throws NumberFormatException {
/* 371 */           if (this._resultArray != null) {
/* 372 */               return NumberInput.parseBigDecimal(this._resultArray);
                    }
/* 375 */           if (this._inputStart >= 0 && this._inputBuffer != null) {
/* 376 */               return NumberInput.parseBigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
                    }
/* 379 */           if (this._segmentSize == 0 && this._currentSegment != null) {
/* 380 */               return NumberInput.parseBigDecimal(this._currentSegment, 0, this._currentSize);
                    }
/* 383 */           return NumberInput.parseBigDecimal(contentsAsArray());
                }

/* 390 */       public double contentsAsDouble() throws NumberFormatException {
/* 391 */           return NumberInput.parseDouble(contentsAsString());
                }

/* 404 */       public void ensureNotShared() {
/* 405 */           if (this._inputStart >= 0) {
/* 406 */               unshare(16);
                    }
                }

/* 411 */       public void append(char c) {
/* 412 */           if (this._inputStart >= 0) {
/* 413 */               unshare(16);
                    }
/* 415 */           this._resultString = null;
/* 416 */           this._resultArray = null;
/* 418 */           char[] cArr = this._currentSegment;
/* 419 */           if (this._currentSize >= cArr.length) {
/* 420 */               expand(1);
/* 421 */               cArr = this._currentSegment;
                    }
/* 423 */           int i = this._currentSize;
                    this._currentSize = i + 1;
                    cArr[i] = c;
                }

/* 428 */       public void append(char[] cArr, int i, int i2) {
/* 429 */           if (this._inputStart >= 0) {
/* 430 */               unshare(i2);
                    }
/* 432 */           this._resultString = null;
/* 433 */           this._resultArray = null;
/* 436 */           char[] cArr2 = this._currentSegment;
/* 437 */           int length = cArr2.length - this._currentSize;
/* 439 */           if (length >= i2) {
/* 440 */               System.arraycopy(cArr, i, cArr2, this._currentSize, i2);
/* 441 */               this._currentSize += i2;
/* 462 */               return;
                    }
/* 445 */           if (length > 0) {
/* 446 */               System.arraycopy(cArr, i, cArr2, this._currentSize, length);
/* 447 */               i += length;
/* 448 */               i2 -= length;
                    }
/* 461 */           do {
/* 455 */               expand(i2);
/* 456 */               int iMin = Math.min(this._currentSegment.length, i2);
/* 457 */               System.arraycopy(cArr, i, this._currentSegment, 0, iMin);
/* 458 */               this._currentSize += iMin;
/* 459 */               i += iMin;
/* 460 */               i2 -= iMin;
/* 461 */           } while (i2 > 0);
                }

/* 466 */       public void append(String str, int i, int i2) {
/* 467 */           if (this._inputStart >= 0) {
/* 468 */               unshare(i2);
                    }
/* 470 */           this._resultString = null;
/* 471 */           this._resultArray = null;
/* 474 */           char[] cArr = this._currentSegment;
/* 475 */           int length = cArr.length - this._currentSize;
/* 476 */           if (length >= i2) {
/* 477 */               str.getChars(i, i + i2, cArr, this._currentSize);
/* 478 */               this._currentSize += i2;
/* 499 */               return;
                    }
/* 482 */           if (length > 0) {
/* 483 */               str.getChars(i, i + length, cArr, this._currentSize);
/* 484 */               i2 -= length;
/* 485 */               i += length;
                    }
/* 498 */           do {
/* 492 */               expand(i2);
/* 493 */               int iMin = Math.min(this._currentSegment.length, i2);
/* 494 */               str.getChars(i, i + iMin, this._currentSegment, 0);
/* 495 */               this._currentSize += iMin;
/* 496 */               i += iMin;
/* 497 */               i2 -= iMin;
/* 498 */           } while (i2 > 0);
                }

/* 512 */       public char[] getCurrentSegment() {
/* 513 */           if (this._inputStart >= 0) {
/* 514 */               unshare(1);
                    } else {
/* 516 */               char[] cArr = this._currentSegment;
/* 517 */               if (cArr == null) {
/* 518 */                   this._currentSegment = buf(0);
                        } else if (this._currentSize >= cArr.length) {
/* 521 */                   expand(1);
                        }
                    }
/* 524 */           return this._currentSegment;
                }

/* 529 */       public char[] emptyAndGetCurrentSegment() {
/* 530 */           this._inputStart = -1;
/* 531 */           this._currentSize = 0;
/* 532 */           this._inputLen = 0;
/* 534 */           this._inputBuffer = null;
/* 535 */           this._resultString = null;
/* 536 */           this._resultArray = null;
/* 539 */           if (this._hasSegments) {
/* 540 */               clearSegments();
                    }
/* 542 */           char[] cArr = this._currentSegment;
/* 543 */           if (cArr == null) {
/* 544 */               char[] cArrBuf = buf(0);
                        this._currentSegment = cArrBuf;
/* 546 */               return cArrBuf;
                    }
/* 546 */           return cArr;
                }

/* 548 */       public int getCurrentSegmentSize() {
/* 549 */           return this._currentSize;
                }

/* 549 */       public void setCurrentLength(int i) {
/* 550 */           this._currentSize = i;
                }

/* 552 */       public char[] finishCurrentSegment() {
/* 553 */           if (this._segments == null) {
/* 554 */               this._segments = new ArrayList<>();
                    }
/* 556 */           this._hasSegments = true;
/* 557 */           this._segments.add(this._currentSegment);
/* 558 */           int length = this._currentSegment.length;
/* 559 */           this._segmentSize += length;
/* 562 */           char[] carr = carr(Math.min(length + (length >> 1), 262144));
/* 563 */           this._currentSize = 0;
/* 564 */           this._currentSegment = carr;
/* 565 */           return carr;
                }

/* 574 */       public char[] expandCurrentSegment() {
/* 575 */           char[] cArr = this._currentSegment;
/* 577 */           int length = cArr.length;
/* 580 */           char[] cArrCopyOf = ArraysCompat.copyOf(cArr, length == 262144 ? 262145 : Math.min(262144, length + (length >> 1)));
                    this._currentSegment = cArrCopyOf;
                    return cArrCopyOf;
                }

/* 592 */       public char[] expandCurrentSegment(int i) {
/* 593 */           char[] cArr = this._currentSegment;
/* 594 */           if (cArr.length < i) {
/* 595 */               char[] cArrCopyOf = ArraysCompat.copyOf(cArr, i);
                        this._currentSegment = cArrCopyOf;
/* 596 */               return cArrCopyOf;
                    }
/* 596 */           return cArr;
                }

/* 609 */       public String toString() {
/* 610 */           return contentsAsString();
                }

/* 623 */       private void unshare(int i) {
/* 624 */           int i2 = this._inputLen;
/* 625 */           this._inputLen = 0;
/* 626 */           char[] cArr = this._inputBuffer;
/* 627 */           this._inputBuffer = null;
/* 628 */           int i3 = this._inputStart;
/* 629 */           this._inputStart = -1;
/* 632 */           int i4 = i2 + i;
/* 633 */           if (this._currentSegment == null || i4 > this._currentSegment.length) {
/* 634 */               this._currentSegment = buf(i4);
                    }
/* 636 */           if (i2 > 0) {
/* 637 */               System.arraycopy(cArr, i3, this._currentSegment, 0, i2);
                    }
/* 639 */           this._segmentSize = 0;
/* 640 */           this._currentSize = i2;
                }

/* 649 */       private void expand(int i) {
/* 650 */           if (this._segments == null) {
/* 651 */               this._segments = new ArrayList<>();
                    }
/* 653 */           char[] cArr = this._currentSegment;
/* 654 */           this._hasSegments = true;
/* 655 */           this._segments.add(cArr);
/* 656 */           this._segmentSize += cArr.length;
/* 657 */           int length = cArr.length;
/* 659 */           int i2 = length >> 1;
/* 660 */           if (i2 >= i) {
                        i = i2;
                    }
/* 663 */           this._currentSize = 0;
/* 664 */           this._currentSegment = carr(Math.min(262144, length + i));
                }

/* 668 */       private char[] resultArray() {
                    int i;
/* 669 */           if (this._resultString != null) {
/* 670 */               return this._resultString.toCharArray();
                    }
/* 673 */           if (this._inputStart >= 0) {
/* 674 */               int i2 = this._inputLen;
/* 675 */               if (i2 < 1) {
/* 676 */                   return NO_CHARS;
                        }
/* 678 */               int i3 = this._inputStart;
/* 679 */               if (i3 == 0) {
/* 680 */                   return ArraysCompat.copyOf(this._inputBuffer, i2);
                        }
/* 682 */               return ArraysCompat.copyOfRange(this._inputBuffer, i3, i2 + i3);
                    }
/* 685 */           int size = size();
/* 686 */           if (size < 1) {
/* 687 */               return NO_CHARS;
                    }
/* 690 */           char[] carr = carr(size);
/* 691 */           if (this._segments != null) {
/* 692 */               int size2 = this._segments.size();
                        int i4 = 0;
                        for (int i5 = 0; i5 < size2; i5++) {
/* 693 */                   char[] cArr = this._segments.get(i5);
/* 694 */                   int length = cArr.length;
/* 695 */                   System.arraycopy(cArr, 0, carr, i4, length);
/* 696 */                   i4 += length;
                        }
                        i = i4;
                    } else {
                        i = 0;
                    }
/* 699 */           System.arraycopy(this._currentSegment, 0, carr, i, this._currentSize);
                    return carr;
                }

/* 702 */       private char[] carr(int i) {
/* 703 */           return new char[i];
                }
            }
