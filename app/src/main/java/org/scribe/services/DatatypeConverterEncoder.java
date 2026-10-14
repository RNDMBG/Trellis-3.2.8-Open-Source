            package org.scribe.services;

            
            
     public class DatatypeConverterEncoder extends Base64Encoder {
                @Override 
         public String encode(byte[] bytes) {
            return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP);
                }

                @Override 
        public String getType() {
            return "DatatypeConverter";
                }
            }
