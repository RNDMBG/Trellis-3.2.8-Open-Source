            package org.apache.commons.p005io;

            import java.io.ByteArrayInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.io.Reader;
            import java.io.StringReader;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Deprecated
/* 134 */   public class CopyUtils {
                private static final int DEFAULT_BUFFER_SIZE = 4096;

/* 135 */       public static void copy(byte[] input, OutputStream output) throws IOException {
/* 136 */           output.write(input);
                }

/* 152 */       public static void copy(byte[] input, Writer output) throws IOException {
/* 153 */           ByteArrayInputStream in = new ByteArrayInputStream(input);
/* 154 */           copy(in, output);
                }

/* 172 */       public static void copy(byte[] input, Writer output, String encoding) throws IOException {
/* 173 */           ByteArrayInputStream in = new ByteArrayInputStream(input);
/* 174 */           copy(in, output, encoding);
                }

/* 193 */       public static int copy(InputStream input, OutputStream output) throws IOException {
/* 194 */           byte[] buffer = new byte[4096];
/* 195 */           int count = 0;
                    while (true) {
                        int n = input.read(buffer);
/* 197 */               if (-1 != n) {
/* 198 */                   output.write(buffer, 0, n);
/* 199 */                   count += n;
                        } else {
/* 201 */                   return count;
                        }
                    }
                }

/* 218 */       public static int copy(Reader input, Writer output) throws IOException {
/* 219 */           char[] buffer = new char[4096];
/* 220 */           int count = 0;
                    while (true) {
                        int n = input.read(buffer);
/* 222 */               if (-1 != n) {
/* 223 */                   output.write(buffer, 0, n);
/* 224 */                   count += n;
                        } else {
/* 226 */                   return count;
                        }
                    }
                }

/* 244 */       public static void copy(InputStream input, Writer output) throws IOException {
/* 245 */           InputStreamReader in = new InputStreamReader(input);
/* 246 */           copy(in, output);
                }

/* 263 */       public static void copy(InputStream input, Writer output, String encoding) throws IOException {
/* 264 */           InputStreamReader in = new InputStreamReader(input, encoding);
/* 265 */           copy(in, output);
                }

/* 283 */       public static void copy(Reader input, OutputStream output) throws IOException {
/* 284 */           OutputStreamWriter out = new OutputStreamWriter(output);
/* 285 */           copy(input, out);
/* 288 */           out.flush();
                }

/* 306 */       public static void copy(String input, OutputStream output) throws IOException {
/* 307 */           StringReader in = new StringReader(input);
/* 308 */           OutputStreamWriter out = new OutputStreamWriter(output);
/* 309 */           copy(in, out);
/* 312 */           out.flush();
                }

/* 326 */       public static void copy(String input, Writer output) throws IOException {
/* 327 */           output.write(input);
                }
            }
