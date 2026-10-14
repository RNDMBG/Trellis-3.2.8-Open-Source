            package android.support.v4.text;

            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class TextUtilsCompat {
                public static final Locale ROOT = new Locale("", "");
                private static String ARAB_SCRIPT_SUBTAG = "Arab";
                private static String HEBR_SCRIPT_SUBTAG = "Hebr";

/* 30 */        public static String htmlEncode(String s) {
/* 31 */            StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < s.length(); i++) {
/* 34 */                char c = s.charAt(i);
                        switch (c) {
                            case '\"':
/* 53 */                        sb.append("&quot;");
                                break;
                            case '&':
/* 43 */                        sb.append("&amp;");
                                break;
                            case '\'':
/* 50 */                        sb.append("&#39;");
                                break;
                            case '<':
/* 37 */                        sb.append("&lt;");
                                break;
                            case '>':
/* 40 */                        sb.append("&gt;");
                                break;
                            default:
/* 56 */                        sb.append(c);
                                break;
                        }
                    }
/* 59 */            return sb.toString();
                }

/* 72 */        public static int getLayoutDirectionFromLocale(Locale locale) {
/* 73 */            if (locale != null && !locale.equals(ROOT)) {
/* 74 */                String scriptSubtag = ICUCompat.getScript(ICUCompat.addLikelySubtags(locale.toString()));
/* 76 */                if (scriptSubtag == null) {
                            return getLayoutDirectionFromFirstChar(locale);
                        }
/* 78 */                if (scriptSubtag.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG) || scriptSubtag.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) {
/* 80 */                    return 1;
                        }
                    }
/* 84 */            return 0;
                }

/* 99 */        private static int getLayoutDirectionFromFirstChar(Locale locale) {
                    switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                        case 1:
                        case 2:
/* 103 */                   return 1;
                        default:
                            return 0;
                    }
                }
            }
