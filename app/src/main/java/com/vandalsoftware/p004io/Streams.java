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
            
            
    public final class Streams {
                private static AtomicReference<byte[]> skipBuffer = new AtomicReference<>();

        private Streams() {
                }

        public static int readSingleByte(InputStream in) throws IOException {
            byte[] buffer = new byte[1];
            int result = in.read(buffer, 0, 1);
            if (result != -1) {
                        return buffer[0] & Constants.UNKNOWN;
                    }
                    return -1;
                }

        public static void writeSingleByte(OutputStream out, int b) throws IOException {
            byte[] buffer = {(byte) (b & 255)};
            out.write(buffer);
                }

        public static void readFully(InputStream in, byte[] dst) throws IOException {
            readFully(in, dst, 0, dst.length);
                }

        public static void readFully(InputStream in, byte[] dst, int offset, int byteCount) throws IOException {
            if (byteCount != 0) {
                if (in == null) {
                    throw new NullPointerException("in == null");
                        }
                if (dst == null) {
                    throw new NullPointerException("dst == null");
                        }
                checkOffsetAndCount(dst.length, offset, byteCount);
                while (byteCount > 0) {
                    int bytesRead = in.read(dst, offset, byteCount);
                    if (bytesRead < 0) {
                        throw new EOFException();
                            }
                    offset += bytesRead;
                    byteCount -= bytesRead;
                        }
                    }
                }

        private static void checkOffsetAndCount(int length, int offset, int byteCount) throws EOFException {
            if (offset + byteCount > length) {
                throw new EOFException();
                    }
                }

       public static byte[] readFully(InputStream in) throws IOException {
                    try {
               byte[] fullyNoClose = readFullyNoClose(in);
               in.close();
                        return fullyNoClose;
                    } catch (Throwable th) {
                        in.close();
                        throw th;
                    }
                }

       public static byte[] readFullyNoClose(InputStream in) throws IOException {
           ByteArrayOutputStream bytes = new ByteArrayOutputStream();
           byte[] buffer = new byte[1024];
                    while (true) {
               int count = in.read(buffer);
                        if (count != -1) {
                   bytes.write(buffer, 0, count);
                        } else {
                   return bytes.toByteArray();
                        }
                    }
                }

       public static String readFully(Reader reader) throws IOException {
                    try {
               StringWriter writer = new StringWriter();
               char[] buffer = new char[1024];
                        while (true) {
                   int count = reader.read(buffer);
                            if (count != -1) {
                       writer.write(buffer, 0, count);
                            } else {
                       String string = writer.toString();
                       reader.close();
                                return string;
                            }
                        }
                    } catch (Throwable th) {
                        reader.close();
                        throw th;
                    }
                }

       public static int skipAll(InputStream in) throws IOException {
           int skipped = 0;
           do {
               skipped = (int) (((long) skipped) + in.skip(Long.MAX_VALUE));
           } while (in.read() != -1);
           return skipped;
                }

       public static long skipByReading(InputStream in, long byteCount) throws IOException {
                    int toRead;
                    int read;
           byte[] buffer = skipBuffer.getAndSet(null);
           if (buffer == null) {
               buffer = new byte[4096];
                    }
           long skipped = 0;
           while (skipped < byteCount && (read = in.read(buffer, 0, (toRead = (int) Math.min(byteCount - skipped, buffer.length)))) != -1) {
               skipped += (long) read;
               if (read < toRead) {
                            break;
                        }
                    }
           skipBuffer.set(buffer);
           return skipped;
                }

       public static int copy(InputStream in, OutputStream out) throws IOException {
           int total = 0;
           byte[] buffer = new byte[8192];
                    while (true) {
               int c = in.read(buffer);
                        if (c != -1) {
                   total += c;
                   out.write(buffer, 0, c);
                        } else {
                   return total;
                        }
                    }
                }

       public static String readAsciiLine(InputStream in) throws IOException {
           StringBuilder result = new StringBuilder(80);
                    while (true) {
               int c = in.read();
               if (c == -1) {
                   throw new EOFException();
                        }
               if (c != 10) {
                   result.append((char) c);
                        } else {
                   int length = result.length();
                   if (length > 0 && result.charAt(length - 1) == '\r') {
                       result.setLength(length - 1);
                            }
                   return result.toString();
                        }
                    }
                }

       public static void writeStringTo(OutputStream outputStream, String s) throws Throwable {
           Writer writer = null;
                    try {
               Writer writer2 = new OutputStreamWriter(outputStream, IoUtils.UTF_8);
                        try {
                   writer2.write(s);
                   IoUtils.closeQuietly(writer2);
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

       public static String readStringFrom(InputStream in) throws IOException {
           return readFully(new InputStreamReader(in, IoUtils.UTF_8));
                }
            }
