            package com.fasterxml.jackson.core.json;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.JsonGenerationException;
            import com.fasterxml.jackson.core.JsonProcessingException;
            import com.fasterxml.jackson.core.JsonStreamContext;
            
            
     public class JsonWriteContext extends JsonStreamContext {
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


        protected JsonWriteContext(int i, JsonWriteContext jsonWriteContext, DupDetector dupDetector) {
            this._type = i;
            this._parent = jsonWriteContext;
            this._dups = dupDetector;
            this._index = -1;
                }

        protected JsonWriteContext reset(int i) {
            this._type = i;
            this._index = -1;
            this._currentName = null;
            this._gotName = false;
            if (this._dups != null) {
                this._dups.reset();
                    }
            return this;
                }

                @Deprecated
        public static JsonWriteContext createRootContext() {
            return createRootContext(null);
                }

        public static JsonWriteContext createRootContext(DupDetector dupDetector) {
            return new JsonWriteContext(0, null, dupDetector);
                }

        public JsonWriteContext createChildArrayContext() {
            JsonWriteContext jsonWriteContext = this._child;
            if (jsonWriteContext == null) {
                JsonWriteContext jsonWriteContext2 = new JsonWriteContext(1, this, this._dups == null ? null : this._dups.child());
                        this._child = jsonWriteContext2;
                        return jsonWriteContext2;
                    }
           return jsonWriteContext.reset(1);
                }

       public JsonWriteContext createChildObjectContext() {
           JsonWriteContext jsonWriteContext = this._child;
           if (jsonWriteContext == null) {
               JsonWriteContext jsonWriteContext2 = new JsonWriteContext(2, this, this._dups == null ? null : this._dups.child());
                        this._child = jsonWriteContext2;
                        return jsonWriteContext2;
                    }
           return jsonWriteContext.reset(2);
                }

                @Override 
       public final JsonWriteContext getParent() {
           return this._parent;
                }

                @Override 
       public final String getCurrentName() {
           return this._currentName;
                }

       public final int writeFieldName(String str) throws JsonProcessingException {
           this._gotName = true;
           this._currentName = str;
           if (this._dups != null) {
               _checkDup(this._dups, str);
                    }
                    return this._index < 0 ? 0 : 1;
                }

       private void _checkDup(DupDetector dupDetector, String str) throws JsonProcessingException {
           if (dupDetector.isDup(str)) {
               throw new JsonGenerationException("Duplicate field '" + str + "'");
                    }
                }

       public final int writeValue() {
           if (this._type == 2) {
               this._gotName = false;
               this._index++;
                        return 2;
                    }
           if (this._type == 1) {
               int i = this._index;
               this._index++;
                        return i >= 0 ? 1 : 0;
                    }
           this._index++;
                    return this._index != 0 ? 3 : 0;
                }

       protected final void appendDesc(StringBuilder sb) {
           if (this._type == 2) {
               sb.append('{');
               if (this._currentName != null) {
                   sb.append(CommonUtil.MARKER_QUOTES);
                   sb.append(this._currentName);
                   sb.append(CommonUtil.MARKER_QUOTES);
                        } else {
                   sb.append('?');
                        }
               sb.append('}');
               return;
                    }
           if (this._type == 1) {
               sb.append('[');
               sb.append(getCurrentIndex());
               sb.append(']');
               return;
                    }
           sb.append("/");
                }

       public final String toString() {
           StringBuilder sb = new StringBuilder(64);
           appendDesc(sb);
           return sb.toString();
                }
            }
