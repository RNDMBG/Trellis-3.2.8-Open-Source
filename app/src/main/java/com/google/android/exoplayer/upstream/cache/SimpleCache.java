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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class SimpleCache implements Cache {
                private final File cacheDir;
                private final CacheEvictor evictor;
                private long totalSpace = 0;
                private final HashMap<String, CacheSpan> lockedSpans = new HashMap<>();
                private final HashMap<String, TreeSet<CacheSpan>> cachedSpans = new HashMap<>();
                private final HashMap<String, ArrayList<Cache.Listener>> listeners = new HashMap<>();

/* 34 */        static /* synthetic */ void access$000(SimpleCache x0) {
/* 35 */            x0.initialize();
                }

/* 49 */        public SimpleCache(File cacheDir, CacheEvictor evictor) {
/* 51 */            this.cacheDir = cacheDir;
/* 52 */            this.evictor = evictor;
/* 57 */            ConditionVariable conditionVariable = new ConditionVariable();
/* 58 */            new C08761(conditionVariable).start();
/* 67 */            conditionVariable.block();
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.upstream.cache.SimpleCache$1 */
/* 56 */        class C08761 extends Thread {
                    final /* synthetic */ ConditionVariable val$conditionVariable;

/* 57 */            C08761(ConditionVariable conditionVariable) {
                        this.val$conditionVariable = conditionVariable;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 60 */            public void run() {
                        synchronized (SimpleCache.this) {
                            try {
/* 62 */                        this.val$conditionVariable.open();
/* 63 */                        SimpleCache.access$000(SimpleCache.this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 71 */        public synchronized NavigableSet<CacheSpan> addListener(String key, Cache.Listener listener) {
                    try {
                        ArrayList<Cache.Listener> listenersForKey = this.listeners.get(key);
/* 73 */                if (listenersForKey == null) {
/* 74 */                    listenersForKey = new ArrayList<>();
/* 75 */                    this.listeners.put(key, listenersForKey);
                        }
/* 77 */                listenersForKey.add(listener);
                    } catch (Throwable th) {
                        throw th;
                    }
/* 78 */            return getCachedSpans(key);
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 82 */        public synchronized void removeListener(String key, Cache.Listener listener) {
                    try {
                        ArrayList<Cache.Listener> listenersForKey = this.listeners.get(key);
/* 84 */                if (listenersForKey != null) {
/* 85 */                    listenersForKey.remove(listener);
/* 86 */                    if (listenersForKey.isEmpty()) {
/* 87 */                        this.listeners.remove(key);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 93 */        public synchronized NavigableSet<CacheSpan> getCachedSpans(String key) {
                    TreeSet<CacheSpan> spansForKey;
                    try {
                        spansForKey = this.cachedSpans.get(key);
                    } catch (Throwable th) {
                        throw th;
                    }
/* 95 */            return spansForKey == null ? null : new TreeSet((SortedSet) spansForKey);
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 99 */        public synchronized Set<String> getKeys() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return new HashSet(this.cachedSpans.keySet());
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 104 */       public synchronized long getCacheSpace() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.totalSpace;
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 110 */       public synchronized CacheSpan startReadWrite(String key, long position) throws InterruptedException {
                    CacheSpan lookupSpan = CacheSpan.createLookup(key, position);
                    CacheSpan span;
                    while (true) {
/* 113 */                   span = startReadWriteNonBlocking(lookupSpan);
/* 114 */                   if (span == null) {
/* 121 */                       wait();
                        } else {
                            return span;
                            }
                    }
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 127 */       public synchronized CacheSpan startReadWriteNonBlocking(String key, long position) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return startReadWriteNonBlocking(CacheSpan.createLookup(key, position));
                }

/* 131 */       private synchronized CacheSpan startReadWriteNonBlocking(CacheSpan lookupSpan) {
                    CacheSpan cacheSpan;
                    try {
                        CacheSpan spanningRegion = getSpan(lookupSpan);
/* 135 */               if (spanningRegion.isCached) {
/* 136 */                   TreeSet<CacheSpan> spansForKey = this.cachedSpans.get(spanningRegion.key);
/* 139 */                   Assertions.checkState(spansForKey.remove(spanningRegion));
/* 141 */                   CacheSpan spanningRegion2 = spanningRegion.touch();
/* 143 */                   spansForKey.add(spanningRegion2);
/* 144 */                   notifySpanTouched(spanningRegion, spanningRegion2);
                            cacheSpan = spanningRegion2;
                        } else if (!this.lockedSpans.containsKey(lookupSpan.key)) {
/* 150 */                   this.lockedSpans.put(lookupSpan.key, spanningRegion);
                            cacheSpan = spanningRegion;
                        } else {
/* 155 */                   cacheSpan = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return cacheSpan;
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 159 */       public synchronized File startFile(String key, long position, long length) {
                    try {
                        Assertions.checkState(this.lockedSpans.containsKey(key));
/* 161 */               if (!this.cacheDir.exists()) {
/* 163 */                   removeStaleSpans();
/* 164 */                   this.cacheDir.mkdirs();
                        }
/* 166 */               this.evictor.onStartFile(this, key, position, length);
                    } catch (Throwable th) {
                        throw th;
                    }
/* 167 */           return CacheSpan.getCacheFileName(this.cacheDir, key, position, System.currentTimeMillis());
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 171 */       public synchronized void commitFile(File file) {
                    try {
                        CacheSpan span = CacheSpan.createCacheEntry(file);
/* 173 */               Assertions.checkState(span != null);
/* 174 */               Assertions.checkState(this.lockedSpans.containsKey(span.key));
/* 176 */               if (file.exists()) {
/* 180 */                   long length = file.length();
                            if (length == 0) {
/* 182 */                       file.delete();
                            } else {
/* 185 */                       addSpan(span);
/* 186 */                       notifyAll();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 190 */       public synchronized void releaseHoleSpan(CacheSpan holeSpan) {
                    try {
/* 191 */               Assertions.checkState(holeSpan == this.lockedSpans.remove(holeSpan.key));
/* 192 */               notifyAll();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 206 */       private CacheSpan getSpan(CacheSpan lookupSpan) {
/* 207 */           String key = lookupSpan.key;
/* 208 */           long offset = lookupSpan.position;
/* 209 */           TreeSet<CacheSpan> entries = this.cachedSpans.get(key);
/* 210 */           if (entries == null) {
/* 211 */               return CacheSpan.createOpenHole(key, lookupSpan.position);
                    }
/* 213 */           CacheSpan floorSpan = entries.floor(lookupSpan);
/* 214 */           if (floorSpan != null && floorSpan.position <= offset && offset < floorSpan.position + floorSpan.length) {
/* 217 */               if (!floorSpan.file.exists()) {
/* 222 */                   removeStaleSpans();
/* 223 */                   return getSpan(lookupSpan);
                        }
/* 227 */               return floorSpan;
                    }
/* 226 */           CacheSpan ceilEntry = entries.ceiling(lookupSpan);
/* 227 */           return ceilEntry == null ? CacheSpan.createOpenHole(key, lookupSpan.position) : CacheSpan.createClosedHole(key, lookupSpan.position, ceilEntry.position - lookupSpan.position);
                }

/* 235 */       private void initialize() {
/* 236 */           if (!this.cacheDir.exists()) {
/* 237 */               this.cacheDir.mkdirs();
                    }
/* 239 */           File[] files = this.cacheDir.listFiles();
/* 240 */           if (files != null) {
                        for (File file : files) {
                            if (file.length() == 0) {
/* 246 */                       file.delete();
                            } else {
/* 248 */                       CacheSpan span = CacheSpan.createCacheEntry(file);
/* 249 */                       if (span == null) {
/* 250 */                           file.delete();
                                } else {
/* 252 */                           addSpan(span);
                                }
                            }
                        }
                    }
                }

/* 263 */       private void addSpan(CacheSpan span) {
/* 264 */           TreeSet<CacheSpan> spansForKey = this.cachedSpans.get(span.key);
/* 265 */           if (spansForKey == null) {
/* 266 */               spansForKey = new TreeSet<>();
/* 267 */               this.cachedSpans.put(span.key, spansForKey);
                    }
/* 269 */           spansForKey.add(span);
/* 270 */           this.totalSpace += span.length;
/* 271 */           notifySpanAdded(span);
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 275 */       public synchronized void removeSpan(CacheSpan span) {
                    try {
                        TreeSet<CacheSpan> spansForKey = this.cachedSpans.get(span.key);
/* 277 */               this.totalSpace -= span.length;
/* 278 */               Assertions.checkState(spansForKey.remove(span));
/* 279 */               span.file.delete();
/* 280 */               if (spansForKey.isEmpty()) {
/* 281 */                   this.cachedSpans.remove(span.key);
                        }
/* 283 */               notifySpanRemoved(span);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 290 */       private void removeStaleSpans() {
/* 291 */           Iterator<Map.Entry<String, TreeSet<CacheSpan>>> iterator = this.cachedSpans.entrySet().iterator();
/* 292 */           while (iterator.hasNext()) {
/* 293 */               Map.Entry<String, TreeSet<CacheSpan>> next = iterator.next();
/* 294 */               Iterator<CacheSpan> spanIterator = next.getValue().iterator();
/* 295 */               boolean isEmpty = true;
/* 296 */               while (spanIterator.hasNext()) {
/* 297 */                   CacheSpan span = spanIterator.next();
/* 298 */                   if (!span.file.exists()) {
/* 299 */                       spanIterator.remove();
/* 300 */                       if (span.isCached) {
/* 301 */                           this.totalSpace -= span.length;
                                }
/* 303 */                       notifySpanRemoved(span);
                            } else {
/* 305 */                       isEmpty = false;
                            }
                        }
/* 308 */               if (isEmpty) {
/* 309 */                   iterator.remove();
                        }
                    }
                }

/* 314 */       private void notifySpanRemoved(CacheSpan span) {
/* 315 */           ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
/* 316 */           if (keyListeners != null) {
                        for (int i = keyListeners.size() - 1; i >= 0; i--) {
/* 318 */                   keyListeners.get(i).onSpanRemoved(this, span);
                        }
                    }
/* 321 */           this.evictor.onSpanRemoved(this, span);
                }

/* 324 */       private void notifySpanAdded(CacheSpan span) {
/* 325 */           ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
/* 326 */           if (keyListeners != null) {
                        for (int i = keyListeners.size() - 1; i >= 0; i--) {
/* 328 */                   keyListeners.get(i).onSpanAdded(this, span);
                        }
                    }
/* 331 */           this.evictor.onSpanAdded(this, span);
                }

/* 334 */       private void notifySpanTouched(CacheSpan oldSpan, CacheSpan newSpan) {
/* 335 */           ArrayList<Cache.Listener> keyListeners = this.listeners.get(oldSpan.key);
/* 336 */           if (keyListeners != null) {
                        for (int i = keyListeners.size() - 1; i >= 0; i--) {
/* 338 */                   keyListeners.get(i).onSpanTouched(this, oldSpan, newSpan);
                        }
                    }
/* 341 */           this.evictor.onSpanTouched(this, oldSpan, newSpan);
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache
/* 345 */       public synchronized boolean isCached(String key, long position, long length) {
                    boolean z;
                    try {
                        TreeSet<CacheSpan> entries = this.cachedSpans.get(key);
/* 347 */               if (entries == null) {
/* 348 */                   z = false;
                        } else {
/* 350 */                   CacheSpan lookupSpan = CacheSpan.createLookup(key, position);
/* 351 */                   CacheSpan floorSpan = entries.floor(lookupSpan);
/* 352 */                   if (floorSpan == null || floorSpan.position + floorSpan.length <= position) {
/* 354 */                       z = false;
                            } else {
/* 356 */                       long queryEndPosition = position + length;
/* 357 */                       long currentEndPosition = floorSpan.position + floorSpan.length;
                                if (currentEndPosition >= queryEndPosition) {
/* 360 */                           z = true;
                                } else {
/* 363 */                           for (CacheSpan next : entries.tailSet(floorSpan, false)) {
                                        if (next.position > currentEndPosition) {
/* 367 */                                   z = false;
                                        } else {
/* 371 */                                   currentEndPosition = Math.max(currentEndPosition, next.position + next.length);
                                            if (currentEndPosition >= queryEndPosition) {
/* 374 */                                       z = true;
                                            }
                                        }
                                    }
/* 378 */                           z = false;
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }
            }
