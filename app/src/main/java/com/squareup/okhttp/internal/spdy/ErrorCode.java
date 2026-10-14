            package com.squareup.okhttp.internal.spdy;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public enum ErrorCode {
                NO_ERROR(0, -1, 0),
                PROTOCOL_ERROR(1, 1, 1),
                INVALID_STREAM(1, 2, -1),
                UNSUPPORTED_VERSION(1, 4, -1),
                STREAM_IN_USE(1, 8, -1),
                STREAM_ALREADY_CLOSED(1, 9, -1),
                INTERNAL_ERROR(2, 6, 2),
                FLOW_CONTROL_ERROR(3, 7, -1),
                STREAM_CLOSED(5, -1, -1),
                FRAME_TOO_LARGE(6, 11, -1),
                REFUSED_STREAM(7, 3, -1),
                CANCEL(8, 5, -1),
                COMPRESSION_ERROR(9, -1, -1),
                CONNECT_ERROR(10, -1, -1),
                ENHANCE_YOUR_CALM(11, -1, -1),
                INADEQUATE_SECURITY(12, -1, -1),
                INVALID_CREDENTIALS(-1, 10, -1);

                public final int httpCode;
                public final int spdyGoAwayCode;
                public final int spdyRstCode;

/* 62 */        ErrorCode(int httpCode, int spdyRstCode, int spdyGoAwayCode) {
/* 64 */            this.httpCode = httpCode;
/* 65 */            this.spdyRstCode = spdyRstCode;
/* 66 */            this.spdyGoAwayCode = spdyGoAwayCode;
                }

/* 69 */        public static ErrorCode fromSpdy3Rst(int code) {
/* 70 */            ErrorCode[] arr$ = values();
                    for (ErrorCode errorCode : arr$) {
/* 71 */                if (errorCode.spdyRstCode == code) {
/* 73 */                    return errorCode;
                        }
                    }
/* 73 */            return null;
                }

/* 76 */        public static ErrorCode fromHttp2(int code) {
/* 77 */            ErrorCode[] arr$ = values();
                    for (ErrorCode errorCode : arr$) {
/* 78 */                if (errorCode.httpCode == code) {
/* 80 */                    return errorCode;
                        }
                    }
/* 80 */            return null;
                }

/* 83 */        public static ErrorCode fromSpdyGoAway(int code) {
/* 84 */            ErrorCode[] arr$ = values();
                    for (ErrorCode errorCode : arr$) {
/* 85 */                if (errorCode.spdyGoAwayCode == code) {
/* 87 */                    return errorCode;
                        }
                    }
/* 87 */            return null;
                }
            }
