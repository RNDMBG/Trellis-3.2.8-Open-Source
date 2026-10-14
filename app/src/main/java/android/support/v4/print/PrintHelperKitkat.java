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
            
            
    class PrintHelperKitkat {
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

        static  Matrix access$000(PrintHelperKitkat x0, int x1, int x2, RectF x3, int x4) {
            return x0.getMatrix(x1, x2, x3, x4);
                }

        static  Bitmap access$200(PrintHelperKitkat x0, Uri x1, int x2) throws FileNotFoundException {
            return x0.loadConstrainedBitmap(x1, x2);
                }

        static  Object access$300(PrintHelperKitkat x0) {
            return x0.mLock;
                }

        PrintHelperKitkat(Context context) {
            this.mContext = context;
                }

       public void setScaleMode(int scaleMode) {
           this.mScaleMode = scaleMode;
                }

       public int getScaleMode() {
           return this.mScaleMode;
                }

       public void setColorMode(int colorMode) {
           this.mColorMode = colorMode;
                }

       public void setOrientation(int orientation) {
           this.mOrientation = orientation;
                }

       public int getOrientation() {
           return this.mOrientation;
                }

       public int getColorMode() {
           return this.mColorMode;
                }

       public void printBitmap(String jobName, Bitmap bitmap) {
           if (bitmap != null) {
               int fittingMode = this.mScaleMode;
               PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
               PrintAttributes.MediaSize mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
               if (bitmap.getWidth() > bitmap.getHeight()) {
                   mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
                        }
               PrintAttributes attr = new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
               printManager.print(jobName, new C00341(jobName, bitmap, fittingMode), attr);
                    }
                }

                
       class C00341 extends PrintDocumentAdapter {
                    private PrintAttributes mAttributes;
                    final  Bitmap val$bitmap;
                    final  int val$fittingMode;
                    final  String val$jobName;

           C00341(String str, Bitmap bitmap, int i) {
                        this.val$jobName = str;
                        this.val$bitmap = bitmap;
                        this.val$fittingMode = i;
                    }

                    @Override 
           public void onLayout(PrintAttributes oldPrintAttributes, PrintAttributes newPrintAttributes, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
               this.mAttributes = newPrintAttributes;
               PrintDocumentInfo info = new PrintDocumentInfo.Builder(this.val$jobName).setContentType(1).setPageCount(1).build();
               boolean changed = newPrintAttributes.equals(oldPrintAttributes) ? false : true;
               layoutResultCallback.onLayoutFinished(info, changed);
                    }

                    @Override 
           public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
               PrintedPdfDocument pdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                        try {
                   PdfDocument.Page page = pdfDocument.startPage(1);
                   RectF content = new RectF(page.getInfo().getContentRect());
                   Matrix matrix = PrintHelperKitkat.access$000(PrintHelperKitkat.this, this.val$bitmap.getWidth(), this.val$bitmap.getHeight(), content, this.val$fittingMode);
                   page.getCanvas().drawBitmap(this.val$bitmap, matrix, null);
                   pdfDocument.finishPage(page);
                            try {
                       pdfDocument.writeTo(new FileOutputStream(fileDescriptor.getFileDescriptor()));
                       writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                            } catch (IOException ioe) {
                       Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", ioe);
                       writeResultCallback.onWriteFailed(null);
                            }
                   if (pdfDocument != null) {
                       pdfDocument.close();
                            }
                   if (fileDescriptor != null) {
                                try {
                           fileDescriptor.close();
                                } catch (IOException e) {
                                }
                            }
                        } catch (Throwable th) {
                            if (pdfDocument != null) {
                       pdfDocument.close();
                            }
                   if (fileDescriptor != null) {
                                try {
                           fileDescriptor.close();
                                } catch (IOException e2) {
                                }
                            }
                   throw th;
                        }
                    }
                }

       private Matrix getMatrix(int imageWidth, int imageHeight, RectF content, int fittingMode) {
                    float scale;
           Matrix matrix = new Matrix();
           float scale2 = content.width() / imageWidth;
           if (fittingMode == 2) {
               scale = Math.max(scale2, content.height() / imageHeight);
                    } else {
               scale = Math.min(scale2, content.height() / imageHeight);
                    }
           matrix.postScale(scale, scale);
           float translateX = (content.width() - (imageWidth * scale)) / 2.0f;
           float translateY = (content.height() - (imageHeight * scale)) / 2.0f;
           matrix.postTranslate(translateX, translateY);
           return matrix;
                }

       public void printBitmap(String jobName, Uri imageFile) throws FileNotFoundException {
           int fittingMode = this.mScaleMode;
           PrintDocumentAdapter printDocumentAdapter = new C00352(jobName, imageFile, fittingMode);
           PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
           PrintAttributes.Builder builder = new PrintAttributes.Builder();
           builder.setColorMode(this.mColorMode);
           if (this.mOrientation == 1) {
               builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
                    } else if (this.mOrientation == 2) {
               builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
                    }
           PrintAttributes attr = builder.build();
           printManager.print(jobName, printDocumentAdapter, attr);
                }

                
       class C00352 extends PrintDocumentAdapter {
                    AsyncTask<Uri, Boolean, Bitmap> loadBitmap;
                    private PrintAttributes mAttributes;
                    Bitmap mBitmap = null;
                    final  int val$fittingMode;
                    final  Uri val$imageFile;
                    final  String val$jobName;

           C00352(String str, Uri uri, int i) {
                        this.val$jobName = str;
                        this.val$imageFile = uri;
                        this.val$fittingMode = i;
                    }

           static  void access$100(C00352 x0) {
               x0.cancelLoad();
                    }

                    @Override 
           public void onLayout(PrintAttributes oldPrintAttributes, PrintAttributes newPrintAttributes, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
               if (cancellationSignal.isCanceled()) {
                   layoutResultCallback.onLayoutCancelled();
                   this.mAttributes = newPrintAttributes;
                        } else if (this.mBitmap != null) {
                   PrintDocumentInfo info = new PrintDocumentInfo.Builder(this.val$jobName).setContentType(1).setPageCount(1).build();
                   boolean changed = newPrintAttributes.equals(oldPrintAttributes) ? false : true;
                   layoutResultCallback.onLayoutFinished(info, changed);
                        } else {
                   this.loadBitmap = new AnonymousClass1(cancellationSignal, newPrintAttributes, oldPrintAttributes, layoutResultCallback);
                   this.loadBitmap.execute(new Uri[0]);
                   this.mAttributes = newPrintAttributes;
                        }
                    }

                    
           class AnonymousClass1 extends AsyncTask<Uri, Boolean, Bitmap> {
                        final  CancellationSignal val$cancellationSignal;
                        final  PrintDocumentAdapter.LayoutResultCallback val$layoutResultCallback;
                        final  PrintAttributes val$newPrintAttributes;
                        final  PrintAttributes val$oldPrintAttributes;

               AnonymousClass1(CancellationSignal cancellationSignal, PrintAttributes printAttributes, PrintAttributes printAttributes2, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback) {
                            this.val$cancellationSignal = cancellationSignal;
                            this.val$newPrintAttributes = printAttributes;
                            this.val$oldPrintAttributes = printAttributes2;
                            this.val$layoutResultCallback = layoutResultCallback;
                        }

                        @Override 
               protected   Bitmap doInBackground(Uri[] uriArr) {
                   return doInBackground2(uriArr);
                        }

                        @Override 
               protected   void onCancelled(Bitmap bitmap) {
                   onCancelled2(bitmap);
                        }

                        @Override 
               protected   void onPostExecute(Bitmap bitmap) {
                   onPostExecute2(bitmap);
                        }

                        
               class C13111 implements CancellationSignal.OnCancelListener {
                   C13111() {
                            }

                            @Override 
                   public void onCancel() {
                       C00352.access$100(C00352.this);
                       AnonymousClass1.this.cancel(false);
                            }
                        }

                        @Override 
               protected void onPreExecute() {
                   this.val$cancellationSignal.setOnCancelListener(new C13111());
                        }

                        
               protected Bitmap doInBackground2(Uri... uris) {
                            try {
                       return PrintHelperKitkat.access$200(PrintHelperKitkat.this, C00352.this.val$imageFile, PrintHelperKitkat.MAX_PRINT_SIZE);
                            } catch (FileNotFoundException e) {
                       return null;
                            }
                        }

                        
               protected void onPostExecute2(Bitmap bitmap) {
                   super.onPostExecute(bitmap);
                   C00352.this.mBitmap = bitmap;
                   if (bitmap != null) {
                       PrintDocumentInfo info = new PrintDocumentInfo.Builder(C00352.this.val$jobName).setContentType(1).setPageCount(1).build();
                       boolean changed = this.val$newPrintAttributes.equals(this.val$oldPrintAttributes) ? false : true;
                       this.val$layoutResultCallback.onLayoutFinished(info, changed);
                       return;
                            }
                   this.val$layoutResultCallback.onLayoutFailed(null);
                        }

                        
               protected void onCancelled2(Bitmap result) {
                   this.val$layoutResultCallback.onLayoutCancelled();
                        }
                    }

           private void cancelLoad() {
                        synchronized (PrintHelperKitkat.access$300(PrintHelperKitkat.this)) {
                            try {
                       if (PrintHelperKitkat.this.mDecodeOptions != null) {
                           PrintHelperKitkat.this.mDecodeOptions.requestCancelDecode();
                           PrintHelperKitkat.this.mDecodeOptions = null;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override 
           public void onFinish() {
               super.onFinish();
               cancelLoad();
               this.loadBitmap.cancel(true);
                    }

                    @Override 
           public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor fileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
               PrintedPdfDocument pdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                        try {
                   PdfDocument.Page page = pdfDocument.startPage(1);
                   RectF content = new RectF(page.getInfo().getContentRect());
                   Matrix matrix = PrintHelperKitkat.access$000(PrintHelperKitkat.this, this.mBitmap.getWidth(), this.mBitmap.getHeight(), content, this.val$fittingMode);
                   page.getCanvas().drawBitmap(this.mBitmap, matrix, null);
                   pdfDocument.finishPage(page);
                            try {
                       pdfDocument.writeTo(new FileOutputStream(fileDescriptor.getFileDescriptor()));
                       writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                            } catch (IOException ioe) {
                       Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", ioe);
                       writeResultCallback.onWriteFailed(null);
                            }
                   if (pdfDocument != null) {
                       pdfDocument.close();
                            }
                   if (fileDescriptor != null) {
                                try {
                           fileDescriptor.close();
                                } catch (IOException e) {
                                }
                            }
                        } catch (Throwable th) {
                            if (pdfDocument != null) {
                       pdfDocument.close();
                            }
                   if (fileDescriptor != null) {
                                try {
                           fileDescriptor.close();
                                } catch (IOException e2) {
                                }
                            }
                   throw th;
                        }
                    }
                }

       private Bitmap loadConstrainedBitmap(Uri uri, int maxSideLength) throws FileNotFoundException {
                    BitmapFactory.Options decodeOptions;
                    Bitmap bitmapLoadBitmap = null;
           if (maxSideLength <= 0 || uri == null || this.mContext == null) {
               throw new IllegalArgumentException("bad argument to getScaledBitmap");
                    }
           BitmapFactory.Options opt = new BitmapFactory.Options();
           opt.inJustDecodeBounds = true;
           loadBitmap(uri, opt);
           int w = opt.outWidth;
           int h = opt.outHeight;
           if (w > 0 && h > 0) {
               int imageSide = Math.max(w, h);
               int sampleSize = 1;
               while (imageSide > maxSideLength) {
                   imageSide >>>= 1;
                   sampleSize <<= 1;
                        }
               if (sampleSize > 0 && Math.min(w, h) / sampleSize > 0) {
                            synchronized (this.mLock) {
                                try {
                           this.mDecodeOptions = new BitmapFactory.Options();
                           this.mDecodeOptions.inMutable = true;
                           this.mDecodeOptions.inSampleSize = sampleSize;
                           decodeOptions = this.mDecodeOptions;
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            try {
                       bitmapLoadBitmap = loadBitmap(uri, decodeOptions);
                                synchronized (this.mLock) {
                                    try {
                               this.mDecodeOptions = null;
                                    } catch (Throwable th2) {
                                        throw th2;
                                    }
                                }
                            } catch (Throwable th3) {
                                synchronized (this.mLock) {
                                    try {
                               this.mDecodeOptions = null;
                                        throw th3;
                                    } catch (Throwable th4) {
                                        throw th4;
                                    }
                                }
                            }
                        }
                    }
           return bitmapLoadBitmap;
                }

       private Bitmap loadBitmap(Uri uri, BitmapFactory.Options o) throws FileNotFoundException {
           if (uri == null || this.mContext == null) {
               throw new IllegalArgumentException("bad argument to loadBitmap");
                    }
           InputStream is = null;
                    try {
               is = this.mContext.getContentResolver().openInputStream(uri);
               Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(is, null, o);
               if (is != null) {
                            try {
                       is.close();
                            } catch (IOException t) {
                       Log.w(LOG_TAG, "close fail ", t);
                            }
                        }
               return bitmapDecodeStream;
                    } catch (Throwable th) {
                        if (is != null) {
                            try {
                       is.close();
                            } catch (IOException t2) {
                       Log.w(LOG_TAG, "close fail ", t2);
                            }
                        }
               throw th;
                    }
                }
            }
