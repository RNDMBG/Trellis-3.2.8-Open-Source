            package com.fasterxml.jackson.core;

            import java.io.IOException;
            
            
    public class JsonProcessingException extends IOException {
                static final long serialVersionUID = 123;
                protected JsonLocation _location;

        protected JsonProcessingException(String str, JsonLocation jsonLocation, Throwable th) {
            super(str);
            if (th != null) {
                initCause(th);
                    }
            this._location = jsonLocation;
                }

        protected JsonProcessingException(String str) {
            super(str);
                }

        protected JsonProcessingException(String str, JsonLocation jsonLocation) {
            this(str, jsonLocation, null);
                }

        protected JsonProcessingException(String str, Throwable th) {
            this(str, null, th);
                }

        protected JsonProcessingException(Throwable th) {
            this(null, null, th);
                }

        public JsonLocation getLocation() {
            return this._location;
                }

        public String getOriginalMessage() {
            return super.getMessage();
                }

        protected String getMessageSuffix() {
            return null;
                }

                @Override 
       public String getMessage() {
           String message = super.getMessage();
           if (message == null) {
               message = "N/A";
                    }
           JsonLocation location = getLocation();
           String messageSuffix = getMessageSuffix();
           if (location != null || messageSuffix != null) {
               StringBuilder sb = new StringBuilder(100);
               sb.append(message);
               if (messageSuffix != null) {
                   sb.append(messageSuffix);
                        }
               if (location != null) {
                   sb.append('\n');
                   sb.append(" at ");
                   sb.append(location.toString());
                        }
               return sb.toString();
                    }
           return message;
                }

                @Override 
       public String toString() {
           return getClass().getName() + ": " + getMessage();
                }
            }
