            package com.fasterxml.jackson.core.util;

            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonToken;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class JsonParserSequence extends JsonParserDelegate {
                protected int _nextParser;
                protected final JsonParser[] _parsers;

/* 36 */        protected JsonParserSequence(JsonParser[] jsonParserArr) {
/* 37 */            super(jsonParserArr[0]);
/* 38 */            this._parsers = jsonParserArr;
/* 39 */            this._nextParser = 1;
                }

/* 52 */        public static JsonParserSequence createFlattened(JsonParser jsonParser, JsonParser jsonParser2) {
/* 53 */            if (!(jsonParser instanceof JsonParserSequence) && !(jsonParser2 instanceof JsonParserSequence)) {
/* 55 */                return new JsonParserSequence(new JsonParser[]{jsonParser, jsonParser2});
                    }
/* 57 */            ArrayList arrayList = new ArrayList();
/* 58 */            if (jsonParser instanceof JsonParserSequence) {
/* 59 */                ((JsonParserSequence) jsonParser).addFlattenedActiveParsers(arrayList);
                    } else {
/* 61 */                arrayList.add(jsonParser);
                    }
/* 63 */            if (jsonParser2 instanceof JsonParserSequence) {
/* 64 */                ((JsonParserSequence) jsonParser2).addFlattenedActiveParsers(arrayList);
                    } else {
/* 66 */                arrayList.add(jsonParser2);
                    }
/* 68 */            return new JsonParserSequence((JsonParser[]) arrayList.toArray(new JsonParser[arrayList.size()]));
                }

/* 73 */        protected void addFlattenedActiveParsers(List<JsonParser> list) {
/* 74 */            int i = this._nextParser - 1;
                    int length = this._parsers.length;
                    for (int i2 = i; i2 < length; i2++) {
/* 75 */                JsonParser jsonParser = this._parsers[i2];
/* 76 */                if (jsonParser instanceof JsonParserSequence) {
/* 77 */                    ((JsonParserSequence) jsonParser).addFlattenedActiveParsers(list);
                        } else {
/* 79 */                    list.add(jsonParser);
                        }
                    }
                }

                @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser, java.io.Closeable, java.lang.AutoCloseable
/* 94 */        public void close() throws IOException {
/* 96 */            do {
/* 95 */                this.delegate.close();
/* 96 */            } while (switchToNext());
                }

                @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
/* 101 */       public JsonToken nextToken() throws IOException {
/* 102 */           JsonToken jsonTokenNextToken = this.delegate.nextToken();
/* 103 */           if (jsonTokenNextToken == null) {
/* 104 */               while (switchToNext()) {
/* 105 */                   JsonToken jsonTokenNextToken2 = this.delegate.nextToken();
/* 106 */                   if (jsonTokenNextToken2 != null) {
/* 108 */                       return jsonTokenNextToken2;
                            }
                        }
/* 108 */               return null;
                    }
/* 108 */           return jsonTokenNextToken;
                }

/* 122 */       public int containedParsersCount() {
/* 123 */           return this._parsers.length;
                }

/* 141 */       protected boolean switchToNext() {
/* 142 */           if (this._nextParser >= this._parsers.length) {
/* 143 */               return false;
                    }
/* 145 */           JsonParser[] jsonParserArr = this._parsers;
                    int i = this._nextParser;
                    this._nextParser = i + 1;
                    this.delegate = jsonParserArr[i];
/* 146 */           return true;
                }
            }
