            package org.apache.commons.p005io;

            import com.vandalsoftware.p004io.IoUtils;
            import java.nio.charset.Charset;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 65 */    public class Charsets {
                public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
                public static final Charset US_ASCII = Charset.forName("US-ASCII");
                public static final Charset UTF_16 = Charset.forName("UTF-16");
                public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
                public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
                public static final Charset UTF_8 = Charset.forName(IoUtils.UTF_8);

/* 66 */        public static Charset toCharset(Charset charset) {
                    return charset == null ? Charset.defaultCharset() : charset;
                }

/* 79 */        public static Charset toCharset(String charset) {
                    return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
                }
            }
