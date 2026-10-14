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
            
            
    public final class DiskLruCache implements Closeable {
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

        static  DataOutputStream access$000(DiskLruCache x0) {
            return x0.journalStream;
                }

        static  void access$100(DiskLruCache x0) throws IOException {
            x0.trimToSize();
                }

        static  void access$1300(DiskLruCache x0, Editor x1, boolean x2) throws IOException {
            x0.completeEdit(x1, x2);
                }

        static  int access$1500(DiskLruCache x0) {
            return x0.valueCount;
                }

        static  File access$1600(DiskLruCache x0) {
            return x0.directory;
                }

        static  boolean access$200(DiskLruCache x0) {
            return x0.journalRebuildRequired();
                }

        static  void access$300(DiskLruCache x0) throws IOException {
            x0.rebuildJournal();
                }

                
       class CallableC12941 implements Callable<Void> {
           CallableC12941() {
                    }


                    @Override 
                    
           public Void call() throws Exception {
                        synchronized (DiskLruCache.this) {
                            try {
                       if (DiskLruCache.access$000(DiskLruCache.this) != null) {
                           DiskLruCache.access$100(DiskLruCache.this);
                           if (DiskLruCache.access$200(DiskLruCache.this)) {
                               DiskLruCache.access$300(DiskLruCache.this);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return null;
                    }
                }

       private DiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
           this.directory = directory;
           this.appVersion = appVersion;
           this.journalFile = new File(directory, JOURNAL_FILE);
           this.journalFileTmp = new File(directory, JOURNAL_FILE_TMP);
           this.valueCount = valueCount;
           this.maxSize = maxSize;
                }

       public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize) throws IOException {
                    if (maxSize <= 0) {
               throw new IllegalArgumentException("maxSize <= 0");
                    }
           if (valueCount <= 0) {
               throw new IllegalArgumentException("valueCount <= 0");
                    }
           DiskLruCache cache = new DiskLruCache(directory, appVersion, valueCount, maxSize);
           if (cache.journalFile.exists()) {
                        try {
                   int journalEntries = cache.readJournal();
                   cache.processJournal();
                   cache.journalStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(cache.journalFile, true)));
                   cache.redundantOpCount = journalEntries - cache.lruEntries.size();
                            return cache;
                        } catch (IOException journalIsCorrupt) {
                   Log.w("DiskLruCache", directory + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
                   cache.delete();
                        }
                    }
           if (directory.mkdirs() || directory.exists()) {
               DiskLruCache cache2 = new DiskLruCache(directory, appVersion, valueCount, maxSize);
               cache2.rebuildJournal();
                        return cache2;
                    }
           throw new FileNotFoundException("directory not found " + directory);
                }

       private static boolean deleteIfExists(File file) throws IOException {
                    return file.exists() && file.delete();
                }

       private int readJournal() throws IOException {
           DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(this.journalFile)));
                    try {
               long fileMagic = in.readLong();
               int fileVersion = in.readUnsignedByte();
               int fileAppVersion = in.readInt();
               int fileValueCount = in.readInt();
               byte blank = in.readByte();
                        if (MAGIC != fileMagic || 2 != fileVersion || this.appVersion != fileAppVersion || this.valueCount != fileValueCount || blank != 10) {
                   throw new IOException("unexpected journal header: [" + fileMagic + ", " + fileVersion + ", " + fileValueCount + ", " + ((int) blank) + "]");
                        }
               int entries = 0;
                        while (true) {
                            try {
                       readJournalLine(in);
                       entries++;
                            } catch (EOFException e) {
                       IoUtils.closeQuietly(in);
                                return entries;
                            }
                        }
                    } catch (Throwable th) {
                        IoUtils.closeQuietly(in);
                        throw th;
                    }
                }

       private void readJournalLine(DataInput in) throws IOException {
           int op = in.readUnsignedByte();
           String key = in.readUTF();
           if (op == 3 && in.readByte() == 10) {
               this.lruEntries.remove(key);
               return;
                    }
           Entry entry = this.lruEntries.get(key);
           if (entry == null) {
               entry = new Entry(this, key, null);
               this.lruEntries.put(key, entry);
                    }
           if (op == 1) {
               long[] temp = new long[this.valueCount];
                        for (int i = 0; i < this.valueCount; i++) {
                   temp[i] = in.readLong();
                        }
               if (in.readByte() != 10) {
                   throw new IOException("unexpected journal entry: " + op + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + key);
                        }
               entry.setReadable(true);
               entry.setCurrentEditor(null);
               System.arraycopy(temp, 0, entry.getLengths(), 0, this.valueCount);
               return;
                    }
           if (op == 2 && in.readByte() == 10) {
               entry.setCurrentEditor(new Editor(this, entry, null));
                    } else if (op != 4 || in.readByte() != 10) {
               throw new IOException("unexpected journal entry: " + op + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + key);
                    }
                }

       private void processJournal() throws IOException {
           deleteIfExists(this.journalFileTmp);
           Iterator<Entry> i = this.lruEntries.values().iterator();
                    while (i.hasNext()) {
               Entry entry = i.next();
               if (entry.getCurrentEditor() == null) {
                            for (int t = 0; t < this.valueCount; t++) {
                       this.size += entry.getLengths()[t];
                            }
                        } else {
                   entry.setCurrentEditor(null);
                            for (int t2 = 0; t2 < this.valueCount; t2++) {
                       deleteIfExists(entry.getCleanFile(t2));
                       deleteIfExists(entry.getDirtyFile(t2));
                            }
                   i.remove();
                        }
                    }
                }

       private synchronized void rebuildJournal() throws IOException {
                    try {
                        if (this.journalStream != null) {
                   this.journalStream.close();
                        }
               DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.journalFileTmp)));
                        try {
                   out.writeLong(MAGIC);
                   out.writeByte(2);
                   out.writeInt(this.appVersion);
                   out.writeInt(this.valueCount);
                   out.writeByte(10);
                            for (Entry entry : this.lruEntries.values()) {
                       if (entry.getCurrentEditor() != null) {
                           writeEntryKey(out, 2, entry.getKey());
                                } else {
                           writeCleanEntry(out, entry);
                                }
                            }
                   out.close();
                   IoUtils.renameFileOrThrow(this.journalFileTmp, this.journalFile);
                   this.journalStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.journalFile, true)));
                   this.redundantOpCount = 0;
                        } catch (Throwable th) {
                            out.close();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

       private void writeEntryKey(DataOutput out, int s, String key) throws IOException {
           out.writeByte(s);
           out.writeUTF(key);
           out.writeByte(10);
                }

       private void writeCleanEntry(DataOutput out, Entry entry) throws IOException {
           out.writeByte(1);
           out.writeUTF(entry.getKey());
           long[] arr$ = entry.getLengths();
                    for (long len : arr$) {
               out.writeLong(len);
                    }
           out.writeByte(10);
                }

       public synchronized Snapshot get(String key, boolean peek) throws IOException {
                    Snapshot snapshot = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
                   validateKey(key);
                   Entry entry = this.lruEntries.get(key);
                   if (entry != null && entry.isReadable()) {
                       InputStream[] ins = new InputStream[this.valueCount];
                       String[] paths = new String[this.valueCount];
                                for (int i = 0; i < this.valueCount; i++) {
                                    try {
                               File file = entry.getCleanFile(i);
                               ins[i] = new FileInputStream(file);
                               paths[i] = file.getAbsolutePath();
                                    } catch (FileNotFoundException e) {
                                    }
                                }
                       if (!peek) {
                           this.redundantOpCount++;
                           writeEntryKey(this.journalStream, 4, key);
                           if (journalRebuildRequired()) {
                               this.executorService.submit(this.cleanupCallable);
                                    }
                                }
                       snapshot = new Snapshot(ins, paths, null);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return snapshot;
                }

       public synchronized Snapshot get(String key) throws IOException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return get(key, false);
                }

       public synchronized Editor edit(String key) throws IOException {
                    Editor editor = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
                   validateKey(key);
                   Entry entry = this.lruEntries.get(key);
                   if (entry == null) {
                       entry = new Entry(this, key, null);
                       this.lruEntries.put(key, entry);
                            } else if (entry.getCurrentEditor() == null) {
                            }
                   editor = new Editor(this, entry, null);
                   entry.setCurrentEditor(editor);
                   writeEntryKey(this.journalStream, 2, key);
                   this.journalStream.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return editor;
                }

       public File getDirectory() {
           return this.directory;
                }

       public long maxSize() {
           return this.maxSize;
                }

       public synchronized long size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.size;
                }

       private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
                    try {
                        Entry entry = editor.getEntry();
               if (entry.getCurrentEditor() != editor) {
                   throw new IllegalStateException();
                        }
               if (success && !entry.isReadable()) {
                            for (int i = 0; i < this.valueCount; i++) {
                       if (!entry.getDirtyFile(i).exists()) {
                           editor.abort();
                           throw new IllegalStateException("edit didn't create file " + i);
                                }
                            }
                        }
                        for (int i2 = 0; i2 < this.valueCount; i2++) {
                   File dirty = entry.getDirtyFile(i2);
                   if (success) {
                       if (dirty.exists()) {
                           File clean = entry.getCleanFile(i2);
                           IoUtils.renameFileOrThrow(dirty, clean);
                           long oldLength = entry.getLengths()[i2];
                           long newLength = clean.length();
                           entry.getLengths()[i2] = newLength;
                           this.size = (this.size - oldLength) + newLength;
                                }
                            } else {
                       deleteIfExists(dirty);
                            }
                        }
               this.redundantOpCount++;
               entry.setCurrentEditor(null);
               if (entry.isReadable() | success) {
                   entry.setReadable(true);
                   writeCleanEntry(this.journalStream, entry);
                        } else {
                   this.lruEntries.remove(entry.getKey());
                   writeEntryKey(this.journalStream, 3, entry.getKey());
                        }
                        if (this.size > this.maxSize || journalRebuildRequired()) {
                   this.executorService.submit(this.cleanupCallable);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private boolean journalRebuildRequired() {
                    return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
                }

       public synchronized boolean remove(String key) throws IOException {
                    boolean z;
                    try {
                        checkNotClosed();
               validateKey(key);
               Entry entry = this.lruEntries.get(key);
               if (entry == null || entry.getCurrentEditor() != null) {
                   z = false;
                        } else {
                            for (int i = 0; i < this.valueCount; i++) {
                       File file = entry.getCleanFile(i);
                       IoUtils.deleteFileOrThrow(file);
                       this.size -= entry.getLengths()[i];
                       entry.getLengths()[i] = 0;
                            }
                   this.redundantOpCount++;
                   writeEntryKey(this.journalStream, 3, key);
                   this.lruEntries.remove(key);
                   if (journalRebuildRequired()) {
                       this.executorService.submit(this.cleanupCallable);
                            }
                   z = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }

       public boolean isClosed() {
                    return this.journalStream == null;
                }

       private void checkNotClosed() {
           if (this.journalStream == null) {
               throw new IllegalStateException("cache is closed");
                    }
                }

       public synchronized void flush() throws IOException {
                    try {
                        checkNotClosed();
               trimToSize();
               this.journalStream.flush();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void close() throws IOException {
                    try {
                        if (this.journalStream != null) {
                            for (Entry entry : new ArrayList<Entry>(this.lruEntries.values())) {
                       if (entry.getCurrentEditor() != null) {
                           entry.getCurrentEditor().abort();
                                }
                            }
                   trimToSize();
                   this.journalStream.close();
                   this.journalStream = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void trimToSize() throws IOException {
                    while (this.size > this.maxSize) {
               Map.Entry<String, Entry> toEvict = this.lruEntries.entrySet().iterator().next();
               remove(toEvict.getKey());
                    }
                }

       public void delete() throws IOException {
           close();
           if (this.directory.isDirectory()) {
               IoUtils.deleteContents(this.directory);
                    }
                }

       private void validateKey(String key) {
           if (key.contains(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR) || key.contains(IOUtils.LINE_SEPARATOR_UNIX) || key.contains("\r")) {
               throw new IllegalArgumentException("keys must not contain spaces or newlines: \"" + key + "\"");
                    }
                }

       public LinkedHashMap<String, Entry> getLruEntries() {
           return this.lruEntries;
                }

       public static final class Snapshot implements Closeable {
                    private final InputStream[] ins;
                    private final String[] paths;

            Snapshot(InputStream[] x0, String[] x1, CallableC12941 x2) {
               this(x0, x1);
                    }

           private Snapshot(InputStream[] ins, String[] paths) {
               this.ins = ins;
               this.paths = paths;
                    }

           public InputStream getInputStream(int index) {
               return this.ins[index];
                    }

           public String getPath(int index) {
               return this.paths[index];
                    }

                    @Override 
           public void close() {
               InputStream[] arr$ = this.ins;
                        for (InputStream in : arr$) {
                   IoUtils.closeQuietly(in);
                        }
                    }
                }

       public final class Editor {
                    private final Entry entry;
                    private boolean hasErrors;

            Editor(DiskLruCache x0, Entry x1, CallableC12941 x2) {
               this(x1);
                    }

           Entry getEntry() {
               return this.entry;
                    }

           boolean setHasErrors(boolean x1) {
               this.hasErrors = x1;
                        return x1;
                    }

           private Editor(Entry entry) {
               this.entry = entry;
                    }

           public OutputStream newOutputStream(int index) throws IOException {
                        ErrorCatchingOutputStream errorCatchingOutputStream;
                        synchronized (DiskLruCache.this) {
                            try {
                       if (this.entry.getCurrentEditor() != this) {
                           throw new IllegalStateException();
                                }
                       errorCatchingOutputStream = new ErrorCatchingOutputStream(this, new FileOutputStream(this.entry.getDirtyFile(index)), null);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return errorCatchingOutputStream;
                    }

           public void commit() throws IOException {
               if (this.hasErrors) {
                   DiskLruCache.access$1300(DiskLruCache.this, this, false);
                   DiskLruCache.this.remove(this.entry.getKey());
                        } else {
                   DiskLruCache.access$1300(DiskLruCache.this, this, true);
                        }
                    }

           public void abort() throws IOException {
               DiskLruCache.access$1300(DiskLruCache.this, this, false);
                    }

           private class ErrorCatchingOutputStream extends FilterOutputStream {
                ErrorCatchingOutputStream(Editor x0, OutputStream x1, CallableC12941 x2) {
                   this(x1);
                        }

               private ErrorCatchingOutputStream(OutputStream out) {
                   super(out);
                        }

                        @Override 
               public void write(int oneByte) throws IOException {
                            try {
                       this.out.write(oneByte);
                            } catch (IOException e) {
                       Editor.this.setHasErrors(true);
                       throw e;
                            }
                        }

                        @Override 
               public void write(byte[] buffer, int offset, int length) throws IOException {
                            try {
                       this.out.write(buffer, offset, length);
                            } catch (IOException e) {
                       Editor.this.setHasErrors(true);
                       throw e;
                            }
                        }

                        @Override 
               public void close() throws IOException {
                            try {
                       this.out.close();
                            } catch (IOException e) {
                       Editor.this.setHasErrors(true);
                       throw e;
                            }
                        }

                        @Override 
               public void flush() throws IOException {
                            try {
                       this.out.flush();
                            } catch (IOException e) {
                       Editor.this.setHasErrors(true);
                       throw e;
                            }
                        }
                    }
                }

       public final class Entry {
                    private Editor currentEditor;
                    private final String key;
                    private final long[] lengths;
                    private boolean readable;

            Entry(DiskLruCache x0, String x1, CallableC12941 x2) {
               this(x1);
                    }

           boolean isReadable() {
               return this.readable;
                    }

           boolean setReadable(boolean x1) {
               this.readable = x1;
                        return x1;
                    }

           Editor getCurrentEditor() {
               return this.currentEditor;
                    }

           Editor setCurrentEditor(Editor x1) {
               this.currentEditor = x1;
                        return x1;
                    }

           long[] getLengths() {
               return this.lengths;
                    }

           String getKey() {
               return this.key;
                    }

           private Entry(String key) {
               this.key = key;
               this.lengths = new long[DiskLruCache.access$1500(DiskLruCache.this)];
                    }

           public File getCleanFile(int i) {
               return new File(DiskLruCache.access$1600(DiskLruCache.this), this.key + "." + i);
                    }

           public File getDirtyFile(int i) {
               return new File(DiskLruCache.access$1600(DiskLruCache.this), this.key + "." + i + ".tmp");
                    }
                }
            }

