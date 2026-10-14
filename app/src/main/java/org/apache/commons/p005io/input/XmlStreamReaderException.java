            package org.apache.commons.p005io.input;

            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 59 */    public class XmlStreamReaderException extends IOException {
                private static final long serialVersionUID = 1;
                private final String bomEncoding;
                private final String contentTypeEncoding;
                private final String contentTypeMime;
                private final String xmlEncoding;
                private final String xmlGuessEncoding;

/* 60 */        public XmlStreamReaderException(String msg, String bomEnc, String xmlGuessEnc, String xmlEnc) {
/* 61 */            this(msg, null, null, bomEnc, xmlGuessEnc, xmlEnc);
                }

/* 78 */        public XmlStreamReaderException(String msg, String ctMime, String ctEnc, String bomEnc, String xmlGuessEnc, String xmlEnc) {
/* 79 */            super(msg);
/* 80 */            this.contentTypeMime = ctMime;
/* 81 */            this.contentTypeEncoding = ctEnc;
/* 82 */            this.bomEncoding = bomEnc;
/* 83 */            this.xmlGuessEncoding = xmlGuessEnc;
/* 84 */            this.xmlEncoding = xmlEnc;
                }

/* 92 */        public String getBomEncoding() {
/* 93 */            return this.bomEncoding;
                }

/* 101 */       public String getXmlGuessEncoding() {
/* 102 */           return this.xmlGuessEncoding;
                }

/* 110 */       public String getXmlEncoding() {
/* 111 */           return this.xmlEncoding;
                }

/* 121 */       public String getContentTypeMime() {
/* 122 */           return this.contentTypeMime;
                }

/* 133 */       public String getContentTypeEncoding() {
/* 134 */           return this.contentTypeEncoding;
                }
            }
