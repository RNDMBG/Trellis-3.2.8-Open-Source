            package com.fasterxml.jackson.core.util;

            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.JsonGenerator;
            import com.fasterxml.jackson.core.PrettyPrinter;
            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.p003io.SerializedString;
            import java.io.IOException;
            import java.io.Serializable;
            import java.util.Arrays;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable {
                public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                private static final long serialVersionUID = -5512586643324525213L;
                protected Indenter _arrayIndenter;
                protected transient int _nesting;
                protected Indenter _objectIndenter;
                protected final SerializableString _rootSeparator;
                protected boolean _spacesInObjectEntries;

                public interface Indenter {
                    boolean isInline();

                    void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException;
                }


/* 92 */        public DefaultPrettyPrinter() {
/* 93 */            this(DEFAULT_ROOT_VALUE_SEPARATOR);
                }

/* 107 */       public DefaultPrettyPrinter(String str) {
/* 108 */           this(str == null ? null : new SerializedString(str));
                }

/* 118 */       public DefaultPrettyPrinter(SerializableString serializableString) {
/* 54 */            this._arrayIndenter = FixedSpaceIndenter.instance;
/* 62 */            this._objectIndenter = Lf2SpacesIndenter.instance;
/* 76 */            this._spacesInObjectEntries = true;
/* 84 */            this._nesting = 0;
/* 120 */           this._rootSeparator = serializableString;
                }

/* 123 */       public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter) {
/* 124 */           this(defaultPrettyPrinter, defaultPrettyPrinter._rootSeparator);
                }

/* 128 */       public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter, SerializableString serializableString) {
/* 54 */            this._arrayIndenter = FixedSpaceIndenter.instance;
/* 62 */            this._objectIndenter = Lf2SpacesIndenter.instance;
/* 76 */            this._spacesInObjectEntries = true;
/* 84 */            this._nesting = 0;
/* 130 */           this._arrayIndenter = defaultPrettyPrinter._arrayIndenter;
/* 131 */           this._objectIndenter = defaultPrettyPrinter._objectIndenter;
/* 132 */           this._spacesInObjectEntries = defaultPrettyPrinter._spacesInObjectEntries;
/* 133 */           this._nesting = defaultPrettyPrinter._nesting;
/* 135 */           this._rootSeparator = serializableString;
                }

/* 139 */       public DefaultPrettyPrinter withRootSeparator(SerializableString serializableString) {
/* 140 */           if (this._rootSeparator != serializableString) {
                        return (serializableString == null || !serializableString.equals(this._rootSeparator)) ? new DefaultPrettyPrinter(this, serializableString) : this;
                    }
/* 144 */           return this;
                }

/* 147 */       public void indentArraysWith(Indenter indenter) {
/* 148 */           if (indenter == null) {
                        indenter = NopIndenter.instance;
                    }
                    this._arrayIndenter = indenter;
                }

/* 151 */       public void indentObjectsWith(Indenter indenter) {
/* 152 */           if (indenter == null) {
                        indenter = NopIndenter.instance;
                    }
                    this._objectIndenter = indenter;
                }

                @Deprecated
/* 158 */       public void spacesInObjectEntries(boolean z) {
/* 159 */           this._spacesInObjectEntries = z;
                }

/* 164 */       public DefaultPrettyPrinter withArrayIndenter(Indenter indenter) {
/* 165 */           if (indenter == null) {
/* 166 */               indenter = NopIndenter.instance;
                    }
/* 168 */           if (this._arrayIndenter != indenter) {
/* 171 */               DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
/* 172 */               defaultPrettyPrinter._arrayIndenter = indenter;
                        return defaultPrettyPrinter;
                    }
/* 173 */           return this;
                }

/* 179 */       public DefaultPrettyPrinter withObjectIndenter(Indenter indenter) {
/* 180 */           if (indenter == null) {
/* 181 */               indenter = NopIndenter.instance;
                    }
/* 183 */           if (this._objectIndenter != indenter) {
/* 186 */               DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
/* 187 */               defaultPrettyPrinter._objectIndenter = indenter;
                        return defaultPrettyPrinter;
                    }
/* 188 */           return this;
                }

/* 199 */       public DefaultPrettyPrinter withSpacesInObjectEntries() {
/* 200 */           return _withSpaces(true);
                }

/* 211 */       public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
/* 212 */           return _withSpaces(false);
                }

/* 216 */       protected DefaultPrettyPrinter _withSpaces(boolean z) {
/* 217 */           if (this._spacesInObjectEntries != z) {
/* 220 */               DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
/* 221 */               defaultPrettyPrinter._spacesInObjectEntries = z;
                        return defaultPrettyPrinter;
                    }
/* 222 */           return this;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.fasterxml.jackson.core.util.Instantiatable
/* 232 */       public DefaultPrettyPrinter createInstance() {
/* 233 */           return new DefaultPrettyPrinter(this);
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 245 */       public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException {
/* 246 */           if (this._rootSeparator != null) {
/* 247 */               jsonGenerator.writeRaw(this._rootSeparator);
                    }
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 254 */       public void writeStartObject(JsonGenerator jsonGenerator) throws IOException {
/* 255 */           jsonGenerator.writeRaw('{');
/* 256 */           if (!this._objectIndenter.isInline()) {
/* 257 */               this._nesting++;
                    }
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 264 */       public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException {
/* 265 */           this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 280 */       public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException {
/* 281 */           if (this._spacesInObjectEntries) {
/* 282 */               jsonGenerator.writeRaw(" : ");
                    } else {
/* 284 */               jsonGenerator.writeRaw(CommonUtil.MARKER_COLON);
                    }
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 300 */       public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException {
/* 301 */           jsonGenerator.writeRaw(',');
/* 302 */           this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 308 */       public void writeEndObject(JsonGenerator jsonGenerator, int i) throws IOException {
/* 309 */           if (!this._objectIndenter.isInline()) {
/* 310 */               this._nesting--;
                    }
/* 312 */           if (i > 0) {
/* 313 */               this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
                    } else {
/* 315 */               jsonGenerator.writeRaw(' ');
                    }
/* 317 */           jsonGenerator.writeRaw('}');
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 323 */       public void writeStartArray(JsonGenerator jsonGenerator) throws IOException {
/* 324 */           if (!this._arrayIndenter.isInline()) {
/* 325 */               this._nesting++;
                    }
/* 327 */           jsonGenerator.writeRaw('[');
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 333 */       public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException {
/* 334 */           this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 349 */       public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException {
/* 350 */           jsonGenerator.writeRaw(',');
/* 351 */           this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override // com.fasterxml.jackson.core.PrettyPrinter
/* 357 */       public void writeEndArray(JsonGenerator jsonGenerator, int i) throws IOException {
/* 358 */           if (!this._arrayIndenter.isInline()) {
/* 359 */               this._nesting--;
                    }
/* 361 */           if (i > 0) {
/* 362 */               this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
                    } else {
/* 364 */               jsonGenerator.writeRaw(' ');
                    }
/* 366 */           jsonGenerator.writeRaw(']');
                }

/* 384 */       public static class NopIndenter implements Indenter, Serializable {
                    public static final NopIndenter instance = new NopIndenter();

                    @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
/* 385 */           public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
                    }

                    @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
/* 388 */           public boolean isInline() {
/* 389 */               return true;
                    }
                }

/* 404 */       public static class FixedSpaceIndenter extends NopIndenter {
                    public static final FixedSpaceIndenter instance = new FixedSpaceIndenter();

                    @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.NopIndenter, com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
/* 405 */           public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
/* 406 */               jsonGenerator.writeRaw(' ');
                    }

                    @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.NopIndenter, com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
/* 409 */           public boolean isInline() {
/* 410 */               return true;
                    }
                }

/* 419 */       public static class Lf2SpacesIndenter extends NopIndenter {
                    static final char[] SPACES;
                    static final int SPACE_COUNT = 64;
                    private static final String SYS_LF;
                    public static final Lf2SpacesIndenter instance;
                    protected final String _lf;

                    static {
/* 421 */               String property = null;
                        try {
/* 423 */                   property = System.getProperty("line.separator");
                        } catch (Throwable th) {
                        }
/* 425 */               if (property == null) {
                            property = IOUtils.LINE_SEPARATOR_UNIX;
                        }
                        SYS_LF = property;
/* 429 */               SPACES = new char[64];
/* 431 */               Arrays.fill(SPACES, ' ');
/* 435 */               instance = new Lf2SpacesIndenter();
                    }

/* 441 */           public Lf2SpacesIndenter() {
/* 442 */               this(SYS_LF);
                    }

/* 446 */           public Lf2SpacesIndenter(String str) {
/* 448 */               this._lf = str;
                    }

/* 458 */           public Lf2SpacesIndenter withLinefeed(String str) {
                        return str.equals(this._lf) ? this : new Lf2SpacesIndenter(str);
                    }

                    @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.NopIndenter, com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
/* 465 */           public boolean isInline() {
/* 466 */               return false;
                    }

                    @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.NopIndenter, com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
/* 471 */           public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
/* 472 */               jsonGenerator.writeRaw(this._lf);
/* 473 */               if (i > 0) {
/* 474 */                   int length = i + i;
/* 475 */                   while (length > 64) {
/* 476 */                       jsonGenerator.writeRaw(SPACES, 0, 64);
/* 477 */                       length -= SPACES.length;
                            }
/* 479 */                   jsonGenerator.writeRaw(SPACES, 0, length);
                        }
                    }
                }
            }
