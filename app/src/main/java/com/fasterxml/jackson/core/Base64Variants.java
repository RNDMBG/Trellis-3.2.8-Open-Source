            package com.fasterxml.jackson.core;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class Base64Variants {
                public static final Base64Variant MODIFIED_FOR_URL;
                static final String STD_BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
                public static final Base64Variant MIME = new Base64Variant("MIME", STD_BASE64_ALPHABET, true, '=', 76);
                public static final Base64Variant MIME_NO_LINEFEEDS = new Base64Variant(MIME, "MIME-NO-LINEFEEDS", Integer.MAX_VALUE);
                public static final Base64Variant PEM = new Base64Variant(MIME, "PEM", true, '=', 64);

                static {
/* 67 */            StringBuffer stringBuffer = new StringBuffer(STD_BASE64_ALPHABET);
/* 69 */            stringBuffer.setCharAt(stringBuffer.indexOf("+"), '-');
/* 70 */            stringBuffer.setCharAt(stringBuffer.indexOf("/"), '_');
/* 74 */            MODIFIED_FOR_URL = new Base64Variant("MODIFIED-FOR-URL", stringBuffer.toString(), false, (char) 0, Integer.MAX_VALUE);
                }

/* 83 */        public static Base64Variant getDefaultVariant() {
/* 84 */            return MIME_NO_LINEFEEDS;
                }

/* 91 */        public static Base64Variant valueOf(String str) throws IllegalArgumentException {
                    String str2;
/* 92 */            if (MIME._name.equals(str)) {
/* 93 */                return MIME;
                    }
/* 95 */            if (MIME_NO_LINEFEEDS._name.equals(str)) {
/* 96 */                return MIME_NO_LINEFEEDS;
                    }
/* 98 */            if (PEM._name.equals(str)) {
/* 99 */                return PEM;
                    }
/* 101 */           if (MODIFIED_FOR_URL._name.equals(str)) {
/* 102 */               return MODIFIED_FOR_URL;
                    }
/* 104 */           if (str == null) {
/* 105 */               str2 = "<null>";
                    } else {
/* 107 */               str2 = "'" + str + "'";
                    }
/* 109 */           throw new IllegalArgumentException("No Base64Variant with name " + str2);
                }
            }
