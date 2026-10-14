            package com.vandalsoftware.p004io;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.Closeable;
            import java.io.File;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import java.net.Socket;
            import java.util.ArrayList;
            import java.util.LinkedList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public final class IoUtils {
                public static final String UTF_8 = "UTF-8";

/* 29 */        private IoUtils() {
                }

/* 36 */        public static void closeQuietly(Closeable closeable) {
/* 37 */            if (closeable != null) {
                        try {
/* 39 */                    closeable.close();
                        } catch (IOException e) {
                        }
                    }
                }

/* 48 */        public static void closeQuietly(Socket socket) {
/* 49 */            if (socket != null) {
                        try {
/* 51 */                    socket.close();
                        } catch (Exception e) {
                        }
                    }
                }

/* 60 */        public static byte[] readFileAsByteArray(String path) throws IOException {
/* 61 */            return readFileAsBytes(path).toByteArray();
                }

/* 67 */        public static String readFileAsString(String path) throws IOException {
/* 68 */            return readFileAsBytes(path).toString(UTF_8);
                }

/* 71 */        private static UnsafeByteSequence readFileAsBytes(String path) throws IOException {
/* 72 */            RandomAccessFile f = null;
                    try {
/* 74 */                RandomAccessFile f2 = new RandomAccessFile(path, "r");
                        try {
/* 75 */                    UnsafeByteSequence bytes = new UnsafeByteSequence((int) f2.length());
/* 76 */                    byte[] buffer = new byte[8192];
                            while (true) {
/* 78 */                        int byteCount = f2.read(buffer);
/* 79 */                        if (byteCount != -1) {
/* 82 */                            bytes.write(buffer, 0, byteCount);
                                } else {
/* 85 */                            closeQuietly(f2);
                                    return bytes;
                                }
                            }
                        } catch (Throwable th) {
                            f = f2;
                            closeQuietly(f);
                            throw new IOException(th);
                        }
                    } catch (Throwable th2) {
                        throw new IOException(th2);
                    }
                }

/* 92 */        public static void deleteContents(File dir) throws IOException {
/* 93 */            if (!dir.isDirectory()) {
/* 94 */                throw new IllegalArgumentException("not a directory: " + dir);
                    }
/* 96 */            LinkedList<File> dirs = new LinkedList<>();
/* 97 */            dirs.add(dir);
/* 98 */            ArrayList<File> emptyDirs = new ArrayList<>();
/* 99 */            while (!dirs.isEmpty()) {
/* 100 */               File d = dirs.remove();
/* 101 */               File[] fs = d.listFiles();
/* 102 */               if (fs != null) {
                            for (File f : fs) {
/* 104 */                       if (f.isDirectory()) {
/* 105 */                           dirs.add(f);
                                } else {
/* 107 */                           deleteFileOrThrow(f);
                                }
                            }
/* 110 */                   emptyDirs.add(d);
                        }
                    }
                    for (int i = emptyDirs.size() - 1; i >= 0; i--) {
/* 115 */               deleteFileOrThrow(emptyDirs.get(i));
                    }
                }

/* 119 */       public static void deleteFileOrThrow(File f) throws IOException {
/* 120 */           if (f.exists() && !f.delete()) {
/* 121 */               throw new IOException("failed to delete file: " + f);
                    }
                }

/* 125 */       public static void renameFileOrThrow(File src, File dst) throws IOException {
/* 126 */           if (!src.renameTo(dst)) {
/* 127 */               throw new IOException("file not renamed " + src + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dst);
                    }
                }
            }
