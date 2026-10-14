            package com.google.android.exoplayer.parser.webm;

            import com.flurry.android.Constants;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import com.vandalsoftware.p004io.IoUtils;
            import java.nio.ByteBuffer;
            import java.nio.charset.Charset;
            import java.util.Stack;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    final class DefaultEbmlReader implements EbmlReader {
                private static final int MAX_INTEGER_ELEMENT_SIZE_BYTES = 8;
                private static final int STATE_BEGIN_READING = 0;
                private static final int STATE_FINISHED_READING = 2;
                private static final int STATE_READ_CONTENTS = 1;
                private static final int VALID_FLOAT32_ELEMENT_SIZE_BYTES = 4;
                private static final int VALID_FLOAT64_ELEMENT_SIZE_BYTES = 8;
                private static final int[] VARINT_LENGTH_MASKS = {128, 64, 32, 16, 8, 4, 2, 1};
                private long bytesRead;
                private int bytesState;
                private long elementContentSize;
                private int elementContentSizeState;
                private int elementId;
                private int elementIdState;
                private long elementOffset;
                private EbmlEventHandler eventHandler;
                private int state;
                private byte[] stringBytes;
                private int varintBytesLength;
                private int varintBytesState;
                private final byte[] tempByteArray = new byte[8];
                private final Stack<MasterElement> masterElementsStack = new Stack<>();

/* 32 */        DefaultEbmlReader() {
                }

                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 131 */       public void setEventHandler(EbmlEventHandler eventHandler) {
/* 132 */           this.eventHandler = eventHandler;
                }

                /* JADX WARN: Switch 'out' block B:6:0x000a for B:22:0x006f already processed. Defaulting to fallback option. */
                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 136 */       public int read(NonBlockingInputStream inputStream) {
                    double floatValue;
/* 137 */           Assertions.checkState(this.eventHandler != null);
                    while (true) {
/* 140 */               if (!this.masterElementsStack.isEmpty() && this.bytesRead >= MasterElement.access$000(this.masterElementsStack.peek())) {
/* 141 */                   this.eventHandler.onMasterElementEnd(MasterElement.access$100(this.masterElementsStack.pop()));
/* 142 */                   return 0;
                        }
/* 145 */               if (this.state == 0) {
/* 146 */                   int idResult = readElementId(inputStream);
/* 147 */                   if (idResult == 0) {
/* 150 */                       int sizeResult = readElementContentSize(inputStream);
/* 151 */                       if (sizeResult != 0) {
                                    return sizeResult;
                                }
/* 154 */                       this.state = 1;
/* 155 */                       this.bytesState = 0;
                            } else {
/* 245 */                       return idResult;
                            }
                        }
/* 158 */               int type = this.eventHandler.getElementType(this.elementId);
                        switch (type) {
                            case 0:
                                if (this.elementContentSize > 2147483647L) {
/* 240 */                           throw new IllegalStateException("Unknown element size " + this.elementContentSize + " is larger than MAX_INT");
                                }
/* 243 */                       int skipResult = skipBytesInternal(inputStream, (int) this.elementContentSize);
/* 244 */                       if (skipResult != 0) {
                                    return skipResult;
                                }
/* 247 */                       prepareForNextElement();
                                break;
                            case 1:
/* 161 */                       int masterHeaderSize = (int) (this.bytesRead - this.elementOffset);
/* 162 */                       this.masterElementsStack.add(new MasterElement(this.elementId, this.bytesRead + this.elementContentSize, null));
/* 163 */                       this.eventHandler.onMasterElementStart(this.elementId, this.elementOffset, masterHeaderSize, this.elementContentSize);
/* 165 */                       prepareForNextElement();
/* 166 */                       return 0;
                            case 2:
                                if (this.elementContentSize > 8) {
/* 169 */                           throw new IllegalStateException("Invalid integer size " + this.elementContentSize);
                                }
/* 171 */                       int intResult = readBytesInternal(inputStream, this.tempByteArray, (int) this.elementContentSize);
/* 173 */                       if (intResult != 0) {
                                    return intResult;
                                }
/* 176 */                       long intValue = getTempByteArrayValue((int) this.elementContentSize, false);
/* 177 */                       this.eventHandler.onIntegerElement(this.elementId, intValue);
/* 178 */                       prepareForNextElement();
/* 179 */                       return 0;
                            case 3:
                                if (this.elementContentSize > 2147483647L) {
/* 202 */                           throw new IllegalStateException("String element size " + this.elementContentSize + " is larger than MAX_INT");
                                }
/* 205 */                       if (this.stringBytes == null) {
/* 206 */                           this.stringBytes = new byte[(int) this.elementContentSize];
                                }
/* 208 */                       int stringResult = readBytesInternal(inputStream, this.stringBytes, (int) this.elementContentSize);
/* 210 */                       if (stringResult != 0) {
                                    return stringResult;
                                }
/* 213 */                       String stringValue = new String(this.stringBytes, Charset.forName(IoUtils.UTF_8));
/* 214 */                       this.stringBytes = null;
/* 215 */                       this.eventHandler.onStringElement(this.elementId, stringValue);
/* 216 */                       prepareForNextElement();
/* 217 */                       return 0;
                            case 4:
                                if (this.elementContentSize > 2147483647L) {
/* 220 */                           throw new IllegalStateException("Binary element size " + this.elementContentSize + " is larger than MAX_INT");
                                }
                                if (inputStream.getAvailableByteCount() < this.elementContentSize) {
/* 224 */                           return 1;
                                }
/* 226 */                       int binaryHeaderSize = (int) (this.bytesRead - this.elementOffset);
/* 227 */                       boolean consumed = this.eventHandler.onBinaryElement(this.elementId, this.elementOffset, binaryHeaderSize, (int) this.elementContentSize, inputStream);
/* 229 */                       if (consumed) {
/* 230 */                           long expectedBytesRead = this.elementOffset + ((long) binaryHeaderSize) + this.elementContentSize;
                                    if (expectedBytesRead != this.bytesRead) {
/* 232 */                               throw new IllegalStateException("Incorrect total bytes read. Expected " + expectedBytesRead + " but actually " + this.bytesRead);
                                    }
/* 235 */                           prepareForNextElement();
                                }
/* 237 */                       return 0;
                            case 5:
                                if (this.elementContentSize != 4 && this.elementContentSize != 8) {
/* 183 */                           throw new IllegalStateException("Invalid float size " + this.elementContentSize);
                                }
/* 185 */                       int floatResult = readBytesInternal(inputStream, this.tempByteArray, (int) this.elementContentSize);
/* 187 */                       if (floatResult != 0) {
                                    return floatResult;
                                }
/* 190 */                       long valueBits = getTempByteArrayValue((int) this.elementContentSize, false);
                                if (this.elementContentSize == 4) {
/* 193 */                           floatValue = Float.intBitsToFloat((int) valueBits);
                                } else {
/* 195 */                           floatValue = Double.longBitsToDouble(valueBits);
                                }
/* 197 */                       this.eventHandler.onFloatElement(this.elementId, floatValue);
/* 198 */                       prepareForNextElement();
/* 199 */                       return 0;
                            default:
/* 250 */                       throw new IllegalStateException("Invalid element type " + type);
                        }
                    }
                }

                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 256 */       public long getBytesRead() {
/* 257 */           return this.bytesRead;
                }

                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 261 */       public void reset() {
/* 262 */           prepareForNextElement();
/* 263 */           this.masterElementsStack.clear();
/* 264 */           this.bytesRead = 0L;
                }

                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 268 */       public long readVarint(NonBlockingInputStream inputStream) {
/* 269 */           this.varintBytesState = 0;
/* 270 */           int result = readVarintBytes(inputStream);
/* 271 */           if (result != 0) {
/* 272 */               throw new IllegalStateException("Couldn't read varint");
                    }
/* 274 */           return getTempByteArrayValue(this.varintBytesLength, true);
                }

                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 278 */       public void readBytes(NonBlockingInputStream inputStream, ByteBuffer byteBuffer, int totalBytes) {
/* 279 */           this.bytesState = 0;
/* 280 */           int result = readBytesInternal(inputStream, byteBuffer, totalBytes);
/* 281 */           if (result != 0) {
/* 282 */               throw new IllegalStateException("Couldn't read bytes into buffer");
                    }
                }

                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 287 */       public void readBytes(NonBlockingInputStream inputStream, byte[] byteArray, int totalBytes) {
/* 288 */           this.bytesState = 0;
/* 289 */           int result = readBytesInternal(inputStream, byteArray, totalBytes);
/* 290 */           if (result != 0) {
/* 291 */               throw new IllegalStateException("Couldn't read bytes into array");
                    }
                }

                @Override // com.google.android.exoplayer.parser.webm.EbmlReader
/* 296 */       public void skipBytes(NonBlockingInputStream inputStream, int totalBytes) {
/* 297 */           this.bytesState = 0;
/* 298 */           int result = skipBytesInternal(inputStream, totalBytes);
/* 299 */           if (result != 0) {
/* 300 */               throw new IllegalStateException("Couldn't skip bytes");
                    }
                }

/* 308 */       private void prepareForNextElement() {
/* 309 */           this.state = 0;
/* 310 */           this.elementIdState = 0;
/* 311 */           this.elementContentSizeState = 0;
/* 312 */           this.elementOffset = this.bytesRead;
                }

/* 324 */       private int readElementId(NonBlockingInputStream inputStream) {
/* 325 */           if (this.elementIdState == 2) {
                        return 0;
                    }
/* 328 */           if (this.elementIdState == 0) {
/* 329 */               this.varintBytesState = 0;
/* 330 */               this.elementIdState = 1;
                    }
/* 332 */           int result = readVarintBytes(inputStream);
/* 333 */           if (result == 0) {
/* 337 */               this.elementId = (int) getTempByteArrayValue(this.varintBytesLength, false);
/* 338 */               this.elementIdState = 2;
                        return 0;
                    }
/* 339 */           return result;
                }

/* 353 */       private int readElementContentSize(NonBlockingInputStream inputStream) {
/* 354 */           if (this.elementContentSizeState == 2) {
                        return 0;
                    }
/* 357 */           if (this.elementContentSizeState == 0) {
/* 358 */               this.varintBytesState = 0;
/* 359 */               this.elementContentSizeState = 1;
                    }
/* 361 */           int result = readVarintBytes(inputStream);
/* 362 */           if (result == 0) {
/* 365 */               this.elementContentSize = getTempByteArrayValue(this.varintBytesLength, true);
/* 366 */               this.elementContentSizeState = 2;
                        return 0;
                    }
/* 367 */           return result;
                }

/* 382 */       private int readVarintBytes(NonBlockingInputStream inputStream) {
/* 383 */           if (this.varintBytesState == 2) {
                        return 0;
                    }
/* 388 */           if (this.varintBytesState == 0) {
/* 389 */               this.bytesState = 0;
/* 390 */               int result = readBytesInternal(inputStream, this.tempByteArray, 1);
/* 391 */               if (result == 0) {
/* 394 */                   this.varintBytesState = 1;
/* 396 */                   int firstByte = this.tempByteArray[0] & Constants.UNKNOWN;
/* 397 */                   this.varintBytesLength = -1;
                            for (int i = 0; i < VARINT_LENGTH_MASKS.length; i++) {
/* 399 */                       if ((VARINT_LENGTH_MASKS[i] & firstByte) != 0) {
/* 400 */                           this.varintBytesLength = i + 1;
                                    break;
                                }
                            }
/* 404 */                   if (this.varintBytesLength == -1) {
/* 405 */                       throw new IllegalStateException("No valid varint length mask found at bytesRead = " + this.bytesRead);
                            }
                        } else {
/* 417 */                   return result;
                        }
                    }
/* 411 */           int result2 = readBytesInternal(inputStream, this.tempByteArray, this.varintBytesLength);
/* 412 */           if (result2 == 0) {
                        return 0;
                    }
/* 417 */           return result2;
                }

/* 433 */       private int readBytesInternal(NonBlockingInputStream inputStream, ByteBuffer byteBuffer, int totalBytes) {
/* 434 */           if (this.bytesState == 0 && totalBytes > byteBuffer.capacity()) {
/* 435 */               throw new IllegalArgumentException("Byte buffer not large enough");
                    }
/* 437 */           if (this.bytesState >= totalBytes) {
/* 438 */               return 0;
                    }
/* 440 */           int remainingBytes = totalBytes - this.bytesState;
/* 441 */           int additionalBytesRead = inputStream.read(byteBuffer, remainingBytes);
/* 442 */           return updateBytesState(additionalBytesRead, totalBytes);
                }

/* 458 */       private int readBytesInternal(NonBlockingInputStream inputStream, byte[] byteArray, int totalBytes) {
/* 459 */           if (this.bytesState == 0 && totalBytes > byteArray.length) {
/* 460 */               throw new IllegalArgumentException("Byte array not large enough");
                    }
/* 462 */           if (this.bytesState >= totalBytes) {
/* 463 */               return 0;
                    }
/* 465 */           int remainingBytes = totalBytes - this.bytesState;
/* 466 */           int additionalBytesRead = inputStream.read(byteArray, this.bytesState, remainingBytes);
/* 467 */           return updateBytesState(additionalBytesRead, totalBytes);
                }

/* 482 */       private int skipBytesInternal(NonBlockingInputStream inputStream, int totalBytes) {
/* 483 */           if (this.bytesState >= totalBytes) {
/* 484 */               return 0;
                    }
/* 486 */           int remainingBytes = totalBytes - this.bytesState;
/* 487 */           int additionalBytesRead = inputStream.skip(remainingBytes);
/* 488 */           return updateBytesState(additionalBytesRead, totalBytes);
                }

/* 499 */       private int updateBytesState(int additionalBytesRead, int totalBytes) {
/* 500 */           if (additionalBytesRead == -1) {
/* 501 */               return 2;
                    }
/* 503 */           this.bytesState += additionalBytesRead;
/* 504 */           this.bytesRead += (long) additionalBytesRead;
/* 505 */           if (this.bytesState < totalBytes) {
/* 506 */               return 1;
                    }
/* 508 */           return 0;
                }

/* 520 */       private long getTempByteArrayValue(int byteLength, boolean removeLengthMask) {
/* 521 */           if (removeLengthMask) {
/* 522 */               byte[] bArr = this.tempByteArray;
                        bArr[0] = (byte) (bArr[0] & (VARINT_LENGTH_MASKS[this.varintBytesLength - 1] ^ (-1)));
                    }
/* 524 */           long varint = 0;
                    for (int i = 0; i < byteLength; i++) {
/* 527 */               varint = (varint << 8) | ((long) (this.tempByteArray[i] & Constants.UNKNOWN));
                    }
/* 529 */           return varint;
                }

/* 534 */       private static final class MasterElement {
                    private final long elementEndOffsetBytes;
                    private final int elementId;

/* 535 */           /* synthetic */ MasterElement(int x0, long x1, Object x2) {
/* 536 */               this(x0, x1);
                    }

/* 535 */           static /* synthetic */ long access$000(MasterElement x0) {
/* 536 */               return x0.elementEndOffsetBytes;
                    }

/* 535 */           static /* synthetic */ int access$100(MasterElement x0) {
/* 536 */               return x0.elementId;
                    }

/* 540 */           private MasterElement(int elementId, long elementEndOffsetBytes) {
/* 542 */               this.elementId = elementId;
/* 543 */               this.elementEndOffsetBytes = elementEndOffsetBytes;
                    }
                }
            }
