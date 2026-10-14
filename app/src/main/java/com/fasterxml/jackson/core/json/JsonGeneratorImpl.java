            package com.fasterxml.jackson.core.json;

            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.ObjectCodec;
            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.base.GeneratorBase;
            import com.fasterxml.jackson.core.p003io.CharTypes;
            import com.fasterxml.jackson.core.p003io.CharacterEscapes;
            import com.fasterxml.jackson.core.p003io.IOContext;
            import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
            import com.fasterxml.jackson.core.util.VersionUtil;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 93 */    public abstract class JsonGeneratorImpl extends GeneratorBase {
                protected static final int[] sOutputEscapes = CharTypes.get7BitOutputEscapes();
                protected CharacterEscapes _characterEscapes;
                protected final IOContext _ioContext;
                protected int _maximumNonEscapedChar;
                protected int[] _outputEscapes;
                protected SerializableString _rootValueSeparator;

/* 94 */        public JsonGeneratorImpl(IOContext iOContext, int i, ObjectCodec objectCodec) {
/* 95 */            super(i, objectCodec);
/* 53 */            this._outputEscapes = sOutputEscapes;
/* 84 */            this._rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/* 96 */            this._ioContext = iOContext;
/* 97 */            if (isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
/* 98 */                setHighestNonEscapedChar(127);
                    }
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 109 */       public JsonGenerator setHighestNonEscapedChar(int i) {
/* 110 */           if (i < 0) {
                        i = 0;
                    }
                    this._maximumNonEscapedChar = i;
/* 111 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 115 */       public int getHighestEscapedChar() {
/* 116 */           return this._maximumNonEscapedChar;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 121 */       public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
/* 122 */           this._characterEscapes = characterEscapes;
/* 123 */           if (characterEscapes == null) {
/* 124 */               this._outputEscapes = sOutputEscapes;
                    } else {
/* 126 */               this._outputEscapes = characterEscapes.getEscapeCodesForAscii();
                    }
/* 128 */           return this;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 136 */       public CharacterEscapes getCharacterEscapes() {
/* 137 */           return this._characterEscapes;
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 141 */       public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
/* 142 */           this._rootValueSeparator = serializableString;
/* 143 */           return this;
                }

                @Override // com.fasterxml.jackson.core.base.GeneratorBase, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.core.Versioned
/* 153 */       public Version version() {
/* 154 */           return VersionUtil.versionFor(getClass());
                }

                @Override // com.fasterxml.jackson.core.JsonGenerator
/* 168 */       public final void writeStringField(String str, String str2) throws IOException {
/* 169 */           writeFieldName(str);
/* 170 */           writeString(str2);
                }
            }
