            package com.fasterxml.jackson.core.p003io;

            import com.fasterxml.jackson.core.SerializableString;
            import java.io.IOException;
            import java.io.ObjectInputStream;
            import java.io.ObjectOutputStream;
            import java.io.OutputStream;
            import java.io.Serializable;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class SerializedString implements SerializableString, Serializable {
                protected transient String _jdkSerializeValue;
                protected char[] _quotedChars;
                protected byte[] _quotedUTF8Ref;
                protected byte[] _unquotedUTF8Ref;
                protected final String _value;

/* 45 */        public SerializedString(String str) {
/* 47 */            if (str == null) {
/* 48 */                throw new IllegalStateException("Null String illegal for SerializedString");
                    }
/* 50 */            this._value = str;
                }

/* 67 */        private void readObject(ObjectInputStream objectInputStream) throws IOException {
/* 68 */            this._jdkSerializeValue = objectInputStream.readUTF();
                }

/* 71 */        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
/* 72 */            objectOutputStream.writeUTF(this._value);
                }

/* 75 */        protected Object readResolve() {
/* 76 */            return new SerializedString(this._jdkSerializeValue);
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 85 */        public final String getValue() {
/* 86 */            return this._value;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 91 */        public final int charLength() {
/* 92 */            return this._value.length();
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 96 */        public final char[] asQuotedChars() {
/* 97 */            char[] cArr = this._quotedChars;
/* 98 */            if (cArr == null) {
/* 99 */                char[] cArrQuoteAsString = JsonStringEncoder.getInstance().quoteAsString(this._value);
/* 100 */               this._quotedChars = cArrQuoteAsString;
/* 102 */               return cArrQuoteAsString;
                    }
/* 102 */           return cArr;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 111 */       public final byte[] asUnquotedUTF8() {
/* 112 */           byte[] bArr = this._unquotedUTF8Ref;
/* 113 */           if (bArr == null) {
/* 114 */               byte[] bArrEncodeAsUTF8 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 115 */               this._unquotedUTF8Ref = bArrEncodeAsUTF8;
/* 117 */               return bArrEncodeAsUTF8;
                    }
/* 117 */           return bArr;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 126 */       public final byte[] asQuotedUTF8() {
/* 127 */           byte[] bArr = this._quotedUTF8Ref;
/* 128 */           if (bArr == null) {
/* 129 */               byte[] bArrQuoteAsUTF8 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 130 */               this._quotedUTF8Ref = bArrQuoteAsUTF8;
/* 132 */               return bArrQuoteAsUTF8;
                    }
/* 132 */           return bArr;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 143 */       public int appendQuotedUTF8(byte[] bArr, int i) {
/* 144 */           byte[] bArrQuoteAsUTF8 = this._quotedUTF8Ref;
/* 145 */           if (bArrQuoteAsUTF8 == null) {
/* 146 */               bArrQuoteAsUTF8 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 147 */               this._quotedUTF8Ref = bArrQuoteAsUTF8;
                    }
/* 149 */           int length = bArrQuoteAsUTF8.length;
/* 150 */           if (i + length > bArr.length) {
/* 151 */               return -1;
                    }
/* 153 */           System.arraycopy(bArrQuoteAsUTF8, 0, bArr, i, length);
                    return length;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 159 */       public int appendQuoted(char[] cArr, int i) {
/* 160 */           char[] cArrQuoteAsString = this._quotedChars;
/* 161 */           if (cArrQuoteAsString == null) {
/* 162 */               cArrQuoteAsString = JsonStringEncoder.getInstance().quoteAsString(this._value);
/* 163 */               this._quotedChars = cArrQuoteAsString;
                    }
/* 165 */           int length = cArrQuoteAsString.length;
/* 166 */           if (i + length > cArr.length) {
/* 167 */               return -1;
                    }
/* 169 */           System.arraycopy(cArrQuoteAsString, 0, cArr, i, length);
                    return length;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 175 */       public int appendUnquotedUTF8(byte[] bArr, int i) {
/* 176 */           byte[] bArrEncodeAsUTF8 = this._unquotedUTF8Ref;
/* 177 */           if (bArrEncodeAsUTF8 == null) {
/* 178 */               bArrEncodeAsUTF8 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 179 */               this._unquotedUTF8Ref = bArrEncodeAsUTF8;
                    }
/* 181 */           int length = bArrEncodeAsUTF8.length;
/* 182 */           if (i + length > bArr.length) {
/* 183 */               return -1;
                    }
/* 185 */           System.arraycopy(bArrEncodeAsUTF8, 0, bArr, i, length);
                    return length;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 191 */       public int appendUnquoted(char[] cArr, int i) {
/* 192 */           String str = this._value;
/* 193 */           int length = str.length();
/* 194 */           if (i + length > cArr.length) {
/* 195 */               return -1;
                    }
/* 197 */           str.getChars(0, length, cArr, i);
/* 198 */           return length;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 203 */       public int writeQuotedUTF8(OutputStream outputStream) throws IOException {
/* 204 */           byte[] bArrQuoteAsUTF8 = this._quotedUTF8Ref;
/* 205 */           if (bArrQuoteAsUTF8 == null) {
/* 206 */               bArrQuoteAsUTF8 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 207 */               this._quotedUTF8Ref = bArrQuoteAsUTF8;
                    }
/* 209 */           int length = bArrQuoteAsUTF8.length;
/* 210 */           outputStream.write(bArrQuoteAsUTF8, 0, length);
/* 211 */           return length;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 216 */       public int writeUnquotedUTF8(OutputStream outputStream) throws IOException {
/* 217 */           byte[] bArrEncodeAsUTF8 = this._unquotedUTF8Ref;
/* 218 */           if (bArrEncodeAsUTF8 == null) {
/* 219 */               bArrEncodeAsUTF8 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 220 */               this._unquotedUTF8Ref = bArrEncodeAsUTF8;
                    }
/* 222 */           int length = bArrEncodeAsUTF8.length;
/* 223 */           outputStream.write(bArrEncodeAsUTF8, 0, length);
/* 224 */           return length;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 229 */       public int putQuotedUTF8(ByteBuffer byteBuffer) {
/* 230 */           byte[] bArrQuoteAsUTF8 = this._quotedUTF8Ref;
/* 231 */           if (bArrQuoteAsUTF8 == null) {
/* 232 */               bArrQuoteAsUTF8 = JsonStringEncoder.getInstance().quoteAsUTF8(this._value);
/* 233 */               this._quotedUTF8Ref = bArrQuoteAsUTF8;
                    }
/* 235 */           int length = bArrQuoteAsUTF8.length;
/* 236 */           if (length > byteBuffer.remaining()) {
/* 237 */               return -1;
                    }
/* 239 */           byteBuffer.put(bArrQuoteAsUTF8, 0, length);
                    return length;
                }

                @Override // com.fasterxml.jackson.core.SerializableString
/* 245 */       public int putUnquotedUTF8(ByteBuffer byteBuffer) {
/* 246 */           byte[] bArrEncodeAsUTF8 = this._unquotedUTF8Ref;
/* 247 */           if (bArrEncodeAsUTF8 == null) {
/* 248 */               bArrEncodeAsUTF8 = JsonStringEncoder.getInstance().encodeAsUTF8(this._value);
/* 249 */               this._unquotedUTF8Ref = bArrEncodeAsUTF8;
                    }
/* 251 */           int length = bArrEncodeAsUTF8.length;
/* 252 */           if (length > byteBuffer.remaining()) {
/* 253 */               return -1;
                    }
/* 255 */           byteBuffer.put(bArrEncodeAsUTF8, 0, length);
                    return length;
                }

/* 266 */       public final String toString() {
/* 267 */           return this._value;
                }

/* 269 */       public final int hashCode() {
/* 270 */           return this._value.hashCode();
                }

/* 274 */       public final boolean equals(Object obj) {
/* 275 */           if (obj == this) {
                        return true;
                    }
/* 276 */           if (obj == null || obj.getClass() != getClass()) {
                        return false;
                    }
/* 277 */           return this._value.equals(((SerializedString) obj)._value);
                }
            }
