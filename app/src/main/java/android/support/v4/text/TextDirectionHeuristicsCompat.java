            package android.support.v4.text;

            import java.nio.CharBuffer;
            import java.util.Locale;
            
            
    public class TextDirectionHeuristicsCompat {
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

        static  int access$100(int x0) {
            return isRtlTextOrFormat(x0);
                }

        static  int access$200(int x0) {
            return isRtlText(x0);
                }

        private static int isRtlText(int directionality) {
                    switch (directionality) {
                        case 0:
                    return 1;
                        case 1:
                        case 2:
                    return 0;
                        default:
                    return 2;
                    }
                }

        private static int isRtlTextOrFormat(int directionality) {
                    switch (directionality) {
                        case 0:
                        case 14:
                        case 15:
                    return 1;
                        case 1:
                        case 2:
                        case 16:
                        case 17:
                   return 0;
                        default:
                   return 2;
                    }
                }

       private static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
                    private final TextDirectionAlgorithm mAlgorithm;

                    protected abstract boolean defaultIsRtl();

           public TextDirectionHeuristicImpl(TextDirectionAlgorithm algorithm) {
               this.mAlgorithm = algorithm;
                    }

                    @Override 
           public boolean isRtl(char[] array, int start, int count) {
               return isRtl(CharBuffer.wrap(array), start, count);
                    }

                    @Override 
           public boolean isRtl(CharSequence cs, int start, int count) {
               if (cs == null || start < 0 || count < 0 || cs.length() - count < start) {
                   throw new IllegalArgumentException();
                        }
                        return this.mAlgorithm == null ? defaultIsRtl() : doCheck(cs, start, count);
                    }

           private boolean doCheck(CharSequence cs, int start, int count) {
                        switch (this.mAlgorithm.checkRtl(cs, start, count)) {
                            case 0:
                       return true;
                            case 1:
                       return false;
                            default:
                       return defaultIsRtl();
                        }
                    }
                }

       private static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
                    private final boolean mDefaultIsRtl;

            TextDirectionHeuristicInternal(TextDirectionAlgorithm x0, boolean x1, C00371 x2) {
               this(x0, x1);
                    }

           private TextDirectionHeuristicInternal(TextDirectionAlgorithm algorithm, boolean defaultIsRtl) {
               super(algorithm);
               this.mDefaultIsRtl = defaultIsRtl;
                    }

                    @Override 
           protected boolean defaultIsRtl() {
               return this.mDefaultIsRtl;
                    }
                }

       private static class FirstStrong implements TextDirectionAlgorithm {
                    public static final FirstStrong INSTANCE = new FirstStrong();

                    @Override 
           public int checkRtl(CharSequence cs, int start, int count) {
               int result = 2;
                        int e = start + count;
                        for (int i = start; i < e && result == 2; i++) {
                   result = TextDirectionHeuristicsCompat.access$100(Character.getDirectionality(cs.charAt(i)));
                        }
               return result;
                    }

           private FirstStrong() {
                    }
                }

       private static class AnyStrong implements TextDirectionAlgorithm {
                    private final boolean mLookForRtl;
                    public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
                    public static final AnyStrong INSTANCE_LTR = new AnyStrong(false);

                    @Override 
           public int checkRtl(CharSequence cs, int start, int count) {
               boolean haveUnlookedFor = false;
                        int e = start + count;
                        for (int i = start; i < e; i++) {
                            switch (TextDirectionHeuristicsCompat.access$200(Character.getDirectionality(cs.charAt(i)))) {
                                case 0:
                           if (this.mLookForRtl) {
                                        return 0;
                                    }
                           haveUnlookedFor = true;
                                    break;
                                    break;
                                case 1:
                           if (!this.mLookForRtl) {
                                        return 1;
                                    }
                           haveUnlookedFor = true;
                                    break;
                                    break;
                            }
                        }
               if (haveUnlookedFor) {
                            return !this.mLookForRtl ? 0 : 1;
                        }
               return 2;
                    }

           private AnyStrong(boolean lookForRtl) {
               this.mLookForRtl = lookForRtl;
                    }
                }

       private static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
                    public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

           public TextDirectionHeuristicLocale() {
               super(null);
                    }

                    @Override 
           protected boolean defaultIsRtl() {
               int dir = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
                        return dir == 1;
                    }
                }
            }
