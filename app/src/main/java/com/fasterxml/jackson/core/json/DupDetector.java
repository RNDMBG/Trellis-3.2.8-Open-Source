            package com.fasterxml.jackson.core.json;

            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.JsonLocation;
            import com.fasterxml.jackson.core.JsonParseException;
            import com.fasterxml.jackson.core.JsonParser;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class DupDetector {
                protected String _firstName;
                protected String _secondName;
                protected HashSet<String> _seen;
                protected final Object _source;

/* 35 */        private DupDetector(Object obj) {
/* 37 */            this._source = obj;
                }

/* 40 */        public static DupDetector rootDetector(JsonParser jsonParser) {
/* 41 */            return new DupDetector(jsonParser);
                }

/* 44 */        public static DupDetector rootDetector(JsonGenerator jsonGenerator) {
/* 45 */            return new DupDetector(jsonGenerator);
                }

/* 48 */        public DupDetector child() {
/* 49 */            return new DupDetector(this._source);
                }

/* 52 */        public void reset() {
/* 53 */            this._firstName = null;
/* 54 */            this._secondName = null;
/* 55 */            this._seen = null;
                }

/* 59 */        public JsonLocation findLocation() {
/* 60 */            if (this._source instanceof JsonParser) {
/* 61 */                return ((JsonParser) this._source).getCurrentLocation();
                    }
                    return null;
                }

/* 68 */        public boolean isDup(String str) throws JsonParseException {
/* 69 */            if (this._firstName == null) {
/* 70 */                this._firstName = str;
                        return false;
                    }
/* 73 */            if (str.equals(this._firstName)) {
                        return true;
                    }
/* 76 */            if (this._secondName == null) {
/* 77 */                this._secondName = str;
                        return false;
                    }
/* 80 */            if (str.equals(this._secondName)) {
                        return true;
                    }
/* 83 */            if (this._seen == null) {
/* 84 */                this._seen = new HashSet<>(16);
/* 85 */                this._seen.add(this._firstName);
/* 86 */                this._seen.add(this._secondName);
                    }
                    return !this._seen.add(str);
                }
            }
