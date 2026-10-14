            package android.support.v4.text;

            import java.nio.CharBuffer;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class TextDirectionHeuristicsCompat {
                private static final int STATE_FALSE = 1;
                private static final int STATE_TRUE = 0;
                private static final int STATE_UNKNOWN = 2;
                public static final TextDirectionHeuristicCompat LTR = new TextDirectionHeuristicInternal(null, false, null);
                public static final TextDirectionHeuristicCompat RTL = new TextDirectionHeuristicInternal(null, true, null);
                public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false, null);
                public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true, null);
                public static final TextDirectionHeuristicCompat ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false, null);
                public static final TextDirectionHeuristicCompat LOCALE = TextDirectionHeuristicLocale.INSTANCE;

                private interface TextDirectionAlgorithm {
                    int checkRtl(CharSequence charSequence, int i, int i2);
                }

/* 27 */        static /* synthetic */ int access$100(int x0) {
/* 28 */            return isRtlTextOrFormat(x0);
                }

/* 27 */        static /* synthetic */ int access$200(int x0) {
/* 28 */            return isRtlText(x0);
                }

/* 78 */        private static int isRtlText(int directionality) {
                    switch (directionality) {
                        case 0:
/* 81 */                    return 1;
                        case 1:
                        case 2:
/* 84 */                    return 0;
                        default:
/* 86 */                    return 2;
                    }
                }

/* 90 */        private static int isRtlTextOrFormat(int directionality) {
                    switch (directionality) {
                        case 0:
                        case 14:
                        case 15:
/* 95 */                    return 1;
                        case 1:
                        case 2:
                        case 16:
                        case 17:
/* 100 */                   return 0;
                        default:
/* 102 */                   return 2;
                    }
                }

/* 112 */       private static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
                    private final TextDirectionAlgorithm mAlgorithm;

                    protected abstract boolean defaultIsRtl();

/* 113 */           public TextDirectionHeuristicImpl(TextDirectionAlgorithm algorithm) {
/* 115 */               this.mAlgorithm = algorithm;
                    }

                    @Override // android.support.v4.text.TextDirectionHeuristicCompat
/* 124 */           public boolean isRtl(char[] array, int start, int count) {
/* 125 */               return isRtl(CharBuffer.wrap(array), start, count);
                    }

                    @Override // android.support.v4.text.TextDirectionHeuristicCompat
/* 129 */           public boolean isRtl(CharSequence cs, int start, int count) {
/* 130 */               if (cs == null || start < 0 || count < 0 || cs.length() - count < start) {
/* 131 */                   throw new IllegalArgumentException();
                        }
                        return this.mAlgorithm == null ? defaultIsRtl() : doCheck(cs, start, count);
                    }

/* 139 */           private boolean doCheck(CharSequence cs, int start, int count) {
                        switch (this.mAlgorithm.checkRtl(cs, start, count)) {
                            case 0:
/* 142 */                       return true;
                            case 1:
/* 144 */                       return false;
                            default:
/* 146 */                       return defaultIsRtl();
                        }
                    }
                }

/* 149 */       private static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
                    private final boolean mDefaultIsRtl;

/* 150 */           /* synthetic */ TextDirectionHeuristicInternal(TextDirectionAlgorithm x0, boolean x1, C00371 x2) {
/* 151 */               this(x0, x1);
                    }

/* 155 */           private TextDirectionHeuristicInternal(TextDirectionAlgorithm algorithm, boolean defaultIsRtl) {
/* 156 */               super(algorithm);
/* 157 */               this.mDefaultIsRtl = defaultIsRtl;
                    }

                    @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
/* 161 */           protected boolean defaultIsRtl() {
/* 162 */               return this.mDefaultIsRtl;
                    }
                }

/* 181 */       private static class FirstStrong implements TextDirectionAlgorithm {
                    public static final FirstStrong INSTANCE = new FirstStrong();

                    @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
/* 182 */           public int checkRtl(CharSequence cs, int start, int count) {
/* 183 */               int result = 2;
                        int e = start + count;
                        for (int i = start; i < e && result == 2; i++) {
/* 185 */                   result = TextDirectionHeuristicsCompat.access$100(Character.getDirectionality(cs.charAt(i)));
                        }
/* 187 */               return result;
                    }

/* 189 */           private FirstStrong() {
                    }
                }

/* 204 */       private static class AnyStrong implements TextDirectionAlgorithm {
                    private final boolean mLookForRtl;
                    public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
                    public static final AnyStrong INSTANCE_LTR = new AnyStrong(false);

                    @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
/* 205 */           public int checkRtl(CharSequence cs, int start, int count) {
/* 206 */               boolean haveUnlookedFor = false;
                        int e = start + count;
                        for (int i = start; i < e; i++) {
                            switch (TextDirectionHeuristicsCompat.access$200(Character.getDirectionality(cs.charAt(i)))) {
                                case 0:
/* 210 */                           if (this.mLookForRtl) {
                                        return 0;
                                    }
/* 213 */                           haveUnlookedFor = true;
                                    break;
                                    break;
                                case 1:
/* 216 */                           if (!this.mLookForRtl) {
                                        return 1;
                                    }
/* 219 */                           haveUnlookedFor = true;
                                    break;
                                    break;
                            }
                        }
/* 225 */               if (haveUnlookedFor) {
                            return !this.mLookForRtl ? 0 : 1;
                        }
/* 228 */               return 2;
                    }

/* 230 */           private AnyStrong(boolean lookForRtl) {
/* 232 */               this.mLookForRtl = lookForRtl;
                    }
                }

/* 243 */       private static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
                    public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

/* 244 */           public TextDirectionHeuristicLocale() {
/* 245 */               super(null);
                    }

                    @Override // android.support.v4.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
/* 249 */           protected boolean defaultIsRtl() {
/* 250 */               int dir = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
                        return dir == 1;
                    }
                }
            }
