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
            
            
    public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable {
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


        public DefaultPrettyPrinter() {
            this(DEFAULT_ROOT_VALUE_SEPARATOR);
                }

       public DefaultPrettyPrinter(String str) {
           this(str == null ? null : new SerializedString(str));
                }

       public DefaultPrettyPrinter(SerializableString serializableString) {
            this._arrayIndenter = FixedSpaceIndenter.instance;
            this._objectIndenter = Lf2SpacesIndenter.instance;
            this._spacesInObjectEntries = true;
            this._nesting = 0;
           this._rootSeparator = serializableString;
                }

       public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter) {
           this(defaultPrettyPrinter, defaultPrettyPrinter._rootSeparator);
                }

       public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter, SerializableString serializableString) {
            this._arrayIndenter = FixedSpaceIndenter.instance;
            this._objectIndenter = Lf2SpacesIndenter.instance;
            this._spacesInObjectEntries = true;
            this._nesting = 0;
           this._arrayIndenter = defaultPrettyPrinter._arrayIndenter;
           this._objectIndenter = defaultPrettyPrinter._objectIndenter;
           this._spacesInObjectEntries = defaultPrettyPrinter._spacesInObjectEntries;
           this._nesting = defaultPrettyPrinter._nesting;
           this._rootSeparator = serializableString;
                }

       public DefaultPrettyPrinter withRootSeparator(SerializableString serializableString) {
           if (this._rootSeparator != serializableString) {
                        return (serializableString == null || !serializableString.equals(this._rootSeparator)) ? new DefaultPrettyPrinter(this, serializableString) : this;
                    }
           return this;
                }

       public void indentArraysWith(Indenter indenter) {
           if (indenter == null) {
                        indenter = NopIndenter.instance;
                    }
                    this._arrayIndenter = indenter;
                }

       public void indentObjectsWith(Indenter indenter) {
           if (indenter == null) {
                        indenter = NopIndenter.instance;
                    }
                    this._objectIndenter = indenter;
                }

                @Deprecated
       public void spacesInObjectEntries(boolean z) {
           this._spacesInObjectEntries = z;
                }

       public DefaultPrettyPrinter withArrayIndenter(Indenter indenter) {
           if (indenter == null) {
               indenter = NopIndenter.instance;
                    }
           if (this._arrayIndenter != indenter) {
               DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
               defaultPrettyPrinter._arrayIndenter = indenter;
                        return defaultPrettyPrinter;
                    }
           return this;
                }

       public DefaultPrettyPrinter withObjectIndenter(Indenter indenter) {
           if (indenter == null) {
               indenter = NopIndenter.instance;
                    }
           if (this._objectIndenter != indenter) {
               DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
               defaultPrettyPrinter._objectIndenter = indenter;
                        return defaultPrettyPrinter;
                    }
           return this;
                }

       public DefaultPrettyPrinter withSpacesInObjectEntries() {
           return _withSpaces(true);
                }

       public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
           return _withSpaces(false);
                }

       protected DefaultPrettyPrinter _withSpaces(boolean z) {
           if (this._spacesInObjectEntries != z) {
               DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
               defaultPrettyPrinter._spacesInObjectEntries = z;
                        return defaultPrettyPrinter;
                    }
           return this;
                }

                
                @Override 
       public DefaultPrettyPrinter createInstance() {
           return new DefaultPrettyPrinter(this);
                }

                @Override 
       public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException {
           if (this._rootSeparator != null) {
               jsonGenerator.writeRaw(this._rootSeparator);
                    }
                }

                @Override 
       public void writeStartObject(JsonGenerator jsonGenerator) throws IOException {
           jsonGenerator.writeRaw('{');
           if (!this._objectIndenter.isInline()) {
               this._nesting++;
                    }
                }

                @Override 
       public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException {
           this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override 
       public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException {
           if (this._spacesInObjectEntries) {
               jsonGenerator.writeRaw(" : ");
                    } else {
               jsonGenerator.writeRaw(CommonUtil.MARKER_COLON);
                    }
                }

                @Override 
       public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException {
           jsonGenerator.writeRaw(',');
           this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override 
       public void writeEndObject(JsonGenerator jsonGenerator, int i) throws IOException {
           if (!this._objectIndenter.isInline()) {
               this._nesting--;
                    }
           if (i > 0) {
               this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
                    } else {
               jsonGenerator.writeRaw(' ');
                    }
           jsonGenerator.writeRaw('}');
                }

                @Override 
       public void writeStartArray(JsonGenerator jsonGenerator) throws IOException {
           if (!this._arrayIndenter.isInline()) {
               this._nesting++;
                    }
           jsonGenerator.writeRaw('[');
                }

                @Override 
       public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException {
           this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override 
       public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException {
           jsonGenerator.writeRaw(',');
           this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
                }

                @Override 
       public void writeEndArray(JsonGenerator jsonGenerator, int i) throws IOException {
           if (!this._arrayIndenter.isInline()) {
               this._nesting--;
                    }
           if (i > 0) {
               this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
                    } else {
               jsonGenerator.writeRaw(' ');
                    }
           jsonGenerator.writeRaw(']');
                }

       public static class NopIndenter implements Indenter, Serializable {
                    public static final NopIndenter instance = new NopIndenter();

                    @Override 
           public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
                    }

                    @Override 
           public boolean isInline() {
               return true;
                    }
                }

       public static class FixedSpaceIndenter extends NopIndenter {
                    public static final FixedSpaceIndenter instance = new FixedSpaceIndenter();

                    @Override 
           public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
               jsonGenerator.writeRaw(' ');
                    }

                    @Override 
           public boolean isInline() {
               return true;
                    }
                }

       public static class Lf2SpacesIndenter extends NopIndenter {
                    static final char[] SPACES;
                    static final int SPACE_COUNT = 64;
                    private static final String SYS_LF;
                    public static final Lf2SpacesIndenter instance;
                    protected final String _lf;

                    static {
               String property = null;
                        try {
                   property = System.getProperty("line.separator");
                        } catch (Throwable th) {
                        }
               if (property == null) {
                            property = IOUtils.LINE_SEPARATOR_UNIX;
                        }
                        SYS_LF = property;
               SPACES = new char[64];
               Arrays.fill(SPACES, ' ');
               instance = new Lf2SpacesIndenter();
                    }

           public Lf2SpacesIndenter() {
               this(SYS_LF);
                    }

           public Lf2SpacesIndenter(String str) {
               this._lf = str;
                    }

           public Lf2SpacesIndenter withLinefeed(String str) {
                        return str.equals(this._lf) ? this : new Lf2SpacesIndenter(str);
                    }

                    @Override 
           public boolean isInline() {
               return false;
                    }

                    @Override 
           public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
               jsonGenerator.writeRaw(this._lf);
               if (i > 0) {
                   int length = i + i;
                   while (length > 64) {
                       jsonGenerator.writeRaw(SPACES, 0, 64);
                       length -= SPACES.length;
                            }
                   jsonGenerator.writeRaw(SPACES, 0, length);
                        }
                    }
                }
            }
