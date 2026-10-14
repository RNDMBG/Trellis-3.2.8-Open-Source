            package org.scribe.model;

            import java.io.OutputStream;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class OAuthConfig {
                private final String apiKey;
                private final String apiSecret;
                private final String callback;
                private final OutputStream debugStream;
                private final String scope;
                private final SignatureType signatureType;

/* 20 */        public OAuthConfig(String key, String secret) {
/* 21 */            this(key, secret, null, null, null, null);
                }

/* 24 */        public OAuthConfig(String key, String secret, String callback, SignatureType type, String scope, OutputStream stream) {
/* 26 */            this.apiKey = key;
/* 27 */            this.apiSecret = secret;
/* 28 */            this.callback = callback;
/* 29 */            this.signatureType = type;
/* 30 */            this.scope = scope;
/* 31 */            this.debugStream = stream;
                }

/* 35 */        public String getApiKey() {
/* 36 */            return this.apiKey;
                }

/* 40 */        public String getApiSecret() {
/* 41 */            return this.apiSecret;
                }

/* 45 */        public String getCallback() {
/* 46 */            return this.callback;
                }

/* 50 */        public SignatureType getSignatureType() {
/* 51 */            return this.signatureType;
                }

/* 55 */        public String getScope() {
/* 56 */            return this.scope;
                }

/* 60 */        public boolean hasScope() {
                    return this.scope != null;
                }

/* 65 */        public void log(String message) {
/* 66 */            if (this.debugStream != null) {
                        try {
/* 71 */                    this.debugStream.write((message + IOUtils.LINE_SEPARATOR_UNIX).getBytes("UTF8"));
                        } catch (Exception e) {
/* 75 */                    throw new RuntimeException("there were problems while writting to the debug stream", e);
                        }
                    }
                }
            }
