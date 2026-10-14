            package com.squareup.okhttp.internal;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.Closeable;
            import java.io.EOFException;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.IOException;
            import java.util.Arrays;
            import java.util.Iterator;
            import java.util.LinkedHashMap;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import okio.Buffer;
            import okio.BufferedSink;
            import okio.BufferedSource;
            import okio.ForwardingSink;
            import okio.Okio;
            import okio.Sink;
            import okio.Source;
            import okio.Timeout;
            import org.apache.commons.io.FilenameUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 83 */    public final class DiskLruCache implements Closeable {
                static final long ANY_SEQUENCE_NUMBER = -1;
                private static final String CLEAN = "CLEAN";
                private static final String DIRTY = "DIRTY";
                static final String JOURNAL_FILE = "journal";
                static final String JOURNAL_FILE_BACKUP = "journal.bkp";
                static final String JOURNAL_FILE_TEMP = "journal.tmp";
                static final String MAGIC = "libcore.io.DiskLruCache";
                private static final String READ = "READ";
                private static final String REMOVE = "REMOVE";
                static final String VERSION_1 = "1";
                private final int appVersion;
                private final File directory;
                private final File journalFile;
                private final File journalFileBackup;
                private final File journalFileTmp;
                private BufferedSink journalWriter;
                private long maxSize;
                private int redundantOpCount;
                private final int valueCount;
                static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
                private static final Sink NULL_SINK = new C12702();
                private long size = 0;
                private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
                private long nextSequenceNumber = 0;
                final ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true));
                private final Runnable cleanupRunnable = new RunnableC12691();

/* 84 */        static /* synthetic */ BufferedSink access$000(DiskLruCache x0) {
/* 85 */            return x0.journalWriter;
                }

/* 84 */        static /* synthetic */ void access$100(DiskLruCache x0) throws IOException {
/* 85 */            x0.trimToSize();
                }

/* 84 */        static /* synthetic */ Editor access$1900(DiskLruCache x0, String x1, long x2) throws IOException {
/* 85 */            return x0.edit(x1, x2);
                }

/* 84 */        static /* synthetic */ boolean access$200(DiskLruCache x0) {
/* 85 */            return x0.journalRebuildRequired();
                }

/* 84 */        static /* synthetic */ String access$2000(Source x0) throws IOException {
/* 85 */            return sourceToString(x0);
                }

/* 84 */        static /* synthetic */ int access$2100(DiskLruCache x0) {
/* 85 */            return x0.valueCount;
                }

/* 84 */        static /* synthetic */ File access$2200(DiskLruCache x0) {
/* 85 */            return x0.directory;
                }

/* 84 */        static /* synthetic */ Sink access$2300() {
/* 85 */            return NULL_SINK;
                }

/* 84 */        static /* synthetic */ void access$2400(DiskLruCache x0, Editor x1, boolean x2) throws IOException {
/* 85 */            x0.completeEdit(x1, x2);
                }

/* 84 */        static /* synthetic */ boolean access$2500(DiskLruCache x0, Entry x1) throws IOException {
/* 85 */            return x0.removeEntry(x1);
                }

/* 84 */        static /* synthetic */ void access$300(DiskLruCache x0) throws IOException {
/* 85 */            x0.rebuildJournal();
                }

/* 84 */        static /* synthetic */ int access$402(DiskLruCache x0, int x1) {
/* 85 */            x0.redundantOpCount = x1;
                    return x1;
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.DiskLruCache$1 */
/* 158 */       class RunnableC12691 implements Runnable {
/* 159 */           RunnableC12691() {
                    }

                    @Override // java.lang.Runnable
/* 161 */           public void run() {
                        synchronized (DiskLruCache.this) {
                            try {
/* 163 */                       if (DiskLruCache.access$000(DiskLruCache.this) != null) {
                                    try {
/* 167 */                               DiskLruCache.access$100(DiskLruCache.this);
/* 168 */                               if (DiskLruCache.access$200(DiskLruCache.this)) {
/* 169 */                                   DiskLruCache.access$300(DiskLruCache.this);
/* 170 */                                   DiskLruCache.access$402(DiskLruCache.this, 0);
                                        }
                                    } catch (IOException e) {
/* 173 */                               throw new RuntimeException(e);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 178 */       private DiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
/* 180 */           this.directory = directory;
/* 181 */           this.appVersion = appVersion;
/* 182 */           this.journalFile = new File(directory, JOURNAL_FILE);
/* 183 */           this.journalFileTmp = new File(directory, JOURNAL_FILE_TEMP);
/* 184 */           this.journalFileBackup = new File(directory, JOURNAL_FILE_BACKUP);
/* 185 */           this.valueCount = valueCount;
/* 186 */           this.maxSize = maxSize;
                }

/* 199 */       public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize) throws IOException {
                    if (maxSize <= 0) {
/* 201 */               throw new IllegalArgumentException("maxSize <= 0");
                    }
/* 203 */           if (valueCount <= 0) {
/* 204 */               throw new IllegalArgumentException("valueCount <= 0");
                    }
/* 208 */           File backupFile = new File(directory, JOURNAL_FILE_BACKUP);
/* 209 */           if (backupFile.exists()) {
/* 210 */               File journalFile = new File(directory, JOURNAL_FILE);
/* 212 */               if (journalFile.exists()) {
/* 213 */                   backupFile.delete();
                        } else {
/* 215 */                   renameTo(backupFile, journalFile, false);
                        }
                    }
/* 220 */           DiskLruCache cache = new DiskLruCache(directory, appVersion, valueCount, maxSize);
/* 221 */           if (cache.journalFile.exists()) {
                        try {
/* 223 */                   cache.readJournal();
/* 224 */                   cache.processJournal();
                            return cache;
                        } catch (IOException journalIsCorrupt) {
/* 227 */                   Platform.get().logW("DiskLruCache " + directory + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
/* 229 */                   cache.delete();
                        }
                    }
/* 234 */           directory.mkdirs();
/* 235 */           DiskLruCache cache2 = new DiskLruCache(directory, appVersion, valueCount, maxSize);
/* 236 */           cache2.rebuildJournal();
                    return cache2;
                }

/* 240 */       private void readJournal() throws IOException {
/* 241 */           BufferedSource source = Okio.buffer(Okio.source(this.journalFile));
                    try {
/* 243 */               String magic = source.readUtf8LineStrict();
/* 244 */               String version = source.readUtf8LineStrict();
/* 245 */               String appVersionString = source.readUtf8LineStrict();
/* 246 */               String valueCountString = source.readUtf8LineStrict();
/* 247 */               String blank = source.readUtf8LineStrict();
/* 248 */               if (!MAGIC.equals(magic) || !VERSION_1.equals(version) || !Integer.toString(this.appVersion).equals(appVersionString) || !Integer.toString(this.valueCount).equals(valueCountString) || !"".equals(blank)) {
/* 253 */                   throw new IOException("unexpected journal header: [" + magic + ", " + version + ", " + valueCountString + ", " + blank + "]");
                        }
/* 257 */               int lineCount = 0;
                        while (true) {
                            try {
/* 260 */                       readJournalLine(source.readUtf8LineStrict());
/* 261 */                       lineCount++;
                            } catch (EOFException e) {
/* 266 */                       this.redundantOpCount = lineCount - this.lruEntries.size();
/* 269 */                       if (!source.exhausted()) {
/* 270 */                           rebuildJournal();
                                } else {
/* 272 */                           this.journalWriter = Okio.buffer(Okio.appendingSink(this.journalFile));
                                }
/* 275 */                       Util.closeQuietly(source);
/* 277 */                       return;
                            }
                        }
                    } catch (Throwable th) {
                        Util.closeQuietly(source);
                        throw th;
                    }
                }

/* 279 */       private void readJournalLine(String line) throws IOException {
                    String key;
/* 280 */           int firstSpace = line.indexOf(32);
/* 281 */           if (firstSpace == -1) {
/* 282 */               throw new IOException("unexpected journal line: " + line);
                    }
/* 285 */           int keyBegin = firstSpace + 1;
/* 286 */           int secondSpace = line.indexOf(32, keyBegin);
/* 288 */           if (secondSpace == -1) {
/* 289 */               key = line.substring(keyBegin);
/* 290 */               if (firstSpace == REMOVE.length() && line.startsWith(REMOVE)) {
/* 291 */                   this.lruEntries.remove(key);
/* 316 */                   return;
                        }
                    } else {
/* 295 */               key = line.substring(keyBegin, secondSpace);
                    }
/* 298 */           Entry entry = this.lruEntries.get(key);
/* 299 */           if (entry == null) {
/* 300 */               entry = new Entry(this, key, null);
/* 301 */               this.lruEntries.put(key, entry);
                    }
/* 304 */           if (secondSpace != -1 && firstSpace == CLEAN.length() && line.startsWith(CLEAN)) {
/* 305 */               String[] parts = line.substring(secondSpace + 1).split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 306 */               Entry.access$602(entry, true);
/* 307 */               Entry.access$702(entry, null);
/* 308 */               Entry.access$800(entry, parts);
/* 316 */               return;
                    }
/* 309 */           if (secondSpace == -1 && firstSpace == DIRTY.length() && line.startsWith(DIRTY)) {
/* 310 */               Entry.access$702(entry, new Editor(this, entry, null));
                    } else if (secondSpace != -1 || firstSpace != READ.length() || !line.startsWith(READ)) {
/* 314 */               throw new IOException("unexpected journal line: " + line);
                    }
                }

/* 322 */       private void processJournal() throws IOException {
/* 323 */           deleteIfExists(this.journalFileTmp);
/* 324 */           Iterator<Entry> i = this.lruEntries.values().iterator();
                    while (i.hasNext()) {
/* 325 */               Entry entry = i.next();
/* 326 */               if (Entry.access$700(entry) == null) {
                            for (int t = 0; t < this.valueCount; t++) {
/* 328 */                       this.size += Entry.access$1000(entry)[t];
                            }
                        } else {
/* 331 */                   Entry.access$702(entry, null);
                            for (int t2 = 0; t2 < this.valueCount; t2++) {
/* 333 */                       deleteIfExists(Entry.access$1100(entry)[t2]);
/* 334 */                       deleteIfExists(Entry.access$1200(entry)[t2]);
                            }
/* 336 */                   i.remove();
                        }
                    }
                }

/* 345 */       private synchronized void rebuildJournal() throws IOException {
                    try {
                        if (this.journalWriter != null) {
/* 347 */                   this.journalWriter.close();
                        }
/* 350 */               BufferedSink writer = Okio.buffer(Okio.sink(this.journalFileTmp));
                        try {
/* 352 */                   writer.writeUtf8(MAGIC).writeByte(10);
/* 353 */                   writer.writeUtf8(VERSION_1).writeByte(10);
/* 354 */                   writer.writeUtf8(Integer.toString(this.appVersion)).writeByte(10);
/* 355 */                   writer.writeUtf8(Integer.toString(this.valueCount)).writeByte(10);
/* 356 */                   writer.writeByte(10);
                            for (Entry entry : this.lruEntries.values()) {
/* 359 */                       if (Entry.access$700(entry) != null) {
/* 360 */                           writer.writeUtf8(DIRTY).writeByte(32);
/* 361 */                           writer.writeUtf8(Entry.access$1300(entry));
/* 362 */                           writer.writeByte(10);
                                } else {
/* 364 */                           writer.writeUtf8(CLEAN).writeByte(32);
/* 365 */                           writer.writeUtf8(Entry.access$1300(entry));
/* 366 */                           writer.writeUtf8(entry.getLengths());
/* 367 */                           writer.writeByte(10);
                                }
                            }
/* 371 */                   writer.close();
/* 374 */                   if (this.journalFile.exists()) {
/* 375 */                       renameTo(this.journalFile, this.journalFileBackup, true);
                            }
/* 377 */                   renameTo(this.journalFileTmp, this.journalFile, false);
/* 378 */                   this.journalFileBackup.delete();
/* 380 */                   this.journalWriter = Okio.buffer(Okio.appendingSink(this.journalFile));
                        } catch (Throwable th) {
                            writer.close();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 384 */       private static void deleteIfExists(File file) throws IOException {
/* 385 */           if (!file.delete() && file.exists()) {
/* 386 */               throw new IOException("failed to delete " + file);
                    }
                }

/* 390 */       private static void renameTo(File from, File to, boolean deleteDestination) throws IOException {
/* 391 */           if (deleteDestination) {
/* 392 */               deleteIfExists(to);
                    }
/* 394 */           if (!from.renameTo(to)) {
/* 395 */               throw new IOException();
                    }
                }

/* 404 */       public synchronized Snapshot get(String key) throws IOException {
                    Snapshot snapshot = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
/* 406 */                   validateKey(key);
/* 407 */                   Entry entry = this.lruEntries.get(key);
/* 408 */                   if (entry != null && Entry.access$600(entry)) {
/* 419 */                       Source[] sources = new Source[this.valueCount];
                                for (int i = 0; i < this.valueCount; i++) {
                                    try {
/* 422 */                               sources[i] = Okio.source(Entry.access$1100(entry)[i]);
                                    } catch (FileNotFoundException e) {
/* 427 */                               for (int i2 = 0; i2 < this.valueCount && sources[i2] != null; i2++) {
/* 428 */                                   Util.closeQuietly(sources[i2]);
                                        }
                                    }
                                }
/* 436 */                       this.redundantOpCount++;
/* 437 */                       this.journalWriter.writeUtf8(READ).writeByte(32).writeUtf8(key).writeByte(10);
/* 438 */                       if (journalRebuildRequired()) {
/* 439 */                           this.executorService.execute(this.cleanupRunnable);
                                }
/* 442 */                       snapshot = new Snapshot(this, key, Entry.access$1400(entry), sources, Entry.access$1000(entry), null);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return snapshot;
                }

/* 449 */       public Editor edit(String key) throws IOException {
/* 450 */           return edit(key, -1L);
                }

/* 453 */       private synchronized Editor edit(String key, long expectedSequenceNumber) throws IOException {
                    Editor editor = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
/* 455 */                   validateKey(key);
/* 456 */                   Entry entry = this.lruEntries.get(key);
                            if (expectedSequenceNumber == -1 || (entry != null && Entry.access$1400(entry) == expectedSequenceNumber)) {
/* 461 */                       if (entry == null) {
/* 462 */                           entry = new Entry(this, key, null);
/* 463 */                           this.lruEntries.put(key, entry);
                                } else if (Entry.access$700(entry) == null) {
                                }
/* 468 */                       editor = new Editor(this, entry, null);
/* 469 */                       Entry.access$702(entry, editor);
/* 472 */                       this.journalWriter.writeUtf8(DIRTY).writeByte(32).writeUtf8(key).writeByte(10);
/* 473 */                       this.journalWriter.flush();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return editor;
                }

/* 478 */       public File getDirectory() {
/* 479 */           return this.directory;
                }

/* 486 */       public synchronized long getMaxSize() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxSize;
                }

/* 494 */       public synchronized void setMaxSize(long maxSize) {
                    try {
                        this.maxSize = maxSize;
/* 496 */               this.executorService.execute(this.cleanupRunnable);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 504 */       public synchronized long size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.size;
                }

                /* JADX WARN: Code duplicated, block: B:29:0x005e A[Catch: all -> 0x0011, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:31:0x0066 A[Catch: all -> 0x0011, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:33:0x006c A[Catch: all -> 0x0011, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:35:0x008e A[Catch: all -> 0x0011, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:38:0x00a3 A[Catch: all -> 0x0011, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:40:0x00cf A[Catch: all -> 0x0011, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:45:0x00ec A[Catch: all -> 0x0011, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:46:0x00f5 A[Catch: all -> 0x0011, TRY_LEAVE, TryCatch #0 {all -> 0x0011, blocks: (B:3:0x0001, B:5:0x000b, B:6:0x0010, B:11:0x0016, B:14:0x001d, B:16:0x0021, B:18:0x0029, B:19:0x0044, B:20:0x0045, B:22:0x0051, B:27:0x005a, B:29:0x005e, B:31:0x0066, B:33:0x006c, B:34:0x008b, B:35:0x008e, B:36:0x0092, B:38:0x00a3, B:40:0x00cf, B:41:0x00d9, B:43:0x00e6, B:45:0x00ec, B:46:0x00f5), top: B:48:0x0001 }] */
                /* JADX WARN: Code duplicated, block: B:54:0x008b A[SYNTHETIC] */
/* 508 */       private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
                    int i = 0;
                    File dirty;
                    try {
                        Entry entry = Editor.access$1600(editor);
/* 510 */               if (Entry.access$700(entry) != editor) {
/* 511 */                   throw new IllegalStateException();
                        }
/* 515 */               if (success && !Entry.access$600(entry)) {
                            for (int i2 = 0; i2 < this.valueCount; i2++) {
/* 517 */                       if (!Editor.access$1700(editor)[i2]) {
/* 518 */                           editor.abort();
/* 519 */                           throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                                }
/* 521 */                       if (!Entry.access$1200(entry)[i2].exists()) {
/* 522 */                           editor.abort();
                                }
                            }
                            for (i = 0; i < this.valueCount; i++) {
/* 529 */                       dirty = Entry.access$1200(entry)[i];
/* 530 */                       if (success) {
/* 531 */                           if (dirty.exists()) {
/* 532 */                               File clean = Entry.access$1100(entry)[i];
/* 533 */                               dirty.renameTo(clean);
/* 534 */                               long oldLength = Entry.access$1000(entry)[i];
/* 535 */                               long newLength = clean.length();
/* 536 */                               Entry.access$1000(entry)[i] = newLength;
/* 537 */                               this.size = (this.size - oldLength) + newLength;
                                    }
                                } else {
/* 540 */                           deleteIfExists(dirty);
                                }
                            }
/* 544 */                   this.redundantOpCount++;
/* 545 */                   Entry.access$702(entry, null);
/* 546 */                   if (Entry.access$600(entry) | success) {
/* 547 */                       Entry.access$602(entry, true);
/* 548 */                       this.journalWriter.writeUtf8(CLEAN).writeByte(32);
/* 549 */                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
/* 550 */                       this.journalWriter.writeUtf8(entry.getLengths());
/* 551 */                       this.journalWriter.writeByte(10);
/* 552 */                       if (success) {
/* 553 */                           long j = this.nextSequenceNumber;
                                    this.nextSequenceNumber = 1 + j;
                                    Entry.access$1402(entry, j);
                                }
                            } else {
/* 556 */                       this.lruEntries.remove(Entry.access$1300(entry));
/* 557 */                       this.journalWriter.writeUtf8(REMOVE).writeByte(32);
/* 558 */                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
/* 559 */                       this.journalWriter.writeByte(10);
                            }
/* 561 */                   this.journalWriter.flush();
                            if (this.size <= this.maxSize) {
/* 564 */                       this.executorService.execute(this.cleanupRunnable);
                            } else {
/* 564 */                       this.executorService.execute(this.cleanupRunnable);
                            }
                        } else {
                            while (i < this.valueCount) {
/* 529 */                       dirty = Entry.access$1200(entry)[i];
/* 530 */                       if (success) {
/* 531 */                           if (dirty.exists()) {
/* 532 */                               File clean2 = Entry.access$1100(entry)[i];
/* 533 */                               dirty.renameTo(clean2);
/* 534 */                               long oldLength2 = Entry.access$1000(entry)[i];
/* 535 */                               long newLength2 = clean2.length();
/* 536 */                               Entry.access$1000(entry)[i] = newLength2;
/* 537 */                               this.size = (this.size - oldLength2) + newLength2;
                                    }
                                } else {
/* 540 */                           deleteIfExists(dirty);
                                }
                            }
/* 544 */                   this.redundantOpCount++;
/* 545 */                   Entry.access$702(entry, null);
/* 546 */                   if (Entry.access$600(entry) | success) {
/* 547 */                       Entry.access$602(entry, true);
/* 548 */                       this.journalWriter.writeUtf8(CLEAN).writeByte(32);
/* 549 */                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
/* 550 */                       this.journalWriter.writeUtf8(entry.getLengths());
/* 551 */                       this.journalWriter.writeByte(10);
/* 552 */                       if (success) {
/* 553 */                           long j2 = this.nextSequenceNumber;
                                    this.nextSequenceNumber = 1 + j2;
                                    Entry.access$1402(entry, j2);
                                }
                            } else {
/* 556 */                       this.lruEntries.remove(Entry.access$1300(entry));
/* 557 */                       this.journalWriter.writeUtf8(REMOVE).writeByte(32);
/* 558 */                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
/* 559 */                       this.journalWriter.writeByte(10);
                            }
/* 561 */                   this.journalWriter.flush();
                            if (this.size <= this.maxSize || journalRebuildRequired()) {
/* 564 */                       this.executorService.execute(this.cleanupRunnable);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 572 */       private boolean journalRebuildRequired() {
                    return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
                }

/* 585 */       public synchronized boolean remove(String key) throws IOException {
                    Entry entry;
                    try {
                        checkNotClosed();
/* 587 */               validateKey(key);
/* 588 */               entry = this.lruEntries.get(key);
                    } catch (Throwable th) {
                        throw th;
                    }
/* 589 */           return entry == null ? false : removeEntry(entry);
                }

/* 593 */       private boolean removeEntry(Entry entry) throws IOException {
/* 594 */           if (Entry.access$700(entry) != null) {
/* 595 */               Editor.access$1802(Entry.access$700(entry), true);
                    }
                    for (int i = 0; i < this.valueCount; i++) {
/* 599 */               File file = Entry.access$1100(entry)[i];
/* 600 */               deleteIfExists(file);
/* 601 */               this.size -= Entry.access$1000(entry)[i];
/* 602 */               Entry.access$1000(entry)[i] = 0;
                    }
/* 605 */           this.redundantOpCount++;
/* 606 */           this.journalWriter.writeUtf8(REMOVE).writeByte(32).writeUtf8(Entry.access$1300(entry)).writeByte(10);
/* 607 */           this.lruEntries.remove(Entry.access$1300(entry));
/* 609 */           if (journalRebuildRequired()) {
/* 610 */               this.executorService.execute(this.cleanupRunnable);
                    }
                    return true;
                }

/* 617 */       public boolean isClosed() {
                    return this.journalWriter == null;
                }

/* 621 */       private void checkNotClosed() {
/* 622 */           if (this.journalWriter == null) {
/* 623 */               throw new IllegalStateException("cache is closed");
                    }
                }

/* 628 */       public synchronized void flush() throws IOException {
                    try {
                        checkNotClosed();
/* 630 */               trimToSize();
/* 631 */               this.journalWriter.flush();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.Closeable, java.lang.AutoCloseable
/* 635 */       public synchronized void close() throws IOException {
                    try {
                        if (this.journalWriter != null) {
/* 640 */                   Entry[] arr$ = (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
                            for (Entry entry : arr$) {
/* 641 */                       if (Entry.access$700(entry) != null) {
/* 642 */                           Entry.access$700(entry).abort();
                                }
                            }
/* 645 */                   trimToSize();
/* 646 */                   this.journalWriter.close();
/* 647 */                   this.journalWriter = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 650 */       private void trimToSize() throws IOException {
                    while (this.size > this.maxSize) {
/* 652 */               Entry toEvict = this.lruEntries.values().iterator().next();
/* 653 */               removeEntry(toEvict);
                    }
                }

/* 662 */       public void delete() throws IOException {
/* 663 */           close();
/* 664 */           Util.deleteContents(this.directory);
                }

/* 672 */       public synchronized void evictAll() throws IOException {
                    try {
                        Entry[] arr$ = (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
                        for (Entry entry : arr$) {
/* 674 */                   removeEntry(entry);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 678 */       private void validateKey(String key) {
/* 679 */           Matcher matcher = LEGAL_KEY_PATTERN.matcher(key);
/* 680 */           if (!matcher.matches()) {
/* 681 */               throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + key + "\"");
                    }
                }

/* 687 */       private static String sourceToString(Source in) throws IOException {
                    try {
/* 688 */               String utf8 = Okio.buffer(in).readUtf8();
/* 690 */               Util.closeQuietly(in);
                        return utf8;
                    } catch (Throwable th) {
                        Util.closeQuietly(in);
                        throw th;
                    }
                }

/* 693 */       public final class Snapshot implements Closeable {
                    private final String key;
                    private final long[] lengths;
                    private final long sequenceNumber;
                    private final Source[] sources;

/* 694 */           /* synthetic */ Snapshot(DiskLruCache x0, String x1, long x2, Source[] x3, long[] x4, RunnableC12691 x5) {
/* 695 */               this(x1, x2, x3, x4);
                    }

/* 700 */           private Snapshot(String key, long sequenceNumber, Source[] sources, long[] lengths) {
/* 702 */               this.key = key;
/* 703 */               this.sequenceNumber = sequenceNumber;
/* 704 */               this.sources = sources;
/* 705 */               this.lengths = lengths;
                    }

/* 713 */           public Editor edit() throws IOException {
/* 714 */               return DiskLruCache.access$1900(DiskLruCache.this, this.key, this.sequenceNumber);
                    }

/* 718 */           public Source getSource(int index) {
/* 719 */               return this.sources[index];
                    }

/* 723 */           public String getString(int index) throws IOException {
/* 724 */               return DiskLruCache.access$2000(getSource(index));
                    }

/* 728 */           public long getLength(int index) {
/* 729 */               return this.lengths[index];
                    }

                    @Override // java.io.Closeable, java.lang.AutoCloseable
/* 732 */           public void close() {
/* 733 */               Source[] arr$ = this.sources;
                        for (Source in : arr$) {
/* 734 */                   Util.closeQuietly(in);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.DiskLruCache$2 */
/* 737 */       static class C12702 implements Sink {
/* 738 */           C12702() {
                    }

                    @Override // okio.Sink
/* 741 */           public void write(Buffer source, long byteCount) throws IOException {
                    }

                    @Override // okio.Sink
/* 744 */           public void flush() throws IOException {
                    }

                    @Override // okio.Sink
/* 747 */           public Timeout timeout() {
/* 748 */               return Timeout.NONE;
                    }

                    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 751 */           public void close() throws IOException {
                    }
                }

/* 754 */       public final class Editor {
                    private boolean committed;
                    private final Entry entry;
                    private boolean hasErrors;
                    private final boolean[] written;

/* 755 */           /* synthetic */ Editor(DiskLruCache x0, Entry x1, RunnableC12691 x2) {
/* 756 */               this(x1);
                    }

/* 755 */           static /* synthetic */ Entry access$1600(Editor x0) {
/* 756 */               return x0.entry;
                    }

/* 755 */           static /* synthetic */ boolean[] access$1700(Editor x0) {
/* 756 */               return x0.written;
                    }

/* 755 */           static /* synthetic */ boolean access$1802(Editor x0, boolean x1) {
/* 756 */               x0.hasErrors = x1;
                        return x1;
                    }

/* 761 */           private Editor(Entry entry) {
/* 763 */               this.entry = entry;
/* 764 */               this.written = Entry.access$600(entry) ? null : new boolean[DiskLruCache.access$2100(DiskLruCache.this)];
                    }

/* 771 */           public Source newSource(int index) throws IOException {
                        Source source = null;
                        synchronized (DiskLruCache.this) {
                            try {
/* 773 */                       if (Entry.access$700(this.entry) != this) {
/* 774 */                           throw new IllegalStateException();
                                }
/* 776 */                       if (Entry.access$600(this.entry)) {
                                    try {
/* 780 */                               source = Okio.source(Entry.access$1100(this.entry)[index]);
                                    } catch (FileNotFoundException e) {
                                    }
                                }
/* 782 */                       return source;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 791 */           public String getString(int index) throws IOException {
/* 792 */               Source source = newSource(index);
/* 793 */               if (source != null) {
                            return DiskLruCache.access$2000(source);
                        }
                        return null;
                    }

/* 803 */           public Sink newSink(int index) throws IOException {
                        Sink sinkAccess$2300;
                        Sink sink = DiskLruCache.access$2300();
                        synchronized (DiskLruCache.this) {
                            try {
/* 805 */                       if (Entry.access$700(this.entry) != this) {
/* 806 */                           throw new IllegalStateException();
                                }
/* 808 */                       if (!Entry.access$600(this.entry)) {
/* 809 */                           this.written[index] = true;
                                }
/* 811 */                       File dirtyFile = Entry.access$1200(this.entry)[index];
                                try {
/* 814 */                           sink = Okio.sink(dirtyFile);
                                } catch (FileNotFoundException e) {
/* 817 */                           DiskLruCache.access$2200(DiskLruCache.this).mkdirs();
                                    try {
/* 819 */                               sink = Okio.sink(dirtyFile);
                                    } catch (FileNotFoundException e2) {
/* 822 */                               sink = DiskLruCache.access$2300();
                                    }
                                }
/* 825 */                       sinkAccess$2300 = new FaultHidingSink(sink);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return sinkAccess$2300;
                    }

/* 830 */           public void set(int index, String value) throws IOException {
/* 831 */               BufferedSink writer = Okio.buffer(newSink(index));
/* 832 */               writer.writeUtf8(value);
/* 833 */               writer.close();
                    }

/* 840 */           public void commit() throws IOException {
                        synchronized (DiskLruCache.this) {
                            try {
/* 842 */                       if (this.hasErrors) {
/* 843 */                           DiskLruCache.access$2400(DiskLruCache.this, this, false);
/* 844 */                           DiskLruCache.access$2500(DiskLruCache.this, this.entry);
                                } else {
/* 846 */                           DiskLruCache.access$2400(DiskLruCache.this, this, true);
                                }
/* 848 */                       this.committed = true;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 856 */           public void abort() throws IOException {
                        synchronized (DiskLruCache.this) {
                            try {
/* 858 */                       DiskLruCache.access$2400(DiskLruCache.this, this, false);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 862 */           public void abortUnlessCommitted() {
                        synchronized (DiskLruCache.this) {
                            try {
/* 864 */                       if (!this.committed) {
                                    try {
/* 866 */                               DiskLruCache.access$2400(DiskLruCache.this, this, false);
                                    } catch (IOException e) {
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 872 */           private class FaultHidingSink extends ForwardingSink {
/* 873 */               public FaultHidingSink(Sink delegate) {
/* 875 */                   super(delegate);
                        }

                        @Override // okio.ForwardingSink, okio.Sink
/* 879 */               public void write(Buffer source, long byteCount) throws IOException {
                            try {
/* 880 */                       super.write(source, byteCount);
                            } catch (IOException e) {
                                synchronized (DiskLruCache.this) {
                                    try {
/* 883 */                               Editor.access$1802(Editor.this, true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }

                        @Override // okio.ForwardingSink, okio.Sink
/* 889 */               public void flush() throws IOException {
                            try {
/* 890 */                       super.flush();
                            } catch (IOException e) {
                                synchronized (DiskLruCache.this) {
                                    try {
/* 893 */                               Editor.access$1802(Editor.this, true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }

                        @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 899 */               public void close() throws IOException {
                            try {
/* 900 */                       super.close();
                            } catch (IOException e) {
                                synchronized (DiskLruCache.this) {
                                    try {
/* 903 */                               Editor.access$1802(Editor.this, true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }
                }

/* 908 */       private final class Entry {
                    private final File[] cleanFiles;
                    private Editor currentEditor;
                    private final File[] dirtyFiles;
                    private final String key;
                    private final long[] lengths;
                    private boolean readable;
                    private long sequenceNumber;

/* 909 */           /* synthetic */ Entry(DiskLruCache x0, String x1, RunnableC12691 x2) {
/* 910 */               this(x1);
                    }

/* 909 */           static /* synthetic */ long[] access$1000(Entry x0) {
/* 910 */               return x0.lengths;
                    }

/* 909 */           static /* synthetic */ File[] access$1100(Entry x0) {
/* 910 */               return x0.cleanFiles;
                    }

/* 909 */           static /* synthetic */ File[] access$1200(Entry x0) {
/* 910 */               return x0.dirtyFiles;
                    }

/* 909 */           static /* synthetic */ String access$1300(Entry x0) {
/* 910 */               return x0.key;
                    }

/* 909 */           static /* synthetic */ long access$1400(Entry x0) {
/* 910 */               return x0.sequenceNumber;
                    }

/* 909 */           static /* synthetic */ long access$1402(Entry x0, long x1) {
/* 910 */               x0.sequenceNumber = x1;
                        return x1;
                    }

/* 909 */           static /* synthetic */ boolean access$600(Entry x0) {
/* 910 */               return x0.readable;
                    }

/* 909 */           static /* synthetic */ boolean access$602(Entry x0, boolean x1) {
/* 910 */               x0.readable = x1;
                        return x1;
                    }

/* 909 */           static /* synthetic */ Editor access$700(Entry x0) {
/* 910 */               return x0.currentEditor;
                    }

/* 909 */           static /* synthetic */ Editor access$702(Entry x0, Editor x1) {
/* 910 */               x0.currentEditor = x1;
                        return x1;
                    }

/* 909 */           static /* synthetic */ void access$800(Entry x0, String[] x1) throws IOException {
/* 910 */               x0.setLengths(x1);
                    }

/* 926 */           private Entry(String key) {
/* 928 */               this.key = key;
/* 930 */               this.lengths = new long[DiskLruCache.access$2100(DiskLruCache.this)];
/* 931 */               this.cleanFiles = new File[DiskLruCache.access$2100(DiskLruCache.this)];
/* 932 */               this.dirtyFiles = new File[DiskLruCache.access$2100(DiskLruCache.this)];
/* 935 */               StringBuilder fileBuilder = new StringBuilder(key).append(FilenameUtils.EXTENSION_SEPARATOR);
/* 936 */               int truncateTo = fileBuilder.length();
                        for (int i = 0; i < DiskLruCache.access$2100(DiskLruCache.this); i++) {
/* 938 */                   fileBuilder.append(i);
/* 939 */                   this.cleanFiles[i] = new File(DiskLruCache.access$2200(DiskLruCache.this), fileBuilder.toString());
/* 940 */                   fileBuilder.append(".tmp");
/* 941 */                   this.dirtyFiles[i] = new File(DiskLruCache.access$2200(DiskLruCache.this), fileBuilder.toString());
/* 942 */                   fileBuilder.setLength(truncateTo);
                        }
                    }

/* 946 */           public String getLengths() throws IOException {
/* 947 */               StringBuilder result = new StringBuilder();
/* 948 */               long[] arr$ = this.lengths;
                        for (long size : arr$) {
/* 949 */                   result.append(' ').append(size);
                        }
/* 951 */               return result.toString();
                    }

/* 955 */           private void setLengths(String[] strings) throws IOException {
/* 956 */               if (strings.length != DiskLruCache.access$2100(DiskLruCache.this)) {
/* 957 */                   throw invalidLengths(strings);
                        }
                        for (int i = 0; i < strings.length; i++) {
                            try {
/* 962 */                       this.lengths[i] = Long.parseLong(strings[i]);
                            } catch (NumberFormatException e) {
/* 965 */                       throw invalidLengths(strings);
                            }
                        }
                    }

/* 969 */           private IOException invalidLengths(String[] strings) throws IOException {
/* 970 */               throw new IOException("unexpected journal line: " + Arrays.toString(strings));
                    }
                }
            }
