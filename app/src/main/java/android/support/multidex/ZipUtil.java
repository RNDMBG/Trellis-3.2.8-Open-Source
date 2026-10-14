            package android.support.multidex;

            import java.io.File;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import java.util.zip.CRC32;
            import java.util.zip.ZipException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    final class ZipUtil {
                private static final int BUFFER_SIZE = 16384;
                private static final int ENDHDR = 22;
                private static final int ENDSIG = 101010256;

/* 31 */        static class CentralDirectory {
                    long offset;
                    long size;

/* 32 */            CentralDirectory() {
                    }
                }

/* 31 */        ZipUtil() {
                }

/* 54 */        static long getZipCrc(File apk) throws IOException {
/* 55 */            RandomAccessFile raf = new RandomAccessFile(apk, "r");
                    try {
/* 57 */                CentralDirectory dir = findCentralDirectory(raf);
/* 59 */                long jComputeCrcOfCentralDir = computeCrcOfCentralDir(raf, dir);
/* 61 */                raf.close();
                        return jComputeCrcOfCentralDir;
                    } catch (Throwable th) {
                        raf.close();
                        throw th;
                    }
                }

/* 67 */        static CentralDirectory findCentralDirectory(RandomAccessFile raf) throws IOException {
/* 68 */            long scanOffset = raf.length() - 22;
                    if (scanOffset < 0) {
/* 70 */                throw new ZipException("File too short to be a zip file: " + raf.length());
                    }
/* 73 */            long stopOffset = scanOffset - 65536;
                    if (stopOffset < 0) {
/* 75 */                stopOffset = 0;
                    }
/* 78 */            int endSig = Integer.reverseBytes(ENDSIG);
                    do {
/* 80 */                raf.seek(scanOffset);
/* 81 */                if (raf.readInt() != endSig) {
/* 85 */                    scanOffset--;
                        } else {
/* 95 */                    raf.skipBytes(2);
/* 96 */                    raf.skipBytes(2);
/* 97 */                    raf.skipBytes(2);
/* 98 */                    raf.skipBytes(2);
/* 99 */                    CentralDirectory dir = new CentralDirectory();
/* 100 */                   dir.size = ((long) Integer.reverseBytes(raf.readInt())) & 4294967295L;
/* 101 */                   dir.offset = ((long) Integer.reverseBytes(raf.readInt())) & 4294967295L;
/* 102 */                   return dir;
                        }
                    } while (scanOffset >= stopOffset);
/* 87 */            throw new ZipException("End Of Central Directory signature not found");
                }

/* 107 */       static long computeCrcOfCentralDir(RandomAccessFile raf, CentralDirectory dir) throws IOException {
/* 108 */           CRC32 crc = new CRC32();
/* 109 */           long stillToRead = dir.size;
/* 110 */           raf.seek(dir.offset);
/* 111 */           int length = (int) Math.min(16384L, stillToRead);
/* 112 */           byte[] buffer = new byte[16384];
/* 113 */           int length2 = raf.read(buffer, 0, length);
/* 114 */           while (length2 != -1) {
/* 115 */               crc.update(buffer, 0, length2);
/* 116 */               stillToRead -= (long) length2;
                        if (stillToRead == 0) {
                            break;
                        }
/* 120 */               int length3 = (int) Math.min(16384L, stillToRead);
/* 121 */               length2 = raf.read(buffer, 0, length3);
                    }
/* 123 */           return crc.getValue();
                }
            }
