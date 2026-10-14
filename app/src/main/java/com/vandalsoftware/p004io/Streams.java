            package com.vandalsoftware.p004io;

            import com.flurry.android.Constants;
            import java.io.ByteArrayOutputStream;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.io.Reader;
            import java.io.StringWriter;
            import java.io.Writer;
            import java.util.concurrent.atomic.AtomicReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class Streams {
                private static AtomicReference<byte[]> skipBuffer = new AtomicReference<>();

/* 33 */        private Streams() {
                }

/* 42 */        public static int readSingleByte(InputStream in) throws IOException {
/* 43 */            byte[] buffer = new byte[1];
/* 44 */            int result = in.read(buffer, 0, 1);
/* 45 */            if (result != -1) {
                        return buffer[0] & Constants.UNKNOWN;
                    }
                    return -1;
                }

/* 53 */        public static void writeSingleByte(OutputStream out, int b) throws IOException {
/* 54 */            byte[] buffer = {(byte) (b & 255)};
/* 56 */            out.write(buffer);
                }

/* 62 */        public static void readFully(InputStream in, byte[] dst) throws IOException {
/* 63 */            readFully(in, dst, 0, dst.length);
                }

/* 72 */        public static void readFully(InputStream in, byte[] dst, int offset, int byteCount) throws IOException {
/* 73 */            if (byteCount != 0) {
/* 76 */                if (in == null) {
/* 77 */                    throw new NullPointerException("in == null");
                        }
/* 79 */                if (dst == null) {
/* 80 */                    throw new NullPointerException("dst == null");
                        }
/* 82 */                checkOffsetAndCount(dst.length, offset, byteCount);
/* 83 */                while (byteCount > 0) {
/* 84 */                    int bytesRead = in.read(dst, offset, byteCount);
/* 85 */                    if (bytesRead < 0) {
/* 86 */                        throw new EOFException();
                            }
/* 88 */                    offset += bytesRead;
/* 89 */                    byteCount -= bytesRead;
                        }
                    }
                }

/* 97 */        private static void checkOffsetAndCount(int length, int offset, int byteCount) throws EOFException {
/* 98 */            if (offset + byteCount > length) {
/* 99 */                throw new EOFException();
                    }
                }

/* 107 */       public static byte[] readFully(InputStream in) throws IOException {
                    try {
/* 108 */               byte[] fullyNoClose = readFullyNoClose(in);
/* 110 */               in.close();
                        return fullyNoClose;
                    } catch (Throwable th) {
                        in.close();
                        throw th;
                    }
                }

/* 117 */       public static byte[] readFullyNoClose(InputStream in) throws IOException {
/* 118 */           ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/* 119 */           byte[] buffer = new byte[1024];
                    while (true) {
/* 121 */               int count = in.read(buffer);
                        if (count != -1) {
/* 122 */                   bytes.write(buffer, 0, count);
                        } else {
/* 124 */                   return bytes.toByteArray();
                        }
                    }
                }

/* 131 */       public static String readFully(Reader reader) throws IOException {
                    try {
/* 132 */               StringWriter writer = new StringWriter();
/* 133 */               char[] buffer = new char[1024];
                        while (true) {
/* 135 */                   int count = reader.read(buffer);
                            if (count != -1) {
/* 136 */                       writer.write(buffer, 0, count);
                            } else {
/* 138 */                       String string = writer.toString();
/* 140 */                       reader.close();
                                return string;
                            }
                        }
                    } catch (Throwable th) {
                        reader.close();
                        throw th;
                    }
                }

/* 144 */       public static int skipAll(InputStream in) throws IOException {
/* 145 */           int skipped = 0;
/* 148 */           do {
/* 147 */               skipped = (int) (((long) skipped) + in.skip(Long.MAX_VALUE));
/* 148 */           } while (in.read() != -1);
/* 149 */           return skipped;
                }

/* 163 */       public static long skipByReading(InputStream in, long byteCount) throws IOException {
                    int toRead;
                    int read;
/* 164 */           byte[] buffer = skipBuffer.getAndSet(null);
/* 165 */           if (buffer == null) {
/* 166 */               buffer = new byte[4096];
                    }
/* 169 */           long skipped = 0;
/* 173 */           while (skipped < byteCount && (read = in.read(buffer, 0, (toRead = (int) Math.min(byteCount - skipped, buffer.length)))) != -1) {
/* 176 */               skipped += (long) read;
/* 177 */               if (read < toRead) {
                            break;
                        }
                    }
/* 183 */           skipBuffer.set(buffer);
/* 185 */           return skipped;
                }

/* 192 */       public static int copy(InputStream in, OutputStream out) throws IOException {
/* 193 */           int total = 0;
/* 194 */           byte[] buffer = new byte[8192];
                    while (true) {
/* 196 */               int c = in.read(buffer);
                        if (c != -1) {
/* 197 */                   total += c;
/* 198 */                   out.write(buffer, 0, c);
                        } else {
/* 200 */                   return total;
                        }
                    }
                }

/* 210 */       public static String readAsciiLine(InputStream in) throws IOException {
/* 211 */           StringBuilder result = new StringBuilder(80);
                    while (true) {
/* 213 */               int c = in.read();
/* 214 */               if (c == -1) {
/* 215 */                   throw new EOFException();
                        }
/* 216 */               if (c != 10) {
/* 220 */                   result.append((char) c);
                        } else {
/* 222 */                   int length = result.length();
/* 223 */                   if (length > 0 && result.charAt(length - 1) == '\r') {
/* 224 */                       result.setLength(length - 1);
                            }
/* 226 */                   return result.toString();
                        }
                    }
                }

/* 236 */       public static void writeStringTo(OutputStream outputStream, String s) throws Throwable {
/* 237 */           Writer writer = null;
                    try {
/* 239 */               Writer writer2 = new OutputStreamWriter(outputStream, IoUtils.UTF_8);
                        try {
/* 240 */                   writer2.write(s);
/* 242 */                   IoUtils.closeQuietly(writer2);
                        } catch (Throwable th) {
                            th = th;
                            writer = writer2;
                            IoUtils.closeQuietly(writer);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 253 */       public static String readStringFrom(InputStream in) throws IOException {
/* 254 */           return readFully(new InputStreamReader(in, IoUtils.UTF_8));
                }
            }
