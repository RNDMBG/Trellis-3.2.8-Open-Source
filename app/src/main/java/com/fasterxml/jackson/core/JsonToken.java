            package com.fasterxml.jackson.core;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 136 */   public enum JsonToken {
                NOT_AVAILABLE(null, -1),
                START_OBJECT("{", 1),
                END_OBJECT("}", 2),
                START_ARRAY("[", 3),
                END_ARRAY("]", 4),
                FIELD_NAME(null, 5),
                VALUE_EMBEDDED_OBJECT(null, 12),
                VALUE_STRING(null, 6),
                VALUE_NUMBER_INT(null, 7),
                VALUE_NUMBER_FLOAT(null, 8),
                VALUE_TRUE("true", 9),
                VALUE_FALSE("false", 10),
                VALUE_NULL("null", 11);

                final int _id;
                final boolean _isBoolean;
                final boolean _isNumber;
                final boolean _isScalar;
                final boolean _isStructEnd;
                final boolean _isStructStart;
                final String _serialized;
                final byte[] _serializedBytes;
                final char[] _serializedChars;

/* 137 */       JsonToken(String str, int i) {
/* 139 */           if (str == null) {
/* 140 */               this._serialized = null;
/* 141 */               this._serializedChars = null;
/* 142 */               this._serializedBytes = null;
                    } else {
/* 144 */               this._serialized = str;
/* 145 */               this._serializedChars = str.toCharArray();
/* 147 */               int length = this._serializedChars.length;
/* 148 */               this._serializedBytes = new byte[length];
/* 149 */               for (int i2 = 0; i2 < length; i2++) {
/* 150 */                   this._serializedBytes[i2] = (byte) this._serializedChars[i2];
                        }
                    }
/* 153 */           this._id = i;
/* 155 */           this._isBoolean = i == 10 || i == 9;
/* 156 */           this._isNumber = i == 7 || i == 8;
/* 158 */           this._isStructStart = i == 1 || i == 3;
/* 159 */           this._isStructEnd = i == 2 || i == 4;
/* 161 */           this._isScalar = (this._isStructStart || this._isStructEnd || i == 5 || i == -1) ? false : true;
                }

                /* JADX INFO: renamed from: id */
/* 165 */       public final int m524id() {
/* 166 */           return this._id;
                }

/* 167 */       public final String asString() {
/* 168 */           return this._serialized;
                }

/* 168 */       public final char[] asCharArray() {
/* 169 */           return this._serializedChars;
                }

/* 169 */       public final byte[] asByteArray() {
/* 170 */           return this._serializedBytes;
                }

/* 172 */       public final boolean isNumeric() {
/* 173 */           return this._isNumber;
                }

/* 184 */       public final boolean isStructStart() {
/* 185 */           return this._isStructStart;
                }

/* 196 */       public final boolean isStructEnd() {
/* 197 */           return this._isStructEnd;
                }

/* 205 */       public final boolean isScalarValue() {
/* 206 */           return this._isScalar;
                }

/* 209 */       public final boolean isBoolean() {
/* 210 */           return this._isBoolean;
                }
            }
