            package com.fasterxml.jackson.core;

            import com.fasterxml.jackson.core.p003io.NumberInput;
            
            
    public class JsonPointer {
                protected static final JsonPointer EMPTY = new JsonPointer();
                protected final String _asString;
                protected final int _matchingElementIndex;
                protected final String _matchingPropertyName;
                protected final JsonPointer _nextSegment;

        protected JsonPointer() {
            this._nextSegment = null;
            this._matchingPropertyName = "";
            this._matchingElementIndex = -1;
            this._asString = "";
                }

        protected JsonPointer(String str, String str2, JsonPointer jsonPointer) {
            this._asString = str;
            this._nextSegment = jsonPointer;
            this._matchingPropertyName = str2;
            this._matchingElementIndex = _parseIndex(str2);
                }

        public static JsonPointer compile(String str) throws IllegalArgumentException {
            if (str == null || str.length() == 0) {
                return EMPTY;
                    }
            if (str.charAt(0) != '/') {
                throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + str + "\"");
                    }
            return _parseTail(str);
                }

       public static JsonPointer valueOf(String str) {
           return compile(str);
                }

       public boolean matches() {
                    return this._nextSegment == null;
                }

       public String getMatchingProperty() {
           return this._matchingPropertyName;
                }

       public int getMatchingIndex() {
           return this._matchingElementIndex;
                }

       public boolean mayMatchProperty() {
                    return this._matchingPropertyName != null;
                }

       public boolean mayMatchElement() {
                    return this._matchingElementIndex >= 0;
                }

       public JsonPointer matchProperty(String str) {
           if (this._nextSegment == null || !this._matchingPropertyName.equals(str)) {
               return null;
                    }
                    return this._nextSegment;
                }

       public JsonPointer matchElement(int i) {
           if (i != this._matchingElementIndex || i < 0) {
               return null;
                    }
                    return this._nextSegment;
                }

       public JsonPointer tail() {
           return this._nextSegment;
                }

       public String toString() {
           return this._asString;
                }

       public int hashCode() {
           return this._asString.hashCode();
                }

       public boolean equals(Object obj) {
           if (obj == this) {
                        return true;
                    }
           if (obj == null || !(obj instanceof JsonPointer)) {
                        return false;
                    }
           return this._asString.equals(((JsonPointer) obj)._asString);
                }

       private static final int _parseIndex(String str) {
           int length = str.length();
           if (length == 0 || length > 10) {
                        return -1;
                    }
           int i = 0;
                    while (i < length) {
               int i2 = i + 1;
                        char cCharAt = str.charAt(i);
               if (cCharAt > '9' || cCharAt < '0') {
                            return -1;
                        }
               i = i2 + 1;
                    }
           if (length != 10 || NumberInput.parseLong(str) <= 2147483647L) {
               return NumberInput.parseInt(str);
                    }
                    return -1;
                }

       protected static JsonPointer _parseTail(String str) {
           int length = str.length();
                    int i = 1;
           while (i < length) {
               char cCharAt = str.charAt(i);
               if (cCharAt == '/') {
                   return new JsonPointer(str, str.substring(1, i), _parseTail(str.substring(i)));
                        }
               int i2 = i + 1;
               if (cCharAt == '~' && i2 < length) {
                   return _parseQuotedTail(str, i2);
                        }
                        i = i2;
                    }
           return new JsonPointer(str, str.substring(1), EMPTY);
                }

       protected static JsonPointer _parseQuotedTail(String str, int i) {
           int length = str.length();
           StringBuilder sb = new StringBuilder(Math.max(16, length));
           if (i > 2) {
               sb.append((CharSequence) str, 1, i - 1);
                    }
                    _appendEscape(sb, str.charAt(i));
           int i2 = i + 1;
           while (i2 < length) {
               char cCharAt = str.charAt(i2);
               if (cCharAt == '/') {
                   return new JsonPointer(str, sb.toString(), _parseTail(str.substring(i2)));
                        }
               i2++;
               if (cCharAt == '~' && i2 < length) {
                            _appendEscape(sb, str.charAt(i2));
                   i2++;
                        } else {
                   sb.append(cCharAt);
                        }
                    }
           return new JsonPointer(str, sb.toString(), EMPTY);
                }

       private static void _appendEscape(StringBuilder sb, char c) {
           if (c == '0') {
                        c = '~';
                    } else if (c == '1') {
               c = '/';
                    } else {
               sb.append('~');
                    }
           sb.append(c);
                }
            }
