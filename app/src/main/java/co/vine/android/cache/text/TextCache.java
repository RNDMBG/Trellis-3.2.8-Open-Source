            package co.vine.android.cache.text;

            import android.content.Context;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.cache.PipingInputStream;
            import co.vine.android.cache.UrlResource;
            import co.vine.android.cache.UrlResourceCache;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.NetworkOperationFactory;
            import co.vine.android.util.CommonUtil;
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.DiskLruCache;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.File;
            import java.io.IOException;
            import java.io.InputStream;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.HashMap;
            import org.apache.commons.io.IOUtils;
            
            
    public class TextCache<T> extends UrlResourceCache<TextKey, UrlText, byte[], T> {
                private static final String DISK_CACHE_NAME = "text_cache";
                private static final int INDEX_TEXT_CACHE_STREAM = 0;
                private static final int MAX_EXTERNAL_DISK_CACHE_SIZE = 31457280;
                private static final int MAX_INTERNAL_DISK_CACHE_SIZE = 10485760;
                private static final int VALUE_COUNT = 1;
                private static final int VERSION = 2;
                private static DiskLruCache sDiskCache;
                private final ArrayList<TextListener> mListeners;
                private static final int[] LOCK = new int[0];
                private static boolean sIsCacheInitialized = false;






        public TextCache(Context context, int cacheSize, T networkContext, NetworkOperationFactory<T> factory) {
            super(context, cacheSize, networkContext, factory);
            this.mListeners = new ArrayList<>();
                }

        private static DiskLruCache getDiskLruCache(Context context) {
                    DiskLruCache diskLruCache;
                    synchronized (LOCK) {
                        try {
                    if (!sIsCacheInitialized) {
                        DiskLruCache diskCache = null;
                                try {
                            File cacheDir = CommonUtil.getExternalCacheDir(context);
                            if (cacheDir != null) {
                                diskCache = DiskLruCache.open(new File(cacheDir, DISK_CACHE_NAME), 2, 1, 31457280L);
                                    } else {
                                diskCache = DiskLruCache.open(new File(context.getCacheDir(), DISK_CACHE_NAME), 2, 1, 10485760L);
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

        static File getCacheDir() {
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

        public void addListener(TextListener listener) {
            this.mListeners.add(listener);
                }

       public void removeListener(TextListener listener) {
           this.mListeners.remove(listener);
                }

                @Override 
       protected void onResourceLoaded(HashMap<TextKey, UrlText> text) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
               this.mListeners.get(i).onPathObtained(this, text);
                    }
                }

       protected void onResourceError(TextKey key, HttpResult result) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
               this.mListeners.get(i).onPathError(this, key, result);
                    }
                }

       protected UrlText instantiateResource(TextKey key, String url, byte[] b) {
                    try {
               return new UrlText(url, b);
                    } catch (IOException e) {
               return new UrlText(url);
                    }
                }

       public UrlText get(TextKey key) {
           return get(0L, key, key.url, false, true);
                }

       protected UrlText loadResource(long ownerId, TextKey key, String url) throws Throwable {
           String diskCacheKey = CommonUtil.md5Digest(url);
           if (diskCacheKey == null) {
               return null;
                    }
           DiskLruCache.Snapshot snapshot = null;
           InputStream is = null;
           BufferedInputStream bis = null;
                    synchronized (LOCK) {
                        try {
                   DiskLruCache diskCache = getDiskLruCache(this.mContext);
                   if (diskCache != null && !diskCache.isClosed()) {
                                try {
                           snapshot = diskCache.get(diskCacheKey);
                           if (snapshot == null) {
                               CommonUtil.closeSilently(snapshot);
                               CommonUtil.closeSilently(null);
                               CommonUtil.closeSilently(null);
                               return null;
                                    }
                           is = snapshot.getInputStream(0);
                           if (is == null) {
                               CommonUtil.closeSilently(snapshot);
                               CommonUtil.closeSilently(is);
                               CommonUtil.closeSilently(null);
                               return null;
                                    }
                           BufferedInputStream bis2 = new BufferedInputStream(is, 4096);
                                    try {
                               byte[] bytes = new byte[8];
                               bis2.read(bytes);
                               ByteBuffer bb = ByteBuffer.wrap(bytes);
                               long cacheTime = bb.getLong();
                               UrlText res = obtainResource(key, url, (InputStream) bis2);
                               if (res != null) {
                                   res.cacheTime = cacheTime;
                                        }
                                        try {
                                   CommonUtil.closeSilently(snapshot);
                                   CommonUtil.closeSilently(is);
                                   CommonUtil.closeSilently(bis2);
                                   return res;
                                        } catch (Throwable th) {
                                   th = th;
                                            throw new RuntimeException(th);
                                        }
                                    } catch (IOException e) {
                                        bis = bis2;
                                    } catch (Throwable th2) {
                                        bis = bis2;
                                        CommonUtil.closeSilently(snapshot);
                               CommonUtil.closeSilently(is);
                               CommonUtil.closeSilently(bis);
                                        throw th2;
                                    }
                                } catch (IOException e2) {
                                } catch (Throwable th3) {
                                    throw th3;
                                }
                       CommonUtil.closeSilently(snapshot);
                       CommonUtil.closeSilently(is);
                       CommonUtil.closeSilently(bis);
                            }
                   return null;
                        } catch (Throwable th4) {
                            throw th4;
                        }
                    }
                }

       public UrlText save(TextKey key, InputStream is) {
           return saveResource(0L, key, key.url, is, false);
                }

       protected UrlText saveResource(long ownerId, TextKey key, String url, InputStream is, boolean reportSpeed) {
                    UrlText result;
           String diskCacheKey = CommonUtil.md5Digest(url);
           if (diskCacheKey == null) {
               return obtainResource(key, url, is);
                    }
           DiskLruCache.Editor editor = null;
           BufferedOutputStream os = null;
                    synchronized (LOCK) {
                        try {
                   DiskLruCache diskCache = getDiskLruCache(this.mContext);
                   if (diskCache != null && !diskCache.isClosed()) {
                       boolean success = false;
                                try {
                           editor = diskCache.edit(diskCacheKey);
                           if (editor != null) {
                               BufferedOutputStream os2 = new BufferedOutputStream(editor.newOutputStream(0), 4096);
                                        try {
                                   long time = System.currentTimeMillis();
                                   ByteBuffer buffer = ByteBuffer.allocate(8);
                                   buffer.putLong(time);
                                   os2.write(buffer.array());
                                   result = obtainResource(key, url, (InputStream) new PipingInputStream(is, os2));
                                   result.cacheTime = time;
                                   success = result.isValid();
                                   if (success) {
                                       os2.flush();
                                       os2.close();
                                       os = null;
                                            } else {
                                                os = os2;
                                            }
                                   if (editor != null) {
                                                try {
                                           if (success) {
                                               editor.commit();
                                               diskCache.flush();
                                                    } else {
                                               editor.abort();
                                                    }
                                                } catch (IOException e) {
                                                }
                                            }
                                   CommonUtil.closeSilently(os);
                                        } catch (IOException e2) {
                                            os = os2;
                                   if (editor != null) {
                                                try {
                                           if (0 != 0) {
                                               editor.commit();
                                               diskCache.flush();
                                                    } else {
                                               editor.abort();
                                                    }
                                                } catch (IOException e3) {
                                                }
                                            }
                                   CommonUtil.closeSilently(os);
                                   return obtainResource(key, url, is);
                                        } catch (Throwable th) {
                                   th = th;
                                            os = os2;
                                            if (editor != null) {
                                                try {
                                           if (success) {
                                               editor.commit();
                                               diskCache.flush();
                                                    } else {
                                               editor.abort();
                                                    }
                                                } catch (IOException e4) {
                                                }
                                            }
                                   CommonUtil.closeSilently(os);
                                            throw new RuntimeException(th);
                                        }
                                    } else {
                               result = obtainResource(key, url, is);
                               if (editor != null) {
                                            try {
                                       if (0 != 0) {
                                           editor.commit();
                                           diskCache.flush();
                                                } else {
                                           editor.abort();
                                                }
                                            } catch (IOException e5) {
                                            }
                                        }
                               CommonUtil.closeSilently(null);
                                    }
                           return result;
                                } catch (IOException e6) {
                                } catch (RuntimeException e) {
                                    throw e;
                                } catch (Error e) {
                                    throw e;
                                }
                            }
                   return obtainResource(key, url, is);
                        } catch (RuntimeException e) {
                            throw e;
                        } catch (Error e) {
                            throw e;
                        }
                    }
                }

       protected UrlText obtainResource(TextKey key, String url, InputStream is) {
                    try {
               return instantiateResource(key, url, IOUtils.toByteArray(is));
                    } catch (Exception e) {
               SLog.m459e("Failed to obtain cache.", (Throwable) e);
               return null;
                    }
                }
            }
