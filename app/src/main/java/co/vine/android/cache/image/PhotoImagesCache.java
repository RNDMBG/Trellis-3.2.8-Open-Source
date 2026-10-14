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
            
            
    public class PhotoImagesCache<T> extends UrlResourceCache<ImageKey, UrlImage, ImageUtils.BitmapInfo, T> {
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






        public PhotoImagesCache(Context context, int maxImageSize, int cacheSize, BlurTool blurTool, T networkContext, NetworkOperationFactory<T> factory) {
            super(context, cacheSize, networkContext, factory);
            this.mBlurTool = blurTool;
            this.mMaxImageSize = maxImageSize;
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
                                diskCache = DiskLruCache.open(new File(cacheDir, DISK_CACHE_NAME), 2, 1, 104857600L);
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

       public void addListener(PhotoImagesListener listener) {
           this.mListeners.add(listener);
                }

       public void removeListener(PhotoImagesListener listener) {
           this.mListeners.remove(listener);
                }

       public Bitmap getBitmap(long ownerId, ImageKey key) {
           UrlImage image = get(ownerId, key, key.url, true);
           if (image != null) {
               return image.bitmap;
                    }
                    return null;
                }

       public UrlImage get(long ownerId, ImageKey key) {
           return (UrlImage) super.get(ownerId, key, key.url, true);
                }

                @Override 
       protected void onResourceLoaded(HashMap<ImageKey, UrlImage> images) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
               this.mListeners.get(i).onPhotoImageLoaded(this, images);
                    }
                }

       protected void onResourceError(ImageKey key, HttpResult result) {
                    for (int i = this.mListeners.size() - 1; i >= 0; i--) {
               this.mListeners.get(i).onPhotoImageError(this, key, result);
                    }
                }

       protected UrlImage instantiateResource(ImageKey key, String url, ImageUtils.BitmapInfo b) {
           return new UrlImage(url, b);
                }

       protected UrlImage loadResource(long ownerId, ImageKey key, String url) throws Throwable {
                    UrlImage urlImageObtainResource = null;
           String diskCacheKey = CommonUtil.md5Digest(url);
           if (diskCacheKey != null) {
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
                                        } else {
                                   is = snapshot.getInputStream(0);
                                   if (is == null) {
                                       CommonUtil.closeSilently(snapshot);
                                       CommonUtil.closeSilently(is);
                                       CommonUtil.closeSilently(null);
                                            } else {
                                       BufferedInputStream bis2 = new BufferedInputStream(is, 4096);
                                                try {
                                           urlImageObtainResource = obtainResource(key, url, (InputStream) bis2);
                                                    try {
                                               CommonUtil.closeSilently(snapshot);
                                               CommonUtil.closeSilently(is);
                                               CommonUtil.closeSilently(bis2);
                                                    } catch (Throwable th) {
                                               th = th;
                                                        throw th;
                                                    }
                                                } catch (IOException e) {
                                                    bis = bis2;
                                           CommonUtil.closeSilently(snapshot);
                                           CommonUtil.closeSilently(is);
                                           CommonUtil.closeSilently(bis);
                                                } catch (Throwable th2) {
                                                    bis = bis2;
                                                    CommonUtil.closeSilently(snapshot);
                                           CommonUtil.closeSilently(is);
                                           CommonUtil.closeSilently(bis);
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
           return urlImageObtainResource;
                }

       protected UrlImage saveResource(long ownerId, ImageKey key, String url, InputStream is, boolean reportSpeed) {
                    UrlImage result;
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
                                   result = obtainResource(key, url, (InputStream) new PipingInputStream(is, os2));
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
                                        } catch (RuntimeException e) {
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

       protected UrlImage obtainResource(ImageKey key, String url, InputStream is) {
                    int width;
                    int height;
                    ImageUtils.BitmapInfo blurred;
           if (key.requestResize) {
               width = key.width;
               height = key.height;
                    } else {
               width = this.mMaxImageSize;
               height = this.mMaxImageSize;
                    }
           ImageUtils.BitmapInfo imageInfo = null;
           boolean hasMemoryException = false;
                    try {
               imageInfo = ImageUtils.resizeBitmap(this.mContext, is, width, height);
                    } catch (ImageUtils.ImageMemoryException e) {
               hasMemoryException = true;
                    }
           if (key.isDownloadOnly()) {
               return new UrlImage(url, imageInfo);
                    }
           if (imageInfo != null && imageInfo.bitmap != null) {
               int w = imageInfo.bitmap.getWidth();
               int h = imageInfo.bitmap.getHeight();
               int r = w < h ? w : h;
               if (key.circularCropped) {
                            try {
                       imageInfo = new ImageUtils.BitmapInfo(ImageUtils.getCroppedBitmap(imageInfo.bitmap, r), r, w, h);
                            } catch (OutOfMemoryError e2) {
                       hasMemoryException = true;
                            }
                        }
               if (key.blurred && (blurred = this.mBlurTool.blur(this.mContext, imageInfo.bitmap, key.blurRadius, key.desaturated, r, w, h)) != null) {
                   imageInfo = blurred;
                        }
                    }
           UrlImage urlImageInstantiateResource = instantiateResource(key, url, imageInfo);
           if (hasMemoryException) {
               SLog.m455e("Decoding error!!! Memory Low? Clear all bitmaps now!");
               clearMemory();
                        try {
                   UrlImage image = instantiateResource(key, url, ImageUtils.resizeBitmap(this.mContext, is, width, height));
                   return image;
                        } catch (ImageUtils.ImageMemoryException e3) {
                   UrlImage image2 = instantiateResource(key, url, (ImageUtils.BitmapInfo) null);
                   return image2;
                        }
                    }
           return urlImageInstantiateResource;
                }

       public void clearMemory() {
                    synchronized (LOCK) {
                        try {
                   Collection<UrlImage> snapshot = new ArrayList<>(snapShot().values());
                   clear();
                            for (UrlImage v : snapshot) {
                       if (v.bitmap != null) {
                           v.bitmap.recycle();
                           v.bitmap = null;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public Bitmap peekBitmap(ImageKey key) {
           UrlImage image = (UrlImage) super.peek(key);
           if (image != null) {
               return image.bitmap;
                    }
                    return null;
                }

       public void prepopulateThumbnailCacheForUrl(long ownerId, ImageKey imageKey, String thumbnailUrl, FileInputStream fis) {
           saveResource(ownerId, imageKey, thumbnailUrl, (InputStream) fis, false);
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
            }
