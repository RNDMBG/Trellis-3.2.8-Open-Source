            package com.fasterxml.jackson.core.format;

            import com.fasterxml.jackson.core.JsonFactory;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.p003io.MergedStream;
            import java.io.ByteArrayInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class DataFormatMatcher {
                protected final byte[] _bufferedData;
                protected final int _bufferedLength;
                protected final int _bufferedStart;
                protected final JsonFactory _match;
                protected final MatchStrength _matchStrength;
                protected final InputStream _originalStream;

/* 44 */        protected DataFormatMatcher(InputStream inputStream, byte[] bArr, int i, int i2, JsonFactory jsonFactory, MatchStrength matchStrength) {
/* 46 */            this._originalStream = inputStream;
/* 47 */            this._bufferedData = bArr;
/* 48 */            this._bufferedStart = i;
/* 49 */            this._bufferedLength = i2;
/* 50 */            this._match = jsonFactory;
/* 51 */            this._matchStrength = matchStrength;
                }

/* 63 */        public boolean hasMatch() {
                    return this._match != null;
                }

/* 70 */        public MatchStrength getMatchStrength() {
                    return this._matchStrength == null ? MatchStrength.INCONCLUSIVE : this._matchStrength;
                }

/* 76 */        public JsonFactory getMatch() {
/* 77 */            return this._match;
                }

/* 86 */        public String getMatchedFormatName() {
/* 87 */            return this._match.getFormatName();
                }

/* 101 */       public JsonParser createParserWithMatch() throws IOException {
/* 102 */           if (this._match == null) {
/* 103 */               return null;
                    }
/* 105 */           if (this._originalStream == null) {
/* 106 */               return this._match.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
                    }
/* 108 */           return this._match.createParser(getDataStream());
                }

/* 118 */       public InputStream getDataStream() {
                    return this._originalStream == null ? new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength) : new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
                }
            }
