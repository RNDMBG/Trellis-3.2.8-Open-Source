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
            
            
   public class JsonFactory implements Versioned, Serializable {
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

       public enum Feature {
                    INTERN_FIELD_NAMES(true),
                    CANONICALIZE_FIELD_NAMES(true);

                    private final boolean _defaultState;

           public static int collectDefaults() {
                        int mask = 0;
                        for (Feature feature : values()) {
                   if (feature.enabledByDefault()) {
                       mask |= feature.getMask();
                            }
                        }
               return mask;
                    }

           Feature(boolean z) {
               this._defaultState = z;
                    }

           public boolean enabledByDefault() {
               return this._defaultState;
                    }

           public boolean enabledIn(int i) {
                        return (getMask() & i) != 0;
                    }

           public int getMask() {
               return 1 << ordinal();
                    }
                }

       public JsonFactory() {
           this((ObjectCodec) null);
                }

       public JsonFactory(ObjectCodec objectCodec) {
           this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
           this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
           this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
           this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
           this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
           this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
           this._objectCodec = objectCodec;
                }

       protected JsonFactory(JsonFactory jsonFactory, ObjectCodec objectCodec) {
           this._rootCharSymbols = CharsToNameCanonicalizer.createRoot();
           this._rootByteSymbols = BytesToNameCanonicalizer.createRoot();
           this._factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
           this._parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
           this._generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
           this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
           this._objectCodec = null;
           this._factoryFeatures = jsonFactory._factoryFeatures;
           this._parserFeatures = jsonFactory._parserFeatures;
           this._generatorFeatures = jsonFactory._generatorFeatures;
           this._characterEscapes = jsonFactory._characterEscapes;
           this._inputDecorator = jsonFactory._inputDecorator;
           this._outputDecorator = jsonFactory._outputDecorator;
           this._rootValueSeparator = jsonFactory._rootValueSeparator;
                }

       public JsonFactory copy() {
           _checkInvalidCopy(JsonFactory.class);
           return new JsonFactory(this, null);
                }

       protected void _checkInvalidCopy(Class<?> cls) {
           if (getClass() != cls) {
               throw new IllegalStateException("Failed copy(): " + getClass().getName() + " (version: " + version() + ") does not override copy(); it has to");
                    }
                }

       protected Object readResolve() {
           return new JsonFactory(this, this._objectCodec);
                }

       public boolean requiresPropertyOrdering() {
           return false;
                }

       public boolean canHandleBinaryNatively() {
           return false;
                }

       public boolean canUseSchema(FormatSchema formatSchema) {
           String formatName = getFormatName();
                    return formatName != null && formatName.equals(formatSchema.getSchemaType());
                }

       public String getFormatName() {
           if (getClass() == JsonFactory.class) {
               return FORMAT_NAME_JSON;
                    }
                    return null;
                }

       public MatchStrength hasFormat(InputAccessor inputAccessor) throws IOException {
           if (getClass() == JsonFactory.class) {
               return hasJSONFormat(inputAccessor);
                    }
                    return null;
                }

       public boolean requiresCustomCodec() {
           return false;
                }

       protected MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
           return ByteSourceJsonBootstrapper.hasJSONFormat(inputAccessor);
                }

                @Override 
       public Version version() {
           return PackageVersion.VERSION;
                }

       public final JsonFactory configure(Feature feature, boolean z) {
                    return z ? enable(feature) : disable(feature);
                }

       public JsonFactory enable(Feature feature) {
           this._factoryFeatures |= feature.getMask();
           return this;
                }

       public JsonFactory disable(Feature feature) {
           this._factoryFeatures &= feature.getMask() ^ (-1);
           return this;
                }

       public final boolean isEnabled(Feature feature) {
                    return (this._factoryFeatures & feature.getMask()) != 0;
                }

       public final JsonFactory configure(JsonParser.Feature feature, boolean z) {
                    return z ? enable(feature) : disable(feature);
                }

       public JsonFactory enable(JsonParser.Feature feature) {
           this._parserFeatures |= feature.getMask();
           return this;
                }

       public JsonFactory disable(JsonParser.Feature feature) {
           this._parserFeatures &= feature.getMask() ^ (-1);
           return this;
                }

       public final boolean isEnabled(JsonParser.Feature feature) {
                    return (this._parserFeatures & feature.getMask()) != 0;
                }

       public InputDecorator getInputDecorator() {
           return this._inputDecorator;
                }

       public JsonFactory setInputDecorator(InputDecorator inputDecorator) {
           this._inputDecorator = inputDecorator;
           return this;
                }

       public final JsonFactory configure(JsonGenerator.Feature feature, boolean z) {
                    return z ? enable(feature) : disable(feature);
                }

       public JsonFactory enable(JsonGenerator.Feature feature) {
           this._generatorFeatures |= feature.getMask();
           return this;
                }

       public JsonFactory disable(JsonGenerator.Feature feature) {
           this._generatorFeatures &= feature.getMask() ^ (-1);
           return this;
                }

       public final boolean isEnabled(JsonGenerator.Feature feature) {
                    return (this._generatorFeatures & feature.getMask()) != 0;
                }

       public CharacterEscapes getCharacterEscapes() {
           return this._characterEscapes;
                }

       public JsonFactory setCharacterEscapes(CharacterEscapes characterEscapes) {
           this._characterEscapes = characterEscapes;
           return this;
                }

       public OutputDecorator getOutputDecorator() {
           return this._outputDecorator;
                }

       public JsonFactory setOutputDecorator(OutputDecorator outputDecorator) {
           this._outputDecorator = outputDecorator;
           return this;
                }

       public JsonFactory setRootValueSeparator(String str) {
           this._rootValueSeparator = str == null ? null : new SerializedString(str);
           return this;
                }

       public String getRootValueSeparator() {
           if (this._rootValueSeparator == null) {
                        return null;
                    }
                    return this._rootValueSeparator.getValue();
                }

       public JsonFactory setCodec(ObjectCodec objectCodec) {
           this._objectCodec = objectCodec;
           return this;
                }

       public ObjectCodec getCodec() {
           return this._objectCodec;
                }

       public JsonParser createParser(File file) throws IOException {
           IOContext iOContext_createContext = _createContext(file, true);
           InputStream fileInputStream = new FileInputStream(file);
           if (this._inputDecorator != null) {
               fileInputStream = this._inputDecorator.decorate(iOContext_createContext, fileInputStream);
                    }
           return _createParser(fileInputStream, iOContext_createContext);
                }

       public JsonParser createParser(URL url) throws IOException {
           IOContext iOContext_createContext = _createContext(url, true);
           InputStream inputStream_optimizedStreamFromURL = _optimizedStreamFromURL(url);
           if (this._inputDecorator != null) {
               inputStream_optimizedStreamFromURL = this._inputDecorator.decorate(iOContext_createContext, inputStream_optimizedStreamFromURL);
                    }
           return _createParser(inputStream_optimizedStreamFromURL, iOContext_createContext);
                }

       public JsonParser createParser(InputStream inputStream) throws IOException {
           IOContext iOContext_createContext = _createContext(inputStream, false);
           if (this._inputDecorator != null) {
               inputStream = this._inputDecorator.decorate(iOContext_createContext, inputStream);
                    }
           return _createParser(inputStream, iOContext_createContext);
                }

       public JsonParser createParser(Reader reader) throws IOException {
           IOContext iOContext_createContext = _createContext(reader, false);
           if (this._inputDecorator != null) {
               reader = this._inputDecorator.decorate(iOContext_createContext, reader);
                    }
           return _createParser(reader, iOContext_createContext);
                }

       public JsonParser createParser(byte[] bArr) throws IOException {
                    InputStream inputStreamDecorate;
           IOContext iOContext_createContext = _createContext(bArr, true);
                    return (this._inputDecorator == null || (inputStreamDecorate = this._inputDecorator.decorate(iOContext_createContext, bArr, 0, bArr.length)) == null) ? _createParser(bArr, 0, bArr.length, iOContext_createContext) : _createParser(inputStreamDecorate, iOContext_createContext);
                }

       public JsonParser createParser(byte[] bArr, int i, int i2) throws IOException {
                    InputStream inputStreamDecorate;
           IOContext iOContext_createContext = _createContext(bArr, true);
                    return (this._inputDecorator == null || (inputStreamDecorate = this._inputDecorator.decorate(iOContext_createContext, bArr, i, i2)) == null) ? _createParser(bArr, i, i2, iOContext_createContext) : _createParser(inputStreamDecorate, iOContext_createContext);
                }

       public JsonParser createParser(String str) throws IOException {
           Reader stringReader = new StringReader(str);
           IOContext iOContext_createContext = _createContext(stringReader, true);
           if (this._inputDecorator != null) {
               stringReader = this._inputDecorator.decorate(iOContext_createContext, stringReader);
                    }
           return _createParser(stringReader, iOContext_createContext);
                }

                @Deprecated
       public JsonParser createJsonParser(File file) throws IOException {
           return createParser(file);
                }

                @Deprecated
       public JsonParser createJsonParser(URL url) throws IOException {
           return createParser(url);
                }

                @Deprecated
       public JsonParser createJsonParser(InputStream inputStream) throws IOException {
           return createParser(inputStream);
                }

                @Deprecated
       public JsonParser createJsonParser(Reader reader) throws IOException {
           return createParser(reader);
                }

                @Deprecated
       public JsonParser createJsonParser(byte[] bArr) throws IOException {
           return createParser(bArr);
                }

                @Deprecated
       public JsonParser createJsonParser(byte[] bArr, int i, int i2) throws IOException {
           return createParser(bArr, i, i2);
                }

                @Deprecated
       public JsonParser createJsonParser(String str) throws IOException {
           return createParser(str);
                }

      public JsonGenerator createGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException {
          IOContext iOContext_createContext = _createContext(outputStream, false);
          iOContext_createContext.setEncoding(jsonEncoding);
          if (jsonEncoding == JsonEncoding.UTF8) {
              if (this._outputDecorator != null) {
                  outputStream = this._outputDecorator.decorate(iOContext_createContext, outputStream);
                        }
              return _createUTF8Generator(outputStream, iOContext_createContext);
                    }
          Writer writer_createWriter = _createWriter(outputStream, jsonEncoding, iOContext_createContext);
          if (this._outputDecorator != null) {
              writer_createWriter = this._outputDecorator.decorate(iOContext_createContext, writer_createWriter);
                    }
          return _createGenerator(writer_createWriter, iOContext_createContext);
                }

      public JsonGenerator createGenerator(OutputStream outputStream) throws IOException {
          return createGenerator(outputStream, JsonEncoding.UTF8);
                }

      public JsonGenerator createGenerator(Writer writer) throws IOException {
          IOContext iOContext_createContext = _createContext(writer, false);
          if (this._outputDecorator != null) {
              writer = this._outputDecorator.decorate(iOContext_createContext, writer);
                    }
          return _createGenerator(writer, iOContext_createContext);
                }

      public JsonGenerator createGenerator(File file, JsonEncoding jsonEncoding) throws IOException {
          OutputStream fileOutputStream = new FileOutputStream(file);
          IOContext iOContext_createContext = _createContext(fileOutputStream, true);
          iOContext_createContext.setEncoding(jsonEncoding);
          if (jsonEncoding == JsonEncoding.UTF8) {
              if (this._outputDecorator != null) {
                  fileOutputStream = this._outputDecorator.decorate(iOContext_createContext, fileOutputStream);
                        }
              return _createUTF8Generator(fileOutputStream, iOContext_createContext);
                    }
          Writer writer_createWriter = _createWriter(fileOutputStream, jsonEncoding, iOContext_createContext);
          if (this._outputDecorator != null) {
              writer_createWriter = this._outputDecorator.decorate(iOContext_createContext, writer_createWriter);
                    }
          return _createGenerator(writer_createWriter, iOContext_createContext);
                }

                @Deprecated
      public JsonGenerator createJsonGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException {
          return createGenerator(outputStream, jsonEncoding);
                }

                @Deprecated
      public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
          return createGenerator(writer);
                }

                @Deprecated
      public JsonGenerator createJsonGenerator(OutputStream outputStream) throws IOException {
          return createGenerator(outputStream, JsonEncoding.UTF8);
                }

                @Deprecated
      public JsonGenerator createJsonGenerator(File file, JsonEncoding jsonEncoding) throws IOException {
          return createGenerator(file, jsonEncoding);
                }

      protected JsonParser _createParser(InputStream inputStream, IOContext iOContext) throws IOException {
          return new ByteSourceJsonBootstrapper(iOContext, inputStream).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES));
                }

                @Deprecated
      protected JsonParser _createJsonParser(InputStream inputStream, IOContext iOContext) throws IOException {
          return _createParser(inputStream, iOContext);
                }

      protected JsonParser _createParser(Reader reader, IOContext iOContext) throws IOException {
          return new ReaderBasedJsonParser(iOContext, this._parserFeatures, reader, this._objectCodec, this._rootCharSymbols.makeChild(isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES)));
                }

                @Deprecated
      protected JsonParser _createJsonParser(Reader reader, IOContext iOContext) throws IOException {
          return _createParser(reader, iOContext);
                }

      protected JsonParser _createParser(byte[] bArr, int i, int i2, IOContext iOContext) throws IOException {
          return new ByteSourceJsonBootstrapper(iOContext, bArr, i, i2).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES));
                }

                @Deprecated
      protected JsonParser _createJsonParser(byte[] bArr, int i, int i2, IOContext iOContext) throws IOException {
          return _createParser(bArr, i, i2, iOContext);
                }

      protected JsonGenerator _createGenerator(Writer writer, IOContext iOContext) throws IOException {
          WriterBasedJsonGenerator writerBasedJsonGenerator = new WriterBasedJsonGenerator(iOContext, this._generatorFeatures, this._objectCodec, writer);
          if (this._characterEscapes != null) {
              writerBasedJsonGenerator.setCharacterEscapes(this._characterEscapes);
                    }
          SerializableString serializableString = this._rootValueSeparator;
          if (serializableString != DEFAULT_ROOT_VALUE_SEPARATOR) {
              writerBasedJsonGenerator.setRootValueSeparator(serializableString);
                    }
          return writerBasedJsonGenerator;
                }

                @Deprecated
      protected JsonGenerator _createJsonGenerator(Writer writer, IOContext iOContext) throws IOException {
          return _createGenerator(writer, iOContext);
                }

      protected JsonGenerator _createUTF8Generator(OutputStream outputStream, IOContext iOContext) throws IOException {
          UTF8JsonGenerator uTF8JsonGenerator = new UTF8JsonGenerator(iOContext, this._generatorFeatures, this._objectCodec, outputStream);
          if (this._characterEscapes != null) {
              uTF8JsonGenerator.setCharacterEscapes(this._characterEscapes);
                    }
          SerializableString serializableString = this._rootValueSeparator;
          if (serializableString != DEFAULT_ROOT_VALUE_SEPARATOR) {
              uTF8JsonGenerator.setRootValueSeparator(serializableString);
                    }
          return uTF8JsonGenerator;
                }

                @Deprecated
      protected JsonGenerator _createUTF8JsonGenerator(OutputStream outputStream, IOContext iOContext) throws IOException {
          return _createUTF8Generator(outputStream, iOContext);
                }

      protected Writer _createWriter(OutputStream outputStream, JsonEncoding jsonEncoding, IOContext iOContext) throws IOException {
                    return jsonEncoding == JsonEncoding.UTF8 ? new UTF8Writer(iOContext, outputStream) : new OutputStreamWriter(outputStream, jsonEncoding.getJavaName());
                }

      protected IOContext _createContext(Object obj, boolean z) {
          return new IOContext(_getBufferRecycler(), obj, z);
                }

      public BufferRecycler _getBufferRecycler() {
          SoftReference<BufferRecycler> softReference = _recyclerRef.get();
          BufferRecycler bufferRecycler = softReference == null ? null : softReference.get();
          if (bufferRecycler == null) {
              BufferRecycler bufferRecycler2 = new BufferRecycler();
              _recyclerRef.set(new SoftReference<>(bufferRecycler2));
              return bufferRecycler2;
                    }
          return bufferRecycler;
                }

      protected InputStream _optimizedStreamFromURL(URL url) throws IOException {
                    String host;
                    return (!CommonUtil.FILE.equals(url.getProtocol()) || !((host = url.getHost()) == null || host.length() == 0) || url.getPath().indexOf(37) >= 0) ? url.openStream() : new FileInputStream(url.getPath());
                }
            }
