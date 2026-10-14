            package com.fasterxml.jackson.core.json;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.JsonLocation;
            import com.fasterxml.jackson.core.JsonParseException;
            import com.fasterxml.jackson.core.JsonProcessingException;
            import com.fasterxml.jackson.core.JsonStreamContext;
            import com.fasterxml.jackson.core.p003io.CharTypes;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     public final class JsonReadContext extends JsonStreamContext {
                protected JsonReadContext _child = null;
                protected int _columnNr;
                protected String _currentName;
                protected final DupDetector _dups;
                protected int _lineNr;
                protected final JsonReadContext _parent;


/* 51 */        public JsonReadContext(JsonReadContext jsonReadContext, DupDetector dupDetector, int i, int i2, int i3) {
/* 53 */            this._parent = jsonReadContext;
/* 54 */            this._dups = dupDetector;
/* 55 */            this._type = i;
/* 56 */            this._lineNr = i2;
/* 57 */            this._columnNr = i3;
/* 58 */            this._index = -1;
                }

/* 62 */        protected void reset(int i, int i2, int i3) {
/* 63 */            this._type = i;
/* 64 */            this._index = -1;
/* 65 */            this._lineNr = i2;
/* 66 */            this._columnNr = i3;
/* 67 */            this._currentName = null;
/* 68 */            if (this._dups != null) {
/* 69 */                this._dups.reset();
                    }
                }

                @Deprecated
/* 82 */        public static JsonReadContext createRootContext(int i, int i2) {
/* 83 */            return createRootContext(i, i2, null);
                }

/* 88 */        public static JsonReadContext createRootContext(int i, int i2, DupDetector dupDetector) {
/* 89 */            return new JsonReadContext(null, dupDetector, 0, i, i2);
                }

                @Deprecated
/* 93 */        public static JsonReadContext createRootContext() {
/* 94 */            return createRootContext(null);
                }

/* 97 */        public static JsonReadContext createRootContext(DupDetector dupDetector) {
/* 98 */            return new JsonReadContext(null, dupDetector, 0, 1, 0);
                }

/* 102 */       public JsonReadContext createChildArrayContext(int i, int i2) {
/* 103 */           JsonReadContext jsonReadContext = this._child;
/* 104 */           if (jsonReadContext == null) {
/* 105 */               jsonReadContext = new JsonReadContext(this, this._dups == null ? null : this._dups.child(), 1, i, i2);
                        this._child = jsonReadContext;
                    } else {
/* 109 */               jsonReadContext.reset(1, i, i2);
                    }
/* 111 */           return jsonReadContext;
                }

/* 115 */       public JsonReadContext createChildObjectContext(int i, int i2) {
/* 116 */           JsonReadContext jsonReadContext = this._child;
/* 117 */           if (jsonReadContext == null) {
/* 118 */               jsonReadContext = new JsonReadContext(this, this._dups == null ? null : this._dups.child(), 2, i, i2);
                        this._child = jsonReadContext;
                    } else {
/* 123 */               jsonReadContext.reset(2, i, i2);
                    }
/* 124 */           return jsonReadContext;
                }

                @Override // com.fasterxml.jackson.core.JsonStreamContext
/* 133 */       public String getCurrentName() {
/* 134 */           return this._currentName;
                }

                @Override // com.fasterxml.jackson.core.JsonStreamContext
/* 136 */       public JsonReadContext getParent() {
/* 137 */           return this._parent;
                }

/* 153 */       public JsonLocation getStartLocation(Object obj) {
/* 156 */           return new JsonLocation(obj, -1L, this._lineNr, this._columnNr);
                }

/* 170 */       public boolean expectComma() {
/* 171 */           int i = this._index + 1;
                    this._index = i;
                    return this._type != 0 && i > 0;
                }

/* 176 */       public void setCurrentName(String str) throws JsonProcessingException {
/* 177 */           this._currentName = str;
/* 178 */           if (this._dups != null) {
/* 179 */               _checkDup(this._dups, str);
                    }
                }

/* 184 */       private void _checkDup(DupDetector dupDetector, String str) throws JsonProcessingException {
/* 185 */           if (dupDetector.isDup(str)) {
/* 186 */               throw new JsonParseException("Duplicate field '" + str + "'", dupDetector.findLocation());
                    }
                }

/* 202 */       public String toString() {
/* 203 */           StringBuilder sb = new StringBuilder(64);
                    switch (this._type) {
                        case 0:
/* 206 */                   sb.append("/");
                            break;
                        case 1:
/* 209 */                   sb.append('[');
/* 210 */                   sb.append(getCurrentIndex());
/* 211 */                   sb.append(']');
                            break;
                        case 2:
/* 214 */                   sb.append('{');
/* 215 */                   if (this._currentName != null) {
/* 216 */                       sb.append(CommonUtil.MARKER_QUOTES);
/* 217 */                       CharTypes.appendQuoted(sb, this._currentName);
/* 218 */                       sb.append(CommonUtil.MARKER_QUOTES);
                            } else {
/* 220 */                       sb.append('?');
                            }
/* 222 */                   sb.append('}');
                            break;
                    }
/* 225 */           return sb.toString();
                }
            }
