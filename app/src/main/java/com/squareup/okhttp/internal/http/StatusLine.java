            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Protocol;
            import com.squareup.okhttp.Response;
            import java.io.IOException;
            import java.net.ProtocolException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public final class StatusLine {
                public static final int HTTP_CONTINUE = 100;
                public static final int HTTP_PERM_REDIRECT = 308;
                public static final int HTTP_TEMP_REDIRECT = 307;
                public final int code;
                public final String message;
                public final Protocol protocol;

/* 18 */        public StatusLine(Protocol protocol, int code, String message) {
/* 20 */            this.protocol = protocol;
/* 21 */            this.code = code;
/* 22 */            this.message = message;
                }

/* 25 */        public static StatusLine get(Response response) {
/* 26 */            return new StatusLine(response.protocol(), response.code(), response.message());
                }

/* 35 */        public static StatusLine parse(String statusLine) throws IOException {
                    Protocol protocol;
                    int codeStart;
/* 36 */            if (statusLine.startsWith("HTTP/1.")) {
/* 37 */                if (statusLine.length() < 9 || statusLine.charAt(8) != ' ') {
/* 38 */                    throw new ProtocolException("Unexpected status line: " + statusLine);
                        }
/* 40 */                int httpMinorVersion = statusLine.charAt(7) - '0';
/* 41 */                codeStart = 9;
/* 42 */                if (httpMinorVersion == 0) {
/* 43 */                    protocol = Protocol.HTTP_1_0;
                        } else if (httpMinorVersion == 1) {
/* 45 */                    protocol = Protocol.HTTP_1_1;
                        } else {
/* 47 */                    throw new ProtocolException("Unexpected status line: " + statusLine);
                        }
                    } else if (statusLine.startsWith("ICY ")) {
/* 51 */                protocol = Protocol.HTTP_1_0;
/* 52 */                codeStart = 4;
                    } else {
/* 54 */                throw new ProtocolException("Unexpected status line: " + statusLine);
                    }
/* 58 */            if (statusLine.length() < codeStart + 3) {
/* 59 */                throw new ProtocolException("Unexpected status line: " + statusLine);
                    }
                    try {
/* 63 */                int code = Integer.parseInt(statusLine.substring(codeStart, codeStart + 3));
/* 70 */                String message = "";
/* 71 */                if (statusLine.length() > codeStart + 3) {
/* 72 */                    if (statusLine.charAt(codeStart + 3) != ' ') {
/* 73 */                        throw new ProtocolException("Unexpected status line: " + statusLine);
                            }
/* 75 */                    message = statusLine.substring(codeStart + 4);
                        }
/* 78 */                return new StatusLine(protocol, code, message);
                    } catch (NumberFormatException e) {
/* 65 */                throw new ProtocolException("Unexpected status line: " + statusLine);
                    }
                }

/* 81 */        public String toString() {
/* 82 */            StringBuilder result = new StringBuilder();
/* 83 */            result.append(this.protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1");
/* 84 */            result.append(' ').append(this.code);
/* 85 */            if (this.message != null) {
/* 86 */                result.append(' ').append(this.message);
                    }
/* 88 */            return result.toString();
                }
            }
