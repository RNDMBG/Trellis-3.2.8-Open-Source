            package com.facebook;

            import com.facebook.internal.Utility;
            import com.googlecode.javacv.cpp.avutil;
            import java.net.HttpURLConnection;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
    public final class FacebookRequestError {
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

        private static class Range {
                    private final int end;
                    private final int start;

             Range(int x0, int x1, Object x2) {
                this(x0, x1);
                    }

            private Range(int start, int end) {
                this.start = start;
                this.end = end;
                    }

            boolean contains(int value) {
                        return this.start <= value && value <= this.end;
                    }
                }

        public static void initRequestErrorPermissions(int permissions, int relogin, int webLogin, int passwordChanged, int reconnect) {
            STRING_COM_FACEBOOK_REQUESTERROR_PERMISSIONS = permissions;
            STRING_COM_FACEBOOK_REQUESTERROR_RELOGIN = relogin;
            STRING_COM_FACEBOOK_REQUESTERROR_WEB_LOGIN = webLogin;
            STRING_COM_FACEBOOK_REQUESTERROR_PASSWORD_CHANGED = passwordChanged;
            STRING_COM_FACEBOOK_REQUESTERROR_RECONNECT = reconnect;
                }

       private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection, FacebookException exception) {
           if (STRING_COM_FACEBOOK_REQUESTERROR_PASSWORD_CHANGED == -1 || STRING_COM_FACEBOOK_REQUESTERROR_RELOGIN == -1 || STRING_COM_FACEBOOK_REQUESTERROR_WEB_LOGIN == -1 || STRING_COM_FACEBOOK_REQUESTERROR_PERMISSIONS == -1 || STRING_COM_FACEBOOK_REQUESTERROR_RECONNECT == -1) {
               throw new IllegalStateException("Error strings not initialized, is the application setup wrong?");
                    }
           this.requestStatusCode = requestStatusCode;
           this.errorCode = errorCode;
           this.subErrorCode = subErrorCode;
           this.errorType = errorType;
           this.errorMessage = errorMessage;
           this.requestResultBody = requestResultBody;
           this.requestResult = requestResult;
           this.batchRequestResult = batchRequestResult;
           this.connection = connection;
           boolean isLocalException = false;
           if (exception != null) {
               this.exception = exception;
               isLocalException = true;
                    } else {
               this.exception = new FacebookServiceException(this, errorMessage);
                    }
           Category errorCategory = null;
           int messageId = 0;
           boolean shouldNotify = false;
           if (isLocalException) {
               errorCategory = Category.CLIENT;
               messageId = 0;
                    } else {
               if (errorCode == 1 || errorCode == 2) {
                   errorCategory = Category.SERVER;
                        } else if (errorCode == 4 || errorCode == 17) {
                   errorCategory = Category.THROTTLING;
                        } else if (errorCode == 10 || EC_RANGE_PERMISSION.contains(errorCode)) {
                   errorCategory = Category.PERMISSION;
                   messageId = STRING_COM_FACEBOOK_REQUESTERROR_PERMISSIONS;
                        } else if (errorCode == 102 || errorCode == EC_INVALID_TOKEN) {
                   if (subErrorCode == EC_USER_CHECKPOINTED || subErrorCode == EC_UNCONFIRMED_USER) {
                       errorCategory = Category.AUTHENTICATION_RETRY;
                       messageId = STRING_COM_FACEBOOK_REQUESTERROR_WEB_LOGIN;
                       shouldNotify = true;
                            } else {
                       errorCategory = Category.AUTHENTICATION_REOPEN_SESSION;
                       if (subErrorCode == EC_APP_NOT_INSTALLED || subErrorCode == EC_EXPIRED) {
                           messageId = STRING_COM_FACEBOOK_REQUESTERROR_RELOGIN;
                                } else if (subErrorCode == EC_PASSWORD_CHANGED) {
                           messageId = STRING_COM_FACEBOOK_REQUESTERROR_PASSWORD_CHANGED;
                                } else {
                           messageId = STRING_COM_FACEBOOK_REQUESTERROR_RECONNECT;
                           shouldNotify = true;
                                }
                            }
                        }
               if (errorCategory == null) {
                   if (HTTP_RANGE_CLIENT_ERROR.contains(requestStatusCode)) {
                       errorCategory = Category.BAD_REQUEST;
                            } else if (HTTP_RANGE_SERVER_ERROR.contains(requestStatusCode)) {
                       errorCategory = Category.SERVER;
                            } else {
                       errorCategory = Category.OTHER;
                            }
                        }
                    }
           this.category = errorCategory;
           this.userActionMessageId = messageId;
           this.shouldNotifyUser = shouldNotify;
                }

       private FacebookRequestError(int requestStatusCode, int errorCode, int subErrorCode, String errorType, String errorMessage, JSONObject requestResultBody, JSONObject requestResult, Object batchRequestResult, HttpURLConnection connection) {
           this(requestStatusCode, errorCode, subErrorCode, errorType, errorMessage, requestResultBody, requestResult, batchRequestResult, connection, null);
                }

       FacebookRequestError(HttpURLConnection connection, Exception exception) {
           this(-1, -1, -1, null, null, null, null, null, connection, exception instanceof FacebookException ? (FacebookException) exception : new FacebookException(exception));
                }

       public FacebookRequestError(int errorCode, String errorType, String errorMessage) {
           this(-1, errorCode, -1, errorType, errorMessage, null, null, null, null, null);
                }

       public int getUserActionMessageId() {
           return this.userActionMessageId;
                }

       public boolean shouldNotifyUser() {
           return this.shouldNotifyUser;
                }

       public Category getCategory() {
           return this.category;
                }

       public int getRequestStatusCode() {
           return this.requestStatusCode;
                }

       public int getErrorCode() {
           return this.errorCode;
                }

       public int getSubErrorCode() {
           return this.subErrorCode;
                }

       public String getErrorType() {
           return this.errorType;
                }

       public String getErrorMessage() {
                    return this.errorMessage != null ? this.errorMessage : this.exception.getLocalizedMessage();
                }

       public JSONObject getRequestResultBody() {
           return this.requestResultBody;
                }

       public JSONObject getRequestResult() {
           return this.requestResult;
                }

       public Object getBatchRequestResult() {
           return this.batchRequestResult;
                }

       public HttpURLConnection getConnection() {
           return this.connection;
                }

       public FacebookException getException() {
           return this.exception;
                }

       public String toString() {
           return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", errorType: " + this.errorType + ", errorMessage: " + this.errorMessage + "}";
                }

       static FacebookRequestError checkResponseAndCreateError(JSONObject singleResult, Object batchResult, HttpURLConnection connection) {
                    try {
               if (singleResult.has("code")) {
                   int responseCode = singleResult.getInt("code");
                   Object body = Utility.getStringPropertyAsJSON(singleResult, "body", Response.NON_JSON_RESPONSE_PROPERTY);
                   if (body != null && (body instanceof JSONObject)) {
                       JSONObject jsonBody = (JSONObject) body;
                       String errorType = null;
                       String errorMessage = null;
                       int errorCode = -1;
                       int errorSubCode = -1;
                       boolean hasError = false;
                       if (jsonBody.has(ERROR_KEY)) {
                           JSONObject error = (JSONObject) Utility.getStringPropertyAsJSON(jsonBody, ERROR_KEY, null);
                           errorType = error.optString("type", null);
                           errorMessage = error.optString("message", null);
                           errorCode = error.optInt("code", -1);
                           errorSubCode = error.optInt(ERROR_SUB_CODE_KEY, -1);
                           hasError = true;
                                } else if (jsonBody.has("error_code") || jsonBody.has(ERROR_MSG_KEY) || jsonBody.has("error_reason")) {
                           errorType = jsonBody.optString("error_reason", null);
                           errorMessage = jsonBody.optString(ERROR_MSG_KEY, null);
                           errorCode = jsonBody.optInt("error_code", -1);
                           errorSubCode = jsonBody.optInt(ERROR_SUB_CODE_KEY, -1);
                           hasError = true;
                                }
                       if (hasError) {
                           return new FacebookRequestError(responseCode, errorCode, errorSubCode, errorType, errorMessage, jsonBody, singleResult, batchResult, connection);
                                }
                            }
                   if (!HTTP_RANGE_SUCCESS.contains(responseCode)) {
                       return new FacebookRequestError(responseCode, -1, -1, null, null, singleResult.has("body") ? (JSONObject) Utility.getStringPropertyAsJSON(singleResult, "body", Response.NON_JSON_RESPONSE_PROPERTY) : null, singleResult, batchResult, connection);
                            }
                        }
                    } catch (JSONException e) {
                    }
           return null;
                }
            }
