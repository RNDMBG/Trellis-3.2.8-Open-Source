            package android.support.v4.print;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Matrix;
            import android.graphics.RectF;
            import android.graphics.pdf.PdfDocument;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.os.CancellationSignal;
            import android.os.ParcelFileDescriptor;
            import android.print.PageRange;
            import android.print.PrintAttributes;
            import android.print.PrintDocumentAdapter;
            import android.print.PrintDocumentInfo;
            import android.print.PrintManager;
            import android.print.pdf.PrintedPdfDocument;
            import android.util.Log;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    class PrintHelperKitkat {
                public static final int COLOR_MODE_COLOR = 2;
                public static final int COLOR_MODE_MONOCHROME = 1;
                private static final String LOG_TAG = "PrintHelperKitkat";
                private static final int MAX_PRINT_SIZE = 3500;
                public static final int ORIENTATION_LANDSCAPE = 1;
                public static final int ORIENTATION_PORTRAIT = 2;
                public static final int SCALE_MODE_FILL = 2;
                public static final int SCALE_MODE_FIT = 1;
                final Context mContext;
                BitmapFactory.Options mDecodeOptions = null;
                private final Object mLock = new Object();
                int mScaleMode = 2;
                int mColorMode = 2;
                int mOrientation = 1;

/* 45 */        static /* synthetic */ Matrix access$000(PrintHelperKitkat x0, int x1, int x2, RectF x3, int x4) {
/* 46 */            return x0.getMatrix(x1, x2, x3, x4);
                }

/* 45 */        static /* synthetic */ Bitmap access$200(PrintHelperKitkat x0, Uri x1, int x2) throws FileNotFoundException {
/* 46 */            return x0.loadConstrainedBitmap(x1, x2);
                }

/* 45 */        static /* synthetic */ Object access$300(PrintHelperKitkat x0) {
/* 46 */            return x0.mLock;
                }

/* 86 */        PrintHelperKitkat(Context context) {
/* 88 */            this.mContext = context;
                }

/* 101 */       public void setScaleMode(int scaleMode) {
/* 102 */           this.mScaleMode = scaleMode;
                }

/* 111 */       public int getScaleMode() {
/* 112 */           return this.mScaleMode;
                }

/* 123 */       public void setColorMode(int colorMode) {
/* 124 */           this.mColorMode = colorMode;
                }

/* 133 */       public void setOrientation(int orientation) {
/* 134 */           this.mOrientation = orientation;
                }

/* 143 */       public int getOrientation() {
/* 144 */           return this.mOrientation;
                }

/* 153 */       public int getColorMode() {
/* 154 */           return this.mColorMode;
                }

/* 163 */       public void printBitmap(String jobName, Bitmap bitmap) {
/* 164 */           if (bitmap != null) {
/* 167 */               int fittingMode = this.mScaleMode;
/* 168 */               PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
/* 169 */               PrintAttributes.MediaSize mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
/* 170 */               if (bitmap.getWidth() > bitmap.getHeight()) {
/* 171 */                   mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
                        }
/* 173 */               PrintAttributes attr = new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
/* 178 */               printManager.print(jobName, new C00341(jobName, bitmap, fittingMode), attr);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.print.PrintHelperKitkat$1 */
/* 177 */       class C00341 extends PrintDocumentAdapter {
                    private PrintAttributes mAttributes;
                    final /* synthetic */ Bitmap val$bitmap;
                    final /* synthetic */ int val$fittingMode;
                    final /* synthetic */ String val$jobName;

/* 178 */           C00341(String str, Bitmap bitmap, int i) {
                        this.val$jobName = str;
                        this.val$bitmap = bitmap;
                        this.val$fittingMode = i;
                    }

                    @Override // android.print.PrintDocumentAdapter
/* 188 */           public void onLayout(PrintAttributes oldPrintAttributes, PrintAttributes newPrintAttributes, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
/* 189 */               this.mAttributes = newPrintAttributes;
/* 191 */               PrintDocumentInfo info = new PrintDocumentInfo.Builder(this.val$jobName).setContentType(1).setPageCount(1).build();
/* 195 */               boolean changed = newPrintAttributes.equals(oldPrintAttributes) ? false : true;
/* 196 */               layoutResultCallback.onLayoutFinished(info, changed);
                    }

                    @Override // android.print.PrintDocumentAdapter
/* 202 */           public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
/* 203 */               PrintedPdfDocument pdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                        try {
/* 206 */                   PdfDocument.Page page = pdfDocument.startPage(1);
/* 208 */                   RectF content = new RectF(page.getInfo().getContentRect());
/* 210 */                   Matrix matrix = PrintHelperKitkat.access$000(PrintHelperKitkat.this, this.val$bitmap.getWidth(), this.val$bitmap.getHeight(), content, this.val$fittingMode);
/* 214 */                   page.getCanvas().drawBitmap(this.val$bitmap, matrix, null);
/* 217 */                   pdfDocument.finishPage(page);
                            try {
/* 221 */                       pdfDocument.writeTo(new FileOutputStream(fileDescriptor.getFileDescriptor()));
/* 224 */                       writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                            } catch (IOException ioe) {
/* 228 */                       Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", ioe);
/* 229 */                       writeResultCallback.onWriteFailed(null);
                            }
/* 232 */                   if (pdfDocument != null) {
/* 233 */                       pdfDocument.close();
                            }
/* 235 */                   if (fileDescriptor != null) {
                                try {
/* 237 */                           fileDescriptor.close();
                                } catch (IOException e) {
                                }
                            }
                        } catch (Throwable th) {
                            if (pdfDocument != null) {
/* 233 */                       pdfDocument.close();
                            }
/* 235 */                   if (fileDescriptor != null) {
                                try {
/* 237 */                           fileDescriptor.close();
                                } catch (IOException e2) {
                                }
                            }
/* 240 */                   throw th;
                        }
                    }
                }

/* 256 */       private Matrix getMatrix(int imageWidth, int imageHeight, RectF content, int fittingMode) {
                    float scale;
/* 257 */           Matrix matrix = new Matrix();
/* 260 */           float scale2 = content.width() / imageWidth;
/* 261 */           if (fittingMode == 2) {
/* 262 */               scale = Math.max(scale2, content.height() / imageHeight);
                    } else {
/* 264 */               scale = Math.min(scale2, content.height() / imageHeight);
                    }
/* 266 */           matrix.postScale(scale, scale);
/* 269 */           float translateX = (content.width() - (imageWidth * scale)) / 2.0f;
/* 271 */           float translateY = (content.height() - (imageHeight * scale)) / 2.0f;
/* 273 */           matrix.postTranslate(translateX, translateY);
/* 274 */           return matrix;
                }

/* 286 */       public void printBitmap(String jobName, Uri imageFile) throws FileNotFoundException {
/* 287 */           int fittingMode = this.mScaleMode;
/* 289 */           PrintDocumentAdapter printDocumentAdapter = new C00352(jobName, imageFile, fittingMode);
/* 434 */           PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
/* 435 */           PrintAttributes.Builder builder = new PrintAttributes.Builder();
/* 436 */           builder.setColorMode(this.mColorMode);
/* 438 */           if (this.mOrientation == 1) {
/* 439 */               builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
                    } else if (this.mOrientation == 2) {
/* 441 */               builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
                    }
/* 443 */           PrintAttributes attr = builder.build();
/* 445 */           printManager.print(jobName, printDocumentAdapter, attr);
                }

                /* JADX INFO: renamed from: android.support.v4.print.PrintHelperKitkat$2 */
/* 287 */       class C00352 extends PrintDocumentAdapter {
                    AsyncTask<Uri, Boolean, Bitmap> loadBitmap;
                    private PrintAttributes mAttributes;
                    Bitmap mBitmap = null;
                    final /* synthetic */ int val$fittingMode;
                    final /* synthetic */ Uri val$imageFile;
                    final /* synthetic */ String val$jobName;

/* 288 */           C00352(String str, Uri uri, int i) {
                        this.val$jobName = str;
                        this.val$imageFile = uri;
                        this.val$fittingMode = i;
                    }

/* 288 */           static /* synthetic */ void access$100(C00352 x0) {
/* 289 */               x0.cancelLoad();
                    }

                    @Override // android.print.PrintDocumentAdapter
/* 299 */           public void onLayout(PrintAttributes oldPrintAttributes, PrintAttributes newPrintAttributes, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
/* 300 */               if (cancellationSignal.isCanceled()) {
/* 301 */                   layoutResultCallback.onLayoutCancelled();
/* 302 */                   this.mAttributes = newPrintAttributes;
                        } else if (this.mBitmap != null) {
/* 307 */                   PrintDocumentInfo info = new PrintDocumentInfo.Builder(this.val$jobName).setContentType(1).setPageCount(1).build();
/* 311 */                   boolean changed = newPrintAttributes.equals(oldPrintAttributes) ? false : true;
/* 312 */                   layoutResultCallback.onLayoutFinished(info, changed);
                        } else {
/* 316 */                   this.loadBitmap = new AnonymousClass1(cancellationSignal, newPrintAttributes, oldPrintAttributes, layoutResultCallback);
/* 365 */                   this.loadBitmap.execute(new Uri[0]);
/* 367 */                   this.mAttributes = newPrintAttributes;
                        }
                    }

                    /* JADX INFO: renamed from: android.support.v4.print.PrintHelperKitkat$2$1, reason: invalid class name */
/* 314 */           class AnonymousClass1 extends AsyncTask<Uri, Boolean, Bitmap> {
                        final /* synthetic */ CancellationSignal val$cancellationSignal;
                        final /* synthetic */ PrintDocumentAdapter.LayoutResultCallback val$layoutResultCallback;
                        final /* synthetic */ PrintAttributes val$newPrintAttributes;
                        final /* synthetic */ PrintAttributes val$oldPrintAttributes;

/* 315 */               AnonymousClass1(CancellationSignal cancellationSignal, PrintAttributes printAttributes, PrintAttributes printAttributes2, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback) {
                            this.val$cancellationSignal = cancellationSignal;
                            this.val$newPrintAttributes = printAttributes;
                            this.val$oldPrintAttributes = printAttributes2;
                            this.val$layoutResultCallback = layoutResultCallback;
                        }

                        @Override // android.os.AsyncTask
/* 315 */               protected /* bridge */ /* synthetic */ Bitmap doInBackground(Uri[] uriArr) {
/* 316 */                   return doInBackground2(uriArr);
                        }

                        @Override // android.os.AsyncTask
/* 315 */               protected /* bridge */ /* synthetic */ void onCancelled(Bitmap bitmap) {
/* 316 */                   onCancelled2(bitmap);
                        }

                        @Override // android.os.AsyncTask
/* 315 */               protected /* bridge */ /* synthetic */ void onPostExecute(Bitmap bitmap) {
/* 316 */                   onPostExecute2(bitmap);
                        }

                        /* JADX INFO: renamed from: android.support.v4.print.PrintHelperKitkat$2$1$1, reason: invalid class name and collision with other inner class name */
/* 320 */               class C13111 implements CancellationSignal.OnCancelListener {
/* 321 */                   C13111() {
                            }

                            @Override // android.os.CancellationSignal.OnCancelListener
/* 324 */                   public void onCancel() {
/* 325 */                       C00352.access$100(C00352.this);
/* 326 */                       AnonymousClass1.this.cancel(false);
                            }
                        }

                        @Override // android.os.AsyncTask
/* 320 */               protected void onPreExecute() {
/* 321 */                   this.val$cancellationSignal.setOnCancelListener(new C13111());
                        }

                        /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 333 */               protected Bitmap doInBackground2(Uri... uris) {
                            try {
/* 334 */                       return PrintHelperKitkat.access$200(PrintHelperKitkat.this, C00352.this.val$imageFile, PrintHelperKitkat.MAX_PRINT_SIZE);
                            } catch (FileNotFoundException e) {
/* 338 */                       return null;
                            }
                        }

                        /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 342 */               protected void onPostExecute2(Bitmap bitmap) {
/* 343 */                   super.onPostExecute(bitmap);
/* 344 */                   C00352.this.mBitmap = bitmap;
/* 345 */                   if (bitmap != null) {
/* 346 */                       PrintDocumentInfo info = new PrintDocumentInfo.Builder(C00352.this.val$jobName).setContentType(1).setPageCount(1).build();
/* 350 */                       boolean changed = this.val$newPrintAttributes.equals(this.val$oldPrintAttributes) ? false : true;
/* 352 */                       this.val$layoutResultCallback.onLayoutFinished(info, changed);
/* 357 */                       return;
                            }
/* 355 */                   this.val$layoutResultCallback.onLayoutFailed(null);
                        }

                        /* JADX INFO: renamed from: onCancelled, reason: avoid collision after fix types in other method */
/* 361 */               protected void onCancelled2(Bitmap result) {
/* 362 */                   this.val$layoutResultCallback.onLayoutCancelled();
                        }
                    }

/* 370 */           private void cancelLoad() {
                        synchronized (PrintHelperKitkat.access$300(PrintHelperKitkat.this)) {
                            try {
/* 372 */                       if (PrintHelperKitkat.this.mDecodeOptions != null) {
/* 373 */                           PrintHelperKitkat.this.mDecodeOptions.requestCancelDecode();
/* 374 */                           PrintHelperKitkat.this.mDecodeOptions = null;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.print.PrintDocumentAdapter
/* 380 */           public void onFinish() {
/* 381 */               super.onFinish();
/* 382 */               cancelLoad();
/* 383 */               this.loadBitmap.cancel(true);
                    }

                    @Override // android.print.PrintDocumentAdapter
/* 389 */           public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
/* 390 */               PrintedPdfDocument pdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                        try {
/* 394 */                   PdfDocument.Page page = pdfDocument.startPage(1);
/* 395 */                   RectF content = new RectF(page.getInfo().getContentRect());
/* 398 */                   Matrix matrix = PrintHelperKitkat.access$000(PrintHelperKitkat.this, this.mBitmap.getWidth(), this.mBitmap.getHeight(), content, this.val$fittingMode);
/* 402 */                   page.getCanvas().drawBitmap(this.mBitmap, matrix, null);
/* 405 */                   pdfDocument.finishPage(page);
                            try {
/* 409 */                       pdfDocument.writeTo(new FileOutputStream(fileDescriptor.getFileDescriptor()));
/* 412 */                       writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                            } catch (IOException ioe) {
/* 416 */                       Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", ioe);
/* 417 */                       writeResultCallback.onWriteFailed(null);
                            }
/* 420 */                   if (pdfDocument != null) {
/* 421 */                       pdfDocument.close();
                            }
/* 423 */                   if (fileDescriptor != null) {
                                try {
/* 425 */                           fileDescriptor.close();
                                } catch (IOException e) {
                                }
                            }
                        } catch (Throwable th) {
                            if (pdfDocument != null) {
/* 421 */                       pdfDocument.close();
                            }
/* 423 */                   if (fileDescriptor != null) {
                                try {
/* 425 */                           fileDescriptor.close();
                                } catch (IOException e2) {
                                }
                            }
/* 428 */                   throw th;
                        }
                    }
                }

/* 456 */       private Bitmap loadConstrainedBitmap(Uri uri, int maxSideLength) throws FileNotFoundException {
                    BitmapFactory.Options decodeOptions;
                    Bitmap bitmapLoadBitmap = null;
/* 457 */           if (maxSideLength <= 0 || uri == null || this.mContext == null) {
/* 458 */               throw new IllegalArgumentException("bad argument to getScaledBitmap");
                    }
/* 461 */           BitmapFactory.Options opt = new BitmapFactory.Options();
/* 462 */           opt.inJustDecodeBounds = true;
/* 463 */           loadBitmap(uri, opt);
/* 465 */           int w = opt.outWidth;
/* 466 */           int h = opt.outHeight;
/* 469 */           if (w > 0 && h > 0) {
/* 474 */               int imageSide = Math.max(w, h);
/* 476 */               int sampleSize = 1;
/* 477 */               while (imageSide > maxSideLength) {
/* 478 */                   imageSide >>>= 1;
/* 479 */                   sampleSize <<= 1;
                        }
/* 483 */               if (sampleSize > 0 && Math.min(w, h) / sampleSize > 0) {
                            synchronized (this.mLock) {
                                try {
/* 488 */                           this.mDecodeOptions = new BitmapFactory.Options();
/* 489 */                           this.mDecodeOptions.inMutable = true;
/* 490 */                           this.mDecodeOptions.inSampleSize = sampleSize;
/* 491 */                           decodeOptions = this.mDecodeOptions;
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            try {
/* 494 */                       bitmapLoadBitmap = loadBitmap(uri, decodeOptions);
                                synchronized (this.mLock) {
                                    try {
/* 497 */                               this.mDecodeOptions = null;
                                    } catch (Throwable th2) {
                                        throw th2;
                                    }
                                }
                            } catch (Throwable th3) {
                                synchronized (this.mLock) {
                                    try {
/* 497 */                               this.mDecodeOptions = null;
                                        throw th3;
                                    } catch (Throwable th4) {
                                        throw th4;
                                    }
                                }
                            }
                        }
                    }
/* 498 */           return bitmapLoadBitmap;
                }

/* 506 */       private Bitmap loadBitmap(Uri uri, BitmapFactory.Options o) throws FileNotFoundException {
/* 507 */           if (uri == null || this.mContext == null) {
/* 508 */               throw new IllegalArgumentException("bad argument to loadBitmap");
                    }
/* 510 */           InputStream is = null;
                    try {
/* 512 */               is = this.mContext.getContentResolver().openInputStream(uri);
/* 513 */               Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(is, null, o);
/* 515 */               if (is != null) {
                            try {
/* 517 */                       is.close();
                            } catch (IOException t) {
/* 519 */                       Log.w(LOG_TAG, "close fail ", t);
                            }
                        }
/* 520 */               return bitmapDecodeStream;
                    } catch (Throwable th) {
                        if (is != null) {
                            try {
/* 517 */                       is.close();
                            } catch (IOException t2) {
/* 519 */                       Log.w(LOG_TAG, "close fail ", t2);
                            }
                        }
/* 520 */               throw th;
                    }
                }
            }
