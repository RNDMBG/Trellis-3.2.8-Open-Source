            package okio;

            import com.vandalsoftware.p004io.IoUtils;
            import java.nio.charset.Charset;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    final class Util {
                public static final Charset UTF_8 = Charset.forName(IoUtils.UTF_8);

/* 23 */        private Util() {
                }

/* 27 */        public static void checkOffsetAndCount(long size, long offset, long byteCount) {
                    if ((offset | byteCount) < 0 || offset > size || size - offset < byteCount) {
/* 29 */                throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", Long.valueOf(size), Long.valueOf(offset), Long.valueOf(byteCount)));
                    }
                }

/* 34 */        public static short reverseBytesShort(short s) {
/* 35 */            int i = s & 65535;
/* 36 */            int reversed = ((65280 & i) >>> 8) | ((i & 255) << 8);
/* 38 */            return (short) reversed;
                }

/* 41 */        public static int reverseBytesInt(int i) {
/* 42 */            return (((-16777216) & i) >>> 24) | ((16711680 & i) >>> 8) | ((65280 & i) << 8) | ((i & 255) << 24);
                }

/* 48 */        public static long reverseBytesLong(long v) {
/* 49 */            return (((-72057594037927936L) & v) >>> 56) | ((71776119061217280L & v) >>> 40) | ((280375465082880L & v) >>> 24) | ((1095216660480L & v) >>> 8) | ((4278190080L & v) << 8) | ((16711680 & v) << 24) | ((65280 & v) << 40) | ((255 & v) << 56);
                }

/* 64 */        public static void sneakyRethrow(Throwable t) throws Throwable {
/* 65 */            sneakyThrow2(t);
                }

/* 69 */        private static <T extends Throwable> void sneakyThrow2(Throwable t) throws Throwable {
/* 70 */            throw t;
                }
            }
