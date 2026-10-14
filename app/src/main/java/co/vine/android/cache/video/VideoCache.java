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
            
            
    public class VideoCache<T> extends UrlResourceCache<VideoKey, UrlVideo, File, T> {
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






        public static void resetAverageSpeed() {
                    synchronized (AVG_LOCK) {
                        try {
                    averagePosition = 0;
                    numberRecorded = 0;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        private static void reportAverageSpeed(int speed) {
                    synchronized (AVG_LOCK) {
                        try {
                    SLog.m447d("Latest download speed: {}", Integer.valueOf(speed));
                    int nextPos = averagePosition % 5;
                    averages[nextPos] = speed;
                    averagePosition = (nextPos + 1) % 5;
                    numberRecorded++;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        public static int getCurrentAverageSpeed() {
                    int i;
                    synchronized (AVG_LOCK) {
                int total = 0;
                        try {
                    if (numberRecorded == 0) {
                        i = Integer.MAX_VALUE;
                            } else {
                        int count = Math.min(numberRecorded, 5);
                                for (int i2 = 0; i2 < count; i2++) {
                            total += averages[i2];
                                }
                        i = total / count;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
            return i;
                }

        public VideoCache(Context context, T networkContext, NetworkOperationFactory<T> factory) {
            super(context, 0, networkContext, factory);
            this.mListeners = new ArrayList<>();
                }

                
       public String getFile(long ownerId, VideoKey key, boolean peekOnly) {
           UrlVideo video = get(ownerId, key, key.url, true, peekOnly);
           if (video != null) {
               return ((File) video.value).getAbsolutePath();
                    }
                    return null;
                }

       public UrlVideo get(long ownerId, VideoKey key) {
           return (UrlVideo) super.get(ownerId, key, key.url, true);
                }

       private static DiskLruCache getCacheInstance(Context context) {
                    DiskLruCache diskLruCache;
                    synchronized (LOCK) {
                        try {
                   if (!sIsCacheInitialized) {
                       DiskLruCache diskCache = null;
                                try {
                           File cacheDir = CommonUtil.getExternalCacheDir(context);
                           if (cacheDir != null) {
                               diskCache = prepareCache(cacheDir, 157286400L, false);
                                    } else if (Build.VERSION.SDK_INT < 14) {
                               diskCache = prepareCache(context.getDir(CACHE_NAME, 1), 52428800L, false);
                                    } else {
                               diskCache = prepareCache(context.getCacheDir(), 52428800L, false);
                                    }
                                } catch (IOException e) {
                                }
                       sDiskCache = diskCache;
                       sIsCacheInitialized = true;
                            }
                   diskLruCache = sDiskCache;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return diskLruCache;
                }

       public static void invalidateCache() {
                    synchronized (LOCK) {
                        try {
                   sDiskCache = null;
                   sIsCacheInitialized = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static void evictExpiredEntries(Context context, int lifetime) {
           DiskLruCache diskCache = getCacheInstance(context);
           LinkedHashSet<String> entrySet = new LinkedHashSet<>(diskCache.getLruEntries().keySet());
           for (String key : entrySet) {
                        try {
                   DiskLruCache.Snapshot snapshot = diskCache.get(key, true);
                   if (snapshot != null) {
                       ByteBuffer buffer = ByteBuffer.allocate(8);
                       InputStream is = snapshot.getInputStream(1);
                       byte[] b = new byte[8];
                       if (is.read(b, 0, 8) > 0) {
                           buffer.put(b).flip();
                           long age = System.currentTimeMillis() - buffer.getLong();
                                    if (age > ((long) lifetime) * CommonUtil.HOUR_MILLIS) {
                               diskCache.remove(key);
                                    } else {
                               return;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } catch (IOException e) {
                   return;
                        }
                    }
                }

       private static DiskLruCache prepareCache(File dir, long absMax, boolean appendCacheName) throws IOException {
           long available = -1;
                    try {
               StatFs stat = new StatFs(dir.getPath());
               if (Build.VERSION.SDK_INT >= 18) {
                            try {
                       available = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
                            } catch (NoSuchMethodError e) {
                            }
                        }
                        if (available < 0) {
                   available = ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks());
                        }
                    } catch (Exception e2) {
               CrashUtil.log("Failed to statf, but it is ok, we will get the aval size in other ways on this phone.");
                    }
                    if (available < 0) {
               available = dir.getUsableSpace();
                    }
           long maxSize = (available <= 0 || available > absMax) ? absMax : available / 10;
           SLog.m449d("Preparing a DiskLruCache with absMax={}, available={}, maxSize={}", Long.valueOf(absMax), Long.valueOf(available), Long.valueOf(maxSize));
           if (appendCacheName) {
                        dir = new File(dir, CACHE_NAME);
                    }
                    return DiskLruCache.open(dir, 2, 2, maxSize);
                }

       protected static File getCacheDir() {
                    File directory;
                    synchronized (LOCK) {
                        try {
                   directory = sDiskCache != null ? sDiskCache.getDirectory() : null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return directory;
                }

       public void addListener(VideoListener listener) {
           this.mListeners.add(listener);
                }

       public void removeListener(VideoListener listener) {
           this.mListeners.remove(listener);
                }

       protected UrlVideo loadResource(long ownerId, VideoKey key, String url) {
                    UrlVideo urlVideoInstantiateResource = null;
           String diskCacheKey = CommonUtil.md5Digest(url);
           if (diskCacheKey != null) {
                        synchronized (LOCK) {
                            try {
                       DiskLruCache diskCache = getCacheInstance(this.mContext);
                       if (diskCache != null && !diskCache.isClosed()) {
                                    try {
                               DiskLruCache.Snapshot snapshot = diskCache.get(diskCacheKey);
                               if (snapshot == null) {
                                   CommonUtil.closeSilently(snapshot);
                                        } else {
                                   urlVideoInstantiateResource = instantiateResource(key, url, new File(snapshot.getPath(0)));
                                   CommonUtil.closeSilently(snapshot);
                                        }
                                    } catch (IOException e) {
                               CommonUtil.closeSilently(null);
                                    } catch (Throwable th) {
                               CommonUtil.closeSilently(null);
                                        throw th;
                                    }
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
           return urlVideoInstantiateResource;
                }

       public void prepopulateVideoCacheForUrl(long ownerId, VideoKey key, String url, InputStream is) {
           saveResource(ownerId, key, url, is, false);
           instantiateResource(key, url, (File) null);
                }

       protected UrlVideo saveResource(long ownerId, VideoKey key, String url, InputStream is, boolean reportSpeed) {
           String diskCacheKey = CommonUtil.md5Digest(url);
           DiskLruCache.Editor editor = null;
           UrlVideo ret = null;
                    synchronized (LOCK) {
                        try {
                   DiskLruCache diskCache = getCacheInstance(this.mContext);
                   if (diskCache != null && !diskCache.isClosed()) {
                       boolean success = false;
                                try {
                           editor = diskCache.edit(diskCacheKey);
                           if (editor != null) {
                               long startTime = System.currentTimeMillis();
                               int bytesLen = CommonUtil.readFullyWriteTo(is, editor.newOutputStream(0), 4096);
                               success = bytesLen > 0;
                               long time = (System.currentTimeMillis() - startTime) / 1000;
                               SLog.m448d("Bytes {} Download time: {}.", Integer.valueOf(bytesLen), Long.valueOf(time));
                               if (bytesLen > 0 && reportSpeed) {
                                   reportAverageSpeed((int) Math.round(((double) (bytesLen / 1024)) / Math.max(1L, time)));
                                        }
                               ByteBuffer buffer = ByteBuffer.allocate(8);
                               buffer.putLong(System.currentTimeMillis());
                               editor.newOutputStream(1).write(buffer.array());
                                    }
                           if (editor != null) {
                                        try {
                                   if (success) {
                                       editor.commit();
                                       diskCache.flush();
                                       File f = new File(diskCache.get(diskCacheKey, false).getPath(0));
                                       ret = new UrlVideo(url, f);
                                            } else {
                                       editor.abort();
                                       ret = obtainResource(key, url, is);
                                       CrashUtil.log("Failed to save a video to DiskLruCache");
                                            }
                                        } catch (IOException e) {
                                        }
                                    }
                                } catch (IOException e2) {
                           if (editor != null) {
                                        try {
                                   if (0 != 0) {
                                       editor.commit();
                                       diskCache.flush();
                                       File f2 = new File(diskCache.get(diskCacheKey, false).getPath(0));
                                       ret = new UrlVideo(url, f2);
                                            } else {
                                       editor.abort();
                                       ret = obtainResource(key, url, is);
                                       CrashUtil.log("Failed to save a video to DiskLruCache");
                                            }
                                        } catch (IOException e3) {
                                        }
                                    }
                                } catch (Throwable th) {
                                    if (editor != null) {
                                        try {
                                   if (success) {
                                       editor.commit();
                                       diskCache.flush();
                                       File f3 = new File(diskCache.get(diskCacheKey, false).getPath(0));
                                       new UrlVideo(url, f3);
                                            } else {
                                       editor.abort();
                                       obtainResource(key, url, is);
                                       CrashUtil.log("Failed to save a video to DiskLruCache");
                                            }
                                        } catch (IOException e4) {
                                        }
                                    }
                           throw new RuntimeException(th);
                                }
                            } else {
                       ret = obtainResource(key, url, is);
                       CrashUtil.log("Videos DiskLruCache could not be opened");
                            }
                        } catch (RuntimeException e) {
                            throw e;
                        } catch (Error e) {
                            throw e;
                        }
                    }
           return ret;
                }

       protected UrlVideo instantiateResource(VideoKey key, String url, File file) {
           return new UrlVideo(url, file);
                }

                @Override 
       protected void onResourceLoaded(HashMap<VideoKey, UrlVideo> resources) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
               this.mListeners.get(i).onVideoPathObtained(this, resources);
                    }
                }

       protected void onResourceError(VideoKey key, HttpResult result) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
               this.mListeners.get(i).onVideoPathError(this, key, result);
                    }
                }

       protected UrlVideo obtainResource(VideoKey key, String url, InputStream is) {
           File cacheDir = CommonUtil.getCacheDir(this.mContext);
           if (cacheDir != null) {
               String md5 = CommonUtil.md5Digest(url);
               if (md5 == null) {
                   md5 = CommonUtil.randomString(10);
                        }
               File f = new File(cacheDir, md5);
               FileOutputStream out = null;
                        try {
                            try {
                       long startTime = System.currentTimeMillis();
                       FileOutputStream out2 = new FileOutputStream(f);
                                try {
                           int bytesLen = CommonUtil.readFullyWriteTo(is, out2, 4096);
                           if (bytesLen != 0) {
                               long time = (System.currentTimeMillis() - startTime) / 1000;
                               if (bytesLen > 0) {
                                   SLog.m447d("Download time: {}.", Long.valueOf(time));
                                   reportAverageSpeed((int) Math.round(((double) (bytesLen / 1024)) / Math.max(1L, time)));
                                        } else {
                                   SLog.m447d("Invalid bytesLen or time: {}", Integer.valueOf(bytesLen));
                                        }
                               out2.flush();
                               CommonUtil.closeSilently(out2);
                               return new UrlVideo(url, f);
                                    }
                           CommonUtil.closeSilently(out2);
                           return null;
                                } catch (IOException e) {
                           e = e;
                                    out = out2;
                           SLog.m480w("Failed to download file.", (Throwable) e);
                           CommonUtil.closeSilently(out);
                           return null;
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
               SLog.m477w("cache dir is null.");
               return null;
                    }
                }

       public String peekPath(VideoKey key) {
           UrlVideo video = (UrlVideo) super.peek(key);
           if (video != null) {
               return video.url;
                    }
                    return null;
                }
            }
