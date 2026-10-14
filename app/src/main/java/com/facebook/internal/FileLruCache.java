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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public final class FileLruCache {
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

/* 57 */        static /* synthetic */ void access$000(FileLruCache x0, String x1, File x2) {
/* 58 */            x0.renameToTargetAndTrim(x1, x2);
                }

/* 57 */        static /* synthetic */ void access$100(FileLruCache x0) {
/* 58 */            x0.trim();
                }

/* 57 */        static /* synthetic */ AtomicLong access$200() {
/* 58 */            return bufferIndex;
                }

/* 71 */        public FileLruCache(Context context, String tag, Limits limits) {
/* 73 */            this.tag = tag;
/* 74 */            this.limits = limits;
/* 75 */            this.directory = new File(context.getCacheDir(), tag);
/* 79 */            this.directory.mkdirs();
/* 82 */            BufferFile.deleteAll(this.directory);
                }

/* 88 */        public void clearForTest() throws IOException {
/* 89 */            File[] arr$ = this.directory.listFiles();
                    for (File file : arr$) {
/* 90 */                file.delete();
                    }
                }

/* 99 */        long sizeInBytesForTest() {
                    synchronized (this.lock) {
/* 101 */               while (this.isTrimPending) {
                            try {
                                try {
/* 103 */                           this.lock.wait();
                                } catch (InterruptedException e) {
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
/* 110 */           File[] files = this.directory.listFiles();
/* 111 */           long total = 0;
                    for (File file : files) {
/* 113 */               total += file.length();
                    }
/* 115 */           return total;
                }

/* 118 */       public InputStream get(String key) throws IOException {
/* 119 */           return get(key, null);
                }

/* 122 */       public InputStream get(String key, String contentTag) throws IOException {
/* 123 */           File file = new File(this.directory, Utility.md5hash(key));
                    try {
/* 127 */               FileInputStream input = new FileInputStream(file);
/* 132 */               BufferedInputStream buffered = new BufferedInputStream(input, 8192);
                        try {
/* 136 */                   JSONObject header = StreamHeader.readHeader(buffered);
/* 137 */                   if (header == null) {
/* 161 */                       if (0 == 0) {
/* 162 */                           buffered.close();
                                }
/* 138 */                       return null;
                            }
/* 141 */                   String foundKey = header.optString("key");
/* 142 */                   if (foundKey == null || !foundKey.equals(key)) {
/* 161 */                       if (0 == 0) {
/* 162 */                           buffered.close();
                                }
/* 143 */                       return null;
                            }
/* 146 */                   String headerContentTag = header.optString("tag", null);
/* 148 */                   if ((contentTag == null && headerContentTag != null) || (contentTag != null && !contentTag.equals(headerContentTag))) {
/* 161 */                       if (0 == 0) {
/* 162 */                           buffered.close();
                                }
/* 150 */                       return null;
                            }
/* 153 */                   long accessTime = new Date().getTime();
/* 154 */                   Logger.log(LoggingBehavior.CACHE, TAG, "Setting lastModified to " + accessTime + " for " + file.getName());
/* 156 */                   file.setLastModified(accessTime);
/* 161 */                   if (1 == 0) {
/* 162 */                       buffered.close();
                            }
                            return buffered;
                        } catch (Throwable th) {
/* 133 */                   if (0 == 0) {
/* 162 */                       buffered.close();
                            }
                            throw th;
                        }
                    } catch (IOException e) {
/* 129 */               return null;
                    }
                }

/* 167 */       OutputStream openPutStream(String key) throws IOException {
/* 168 */           return openPutStream(key, null);
                }

/* 171 */       public OutputStream openPutStream(String key, String contentTag) throws IOException {
/* 172 */           File buffer = BufferFile.newFile(this.directory);
/* 173 */           buffer.delete();
/* 174 */           if (!buffer.createNewFile()) {
/* 175 */               throw new IOException("Could not create file at " + buffer.getAbsolutePath());
                    }
                    try {
/* 180 */               FileOutputStream file = new FileOutputStream(buffer);
/* 186 */               StreamCloseCallback renameToTargetCallback = new C07761(key, buffer);
/* 193 */               CloseCallbackOutputStream cleanup = new CloseCallbackOutputStream(file, renameToTargetCallback);
/* 194 */               BufferedOutputStream buffered = new BufferedOutputStream(cleanup, 8192);
                        try {
                            try {
/* 199 */                       JSONObject header = new JSONObject();
/* 200 */                       header.put("key", key);
/* 201 */                       if (!Utility.isNullOrEmpty(contentTag)) {
/* 202 */                           header.put("tag", contentTag);
                                }
/* 205 */                       StreamHeader.writeHeader(buffered, header);
/* 214 */                       if (1 == 0) {
/* 215 */                           buffered.close();
                                }
                                return buffered;
                            } catch (JSONException e) {
/* 211 */                       Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating JSON header for cache file: " + e);
/* 212 */                       throw new IOException(e.getMessage());
                            }
                        } catch (Throwable th) {
/* 195 */                   if (0 == 0) {
/* 215 */                       buffered.close();
                            }
                            throw th;
                        }
                    } catch (FileNotFoundException e2) {
/* 182 */               Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating buffer output stream: " + e2);
/* 183 */               throw new IOException(e2.getMessage());
                    }
                }

                /* JADX INFO: renamed from: com.facebook.internal.FileLruCache$1 */
/* 184 */       class C07761 implements StreamCloseCallback {
                    final /* synthetic */ File val$buffer;
                    final /* synthetic */ String val$key;

/* 185 */           C07761(String str, File file) {
                        this.val$key = str;
                        this.val$buffer = file;
                    }

                    @Override // com.facebook.internal.FileLruCache.StreamCloseCallback
/* 188 */           public void onClose() {
/* 189 */               FileLruCache.access$000(FileLruCache.this, this.val$key, this.val$buffer);
                    }
                }

/* 220 */       private void renameToTargetAndTrim(String key, File buffer) {
/* 221 */           File target = new File(this.directory, Utility.md5hash(key));
/* 228 */           if (!buffer.renameTo(target)) {
/* 229 */               buffer.delete();
                    }
/* 232 */           postTrim();
                }

/* 238 */       public InputStream interceptAndPut(String key, InputStream input) throws IOException {
/* 239 */           OutputStream output = openPutStream(key);
/* 240 */           return new CopyingInputStream(input, output);
                }

/* 243 */       public String toString() {
/* 244 */           return "{FileLruCache: tag:" + this.tag + " file:" + this.directory.getName() + "}";
                }

/* 247 */       private void postTrim() {
                    synchronized (this.lock) {
                        try {
/* 249 */                   if (!this.isTrimPending) {
/* 250 */                       this.isTrimPending = true;
/* 251 */                       Settings.getExecutor().execute(new RunnableC07772());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: com.facebook.internal.FileLruCache$2 */
/* 249 */       class RunnableC07772 implements Runnable {
/* 250 */           RunnableC07772() {
                    }

                    @Override // java.lang.Runnable
/* 253 */           public void run() {
/* 254 */               FileLruCache.access$100(FileLruCache.this);
                    }
                }

/* 262 */       private void trim() {
                    try {
/* 263 */               Logger.log(LoggingBehavior.CACHE, TAG, "trim started");
/* 264 */               PriorityQueue<ModifiedFile> heap = new PriorityQueue<>();
/* 265 */               long size = 0;
/* 266 */               long count = 0;
/* 267 */               File[] arr$ = this.directory.listFiles(BufferFile.excludeBufferFiles());
                        for (File file : arr$) {
/* 268 */                   ModifiedFile modified = new ModifiedFile(file);
/* 269 */                   heap.add(modified);
/* 270 */                   Logger.log(LoggingBehavior.CACHE, TAG, "  trim considering time=" + modified.getModified() + " name=" + modified.getFile().getName());
/* 273 */                   size += file.length();
/* 274 */                   count++;
                        }
                        while (true) {
                            if (size > this.limits.getByteCount() || count > this.limits.getFileCount()) {
/* 278 */                       File file2 = heap.remove().getFile();
/* 279 */                       Logger.log(LoggingBehavior.CACHE, TAG, "  trim removing " + file2.getName());
/* 280 */                       size -= file2.length();
/* 281 */                       count--;
/* 282 */                       file2.delete();
                            } else {
                                synchronized (this.lock) {
                                    try {
/* 286 */                               this.isTrimPending = false;
/* 287 */                               this.lock.notifyAll();
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
/* 290 */                       return;
                            }
                        }
                    } catch (Throwable th2) {
                        synchronized (this.lock) {
                            try {
/* 286 */                       this.isTrimPending = false;
/* 287 */                       this.lock.notifyAll();
                                throw th2;
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }
                }

/* 290 */       private static class BufferFile {
                    private static final String FILE_NAME_PREFIX = "buffer";
                    private static final FilenameFilter filterExcludeBufferFiles = new C07781();
                    private static final FilenameFilter filterExcludeNonBufferFiles = new C07792();

/* 291 */           private BufferFile() {
                    }

                    /* JADX INFO: renamed from: com.facebook.internal.FileLruCache$BufferFile$1 */
/* 292 */           static class C07781 implements FilenameFilter {
/* 293 */               C07781() {
                        }

                        @Override // java.io.FilenameFilter
/* 296 */               public boolean accept(File dir, String filename) {
                            return !filename.startsWith(BufferFile.FILE_NAME_PREFIX);
                        }
                    }

                    /* JADX INFO: renamed from: com.facebook.internal.FileLruCache$BufferFile$2 */
/* 298 */           static class C07792 implements FilenameFilter {
/* 299 */               C07792() {
                        }

                        @Override // java.io.FilenameFilter
/* 302 */               public boolean accept(File dir, String filename) {
/* 303 */                   return filename.startsWith(BufferFile.FILE_NAME_PREFIX);
                        }
                    }

/* 307 */           static void deleteAll(File root) {
/* 308 */               File[] arr$ = root.listFiles(excludeNonBufferFiles());
                        for (File file : arr$) {
/* 309 */                   file.delete();
                        }
                    }

/* 313 */           static FilenameFilter excludeBufferFiles() {
/* 314 */               return filterExcludeBufferFiles;
                    }

/* 317 */           static FilenameFilter excludeNonBufferFiles() {
/* 318 */               return filterExcludeNonBufferFiles;
                    }

/* 321 */           static File newFile(File root) {
/* 322 */               String name = FILE_NAME_PREFIX + Long.valueOf(FileLruCache.access$200().incrementAndGet()).toString();
/* 323 */               return new File(root, name);
                    }
                }

/* 335 */       private static final class StreamHeader {
                    private static final int HEADER_VERSION = 0;

/* 336 */           private StreamHeader() {
                    }

/* 340 */           static void writeHeader(OutputStream stream, JSONObject header) throws IOException {
/* 341 */               String headerString = header.toString();
/* 342 */               byte[] headerBytes = headerString.getBytes();
/* 345 */               stream.write(0);
/* 346 */               stream.write((headerBytes.length >> 16) & 255);
/* 347 */               stream.write((headerBytes.length >> 8) & 255);
/* 348 */               stream.write((headerBytes.length >> 0) & 255);
/* 350 */               stream.write(headerBytes);
                    }

/* 353 */           static JSONObject readHeader(InputStream stream) throws IOException {
                        JSONObject header;
/* 354 */               int version = stream.read();
/* 355 */               if (version != 0) {
/* 356 */                   return null;
                        }
/* 359 */               int headerSize = 0;
                        for (int i = 0; i < 3; i++) {
/* 361 */                   int b = stream.read();
/* 362 */                   if (b == -1) {
/* 363 */                       Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
/* 365 */                       return null;
                            }
/* 368 */                   headerSize = (headerSize << 8) + (b & 255);
                        }
/* 371 */               byte[] headerBytes = new byte[headerSize];
/* 372 */               int count = 0;
/* 373 */               while (count < headerBytes.length) {
/* 374 */                   int readCount = stream.read(headerBytes, count, headerBytes.length - count);
/* 375 */                   if (readCount < 1) {
/* 376 */                       Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read stopped at " + count + " when expected " + headerBytes.length);
/* 379 */                       return null;
                            }
/* 381 */                   count += readCount;
                        }
/* 384 */               String headerString = new String(headerBytes);
/* 386 */               JSONTokener tokener = new JSONTokener(headerString);
                        try {
/* 388 */                   Object parsed = tokener.nextValue();
/* 389 */                   if (!(parsed instanceof JSONObject)) {
/* 390 */                       Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: expected JSONObject, got " + parsed.getClass().getCanonicalName());
/* 391 */                       header = null;
                            } else {
/* 393 */                       header = (JSONObject) parsed;
                            }
/* 398 */                   return header;
                        } catch (JSONException e) {
/* 395 */                   throw new IOException(e.getMessage());
                        }
                    }
                }

/* 404 */       private static class CloseCallbackOutputStream extends OutputStream {
                    final StreamCloseCallback callback;
                    final OutputStream innerStream;

/* 405 */           CloseCallbackOutputStream(OutputStream innerStream, StreamCloseCallback callback) {
/* 407 */               this.innerStream = innerStream;
/* 408 */               this.callback = callback;
                    }

                    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 413 */           public void close() throws IOException {
                        try {
/* 414 */                   this.innerStream.close();
/* 416 */                   this.callback.onClose();
                        } catch (Throwable th) {
                            this.callback.onClose();
                            throw th;
                        }
                    }

                    @Override // java.io.OutputStream, java.io.Flushable
/* 421 */           public void flush() throws IOException {
/* 422 */               this.innerStream.flush();
                    }

                    @Override // java.io.OutputStream
/* 426 */           public void write(byte[] buffer, int offset, int count) throws IOException {
/* 427 */               this.innerStream.write(buffer, offset, count);
                    }

                    @Override // java.io.OutputStream
/* 431 */           public void write(byte[] buffer) throws IOException {
/* 432 */               this.innerStream.write(buffer);
                    }

                    @Override // java.io.OutputStream
/* 436 */           public void write(int oneByte) throws IOException {
/* 437 */               this.innerStream.write(oneByte);
                    }
                }

/* 443 */       private static final class CopyingInputStream extends InputStream {
                    final InputStream input;
                    final OutputStream output;

/* 444 */           CopyingInputStream(InputStream input, OutputStream output) {
/* 446 */               this.input = input;
/* 447 */               this.output = output;
                    }

                    @Override // java.io.InputStream
/* 451 */           public int available() throws IOException {
/* 452 */               return this.input.available();
                    }

                    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 463 */           public void close() throws IOException {
                        try {
/* 464 */                   this.input.close();
/* 466 */                   this.output.close();
                        } catch (Throwable th) {
                            this.output.close();
                            throw th;
                        }
                    }

                    @Override // java.io.InputStream
/* 471 */           public void mark(int readlimit) {
/* 472 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.io.InputStream
/* 476 */           public boolean markSupported() {
/* 477 */               return false;
                    }

                    @Override // java.io.InputStream
/* 481 */           public int read(byte[] buffer) throws IOException {
/* 482 */               int count = this.input.read(buffer);
/* 483 */               if (count > 0) {
/* 484 */                   this.output.write(buffer, 0, count);
                        }
/* 486 */               return count;
                    }

                    @Override // java.io.InputStream
/* 490 */           public int read() throws IOException {
/* 491 */               int b = this.input.read();
/* 492 */               if (b >= 0) {
/* 493 */                   this.output.write(b);
                        }
/* 495 */               return b;
                    }

                    @Override // java.io.InputStream
/* 499 */           public int read(byte[] buffer, int offset, int length) throws IOException {
/* 500 */               int count = this.input.read(buffer, offset, length);
/* 501 */               if (count > 0) {
/* 502 */                   this.output.write(buffer, offset, count);
                        }
/* 504 */               return count;
                    }

                    @Override // java.io.InputStream
/* 508 */           public synchronized void reset() {
                        try {
                            throw new UnsupportedOperationException();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // java.io.InputStream
/* 513 */           public long skip(long byteCount) throws IOException {
                        int count;
/* 514 */               byte[] buffer = new byte[1024];
/* 515 */               long total = 0;
/* 518 */               while (total < byteCount && (count = read(buffer, 0, (int) Math.min(byteCount - total, buffer.length))) >= 0) {
/* 521 */                   total += (long) count;
                        }
/* 523 */               return total;
                    }
                }

/* 542 */       public static final class Limits {
                    private int fileCount = 1024;
                    private int byteCount = 1048576;

/* 543 */           int getByteCount() {
/* 544 */               return this.byteCount;
                    }

/* 547 */           int getFileCount() {
/* 548 */               return this.fileCount;
                    }

/* 551 */           void setByteCount(int n) {
/* 552 */               if (n < 0) {
/* 553 */                   throw new InvalidParameterException("Cache byte-count limit must be >= 0");
                        }
/* 555 */               this.byteCount = n;
                    }

/* 558 */           void setFileCount(int n) {
/* 559 */               if (n < 0) {
/* 560 */                   throw new InvalidParameterException("Cache file count limit must be >= 0");
                        }
/* 562 */               this.fileCount = n;
                    }
                }

/* 565 */       private static final class ModifiedFile implements Comparable<ModifiedFile> {
                    private final File file;
                    private final long modified;


/* 570 */           ModifiedFile(File file) {
/* 572 */               this.file = file;
/* 573 */               this.modified = file.lastModified();
                    }

/* 576 */           File getFile() {
/* 577 */               return this.file;
                    }

/* 580 */           long getModified() {
/* 581 */               return this.modified;
                    }

                    /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 585 */           public int compareTo(ModifiedFile another) {
                        if (getModified() < another.getModified()) {
/* 587 */                   return -1;
                        }
                        if (getModified() > another.getModified()) {
/* 589 */                   return 1;
                        }
/* 591 */               return getFile().compareTo(another.getFile());
                    }

/* 596 */           public boolean equals(Object another) {
                        return (another instanceof ModifiedFile) && compareTo((ModifiedFile) another) == 0;
                    }
                }
            }
