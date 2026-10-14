            package com.google.android.exoplayer.upstream.cache;

            import android.os.ConditionVariable;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.File;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.NavigableSet;
            import java.util.Set;
            import java.util.SortedSet;
            import java.util.TreeSet;
            
            
    public class SimpleCache implements Cache {
                private final File cacheDir;
                private final CacheEvictor evictor;
                private long totalSpace = 0;
                private final HashMap<String, CacheSpan> lockedSpans = new HashMap<>();
                private final HashMap<String, TreeSet<CacheSpan>> cachedSpans = new HashMap<>();
                private final HashMap<String, ArrayList<Cache.Listener>> listeners = new HashMap<>();

        static  void access$000(SimpleCache x0) {
            x0.initialize();
                }

        public SimpleCache(File cacheDir, CacheEvictor evictor) {
            this.cacheDir = cacheDir;
            this.evictor = evictor;
            ConditionVariable conditionVariable = new ConditionVariable();
            new C08761(conditionVariable).start();
            conditionVariable.block();
                }

                
        class C08761 extends Thread {
                    final  ConditionVariable val$conditionVariable;

            C08761(ConditionVariable conditionVariable) {
                        this.val$conditionVariable = conditionVariable;
                    }

                    @Override 
            public void run() {
                        synchronized (SimpleCache.this) {
                            try {
                        this.val$conditionVariable.open();
                        SimpleCache.access$000(SimpleCache.this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                @Override 
        public synchronized NavigableSet<CacheSpan> addListener(String key, Cache.Listener listener) {
                    try {
                        ArrayList<Cache.Listener> listenersForKey = this.listeners.get(key);
                if (listenersForKey == null) {
                    listenersForKey = new ArrayList<>();
                    this.listeners.put(key, listenersForKey);
                        }
                listenersForKey.add(listener);
                    } catch (Throwable th) {
                        throw th;
                    }
            return getCachedSpans(key);
                }

                @Override 
        public synchronized void removeListener(String key, Cache.Listener listener) {
                    try {
                        ArrayList<Cache.Listener> listenersForKey = this.listeners.get(key);
                if (listenersForKey != null) {
                    listenersForKey.remove(listener);
                    if (listenersForKey.isEmpty()) {
                        this.listeners.remove(key);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public synchronized NavigableSet<CacheSpan> getCachedSpans(String key) {
                    TreeSet<CacheSpan> spansForKey;
                    try {
                        spansForKey = this.cachedSpans.get(key);
                    } catch (Throwable th) {
                        throw th;
                    }
            return spansForKey == null ? null : new TreeSet((SortedSet) spansForKey);
                }

                @Override 
        public synchronized Set<String> getKeys() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return new HashSet(this.cachedSpans.keySet());
                }

                @Override 
       public synchronized long getCacheSpace() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.totalSpace;
                }

                @Override 
       public synchronized CacheSpan startReadWrite(String key, long position) throws InterruptedException {
                    CacheSpan lookupSpan = CacheSpan.createLookup(key, position);
                    CacheSpan span;
                    while (true) {
                   span = startReadWriteNonBlocking(lookupSpan);
                   if (span == null) {
                       wait();
                        } else {
                            return span;
                            }
                    }
                }

                @Override 
       public synchronized CacheSpan startReadWriteNonBlocking(String key, long position) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return startReadWriteNonBlocking(CacheSpan.createLookup(key, position));
                }

       private synchronized CacheSpan startReadWriteNonBlocking(CacheSpan lookupSpan) {
                    CacheSpan cacheSpan;
                    try {
                        CacheSpan spanningRegion = getSpan(lookupSpan);
               if (spanningRegion.isCached) {
                   TreeSet<CacheSpan> spansForKey = this.cachedSpans.get(spanningRegion.key);
                   Assertions.checkState(spansForKey.remove(spanningRegion));
                   CacheSpan spanningRegion2 = spanningRegion.touch();
                   spansForKey.add(spanningRegion2);
                   notifySpanTouched(spanningRegion, spanningRegion2);
                            cacheSpan = spanningRegion2;
                        } else if (!this.lockedSpans.containsKey(lookupSpan.key)) {
                   this.lockedSpans.put(lookupSpan.key, spanningRegion);
                            cacheSpan = spanningRegion;
                        } else {
                   cacheSpan = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return cacheSpan;
                }

                @Override 
       public synchronized File startFile(String key, long position, long length) {
                    try {
                        Assertions.checkState(this.lockedSpans.containsKey(key));
               if (!this.cacheDir.exists()) {
                   removeStaleSpans();
                   this.cacheDir.mkdirs();
                        }
               this.evictor.onStartFile(this, key, position, length);
                    } catch (Throwable th) {
                        throw th;
                    }
           return CacheSpan.getCacheFileName(this.cacheDir, key, position, System.currentTimeMillis());
                }

                @Override 
       public synchronized void commitFile(File file) {
                    try {
                        CacheSpan span = CacheSpan.createCacheEntry(file);
               Assertions.checkState(span != null);
               Assertions.checkState(this.lockedSpans.containsKey(span.key));
               if (file.exists()) {
                   long length = file.length();
                            if (length == 0) {
                       file.delete();
                            } else {
                       addSpan(span);
                       notifyAll();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void releaseHoleSpan(CacheSpan holeSpan) {
                    try {
               Assertions.checkState(holeSpan == this.lockedSpans.remove(holeSpan.key));
               notifyAll();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private CacheSpan getSpan(CacheSpan lookupSpan) {
           String key = lookupSpan.key;
           long offset = lookupSpan.position;
           TreeSet<CacheSpan> entries = this.cachedSpans.get(key);
           if (entries == null) {
               return CacheSpan.createOpenHole(key, lookupSpan.position);
                    }
           CacheSpan floorSpan = entries.floor(lookupSpan);
           if (floorSpan != null && floorSpan.position <= offset && offset < floorSpan.position + floorSpan.length) {
               if (!floorSpan.file.exists()) {
                   removeStaleSpans();
                   return getSpan(lookupSpan);
                        }
               return floorSpan;
                    }
           CacheSpan ceilEntry = entries.ceiling(lookupSpan);
           return ceilEntry == null ? CacheSpan.createOpenHole(key, lookupSpan.position) : CacheSpan.createClosedHole(key, lookupSpan.position, ceilEntry.position - lookupSpan.position);
                }

       private void initialize() {
           if (!this.cacheDir.exists()) {
               this.cacheDir.mkdirs();
                    }
           File[] files = this.cacheDir.listFiles();
           if (files != null) {
                        for (File file : files) {
                            if (file.length() == 0) {
                       file.delete();
                            } else {
                       CacheSpan span = CacheSpan.createCacheEntry(file);
                       if (span == null) {
                           file.delete();
                                } else {
                           addSpan(span);
                                }
                            }
                        }
                    }
                }

       private void addSpan(CacheSpan span) {
           TreeSet<CacheSpan> spansForKey = this.cachedSpans.get(span.key);
           if (spansForKey == null) {
               spansForKey = new TreeSet<>();
               this.cachedSpans.put(span.key, spansForKey);
                    }
           spansForKey.add(span);
           this.totalSpace += span.length;
           notifySpanAdded(span);
                }

                @Override 
       public synchronized void removeSpan(CacheSpan span) {
                    try {
                        TreeSet<CacheSpan> spansForKey = this.cachedSpans.get(span.key);
               this.totalSpace -= span.length;
               Assertions.checkState(spansForKey.remove(span));
               span.file.delete();
               if (spansForKey.isEmpty()) {
                   this.cachedSpans.remove(span.key);
                        }
               notifySpanRemoved(span);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void removeStaleSpans() {
           Iterator<Map.Entry<String, TreeSet<CacheSpan>>> iterator = this.cachedSpans.entrySet().iterator();
           while (iterator.hasNext()) {
               Map.Entry<String, TreeSet<CacheSpan>> next = iterator.next();
               Iterator<CacheSpan> spanIterator = next.getValue().iterator();
               boolean isEmpty = true;
               while (spanIterator.hasNext()) {
                   CacheSpan span = spanIterator.next();
                   if (!span.file.exists()) {
                       spanIterator.remove();
                       if (span.isCached) {
                           this.totalSpace -= span.length;
                                }
                       notifySpanRemoved(span);
                            } else {
                       isEmpty = false;
                            }
                        }
               if (isEmpty) {
                   iterator.remove();
                        }
                    }
                }

       private void notifySpanRemoved(CacheSpan span) {
           ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
           if (keyListeners != null) {
                        for (int i = keyListeners.size() - 1; i >= 0; i--) {
                   keyListeners.get(i).onSpanRemoved(this, span);
                        }
                    }
           this.evictor.onSpanRemoved(this, span);
                }

       private void notifySpanAdded(CacheSpan span) {
           ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
           if (keyListeners != null) {
                        for (int i = keyListeners.size() - 1; i >= 0; i--) {
                   keyListeners.get(i).onSpanAdded(this, span);
                        }
                    }
           this.evictor.onSpanAdded(this, span);
                }

       private void notifySpanTouched(CacheSpan oldSpan, CacheSpan newSpan) {
           ArrayList<Cache.Listener> keyListeners = this.listeners.get(oldSpan.key);
           if (keyListeners != null) {
                        for (int i = keyListeners.size() - 1; i >= 0; i--) {
                   keyListeners.get(i).onSpanTouched(this, oldSpan, newSpan);
                        }
                    }
           this.evictor.onSpanTouched(this, oldSpan, newSpan);
                }

                @Override 
       public synchronized boolean isCached(String key, long position, long length) {
                    boolean z;
                    try {
                        TreeSet<CacheSpan> entries = this.cachedSpans.get(key);
               if (entries == null) {
                   z = false;
                        } else {
                   CacheSpan lookupSpan = CacheSpan.createLookup(key, position);
                   CacheSpan floorSpan = entries.floor(lookupSpan);
                   if (floorSpan == null || floorSpan.position + floorSpan.length <= position) {
                       z = false;
                            } else {
                       long queryEndPosition = position + length;
                       long currentEndPosition = floorSpan.position + floorSpan.length;
                                if (currentEndPosition >= queryEndPosition) {
                           z = true;
                                } else {
                           for (CacheSpan next : entries.tailSet(floorSpan, false)) {
                                        if (next.position > currentEndPosition) {
                                   z = false;
                                        } else {
                                   currentEndPosition = Math.max(currentEndPosition, next.position + next.length);
                                            if (currentEndPosition >= queryEndPosition) {
                                       z = true;
                                            }
                                        }
                                    }
                           z = false;
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }
            }
