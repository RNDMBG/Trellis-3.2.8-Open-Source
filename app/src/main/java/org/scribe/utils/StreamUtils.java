            package org.scribe.utils;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.Reader;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class StreamUtils {
/* 11 */        private StreamUtils() {
                }

/* 21 */        public static String getStreamContents(InputStream is) {
                    int read;
/* 22 */            Preconditions.checkNotNull(is, "Cannot get String from a null object");
                    try {
/* 25 */                char[] buffer = new char[65536];
/* 26 */                StringBuilder out = new StringBuilder();
/* 27 */                Reader in = new InputStreamReader(is, IoUtils.UTF_8);
/* 36 */                do {
/* 31 */                    read = in.read(buffer, 0, buffer.length);
/* 32 */                    if (read > 0) {
/* 34 */                        out.append(buffer, 0, read);
                            }
/* 36 */                } while (read >= 0);
/* 37 */                in.close();
/* 38 */                return out.toString();
                    } catch (IOException ioe) {
/* 41 */                throw new IllegalStateException("Error while reading response body", ioe);
                    }
                }
            }
