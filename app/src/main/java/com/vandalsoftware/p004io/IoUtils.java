            package com.vandalsoftware.p004io;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.Closeable;
            import java.io.File;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import java.net.Socket;
            import java.util.ArrayList;
            import java.util.LinkedList;
            
            
    public final class IoUtils {
                public static final String UTF_8 = "UTF-8";

        private IoUtils() {
                }

        public static void closeQuietly(Closeable closeable) {
            if (closeable != null) {
                        try {
                    closeable.close();
                        } catch (IOException e) {
                        }
                    }
                }

        public static void closeQuietly(Socket socket) {
            if (socket != null) {
                        try {
                    socket.close();
                        } catch (Exception e) {
                        }
                    }
                }

        public static byte[] readFileAsByteArray(String path) throws IOException {
            return readFileAsBytes(path).toByteArray();
                }

        public static String readFileAsString(String path) throws IOException {
            return readFileAsBytes(path).toString(UTF_8);
                }

        private static UnsafeByteSequence readFileAsBytes(String path) throws IOException {
            RandomAccessFile f = null;
                    try {
                RandomAccessFile f2 = new RandomAccessFile(path, "r");
                        try {
                    UnsafeByteSequence bytes = new UnsafeByteSequence((int) f2.length());
                    byte[] buffer = new byte[8192];
                            while (true) {
                        int byteCount = f2.read(buffer);
                        if (byteCount != -1) {
                            bytes.write(buffer, 0, byteCount);
                                } else {
                            closeQuietly(f2);
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

        public static void deleteContents(File dir) throws IOException {
            if (!dir.isDirectory()) {
                throw new IllegalArgumentException("not a directory: " + dir);
                    }
            LinkedList<File> dirs = new LinkedList<>();
            dirs.add(dir);
            ArrayList<File> emptyDirs = new ArrayList<>();
            while (!dirs.isEmpty()) {
               File d = dirs.remove();
               File[] fs = d.listFiles();
               if (fs != null) {
                            for (File f : fs) {
                       if (f.isDirectory()) {
                           dirs.add(f);
                                } else {
                           deleteFileOrThrow(f);
                                }
                            }
                   emptyDirs.add(d);
                        }
                    }
                    for (int i = emptyDirs.size() - 1; i >= 0; i--) {
               deleteFileOrThrow(emptyDirs.get(i));
                    }
                }

       public static void deleteFileOrThrow(File f) throws IOException {
           if (f.exists() && !f.delete()) {
               throw new IOException("failed to delete file: " + f);
                    }
                }

       public static void renameFileOrThrow(File src, File dst) throws IOException {
           if (!src.renameTo(dst)) {
               throw new IOException("file not renamed " + src + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dst);
                    }
                }
            }
