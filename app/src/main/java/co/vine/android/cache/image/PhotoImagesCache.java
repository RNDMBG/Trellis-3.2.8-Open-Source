            package co.vine.android.cache.image;

            import android.content.Context;
            import android.graphics.Bitmap;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.cache.PipingInputStream;
            import co.vine.android.cache.UrlResource;
            import co.vine.android.cache.UrlResourceCache;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.NetworkOperationFactory;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.ImageUtils;
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.DiskLruCache;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class PhotoImagesCache<T> extends UrlResourceCache<ImageKey, UrlImage, ImageUtils.BitmapInfo, T> {
                private static final String DISK_CACHE_NAME = "photos";
                private static final int INDEX_IMAGE_BYTES = 0;
                private static final int MAX_EXTERNAL_DISK_CACHE_SIZE = 104857600;
                private static final int MAX_INTERNAL_DISK_CACHE_SIZE = 10485760;
                private static final int VALUE_COUNT = 1;
                private static final int VERSION = 2;
                private static DiskLruCache sDiskCache;
                private final BlurTool mBlurTool;
                private final ArrayList<PhotoImagesListener> mListeners;
                private final int mMaxImageSize;
                private static final int[] LOCK = new int[0];
                private static boolean sIsCacheInitialized = false;

                public interface BlurTool {
                    ImageUtils.BitmapInfo blur(Context context, Bitmap bitmap, int i, boolean z, int i2, int i3, int i4);
                }






/* 74 */        public PhotoImagesCache(Context context, int maxImageSize, int cacheSize, BlurTool blurTool, T networkContext, NetworkOperationFactory<T> factory) {
/* 75 */            super(context, cacheSize, networkContext, factory);
/* 76 */            this.mBlurTool = blurTool;
/* 77 */            this.mMaxImageSize = maxImageSize;
/* 78 */            this.mListeners = new ArrayList<>();
                }

/* 81 */        private static DiskLruCache getDiskLruCache(Context context) {
                    DiskLruCache diskLruCache;
                    synchronized (LOCK) {
                        try {
/* 83 */                    if (!sIsCacheInitialized) {
/* 84 */                        DiskLruCache diskCache = null;
                                try {
/* 86 */                            File cacheDir = CommonUtil.getExternalCacheDir(context);
/* 87 */                            if (cacheDir != null) {
/* 88 */                                diskCache = DiskLruCache.open(new File(cacheDir, DISK_CACHE_NAME), 2, 1, 104857600L);
                                    } else {
/* 92 */                                diskCache = DiskLruCache.open(new File(context.getCacheDir(), DISK_CACHE_NAME), 2, 1, 10485760L);
                                    }
                                } catch (IOException e) {
                                }
/* 98 */                        sDiskCache = diskCache;
/* 99 */                        sIsCacheInitialized = true;
                            }
/* 101 */                   diskLruCache = sDiskCache;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return diskLruCache;
                }

/* 105 */       static File getCacheDir() {
                    File directory;
                    synchronized (LOCK) {
                        try {
/* 108 */                   directory = sDiskCache != null ? sDiskCache.getDirectory() : null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 110 */           return directory;
                }

/* 120 */       public void addListener(PhotoImagesListener listener) {
/* 121 */           this.mListeners.add(listener);
                }

/* 129 */       public void removeListener(PhotoImagesListener listener) {
/* 130 */           this.mListeners.remove(listener);
                }

/* 143 */       public Bitmap getBitmap(long ownerId, ImageKey key) {
/* 144 */           UrlImage image = get(ownerId, key, key.url, true);
/* 145 */           if (image != null) {
/* 146 */               return image.bitmap;
                    }
                    return null;
                }

/* 162 */       public UrlImage get(long ownerId, ImageKey key) {
/* 163 */           return (UrlImage) super.get(ownerId, key, key.url, true);
                }

                @Override // co.vine.android.cache.UrlResourceCache
/* 168 */       protected void onResourceLoaded(HashMap<ImageKey, UrlImage> images) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
/* 170 */               this.mListeners.get(i).onPhotoImageLoaded(this, images);
                    }
                }

/* 175 */       protected void onResourceError(ImageKey key, HttpResult result) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
/* 177 */               this.mListeners.get(i).onPhotoImageError(this, key, result);
                    }
                }

/* 182 */       protected UrlImage instantiateResource(ImageKey key, String url, ImageUtils.BitmapInfo b) {
/* 183 */           return new UrlImage(url, b);
                }

/* 187 */       protected UrlImage loadResource(long ownerId, ImageKey key, String url) throws Throwable {
                    UrlImage urlImageObtainResource = null;
/* 188 */           String diskCacheKey = CommonUtil.md5Digest(url);
/* 189 */           if (diskCacheKey != null) {
/* 193 */               DiskLruCache.Snapshot snapshot = null;
/* 194 */               InputStream is = null;
/* 195 */               BufferedInputStream bis = null;
                        synchronized (LOCK) {
                            try {
/* 197 */                       DiskLruCache diskCache = getDiskLruCache(this.mContext);
/* 198 */                       if (diskCache != null && !diskCache.isClosed()) {
                                    try {
/* 200 */                               snapshot = diskCache.get(diskCacheKey);
/* 201 */                               if (snapshot == null) {
/* 212 */                                   CommonUtil.closeSilently(snapshot);
/* 213 */                                   CommonUtil.closeSilently(null);
/* 214 */                                   CommonUtil.closeSilently(null);
                                        } else {
/* 204 */                                   is = snapshot.getInputStream(0);
/* 205 */                                   if (is == null) {
/* 212 */                                       CommonUtil.closeSilently(snapshot);
/* 213 */                                       CommonUtil.closeSilently(is);
/* 214 */                                       CommonUtil.closeSilently(null);
                                            } else {
/* 208 */                                       BufferedInputStream bis2 = new BufferedInputStream(is, 4096);
                                                try {
/* 209 */                                           urlImageObtainResource = obtainResource(key, url, (InputStream) bis2);
                                                    try {
/* 212 */                                               CommonUtil.closeSilently(snapshot);
/* 213 */                                               CommonUtil.closeSilently(is);
/* 214 */                                               CommonUtil.closeSilently(bis2);
                                                    } catch (Throwable th) {
/* 217 */                                               th = th;
                                                        throw th;
                                                    }
                                                } catch (IOException e) {
                                                    bis = bis2;
/* 212 */                                           CommonUtil.closeSilently(snapshot);
/* 213 */                                           CommonUtil.closeSilently(is);
/* 214 */                                           CommonUtil.closeSilently(bis);
                                                } catch (Throwable th2) {
                                                    bis = bis2;
                                                    CommonUtil.closeSilently(snapshot);
/* 213 */                                           CommonUtil.closeSilently(is);
/* 214 */                                           CommonUtil.closeSilently(bis);
                                                    throw th2;
                                                }
                                            }
                                        }
                                    } catch (IOException e2) {
                                    } catch (Throwable th3) {
                                        throw th3;
                                    }
                                }
                            } catch (Throwable th4) {
                                throw th4;
                            }
                        }
                    }
/* 218 */           return urlImageObtainResource;
                }

/* 222 */       protected UrlImage saveResource(long ownerId, ImageKey key, String url, InputStream is, boolean reportSpeed) {
                    UrlImage result;
/* 223 */           String diskCacheKey = CommonUtil.md5Digest(url);
/* 224 */           if (diskCacheKey == null) {
/* 225 */               return obtainResource(key, url, is);
                    }
/* 228 */           DiskLruCache.Editor editor = null;
/* 229 */           BufferedOutputStream os = null;
                    synchronized (LOCK) {
                        try {
/* 231 */                   DiskLruCache diskCache = getDiskLruCache(this.mContext);
/* 232 */                   if (diskCache != null && !diskCache.isClosed()) {
/* 233 */                       boolean success = false;
                                try {
/* 235 */                           editor = diskCache.edit(diskCacheKey);
/* 236 */                           if (editor != null) {
/* 237 */                               BufferedOutputStream os2 = new BufferedOutputStream(editor.newOutputStream(0), 4096);
                                        try {
/* 239 */                                   result = obtainResource(key, url, (InputStream) new PipingInputStream(is, os2));
/* 241 */                                   success = result.isValid();
/* 242 */                                   if (success) {
/* 243 */                                       os2.flush();
/* 244 */                                       os2.close();
/* 245 */                                       os = null;
                                            } else {
                                                os = os2;
                                            }
/* 254 */                                   if (editor != null) {
                                                try {
/* 258 */                                           if (success) {
/* 259 */                                               editor.commit();
/* 260 */                                               diskCache.flush();
                                                    } else {
/* 262 */                                               editor.abort();
                                                    }
                                                } catch (IOException e) {
                                                }
                                            }
/* 267 */                                   CommonUtil.closeSilently(os);
                                        } catch (IOException e2) {
                                            os = os2;
/* 254 */                                   if (editor != null) {
                                                try {
/* 258 */                                           if (0 != 0) {
/* 259 */                                               editor.commit();
/* 260 */                                               diskCache.flush();
                                                    } else {
/* 262 */                                               editor.abort();
                                                    }
                                                } catch (IOException e3) {
                                                }
                                            }
/* 267 */                                   CommonUtil.closeSilently(os);
/* 271 */                                   return obtainResource(key, url, is);
                                        } catch (RuntimeException e) {
                                            os = os2;
                                            if (editor != null) {
                                                try {
/* 258 */                                           if (success) {
/* 259 */                                               editor.commit();
/* 260 */                                               diskCache.flush();
                                                    } else {
/* 262 */                                               editor.abort();
                                                    }
                                                } catch (IOException e4) {
                                                }
                                            }
/* 267 */                                   CommonUtil.closeSilently(os);
                                            throw e;
                                        } catch (Error e) {
                                            os = os2;
                                            if (editor != null) {
                                                try {
                                                    editor.abort();
                                                } catch (IOException ignored) {
                                                }
                                            }
                                            CommonUtil.closeSilently(os);
                                            throw e;
                                        }
                                    } else {
/* 249 */                               result = obtainResource(key, url, is);
/* 254 */                               if (editor != null) {
                                            try {
/* 258 */                                       if (0 != 0) {
/* 259 */                                           editor.commit();
/* 260 */                                           diskCache.flush();
                                                } else {
/* 262 */                                           editor.abort();
                                                }
                                            } catch (IOException e5) {
                                            }
                                        }
/* 267 */                               CommonUtil.closeSilently(null);
                                    }
/* 271 */                           return result;
                                } catch (IOException e6) {
                                } catch (RuntimeException e) {
                                    throw e;
                                } catch (Error e) {
                                    throw e;
                                }
                            }
/* 271 */                   return obtainResource(key, url, is);
                        } catch (RuntimeException e) {
                            throw e;
                        } catch (Error e) {
                            throw e;
                        }
                    }
                }

/* 277 */       protected UrlImage obtainResource(ImageKey key, String url, InputStream is) {
                    int width;
                    int height;
                    ImageUtils.BitmapInfo blurred;
/* 278 */           if (key.requestResize) {
/* 279 */               width = key.width;
/* 280 */               height = key.height;
                    } else {
/* 282 */               width = this.mMaxImageSize;
/* 283 */               height = this.mMaxImageSize;
                    }
/* 285 */           ImageUtils.BitmapInfo imageInfo = null;
/* 286 */           boolean hasMemoryException = false;
                    try {
/* 288 */               imageInfo = ImageUtils.resizeBitmap(this.mContext, is, width, height);
                    } catch (ImageUtils.ImageMemoryException e) {
/* 290 */               hasMemoryException = true;
                    }
/* 292 */           if (key.isDownloadOnly()) {
/* 293 */               return new UrlImage(url, imageInfo);
                    }
/* 295 */           if (imageInfo != null && imageInfo.bitmap != null) {
/* 296 */               int w = imageInfo.bitmap.getWidth();
/* 297 */               int h = imageInfo.bitmap.getHeight();
/* 298 */               int r = w < h ? w : h;
/* 299 */               if (key.circularCropped) {
                            try {
/* 301 */                       imageInfo = new ImageUtils.BitmapInfo(ImageUtils.getCroppedBitmap(imageInfo.bitmap, r), r, w, h);
                            } catch (OutOfMemoryError e2) {
/* 303 */                       hasMemoryException = true;
                            }
                        }
/* 306 */               if (key.blurred && (blurred = this.mBlurTool.blur(this.mContext, imageInfo.bitmap, key.blurRadius, key.desaturated, r, w, h)) != null) {
/* 309 */                   imageInfo = blurred;
                        }
                    }
/* 313 */           UrlImage urlImageInstantiateResource = instantiateResource(key, url, imageInfo);
/* 314 */           if (hasMemoryException) {
/* 315 */               SLog.m455e("Decoding error!!! Memory Low? Clear all bitmaps now!");
/* 317 */               clearMemory();
                        try {
/* 319 */                   UrlImage image = instantiateResource(key, url, ImageUtils.resizeBitmap(this.mContext, is, width, height));
/* 324 */                   return image;
                        } catch (ImageUtils.ImageMemoryException e3) {
/* 321 */                   UrlImage image2 = instantiateResource(key, url, (ImageUtils.BitmapInfo) null);
/* 324 */                   return image2;
                        }
                    }
/* 324 */           return urlImageInstantiateResource;
                }

/* 328 */       public void clearMemory() {
                    synchronized (LOCK) {
                        try {
/* 330 */                   Collection<UrlImage> snapshot = new ArrayList<>(snapShot().values());
/* 331 */                   clear();
                            for (UrlImage v : snapshot) {
/* 333 */                       if (v.bitmap != null) {
/* 334 */                           v.bitmap.recycle();
/* 335 */                           v.bitmap = null;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 347 */       public Bitmap peekBitmap(ImageKey key) {
/* 348 */           UrlImage image = (UrlImage) super.peek(key);
/* 349 */           if (image != null) {
/* 350 */               return image.bitmap;
                    }
                    return null;
                }

/* 357 */       public void prepopulateThumbnailCacheForUrl(long ownerId, ImageKey imageKey, String thumbnailUrl, FileInputStream fis) {
/* 358 */           saveResource(ownerId, imageKey, thumbnailUrl, (InputStream) fis, false);
                }

/* 361 */       public static void invalidateCache() {
                    synchronized (LOCK) {
                        try {
/* 363 */                   sDiskCache = null;
/* 364 */                   sIsCacheInitialized = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
