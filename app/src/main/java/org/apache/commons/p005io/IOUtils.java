            package org.apache.commons.p005io;

            import java.io.BufferedInputStream;
            import java.io.BufferedReader;
            import java.io.ByteArrayInputStream;
            import java.io.CharArrayWriter;
            import java.io.Closeable;
            import java.io.EOFException;
            import java.io.File;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.io.PrintWriter;
            import java.io.Reader;
            import java.io.Writer;
            import java.net.HttpURLConnection;
            import java.net.ServerSocket;
            import java.net.Socket;
            import java.net.URI;
            import java.net.URL;
            import java.net.URLConnection;
            import java.nio.channels.Selector;
            import java.nio.charset.Charset;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.List;
            import org.apache.commons.p005io.output.ByteArrayOutputStream;
            import org.apache.commons.p005io.output.StringBuilderWriter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 99 */    public class IOUtils {
                private static final int DEFAULT_BUFFER_SIZE = 4096;
                public static final char DIR_SEPARATOR = File.separatorChar;
                public static final char DIR_SEPARATOR_UNIX = '/';
                public static final char DIR_SEPARATOR_WINDOWS = '\\';
                private static final int EOF = -1;
                public static final String LINE_SEPARATOR;
                public static final String LINE_SEPARATOR_UNIX = "\n";
                public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
                private static final int SKIP_BUFFER_SIZE = 2048;
                private static byte[] SKIP_BYTE_BUFFER;
                private static char[] SKIP_CHAR_BUFFER;

                static {
/* 117 */           StringBuilderWriter buf = new StringBuilderWriter(4);
/* 118 */           PrintWriter out = new PrintWriter(buf);
/* 119 */           out.println();
/* 120 */           LINE_SEPARATOR = buf.toString();
/* 121 */           out.close();
                }

/* 163 */       public static void close(URLConnection conn) {
/* 164 */           if (conn instanceof HttpURLConnection) {
/* 165 */               ((HttpURLConnection) conn).disconnect();
                    }
                }

/* 192 */       public static void closeQuietly(Reader input) {
/* 193 */           closeQuietly((Closeable) input);
                }

/* 218 */       public static void closeQuietly(Writer output) {
/* 219 */           closeQuietly((Closeable) output);
                }

/* 245 */       public static void closeQuietly(InputStream input) {
/* 246 */           closeQuietly((Closeable) input);
                }

/* 273 */       public static void closeQuietly(OutputStream output) {
/* 274 */           closeQuietly((Closeable) output);
                }

/* 301 */       public static void closeQuietly(Closeable closeable) {
/* 302 */           if (closeable != null) {
                        try {
/* 303 */                   closeable.close();
                        } catch (IOException e) {
                        }
                    }
                }

/* 333 */       public static void closeQuietly(Socket sock) {
/* 334 */           if (sock != null) {
                        try {
/* 336 */                   sock.close();
                        } catch (IOException e) {
                        }
                    }
                }

/* 366 */       public static void closeQuietly(Selector selector) {
/* 367 */           if (selector != null) {
                        try {
/* 369 */                   selector.close();
                        } catch (IOException e) {
                        }
                    }
                }

/* 399 */       public static void closeQuietly(ServerSocket sock) {
/* 400 */           if (sock != null) {
                        try {
/* 402 */                   sock.close();
                        } catch (IOException e) {
                        }
                    }
                }

/* 430 */       public static InputStream toBufferedInputStream(InputStream input) throws IOException {
/* 431 */           return ByteArrayOutputStream.toBufferedInputStream(input);
                }

/* 443 */       public static BufferedReader toBufferedReader(Reader reader) {
                    return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
                }

/* 460 */       public static byte[] toByteArray(InputStream input) throws IOException {
/* 461 */           ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 462 */           copy(input, output);
/* 463 */           return output.toByteArray();
                }

/* 483 */       public static byte[] toByteArray(InputStream input, long size) throws IOException {
                    if (size > 2147483647L) {
/* 485 */               throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
                    }
/* 488 */           return toByteArray(input, (int) size);
                }

/* 503 */       public static byte[] toByteArray(InputStream input, int size) throws IOException {
/* 504 */           if (size < 0) {
/* 505 */               throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
                    }
/* 508 */           if (size == 0) {
/* 509 */               return new byte[0];
                    }
/* 512 */           byte[] data = new byte[size];
/* 513 */           int offset = 0;
/* 516 */           while (offset < size) {
                        int readed = input.read(data, offset, size - offset);
                        if (readed == -1) {
                            break;
                        }
/* 517 */               offset += readed;
                    }
/* 520 */           if (offset != size) {
/* 521 */               throw new IOException("Unexpected readed size. current: " + offset + ", excepted: " + size);
                    }
/* 524 */           return data;
                }

/* 539 */       public static byte[] toByteArray(Reader input) throws IOException {
/* 540 */           return toByteArray(input, Charset.defaultCharset());
                }

/* 557 */       public static byte[] toByteArray(Reader input, Charset encoding) throws IOException {
/* 558 */           ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 559 */           copy(input, output, encoding);
/* 560 */           return output.toByteArray();
                }

/* 583 */       public static byte[] toByteArray(Reader input, String encoding) throws IOException {
/* 584 */           return toByteArray(input, Charsets.toCharset(encoding));
                }

                @Deprecated
/* 600 */       public static byte[] toByteArray(String input) throws IOException {
/* 601 */           return input.getBytes();
                }

/* 616 */       public static byte[] toByteArray(URI uri) throws IOException {
/* 617 */           return toByteArray(uri.toURL());
                }

/* 632 */       public static byte[] toByteArray(URL url) throws IOException {
/* 633 */           URLConnection conn = url.openConnection();
                    try {
/* 635 */               byte[] byteArray = toByteArray(conn);
/* 637 */               close(conn);
                        return byteArray;
                    } catch (Throwable th) {
                        close(conn);
                        throw th;
                    }
                }

/* 653 */       public static byte[] toByteArray(URLConnection urlConn) throws IOException {
/* 654 */           InputStream inputStream = urlConn.getInputStream();
                    try {
/* 656 */               byte[] byteArray = toByteArray(inputStream);
/* 658 */               inputStream.close();
                        return byteArray;
                    } catch (Throwable th) {
                        inputStream.close();
                        throw th;
                    }
                }

/* 677 */       public static char[] toCharArray(InputStream is) throws IOException {
/* 678 */           return toCharArray(is, Charset.defaultCharset());
                }

/* 696 */       public static char[] toCharArray(InputStream is, Charset encoding) throws IOException {
/* 697 */           CharArrayWriter output = new CharArrayWriter();
/* 698 */           copy(is, output, encoding);
/* 699 */           return output.toCharArray();
                }

/* 722 */       public static char[] toCharArray(InputStream is, String encoding) throws IOException {
/* 723 */           return toCharArray(is, Charsets.toCharset(encoding));
                }

/* 738 */       public static char[] toCharArray(Reader input) throws IOException {
/* 739 */           CharArrayWriter sw = new CharArrayWriter();
/* 740 */           copy(input, sw);
/* 741 */           return sw.toCharArray();
                }

/* 758 */       public static String toString(InputStream input) throws IOException {
/* 759 */           return toString(input, Charset.defaultCharset());
                }

/* 776 */       public static String toString(InputStream input, Charset encoding) throws IOException {
/* 777 */           StringBuilderWriter sw = new StringBuilderWriter();
/* 778 */           copy(input, sw, encoding);
/* 779 */           return sw.toString();
                }

/* 802 */       public static String toString(InputStream input, String encoding) throws IOException {
/* 803 */           return toString(input, Charsets.toCharset(encoding));
                }

/* 817 */       public static String toString(Reader input) throws IOException {
/* 818 */           StringBuilderWriter sw = new StringBuilderWriter();
/* 819 */           copy(input, sw);
/* 820 */           return sw.toString();
                }

/* 832 */       public static String toString(URI uri) throws IOException {
/* 833 */           return toString(uri, Charset.defaultCharset());
                }

/* 847 */       public static String toString(URI uri, Charset encoding) throws IOException {
/* 848 */           return toString(uri.toURL(), Charsets.toCharset(encoding));
                }

/* 865 */       public static String toString(URI uri, String encoding) throws IOException {
/* 866 */           return toString(uri, Charsets.toCharset(encoding));
                }

/* 878 */       public static String toString(URL url) throws IOException {
/* 879 */           return toString(url, Charset.defaultCharset());
                }

/* 893 */       public static String toString(URL url, Charset encoding) throws IOException {
/* 894 */           InputStream inputStream = url.openStream();
                    try {
/* 896 */               String string = toString(inputStream, encoding);
/* 898 */               inputStream.close();
                        return string;
                    } catch (Throwable th) {
                        inputStream.close();
                        throw th;
                    }
                }

/* 916 */       public static String toString(URL url, String encoding) throws IOException {
/* 917 */           return toString(url, Charsets.toCharset(encoding));
                }

                @Deprecated
/* 931 */       public static String toString(byte[] input) throws IOException {
/* 932 */           return new String(input);
                }

/* 948 */       public static String toString(byte[] input, String encoding) throws IOException {
/* 949 */           return new String(input, Charsets.toCharset(encoding));
                }

/* 967 */       public static List<String> readLines(InputStream input) throws IOException {
/* 968 */           return readLines(input, Charset.defaultCharset());
                }

/* 985 */       public static List<String> readLines(InputStream input, Charset encoding) throws IOException {
/* 986 */           InputStreamReader reader = new InputStreamReader(input, Charsets.toCharset(encoding));
/* 987 */           return readLines(reader);
                }

/* 1010 */      public static List<String> readLines(InputStream input, String encoding) throws IOException {
/* 1011 */          return readLines(input, Charsets.toCharset(encoding));
                }

/* 1027 */      public static List<String> readLines(Reader input) throws IOException {
/* 1028 */          BufferedReader reader = toBufferedReader(input);
/* 1029 */          List<String> list = new ArrayList<>();
/* 1031 */          for (String line = reader.readLine(); line != null; line = reader.readLine()) {
/* 1032 */              list.add(line);
                    }
/* 1035 */          return list;
                }

/* 1067 */      public static LineIterator lineIterator(Reader reader) {
/* 1068 */          return new LineIterator(reader);
                }

/* 1101 */      public static LineIterator lineIterator(InputStream input, Charset encoding) throws IOException {
/* 1102 */          return new LineIterator(new InputStreamReader(input, Charsets.toCharset(encoding)));
                }

/* 1138 */      public static LineIterator lineIterator(InputStream input, String encoding) throws IOException {
/* 1139 */          return lineIterator(input, Charsets.toCharset(encoding));
                }

/* 1151 */      public static InputStream toInputStream(CharSequence input) {
/* 1152 */          return toInputStream(input, Charset.defaultCharset());
                }

/* 1164 */      public static InputStream toInputStream(CharSequence input, Charset encoding) {
/* 1165 */          return toInputStream(input.toString(), encoding);
                }

/* 1184 */      public static InputStream toInputStream(CharSequence input, String encoding) throws IOException {
/* 1185 */          return toInputStream(input, Charsets.toCharset(encoding));
                }

/* 1197 */      public static InputStream toInputStream(String input) {
/* 1198 */          return toInputStream(input, Charset.defaultCharset());
                }

/* 1210 */      public static InputStream toInputStream(String input, Charset encoding) {
/* 1211 */          return new ByteArrayInputStream(input.getBytes(Charsets.toCharset(encoding)));
                }

/* 1230 */      public static InputStream toInputStream(String input, String encoding) throws IOException {
/* 1231 */          byte[] bytes = input.getBytes(Charsets.toCharset(encoding));
/* 1232 */          return new ByteArrayInputStream(bytes);
                }

/* 1248 */      public static void write(byte[] data, OutputStream output) throws IOException {
/* 1249 */          if (data != null) {
/* 1250 */              output.write(data);
                    }
                }

/* 1267 */      public static void write(byte[] data, Writer output) throws IOException {
/* 1268 */          write(data, output, Charset.defaultCharset());
                }

/* 1285 */      public static void write(byte[] data, Writer output, Charset encoding) throws IOException {
/* 1286 */          if (data != null) {
/* 1287 */              output.write(new String(data, Charsets.toCharset(encoding)));
                    }
                }

/* 1311 */      public static void write(byte[] data, Writer output, String encoding) throws IOException {
/* 1312 */          write(data, output, Charsets.toCharset(encoding));
                }

/* 1328 */      public static void write(char[] data, Writer output) throws IOException {
/* 1329 */          if (data != null) {
/* 1330 */              output.write(data);
                    }
                }

/* 1349 */      public static void write(char[] data, OutputStream output) throws IOException {
/* 1350 */          write(data, output, Charset.defaultCharset());
                }

/* 1368 */      public static void write(char[] data, OutputStream output, Charset encoding) throws IOException {
/* 1369 */          if (data != null) {
/* 1370 */              output.write(new String(data).getBytes(Charsets.toCharset(encoding)));
                    }
                }

/* 1396 */      public static void write(char[] data, OutputStream output, String encoding) throws IOException {
/* 1397 */          write(data, output, Charsets.toCharset(encoding));
                }

/* 1411 */      public static void write(CharSequence data, Writer output) throws IOException {
/* 1412 */          if (data != null) {
/* 1413 */              write(data.toString(), output);
                    }
                }

/* 1431 */      public static void write(CharSequence data, OutputStream output) throws IOException {
/* 1432 */          write(data, output, Charset.defaultCharset());
                }

/* 1448 */      public static void write(CharSequence data, OutputStream output, Charset encoding) throws IOException {
/* 1449 */          if (data != null) {
/* 1450 */              write(data.toString(), output, encoding);
                    }
                }

/* 1473 */      public static void write(CharSequence data, OutputStream output, String encoding) throws IOException {
/* 1474 */          write(data, output, Charsets.toCharset(encoding));
                }

/* 1488 */      public static void write(String data, Writer output) throws IOException {
/* 1489 */          if (data != null) {
/* 1490 */              output.write(data);
                    }
                }

/* 1508 */      public static void write(String data, OutputStream output) throws IOException {
/* 1509 */          write(data, output, Charset.defaultCharset());
                }

/* 1525 */      public static void write(String data, OutputStream output, Charset encoding) throws IOException {
/* 1526 */          if (data != null) {
/* 1527 */              output.write(data.getBytes(Charsets.toCharset(encoding)));
                    }
                }

/* 1551 */      public static void write(String data, OutputStream output, String encoding) throws IOException {
/* 1552 */          write(data, output, Charsets.toCharset(encoding));
                }

                @Deprecated
/* 1569 */      public static void write(StringBuffer data, Writer output) throws IOException {
/* 1570 */          if (data != null) {
/* 1571 */              output.write(data.toString());
                    }
                }

                @Deprecated
/* 1591 */      public static void write(StringBuffer data, OutputStream output) throws IOException {
/* 1592 */          write(data, output, (String) null);
                }

                @Deprecated
/* 1616 */      public static void write(StringBuffer data, OutputStream output, String encoding) throws IOException {
/* 1617 */          if (data != null) {
/* 1618 */              output.write(data.toString().getBytes(Charsets.toCharset(encoding)));
                    }
                }

/* 1637 */      public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output) throws IOException {
/* 1638 */          writeLines(lines, lineEnding, output, Charset.defaultCharset());
                }

/* 1655 */      public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, Charset encoding) throws IOException {
/* 1656 */          if (lines != null) {
/* 1659 */              if (lineEnding == null) {
/* 1660 */                  lineEnding = LINE_SEPARATOR;
                        }
/* 1662 */              Charset cs = Charsets.toCharset(encoding);
                        for (Object line : lines) {
/* 1664 */                  if (line != null) {
/* 1665 */                      output.write(line.toString().getBytes(cs));
                            }
/* 1667 */                  output.write(lineEnding.getBytes(cs));
                        }
                    }
                }

/* 1691 */      public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, String encoding) throws IOException {
/* 1692 */          writeLines(lines, lineEnding, output, Charsets.toCharset(encoding));
                }

/* 1707 */      public static void writeLines(Collection<?> lines, String lineEnding, Writer writer) throws IOException {
/* 1708 */          if (lines != null) {
/* 1711 */              if (lineEnding == null) {
/* 1712 */                  lineEnding = LINE_SEPARATOR;
                        }
                        for (Object line : lines) {
/* 1715 */                  if (line != null) {
/* 1716 */                      writer.write(line.toString());
                            }
/* 1718 */                  writer.write(lineEnding);
                        }
                    }
                }

/* 1743 */      public static int copy(InputStream input, OutputStream output) throws IOException {
/* 1744 */          long count = copyLarge(input, output);
                    if (count > 2147483647L) {
/* 1746 */              return -1;
                    }
                    return (int) count;
                }

/* 1768 */      public static long copyLarge(InputStream input, OutputStream output) throws IOException {
/* 1769 */          return copyLarge(input, output, new byte[4096]);
                }

/* 1789 */      public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
/* 1790 */          long count = 0;
                    while (true) {
                        int n = input.read(buffer);
/* 1792 */              if (-1 != n) {
/* 1793 */                  output.write(buffer, 0, n);
/* 1794 */                  count += (long) n;
                        } else {
/* 1796 */                  return count;
                        }
                    }
                }

/* 1819 */      public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length) throws IOException {
/* 1820 */          return copyLarge(input, output, inputOffset, length, new byte[4096]);
                }

/* 1844 */      public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) throws IOException {
                    if (inputOffset > 0) {
/* 1846 */              skipFully(input, inputOffset);
                    }
                    if (length == 0) {
/* 1849 */              return 0L;
                    }
/* 1851 */          int bufferLength = buffer.length;
/* 1852 */          int bytesToRead = bufferLength;
                    if (length > 0 && length < bufferLength) {
/* 1854 */              bytesToRead = (int) length;
                    }
/* 1857 */          long totalRead = 0;
/* 1858 */          while (bytesToRead > 0) {
                        int read = input.read(buffer, 0, bytesToRead);
                        if (-1 != read) {
/* 1859 */                  output.write(buffer, 0, read);
/* 1860 */                  totalRead += (long) read;
                            if (length > 0) {
/* 1863 */                      bytesToRead = (int) Math.min(length - totalRead, bufferLength);
                            }
                        } else {
/* 1866 */                  return totalRead;
                        }
                    }
/* 1866 */          return totalRead;
                }

/* 1885 */      public static void copy(InputStream input, Writer output) throws IOException {
/* 1886 */          copy(input, output, Charset.defaultCharset());
                }

/* 1905 */      public static void copy(InputStream input, Writer output, Charset encoding) throws IOException {
/* 1906 */          InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(encoding));
/* 1907 */          copy(in, output);
                }

/* 1932 */      public static void copy(InputStream input, Writer output, String encoding) throws IOException {
/* 1933 */          copy(input, output, Charsets.toCharset(encoding));
                }

/* 1956 */      public static int copy(Reader input, Writer output) throws IOException {
/* 1957 */          long count = copyLarge(input, output);
                    if (count > 2147483647L) {
/* 1959 */              return -1;
                    }
                    return (int) count;
                }

/* 1979 */      public static long copyLarge(Reader input, Writer output) throws IOException {
/* 1980 */          return copyLarge(input, output, new char[4096]);
                }

/* 1998 */      public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
/* 1999 */          long count = 0;
                    while (true) {
                        int n = input.read(buffer);
/* 2001 */              if (-1 != n) {
/* 2002 */                  output.write(buffer, 0, n);
/* 2003 */                  count += (long) n;
                        } else {
/* 2005 */                  return count;
                        }
                    }
                }

/* 2028 */      public static long copyLarge(Reader input, Writer output, long inputOffset, long length) throws IOException {
/* 2029 */          return copyLarge(input, output, inputOffset, length, new char[4096]);
                }

/* 2052 */      public static long copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer) throws IOException {
                    if (inputOffset > 0) {
/* 2054 */              skipFully(input, inputOffset);
                    }
                    if (length == 0) {
/* 2057 */              return 0L;
                    }
/* 2059 */          int bytesToRead = buffer.length;
                    if (length > 0 && length < buffer.length) {
/* 2061 */              bytesToRead = (int) length;
                    }
/* 2064 */          long totalRead = 0;
/* 2065 */          while (bytesToRead > 0) {
                        int read = input.read(buffer, 0, bytesToRead);
                        if (-1 != read) {
/* 2066 */                  output.write(buffer, 0, read);
/* 2067 */                  totalRead += (long) read;
                            if (length > 0) {
/* 2070 */                      bytesToRead = (int) Math.min(length - totalRead, buffer.length);
                            }
                        } else {
/* 2073 */                  return totalRead;
                        }
                    }
/* 2073 */          return totalRead;
                }

/* 2096 */      public static void copy(Reader input, OutputStream output) throws IOException {
/* 2097 */          copy(input, output, Charset.defaultCharset());
                }

/* 2123 */      public static void copy(Reader input, OutputStream output, Charset encoding) throws IOException {
/* 2124 */          OutputStreamWriter out = new OutputStreamWriter(output, Charsets.toCharset(encoding));
/* 2125 */          copy(input, out);
/* 2128 */          out.flush();
                }

/* 2157 */      public static void copy(Reader input, OutputStream output, String encoding) throws IOException {
/* 2158 */          copy(input, output, Charsets.toCharset(encoding));
                }

/* 2178 */      public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
/* 2179 */          if (!(input1 instanceof BufferedInputStream)) {
/* 2180 */              input1 = new BufferedInputStream(input1);
                    }
/* 2182 */          if (!(input2 instanceof BufferedInputStream)) {
/* 2183 */              input2 = new BufferedInputStream(input2);
                    }
/* 2187 */          for (int ch = input1.read(); -1 != ch; ch = input1.read()) {
/* 2188 */              int ch2 = input2.read();
/* 2189 */              if (ch != ch2) {
                            return false;
                        }
                    }
/* 2195 */          int ch3 = input2.read();
                    return ch3 == -1;
                }

/* 2216 */      public static boolean contentEquals(Reader input1, Reader input2) throws IOException {
/* 2217 */          Reader input3 = toBufferedReader(input1);
/* 2218 */          Reader input4 = toBufferedReader(input2);
/* 2221 */          for (int ch = input3.read(); -1 != ch; ch = input3.read()) {
/* 2222 */              int ch2 = input4.read();
/* 2223 */              if (ch != ch2) {
                            return false;
                        }
                    }
/* 2229 */          int ch3 = input4.read();
                    return ch3 == -1;
                }

/* 2248 */      public static boolean contentEqualsIgnoreEOL(Reader input1, Reader input2) throws IOException {
/* 2249 */          BufferedReader br1 = toBufferedReader(input1);
/* 2250 */          BufferedReader br2 = toBufferedReader(input2);
/* 2252 */          String line1 = br1.readLine();
/* 2253 */          String line2 = br2.readLine();
/* 2254 */          while (line1 != null && line2 != null && line1.equals(line2)) {
/* 2255 */              line1 = br1.readLine();
/* 2256 */              line2 = br2.readLine();
                    }
/* 2258 */          if (line1 == null) {
                        return line2 == null;
                    }
                    return line1.equals(line2);
                }

/* 2277 */      public static long skip(InputStream input, long toSkip) throws IOException {
                    if (toSkip < 0) {
/* 2279 */              throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
                    }
/* 2286 */          if (SKIP_BYTE_BUFFER == null) {
/* 2287 */              SKIP_BYTE_BUFFER = new byte[2048];
                    }
/* 2289 */          long remain = toSkip;
                    while (remain > 0) {
/* 2291 */              long n = input.read(SKIP_BYTE_BUFFER, 0, (int) Math.min(remain, 2048L));
                        if (n < 0) {
                            break;
                        }
/* 2295 */              remain -= n;
                    }
/* 2297 */          return toSkip - remain;
                }

/* 2316 */      public static long skip(Reader input, long toSkip) throws IOException {
                    if (toSkip < 0) {
/* 2318 */              throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
                    }
/* 2325 */          if (SKIP_CHAR_BUFFER == null) {
/* 2326 */              SKIP_CHAR_BUFFER = new char[2048];
                    }
/* 2328 */          long remain = toSkip;
                    while (remain > 0) {
/* 2330 */              long n = input.read(SKIP_CHAR_BUFFER, 0, (int) Math.min(remain, 2048L));
                        if (n < 0) {
                            break;
                        }
/* 2334 */              remain -= n;
                    }
/* 2336 */          return toSkip - remain;
                }

/* 2354 */      public static void skipFully(InputStream input, long toSkip) throws IOException {
                    if (toSkip < 0) {
/* 2356 */              throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
                    }
/* 2358 */          long skipped = skip(input, toSkip);
                    if (skipped != toSkip) {
/* 2360 */              throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
                    }
                }

/* 2379 */      public static void skipFully(Reader input, long toSkip) throws IOException {
/* 2380 */          long skipped = skip(input, toSkip);
                    if (skipped != toSkip) {
/* 2382 */              throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
                    }
                }

/* 2401 */      public static int read(Reader input, char[] buffer, int offset, int length) throws IOException {
/* 2402 */          if (length < 0) {
/* 2403 */              throw new IllegalArgumentException("Length must not be negative: " + length);
                    }
/* 2405 */          int remaining = length;
/* 2406 */          while (remaining > 0) {
/* 2407 */              int location = length - remaining;
/* 2408 */              int count = input.read(buffer, offset + location, remaining);
/* 2409 */              if (-1 == count) {
                            break;
                        }
/* 2412 */              remaining -= count;
                    }
/* 2414 */          return length - remaining;
                }

/* 2429 */      public static int read(Reader input, char[] buffer) throws IOException {
/* 2430 */          return read(input, buffer, 0, buffer.length);
                }

/* 2447 */      public static int read(InputStream input, byte[] buffer, int offset, int length) throws IOException {
/* 2448 */          if (length < 0) {
/* 2449 */              throw new IllegalArgumentException("Length must not be negative: " + length);
                    }
/* 2451 */          int remaining = length;
/* 2452 */          while (remaining > 0) {
/* 2453 */              int location = length - remaining;
/* 2454 */              int count = input.read(buffer, offset + location, remaining);
/* 2455 */              if (-1 == count) {
                            break;
                        }
/* 2458 */              remaining -= count;
                    }
/* 2460 */          return length - remaining;
                }

/* 2475 */      public static int read(InputStream input, byte[] buffer) throws IOException {
/* 2476 */          return read(input, buffer, 0, buffer.length);
                }

/* 2495 */      public static void readFully(Reader input, char[] buffer, int offset, int length) throws IOException {
/* 2496 */          int actual = read(input, buffer, offset, length);
/* 2497 */          if (actual != length) {
/* 2498 */              throw new EOFException("Length to read: " + length + " actual: " + actual);
                    }
                }

/* 2516 */      public static void readFully(Reader input, char[] buffer) throws IOException {
/* 2517 */          readFully(input, buffer, 0, buffer.length);
                }

/* 2536 */      public static void readFully(InputStream input, byte[] buffer, int offset, int length) throws IOException {
/* 2537 */          int actual = read(input, buffer, offset, length);
/* 2538 */          if (actual != length) {
/* 2539 */              throw new EOFException("Length to read: " + length + " actual: " + actual);
                    }
                }

/* 2557 */      public static void readFully(InputStream input, byte[] buffer) throws IOException {
/* 2558 */          readFully(input, buffer, 0, buffer.length);
                }
            }
