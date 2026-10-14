            package com.fasterxml.jackson.core;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.format.InputAccessor;
            import com.fasterxml.jackson.core.format.MatchStrength;
            import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
            import com.fasterxml.jackson.core.json.PackageVersion;
            import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
            import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
            import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
            import com.fasterxml.jackson.core.p003io.CharacterEscapes;
            import com.fasterxml.jackson.core.p003io.IOContext;
            import com.fasterxml.jackson.core.p003io.InputDecorator;
            import com.fasterxml.jackson.core.p003io.OutputDecorator;
            import com.fasterxml.jackson.core.p003io.SerializedString;
            import com.fasterxml.jackson.core.p003io.UTF8Writer;
            import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
            import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
            import com.fasterxml.jackson.core.util.BufferRecycler;
            import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.io.Reader;
            import java.io.Serializable;
            import java.io.StringReader;
            import java.io.Writer;
            import java.lang.ref.SoftReference;
            import java.net.URL;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 101 */   public class JsonFactory implements Versioned, Serializable {
                public static final String FORMAT_NAME_JSON = "JSON";
                private static final long serialVersionUID = 3194418244231611666L;
                protected CharacterEscapes _characterEscapes;
                protected int _factoryFeatures;
                protected int _generatorFeatures;
                protected InputDecorator _inputDecorator;
                protected ObjectCodec _objectCodec;
                protected OutputDecorator _outputDecorator;
                protected int _parserFeatures;
                protected final transient BytesToNameCanonicalizer _rootByteSymbols;
                protected final transient CharsToNameCanonicalizer _rootCharSymbols;
                protected SerializableString _rootValueSeparator;
                protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = Feature.collectDefaults();
                protected static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
                protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
                private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef = new ThreadLocal<>();

/* 102 */       public enum Feature {
                    INTERN_FIELD_NAMES(true),
                    CANONICALIZE_FIELD_NAMES(true);

                    private final boolean _defaultState;

/* 103 */           public static int collectDefaults() {
                        int mask = 0;
                        for (Feature feature : values()) {
/* 105 */                   if (feature.enabledByDefault()) {
/* 106 */                       mask |= feature.getMask();
                            }
                        }
/* 109 */               return mask;
                    }

/* 111 */           Feature(boolean z) {
/* 113 */               this._defaultState = z;
                    }

/* 115 */           public boolean enabledByDefault() {
/* 116 */               return this._defaultState;
                    }

/* 117 */           public boolean enabledIn(int i) {
                        return (getMask() & i) != 0;
                    }

/* 119 */           public int getMask() {
/* 120 */               return 1 << ordinal();
                    }
                }

/* 257 */       public JsonFactory() {
/* 258 */           this((ObjectCodec) null);
                }

/* 259 */       public JsonFactory(ObjectCodec objectCodec) {
/* 173 */           this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
/* 182 */           this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
/* 202 */           this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
/* 207 */           this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
/* 212 */           this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
/* 240 */           this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
/* 260 */           this._objectCodec = objectCodec;
                }

/* 267 */       protected JsonFactory(JsonFactory jsonFactory, ObjectCodec objectCodec) {
/* 173 */           this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
/* 182 */           this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
/* 202 */           this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
/* 207 */           this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
/* 212 */           this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
/* 240 */           this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
/* 269 */           this._objectCodec = null;
/* 270 */           this._factoryFeatures = jsonFactory._factoryFeatures;
/* 271 */           this._parserFeatures = jsonFactory._parserFeatures;
/* 272 */           this._generatorFeatures = jsonFactory._generatorFeatures;
/* 273 */           this._characterEscapes = jsonFactory._characterEscapes;
/* 274 */           this._inputDecorator = jsonFactory._inputDecorator;
/* 275 */           this._outputDecorator = jsonFactory._outputDecorator;
/* 276 */           this._rootValueSeparator = jsonFactory._rootValueSeparator;
                }

/* 299 */       public JsonFactory copy() {
/* 300 */           _checkInvalidCopy(JsonFactory.class);
/* 302 */           return new JsonFactory(this, null);
                }

/* 310 */       protected void _checkInvalidCopy(Class<?> cls) {
/* 311 */           if (getClass() != cls) {
/* 312 */               throw new IllegalStateException("Failed copy(): " + getClass().getName() + " (version: " + version() + ") does not override copy(); it has to");
                    }
                }

/* 328 */       protected Object readResolve() {
/* 329 */           return new JsonFactory(this, this._objectCodec);
                }

/* 353 */       public boolean requiresPropertyOrdering() {
/* 354 */           return false;
                }

/* 369 */       public boolean canHandleBinaryNatively() {
/* 370 */           return false;
                }

/* 389 */       public boolean canUseSchema(FormatSchema formatSchema) {
/* 390 */           String formatName = getFormatName();
                    return formatName != null && formatName.equals(formatSchema.getSchemaType());
                }

/* 406 */       public String getFormatName() {
/* 407 */           if (getClass() == JsonFactory.class) {
/* 408 */               return FORMAT_NAME_JSON;
                    }
                    return null;
                }

/* 415 */       public MatchStrength hasFormat(InputAccessor inputAccessor) throws IOException {
/* 416 */           if (getClass() == JsonFactory.class) {
/* 417 */               return hasJSONFormat(inputAccessor);
                    }
                    return null;
                }

/* 435 */       public boolean requiresCustomCodec() {
/* 436 */           return false;
                }

/* 444 */       protected MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
/* 445 */           return ByteSourceJsonBootstrapper.hasJSONFormat(inputAccessor);
                }

                @Override // com.fasterxml.jackson.core.Versioned
/* 455 */       public Version version() {
/* 456 */           return PackageVersion.VERSION;
                }

/* 469 */       public final JsonFactory configure(Feature feature, boolean z) {
                    return z ? enable(feature) : disable(feature);
                }

/* 477 */       public JsonFactory enable(Feature feature) {
/* 478 */           this._factoryFeatures |= feature.getMask();
/* 479 */           return this;
                }

/* 486 */       public JsonFactory disable(Feature feature) {
/* 487 */           this._factoryFeatures &= feature.getMask() ^ (-1);
/* 488 */           return this;
                }

/* 494 */       public final boolean isEnabled(Feature feature) {
                    return (this._factoryFeatures & feature.getMask()) != 0;
                }

/* 508 */       public final JsonFactory configure(JsonParser.Feature feature, boolean z) {
                    return z ? enable(feature) : disable(feature);
                }

/* 516 */       public JsonFactory enable(JsonParser.Feature feature) {
/* 517 */           this._parserFeatures |= feature.getMask();
/* 518 */           return this;
                }

/* 525 */       public JsonFactory disable(JsonParser.Feature feature) {
/* 526 */           this._parserFeatures &= feature.getMask() ^ (-1);
/* 527 */           return this;
                }

/* 533 */       public final boolean isEnabled(JsonParser.Feature feature) {
                    return (this._parserFeatures & feature.getMask()) != 0;
                }

/* 541 */       public InputDecorator getInputDecorator() {
/* 542 */           return this._inputDecorator;
                }

/* 548 */       public JsonFactory setInputDecorator(InputDecorator inputDecorator) {
/* 549 */           this._inputDecorator = inputDecorator;
/* 550 */           return this;
                }

/* 563 */       public final JsonFactory configure(JsonGenerator.Feature feature, boolean z) {
                    return z ? enable(feature) : disable(feature);
                }

/* 572 */       public JsonFactory enable(JsonGenerator.Feature feature) {
/* 573 */           this._generatorFeatures |= feature.getMask();
/* 574 */           return this;
                }

/* 581 */       public JsonFactory disable(JsonGenerator.Feature feature) {
/* 582 */           this._generatorFeatures &= feature.getMask() ^ (-1);
/* 583 */           return this;
                }

/* 589 */       public final boolean isEnabled(JsonGenerator.Feature feature) {
                    return (this._generatorFeatures & feature.getMask()) != 0;
                }

/* 597 */       public CharacterEscapes getCharacterEscapes() {
/* 598 */           return this._characterEscapes;
                }

/* 605 */       public JsonFactory setCharacterEscapes(CharacterEscapes characterEscapes) {
/* 606 */           this._characterEscapes = characterEscapes;
/* 607 */           return this;
                }

/* 614 */       public OutputDecorator getOutputDecorator() {
/* 615 */           return this._outputDecorator;
                }

/* 621 */       public JsonFactory setOutputDecorator(OutputDecorator outputDecorator) {
/* 622 */           this._outputDecorator = outputDecorator;
/* 623 */           return this;
                }

/* 635 */       public JsonFactory setRootValueSeparator(String str) {
/* 636 */           this._rootValueSeparator = str == null ? null : new SerializedString(str);
/* 637 */           return this;
                }

/* 643 */       public String getRootValueSeparator() {
/* 644 */           if (this._rootValueSeparator == null) {
                        return null;
                    }
                    return this._rootValueSeparator.getValue();
                }

/* 660 */       public JsonFactory setCodec(ObjectCodec objectCodec) {
/* 661 */           this._objectCodec = objectCodec;
/* 662 */           return this;
                }

/* 664 */       public ObjectCodec getCodec() {
/* 665 */           return this._objectCodec;
                }

/* 690 */       public JsonParser createParser(File file) throws IOException {
/* 691 */           IOContext iOContext_createContext = _createContext(file, true);
/* 692 */           InputStream fileInputStream = new FileInputStream(file);
/* 694 */           if (this._inputDecorator != null) {
/* 695 */               fileInputStream = this._inputDecorator.decorate(iOContext_createContext, fileInputStream);
                    }
/* 697 */           return _createParser(fileInputStream, iOContext_createContext);
                }

/* 718 */       public JsonParser createParser(URL url) throws IOException {
/* 719 */           IOContext iOContext_createContext = _createContext(url, true);
/* 720 */           InputStream inputStream_optimizedStreamFromURL = _optimizedStreamFromURL(url);
/* 722 */           if (this._inputDecorator != null) {
/* 723 */               inputStream_optimizedStreamFromURL = this._inputDecorator.decorate(iOContext_createContext, inputStream_optimizedStreamFromURL);
                    }
/* 725 */           return _createParser(inputStream_optimizedStreamFromURL, iOContext_createContext);
                }

/* 747 */       public JsonParser createParser(InputStream inputStream) throws IOException {
/* 748 */           IOContext iOContext_createContext = _createContext(inputStream, false);
/* 750 */           if (this._inputDecorator != null) {
/* 751 */               inputStream = this._inputDecorator.decorate(iOContext_createContext, inputStream);
                    }
/* 753 */           return _createParser(inputStream, iOContext_createContext);
                }

/* 773 */       public JsonParser createParser(Reader reader) throws IOException {
/* 774 */           IOContext iOContext_createContext = _createContext(reader, false);
/* 776 */           if (this._inputDecorator != null) {
/* 777 */               reader = this._inputDecorator.decorate(iOContext_createContext, reader);
                    }
/* 779 */           return _createParser(reader, iOContext_createContext);
                }

/* 790 */       public JsonParser createParser(byte[] bArr) throws IOException {
                    InputStream inputStreamDecorate;
/* 791 */           IOContext iOContext_createContext = _createContext(bArr, true);
                    return (this._inputDecorator == null || (inputStreamDecorate = this._inputDecorator.decorate(iOContext_createContext, bArr, 0, bArr.length)) == null) ? _createParser(bArr, 0, bArr.length, iOContext_createContext) : _createParser(inputStreamDecorate, iOContext_createContext);
                }

/* 814 */       public JsonParser createParser(byte[] bArr, int i, int i2) throws IOException {
                    InputStream inputStreamDecorate;
/* 815 */           IOContext iOContext_createContext = _createContext(bArr, true);
                    return (this._inputDecorator == null || (inputStreamDecorate = this._inputDecorator.decorate(iOContext_createContext, bArr, i, i2)) == null) ? _createParser(bArr, i, i2, iOContext_createContext) : _createParser(inputStreamDecorate, iOContext_createContext);
                }

/* 834 */       public JsonParser createParser(String str) throws IOException {
/* 835 */           Reader stringReader = new StringReader(str);
/* 837 */           IOContext iOContext_createContext = _createContext(stringReader, true);
/* 839 */           if (this._inputDecorator != null) {
/* 840 */               stringReader = this._inputDecorator.decorate(iOContext_createContext, stringReader);
                    }
/* 842 */           return _createParser(stringReader, iOContext_createContext);
                }

                @Deprecated
/* 871 */       public JsonParser createJsonParser(File file) throws IOException {
/* 872 */           return createParser(file);
                }

                @Deprecated
/* 896 */       public JsonParser createJsonParser(URL url) throws IOException {
/* 897 */           return createParser(url);
                }

                @Deprecated
/* 923 */       public JsonParser createJsonParser(InputStream inputStream) throws IOException {
/* 924 */           return createParser(inputStream);
                }

                @Deprecated
/* 947 */       public JsonParser createJsonParser(Reader reader) throws IOException {
/* 948 */           return createParser(reader);
                }

                @Deprecated
/* 963 */       public JsonParser createJsonParser(byte[] bArr) throws IOException {
/* 964 */           return createParser(bArr);
                }

                @Deprecated
/* 983 */       public JsonParser createJsonParser(byte[] bArr, int i, int i2) throws IOException {
/* 984 */           return createParser(bArr, i, i2);
                }

                @Deprecated
/* 996 */       public JsonParser createJsonParser(String str) throws IOException {
/* 997 */           return createParser(str);
                }

/* 1031 */      public JsonGenerator createGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException {
/* 1032 */          IOContext iOContext_createContext = _createContext(outputStream, false);
/* 1033 */          iOContext_createContext.setEncoding(jsonEncoding);
/* 1034 */          if (jsonEncoding == JsonEncoding.UTF8) {
/* 1036 */              if (this._outputDecorator != null) {
/* 1037 */                  outputStream = this._outputDecorator.decorate(iOContext_createContext, outputStream);
                        }
/* 1039 */              return _createUTF8Generator(outputStream, iOContext_createContext);
                    }
/* 1041 */          Writer writer_createWriter = _createWriter(outputStream, jsonEncoding, iOContext_createContext);
/* 1043 */          if (this._outputDecorator != null) {
/* 1044 */              writer_createWriter = this._outputDecorator.decorate(iOContext_createContext, writer_createWriter);
                    }
/* 1046 */          return _createGenerator(writer_createWriter, iOContext_createContext);
                }

/* 1057 */      public JsonGenerator createGenerator(OutputStream outputStream) throws IOException {
/* 1058 */          return createGenerator(outputStream, JsonEncoding.UTF8);
                }

/* 1078 */      public JsonGenerator createGenerator(Writer writer) throws IOException {
/* 1079 */          IOContext iOContext_createContext = _createContext(writer, false);
/* 1081 */          if (this._outputDecorator != null) {
/* 1082 */              writer = this._outputDecorator.decorate(iOContext_createContext, writer);
                    }
/* 1084 */          return _createGenerator(writer, iOContext_createContext);
                }

/* 1105 */      public JsonGenerator createGenerator(File file, JsonEncoding jsonEncoding) throws IOException {
/* 1106 */          OutputStream fileOutputStream = new FileOutputStream(file);
/* 1108 */          IOContext iOContext_createContext = _createContext(fileOutputStream, true);
/* 1109 */          iOContext_createContext.setEncoding(jsonEncoding);
/* 1110 */          if (jsonEncoding == JsonEncoding.UTF8) {
/* 1112 */              if (this._outputDecorator != null) {
/* 1113 */                  fileOutputStream = this._outputDecorator.decorate(iOContext_createContext, fileOutputStream);
                        }
/* 1115 */              return _createUTF8Generator(fileOutputStream, iOContext_createContext);
                    }
/* 1117 */          Writer writer_createWriter = _createWriter(fileOutputStream, jsonEncoding, iOContext_createContext);
/* 1119 */          if (this._outputDecorator != null) {
/* 1120 */              writer_createWriter = this._outputDecorator.decorate(iOContext_createContext, writer_createWriter);
                    }
/* 1122 */          return _createGenerator(writer_createWriter, iOContext_createContext);
                }

                @Deprecated
/* 1156 */      public JsonGenerator createJsonGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException {
/* 1157 */          return createGenerator(outputStream, jsonEncoding);
                }

                @Deprecated
/* 1178 */      public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
/* 1179 */          return createGenerator(writer);
                }

                @Deprecated
/* 1191 */      public JsonGenerator createJsonGenerator(OutputStream outputStream) throws IOException {
/* 1192 */          return createGenerator(outputStream, JsonEncoding.UTF8);
                }

                @Deprecated
/* 1215 */      public JsonGenerator createJsonGenerator(File file, JsonEncoding jsonEncoding) throws IOException {
/* 1216 */          return createGenerator(file, jsonEncoding);
                }

/* 1241 */      protected JsonParser _createParser(InputStream inputStream, IOContext iOContext) throws IOException {
/* 1242 */          return new ByteSourceJsonBootstrapper(iOContext, inputStream).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES));
                }

                @Deprecated
/* 1252 */      protected JsonParser _createJsonParser(InputStream inputStream, IOContext iOContext) throws IOException {
/* 1253 */          return _createParser(inputStream, iOContext);
                }

/* 1270 */      protected JsonParser _createParser(Reader reader, IOContext iOContext) throws IOException {
/* 1271 */          return new ReaderBasedJsonParser(iOContext, this._parserFeatures, reader, this._objectCodec, this._rootCharSymbols.makeChild(isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES)));
                }

                @Deprecated
/* 1280 */      protected JsonParser _createJsonParser(Reader reader, IOContext iOContext) throws IOException {
/* 1281 */          return _createParser(reader, iOContext);
                }

/* 1297 */      protected JsonParser _createParser(byte[] bArr, int i, int i2, IOContext iOContext) throws IOException {
/* 1298 */          return new ByteSourceJsonBootstrapper(iOContext, bArr, i, i2).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES));
                }

                @Deprecated
/* 1308 */      protected JsonParser _createJsonParser(byte[] bArr, int i, int i2, IOContext iOContext) throws IOException {
/* 1309 */          return _createParser(bArr, i, i2, iOContext);
                }

/* 1331 */      protected JsonGenerator _createGenerator(Writer writer, IOContext iOContext) throws IOException {
/* 1332 */          WriterBasedJsonGenerator writerBasedJsonGenerator = new WriterBasedJsonGenerator(iOContext, this._generatorFeatures, this._objectCodec, writer);
/* 1334 */          if (this._characterEscapes != null) {
/* 1335 */              writerBasedJsonGenerator.setCharacterEscapes(this._characterEscapes);
                    }
/* 1337 */          SerializableString serializableString = this._rootValueSeparator;
/* 1338 */          if (serializableString != DEFAULT_ROOT_VALUE_SEPARATOR) {
/* 1339 */              writerBasedJsonGenerator.setRootValueSeparator(serializableString);
                    }
/* 1341 */          return writerBasedJsonGenerator;
                }

                @Deprecated
/* 1353 */      protected JsonGenerator _createJsonGenerator(Writer writer, IOContext iOContext) throws IOException {
/* 1354 */          return _createGenerator(writer, iOContext);
                }

/* 1367 */      protected JsonGenerator _createUTF8Generator(OutputStream outputStream, IOContext iOContext) throws IOException {
/* 1368 */          UTF8JsonGenerator uTF8JsonGenerator = new UTF8JsonGenerator(iOContext, this._generatorFeatures, this._objectCodec, outputStream);
/* 1370 */          if (this._characterEscapes != null) {
/* 1371 */              uTF8JsonGenerator.setCharacterEscapes(this._characterEscapes);
                    }
/* 1373 */          SerializableString serializableString = this._rootValueSeparator;
/* 1374 */          if (serializableString != DEFAULT_ROOT_VALUE_SEPARATOR) {
/* 1375 */              uTF8JsonGenerator.setRootValueSeparator(serializableString);
                    }
/* 1377 */          return uTF8JsonGenerator;
                }

                @Deprecated
/* 1386 */      protected JsonGenerator _createUTF8JsonGenerator(OutputStream outputStream, IOContext iOContext) throws IOException {
/* 1387 */          return _createUTF8Generator(outputStream, iOContext);
                }

/* 1392 */      protected Writer _createWriter(OutputStream outputStream, JsonEncoding jsonEncoding, IOContext iOContext) throws IOException {
                    return jsonEncoding == JsonEncoding.UTF8 ? new UTF8Writer(iOContext, outputStream) : new OutputStreamWriter(outputStream, jsonEncoding.getJavaName());
                }

/* 1411 */      protected IOContext _createContext(Object obj, boolean z) {
/* 1412 */          return new IOContext(_getBufferRecycler(), obj, z);
                }

/* 1422 */      public BufferRecycler _getBufferRecycler() {
/* 1423 */          SoftReference<BufferRecycler> softReference = _recyclerRef.get();
/* 1424 */          BufferRecycler bufferRecycler = softReference == null ? null : softReference.get();
/* 1426 */          if (bufferRecycler == null) {
/* 1427 */              BufferRecycler bufferRecycler2 = new BufferRecycler();
/* 1428 */              _recyclerRef.set(new SoftReference<>(bufferRecycler2));
/* 1430 */              return bufferRecycler2;
                    }
/* 1430 */          return bufferRecycler;
                }

/* 1440 */      protected InputStream _optimizedStreamFromURL(URL url) throws IOException {
                    String host;
                    return (!CommonUtil.FILE.equals(url.getProtocol()) || !((host = url.getHost()) == null || host.length() == 0) || url.getPath().indexOf(37) >= 0) ? url.openStream() : new FileInputStream(url.getPath());
                }
            }
