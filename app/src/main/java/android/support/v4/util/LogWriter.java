            package android.support.v4.util;

            import android.util.Log;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class LogWriter extends Writer {
                private StringBuilder mBuilder = new StringBuilder(128);
                private final String mTag;

/* 38 */        public LogWriter(String tag) {
/* 40 */            this.mTag = tag;
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 43 */        public void close() {
/* 44 */            flushBuilder();
                }

                @Override // java.io.Writer, java.io.Flushable
/* 47 */        public void flush() {
/* 48 */            flushBuilder();
                }

                @Override // java.io.Writer
/* 51 */        public void write(char[] buf, int offset, int count) {
                    for (int i = 0; i < count; i++) {
/* 53 */                char c = buf[offset + i];
/* 54 */                if (c == '\n') {
/* 55 */                    flushBuilder();
                        } else {
/* 58 */                    this.mBuilder.append(c);
                        }
                    }
                }

/* 63 */        private void flushBuilder() {
/* 64 */            if (this.mBuilder.length() > 0) {
/* 65 */                Log.d(this.mTag, this.mBuilder.toString());
/* 66 */                this.mBuilder.delete(0, this.mBuilder.length());
                    }
                }
            }
