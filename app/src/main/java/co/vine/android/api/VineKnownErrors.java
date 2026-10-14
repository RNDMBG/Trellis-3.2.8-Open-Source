            package co.vine.android.api;

            import android.content.Context;
            import co.vine.android.C0257R;
            import co.vine.android.service.VineService;
            import com.googlecode.javacv.cpp.avcodec;
            import com.googlecode.javacv.cpp.avutil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 65 */    public enum VineKnownErrors {
                INVALID_ERROR_CODE(-1, C0257R.string.API_ERROR_INVALID_ERROR_CODE),
                UNKNOWN_ERROR(0, C0257R.string.API_ERROR_UNKNOWN_ERROR),
                UNSUPPORTED_METHOD(1, C0257R.string.API_ERROR_UNSUPPORTED_METHOD),
                MISSING_REQUIRED_FIELD(3, C0257R.string.API_ERROR_MISSING_REQUIRED_FIELD),
                ACCESS_DENIED(4, C0257R.string.API_ERROR_ACCESS_DENIED),
                INVALID_ARGUMENT(5, C0257R.string.API_ERROR_INVALID_ARGUMENT),
                NON_INTEGER_VALUE(6, C0257R.string.API_ERROR_NON_INTEGER_VALUE),
                REQUIRES_LOGIN(100, C0257R.string.API_ERROR_REQUIRES_LOGIN),
                BAD_CREDENTIALS(101, C0257R.string.API_ERROR_BAD_CREDENTIALS),
                EXPIRED_SESSION(102, C0257R.string.API_ERROR_EXPIRED_SESSION),
                INVALID_SESSION(103, C0257R.string.API_ERROR_INVALID_SESSION),
                FACEBOOK_NOT_AUTHORIZED(104, C0257R.string.API_ERROR_FACEBOOK_NOT_AUTHORIZED),
                TWITTER_NOT_AUTHORIZED(105, C0257R.string.API_ERROR_TWITTER_NOT_AUTHORIZED),
                ADDRESS_BOOK_NOT_FOUND(106, C0257R.string.API_ERROR_ADDRESS_BOOK_NOT_FOUND),
                FACEBOOK_FF_DISABLED(107, C0257R.string.API_ERROR_FACEBOOK_FF_DISABLED),
                REQUIRES_ADMIN_LOGIN(avcodec.AV_CODEC_ID_JV, C0257R.string.API_ERROR_REQUIRES_ADMIN_LOGIN),
                ACCOUNT_DEACTIVATED(187, C0257R.string.API_ERROR_ACCOUNT_DEACTIVATED),
                USERNAME_IN_USE(200, C0257R.string.API_ERROR_USERNAME_IN_USE),
                USERNAME_INVALID_CHARACTERS(201, C0257R.string.API_ERROR_USERNAME_INVALID_CHARACTERS),
                USERNAME_INVALID_LENGTH(202, C0257R.string.API_ERROR_USERNAME_INVALID_LENGTH),
                ORPHANED_USER_ACCOUNT(203, C0257R.string.API_ERROR_ORPHANED_USER_ACCOUNT),
                DESCRIPTION_INVALID_LENGTH(205, C0257R.string.API_ERROR_DESCRIPTION_INVALID_LENGTH),
                TOO_MANY_MENTIONS(206, C0257R.string.API_ERROR_TOO_MANY_MENTIONS),
                DUPLICATE_MENTIONS(207, C0257R.string.API_ERROR_DUPLICATE_MENTIONS),
                USER_DESCRIPTION_INVALID_LENGTH(208, C0257R.string.API_ERROR_USER_DESCRIPTION_INVALID_LENGTH),
                PASSWORD_INVALID(210, C0257R.string.API_ERROR_PASSWORD_INVALID),
                PHONE_INVALID(211, C0257R.string.API_ERROR_PHONE_INVALID),
                LOCATION_INVALID_LENGTH(212, C0257R.string.API_ERROR_LOCATION_INVALID_LENGTH),
                EMAIL_IN_USE(220, C0257R.string.API_ERROR_EMAIL_IN_USE),
                EMAIL_INVALID(221, C0257R.string.API_ERROR_EMAIL_INVALID),
                TWITTER_NAME_IN_USE(222, C0257R.string.API_ERROR_TWITTER_NAME_IN_USE),
                TWITTER_WRONG_ACCOUNT(223, C0257R.string.API_ERROR_TWITTER_WRONG_ACCOUNT),
                PASSWORD_RESET_TOKEN_EXPIRED(225, C0257R.string.API_ERROR_PASSWORD_RESET_TOKEN_EXPIRED),
                CANT_FOLLOW_SELF(250, C0257R.string.API_ERROR_CANT_FOLLOW_SELF),
                CANT_FLAG_SELF(251, C0257R.string.API_ERROR_CANT_FLAG_SELF),
                FSQUARE_VENUE_INVALID(avutil.AV_PIX_FMT_YUV420P12BE, C0257R.string.API_ERROR_FSQUARE_VENUE_INVALID),
                VIDEOURL_INVALID(302, C0257R.string.API_ERROR_VIDEOURL_INVALID),
                COMMENT_INVALID_LENGTH(avutil.AV_PIX_FMT_YUV420P14BE, C0257R.string.API_ERROR_COMMENT_INVALID_LENGTH),
                AVATAR_INVALID(304, C0257R.string.API_ERROR_AVATAR_INVALID),
                MESSAGE_RECIPIENT_INVALID_EMAIL_ADDRESS(333, 0),
                RELATIONSHIP_REQUIRES_ACTION(400, C0257R.string.API_ERROR_RELATIONSHIP_REQUIRES_ACTION),
                CAPTCHA(419, C0257R.string.API_ERROR_CAPTCHA_REQUIRED),
                NOTIFICATION_REFERENCES_SELF(500, C0257R.string.API_ERROR_NOTIFICATION_REFERENCES_SELF),
                RECORD_DOES_NOT_EXIST(VineService.AMAZON_ERROR_CODE, C0257R.string.API_ERROR_RECORD_DOES_NOT_EXIST),
                NO_ARGUMENTS_PROVIDED(901, C0257R.string.API_ERROR_NO_ARGUMENTS_PROVIDED),
                VERIFY_TOKEN_EXPIRED(620, C0257R.string.API_ERROR_TOKEN_EXPIRED),
                VERIFY_CONTACT_MISMATCH(621, C0257R.string.API_ERROR_VERIFY_CONTACT_MISMATCH),
                VERIFY_PHONE_WRONG_USER(623, C0257R.string.API_ERROR_VERIFY_PHONE_WRONG_USER),
                VERIFY_TOKEN_NOT_FOUND(624, C0257R.string.API_ERROR_VERIFY_TOKEN_NOT_FOUND),
                MESSAGE_RECIPIENT_OUT_OF_NETWORK(666, 0);

                public final int code;
                public final int message;

/* 66 */        VineKnownErrors(int code, int message) {
/* 68 */            this.code = code;
/* 69 */            this.message = message;
                }

/* 72 */        public String getMessage(Context context) {
/* 73 */            return context.getString(this.message);
                }

/* 76 */        public static VineKnownErrors valueOf(VineError error) {
/* 77 */            return valueOf(error.errorCode);
                }

/* 80 */        public static VineKnownErrors valueOf(int code) {
                    switch (code) {
                        case 0:
/* 83 */                    return UNKNOWN_ERROR;
                        case 1:
/* 85 */                    return UNSUPPORTED_METHOD;
                        case 3:
/* 87 */                    return MISSING_REQUIRED_FIELD;
                        case 4:
/* 89 */                    return ACCESS_DENIED;
                        case 5:
/* 91 */                    return INVALID_ARGUMENT;
                        case 6:
/* 93 */                    return NON_INTEGER_VALUE;
                        case 100:
/* 95 */                    return REQUIRES_LOGIN;
                        case 101:
/* 97 */                    return BAD_CREDENTIALS;
                        case 102:
/* 99 */                    return EXPIRED_SESSION;
                        case 103:
/* 101 */                   return INVALID_SESSION;
                        case 104:
/* 103 */                   return FACEBOOK_NOT_AUTHORIZED;
                        case 105:
/* 105 */                   return TWITTER_NOT_AUTHORIZED;
                        case 106:
/* 107 */                   return ADDRESS_BOOK_NOT_FOUND;
                        case 107:
/* 109 */                   return FACEBOOK_FF_DISABLED;
                        case avcodec.AV_CODEC_ID_JV /* 150 */:
/* 111 */                   return REQUIRES_ADMIN_LOGIN;
                        case 200:
/* 113 */                   return USERNAME_IN_USE;
                        case 201:
/* 115 */                   return USERNAME_INVALID_CHARACTERS;
                        case 202:
/* 117 */                   return USERNAME_INVALID_LENGTH;
                        case 203:
/* 119 */                   return ORPHANED_USER_ACCOUNT;
                        case 205:
/* 121 */                   return DESCRIPTION_INVALID_LENGTH;
                        case 206:
/* 123 */                   return TOO_MANY_MENTIONS;
                        case 207:
/* 125 */                   return DUPLICATE_MENTIONS;
                        case 208:
/* 127 */                   return USER_DESCRIPTION_INVALID_LENGTH;
                        case 210:
/* 129 */                   return PASSWORD_INVALID;
                        case 211:
/* 131 */                   return PHONE_INVALID;
                        case 212:
/* 133 */                   return LOCATION_INVALID_LENGTH;
                        case 220:
/* 135 */                   return EMAIL_IN_USE;
                        case 221:
/* 137 */                   return EMAIL_INVALID;
                        case 222:
/* 139 */                   return TWITTER_NAME_IN_USE;
                        case 223:
/* 141 */                   return TWITTER_WRONG_ACCOUNT;
                        case 225:
/* 143 */                   return PASSWORD_RESET_TOKEN_EXPIRED;
                        case 250:
/* 145 */                   return CANT_FOLLOW_SELF;
                        case 251:
/* 147 */                   return CANT_FLAG_SELF;
                        case avutil.AV_PIX_FMT_YUV420P12BE /* 301 */:
/* 149 */                   return FSQUARE_VENUE_INVALID;
                        case 302:
/* 151 */                   return VIDEOURL_INVALID;
                        case avutil.AV_PIX_FMT_YUV420P14BE /* 303 */:
/* 153 */                   return COMMENT_INVALID_LENGTH;
                        case 304:
/* 155 */                   return AVATAR_INVALID;
                        case 333:
/* 157 */                   return MESSAGE_RECIPIENT_INVALID_EMAIL_ADDRESS;
                        case 400:
/* 159 */                   return RELATIONSHIP_REQUIRES_ACTION;
                        case 419:
/* 161 */                   return CAPTCHA;
                        case 500:
/* 163 */                   return NOTIFICATION_REFERENCES_SELF;
                        case 620:
/* 165 */                   return VERIFY_TOKEN_EXPIRED;
                        case 621:
/* 167 */                   return VERIFY_CONTACT_MISMATCH;
                        case 623:
/* 169 */                   return VERIFY_PHONE_WRONG_USER;
                        case 624:
/* 171 */                   return VERIFY_TOKEN_NOT_FOUND;
                        case 666:
/* 173 */                   return MESSAGE_RECIPIENT_OUT_OF_NETWORK;
                        case VineService.AMAZON_ERROR_CODE /* 900 */:
/* 175 */                   return RECORD_DOES_NOT_EXIST;
                        case 901:
/* 177 */                   return NO_ARGUMENTS_PROVIDED;
                        default:
/* 179 */                   return INVALID_ERROR_CODE;
                    }
                }
            }
