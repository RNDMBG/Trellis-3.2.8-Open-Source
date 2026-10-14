            package com.fasterxml.jackson.core;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class JsonProcessingException extends IOException {
                static final long serialVersionUID = 123;
                protected JsonLocation _location;

/* 26 */        protected JsonProcessingException(String str, JsonLocation jsonLocation, Throwable th) {
/* 27 */            super(str);
/* 28 */            if (th != null) {
/* 29 */                initCause(th);
                    }
/* 31 */            this._location = jsonLocation;
                }

/* 35 */        protected JsonProcessingException(String str) {
/* 36 */            super(str);
                }

/* 40 */        protected JsonProcessingException(String str, JsonLocation jsonLocation) {
/* 41 */            this(str, jsonLocation, null);
                }

/* 45 */        protected JsonProcessingException(String str, Throwable th) {
/* 46 */            this(str, null, th);
                }

/* 50 */        protected JsonProcessingException(Throwable th) {
/* 51 */            this(null, null, th);
                }

/* 54 */        public JsonLocation getLocation() {
/* 55 */            return this._location;
                }

/* 72 */        public String getOriginalMessage() {
/* 73 */            return super.getMessage();
                }

/* 87 */        protected String getMessageSuffix() {
/* 88 */            return null;
                }

                @Override // java.lang.Throwable
/* 102 */       public String getMessage() {
/* 103 */           String message = super.getMessage();
/* 104 */           if (message == null) {
/* 105 */               message = "N/A";
                    }
/* 107 */           JsonLocation location = getLocation();
/* 108 */           String messageSuffix = getMessageSuffix();
/* 110 */           if (location != null || messageSuffix != null) {
/* 111 */               StringBuilder sb = new StringBuilder(100);
/* 112 */               sb.append(message);
/* 113 */               if (messageSuffix != null) {
/* 114 */                   sb.append(messageSuffix);
                        }
/* 116 */               if (location != null) {
/* 117 */                   sb.append('\n');
/* 118 */                   sb.append(" at ");
/* 119 */                   sb.append(location.toString());
                        }
/* 121 */               return sb.toString();
                    }
/* 123 */           return message;
                }

                @Override // java.lang.Throwable
/* 127 */       public String toString() {
/* 128 */           return getClass().getName() + ": " + getMessage();
                }
            }
