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
            
            
    public final class DiskLruCache implements Closeable {
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

        static  BufferedSink access$000(DiskLruCache x0) {
            return x0.journalWriter;
                }

        static  void access$100(DiskLruCache x0) throws IOException {
            x0.trimToSize();
                }

        static  Editor access$1900(DiskLruCache x0, String x1, long x2) throws IOException {
            return x0.edit(x1, x2);
                }

        static  boolean access$200(DiskLruCache x0) {
            return x0.journalRebuildRequired();
                }

        static  String access$2000(Source x0) throws IOException {
            return sourceToString(x0);
                }

        static  int access$2100(DiskLruCache x0) {
            return x0.valueCount;
                }

        static  File access$2200(DiskLruCache x0) {
            return x0.directory;
                }

        static  Sink access$2300() {
            return NULL_SINK;
                }

        static  void access$2400(DiskLruCache x0, Editor x1, boolean x2) throws IOException {
            x0.completeEdit(x1, x2);
                }

        static  boolean access$2500(DiskLruCache x0, Entry x1) throws IOException {
            return x0.removeEntry(x1);
                }

        static  void access$300(DiskLruCache x0) throws IOException {
            x0.rebuildJournal();
                }

        static  int access$402(DiskLruCache x0, int x1) {
            x0.redundantOpCount = x1;
                    return x1;
                }

                
       class RunnableC12691 implements Runnable {
           RunnableC12691() {
                    }

                    @Override 
           public void run() {
                        synchronized (DiskLruCache.this) {
                            try {
                       if (DiskLruCache.access$000(DiskLruCache.this) != null) {
                                    try {
                               DiskLruCache.access$100(DiskLruCache.this);
                               if (DiskLruCache.access$200(DiskLruCache.this)) {
                                   DiskLruCache.access$300(DiskLruCache.this);
                                   DiskLruCache.access$402(DiskLruCache.this, 0);
                                        }
                                    } catch (IOException e) {
                               throw new RuntimeException(e);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       private DiskLruCache(File directory, int appVersion, int valueCount, long maxSize) {
           this.directory = directory;
           this.appVersion = appVersion;
           this.journalFile = new File(directory, JOURNAL_FILE);
           this.journalFileTmp = new File(directory, JOURNAL_FILE_TEMP);
           this.journalFileBackup = new File(directory, JOURNAL_FILE_BACKUP);
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
           File backupFile = new File(directory, JOURNAL_FILE_BACKUP);
           if (backupFile.exists()) {
               File journalFile = new File(directory, JOURNAL_FILE);
               if (journalFile.exists()) {
                   backupFile.delete();
                        } else {
                   renameTo(backupFile, journalFile, false);
                        }
                    }
           DiskLruCache cache = new DiskLruCache(directory, appVersion, valueCount, maxSize);
           if (cache.journalFile.exists()) {
                        try {
                   cache.readJournal();
                   cache.processJournal();
                            return cache;
                        } catch (IOException journalIsCorrupt) {
                   Platform.get().logW("DiskLruCache " + directory + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
                   cache.delete();
                        }
                    }
           directory.mkdirs();
           DiskLruCache cache2 = new DiskLruCache(directory, appVersion, valueCount, maxSize);
           cache2.rebuildJournal();
                    return cache2;
                }

       private void readJournal() throws IOException {
           BufferedSource source = Okio.buffer(Okio.source(this.journalFile));
                    try {
               String magic = source.readUtf8LineStrict();
               String version = source.readUtf8LineStrict();
               String appVersionString = source.readUtf8LineStrict();
               String valueCountString = source.readUtf8LineStrict();
               String blank = source.readUtf8LineStrict();
               if (!MAGIC.equals(magic) || !VERSION_1.equals(version) || !Integer.toString(this.appVersion).equals(appVersionString) || !Integer.toString(this.valueCount).equals(valueCountString) || !"".equals(blank)) {
                   throw new IOException("unexpected journal header: [" + magic + ", " + version + ", " + valueCountString + ", " + blank + "]");
                        }
               int lineCount = 0;
                        while (true) {
                            try {
                       readJournalLine(source.readUtf8LineStrict());
                       lineCount++;
                            } catch (EOFException e) {
                       this.redundantOpCount = lineCount - this.lruEntries.size();
                       if (!source.exhausted()) {
                           rebuildJournal();
                                } else {
                           this.journalWriter = Okio.buffer(Okio.appendingSink(this.journalFile));
                                }
                       Util.closeQuietly(source);
                       return;
                            }
                        }
                    } catch (Throwable th) {
                        Util.closeQuietly(source);
                        throw th;
                    }
                }

       private void readJournalLine(String line) throws IOException {
                    String key;
           int firstSpace = line.indexOf(32);
           if (firstSpace == -1) {
               throw new IOException("unexpected journal line: " + line);
                    }
           int keyBegin = firstSpace + 1;
           int secondSpace = line.indexOf(32, keyBegin);
           if (secondSpace == -1) {
               key = line.substring(keyBegin);
               if (firstSpace == REMOVE.length() && line.startsWith(REMOVE)) {
                   this.lruEntries.remove(key);
                   return;
                        }
                    } else {
               key = line.substring(keyBegin, secondSpace);
                    }
           Entry entry = this.lruEntries.get(key);
           if (entry == null) {
               entry = new Entry(this, key, null);
               this.lruEntries.put(key, entry);
                    }
           if (secondSpace != -1 && firstSpace == CLEAN.length() && line.startsWith(CLEAN)) {
               String[] parts = line.substring(secondSpace + 1).split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
               Entry.access$602(entry, true);
               Entry.access$702(entry, null);
               Entry.access$800(entry, parts);
               return;
                    }
           if (secondSpace == -1 && firstSpace == DIRTY.length() && line.startsWith(DIRTY)) {
               Entry.access$702(entry, new Editor(this, entry, null));
                    } else if (secondSpace != -1 || firstSpace != READ.length() || !line.startsWith(READ)) {
               throw new IOException("unexpected journal line: " + line);
                    }
                }

       private void processJournal() throws IOException {
           deleteIfExists(this.journalFileTmp);
           Iterator<Entry> i = this.lruEntries.values().iterator();
                    while (i.hasNext()) {
               Entry entry = i.next();
               if (Entry.access$700(entry) == null) {
                            for (int t = 0; t < this.valueCount; t++) {
                       this.size += Entry.access$1000(entry)[t];
                            }
                        } else {
                   Entry.access$702(entry, null);
                            for (int t2 = 0; t2 < this.valueCount; t2++) {
                       deleteIfExists(Entry.access$1100(entry)[t2]);
                       deleteIfExists(Entry.access$1200(entry)[t2]);
                            }
                   i.remove();
                        }
                    }
                }

       private synchronized void rebuildJournal() throws IOException {
                    try {
                        if (this.journalWriter != null) {
                   this.journalWriter.close();
                        }
               BufferedSink writer = Okio.buffer(Okio.sink(this.journalFileTmp));
                        try {
                   writer.writeUtf8(MAGIC).writeByte(10);
                   writer.writeUtf8(VERSION_1).writeByte(10);
                   writer.writeUtf8(Integer.toString(this.appVersion)).writeByte(10);
                   writer.writeUtf8(Integer.toString(this.valueCount)).writeByte(10);
                   writer.writeByte(10);
                            for (Entry entry : this.lruEntries.values()) {
                       if (Entry.access$700(entry) != null) {
                           writer.writeUtf8(DIRTY).writeByte(32);
                           writer.writeUtf8(Entry.access$1300(entry));
                           writer.writeByte(10);
                                } else {
                           writer.writeUtf8(CLEAN).writeByte(32);
                           writer.writeUtf8(Entry.access$1300(entry));
                           writer.writeUtf8(entry.getLengths());
                           writer.writeByte(10);
                                }
                            }
                   writer.close();
                   if (this.journalFile.exists()) {
                       renameTo(this.journalFile, this.journalFileBackup, true);
                            }
                   renameTo(this.journalFileTmp, this.journalFile, false);
                   this.journalFileBackup.delete();
                   this.journalWriter = Okio.buffer(Okio.appendingSink(this.journalFile));
                        } catch (Throwable th) {
                            writer.close();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

       private static void deleteIfExists(File file) throws IOException {
           if (!file.delete() && file.exists()) {
               throw new IOException("failed to delete " + file);
                    }
                }

       private static void renameTo(File from, File to, boolean deleteDestination) throws IOException {
           if (deleteDestination) {
               deleteIfExists(to);
                    }
           if (!from.renameTo(to)) {
               throw new IOException();
                    }
                }

       public synchronized Snapshot get(String key) throws IOException {
                    Snapshot snapshot = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
                   validateKey(key);
                   Entry entry = this.lruEntries.get(key);
                   if (entry != null && Entry.access$600(entry)) {
                       Source[] sources = new Source[this.valueCount];
                                for (int i = 0; i < this.valueCount; i++) {
                                    try {
                               sources[i] = Okio.source(Entry.access$1100(entry)[i]);
                                    } catch (FileNotFoundException e) {
                               for (int i2 = 0; i2 < this.valueCount && sources[i2] != null; i2++) {
                                   Util.closeQuietly(sources[i2]);
                                        }
                                    }
                                }
                       this.redundantOpCount++;
                       this.journalWriter.writeUtf8(READ).writeByte(32).writeUtf8(key).writeByte(10);
                       if (journalRebuildRequired()) {
                           this.executorService.execute(this.cleanupRunnable);
                                }
                       snapshot = new Snapshot(this, key, Entry.access$1400(entry), sources, Entry.access$1000(entry), null);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return snapshot;
                }

       public Editor edit(String key) throws IOException {
           return edit(key, -1L);
                }

       private synchronized Editor edit(String key, long expectedSequenceNumber) throws IOException {
                    Editor editor = null;
                    synchronized (this) {
                        try {
                            checkNotClosed();
                   validateKey(key);
                   Entry entry = this.lruEntries.get(key);
                            if (expectedSequenceNumber == -1 || (entry != null && Entry.access$1400(entry) == expectedSequenceNumber)) {
                       if (entry == null) {
                           entry = new Entry(this, key, null);
                           this.lruEntries.put(key, entry);
                                } else if (Entry.access$700(entry) == null) {
                                }
                       editor = new Editor(this, entry, null);
                       Entry.access$702(entry, editor);
                       this.journalWriter.writeUtf8(DIRTY).writeByte(32).writeUtf8(key).writeByte(10);
                       this.journalWriter.flush();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return editor;
                }

       public File getDirectory() {
           return this.directory;
                }

       public synchronized long getMaxSize() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxSize;
                }

       public synchronized void setMaxSize(long maxSize) {
                    try {
                        this.maxSize = maxSize;
               this.executorService.execute(this.cleanupRunnable);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized long size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.size;
                }

                
                
                
                
                
                
                
                
                
       private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
                    int i = 0;
                    File dirty;
                    try {
                        Entry entry = Editor.access$1600(editor);
               if (Entry.access$700(entry) != editor) {
                   throw new IllegalStateException();
                        }
               if (success && !Entry.access$600(entry)) {
                            for (int i2 = 0; i2 < this.valueCount; i2++) {
                       if (!Editor.access$1700(editor)[i2]) {
                           editor.abort();
                           throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                                }
                       if (!Entry.access$1200(entry)[i2].exists()) {
                           editor.abort();
                                }
                            }
                            for (i = 0; i < this.valueCount; i++) {
                       dirty = Entry.access$1200(entry)[i];
                       if (success) {
                           if (dirty.exists()) {
                               File clean = Entry.access$1100(entry)[i];
                               dirty.renameTo(clean);
                               long oldLength = Entry.access$1000(entry)[i];
                               long newLength = clean.length();
                               Entry.access$1000(entry)[i] = newLength;
                               this.size = (this.size - oldLength) + newLength;
                                    }
                                } else {
                           deleteIfExists(dirty);
                                }
                            }
                   this.redundantOpCount++;
                   Entry.access$702(entry, null);
                   if (Entry.access$600(entry) | success) {
                       Entry.access$602(entry, true);
                       this.journalWriter.writeUtf8(CLEAN).writeByte(32);
                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
                       this.journalWriter.writeUtf8(entry.getLengths());
                       this.journalWriter.writeByte(10);
                       if (success) {
                           long j = this.nextSequenceNumber;
                                    this.nextSequenceNumber = 1 + j;
                                    Entry.access$1402(entry, j);
                                }
                            } else {
                       this.lruEntries.remove(Entry.access$1300(entry));
                       this.journalWriter.writeUtf8(REMOVE).writeByte(32);
                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
                       this.journalWriter.writeByte(10);
                            }
                   this.journalWriter.flush();
                            if (this.size <= this.maxSize) {
                       this.executorService.execute(this.cleanupRunnable);
                            } else {
                       this.executorService.execute(this.cleanupRunnable);
                            }
                        } else {
                            while (i < this.valueCount) {
                       dirty = Entry.access$1200(entry)[i];
                       if (success) {
                           if (dirty.exists()) {
                               File clean2 = Entry.access$1100(entry)[i];
                               dirty.renameTo(clean2);
                               long oldLength2 = Entry.access$1000(entry)[i];
                               long newLength2 = clean2.length();
                               Entry.access$1000(entry)[i] = newLength2;
                               this.size = (this.size - oldLength2) + newLength2;
                                    }
                                } else {
                           deleteIfExists(dirty);
                                }
                            }
                   this.redundantOpCount++;
                   Entry.access$702(entry, null);
                   if (Entry.access$600(entry) | success) {
                       Entry.access$602(entry, true);
                       this.journalWriter.writeUtf8(CLEAN).writeByte(32);
                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
                       this.journalWriter.writeUtf8(entry.getLengths());
                       this.journalWriter.writeByte(10);
                       if (success) {
                           long j2 = this.nextSequenceNumber;
                                    this.nextSequenceNumber = 1 + j2;
                                    Entry.access$1402(entry, j2);
                                }
                            } else {
                       this.lruEntries.remove(Entry.access$1300(entry));
                       this.journalWriter.writeUtf8(REMOVE).writeByte(32);
                       this.journalWriter.writeUtf8(Entry.access$1300(entry));
                       this.journalWriter.writeByte(10);
                            }
                   this.journalWriter.flush();
                            if (this.size <= this.maxSize || journalRebuildRequired()) {
                       this.executorService.execute(this.cleanupRunnable);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private boolean journalRebuildRequired() {
                    return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
                }

       public synchronized boolean remove(String key) throws IOException {
                    Entry entry;
                    try {
                        checkNotClosed();
               validateKey(key);
               entry = this.lruEntries.get(key);
                    } catch (Throwable th) {
                        throw th;
                    }
           return entry == null ? false : removeEntry(entry);
                }

       private boolean removeEntry(Entry entry) throws IOException {
           if (Entry.access$700(entry) != null) {
               Editor.access$1802(Entry.access$700(entry), true);
                    }
                    for (int i = 0; i < this.valueCount; i++) {
               File file = Entry.access$1100(entry)[i];
               deleteIfExists(file);
               this.size -= Entry.access$1000(entry)[i];
               Entry.access$1000(entry)[i] = 0;
                    }
           this.redundantOpCount++;
           this.journalWriter.writeUtf8(REMOVE).writeByte(32).writeUtf8(Entry.access$1300(entry)).writeByte(10);
           this.lruEntries.remove(Entry.access$1300(entry));
           if (journalRebuildRequired()) {
               this.executorService.execute(this.cleanupRunnable);
                    }
                    return true;
                }

       public boolean isClosed() {
                    return this.journalWriter == null;
                }

       private void checkNotClosed() {
           if (this.journalWriter == null) {
               throw new IllegalStateException("cache is closed");
                    }
                }

       public synchronized void flush() throws IOException {
                    try {
                        checkNotClosed();
               trimToSize();
               this.journalWriter.flush();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void close() throws IOException {
                    try {
                        if (this.journalWriter != null) {
                   Entry[] arr$ = (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
                            for (Entry entry : arr$) {
                       if (Entry.access$700(entry) != null) {
                           Entry.access$700(entry).abort();
                                }
                            }
                   trimToSize();
                   this.journalWriter.close();
                   this.journalWriter = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void trimToSize() throws IOException {
                    while (this.size > this.maxSize) {
               Entry toEvict = this.lruEntries.values().iterator().next();
               removeEntry(toEvict);
                    }
                }

       public void delete() throws IOException {
           close();
           Util.deleteContents(this.directory);
                }

       public synchronized void evictAll() throws IOException {
                    try {
                        Entry[] arr$ = (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
                        for (Entry entry : arr$) {
                   removeEntry(entry);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void validateKey(String key) {
           Matcher matcher = LEGAL_KEY_PATTERN.matcher(key);
           if (!matcher.matches()) {
               throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + key + "\"");
                    }
                }

       private static String sourceToString(Source in) throws IOException {
                    try {
               String utf8 = Okio.buffer(in).readUtf8();
               Util.closeQuietly(in);
                        return utf8;
                    } catch (Throwable th) {
                        Util.closeQuietly(in);
                        throw th;
                    }
                }

       public final class Snapshot implements Closeable {
                    private final String key;
                    private final long[] lengths;
                    private final long sequenceNumber;
                    private final Source[] sources;

            Snapshot(DiskLruCache x0, String x1, long x2, Source[] x3, long[] x4, RunnableC12691 x5) {
               this(x1, x2, x3, x4);
                    }

           private Snapshot(String key, long sequenceNumber, Source[] sources, long[] lengths) {
               this.key = key;
               this.sequenceNumber = sequenceNumber;
               this.sources = sources;
               this.lengths = lengths;
                    }

           public Editor edit() throws IOException {
               return DiskLruCache.access$1900(DiskLruCache.this, this.key, this.sequenceNumber);
                    }

           public Source getSource(int index) {
               return this.sources[index];
                    }

           public String getString(int index) throws IOException {
               return DiskLruCache.access$2000(getSource(index));
                    }

           public long getLength(int index) {
               return this.lengths[index];
                    }

                    @Override 
           public void close() {
               Source[] arr$ = this.sources;
                        for (Source in : arr$) {
                   Util.closeQuietly(in);
                        }
                    }
                }

                
       static class C12702 implements Sink {
           C12702() {
                    }

                    @Override 
           public void write(Buffer source, long byteCount) throws IOException {
                    }

                    @Override 
           public void flush() throws IOException {
                    }

                    @Override 
           public Timeout timeout() {
               return Timeout.NONE;
                    }

                    @Override 
           public void close() throws IOException {
                    }
                }

       public final class Editor {
                    private boolean committed;
                    private final Entry entry;
                    private boolean hasErrors;
                    private final boolean[] written;

            Editor(DiskLruCache x0, Entry x1, RunnableC12691 x2) {
               this(x1);
                    }

           static  Entry access$1600(Editor x0) {
               return x0.entry;
                    }

           static  boolean[] access$1700(Editor x0) {
               return x0.written;
                    }

           static  boolean access$1802(Editor x0, boolean x1) {
               x0.hasErrors = x1;
                        return x1;
                    }

           private Editor(Entry entry) {
               this.entry = entry;
               this.written = Entry.access$600(entry) ? null : new boolean[DiskLruCache.access$2100(DiskLruCache.this)];
                    }

           public Source newSource(int index) throws IOException {
                        Source source = null;
                        synchronized (DiskLruCache.this) {
                            try {
                       if (Entry.access$700(this.entry) != this) {
                           throw new IllegalStateException();
                                }
                       if (Entry.access$600(this.entry)) {
                                    try {
                               source = Okio.source(Entry.access$1100(this.entry)[index]);
                                    } catch (FileNotFoundException e) {
                                    }
                                }
                       return source;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           public String getString(int index) throws IOException {
               Source source = newSource(index);
               if (source != null) {
                            return DiskLruCache.access$2000(source);
                        }
                        return null;
                    }

           public Sink newSink(int index) throws IOException {
                        Sink sinkAccess$2300;
                        Sink sink = DiskLruCache.access$2300();
                        synchronized (DiskLruCache.this) {
                            try {
                       if (Entry.access$700(this.entry) != this) {
                           throw new IllegalStateException();
                                }
                       if (!Entry.access$600(this.entry)) {
                           this.written[index] = true;
                                }
                       File dirtyFile = Entry.access$1200(this.entry)[index];
                                try {
                           sink = Okio.sink(dirtyFile);
                                } catch (FileNotFoundException e) {
                           DiskLruCache.access$2200(DiskLruCache.this).mkdirs();
                                    try {
                               sink = Okio.sink(dirtyFile);
                                    } catch (FileNotFoundException e2) {
                               sink = DiskLruCache.access$2300();
                                    }
                                }
                       sinkAccess$2300 = new FaultHidingSink(sink);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return sinkAccess$2300;
                    }

           public void set(int index, String value) throws IOException {
               BufferedSink writer = Okio.buffer(newSink(index));
               writer.writeUtf8(value);
               writer.close();
                    }

           public void commit() throws IOException {
                        synchronized (DiskLruCache.this) {
                            try {
                       if (this.hasErrors) {
                           DiskLruCache.access$2400(DiskLruCache.this, this, false);
                           DiskLruCache.access$2500(DiskLruCache.this, this.entry);
                                } else {
                           DiskLruCache.access$2400(DiskLruCache.this, this, true);
                                }
                       this.committed = true;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           public void abort() throws IOException {
                        synchronized (DiskLruCache.this) {
                            try {
                       DiskLruCache.access$2400(DiskLruCache.this, this, false);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           public void abortUnlessCommitted() {
                        synchronized (DiskLruCache.this) {
                            try {
                       if (!this.committed) {
                                    try {
                               DiskLruCache.access$2400(DiskLruCache.this, this, false);
                                    } catch (IOException e) {
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           private class FaultHidingSink extends ForwardingSink {
               public FaultHidingSink(Sink delegate) {
                   super(delegate);
                        }

                        @Override 
               public void write(Buffer source, long byteCount) throws IOException {
                            try {
                       super.write(source, byteCount);
                            } catch (IOException e) {
                                synchronized (DiskLruCache.this) {
                                    try {
                               Editor.access$1802(Editor.this, true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }

                        @Override 
               public void flush() throws IOException {
                            try {
                       super.flush();
                            } catch (IOException e) {
                                synchronized (DiskLruCache.this) {
                                    try {
                               Editor.access$1802(Editor.this, true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }

                        @Override 
               public void close() throws IOException {
                            try {
                       super.close();
                            } catch (IOException e) {
                                synchronized (DiskLruCache.this) {
                                    try {
                               Editor.access$1802(Editor.this, true);
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    }
                }

       private final class Entry {
                    private final File[] cleanFiles;
                    private Editor currentEditor;
                    private final File[] dirtyFiles;
                    private final String key;
                    private final long[] lengths;
                    private boolean readable;
                    private long sequenceNumber;

            Entry(DiskLruCache x0, String x1, RunnableC12691 x2) {
               this(x1);
                    }

           static  long[] access$1000(Entry x0) {
               return x0.lengths;
                    }

           static  File[] access$1100(Entry x0) {
               return x0.cleanFiles;
                    }

           static  File[] access$1200(Entry x0) {
               return x0.dirtyFiles;
                    }

           static  String access$1300(Entry x0) {
               return x0.key;
                    }

           static  long access$1400(Entry x0) {
               return x0.sequenceNumber;
                    }

           static  long access$1402(Entry x0, long x1) {
               x0.sequenceNumber = x1;
                        return x1;
                    }

           static  boolean access$600(Entry x0) {
               return x0.readable;
                    }

           static  boolean access$602(Entry x0, boolean x1) {
               x0.readable = x1;
                        return x1;
                    }

           static  Editor access$700(Entry x0) {
               return x0.currentEditor;
                    }

           static  Editor access$702(Entry x0, Editor x1) {
               x0.currentEditor = x1;
                        return x1;
                    }

           static  void access$800(Entry x0, String[] x1) throws IOException {
               x0.setLengths(x1);
                    }

           private Entry(String key) {
               this.key = key;
               this.lengths = new long[DiskLruCache.access$2100(DiskLruCache.this)];
               this.cleanFiles = new File[DiskLruCache.access$2100(DiskLruCache.this)];
               this.dirtyFiles = new File[DiskLruCache.access$2100(DiskLruCache.this)];
               StringBuilder fileBuilder = new StringBuilder(key).append(FilenameUtils.EXTENSION_SEPARATOR);
               int truncateTo = fileBuilder.length();
                        for (int i = 0; i < DiskLruCache.access$2100(DiskLruCache.this); i++) {
                   fileBuilder.append(i);
                   this.cleanFiles[i] = new File(DiskLruCache.access$2200(DiskLruCache.this), fileBuilder.toString());
                   fileBuilder.append(".tmp");
                   this.dirtyFiles[i] = new File(DiskLruCache.access$2200(DiskLruCache.this), fileBuilder.toString());
                   fileBuilder.setLength(truncateTo);
                        }
                    }

           public String getLengths() throws IOException {
               StringBuilder result = new StringBuilder();
               long[] arr$ = this.lengths;
                        for (long size : arr$) {
                   result.append(' ').append(size);
                        }
               return result.toString();
                    }

           private void setLengths(String[] strings) throws IOException {
               if (strings.length != DiskLruCache.access$2100(DiskLruCache.this)) {
                   throw invalidLengths(strings);
                        }
                        for (int i = 0; i < strings.length; i++) {
                            try {
                       this.lengths[i] = Long.parseLong(strings[i]);
                            } catch (NumberFormatException e) {
                       throw invalidLengths(strings);
                            }
                        }
                    }

           private IOException invalidLengths(String[] strings) throws IOException {
               throw new IOException("unexpected journal line: " + Arrays.toString(strings));
                    }
                }
            }
