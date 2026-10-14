            package co.vine.android.util;

            import android.content.ContentProviderClient;
            import android.content.ContentResolver;
            import android.content.Context;
            import android.content.res.Resources;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Canvas;
            import android.graphics.ColorMatrix;
            import android.graphics.ColorMatrixColorFilter;
            import android.graphics.Matrix;
            import android.graphics.Paint;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.PorterDuffXfermode;
            import android.graphics.Rect;
            import android.graphics.RectF;
            import android.graphics.drawable.shapes.RoundRectShape;
            import android.media.ExifInterface;
            import android.net.Uri;
            import android.os.RemoteException;
            import android.os.StatFs;
            import android.util.FloatMath;
            import java.io.BufferedInputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.FilenameFilter;
            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class ImageUtils {
                public static final int MAX_AVATAR_UPLOAD_SIZE_KB = 700;
                public static final int MAX_TWEET_UPLOAD_SIZE_KB = 3072;
                public static final long MIN_REQUIRED_SD_SPACE = 1048576;
                public static final boolean SUPPORTS_FILTERS = false;
                public static final String TEMP_FILENAME_PREFIX = "pic-";
                public static final String TEMP_RESIZED_PREFIX = "pic-r-";
                private static final Paint sInStrokePaint;
                private static final Paint sSolidStrokePaint;
                public static final PorterDuffXfermode sSrcXferMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
                private static final Paint sResizePaint = new Paint(2);
                private static final Paint sOutStrokePaint = new Paint();

                static {
            sOutStrokePaint.setStrokeWidth(1.0f);
            sOutStrokePaint.setStyle(Paint.Style.STROKE);
            sOutStrokePaint.setColor(-2039584);
            sInStrokePaint = new Paint();
            sInStrokePaint.setStrokeWidth(1.0f);
            sInStrokePaint.setStyle(Paint.Style.STROKE);
            sInStrokePaint.setColor(-986896);
            sSolidStrokePaint = new Paint();
            sSolidStrokePaint.setStrokeWidth(3.0f);
            sSolidStrokePaint.setStyle(Paint.Style.STROKE);
            sSolidStrokePaint.setColor(-986896);
                }

       private ImageUtils() {
                }

       public static BitmapInfo safeDecode(Context context, byte[] imageBytes, int viewWidth, int viewHeight) {
           BitmapFactory.Options dbo = new BitmapFactory.Options();
           dbo.inJustDecodeBounds = true;
           decodeByteArray(context, imageBytes, 0, imageBytes.length, dbo);
           int origWidth = dbo.outWidth;
           int origHeight = dbo.outHeight;
           setBitmapOptionsScale(dbo, viewWidth, viewHeight, false);
           BitmapInfo bitmapInfo = decodeByteArray(context, imageBytes, 0, imageBytes.length, dbo);
           if (bitmapInfo != null) {
               return new BitmapInfo(bitmapInfo.bitmap, dbo.inSampleSize, origWidth, origHeight);
                    }
                    return null;
                }

       public static BitmapInfo safeDecode(Context context, InputStream is, int viewWidth, int viewHeight) throws ImageMemoryException {
                    File f = CommonUtil.getTempFile(context);
                    int bytesLen;
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(f);
                        bytesLen = CommonUtil.readFullyWriteTo(is, out, 4096);
                        out.flush();
                    } catch (IOException e) {
                        f.delete();
                        return null;
                    } finally {
                        CommonUtil.closeSilently(out);
                    }
                    try {
                        if (bytesLen == 0) {
                            return null;
                        }
                        BufferedInputStream optionsInput = new BufferedInputStream(new FileInputStream(f));
                        try {
                            BitmapFactory.Options dbo = new BitmapFactory.Options();
                            dbo.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(optionsInput, null, dbo);
                            int origWidth = dbo.outWidth;
                            int origHeight = dbo.outHeight;
                            setBitmapOptionsScale(dbo, viewWidth, viewHeight, false);
                            BufferedInputStream imageInput = new BufferedInputStream(new FileInputStream(f));
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(imageInput, null, dbo);
                                return bitmap == null ? null : new BitmapInfo(bitmap, dbo.inSampleSize, origWidth, origHeight);
                            } finally {
                                CommonUtil.closeSilently(imageInput);
                            }
                        } finally {
                            CommonUtil.closeSilently(optionsInput);
                        }
                    } catch (IOException e) {
                        return null;
                    } catch (OutOfMemoryError e) {
                        throw new ImageMemoryException(e);
                    } finally {
                        f.delete();
                    }
                }

       private static boolean isPortrait(int width, int height) {
                    return height > width;
                }

       public static class ImageMemoryException extends Exception {
           public ImageMemoryException(OutOfMemoryError e) {
               super(e);
                    }
                }

       public static BitmapInfo safeDecode(Context context, Uri uri, int width, int height) {
           return safeDecode(context, uri, width, height, false);
                }

       public static BitmapInfo safeDecode(Context context, Uri uri, int width, int height, boolean conservative) {
           InputStream optionsStream = null;
           InputStream inputStream = null;
                    try {
               ContentResolver resolver = context.getContentResolver();
               if (resolver == null || uri == null) {
                   CommonUtil.closeSilently(null);
                   CommonUtil.closeSilently(null);
                   return null;
                        }
               optionsStream = resolver.openInputStream(uri);
               BitmapFactory.Options dbo = new BitmapFactory.Options();
               dbo.inJustDecodeBounds = true;
               decodeStream(context, optionsStream, null, dbo);
               int bitmapWidth = dbo.outWidth;
               int bitmapHeight = dbo.outHeight;
               setBitmapOptionsScale(dbo, width, height, conservative);
               inputStream = resolver.openInputStream(uri);
               Bitmap bmp = decodeStream(context, inputStream, null, dbo);
               if (bmp == null) {
                   CommonUtil.closeSilently(optionsStream);
                   CommonUtil.closeSilently(inputStream);
                   return null;
                        }
               Bitmap result = adjustRotation(context, uri, bmp);
               if (result == null) {
                   result = bmp;
                        } else if (isPortrait(bitmapWidth, bitmapHeight) != isPortrait(result.getWidth(), result.getHeight())) {
                   bitmapHeight = bitmapWidth;
                   bitmapWidth = bitmapHeight;
                        }
               BitmapInfo bitmapInfo = new BitmapInfo(result, dbo.inSampleSize, bitmapWidth, bitmapHeight);
               CommonUtil.closeSilently(optionsStream);
               CommonUtil.closeSilently(inputStream);
                        return bitmapInfo;
                    } catch (FileNotFoundException e) {
               CommonUtil.closeSilently(optionsStream);
               CommonUtil.closeSilently(inputStream);
               return null;
                    } catch (SecurityException e2) {
               CommonUtil.closeSilently(optionsStream);
               CommonUtil.closeSilently(inputStream);
               return null;
                    } catch (Throwable th) {
                        CommonUtil.closeSilently(optionsStream);
               CommonUtil.closeSilently(inputStream);
                        throw th;
                    }
                }

       public static void setBitmapOptionsScale(BitmapFactory.Options dbo, int desiredWidth, int desiredHeight, boolean conservative) {
                    int initialScale;
           int width = dbo.outWidth;
           int height = dbo.outHeight;
           int scale = 1;
           int size = Math.max(width, height);
           int maxSize = Math.max(desiredWidth, desiredHeight);
           if (conservative) {
               initialScale = 1;
                    } else {
               initialScale = 2;
                    }
           while (size / initialScale >= maxSize) {
               size /= 2;
               scale *= 2;
                    }
           dbo.inJustDecodeBounds = false;
           dbo.inDither = true;
           dbo.inPreferQualityOverSpeed = true;
           dbo.inSampleSize = scale;
                }

       public static Bitmap roundBitmapCorners(Context context, Bitmap bm, int width, int height, float cornerRadius) {
           Bitmap bitmap = createBitmap(context, width, height, Bitmap.Config.ARGB_8888);
           if (bitmap == null) {
               return null;
                    }
           Canvas canvas = new Canvas(bitmap);
           Paint p = new Paint(3);
           RoundRectShape rrs = new RoundRectShape(new float[]{cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius}, null, null);
           rrs.resize(width, height);
           rrs.draw(canvas, p);
           p.setXfermode(sSrcXferMode);
           if (bm.getWidth() != width || bm.getHeight() != height) {
               Rect src = new Rect(0, 0, bm.getWidth(), bm.getHeight());
               Rect dest = new Rect(0, 0, width, height);
               canvas.drawBitmap(bm, src, dest, p);
               return bitmap;
                    }
           canvas.drawBitmap(bm, 0.0f, 0.0f, p);
           return bitmap;
                }

       public static BitmapInfo resizeBitmap(Context context, byte[] imageBytes, int desiredWidth, int desiredHeight) {
           return resizeBitmap(context, safeDecode(context, imageBytes, desiredWidth, desiredHeight), desiredWidth, desiredHeight);
                }

       public static BitmapInfo resizeBitmap(Context context, InputStream is, int desiredWidth, int desiredHeight) throws ImageMemoryException {
           return resizeBitmap(context, safeDecode(context, is, desiredWidth, desiredHeight), desiredWidth, desiredHeight);
                }

       private static BitmapInfo resizeBitmap(Context context, BitmapInfo src, int desiredWidth, int desiredHeight) {
           if (src == null) {
               return null;
                    }
           Bitmap bm = src.bitmap;
           int width = bm.getWidth();
           int height = bm.getHeight();
           int desiredSize = Math.max(desiredWidth, desiredHeight);
           int size = Math.max(width, height);
           if (size > desiredSize) {
               Matrix matrix = new Matrix();
               float scale = (float) desiredSize / (float) size;
               matrix.postScale(scale, scale);
               Bitmap bitmap = createBitmap(context, bm, 0, 0, width, height, matrix, true);
               if (bitmap != null) {
                   bm.recycle();
                   return new BitmapInfo(bitmap, (int) scale, width, height);
                        }
               return null;
                    }
           return src;
                }

       public static Bitmap resizeBitmap(Context context, Uri uri, long size, Rect rect) {
           ContentResolver cr = context.getContentResolver();
           InputStream inputStream = null;
           InputStream optionsStream = null;
                    try {
               optionsStream = cr.openInputStream(uri);
               BitmapFactory.Options dbo = new BitmapFactory.Options();
               dbo.inJustDecodeBounds = true;
               decodeStream(context, optionsStream, null, dbo);
               float aspectRatio = dbo.outWidth / dbo.outHeight;
               float newWidth = (float) Math.sqrt(size * aspectRatio);
               float newHeight = (float) Math.sqrt(size * (1.0f / aspectRatio));
               int inSampleSize = 1;
                        if (dbo.outWidth > newWidth || dbo.outHeight > newHeight) {
                   if (dbo.outWidth > dbo.outHeight) {
                       inSampleSize = (int) Math.ceil(dbo.outHeight / newHeight);
                            } else {
                       inSampleSize = (int) Math.ceil(dbo.outWidth / newWidth);
                            }
                   if (rect != null) {
                       rect.left /= inSampleSize;
                       rect.top /= inSampleSize;
                       rect.right /= inSampleSize;
                       rect.bottom /= inSampleSize;
                            }
                        }
               inputStream = cr.openInputStream(uri);
               dbo.inSampleSize = inSampleSize;
               dbo.inJustDecodeBounds = false;
               Bitmap bitmapDecodeStream = decodeStream(context, inputStream, null, dbo);
               CommonUtil.closeSilently(inputStream);
               CommonUtil.closeSilently(optionsStream);
                        return bitmapDecodeStream;
                    } catch (IOException e) {
               CommonUtil.closeSilently(inputStream);
               CommonUtil.closeSilently(optionsStream);
               return null;
                    } catch (Throwable th) {
                        CommonUtil.closeSilently(inputStream);
               CommonUtil.closeSilently(optionsStream);
                        throw th;
                    }
                }

       public static Bitmap resizeBitmap(Context context, Uri uri, float viewWidth, float viewHeight, int border) {
                    float bitmapWidth;
                    float bitmapHeight;
                    Bitmap srcBitmap;
           ContentResolver cr = context.getContentResolver();
           InputStream inputStream = null;
           InputStream optionStream = null;
                    try {
               optionStream = cr.openInputStream(uri);
               BitmapFactory.Options dbo = new BitmapFactory.Options();
               dbo.inJustDecodeBounds = true;
               decodeStream(context, optionStream, null, dbo);
               float nativeWidth = dbo.outWidth;
               float nativeHeight = dbo.outHeight;
               float viewWidth2 = viewWidth - border;
               float viewHeight2 = viewHeight - border;
               inputStream = cr.openInputStream(uri);
                        if (nativeWidth > viewWidth2 || nativeHeight > viewHeight2) {
                   float dx = nativeWidth / viewWidth2;
                   float dy = nativeHeight / viewHeight2;
                            if (dx > dy) {
                       bitmapWidth = viewWidth2;
                       bitmapHeight = nativeHeight / dx;
                            } else {
                       bitmapWidth = nativeWidth / dy;
                       bitmapHeight = viewHeight2;
                            }
                            if (nativeWidth / bitmapWidth > 1.0f) {
                       BitmapFactory.Options options = new BitmapFactory.Options();
                       options.inSampleSize = (int) (nativeWidth / bitmapWidth);
                       srcBitmap = decodeStream(context, inputStream, null, options);
                            } else {
                       srcBitmap = decodeStream(context, inputStream);
                            }
                        } else {
                   bitmapWidth = viewWidth2;
                   bitmapHeight = viewHeight2;
                   srcBitmap = decodeStream(context, inputStream);
                        }
               if (srcBitmap == null) {
                   CommonUtil.closeSilently(optionStream);
                   CommonUtil.closeSilently(inputStream);
                   return null;
                        }
               Bitmap bitmap = scaleBitmap(context, srcBitmap, bitmapWidth, bitmapHeight, border);
               srcBitmap.recycle();
               CommonUtil.closeSilently(optionStream);
               CommonUtil.closeSilently(inputStream);
                        return bitmap;
                    } catch (IOException e) {
               CommonUtil.closeSilently(optionStream);
               CommonUtil.closeSilently(inputStream);
               return null;
                    } catch (Throwable th) {
                        CommonUtil.closeSilently(optionStream);
               CommonUtil.closeSilently(inputStream);
                        throw th;
                    }
                }

       public static Bitmap scaleBitmap(Context context, Bitmap src, float width, float height, int border) {
           Bitmap bitmap = createBitmap(context, ((int) width) + border, ((int) height) + border, Bitmap.Config.ARGB_8888);
           if (bitmap == null) {
               return null;
                    }
           Canvas canvas = new Canvas(bitmap);
           if (border > 0) {
               canvas.drawColor(-1);
               canvas.drawBitmap(src, new Rect(0, 0, src.getWidth(), src.getHeight()), new Rect(border, border, ((int) width) + 1, (int) height), sResizePaint);
               canvas.drawRect(0.0f, 0.0f, (border + width) - 1.0f, (border + height) - 1.0f, sOutStrokePaint);
               canvas.drawRect(border - 1, border - 1, width, height, sInStrokePaint);
               return bitmap;
                    }
           canvas.drawBitmap(src, new Rect(0, 0, src.getWidth(), src.getHeight()), new Rect(border, border, ((int) width) + 1, (int) height), sResizePaint);
           return bitmap;
                }

       public static File getTempPic(Context context, boolean resized, long ownerId) {
           File dir = CommonUtil.getFilesDir(context);
           if (dir == null) {
               return null;
                    }
           String prefix = resized ? TEMP_RESIZED_PREFIX : TEMP_FILENAME_PREFIX + ownerId + "-";
           return new File(dir, prefix + System.currentTimeMillis() + ".jpg");
                }

       public static boolean isTempPic(Uri uri) {
                    return (uri == null || uri.getLastPathSegment() == null || !uri.getLastPathSegment().startsWith(TEMP_FILENAME_PREFIX)) ? false : true;
                }

       public static boolean deleteTempPic(Uri uri) {
           if (!isTempPic(uri)) {
               return false;
                    }
           File f = new File(uri.getPath());
           return f.delete();
                }

       public static Uri processPicture(Context context, Uri uri, long ownerId) {
           return resizeIfNeeded(context, changeFormatIfNeeded(context, uri, ownerId), MAX_TWEET_UPLOAD_SIZE_KB, ownerId, null);
                }

       public static Uri resizeIfNeeded(Context context, Uri uri, int maxSizeKB, long ownerId, Rect rect) {
           if (uri == null) {
                        return null;
                    }
           long maxSizePixels = maxSizeKB * 250;
                    if (CommonUtil.getFileSize(context, uri) <= maxSizeKB) {
                        return uri;
                    }
           Bitmap smaller = resizeBitmap(context, uri, maxSizePixels, rect);
           if (smaller == null) {
                        return null;
                    }
           Uri newUri = writePicToFile(context, smaller, ownerId);
           if (newUri != null) {
               deleteTempPic(uri);
               return newUri;
                    }
           return newUri;
                }

       public static Uri changeFormatIfNeeded(Context context, Uri uri, long ownerId) {
           if (uri == null) {
                        return null;
                    }
           InputStream inputStream = null;
           InputStream optionsStream = null;
                    try {
               ContentResolver cr = context.getContentResolver();
               optionsStream = cr.openInputStream(uri);
               BitmapFactory.Options dbo = new BitmapFactory.Options();
               dbo.inJustDecodeBounds = true;
               decodeStream(context, optionsStream, null, dbo);
               if (dbo.outMimeType.equals("image/jpeg") || dbo.outMimeType.equals("image/gif") || dbo.outMimeType.equals("image/png")) {
                   CommonUtil.closeSilently(null);
                   CommonUtil.closeSilently(optionsStream);
                            return uri;
                        }
               inputStream = cr.openInputStream(uri);
               Bitmap srcBitmap = decodeStream(context, inputStream);
               if (srcBitmap == null) {
                   CommonUtil.closeSilently(inputStream);
                   CommonUtil.closeSilently(optionsStream);
                            return null;
                        }
               Uri newUri = writePicToFile(context, srcBitmap, ownerId);
               if (newUri != null) {
                   deleteTempPic(uri);
                        }
               CommonUtil.closeSilently(inputStream);
               CommonUtil.closeSilently(optionsStream);
               return newUri;
                    } catch (IOException e) {
               CommonUtil.closeSilently(inputStream);
               CommonUtil.closeSilently(optionsStream);
                        return null;
                    } catch (RuntimeException e) {
                        CommonUtil.closeSilently(inputStream);
               CommonUtil.closeSilently(optionsStream);
                        throw e;
                    } catch (Error e) {
                        CommonUtil.closeSilently(inputStream);
               CommonUtil.closeSilently(optionsStream);
                        throw e;
                    }
                }

       public static Bitmap decodeStream(Context context, InputStream is) {
                    try {
               return BitmapFactory.decodeStream(is);
                    } catch (OutOfMemoryError e) {
               return null;
                    }
                }

       public static Bitmap decodeStream(Context context, InputStream is, Rect outPadding, BitmapFactory.Options opts) {
                    try {
               return BitmapFactory.decodeStream(is, outPadding, opts);
                    } catch (OutOfMemoryError e) {
               return null;
                    }
                }

       public static BitmapInfo decodeByteArray(Context context, byte[] data, int offset, int length) {
                    try {
               BitmapFactory.Options dbo = new BitmapFactory.Options();
               Bitmap bitmap = BitmapFactory.decodeByteArray(data, offset, length, dbo);
               if (bitmap != null) {
                   return new BitmapInfo(bitmap, dbo.inSampleSize, dbo.outWidth, dbo.outHeight);
                        }
                        return null;
                    } catch (OutOfMemoryError e) {
                        return null;
                    }
                }

       public static BitmapInfo decodeByteArray(Context context, byte[] data, int offset, int length, BitmapFactory.Options opts) {
                    try {
               BitmapFactory.Options dbo = new BitmapFactory.Options();
               dbo.inJustDecodeBounds = true;
               BitmapFactory.decodeByteArray(data, 0, data.length, dbo);
               int origWidth = dbo.outWidth;
               int origHeight = dbo.outHeight;
               int scale = opts.inSampleSize;
               Bitmap bitmap = BitmapFactory.decodeByteArray(data, offset, length, opts);
               if (bitmap != null) {
                   return new BitmapInfo(bitmap, scale, origWidth, origHeight);
                        }
                        return null;
                    } catch (OutOfMemoryError e) {
                        return null;
                    }
                }

       public static Bitmap createBitmap(Context context, Bitmap bmp, int left, int top, int width, int height) {
                    try {
               return Bitmap.createBitmap(bmp, left, top, width, height);
                    } catch (OutOfMemoryError e) {
               return null;
                    }
                }

       public static Bitmap createBitmap(Context context, Bitmap bmp, int left, int top, int width, int height, Matrix matrix, boolean filter) {
                    try {
               return Bitmap.createBitmap(bmp, left, top, width, height, matrix, filter);
                    } catch (OutOfMemoryError e) {
               return null;
                    }
                }

       public static Bitmap createBitmap(Context context, int width, int height, Bitmap.Config config) {
                    try {
               return Bitmap.createBitmap(width, height, config);
                    } catch (OutOfMemoryError e) {
               return null;
                    }
                }

       public static Bitmap decodeResource(Context context, Resources resources, int id, BitmapFactory.Options opts) {
                    try {
               return BitmapFactory.decodeResource(resources, id, opts);
                    } catch (OutOfMemoryError e) {
               return null;
                    }
                }

       public static Bitmap decodeResource(Context context, Resources resources, int id) {
                    try {
               return BitmapFactory.decodeResource(resources, id);
                    } catch (OutOfMemoryError e) {
               return null;
                    }
                }

       private static class ImageFilenameFilter implements FilenameFilter {
                    private final long mOwnerId;

           ImageFilenameFilter(long ownerId) {
               this.mOwnerId = ownerId;
                    }

                    @Override 
           public boolean accept(File dir, String filename) {
                        return filename.startsWith(new StringBuilder().append(ImageUtils.TEMP_RESIZED_PREFIX).append(this.mOwnerId).toString()) || filename.startsWith(new StringBuilder().append(ImageUtils.TEMP_FILENAME_PREFIX).append(this.mOwnerId).toString());
                    }
                }

       public static void removeFiles(Context context) {
                    File[] files;
           File dir = CommonUtil.getFilesDir(context);
           if (dir != null && (files = dir.listFiles()) != null) {
                        for (File f : files) {
                   if (!f.isDirectory()) {
                       f.delete();
                            }
                        }
                    }
                }

       public static Uri writePicToFile(Context context, Bitmap pic, long ownerId) {
                    Uri uriFromFile = null;
                    File f = getTempPic(context, true, ownerId);
                    if (f != null) {
                        FileOutputStream fs = null;
                        try {
                            fs = new FileOutputStream(f);
                            pic.compress(Bitmap.CompressFormat.JPEG, 85, fs);
                            pic.recycle();
                            uriFromFile = Uri.fromFile(f);
                        } catch (IOException e) {
                            
                        } catch (OutOfMemoryError e) {
                            
                        } finally {
                            CommonUtil.closeSilently(fs);
                        }
                    }
                    return uriFromFile;
                }

       public static boolean hasEnoughExternalStorageForNewPhoto(Context context) {
           File dir = CommonUtil.getFilesDir(context);
           if (dir == null) {
                        return false;
                    }
           StatFs stat = new StatFs(dir.getAbsolutePath());
           long bytesAvailable = ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks());
                    return bytesAvailable > 1048576;
                }

       public static class BitmapInfo {
                    public final Bitmap bitmap;
                    public final int origHeight;
                    public final int origWidth;
                    public final int scale;

           public BitmapInfo(Bitmap bitmap, int scale, int origWidth, int origHeight) {
               this.bitmap = bitmap;
               this.scale = scale;
               this.origWidth = origWidth;
               this.origHeight = origHeight;
                    }
                }

       public static int getExifOrientation(String filepath) {
           if (filepath == null) {
                        return 0;
                    }
                    try {
               ExifInterface exif = new ExifInterface(filepath);
               return exif.getAttributeInt("Orientation", 0);
                    } catch (IOException e) {
                        return 0;
                    }
                }

       public static String getRealPathFromImageUri(Context context, Uri uri) {
                    String result;
           String scheme = uri.getScheme();
           if (scheme == null || CommonUtil.FILE.equals(scheme)) {
               return uri.getPath();
                    }
           if ("content".equals(scheme)) {
               String[] proj = {"_data"};
               Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
               if (cursor != null) {
                            try {
                       if (cursor.moveToFirst()) {
                           result = cursor.getString(0);
                           if (result == null) {
                               result = uri.getPath();
                               cursor.close();
                                    } else {
                               cursor.close();
                                    }
                                } else {
                           result = uri.getPath();
                           cursor.close();
                                }
                       return result;
                            } catch (Throwable th) {
                                cursor.close();
                                throw th;
                            }
                        }
                    }
           return uri.getPath();
                }

       public static int getExifOrientation(Context context, Uri uri) {
                    ContentProviderClient provider;
                    int rotationFromFile;
           String scheme = uri.getScheme();
           if (scheme == null || CommonUtil.FILE.equals(scheme)) {
               return getExifOrientation(uri.getPath());
                    }
           if ("content".equals(scheme) && (provider = context.getContentResolver().acquireContentProviderClient(uri)) != null) {
                        try {
                   Cursor result = provider.query(uri, new String[]{"orientation", "_data"}, null, null, null);
                  if (result == null) {
                                return 0;
                            }
                            try {
                      if (result.moveToFirst()) {
                          String path = result.getString(1);
                          if (path == null || (rotationFromFile = getExifOrientation(path)) == 0) {
                              int rotationFromCursor = result.getInt(0);
                              if (rotationFromCursor != 0) {
                                  result.close();
                                            return rotationFromCursor;
                                        }
                                        result.close();
                                        return 0;
                                    }
                          result.close();
                          return rotationFromFile;
                                }
                                result.close();
                            } catch (Throwable th) {
                                result.close();
                                throw th;
                            }
                        } catch (RemoteException | RuntimeException e) {
                            return 0;
                        }
                    }
                    return 0;
                }

      public static Bitmap adjustRotation(Context context, Uri uri, Bitmap bitmap) {
                    float rotation;
          int degrees = getExifOrientation(context, uri);
                    switch (degrees) {
                        case 3:
                  rotation = 180.0f;
                            break;
                        case 4:
                        case 5:
                        case 7:
                        default:
                  return bitmap;
                        case 6:
                  rotation = 90.0f;
                            break;
                        case 8:
                  rotation = 270.0f;
                            break;
                    }
          Matrix matrix = new Matrix();
          matrix.postRotate(rotation);
          Bitmap result = createBitmap(context, bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
          if (result != null && result != bitmap) {
              bitmap.recycle();
                    }
                    return result;
                }

      public static boolean isResized(Uri uri) {
                    return isTempPic(uri) && uri.toString().contains(TEMP_RESIZED_PREFIX);
                }

      public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
          Bitmap sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
          Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
          Canvas canvas = new Canvas(output);
          Paint paint = new Paint();
          Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
          paint.setAntiAlias(true);
          paint.setFilterBitmap(true);
          paint.setDither(true);
          canvas.drawARGB(0, 0, 0, 0);
          canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2, sbmp.getWidth() / 2, paint);
          paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
          canvas.drawBitmap(sbmp, rect, rect, paint);
          return output;
                }

      public static Bitmap getMutableBitmap(Bitmap bm) {
          if (!bm.isMutable()) {
              return bm.copy(bm.getConfig(), true);
                    }
          return bm;
                }

      public static Bitmap addStroke(int color, float width, Bitmap source) {
          Bitmap bm = getMutableBitmap(source);
          Canvas canvas = new Canvas(bm);
          Paint paint = new Paint();
          paint.setStyle(Paint.Style.STROKE);
          paint.setColor(color);
          paint.setStrokeWidth(width);
          paint.setAntiAlias(true);
          float halfStroke = width / 2.0f;
          canvas.drawOval(new RectF(halfStroke, halfStroke, bm.getWidth() - halfStroke, bm.getHeight() - halfStroke), paint);
          return bm;
                }

      public static Bitmap desaturateWithColorFilter(Bitmap source, float sat, int color) {
          Canvas canvas = new Canvas(source);
          Paint paint = new Paint();
          paint.setAntiAlias(true);
          ColorMatrix matrix = new ColorMatrix();
          matrix.setSaturation(sat);
          ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
          paint.setColorFilter(filter);
          canvas.drawBitmap(source, 0.0f, 0.0f, paint);
          if (color != 0) {
              paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
              canvas.drawOval(new RectF(0.0f, 0.0f, source.getWidth(), source.getHeight()), paint);
                    }
          return source;
                }
            }
