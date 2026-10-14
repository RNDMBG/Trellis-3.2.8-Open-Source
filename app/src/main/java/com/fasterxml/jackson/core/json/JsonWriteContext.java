            package com.fasterxml.jackson.core.json;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.JsonGenerationException;
            import com.fasterxml.jackson.core.JsonProcessingException;
            import com.fasterxml.jackson.core.JsonStreamContext;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class JsonWriteContext extends JsonStreamContext {
                public static final int STATUS_EXPECT_NAME = 5;
                public static final int STATUS_EXPECT_VALUE = 4;
                public static final int STATUS_OK_AFTER_COLON = 2;
                public static final int STATUS_OK_AFTER_COMMA = 1;
                public static final int STATUS_OK_AFTER_SPACE = 3;
                public static final int STATUS_OK_AS_IS = 0;
                protected JsonWriteContext _child = null;
                protected String _currentName;
                protected final DupDetector _dups;
                protected boolean _gotName;
                protected final JsonWriteContext _parent;


/* 61 */        protected JsonWriteContext(int i, JsonWriteContext jsonWriteContext, DupDetector dupDetector) {
/* 63 */            this._type = i;
/* 64 */            this._parent = jsonWriteContext;
/* 65 */            this._dups = dupDetector;
/* 66 */            this._index = -1;
                }

/* 69 */        protected JsonWriteContext reset(int i) {
/* 70 */            this._type = i;
/* 71 */            this._index = -1;
/* 72 */            this._currentName = null;
/* 73 */            this._gotName = false;
/* 74 */            if (this._dups != null) {
/* 75 */                this._dups.reset();
                    }
/* 77 */            return this;
                }

                @Deprecated
/* 86 */        public static JsonWriteContext createRootContext() {
/* 87 */            return createRootContext(null);
                }

/* 90 */        public static JsonWriteContext createRootContext(DupDetector dupDetector) {
/* 91 */            return new JsonWriteContext(0, null, dupDetector);
                }

/* 95 */        public JsonWriteContext createChildArrayContext() {
/* 96 */            JsonWriteContext jsonWriteContext = this._child;
/* 97 */            if (jsonWriteContext == null) {
/* 98 */                JsonWriteContext jsonWriteContext2 = new JsonWriteContext(1, this, this._dups == null ? null : this._dups.child());
                        this._child = jsonWriteContext2;
                        return jsonWriteContext2;
                    }
/* 102 */           return jsonWriteContext.reset(1);
                }

/* 106 */       public JsonWriteContext createChildObjectContext() {
/* 107 */           JsonWriteContext jsonWriteContext = this._child;
/* 108 */           if (jsonWriteContext == null) {
/* 109 */               JsonWriteContext jsonWriteContext2 = new JsonWriteContext(2, this, this._dups == null ? null : this._dups.child());
                        this._child = jsonWriteContext2;
                        return jsonWriteContext2;
                    }
/* 113 */           return jsonWriteContext.reset(2);
                }

                @Override // com.fasterxml.jackson.core.JsonStreamContext
/* 118 */       public final JsonWriteContext getParent() {
/* 119 */           return this._parent;
                }

                @Override // com.fasterxml.jackson.core.JsonStreamContext
/* 121 */       public final String getCurrentName() {
/* 122 */           return this._currentName;
                }

/* 132 */       public final int writeFieldName(String str) throws JsonProcessingException {
/* 133 */           this._gotName = true;
/* 134 */           this._currentName = str;
/* 135 */           if (this._dups != null) {
/* 136 */               _checkDup(this._dups, str);
                    }
                    return this._index < 0 ? 0 : 1;
                }

/* 142 */       private void _checkDup(DupDetector dupDetector, String str) throws JsonProcessingException {
/* 143 */           if (dupDetector.isDup(str)) {
/* 144 */               throw new JsonGenerationException("Duplicate field '" + str + "'");
                    }
                }

/* 150 */       public final int writeValue() {
/* 151 */           if (this._type == 2) {
/* 152 */               this._gotName = false;
/* 153 */               this._index++;
                        return 2;
                    }
/* 158 */           if (this._type == 1) {
/* 159 */               int i = this._index;
/* 160 */               this._index++;
                        return i >= 0 ? 1 : 0;
                    }
/* 166 */           this._index++;
                    return this._index != 0 ? 3 : 0;
                }

/* 173 */       protected final void appendDesc(StringBuilder sb) {
/* 174 */           if (this._type == 2) {
/* 175 */               sb.append('{');
/* 176 */               if (this._currentName != null) {
/* 177 */                   sb.append(CommonUtil.MARKER_QUOTES);
/* 179 */                   sb.append(this._currentName);
/* 180 */                   sb.append(CommonUtil.MARKER_QUOTES);
                        } else {
/* 182 */                   sb.append('?');
                        }
/* 184 */               sb.append('}');
/* 193 */               return;
                    }
/* 185 */           if (this._type == 1) {
/* 186 */               sb.append('[');
/* 187 */               sb.append(getCurrentIndex());
/* 188 */               sb.append(']');
/* 193 */               return;
                    }
/* 191 */           sb.append("/");
                }

/* 203 */       public final String toString() {
/* 204 */           StringBuilder sb = new StringBuilder(64);
/* 205 */           appendDesc(sb);
/* 206 */           return sb.toString();
                }
            }
