            package com.googlecode.javacpp;

            import co.vine.android.provider.Vine;
            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.BufferedReader;
            import java.io.Closeable;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileReader;
            import java.io.FileWriter;
            import java.io.IOException;
            import java.io.Reader;
            import java.io.StringReader;
            import java.io.Writer;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.LinkedHashMap;
            import java.util.LinkedList;
            import java.util.Properties;
            import java.util.Scanner;
            import java.util.TreeSet;
            import org.apache.commons.io.FilenameUtils;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class Parser {
                InfoMap infoMap;
                Properties properties;
                Token[] tokenArray = null;
                int tokenIndex = 0;

                public interface InfoMapper {
                    void map(InfoMap infoMap);
                }

/* 37 */        public static class Exception extends java.lang.Exception {
/* 38 */            public Exception(String message) {
/* 39 */                super(message);
                    }

/* 39 */            public Exception(String message, Throwable cause) {
/* 40 */                super(message, cause);
                    }
                }

/* 41 */        public static class Info implements Cloneable {
                    String[] annotations;
                    boolean cast;
                    boolean complex;
                    String[] cppNames;
                    boolean define;
                    boolean forwardDeclared;
                    String[] genericTypes;
                    String[] javaNames;
                    String[] pointerTypes;
                    String[] primitiveTypes;
                    String textAfter;
                    String textBefore;

                    /* JADX INFO: renamed from: clone, reason: collision with other method in class */

/* 43 */            public Info() {
/* 47 */                this.cppNames = null;
                        this.javaNames = null;
                        this.annotations = null;
                        this.primitiveTypes = null;
                        this.pointerTypes = null;
                        this.genericTypes = null;
/* 49 */                this.cast = false;
                        this.define = false;
                        this.complex = false;
                        this.forwardDeclared = false;
/* 50 */                this.textBefore = null;
                        this.textAfter = null;
                    }

/* 44 */            public Info(String... cppNames) {
/* 47 */                this.cppNames = null;
                        this.javaNames = null;
                        this.annotations = null;
                        this.primitiveTypes = null;
                        this.pointerTypes = null;
                        this.genericTypes = null;
/* 49 */                this.cast = false;
                        this.define = false;
                        this.complex = false;
                        this.forwardDeclared = false;
/* 50 */                this.textBefore = null;
                        this.textAfter = null;
/* 45 */                this.cppNames = cppNames;
                    }

/* 51 */            public Info cppNames(String... cppNames) {
/* 52 */                this.cppNames = cppNames;
                        return this;
                    }

/* 52 */            public Info javaNames(String... javaNames) {
/* 53 */                this.javaNames = javaNames;
                        return this;
                    }

/* 53 */            public Info annotations(String... annotations) {
/* 54 */                this.annotations = annotations;
                        return this;
                    }

/* 54 */            public Info primitiveTypes(String... primitiveTypes) {
/* 55 */                this.primitiveTypes = primitiveTypes;
                        return this;
                    }

/* 55 */            public Info pointerTypes(String... pointerTypes) {
/* 56 */                this.pointerTypes = pointerTypes;
                        return this;
                    }

/* 56 */            public Info genericTypes(String... genericTypes) {
/* 57 */                this.genericTypes = genericTypes;
                        return this;
                    }

/* 57 */            public Info cast(boolean cast) {
/* 58 */                this.cast = cast;
                        return this;
                    }

/* 58 */            public Info define(boolean define) {
/* 59 */                this.define = define;
                        return this;
                    }

/* 59 */            public Info complex(boolean complex) {
/* 60 */                this.complex = complex;
                        return this;
                    }

/* 60 */            public Info forwardDeclared(boolean forwardDeclared) {
/* 61 */                this.forwardDeclared = forwardDeclared;
                        return this;
                    }

/* 61 */            public Info textBefore(String textBefore) {
/* 62 */                this.textBefore = textBefore;
                        return this;
                    }

/* 62 */            public Info textAfter(String textAfter) {
/* 63 */                this.textAfter = textAfter;
                        return this;
                    }

/* 65 */            public Info clone() {
/* 66 */                Info i = new Info();
/* 67 */                i.cppNames = this.cppNames != null ? (String[]) this.cppNames.clone() : null;
/* 68 */                i.javaNames = this.javaNames != null ? (String[]) this.javaNames.clone() : null;
/* 69 */                i.annotations = this.annotations != null ? (String[]) this.annotations.clone() : null;
/* 70 */                i.primitiveTypes = this.primitiveTypes != null ? (String[]) this.primitiveTypes.clone() : null;
/* 71 */                i.pointerTypes = this.pointerTypes != null ? (String[]) this.pointerTypes.clone() : null;
/* 72 */                i.genericTypes = this.genericTypes != null ? (String[]) this.genericTypes.clone() : null;
/* 73 */                i.cast = this.cast;
/* 74 */                i.define = this.define;
/* 75 */                i.forwardDeclared = this.forwardDeclared;
/* 76 */                i.textBefore = this.textBefore;
/* 77 */                i.textAfter = this.textAfter;
/* 78 */                return i;
                    }
                }

/* 81 */        public static class InfoMap extends HashMap<String, LinkedList<Info>> {
                    static final InfoMap defaults = new InfoMap(null).put(new Info("void").primitiveTypes("void").pointerTypes("Pointer")).put(new Info("FILE").pointerTypes("Pointer").cast(true)).put(new Info("va_list").pointerTypes("Pointer").cast(true)).put(new Info("int8_t", "jbyte", "signed char").primitiveTypes("byte").pointerTypes("BytePointer", "ByteBuffer", "byte[]")).put(new Info("uint8_t", "char", "unsigned char").primitiveTypes("byte").pointerTypes("BytePointer", "ByteBuffer", "byte[]").cast(true)).put(new Info("int16_t", "jshort", "short", "signed short", "short int", "signed short int").primitiveTypes("short").pointerTypes("ShortPointer", "ShortBuffer", "short[]")).put(new Info("uint16_t", "unsigned short", "unsigned short int").primitiveTypes("short").pointerTypes("ShortPointer", "ShortBuffer", "short[]").cast(true)).put(new Info("int32_t", "jint", "int", "signed int", "signed").primitiveTypes("int").pointerTypes("IntPointer", "IntBuffer", "int[]")).put(new Info("uint32_t", "unsigned int", "unsigned").primitiveTypes("int").pointerTypes("IntPointer", "IntBuffer", "int[]").cast(true)).put(new Info("int64_t", "__int64", "jlong", "long long", "signed long long", "long long int", "signed long long int").primitiveTypes("long").pointerTypes("LongPointer", "LongBuffer", "long[]")).put(new Info("uint64_t", "__uint64", "unsigned long long", "unsigned long long int").primitiveTypes("long").pointerTypes("LongPointer", "LongBuffer", "long[]").cast(true)).put(new Info("long", "signed long", "long int", "signed long int").primitiveTypes("long").pointerTypes("CLongPointer")).put(new Info("unsigned long", "unsigned long int").primitiveTypes("long").pointerTypes("CLongPointer").cast(true)).put(new Info("size_t").primitiveTypes("long").pointerTypes("SizeTPointer")).put(new Info("float", "jfloat").primitiveTypes("float").pointerTypes("FloatPointer", "FloatBuffer", "float[]")).put(new Info("double", "jdouble").primitiveTypes("double").pointerTypes("DoublePointer", "DoubleBuffer", "double[]")).put(new Info("bool", "jboolean").primitiveTypes("boolean").pointerTypes("BoolPointer").cast(true)).put(new Info("const char").primitiveTypes("byte").pointerTypes("@Cast(\"const char*\") BytePointer", "String")).put(new Info("position").javaNames("_position")).put(new Info(Vine.QUERY_PARAMETER_LIMIT).javaNames("_limit")).put(new Info("capacity").javaNames("_capacity"));
                    InfoMap parent;

/* 82 */            public InfoMap() {
/* 86 */                this.parent = null;
/* 83 */                this.parent = defaults;
                    }

/* 83 */            public InfoMap(InfoMap parent) {
/* 86 */                this.parent = null;
/* 84 */                this.parent = parent;
                    }

/* 127 */           static String sort(String name) {
/* 128 */               return sort(name, false);
                    }

/* 130 */           static String sort(String name, boolean unconst) {
/* 131 */               if (name == null) {
/* 132 */                   return null;
                        }
/* 134 */               TreeSet<String> set = new TreeSet<>();
                        try {
/* 136 */                   Tokenizer tokenizer = new Tokenizer(new StringReader(name));
                            while (true) {
/* 138 */                       Token token = tokenizer.nextToken();
                                if (token.isEmpty()) {
                                    break;
                                }
/* 139 */                       set.add(token.value);
                            }
/* 144 */                   boolean foundConst = false;
/* 145 */                   String name2 = "";
                            for (String s : set) {
/* 147 */                       if ("const".equals(s)) {
/* 148 */                           foundConst = true;
                                } else {
/* 150 */                           name2 = name2 + s + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                                }
                            }
/* 153 */                   if (!unconst && foundConst) {
/* 154 */                       return "const " + name2.trim();
                            }
/* 156 */                   return name2.trim();
                        } catch (IOException ex) {
/* 142 */                   throw new RuntimeException(ex);
                        }
                    }

/* 160 */           public LinkedList<Info> get(String cppName) {
/* 161 */               String key = sort(cppName, false);
/* 162 */               LinkedList<Info> infoList = (LinkedList) super.get((Object) key);
/* 163 */               if (infoList == null) {
/* 164 */                   String key2 = sort(cppName, true);
/* 165 */                   infoList = (LinkedList) super.get((Object) key2);
                        }
/* 167 */               if (infoList == null && this.parent != null) {
/* 168 */                   infoList = this.parent.get(cppName);
                        }
/* 170 */               if (infoList == null) {
/* 171 */                   return new LinkedList<>();
                        }
/* 173 */               return infoList;
                    }

/* 176 */           public InfoMap put(Info info) {
/* 177 */               String[] arr$ = info.cppNames != null ? info.cppNames : new String[]{null};
                        for (String cppName : arr$) {
/* 178 */                   String key = sort(cppName, false);
/* 179 */                   LinkedList<Info> infoList = (LinkedList) super.get((Object) key);
/* 180 */                   if (infoList == null) {
/* 181 */                       infoList = new LinkedList<>();
                            }
/* 183 */                   if (!infoList.contains(info)) {
/* 184 */                       infoList.add(info);
                            }
/* 186 */                   super.put(key, infoList);
                        }
/* 188 */               return this;
                    }
                }

/* 194 */       static class Token implements Cloneable {
                    static final int COMMENT = 4;
                    static final int FLOAT = 2;
                    static final int IDENTIFIER = 5;
                    static final int INTEGER = 1;
                    static final int STRING = 3;
                    File file;
                    int lineNumber;
                    String spacing;
                    int type;
                    String value;
                    static final Token EOF = new Token();
                    static final Token CONST = new Token(5, "const");
                    static final Token DEFINE = new Token(5, "define");

                    /* JADX INFO: renamed from: IF */
                    static final Token f2531IF = new Token(5, "if");
                    static final Token IFDEF = new Token(5, "ifdef");
                    static final Token IFNDEF = new Token(5, "ifndef");
                    static final Token ELIF = new Token(5, "elif");
                    static final Token ELSE = new Token(5, "else");
                    static final Token ENDIF = new Token(5, "endif");
                    static final Token ENUM = new Token(5, "enum");
                    static final Token EXTERN = new Token(5, "extern");
                    static final Token INLINE = new Token(5, "inline");
                    static final Token STATIC = new Token(5, "static");
                    static final Token CLASS = new Token(5, "class");
                    static final Token STRUCT = new Token(5, "struct");
                    static final Token UNION = new Token(5, "union");
                    static final Token TEMPLATE = new Token(5, "template");
                    static final Token TYPEDEF = new Token(5, "typedef");
                    static final Token TYPENAME = new Token(5, "typename");

                    /* JADX INFO: renamed from: clone, reason: collision with other method in class */

/* 196 */           Token() {
/* 228 */               this.file = null;
/* 229 */               this.lineNumber = 0;
                        this.type = -1;
/* 230 */               this.spacing = "";
                        this.value = "";
                    }

/* 197 */           Token(int type, String value) {
/* 228 */               this.file = null;
/* 229 */               this.lineNumber = 0;
                        this.type = -1;
/* 230 */               this.spacing = "";
                        this.value = "";
/* 198 */               this.type = type;
                        this.value = value;
                    }

/* 232 */           boolean match(Object... tokens) {
/* 233 */               boolean found = false;
                        for (Object t : tokens) {
/* 235 */                   found = found || equals(t);
                        }
/* 237 */               return found;
                    }

/* 240 */           Token expect(Object... tokens) throws Exception {
/* 241 */               if (!match(tokens)) {
/* 242 */                   throw new Exception(this.file + ":" + this.lineNumber + ": Unexpected token '" + toString() + "'");
                        }
/* 244 */               return this;
                    }

/* 247 */           boolean isEmpty() {
                        return this.type == -1 && this.spacing.isEmpty();
                    }

/* 251 */           public Token clone() {
/* 252 */               Token t = new Token();
/* 253 */               t.file = this.file;
/* 254 */               t.lineNumber = this.lineNumber;
/* 255 */               t.type = this.type;
/* 256 */               t.spacing = this.spacing;
/* 257 */               t.value = this.value;
/* 258 */               return t;
                    }

/* 261 */           public boolean equals(Object obj) {
/* 262 */               if (obj == this) {
                            return true;
                        }
/* 264 */               if (obj == null) {
                            return false;
                        }
/* 266 */               if (obj.getClass() == Integer.class) {
                            return this.type == ((Integer) obj).intValue();
                        }
/* 268 */               if (obj.getClass() == Character.class) {
                            return this.type == ((Character) obj).charValue();
                        }
/* 270 */               if (obj.getClass() == String.class) {
/* 271 */                   return obj.equals(this.value);
                        }
/* 272 */               if (obj.getClass() != getClass()) {
                            return false;
                        }
/* 273 */               Token other = (Token) obj;
/* 274 */               if (this.type == other.type) {
                            if (this.value == null && other.value == null) {
                                return true;
                            }
                            if (this.value != null && this.value.equals(other.value)) {
                                return true;
                            }
                        }
                        return false;
                    }

/* 281 */           public int hashCode() {
/* 282 */               return this.type;
                    }

/* 285 */           public String toString() {
                        return (this.value == null || this.value.length() <= 0) ? String.valueOf((char) this.type) : this.value;
                    }
                }

/* 289 */       static class Tokenizer implements Closeable {
                    StringBuilder buffer;
                    File file;
                    int lastChar;
                    int lineNumber;
                    String lineSeparator;
                    Reader reader;

/* 290 */           Tokenizer(Reader reader) {
/* 299 */               this.file = null;
/* 300 */               this.reader = null;
/* 301 */               this.lineSeparator = null;
/* 302 */               this.lastChar = -1;
                        this.lineNumber = 1;
/* 303 */               this.buffer = new StringBuilder();
/* 292 */               this.reader = reader;
                    }

/* 293 */           Tokenizer(File file) throws FileNotFoundException {
/* 299 */               this.file = null;
/* 300 */               this.reader = null;
/* 301 */               this.lineSeparator = null;
/* 302 */               this.lastChar = -1;
                        this.lineNumber = 1;
/* 303 */               this.buffer = new StringBuilder();
/* 295 */               this.file = file;
/* 296 */               this.reader = new BufferedReader(new FileReader(file));
                    }

                    @Override // java.io.Closeable, java.lang.AutoCloseable
/* 305 */           public void close() throws IOException {
/* 306 */               this.reader.close();
                    }

/* 309 */           int readChar() throws IOException {
                        String str;
/* 310 */               if (this.lastChar != -1) {
/* 311 */                   int c = this.lastChar;
/* 312 */                   this.lastChar = -1;
                            return c;
                        }
/* 315 */               int c2 = this.reader.read();
/* 316 */               if (c2 == 13 || c2 == 10) {
/* 317 */                   this.lineNumber++;
/* 318 */                   int c3 = c2 == 13 ? this.reader.read() : -1;
/* 319 */                   if (this.lineSeparator == null) {
/* 320 */                       if (c2 == 13 && c3 == 10) {
                                    str = "\r\n";
                                } else {
/* 320 */                           str = c2 == 13 ? "\r" : IOUtils.LINE_SEPARATOR_UNIX;
                                }
                                this.lineSeparator = str;
                            }
/* 323 */                   if (c3 != 10) {
/* 324 */                       this.lastChar = c3;
                            }
/* 326 */                   c2 = 10;
                        }
                        return c2;
                    }

/* 331 */           public Token nextToken() throws IOException {
                        int c;
                        int c2;
                        int c3;
/* 332 */               Token token = new Token();
/* 333 */               int c4 = readChar();
/* 335 */               this.buffer.setLength(0);
/* 336 */               if (Character.isWhitespace(c4)) {
/* 337 */                   this.buffer.append((char) c4);
                            while (true) {
/* 338 */                       c4 = readChar();
                                if (c4 == -1 || !Character.isWhitespace(c4)) {
                                    break;
                                }
/* 339 */                       this.buffer.append((char) c4);
                            }
                        }
/* 342 */               token.file = this.file;
/* 343 */               token.lineNumber = this.lineNumber;
/* 344 */               token.spacing = this.buffer.toString();
/* 346 */               this.buffer.setLength(0);
/* 347 */               if (Character.isLetter(c4) || c4 == 95) {
/* 348 */                   token.type = 5;
/* 349 */                   this.buffer.append((char) c4);
                            while (true) {
/* 350 */                       c = readChar();
                                if (c == -1 || !(Character.isDigit(c) || Character.isLetter(c) || c == 95)) {
                                    break;
                                }
/* 351 */                       this.buffer.append((char) c);
                            }
/* 353 */                   token.value = this.buffer.toString();
/* 354 */                   this.lastChar = c;
                        } else if (Character.isDigit(c4) || c4 == 46 || c4 == 45 || c4 == 43) {
/* 356 */                   token.type = c4 == 46 ? 2 : 1;
/* 357 */                   this.buffer.append((char) c4);
/* 358 */                   int prevc = 0;
                            while (true) {
/* 359 */                       c2 = readChar();
                                if (c2 == -1 || !(Character.isDigit(c2) || c2 == 46 || c2 == 45 || c2 == 43 || ((c2 >= 97 && c2 <= 102) || c2 == 108 || c2 == 117 || c2 == 120 || ((c2 >= 65 && c2 <= 70) || c2 == 76 || c2 == 85 || c2 == 88)))) {
                                    break;
                                }
/* 362 */                       if (c2 == 46) {
/* 363 */                           token.type = 2;
                                }
/* 365 */                       if (c2 != 117 && c2 != 85) {
/* 366 */                           this.buffer.append((char) c2);
                                }
/* 368 */                       prevc = c2;
                            }
/* 370 */                   if (prevc == 102 || prevc == 70) {
/* 371 */                       token.type = 2;
                            }
/* 373 */                   token.value = this.buffer.toString();
/* 374 */                   if (token.type == 1 && token.value.endsWith("LL")) {
/* 375 */                       token.value = token.value.substring(0, token.value.length() - 1);
                            }
/* 377 */                   this.lastChar = c2;
                        } else if (c4 == 34) {
/* 379 */                   token.type = 3;
/* 380 */                   this.buffer.append(CommonUtil.MARKER_QUOTES);
/* 381 */                   int prevc2 = 0;
                            while (true) {
/* 382 */                       int c5 = readChar();
                                if (c5 == -1 || (prevc2 != 92 && c5 == 34)) {
                                    break;
                                }
/* 383 */                       this.buffer.append((char) c5);
/* 384 */                       prevc2 = c5;
                            }
/* 386 */                   this.buffer.append(CommonUtil.MARKER_QUOTES);
/* 387 */                   token.value = this.buffer.toString();
                        } else if (c4 == 47) {
/* 389 */                   int c6 = readChar();
/* 390 */                   if (c6 == 47) {
/* 391 */                       token.type = 4;
/* 392 */                       this.buffer.append('/').append('/');
/* 393 */                       int prevc3 = 0;
                                while (true) {
/* 394 */                           c3 = readChar();
                                    if (c3 == -1 || (prevc3 != 92 && c3 == 10)) {
                                        break;
                                    }
/* 395 */                           this.buffer.append((char) c3);
/* 396 */                           prevc3 = c3;
                                }
/* 398 */                       token.value = this.buffer.toString();
/* 399 */                       this.lastChar = c3;
                            } else if (c6 == 42) {
/* 401 */                       token.type = 4;
/* 402 */                       this.buffer.append('/').append('*');
/* 403 */                       int prevc4 = 0;
                                while (true) {
/* 404 */                           int c7 = readChar();
                                    if (c7 == -1 || (prevc4 == 42 && c7 == 47)) {
                                        break;
                                    }
/* 405 */                           this.buffer.append((char) c7);
/* 406 */                           prevc4 = c7;
                                }
/* 408 */                       this.buffer.append('/');
/* 409 */                       token.value = this.buffer.toString();
                            } else {
/* 411 */                       this.lastChar = c6;
/* 412 */                       token.type = 47;
                            }
                        } else {
/* 415 */                   if (c4 == 92) {
/* 416 */                       int c8 = readChar();
/* 417 */                       if (c8 == 10) {
/* 418 */                           String s = token.spacing;
/* 419 */                           Token token2 = nextToken();
/* 420 */                           token2.spacing = s;
                                    return token2;
                                }
/* 423 */                       this.lastChar = c8;
                            }
/* 426 */                   token.type = c4;
                        }
                        return token;
                    }
                }

/* 432 */       public Parser(Properties properties, InfoMap infoMap) {
/* 438 */           this.properties = null;
/* 439 */           this.infoMap = null;
/* 434 */           this.properties = properties;
/* 435 */           this.infoMap = infoMap;
                }

/* 443 */       Token getToken() {
/* 444 */           return getToken(0);
                }

/* 446 */       Token getToken(int i) {
                    return this.tokenIndex + i < this.tokenArray.length ? this.tokenArray[this.tokenIndex + i] : Token.EOF;
                }

/* 449 */       Token nextToken() {
/* 450 */           return nextToken(true);
                }

/* 452 */       Token nextToken(boolean skipComment) {
/* 453 */           this.tokenIndex++;
/* 454 */           while (skipComment && getToken().match(4)) {
/* 455 */               this.tokenIndex++;
                    }
/* 457 */           return getToken();
                }

/* 460 */       static class TemplateMap extends LinkedHashMap<String, String> {
                    LinkedHashMap<String, String> defaults;

/* 461 */           TemplateMap(TemplateMap defaults) {
/* 465 */               this.defaults = null;
/* 463 */               this.defaults = defaults;
                    }

/* 467 */           String get(String key) {
/* 468 */               String value = (String) super.get((Object) key);
                        return (value != null || this.defaults == null) ? value : this.defaults.get(key);
                    }
                }

/* 477 */       TemplateMap template(TemplateMap defaults) throws Exception {
                    if (getToken().match(Token.TEMPLATE)) {
/* 481 */               TemplateMap map = new TemplateMap(defaults);
/* 483 */               nextToken().expect('<');
/* 484 */               Token token = nextToken();
                        while (!token.match(Token.EOF)) {
                            if (token.match(Token.CLASS, Token.TYPENAME)) {
                                map.put(nextToken().expect(5).value, null);
                            }
                            if (!nextToken().expect(',', '>').match('>')) {
/* 484 */                       token = nextToken();
                            } else {
/* 489 */                       nextToken();
                                break;
                            }
                        }
                        return map;
                    }
/* 493 */           return defaults;
                }

/* 495 */       static class Declarator {
                    int infoNumber = 0;
                    int indices = 0;
                    boolean constValue = false;
                    boolean constPointer = false;
                    String annotations = "";
                    String cppType = "";
                    String javaType = "";
                    String objectName = "";
                    String convention = "";
                    String definitions = "";
                    String parameters = "";

/* 496 */           Declarator() {
                    }
                }

/* 504 */       Declarator declarator(TemplateMap typeMap, String defaultName, int infoNumber, int varNumber, boolean arrayAsPointer, boolean pointerAsArray) throws Exception {
                    String str;
/* 505 */           boolean isTypedef = getToken().match(Token.TYPEDEF);
/* 506 */           Declarator decl = new Declarator();
/* 507 */           String cppName = "";
/* 508 */           boolean simpleType = false;
/* 509 */           Token token = getToken();
                    while (!token.match(Token.EOF) && token.match(5)) {
                        if (token.match(Token.CONST)) {
/* 513 */                   decl.constValue = true;
                        } else if (token.match(Token.TYPEDEF, Token.ENUM, Token.CLASS, Token.STRUCT, Token.UNION)) {
                            continue;
                        } else if (token.match("signed", "unsigned", "char", "short", "int", "long", "bool", "float", "double")) {
/* 518 */                   if (!simpleType) {
/* 519 */                       cppName = token.value + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                            } else {
/* 521 */                       cppName = cppName + token.value + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                            }
/* 523 */                   simpleType = true;
                        } else {
/* 524 */                   if (cppName.length() > 0 && !getToken(1).match('*', '&', 5, Token.CONST)) {
                                break;
                            }
/* 528 */                   LinkedList<Info> infoList = this.infoMap.get(token.value);
/* 529 */                   if (infoList.size() > 0 && infoList.getFirst().annotations != null) {
/* 530 */                       String[] arr$ = infoList.getFirst().annotations;
                                for (String s : arr$) {
/* 531 */                           decl.annotations += s + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                                }
                            } else {
/* 534 */                       cppName = token.value;
                            }
                        }
/* 509 */               token = nextToken();
                    }
/* 538 */           String cppName2 = cppName.trim();
/* 539 */           if (typeMap != null && typeMap.containsKey(cppName2)) {
/* 540 */               cppName2 = typeMap.get(cppName2);
                    }
/* 542 */           decl.javaType = cppName2;
                    decl.cppType = cppName2;
/* 543 */           if ("...".equals(getToken().value)) {
/* 544 */               nextToken();
/* 545 */               return null;
                    }
/* 548 */           int count = 0;
/* 549 */           Token token2 = getToken();
                    while (varNumber > 0 && !token2.match(Token.EOF)) {
                        if (token2.match('(')) {
/* 551 */                   count++;
                        } else if (token2.match(')')) {
/* 553 */                   count--;
                        } else if (count != 0) {
                            continue;
                        } else if (token2.match(',')) {
/* 557 */                   varNumber--;
                        } else if (token2.match(Character.valueOf(CommonUtil.MARKER_SEMI_COLON))) {
/* 559 */                   nextToken();
/* 560 */                   return null;
                        }
/* 549 */               token2 = nextToken();
                    }
/* 564 */           String cast = cppName2;
/* 565 */           int indirections = 0;
/* 566 */           boolean reference = false;
/* 567 */           Token token3 = getToken();
                    while (!token3.match(Token.EOF)) {
                        if (token3.match('*')) {
/* 569 */                   indirections++;
                        } else if (token3.match('&')) {
/* 571 */                   reference = true;
                        } else {
                            if (!token3.match(Token.CONST)) {
                                break;
                            }
/* 573 */                   decl.constPointer = true;
                        }
/* 577 */               cast = cast + token3.toString();
/* 567 */               token3 = nextToken();
                    }
/* 580 */           int indirections2 = 0;
/* 581 */           decl.objectName = defaultName;
                    if (getToken().match('(')) {
                        while (getToken().match('(')) {
/* 584 */                   nextToken();
                        }
/* 586 */               Token token4 = getToken();
                        while (!token4.match(Token.EOF)) {
                            if (!token4.match(5)) {
                                if (!token4.match('*')) {
                                    if (!token4.match('[')) {
                                        if (token4.match(')')) {
/* 596 */                                   nextToken();
                                            break;
                                        }
                                    } else {
/* 594 */                               decl.indices++;
                                    }
                                } else {
/* 590 */                           indirections2++;
/* 591 */                           decl.convention = decl.objectName;
/* 592 */                           decl.objectName = defaultName;
                                }
                            } else {
/* 588 */                       decl.objectName = token4.value;
                            }
/* 586 */                   token4 = nextToken();
                        }
                        while (getToken().match(')')) {
/* 601 */                   nextToken();
                        }
                    } else if (getToken().match(5)) {
/* 604 */               decl.objectName = getToken().value;
/* 605 */               nextToken();
                    }
/* 608 */           boolean bracket = false;
/* 609 */           Token token5 = getToken();
                    while (!token5.match(Token.EOF)) {
/* 610 */               if (!bracket && token5.match('[')) {
/* 611 */                   bracket = true;
/* 612 */                   decl.indices++;
                        } else {
/* 613 */                   if (!bracket) {
                                break;
                            }
/* 615 */                   if (bracket && token5.match(']')) {
/* 616 */                       bracket = false;
                            }
                        }
/* 609 */               token5 = nextToken();
                    }
/* 619 */           while (decl.indices > 0 && indirections2 > 0) {
/* 621 */               decl.indices++;
/* 622 */               indirections2--;
                    }
/* 624 */           if (arrayAsPointer && decl.indices > 0) {
/* 627 */               indirections++;
/* 628 */               cast = cast + "*";
                    }
/* 630 */           if (pointerAsArray && indirections > 1) {
/* 632 */               decl.indices++;
/* 633 */               indirections--;
/* 634 */               cast = cast.substring(0, cast.length() - 1);
                    }
                    if (getToken().match(Character.valueOf(CommonUtil.MARKER_COLON))) {
/* 639 */               decl.annotations += "@NoOffset ";
/* 640 */               nextToken().expect(1);
/* 641 */               nextToken().expect(',', Character.valueOf(CommonUtil.MARKER_SEMI_COLON));
                    }
/* 644 */           int infoLength = 1;
/* 645 */           boolean primitive = false;
                    boolean needCast = false;
                    boolean implicitConst = false;
/* 646 */           String key = (!decl.constValue || indirections >= 2 || reference) ? decl.cppType : "const " + decl.cppType;
/* 647 */           LinkedList<Info> infoList2 = this.infoMap.get(key);
/* 648 */           if (infoList2.size() > 0) {
/* 649 */               Info info = infoList2.getFirst();
/* 650 */               primitive = (info.primitiveTypes == null || indirections != 0 || reference) ? false : true;
/* 651 */               needCast = info.cast;
/* 652 */               implicitConst = info.cppNames[0].startsWith("const ");
/* 653 */               if (primitive) {
                            infoLength = info.primitiveTypes.length;
                        } else {
/* 653 */                   infoLength = info.pointerTypes != null ? info.pointerTypes.length : 1;
                        }
/* 655 */               decl.infoNumber = infoNumber < 0 ? 0 : infoNumber % infoLength;
/* 656 */               if (primitive) {
                            str = info.primitiveTypes[decl.infoNumber];
                        } else {
/* 656 */                   str = info.pointerTypes != null ? info.pointerTypes[decl.infoNumber] : decl.cppType;
                        }
                        decl.javaType = str;
                    }
/* 660 */           if (!primitive) {
/* 661 */               if (indirections == 0 && !reference) {
/* 662 */                   decl.annotations += "@ByVal ";
                        } else if (indirections == 0 && reference) {
/* 664 */                   decl.annotations += "@ByRef ";
                        } else if (indirections == 1 && reference) {
/* 666 */                   decl.annotations += "@ByPtrRef ";
                        } else if (indirections == 2 && !reference && infoNumber >= 0) {
/* 668 */                   decl.annotations += "@ByPtrPtr ";
/* 669 */                   if (decl.cppType.equals("void")) {
/* 670 */                       needCast = true;
                            }
                        } else if (indirections >= 2) {
/* 673 */                   decl.infoNumber += infoLength;
/* 674 */                   needCast = true;
/* 675 */                   decl.javaType = "PointerPointer";
/* 676 */                   if (reference) {
/* 677 */                       decl.annotations += "@ByRef ";
                            }
                        }
/* 681 */               if (!needCast && decl.constValue && !implicitConst) {
/* 682 */                   decl.annotations = "@Const " + decl.annotations;
                        }
                    }
/* 685 */           if (needCast || (arrayAsPointer && decl.indices > 1)) {
/* 686 */               if (decl.constValue) {
/* 687 */                   cast = "const " + cast;
                        }
/* 689 */               if (!primitive && indirections == 0 && !reference) {
/* 690 */                   decl.annotations += "@Cast(\"" + cast + "*\") ";
                        } else {
/* 692 */                   decl.annotations = "@Cast(\"" + cast + "\") " + decl.annotations;
                        }
                    }
/* 696 */           Parameters params = parameters(typeMap, infoNumber);
/* 697 */           if (params != null) {
/* 698 */               decl.infoNumber = Math.max(decl.infoNumber, params.infoNumber);
/* 699 */               if (params.definitions.length() > 0) {
/* 700 */                   decl.definitions += params.definitions + IOUtils.LINE_SEPARATOR_UNIX;
                        }
/* 702 */               if (indirections2 == 0) {
/* 703 */                   decl.parameters = params.list;
                        } else {
/* 705 */                   String functionType = isTypedef ? decl.objectName : Character.toUpperCase(decl.objectName.charAt(0)) + decl.objectName.substring(1) + params.signature;
/* 707 */                   if (infoNumber <= params.infoNumber) {
/* 708 */                       decl.definitions += "public static class " + functionType + " extends FunctionPointer {\n    static { Loader.load(); }\n    public    " + functionType + "(Pointer p) { super(p); }\n    protected " + functionType + "() { allocate(); }\n    private native void allocate();\n    public native " + decl.annotations + decl.javaType + " call" + params.list + ";\n}\n";
                            }
/* 717 */                   decl.annotations = "";
/* 718 */                   decl.javaType = functionType;
/* 719 */                   decl.parameters = "";
                        }
                    }
/* 723 */           LinkedList<Info> infoList3 = this.infoMap.get(decl.objectName);
/* 724 */           if (infoList3.size() > 0) {
/* 725 */               Info info2 = infoList3.getFirst();
/* 726 */               if (info2.javaNames != null && info2.javaNames.length > 0) {
/* 727 */                   decl.annotations += "@Name(\"" + decl.objectName + "\") ";
/* 728 */                   decl.objectName = info2.javaNames[0];
/* 731 */                   return decl;
                        }
/* 731 */               return decl;
                    }
/* 731 */           return decl;
                }

/* 734 */       String commentBefore() throws Exception {
/* 735 */           String comment = "";
/* 736 */           while (this.tokenIndex > 0 && getToken(-1).match(4)) {
/* 737 */               this.tokenIndex--;
                    }
/* 739 */           Token token = getToken();
                    while (token.match(4)) {
/* 740 */               if (token.value.length() <= 3 || token.value.charAt(3) != '<') {
/* 741 */                   comment = comment + token.spacing + token.value;
                        }
/* 739 */               token = nextToken(false);
                    }
/* 744 */           return comment;
                }

/* 747 */       String commentAfter() throws Exception {
/* 748 */           String comment = "";
/* 749 */           while (this.tokenIndex > 0 && getToken(-1).match(4)) {
/* 750 */               this.tokenIndex--;
                    }
/* 752 */           Token token = getToken();
                    while (token.match(4)) {
/* 753 */               if (token.value.length() > 3 && token.value.charAt(3) == '<') {
/* 754 */                   String value = token.value;
                            comment = comment + (comment.length() > 0 ? " * " : "/**") + value.substring(4);
                        }
/* 752 */               token = nextToken(false);
                    }
/* 758 */           if (comment.length() > 0 && !comment.endsWith("*/")) {
/* 760 */               return comment + " */";
                    }
/* 763 */           return comment;
                }

/* 766 */       boolean attribute() throws Exception {
                    if (!getToken().match(5)) {
                        return false;
                    }
                    if (!nextToken().match('(')) {
                        return true;
                    }
/* 774 */           int count = 1;
/* 775 */           Token token = nextToken();
                    while (!token.match(Token.EOF) && count > 0) {
                        if (token.match('(')) {
/* 777 */                   count++;
                        } else if (token.match(')')) {
/* 779 */                   count--;
                        }
/* 775 */               token = nextToken(false);
                    }
                    return true;
                }

/* 785 */       boolean body() throws Exception {
                    if (!getToken().match('{')) {
                        return false;
                    }
/* 790 */           int count = 1;
/* 791 */           Token token = nextToken();
                    while (!token.match(Token.EOF) && count > 0) {
                        if (token.match('{')) {
/* 793 */                   count++;
                        } else if (token.match('}')) {
/* 795 */                   count--;
                        }
/* 791 */               token = nextToken(false);
                    }
                    return true;
                }

/* 800 */       static class Parameters {
                    int infoNumber = 0;
                    String list = "";
                    String definitions = "";
                    String signature = "";

/* 801 */           Parameters() {
                    }
                }

                /* JADX WARN: Code duplicated, block: B:40:0x01a7  */
/* 807 */       Parameters parameters(TemplateMap templateMap, int infoNumber) throws Exception {
                    if (!getToken().match('(')) {
/* 809 */               return null;
                    }
/* 812 */           int count = 0;
/* 813 */           Parameters params = new Parameters();
/* 814 */           params.list = "(";
/* 815 */           Token token = nextToken();
                    while (!token.match(Token.EOF)) {
/* 816 */               String spacing = token.spacing;
                        if (token.match(')')) {
/* 818 */                   params.list += spacing + ")";
/* 819 */                   nextToken();
/* 843 */                   return params;
                        }
                        int count2 = count + 1;
/* 822 */               Declarator decl = declarator(templateMap, "arg" + count, infoNumber, 0, true, false);
/* 823 */               if (decl == null || decl.javaType.equals("void")) {
                            count = count2;
                        } else {
/* 824 */                   params.infoNumber = Math.max(params.infoNumber, decl.infoNumber);
                            params.list += (count2 > 1 ? "," : "") + spacing + decl.annotations + decl.javaType + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + decl.objectName;
/* 826 */                   params.definitions += decl.definitions;
/* 827 */                   params.signature += '_';
/* 828 */                   char[] arr$ = decl.javaType.substring(decl.javaType.lastIndexOf(32) + 1).toCharArray();
                            for (char c : arr$) {
/* 829 */                       if (Character.isDigit(c) || Character.isLetter(c) || c == '_') {
/* 830 */                           params.signature += c;
                                }
                            }
/* 833 */                   if (decl.objectName.startsWith("arg")) {
                                try {
/* 835 */                           count = Integer.parseInt(decl.objectName.substring(3)) + 1;
                                } catch (NumberFormatException e) {
                                    count = count2;
                                }
                            } else {
                                count = count2;
                            }
                        }
                        if (getToken().expect(',', ')').match(',')) {
/* 840 */                   nextToken();
                        }
/* 815 */               token = getToken();
                    }
/* 843 */           return params;
                }

/* 846 */       String function(String group, TemplateMap templateMap) throws Exception {
/* 847 */           int backIndex = this.tokenIndex;
/* 848 */           String spacing = getToken().spacing;
/* 849 */           String access = (group == null || group.length() == 0) ? "public static native " : "public native ";
/* 850 */           Token token = getToken();
                    while (!token.match(Token.EOF)) {
                        if (!token.match(Token.STATIC)) {
                            if (!token.match(Token.INLINE)) {
                                break;
                            }
                        } else {
/* 852 */                   access = "public static native ";
                        }
/* 850 */               token = nextToken();
                    }
/* 857 */           int startIndex = this.tokenIndex;
/* 858 */           Declarator decl = declarator(templateMap, null, 0, 0, false, false);
/* 859 */           String name = decl.objectName;
/* 860 */           if (name == null || decl.parameters.length() == 0) {
/* 861 */               this.tokenIndex = backIndex;
/* 862 */               return null;
                    }
/* 865 */           String text = "";
/* 866 */           String statements = "";
/* 867 */           String definitions = "";
/* 868 */           LinkedList<Info> infoList = this.infoMap.get(name);
/* 869 */           if (infoList.size() == 0) {
/* 870 */               infoList.add(null);
                    }
                    for (Info info : infoList) {
/* 873 */               if (info != null) {
/* 874 */                   if (info.genericTypes != null && templateMap != null) {
/* 875 */                       int count = 0;
                                for (String key : templateMap.keySet()) {
/* 877 */                           if (count < info.genericTypes.length) {
/* 878 */                               templateMap.put(key, info.genericTypes[count]);
                                        count++;
                                    }
                                }
                            }
/* 882 */                   if (info.javaNames == null) {
                                name = info.cppNames[0];
                            } else {
/* 882 */                       name = info.javaNames.length == 0 ? "" : info.javaNames[0];
                            }
/* 883 */                   if (!name.equals(info.cppNames[0]) && name.length() > 0) {
/* 884 */                       name = "@Name(\"" + info.cppNames[0] + "\") " + name;
                            }
                        }
/* 888 */               LinkedList<Declarator> prevDecl = new LinkedList<>();
                        for (int n = -1; n < Integer.MAX_VALUE; n++) {
/* 890 */                   this.tokenIndex = startIndex;
/* 891 */                   Declarator decl2 = declarator(templateMap, null, n, 0, false, false);
/* 892 */                   boolean found = false;
                            for (Declarator d : prevDecl) {
/* 894 */                       found |= decl2.parameters.equals(d.parameters);
                            }
/* 896 */                   if (found && n > 0) {
                                break;
                            }
/* 899 */                   if (name.length() > 0 && !found) {
/* 900 */                       statements = statements + access + decl2.annotations + decl2.javaType + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name + decl2.parameters + ";\n";
/* 901 */                       definitions = definitions + decl2.definitions;
                            }
/* 903 */                   prevDecl.add(decl2);
                        }
/* 905 */               while (attribute()) {
                        }
                        if (getToken().match('{')) {
/* 907 */                   body();
                        } else {
/* 909 */                   nextToken();
                        }
                    }
/* 912 */           String comment = commentAfter();
/* 913 */           if (comment.length() > 0) {
/* 914 */               statements = comment + IOUtils.LINE_SEPARATOR_UNIX + statements;
                    }
/* 916 */           Scanner scanner = new Scanner(definitions);
/* 917 */           while (scanner.hasNextLine()) {
/* 918 */               text = text + spacing + scanner.nextLine();
/* 919 */               int newline = spacing.lastIndexOf(10);
/* 920 */               if (newline > 0) {
/* 921 */                   spacing = spacing.substring(newline);
                        }
                    }
/* 924 */           Scanner scanner2 = new Scanner(statements);
/* 925 */           while (scanner2.hasNextLine()) {
/* 926 */               text = text + spacing + scanner2.nextLine();
/* 927 */               int newline2 = spacing.lastIndexOf(10);
/* 928 */               if (newline2 > 0) {
/* 929 */                   spacing = spacing.substring(newline2);
                        }
                    }
/* 932 */           return text;
                }

/* 935 */       String variable(String group) throws Exception {
/* 936 */           int backIndex = this.tokenIndex;
/* 937 */           String spacing = getToken().spacing;
/* 940 */           if (declarator(null, null, 0, 0, false, true).objectName == null || !getToken().match('[', '=', ',', Character.valueOf(CommonUtil.MARKER_COLON), Character.valueOf(CommonUtil.MARKER_SEMI_COLON))) {
/* 941 */               this.tokenIndex = backIndex;
/* 942 */               return null;
                    }
/* 945 */           String text = "";
/* 946 */           String statements = "";
/* 947 */           String definitions = "";
                    for (int n = 0; n < Integer.MAX_VALUE; n++) {
/* 949 */               this.tokenIndex = backIndex;
/* 950 */               Declarator decl = declarator(null, null, -1, n, false, true);
/* 951 */               if (decl == null) {
                            break;
                        }
/* 954 */               String access = (group == null || group.length() == 0) ? "public static native " : "public native ";
/* 955 */               String setterType = (group == null || group.length() == 0) ? "void " : group + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/* 956 */               String name = decl.objectName;
/* 957 */               String indices = "";
                        for (int i = 0; i < decl.indices; i++) {
/* 959 */                   if (i > 0) {
/* 960 */                       indices = indices + ", ";
                            }
/* 962 */                   indices = indices + "int " + ((char) (i + 105));
                        }
/* 964 */               if (decl.constValue) {
/* 965 */                   statements = statements + "@MemberGetter ";
                        }
/* 967 */               String statements2 = statements + access + decl.annotations + decl.javaType + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name + "(" + indices + ");";
/* 968 */               if (!decl.constValue) {
/* 969 */                   if (decl.indices > 0) {
/* 970 */                       indices = indices + ", ";
                            }
/* 972 */                   statements2 = statements2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + access + setterType + name + "(" + indices + decl.javaType + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name + ");";
                        }
/* 974 */               statements = statements2 + IOUtils.LINE_SEPARATOR_UNIX;
/* 975 */               definitions = definitions + decl.definitions;
/* 977 */               if (decl.indices > 0) {
/* 979 */                   this.tokenIndex = backIndex;
/* 980 */                   Declarator decl2 = declarator(null, null, -1, n, true, false);
/* 981 */                   statements = statements + "@MemberGetter " + access + decl2.annotations + decl2.javaType + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name + "();\n";
                        }
                    }
/* 984 */           String comment = commentAfter();
/* 985 */           if (comment.length() > 0) {
/* 986 */               statements = comment + IOUtils.LINE_SEPARATOR_UNIX + statements;
                    }
/* 988 */           Scanner scanner = new Scanner(definitions);
/* 989 */           while (scanner.hasNextLine()) {
/* 990 */               text = text + spacing + scanner.nextLine();
/* 991 */               int newline = spacing.lastIndexOf(10);
/* 992 */               if (newline > 0) {
/* 993 */                   spacing = spacing.substring(newline);
                        }
                    }
/* 996 */           Scanner scanner2 = new Scanner(statements);
/* 997 */           while (scanner2.hasNextLine()) {
/* 998 */               text = text + spacing + scanner2.nextLine();
/* 999 */               int newline2 = spacing.lastIndexOf(10);
/* 1000 */              if (newline2 > 0) {
/* 1001 */                  spacing = spacing.substring(newline2);
                        }
                    }
/* 1004 */          return text;
                }

/* 1007 */      String macro() throws Exception {
                    if (!getToken().match('#')) {
/* 1009 */              return null;
                    }
/* 1011 */          String spacing = getToken().spacing;
/* 1012 */          Token keyword = nextToken();
/* 1014 */          nextToken();
/* 1015 */          int beginIndex = this.tokenIndex;
/* 1016 */          Token token = getToken();
                    while (!token.match(Token.EOF) && !token.match(4) && token.spacing.indexOf(10) < 0) {
/* 1016 */              token = nextToken(false);
                    }
/* 1021 */          int endIndex = this.tokenIndex;
/* 1023 */          String text = "";
                    if (!keyword.match(Token.DEFINE) || beginIndex + 1 >= endIndex) {
                        if (keyword.match(Token.f2531IF, Token.IFDEF, Token.IFNDEF, Token.ELIF) && beginIndex < endIndex) {
/* 1137 */                  this.tokenIndex = beginIndex;
/* 1138 */                  String value = "";
/* 1139 */                  Token token2 = getToken();
                            while (this.tokenIndex < endIndex) {
/* 1140 */                      value = value + token2.spacing + token2;
/* 1139 */                      token2 = nextToken();
                            }
/* 1142 */                  String text2 = spacing + "// #" + keyword + value;
/* 1143 */                  LinkedList<Info> infoList = this.infoMap.get(value);
/* 1144 */                  boolean define = true;
/* 1145 */                  if (infoList.size() > 0) {
/* 1146 */                      Info info = infoList.getFirst();
                                if (keyword.match(Token.IFNDEF)) {
/* 1147 */                          define = !info.define;
                                } else {
                                    define = info.define;
                                }
                            }
/* 1149 */                  if (!define) {
/* 1150 */                      int count = 1;
/* 1151 */                      Token prevToken = new Token();
/* 1152 */                      Token token3 = getToken();
                                while (!token3.match(Token.EOF) && count > 0) {
                                    if (prevToken.match('#') && token3.match(Token.f2531IF, Token.IFDEF, Token.IFNDEF)) {
/* 1154 */                              count++;
                                    } else if (prevToken.match('#') && token3.match(Token.ELIF, Token.ELSE, Token.ENDIF)) {
/* 1156 */                              count--;
                                    }
/* 1158 */                          prevToken = token3;
/* 1152 */                          token3 = nextToken(false);
                                }
/* 1160 */                      this.tokenIndex -= 2;
/* 1169 */                      return text2;
                            }
/* 1169 */                  return text2;
                        }
/* 1163 */              this.tokenIndex = beginIndex;
/* 1164 */              String text3 = spacing + "// #" + keyword;
/* 1165 */              Token token4 = getToken();
                        while (this.tokenIndex < endIndex) {
/* 1166 */                  text3 = text3 + token4.spacing + token4;
/* 1165 */                  token4 = nextToken();
                        }
/* 1169 */              return text3;
                    }
/* 1025 */          this.tokenIndex = beginIndex;
/* 1026 */          String name = getToken().value;
/* 1027 */          Token first = nextToken();
/* 1028 */          String statements = "";
/* 1029 */          LinkedList<Info> infoList2 = this.infoMap.get(name);
/* 1030 */          if (first.spacing.length() == 0 && first.match('(')) {
                        for (Info info2 : infoList2) {
/* 1033 */                  if (info2.genericTypes != null && info2.genericTypes.length != 0) {
/* 1037 */                      int count2 = 1;
/* 1038 */                      this.tokenIndex = beginIndex + 2;
                                String params = "(";
/* 1040 */                      Token token5 = getToken();
                                while (this.tokenIndex < endIndex && count2 < info2.genericTypes.length) {
                                    if (!token5.match(5)) {
                                        if (token5.match(')')) {
                                            break;
                                        }
                                    } else {
/* 1043 */                              String type = info2.genericTypes[count2];
/* 1044 */                              String name2 = token5.value;
/* 1045 */                              if (name2.equals("...")) {
/* 1046 */                                  name2 = "arg" + count2;
                                        }
/* 1048 */                              params = params + type + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name2;
/* 1049 */                              count2++;
                                        if (count2 < info2.genericTypes.length) {
/* 1050 */                                  params = params + ", ";
                                        }
                                    }
/* 1041 */                          token5 = nextToken();
                                }
/* 1056 */                      while (count2 < info2.genericTypes.length) {
/* 1057 */                          String type2 = info2.genericTypes[count2];
/* 1059 */                          params = params + type2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + ("arg" + count2);
/* 1060 */                          count2++;
                                    if (count2 < info2.genericTypes.length) {
/* 1061 */                              params = params + ", ";
                                    }
                                }
/* 1064 */                      String params2 = params + ")";
/* 1066 */                      String type3 = info2.genericTypes[0];
/* 1067 */                      String name3 = info2.javaNames == null ? info2.cppNames[0] : info2.javaNames[0];
/* 1068 */                      if (!name3.equals(info2.cppNames[0])) {
/* 1069 */                          name3 = "@Name(\"" + info2.cppNames[0] + "\") " + name3;
                                }
/* 1071 */                      statements = statements + "public static native " + type3 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name3 + params2 + ";\n";
                            }
                        }
                    } else if (infoList2.size() == 0 || infoList2.getFirst().genericTypes == null || infoList2.getFirst().genericTypes.length > 0) {
/* 1077 */              String value2 = "";
/* 1078 */              String type4 = "int";
/* 1079 */              String cat = "";
/* 1080 */              this.tokenIndex = beginIndex + 1;
/* 1081 */              Token prevToken2 = new Token();
/* 1082 */              boolean complex = false;
/* 1083 */              Token token6 = getToken();
                        while (this.tokenIndex < endIndex) {
                            if (token6.match(3)) {
/* 1085 */                      type4 = "String";
                                cat = " + ";
                                break;
                            }
                            if (token6.match(2)) {
/* 1087 */                      type4 = "double";
                                cat = "";
                                break;
                            }
                            if (token6.match(1) && token6.value.endsWith("L")) {
/* 1089 */                      type4 = "long";
                                cat = "";
                                break;
                            }
                            if ((prevToken2.match(5) && token6.match('(')) || token6.match('{', '}')) {
/* 1091 */                      complex = true;
                            }
/* 1093 */                  prevToken2 = token6;
/* 1083 */                  token6 = nextToken();
                        }
/* 1095 */              if (infoList2.size() > 0) {
/* 1096 */                  Info info3 = infoList2.getFirst();
/* 1097 */                  if (info3.genericTypes != null) {
/* 1098 */                      type4 = info3.genericTypes[0];
                            }
/* 1100 */                  name = info3.cppNames[0];
/* 1101 */                  if (info3.javaNames != null) {
/* 1102 */                      name = info3.javaNames[0];
                            }
/* 1104 */                  if (info3.complex) {
/* 1105 */                      complex = true;
                            }
                        }
/* 1108 */              this.tokenIndex = beginIndex + 1;
/* 1109 */              if (complex) {
/* 1028 */                  statements = "public static native @MemberGetter " + type4 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name + "();\n";
/* 1111 */                  value2 = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name + "()";
                        } else {
/* 1113 */                  Token token7 = getToken();
                            while (this.tokenIndex < endIndex) {
                                value2 = value2 + token7.spacing + token7 + (this.tokenIndex < endIndex ? cat : "");
/* 1113 */                      token7 = nextToken();
                            }
                        }
/* 1117 */              int i = type4.lastIndexOf(32);
/* 1118 */              if (i > 0) {
/* 1119 */                  type4 = type4.substring(i + 1);
                        }
/* 1121 */              statements = statements + "public static final " + type4 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + name + " =" + value2 + ";\n";
                    }
/* 1123 */          this.tokenIndex = endIndex;
/* 1124 */          String comment = commentAfter();
/* 1125 */          if (comment.length() > 0) {
/* 1126 */              statements = comment + IOUtils.LINE_SEPARATOR_UNIX + statements;
                    }
/* 1128 */          Scanner scanner = new Scanner(statements);
/* 1129 */          while (scanner.hasNextLine()) {
/* 1130 */              text = text + spacing + scanner.nextLine();
/* 1131 */              int newline = spacing.lastIndexOf(10);
/* 1132 */              if (newline > 0) {
/* 1133 */                  spacing = spacing.substring(newline);
                        }
                    }
/* 1169 */          return text;
                }

/* 1172 */      String typedef() throws Exception {
                    if (!getToken().match(Token.TYPEDEF)) {
/* 1174 */              return null;
                    }
/* 1176 */          String spacing = getToken().spacing;
/* 1177 */          Declarator decl = declarator(null, null, 0, 0, false, false);
/* 1178 */          nextToken();
/* 1180 */          LinkedList<Info> infoList = this.infoMap.get(decl.cppType);
/* 1181 */          Info info = infoList.size() > 0 ? infoList.getFirst().clone() : new Info();
/* 1182 */          this.infoMap.put(info.cppNames(decl.objectName).cast(true));
/* 1185 */          String comment = commentAfter();
/* 1184 */          String text = comment.length() > 0 ? comment + IOUtils.LINE_SEPARATOR_UNIX + "" : "";
/* 1189 */          Scanner scanner = new Scanner(decl.definitions);
/* 1190 */          while (scanner.hasNextLine()) {
/* 1191 */              text = text + spacing + scanner.nextLine();
/* 1192 */              int newline = spacing.lastIndexOf(10);
/* 1193 */              if (newline > 0) {
/* 1194 */                  spacing = spacing.substring(newline);
                        }
                    }
/* 1197 */          return text;
                }

/* 1200 */      String group(TemplateMap templateMap) throws Exception {
/* 1201 */          int backIndex = this.tokenIndex;
/* 1202 */          String spacing = getToken().spacing;
/* 1203 */          boolean isTypedef = getToken().match(Token.TYPEDEF);
/* 1204 */          boolean foundGroup = false;
                    boolean doOutput = true;
/* 1205 */          Token token = getToken();
                    while (!token.match(Token.EOF)) {
                        if (!token.match(Token.CLASS, Token.STRUCT, Token.UNION)) {
                            if (!token.match(Token.EXTERN) || !nextToken().match(3)) {
                                if (!token.match(5)) {
                                    break;
                                }
/* 1205 */                      token = nextToken();
                            } else {
/* 1210 */                      foundGroup = true;
/* 1211 */                      doOutput = false;
                                break;
                            }
                        } else {
/* 1207 */                  foundGroup = true;
                            break;
                        }
                    }
/* 1217 */          if (!foundGroup) {
/* 1218 */              this.tokenIndex = backIndex;
/* 1219 */              return null;
                    }
/* 1222 */          if (isTypedef && !getToken(1).match('{') && getToken(2).match(5)) {
/* 1223 */              nextToken();
                    }
/* 1225 */          String text = "";
                    String name = nextToken().expect(3, 5, '{').value;
                    if (!getToken(0).match('{', Character.valueOf(CommonUtil.MARKER_SEMI_COLON)) && !getToken(1).match('{', Character.valueOf(CommonUtil.MARKER_SEMI_COLON))) {
/* 1228 */              this.tokenIndex = backIndex;
/* 1229 */              return null;
                    }
/* 1231 */          if (doOutput) {
/* 1232 */              LinkedList<Info> infoList = this.infoMap.get(name);
                        if (getToken().match(5) && nextToken().match(Character.valueOf(CommonUtil.MARKER_SEMI_COLON))) {
/* 1234 */                  nextToken();
/* 1235 */                  if (infoList.size() == 0 || !infoList.getFirst().forwardDeclared) {
/* 1236 */                      this.infoMap.put(new Info(name).forwardDeclared(true));
/* 1237 */                      String text2 = spacing + "@Opaque public static class " + name + " extends Pointer {\n    public " + name + "() { }\n    public " + name + "(Pointer p) { super(p); }\n}";
/* 1291 */                      return text2;
                            }
/* 1243 */                  return "";
                        }
/* 1246 */              int index = this.tokenIndex;
/* 1247 */              if (body() && isTypedef && getToken().match(5)) {
/* 1248 */                  name = getToken().value;
/* 1249 */                  this.infoMap.get(name);
                        }
/* 1251 */              if (name.length() == 0) {
/* 1253 */                  Token token2 = nextToken();
                            while (!token2.match(Token.EOF)) {
                                if (!token2.match(Character.valueOf(CommonUtil.MARKER_SEMI_COLON))) {
/* 1253 */                          token2 = nextToken();
                                } else {
/* 1255 */                          nextToken();
                                    break;
                                }
                            }
/* 1259 */                  return "";
                        }
/* 1261 */              this.tokenIndex = index;
/* 1225 */              text = "" + spacing + "public static class " + name + " extends Pointer {\n    static { Loader.load(); }\n    public " + name + "() { allocate(); }\n    public " + name + "(int size) { allocateArray(size); }\n    public " + name + "(Pointer p) { super(p); }\n    private native void allocate();\n    private native void allocateArray(int size);\n    @Override public " + name + " position(int position) {\n        return (" + name + ")super.position(position);\n    }\n";
                    }
                    if (getToken().match('{')) {
/* 1276 */              nextToken();
                    }
                    while (!getToken().match(Token.EOF, '}')) {
/* 1279 */              text = text + declaration(name, templateMap);
                    }
/* 1282 */          if (doOutput) {
/* 1283 */              String text3 = text + getToken().spacing + '}';
/* 1284 */              Token token3 = nextToken();
                        if (token3.match(5)) {
/* 1286 */                  token3 = nextToken();
                        }
                        text = text3 + token3.expect(Character.valueOf(CommonUtil.MARKER_SEMI_COLON)).spacing;
                    }
/* 1290 */          nextToken();
/* 1291 */          return text;
                }

/* 1294 */      String enumeration() throws Exception {
/* 1295 */          int backIndex = this.tokenIndex;
/* 1296 */          String enumSpacing = getToken().spacing;
/* 1297 */          boolean isTypedef = getToken().match(Token.TYPEDEF);
/* 1298 */          boolean foundEnum = false;
/* 1299 */          Token token = getToken();
                    while (!token.match(Token.EOF)) {
                        if (!token.match(Token.ENUM)) {
                            if (!token.match(5)) {
                                break;
                            }
/* 1299 */                  token = nextToken();
                        } else {
/* 1301 */                  foundEnum = true;
                            break;
                        }
                    }
/* 1307 */          if (!foundEnum) {
/* 1308 */              this.tokenIndex = backIndex;
/* 1309 */              return null;
                    }
/* 1312 */          if (isTypedef && !getToken(1).match('{') && getToken(2).match(5)) {
/* 1313 */              nextToken();
                    }
/* 1315 */          boolean first = true;
/* 1316 */          int count = 0;
/* 1317 */          String countPrefix = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/* 1318 */          String enumerators = "";
/* 1319 */          String macroText = "";
                    String name = nextToken().expect(5, '{').value;
                    if (!getToken().match('{') && !nextToken().match('{')) {
/* 1322 */              this.tokenIndex = backIndex;
/* 1323 */              return null;
                    }
/* 1325 */          Token token2 = nextToken();
                    while (!token2.match(Token.EOF, '}')) {
/* 1326 */              String s = macro();
/* 1327 */              if (s != null) {
/* 1328 */                  macroText = macroText + s;
                        } else {
/* 1331 */                  String comment = commentBefore();
/* 1332 */                  Token enumerator = getToken();
/* 1333 */                  String spacing2 = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/* 1334 */                  String separator = first ? "" : ",";
                            if (nextToken().match('=')) {
/* 1336 */                      spacing2 = getToken().spacing;
/* 1337 */                      countPrefix = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/* 1338 */                      int count2 = 0;
/* 1339 */                      Token prevToken = new Token();
/* 1340 */                      boolean complex = false;
/* 1341 */                      Token token3 = nextToken();
                                while (true) {
                                    if (!token3.match(Token.EOF, ',', '}') || count2 > 0) {
/* 1342 */                              countPrefix = countPrefix + token3.spacing + token3;
                                        if (token3.match('(')) {
/* 1344 */                                  count2++;
                                        } else if (token3.match(')')) {
/* 1346 */                                  count2--;
                                        }
                                        if (prevToken.match(5) && token3.match('(')) {
/* 1349 */                                  complex = true;
                                        }
/* 1351 */                              prevToken = token3;
/* 1341 */                              token3 = nextToken();
                                    } else {
                                        try {
                                            break;
                                        } catch (NumberFormatException e) {
/* 1357 */                                  count = 0;
/* 1358 */                                  if (complex) {
/* 1359 */                                      if (!first) {
/* 1360 */                                          separator = ";\n";
                                                }
/* 1363 */                                      separator = separator + "public static native @MemberGetter int " + enumerator.value + "();\npublic static final int";
/* 1364 */                                      countPrefix = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + enumerator.value + "()";
                                            }
                                        }
                                    }
                                }
/* 1354 */                      count = Integer.parseInt(countPrefix.trim());
/* 1355 */                      countPrefix = MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                            }
/* 1368 */                  first = false;
/* 1369 */                  String enumerators2 = enumerators + separator + macroText + comment;
/* 1370 */                  macroText = "";
/* 1371 */                  String comment2 = commentAfter();
/* 1372 */                  if (comment2.length() == 0 && getToken().match(',')) {
/* 1373 */                      nextToken();
/* 1374 */                      comment2 = commentAfter();
                            }
/* 1376 */                  String spacing = enumerator.spacing;
/* 1377 */                  if (comment2.length() > 0) {
/* 1378 */                      enumerators2 = enumerators2 + spacing + comment2;
/* 1379 */                      int newline = spacing.lastIndexOf(10);
/* 1380 */                      if (newline > 0) {
/* 1381 */                          spacing = spacing.substring(newline);
                                }
                            }
/* 1384 */                  enumerators = enumerators2 + spacing + enumerator.value + spacing2 + "=" + countPrefix;
/* 1385 */                  if (countPrefix.trim().length() > 0) {
/* 1386 */                      if (count > 0) {
/* 1387 */                          enumerators = enumerators + " + " + count;
                                }
                            } else {
/* 1390 */                      enumerators = enumerators + count;
                            }
/* 1392 */                  count++;
                        }
/* 1325 */              token2 = getToken();
                    }
/* 1394 */          String comment3 = commentBefore();
/* 1396 */          Token token4 = nextToken();
                    if (token4.match(5)) {
/* 1398 */              name = token4.value;
/* 1399 */              token4 = nextToken();
                    }
/* 1395 */          String text = "" + enumSpacing + "/** enum " + name + " */\n";
/* 1402 */          int newline2 = enumSpacing.lastIndexOf(10);
/* 1403 */          if (newline2 >= 0) {
/* 1404 */              enumSpacing = enumSpacing.substring(newline2 + 1);
                    }
                    String text2 = text + enumSpacing + "public static final int" + enumerators + token4.expect(Character.valueOf(CommonUtil.MARKER_SEMI_COLON)).spacing + ";";
/* 1407 */          this.infoMap.put(new Info(name).primitiveTypes("int").pointerTypes("IntPointer", "IntBuffer", "int[]").cast(true));
/* 1408 */          nextToken();
/* 1409 */          return text2 + macroText + comment3;
                }

/* 1412 */      String declaration(String group, TemplateMap templateMap) throws Exception {
/* 1413 */          String comment = commentBefore();
/* 1414 */          Token token = getToken();
/* 1415 */          String spacing = token.spacing;
/* 1416 */          TemplateMap map = template(templateMap);
/* 1417 */          if (map != templateMap) {
/* 1418 */              comment = comment + spacing.substring(0, spacing.lastIndexOf(10));
                    }
/* 1420 */          String text = macro();
                    if (text != null) {
                        return comment + text;
                    }
/* 1421 */          String text2 = enumeration();
                    if (text2 != null) {
                        return comment + text2;
                    }
/* 1422 */          String text3 = group(map);
                    if (text3 != null) {
                        return comment + text3;
                    }
/* 1423 */          String text4 = typedef();
                    if (text4 != null) {
                        return comment + text4;
                    }
/* 1424 */          String text5 = function(group, map);
                    if (text5 != null) {
                        return comment + text5;
                    }
/* 1425 */          String text6 = variable(group);
                    if (text6 != null) {
                        return comment + text6;
                    }
/* 1426 */          if (attribute()) {
                        return comment + spacing;
                    }
/* 1427 */          throw new Exception(token.file + ":" + token.lineNumber + ": Could not parse declaration at '" + token + "'");
                }

/* 1430 */      public void parse(String outputFilename, String... inputFilenames) throws Exception, IOException {
/* 1431 */          File[] files = new File[inputFilenames.length];
                    for (int i = 0; i < files.length; i++) {
/* 1433 */              files[i] = new File(inputFilenames[i]);
                    }
/* 1435 */          parse(new File(outputFilename), files);
                }

/* 1437 */      public void parse(File outputFile, File... inputFiles) throws Exception, IOException {
/* 1438 */          ArrayList<Token> tokens = new ArrayList<>();
/* 1439 */          String lineSeparator = IOUtils.LINE_SEPARATOR_UNIX;
                    for (File file : inputFiles) {
/* 1441 */              System.out.println("Parsing header file: " + file);
/* 1442 */              Token token = new Token();
/* 1443 */              token.type = 4;
/* 1444 */              token.value = "\n/* Wrapper for header file " + file + " */\n\n";
/* 1445 */              tokens.add(token);
/* 1446 */              Tokenizer tokenizer = new Tokenizer(file);
                        while (true) {
/* 1447 */                  Token token2 = tokenizer.nextToken();
                            if (token2.isEmpty()) {
                                break;
                            }
/* 1448 */                  if (token2.type == -1) {
/* 1449 */                      token2.type = 4;
                            }
/* 1451 */                  tokens.add(token2);
                        }
/* 1453 */              if (lineSeparator == null) {
/* 1454 */                  lineSeparator = tokenizer.lineSeparator;
                        }
/* 1456 */              tokenizer.close();
/* 1457 */              Token token3 = new Token();
/* 1458 */              token3.type = 4;
/* 1459 */              token3.spacing = IOUtils.LINE_SEPARATOR_UNIX;
/* 1460 */              tokens.add(token3);
                    }
/* 1462 */          this.tokenArray = (Token[]) tokens.toArray(new Token[tokens.size()]);
/* 1463 */          this.tokenIndex = 0;
/* 1465 */          Writer out = outputFile != null ? new FileWriter(outputFile) : new C12241();
/* 1470 */          LinkedList<Info> infoList = this.infoMap.get((String) null);
                    for (Info info : infoList) {
/* 1472 */              if (info.textBefore != null) {
/* 1473 */                  out.append((CharSequence) info.textBefore.replaceAll(IOUtils.LINE_SEPARATOR_UNIX, lineSeparator));
                        }
                    }
/* 1476 */          out.append((CharSequence) ("{" + lineSeparator));
/* 1477 */          out.append((CharSequence) ("    static { Loader.load(); }" + lineSeparator));
                    for (Info info2 : infoList) {
/* 1479 */              if (info2.textAfter != null) {
/* 1480 */                  out.append((CharSequence) info2.textAfter.replaceAll(IOUtils.LINE_SEPARATOR_UNIX, lineSeparator));
                        }
                    }
                    while (!getToken().match(Token.EOF)) {
/* 1484 */              out.append((CharSequence) declaration(null, null).replaceAll(IOUtils.LINE_SEPARATOR_UNIX, lineSeparator));
                    }
/* 1486 */          String comment = commentBefore();
/* 1487 */          if (comment != null) {
/* 1488 */              out.append((CharSequence) comment.replaceAll(IOUtils.LINE_SEPARATOR_UNIX, lineSeparator));
                    }
/* 1490 */          out.append((CharSequence) (lineSeparator + "}" + lineSeparator));
/* 1491 */          out.close();
                }

                /* JADX INFO: renamed from: com.googlecode.javacpp.Parser$1 */
/* 1463 */      class C12241 extends Writer {
/* 1464 */          C12241() {
                    }

                    @Override // java.io.Writer
/* 1465 */          public void write(char[] cbuf, int off, int len) {
                    }

                    @Override // java.io.Writer, java.io.Flushable
/* 1466 */          public void flush() {
                    }

                    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 1467 */          public void close() {
                    }
                }

/* 1494 */      public File parse(String outputDirectory, Class cls) throws Exception, IOException {
/* 1495 */          return parse(new File(outputDirectory), cls);
                }

/* 1497 */      public File parse(File outputDirectory, Class cls) throws Exception, IOException {
/* 1498 */          Loader.ClassProperties allProperties = Loader.loadProperties(cls, this.properties, true);
/* 1499 */          Loader.ClassProperties clsProperties = Loader.loadProperties(cls, this.properties, false);
/* 1500 */          LinkedList<File> allFiles = allProperties.getHeaderFiles();
/* 1501 */          LinkedList<File> clsFiles = clsProperties.getHeaderFiles();
/* 1502 */          LinkedList<String> allTargets = allProperties.get("parser.target");
/* 1503 */          LinkedList<String> clsTargets = clsProperties.get("parser.target");
/* 1504 */          String target = clsTargets.getFirst();
/* 1507 */          int n = target.lastIndexOf(46);
/* 1506 */          String text = n > 0 ? "/* DO NOT EDIT THIS FILE - IT IS MACHINE GENERATED */\n\npackage " + target.substring(0, n) + ";\n\n" : "/* DO NOT EDIT THIS FILE - IT IS MACHINE GENERATED */\n\n";
/* 1511 */          String text2 = text + "import com.googlecode.javacpp.*;\nimport com.googlecode.javacpp.annotation.*;\nimport java.nio.*;\n\n";
                    for (String s : allTargets) {
/* 1515 */              if (!target.equals(s)) {
/* 1516 */                  text2 = text2 + "import static " + s + ".*;\n";
                        }
                    }
/* 1519 */          if (allTargets.size() > 1) {
/* 1520 */              text2 = text2 + IOUtils.LINE_SEPARATOR_UNIX;
                    }
/* 1523 */          this.infoMap.put(new Info().textBefore(text2 + "public class " + target.substring(n + 1) + " extends " + cls.getCanonicalName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
/* 1525 */          File targetFile = new File(outputDirectory, target.replace(FilenameUtils.EXTENSION_SEPARATOR, '/') + ".java");
/* 1526 */          System.out.println("Targeting file: " + targetFile);
                    for (File f : allFiles) {
/* 1528 */              if (!clsFiles.contains(f)) {
/* 1529 */                  parse((File) null, f);
                        }
                    }
/* 1532 */          parse(targetFile, (File[]) clsFiles.toArray(new File[clsFiles.size()]));
/* 1533 */          return targetFile;
                }
            }
