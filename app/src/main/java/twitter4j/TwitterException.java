            package twitter4j;

            import java.io.IOException;
            import java.util.List;
            import org.apache.commons.io.IOUtils;
            import org.json.JSONException;
            import org.json.JSONObject;
            import twitter4j.internal.http.HttpResponse;
            import twitter4j.internal.http.HttpResponseCode;
            import twitter4j.internal.json.z_T4JInternalParseUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class TwitterException extends Exception implements TwitterResponse, HttpResponseCode {
                private static final String[] FILTER = {"twitter4j"};
                private static final long serialVersionUID = -2623309261327598087L;
                private int errorCode;
                private String errorMessage;
                private ExceptionDiagnosis exceptionDiagnosis;
                boolean nested;
                private HttpResponse response;
                private int statusCode;

/* 44 */        public TwitterException(String message, Throwable cause) {
/* 45 */            super(message, cause);
/* 37 */            this.statusCode = -1;
/* 38 */            this.errorCode = -1;
/* 40 */            this.exceptionDiagnosis = null;
/* 42 */            this.errorMessage = null;
/* 235 */           this.nested = false;
/* 46 */            decode(message);
                }

/* 49 */        public TwitterException(String message) {
/* 50 */            this(message, (Throwable) null);
                }

/* 54 */        public TwitterException(Exception cause) {
/* 55 */            this(cause.getMessage(), cause);
/* 56 */            if (cause instanceof TwitterException) {
/* 57 */                ((TwitterException) cause).setNested();
                    }
                }

/* 61 */        public TwitterException(String message, HttpResponse res) {
/* 62 */            this(message);
/* 63 */            this.response = res;
/* 64 */            this.statusCode = res.getStatusCode();
                }

/* 67 */        public TwitterException(String message, Exception cause, int statusCode) {
/* 68 */            this(message, cause);
/* 69 */            this.statusCode = statusCode;
                }

                @Override // java.lang.Throwable
/* 76 */        public String getMessage() {
/* 77 */            StringBuilder value = new StringBuilder();
/* 78 */            if (this.errorMessage != null && this.errorCode != -1) {
/* 79 */                value.append("message - ").append(this.errorMessage).append(IOUtils.LINE_SEPARATOR_UNIX);
/* 81 */                value.append("code - ").append(this.errorCode).append(IOUtils.LINE_SEPARATOR_UNIX);
                    } else {
/* 84 */                value.append(super.getMessage());
                    }
/* 86 */            if (this.statusCode != -1) {
/* 87 */                return getCause(this.statusCode) + IOUtils.LINE_SEPARATOR_UNIX + value.toString();
                    }
/* 89 */            return value.toString();
                }

/* 93 */        private void decode(String str) {
/* 94 */            if (str != null && str.startsWith("{")) {
                        try {
/* 96 */                    JSONObject json = new JSONObject(str);
/* 97 */                    if (!json.isNull("errors")) {
/* 98 */                        JSONObject error = json.getJSONArray("errors").getJSONObject(0);
/* 99 */                        this.errorMessage = error.getString("message");
/* 100 */                       this.errorCode = z_T4JInternalParseUtil.getInt("code", error);
                            }
                        } catch (JSONException e) {
                        }
                    }
                }

/* 107 */       public int getStatusCode() {
/* 108 */           return this.statusCode;
                }

/* 111 */       public int getErrorCode() {
/* 112 */           return this.errorCode;
                }

/* 115 */       public String getResponseHeader(String name) {
/* 117 */           if (this.response == null) {
/* 116 */               return null;
                    }
/* 118 */           List<String> header = this.response.getResponseHeaderFields().get(name);
/* 119 */           if (header.size() <= 0) {
/* 116 */               return null;
                    }
/* 120 */           String value = header.get(0);
                    return value;
                }

                @Override // twitter4j.TwitterResponse
/* 143 */       public int getAccessLevel() {
/* 144 */           return z_T4JInternalParseUtil.toAccessLevel(this.response);
                }

/* 160 */       public int getRetryAfter() {
/* 162 */           if (this.statusCode == 400 || this.statusCode != 420) {
/* 161 */               return -1;
                    }
                    try {
/* 169 */               String retryAfterStr = this.response.getResponseHeader("Retry-After");
/* 170 */               if (retryAfterStr == null) {
/* 161 */                   return -1;
                        }
/* 171 */               int retryAfter = Integer.valueOf(retryAfterStr).intValue();
/* 176 */               return retryAfter;
                    } catch (NumberFormatException e) {
/* 161 */               return -1;
                    }
                }

/* 185 */       public boolean isCausedByNetworkIssue() {
/* 186 */           return getCause() instanceof IOException;
                }

/* 208 */       public boolean resourceNotFound() {
                    return this.statusCode == 404;
                }

/* 224 */       public String getExceptionCode() {
/* 225 */           return getExceptionDiagnosis().asHexString();
                }

/* 228 */       private ExceptionDiagnosis getExceptionDiagnosis() {
/* 229 */           if (this.exceptionDiagnosis == null) {
/* 230 */               this.exceptionDiagnosis = new ExceptionDiagnosis(this, FILTER);
                    }
/* 232 */           return this.exceptionDiagnosis;
                }

/* 237 */       void setNested() {
/* 238 */           this.nested = true;
                }

/* 247 */       public String getErrorMessage() {
/* 248 */           return this.errorMessage;
                }

/* 257 */       public boolean isErrorMessageAvailable() {
                    return this.errorMessage != null;
                }

/* 262 */       public boolean equals(Object o) {
/* 263 */           if (this == o) {
                        return true;
                    }
/* 264 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 266 */           TwitterException that = (TwitterException) o;
/* 268 */           if (this.errorCode == that.errorCode && this.nested == that.nested && this.statusCode == that.statusCode) {
/* 271 */               if (this.errorMessage == null ? that.errorMessage != null : !this.errorMessage.equals(that.errorMessage)) {
                            return false;
                        }
/* 272 */               if (this.exceptionDiagnosis == null ? that.exceptionDiagnosis != null : !this.exceptionDiagnosis.equals(that.exceptionDiagnosis)) {
                            return false;
                        }
/* 274 */               if (this.response != null) {
                            if (this.response.equals(that.response)) {
                                return true;
                            }
                        } else if (that.response == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

/* 280 */       public int hashCode() {
/* 281 */           int result = this.statusCode;
/* 286 */           return (((((((((result * 31) + this.errorCode) * 31) + (this.exceptionDiagnosis != null ? this.exceptionDiagnosis.hashCode() : 0)) * 31) + (this.response != null ? this.response.hashCode() : 0)) * 31) + (this.errorMessage != null ? this.errorMessage.hashCode() : 0)) * 31) + (this.nested ? 1 : 0);
                }

                @Override // java.lang.Throwable
/* 291 */       public String toString() {
                    return getMessage() + (this.nested ? "" : "\nRelevant discussions can be found on the Internet at:\n\thttp://www.google.co.jp/search?q=" + getExceptionDiagnosis().getStackLineHashAsHex() + " or\n\thttp://www.google.co.jp/search?q=" + getExceptionDiagnosis().getLineNumberHashAsHex()) + "\nTwitterException{" + (this.nested ? "" : "exceptionCode=[" + getExceptionCode() + "], ") + "statusCode=" + this.statusCode + ", message=" + this.errorMessage + ", code=" + this.errorCode + ", retryAfter=" + getRetryAfter() + ", version=" + Version.getVersion() + '}';
                }

/* 307 */       private static String getCause(int statusCode) {
                    String cause;
                    switch (statusCode) {
                        case 304:
/* 310 */                   cause = "There was no new data to return.";
                            break;
                        case 400:
/* 313 */                   cause = "The request was invalid. An accompanying error message will explain why. This is the status code will be returned during version 1.0 rate limiting(https://dev.twitter.com/pages/rate-limiting). In API v1.1, a request without authentication is considered invalid and you will get this response.";
                            break;
                        case 401:
/* 316 */                   cause = "Authentication credentials (https://dev.twitter.com/pages/auth) were missing or incorrect. Ensure that you have set valid consumer key/secret, access token/secret, and the system clock is in sync.";
                            break;
                        case HttpResponseCode.FORBIDDEN /* 403 */:
/* 319 */                   cause = "The request is understood, but it has been refused. An accompanying error message will explain why. This code is used when requests are being denied due to update limits (https://support.twitter.com/articles/15364-about-twitter-limits-update-api-dm-and-following).";
                            break;
                        case 404:
/* 322 */                   cause = "The URI requested is invalid or the resource requested, such as a user, does not exists. Also returned when the requested format is not supported by the requested method.";
                            break;
                        case 406:
/* 325 */                   cause = "Returned by the Search API when an invalid format is specified in the request.\nReturned by the Streaming API when one or more of the parameters are not suitable for the resource. The track parameter, for example, would throw this error if:\n The track keyword is too long or too short.\n The bounding box specified is invalid.\n No predicates defined for filtered resource, for example, neither track nor follow parameter defined.\n Follow userid cannot be read.";
                            break;
                        case HttpResponseCode.ENHANCE_YOUR_CLAIM /* 420 */:
/* 333 */                   cause = "Returned by the Search and Trends API when you are being rate limited (https://dev.twitter.com/docs/rate-limiting).\nReturned by the Streaming API:\n Too many login attempts in a short period of time.\n Running too many copies of the same application authenticating with the same account name.";
                            break;
                        case HttpResponseCode.UNPROCESSABLE_ENTITY /* 422 */:
/* 338 */                   cause = "Returned when an image uploaded to POST account/update_profile_banner(https://dev.twitter.com/docs/api/1/post/account/update_profile_banner) is unable to be processed.";
                            break;
                        case HttpResponseCode.TOO_MANY_REQUESTS /* 429 */:
/* 341 */                   cause = "Returned in API v1.1 when a request cannot be served due to the application's rate limit having been exhausted for the resource. See Rate Limiting in API v1.1.(https://dev.twitter.com/docs/rate-limiting/1.1)";
                            break;
                        case 500:
/* 344 */                   cause = "Something is broken. Please post to the group (https://dev.twitter.com/docs/support) so the Twitter team can investigate.";
                            break;
                        case HttpResponseCode.BAD_GATEWAY /* 502 */:
/* 347 */                   cause = "Twitter is down or being upgraded.";
                            break;
                        case HttpResponseCode.SERVICE_UNAVAILABLE /* 503 */:
/* 350 */                   cause = "The Twitter servers are up, but overloaded with requests. Try again later.";
                            break;
                        case HttpResponseCode.GATEWAY_TIMEOUT /* 504 */:
/* 353 */                   cause = "The Twitter servers are up, but the request couldn't be serviced due to some failure within our stack. Try again later.";
                            break;
                        default:
/* 356 */                   cause = "";
                            break;
                    }
/* 358 */           return statusCode + ":" + cause;
                }
            }
