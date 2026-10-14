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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class TextCache<T> extends UrlResourceCache<TextKey, UrlText, byte[], T> {
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






/* 49 */        public TextCache(Context context, int cacheSize, T networkContext, NetworkOperationFactory<T> factory) {
/* 50 */            super(context, cacheSize, networkContext, factory);
/* 51 */            this.mListeners = new ArrayList<>();
                }

/* 54 */        private static DiskLruCache getDiskLruCache(Context context) {
                    DiskLruCache diskLruCache;
                    synchronized (LOCK) {
                        try {
/* 56 */                    if (!sIsCacheInitialized) {
/* 57 */                        DiskLruCache diskCache = null;
                                try {
/* 59 */                            File cacheDir = CommonUtil.getExternalCacheDir(context);
/* 60 */                            if (cacheDir != null) {
/* 61 */                                diskCache = DiskLruCache.open(new File(cacheDir, DISK_CACHE_NAME), 2, 1, 31457280L);
                                    } else {
/* 65 */                                diskCache = DiskLruCache.open(new File(context.getCacheDir(), DISK_CACHE_NAME), 2, 1, 10485760L);
                                    }
                                } catch (IOException e) {
                                }
/* 71 */                        sDiskCache = diskCache;
/* 72 */                        sIsCacheInitialized = true;
                            }
/* 74 */                    diskLruCache = sDiskCache;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return diskLruCache;
                }

/* 78 */        static File getCacheDir() {
                    File directory;
                    synchronized (LOCK) {
                        try {
/* 81 */                    directory = sDiskCache != null ? sDiskCache.getDirectory() : null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 83 */            return directory;
                }

/* 93 */        public void addListener(TextListener listener) {
/* 94 */            this.mListeners.add(listener);
                }

/* 102 */       public void removeListener(TextListener listener) {
/* 103 */           this.mListeners.remove(listener);
                }

                @Override // co.vine.android.cache.UrlResourceCache
/* 108 */       protected void onResourceLoaded(HashMap<TextKey, UrlText> text) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
/* 110 */               this.mListeners.get(i).onPathObtained(this, text);
                    }
                }

/* 115 */       protected void onResourceError(TextKey key, HttpResult result) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
/* 117 */               this.mListeners.get(i).onPathError(this, key, result);
                    }
                }

/* 123 */       protected UrlText instantiateResource(TextKey key, String url, byte[] b) {
                    try {
/* 124 */               return new UrlText(url, b);
                    } catch (IOException e) {
/* 126 */               return new UrlText(url);
                    }
                }

/* 130 */       public UrlText get(TextKey key) {
/* 131 */           return get(0L, key, key.url, false, true);
                }

/* 135 */       protected UrlText loadResource(long ownerId, TextKey key, String url) throws Throwable {
/* 136 */           String diskCacheKey = CommonUtil.md5Digest(url);
/* 137 */           if (diskCacheKey == null) {
/* 138 */               return null;
                    }
/* 141 */           DiskLruCache.Snapshot snapshot = null;
/* 142 */           InputStream is = null;
/* 143 */           BufferedInputStream bis = null;
                    synchronized (LOCK) {
                        try {
/* 145 */                   DiskLruCache diskCache = getDiskLruCache(this.mContext);
/* 146 */                   if (diskCache != null && !diskCache.isClosed()) {
                                try {
/* 148 */                           snapshot = diskCache.get(diskCacheKey);
/* 149 */                           if (snapshot == null) {
/* 172 */                               CommonUtil.closeSilently(snapshot);
/* 173 */                               CommonUtil.closeSilently(null);
/* 174 */                               CommonUtil.closeSilently(null);
/* 150 */                               return null;
                                    }
/* 152 */                           is = snapshot.getInputStream(0);
/* 153 */                           if (is == null) {
/* 172 */                               CommonUtil.closeSilently(snapshot);
/* 173 */                               CommonUtil.closeSilently(is);
/* 174 */                               CommonUtil.closeSilently(null);
/* 154 */                               return null;
                                    }
/* 156 */                           BufferedInputStream bis2 = new BufferedInputStream(is, 4096);
                                    try {
/* 157 */                               byte[] bytes = new byte[8];
/* 158 */                               bis2.read(bytes);
/* 160 */                               ByteBuffer bb = ByteBuffer.wrap(bytes);
/* 161 */                               long cacheTime = bb.getLong();
/* 163 */                               UrlText res = obtainResource(key, url, (InputStream) bis2);
/* 165 */                               if (res != null) {
/* 166 */                                   res.cacheTime = cacheTime;
                                        }
                                        try {
/* 172 */                                   CommonUtil.closeSilently(snapshot);
/* 173 */                                   CommonUtil.closeSilently(is);
/* 174 */                                   CommonUtil.closeSilently(bis2);
/* 178 */                                   return res;
                                        } catch (Throwable th) {
/* 177 */                                   th = th;
                                            throw new RuntimeException(th);
                                        }
                                    } catch (IOException e) {
                                        bis = bis2;
                                    } catch (Throwable th2) {
                                        bis = bis2;
                                        CommonUtil.closeSilently(snapshot);
/* 173 */                               CommonUtil.closeSilently(is);
/* 174 */                               CommonUtil.closeSilently(bis);
                                        throw th2;
                                    }
                                } catch (IOException e2) {
                                } catch (Throwable th3) {
                                    throw th3;
                                }
/* 172 */                       CommonUtil.closeSilently(snapshot);
/* 173 */                       CommonUtil.closeSilently(is);
/* 174 */                       CommonUtil.closeSilently(bis);
                            }
/* 178 */                   return null;
                        } catch (Throwable th4) {
                            throw th4;
                        }
                    }
                }

/* 181 */       public UrlText save(TextKey key, InputStream is) {
/* 182 */           return saveResource(0L, key, key.url, is, false);
                }

/* 186 */       protected UrlText saveResource(long ownerId, TextKey key, String url, InputStream is, boolean reportSpeed) {
                    UrlText result;
/* 187 */           String diskCacheKey = CommonUtil.md5Digest(url);
/* 188 */           if (diskCacheKey == null) {
/* 189 */               return obtainResource(key, url, is);
                    }
/* 192 */           DiskLruCache.Editor editor = null;
/* 193 */           BufferedOutputStream os = null;
                    synchronized (LOCK) {
                        try {
/* 195 */                   DiskLruCache diskCache = getDiskLruCache(this.mContext);
/* 196 */                   if (diskCache != null && !diskCache.isClosed()) {
/* 197 */                       boolean success = false;
                                try {
/* 199 */                           editor = diskCache.edit(diskCacheKey);
/* 200 */                           if (editor != null) {
/* 201 */                               BufferedOutputStream os2 = new BufferedOutputStream(editor.newOutputStream(0), 4096);
                                        try {
/* 203 */                                   long time = System.currentTimeMillis();
/* 204 */                                   ByteBuffer buffer = ByteBuffer.allocate(8);
/* 205 */                                   buffer.putLong(time);
/* 206 */                                   os2.write(buffer.array());
/* 207 */                                   result = obtainResource(key, url, (InputStream) new PipingInputStream(is, os2));
/* 209 */                                   result.cacheTime = time;
/* 210 */                                   success = result.isValid();
/* 211 */                                   if (success) {
/* 212 */                                       os2.flush();
/* 213 */                                       os2.close();
/* 214 */                                       os = null;
                                            } else {
                                                os = os2;
                                            }
/* 223 */                                   if (editor != null) {
                                                try {
/* 227 */                                           if (success) {
/* 228 */                                               editor.commit();
/* 229 */                                               diskCache.flush();
                                                    } else {
/* 231 */                                               editor.abort();
                                                    }
                                                } catch (IOException e) {
                                                }
                                            }
/* 236 */                                   CommonUtil.closeSilently(os);
                                        } catch (IOException e2) {
                                            os = os2;
/* 223 */                                   if (editor != null) {
                                                try {
/* 227 */                                           if (0 != 0) {
/* 228 */                                               editor.commit();
/* 229 */                                               diskCache.flush();
                                                    } else {
/* 231 */                                               editor.abort();
                                                    }
                                                } catch (IOException e3) {
                                                }
                                            }
/* 236 */                                   CommonUtil.closeSilently(os);
/* 240 */                                   return obtainResource(key, url, is);
                                        } catch (Throwable th) {
/* 223 */                                   th = th;
                                            os = os2;
                                            if (editor != null) {
                                                try {
/* 227 */                                           if (success) {
/* 228 */                                               editor.commit();
/* 229 */                                               diskCache.flush();
                                                    } else {
/* 231 */                                               editor.abort();
                                                    }
                                                } catch (IOException e4) {
                                                }
                                            }
/* 236 */                                   CommonUtil.closeSilently(os);
                                            throw new RuntimeException(th);
                                        }
                                    } else {
/* 218 */                               result = obtainResource(key, url, is);
/* 223 */                               if (editor != null) {
                                            try {
/* 227 */                                       if (0 != 0) {
/* 228 */                                           editor.commit();
/* 229 */                                           diskCache.flush();
                                                } else {
/* 231 */                                           editor.abort();
                                                }
                                            } catch (IOException e5) {
                                            }
                                        }
/* 236 */                               CommonUtil.closeSilently(null);
                                    }
/* 240 */                           return result;
                                } catch (IOException e6) {
                                } catch (RuntimeException e) {
                                    throw e;
                                } catch (Error e) {
                                    throw e;
                                }
                            }
/* 240 */                   return obtainResource(key, url, is);
                        } catch (RuntimeException e) {
                            throw e;
                        } catch (Error e) {
                            throw e;
                        }
                    }
                }

/* 245 */       protected UrlText obtainResource(TextKey key, String url, InputStream is) {
                    try {
/* 246 */               return instantiateResource(key, url, IOUtils.toByteArray(is));
                    } catch (Exception e) {
/* 248 */               SLog.m459e("Failed to obtain cache.", (Throwable) e);
/* 249 */               return null;
                    }
                }
            }
