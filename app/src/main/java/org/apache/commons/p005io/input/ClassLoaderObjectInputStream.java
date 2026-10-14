            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            import java.io.ObjectInputStream;
            import java.io.ObjectStreamClass;
            import java.lang.reflect.Proxy;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class ClassLoaderObjectInputStream extends ObjectInputStream {
                private final ClassLoader classLoader;

/* 50 */        public ClassLoaderObjectInputStream(ClassLoader classLoader, InputStream inputStream) throws IOException {
/* 51 */            super(inputStream);
/* 52 */            this.classLoader = classLoader;
                }

                @Override // java.io.ObjectInputStream
/* 67 */        protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws ClassNotFoundException, IOException {
/* 68 */            Class<?> clazz = Class.forName(objectStreamClass.getName(), false, this.classLoader);
                    return clazz != null ? clazz : super.resolveClass(objectStreamClass);
                }

                @Override // java.io.ObjectInputStream
/* 92 */        protected Class<?> resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
/* 93 */            Class<?>[] interfaceClasses = new Class[interfaces.length];
                    for (int i = 0; i < interfaces.length; i++) {
/* 95 */                interfaceClasses[i] = Class.forName(interfaces[i], false, this.classLoader);
                    }
                    try {
/* 98 */                return Proxy.getProxyClass(this.classLoader, interfaceClasses);
                    } catch (IllegalArgumentException e) {
/* 100 */               return super.resolveProxyClass(interfaces);
                    }
                }
            }
