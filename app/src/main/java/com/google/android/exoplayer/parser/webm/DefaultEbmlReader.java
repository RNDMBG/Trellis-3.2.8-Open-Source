            package com.google.android.exoplayer.parser.webm;

            import com.flurry.android.Constants;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import com.vandalsoftware.p004io.IoUtils;
            import java.nio.ByteBuffer;
            import java.nio.charset.Charset;
            import java.util.Stack;
            
            
    final class DefaultEbmlReader implements EbmlReader {
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

        DefaultEbmlReader() {
                }

                @Override 
       public void setEventHandler(EbmlEventHandler eventHandler) {
           this.eventHandler = eventHandler;
                }

                
                @Override 
       public int read(NonBlockingInputStream inputStream) {
                    double floatValue;
           Assertions.checkState(this.eventHandler != null);
                    while (true) {
               if (!this.masterElementsStack.isEmpty() && this.bytesRead >= MasterElement.access$000(this.masterElementsStack.peek())) {
                   this.eventHandler.onMasterElementEnd(MasterElement.access$100(this.masterElementsStack.pop()));
                   return 0;
                        }
               if (this.state == 0) {
                   int idResult = readElementId(inputStream);
                   if (idResult == 0) {
                       int sizeResult = readElementContentSize(inputStream);
                       if (sizeResult != 0) {
                                    return sizeResult;
                                }
                       this.state = 1;
                       this.bytesState = 0;
                            } else {
                       return idResult;
                            }
                        }
               int type = this.eventHandler.getElementType(this.elementId);
                        switch (type) {
                            case 0:
                                if (this.elementContentSize > 2147483647L) {
                           throw new IllegalStateException("Unknown element size " + this.elementContentSize + " is larger than MAX_INT");
                                }
                       int skipResult = skipBytesInternal(inputStream, (int) this.elementContentSize);
                       if (skipResult != 0) {
                                    return skipResult;
                                }
                       prepareForNextElement();
                                break;
                            case 1:
                       int masterHeaderSize = (int) (this.bytesRead - this.elementOffset);
                       this.masterElementsStack.add(new MasterElement(this.elementId, this.bytesRead + this.elementContentSize, null));
                       this.eventHandler.onMasterElementStart(this.elementId, this.elementOffset, masterHeaderSize, this.elementContentSize);
                       prepareForNextElement();
                       return 0;
                            case 2:
                                if (this.elementContentSize > 8) {
                           throw new IllegalStateException("Invalid integer size " + this.elementContentSize);
                                }
                       int intResult = readBytesInternal(inputStream, this.tempByteArray, (int) this.elementContentSize);
                       if (intResult != 0) {
                                    return intResult;
                                }
                       long intValue = getTempByteArrayValue((int) this.elementContentSize, false);
                       this.eventHandler.onIntegerElement(this.elementId, intValue);
                       prepareForNextElement();
                       return 0;
                            case 3:
                                if (this.elementContentSize > 2147483647L) {
                           throw new IllegalStateException("String element size " + this.elementContentSize + " is larger than MAX_INT");
                                }
                       if (this.stringBytes == null) {
                           this.stringBytes = new byte[(int) this.elementContentSize];
                                }
                       int stringResult = readBytesInternal(inputStream, this.stringBytes, (int) this.elementContentSize);
                       if (stringResult != 0) {
                                    return stringResult;
                                }
                       String stringValue = new String(this.stringBytes, Charset.forName(IoUtils.UTF_8));
                       this.stringBytes = null;
                       this.eventHandler.onStringElement(this.elementId, stringValue);
                       prepareForNextElement();
                       return 0;
                            case 4:
                                if (this.elementContentSize > 2147483647L) {
                           throw new IllegalStateException("Binary element size " + this.elementContentSize + " is larger than MAX_INT");
                                }
                                if (inputStream.getAvailableByteCount() < this.elementContentSize) {
                           return 1;
                                }
                       int binaryHeaderSize = (int) (this.bytesRead - this.elementOffset);
                       boolean consumed = this.eventHandler.onBinaryElement(this.elementId, this.elementOffset, binaryHeaderSize, (int) this.elementContentSize, inputStream);
                       if (consumed) {
                           long expectedBytesRead = this.elementOffset + ((long) binaryHeaderSize) + this.elementContentSize;
                                    if (expectedBytesRead != this.bytesRead) {
                               throw new IllegalStateException("Incorrect total bytes read. Expected " + expectedBytesRead + " but actually " + this.bytesRead);
                                    }
                           prepareForNextElement();
                                }
                       return 0;
                            case 5:
                                if (this.elementContentSize != 4 && this.elementContentSize != 8) {
                           throw new IllegalStateException("Invalid float size " + this.elementContentSize);
                                }
                       int floatResult = readBytesInternal(inputStream, this.tempByteArray, (int) this.elementContentSize);
                       if (floatResult != 0) {
                                    return floatResult;
                                }
                       long valueBits = getTempByteArrayValue((int) this.elementContentSize, false);
                                if (this.elementContentSize == 4) {
                           floatValue = Float.intBitsToFloat((int) valueBits);
                                } else {
                           floatValue = Double.longBitsToDouble(valueBits);
                                }
                       this.eventHandler.onFloatElement(this.elementId, floatValue);
                       prepareForNextElement();
                       return 0;
                            default:
                       throw new IllegalStateException("Invalid element type " + type);
                        }
                    }
                }

                @Override 
       public long getBytesRead() {
           return this.bytesRead;
                }

                @Override 
       public void reset() {
           prepareForNextElement();
           this.masterElementsStack.clear();
           this.bytesRead = 0L;
                }

                @Override 
       public long readVarint(NonBlockingInputStream inputStream) {
           this.varintBytesState = 0;
           int result = readVarintBytes(inputStream);
           if (result != 0) {
               throw new IllegalStateException("Couldn't read varint");
                    }
           return getTempByteArrayValue(this.varintBytesLength, true);
                }

                @Override 
       public void readBytes(NonBlockingInputStream inputStream, ByteBuffer byteBuffer, int totalBytes) {
           this.bytesState = 0;
           int result = readBytesInternal(inputStream, byteBuffer, totalBytes);
           if (result != 0) {
               throw new IllegalStateException("Couldn't read bytes into buffer");
                    }
                }

                @Override 
       public void readBytes(NonBlockingInputStream inputStream, byte[] byteArray, int totalBytes) {
           this.bytesState = 0;
           int result = readBytesInternal(inputStream, byteArray, totalBytes);
           if (result != 0) {
               throw new IllegalStateException("Couldn't read bytes into array");
                    }
                }

                @Override 
       public void skipBytes(NonBlockingInputStream inputStream, int totalBytes) {
           this.bytesState = 0;
           int result = skipBytesInternal(inputStream, totalBytes);
           if (result != 0) {
               throw new IllegalStateException("Couldn't skip bytes");
                    }
                }

       private void prepareForNextElement() {
           this.state = 0;
           this.elementIdState = 0;
           this.elementContentSizeState = 0;
           this.elementOffset = this.bytesRead;
                }

       private int readElementId(NonBlockingInputStream inputStream) {
           if (this.elementIdState == 2) {
                        return 0;
                    }
           if (this.elementIdState == 0) {
               this.varintBytesState = 0;
               this.elementIdState = 1;
                    }
           int result = readVarintBytes(inputStream);
           if (result == 0) {
               this.elementId = (int) getTempByteArrayValue(this.varintBytesLength, false);
               this.elementIdState = 2;
                        return 0;
                    }
           return result;
                }

       private int readElementContentSize(NonBlockingInputStream inputStream) {
           if (this.elementContentSizeState == 2) {
                        return 0;
                    }
           if (this.elementContentSizeState == 0) {
               this.varintBytesState = 0;
               this.elementContentSizeState = 1;
                    }
           int result = readVarintBytes(inputStream);
           if (result == 0) {
               this.elementContentSize = getTempByteArrayValue(this.varintBytesLength, true);
               this.elementContentSizeState = 2;
                        return 0;
                    }
           return result;
                }

       private int readVarintBytes(NonBlockingInputStream inputStream) {
           if (this.varintBytesState == 2) {
                        return 0;
                    }
           if (this.varintBytesState == 0) {
               this.bytesState = 0;
               int result = readBytesInternal(inputStream, this.tempByteArray, 1);
               if (result == 0) {
                   this.varintBytesState = 1;
                   int firstByte = this.tempByteArray[0] & Constants.UNKNOWN;
                   this.varintBytesLength = -1;
                            for (int i = 0; i < VARINT_LENGTH_MASKS.length; i++) {
                       if ((VARINT_LENGTH_MASKS[i] & firstByte) != 0) {
                           this.varintBytesLength = i + 1;
                                    break;
                                }
                            }
                   if (this.varintBytesLength == -1) {
                       throw new IllegalStateException("No valid varint length mask found at bytesRead = " + this.bytesRead);
                            }
                        } else {
                   return result;
                        }
                    }
           int result2 = readBytesInternal(inputStream, this.tempByteArray, this.varintBytesLength);
           if (result2 == 0) {
                        return 0;
                    }
           return result2;
                }

       private int readBytesInternal(NonBlockingInputStream inputStream, ByteBuffer byteBuffer, int totalBytes) {
           if (this.bytesState == 0 && totalBytes > byteBuffer.capacity()) {
               throw new IllegalArgumentException("Byte buffer not large enough");
                    }
           if (this.bytesState >= totalBytes) {
               return 0;
                    }
           int remainingBytes = totalBytes - this.bytesState;
           int additionalBytesRead = inputStream.read(byteBuffer, remainingBytes);
           return updateBytesState(additionalBytesRead, totalBytes);
                }

       private int readBytesInternal(NonBlockingInputStream inputStream, byte[] byteArray, int totalBytes) {
           if (this.bytesState == 0 && totalBytes > byteArray.length) {
               throw new IllegalArgumentException("Byte array not large enough");
                    }
           if (this.bytesState >= totalBytes) {
               return 0;
                    }
           int remainingBytes = totalBytes - this.bytesState;
           int additionalBytesRead = inputStream.read(byteArray, this.bytesState, remainingBytes);
           return updateBytesState(additionalBytesRead, totalBytes);
                }

       private int skipBytesInternal(NonBlockingInputStream inputStream, int totalBytes) {
           if (this.bytesState >= totalBytes) {
               return 0;
                    }
           int remainingBytes = totalBytes - this.bytesState;
           int additionalBytesRead = inputStream.skip(remainingBytes);
           return updateBytesState(additionalBytesRead, totalBytes);
                }

       private int updateBytesState(int additionalBytesRead, int totalBytes) {
           if (additionalBytesRead == -1) {
               return 2;
                    }
           this.bytesState += additionalBytesRead;
           this.bytesRead += (long) additionalBytesRead;
           if (this.bytesState < totalBytes) {
               return 1;
                    }
           return 0;
                }

       private long getTempByteArrayValue(int byteLength, boolean removeLengthMask) {
           if (removeLengthMask) {
               byte[] bArr = this.tempByteArray;
                        bArr[0] = (byte) (bArr[0] & (VARINT_LENGTH_MASKS[this.varintBytesLength - 1] ^ (-1)));
                    }
           long varint = 0;
                    for (int i = 0; i < byteLength; i++) {
               varint = (varint << 8) | ((long) (this.tempByteArray[i] & Constants.UNKNOWN));
                    }
           return varint;
                }

       private static final class MasterElement {
                    private final long elementEndOffsetBytes;
                    private final int elementId;

            MasterElement(int x0, long x1, Object x2) {
               this(x0, x1);
                    }

           static  long access$000(MasterElement x0) {
               return x0.elementEndOffsetBytes;
                    }

           static  int access$100(MasterElement x0) {
               return x0.elementId;
                    }

           private MasterElement(int elementId, long elementEndOffsetBytes) {
               this.elementId = elementId;
               this.elementEndOffsetBytes = elementEndOffsetBytes;
                    }
                }
            }
