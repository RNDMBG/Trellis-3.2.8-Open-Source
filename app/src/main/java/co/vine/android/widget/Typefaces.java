            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.AssetManager;
            import android.graphics.Typeface;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class Typefaces {
                public static final int WEIGHT_BOLD = 4;
                public static final int WEIGHT_LIGHT = 1;
                public static final int WEIGHT_MEDIUM = 3;
                public static final int WEIGHT_NORMAL = 2;
                private static Typefaces sInstance;
                public final Typeface boldContent;
                public final Typeface boldContentBold;
                public final Typeface boldContentBoldItalic;
                public final Typeface boldContentDetail;
                public final Typeface boldContentItalic;
                public final Typeface lightContent;
                public final Typeface lightContentBold;
                public final Typeface lightContentBoldItalic;
                public final Typeface lightContentDetail;
                public final Typeface lightContentItalic;
                public final Typeface mediumContent;
                public final Typeface mediumContentBold;
                public final Typeface mediumContentBoldItalic;
                public final Typeface mediumContentDetail;
                public final Typeface mediumContentItalic;
                public final Typeface regularContent;
                public final Typeface regularContentBold;
                public final Typeface regularContentBoldItalic;
                public final Typeface regularContentDetail;
                public final Typeface regularContentItalic;

/* 45 */        private Typefaces(Context context) {
/* 47 */            AssetManager mgr = context.getAssets();
/* 48 */            Typeface regular = Typeface.createFromAsset(mgr, "fonts/Roboto-Regular.ttf");
/* 49 */            this.regularContent = regular;
/* 50 */            this.regularContentItalic = Typeface.createFromAsset(mgr, "fonts/Roboto-Italic.ttf");
/* 51 */            this.regularContentBold = Typeface.createFromAsset(mgr, "fonts/Roboto-Bold.ttf");
/* 52 */            this.regularContentBoldItalic = Typeface.create(this.regularContentBold, 2);
/* 53 */            this.regularContentDetail = regular;
/* 55 */            Typeface bold = Typeface.createFromAsset(mgr, "fonts/Roboto-Bold.ttf");
/* 56 */            this.boldContent = bold;
/* 57 */            this.boldContentItalic = Typeface.createFromAsset(mgr, "fonts/Roboto-BoldItalic.ttf");
/* 58 */            this.boldContentBold = bold;
/* 59 */            this.boldContentBoldItalic = Typeface.create(bold, 2);
/* 60 */            this.boldContentDetail = bold;
/* 62 */            Typeface medium = Typeface.createFromAsset(mgr, "fonts/Roboto-Medium.ttf");
/* 63 */            this.mediumContent = medium;
/* 64 */            this.mediumContentItalic = Typeface.createFromAsset(mgr, "fonts/Roboto-MediumItalic.ttf");
/* 65 */            this.mediumContentBold = this.boldContent;
/* 66 */            this.mediumContentBoldItalic = Typeface.create(this.mediumContentBold, 2);
/* 67 */            this.mediumContentDetail = medium;
/* 69 */            Typeface light = Typeface.createFromAsset(mgr, "fonts/Roboto-Light.ttf");
/* 70 */            this.lightContent = light;
/* 71 */            this.lightContentItalic = Typeface.createFromAsset(mgr, "fonts/Roboto-LightItalic.ttf");
/* 72 */            this.lightContentBold = this.regularContent;
/* 73 */            this.lightContentBoldItalic = Typeface.create(this.lightContentBold, 2);
/* 74 */            this.lightContentDetail = light;
                }

/* 84 */        public Typeface getContentTypeface(int style, int weight) {
                    switch (weight) {
                        case 1:
/* 87 */                    if ((style & 1) != 0 && (style & 2) != 0) {
/* 88 */                        return this.lightContentBoldItalic;
                            }
/* 89 */                    if ((style & 1) != 0) {
/* 90 */                        return this.lightContentBold;
                            }
/* 91 */                    if ((style & 2) != 0) {
/* 92 */                        return this.lightContentItalic;
                            }
/* 94 */                    return this.lightContent;
                        case 2:
                        default:
/* 121 */                   if ((style & 1) != 0 && (style & 2) != 0) {
/* 122 */                       return this.regularContentBoldItalic;
                            }
/* 123 */                   if ((style & 1) != 0) {
/* 124 */                       return this.regularContentBold;
                            }
/* 125 */                   if ((style & 2) != 0) {
/* 126 */                       return this.regularContentItalic;
                            }
/* 128 */                   return this.regularContent;
                        case 3:
/* 98 */                    if ((style & 1) != 0 && (style & 2) != 0) {
/* 99 */                        return this.mediumContentBoldItalic;
                            }
/* 100 */                   if ((style & 1) != 0) {
/* 101 */                       return this.mediumContentBold;
                            }
/* 102 */                   if ((style & 2) != 0) {
/* 103 */                       return this.mediumContentItalic;
                            }
/* 105 */                   return this.mediumContent;
                        case 4:
/* 109 */                   if ((style & 1) != 0 && (style & 2) != 0) {
/* 110 */                       return this.boldContentBoldItalic;
                            }
/* 111 */                   if ((style & 1) != 0) {
/* 112 */                       return this.boldContentBold;
                            }
/* 113 */                   if ((style & 2) != 0) {
/* 114 */                       return this.boldContentItalic;
                            }
/* 116 */                   return this.boldContent;
                    }
                }

/* 134 */       public static synchronized Typefaces get(Context context) {
                    try {
                        if (sInstance == null) {
/* 136 */                   sInstance = new Typefaces(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 138 */           return sInstance;
                }
            }
