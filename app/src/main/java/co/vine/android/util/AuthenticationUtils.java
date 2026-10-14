            package co.vine.android.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class AuthenticationUtils {
                private static final int MIN_PASSWORD_CHARS = 6;
                private static final int MIN_USERNAME_CHARS = 3;

                public enum Result {
                    EMPTY,
                    TOO_SHORT,
                    VALID
                }

/* 18 */        public static Result validateUsername(String username) {
/* 19 */            if (username == null || username.isEmpty()) {
/* 20 */                return Result.EMPTY;
                    }
/* 21 */            if (username.length() < 3) {
/* 22 */                return Result.TOO_SHORT;
                    }
/* 24 */            return Result.VALID;
                }

/* 35 */        public static boolean isUsernameValid(String username) {
                    return validateUsername(username) == Result.VALID;
                }

/* 46 */        public static Result validatePassword(String password) {
/* 47 */            if (password == null || password.isEmpty()) {
/* 48 */                return Result.EMPTY;
                    }
/* 49 */            if (password.length() < 6) {
/* 50 */                return Result.TOO_SHORT;
                    }
/* 52 */            return Result.VALID;
                }

/* 63 */        public static boolean isPasswordValid(String password) {
                    return validatePassword(password) == Result.VALID;
                }

/* 73 */        public static boolean isEmailAddressValid(String emailAddress) {
                    return emailAddress != null && emailAddress.indexOf(64) >= 0 && emailAddress.indexOf(46) >= 0;
                }
            }
