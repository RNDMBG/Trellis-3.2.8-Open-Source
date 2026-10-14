            package co.vine.android.cache.video;

            import android.content.Context;
            import android.os.Build;
            import android.os.StatFs;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.cache.UrlResource;
            import co.vine.android.cache.UrlResourceCache;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.NetworkOperationFactory;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.DiskLruCache;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.LinkedHashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class VideoCache<T> extends UrlResourceCache<VideoKey, UrlVideo, File, T> {
                private static final String CACHE_NAME = "video_cache";
                private static final int INDEX_TIMESTAMP = 1;
                private static final int INDEX_VIDEO_BYTES = 0;
                private static final int MAX_AVERAGE = 5;
                private static final int MAX_EXTERNAL_DISK_CACHE_SIZE = 157286400;
                private static final int MAX_INTERNAL_DISK_CACHE_SIZE = 52428800;
                private static final int VALUE_COUNT = 2;
                private static final int VERSION = 2;
                private static DiskLruCache sDiskCache;
                private final ArrayList<VideoListener> mListeners;
                private static final int[] LOCK = new int[0];
                private static boolean sIsCacheInitialized = false;
                private static final int[] averages = new int[5];
                private static int averagePosition = 0;
                private static int numberRecorded = 0;
                private static final int[] AVG_LOCK = new int[0];






/* 48 */        public static void resetAverageSpeed() {
                    synchronized (AVG_LOCK) {
                        try {
/* 50 */                    averagePosition = 0;
/* 51 */                    numberRecorded = 0;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 55 */        private static void reportAverageSpeed(int speed) {
                    synchronized (AVG_LOCK) {
                        try {
/* 57 */                    SLog.m447d("Latest download speed: {}", Integer.valueOf(speed));
/* 58 */                    int nextPos = averagePosition % 5;
/* 59 */                    averages[nextPos] = speed;
/* 60 */                    averagePosition = (nextPos + 1) % 5;
/* 61 */                    numberRecorded++;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 65 */        public static int getCurrentAverageSpeed() {
                    int i;
                    synchronized (AVG_LOCK) {
/* 67 */                int total = 0;
                        try {
/* 68 */                    if (numberRecorded == 0) {
/* 69 */                        i = Integer.MAX_VALUE;
                            } else {
/* 71 */                        int count = Math.min(numberRecorded, 5);
                                for (int i2 = 0; i2 < count; i2++) {
/* 73 */                            total += averages[i2];
                                }
/* 75 */                        i = total / count;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 75 */            return i;
                }

/* 87 */        public VideoCache(Context context, T networkContext, NetworkOperationFactory<T> factory) {
/* 88 */            super(context, 0, networkContext, factory);
/* 89 */            this.mListeners = new ArrayList<>();
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 102 */       public String getFile(long ownerId, VideoKey key, boolean peekOnly) {
/* 103 */           UrlVideo video = get(ownerId, key, key.url, true, peekOnly);
/* 104 */           if (video != null) {
/* 105 */               return ((File) video.value).getAbsolutePath();
                    }
                    return null;
                }

/* 121 */       public UrlVideo get(long ownerId, VideoKey key) {
/* 122 */           return (UrlVideo) super.get(ownerId, key, key.url, true);
                }

/* 125 */       private static DiskLruCache getCacheInstance(Context context) {
                    DiskLruCache diskLruCache;
                    synchronized (LOCK) {
                        try {
/* 127 */                   if (!sIsCacheInitialized) {
/* 128 */                       DiskLruCache diskCache = null;
                                try {
/* 130 */                           File cacheDir = CommonUtil.getExternalCacheDir(context);
/* 131 */                           if (cacheDir != null) {
/* 132 */                               diskCache = prepareCache(cacheDir, 157286400L, false);
                                    } else if (Build.VERSION.SDK_INT < 14) {
/* 135 */                               diskCache = prepareCache(context.getDir(CACHE_NAME, 1), 52428800L, false);
                                    } else {
/* 139 */                               diskCache = prepareCache(context.getCacheDir(), 52428800L, false);
                                    }
                                } catch (IOException e) {
                                }
/* 145 */                       sDiskCache = diskCache;
/* 146 */                       sIsCacheInitialized = true;
                            }
/* 148 */                   diskLruCache = sDiskCache;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return diskLruCache;
                }

/* 152 */       public static void invalidateCache() {
                    synchronized (LOCK) {
                        try {
/* 154 */                   sDiskCache = null;
/* 155 */                   sIsCacheInitialized = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 164 */       public static void evictExpiredEntries(Context context, int lifetime) {
/* 165 */           DiskLruCache diskCache = getCacheInstance(context);
/* 166 */           LinkedHashSet<String> entrySet = new LinkedHashSet<>(diskCache.getLruEntries().keySet());
/* 169 */           for (String key : entrySet) {
                        try {
/* 171 */                   DiskLruCache.Snapshot snapshot = diskCache.get(key, true);
/* 172 */                   if (snapshot != null) {
/* 177 */                       ByteBuffer buffer = ByteBuffer.allocate(8);
/* 178 */                       InputStream is = snapshot.getInputStream(1);
/* 179 */                       byte[] b = new byte[8];
/* 180 */                       if (is.read(b, 0, 8) > 0) {
/* 181 */                           buffer.put(b).flip();
/* 182 */                           long age = System.currentTimeMillis() - buffer.getLong();
                                    if (age > ((long) lifetime) * CommonUtil.HOUR_MILLIS) {
/* 184 */                               diskCache.remove(key);
                                    } else {
/* 193 */                               return;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } catch (IOException e) {
/* 193 */                   return;
                        }
                    }
                }

/* 196 */       private static DiskLruCache prepareCache(File dir, long absMax, boolean appendCacheName) throws IOException {
/* 197 */           long available = -1;
                    try {
/* 199 */               StatFs stat = new StatFs(dir.getPath());
/* 201 */               if (Build.VERSION.SDK_INT >= 18) {
                            try {
/* 203 */                       available = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
                            } catch (NoSuchMethodError e) {
                            }
                        }
                        if (available < 0) {
/* 210 */                   available = ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks());
                        }
                    } catch (Exception e2) {
/* 214 */               CrashUtil.log("Failed to statf, but it is ok, we will get the aval size in other ways on this phone.");
                    }
                    if (available < 0) {
/* 217 */               available = dir.getUsableSpace();
                    }
/* 221 */           long maxSize = (available <= 0 || available > absMax) ? absMax : available / 10;
/* 225 */           SLog.m449d("Preparing a DiskLruCache with absMax={}, available={}, maxSize={}", Long.valueOf(absMax), Long.valueOf(available), Long.valueOf(maxSize));
/* 227 */           if (appendCacheName) {
                        dir = new File(dir, CACHE_NAME);
                    }
                    return DiskLruCache.open(dir, 2, 2, maxSize);
                }

/* 230 */       protected static File getCacheDir() {
                    File directory;
                    synchronized (LOCK) {
                        try {
/* 233 */                   directory = sDiskCache != null ? sDiskCache.getDirectory() : null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 235 */           return directory;
                }

/* 245 */       public void addListener(VideoListener listener) {
/* 246 */           this.mListeners.add(listener);
                }

/* 254 */       public void removeListener(VideoListener listener) {
/* 255 */           this.mListeners.remove(listener);
                }

/* 259 */       protected UrlVideo loadResource(long ownerId, VideoKey key, String url) {
                    UrlVideo urlVideoInstantiateResource = null;
/* 260 */           String diskCacheKey = CommonUtil.md5Digest(url);
/* 261 */           if (diskCacheKey != null) {
                        synchronized (LOCK) {
                            try {
/* 266 */                       DiskLruCache diskCache = getCacheInstance(this.mContext);
/* 267 */                       if (diskCache != null && !diskCache.isClosed()) {
                                    try {
/* 269 */                               DiskLruCache.Snapshot snapshot = diskCache.get(diskCacheKey);
/* 270 */                               if (snapshot == null) {
/* 276 */                                   CommonUtil.closeSilently(snapshot);
                                        } else {
/* 273 */                                   urlVideoInstantiateResource = instantiateResource(key, url, new File(snapshot.getPath(0)));
/* 276 */                                   CommonUtil.closeSilently(snapshot);
                                        }
                                    } catch (IOException e) {
/* 276 */                               CommonUtil.closeSilently(null);
                                    } catch (Throwable th) {
/* 264 */                               CommonUtil.closeSilently(null);
                                        throw th;
                                    }
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
/* 280 */           return urlVideoInstantiateResource;
                }

/* 283 */       public void prepopulateVideoCacheForUrl(long ownerId, VideoKey key, String url, InputStream is) {
/* 284 */           saveResource(ownerId, key, url, is, false);
/* 285 */           instantiateResource(key, url, (File) null);
                }

/* 289 */       protected UrlVideo saveResource(long ownerId, VideoKey key, String url, InputStream is, boolean reportSpeed) {
/* 290 */           String diskCacheKey = CommonUtil.md5Digest(url);
/* 291 */           DiskLruCache.Editor editor = null;
/* 292 */           UrlVideo ret = null;
                    synchronized (LOCK) {
                        try {
/* 294 */                   DiskLruCache diskCache = getCacheInstance(this.mContext);
/* 295 */                   if (diskCache != null && !diskCache.isClosed()) {
/* 296 */                       boolean success = false;
                                try {
/* 298 */                           editor = diskCache.edit(diskCacheKey);
/* 299 */                           if (editor != null) {
/* 300 */                               long startTime = System.currentTimeMillis();
/* 301 */                               int bytesLen = CommonUtil.readFullyWriteTo(is, editor.newOutputStream(0), 4096);
/* 303 */                               success = bytesLen > 0;
/* 304 */                               long time = (System.currentTimeMillis() - startTime) / 1000;
/* 306 */                               SLog.m448d("Bytes {} Download time: {}.", Integer.valueOf(bytesLen), Long.valueOf(time));
/* 307 */                               if (bytesLen > 0 && reportSpeed) {
/* 308 */                                   reportAverageSpeed((int) Math.round(((double) (bytesLen / 1024)) / Math.max(1L, time)));
                                        }
/* 311 */                               ByteBuffer buffer = ByteBuffer.allocate(8);
/* 312 */                               buffer.putLong(System.currentTimeMillis());
/* 313 */                               editor.newOutputStream(1).write(buffer.array());
                                    }
/* 318 */                           if (editor != null) {
                                        try {
/* 322 */                                   if (success) {
/* 323 */                                       editor.commit();
/* 324 */                                       diskCache.flush();
/* 325 */                                       File f = new File(diskCache.get(diskCacheKey, false).getPath(0));
/* 327 */                                       ret = new UrlVideo(url, f);
                                            } else {
/* 329 */                                       editor.abort();
/* 330 */                                       ret = obtainResource(key, url, is);
/* 331 */                                       CrashUtil.log("Failed to save a video to DiskLruCache");
                                            }
                                        } catch (IOException e) {
                                        }
                                    }
                                } catch (IOException e2) {
/* 318 */                           if (editor != null) {
                                        try {
/* 322 */                                   if (0 != 0) {
/* 323 */                                       editor.commit();
/* 324 */                                       diskCache.flush();
/* 325 */                                       File f2 = new File(diskCache.get(diskCacheKey, false).getPath(0));
/* 327 */                                       ret = new UrlVideo(url, f2);
                                            } else {
/* 329 */                                       editor.abort();
/* 330 */                                       ret = obtainResource(key, url, is);
/* 331 */                                       CrashUtil.log("Failed to save a video to DiskLruCache");
                                            }
                                        } catch (IOException e3) {
                                        }
                                    }
                                } catch (Throwable th) {
                                    if (editor != null) {
                                        try {
/* 322 */                                   if (success) {
/* 323 */                                       editor.commit();
/* 324 */                                       diskCache.flush();
/* 325 */                                       File f3 = new File(diskCache.get(diskCacheKey, false).getPath(0));
/* 327 */                                       new UrlVideo(url, f3);
                                            } else {
/* 329 */                                       editor.abort();
/* 330 */                                       obtainResource(key, url, is);
/* 331 */                                       CrashUtil.log("Failed to save a video to DiskLruCache");
                                            }
                                        } catch (IOException e4) {
                                        }
                                    }
/* 334 */                           throw new RuntimeException(th);
                                }
                            } else {
/* 339 */                       ret = obtainResource(key, url, is);
/* 340 */                       CrashUtil.log("Videos DiskLruCache could not be opened");
                            }
                        } catch (RuntimeException e) {
                            throw e;
                        } catch (Error e) {
                            throw e;
                        }
                    }
/* 343 */           return ret;
                }

/* 347 */       protected UrlVideo instantiateResource(VideoKey key, String url, File file) {
/* 348 */           return new UrlVideo(url, file);
                }

                @Override // co.vine.android.cache.UrlResourceCache
/* 353 */       protected void onResourceLoaded(HashMap<VideoKey, UrlVideo> resources) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
/* 355 */               this.mListeners.get(i).onVideoPathObtained(this, resources);
                    }
                }

/* 360 */       protected void onResourceError(VideoKey key, HttpResult result) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
/* 362 */               this.mListeners.get(i).onVideoPathError(this, key, result);
                    }
                }

/* 367 */       protected UrlVideo obtainResource(VideoKey key, String url, InputStream is) {
/* 368 */           File cacheDir = CommonUtil.getCacheDir(this.mContext);
/* 370 */           if (cacheDir != null) {
/* 371 */               String md5 = CommonUtil.md5Digest(url);
/* 372 */               if (md5 == null) {
/* 373 */                   md5 = CommonUtil.randomString(10);
                        }
/* 375 */               File f = new File(cacheDir, md5);
/* 380 */               FileOutputStream out = null;
                        try {
                            try {
/* 382 */                       long startTime = System.currentTimeMillis();
/* 383 */                       FileOutputStream out2 = new FileOutputStream(f);
                                try {
/* 384 */                           int bytesLen = CommonUtil.readFullyWriteTo(is, out2, 4096);
/* 385 */                           if (bytesLen != 0) {
/* 388 */                               long time = (System.currentTimeMillis() - startTime) / 1000;
/* 389 */                               if (bytesLen > 0) {
/* 390 */                                   SLog.m447d("Download time: {}.", Long.valueOf(time));
/* 391 */                                   reportAverageSpeed((int) Math.round(((double) (bytesLen / 1024)) / Math.max(1L, time)));
                                        } else {
/* 393 */                                   SLog.m447d("Invalid bytesLen or time: {}", Integer.valueOf(bytesLen));
                                        }
/* 395 */                               out2.flush();
/* 400 */                               CommonUtil.closeSilently(out2);
/* 402 */                               return new UrlVideo(url, f);
                                    }
/* 400 */                           CommonUtil.closeSilently(out2);
/* 386 */                           return null;
                                } catch (IOException e) {
/* 396 */                           e = e;
                                    out = out2;
/* 397 */                           SLog.m480w("Failed to download file.", (Throwable) e);
/* 400 */                           CommonUtil.closeSilently(out);
/* 398 */                           return null;
                                } catch (Throwable th) {
                                    th = th;
                                    out = out2;
                                    CommonUtil.closeSilently(out);
                                    throw new RuntimeException(th);
                                }
                            } catch (IOException e2) {
                                SLog.m480w("Failed to download file.", (Throwable) e2);
                                return null;
                            }
                        } catch (Throwable th2) {
                            throw new RuntimeException(th2);
                        }
                    } else {
/* 377 */               SLog.m477w("cache dir is null.");
/* 378 */               return null;
                    }
                }

/* 411 */       public String peekPath(VideoKey key) {
/* 412 */           UrlVideo video = (UrlVideo) super.peek(key);
/* 413 */           if (video != null) {
/* 414 */               return video.url;
                    }
                    return null;
                }
            }
