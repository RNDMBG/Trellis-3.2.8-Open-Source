            package com.vandalsoftware.p004io;

            import android.util.Log;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.Closeable;
            import java.io.DataInput;
            import java.io.DataInputStream;
            import java.io.DataOutput;
            import java.io.DataOutputStream;
            import java.io.EOFException;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.FilterOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.LinkedHashMap;
            import java.util.Map;
            import java.util.concurrent.Callable;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 78 */    public final class DiskLruCache implements Closeable {
                private static final int CLEAN = 1;
                private static final int DIRTY = 2;
                private static final String JOURNAL_FILE = "journal";
                private static final String JOURNAL_FILE_TMP = "journal.tmp";
                private static final long MAGIC = -9130401435085039094L;
                private static final int READ = 4;
                private static final int REDUNDANT_OP_COMPACT_THRESHOLD = 2000;
                private static final int REMOVE = 3;
                private static final int VERSION = 2;
                private final int appVersion;
                private final File directory;
                private final File journalFile;
                private final File journalFileTmp;
                private DataOutputStream journalStream;
                private final long maxSize;
                private int redundantOpCount;
                private final int valueCount;
                private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<>(0, 0.75f, true);
                private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
                private final Callable<Void> cleanupCallable = new CallableC12941();
                private long size = 0;

/* 79 */        static /* synthetic */ DataOutputStream access$000(DiskLruCache x0) {
/* 80 */            return x0.journalStream;
                }

/* 79 */        static /* synthetic */ void access$100(DiskLruCache x0) throws IOException {
/* 80 */            x0.trimToSize();
                }

/* 79 */        static /* synthetic */ void access$1300(DiskLruCache x0, Editor x1, boolean x2) throws IOException {
/* 80 */            x0.completeEdit(x1, x2);
                }

/* 79 */        static /* synthetic */ int access$1500(DiskLruCache x0) {
/* 80 */            return x0.valueCount;
                }

/* 79 */        static /* synthetic */ File access$1600(DiskLruCache x0) {
/* 80 */            return x0.directory;
                }

/* 79 */        static /* synthetic */ boolean access$200(DiskLruCache x0) {
/* 80 */            return x0.journalRebuildRequired();
                }

/* 79 */        static /* synthetic */ void access$300(DiskLruCache x0) throws IOException {
/* 80 */            x0.rebuildJournal();
                }

                /* JADX INFO: renamed from: com.vandalsoftware.io.DiskLruCache$1 */
/* 137 */       class CallableC12941 implements Callable<Void> {
/* 138 */           CallableC12941() {
                    }


                    @Override // java.util.concurrent.Callable
                    /* JADX INFO: renamed from: call, reason: avoid collision after fix types in other method */
/* 141 */           public Void call() throws Exception {
                        synchronized (DiskLruCache.this) {
                            try {
/* 143 */                       if (DiskLruCache.access$000(DiskLruCache.this) != null) {
/* 146 */                           DiskLruCache.access$100(DiskLruCache.this);
/* 147 */                           if (DiskLruCache.access$200(DiskLruCache.this)) {
/* 148 */                               DiskLruCache.access$300(DiskLruCache.this);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return null;
                    }
                }

/* 157 */       private DiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
/* 159 */           this.directory = directory;
/* 160 */           this.appVersion = appVersion;
/* 161 */           this.journalFile = new File(directory, JOURNAL_FILE);
/* 162 */           this.journalFileTmp = new File(directory, JOURNAL_FILE_TMP);
/* 163 */           this.valueCount = valueCount;
/* 164 */           this.maxSize = maxSize;
                }

/* 177 */       public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize) throws IOException {
                    if (maxSize <= 0) {
/* 179 */               throw new IllegalArgumentException("maxSize <= 0");
                    }
/* 181 */           if (valueCount <= 0) {
/* 182 */               throw new IllegalArgumentException("valueCount <= 0");
                    }
/* 186 */           DiskLruCache cache = new DiskLruCache(directory, appVersion, valueCount, maxSize);
/* 187 */           if (cache.journalFile.exists()) {
                        try {
/* 189 */                   int journalEntries = cache.readJournal();
/* 190 */                   cache.processJournal();
/* 191 */                   cache.journalStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cache.journalFile, true)));
/* 193 */                   cache.redundantOpCount = journalEntries - cache.lruEntries.size();
                            return cache;
                        } catch (IOException journalIsCorrupt) {
/* 196 */                   Log.w("DiskLruCache", directory + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
/* 198 */                   cache.delete();
                        }
                    }
/* 203 */           if (directory.mkdirs() || directory.exists()) {
/* 204 */               DiskLruCache cache2 = new DiskLruCache(directory, appVersion, valueCount, maxSize);
/* 205 */               cache2.rebuildJournal();
                        return cache2;
                    }
/* 208 */           throw new FileNotFoundException("directory not found " + directory);
                }

/* 212 */       private static boolean deleteIfExists(File file) throws IOException {
                    return file.exists() && file.delete();
                }

/* 222 */       private int readJournal() throws IOException {
/* 223 */           DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(this.journalFile)));
                    try {
/* 226 */               long fileMagic = in.readLong();
/* 227 */               int fileVersion = in.readUnsignedByte();
/* 228 */               int fileAppVersion = in.readInt();
/* 229 */               int fileValueCount = in.readInt();
/* 230 */               byte blank = in.readByte();
                        if (MAGIC != fileMagic || 2 != fileVersion || this.appVersion != fileAppVersion || this.valueCount != fileValueCount || blank != 10) {
/* 236 */                   throw new IOException("unexpected journal header: [" + fileMagic + ", " + fileVersion + ", " + fileValueCount + ", " + ((int) blank) + "]");
                        }
/* 240 */               int entries = 0;
                        while (true) {
                            try {
/* 243 */                       readJournalLine(in);
/* 244 */                       entries++;
                            } catch (EOFException e) {
/* 251 */                       IoUtils.closeQuietly(in);
                                return entries;
                            }
                        }
                    } catch (Throwable th) {
                        IoUtils.closeQuietly(in);
                        throw th;
                    }
                }

/* 255 */       private void readJournalLine(DataInput in) throws IOException {
/* 256 */           int op = in.readUnsignedByte();
/* 257 */           String key = in.readUTF();
/* 258 */           if (op == 3 && in.readByte() == 10) {
/* 259 */               this.lruEntries.remove(key);
/* 287 */               return;
                    }
/* 263 */           Entry entry = this.lruEntries.get(key);
/* 264 */           if (entry == null) {
/* 265 */               entry = new Entry(this, key, null);
/* 266 */               this.lruEntries.put(key, entry);
                    }
/* 269 */           if (op == 1) {
/* 270 */               long[] temp = new long[this.valueCount];
                        for (int i = 0; i < this.valueCount; i++) {
/* 272 */                   temp[i] = in.readLong();
                        }
/* 274 */               if (in.readByte() != 10) {
/* 275 */                   throw new IOException("unexpected journal entry: " + op + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + key);
                        }
/* 277 */               entry.setReadable(true);
/* 278 */               entry.setCurrentEditor(null);
/* 279 */               System.arraycopy(temp, 0, entry.getLengths(), 0, this.valueCount);
/* 287 */               return;
                    }
/* 280 */           if (op == 2 && in.readByte() == 10) {
/* 281 */               entry.setCurrentEditor(new Editor(this, entry, null));
                    } else if (op != 4 || in.readByte() != 10) {
/* 285 */               throw new IOException("unexpected journal entry: " + op + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + key);
                    }
                }

/* 293 */       private void processJournal() throws IOException {
/* 294 */           deleteIfExists(this.journalFileTmp);
/* 295 */           Iterator<Entry> i = this.lruEntries.values().iterator();
                    while (i.hasNext()) {
/* 296 */               Entry entry = i.next();
/* 297 */               if (entry.getCurrentEditor() == null) {
                            for (int t = 0; t < this.valueCount; t++) {
/* 299 */                       this.size += entry.getLengths()[t];
                            }
                        } else {
/* 302 */                   entry.setCurrentEditor(null);
                            for (int t2 = 0; t2 < this.valueCount; t2++) {
/* 304 */                       deleteIfExists(entry.getCleanFile(t2));
/* 305 */                       deleteIfExists(entry.getDirtyFile(t2));
                            }
/* 307 */                   i.remove();
                        }
                    }
                }

/* 316 */       private synchronized void rebuildJournal() throws IOException {
                    try {
                        if (this.journalStream != null) {
/* 318 */                   this.journalStream.close();
                        }
/* 321 */               DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.journalFileTmp)));
                        try {
/* 324 */                   out.writeLong(MAGIC);
/* 325 */                   out.writeByte(2);
/* 326 */                   out.writeInt(this.appVersion);
/* 327 */                   out.writeInt(this.valueCount);
/* 328 */                   out.writeByte(10);
                            for (Entry entry : this.lruEntries.values()) {
/* 331 */                       if (entry.getCurrentEditor() != null) {
/* 332 */                           writeEntryKey(out, 2, entry.getKey());
                                } else {
/* 334 */                           writeCleanEntry(out, entry);
                                }
                            }
/* 338 */                   out.close();
/* 340 */                   IoUtils.renameFileOrThrow(this.journalFileTmp, this.journalFile);
/* 341 */                   this.journalStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.journalFile, true)));
/* 343 */                   this.redundantOpCount = 0;
                        } catch (Throwable th) {
                            out.close();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 346 */       private void writeEntryKey(DataOutput out, int s, String key) throws IOException {
/* 347 */           out.writeByte(s);
/* 348 */           out.writeUTF(key);
/* 349 */           out.writeByte(10);
                }

/* 352 */       private void writeCleanEntry(DataOutput out, Entry entry) throws IOException {
/* 353 */           out.writeByte(1);
/* 354 */           out.writeUTF(entry.getKey());
/* 355 */           long[] arr$ = entry.getLengths();
                    for (long len : arr$) {
/* 356 */               out.writeLong(len);
                    }
/* 358 */           out.writeByte(10);
                }

/* 365 */       public synchronized Snapshot get(String key, boolean peek) throws IOException {
                    Snapshot snapshot = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
/* 367 */                   validateKey(key);
/* 368 */                   Entry entry = this.lruEntries.get(key);
/* 369 */                   if (entry != null && entry.isReadable()) {
/* 382 */                       InputStream[] ins = new InputStream[this.valueCount];
/* 383 */                       String[] paths = new String[this.valueCount];
                                for (int i = 0; i < this.valueCount; i++) {
                                    try {
/* 387 */                               File file = entry.getCleanFile(i);
/* 388 */                               ins[i] = new FileInputStream(file);
/* 389 */                               paths[i] = file.getAbsolutePath();
                                    } catch (FileNotFoundException e) {
                                    }
                                }
/* 396 */                       if (!peek) {
/* 397 */                           this.redundantOpCount++;
/* 398 */                           writeEntryKey(this.journalStream, 4, key);
/* 399 */                           if (journalRebuildRequired()) {
/* 400 */                               this.executorService.submit(this.cleanupCallable);
                                    }
                                }
/* 404 */                       snapshot = new Snapshot(ins, paths, null);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return snapshot;
                }

/* 407 */       public synchronized Snapshot get(String key) throws IOException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return get(key, false);
                }

/* 414 */       public synchronized Editor edit(String key) throws IOException {
                    Editor editor = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
/* 416 */                   validateKey(key);
/* 417 */                   Entry entry = this.lruEntries.get(key);
/* 418 */                   if (entry == null) {
/* 419 */                       entry = new Entry(this, key, null);
/* 420 */                       this.lruEntries.put(key, entry);
                            } else if (entry.getCurrentEditor() == null) {
                            }
/* 425 */                   editor = new Editor(this, entry, null);
/* 426 */                   entry.setCurrentEditor(editor);
/* 429 */                   writeEntryKey(this.journalStream, 2, key);
/* 430 */                   this.journalStream.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return editor;
                }

/* 437 */       public File getDirectory() {
/* 438 */           return this.directory;
                }

/* 444 */       public long maxSize() {
/* 445 */           return this.maxSize;
                }

/* 452 */       public synchronized long size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.size;
                }

/* 456 */       private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
                    try {
                        Entry entry = editor.getEntry();
/* 458 */               if (entry.getCurrentEditor() != editor) {
/* 459 */                   throw new IllegalStateException();
                        }
/* 463 */               if (success && !entry.isReadable()) {
                            for (int i = 0; i < this.valueCount; i++) {
/* 465 */                       if (!entry.getDirtyFile(i).exists()) {
/* 466 */                           editor.abort();
/* 467 */                           throw new IllegalStateException("edit didn't create file " + i);
                                }
                            }
                        }
                        for (int i2 = 0; i2 < this.valueCount; i2++) {
/* 473 */                   File dirty = entry.getDirtyFile(i2);
/* 474 */                   if (success) {
/* 475 */                       if (dirty.exists()) {
/* 476 */                           File clean = entry.getCleanFile(i2);
/* 477 */                           IoUtils.renameFileOrThrow(dirty, clean);
/* 478 */                           long oldLength = entry.getLengths()[i2];
/* 479 */                           long newLength = clean.length();
/* 480 */                           entry.getLengths()[i2] = newLength;
/* 481 */                           this.size = (this.size - oldLength) + newLength;
                                }
                            } else {
/* 484 */                       deleteIfExists(dirty);
                            }
                        }
/* 488 */               this.redundantOpCount++;
/* 489 */               entry.setCurrentEditor(null);
/* 490 */               if (entry.isReadable() | success) {
/* 491 */                   entry.setReadable(true);
/* 492 */                   writeCleanEntry(this.journalStream, entry);
                        } else {
/* 494 */                   this.lruEntries.remove(entry.getKey());
/* 495 */                   writeEntryKey(this.journalStream, 3, entry.getKey());
                        }
                        if (this.size > this.maxSize || journalRebuildRequired()) {
/* 499 */                   this.executorService.submit(this.cleanupCallable);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 507 */       private boolean journalRebuildRequired() {
                    return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
                }

/* 518 */       public synchronized boolean remove(String key) throws IOException {
                    boolean z;
                    try {
                        checkNotClosed();
/* 520 */               validateKey(key);
/* 521 */               Entry entry = this.lruEntries.get(key);
/* 522 */               if (entry == null || entry.getCurrentEditor() != null) {
/* 523 */                   z = false;
                        } else {
                            for (int i = 0; i < this.valueCount; i++) {
/* 527 */                       File file = entry.getCleanFile(i);
/* 528 */                       IoUtils.deleteFileOrThrow(file);
/* 529 */                       this.size -= entry.getLengths()[i];
/* 530 */                       entry.getLengths()[i] = 0;
                            }
/* 533 */                   this.redundantOpCount++;
/* 534 */                   writeEntryKey(this.journalStream, 3, key);
/* 535 */                   this.lruEntries.remove(key);
/* 537 */                   if (journalRebuildRequired()) {
/* 538 */                       this.executorService.submit(this.cleanupCallable);
                            }
/* 541 */                   z = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }

/* 547 */       public boolean isClosed() {
                    return this.journalStream == null;
                }

/* 551 */       private void checkNotClosed() {
/* 552 */           if (this.journalStream == null) {
/* 553 */               throw new IllegalStateException("cache is closed");
                    }
                }

/* 560 */       public synchronized void flush() throws IOException {
                    try {
                        checkNotClosed();
/* 562 */               trimToSize();
/* 563 */               this.journalStream.flush();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.Closeable, java.lang.AutoCloseable
/* 569 */       public synchronized void close() throws IOException {
                    try {
                        if (this.journalStream != null) {
                            for (Entry entry : new ArrayList<Entry>(this.lruEntries.values())) {
/* 574 */                       if (entry.getCurrentEditor() != null) {
/* 575 */                           entry.getCurrentEditor().abort();
                                }
                            }
/* 578 */                   trimToSize();
/* 579 */                   this.journalStream.close();
/* 580 */                   this.journalStream = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 583 */       private void trimToSize() throws IOException {
                    while (this.size > this.maxSize) {
/* 585 */               Map.Entry<String, Entry> toEvict = this.lruEntries.entrySet().iterator().next();
/* 586 */               remove(toEvict.getKey());
                    }
                }

/* 594 */       public void delete() throws IOException {
/* 595 */           close();
/* 596 */           if (this.directory.isDirectory()) {
/* 597 */               IoUtils.deleteContents(this.directory);
                    }
                }

/* 601 */       private void validateKey(String key) {
/* 602 */           if (key.contains(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) || key.contains(IOUtils.LINE_SEPARATOR_UNIX) || key.contains("\r")) {
/* 603 */               throw new IllegalArgumentException("keys must not contain spaces or newlines: \"" + key + "\"");
                    }
                }

/* 608 */       public LinkedHashMap<String, Entry> getLruEntries() {
/* 609 */           return this.lruEntries;
                }

/* 613 */       public static final class Snapshot implements Closeable {
                    private final InputStream[] ins;
                    private final String[] paths;

/* 614 */           /* synthetic */ Snapshot(InputStream[] x0, String[] x1, CallableC12941 x2) {
/* 615 */               this(x0, x1);
                    }

/* 618 */           private Snapshot(InputStream[] ins, String[] paths) {
/* 620 */               this.ins = ins;
/* 621 */               this.paths = paths;
                    }

/* 627 */           public InputStream getInputStream(int index) {
/* 628 */               return this.ins[index];
                    }

/* 634 */           public String getPath(int index) {
/* 635 */               return this.paths[index];
                    }

                    @Override // java.io.Closeable, java.lang.AutoCloseable
/* 639 */           public void close() {
/* 640 */               InputStream[] arr$ = this.ins;
                        for (InputStream in : arr$) {
/* 641 */                   IoUtils.closeQuietly(in);
                        }
                    }
                }

/* 647 */       public final class Editor {
                    private final Entry entry;
                    private boolean hasErrors;

/* 648 */           /* synthetic */ Editor(DiskLruCache x0, Entry x1, CallableC12941 x2) {
/* 649 */               this(x1);
                    }

/* 648 */           Entry getEntry() {
/* 649 */               return this.entry;
                    }

/* 648 */           boolean setHasErrors(boolean x1) {
/* 649 */               this.hasErrors = x1;
                        return x1;
                    }

/* 652 */           private Editor(Entry entry) {
/* 654 */               this.entry = entry;
                    }

/* 662 */           public OutputStream newOutputStream(int index) throws IOException {
                        ErrorCatchingOutputStream errorCatchingOutputStream;
                        synchronized (DiskLruCache.this) {
                            try {
/* 664 */                       if (this.entry.getCurrentEditor() != this) {
/* 665 */                           throw new IllegalStateException();
                                }
/* 667 */                       errorCatchingOutputStream = new ErrorCatchingOutputStream(this, new FileOutputStream(this.entry.getDirtyFile(index)), null);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return errorCatchingOutputStream;
                    }

/* 676 */           public void commit() throws IOException {
/* 677 */               if (this.hasErrors) {
/* 678 */                   DiskLruCache.access$1300(DiskLruCache.this, this, false);
/* 679 */                   DiskLruCache.this.remove(this.entry.getKey());
                        } else {
/* 681 */                   DiskLruCache.access$1300(DiskLruCache.this, this, true);
                        }
                    }

/* 689 */           public void abort() throws IOException {
/* 690 */               DiskLruCache.access$1300(DiskLruCache.this, this, false);
                    }

/* 691 */           private class ErrorCatchingOutputStream extends FilterOutputStream {
/* 692 */               /* synthetic */ ErrorCatchingOutputStream(Editor x0, OutputStream x1, CallableC12941 x2) {
/* 693 */                   this(x1);
                        }

/* 693 */               private ErrorCatchingOutputStream(OutputStream out) {
/* 695 */                   super(out);
                        }

                        @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 700 */               public void write(int oneByte) throws IOException {
                            try {
/* 701 */                       this.out.write(oneByte);
                            } catch (IOException e) {
/* 703 */                       Editor.this.setHasErrors(true);
/* 704 */                       throw e;
                            }
                        }

                        @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 710 */               public void write(byte[] buffer, int offset, int length) throws IOException {
                            try {
/* 711 */                       this.out.write(buffer, offset, length);
                            } catch (IOException e) {
/* 713 */                       Editor.this.setHasErrors(true);
/* 714 */                       throw e;
                            }
                        }

                        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 720 */               public void close() throws IOException {
                            try {
/* 721 */                       this.out.close();
                            } catch (IOException e) {
/* 723 */                       Editor.this.setHasErrors(true);
/* 724 */                       throw e;
                            }
                        }

                        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
/* 730 */               public void flush() throws IOException {
                            try {
/* 731 */                       this.out.flush();
                            } catch (IOException e) {
/* 733 */                       Editor.this.setHasErrors(true);
/* 734 */                       throw e;
                            }
                        }
                    }
                }

/* 738 */       public final class Entry {
                    private Editor currentEditor;
                    private final String key;
                    private final long[] lengths;
                    private boolean readable;

/* 739 */           /* synthetic */ Entry(DiskLruCache x0, String x1, CallableC12941 x2) {
/* 740 */               this(x1);
                    }

/* 739 */           boolean isReadable() {
/* 740 */               return this.readable;
                    }

/* 739 */           boolean setReadable(boolean x1) {
/* 740 */               this.readable = x1;
                        return x1;
                    }

/* 739 */           Editor getCurrentEditor() {
/* 740 */               return this.currentEditor;
                    }

/* 739 */           Editor setCurrentEditor(Editor x1) {
/* 740 */               this.currentEditor = x1;
                        return x1;
                    }

/* 739 */           long[] getLengths() {
/* 740 */               return this.lengths;
                    }

/* 739 */           String getKey() {
/* 740 */               return this.key;
                    }

/* 754 */           private Entry(String key) {
/* 756 */               this.key = key;
/* 757 */               this.lengths = new long[DiskLruCache.access$1500(DiskLruCache.this)];
                    }

/* 760 */           public File getCleanFile(int i) {
/* 761 */               return new File(DiskLruCache.access$1600(DiskLruCache.this), this.key + "." + i);
                    }

/* 764 */           public File getDirtyFile(int i) {
/* 765 */               return new File(DiskLruCache.access$1600(DiskLruCache.this), this.key + "." + i + ".tmp");
                    }
                }
            }

