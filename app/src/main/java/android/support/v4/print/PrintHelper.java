            package android.support.v4.print;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.net.Uri;
            import android.os.Build;
            import java.io.FileNotFoundException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 66 */    public final class PrintHelper {
                public static final int COLOR_MODE_COLOR = 2;
                public static final int COLOR_MODE_MONOCHROME = 1;
                public static final int ORIENTATION_LANDSCAPE = 1;
                public static final int ORIENTATION_PORTRAIT = 2;
                public static final int SCALE_MODE_FILL = 2;
                public static final int SCALE_MODE_FIT = 1;
                PrintHelperVersionImpl mImpl;

                interface PrintHelperVersionImpl {
                    int getColorMode();

                    int getOrientation();

                    int getScaleMode();

                    void printBitmap(String str, Bitmap bitmap);

                    void printBitmap(String str, Uri uri) throws FileNotFoundException;

                    void setColorMode(int i);

                    void setOrientation(int i);

                    void setScaleMode(int i);
                }

/* 67 */        public static boolean systemSupportsPrint() {
                    return Build.VERSION.SDK_INT >= 19;
                }

/* 99 */        private static final class PrintHelperStubImpl implements PrintHelperVersionImpl {
                    int mColorMode;
                    int mOrientation;
                    int mScaleMode;

/* 100 */           private PrintHelperStubImpl() {
/* 102 */               this.mScaleMode = 2;
/* 103 */               this.mColorMode = 2;
/* 104 */               this.mOrientation = 1;
                    }

/* 100 */           /* synthetic */ PrintHelperStubImpl(C00331 x0) {
/* 101 */               this();
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 106 */           public void setScaleMode(int scaleMode) {
/* 107 */               this.mScaleMode = scaleMode;
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 111 */           public int getColorMode() {
/* 112 */               return this.mColorMode;
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 116 */           public void setColorMode(int colorMode) {
/* 117 */               this.mColorMode = colorMode;
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 120 */           public void setOrientation(int orientation) {
/* 121 */               this.mOrientation = orientation;
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 123 */           public int getOrientation() {
/* 124 */               return this.mOrientation;
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 127 */           public int getScaleMode() {
/* 128 */               return this.mScaleMode;
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 132 */           public void printBitmap(String jobName, Bitmap bitmap) {
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 136 */           public void printBitmap(String jobName, Uri imageFile) {
                    }
                }

/* 144 */       private static final class PrintHelperKitkatImpl implements PrintHelperVersionImpl {
                    private final PrintHelperKitkat mPrintHelper;

/* 145 */           PrintHelperKitkatImpl(Context context) {
/* 147 */               this.mPrintHelper = new PrintHelperKitkat(context);
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 151 */           public void setScaleMode(int scaleMode) {
/* 152 */               this.mPrintHelper.setScaleMode(scaleMode);
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 156 */           public int getScaleMode() {
/* 157 */               return this.mPrintHelper.getScaleMode();
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 161 */           public void setColorMode(int colorMode) {
/* 162 */               this.mPrintHelper.setColorMode(colorMode);
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 166 */           public int getColorMode() {
/* 167 */               return this.mPrintHelper.getColorMode();
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 171 */           public void setOrientation(int orientation) {
/* 172 */               this.mPrintHelper.setOrientation(orientation);
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 176 */           public int getOrientation() {
/* 177 */               return this.mPrintHelper.getOrientation();
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 181 */           public void printBitmap(String jobName, Bitmap bitmap) {
/* 182 */               this.mPrintHelper.printBitmap(jobName, bitmap);
                    }

                    @Override // android.support.v4.print.PrintHelper.PrintHelperVersionImpl
/* 186 */           public void printBitmap(String jobName, Uri imageFile) throws FileNotFoundException {
/* 187 */               this.mPrintHelper.printBitmap(jobName, imageFile);
                    }
                }

/* 196 */       public PrintHelper(Context context) {
/* 198 */           if (systemSupportsPrint()) {
/* 199 */               this.mImpl = new PrintHelperKitkatImpl(context);
                    } else {
/* 201 */               this.mImpl = new PrintHelperStubImpl(null);
                    }
                }

/* 214 */       public void setScaleMode(int scaleMode) {
/* 215 */           this.mImpl.setScaleMode(scaleMode);
                }

/* 224 */       public int getScaleMode() {
/* 225 */           return this.mImpl.getScaleMode();
                }

/* 236 */       public void setColorMode(int colorMode) {
/* 237 */           this.mImpl.setColorMode(colorMode);
                }

/* 246 */       public int getColorMode() {
/* 247 */           return this.mImpl.getColorMode();
                }

/* 257 */       public void setOrientation(int orientation) {
/* 258 */           this.mImpl.setOrientation(orientation);
                }

/* 267 */       public int getOrientation() {
/* 268 */           return this.mImpl.getOrientation();
                }

/* 277 */       public void printBitmap(String jobName, Bitmap bitmap) {
/* 278 */           this.mImpl.printBitmap(jobName, bitmap);
                }

/* 290 */       public void printBitmap(String jobName, Uri imageFile) throws FileNotFoundException {
/* 291 */           this.mImpl.printBitmap(jobName, imageFile);
                }
            }
