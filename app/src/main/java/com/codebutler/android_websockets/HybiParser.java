            package com.codebutler.android_websockets;

            import android.util.Log;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.flurry.android.Constants;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.ByteArrayOutputStream;
            import java.io.DataInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.UnsupportedEncodingException;
            import java.util.Arrays;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 92 */    public class HybiParser {
                private static final int BYTE = 255;
                private static final int FIN = 128;
                private static final int LENGTH = 127;
                private static final int MASK = 128;
                private static final int MODE_BINARY = 2;
                private static final int MODE_TEXT = 1;
                private static final int OPCODE = 15;
                private static final int OP_BINARY = 2;
                private static final int OP_CLOSE = 8;
                private static final int OP_CONTINUATION = 0;
                private static final int OP_PING = 9;
                private static final int OP_PONG = 10;
                private static final int OP_TEXT = 1;
                private static final int RSV1 = 64;
                private static final int RSV2 = 32;
                private static final int RSV3 = 16;
                private static final String TAG = "HybiParser";
                private WebSocketClient mClient;
                private boolean mFinal;
                private int mLength;
                private int mLengthSize;
                private boolean mMasked;
                private int mMode;
                private int mOpcode;
                private int mStage;
                private static final List<Integer> OPCODES = Arrays.asList(0, 1, 2, 8, 9, 10);
                private static final List<Integer> FRAGMENTED_OPCODES = Arrays.asList(0, 1, 2);
                private boolean mMasking = true;
                private byte[] mMask = new byte[0];
                private byte[] mPayload = new byte[0];
                private boolean mClosed = false;
                private ByteArrayOutputStream mBuffer = new ByteArrayOutputStream();

/* 93 */        public HybiParser(WebSocketClient client) {
/* 95 */            this.mClient = client;
                }

/* 98 */        private static byte[] mask(byte[] payload, byte[] mask, int offset) {
/* 99 */            if (mask.length != 0) {
                        for (int i = 0; i < payload.length - offset; i++) {
/* 102 */                   payload[offset + i] = (byte) (payload[offset + i] ^ mask[i % 4]);
                        }
                    }
/* 104 */           return payload;
                }

/* 108 */       public void start(HappyDataInputStream stream) throws IOException {
/* 109 */           while (stream.available() != -1) {
                        switch (this.mStage) {
                            case 0:
/* 112 */                       parseOpcode(stream.readByte());
                                break;
                            case 1:
/* 115 */                       parseLength(stream.readByte());
                                break;
                            case 2:
/* 118 */                       parseExtendedLength(stream.readBytes(this.mLengthSize));
                                break;
                            case 3:
/* 121 */                       this.mMask = stream.readBytes(4);
/* 122 */                       this.mStage = 4;
                                break;
                            case 4:
/* 125 */                       this.mPayload = stream.readBytes(this.mLength);
/* 126 */                       emitFrame();
/* 127 */                       this.mStage = 0;
                                break;
                        }
                    }
/* 131 */           this.mClient.getListener().onDisconnect(0, "EOF");
                }

/* 134 */       private void parseOpcode(byte data) throws ProtocolError {
/* 135 */           boolean rsv1 = (data & 64) == 64;
/* 136 */           boolean rsv2 = (data & 32) == 32;
/* 137 */           boolean rsv3 = (data & 16) == 16;
/* 139 */           if (rsv1 || rsv2 || rsv3) {
/* 140 */               throw new ProtocolError("RSV not zero");
                    }
/* 143 */           this.mFinal = (data & 128) == 128;
/* 144 */           this.mOpcode = data & 15;
/* 145 */           this.mMask = new byte[0];
/* 146 */           this.mPayload = new byte[0];
/* 148 */           if (!OPCODES.contains(Integer.valueOf(this.mOpcode))) {
/* 149 */               throw new ProtocolError("Bad opcode");
                    }
/* 152 */           if (!FRAGMENTED_OPCODES.contains(Integer.valueOf(this.mOpcode)) && !this.mFinal) {
/* 153 */               throw new ProtocolError("Expected non-final packet");
                    }
/* 156 */           this.mStage = 1;
                }

/* 159 */       private void parseLength(byte data) {
/* 160 */           this.mMasked = (data & 128) == 128;
/* 161 */           this.mLength = data & 127;
/* 163 */           if (this.mLength >= 0 && this.mLength <= 125) {
/* 164 */               this.mStage = this.mMasked ? 3 : 4;
                    } else {
/* 166 */               this.mLengthSize = this.mLength == 126 ? 2 : 8;
/* 167 */               this.mStage = 2;
                    }
                }

/* 171 */       private void parseExtendedLength(byte[] buffer) throws ProtocolError {
/* 172 */           this.mLength = getInteger(buffer);
/* 173 */           this.mStage = this.mMasked ? 3 : 4;
                }

/* 176 */       public byte[] frame(String data) {
/* 177 */           return frame(data, 1, -1);
                }

/* 180 */       public byte[] frame(byte[] data) {
/* 181 */           return frame(data, 2, -1);
                }

/* 184 */       private byte[] frame(byte[] data, int opcode, int errorCode) {
/* 185 */           return frame((Object) data, opcode, errorCode);
                }

/* 188 */       private byte[] frame(String data, int opcode, int errorCode) {
/* 189 */           return frame((Object) data, opcode, errorCode);
                }

/* 192 */       private byte[] frame(Object data, int opcode, int errorCode) {
                    int header;
/* 193 */           if (this.mClosed) {
                        return null;
                    }
/* 195 */           Log.d(TAG, "Creating frame for: " + data + " op: " + opcode + " err: " + errorCode);
/* 197 */           byte[] buffer = data instanceof String ? decode((String) data) : (byte[]) data;
/* 198 */           int insert = errorCode > 0 ? 2 : 0;
/* 199 */           int length = buffer.length + insert;
/* 200 */           if (length <= 125) {
                        header = 2;
                    } else {
/* 200 */               header = length <= 65535 ? 4 : 10;
                    }
/* 201 */           int offset = header + (this.mMasking ? 4 : 0);
/* 202 */           int masked = this.mMasking ? 128 : 0;
/* 203 */           byte[] frame = new byte[length + offset];
/* 205 */           frame[0] = (byte) (((byte) opcode) | (-128));
/* 207 */           if (length <= 125) {
/* 208 */               frame[1] = (byte) (masked | length);
                    } else if (length <= 65535) {
/* 210 */               frame[1] = (byte) (masked | 126);
/* 211 */               frame[2] = (byte) Math.floor(length / 256);
/* 212 */               frame[3] = (byte) (length & 255);
                    } else {
/* 214 */               frame[1] = (byte) (masked | 127);
/* 215 */               frame[2] = (byte) (((int) Math.floor(((double) length) / Math.pow(2.0d, 56.0d))) & 255);
/* 216 */               frame[3] = (byte) (((int) Math.floor(((double) length) / Math.pow(2.0d, 48.0d))) & 255);
/* 217 */               frame[4] = (byte) (((int) Math.floor(((double) length) / Math.pow(2.0d, 40.0d))) & 255);
/* 218 */               frame[5] = (byte) (((int) Math.floor(((double) length) / Math.pow(2.0d, 32.0d))) & 255);
/* 219 */               frame[6] = (byte) (((int) Math.floor(((double) length) / Math.pow(2.0d, 24.0d))) & 255);
/* 220 */               frame[7] = (byte) (((int) Math.floor(((double) length) / Math.pow(2.0d, 16.0d))) & 255);
/* 221 */               frame[8] = (byte) (((int) Math.floor(((double) length) / Math.pow(2.0d, 8.0d))) & 255);
/* 222 */               frame[9] = (byte) (length & 255);
                    }
/* 225 */           if (errorCode > 0) {
/* 226 */               frame[offset] = (byte) (((int) Math.floor(errorCode / 256)) & 255);
/* 227 */               frame[offset + 1] = (byte) (errorCode & 255);
                    }
/* 229 */           System.arraycopy(buffer, 0, frame, offset + insert, buffer.length);
/* 231 */           if (this.mMasking) {
/* 232 */               byte[] mask = {(byte) Math.floor(Math.random() * 256.0d), (byte) Math.floor(Math.random() * 256.0d), (byte) Math.floor(Math.random() * 256.0d), (byte) Math.floor(Math.random() * 256.0d)};
/* 236 */               System.arraycopy(mask, 0, frame, header, mask.length);
/* 237 */               mask(frame, mask, offset);
/* 240 */               return frame;
                    }
/* 240 */           return frame;
                }

/* 243 */       public void ping(String message) {
/* 244 */           this.mClient.send(frame(message, 9, -1));
                }

/* 247 */       public void close(int code, String reason) {
/* 248 */           if (!this.mClosed) {
/* 249 */               this.mClient.send(frame(reason, 8, code));
/* 250 */               this.mClosed = true;
                    }
                }

/* 253 */       private void emitFrame() throws IOException {
/* 254 */           byte[] payload = mask(this.mPayload, this.mMask, 0);
/* 255 */           int opcode = this.mOpcode;
/* 257 */           if (opcode == 0) {
/* 258 */               if (this.mMode == 0) {
/* 259 */                   throw new ProtocolError("Mode was not set.");
                        }
/* 261 */               this.mBuffer.write(payload);
/* 262 */               if (this.mFinal) {
/* 263 */                   byte[] message = this.mBuffer.toByteArray();
/* 264 */                   if (this.mMode == 1) {
/* 265 */                       this.mClient.getListener().onMessage(encode(message));
                            } else {
/* 267 */                       this.mClient.getListener().onMessage(message);
                            }
/* 269 */                   reset();
/* 305 */                   return;
                        }
/* 305 */               return;
                    }
/* 272 */           if (opcode == 1) {
/* 273 */               if (this.mFinal) {
/* 274 */                   String messageText = encode(payload);
/* 275 */                   this.mClient.getListener().onMessage(messageText);
/* 305 */                   return;
                        } else {
/* 277 */                   this.mMode = 1;
/* 278 */                   this.mBuffer.write(payload);
/* 305 */                   return;
                        }
                    }
/* 281 */           if (opcode == 2) {
/* 282 */               if (this.mFinal) {
/* 283 */                   this.mClient.getListener().onMessage(payload);
/* 305 */                   return;
                        } else {
/* 285 */                   this.mMode = 2;
/* 286 */                   this.mBuffer.write(payload);
/* 305 */                   return;
                        }
                    }
/* 289 */           if (opcode == 8) {
/* 290 */               int code = payload.length >= 2 ? (payload[0] * Constants.FEMALE) + payload[1] : 0;
/* 291 */               String reason = payload.length > 2 ? encode(slice(payload, 2)) : null;
/* 292 */               Log.d(TAG, "Got close op! " + code + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + reason);
/* 293 */               this.mClient.getListener().onDisconnect(code, reason);
/* 305 */               return;
                    }
/* 295 */           if (opcode == 9) {
/* 296 */               if (payload.length > 125) {
                            throw new ProtocolError("Ping payload too large");
                        }
/* 297 */               Log.d(TAG, "Sending pong!!");
/* 298 */               this.mClient.sendFrame(frame(payload, 10, -1));
/* 305 */               return;
                    }
/* 300 */           if (opcode == 10) {
/* 303 */               Log.d(TAG, "Got pong! " + encode(payload));
                    }
                }

/* 307 */       private void reset() {
/* 308 */           this.mMode = 0;
/* 309 */           this.mBuffer.reset();
                }

/* 313 */       private String encode(byte[] buffer) {
                    try {
/* 314 */               return new String(buffer, IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
/* 316 */               throw new RuntimeException(e);
                    }
                }

/* 321 */       private byte[] decode(String string) {
                    try {
/* 322 */               return string.getBytes(IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
/* 324 */               throw new RuntimeException(e);
                    }
                }

/* 328 */       private int getInteger(byte[] bytes) throws ProtocolError {
/* 329 */           long i = byteArrayToLong(bytes, 0, bytes.length);
                    if (i < 0 || i > 2147483647L) {
/* 331 */               throw new ProtocolError("Bad integer: " + i);
                    }
/* 333 */           return (int) i;
                }

/* 336 */       private byte[] slice(byte[] array, int start) {
/* 337 */           return Arrays.copyOfRange(array, start, array.length);
                }

/* 340 */       public static class ProtocolError extends IOException {
/* 341 */           public ProtocolError(String detailMessage) {
/* 342 */               super(detailMessage);
                    }
                }

/* 346 */       private static long byteArrayToLong(byte[] b, int offset, int length) {
/* 347 */           if (b.length < length) {
/* 348 */               throw new IllegalArgumentException("length must be less than or equal to b.length");
                    }
/* 350 */           long value = 0;
                    for (int i = 0; i < length; i++) {
/* 352 */               int shift = ((length - 1) - i) * 8;
/* 353 */               value += (long) ((b[i + offset] & 255) << shift);
                    }
/* 355 */           return value;
                }

/* 358 */       public static class HappyDataInputStream extends DataInputStream {
/* 359 */           public HappyDataInputStream(InputStream in) {
/* 360 */               super(in);
                    }

/* 363 */           public byte[] readBytes(int length) throws IOException {
/* 364 */               byte[] buffer = new byte[length];
/* 365 */               readFully(buffer);
/* 366 */               return buffer;
                    }
                }
            }
