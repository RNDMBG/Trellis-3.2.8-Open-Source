            package org.scribe.utils;

            import java.util.regex.Pattern;
            import org.scribe.model.OAuthConstants;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class Preconditions {
                private static final String DEFAULT_MESSAGE = "Received an invalid parameter";
                private static final Pattern URL_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9+.-]*://\\S+");

/* 18 */        private Preconditions() {
                }

/* 30 */        public static void checkNotNull(Object object, String errorMsg) {
/* 31 */            check(object != null, errorMsg);
                }

/* 43 */        public static void checkEmptyString(String string, String errorMsg) {
/* 44 */            check((string == null || string.trim().equals("")) ? false : true, errorMsg);
                }

/* 54 */        public static void checkValidUrl(String url, String errorMsg) {
/* 55 */            checkEmptyString(url, errorMsg);
/* 56 */            check(isUrl(url), errorMsg);
                }

/* 66 */        public static void checkValidOAuthCallback(String url, String errorMsg) {
/* 67 */            checkEmptyString(url, errorMsg);
/* 68 */            if (url.toLowerCase().compareToIgnoreCase(OAuthConstants.OUT_OF_BAND) != 0) {
/* 70 */                check(isUrl(url), errorMsg);
                    }
                }

/* 75 */        private static boolean isUrl(String url) {
/* 76 */            return URL_PATTERN.matcher(url).matches();
                }

/* 80 */        private static void check(boolean requirements, String error) {
/* 81 */            String message = (error == null || error.trim().length() <= 0) ? DEFAULT_MESSAGE : error;
/* 82 */            if (!requirements) {
/* 84 */                throw new IllegalArgumentException(message);
                    }
                }
            }
