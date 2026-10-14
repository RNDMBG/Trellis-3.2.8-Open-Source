            package com.fasterxml.jackson.core;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class JsonParseException extends JsonProcessingException {
                private static final long serialVersionUID = 1;

/* 19 */        public JsonParseException(String str, JsonLocation jsonLocation) {
/* 20 */            super(str, jsonLocation);
                }

/* 24 */        public JsonParseException(String str, JsonLocation jsonLocation, Throwable th) {
/* 25 */            super(str, jsonLocation, th);
                }
            }
