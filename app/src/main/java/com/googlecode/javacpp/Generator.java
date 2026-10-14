            package com.googlecode.javacpp;

            import co.vine.android.provider.Vine;
            import co.vine.android.util.CommonUtil;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.googlecode.javacpp.annotation.Adapter;
            import com.googlecode.javacpp.annotation.Allocator;
            import com.googlecode.javacpp.annotation.ArrayAllocator;
            import com.googlecode.javacpp.annotation.ByPtr;
            import com.googlecode.javacpp.annotation.ByPtrPtr;
            import com.googlecode.javacpp.annotation.ByPtrRef;
            import com.googlecode.javacpp.annotation.ByRef;
            import com.googlecode.javacpp.annotation.ByVal;
            import com.googlecode.javacpp.annotation.Cast;
            import com.googlecode.javacpp.annotation.Const;
            import com.googlecode.javacpp.annotation.Convention;
            import com.googlecode.javacpp.annotation.Function;
            import com.googlecode.javacpp.annotation.Index;
            import com.googlecode.javacpp.annotation.MemberGetter;
            import com.googlecode.javacpp.annotation.MemberSetter;
            import com.googlecode.javacpp.annotation.Name;
            import com.googlecode.javacpp.annotation.Namespace;
            import com.googlecode.javacpp.annotation.NoDeallocator;
            import com.googlecode.javacpp.annotation.NoException;
            import com.googlecode.javacpp.annotation.NoOffset;
            import com.googlecode.javacpp.annotation.Opaque;
            import com.googlecode.javacpp.annotation.Platform;
            import com.googlecode.javacpp.annotation.Properties;
            import com.googlecode.javacpp.annotation.Raw;
            import com.googlecode.javacpp.annotation.ValueGetter;
            import com.googlecode.javacpp.annotation.ValueSetter;
            import java.io.Closeable;
            import java.io.FileNotFoundException;
            import java.io.PrintWriter;
            import java.io.Writer;
            import java.lang.annotation.Annotation;
            import java.lang.reflect.Method;
            import java.lang.reflect.Modifier;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            import java.nio.CharBuffer;
            import java.nio.DoubleBuffer;
            import java.nio.FloatBuffer;
            import java.nio.IntBuffer;
            import java.nio.LongBuffer;
            import java.nio.ShortBuffer;
            import java.util.Arrays;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.logging.Level;
            import java.util.logging.Logger;
            import org.apache.commons.io.FilenameUtils;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 113 */   public class Generator implements Closeable {
                public static final String JNI_VERSION = "JNI_VERSION_1_6";
                private LinkedListRegister<Class> arrayDeallocators;
                private LinkedListRegister<Class> deallocators;
                private LinkedListRegister<String> functionDefinitions;
                private LinkedListRegister<String> functionPointers;
                private LinkedListRegister<Class> jclasses;
                private LinkedListRegister<Class> jclassesInit;
                private boolean mayThrowExceptions;
                private HashMap<Class, LinkedList<String>> members;
                private PrintWriter out;
                private PrintWriter out2;
                private Loader.ClassProperties properties;
                private boolean usesAdapters;
                private static final Logger logger = Logger.getLogger(Generator.class.getName());
                private static final List<Class> baseClasses = Arrays.asList(Pointer.class, BytePointer.class, ShortPointer.class, IntPointer.class, LongPointer.class, FloatPointer.class, DoublePointer.class, CharPointer.class, PointerPointer.class, BoolPointer.class, CLongPointer.class, SizeTPointer.class);

                public static class AdapterInformation {
                    public int argc;
                    public String cast;
                    public boolean constant;
                    public String name;
                }

                public static class MethodInformation {
                    public boolean allocator;
                    public Annotation[] annotations;
                    public boolean arrayAllocator;
                    public boolean bufferGetter;
                    public Class<?> cls;
                    public boolean deallocator;
                    public int dim;
                    public boolean memberGetter;
                    public String[] memberName;
                    public boolean memberSetter;
                    public Method method;
                    public int modifiers;
                    public String name;
                    public boolean noOffset;
                    public boolean noReturnGetter;
                    public boolean overloaded;
                    public Method pairedMethod;
                    public Annotation[][] parameterAnnotations;
                    public boolean[] parameterRaw;
                    public Class<?>[] parameterTypes;
                    public boolean returnRaw;
                    public Class<?> returnType;
                    public Class<?> throwsException;
                    public boolean valueGetter;
                    public boolean valueSetter;
                    public boolean withEnv;
                }

/* 114 */       public Generator(Loader.ClassProperties properties) {
/* 116 */           this.properties = properties;
                }

/* 119 */       public static class LinkedListRegister<E> extends LinkedList<E> {
/* 120 */           public int register(E e) {
/* 121 */               int i = indexOf(e);
/* 122 */               if (i < 0) {
/* 123 */                   add(e);
/* 124 */                   return size() - 1;
                        }
/* 126 */               return i;
                    }
                }

                /* JADX INFO: renamed from: com.googlecode.javacpp.Generator$1 */
/* 157 */       class C12211 extends Writer {
/* 158 */           C12211() {
                    }

                    @Override // java.io.Writer
/* 159 */           public void write(char[] cbuf, int off, int len) {
                    }

                    @Override // java.io.Writer, java.io.Flushable
/* 160 */           public void flush() {
                    }

                    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 161 */           public void close() {
                    }
                }

/* 158 */       public boolean generate(String sourceFilename, String headerFilename, String classPath, Class<?>... classes) throws FileNotFoundException {
/* 159 */           this.out = new PrintWriter(new C12211());
/* 164 */           this.out2 = null;
/* 165 */           this.functionDefinitions = new LinkedListRegister<>();
/* 166 */           this.functionPointers = new LinkedListRegister<>();
/* 167 */           this.deallocators = new LinkedListRegister<>();
/* 168 */           this.arrayDeallocators = new LinkedListRegister<>();
/* 169 */           this.jclasses = new LinkedListRegister<>();
/* 170 */           this.jclassesInit = new LinkedListRegister<>();
/* 171 */           this.members = new HashMap<>();
/* 172 */           this.mayThrowExceptions = false;
/* 173 */           this.usesAdapters = false;
/* 174 */           if (!doClasses(true, true, classPath, classes)) {
                        return false;
                    }
/* 176 */           this.out = new PrintWriter(sourceFilename);
/* 177 */           if (headerFilename != null) {
/* 178 */               this.out2 = new PrintWriter(headerFilename);
                    }
/* 180 */           return doClasses(this.mayThrowExceptions, this.usesAdapters, classPath, classes);
                }

                @Override // java.io.Closeable, java.lang.AutoCloseable
/* 186 */       public void close() {
/* 187 */           if (this.out != null) {
/* 188 */               this.out.close();
                    }
/* 190 */           if (this.out2 != null) {
/* 191 */               this.out2.close();
                    }
                }

/* 195 */       private boolean doClasses(boolean handleExceptions, boolean defineAdapters, String classPath, Class<?>... classes) {
/* 196 */           this.out.println("/* DO NOT EDIT THIS FILE - IT IS MACHINE GENERATED */");
/* 197 */           this.out.println();
/* 198 */           if (this.out2 != null) {
/* 199 */               this.out2.println("/* DO NOT EDIT THIS FILE - IT IS MACHINE GENERATED */");
/* 200 */               this.out2.println();
                    }
                    for (String s : this.properties.get("generator.define")) {
/* 203 */               this.out.println("#define " + s);
                    }
/* 205 */           this.out.println();
/* 206 */           this.out.println("#ifdef __APPLE__");
/* 207 */           this.out.println("    #define _JAVASOFT_JNI_MD_H_");
/* 208 */           this.out.println();
/* 209 */           this.out.println("    #define JNIEXPORT __attribute__((visibility(\"default\")))");
/* 210 */           this.out.println("    #define JNIIMPORT");
/* 211 */           this.out.println("    #define JNICALL");
/* 212 */           this.out.println();
/* 213 */           this.out.println("    typedef int jint;");
/* 214 */           this.out.println("    typedef long long jlong;");
/* 215 */           this.out.println("    typedef signed char jbyte;");
/* 216 */           this.out.println("#endif");
/* 217 */           this.out.println("#ifdef _WIN32");
/* 218 */           this.out.println("    #define _JAVASOFT_JNI_MD_H_");
/* 219 */           this.out.println();
/* 220 */           this.out.println("    #define JNIEXPORT __declspec(dllexport)");
/* 221 */           this.out.println("    #define JNIIMPORT __declspec(dllimport)");
/* 222 */           this.out.println("    #define JNICALL __stdcall");
/* 223 */           this.out.println();
/* 224 */           this.out.println("    typedef int jint;");
/* 225 */           this.out.println("    typedef long long jlong;");
/* 226 */           this.out.println("    typedef signed char jbyte;");
/* 227 */           this.out.println("#endif");
/* 228 */           this.out.println("#include <jni.h>");
/* 229 */           if (this.out2 != null) {
/* 230 */               this.out2.println("#include <jni.h>");
                    }
/* 232 */           this.out.println("#ifdef ANDROID");
/* 233 */           this.out.println("    #include <android/log.h>");
/* 234 */           this.out.println("    #define NewWeakGlobalRef(obj) NewGlobalRef(obj)");
/* 235 */           this.out.println("    #define DeleteWeakGlobalRef(obj) DeleteGlobalRef(obj)");
/* 236 */           this.out.println("#endif");
/* 237 */           this.out.println();
/* 238 */           this.out.println("#include <stddef.h>");
/* 239 */           this.out.println("#ifndef _WIN32");
/* 240 */           this.out.println("    #include <stdint.h>");
/* 241 */           this.out.println("#endif");
/* 242 */           this.out.println("#include <stdio.h>");
/* 243 */           this.out.println("#include <stdlib.h>");
/* 244 */           this.out.println("#include <string.h>");
/* 245 */           this.out.println("#include <exception>");
/* 246 */           this.out.println("#include <new>");
/* 247 */           this.out.println();
/* 248 */           this.out.println("#define jlong_to_ptr(a) ((void*)(uintptr_t)(a))");
/* 249 */           this.out.println("#define ptr_to_jlong(a) ((jlong)(uintptr_t)(a))");
/* 250 */           this.out.println();
/* 251 */           this.out.println("#if defined(_MSC_VER)");
/* 252 */           this.out.println("    #define JavaCPP_noinline __declspec(noinline)");
/* 253 */           this.out.println("    #define JavaCPP_hidden /* hidden by default */");
/* 254 */           this.out.println("#elif defined(__GNUC__)");
/* 255 */           this.out.println("    #define JavaCPP_noinline __attribute__((noinline))");
/* 256 */           this.out.println("    #define JavaCPP_hidden   __attribute__((visibility(\"hidden\")))");
/* 257 */           this.out.println("#else");
/* 258 */           this.out.println("    #define JavaCPP_noinline");
/* 259 */           this.out.println("    #define JavaCPP_hidden");
/* 260 */           this.out.println("#endif");
/* 261 */           this.out.println();
/* 262 */           List[] include = {this.properties.get("generator.include"), this.properties.get("generator.cinclude")};
                    for (int i = 0; i < include.length; i++) {
/* 265 */               if (include[i] != null && include[i].size() > 0) {
/* 266 */                   if (i == 1) {
/* 267 */                       this.out.println("extern \"C\" {");
/* 268 */                       if (this.out2 != null) {
/* 269 */                           this.out2.println("#ifdef __cplusplus");
/* 270 */                           this.out2.println("extern \"C\" {");
/* 271 */                           this.out2.println("#endif");
                                }
                            }
                            for (String s2 : (List<String>) include[i]) {
/* 275 */                       String line = "#include ";
/* 276 */                       if (!s2.startsWith("<") && !s2.startsWith("\"")) {
/* 275 */                           line = "#include " + CommonUtil.MARKER_QUOTES;
                                }
/* 279 */                       String line2 = line + s2;
/* 280 */                       if (!s2.endsWith(">") && !s2.endsWith("\"")) {
/* 281 */                           line2 = line2 + CommonUtil.MARKER_QUOTES;
                                }
/* 283 */                       this.out.println(line2);
/* 284 */                       if (this.out2 != null) {
/* 285 */                           this.out2.println(line2);
                                }
                            }
/* 288 */                   if (i == 1) {
/* 289 */                       this.out.println("}");
/* 290 */                       if (this.out2 != null) {
/* 291 */                           this.out2.println("#ifdef __cplusplus");
/* 292 */                           this.out2.println("}");
/* 293 */                           this.out2.println("#endif");
                                }
                            }
/* 296 */                   this.out.println();
                        }
                    }
/* 299 */           this.out.println("static JavaVM* JavaCPP_vm = NULL;");
/* 300 */           this.out.println("static const char* JavaCPP_classNames[" + this.jclasses.size() + "] = {");
/* 301 */           Iterator<Class> classIterator = this.jclasses.iterator();
/* 302 */           int maxMemberSize = 0;
/* 303 */           while (classIterator.hasNext()) {
/* 304 */               Class c = classIterator.next();
/* 305 */               this.out.print("        \"" + c.getName().replace(FilenameUtils.EXTENSION_SEPARATOR, '/') + "\"");
/* 306 */               if (classIterator.hasNext()) {
/* 307 */                   this.out.println(",");
                        }
/* 309 */               LinkedList<String> m = this.members.get(c);
/* 310 */               if (m != null && m.size() > maxMemberSize) {
/* 311 */                   maxMemberSize = m.size();
                        }
                    }
/* 314 */           this.out.println(" };");
/* 315 */           this.out.println("static jclass JavaCPP_classes[" + this.jclasses.size() + "] = { NULL };");
/* 316 */           this.out.println("static jmethodID JavaCPP_initMID = NULL;");
/* 317 */           this.out.println("static jfieldID JavaCPP_addressFID = NULL;");
/* 318 */           this.out.println("static jfieldID JavaCPP_positionFID = NULL;");
/* 319 */           this.out.println("static jfieldID JavaCPP_limitFID = NULL;");
/* 320 */           this.out.println("static jfieldID JavaCPP_capacityFID = NULL;");
/* 321 */           this.out.println();
/* 322 */           this.out.println("static inline void JavaCPP_log(const char* fmt, ...) {");
/* 323 */           this.out.println("    va_list ap;");
/* 324 */           this.out.println("    va_start(ap, fmt);");
/* 325 */           this.out.println("#ifdef ANDROID");
/* 326 */           this.out.println("    __android_log_vprint(ANDROID_LOG_ERROR, \"javacpp\", fmt, ap);");
/* 327 */           this.out.println("#else");
/* 328 */           this.out.println("    vfprintf(stderr, fmt, ap);");
/* 329 */           this.out.println("    fprintf(stderr, \"\\n\");");
/* 330 */           this.out.println("#endif");
/* 331 */           this.out.println("    va_end(ap);");
/* 332 */           this.out.println("}");
/* 333 */           this.out.println();
/* 334 */           this.out.println("static JavaCPP_noinline jclass JavaCPP_getClass(JNIEnv* env, int i) {");
/* 335 */           this.out.println("    if (JavaCPP_classes[i] == NULL && env->PushLocalFrame(1) == 0) {");
/* 336 */           this.out.println("        jclass cls = env->FindClass(JavaCPP_classNames[i]);");
/* 337 */           this.out.println("        if (cls == NULL || env->ExceptionCheck()) {");
/* 338 */           this.out.println("            JavaCPP_log(\"Error loading class %s.\", JavaCPP_classNames[i]);");
/* 339 */           this.out.println("            return NULL;");
/* 340 */           this.out.println("        }");
/* 341 */           this.out.println("        JavaCPP_classes[i] = (jclass)env->NewWeakGlobalRef(cls);");
/* 342 */           this.out.println("        if (JavaCPP_classes[i] == NULL || env->ExceptionCheck()) {");
/* 343 */           this.out.println("            JavaCPP_log(\"Error creating global reference of class %s.\", JavaCPP_classNames[i]);");
/* 344 */           this.out.println("            return NULL;");
/* 345 */           this.out.println("        }");
/* 346 */           this.out.println("        env->PopLocalFrame(NULL);");
/* 347 */           this.out.println("    }");
/* 348 */           this.out.println("    return JavaCPP_classes[i];");
/* 349 */           this.out.println("}");
/* 350 */           this.out.println();
/* 351 */           this.out.println("class JavaCPP_hidden JavaCPP_exception : public std::exception {");
/* 352 */           this.out.println("public:");
/* 353 */           this.out.println("    JavaCPP_exception(const char* str) throw() {");
/* 354 */           this.out.println("        if (str == NULL) {");
/* 355 */           this.out.println("            strcpy(msg, \"Unknown exception.\");");
/* 356 */           this.out.println("        } else {");
/* 357 */           this.out.println("            strncpy(msg, str, sizeof(msg));");
/* 358 */           this.out.println("            msg[sizeof(msg) - 1] = 0;");
/* 359 */           this.out.println("        }");
/* 360 */           this.out.println("    }");
/* 361 */           this.out.println("    virtual const char* what() const throw() { return msg; }");
/* 362 */           this.out.println("    char msg[1024];");
/* 363 */           this.out.println("};");
/* 364 */           this.out.println();
/* 365 */           if (handleExceptions) {
/* 366 */               this.out.println("static JavaCPP_noinline jthrowable JavaCPP_handleException(JNIEnv* env, int i) {");
/* 367 */               this.out.println("    jstring str = NULL;");
/* 368 */               this.out.println("    try {");
/* 369 */               this.out.println("        throw;");
/* 370 */               this.out.println("    } catch (std::exception& e) {");
/* 371 */               this.out.println("        str = env->NewStringUTF(e.what());");
/* 372 */               this.out.println("    } catch (...) {");
/* 373 */               this.out.println("        str = env->NewStringUTF(\"Unknown exception.\");");
/* 374 */               this.out.println("    }");
/* 375 */               this.out.println("    jclass cls = JavaCPP_getClass(env, i);");
/* 376 */               this.out.println("    jmethodID mid = env->GetMethodID(cls, \"<init>\", \"(Ljava/lang/String;)V\");");
/* 377 */               this.out.println("    if (mid == NULL || env->ExceptionCheck()) {");
/* 378 */               this.out.println("        JavaCPP_log(\"Error getting constructor ID of %s.\", JavaCPP_classNames[i]);");
/* 379 */               this.out.println("        return NULL;");
/* 380 */               this.out.println("    } else {");
/* 381 */               this.out.println("        return (jthrowable)env->NewObject(cls, mid, str);");
/* 382 */               this.out.println("    }");
/* 383 */               this.out.println("}");
/* 384 */               this.out.println();
                    }
/* 386 */           if (defineAdapters) {
/* 387 */               this.out.println("#include <vector>");
/* 388 */               this.out.println("template<typename P, typename T = P> class JavaCPP_hidden VectorAdapter {");
/* 389 */               this.out.println("public:");
/* 390 */               this.out.println("    VectorAdapter(const P* ptr, typename std::vector<T>::size_type size) : ptr((P*)ptr), size(size),");
/* 391 */               this.out.println("        vec2(ptr ? std::vector<T>((P*)ptr, (P*)ptr + size) : std::vector<T>()), vec(vec2) { }");
/* 392 */               this.out.println("    VectorAdapter(const std::vector<T>& vec) : ptr(0), size(0), vec2(vec), vec(vec2) { }");
/* 393 */               this.out.println("    VectorAdapter(      std::vector<T>& vec) : ptr(0), size(0), vec(vec) { }");
/* 394 */               this.out.println("    void assign(P* ptr, typename std::vector<T>::size_type size) {");
/* 395 */               this.out.println("        this->ptr = ptr;");
/* 396 */               this.out.println("        this->size = size;");
/* 397 */               this.out.println("        vec.assign(ptr, ptr + size);");
/* 398 */               this.out.println("    }");
/* 399 */               this.out.println("    static void deallocate(P* ptr) { delete[] ptr; }");
/* 400 */               this.out.println("    operator P*() {");
/* 401 */               this.out.println("        if (vec.size() > size) {");
/* 402 */               this.out.println("            ptr = new (std::nothrow) P[vec.size()];");
/* 403 */               this.out.println("        }");
/* 404 */               this.out.println("        if (ptr) {");
/* 405 */               this.out.println("            std::copy(vec.begin(), vec.end(), ptr);");
/* 406 */               this.out.println("        }");
/* 407 */               this.out.println("        size = vec.size();");
/* 408 */               this.out.println("        return ptr;");
/* 409 */               this.out.println("    }");
/* 410 */               this.out.println("    operator const P*()        { return &vec[0]; }");
/* 411 */               this.out.println("    operator std::vector<T>&() { return vec; }");
/* 412 */               this.out.println("    operator std::vector<T>*() { return ptr ? &vec : 0; }");
/* 413 */               this.out.println("    P* ptr;");
/* 414 */               this.out.println("    typename std::vector<T>::size_type size;");
/* 415 */               this.out.println("    std::vector<T> vec2;");
/* 416 */               this.out.println("    std::vector<T>& vec;");
/* 417 */               this.out.println("};");
/* 418 */               this.out.println();
/* 419 */               this.out.println("#include <string>");
/* 420 */               this.out.println("class JavaCPP_hidden StringAdapter {");
/* 421 */               this.out.println("public:");
/* 422 */               this.out.println("    StringAdapter(const          char* ptr, size_t size) : ptr((char*)ptr), size(size),");
/* 423 */               this.out.println("        str2(ptr ? (char*)ptr : \"\"), str(str2) { }");
/* 424 */               this.out.println("    StringAdapter(const signed   char* ptr, size_t size) : ptr((char*)ptr), size(size),");
/* 425 */               this.out.println("        str2(ptr ? (char*)ptr : \"\"), str(str2) { }");
/* 426 */               this.out.println("    StringAdapter(const unsigned char* ptr, size_t size) : ptr((char*)ptr), size(size),");
/* 427 */               this.out.println("        str2(ptr ? (char*)ptr : \"\"), str(str2) { }");
/* 428 */               this.out.println("    StringAdapter(const std::string& str) : ptr(0), size(0), str2(str), str(str2) { }");
/* 429 */               this.out.println("    StringAdapter(      std::string& str) : ptr(0), size(0), str(str) { }");
/* 430 */               this.out.println("    void assign(char* ptr, size_t size) {");
/* 431 */               this.out.println("        this->ptr = ptr;");
/* 432 */               this.out.println("        this->size = size;");
/* 433 */               this.out.println("        str.assign(ptr ? ptr : \"\");");
/* 434 */               this.out.println("    }");
/* 435 */               this.out.println("    static void deallocate(char* ptr) { free(ptr); }");
/* 436 */               this.out.println("    operator char*() {");
/* 437 */               this.out.println("        const char* c_str = str.c_str();");
/* 438 */               this.out.println("        if (ptr == NULL || strcmp(c_str, ptr) != 0) {");
/* 439 */               this.out.println("            ptr = strdup(c_str);");
/* 440 */               this.out.println("        }");
/* 441 */               this.out.println("        size = strlen(c_str) + 1;");
/* 442 */               this.out.println("        return ptr;");
/* 443 */               this.out.println("    }");
/* 444 */               this.out.println("    operator       signed   char*() { return (signed   char*)(operator char*)(); }");
/* 445 */               this.out.println("    operator       unsigned char*() { return (unsigned char*)(operator char*)(); }");
/* 446 */               this.out.println("    operator const          char*() { return                 str.c_str(); }");
/* 447 */               this.out.println("    operator const signed   char*() { return (signed   char*)str.c_str(); }");
/* 448 */               this.out.println("    operator const unsigned char*() { return (unsigned char*)str.c_str(); }");
/* 449 */               this.out.println("    operator         std::string&() { return str; }");
/* 450 */               this.out.println("    operator         std::string*() { return ptr ? &str : 0; }");
/* 451 */               this.out.println("    char* ptr;");
/* 452 */               this.out.println("    size_t size;");
/* 453 */               this.out.println("    std::string str2;");
/* 454 */               this.out.println("    std::string& str;");
/* 455 */               this.out.println("};");
/* 456 */               this.out.println();
                    }
/* 458 */           if (!this.functionDefinitions.isEmpty()) {
/* 459 */               this.out.println("static JavaCPP_noinline void JavaCPP_detach(int detach) {");
/* 460 */               this.out.println("    if (detach > 0 && JavaCPP_vm->DetachCurrentThread() != 0) {");
/* 461 */               this.out.println("        JavaCPP_log(\"Could not detach the JavaVM from the current thread.\");");
/* 462 */               this.out.println("    }");
/* 463 */               this.out.println("}");
/* 464 */               this.out.println();
/* 465 */               this.out.println("static JavaCPP_noinline int JavaCPP_getEnv(JNIEnv** env) {");
/* 466 */               this.out.println("    int attached = 0;");
/* 467 */               this.out.println("    struct {");
/* 468 */               this.out.println("        JNIEnv **env;");
/* 469 */               this.out.println("        operator JNIEnv**() { return env; } // Android JNI");
/* 470 */               this.out.println("        operator void**() { return (void**)env; } // standard JNI");
/* 471 */               this.out.println("    } env2 = { env };");
/* 472 */               this.out.println("    JavaVM *vm = JavaCPP_vm;");
/* 473 */               this.out.println("    if (vm == NULL) {");
/* 474 */               if (this.out2 != null) {
/* 475 */                   this.out.println("#ifndef ANDROID");
/* 476 */                   this.out.println("        int size = 1;");
/* 477 */                   this.out.println("        if (JNI_GetCreatedJavaVMs(&vm, 1, &size) != 0 || size == 0) {");
/* 478 */                   this.out.println("#endif");
                        }
/* 480 */               this.out.println("            JavaCPP_log(\"Could not get any created JavaVM.\");");
/* 481 */               this.out.println("            return -1;");
/* 482 */               if (this.out2 != null) {
/* 483 */                   this.out.println("#ifndef ANDROID");
/* 484 */                   this.out.println("        }");
/* 485 */                   this.out.println("#endif");
                        }
/* 487 */               this.out.println("    }");
/* 488 */               this.out.println("    if (vm->GetEnv((void**)env, JNI_VERSION_1_6) != JNI_OK) {");
/* 489 */               this.out.println("        if (vm->AttachCurrentThread(env2, NULL) != 0) {");
/* 490 */               this.out.println("            JavaCPP_log(\"Could not attach the JavaVM to the current thread.\");");
/* 491 */               this.out.println("            return -1;");
/* 492 */               this.out.println("        }");
/* 493 */               this.out.println("        attached = 1;");
/* 494 */               this.out.println("    }");
/* 495 */               this.out.println("    if (JavaCPP_vm == NULL) {");
/* 496 */               this.out.println("        if (JNI_OnLoad(vm, NULL) < 0) {");
/* 497 */               this.out.println("            JavaCPP_detach(attached);");
/* 498 */               this.out.println("            return -1;");
/* 499 */               this.out.println("        }");
/* 500 */               this.out.println("    }");
/* 501 */               this.out.println("    return attached;");
/* 502 */               this.out.println("}");
/* 503 */               this.out.println();
                    }
                    for (String s3 : this.functionDefinitions) {
/* 506 */               this.out.println(s3);
                    }
/* 508 */           this.out.println();
                    for (String s4 : this.functionPointers) {
/* 510 */               this.out.println(s4);
                    }
/* 512 */           this.out.println();
/* 513 */           Iterator i$ = this.deallocators.iterator();
                    while (i$.hasNext()) {
                        Class<?> cls = (Class) i$.next();
/* 514 */               String name = "JavaCPP_" + mangle(cls.getName());
/* 515 */               this.out.print("static void " + name + "_deallocate(");
/* 516 */               if (FunctionPointer.class.isAssignableFrom(cls)) {
/* 518 */                   this.out.println(getFunctionClassName(cls) + "* p) { JNIEnv *e; int a = JavaCPP_getEnv(&e); if (a >= 0) e->DeleteWeakGlobalRef(p->obj); delete p; JavaCPP_detach(a); }");
                        } else {
/* 520 */                   String[] typeName = getCPPTypeName(cls);
/* 521 */                   this.out.println(typeName[0] + " p" + typeName[1] + ") { delete p; }");
                        }
                    }
                    for (Class c2 : this.arrayDeallocators) {
/* 525 */               String name2 = "JavaCPP_" + mangle(c2.getName());
/* 526 */               String[] typeName2 = getCPPTypeName(c2);
/* 527 */               this.out.println("static void " + name2 + "_deallocateArray(" + typeName2[0] + " p" + typeName2[1] + ") { delete[] p; }");
                    }
/* 529 */           this.out.println();
/* 530 */           this.out.println("extern \"C\" {");
/* 531 */           if (this.out2 != null) {
/* 532 */               this.out2.println();
/* 533 */               this.out2.println("#ifdef __cplusplus");
/* 534 */               this.out2.println("extern \"C\" {");
/* 535 */               this.out2.println("#endif");
/* 536 */               this.out2.println("JNIIMPORT int JavaCPP_init(int argc, const char *argv[]);");
/* 537 */               this.out.println();
/* 538 */               this.out.println("JNIEXPORT int JavaCPP_init(int argc, const char *argv[]) {");
/* 539 */               this.out.println("#ifdef ANDROID");
/* 540 */               this.out.println("    return JNI_OK;");
/* 541 */               this.out.println("#else");
/* 542 */               this.out.println("    JavaVM *vm;");
/* 543 */               this.out.println("    JNIEnv *env;");
/* 544 */               this.out.println("    int nOptions = 1 + (argc > 255 ? 255 : argc);");
/* 545 */               this.out.println("    JavaVMOption options[256] = { { NULL } };");
/* 546 */               this.out.println("    options[0].optionString = (char*)\"-Djava.class.path=" + classPath.replace(IOUtils.DIR_SEPARATOR_WINDOWS, '/') + "\";");
/* 547 */               this.out.println("    for (int i = 1; i < nOptions && argv != NULL; i++) {");
/* 548 */               this.out.println("        options[i].optionString = (char*)argv[i - 1];");
/* 549 */               this.out.println("    }");
/* 550 */               this.out.println("    JavaVMInitArgs vm_args = { JNI_VERSION_1_6, nOptions, options };");
/* 551 */               this.out.println("    return JNI_CreateJavaVM(&vm, (void **)&env, &vm_args);");
/* 552 */               this.out.println("#endif");
/* 553 */               this.out.println("}");
                    }
/* 555 */           this.out.println();
/* 556 */           this.out.println("JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {");
/* 557 */           this.out.println("    JNIEnv* env;");
/* 558 */           this.out.println("    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {");
/* 559 */           this.out.println("        JavaCPP_log(\"Could not get JNIEnv for JNI_VERSION_1_6 inside JNI_OnLoad().\");");
/* 560 */           this.out.println("        return JNI_ERR;");
/* 561 */           this.out.println("    }");
/* 562 */           this.out.println("    if (JavaCPP_vm == vm) {");
/* 563 */           this.out.println("        return env->GetVersion();");
/* 564 */           this.out.println("    }");
/* 565 */           this.out.println("    JavaCPP_vm = vm;");
/* 566 */           this.out.println("    const char* members[" + this.jclasses.size() + "][" + maxMemberSize + "] = {");
/* 567 */           Iterator<Class> classIterator2 = this.jclasses.iterator();
/* 568 */           while (classIterator2.hasNext()) {
/* 569 */               this.out.print("            { ");
/* 570 */               LinkedList<String> m2 = this.members.get(classIterator2.next());
/* 571 */               Iterator<String> memberIterator = m2 == null ? null : m2.iterator();
/* 572 */               while (memberIterator != null && memberIterator.hasNext()) {
/* 573 */                   this.out.print("\"" + memberIterator.next() + "\"");
/* 574 */                   if (memberIterator.hasNext()) {
/* 575 */                       this.out.print(", ");
                            }
                        }
/* 578 */               this.out.print(" }");
/* 579 */               if (classIterator2.hasNext()) {
/* 580 */                   this.out.println(",");
                        }
                    }
/* 583 */           this.out.println(" };");
/* 584 */           this.out.println("    int offsets[" + this.jclasses.size() + "][" + maxMemberSize + "] = {");
/* 585 */           Iterator<Class> classIterator3 = this.jclasses.iterator();
/* 586 */           while (classIterator3.hasNext()) {
/* 587 */               this.out.print("            { ");
/* 588 */               Class c3 = classIterator3.next();
/* 589 */               LinkedList<String> m3 = this.members.get(c3);
/* 590 */               Iterator<String> memberIterator2 = m3 == null ? null : m3.iterator();
/* 591 */               while (memberIterator2 != null && memberIterator2.hasNext()) {
/* 593 */                   String valueTypeName = getValueTypeName(getCPPTypeName(c3));
/* 594 */                   String memberName = memberIterator2.next();
/* 595 */                   if ("sizeof".equals(memberName)) {
/* 596 */                       if ("void".equals(valueTypeName)) {
/* 597 */                           valueTypeName = "void*";
                                }
/* 599 */                       this.out.print("sizeof(" + valueTypeName + ")");
                            } else {
/* 601 */                       this.out.print("offsetof(" + valueTypeName + "," + memberName + ")");
                            }
/* 603 */                   if (memberIterator2.hasNext()) {
/* 604 */                       this.out.print(", ");
                            }
                        }
/* 607 */               this.out.print(" }");
/* 608 */               if (classIterator3.hasNext()) {
/* 609 */                   this.out.println(",");
                        }
                    }
/* 612 */           this.out.println(" };");
/* 613 */           this.out.print("    int memberOffsetSizes[" + this.jclasses.size() + "] = { ");
/* 614 */           Iterator<Class> classIterator4 = this.jclasses.iterator();
/* 615 */           while (classIterator4.hasNext()) {
/* 616 */               LinkedList<String> m4 = this.members.get(classIterator4.next());
/* 617 */               this.out.print(m4 == null ? 0 : m4.size());
/* 618 */               if (classIterator4.hasNext()) {
/* 619 */                   this.out.print(", ");
                        }
                    }
/* 622 */           this.out.println(" };");
/* 623 */           this.out.println("    jmethodID putMemberOffsetMID = env->GetStaticMethodID(JavaCPP_getClass(env, " + this.jclasses.register(Loader.class) + "), \"putMemberOffset\", \"(Ljava/lang/String;Ljava/lang/String;I)V\");");
/* 625 */           this.out.println("    if (putMemberOffsetMID == NULL || env->ExceptionCheck()) {");
/* 626 */           this.out.println("        JavaCPP_log(\"Error getting method ID of Loader.putMemberOffset().\");");
/* 627 */           this.out.println("        return JNI_ERR;");
/* 628 */           this.out.println("    }");
/* 629 */           this.out.println("    for (int i = 0; i < " + this.jclasses.size() + " && !env->ExceptionCheck(); i++) {");
/* 630 */           this.out.println("        for (int j = 0; j < memberOffsetSizes[i] && !env->ExceptionCheck(); j++) {");
/* 631 */           this.out.println("            if (env->PushLocalFrame(2) == 0) {");
/* 632 */           this.out.println("                jvalue args[3];");
/* 633 */           this.out.println("                args[0].l = env->NewStringUTF(JavaCPP_classNames[i]);");
/* 634 */           this.out.println("                args[1].l = env->NewStringUTF(members[i][j]);");
/* 635 */           this.out.println("                args[2].i = offsets[i][j];");
/* 636 */           this.out.println("                env->CallStaticVoidMethodA(JavaCPP_getClass(env, " + this.jclasses.register(Loader.class) + "), putMemberOffsetMID, args);");
/* 638 */           this.out.println("                env->PopLocalFrame(NULL);");
/* 639 */           this.out.println("            }");
/* 640 */           this.out.println("        }");
/* 641 */           this.out.println("    }");
/* 642 */           this.out.println("    JavaCPP_initMID = env->GetMethodID(JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), \"init\", \"(JIJ)V\");");
/* 644 */           this.out.println("    if (JavaCPP_initMID == NULL || env->ExceptionCheck()) {");
/* 645 */           this.out.println("        JavaCPP_log(\"Error getting method ID of Pointer.init().\");");
/* 646 */           this.out.println("        return JNI_ERR;");
/* 647 */           this.out.println("    }");
/* 648 */           this.out.println("    JavaCPP_addressFID = env->GetFieldID(JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), \"address\", \"J\");");
/* 650 */           this.out.println("    if (JavaCPP_addressFID == NULL || env->ExceptionCheck()) {");
/* 651 */           this.out.println("        JavaCPP_log(\"Error getting field ID of Pointer.address.\");");
/* 652 */           this.out.println("        return JNI_ERR;");
/* 653 */           this.out.println("    }");
/* 654 */           this.out.println("    JavaCPP_positionFID = env->GetFieldID(JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), \"position\", \"I\");");
/* 656 */           this.out.println("    if (JavaCPP_positionFID == NULL || env->ExceptionCheck()) {");
/* 657 */           this.out.println("        JavaCPP_log(\"Error getting field ID of Pointer.position.\");");
/* 658 */           this.out.println("        return JNI_ERR;");
/* 659 */           this.out.println("    }");
/* 660 */           this.out.println("    JavaCPP_limitFID = env->GetFieldID(JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), \"limit\", \"I\");");
/* 662 */           this.out.println("    if (JavaCPP_limitFID == NULL || env->ExceptionCheck()) {");
/* 663 */           this.out.println("        JavaCPP_log(\"Error getting field ID of Pointer.limit.\");");
/* 664 */           this.out.println("        return JNI_ERR;");
/* 665 */           this.out.println("    }");
/* 666 */           this.out.println("    JavaCPP_capacityFID = env->GetFieldID(JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), \"capacity\", \"I\");");
/* 668 */           this.out.println("    if (JavaCPP_capacityFID == NULL || env->ExceptionCheck()) {");
/* 669 */           this.out.println("        JavaCPP_log(\"Error getting field ID of Pointer.capacity.\");");
/* 670 */           this.out.println("        return JNI_ERR;");
/* 671 */           this.out.println("    }");
/* 673 */           for (Class c4 : this.jclassesInit) {
/* 675 */               if (c4 != Pointer.class) {
/* 678 */                   this.out.println("    if (JavaCPP_getClass(env, " + this.jclasses.indexOf(c4) + ") == NULL) {");
/* 679 */                   this.out.println("        return JNI_ERR;");
/* 680 */                   this.out.println("    }");
                        }
                    }
/* 682 */           this.out.println("    return env->GetVersion();");
/* 683 */           this.out.println("}");
/* 684 */           this.out.println();
/* 685 */           if (this.out2 != null) {
/* 686 */               this.out2.println("JNIIMPORT int JavaCPP_uninit();");
/* 687 */               this.out2.println();
/* 688 */               this.out.println("JNIEXPORT int JavaCPP_uninit() {");
/* 689 */               this.out.println("#ifdef ANDROID");
/* 690 */               this.out.println("    return JNI_OK;");
/* 691 */               this.out.println("#else");
/* 692 */               this.out.println("    JavaVM *vm = JavaCPP_vm;");
/* 693 */               this.out.println("    JNI_OnUnload(JavaCPP_vm, NULL);");
/* 694 */               this.out.println("    return vm->DestroyJavaVM();");
/* 695 */               this.out.println("#endif");
/* 696 */               this.out.println("}");
                    }
/* 698 */           this.out.println();
/* 699 */           this.out.println("JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved) {");
/* 700 */           this.out.println("    JNIEnv* env;");
/* 701 */           this.out.println("    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {");
/* 702 */           this.out.println("        JavaCPP_log(\"Could not get JNIEnv for JNI_VERSION_1_6 inside JNI_OnUnLoad().\");");
/* 703 */           this.out.println("        return;");
/* 704 */           this.out.println("    }");
/* 705 */           this.out.println("    for (int i = 0; i < " + this.jclasses.size() + "; i++) {");
/* 706 */           this.out.println("        env->DeleteWeakGlobalRef(JavaCPP_classes[i]);");
/* 707 */           this.out.println("        JavaCPP_classes[i] = NULL;");
/* 708 */           this.out.println("    }");
/* 709 */           this.out.println("    JavaCPP_vm = NULL;");
/* 710 */           this.out.println("}");
/* 711 */           this.out.println();
/* 713 */           Iterator<Class> it = baseClasses.iterator();
                    while (it.hasNext()) {
/* 714 */               doMethods(it.next());
                    }
/* 717 */           boolean didSomethingUseful = false;
                    for (Class<?> cls2 : classes) {
                        try {
/* 720 */                   didSomethingUseful |= doMethods(cls2);
                        } catch (NoClassDefFoundError e) {
/* 722 */                   logger.log(Level.WARNING, "Could not generate code for class " + cls2.getCanonicalName() + ": " + e);
                        }
                    }
/* 726 */           this.out.println("}");
/* 727 */           this.out.println();
/* 728 */           if (this.out2 != null) {
/* 729 */               this.out2.println("#ifdef __cplusplus");
/* 730 */               this.out2.println("}");
/* 731 */               this.out2.println("#endif");
                    }
/* 734 */           return didSomethingUseful;
                }

/* 737 */       private boolean doMethods(Class<?> cls) {
/* 738 */           if (!checkPlatform(cls)) {
/* 739 */               return false;
                    }
/* 742 */           LinkedList<String> memberList = this.members.get(cls);
/* 743 */           if (!cls.isAnnotationPresent(Opaque.class) && !FunctionPointer.class.isAssignableFrom(cls)) {
/* 745 */               if (memberList == null) {
/* 746 */                   HashMap<Class, LinkedList<String>> map = this.members;
                            memberList = new LinkedList<>();
                            map.put(cls, memberList);
                        }
/* 748 */               if (!memberList.contains("sizeof")) {
/* 749 */                   memberList.add("sizeof");
                        }
                    }
/* 753 */           boolean didSomething = false;
/* 754 */           Class<?>[] declaredClasses = cls.getDeclaredClasses();
                    for (int i = 0; i < declaredClasses.length; i++) {
/* 756 */               if (Pointer.class.isAssignableFrom(declaredClasses[i]) || Pointer.Deallocator.class.isAssignableFrom(declaredClasses[i])) {
/* 758 */                   didSomething |= doMethods(declaredClasses[i]);
                        }
                    }
/* 762 */           Method[] methods = cls.getDeclaredMethods();
/* 763 */           boolean[] callbackAllocators = new boolean[methods.length];
/* 764 */           Method functionMethod = getFunctionMethod(cls, callbackAllocators);
/* 765 */           if (functionMethod != null) {
/* 766 */               String[] typeName = getCPPTypeName(cls);
/* 767 */               String[] returnConvention = typeName[0].split("\\(");
/* 768 */               returnConvention[1] = getValueTypeName(returnConvention[1]);
/* 769 */               String parameterDeclaration = typeName[1].substring(1);
/* 770 */               String instanceTypeName = getFunctionClassName(cls);
/* 771 */               this.functionDefinitions.register("struct JavaCPP_hidden " + instanceTypeName + " {\n    " + instanceTypeName + "() : ptr(NULL), obj(NULL) { }\n    " + returnConvention[0] + "operator()" + parameterDeclaration + ";\n    " + typeName[0] + "ptr" + typeName[1] + ";\n    jobject obj; static jmethodID mid;\n};\njmethodID " + instanceTypeName + "::mid = NULL;");
                    }
/* 779 */           boolean firstCallback = true;
                    for (int i2 = 0; i2 < methods.length; i2++) {
/* 781 */               String nativeName = mangle(cls.getName()) + "_" + mangle(methods[i2].getName());
/* 782 */               if (checkPlatform((Platform) methods[i2].getAnnotation(Platform.class))) {
/* 785 */                   MethodInformation methodInfo = getMethodInformation(methods[i2]);
/* 787 */                   String callbackName = "JavaCPP_" + nativeName + "_callback";
/* 788 */                   if (callbackAllocators[i2] && functionMethod == null) {
/* 789 */                       logger.log(Level.WARNING, "No callback method call() or apply() has been not declared in \"" + cls.getCanonicalName() + "\". No code will be generated for callback allocator.");
                            } else {
/* 792 */                       if (callbackAllocators[i2] || (methods[i2].equals(functionMethod) && methodInfo == null)) {
/* 793 */                           Name name = (Name) methods[i2].getAnnotation(Name.class);
/* 794 */                           if (name != null && name.value().length > 0 && name.value()[0].length() > 0) {
/* 795 */                               callbackName = name.value()[0];
                                    }
/* 797 */                           doCallback(cls, functionMethod, callbackName, firstCallback);
/* 798 */                           firstCallback = false;
/* 799 */                           didSomething = true;
                                }
/* 802 */                       if (methodInfo != null) {
/* 806 */                           if ((methodInfo.memberGetter || methodInfo.memberSetter) && !methodInfo.noOffset && memberList != null && !Modifier.isStatic(methodInfo.modifiers) && !memberList.contains(methodInfo.memberName[0])) {
/* 809 */                               memberList.add(methodInfo.memberName[0]);
                                    }
/* 813 */                           didSomething = true;
/* 814 */                           this.out.print("JNIEXPORT " + getJNITypeName(methodInfo.returnType) + " JNICALL Java_" + nativeName);
/* 815 */                           if (methodInfo.overloaded) {
/* 816 */                               this.out.print("__" + mangle(getSignature(methodInfo.parameterTypes)));
                                    }
/* 818 */                           if (Modifier.isStatic(methodInfo.modifiers)) {
/* 819 */                               this.out.print("(JNIEnv* env, jclass cls");
                                    } else {
/* 821 */                               this.out.print("(JNIEnv* env, jobject obj");
                                    }
                                    for (int j = 0; j < methodInfo.parameterTypes.length; j++) {
/* 824 */                               this.out.print(", " + getJNITypeName(methodInfo.parameterTypes[j]) + " arg" + j);
                                    }
/* 826 */                           this.out.println(") {");
/* 828 */                           if (callbackAllocators[i2]) {
/* 829 */                               doCallbackAllocator(cls, callbackName);
                                    } else {
/* 831 */                               if (!Modifier.isStatic(methodInfo.modifiers) && !methodInfo.allocator && !methodInfo.arrayAllocator && !methodInfo.deallocator) {
/* 834 */                                   String[] typeName2 = getCPPTypeName(cls);
/* 835 */                                   if ("void*".equals(typeName2[0])) {
/* 836 */                                       typeName2[0] = "char*";
                                            } else if (FunctionPointer.class.isAssignableFrom(cls)) {
/* 838 */                                       typeName2[0] = getFunctionClassName(cls) + "*";
/* 839 */                                       typeName2[1] = "";
                                            }
/* 841 */                                   this.out.println("    " + typeName2[0] + " ptr" + typeName2[1] + " = (" + typeName2[0] + typeName2[1] + ")jlong_to_ptr(env->GetLongField(obj, JavaCPP_addressFID));");
/* 843 */                                   this.out.println("    if (ptr == NULL) {");
/* 844 */                                   this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.register(NullPointerException.class) + "), \"This pointer address is NULL.\");");
/* 846 */                                   this.out.println("        return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
/* 847 */                                   this.out.println("    }");
/* 848 */                                   if (FunctionPointer.class.isAssignableFrom(cls)) {
/* 849 */                                       this.out.println("    if (ptr->ptr == NULL) {");
/* 850 */                                       this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.register(NullPointerException.class) + "), \"This function pointer address is NULL.\");");
/* 852 */                                       this.out.println("        return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
/* 853 */                                       this.out.println("    }");
                                            }
/* 855 */                                   if (!cls.isAnnotationPresent(Opaque.class)) {
/* 856 */                                       this.out.println("    jint position = env->GetIntField(obj, JavaCPP_positionFID);");
/* 857 */                                       this.out.println("    ptr += position;");
/* 858 */                                       if (methodInfo.bufferGetter) {
/* 859 */                                           this.out.println("    jint size = env->GetIntField(obj, JavaCPP_limitFID);");
/* 860 */                                           this.out.println("    size -= position;");
                                                }
                                            }
                                        }
/* 865 */                               doParametersBefore(methodInfo);
/* 866 */                               String returnPrefix = doReturnBefore(methodInfo);
/* 867 */                               doCall(methodInfo, returnPrefix);
/* 868 */                               doReturnAfter(methodInfo);
/* 869 */                               doParametersAfter(methodInfo);
/* 870 */                               if (methodInfo.throwsException != null) {
/* 871 */                                   this.out.println("    if (exc != NULL) {");
/* 872 */                                   this.out.println("        env->Throw(exc);");
/* 873 */                                   this.out.println("    }");
                                        }
/* 875 */                               if (methodInfo.returnType != Void.TYPE) {
/* 876 */                                   this.out.println("    return rarg;");
                                        }
/* 878 */                               this.out.println("}");
                                    }
                                }
                            }
                        }
                    }
/* 880 */           this.out.println();
/* 881 */           return didSomething;
                }

/* 884 */       private void doParametersBefore(MethodInformation methodInfo) {
/* 885 */           String adapterLine = "";
/* 886 */           AdapterInformation prevAdapterInfo = null;
                    for (int j = 0; j < methodInfo.parameterTypes.length; j++) {
/* 888 */               if (!methodInfo.parameterTypes[j].isPrimitive()) {
/* 889 */                   Annotation passBy = getParameterBy(methodInfo, j);
/* 890 */                   String cast = getParameterCast(methodInfo, j);
/* 891 */                   String[] typeName = getCPPTypeName(methodInfo.parameterTypes[j]);
/* 892 */                   AdapterInformation adapterInfo = getParameterAdapterInformation(false, methodInfo, j);
/* 894 */                   if (FunctionPointer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
/* 895 */                       if (methodInfo.parameterTypes[j] == FunctionPointer.class) {
/* 896 */                           logger.log(Level.WARNING, "Method \"" + methodInfo.method + "\" has an abstract FunctionPointer parameter, but a concrete subclass is required. Compilation will most likely fail.");
                                }
/* 899 */                       typeName[0] = getFunctionClassName(methodInfo.parameterTypes[j]) + "*";
/* 900 */                       typeName[1] = "";
                            }
/* 903 */                   if (typeName[0].length() == 0 || methodInfo.parameterRaw[j]) {
/* 904 */                       methodInfo.parameterRaw[j] = true;
/* 905 */                       typeName[0] = getJNITypeName(methodInfo.parameterTypes[j]);
/* 906 */                       this.out.println("    " + typeName[0] + " ptr" + j + " = arg" + j + ";");
                            } else {
/* 910 */                       if ("void*".equals(typeName[0])) {
/* 911 */                           typeName[0] = "char*";
                                }
/* 913 */                       this.out.print("    " + typeName[0] + " ptr" + j + typeName[1] + " = ");
/* 914 */                       if (Pointer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
/* 915 */                           this.out.println("arg" + j + " == NULL ? NULL : (" + typeName[0] + typeName[1] + ")jlong_to_ptr(env->GetLongField(arg" + j + ", JavaCPP_addressFID));");
/* 917 */                           if ((j == 0 && FunctionPointer.class.isAssignableFrom(methodInfo.cls) && methodInfo.cls.isAnnotationPresent(Namespace.class)) || (passBy instanceof ByVal) || (passBy instanceof ByRef)) {
/* 921 */                               this.out.println("    if (ptr" + j + " == NULL) {");
/* 922 */                               this.out.println("        env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.register(NullPointerException.class) + "), \"Pointer address of argument " + j + " is NULL.\");");
/* 924 */                               this.out.println("        return" + (methodInfo.returnType == Void.TYPE ? ";" : " 0;"));
/* 925 */                               this.out.println("    }");
                                    }
/* 927 */                           if (adapterInfo != null || prevAdapterInfo != null) {
/* 928 */                               this.out.println("    jint size" + j + " = arg" + j + " == NULL ? 0 : env->GetIntField(arg" + j + ", JavaCPP_limitFID);");
                                    }
/* 931 */                           if (!methodInfo.parameterTypes[j].isAnnotationPresent(Opaque.class)) {
/* 932 */                               this.out.println("    jint position" + j + " = arg" + j + " == NULL ? 0 : env->GetIntField(arg" + j + ", JavaCPP_positionFID);");
/* 934 */                               this.out.println("    ptr" + j + " += position" + j + ";");
/* 935 */                               if (adapterInfo != null || prevAdapterInfo != null) {
/* 936 */                                   this.out.println("    size" + j + " -= position" + j + ";");
                                        }
                                    }
                                } else if (methodInfo.parameterTypes[j] == String.class) {
/* 940 */                           this.out.println("arg" + j + " == NULL ? NULL : env->GetStringUTFChars(arg" + j + ", NULL);");
/* 941 */                           if (adapterInfo != null || prevAdapterInfo != null) {
/* 942 */                               this.out.println("    jint size" + j + " = 0;");
                                    }
                                } else if (methodInfo.parameterTypes[j].isArray() && methodInfo.parameterTypes[j].getComponentType().isPrimitive()) {
/* 946 */                           this.out.print("arg" + j + " == NULL ? NULL : ");
/* 947 */                           String s = methodInfo.parameterTypes[j].getComponentType().getName();
/* 948 */                           if (methodInfo.valueGetter || methodInfo.valueSetter || methodInfo.memberGetter || methodInfo.memberSetter) {
/* 950 */                               this.out.println("(j" + s + "*)env->GetPrimitiveArrayCritical(arg" + j + ", NULL);");
                                    } else {
/* 953 */                               this.out.println("env->Get" + (Character.toUpperCase(s.charAt(0)) + s.substring(1)) + "ArrayElements(arg" + j + ", NULL);");
                                    }
/* 955 */                           if (adapterInfo != null || prevAdapterInfo != null) {
/* 956 */                               this.out.println("    jint size" + j + " = arg" + j + " == NULL ? 0 : env->GetArrayLength(arg" + j + ");");
                                    }
                                } else if (Buffer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
/* 960 */                           this.out.println("arg" + j + " == NULL ? NULL : (" + typeName[0] + typeName[1] + ")env->GetDirectBufferAddress(arg" + j + ");");
/* 961 */                           if (adapterInfo != null || prevAdapterInfo != null) {
/* 962 */                               this.out.println("    jint size" + j + " = arg" + j + " == NULL ? 0 : env->GetDirectBufferCapacity(arg" + j + ");");
                                    }
                                } else {
/* 966 */                           this.out.println("arg" + j + ";");
/* 967 */                           logger.log(Level.WARNING, "Method \"" + methodInfo.method + "\" has an unsupported parameter of type \"" + methodInfo.parameterTypes[j].getCanonicalName() + "\". Compilation will most likely fail.");
                                }
/* 971 */                       if (adapterInfo != null) {
/* 972 */                           this.usesAdapters = true;
/* 973 */                           adapterLine = "    " + adapterInfo.name + " adapter" + j + "(";
/* 974 */                           prevAdapterInfo = adapterInfo;
                                }
/* 976 */                       if (prevAdapterInfo != null) {
/* 977 */                           if (!FunctionPointer.class.isAssignableFrom(methodInfo.cls)) {
/* 979 */                               adapterLine = adapterLine + cast;
                                    }
/* 981 */                           adapterLine = adapterLine + "ptr" + j + ", size" + j;
/* 982 */                           int i = prevAdapterInfo.argc - 1;
                                    prevAdapterInfo.argc = i;
                                    if (i > 0) {
/* 983 */                               adapterLine = adapterLine + ", ";
                                    }
                                }
/* 986 */                       if (prevAdapterInfo != null && prevAdapterInfo.argc <= 0) {
/* 987 */                           this.out.println(adapterLine + ");");
/* 988 */                           prevAdapterInfo = null;
                                }
                            }
                        }
                    }
                }

/* 994 */       private String doReturnBefore(MethodInformation methodInfo) {
/* 995 */           String returnPrefix = "";
/* 996 */           if (methodInfo.returnType == Void.TYPE) {
/* 997 */               if (methodInfo.allocator || methodInfo.arrayAllocator) {
/* 998 */                   if (methodInfo.cls != Pointer.class) {
/* 999 */                       this.out.println("    if (!env->IsSameObject(env->GetObjectClass(obj), JavaCPP_getClass(env, " + this.jclasses.register(methodInfo.cls) + "))) {");
/* 1001 */                      this.out.println("        return;");
/* 1002 */                      this.out.println("    }");
                            }
/* 1004 */                  String[] typeName = getCPPTypeName(methodInfo.cls);
/* 1005 */                  returnPrefix = typeName[0] + " rptr" + typeName[1] + " = ";
                        }
                    } else {
/* 1008 */              String cast = getCast(methodInfo.annotations, methodInfo.returnType);
/* 1009 */              String[] typeName2 = getCastedCPPTypeName(methodInfo.annotations, methodInfo.returnType);
/* 1010 */              if (methodInfo.valueSetter || methodInfo.memberSetter || methodInfo.noReturnGetter) {
/* 1011 */                  this.out.println("    jobject rarg = obj;");
                        } else if (methodInfo.returnType.isPrimitive()) {
/* 1013 */                  this.out.println("    " + getJNITypeName(methodInfo.returnType) + " rarg = 0;");
/* 1014 */                  returnPrefix = typeName2[0] + " rvalue" + typeName2[1] + " = " + cast;
                        } else {
/* 1016 */                  Annotation returnBy = getBy(methodInfo.annotations);
/* 1017 */                  String valueTypeName = getValueTypeName(typeName2);
/* 1019 */                  returnPrefix = "rptr = " + cast;
/* 1020 */                  if (typeName2[0].length() == 0 || methodInfo.returnRaw) {
/* 1021 */                      methodInfo.returnRaw = true;
/* 1022 */                      typeName2[0] = getJNITypeName(methodInfo.returnType);
/* 1023 */                      this.out.println("    " + typeName2[0] + " rarg = NULL;");
/* 1024 */                      this.out.println("    " + typeName2[0] + " rptr;");
                            } else if (Pointer.class.isAssignableFrom(methodInfo.returnType) || Buffer.class.isAssignableFrom(methodInfo.returnType) || (methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive())) {
/* 1029 */                      if (FunctionPointer.class.isAssignableFrom(methodInfo.returnType)) {
/* 1030 */                          typeName2[0] = getFunctionClassName(methodInfo.returnType) + "*";
/* 1031 */                          typeName2[1] = "";
/* 1032 */                          valueTypeName = getValueTypeName(typeName2);
/* 1033 */                          returnPrefix = "if (rptr != NULL) rptr->ptr = ";
                                }
/* 1035 */                      if (returnBy instanceof ByVal) {
                                    returnPrefix = returnPrefix + (getNoException(methodInfo.returnType, methodInfo.method) ? "new (std::nothrow) " : "new ") + valueTypeName + typeName2[1] + "(";
                                } else if (returnBy instanceof ByRef) {
/* 1039 */                          returnPrefix = returnPrefix + "&";
                                } else if (returnBy instanceof ByPtrPtr) {
/* 1041 */                          if (cast.length() > 0) {
/* 1042 */                              typeName2[0] = typeName2[0].substring(0, typeName2[0].length() - 1);
                                    }
/* 1044 */                          returnPrefix = "rptr = NULL; " + typeName2[0] + "* rptrptr" + typeName2[1] + " = " + cast;
                                }
/* 1046 */                      if (methodInfo.bufferGetter) {
/* 1047 */                          this.out.println("    jobject rarg = NULL;");
/* 1048 */                          this.out.println("    char* rptr;");
                                } else {
/* 1050 */                          this.out.println("    " + getJNITypeName(methodInfo.returnType) + " rarg = NULL;");
/* 1051 */                          this.out.println("    " + typeName2[0] + " rptr" + typeName2[1] + ";");
                                }
/* 1053 */                      if (FunctionPointer.class.isAssignableFrom(methodInfo.returnType)) {
/* 1054 */                          this.out.println("    rptr = new (std::nothrow) " + valueTypeName + ";");
                                }
                            } else if (methodInfo.returnType == String.class) {
/* 1057 */                      this.out.println("    jstring rarg = NULL;");
/* 1058 */                      this.out.println("    const char* rptr;");
/* 1062 */                      returnPrefix = returnBy instanceof ByRef ? "std::string rstr(" : returnPrefix + "(const char*)";
                            } else {
/* 1065 */                      logger.log(Level.WARNING, "Method \"" + methodInfo.method + "\" has unsupported return type \"" + methodInfo.returnType.getCanonicalName() + "\". Compilation will most likely fail.");
                            }
/* 1069 */                  AdapterInformation adapterInfo = getAdapterInformation(false, valueTypeName, methodInfo.annotations);
/* 1070 */                  if (adapterInfo != null) {
/* 1071 */                      this.usesAdapters = true;
/* 1072 */                      returnPrefix = adapterInfo.name + " radapter(";
                            }
                        }
                    }
/* 1076 */          if (methodInfo.throwsException != null) {
/* 1077 */              this.out.println("    jthrowable exc = NULL;");
/* 1078 */              this.out.println("    try {");
                    }
/* 1080 */          return returnPrefix;
                }

/* 1083 */      private void doCall(MethodInformation methodInfo, String returnPrefix) {
/* 1084 */          String indent = methodInfo.throwsException != null ? "        " : "    ";
/* 1085 */          String prefix = "(";
/* 1086 */          String suffix = ")";
/* 1087 */          int skipParameters = 0;
/* 1088 */          boolean index = methodInfo.method.isAnnotationPresent(Index.class) || (methodInfo.pairedMethod != null && methodInfo.pairedMethod.isAnnotationPresent(Index.class));
/* 1091 */          if (methodInfo.deallocator) {
/* 1092 */              this.out.println(indent + "void* allocatedAddress = jlong_to_ptr(arg0);");
/* 1093 */              this.out.println(indent + "void (*deallocatorAddress)(void*) = (void(*)(void*))jlong_to_ptr(arg1);");
/* 1094 */              this.out.println(indent + "if (deallocatorAddress != NULL && allocatedAddress != NULL) {");
/* 1095 */              this.out.println(indent + "    (*deallocatorAddress)(allocatedAddress);");
/* 1096 */              this.out.println(indent + "}");
/* 1239 */              return;
                    }
/* 1098 */          if (methodInfo.valueGetter || methodInfo.valueSetter || methodInfo.memberGetter || methodInfo.memberSetter) {
/* 1100 */              boolean wantsPointer = false;
/* 1101 */              int k = methodInfo.parameterTypes.length - 1;
/* 1102 */              if ((methodInfo.valueSetter || methodInfo.memberSetter) && !(getParameterBy(methodInfo, k) instanceof ByRef) && getParameterAdapterInformation(false, methodInfo, k) == null && methodInfo.parameterTypes[k] == String.class) {
/* 1107 */                  this.out.print(indent + "strcpy((char*)");
/* 1108 */                  wantsPointer = true;
/* 1109 */                  prefix = ", ";
                        } else if (k >= 1 && methodInfo.parameterTypes[0].isArray() && methodInfo.parameterTypes[0].getComponentType().isPrimitive() && (methodInfo.parameterTypes[1] == Integer.TYPE || methodInfo.parameterTypes[1] == Long.TYPE)) {
/* 1115 */                  this.out.print(indent + "memcpy(");
/* 1116 */                  wantsPointer = true;
/* 1117 */                  prefix = ", ";
/* 1118 */                  if (!methodInfo.memberGetter && !methodInfo.valueGetter) {
/* 1117 */                      prefix = ", ptr0 + arg1, ";
                            } else {
/* 1119 */                      this.out.print("ptr0 + arg1, ");
                            }
/* 1123 */                  skipParameters = 2;
/* 1086 */                  suffix = " * sizeof(*ptr0))";
                        } else {
/* 1126 */                  this.out.print(indent + returnPrefix);
/* 1127 */                  prefix = (methodInfo.valueGetter || methodInfo.memberGetter) ? "" : " = ";
/* 1128 */                  suffix = "";
                        }
/* 1130 */              if (Modifier.isStatic(methodInfo.modifiers)) {
/* 1131 */                  this.out.print(getCPPScopeName(methodInfo));
                        } else if (methodInfo.memberGetter || methodInfo.memberSetter) {
/* 1133 */                  if (index) {
/* 1134 */                      this.out.print("(*ptr)");
/* 1135 */                      prefix = "." + methodInfo.memberName[0] + prefix;
                            } else {
/* 1137 */                      this.out.print("ptr->" + methodInfo.memberName[0]);
                            }
                        } else {
/* 1140 */                  this.out.print(index ? "(*ptr)" : (methodInfo.dim > 0 || wantsPointer) ? "ptr" : "*ptr");
                        }
                    } else if (methodInfo.bufferGetter) {
/* 1143 */              this.out.print(indent + returnPrefix + "ptr");
/* 1144 */              prefix = "";
/* 1145 */              suffix = "";
                    } else {
/* 1147 */              this.out.print(indent + returnPrefix);
/* 1148 */              if (FunctionPointer.class.isAssignableFrom(methodInfo.cls)) {
/* 1149 */                  if (methodInfo.cls.isAnnotationPresent(Namespace.class)) {
/* 1150 */                      this.out.print("(ptr0->*(ptr->ptr))");
/* 1151 */                      skipParameters = 1;
                            } else {
/* 1153 */                      this.out.print("(*ptr->ptr)");
                            }
                        } else if (methodInfo.allocator) {
/* 1156 */                  String[] typeName = getCPPTypeName(methodInfo.cls);
/* 1157 */                  String valueTypeName = getValueTypeName(typeName);
/* 1158 */                  if (methodInfo.cls == Pointer.class) {
/* 1160 */                      prefix = "";
/* 1161 */                      suffix = "";
                            } else {
/* 1163 */                      this.out.print((getNoException(methodInfo.cls, methodInfo.method) ? "new (std::nothrow) " : "new ") + valueTypeName + typeName[1]);
/* 1165 */                      if (methodInfo.arrayAllocator) {
/* 1166 */                          prefix = "[";
/* 1167 */                          suffix = "]";
                                }
                            }
                        } else if (Modifier.isStatic(methodInfo.modifiers)) {
/* 1171 */                  this.out.print(getCPPScopeName(methodInfo));
                        } else if (index) {
/* 1174 */                  this.out.print("(*ptr)");
/* 1085 */                  prefix = "." + methodInfo.memberName[0] + "(";
                        } else {
/* 1177 */                  this.out.print("ptr->" + methodInfo.memberName[0]);
                        }
                    }
                    for (int j = skipParameters; j < methodInfo.dim; j++) {
/* 1186 */              this.out.print("[" + getParameterCast(methodInfo, j) + (methodInfo.parameterTypes[j].isPrimitive() ? "arg" : "ptr") + j + "]");
                    }
/* 1188 */          if (methodInfo.memberName.length > 1) {
/* 1189 */              this.out.print(methodInfo.memberName[1]);
                    }
/* 1191 */          this.out.print(prefix);
/* 1192 */          if (methodInfo.withEnv) {
/* 1193 */              this.out.print(Modifier.isStatic(methodInfo.modifiers) ? "env, cls" : "env, obj");
/* 1194 */              if ((methodInfo.parameterTypes.length - skipParameters) - methodInfo.dim > 0) {
/* 1195 */                  this.out.print(", ");
                        }
                    }
/* 1198 */          int j2 = skipParameters + methodInfo.dim;
                    while (j2 < methodInfo.parameterTypes.length) {
/* 1199 */              Annotation passBy = getParameterBy(methodInfo, j2);
/* 1200 */              String cast = getParameterCast(methodInfo, j2);
/* 1201 */              AdapterInformation adapterInfo = getParameterAdapterInformation(false, methodInfo, j2);
/* 1203 */              if (("(void*)".equals(cast) || "(void *)".equals(cast)) && methodInfo.parameterTypes[j2] == Long.TYPE) {
/* 1205 */                  this.out.print("jlong_to_ptr(arg" + j2 + ")");
                        } else if (methodInfo.parameterTypes[j2].isPrimitive()) {
/* 1207 */                  this.out.print(cast + "arg" + j2);
                        } else if (adapterInfo != null) {
/* 1209 */                  String cast2 = adapterInfo.cast.trim();
/* 1210 */                  if (cast2.length() > 0 && !cast2.startsWith("(") && !cast2.endsWith(")")) {
/* 1211 */                      cast2 = "(" + cast2 + ")";
                            }
/* 1213 */                  this.out.print(cast2 + "adapter" + j2);
/* 1214 */                  j2 += adapterInfo.argc - 1;
                        } else if (FunctionPointer.class.isAssignableFrom(methodInfo.parameterTypes[j2]) && passBy == null) {
/* 1216 */                  this.out.print(cast + "(ptr" + j2 + " == NULL ? NULL : ptr" + j2 + "->ptr)");
                        } else if ((passBy instanceof ByVal) || ((passBy instanceof ByRef) && methodInfo.parameterTypes[j2] != String.class)) {
/* 1219 */                  this.out.print("*" + cast + "ptr" + j2);
                        } else if (passBy instanceof ByPtrPtr) {
/* 1221 */                  this.out.print(cast + "(arg" + j2 + " == NULL ? NULL : &ptr" + j2 + ")");
                        } else {
/* 1223 */                  this.out.print(cast + "ptr" + j2);
                        }
/* 1226 */              if (j2 < methodInfo.parameterTypes.length - 1) {
/* 1227 */                  this.out.print(", ");
                        }
/* 1198 */              j2++;
                    }
/* 1230 */          this.out.print(suffix);
/* 1231 */          if (methodInfo.memberName.length > 2) {
/* 1232 */              this.out.print(methodInfo.memberName[2]);
                    }
/* 1234 */          if ((getBy(methodInfo.annotations) instanceof ByRef) && methodInfo.returnType == String.class) {
/* 1237 */              this.out.print(");\n" + indent + "rptr = rstr.c_str()");
                    }
                }

/* 1241 */      private void doReturnAfter(MethodInformation methodInfo) {
/* 1242 */          String indent = methodInfo.throwsException != null ? "        " : "    ";
/* 1243 */          String[] typeName = getCastedCPPTypeName(methodInfo.annotations, methodInfo.returnType);
/* 1244 */          Annotation returnBy = getBy(methodInfo.annotations);
/* 1245 */          String valueTypeName = getValueTypeName(typeName);
/* 1246 */          AdapterInformation adapterInfo = getAdapterInformation(false, valueTypeName, methodInfo.annotations);
/* 1247 */          String suffix = methodInfo.deallocator ? "" : ";";
/* 1248 */          if (!methodInfo.returnType.isPrimitive() && adapterInfo != null) {
/* 1249 */              suffix = ")" + suffix;
                    }
/* 1251 */          if (Pointer.class.isAssignableFrom(methodInfo.returnType) || (methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive())) {
/* 1254 */              if (returnBy instanceof ByVal) {
/* 1255 */                  suffix = ")" + suffix;
                        } else if (returnBy instanceof ByPtrPtr) {
/* 1257 */                  this.out.println(suffix);
/* 1258 */                  suffix = "";
/* 1259 */                  this.out.println(indent + "if (rptrptr == NULL) {");
/* 1260 */                  this.out.println(indent + "    env->ThrowNew(JavaCPP_getClass(env, " + this.jclasses.register(NullPointerException.class) + "), \"Return pointer address is NULL.\");");
/* 1262 */                  this.out.println(indent + "} else {");
/* 1263 */                  this.out.println(indent + "    rptr = *rptrptr;");
/* 1264 */                  this.out.println(indent + "}");
                        }
                    }
/* 1267 */          this.out.println(suffix);
/* 1269 */          if (methodInfo.returnType == Void.TYPE) {
/* 1270 */              if (methodInfo.allocator || methodInfo.arrayAllocator) {
/* 1271 */                  this.out.println(indent + "jint rcapacity = " + (methodInfo.arrayAllocator ? "arg0;" : "1;"));
/* 1272 */                  boolean noDeallocator = methodInfo.cls == Pointer.class || methodInfo.cls.isAnnotationPresent(NoDeallocator.class);
/* 1274 */                  Annotation[] arr$ = methodInfo.annotations;
                            for (Annotation a : arr$) {
/* 1275 */                      if (a instanceof NoDeallocator) {
/* 1276 */                          noDeallocator = true;
                                    break;
                                }
                            }
/* 1280 */                  if (!noDeallocator) {
/* 1281 */                      this.out.println(indent + "jvalue args[3];");
/* 1282 */                      this.out.println(indent + "args[0].j = ptr_to_jlong(rptr);");
/* 1283 */                      this.out.println(indent + "args[1].i = rcapacity;");
/* 1284 */                      this.out.print(indent + "args[2].j = ptr_to_jlong(&JavaCPP_" + mangle(methodInfo.cls.getName()));
/* 1285 */                      if (methodInfo.arrayAllocator) {
/* 1286 */                          this.out.println("_deallocateArray);");
/* 1287 */                          this.arrayDeallocators.register(methodInfo.cls);
                                } else {
/* 1289 */                          this.out.println("_deallocate);");
/* 1290 */                          this.deallocators.register(methodInfo.cls);
                                }
/* 1292 */                      this.out.println(indent + "env->CallNonvirtualVoidMethodA(obj, JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), JavaCPP_initMID, args);");
/* 1397 */                      return;
                            }
/* 1295 */                  this.out.println(indent + "env->SetLongField(obj, JavaCPP_addressFID, ptr_to_jlong(rptr));");
/* 1296 */                  this.out.println(indent + "env->SetIntField(obj, JavaCPP_limitFID, rcapacity);");
/* 1297 */                  this.out.println(indent + "env->SetIntField(obj, JavaCPP_capacityFID, rcapacity);");
/* 1397 */                  return;
                        }
/* 1397 */              return;
                    }
/* 1301 */          if (!methodInfo.valueSetter && !methodInfo.memberSetter && !methodInfo.noReturnGetter) {
/* 1303 */              if (methodInfo.returnType.isPrimitive()) {
/* 1304 */                  this.out.println(indent + "rarg = (" + getJNITypeName(methodInfo.returnType) + ")rvalue;");
/* 1397 */                  return;
                        }
/* 1305 */              if (methodInfo.returnRaw) {
/* 1306 */                  this.out.println(indent + "rarg = rptr;");
/* 1397 */                  return;
                        }
/* 1308 */              boolean needInit = false;
/* 1309 */              if (adapterInfo != null) {
/* 1310 */                  this.out.println(indent + "rptr = radapter;");
/* 1311 */                  if (methodInfo.returnType != String.class) {
/* 1312 */                      this.out.println(indent + "jint rcapacity = (jint)radapter.size;");
/* 1313 */                      this.out.println(indent + "jlong deallocator = " + (adapterInfo.constant ? "0;" : "ptr_to_jlong(&(" + adapterInfo.name + "::deallocate));"));
                            }
/* 1316 */                  needInit = true;
                        } else if ((returnBy instanceof ByVal) || FunctionPointer.class.isAssignableFrom(methodInfo.returnType)) {
/* 1319 */                  this.out.println(indent + "jint rcapacity = 1;");
/* 1320 */                  this.out.println(indent + "jlong deallocator = ptr_to_jlong(&JavaCPP_" + mangle(methodInfo.returnType.getName()) + "_deallocate);");
/* 1322 */                  this.deallocators.register(methodInfo.returnType);
/* 1323 */                  needInit = true;
                        }
/* 1326 */              if (Pointer.class.isAssignableFrom(methodInfo.returnType)) {
/* 1327 */                  this.out.print(indent);
/* 1328 */                  if (!(returnBy instanceof ByVal)) {
/* 1330 */                      if (Modifier.isStatic(methodInfo.modifiers) && methodInfo.parameterTypes.length > 0) {
                                    for (int i = 0; i < methodInfo.parameterTypes.length; i++) {
/* 1332 */                              String cast = getParameterCast(methodInfo, i);
/* 1333 */                              if (methodInfo.parameterTypes[i] == methodInfo.returnType) {
/* 1334 */                                  this.out.println("if (rptr == " + cast + "ptr" + i + ") {");
/* 1335 */                                  this.out.println(indent + "    rarg = arg" + i + ";");
/* 1336 */                                  this.out.print(indent + "} else ");
                                        }
                                    }
                                } else if (!Modifier.isStatic(methodInfo.modifiers) && methodInfo.cls == methodInfo.returnType) {
/* 1340 */                          this.out.println("if (rptr == ptr) {");
/* 1341 */                          this.out.println(indent + "    rarg = obj;");
/* 1342 */                          this.out.print(indent + "} else ");
                                }
                            }
/* 1345 */                  this.out.println("if (rptr != NULL) {");
/* 1346 */                  this.out.println(indent + "    rarg = env->AllocObject(JavaCPP_getClass(env, " + this.jclasses.register(methodInfo.returnType) + "));");
/* 1348 */                  if (needInit) {
/* 1349 */                      this.out.println(indent + "    if (deallocator != 0) {");
/* 1350 */                      this.out.println(indent + "        jvalue args[3];");
/* 1351 */                      this.out.println(indent + "        args[0].j = ptr_to_jlong(rptr);");
/* 1352 */                      this.out.println(indent + "        args[1].i = rcapacity;");
/* 1353 */                      this.out.println(indent + "        args[2].j = deallocator;");
/* 1354 */                      this.out.println(indent + "        env->CallNonvirtualVoidMethodA(rarg, JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), JavaCPP_initMID, args);");
/* 1356 */                      this.out.println(indent + "    } else {");
/* 1357 */                      this.out.println(indent + "        env->SetLongField(rarg, JavaCPP_addressFID, ptr_to_jlong(rptr));");
/* 1358 */                      this.out.println(indent + "        env->SetIntField(rarg, JavaCPP_limitFID, rcapacity);");
/* 1359 */                      this.out.println(indent + "        env->SetIntField(rarg, JavaCPP_capacityFID, rcapacity);");
/* 1360 */                      this.out.println(indent + "    }");
                            } else {
/* 1362 */                      this.out.println(indent + "    env->SetLongField(rarg, JavaCPP_addressFID, ptr_to_jlong(rptr));");
                            }
/* 1364 */                  this.out.println(indent + "}");
/* 1397 */                  return;
                        }
/* 1365 */              if (methodInfo.returnType == String.class) {
/* 1366 */                  this.out.println(indent + "if (rptr != NULL) {");
/* 1367 */                  this.out.println(indent + "    rarg = env->NewStringUTF(rptr);");
/* 1368 */                  this.out.println(indent + "}");
/* 1397 */                  return;
                        }
/* 1369 */              if (methodInfo.returnType.isArray() && methodInfo.returnType.getComponentType().isPrimitive()) {
/* 1371 */                  if (adapterInfo == null) {
/* 1372 */                      this.out.println(indent + "jint rcapacity = rptr != NULL ? 1 : 0;");
                            }
/* 1374 */                  String s = methodInfo.returnType.getComponentType().getName();
/* 1375 */                  String S = Character.toUpperCase(s.charAt(0)) + s.substring(1);
/* 1376 */                  this.out.println(indent + "if (rptr != NULL) {");
/* 1377 */                  this.out.println(indent + "    rarg = env->New" + S + "Array(rcapacity);");
/* 1378 */                  this.out.println(indent + "    env->Set" + S + "ArrayRegion(rarg, 0, rcapacity, (j" + s + "*)rptr);");
/* 1379 */                  this.out.println(indent + "}");
/* 1380 */                  if (adapterInfo != null) {
/* 1381 */                      this.out.println(indent + "if (deallocator != 0 && rptr != NULL) {");
/* 1382 */                      this.out.println(indent + "    (*(void(*)(void*))jlong_to_ptr(deallocator))((void*)rptr);");
/* 1383 */                      this.out.println(indent + "}");
/* 1397 */                      return;
                            }
/* 1397 */                  return;
                        }
/* 1385 */              if (Buffer.class.isAssignableFrom(methodInfo.returnType)) {
/* 1386 */                  if (methodInfo.bufferGetter) {
/* 1387 */                      this.out.println(indent + "jint rcapacity = size;");
                            } else if (adapterInfo == null) {
/* 1389 */                      this.out.println(indent + "jint rcapacity = rptr != NULL ? 1 : 0;");
                            }
/* 1391 */                  this.out.println(indent + "if (rptr != NULL) {");
/* 1392 */                  this.out.println(indent + "    rarg = env->NewDirectByteBuffer(rptr, rcapacity);");
/* 1393 */                  this.out.println(indent + "}");
                        }
                    }
                }

/* 1399 */      private void doParametersAfter(MethodInformation methodInfo) {
/* 1400 */          if (methodInfo.throwsException != null) {
/* 1401 */              this.mayThrowExceptions = true;
/* 1402 */              this.out.println("    } catch (...) {");
/* 1403 */              this.out.println("        exc = JavaCPP_handleException(env, " + this.jclasses.register(methodInfo.throwsException) + ");");
/* 1404 */              this.out.println("    }");
/* 1405 */              this.out.println();
                    }
                    for (int j = 0; j < methodInfo.parameterTypes.length; j++) {
/* 1408 */              if (!methodInfo.parameterRaw[j]) {
/* 1411 */                  Annotation passBy = getParameterBy(methodInfo, j);
/* 1412 */                  String cast = getParameterCast(methodInfo, j);
/* 1413 */                  String[] typeName = getCastedCPPTypeName(methodInfo.parameterAnnotations[j], methodInfo.parameterTypes[j]);
/* 1414 */                  AdapterInformation adapterInfo = getParameterAdapterInformation(true, methodInfo, j);
/* 1415 */                  if ("void*".equals(typeName[0])) {
/* 1416 */                      typeName[0] = "char*";
                            }
/* 1418 */                  if (Pointer.class.isAssignableFrom(methodInfo.parameterTypes[j])) {
/* 1419 */                      if (adapterInfo != null) {
/* 1420 */                          int k = 0;
                                    while (k < adapterInfo.argc) {
/* 1421 */                              this.out.println("    " + typeName[0] + " rptr" + (j + k) + typeName[1] + " = " + cast + "adapter" + j + ";");
/* 1422 */                              this.out.println("    jint rsize" + (j + k) + " = (jint)adapter" + j + ".size" + (k > 0 ? (k + 1) + ";" : ";"));
/* 1423 */                              this.out.println("    if (rptr" + (j + k) + " != " + cast + "ptr" + (j + k) + ") {");
/* 1424 */                              this.out.println("        jvalue args[3];");
/* 1425 */                              this.out.println("        args[0].j = ptr_to_jlong(rptr" + (j + k) + ");");
/* 1426 */                              this.out.println("        args[1].i = rsize" + (j + k) + ";");
/* 1427 */                              this.out.println("        args[2].j = ptr_to_jlong(&(" + adapterInfo.name + "::deallocate));");
/* 1428 */                              this.out.println("        env->CallNonvirtualVoidMethodA(arg" + j + ", JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), JavaCPP_initMID, args);");
/* 1430 */                              this.out.println("    } else {");
/* 1431 */                              this.out.println("        env->SetIntField(arg" + j + ", JavaCPP_limitFID, rsize" + (j + k) + " + position" + (j + k) + ");");
/* 1432 */                              this.out.println("    }");
/* 1420 */                              k++;
                                    }
                                } else if (((passBy instanceof ByPtrPtr) || (passBy instanceof ByPtrRef)) && !methodInfo.valueSetter && !methodInfo.memberSetter) {
/* 1436 */                          if (!methodInfo.parameterTypes[j].isAnnotationPresent(Opaque.class)) {
/* 1437 */                              this.out.println("    ptr" + j + " -= position" + j + ";");
                                    }
/* 1439 */                          this.out.println("    if (arg" + j + " != NULL) env->SetLongField(arg" + j + ", JavaCPP_addressFID, ptr_to_jlong(ptr" + j + "));");
                                }
                            } else if (methodInfo.parameterTypes[j] == String.class) {
/* 1443 */                      this.out.println("    if (arg" + j + " != NULL) env->ReleaseStringUTFChars(arg" + j + ", ptr" + j + ");");
                            } else if (methodInfo.parameterTypes[j].isArray() && methodInfo.parameterTypes[j].getComponentType().isPrimitive()) {
/* 1446 */                      this.out.print("    if (arg" + j + " != NULL) ");
/* 1447 */                      if (methodInfo.valueGetter || methodInfo.valueSetter || methodInfo.memberGetter || methodInfo.memberSetter) {
/* 1449 */                          this.out.println("env->ReleasePrimitiveArrayCritical(arg" + j + ", ptr" + j + ", 0);");
                                } else {
/* 1451 */                          String s = methodInfo.parameterTypes[j].getComponentType().getName();
/* 1452 */                          String S = Character.toUpperCase(s.charAt(0)) + s.substring(1);
/* 1453 */                          this.out.println("env->Release" + S + "ArrayElements(arg" + j + ", (j" + s + "*)ptr" + j + ", 0);");
                                }
                            }
                        }
                    }
                }

/* 1459 */      private void doCallback(Class<?> cls, Method callbackMethod, String callbackName, boolean needFunctor) {
/* 1460 */          Class<?> callbackReturnType = callbackMethod.getReturnType();
/* 1461 */          Class<?>[] callbackParameterTypes = callbackMethod.getParameterTypes();
/* 1462 */          Annotation[] callbackAnnotations = callbackMethod.getAnnotations();
/* 1463 */          Annotation[][] callbackParameterAnnotations = callbackMethod.getParameterAnnotations();
/* 1465 */          String instanceTypeName = getFunctionClassName(cls);
/* 1466 */          String[] callbackTypeName = getCPPTypeName(cls);
/* 1467 */          String[] returnConvention = callbackTypeName[0].split("\\(");
/* 1468 */          returnConvention[1] = getValueTypeName(returnConvention[1]);
/* 1469 */          String parameterDeclaration = callbackTypeName[1].substring(1);
/* 1470 */          this.functionPointers.register("static " + instanceTypeName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + callbackName + "_instance;");
/* 1471 */          this.jclassesInit.register(cls);
/* 1472 */          if (this.out2 != null) {
/* 1473 */              this.out2.println("JNIIMPORT " + returnConvention[0] + (returnConvention.length > 1 ? returnConvention[1] : "") + callbackName + parameterDeclaration + ";");
                    }
/* 1476 */          this.out.println("JNIEXPORT " + returnConvention[0] + (returnConvention.length > 1 ? returnConvention[1] : "") + callbackName + parameterDeclaration + " {");
/* 1478 */          this.out.print((callbackReturnType != Void.TYPE ? "    return " : "    ") + callbackName + "_instance(");
                    for (int j = 0; j < callbackParameterTypes.length; j++) {
/* 1480 */              this.out.print("arg" + j);
/* 1481 */              if (j < callbackParameterTypes.length - 1) {
/* 1482 */                  this.out.print(", ");
                        }
                    }
/* 1485 */          this.out.println(");");
/* 1486 */          this.out.println("}");
/* 1487 */          if (needFunctor) {
/* 1491 */              this.out.println(returnConvention[0] + instanceTypeName + "::operator()" + parameterDeclaration + " {");
/* 1492 */              String returnPrefix = "";
/* 1493 */              if (callbackReturnType != Void.TYPE) {
/* 1494 */                  this.out.println("    " + getJNITypeName(callbackReturnType) + " rarg = 0;");
/* 1495 */                  returnPrefix = "rarg = ";
/* 1496 */                  if (callbackReturnType == String.class) {
/* 1495 */                      returnPrefix = "rarg = (jstring)";
                            }
                        }
/* 1500 */              String callbackReturnCast = getCast(callbackAnnotations, callbackReturnType);
/* 1501 */              Annotation returnBy = getBy(callbackAnnotations);
/* 1502 */              String[] returnTypeName = getCPPTypeName(callbackReturnType);
/* 1503 */              String returnValueTypeName = getValueTypeName(returnTypeName);
/* 1504 */              AdapterInformation returnAdapterInfo = getAdapterInformation(false, returnValueTypeName, callbackAnnotations);
/* 1506 */              this.out.println("    jthrowable exc = NULL;");
/* 1507 */              this.out.println("    JNIEnv* env;");
/* 1508 */              this.out.println("    int attached = JavaCPP_getEnv(&env);");
/* 1509 */              this.out.println("    if (attached < 0) {");
/* 1510 */              this.out.println("        goto end;");
/* 1511 */              this.out.println("    }");
/* 1512 */              this.out.println("{");
/* 1513 */              if (callbackParameterTypes.length > 0) {
/* 1514 */                  this.out.println("    jvalue args[" + callbackParameterTypes.length + "];");
                            for (int j2 = 0; j2 < callbackParameterTypes.length; j2++) {
/* 1516 */                      if (callbackParameterTypes[j2].isPrimitive()) {
/* 1517 */                          this.out.println("    args[" + j2 + "]." + getSignature(callbackParameterTypes[j2]).toLowerCase() + " = (" + getJNITypeName(callbackParameterTypes[j2]) + ")arg" + j2 + ";");
                                } else {
/* 1521 */                          Annotation passBy = getBy(callbackParameterAnnotations[j2]);
/* 1522 */                          String[] typeName = getCPPTypeName(callbackParameterTypes[j2]);
/* 1523 */                          String valueTypeName = getValueTypeName(typeName);
/* 1524 */                          AdapterInformation adapterInfo = getAdapterInformation(false, valueTypeName, callbackParameterAnnotations[j2]);
/* 1526 */                          boolean needInit = false;
/* 1527 */                          if (adapterInfo != null) {
/* 1528 */                              this.usesAdapters = true;
/* 1529 */                              this.out.println("    " + adapterInfo.name + " adapter" + j2 + "(arg" + j2 + ");");
/* 1530 */                              if (callbackParameterTypes[j2] != String.class) {
/* 1531 */                                  this.out.println("    jint size" + j2 + " = (jint)adapter" + j2 + ".size;");
/* 1532 */                                  this.out.println("    jlong deallocator" + j2 + " = ptr_to_jlong(&(" + adapterInfo.name + "::deallocate));");
                                        }
/* 1534 */                              needInit = true;
                                    } else if (((passBy instanceof ByVal) && callbackParameterTypes[j2] != Pointer.class) || FunctionPointer.class.isAssignableFrom(callbackParameterTypes[j2])) {
/* 1537 */                              this.out.println("    jint size" + j2 + " = 1;");
/* 1538 */                              this.out.println("    jlong deallocator" + j2 + " = ptr_to_jlong(&JavaCPP_" + mangle(callbackParameterTypes[j2].getName()) + "_deallocate);");
/* 1540 */                              this.deallocators.register(callbackParameterTypes[j2]);
/* 1541 */                              needInit = true;
                                    }
/* 1544 */                          if (Pointer.class.isAssignableFrom(callbackParameterTypes[j2]) || Buffer.class.isAssignableFrom(callbackParameterTypes[j2]) || (callbackParameterTypes[j2].isArray() && callbackParameterTypes[j2].getComponentType().isPrimitive())) {
/* 1548 */                              if (FunctionPointer.class.isAssignableFrom(callbackParameterTypes[j2])) {
/* 1549 */                                  typeName[0] = getFunctionClassName(callbackParameterTypes[j2]) + "*";
/* 1550 */                                  typeName[1] = "";
/* 1551 */                                  valueTypeName = getValueTypeName(typeName);
                                        }
/* 1553 */                              this.out.println("    " + getJNITypeName(callbackParameterTypes[j2]) + " obj" + j2 + " = NULL;");
/* 1554 */                              this.out.println("    " + typeName[0] + " ptr" + j2 + typeName[1] + " = NULL;");
/* 1555 */                              if (FunctionPointer.class.isAssignableFrom(callbackParameterTypes[j2])) {
/* 1556 */                                  this.out.println("    ptr" + j2 + " = new (std::nothrow) " + valueTypeName + ";");
/* 1557 */                                  this.out.println("    if (ptr" + j2 + " != NULL) {");
/* 1558 */                                  this.out.println("        ptr" + j2 + "->ptr = arg" + j2 + ";");
/* 1559 */                                  this.out.println("    }");
                                        } else if (adapterInfo != null) {
/* 1561 */                                  this.out.println("    ptr" + j2 + " = adapter" + j2 + ";");
                                        } else if ((passBy instanceof ByVal) && callbackParameterTypes[j2] != Pointer.class) {
/* 1563 */                                  this.out.println("    ptr" + j2 + (getNoException(callbackParameterTypes[j2], callbackMethod) ? " = new (std::nothrow) " : " = new ") + valueTypeName + typeName[1] + "(*(" + typeName[0] + typeName[1] + ")&arg" + j2 + ");");
                                        } else if ((passBy instanceof ByVal) || (passBy instanceof ByRef)) {
/* 1567 */                                  this.out.println("    ptr" + j2 + " = (" + typeName[0] + typeName[1] + ")&arg" + j2 + ";");
                                        } else if (passBy instanceof ByPtrPtr) {
/* 1569 */                                  this.out.println("    if (arg" + j2 + " == NULL) {");
/* 1570 */                                  this.out.println("        JavaCPP_log(\"Pointer address of argument " + j2 + " is NULL in callback for " + cls.getCanonicalName() + ".\");");
/* 1571 */                                  this.out.println("    } else {");
/* 1572 */                                  this.out.println("        ptr" + j2 + " = (" + typeName[0] + typeName[1] + ")*arg" + j2 + ";");
/* 1573 */                                  this.out.println("    }");
                                        } else {
/* 1575 */                                  this.out.println("    ptr" + j2 + " = (" + typeName[0] + typeName[1] + ")arg" + j2 + ";");
                                        }
                                    }
/* 1579 */                          if (Pointer.class.isAssignableFrom(callbackParameterTypes[j2])) {
/* 1580 */                              String s = "    obj" + j2 + " = env->AllocObject(JavaCPP_getClass(env, " + this.jclasses.register(callbackParameterTypes[j2]) + "));";
/* 1582 */                              this.jclassesInit.register(callbackParameterTypes[j2]);
/* 1584 */                              if (getAdapterInformation(true, valueTypeName, callbackParameterAnnotations[j2]) != null || (passBy instanceof ByPtrPtr) || (passBy instanceof ByPtrRef)) {
/* 1585 */                                  this.out.println(s);
                                        } else {
/* 1587 */                                  this.out.println("    if (ptr" + j2 + " != NULL) { ");
/* 1588 */                                  this.out.println("    " + s);
/* 1589 */                                  this.out.println("    }");
                                        }
/* 1591 */                              this.out.println("    if (obj" + j2 + " != NULL) { ");
/* 1592 */                              if (needInit) {
/* 1593 */                                  this.out.println("        if (deallocator" + j2 + " != 0) {");
/* 1594 */                                  this.out.println("            jvalue args[3];");
/* 1595 */                                  this.out.println("            args[0].j = ptr_to_jlong(ptr" + j2 + ");");
/* 1596 */                                  this.out.println("            args[1].i = size" + j2 + ";");
/* 1597 */                                  this.out.println("            args[2].j = deallocator" + j2 + ";");
/* 1598 */                                  this.out.println("            env->CallNonvirtualVoidMethodA(obj" + j2 + ", JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), JavaCPP_initMID, args);");
/* 1600 */                                  this.out.println("        } else {");
/* 1601 */                                  this.out.println("            env->SetLongField(obj" + j2 + ", JavaCPP_addressFID, ptr_to_jlong(ptr" + j2 + "));");
/* 1602 */                                  this.out.println("            env->SetIntField(obj" + j2 + ", JavaCPP_limitFID, size" + j2 + ");");
/* 1603 */                                  this.out.println("            env->SetIntField(obj" + j2 + ", JavaCPP_capacityFID, size" + j2 + ");");
/* 1604 */                                  this.out.println("        }");
                                        } else {
/* 1606 */                                  this.out.println("        env->SetLongField(obj" + j2 + ", JavaCPP_addressFID, ptr_to_jlong(ptr" + j2 + "));");
                                        }
/* 1608 */                              this.out.println("    }");
/* 1609 */                              this.out.println("    args[" + j2 + "].l = obj" + j2 + ";");
                                    } else if (callbackParameterTypes[j2] == String.class) {
/* 1611 */                              this.out.println("    jstring obj" + j2 + " = (const char*)" + (adapterInfo != null ? "adapter" : "arg") + j2 + " == NULL ? NULL : env->NewStringUTF((const char*)" + (adapterInfo != null ? "adapter" : "arg") + j2 + ");");
/* 1613 */                              this.out.println("    args[" + j2 + "].l = obj" + j2 + ";");
                                    } else if (callbackParameterTypes[j2].isArray() && callbackParameterTypes[j2].getComponentType().isPrimitive()) {
/* 1616 */                              if (adapterInfo == null) {
/* 1617 */                                  this.out.println("    jint size" + j2 + " = ptr" + j2 + " != NULL ? 1 : 0;");
                                        }
/* 1619 */                              String s2 = callbackParameterTypes[j2].getComponentType().getName();
/* 1620 */                              String S = Character.toUpperCase(s2.charAt(0)) + s2.substring(1);
/* 1621 */                              this.out.println("    if (ptr" + j2 + " != NULL) {");
/* 1622 */                              this.out.println("        obj" + j2 + " = env->New" + S + "Array(size" + j2 + ");");
/* 1623 */                              this.out.println("        env->Set" + S + "ArrayRegion(obj" + j2 + ", 0, size" + j2 + ", (j" + s2 + "*)ptr" + j2 + ");");
/* 1624 */                              this.out.println("    }");
/* 1625 */                              if (adapterInfo != null) {
/* 1626 */                                  this.out.println("    if (deallocator" + j2 + " != 0 && ptr" + j2 + " != NULL) {");
/* 1627 */                                  this.out.println("        (*(void(*)(void*))jlong_to_ptr(deallocator" + j2 + "))((void*)ptr" + j2 + ");");
/* 1628 */                                  this.out.println("    }");
                                        }
                                    } else if (Buffer.class.isAssignableFrom(callbackParameterTypes[j2])) {
/* 1631 */                              if (adapterInfo == null) {
/* 1632 */                                  this.out.println("    jint size" + j2 + " = ptr" + j2 + " != NULL ? 1 : 0;");
                                        }
/* 1634 */                              this.out.println("    if (ptr" + j2 + " != NULL) {");
/* 1635 */                              this.out.println("        obj" + j2 + " = env->NewDirectByteBuffer(ptr" + j2 + ", size" + j2 + ");");
/* 1636 */                              this.out.println("    }");
                                    } else {
/* 1638 */                              logger.log(Level.WARNING, "Callback \"" + callbackMethod + "\" has unsupported parameter type \"" + callbackParameterTypes[j2].getCanonicalName() + "\". Compilation will most likely fail.");
                                    }
                                }
                            }
                        }
/* 1645 */              this.out.println("    if (obj == NULL) {");
/* 1646 */              this.out.println("        obj = env->NewGlobalRef(env->AllocObject(JavaCPP_getClass(env, " + this.jclasses.register(cls) + ")));");
/* 1647 */              this.out.println("        if (obj == NULL) {");
/* 1648 */              this.out.println("            JavaCPP_log(\"Error creating global reference of " + cls.getCanonicalName() + " instance for callback.\");");
/* 1649 */              this.out.println("        } else {");
/* 1650 */              this.out.println("            env->SetLongField(obj, JavaCPP_addressFID, ptr_to_jlong(this));");
/* 1651 */              this.out.println("        }");
/* 1652 */              this.out.println("        ptr = &" + callbackName + ";");
/* 1653 */              this.out.println("    }");
/* 1654 */              this.out.println("    if (mid == NULL) {");
/* 1655 */              this.out.println("        mid = env->GetMethodID(JavaCPP_getClass(env, " + this.jclasses.register(cls) + "), \"" + callbackMethod.getName() + "\", \"(" + getSignature(callbackMethod.getParameterTypes()) + ")" + getSignature(callbackMethod.getReturnType()) + "\");");
/* 1657 */              this.out.println("    }");
/* 1658 */              this.out.println("    if (env->IsSameObject(obj, NULL)) {");
/* 1659 */              this.out.println("        JavaCPP_log(\"Function pointer object is NULL in callback for " + cls.getCanonicalName() + ".\");");
/* 1660 */              this.out.println("    } else if (mid == NULL) {");
/* 1661 */              this.out.println("        JavaCPP_log(\"Error getting method ID of function caller \\\"" + callbackMethod + "\\\" for callback.\");");
/* 1662 */              this.out.println("    } else {");
/* 1663 */              String s3 = "Object";
/* 1664 */              if (callbackReturnType.isPrimitive()) {
/* 1665 */                  String s4 = callbackReturnType.getName();
/* 1666 */                  s3 = Character.toUpperCase(s4.charAt(0)) + s4.substring(1);
                        }
/* 1668 */              this.out.println("        " + returnPrefix + "env->Call" + s3 + "MethodA(obj, mid, " + (callbackParameterTypes.length == 0 ? "NULL);" : "args);"));
/* 1669 */              this.out.println("        if ((exc = env->ExceptionOccurred()) != NULL) {");
/* 1670 */              this.out.println("            env->ExceptionClear();");
/* 1671 */              this.out.println("        }");
/* 1672 */              this.out.println("    }");
                        for (int j3 = 0; j3 < callbackParameterTypes.length; j3++) {
/* 1675 */                  if (Pointer.class.isAssignableFrom(callbackParameterTypes[j3])) {
/* 1676 */                      String[] typeName2 = getCPPTypeName(callbackParameterTypes[j3]);
/* 1677 */                      Annotation passBy2 = getBy(callbackParameterAnnotations[j3]);
/* 1678 */                      String cast = getCast(callbackParameterAnnotations[j3], callbackParameterTypes[j3]);
/* 1680 */                      AdapterInformation adapterInfo2 = getAdapterInformation(true, getValueTypeName(typeName2), callbackParameterAnnotations[j3]);
/* 1682 */                      if ("void*".equals(typeName2[0])) {
/* 1683 */                          typeName2[0] = "char*";
                                }
/* 1685 */                      if (adapterInfo2 != null || (passBy2 instanceof ByPtrPtr) || (passBy2 instanceof ByPtrRef)) {
/* 1686 */                          this.out.println("    " + typeName2[0] + " rptr" + j3 + typeName2[1] + " = (" + typeName2[0] + typeName2[1] + ")jlong_to_ptr(env->GetLongField(obj" + j3 + ", JavaCPP_addressFID));");
/* 1688 */                          if (adapterInfo2 != null) {
/* 1689 */                              this.out.println("    jint rsize" + j3 + " = env->GetIntField(obj" + j3 + ", JavaCPP_limitFID);");
                                    }
/* 1691 */                          if (!callbackParameterTypes[j3].isAnnotationPresent(Opaque.class)) {
/* 1692 */                              this.out.println("    jint rposition" + j3 + " = env->GetIntField(obj" + j3 + ", JavaCPP_positionFID);");
/* 1693 */                              this.out.println("    rptr" + j3 + " += rposition" + j3 + ";");
/* 1694 */                              if (adapterInfo2 != null) {
/* 1695 */                                  this.out.println("    rsize" + j3 + " -= rposition" + j3 + ";");
                                        }
                                    }
/* 1698 */                          if (adapterInfo2 != null) {
/* 1699 */                              this.out.println("    adapter" + j3 + ".assign(rptr" + j3 + ", rsize" + j3 + ");");
                                    } else if (passBy2 instanceof ByPtrPtr) {
/* 1701 */                              this.out.println("    if (arg" + j3 + " != NULL) {");
/* 1702 */                              this.out.println("        *arg" + j3 + " = *" + cast + "&rptr" + j3 + ";");
/* 1703 */                              this.out.println("    }");
                                    } else if (passBy2 instanceof ByPtrRef) {
/* 1705 */                              this.out.println("    arg" + j3 + " = " + cast + "rptr" + j3 + ";");
                                    }
                                }
                            }
/* 1709 */                  if (!callbackParameterTypes[j3].isPrimitive()) {
/* 1710 */                      this.out.println("    env->DeleteLocalRef(obj" + j3 + ");");
                            }
                        }
/* 1713 */              this.out.println("}");
/* 1714 */              this.out.println("end:");
/* 1716 */              if (callbackReturnType != Void.TYPE) {
/* 1717 */                  if ("void*".equals(returnTypeName[0])) {
/* 1718 */                      returnTypeName[0] = "char*";
                            }
/* 1720 */                  if (Pointer.class.isAssignableFrom(callbackReturnType)) {
/* 1721 */                      this.out.println("    " + returnTypeName[0] + " rptr" + returnTypeName[1] + " = rarg == NULL ? NULL : (" + returnTypeName[0] + returnTypeName[1] + ")jlong_to_ptr(env->GetLongField(rarg, JavaCPP_addressFID));");
/* 1723 */                      if (returnAdapterInfo != null) {
/* 1724 */                          this.out.println("    jint rsize = rarg == NULL ? 0 : env->GetIntField(rarg, JavaCPP_limitFID);");
                                }
/* 1726 */                      if (!callbackReturnType.isAnnotationPresent(Opaque.class)) {
/* 1727 */                          this.out.println("    jint rposition = rarg == NULL ? 0 : env->GetIntField(rarg, JavaCPP_positionFID);");
/* 1728 */                          this.out.println("    rptr += rposition;");
/* 1729 */                          if (returnAdapterInfo != null) {
/* 1730 */                              this.out.println("    rsize -= rposition;");
                                    }
                                }
                            } else if (callbackReturnType == String.class) {
/* 1734 */                      this.out.println("    " + returnTypeName[0] + " rptr" + returnTypeName[1] + " = rarg == NULL ? NULL : env->GetStringUTFChars(rarg, NULL);");
/* 1735 */                      if (returnAdapterInfo != null) {
/* 1736 */                          this.out.println("    jint rsize = 0;");
                                }
                            } else if (Buffer.class.isAssignableFrom(callbackReturnType)) {
/* 1739 */                      this.out.println("    " + returnTypeName[0] + " rptr" + returnTypeName[1] + " = rarg == NULL ? NULL : env->GetDirectBufferAddress(rarg);");
/* 1740 */                      if (returnAdapterInfo != null) {
/* 1741 */                          this.out.println("    jint rsize = rarg == NULL ? 0 : env->GetDirectBufferCapacity(rarg);");
                                }
                            } else if (!callbackReturnType.isPrimitive()) {
/* 1744 */                      logger.log(Level.WARNING, "Callback \"" + callbackMethod + "\" has unsupported return type \"" + callbackReturnType.getCanonicalName() + "\". Compilation will most likely fail.");
                            }
                        }
/* 1749 */              this.out.println("    if (exc != NULL) {");
/* 1750 */              this.out.println("        jclass cls = env->GetObjectClass(exc);");
/* 1751 */              this.out.println("        jmethodID mid = env->GetMethodID(cls, \"toString\", \"()Ljava/lang/String;\");");
/* 1752 */              this.out.println("        env->DeleteLocalRef(cls);");
/* 1753 */              this.out.println("        jstring str = (jstring)env->CallObjectMethod(exc, mid);");
/* 1754 */              this.out.println("        env->DeleteLocalRef(exc);");
/* 1755 */              this.out.println("        const char *msg = env->GetStringUTFChars(str, NULL);");
/* 1756 */              this.out.println("        JavaCPP_exception e(msg);");
/* 1757 */              this.out.println("        env->ReleaseStringUTFChars(str, msg);");
/* 1758 */              this.out.println("        env->DeleteLocalRef(str);");
/* 1759 */              this.out.println("        JavaCPP_detach(attached);");
/* 1760 */              this.out.println("        throw e;");
/* 1761 */              this.out.println("    } else {");
/* 1762 */              this.out.println("        JavaCPP_detach(attached);");
/* 1763 */              this.out.println("    }");
/* 1765 */              if (callbackReturnType != Void.TYPE) {
/* 1766 */                  if (callbackReturnType.isPrimitive()) {
/* 1767 */                      this.out.println("    return " + callbackReturnCast + "rarg;");
                            } else if (returnAdapterInfo != null) {
/* 1769 */                      this.usesAdapters = true;
/* 1770 */                      this.out.println("    return " + returnAdapterInfo.name + "(" + callbackReturnCast + "rptr, rsize);");
                            } else if (FunctionPointer.class.isAssignableFrom(callbackReturnType)) {
/* 1772 */                      this.out.println("    return " + callbackReturnCast + "(rptr == NULL ? NULL : rptr->ptr);");
                            } else if ((returnBy instanceof ByVal) || (returnBy instanceof ByRef)) {
/* 1774 */                      this.out.println("    if (rptr == NULL) {");
/* 1775 */                      this.out.println("        JavaCPP_log(\"Return pointer address is NULL in callback for " + cls.getCanonicalName() + ".\");");
/* 1776 */                      this.out.println("        static " + returnValueTypeName + " empty" + returnTypeName[1] + ";");
/* 1777 */                      this.out.println("        return empty;");
/* 1778 */                      this.out.println("    } else {");
/* 1779 */                      this.out.println("        return *" + callbackReturnCast + "rptr;");
/* 1780 */                      this.out.println("    }");
                            } else if (returnBy instanceof ByPtrPtr) {
/* 1782 */                      this.out.println("    return " + callbackReturnCast + "&rptr;");
                            } else {
/* 1784 */                      this.out.println("    return " + callbackReturnCast + "rptr;");
                            }
                        }
/* 1787 */              this.out.println("}");
                    }
                }

/* 1792 */      private void doCallbackAllocator(Class cls, String callbackName) {
/* 1793 */          String instanceTypeName = getFunctionClassName(cls);
/* 1794 */          this.out.println("    obj = env->NewWeakGlobalRef(obj);");
/* 1795 */          this.out.println("    if (obj == NULL) {");
/* 1796 */          this.out.println("        JavaCPP_log(\"Error creating global reference of " + cls.getCanonicalName() + " instance for callback.\");");
/* 1797 */          this.out.println("        return;");
/* 1798 */          this.out.println("    }");
/* 1799 */          this.out.println("    " + instanceTypeName + "* rptr = new (std::nothrow) " + instanceTypeName + ";");
/* 1800 */          this.out.println("    if (rptr != NULL) {");
/* 1801 */          this.out.println("        rptr->ptr = &" + callbackName + ";");
/* 1802 */          this.out.println("        rptr->obj = obj;");
/* 1803 */          this.out.println("        jvalue args[3];");
/* 1804 */          this.out.println("        args[0].j = ptr_to_jlong(rptr);");
/* 1805 */          this.out.println("        args[1].i = 1;");
/* 1806 */          this.out.println("        args[2].j = ptr_to_jlong(&JavaCPP_" + mangle(cls.getName()) + "_deallocate);");
/* 1807 */          this.deallocators.register(cls);
/* 1808 */          this.out.println("        env->CallNonvirtualVoidMethodA(obj, JavaCPP_getClass(env, " + this.jclasses.register(Pointer.class) + "), JavaCPP_initMID, args);");
/* 1810 */          this.out.println("        " + callbackName + "_instance = *rptr;");
/* 1811 */          this.out.println("    }");
/* 1812 */          this.out.println("}");
                }

/* 1815 */      public boolean checkPlatform(Class<?> cls) {
/* 1816 */          Properties properties = (Properties) cls.getAnnotation(Properties.class);
/* 1818 */          if (properties != null) {
/* 1819 */              Class[] clsArrInherit = properties.inherit();
/* 1820 */              if (clsArrInherit != null) {
                            for (Class cls2 : clsArrInherit) {
/* 1822 */                      if (checkPlatform((Class<?>) cls2)) {
                                    return true;
                                }
                            }
                        }
/* 1827 */              Platform[] platforms = properties.value();
/* 1828 */              if (platforms != null) {
                            for (Platform p : platforms) {
/* 1830 */                      if (checkPlatform(p)) {
                                    return true;
                                }
                            }
                        }
                    } else if (checkPlatform((Platform) cls.getAnnotation(Platform.class))) {
                        return true;
                    }
/* 1838 */          return false;
                }

/* 1841 */      public boolean checkPlatform(Platform platform) {
/* 1842 */          if (platform == null) {
                        return true;
                    }
/* 1845 */          String platformName = this.properties.getProperty("platform.name");
/* 1846 */          String[][] names = {platform.value(), platform.not()};
/* 1847 */          boolean[] matches = {false, false};
                    for (int i = 0; i < names.length; i++) {
/* 1849 */              String[] arr$ = names[i];
                        for (String s : arr$) {
/* 1850 */                  if (platformName.startsWith(s)) {
/* 1851 */                      matches[i] = true;
                                break;
                            }
                        }
                    }
                    return (names[0].length == 0 || matches[0]) && (names[1].length == 0 || !matches[1]);
                }

/* 1862 */      private String getFunctionClassName(Class<?> cls) {
/* 1863 */          Name name = (Name) cls.getAnnotation(Name.class);
                    return name != null ? name.value()[0] : "JavaCPP_" + mangle(cls.getName());
                }

/* 1867 */      private static Method getFunctionMethod(Class<?> cls, boolean[] callbackAllocators) {
/* 1868 */          if (!FunctionPointer.class.isAssignableFrom(cls)) {
/* 1869 */              return null;
                    }
/* 1871 */          Method[] methods = cls.getDeclaredMethods();
/* 1872 */          Method functionMethod = null;
                    for (int i = 0; i < methods.length; i++) {
/* 1874 */              String methodName = methods[i].getName();
/* 1875 */              int modifiers = methods[i].getModifiers();
/* 1876 */              Class<?>[] parameterTypes = methods[i].getParameterTypes();
/* 1877 */              Class<?> returnType = methods[i].getReturnType();
/* 1878 */              if (!Modifier.isStatic(modifiers)) {
/* 1881 */                  if (callbackAllocators != null && methodName.startsWith("allocate") && Modifier.isNative(modifiers) && returnType == Void.TYPE && parameterTypes.length == 0) {
/* 1885 */                      callbackAllocators[i] = true;
                            } else if (methodName.startsWith("call") || methodName.startsWith("apply")) {
/* 1888 */                      functionMethod = methods[i];
                            }
                        }
                    }
/* 1891 */          return functionMethod;
                }

                /* JADX WARN: Code duplicated, block: B:135:0x034e A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:136:0x0350  */
                /* JADX WARN: Code duplicated, block: B:138:0x035b  */
                /* JADX WARN: Code duplicated, block: B:141:0x036a A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:142:0x036c  */
                /* JADX WARN: Code duplicated, block: B:145:0x038c  */
                /* JADX WARN: Code duplicated, block: B:147:0x0392  */
                /* JADX WARN: Code duplicated, block: B:149:0x0395  */
                /* JADX WARN: Code duplicated, block: B:150:0x0398  */
                /* JADX WARN: Code duplicated, block: B:288:0x0208 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:289:0x0208 A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:290:0x0208 A[SYNTHETIC] */
/* 1910 */      public static MethodInformation getMethodInformation(Method method) {
                    Name name;
                    int length;
                    int i;
/* 1911 */          if (!Modifier.isNative(method.getModifiers())) {
/* 1912 */              return null;
                    }
/* 1914 */          MethodInformation info = new MethodInformation();
/* 1915 */          info.cls = method.getDeclaringClass();
/* 1916 */          info.method = method;
/* 1917 */          info.annotations = method.getAnnotations();
/* 1918 */          info.modifiers = method.getModifiers();
/* 1919 */          info.returnType = method.getReturnType();
/* 1920 */          info.name = method.getName();
/* 1921 */          Name name2 = (Name) method.getAnnotation(Name.class);
/* 1922 */          info.memberName = name2 != null ? name2.value() : new String[]{info.name};
/* 1923 */          Index index = (Index) method.getAnnotation(Index.class);
/* 1924 */          info.dim = index != null ? index.value() : 0;
/* 1925 */          info.parameterTypes = method.getParameterTypes();
/* 1926 */          info.parameterAnnotations = method.getParameterAnnotations();
/* 1927 */          info.returnRaw = method.isAnnotationPresent(Raw.class);
/* 1928 */          info.withEnv = info.returnRaw ? ((Raw) method.getAnnotation(Raw.class)).withEnv() : false;
/* 1929 */          info.parameterRaw = new boolean[info.parameterAnnotations.length];
                    for (int i2 = 0; i2 < info.parameterAnnotations.length; i2++) {
                        for (int j = 0; j < info.parameterAnnotations[i2].length; j++) {
/* 1932 */                  if (info.parameterAnnotations[i2][j] instanceof Raw) {
/* 1933 */                      info.parameterRaw[i2] = true;
                                info.withEnv = ((Raw) info.parameterAnnotations[i2][j]).withEnv() | info.withEnv;
                            }
                        }
                    }
/* 1939 */          boolean canBeGetter = info.returnType != Void.TYPE || (info.parameterTypes.length > 0 && info.parameterTypes[0].isArray() && info.parameterTypes[0].getComponentType().isPrimitive());
/* 1941 */          boolean canBeSetter = (info.returnType == Void.TYPE || info.returnType == info.cls) && info.parameterTypes.length > 0;
/* 1951 */          boolean canBeAllocator = !Modifier.isStatic(info.modifiers) && info.returnType == Void.TYPE;
/* 1953 */          boolean canBeArrayAllocator = canBeAllocator && info.parameterTypes.length == 1 && (info.parameterTypes[0] == Integer.TYPE || info.parameterTypes[0] == Long.TYPE);
/* 1956 */          boolean valueGetter = false;
/* 1957 */          boolean valueSetter = false;
/* 1958 */          boolean memberGetter = false;
/* 1959 */          boolean memberSetter = false;
/* 1960 */          boolean noReturnGetter = false;
/* 1961 */          Method pairedMethod = null;
/* 1962 */          Method[] methods = info.cls.getDeclaredMethods();
                    for (Method method2 : methods) {
/* 1965 */              int modifiers2 = method2.getModifiers();
/* 1966 */              Class<?> returnType = method2.getReturnType();
/* 1967 */              String methodName2 = method2.getName();
/* 1968 */              Class<?>[] parameterTypes = method2.getParameterTypes();
/* 1970 */              if (!method.equals(method2) && Modifier.isNative(modifiers2)) {
/* 1974 */                  boolean canBeValueGetter = false;
/* 1975 */                  boolean canBeValueSetter = false;
/* 1976 */                  if (canBeGetter && "get".equals(info.name) && "put".equals(methodName2)) {
/* 1977 */                      canBeValueGetter = true;
                            } else if (canBeSetter && "put".equals(info.name) && "get".equals(methodName2)) {
/* 1979 */                      canBeValueSetter = true;
                            } else if (methodName2.equals(info.name)) {
/* 1981 */                      info.overloaded = true;
                            }
/* 1986 */                  boolean sameIndexParameters = true;
                            for (int j2 = 0; j2 < info.parameterTypes.length && j2 < parameterTypes.length; j2++) {
/* 1988 */                      if (info.parameterTypes[j2] != parameterTypes[j2]) {
/* 1989 */                          sameIndexParameters = false;
                                }
                            }
/* 1992 */                  if (sameIndexParameters) {
/* 1996 */                      boolean parameterAsReturn = canBeValueGetter && info.parameterTypes.length > 0 && info.parameterTypes[0].isArray() && info.parameterTypes[0].getComponentType().isPrimitive();
/* 1998 */                      boolean parameterAsReturn2 = canBeValueSetter && parameterTypes.length > 0 && parameterTypes[0].isArray() && parameterTypes[0].getComponentType().isPrimitive();
/* 2001 */                      if (canBeGetter) {
                                    if (parameterTypes.length - (parameterAsReturn ? 0 : 1) == info.parameterTypes.length) {
                                        if ((parameterAsReturn ? info.parameterTypes[info.parameterTypes.length - 1] : info.returnType) == parameterTypes[parameterTypes.length - 1] && (returnType == Void.TYPE || returnType == info.cls)) {
/* 2004 */                                  pairedMethod = method2;
/* 2005 */                                  valueGetter = canBeValueGetter;
/* 2006 */                                  memberGetter = !canBeValueGetter;
/* 2007 */                                  noReturnGetter = parameterAsReturn;
                                        } else if (canBeSetter) {
                                            length = info.parameterTypes.length;
                                            if (parameterAsReturn2) {
                                                i = 0;
                                            } else {
/* 2008 */                                      i = 1;
                                            }
                                            if (length - i == parameterTypes.length) {
                                                if (parameterAsReturn2) {
                                                    returnType = parameterTypes[parameterTypes.length - 1];
                                                }
                                                if (returnType == info.parameterTypes[info.parameterTypes.length - 1]) {
/* 2011 */                                          pairedMethod = method2;
/* 2012 */                                          valueSetter = canBeValueSetter;
/* 2013 */                                          if (canBeValueSetter) {
/* 2013 */                                              memberSetter = false;
                                                    } else {
                                                        memberSetter = true;
                                                    }
                                                }
                                            }
                                        }
                                    } else if (canBeSetter) {
                                        length = info.parameterTypes.length;
                                        if (parameterAsReturn2) {
                                            i = 0;
                                        } else {
/* 2008 */                                  i = 1;
                                        }
                                        if (length - i == parameterTypes.length) {
                                            if (parameterAsReturn2) {
                                                returnType = parameterTypes[parameterTypes.length - 1];
                                            }
                                            if (returnType == info.parameterTypes[info.parameterTypes.length - 1]) {
/* 2011 */                                      pairedMethod = method2;
/* 2012 */                                      valueSetter = canBeValueSetter;
/* 2013 */                                      if (canBeValueSetter) {
                                                    memberSetter = true;
                                                } else {
/* 2013 */                                          memberSetter = false;
                                                }
                                            }
                                        }
                                    }
                                } else if (canBeSetter) {
                                    length = info.parameterTypes.length;
                                    if (parameterAsReturn2) {
                                        i = 0;
                                    } else {
/* 2008 */                              i = 1;
                                    }
                                    if (length - i == parameterTypes.length) {
                                        if (parameterAsReturn2) {
                                            returnType = parameterTypes[parameterTypes.length - 1];
                                        }
                                        if (returnType == info.parameterTypes[info.parameterTypes.length - 1]) {
/* 2011 */                                  pairedMethod = method2;
/* 2012 */                                  valueSetter = canBeValueSetter;
/* 2013 */                                  if (canBeValueSetter) {
                                                memberSetter = true;
                                            } else {
/* 2013 */                                      memberSetter = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
/* 2017 */          Annotation behavior = getBehavior(info.annotations);
/* 2018 */          if (canBeGetter && (behavior instanceof ValueGetter)) {
/* 2019 */              info.valueGetter = true;
/* 2020 */              info.noReturnGetter = noReturnGetter;
                    } else if (canBeSetter && (behavior instanceof ValueSetter)) {
/* 2022 */              info.valueSetter = true;
                    } else if (canBeGetter && (behavior instanceof MemberGetter)) {
/* 2024 */              info.memberGetter = true;
/* 2025 */              info.noReturnGetter = noReturnGetter;
                    } else if (canBeSetter && (behavior instanceof MemberSetter)) {
/* 2027 */              info.memberSetter = true;
                    } else if (canBeAllocator && (behavior instanceof Allocator)) {
/* 2029 */              info.allocator = true;
                    } else if (canBeArrayAllocator && (behavior instanceof ArrayAllocator)) {
/* 2031 */              info.arrayAllocator = true;
/* 2031 */              info.allocator = true;
                    } else if (behavior == null) {
/* 2034 */              if (info.returnType == Void.TYPE && "deallocate".equals(info.name) && !Modifier.isStatic(info.modifiers) && info.parameterTypes.length == 2 && info.parameterTypes[0] == Long.TYPE && info.parameterTypes[1] == Long.TYPE) {
/* 2037 */                  info.deallocator = true;
                        } else if (canBeAllocator && "allocate".equals(info.name)) {
/* 2039 */                  info.allocator = true;
                        } else if (canBeArrayAllocator && "allocateArray".equals(info.name)) {
/* 2041 */                  info.arrayAllocator = true;
/* 2041 */                  info.allocator = true;
                        } else if (info.returnType.isAssignableFrom(ByteBuffer.class) && "asDirectBuffer".equals(info.name) && !Modifier.isStatic(info.modifiers) && info.parameterTypes.length == 0) {
/* 2044 */                  info.bufferGetter = true;
                        } else if (valueGetter) {
/* 2046 */                  info.valueGetter = true;
/* 2047 */                  info.noReturnGetter = noReturnGetter;
/* 2048 */                  info.pairedMethod = pairedMethod;
                        } else if (valueSetter) {
/* 2050 */                  info.valueSetter = true;
/* 2051 */                  info.pairedMethod = pairedMethod;
                        } else if (memberGetter) {
/* 2053 */                  info.memberGetter = true;
/* 2054 */                  info.noReturnGetter = noReturnGetter;
/* 2055 */                  info.pairedMethod = pairedMethod;
                        } else if (memberSetter) {
/* 2057 */                  info.memberSetter = true;
/* 2058 */                  info.pairedMethod = pairedMethod;
                        }
                    } else {
/* 2061 */              logger.log(Level.WARNING, "Method \"" + method + "\" cannot behave like a \"" + behavior + "\". No code will be generated.");
/* 2063 */              return null;
                    }
/* 2066 */          if (name2 == null && info.pairedMethod != null && (name = (Name) info.pairedMethod.getAnnotation(Name.class)) != null) {
/* 2069 */              info.memberName = name.value();
                    }
/* 2073 */          info.noOffset = info.cls.isAnnotationPresent(NoOffset.class) || method.isAnnotationPresent(NoOffset.class) || method.isAnnotationPresent(Index.class);
/* 2076 */          if (!info.noOffset && info.pairedMethod != null) {
/* 2077 */              info.noOffset = info.pairedMethod.isAnnotationPresent(NoOffset.class) || info.pairedMethod.isAnnotationPresent(Index.class);
                    }
/* 2081 */          if (info.parameterTypes.length == 0 || !info.parameterTypes[0].isArray()) {
/* 2082 */              if (info.valueGetter || info.memberGetter) {
/* 2083 */                  info.dim = info.parameterTypes.length;
                        } else if (info.memberSetter || info.valueSetter) {
/* 2085 */                  info.dim = info.parameterTypes.length - 1;
                        }
                    }
/* 2089 */          info.throwsException = null;
/* 2090 */          if (!getNoException(info.cls, method)) {
/* 2091 */              if (((getBy(info.annotations) instanceof ByVal) && !getNoException(info.returnType, method)) || (!info.deallocator && !info.valueGetter && !info.valueSetter && !info.memberGetter && !info.memberSetter && !info.bufferGetter)) {
/* 2094 */                  Class<?>[] exceptions = method.getExceptionTypes();
/* 2095 */                  info.throwsException = exceptions.length > 0 ? exceptions[0] : RuntimeException.class;
/* 2098 */                  return info;
                        }
/* 2098 */              return info;
                    }
/* 2098 */          return info;
                }

/* 2101 */      public static boolean getNoException(Class<?> cls, Method method) {
/* 2102 */          boolean noException = baseClasses.contains(cls) || method.isAnnotationPresent(NoException.class);
/* 2104 */          while (!noException && cls != null) {
/* 2105 */              noException = cls.isAnnotationPresent(NoException.class);
                        if (noException) {
                            break;
                        }
/* 2108 */              cls = cls.getDeclaringClass();
                    }
/* 2110 */          return noException;
                }

/* 2119 */      public static AdapterInformation getParameterAdapterInformation(boolean out, MethodInformation methodInfo, int j) {
/* 2120 */          if (out && (methodInfo.parameterTypes[j] == String.class || methodInfo.valueSetter || methodInfo.memberSetter)) {
/* 2121 */              return null;
                    }
/* 2123 */          String typeName = getParameterCast(methodInfo, j);
/* 2124 */          if (typeName != null && typeName.startsWith("(") && typeName.endsWith(")")) {
/* 2125 */              typeName = typeName.substring(1, typeName.length() - 1);
                    }
/* 2127 */          if (typeName == null || typeName.length() == 0) {
/* 2128 */              typeName = getCastedCPPTypeName(methodInfo.parameterAnnotations[j], methodInfo.parameterTypes[j])[0];
                    }
/* 2130 */          String valueTypeName = getValueTypeName(typeName);
/* 2131 */          AdapterInformation adapter = getAdapterInformation(out, valueTypeName, methodInfo.parameterAnnotations[j]);
/* 2132 */          if (adapter != null || methodInfo.pairedMethod == null) {
/* 2136 */              return adapter;
                    }
                    if (methodInfo.valueSetter || methodInfo.memberSetter) {
/* 2134 */              return getAdapterInformation(out, valueTypeName, methodInfo.pairedMethod.getAnnotations());
                    }
/* 2136 */          return adapter;
                }

/* 2138 */      public static AdapterInformation getAdapterInformation(boolean out, String valueTypeName, Annotation... annotations) {
/* 2139 */          AdapterInformation adapterInfo = null;
/* 2140 */          boolean constant = false;
/* 2141 */          String cast = "";
                    for (Annotation a : annotations) {
/* 2143 */              Adapter adapter = a instanceof Adapter ? (Adapter) a : (Adapter) a.annotationType().getAnnotation(Adapter.class);
/* 2144 */              if (adapter != null) {
/* 2145 */                  adapterInfo = new AdapterInformation();
/* 2146 */                  adapterInfo.name = adapter.value();
/* 2147 */                  adapterInfo.argc = adapter.argc();
/* 2148 */                  if (a != adapter) {
                                try {
/* 2150 */                          Class<? extends Annotation> clsAnnotationType = a.annotationType();
/* 2151 */                          if (clsAnnotationType.isAnnotationPresent(Const.class)) {
/* 2152 */                              constant = true;
                                    }
                                    try {
/* 2155 */                              String value = clsAnnotationType.getDeclaredMethod(Vine.Settings.PROP_VALUE, new Class[0]).invoke(a, new Object[0]).toString();
/* 2156 */                              if (value != null && value.length() > 0) {
/* 2157 */                                  valueTypeName = value;
                                        }
                                    } catch (NoSuchMethodException e) {
/* 2162 */                              valueTypeName = null;
                                    }
/* 2164 */                          Cast c = (Cast) clsAnnotationType.getAnnotation(Cast.class);
/* 2165 */                          if (c != null && cast.length() == 0) {
/* 2166 */                              cast = c.value()[0];
/* 2167 */                              if (valueTypeName != null) {
/* 2168 */                                  cast = cast + "< " + valueTypeName + " >";
                                        }
/* 2170 */                              if (c.value().length > 1) {
/* 2171 */                                  cast = cast + c.value()[1];
                                        }
                                    }
                                } catch (Exception ex) {
/* 2175 */                          logger.log(Level.WARNING, "Could not invoke the value() method on annotation \"" + a + "\".", (Throwable) ex);
                                }
/* 2177 */                      if (valueTypeName != null && valueTypeName.length() > 0) {
/* 2178 */                          adapterInfo.name += "< " + valueTypeName + " >";
                                }
                            }
                        } else if (a instanceof Const) {
/* 2182 */                  constant = true;
                        } else if (a instanceof Cast) {
/* 2184 */                  Cast c2 = (Cast) a;
/* 2185 */                  if (c2.value().length > 1) {
/* 2186 */                      cast = c2.value()[1];
                            }
                        }
                    }
/* 2190 */          if (adapterInfo != null) {
/* 2191 */              adapterInfo.cast = cast;
/* 2192 */              adapterInfo.constant = constant;
                    }
/* 2194 */          if (out && constant) {
                        return null;
                    }
                    return adapterInfo;
                }

/* 2197 */      public static String getParameterCast(MethodInformation methodInfo, int j) {
/* 2198 */          String cast = getCast(methodInfo.parameterAnnotations[j], methodInfo.parameterTypes[j]);
/* 2199 */          if ((cast != null && cast.length() != 0) || j != methodInfo.parameterTypes.length - 1) {
/* 2203 */              return cast;
                    }
                    if ((methodInfo.valueSetter || methodInfo.memberSetter) && methodInfo.pairedMethod != null) {
/* 2201 */              return getCast(methodInfo.pairedMethod.getAnnotations(), methodInfo.pairedMethod.getReturnType());
                    }
/* 2203 */          return cast;
                }

/* 2206 */      public static String getCast(Annotation[] annotations, Class<?> type) {
/* 2207 */          String[] typeName = null;
                    for (Annotation a : annotations) {
/* 2209 */              if (((a instanceof Cast) && ((Cast) a).value()[0].length() > 0) || (a instanceof Const)) {
/* 2210 */                  typeName = getCastedCPPTypeName(annotations, type);
                            break;
                        }
                    }
                    return (typeName == null || typeName.length <= 0) ? "" : "(" + typeName[0] + typeName[1] + ")";
                }

/* 2217 */      public static Annotation getParameterBy(MethodInformation methodInfo, int j) {
/* 2218 */          Annotation passBy = getBy(methodInfo.parameterAnnotations[j]);
/* 2219 */          if (passBy != null || methodInfo.pairedMethod == null) {
/* 2223 */              return passBy;
                    }
                    if (methodInfo.valueSetter || methodInfo.memberSetter) {
/* 2221 */              return getBy(methodInfo.pairedMethod.getAnnotations());
                    }
/* 2223 */          return passBy;
                }

/* 2226 */      public static Annotation getBy(Annotation... annotations) {
/* 2227 */          Annotation byAnnotation = null;
                    for (Annotation a : annotations) {
/* 2229 */              if ((a instanceof ByPtr) || (a instanceof ByPtrPtr) || (a instanceof ByPtrRef) || (a instanceof ByRef) || (a instanceof ByVal)) {
/* 2231 */                  if (byAnnotation != null) {
/* 2232 */                      logger.log(Level.WARNING, "\"By\" annotation \"" + byAnnotation + "\" already found. Ignoring superfluous annotation \"" + a + "\".");
                            } else {
/* 2235 */                      byAnnotation = a;
                            }
                        }
                    }
/* 2239 */          return byAnnotation;
                }

/* 2242 */      public static Annotation getBehavior(Annotation... annotations) {
/* 2243 */          Annotation behaviorAnnotation = null;
                    for (Annotation a : annotations) {
/* 2245 */              if ((a instanceof Function) || (a instanceof Allocator) || (a instanceof ArrayAllocator) || (a instanceof ValueSetter) || (a instanceof ValueGetter) || (a instanceof MemberGetter) || (a instanceof MemberSetter)) {
/* 2248 */                  if (behaviorAnnotation != null) {
/* 2249 */                      logger.log(Level.WARNING, "Behavior annotation \"" + behaviorAnnotation + "\" already found. Ignoring superfluous annotation \"" + a + "\".");
                            } else {
/* 2252 */                      behaviorAnnotation = a;
                            }
                        }
                    }
/* 2256 */          return behaviorAnnotation;
                }

/* 2259 */      public static String getValueTypeName(String... typeName) {
/* 2260 */          String type = typeName[0];
/* 2261 */          if (type.startsWith("const ")) {
/* 2262 */              return type.substring(6, type.length() - 1);
                    }
/* 2263 */          if (type.length() != 0) {
/* 2264 */              return type.substring(0, type.length() - 1);
                    }
/* 2266 */          return type;
                }

/* 2269 */      public static String[] getAnnotatedCPPTypeName(Annotation[] annotations, Class<?> type) {
/* 2270 */          String[] typeName = getCastedCPPTypeName(annotations, type);
/* 2271 */          String prefix = typeName[0];
/* 2272 */          String suffix = typeName[1];
/* 2274 */          boolean casted = false;
                    for (Annotation a : annotations) {
/* 2276 */              if (((a instanceof Cast) && ((Cast) a).value()[0].length() > 0) || (a instanceof Const)) {
/* 2277 */                  casted = true;
                            break;
                        }
                    }
/* 2282 */          Annotation by = getBy(annotations);
/* 2283 */          if (by instanceof ByVal) {
/* 2284 */              prefix = getValueTypeName(typeName);
                    } else if (by instanceof ByRef) {
/* 2286 */              prefix = getValueTypeName(typeName) + "&";
                    } else if ((by instanceof ByPtrPtr) && !casted) {
/* 2288 */              prefix = prefix + "*";
                    } else if (by instanceof ByPtrRef) {
/* 2290 */              prefix = prefix + "&";
                    }
/* 2293 */          typeName[0] = prefix;
/* 2294 */          typeName[1] = suffix;
/* 2295 */          return typeName;
                }

/* 2298 */      public static String[] getCastedCPPTypeName(Annotation[] annotations, Class<?> type) {
/* 2299 */          String[] typeName = null;
/* 2300 */          boolean warning = false;
                    boolean adapter = false;
                    for (Annotation a : annotations) {
/* 2302 */              if (a instanceof Cast) {
/* 2303 */                  warning = typeName != null;
/* 2304 */                  String prefix = ((Cast) a).value()[0];
                            String suffix = "";
/* 2305 */                  int parenthesis = prefix.indexOf(41);
/* 2306 */                  if (parenthesis > 0) {
/* 2307 */                      suffix = prefix.substring(parenthesis).trim();
/* 2308 */                      prefix = prefix.substring(0, parenthesis).trim();
                            }
/* 2310 */                  typeName = prefix.length() > 0 ? new String[]{prefix, suffix} : null;
                        } else if (a instanceof Const) {
/* 2312 */                  warning = typeName != null;
                            if (!warning) {
/* 2316 */                      typeName = getCPPTypeName(type);
/* 2317 */                      if (((Const) a).value()) {
/* 2318 */                          typeName[0] = getValueTypeName(typeName) + " const *";
                                } else {
/* 2320 */                          typeName[0] = "const " + typeName[0];
                                }
/* 2322 */                      Annotation by = getBy(annotations);
/* 2323 */                      if (by instanceof ByPtrPtr) {
/* 2324 */                          typeName[0] = typeName[0] + "*";
                                }
                            }
                        } else if ((a instanceof Adapter) || a.annotationType().isAnnotationPresent(Adapter.class)) {
/* 2327 */                  adapter = true;
                        }
                    }
/* 2330 */          if (warning && !adapter) {
/* 2331 */              logger.log(Level.WARNING, "Without \"Adapter\", \"Cast\" and \"Const\" annotations are mutually exclusive.");
                    }
/* 2333 */          if (typeName == null) {
/* 2334 */              String[] typeName2 = getCPPTypeName(type);
/* 2336 */              return typeName2;
                    }
/* 2336 */          return typeName;
                }

/* 2339 */      public static String[] getCPPTypeName(Class<?> type) {
                    String prefix;
/* 2340 */          String prefix2 = "";
                    String suffix = "";
/* 2341 */          if (type == Buffer.class || type == Pointer.class) {
/* 2342 */              prefix2 = "void*";
                    } else if (type == byte[].class || type == ByteBuffer.class || type == BytePointer.class) {
/* 2344 */              prefix2 = "signed char*";
                    } else if (type == short[].class || type == ShortBuffer.class || type == ShortPointer.class) {
/* 2346 */              prefix2 = "short*";
                    } else if (type == int[].class || type == IntBuffer.class || type == IntPointer.class) {
/* 2348 */              prefix2 = "int*";
                    } else if (type == long[].class || type == LongBuffer.class || type == LongPointer.class) {
/* 2350 */              prefix2 = "jlong*";
                    } else if (type == float[].class || type == FloatBuffer.class || type == FloatPointer.class) {
/* 2352 */              prefix2 = "float*";
                    } else if (type == double[].class || type == DoubleBuffer.class || type == DoublePointer.class) {
/* 2354 */              prefix2 = "double*";
                    } else if (type == char[].class || type == CharBuffer.class || type == CharPointer.class) {
/* 2356 */              prefix2 = "unsigned short*";
                    } else if (type == boolean[].class) {
/* 2358 */              prefix2 = "unsigned char*";
                    } else if (type == PointerPointer.class) {
/* 2360 */              prefix2 = "void**";
                    } else if (type == String.class) {
/* 2362 */              prefix2 = "const char*";
                    } else if (type == Byte.TYPE) {
/* 2364 */              prefix2 = "signed char";
                    } else if (type == Long.TYPE) {
/* 2366 */              prefix2 = "jlong";
                    } else if (type == Character.TYPE) {
/* 2368 */              prefix2 = "unsigned short";
                    } else if (type == Boolean.TYPE) {
/* 2370 */              prefix2 = "unsigned char";
                    } else if (type.isPrimitive()) {
/* 2372 */              prefix2 = type.getName();
                    } else if (FunctionPointer.class.isAssignableFrom(type)) {
/* 2374 */              Method functionMethod = getFunctionMethod(type, null);
/* 2375 */              if (functionMethod != null) {
/* 2376 */                  Convention convention = (Convention) type.getAnnotation(Convention.class);
/* 2377 */                  String callingConvention = convention == null ? "" : convention.value() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/* 2378 */                  Namespace namespace = (Namespace) type.getAnnotation(Namespace.class);
/* 2379 */                  String spaceName = namespace == null ? "" : namespace.value();
/* 2380 */                  if (spaceName.length() > 0 && !spaceName.endsWith("::")) {
/* 2381 */                      spaceName = spaceName + "::";
                            }
/* 2383 */                  Class<?> returnType = functionMethod.getReturnType();
/* 2384 */                  Class<?>[] parameterTypes = functionMethod.getParameterTypes();
/* 2385 */                  Annotation[] annotations = functionMethod.getAnnotations();
/* 2386 */                  Annotation[][] parameterAnnotations = functionMethod.getParameterAnnotations();
/* 2387 */                  String[] returnTypeName = getAnnotatedCPPTypeName(annotations, returnType);
/* 2388 */                  AdapterInformation returnAdapterInfo = getAdapterInformation(false, getValueTypeName(returnTypeName), annotations);
/* 2389 */                  if (returnAdapterInfo != null && returnAdapterInfo.cast.length() > 0) {
/* 2390 */                      prefix = returnAdapterInfo.cast;
                            } else {
/* 2392 */                      prefix = returnTypeName[0] + returnTypeName[1];
                            }
/* 2394 */                  prefix2 = prefix + " (" + callingConvention + spaceName + "*";
/* 2395 */                  String suffix2 = ")(";
/* 2396 */                  if (namespace != null && !Pointer.class.isAssignableFrom(parameterTypes[0])) {
/* 2397 */                      logger.log(Level.WARNING, "First parameter of caller method call() or apply() for member function pointer " + type.getCanonicalName() + " is not a Pointer. Compilation will most likely fail.");
                            }
                            for (int j = namespace == null ? 0 : 1; j < parameterTypes.length; j++) {
/* 2401 */                      String[] paramTypeName = getAnnotatedCPPTypeName(parameterAnnotations[j], parameterTypes[j]);
/* 2402 */                      AdapterInformation paramAdapterInfo = getAdapterInformation(false, getValueTypeName(paramTypeName), parameterAnnotations[j]);
/* 2403 */                      if (paramAdapterInfo != null && paramAdapterInfo.cast.length() > 0) {
/* 2404 */                          suffix2 = suffix2 + paramAdapterInfo.cast + " arg" + j;
                                } else {
/* 2406 */                          suffix2 = suffix2 + paramTypeName[0] + " arg" + j + paramTypeName[1];
                                }
/* 2408 */                      if (j < parameterTypes.length - 1) {
/* 2409 */                          suffix2 = suffix2 + ", ";
                                }
                            }
/* 2412 */                  suffix = suffix2 + ")";
/* 2413 */                  if (type.isAnnotationPresent(Const.class)) {
/* 2414 */                      suffix = suffix + " const";
                            }
                        }
                    } else {
/* 2418 */              String scopedType = getCPPScopeName(type);
/* 2419 */              if (scopedType.length() > 0) {
/* 2420 */                  prefix2 = scopedType + "*";
                        } else {
/* 2422 */                  logger.log(Level.WARNING, "The class " + type.getCanonicalName() + " does not map to any C++ type. Compilation will most likely fail.");
                        }
                    }
/* 2426 */          return new String[]{prefix2, suffix};
                }

/* 2429 */      public static String getCPPScopeName(MethodInformation methodInfo) {
/* 2430 */          String scopeName = getCPPScopeName(methodInfo.cls);
/* 2431 */          Namespace namespace = (Namespace) methodInfo.method.getAnnotation(Namespace.class);
/* 2432 */          String spaceName = namespace == null ? "" : namespace.value();
/* 2433 */          if ((namespace != null && namespace.value().length() == 0) || spaceName.startsWith("::")) {
/* 2434 */              scopeName = "";
                    }
/* 2436 */          if (scopeName.length() > 0 && !scopeName.endsWith("::")) {
/* 2437 */              scopeName = scopeName + "::";
                    }
/* 2439 */          String scopeName2 = scopeName + spaceName;
/* 2440 */          if (spaceName.length() > 0 && !spaceName.endsWith("::")) {
/* 2441 */              scopeName2 = scopeName2 + "::";
                    }
/* 2443 */          return scopeName2 + methodInfo.memberName[0];
                }

/* 2446 */      public static String getCPPScopeName(Class<?> type) {
                    String s;
/* 2447 */          String scopeName = "";
/* 2448 */          while (type != null) {
/* 2449 */              Namespace namespace = (Namespace) type.getAnnotation(Namespace.class);
/* 2450 */              String spaceName = namespace == null ? "" : namespace.value();
/* 2451 */              if (Pointer.class.isAssignableFrom(type) && type != Pointer.class) {
/* 2452 */                  Name name = (Name) type.getAnnotation(Name.class);
/* 2454 */                  if (name == null) {
/* 2455 */                      String s2 = type.getName();
/* 2456 */                      int i = s2.lastIndexOf("$");
/* 2457 */                      if (i < 0) {
/* 2458 */                          i = s2.lastIndexOf(".");
                                }
/* 2460 */                      s = s2.substring(i + 1);
                            } else {
/* 2462 */                      s = name.value()[0];
                            }
/* 2464 */                  if (spaceName.length() > 0 && !spaceName.endsWith("::")) {
/* 2465 */                      spaceName = spaceName + "::";
                            }
/* 2467 */                  spaceName = spaceName + s;
                        }
/* 2469 */              if (scopeName.length() > 0 && !spaceName.endsWith("::")) {
/* 2470 */                  spaceName = spaceName + "::";
                        }
/* 2472 */              scopeName = spaceName + scopeName;
/* 2473 */              if ((namespace != null && namespace.value().length() == 0) || spaceName.startsWith("::")) {
                            break;
                        }
/* 2477 */              type = type.getDeclaringClass();
                    }
/* 2479 */          return scopeName;
                }

/* 2482 */      public static String getJNITypeName(Class type) {
/* 2483 */          if (type == Byte.TYPE) {
/* 2484 */              return "jbyte";
                    }
/* 2485 */          if (type == Short.TYPE) {
/* 2486 */              return "jshort";
                    }
/* 2487 */          if (type == Integer.TYPE) {
/* 2488 */              return "jint";
                    }
/* 2489 */          if (type == Long.TYPE) {
/* 2490 */              return "jlong";
                    }
/* 2491 */          if (type == Float.TYPE) {
/* 2492 */              return "jfloat";
                    }
/* 2493 */          if (type == Double.TYPE) {
/* 2494 */              return "jdouble";
                    }
/* 2495 */          if (type == Character.TYPE) {
/* 2496 */              return "jchar";
                    }
/* 2497 */          if (type == Boolean.TYPE) {
/* 2498 */              return "jboolean";
                    }
/* 2499 */          if (type == byte[].class) {
/* 2500 */              return "jbyteArray";
                    }
/* 2501 */          if (type == short[].class) {
/* 2502 */              return "jshortArray";
                    }
/* 2503 */          if (type == int[].class) {
/* 2504 */              return "jintArray";
                    }
/* 2505 */          if (type == long[].class) {
/* 2506 */              return "jlongArray";
                    }
/* 2507 */          if (type == float[].class) {
/* 2508 */              return "jfloatArray";
                    }
/* 2509 */          if (type == double[].class) {
/* 2510 */              return "jdoubleArray";
                    }
/* 2511 */          if (type == char[].class) {
/* 2512 */              return "jcharArray";
                    }
/* 2513 */          if (type == boolean[].class) {
/* 2514 */              return "jbooleanArray";
                    }
/* 2515 */          if (type.isArray()) {
/* 2516 */              return "jobjectArray";
                    }
/* 2517 */          if (type == String.class) {
/* 2518 */              return "jstring";
                    }
/* 2519 */          if (type == Class.class) {
/* 2520 */              return "jclass";
                    }
/* 2521 */          if (type == Void.TYPE) {
/* 2522 */              return "void";
                    }
/* 2524 */          return "jobject";
                }

/* 2528 */      public static String getSignature(Class... types) {
/* 2529 */          StringBuilder signature = new StringBuilder(types.length * 2);
                    for (Class cls : types) {
/* 2531 */              signature.append(getSignature(cls));
                    }
/* 2533 */          return signature.toString();
                }

/* 2535 */      public static String getSignature(Class type) {
/* 2536 */          if (type == Byte.TYPE) {
/* 2537 */              return "B";
                    }
/* 2538 */          if (type == Short.TYPE) {
/* 2539 */              return "S";
                    }
/* 2540 */          if (type == Integer.TYPE) {
/* 2541 */              return "I";
                    }
/* 2542 */          if (type == Long.TYPE) {
/* 2543 */              return "J";
                    }
/* 2544 */          if (type == Float.TYPE) {
/* 2545 */              return "F";
                    }
/* 2546 */          if (type == Double.TYPE) {
/* 2547 */              return "D";
                    }
/* 2548 */          if (type == Boolean.TYPE) {
/* 2549 */              return "Z";
                    }
/* 2550 */          if (type == Character.TYPE) {
/* 2551 */              return "C";
                    }
/* 2552 */          if (type == Void.TYPE) {
/* 2553 */              return "V";
                    }
/* 2554 */          if (type.isArray()) {
/* 2555 */              return type.getName().replace(".", "/");
                    }
/* 2557 */          return "L" + type.getName().replace(".", "/") + ";";
                }

/* 2561 */      public static String mangle(String name) {
/* 2562 */          StringBuilder mangledName = new StringBuilder(name.length());
                    for (int i = 0; i < name.length(); i++) {
/* 2564 */              char c = name.charAt(i);
/* 2565 */              if ((c >= '0' && c <= '9') || ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
/* 2566 */                  mangledName.append(c);
                        } else if (c == '_') {
/* 2568 */                  mangledName.append("_1");
                        } else if (c == ';') {
/* 2570 */                  mangledName.append("_2");
                        } else if (c == '[') {
/* 2572 */                  mangledName.append("_3");
                        } else if (c == '.' || c == '/') {
/* 2574 */                  mangledName.append("_");
                        } else {
/* 2576 */                  String code = Integer.toHexString(c);
/* 2577 */                  mangledName.append("_0");
                            switch (code.length()) {
                                case 1:
/* 2579 */                          mangledName.append("0");
                                case 2:
/* 2580 */                          mangledName.append("0");
                                case 3:
/* 2581 */                          mangledName.append("0");
                                    break;
                            }
/* 2582 */                  mangledName.append(code);
                        }
                    }
/* 2586 */          return mangledName.toString();
                }
            }
