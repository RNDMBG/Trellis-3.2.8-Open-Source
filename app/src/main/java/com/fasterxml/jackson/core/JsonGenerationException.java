            package com.fasterxml.jackson.core;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class JsonGenerationException extends JsonProcessingException {
                private static final long serialVersionUID = 123;

/* 19 */        public JsonGenerationException(Throwable th) {
/* 20 */            super(th);
                }

/* 24 */        public JsonGenerationException(String str) {
/* 25 */            super(str, (JsonLocation) null);
                }

/* 29 */        public JsonGenerationException(String str, Throwable th) {
/* 30 */            super(str, (JsonLocation) null, th);
                }
            }
