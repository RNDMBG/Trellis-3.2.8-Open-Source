            package co.vine.android.api;

            import android.content.Context;
            import co.vine.android.R;
            import co.vine.android.service.VineService;
            import com.googlecode.javacv.cpp.avcodec;
            import com.googlecode.javacv.cpp.avutil;
            
            
    public enum VineKnownErrors {
                INVALID_ERROR_CODE(-1, R.string.API_ERROR_INVALID_ERROR_CODE),
                UNKNOWN_ERROR(0, R.string.API_ERROR_UNKNOWN_ERROR),
                UNSUPPORTED_METHOD(1, R.string.API_ERROR_UNSUPPORTED_METHOD),
                MISSING_REQUIRED_FIELD(3, R.string.API_ERROR_MISSING_REQUIRED_FIELD),
                ACCESS_DENIED(4, R.string.API_ERROR_ACCESS_DENIED),
                INVALID_ARGUMENT(5, R.string.API_ERROR_INVALID_ARGUMENT),
                NON_INTEGER_VALUE(6, R.string.API_ERROR_NON_INTEGER_VALUE),
                REQUIRES_LOGIN(100, R.string.API_ERROR_REQUIRES_LOGIN),
                BAD_CREDENTIALS(101, R.string.API_ERROR_BAD_CREDENTIALS),
                EXPIRED_SESSION(102, R.string.API_ERROR_EXPIRED_SESSION),
                INVALID_SESSION(103, R.string.API_ERROR_INVALID_SESSION),
                FACEBOOK_NOT_AUTHORIZED(104, R.string.API_ERROR_FACEBOOK_NOT_AUTHORIZED),
                TWITTER_NOT_AUTHORIZED(105, R.string.API_ERROR_TWITTER_NOT_AUTHORIZED),
                ADDRESS_BOOK_NOT_FOUND(106, R.string.API_ERROR_ADDRESS_BOOK_NOT_FOUND),
                FACEBOOK_FF_DISABLED(107, R.string.API_ERROR_FACEBOOK_FF_DISABLED),
                REQUIRES_ADMIN_LOGIN(avcodec.AV_CODEC_ID_JV, R.string.API_ERROR_REQUIRES_ADMIN_LOGIN),
                ACCOUNT_DEACTIVATED(187, R.string.API_ERROR_ACCOUNT_DEACTIVATED),
                USERNAME_IN_USE(200, R.string.API_ERROR_USERNAME_IN_USE),
                USERNAME_INVALID_CHARACTERS(201, R.string.API_ERROR_USERNAME_INVALID_CHARACTERS),
                USERNAME_INVALID_LENGTH(202, R.string.API_ERROR_USERNAME_INVALID_LENGTH),
                ORPHANED_USER_ACCOUNT(203, R.string.API_ERROR_ORPHANED_USER_ACCOUNT),
                DESCRIPTION_INVALID_LENGTH(205, R.string.API_ERROR_DESCRIPTION_INVALID_LENGTH),
                TOO_MANY_MENTIONS(206, R.string.API_ERROR_TOO_MANY_MENTIONS),
                DUPLICATE_MENTIONS(207, R.string.API_ERROR_DUPLICATE_MENTIONS),
                USER_DESCRIPTION_INVALID_LENGTH(208, R.string.API_ERROR_USER_DESCRIPTION_INVALID_LENGTH),
                PASSWORD_INVALID(210, R.string.API_ERROR_PASSWORD_INVALID),
                PHONE_INVALID(211, R.string.API_ERROR_PHONE_INVALID),
                LOCATION_INVALID_LENGTH(212, R.string.API_ERROR_LOCATION_INVALID_LENGTH),
                EMAIL_IN_USE(220, R.string.API_ERROR_EMAIL_IN_USE),
                EMAIL_INVALID(221, R.string.API_ERROR_EMAIL_INVALID),
                TWITTER_NAME_IN_USE(222, R.string.API_ERROR_TWITTER_NAME_IN_USE),
                TWITTER_WRONG_ACCOUNT(223, R.string.API_ERROR_TWITTER_WRONG_ACCOUNT),
                PASSWORD_RESET_TOKEN_EXPIRED(225, R.string.API_ERROR_PASSWORD_RESET_TOKEN_EXPIRED),
                CANT_FOLLOW_SELF(250, R.string.API_ERROR_CANT_FOLLOW_SELF),
                CANT_FLAG_SELF(251, R.string.API_ERROR_CANT_FLAG_SELF),
                FSQUARE_VENUE_INVALID(avutil.AV_PIX_FMT_YUV420P12BE, R.string.API_ERROR_FSQUARE_VENUE_INVALID),
                VIDEOURL_INVALID(302, R.string.API_ERROR_VIDEOURL_INVALID),
                COMMENT_INVALID_LENGTH(avutil.AV_PIX_FMT_YUV420P14BE, R.string.API_ERROR_COMMENT_INVALID_LENGTH),
                AVATAR_INVALID(304, R.string.API_ERROR_AVATAR_INVALID),
                MESSAGE_RECIPIENT_INVALID_EMAIL_ADDRESS(333, 0),
                RELATIONSHIP_REQUIRES_ACTION(400, R.string.API_ERROR_RELATIONSHIP_REQUIRES_ACTION),
                CAPTCHA(419, R.string.API_ERROR_CAPTCHA_REQUIRED),
                NOTIFICATION_REFERENCES_SELF(500, R.string.API_ERROR_NOTIFICATION_REFERENCES_SELF),
                RECORD_DOES_NOT_EXIST(VineService.AMAZON_ERROR_CODE, R.string.API_ERROR_RECORD_DOES_NOT_EXIST),
                NO_ARGUMENTS_PROVIDED(901, R.string.API_ERROR_NO_ARGUMENTS_PROVIDED),
                VERIFY_TOKEN_EXPIRED(620, R.string.API_ERROR_TOKEN_EXPIRED),
                VERIFY_CONTACT_MISMATCH(621, R.string.API_ERROR_VERIFY_CONTACT_MISMATCH),
                VERIFY_PHONE_WRONG_USER(623, R.string.API_ERROR_VERIFY_PHONE_WRONG_USER),
                VERIFY_TOKEN_NOT_FOUND(624, R.string.API_ERROR_VERIFY_TOKEN_NOT_FOUND),
                MESSAGE_RECIPIENT_OUT_OF_NETWORK(666, 0);

                public final int code;
                public final int message;

        VineKnownErrors(int code, int message) {
            this.code = code;
            this.message = message;
                }

        public String getMessage(Context context) {
            return context.getString(this.message);
                }

        public static VineKnownErrors valueOf(VineError error) {
            return valueOf(error.errorCode);
                }

        public static VineKnownErrors valueOf(int code) {
                    switch (code) {
                        case 0:
                    return UNKNOWN_ERROR;
                        case 1:
                    return UNSUPPORTED_METHOD;
                        case 3:
                    return MISSING_REQUIRED_FIELD;
                        case 4:
                    return ACCESS_DENIED;
                        case 5:
                    return INVALID_ARGUMENT;
                        case 6:
                    return NON_INTEGER_VALUE;
                        case 100:
                    return REQUIRES_LOGIN;
                        case 101:
                    return BAD_CREDENTIALS;
                        case 102:
                    return EXPIRED_SESSION;
                        case 103:
                   return INVALID_SESSION;
                        case 104:
                   return FACEBOOK_NOT_AUTHORIZED;
                        case 105:
                   return TWITTER_NOT_AUTHORIZED;
                        case 106:
                   return ADDRESS_BOOK_NOT_FOUND;
                        case 107:
                   return FACEBOOK_FF_DISABLED;
                        case avcodec.AV_CODEC_ID_JV :
                   return REQUIRES_ADMIN_LOGIN;
                        case 200:
                   return USERNAME_IN_USE;
                        case 201:
                   return USERNAME_INVALID_CHARACTERS;
                        case 202:
                   return USERNAME_INVALID_LENGTH;
                        case 203:
                   return ORPHANED_USER_ACCOUNT;
                        case 205:
                   return DESCRIPTION_INVALID_LENGTH;
                        case 206:
                   return TOO_MANY_MENTIONS;
                        case 207:
                   return DUPLICATE_MENTIONS;
                        case 208:
                   return USER_DESCRIPTION_INVALID_LENGTH;
                        case 210:
                   return PASSWORD_INVALID;
                        case 211:
                   return PHONE_INVALID;
                        case 212:
                   return LOCATION_INVALID_LENGTH;
                        case 220:
                   return EMAIL_IN_USE;
                        case 221:
                   return EMAIL_INVALID;
                        case 222:
                   return TWITTER_NAME_IN_USE;
                        case 223:
                   return TWITTER_WRONG_ACCOUNT;
                        case 225:
                   return PASSWORD_RESET_TOKEN_EXPIRED;
                        case 250:
                   return CANT_FOLLOW_SELF;
                        case 251:
                   return CANT_FLAG_SELF;
                        case avutil.AV_PIX_FMT_YUV420P12BE :
                   return FSQUARE_VENUE_INVALID;
                        case 302:
                   return VIDEOURL_INVALID;
                        case avutil.AV_PIX_FMT_YUV420P14BE :
                   return COMMENT_INVALID_LENGTH;
                        case 304:
                   return AVATAR_INVALID;
                        case 333:
                   return MESSAGE_RECIPIENT_INVALID_EMAIL_ADDRESS;
                        case 400:
                   return RELATIONSHIP_REQUIRES_ACTION;
                        case 419:
                   return CAPTCHA;
                        case 500:
                   return NOTIFICATION_REFERENCES_SELF;
                        case 620:
                   return VERIFY_TOKEN_EXPIRED;
                        case 621:
                   return VERIFY_CONTACT_MISMATCH;
                        case 623:
                   return VERIFY_PHONE_WRONG_USER;
                        case 624:
                   return VERIFY_TOKEN_NOT_FOUND;
                        case 666:
                   return MESSAGE_RECIPIENT_OUT_OF_NETWORK;
                        case VineService.AMAZON_ERROR_CODE :
                   return RECORD_DOES_NOT_EXIST;
                        case 901:
                   return NO_ARGUMENTS_PROVIDED;
                        default:
                   return INVALID_ERROR_CODE;
                    }
                }
            }
