            package com.fasterxml.jackson.core;

            import com.fasterxml.jackson.core.p003io.NumberInput;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class JsonPointer {
                protected static final JsonPointer EMPTY = new JsonPointer();
                protected final String _asString;
                protected final int _matchingElementIndex;
                protected final String _matchingPropertyName;
                protected final JsonPointer _nextSegment;

/* 54 */        protected JsonPointer() {
/* 56 */            this._nextSegment = null;
/* 57 */            this._matchingPropertyName = "";
/* 58 */            this._matchingElementIndex = -1;
/* 59 */            this._asString = "";
                }

/* 64 */        protected JsonPointer(String str, String str2, JsonPointer jsonPointer) {
/* 66 */            this._asString = str;
/* 67 */            this._nextSegment = jsonPointer;
/* 69 */            this._matchingPropertyName = str2;
/* 70 */            this._matchingElementIndex = _parseIndex(str2);
                }

/* 90 */        public static JsonPointer compile(String str) throws IllegalArgumentException {
/* 91 */            if (str == null || str.length() == 0) {
/* 92 */                return EMPTY;
                    }
/* 95 */            if (str.charAt(0) != '/') {
/* 96 */                throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + str + "\"");
                    }
/* 98 */            return _parseTail(str);
                }

/* 104 */       public static JsonPointer valueOf(String str) {
/* 105 */           return compile(str);
                }

/* 134 */       public boolean matches() {
                    return this._nextSegment == null;
                }

/* 135 */       public String getMatchingProperty() {
/* 136 */           return this._matchingPropertyName;
                }

/* 136 */       public int getMatchingIndex() {
/* 137 */           return this._matchingElementIndex;
                }

/* 137 */       public boolean mayMatchProperty() {
                    return this._matchingPropertyName != null;
                }

/* 138 */       public boolean mayMatchElement() {
                    return this._matchingElementIndex >= 0;
                }

/* 141 */       public JsonPointer matchProperty(String str) {
/* 142 */           if (this._nextSegment == null || !this._matchingPropertyName.equals(str)) {
/* 143 */               return null;
                    }
                    return this._nextSegment;
                }

/* 148 */       public JsonPointer matchElement(int i) {
/* 149 */           if (i != this._matchingElementIndex || i < 0) {
/* 150 */               return null;
                    }
                    return this._nextSegment;
                }

/* 159 */       public JsonPointer tail() {
/* 160 */           return this._nextSegment;
                }

/* 168 */       public String toString() {
/* 169 */           return this._asString;
                }

/* 169 */       public int hashCode() {
/* 170 */           return this._asString.hashCode();
                }

/* 172 */       public boolean equals(Object obj) {
/* 173 */           if (obj == this) {
                        return true;
                    }
/* 174 */           if (obj == null || !(obj instanceof JsonPointer)) {
                        return false;
                    }
/* 176 */           return this._asString.equals(((JsonPointer) obj)._asString);
                }

/* 185 */       private static final int _parseIndex(String str) {
/* 186 */           int length = str.length();
/* 189 */           if (length == 0 || length > 10) {
                        return -1;
                    }
/* 192 */           int i = 0;
                    while (i < length) {
/* 193 */               int i2 = i + 1;
                        char cCharAt = str.charAt(i);
/* 194 */               if (cCharAt > '9' || cCharAt < '0') {
                            return -1;
                        }
/* 192 */               i = i2 + 1;
                    }
/* 198 */           if (length != 10 || NumberInput.parseLong(str) <= 2147483647L) {
/* 204 */               return NumberInput.parseInt(str);
                    }
                    return -1;
                }

/* 207 */       protected static JsonPointer _parseTail(String str) {
/* 208 */           int length = str.length();
                    int i = 1;
/* 211 */           while (i < length) {
/* 212 */               char cCharAt = str.charAt(i);
/* 213 */               if (cCharAt == '/') {
/* 214 */                   return new JsonPointer(str, str.substring(1, i), _parseTail(str.substring(i)));
                        }
/* 217 */               int i2 = i + 1;
/* 219 */               if (cCharAt == '~' && i2 < length) {
/* 220 */                   return _parseQuotedTail(str, i2);
                        }
                        i = i2;
                    }
/* 225 */           return new JsonPointer(str, str.substring(1), EMPTY);
                }

/* 235 */       protected static JsonPointer _parseQuotedTail(String str, int i) {
/* 236 */           int length = str.length();
/* 237 */           StringBuilder sb = new StringBuilder(Math.max(16, length));
/* 238 */           if (i > 2) {
/* 239 */               sb.append((CharSequence) str, 1, i - 1);
                    }
                    _appendEscape(sb, str.charAt(i));
/* 241 */           int i2 = i + 1;
/* 242 */           while (i2 < length) {
/* 243 */               char cCharAt = str.charAt(i2);
/* 244 */               if (cCharAt == '/') {
/* 245 */                   return new JsonPointer(str, sb.toString(), _parseTail(str.substring(i2)));
                        }
/* 248 */               i2++;
/* 249 */               if (cCharAt == '~' && i2 < length) {
                            _appendEscape(sb, str.charAt(i2));
/* 250 */                   i2++;
                        } else {
/* 253 */                   sb.append(cCharAt);
                        }
                    }
/* 256 */           return new JsonPointer(str, sb.toString(), EMPTY);
                }

/* 259 */       private static void _appendEscape(StringBuilder sb, char c) {
/* 260 */           if (c == '0') {
                        c = '~';
                    } else if (c == '1') {
/* 263 */               c = '/';
                    } else {
/* 265 */               sb.append('~');
                    }
/* 267 */           sb.append(c);
                }
            }
