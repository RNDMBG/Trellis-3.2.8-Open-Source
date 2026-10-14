            package com.fasterxml.jackson.core.format;

            import com.fasterxml.jackson.core.JsonFactory;
            import java.io.IOException;
            import java.io.InputStream;
            import java.util.Collection;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 56 */    public class DataFormatDetector {
                public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
                protected final JsonFactory[] _detectors;
                protected final int _maxInputLookahead;
                protected final MatchStrength _minimalMatch;
                protected final MatchStrength _optimalMatch;

/* 57 */        public DataFormatDetector(JsonFactory... jsonFactoryArr) {
/* 58 */            this(jsonFactoryArr, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
                }

/* 62 */        public DataFormatDetector(Collection<JsonFactory> collection) {
/* 63 */            this((JsonFactory[]) collection.toArray(new JsonFactory[collection.size()]));
                }

/* 71 */        public DataFormatDetector withOptimalMatch(MatchStrength matchStrength) {
                    return matchStrength == this._optimalMatch ? this : new DataFormatDetector(this._detectors, matchStrength, this._minimalMatch, this._maxInputLookahead);
                }

/* 82 */        public DataFormatDetector withMinimalMatch(MatchStrength matchStrength) {
                    return matchStrength == this._minimalMatch ? this : new DataFormatDetector(this._detectors, this._optimalMatch, matchStrength, this._maxInputLookahead);
                }

/* 94 */        public DataFormatDetector withMaxInputLookahead(int i) {
                    return i == this._maxInputLookahead ? this : new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, i);
                }

/* 103 */       private DataFormatDetector(JsonFactory[] jsonFactoryArr, MatchStrength matchStrength, MatchStrength matchStrength2, int i) {
/* 105 */           this._detectors = jsonFactoryArr;
/* 106 */           this._optimalMatch = matchStrength;
/* 107 */           this._minimalMatch = matchStrength2;
/* 108 */           this._maxInputLookahead = i;
                }

/* 126 */       public DataFormatMatcher findFormat(InputStream inputStream) throws IOException {
/* 127 */           return _findFormat(new InputAccessor.Std(inputStream, new byte[this._maxInputLookahead]));
                }

/* 138 */       public DataFormatMatcher findFormat(byte[] bArr) throws IOException {
/* 139 */           return _findFormat(new InputAccessor.Std(bArr));
                }

/* 152 */       public DataFormatMatcher findFormat(byte[] bArr, int i, int i2) throws IOException {
/* 153 */           return _findFormat(new InputAccessor.Std(bArr, i, i2));
                }

/* 164 */       public String toString() {
/* 165 */           StringBuilder sb = new StringBuilder();
/* 166 */           sb.append('[');
/* 167 */           int length = this._detectors.length;
/* 168 */           if (length > 0) {
/* 169 */               sb.append(this._detectors[0].getFormatName());
                        for (int i = 1; i < length; i++) {
/* 171 */                   sb.append(", ");
/* 172 */                   sb.append(this._detectors[i].getFormatName());
                        }
                    }
/* 175 */           sb.append(']');
/* 176 */           return sb.toString();
                }

/* 188 */       private DataFormatMatcher _findFormat(InputAccessor.Std std) throws IOException {
                    MatchStrength matchStrengthHasFormat;
                    JsonFactory jsonFactory;
                    JsonFactory jsonFactory2;
/* 189 */           JsonFactory[] jsonFactoryArr = this._detectors;
                    int length = jsonFactoryArr.length;
                    int i = 0;
                    JsonFactory jsonFactory3 = null;
                    MatchStrength matchStrength = null;
                    while (i < length) {
                        jsonFactory = jsonFactoryArr[i];
/* 190 */               std.reset();
/* 191 */               matchStrengthHasFormat = jsonFactory.hasFormat(std);
/* 193 */               if (matchStrengthHasFormat == null) {
                            jsonFactory2 = jsonFactory3;
                        } else if (matchStrengthHasFormat.ordinal() < this._minimalMatch.ordinal()) {
                            jsonFactory2 = jsonFactory3;
                        } else if (jsonFactory3 != null && matchStrength.ordinal() >= matchStrengthHasFormat.ordinal()) {
                            jsonFactory2 = jsonFactory3;
                        } else {
/* 205 */                   if (matchStrengthHasFormat.ordinal() >= this._optimalMatch.ordinal()) {
/* 209 */                       return std.createMatcher(jsonFactory, matchStrengthHasFormat);
                            }
                            matchStrength = matchStrengthHasFormat;
                            jsonFactory2 = jsonFactory;
                        }
/* 189 */               i++;
                        jsonFactory3 = jsonFactory2;
                    }
                    matchStrengthHasFormat = matchStrength;
                    jsonFactory = jsonFactory3;
/* 209 */           return std.createMatcher(jsonFactory, matchStrengthHasFormat);
                }
            }
