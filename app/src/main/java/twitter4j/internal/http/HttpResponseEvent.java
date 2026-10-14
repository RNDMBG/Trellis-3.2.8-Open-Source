            package twitter4j.internal.http;

            import twitter4j.TwitterException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public final class HttpResponseEvent {
                private HttpRequest request;
                private HttpResponse response;
                private TwitterException twitterException;

/* 32 */        HttpResponseEvent(HttpRequest request, HttpResponse response, TwitterException te) {
/* 34 */            this.request = request;
/* 35 */            this.response = response;
/* 36 */            this.twitterException = te;
                }

/* 44 */        public HttpRequest getRequest() {
/* 45 */            return this.request;
                }

/* 53 */        public HttpResponse getResponse() {
/* 54 */            return this.response;
                }

/* 62 */        public TwitterException getTwitterException() {
/* 63 */            return this.twitterException;
                }

/* 66 */        public boolean isAuthenticated() {
/* 67 */            return this.request.getAuthorization().isEnabled();
                }

/* 71 */        public boolean equals(Object o) {
/* 72 */            if (this == o) {
                        return true;
                    }
/* 73 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 75 */            HttpResponseEvent that = (HttpResponseEvent) o;
/* 77 */            if (this.request == null ? that.request != null : !this.request.equals(that.request)) {
                        return false;
                    }
/* 79 */            if (this.response != null) {
                        if (this.response.equals(that.response)) {
                            return true;
                        }
                    } else if (that.response == null) {
                        return true;
                    }
                    return false;
                }

/* 86 */        public int hashCode() {
/* 87 */            int result = this.request != null ? this.request.hashCode() : 0;
/* 88 */            return (result * 31) + (this.response != null ? this.response.hashCode() : 0);
                }

/* 93 */        public String toString() {
/* 94 */            return "HttpResponseEvent{request=" + this.request + ", response=" + this.response + '}';
                }
            }
