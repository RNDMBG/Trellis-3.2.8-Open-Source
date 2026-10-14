            package co.vine.android.util;
            
            
    public class AuthenticationUtils {
                private static final int MIN_PASSWORD_CHARS = 6;
                private static final int MIN_USERNAME_CHARS = 3;

                public enum Result {
                    EMPTY,
                    TOO_SHORT,
                    VALID
                }

        public static Result validateUsername(String username) {
            if (username == null || username.isEmpty()) {
                return Result.EMPTY;
                    }
            if (username.length() < 3) {
                return Result.TOO_SHORT;
                    }
            return Result.VALID;
                }

        public static boolean isUsernameValid(String username) {
                    return validateUsername(username) == Result.VALID;
                }

        public static Result validatePassword(String password) {
            if (password == null || password.isEmpty()) {
                return Result.EMPTY;
                    }
            if (password.length() < 6) {
                return Result.TOO_SHORT;
                    }
            return Result.VALID;
                }

        public static boolean isPasswordValid(String password) {
                    return validatePassword(password) == Result.VALID;
                }

        public static boolean isEmailAddressValid(String emailAddress) {
                    return emailAddress != null && emailAddress.indexOf(64) >= 0 && emailAddress.indexOf(46) >= 0;
                }
            }
