            package com.facebook;

            import com.facebook.internal.Utility;
            import com.googlecode.javacv.cpp.avutil;
            import java.net.HttpURLConnection;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 69 */    public final class FacebookRequestError {
                private static final String BODY_KEY = "body";
                private static final String CODE_KEY = "code";
                private static final int EC_APP_NOT_INSTALLED = 458;
                private static final int EC_APP_TOO_MANY_CALLS = 4;
                private static final int EC_EXPIRED = 463;
                private static final int EC_INVALID_SESSION = 102;
                private static final int EC_INVALID_TOKEN = 190;
                private static final int EC_PASSWORD_CHANGED = 460;
                private static final int EC_PERMISSION_DENIED = 10;
                private static final int EC_SERVICE_UNAVAILABLE = 2;
                private static final int EC_UNCONFIRMED_USER = 464;
                private static final int EC_UNKNOWN_ERROR = 1;
                private static final int EC_USER_CHECKPOINTED = 459;
                private static final int EC_USER_TOO_MANY_CALLS = 17;
                private static final String ERROR_CODE_FIELD_KEY = "code";
                private static final String ERROR_CODE_KEY = "error_code";
                private static final String ERROR_KEY = "error";
                private static final String ERROR_MESSAGE_FIELD_KEY = "message";
                private static final String ERROR_MSG_KEY = "error_msg";
                private static final String ERROR_REASON_KEY = "error_reason";
                private static final String ERROR_SUB_CODE_KEY = "error_subcode";
                private static final String ERROR_TYPE_FIELD_KEY = "type";
                public static final int INVALID_ERROR_CODE = -1;
                public static final int INVALID_HTTP_STATUS_CODE = -1;
                private static final int INVALID_MESSAGE_ID = 0;
                private final Object batchRequestResult;
                private final Category category;
                private final HttpURLConnection connection;
                private final int errorCode;
                private final String errorMessage;
                private final String errorType;
                private final FacebookException exception;
                private final JSONObject requestResult;
                private final JSONObject requestResultBody;
                private final int requestStatusCode;
                private final boolean shouldNotifyUser;
                private final int subErrorCode;
                private final int userActionMessageId;
                private static int STRING_COM_FACEBOOK_REQUESTERROR_PERMISSIONS = -1;
                private static int STRING_COM_FACEBOOK_REQUESTERROR_RELOGIN = -1;
                private static int STRING_COM_FACEBOOK_REQUESTERROR_WEB_LOGIN = -1;
                private static int STRING_COM_FACEBOOK_REQUESTERROR_PASSWORD_CHANGED = -1;
                private static int STRING_COM_FACEBOOK_REQUESTERROR_RECONNECT = -1;
                private static final Range EC_RANGE_PERMISSION = new Range(200, avutil.AV_PIX_FMT_YUVA444P, null);
                private static final Range HTTP_RANGE_SUCCESS = new Range(200, avutil.AV_PIX_FMT_YUVA444P, null);
                private static final Range HTTP_RANGE_CLIENT_ERROR = new Range(400, 499, null);
                private static final Range HTTP_RANGE_SERVER_ERROR = new Range(500, 599, null);

                public enum Category {
                    AUTHENTICATION_RETRY,
                    AUTHENTICATION_REOPEN_SESSION,
                    PERMISSION,
                    SERVER,
                    THROTTLING,
                    OTHER,
                    BAD_REQUEST,
                    CLIENT
                }

/* 70 */        private static class Range {
                    private final int end;
                    private final int start;

/* 71 */            /* synthetic */ Range(int x0, int x1, Object x2) {
/* 72 */                this(x0, x1);
                    }

/* 74 */            private Range(int start, int end) {
/* 76 */                this.start = start;
/* 77 */                this.end = end;
                    }

/* 80 */            boolean contains(int value) {
                        return this.start <= value && value <= this.end;
                    }
                }

/* 89 */        public static void initRequestErrorPermissions(int permissions, int relogin, int webLogin, int passwordChanged, int reconnect) {
/* 90 */            STRING_COM_FACEBOOK_REQUESTERROR_PERMISSIONS = permissions;
/* 91 */            STRING_COM_FACEBOOK_REQUESTERROR_RELOGIN = relogin;
/* 92 */            STRING_COM_FACEBOOK_REQUESTERROR_WEB_LOGIN = webLogin;
/* 93 */            STRING_COM_FACEBOOK_REQUESTERROR_PASSWORD_CHANGED = passwordChanged;
/* 94 */            STRING_COM_FACEBOOK_REQUESTERROR_RECONNECT = reconnect;
                }

/* 131 */       private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection, FacebookException exception) {
/* 133 */           if (STRING_COM_FACEBOOK_REQUESTERROR_PASSWORD_CHANGED == -1 || STRING_COM_FACEBOOK_REQUESTERROR_RELOGIN == -1 || STRING_COM_FACEBOOK_REQUESTERROR_WEB_LOGIN == -1 || STRING_COM_FACEBOOK_REQUESTERROR_PERMISSIONS == -1 || STRING_COM_FACEBOOK_REQUESTERROR_RECONNECT == -1) {
/* 139 */               throw new IllegalStateException("Error strings not initialized, is the application setup wrong?");
                    }
/* 141 */           this.requestStatusCode = requestStatusCode;
/* 142 */           this.errorCode = errorCode;
/* 143 */           this.subErrorCode = subErrorCode;
/* 144 */           this.errorType = errorType;
/* 145 */           this.errorMessage = errorMessage;
/* 146 */           this.requestResultBody = requestResultBody;
/* 147 */           this.requestResult = requestResult;
/* 148 */           this.batchRequestResult = batchRequestResult;
/* 149 */           this.connection = connection;
/* 151 */           boolean isLocalException = false;
/* 152 */           if (exception != null) {
/* 153 */               this.exception = exception;
/* 154 */               isLocalException = true;
                    } else {
/* 156 */               this.exception = new FacebookServiceException(this, errorMessage);
                    }
/* 161 */           Category errorCategory = null;
/* 162 */           int messageId = 0;
/* 163 */           boolean shouldNotify = false;
/* 164 */           if (isLocalException) {
/* 165 */               errorCategory = Category.CLIENT;
/* 166 */               messageId = 0;
                    } else {
/* 168 */               if (errorCode == 1 || errorCode == 2) {
/* 169 */                   errorCategory = Category.SERVER;
                        } else if (errorCode == 4 || errorCode == 17) {
/* 171 */                   errorCategory = Category.THROTTLING;
                        } else if (errorCode == 10 || EC_RANGE_PERMISSION.contains(errorCode)) {
/* 173 */                   errorCategory = Category.PERMISSION;
/* 174 */                   messageId = STRING_COM_FACEBOOK_REQUESTERROR_PERMISSIONS;
                        } else if (errorCode == 102 || errorCode == EC_INVALID_TOKEN) {
/* 176 */                   if (subErrorCode == EC_USER_CHECKPOINTED || subErrorCode == EC_UNCONFIRMED_USER) {
/* 177 */                       errorCategory = Category.AUTHENTICATION_RETRY;
/* 178 */                       messageId = STRING_COM_FACEBOOK_REQUESTERROR_WEB_LOGIN;
/* 179 */                       shouldNotify = true;
                            } else {
/* 181 */                       errorCategory = Category.AUTHENTICATION_REOPEN_SESSION;
/* 183 */                       if (subErrorCode == EC_APP_NOT_INSTALLED || subErrorCode == EC_EXPIRED) {
/* 184 */                           messageId = STRING_COM_FACEBOOK_REQUESTERROR_RELOGIN;
                                } else if (subErrorCode == EC_PASSWORD_CHANGED) {
/* 186 */                           messageId = STRING_COM_FACEBOOK_REQUESTERROR_PASSWORD_CHANGED;
                                } else {
/* 188 */                           messageId = STRING_COM_FACEBOOK_REQUESTERROR_RECONNECT;
/* 189 */                           shouldNotify = true;
                                }
                            }
                        }
/* 194 */               if (errorCategory == null) {
/* 195 */                   if (HTTP_RANGE_CLIENT_ERROR.contains(requestStatusCode)) {
/* 196 */                       errorCategory = Category.BAD_REQUEST;
                            } else if (HTTP_RANGE_SERVER_ERROR.contains(requestStatusCode)) {
/* 198 */                       errorCategory = Category.SERVER;
                            } else {
/* 200 */                       errorCategory = Category.OTHER;
                            }
                        }
                    }
/* 205 */           this.category = errorCategory;
/* 206 */           this.userActionMessageId = messageId;
/* 207 */           this.shouldNotifyUser = shouldNotify;
                }

/* 212 */       private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection) {
/* 213 */           this(requestStatusCode, errorCode, subErrorCode, errorType, errorMessage, requestResultBody, requestResult, batchRequestResult, connection, null);
                }

/* 217 */       FacebookRequestError(HttpURLConnection connection, Exception exception) {
/* 218 */           this(-1, -1, -1, null, null, null, null, null, connection, exception instanceof FacebookException ? (FacebookException) exception : new FacebookException(exception));
                }

/* 224 */       public FacebookRequestError(int errorCode, String errorType, String errorMessage) {
/* 225 */           this(-1, errorCode, -1, errorType, errorMessage, null, null, null, null, null);
                }

/* 235 */       public int getUserActionMessageId() {
/* 236 */           return this.userActionMessageId;
                }

/* 246 */       public boolean shouldNotifyUser() {
/* 247 */           return this.shouldNotifyUser;
                }

/* 257 */       public Category getCategory() {
/* 258 */           return this.category;
                }

/* 266 */       public int getRequestStatusCode() {
/* 267 */           return this.requestStatusCode;
                }

/* 275 */       public int getErrorCode() {
/* 276 */           return this.errorCode;
                }

/* 284 */       public int getSubErrorCode() {
/* 285 */           return this.subErrorCode;
                }

/* 295 */       public String getErrorType() {
/* 296 */           return this.errorType;
                }

/* 304 */       public String getErrorMessage() {
                    return this.errorMessage != null ? this.errorMessage : this.exception.getLocalizedMessage();
                }

/* 317 */       public JSONObject getRequestResultBody() {
/* 318 */           return this.requestResultBody;
                }

/* 329 */       public JSONObject getRequestResult() {
/* 330 */           return this.requestResult;
                }

/* 342 */       public Object getBatchRequestResult() {
/* 343 */           return this.batchRequestResult;
                }

/* 351 */       public HttpURLConnection getConnection() {
/* 352 */           return this.connection;
                }

/* 360 */       public FacebookException getException() {
/* 361 */           return this.exception;
                }

/* 365 */       public String toString() {
/* 366 */           return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", errorType: " + this.errorType + ", errorMessage: " + this.errorMessage + "}";
                }

/* 371 */       static FacebookRequestError checkResponseAndCreateError(JSONObject singleResult, Object batchResult, HttpURLConnection connection) {
                    try {
/* 372 */               if (singleResult.has("code")) {
/* 373 */                   int responseCode = singleResult.getInt("code");
/* 374 */                   Object body = Utility.getStringPropertyAsJSON(singleResult, "body", Response.NON_JSON_RESPONSE_PROPERTY);
/* 377 */                   if (body != null && (body instanceof JSONObject)) {
/* 378 */                       JSONObject jsonBody = (JSONObject) body;
/* 381 */                       String errorType = null;
/* 382 */                       String errorMessage = null;
/* 383 */                       int errorCode = -1;
/* 384 */                       int errorSubCode = -1;
/* 386 */                       boolean hasError = false;
/* 387 */                       if (jsonBody.has(ERROR_KEY)) {
/* 389 */                           JSONObject error = (JSONObject) Utility.getStringPropertyAsJSON(jsonBody, ERROR_KEY, null);
/* 391 */                           errorType = error.optString("type", null);
/* 392 */                           errorMessage = error.optString("message", null);
/* 393 */                           errorCode = error.optInt("code", -1);
/* 394 */                           errorSubCode = error.optInt(ERROR_SUB_CODE_KEY, -1);
/* 395 */                           hasError = true;
                                } else if (jsonBody.has("error_code") || jsonBody.has(ERROR_MSG_KEY) || jsonBody.has("error_reason")) {
/* 398 */                           errorType = jsonBody.optString("error_reason", null);
/* 399 */                           errorMessage = jsonBody.optString(ERROR_MSG_KEY, null);
/* 400 */                           errorCode = jsonBody.optInt("error_code", -1);
/* 401 */                           errorSubCode = jsonBody.optInt(ERROR_SUB_CODE_KEY, -1);
/* 402 */                           hasError = true;
                                }
/* 405 */                       if (hasError) {
/* 406 */                           return new FacebookRequestError(responseCode, errorCode, errorSubCode, errorType, errorMessage, jsonBody, singleResult, batchResult, connection);
                                }
                            }
/* 412 */                   if (!HTTP_RANGE_SUCCESS.contains(responseCode)) {
/* 413 */                       return new FacebookRequestError(responseCode, -1, -1, null, null, singleResult.has("body") ? (JSONObject) Utility.getStringPropertyAsJSON(singleResult, "body", Response.NON_JSON_RESPONSE_PROPERTY) : null, singleResult, batchResult, connection);
                            }
                        }
                    } catch (JSONException e) {
                    }
/* 424 */           return null;
                }
            }
