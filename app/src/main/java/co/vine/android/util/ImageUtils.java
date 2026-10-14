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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 60 */    public class ImageUtils {
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
/* 75 */            sOutStrokePaint.setStrokeWidth(1.0f);
/* 76 */            sOutStrokePaint.setStyle(Paint.Style.STROKE);
/* 77 */            sOutStrokePaint.setColor(-2039584);
/* 80 */            sInStrokePaint = new Paint();
/* 83 */            sInStrokePaint.setStrokeWidth(1.0f);
/* 84 */            sInStrokePaint.setStyle(Paint.Style.STROKE);
/* 85 */            sInStrokePaint.setColor(-986896);
/* 88 */            sSolidStrokePaint = new Paint();
/* 91 */            sSolidStrokePaint.setStrokeWidth(3.0f);
/* 92 */            sSolidStrokePaint.setStyle(Paint.Style.STROKE);
/* 93 */            sSolidStrokePaint.setColor(-986896);
                }

/* 103 */       private ImageUtils() {
                }

/* 118 */       public static BitmapInfo safeDecode(Context context, byte[] imageBytes, int viewWidth, int viewHeight) {
/* 119 */           BitmapFactory.Options dbo = new BitmapFactory.Options();
/* 120 */           dbo.inJustDecodeBounds = true;
/* 121 */           decodeByteArray(context, imageBytes, 0, imageBytes.length, dbo);
/* 122 */           int origWidth = dbo.outWidth;
/* 123 */           int origHeight = dbo.outHeight;
/* 125 */           setBitmapOptionsScale(dbo, viewWidth, viewHeight, false);
/* 126 */           BitmapInfo bitmapInfo = decodeByteArray(context, imageBytes, 0, imageBytes.length, dbo);
/* 128 */           if (bitmapInfo != null) {
/* 129 */               return new BitmapInfo(bitmapInfo.bitmap, dbo.inSampleSize, origWidth, origHeight);
                    }
                    return null;
                }

/* 148 */       public static BitmapInfo safeDecode(Context context, InputStream is, int viewWidth, int viewHeight) throws ImageMemoryException {
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

/* 197 */       private static boolean isPortrait(int width, int height) {
                    return height > width;
                }

/* 202 */       public static class ImageMemoryException extends Exception {
/* 203 */           public ImageMemoryException(OutOfMemoryError e) {
/* 204 */               super(e);
                    }
                }

/* 218 */       public static BitmapInfo safeDecode(Context context, Uri uri, int width, int height) {
/* 219 */           return safeDecode(context, uri, width, height, false);
                }

/* 236 */       public static BitmapInfo safeDecode(Context context, Uri uri, int width, int height, boolean conservative) {
/* 237 */           InputStream optionsStream = null;
/* 238 */           InputStream inputStream = null;
                    try {
/* 240 */               ContentResolver resolver = context.getContentResolver();
/* 241 */               if (resolver == null || uri == null) {
/* 274 */                   CommonUtil.closeSilently(null);
/* 275 */                   CommonUtil.closeSilently(null);
/* 242 */                   return null;
                        }
/* 244 */               optionsStream = resolver.openInputStream(uri);
/* 245 */               BitmapFactory.Options dbo = new BitmapFactory.Options();
/* 246 */               dbo.inJustDecodeBounds = true;
/* 247 */               decodeStream(context, optionsStream, null, dbo);
/* 248 */               int bitmapWidth = dbo.outWidth;
/* 249 */               int bitmapHeight = dbo.outHeight;
/* 250 */               setBitmapOptionsScale(dbo, width, height, conservative);
/* 253 */               inputStream = resolver.openInputStream(uri);
/* 254 */               Bitmap bmp = decodeStream(context, inputStream, null, dbo);
/* 255 */               if (bmp == null) {
/* 274 */                   CommonUtil.closeSilently(optionsStream);
/* 275 */                   CommonUtil.closeSilently(inputStream);
/* 256 */                   return null;
                        }
/* 258 */               Bitmap result = adjustRotation(context, uri, bmp);
/* 259 */               if (result == null) {
/* 261 */                   result = bmp;
                        } else if (isPortrait(bitmapWidth, bitmapHeight) != isPortrait(result.getWidth(), result.getHeight())) {
/* 267 */                   bitmapHeight = bitmapWidth;
/* 268 */                   bitmapWidth = bitmapHeight;
                        }
/* 270 */               BitmapInfo bitmapInfo = new BitmapInfo(result, dbo.inSampleSize, bitmapWidth, bitmapHeight);
/* 274 */               CommonUtil.closeSilently(optionsStream);
/* 275 */               CommonUtil.closeSilently(inputStream);
                        return bitmapInfo;
                    } catch (FileNotFoundException e) {
/* 274 */               CommonUtil.closeSilently(optionsStream);
/* 275 */               CommonUtil.closeSilently(inputStream);
/* 272 */               return null;
                    } catch (SecurityException e2) {
/* 274 */               CommonUtil.closeSilently(optionsStream);
/* 275 */               CommonUtil.closeSilently(inputStream);
/* 272 */               return null;
                    } catch (Throwable th) {
                        CommonUtil.closeSilently(optionsStream);
/* 275 */               CommonUtil.closeSilently(inputStream);
                        throw th;
                    }
                }

/* 290 */       public static void setBitmapOptionsScale(BitmapFactory.Options dbo, int desiredWidth, int desiredHeight, boolean conservative) {
                    int initialScale;
/* 291 */           int width = dbo.outWidth;
/* 292 */           int height = dbo.outHeight;
/* 294 */           int scale = 1;
/* 295 */           int size = Math.max(width, height);
/* 296 */           int maxSize = Math.max(desiredWidth, desiredHeight);
/* 298 */           if (conservative) {
/* 299 */               initialScale = 1;
                    } else {
/* 301 */               initialScale = 2;
                    }
/* 303 */           while (size / initialScale >= maxSize) {
/* 304 */               size /= 2;
/* 305 */               scale *= 2;
                    }
/* 307 */           dbo.inJustDecodeBounds = false;
/* 308 */           dbo.inDither = true;
/* 309 */           dbo.inPreferQualityOverSpeed = true;
/* 310 */           dbo.inSampleSize = scale;
                }

/* 320 */       public static Bitmap roundBitmapCorners(Context context, Bitmap bm, int width, int height, float cornerRadius) {
/* 321 */           Bitmap bitmap = createBitmap(context, width, height, Bitmap.Config.ARGB_8888);
/* 322 */           if (bitmap == null) {
/* 323 */               return null;
                    }
/* 325 */           Canvas canvas = new Canvas(bitmap);
/* 326 */           Paint p = new Paint(3);
/* 327 */           RoundRectShape rrs = new RoundRectShape(new float[]{cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius}, null, null);
/* 331 */           rrs.resize(width, height);
/* 332 */           rrs.draw(canvas, p);
/* 333 */           p.setXfermode(sSrcXferMode);
/* 334 */           if (bm.getWidth() != width || bm.getHeight() != height) {
/* 335 */               Rect src = new Rect(0, 0, bm.getWidth(), bm.getHeight());
/* 336 */               Rect dest = new Rect(0, 0, width, height);
/* 337 */               canvas.drawBitmap(bm, src, dest, p);
/* 341 */               return bitmap;
                    }
/* 339 */           canvas.drawBitmap(bm, 0.0f, 0.0f, p);
/* 341 */           return bitmap;
                }

/* 352 */       public static BitmapInfo resizeBitmap(Context context, byte[] imageBytes, int desiredWidth, int desiredHeight) {
/* 353 */           return resizeBitmap(context, safeDecode(context, imageBytes, desiredWidth, desiredHeight), desiredWidth, desiredHeight);
                }

/* 365 */       public static BitmapInfo resizeBitmap(Context context, InputStream is, int desiredWidth, int desiredHeight) throws ImageMemoryException {
/* 366 */           return resizeBitmap(context, safeDecode(context, is, desiredWidth, desiredHeight), desiredWidth, desiredHeight);
                }

/* 371 */       private static BitmapInfo resizeBitmap(Context context, BitmapInfo src, int desiredWidth, int desiredHeight) {
/* 372 */           if (src == null) {
/* 373 */               return null;
                    }
/* 376 */           Bitmap bm = src.bitmap;
/* 377 */           int width = bm.getWidth();
/* 378 */           int height = bm.getHeight();
/* 379 */           int desiredSize = Math.max(desiredWidth, desiredHeight);
/* 380 */           int size = Math.max(width, height);
/* 381 */           if (size > desiredSize) {
/* 382 */               Matrix matrix = new Matrix();
/* 383 */               float scale = (float) desiredSize / (float) size;
/* 384 */               matrix.postScale(scale, scale);
/* 386 */               Bitmap bitmap = createBitmap(context, bm, 0, 0, width, height, matrix, true);
/* 387 */               if (bitmap != null) {
/* 388 */                   bm.recycle();
/* 389 */                   return new BitmapInfo(bitmap, (int) scale, width, height);
                        }
/* 391 */               return null;
                    }
/* 394 */           return src;
                }

/* 407 */       public static Bitmap resizeBitmap(Context context, Uri uri, long size, Rect rect) {
/* 408 */           ContentResolver cr = context.getContentResolver();
/* 409 */           InputStream inputStream = null;
/* 410 */           InputStream optionsStream = null;
                    try {
/* 413 */               optionsStream = cr.openInputStream(uri);
/* 414 */               BitmapFactory.Options dbo = new BitmapFactory.Options();
/* 415 */               dbo.inJustDecodeBounds = true;
/* 416 */               decodeStream(context, optionsStream, null, dbo);
/* 418 */               float aspectRatio = dbo.outWidth / dbo.outHeight;
/* 419 */               float newWidth = (float) Math.sqrt(size * aspectRatio);
/* 420 */               float newHeight = (float) Math.sqrt(size * (1.0f / aspectRatio));
/* 421 */               int inSampleSize = 1;
                        if (dbo.outWidth > newWidth || dbo.outHeight > newHeight) {
/* 424 */                   if (dbo.outWidth > dbo.outHeight) {
/* 425 */                       inSampleSize = (int) Math.ceil(dbo.outHeight / newHeight);
                            } else {
/* 427 */                       inSampleSize = (int) Math.ceil(dbo.outWidth / newWidth);
                            }
/* 430 */                   if (rect != null) {
/* 432 */                       rect.left /= inSampleSize;
/* 433 */                       rect.top /= inSampleSize;
/* 434 */                       rect.right /= inSampleSize;
/* 435 */                       rect.bottom /= inSampleSize;
                            }
                        }
/* 439 */               inputStream = cr.openInputStream(uri);
/* 440 */               dbo.inSampleSize = inSampleSize;
/* 441 */               dbo.inJustDecodeBounds = false;
/* 442 */               Bitmap bitmapDecodeStream = decodeStream(context, inputStream, null, dbo);
/* 446 */               CommonUtil.closeSilently(inputStream);
/* 447 */               CommonUtil.closeSilently(optionsStream);
                        return bitmapDecodeStream;
                    } catch (IOException e) {
/* 446 */               CommonUtil.closeSilently(inputStream);
/* 447 */               CommonUtil.closeSilently(optionsStream);
/* 444 */               return null;
                    } catch (Throwable th) {
                        CommonUtil.closeSilently(inputStream);
/* 447 */               CommonUtil.closeSilently(optionsStream);
                        throw th;
                    }
                }

/* 462 */       public static Bitmap resizeBitmap(Context context, Uri uri, float viewWidth, float viewHeight, int border) {
                    float bitmapWidth;
                    float bitmapHeight;
                    Bitmap srcBitmap;
/* 463 */           ContentResolver cr = context.getContentResolver();
/* 466 */           InputStream inputStream = null;
/* 467 */           InputStream optionStream = null;
                    try {
/* 470 */               optionStream = cr.openInputStream(uri);
/* 472 */               BitmapFactory.Options dbo = new BitmapFactory.Options();
/* 473 */               dbo.inJustDecodeBounds = true;
/* 474 */               decodeStream(context, optionStream, null, dbo);
/* 476 */               float nativeWidth = dbo.outWidth;
/* 477 */               float nativeHeight = dbo.outHeight;
/* 479 */               float viewWidth2 = viewWidth - border;
/* 480 */               float viewHeight2 = viewHeight - border;
/* 486 */               inputStream = cr.openInputStream(uri);
                        if (nativeWidth > viewWidth2 || nativeHeight > viewHeight2) {
/* 489 */                   float dx = nativeWidth / viewWidth2;
/* 490 */                   float dy = nativeHeight / viewHeight2;
                            if (dx > dy) {
/* 492 */                       bitmapWidth = viewWidth2;
/* 493 */                       bitmapHeight = nativeHeight / dx;
                            } else {
/* 495 */                       bitmapWidth = nativeWidth / dy;
/* 496 */                       bitmapHeight = viewHeight2;
                            }
                            if (nativeWidth / bitmapWidth > 1.0f) {
/* 500 */                       BitmapFactory.Options options = new BitmapFactory.Options();
/* 501 */                       options.inSampleSize = (int) (nativeWidth / bitmapWidth);
/* 502 */                       srcBitmap = decodeStream(context, inputStream, null, options);
                            } else {
/* 505 */                       srcBitmap = decodeStream(context, inputStream);
                            }
                        } else {
/* 509 */                   bitmapWidth = viewWidth2;
/* 510 */                   bitmapHeight = viewHeight2;
/* 511 */                   srcBitmap = decodeStream(context, inputStream);
                        }
/* 514 */               if (srcBitmap == null) {
/* 526 */                   CommonUtil.closeSilently(optionStream);
/* 527 */                   CommonUtil.closeSilently(inputStream);
/* 521 */                   return null;
                        }
/* 515 */               Bitmap bitmap = scaleBitmap(context, srcBitmap, bitmapWidth, bitmapHeight, border);
/* 518 */               srcBitmap.recycle();
/* 526 */               CommonUtil.closeSilently(optionStream);
/* 527 */               CommonUtil.closeSilently(inputStream);
                        return bitmap;
                    } catch (IOException e) {
/* 526 */               CommonUtil.closeSilently(optionStream);
/* 527 */               CommonUtil.closeSilently(inputStream);
/* 524 */               return null;
                    } catch (Throwable th) {
                        CommonUtil.closeSilently(optionStream);
/* 527 */               CommonUtil.closeSilently(inputStream);
                        throw th;
                    }
                }

/* 543 */       public static Bitmap scaleBitmap(Context context, Bitmap src, float width, float height, int border) {
/* 544 */           Bitmap bitmap = createBitmap(context, ((int) width) + border, ((int) height) + border, Bitmap.Config.ARGB_8888);
/* 546 */           if (bitmap == null) {
/* 547 */               return null;
                    }
/* 549 */           Canvas canvas = new Canvas(bitmap);
/* 551 */           if (border > 0) {
/* 552 */               canvas.drawColor(-1);
/* 553 */               canvas.drawBitmap(src, new Rect(0, 0, src.getWidth(), src.getHeight()), new Rect(border, border, ((int) width) + 1, (int) height), sResizePaint);
/* 556 */               canvas.drawRect(0.0f, 0.0f, (border + width) - 1.0f, (border + height) - 1.0f, sOutStrokePaint);
/* 558 */               canvas.drawRect(border - 1, border - 1, width, height, sInStrokePaint);
/* 566 */               return bitmap;
                    }
/* 561 */           canvas.drawBitmap(src, new Rect(0, 0, src.getWidth(), src.getHeight()), new Rect(border, border, ((int) width) + 1, (int) height), sResizePaint);
/* 566 */           return bitmap;
                }

/* 578 */       public static File getTempPic(Context context, boolean resized, long ownerId) {
/* 579 */           File dir = CommonUtil.getFilesDir(context);
/* 580 */           if (dir == null) {
/* 581 */               return null;
                    }
/* 583 */           String prefix = resized ? TEMP_RESIZED_PREFIX : TEMP_FILENAME_PREFIX + ownerId + "-";
/* 584 */           return new File(dir, prefix + System.currentTimeMillis() + ".jpg");
                }

/* 593 */       public static boolean isTempPic(Uri uri) {
                    return (uri == null || uri.getLastPathSegment() == null || !uri.getLastPathSegment().startsWith(TEMP_FILENAME_PREFIX)) ? false : true;
                }

/* 604 */       public static boolean deleteTempPic(Uri uri) {
/* 605 */           if (!isTempPic(uri)) {
/* 606 */               return false;
                    }
/* 608 */           File f = new File(uri.getPath());
/* 609 */           return f.delete();
                }

/* 626 */       public static Uri processPicture(Context context, Uri uri, long ownerId) {
/* 627 */           return resizeIfNeeded(context, changeFormatIfNeeded(context, uri, ownerId), MAX_TWEET_UPLOAD_SIZE_KB, ownerId, null);
                }

/* 643 */       public static Uri resizeIfNeeded(Context context, Uri uri, int maxSizeKB, long ownerId, Rect rect) {
/* 644 */           if (uri == null) {
                        return null;
                    }
/* 652 */           long maxSizePixels = maxSizeKB * 250;
                    if (CommonUtil.getFileSize(context, uri) <= maxSizeKB) {
                        return uri;
                    }
/* 655 */           Bitmap smaller = resizeBitmap(context, uri, maxSizePixels, rect);
/* 656 */           if (smaller == null) {
                        return null;
                    }
/* 659 */           Uri newUri = writePicToFile(context, smaller, ownerId);
/* 660 */           if (newUri != null) {
/* 661 */               deleteTempPic(uri);
/* 665 */               return newUri;
                    }
/* 665 */           return newUri;
                }

/* 678 */       public static Uri changeFormatIfNeeded(Context context, Uri uri, long ownerId) {
/* 679 */           if (uri == null) {
                        return null;
                    }
/* 684 */           InputStream inputStream = null;
/* 685 */           InputStream optionsStream = null;
                    try {
/* 688 */               ContentResolver cr = context.getContentResolver();
/* 689 */               optionsStream = cr.openInputStream(uri);
/* 690 */               BitmapFactory.Options dbo = new BitmapFactory.Options();
/* 691 */               dbo.inJustDecodeBounds = true;
/* 692 */               decodeStream(context, optionsStream, null, dbo);
/* 693 */               if (dbo.outMimeType.equals("image/jpeg") || dbo.outMimeType.equals("image/gif") || dbo.outMimeType.equals("image/png")) {
/* 713 */                   CommonUtil.closeSilently(null);
/* 714 */                   CommonUtil.closeSilently(optionsStream);
                            return uri;
                        }
/* 699 */               inputStream = cr.openInputStream(uri);
/* 700 */               Bitmap srcBitmap = decodeStream(context, inputStream);
/* 701 */               if (srcBitmap == null) {
/* 713 */                   CommonUtil.closeSilently(inputStream);
/* 714 */                   CommonUtil.closeSilently(optionsStream);
                            return null;
                        }
/* 702 */               Uri newUri = writePicToFile(context, srcBitmap, ownerId);
/* 703 */               if (newUri != null) {
/* 704 */                   deleteTempPic(uri);
                        }
/* 713 */               CommonUtil.closeSilently(inputStream);
/* 714 */               CommonUtil.closeSilently(optionsStream);
/* 714 */               return newUri;
                    } catch (IOException e) {
/* 713 */               CommonUtil.closeSilently(inputStream);
/* 714 */               CommonUtil.closeSilently(optionsStream);
                        return null;
                    } catch (RuntimeException e) {
                        CommonUtil.closeSilently(inputStream);
/* 714 */               CommonUtil.closeSilently(optionsStream);
                        throw e;
                    } catch (Error e) {
                        CommonUtil.closeSilently(inputStream);
/* 714 */               CommonUtil.closeSilently(optionsStream);
                        throw e;
                    }
                }

/* 723 */       public static Bitmap decodeStream(Context context, InputStream is) {
                    try {
/* 724 */               return BitmapFactory.decodeStream(is);
                    } catch (OutOfMemoryError e) {
/* 727 */               return null;
                    }
                }

/* 738 */       public static Bitmap decodeStream(Context context, InputStream is, Rect outPadding, BitmapFactory.Options opts) {
                    try {
/* 739 */               return BitmapFactory.decodeStream(is, outPadding, opts);
                    } catch (OutOfMemoryError e) {
/* 742 */               return null;
                    }
                }

/* 751 */       public static BitmapInfo decodeByteArray(Context context, byte[] data, int offset, int length) {
                    try {
/* 752 */               BitmapFactory.Options dbo = new BitmapFactory.Options();
/* 753 */               Bitmap bitmap = BitmapFactory.decodeByteArray(data, offset, length, dbo);
/* 754 */               if (bitmap != null) {
/* 755 */                   return new BitmapInfo(bitmap, dbo.inSampleSize, dbo.outWidth, dbo.outHeight);
                        }
                        return null;
                    } catch (OutOfMemoryError e) {
                        return null;
                    }
                }

/* 772 */       public static BitmapInfo decodeByteArray(Context context, byte[] data, int offset, int length, BitmapFactory.Options opts) {
                    try {
/* 773 */               BitmapFactory.Options dbo = new BitmapFactory.Options();
/* 774 */               dbo.inJustDecodeBounds = true;
/* 775 */               BitmapFactory.decodeByteArray(data, 0, data.length, dbo);
/* 776 */               int origWidth = dbo.outWidth;
/* 777 */               int origHeight = dbo.outHeight;
/* 779 */               int scale = opts.inSampleSize;
/* 780 */               Bitmap bitmap = BitmapFactory.decodeByteArray(data, offset, length, opts);
/* 781 */               if (bitmap != null) {
/* 782 */                   return new BitmapInfo(bitmap, scale, origWidth, origHeight);
                        }
                        return null;
                    } catch (OutOfMemoryError e) {
                        return null;
                    }
                }

/* 798 */       public static Bitmap createBitmap(Context context, Bitmap bmp, int left, int top, int width, int height) {
                    try {
/* 799 */               return Bitmap.createBitmap(bmp, left, top, width, height);
                    } catch (OutOfMemoryError e) {
/* 802 */               return null;
                    }
                }

/* 813 */       public static Bitmap createBitmap(Context context, Bitmap bmp, int left, int top, int width, int height, Matrix matrix, boolean filter) {
                    try {
/* 814 */               return Bitmap.createBitmap(bmp, left, top, width, height, matrix, filter);
                    } catch (OutOfMemoryError e) {
/* 817 */               return null;
                    }
                }

/* 827 */       public static Bitmap createBitmap(Context context, int width, int height, Bitmap.Config config) {
                    try {
/* 828 */               return Bitmap.createBitmap(width, height, config);
                    } catch (OutOfMemoryError e) {
/* 831 */               return null;
                    }
                }

/* 844 */       public static Bitmap decodeResource(Context context, Resources resources, int id, BitmapFactory.Options opts) {
                    try {
/* 845 */               return BitmapFactory.decodeResource(resources, id, opts);
                    } catch (OutOfMemoryError e) {
/* 848 */               return null;
                    }
                }

/* 858 */       public static Bitmap decodeResource(Context context, Resources resources, int id) {
                    try {
/* 859 */               return BitmapFactory.decodeResource(resources, id);
                    } catch (OutOfMemoryError e) {
/* 862 */               return null;
                    }
                }

/* 867 */       private static class ImageFilenameFilter implements FilenameFilter {
                    private final long mOwnerId;

/* 868 */           ImageFilenameFilter(long ownerId) {
/* 870 */               this.mOwnerId = ownerId;
                    }

                    @Override // java.io.FilenameFilter
/* 873 */           public boolean accept(File dir, String filename) {
                        return filename.startsWith(new StringBuilder().append(ImageUtils.TEMP_RESIZED_PREFIX).append(this.mOwnerId).toString()) || filename.startsWith(new StringBuilder().append(ImageUtils.TEMP_FILENAME_PREFIX).append(this.mOwnerId).toString());
                    }
                }

/* 884 */       public static void removeFiles(Context context) {
                    File[] files;
/* 885 */           File dir = CommonUtil.getFilesDir(context);
/* 886 */           if (dir != null && (files = dir.listFiles()) != null) {
                        for (File f : files) {
/* 892 */                   if (!f.isDirectory()) {
/* 893 */                       f.delete();
                            }
                        }
                    }
                }

/* 899 */       public static Uri writePicToFile(Context context, Bitmap pic, long ownerId) {
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
                            // Preserve the original best-effort behavior for file I/O failures.
                        } catch (OutOfMemoryError e) {
                            // Preserve the original best-effort behavior for bitmap memory failures.
                        } finally {
                            CommonUtil.closeSilently(fs);
                        }
                    }
                    return uriFromFile;
                }

/* 920 */       public static boolean hasEnoughExternalStorageForNewPhoto(Context context) {
/* 921 */           File dir = CommonUtil.getFilesDir(context);
/* 922 */           if (dir == null) {
                        return false;
                    }
/* 925 */           StatFs stat = new StatFs(dir.getAbsolutePath());
/* 926 */           long bytesAvailable = ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks());
                    return bytesAvailable > 1048576;
                }

/* 934 */       public static class BitmapInfo {
                    public final Bitmap bitmap;
                    public final int origHeight;
                    public final int origWidth;
                    public final int scale;

/* 935 */           public BitmapInfo(Bitmap bitmap, int scale, int origWidth, int origHeight) {
/* 937 */               this.bitmap = bitmap;
/* 938 */               this.scale = scale;
/* 939 */               this.origWidth = origWidth;
/* 940 */               this.origHeight = origHeight;
                    }
                }

/* 944 */       public static int getExifOrientation(String filepath) {
/* 945 */           if (filepath == null) {
                        return 0;
                    }
                    try {
/* 949 */               ExifInterface exif = new ExifInterface(filepath);
/* 950 */               return exif.getAttributeInt("Orientation", 0);
                    } catch (IOException e) {
                        return 0;
                    }
                }

/* 956 */       public static String getRealPathFromImageUri(Context context, Uri uri) {
                    String result;
/* 957 */           String scheme = uri.getScheme();
/* 958 */           if (scheme == null || CommonUtil.FILE.equals(scheme)) {
/* 959 */               return uri.getPath();
                    }
/* 960 */           if ("content".equals(scheme)) {
/* 961 */               String[] proj = {"_data"};
/* 962 */               Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
/* 963 */               if (cursor != null) {
                            try {
/* 965 */                       if (cursor.moveToFirst()) {
/* 966 */                           result = cursor.getString(0);
/* 967 */                           if (result == null) {
/* 970 */                               result = uri.getPath();
/* 976 */                               cursor.close();
                                    } else {
/* 976 */                               cursor.close();
                                    }
                                } else {
/* 973 */                           result = uri.getPath();
/* 976 */                           cursor.close();
                                }
/* 980 */                       return result;
                            } catch (Throwable th) {
                                cursor.close();
                                throw th;
                            }
                        }
                    }
/* 980 */           return uri.getPath();
                }

/* 983 */       public static int getExifOrientation(Context context, Uri uri) {
                    ContentProviderClient provider;
                    int rotationFromFile;
/* 984 */           String scheme = uri.getScheme();
/* 985 */           if (scheme == null || CommonUtil.FILE.equals(scheme)) {
/* 986 */               return getExifOrientation(uri.getPath());
                    }
/* 987 */           if ("content".equals(scheme) && (provider = context.getContentResolver().acquireContentProviderClient(uri)) != null) {
                        try {
/* 993 */                   Cursor result = provider.query(uri, new String[]{"orientation", "_data"}, null, null, null);
/* 1001 */                  if (result == null) {
                                return 0;
                            }
                            try {
/* 1006 */                      if (result.moveToFirst()) {
/* 1007 */                          String path = result.getString(1);
/* 1008 */                          if (path == null || (rotationFromFile = getExifOrientation(path)) == 0) {
/* 1015 */                              int rotationFromCursor = result.getInt(0);
/* 1016 */                              if (rotationFromCursor != 0) {
/* 1022 */                                  result.close();
                                            return rotationFromCursor;
                                        }
                                        result.close();
                                        return 0;
                                    }
/* 1022 */                          result.close();
/* 1026 */                          return rotationFromFile;
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

/* 1037 */      public static Bitmap adjustRotation(Context context, Uri uri, Bitmap bitmap) {
                    float rotation;
/* 1038 */          int degrees = getExifOrientation(context, uri);
                    switch (degrees) {
                        case 3:
/* 1042 */                  rotation = 180.0f;
                            break;
                        case 4:
                        case 5:
                        case 7:
                        default:
/* 1061 */                  return bitmap;
                        case 6:
/* 1048 */                  rotation = 90.0f;
                            break;
                        case 8:
/* 1045 */                  rotation = 270.0f;
                            break;
                    }
/* 1054 */          Matrix matrix = new Matrix();
/* 1055 */          matrix.postRotate(rotation);
/* 1056 */          Bitmap result = createBitmap(context, bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
/* 1058 */          if (result != null && result != bitmap) {
/* 1059 */              bitmap.recycle();
                    }
                    return result;
                }

/* 1064 */      public static boolean isResized(Uri uri) {
                    return isTempPic(uri) && uri.toString().contains(TEMP_RESIZED_PREFIX);
                }

/* 1068 */      public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
/* 1069 */          Bitmap sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
/* 1070 */          Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
/* 1072 */          Canvas canvas = new Canvas(output);
/* 1074 */          Paint paint = new Paint();
/* 1075 */          Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
/* 1077 */          paint.setAntiAlias(true);
/* 1078 */          paint.setFilterBitmap(true);
/* 1079 */          paint.setDither(true);
/* 1080 */          canvas.drawARGB(0, 0, 0, 0);
/* 1081 */          canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2, sbmp.getWidth() / 2, paint);
/* 1083 */          paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
/* 1084 */          canvas.drawBitmap(sbmp, rect, rect, paint);
/* 1086 */          return output;
                }

/* 1089 */      public static Bitmap getMutableBitmap(Bitmap bm) {
/* 1090 */          if (!bm.isMutable()) {
/* 1091 */              return bm.copy(bm.getConfig(), true);
                    }
/* 1093 */          return bm;
                }

/* 1096 */      public static Bitmap addStroke(int color, float width, Bitmap source) {
/* 1097 */          Bitmap bm = getMutableBitmap(source);
/* 1099 */          Canvas canvas = new Canvas(bm);
/* 1100 */          Paint paint = new Paint();
/* 1101 */          paint.setStyle(Paint.Style.STROKE);
/* 1102 */          paint.setColor(color);
/* 1103 */          paint.setStrokeWidth(width);
/* 1104 */          paint.setAntiAlias(true);
/* 1106 */          float halfStroke = width / 2.0f;
/* 1107 */          canvas.drawOval(new RectF(halfStroke, halfStroke, bm.getWidth() - halfStroke, bm.getHeight() - halfStroke), paint);
/* 1109 */          return bm;
                }

/* 1112 */      public static Bitmap desaturateWithColorFilter(Bitmap source, float sat, int color) {
/* 1113 */          Canvas canvas = new Canvas(source);
/* 1114 */          Paint paint = new Paint();
/* 1115 */          paint.setAntiAlias(true);
/* 1118 */          ColorMatrix matrix = new ColorMatrix();
/* 1119 */          matrix.setSaturation(sat);
/* 1120 */          ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
/* 1121 */          paint.setColorFilter(filter);
/* 1122 */          canvas.drawBitmap(source, 0.0f, 0.0f, paint);
/* 1125 */          if (color != 0) {
/* 1126 */              paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
/* 1127 */              canvas.drawOval(new RectF(0.0f, 0.0f, source.getWidth(), source.getHeight()), paint);
                    }
/* 1130 */          return source;
                }
            }
