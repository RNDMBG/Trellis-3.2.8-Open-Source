            package com.fasterxml.jackson.core;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public abstract class JsonStreamContext {
                protected static final int TYPE_ARRAY = 1;
                protected static final int TYPE_OBJECT = 2;
                protected static final int TYPE_ROOT = 0;
                protected int _index;
                protected int _type;

                public abstract String getCurrentName();

                public abstract JsonStreamContext getParent();

/* 42 */        protected JsonStreamContext() {
                }

/* 60 */        public final boolean inArray() {
                    return this._type == 1;
                }

/* 67 */        public final boolean inRoot() {
                    return this._type == 0;
                }

/* 73 */        public final boolean inObject() {
                    return this._type == 2;
                }

/* 81 */        public final String getTypeDesc() {
                    switch (this._type) {
                        case 0:
/* 83 */                    return "ROOT";
                        case 1:
/* 84 */                    return "ARRAY";
                        case 2:
/* 85 */                    return "OBJECT";
                        default:
/* 87 */                    return "?";
                    }
                }

/* 94 */        public final int getEntryCount() {
/* 95 */            return this._index + 1;
                }

/* 102 */       public final int getCurrentIndex() {
/* 103 */           if (this._index < 0) {
                        return 0;
                    }
                    return this._index;
                }
            }
