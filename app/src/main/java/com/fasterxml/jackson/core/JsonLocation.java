            package com.fasterxml.jackson.core;

            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class JsonLocation implements Serializable {

                /* JADX INFO: renamed from: NA */
                public static final JsonLocation f526NA = new JsonLocation("N/A", -1, -1, -1, -1);
                private static final long serialVersionUID = 1;
                final int _columnNr;
                final int _lineNr;
                final transient Object _sourceRef;
                final long _totalBytes;
                final long _totalChars;

/* 43 */        public JsonLocation(Object obj, long j, int i, int i2) {
/* 44 */            this(obj, -1L, j, i, i2);
                }

/* 48 */        public JsonLocation(Object obj, long j, long j2, int i, int i2) {
/* 50 */            this._sourceRef = obj;
/* 51 */            this._totalBytes = j;
/* 52 */            this._totalChars = j2;
/* 53 */            this._lineNr = i;
/* 54 */            this._columnNr = i2;
                }

/* 64 */        public Object getSourceRef() {
/* 65 */            return this._sourceRef;
                }

/* 69 */        public int getLineNr() {
/* 70 */            return this._lineNr;
                }

/* 74 */        public int getColumnNr() {
/* 75 */            return this._columnNr;
                }

/* 80 */        public long getCharOffset() {
/* 81 */            return this._totalChars;
                }

/* 88 */        public long getByteOffset() {
/* 89 */            return this._totalBytes;
                }

/* 94 */        public String toString() {
/* 95 */            StringBuilder sb = new StringBuilder(80);
/* 96 */            sb.append("[Source: ");
/* 97 */            if (this._sourceRef == null) {
/* 98 */                sb.append("UNKNOWN");
                    } else {
/* 100 */               sb.append(this._sourceRef.toString());
                    }
/* 102 */           sb.append("; line: ");
/* 103 */           sb.append(this._lineNr);
/* 104 */           sb.append(", column: ");
/* 105 */           sb.append(this._columnNr);
/* 106 */           sb.append(']');
/* 107 */           return sb.toString();
                }

/* 112 */       public int hashCode() {
/* 117 */           return ((((this._sourceRef == null ? 1 : this._sourceRef.hashCode()) ^ this._lineNr) + this._columnNr) ^ ((int) this._totalChars)) + ((int) this._totalBytes);
                }

/* 123 */       public boolean equals(Object obj) {
/* 124 */           if (obj == this) {
                        return true;
                    }
/* 125 */           if (obj == null || !(obj instanceof JsonLocation)) {
                        return false;
                    }
/* 127 */           JsonLocation jsonLocation = (JsonLocation) obj;
/* 129 */           if (this._sourceRef == null) {
/* 130 */               if (jsonLocation._sourceRef != null) {
                            return false;
                        }
                    } else if (!this._sourceRef.equals(jsonLocation._sourceRef)) {
                        return false;
                    }
/* 133 */           return this._lineNr == jsonLocation._lineNr && this._columnNr == jsonLocation._columnNr && this._totalChars == jsonLocation._totalChars && getByteOffset() == jsonLocation.getByteOffset();
                }
            }
