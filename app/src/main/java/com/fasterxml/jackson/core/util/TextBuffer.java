            package com.fasterxml.jackson.core.util;

            import com.fasterxml.jackson.core.p003io.NumberInput;
            import java.math.BigDecimal;
            import java.util.ArrayList;
            
            
   public final class TextBuffer {
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

       public TextBuffer(BufferRecycler bufferRecycler) {
           this._allocator = bufferRecycler;
                }

       public void releaseBuffers() {
           if (this._allocator == null) {
               resetWithEmpty();
                    } else if (this._currentSegment != null) {
               resetWithEmpty();
               char[] cArr = this._currentSegment;
               this._currentSegment = null;
               this._allocator.releaseCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, cArr);
                    }
                }

       public void resetWithEmpty() {
           this._inputStart = -1;
           this._currentSize = 0;
           this._inputLen = 0;
           this._inputBuffer = null;
           this._resultString = null;
           this._resultArray = null;
           if (this._hasSegments) {
               clearSegments();
                    }
                }

       public void resetWithShared(char[] cArr, int i, int i2) {
           this._resultString = null;
           this._resultArray = null;
           this._inputBuffer = cArr;
           this._inputStart = i;
           this._inputLen = i2;
           if (this._hasSegments) {
               clearSegments();
                    }
                }

       public void resetWithCopy(char[] cArr, int i, int i2) {
           this._inputBuffer = null;
           this._inputStart = -1;
           this._inputLen = 0;
           this._resultString = null;
           this._resultArray = null;
           if (this._hasSegments) {
               clearSegments();
                    } else if (this._currentSegment == null) {
               this._currentSegment = buf(i2);
                    }
           this._segmentSize = 0;
                    this._currentSize = 0;
           append(cArr, i, i2);
                }

       public void resetWithString(String str) {
           this._inputBuffer = null;
           this._inputStart = -1;
           this._inputLen = 0;
           this._resultString = str;
           this._resultArray = null;
           if (this._hasSegments) {
               clearSegments();
                    }
           this._currentSize = 0;
                }

       private char[] buf(int i) {
                    return this._allocator != null ? this._allocator.allocCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, i) : new char[Math.max(i, 1000)];
                }

       private void clearSegments() {
           this._hasSegments = false;
           this._segments.clear();
           this._segmentSize = 0;
                    this._currentSize = 0;
                }

       public int size() {
           if (this._inputStart >= 0) {
               return this._inputLen;
                    }
           if (this._resultArray != null) {
               return this._resultArray.length;
                    }
           if (this._resultString != null) {
               return this._resultString.length();
                    }
           return this._segmentSize + this._currentSize;
                }

       public int getTextOffset() {
           if (this._inputStart >= 0) {
                        return this._inputStart;
                    }
                    return 0;
                }

       public boolean hasTextAsCharacters() {
                    return this._inputStart >= 0 || this._resultArray != null || this._resultString == null;
                }

       public char[] getTextBuffer() {
           if (this._inputStart >= 0) {
                        return this._inputBuffer;
                    }
           if (this._resultArray != null) {
                        return this._resultArray;
                    }
           if (this._resultString == null) {
                        return !this._hasSegments ? this._currentSegment : contentsAsArray();
                    }
           char[] charArray = this._resultString.toCharArray();
                    this._resultArray = charArray;
           return charArray;
                }

       public String contentsAsString() {
           if (this._resultString == null) {
               if (this._resultArray != null) {
                   this._resultString = new String(this._resultArray);
                        } else if (this._inputStart >= 0) {
                   if (this._inputLen < 1) {
                       this._resultString = "";
                       return "";
                            }
                   this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
                        } else {
                   int i = this._segmentSize;
                   int i2 = this._currentSize;
                   if (i == 0) {
                       this._resultString = i2 == 0 ? "" : new String(this._currentSegment, 0, i2);
                            } else {
                       StringBuilder sb = new StringBuilder(i + i2);
                       if (this._segments != null) {
                           int size = this._segments.size();
                                    for (int i3 = 0; i3 < size; i3++) {
                               char[] cArr = this._segments.get(i3);
                               sb.append(cArr, 0, cArr.length);
                                    }
                                }
                       sb.append(this._currentSegment, 0, this._currentSize);
                       this._resultString = sb.toString();
                            }
                        }
                    }
           return this._resultString;
                }

       public char[] contentsAsArray() {
           char[] cArr = this._resultArray;
           if (cArr == null) {
               char[] cArrResultArray = resultArray();
                        this._resultArray = cArrResultArray;
               return cArrResultArray;
                    }
           return cArr;
                }

       public BigDecimal contentsAsDecimal() throws NumberFormatException {
           if (this._resultArray != null) {
               return NumberInput.parseBigDecimal(this._resultArray);
                    }
           if (this._inputStart >= 0 && this._inputBuffer != null) {
               return NumberInput.parseBigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
                    }
           if (this._segmentSize == 0 && this._currentSegment != null) {
               return NumberInput.parseBigDecimal(this._currentSegment, 0, this._currentSize);
                    }
           return NumberInput.parseBigDecimal(contentsAsArray());
                }

       public double contentsAsDouble() throws NumberFormatException {
           return NumberInput.parseDouble(contentsAsString());
                }

       public void ensureNotShared() {
           if (this._inputStart >= 0) {
               unshare(16);
                    }
                }

       public void append(char c) {
           if (this._inputStart >= 0) {
               unshare(16);
                    }
           this._resultString = null;
           this._resultArray = null;
           char[] cArr = this._currentSegment;
           if (this._currentSize >= cArr.length) {
               expand(1);
               cArr = this._currentSegment;
                    }
           int i = this._currentSize;
                    this._currentSize = i + 1;
                    cArr[i] = c;
                }

       public void append(char[] cArr, int i, int i2) {
           if (this._inputStart >= 0) {
               unshare(i2);
                    }
           this._resultString = null;
           this._resultArray = null;
           char[] cArr2 = this._currentSegment;
           int length = cArr2.length - this._currentSize;
           if (length >= i2) {
               System.arraycopy(cArr, i, cArr2, this._currentSize, i2);
               this._currentSize += i2;
               return;
                    }
           if (length > 0) {
               System.arraycopy(cArr, i, cArr2, this._currentSize, length);
               i += length;
               i2 -= length;
                    }
           do {
               expand(i2);
               int iMin = Math.min(this._currentSegment.length, i2);
               System.arraycopy(cArr, i, this._currentSegment, 0, iMin);
               this._currentSize += iMin;
               i += iMin;
               i2 -= iMin;
           } while (i2 > 0);
                }

       public void append(String str, int i, int i2) {
           if (this._inputStart >= 0) {
               unshare(i2);
                    }
           this._resultString = null;
           this._resultArray = null;
           char[] cArr = this._currentSegment;
           int length = cArr.length - this._currentSize;
           if (length >= i2) {
               str.getChars(i, i + i2, cArr, this._currentSize);
               this._currentSize += i2;
               return;
                    }
           if (length > 0) {
               str.getChars(i, i + length, cArr, this._currentSize);
               i2 -= length;
               i += length;
                    }
           do {
               expand(i2);
               int iMin = Math.min(this._currentSegment.length, i2);
               str.getChars(i, i + iMin, this._currentSegment, 0);
               this._currentSize += iMin;
               i += iMin;
               i2 -= iMin;
           } while (i2 > 0);
                }

       public char[] getCurrentSegment() {
           if (this._inputStart >= 0) {
               unshare(1);
                    } else {
               char[] cArr = this._currentSegment;
               if (cArr == null) {
                   this._currentSegment = buf(0);
                        } else if (this._currentSize >= cArr.length) {
                   expand(1);
                        }
                    }
           return this._currentSegment;
                }

       public char[] emptyAndGetCurrentSegment() {
           this._inputStart = -1;
           this._currentSize = 0;
           this._inputLen = 0;
           this._inputBuffer = null;
           this._resultString = null;
           this._resultArray = null;
           if (this._hasSegments) {
               clearSegments();
                    }
           char[] cArr = this._currentSegment;
           if (cArr == null) {
               char[] cArrBuf = buf(0);
                        this._currentSegment = cArrBuf;
               return cArrBuf;
                    }
           return cArr;
                }

       public int getCurrentSegmentSize() {
           return this._currentSize;
                }

       public void setCurrentLength(int i) {
           this._currentSize = i;
                }

       public char[] finishCurrentSegment() {
           if (this._segments == null) {
               this._segments = new ArrayList<>();
                    }
           this._hasSegments = true;
           this._segments.add(this._currentSegment);
           int length = this._currentSegment.length;
           this._segmentSize += length;
           char[] carr = carr(Math.min(length + (length >> 1), 262144));
           this._currentSize = 0;
           this._currentSegment = carr;
           return carr;
                }

       public char[] expandCurrentSegment() {
           char[] cArr = this._currentSegment;
           int length = cArr.length;
           char[] cArrCopyOf = ArraysCompat.copyOf(cArr, length == 262144 ? 262145 : Math.min(262144, length + (length >> 1)));
                    this._currentSegment = cArrCopyOf;
                    return cArrCopyOf;
                }

       public char[] expandCurrentSegment(int i) {
           char[] cArr = this._currentSegment;
           if (cArr.length < i) {
               char[] cArrCopyOf = ArraysCompat.copyOf(cArr, i);
                        this._currentSegment = cArrCopyOf;
               return cArrCopyOf;
                    }
           return cArr;
                }

       public String toString() {
           return contentsAsString();
                }

       private void unshare(int i) {
           int i2 = this._inputLen;
           this._inputLen = 0;
           char[] cArr = this._inputBuffer;
           this._inputBuffer = null;
           int i3 = this._inputStart;
           this._inputStart = -1;
           int i4 = i2 + i;
           if (this._currentSegment == null || i4 > this._currentSegment.length) {
               this._currentSegment = buf(i4);
                    }
           if (i2 > 0) {
               System.arraycopy(cArr, i3, this._currentSegment, 0, i2);
                    }
           this._segmentSize = 0;
           this._currentSize = i2;
                }

       private void expand(int i) {
           if (this._segments == null) {
               this._segments = new ArrayList<>();
                    }
           char[] cArr = this._currentSegment;
           this._hasSegments = true;
           this._segments.add(cArr);
           this._segmentSize += cArr.length;
           int length = cArr.length;
           int i2 = length >> 1;
           if (i2 >= i) {
                        i = i2;
                    }
           this._currentSize = 0;
           this._currentSegment = carr(Math.min(262144, length + i));
                }

       private char[] resultArray() {
                    int i;
           if (this._resultString != null) {
               return this._resultString.toCharArray();
                    }
           if (this._inputStart >= 0) {
               int i2 = this._inputLen;
               if (i2 < 1) {
                   return NO_CHARS;
                        }
               int i3 = this._inputStart;
               if (i3 == 0) {
                   return ArraysCompat.copyOf(this._inputBuffer, i2);
                        }
               return ArraysCompat.copyOfRange(this._inputBuffer, i3, i2 + i3);
                    }
           int size = size();
           if (size < 1) {
               return NO_CHARS;
                    }
           char[] carr = carr(size);
           if (this._segments != null) {
               int size2 = this._segments.size();
                        int i4 = 0;
                        for (int i5 = 0; i5 < size2; i5++) {
                   char[] cArr = this._segments.get(i5);
                   int length = cArr.length;
                   System.arraycopy(cArr, 0, carr, i4, length);
                   i4 += length;
                        }
                        i = i4;
                    } else {
                        i = 0;
                    }
           System.arraycopy(this._currentSegment, 0, carr, i, this._currentSize);
                    return carr;
                }

       private char[] carr(int i) {
           return new char[i];
                }
            }
