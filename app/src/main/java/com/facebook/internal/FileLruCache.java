            package com.facebook.internal;

            import android.content.Context;
            import com.facebook.LoggingBehavior;
            import com.facebook.Settings;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.FilenameFilter;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.security.InvalidParameterException;
            import java.util.Date;
            import java.util.PriorityQueue;
            import java.util.concurrent.atomic.AtomicLong;
            import org.json.JSONException;
            import org.json.JSONObject;
            import org.json.JSONTokener;
            
            
    public final class FileLruCache {
                private static final String HEADER_CACHEKEY_KEY = "key";
                private static final String HEADER_CACHE_CONTENT_TAG_KEY = "tag";
                static final String TAG = FileLruCache.class.getSimpleName();
                private static final AtomicLong bufferIndex = new AtomicLong();
                private final File directory;
                private boolean isTrimPending;
                private final Limits limits;
                private final Object lock = new Object();
                private final String tag;

                private interface StreamCloseCallback {
                    void onClose();
                }

        static  void access$000(FileLruCache x0, String x1, File x2) {
            x0.renameToTargetAndTrim(x1, x2);
                }

        static  void access$100(FileLruCache x0) {
            x0.trim();
                }

        static  AtomicLong access$200() {
            return bufferIndex;
                }

        public FileLruCache(Context context, String tag, Limits limits) {
            this.tag = tag;
            this.limits = limits;
            this.directory = new File(context.getCacheDir(), tag);
            this.directory.mkdirs();
            BufferFile.deleteAll(this.directory);
                }

        public void clearForTest() throws IOException {
            File[] arr$ = this.directory.listFiles();
                    for (File file : arr$) {
                file.delete();
                    }
                }

        long sizeInBytesForTest() {
                    synchronized (this.lock) {
               while (this.isTrimPending) {
                            try {
                                try {
                           this.lock.wait();
                                } catch (InterruptedException e) {
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
           File[] files = this.directory.listFiles();
           long total = 0;
                    for (File file : files) {
               total += file.length();
                    }
           return total;
                }

       public InputStream get(String key) throws IOException {
           return get(key, null);
                }

       public InputStream get(String key, String contentTag) throws IOException {
           File file = new File(this.directory, Utility.md5hash(key));
                    try {
               FileInputStream input = new FileInputStream(file);
               BufferedInputStream buffered = new BufferedInputStream(input, 8192);
                        try {
                   JSONObject header = StreamHeader.readHeader(buffered);
                   if (header == null) {
                       if (0 == 0) {
                           buffered.close();
                                }
                       return null;
                            }
                   String foundKey = header.optString("key");
                   if (foundKey == null || !foundKey.equals(key)) {
                       if (0 == 0) {
                           buffered.close();
                                }
                       return null;
                            }
                   String headerContentTag = header.optString("tag", null);
                   if ((contentTag == null && headerContentTag != null) || (contentTag != null && !contentTag.equals(headerContentTag))) {
                       if (0 == 0) {
                           buffered.close();
                                }
                       return null;
                            }
                   long accessTime = new Date().getTime();
                   Logger.log(LoggingBehavior.CACHE, TAG, "Setting lastModified to " + accessTime + " for " + file.getName());
                   file.setLastModified(accessTime);
                   if (1 == 0) {
                       buffered.close();
                            }
                            return buffered;
                        } catch (Throwable th) {
                   if (0 == 0) {
                       buffered.close();
                            }
                            throw th;
                        }
                    } catch (IOException e) {
               return null;
                    }
                }

       OutputStream openPutStream(String key) throws IOException {
           return openPutStream(key, null);
                }

       public OutputStream openPutStream(String key, String contentTag) throws IOException {
           File buffer = BufferFile.newFile(this.directory);
           buffer.delete();
           if (!buffer.createNewFile()) {
               throw new IOException("Could not create file at " + buffer.getAbsolutePath());
                    }
                    try {
               FileOutputStream file = new FileOutputStream(buffer);
               StreamCloseCallback renameToTargetCallback = new C07761(key, buffer);
               CloseCallbackOutputStream cleanup = new CloseCallbackOutputStream(file, renameToTargetCallback);
               BufferedOutputStream buffered = new BufferedOutputStream(cleanup, 8192);
                        try {
                            try {
                       JSONObject header = new JSONObject();
                       header.put("key", key);
                       if (!Utility.isNullOrEmpty(contentTag)) {
                           header.put("tag", contentTag);
                                }
                       StreamHeader.writeHeader(buffered, header);
                       if (1 == 0) {
                           buffered.close();
                                }
                                return buffered;
                            } catch (JSONException e) {
                       Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating JSON header for cache file: " + e);
                       throw new IOException(e.getMessage());
                            }
                        } catch (Throwable th) {
                   if (0 == 0) {
                       buffered.close();
                            }
                            throw th;
                        }
                    } catch (FileNotFoundException e2) {
               Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating buffer output stream: " + e2);
               throw new IOException(e2.getMessage());
                    }
                }

                
       class C07761 implements StreamCloseCallback {
                    final  File val$buffer;
                    final  String val$key;

           C07761(String str, File file) {
                        this.val$key = str;
                        this.val$buffer = file;
                    }

                    @Override 
           public void onClose() {
               FileLruCache.access$000(FileLruCache.this, this.val$key, this.val$buffer);
                    }
                }

       private void renameToTargetAndTrim(String key, File buffer) {
           File target = new File(this.directory, Utility.md5hash(key));
           if (!buffer.renameTo(target)) {
               buffer.delete();
                    }
           postTrim();
                }

       public InputStream interceptAndPut(String key, InputStream input) throws IOException {
           OutputStream output = openPutStream(key);
           return new CopyingInputStream(input, output);
                }

       public String toString() {
           return "{FileLruCache: tag:" + this.tag + " file:" + this.directory.getName() + "}";
                }

       private void postTrim() {
                    synchronized (this.lock) {
                        try {
                   if (!this.isTrimPending) {
                       this.isTrimPending = true;
                       Settings.getExecutor().execute(new RunnableC07772());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       class RunnableC07772 implements Runnable {
           RunnableC07772() {
                    }

                    @Override 
           public void run() {
               FileLruCache.access$100(FileLruCache.this);
                    }
                }

       private void trim() {
                    try {
               Logger.log(LoggingBehavior.CACHE, TAG, "trim started");
               PriorityQueue<ModifiedFile> heap = new PriorityQueue<>();
               long size = 0;
               long count = 0;
               File[] arr$ = this.directory.listFiles(BufferFile.excludeBufferFiles());
                        for (File file : arr$) {
                   ModifiedFile modified = new ModifiedFile(file);
                   heap.add(modified);
                   Logger.log(LoggingBehavior.CACHE, TAG, "  trim considering time=" + modified.getModified() + " name=" + modified.getFile().getName());
                   size += file.length();
                   count++;
                        }
                        while (true) {
                            if (size > this.limits.getByteCount() || count > this.limits.getFileCount()) {
                       File file2 = heap.remove().getFile();
                       Logger.log(LoggingBehavior.CACHE, TAG, "  trim removing " + file2.getName());
                       size -= file2.length();
                       count--;
                       file2.delete();
                            } else {
                                synchronized (this.lock) {
                                    try {
                               this.isTrimPending = false;
                               this.lock.notifyAll();
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                       return;
                            }
                        }
                    } catch (Throwable th2) {
                        synchronized (this.lock) {
                            try {
                       this.isTrimPending = false;
                       this.lock.notifyAll();
                                throw th2;
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }
                }

       private static class BufferFile {
                    private static final String FILE_NAME_PREFIX = "buffer";
                    private static final FilenameFilter filterExcludeBufferFiles = new C07781();
                    private static final FilenameFilter filterExcludeNonBufferFiles = new C07792();

           private BufferFile() {
                    }

                    
           static class C07781 implements FilenameFilter {
               C07781() {
                        }

                        @Override 
               public boolean accept(File dir, String filename) {
                            return !filename.startsWith(BufferFile.FILE_NAME_PREFIX);
                        }
                    }

                    
           static class C07792 implements FilenameFilter {
               C07792() {
                        }

                        @Override 
               public boolean accept(File dir, String filename) {
                   return filename.startsWith(BufferFile.FILE_NAME_PREFIX);
                        }
                    }

           static void deleteAll(File root) {
               File[] arr$ = root.listFiles(excludeNonBufferFiles());
                        for (File file : arr$) {
                   file.delete();
                        }
                    }

           static FilenameFilter excludeBufferFiles() {
               return filterExcludeBufferFiles;
                    }

           static FilenameFilter excludeNonBufferFiles() {
               return filterExcludeNonBufferFiles;
                    }

           static File newFile(File root) {
               String name = FILE_NAME_PREFIX + Long.valueOf(FileLruCache.access$200().incrementAndGet()).toString();
               return new File(root, name);
                    }
                }

       private static final class StreamHeader {
                    private static final int HEADER_VERSION = 0;

           private StreamHeader() {
                    }

           static void writeHeader(OutputStream stream, JSONObject header) throws IOException {
               String headerString = header.toString();
               byte[] headerBytes = headerString.getBytes();
               stream.write(0);
               stream.write((headerBytes.length >> 16) & 255);
               stream.write((headerBytes.length >> 8) & 255);
               stream.write((headerBytes.length >> 0) & 255);
               stream.write(headerBytes);
                    }

           static JSONObject readHeader(InputStream stream) throws IOException {
                        JSONObject header;
               int version = stream.read();
               if (version != 0) {
                   return null;
                        }
               int headerSize = 0;
                        for (int i = 0; i < 3; i++) {
                   int b = stream.read();
                   if (b == -1) {
                       Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
                       return null;
                            }
                   headerSize = (headerSize << 8) + (b & 255);
                        }
               byte[] headerBytes = new byte[headerSize];
               int count = 0;
               while (count < headerBytes.length) {
                   int readCount = stream.read(headerBytes, count, headerBytes.length - count);
                   if (readCount < 1) {
                       Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read stopped at " + count + " when expected " + headerBytes.length);
                       return null;
                            }
                   count += readCount;
                        }
               String headerString = new String(headerBytes);
               JSONTokener tokener = new JSONTokener(headerString);
                        try {
                   Object parsed = tokener.nextValue();
                   if (!(parsed instanceof JSONObject)) {
                       Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: expected JSONObject, got " + parsed.getClass().getCanonicalName());
                       header = null;
                            } else {
                       header = (JSONObject) parsed;
                            }
                   return header;
                        } catch (JSONException e) {
                   throw new IOException(e.getMessage());
                        }
                    }
                }

       private static class CloseCallbackOutputStream extends OutputStream {
                    final StreamCloseCallback callback;
                    final OutputStream innerStream;

           CloseCallbackOutputStream(OutputStream innerStream, StreamCloseCallback callback) {
               this.innerStream = innerStream;
               this.callback = callback;
                    }

                    @Override 
           public void close() throws IOException {
                        try {
                   this.innerStream.close();
                   this.callback.onClose();
                        } catch (Throwable th) {
                            this.callback.onClose();
                            throw th;
                        }
                    }

                    @Override 
           public void flush() throws IOException {
               this.innerStream.flush();
                    }

                    @Override 
           public void write(byte[] buffer, int offset, int count) throws IOException {
               this.innerStream.write(buffer, offset, count);
                    }

                    @Override 
           public void write(byte[] buffer) throws IOException {
               this.innerStream.write(buffer);
                    }

                    @Override 
           public void write(int oneByte) throws IOException {
               this.innerStream.write(oneByte);
                    }
                }

       private static final class CopyingInputStream extends InputStream {
                    final InputStream input;
                    final OutputStream output;

           CopyingInputStream(InputStream input, OutputStream output) {
               this.input = input;
               this.output = output;
                    }

                    @Override 
           public int available() throws IOException {
               return this.input.available();
                    }

                    @Override 
           public void close() throws IOException {
                        try {
                   this.input.close();
                   this.output.close();
                        } catch (Throwable th) {
                            this.output.close();
                            throw th;
                        }
                    }

                    @Override 
           public void mark(int readlimit) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public boolean markSupported() {
               return false;
                    }

                    @Override 
           public int read(byte[] buffer) throws IOException {
               int count = this.input.read(buffer);
               if (count > 0) {
                   this.output.write(buffer, 0, count);
                        }
               return count;
                    }

                    @Override 
           public int read() throws IOException {
               int b = this.input.read();
               if (b >= 0) {
                   this.output.write(b);
                        }
               return b;
                    }

                    @Override 
           public int read(byte[] buffer, int offset, int length) throws IOException {
               int count = this.input.read(buffer, offset, length);
               if (count > 0) {
                   this.output.write(buffer, offset, count);
                        }
               return count;
                    }

                    @Override 
           public synchronized void reset() {
                        try {
                            throw new UnsupportedOperationException();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public long skip(long byteCount) throws IOException {
                        int count;
               byte[] buffer = new byte[1024];
               long total = 0;
               while (total < byteCount && (count = read(buffer, 0, (int) Math.min(byteCount - total, buffer.length))) >= 0) {
                   total += (long) count;
                        }
               return total;
                    }
                }

       public static final class Limits {
                    private int fileCount = 1024;
                    private int byteCount = 1048576;

           int getByteCount() {
               return this.byteCount;
                    }

           int getFileCount() {
               return this.fileCount;
                    }

           void setByteCount(int n) {
               if (n < 0) {
                   throw new InvalidParameterException("Cache byte-count limit must be >= 0");
                        }
               this.byteCount = n;
                    }

           void setFileCount(int n) {
               if (n < 0) {
                   throw new InvalidParameterException("Cache file count limit must be >= 0");
                        }
               this.fileCount = n;
                    }
                }

       private static final class ModifiedFile implements Comparable<ModifiedFile> {
                    private final File file;
                    private final long modified;


           ModifiedFile(File file) {
               this.file = file;
               this.modified = file.lastModified();
                    }

           File getFile() {
               return this.file;
                    }

           long getModified() {
               return this.modified;
                    }

                    
           public int compareTo(ModifiedFile another) {
                        if (getModified() < another.getModified()) {
                   return -1;
                        }
                        if (getModified() > another.getModified()) {
                   return 1;
                        }
               return getFile().compareTo(another.getFile());
                    }

           public boolean equals(Object another) {
                        return (another instanceof ModifiedFile) && compareTo((ModifiedFile) another) == 0;
                    }
                }
            }
