            package com.facebook.buck.android.support.exopackage;

            import android.annotation.TargetApi;
            import dalvik.system.BaseDexClassLoader;
            import dalvik.system.DexClassLoader;
            import dalvik.system.PathClassLoader;
            import java.io.File;
            import java.lang.reflect.Array;
            import java.lang.reflect.Field;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    class SystemClassLoaderAdder {
/* 38 */        private SystemClassLoaderAdder() {
                }

/* 52 */        static void installDexJars(ClassLoader appClassLoader, File optimizedDirectory, List<File> dexJars) {
/* 53 */            SystemClassLoaderAdder classLoaderAdder = new SystemClassLoaderAdder();
                    for (File dexJar : dexJars) {
/* 56 */                DexClassLoader newClassLoader = new DexClassLoader(dexJar.getAbsolutePath(), optimizedDirectory.getAbsolutePath(), null, appClassLoader);
/* 61 */                classLoaderAdder.addPathsOfClassLoaderToSystemClassLoader(newClassLoader, (PathClassLoader) appClassLoader);
                    }
                }

/* 78 */        private void addPathsOfClassLoaderToSystemClassLoader(DexClassLoader newClassLoader, PathClassLoader systemClassLoader) {
                    try {
/* 79 */                if (existsBaseDexClassLoader()) {
/* 82 */                    addNewClassLoaderToSystemClassLoaderWithBaseDex(newClassLoader, systemClassLoader);
                        } else {
/* 86 */                    addNewClassLoaderToSystemClassLoaderPreBaseDex(newClassLoader, systemClassLoader);
                        }
                    } catch (IllegalAccessException e) {
/* 91 */                throw new RuntimeException(e);
                    } catch (NoSuchFieldException e2) {
/* 89 */                throw new RuntimeException(e2);
                    }
                }

/* 96 */        private boolean existsBaseDexClassLoader() {
                    try {
/* 97 */                Class.forName("dalvik.system.BaseDexClassLoader");
/* 98 */                return true;
                    } catch (ClassNotFoundException e) {
/* 100 */               return false;
                    }
                }

/* 113 */       private void addNewClassLoaderToSystemClassLoaderWithBaseDex(DexClassLoader newClassLoader, PathClassLoader systemClassLoader) throws IllegalAccessException, NoSuchFieldException {
/* 114 */           Object currentElementsArray = getDexElementsArray(getDexPathList(systemClassLoader));
/* 115 */           Object newElementsArray = getDexElementsArray(getDexPathList(newClassLoader));
/* 116 */           Object mergedElementsArray = mergeArrays(currentElementsArray, newElementsArray);
/* 117 */           setDexElementsArray(getDexPathList(systemClassLoader), mergedElementsArray);
                }

/* 133 */       private void addNewClassLoaderToSystemClassLoaderPreBaseDex(DexClassLoader newClassLoader, PathClassLoader systemClassLoader) throws IllegalAccessException, NoSuchFieldException {
                    try {
/* 134 */               newClassLoader.loadClass("foo");
                    } catch (ClassNotFoundException e) {
                    }
/* 141 */           setField(systemClassLoader, PathClassLoader.class, "mPaths", mergeArrayAndScalar(getField(systemClassLoader, PathClassLoader.class, "mPaths"), getField(newClassLoader, DexClassLoader.class, "mRawDexPath")));
/* 153 */           setField(systemClassLoader, PathClassLoader.class, "mFiles", mergeArrays(getField(systemClassLoader, PathClassLoader.class, "mFiles"), getField(newClassLoader, DexClassLoader.class, "mFiles")));
/* 165 */           setField(systemClassLoader, PathClassLoader.class, "mZips", mergeArrays(getField(systemClassLoader, PathClassLoader.class, "mZips"), getField(newClassLoader, DexClassLoader.class, "mZips")));
/* 177 */           setField(systemClassLoader, PathClassLoader.class, "mDexs", mergeArrays(getField(systemClassLoader, PathClassLoader.class, "mDexs"), getField(newClassLoader, DexClassLoader.class, "mDexs")));
                }

                @TargetApi(14)
/* 190 */       private Object getDexPathList(BaseDexClassLoader classLoader) throws IllegalAccessException, NoSuchFieldException {
/* 191 */           return getField(classLoader, BaseDexClassLoader.class, "pathList");
                }

/* 195 */       private Object getDexElementsArray(Object dexPathList) throws IllegalAccessException, NoSuchFieldException {
/* 196 */           return getField(dexPathList, dexPathList.getClass(), "dexElements");
                }

/* 200 */       private void setDexElementsArray(Object dexPathList, Object newElementArray) throws IllegalAccessException, NoSuchFieldException {
/* 201 */           setField(dexPathList, dexPathList.getClass(), "dexElements", newElementArray);
                }

/* 204 */       private Object mergeArrays(Object array1, Object array2) {
/* 205 */           Class<?> arrayClass = array1.getClass();
/* 206 */           Class<?> itemClass = arrayClass.getComponentType();
/* 207 */           int array1Size = Array.getLength(array1);
/* 208 */           int array2Size = Array.getLength(array2);
/* 209 */           int newSize = array1Size + array2Size;
/* 210 */           Object newArray = Array.newInstance(itemClass, newSize);
                    for (int i = 0; i < newSize; i++) {
/* 212 */               if (i < array1Size) {
/* 213 */                   Array.set(newArray, i, Array.get(array1, i));
                        } else {
/* 215 */                   Array.set(newArray, i, Array.get(array2, i - array1Size));
                        }
                    }
/* 218 */           return newArray;
                }

/* 221 */       private Object mergeArrayAndScalar(Object array, Object scalar) {
/* 222 */           Class<?> arrayClass = array.getClass();
/* 223 */           Class<?> itemClass = arrayClass.getComponentType();
/* 224 */           int array1Size = Array.getLength(array);
/* 225 */           int newSize = array1Size + 1;
/* 226 */           Object newArray = Array.newInstance(itemClass, newSize);
                    for (int i = 0; i < newSize; i++) {
/* 228 */               if (i < array1Size) {
/* 229 */                   Array.set(newArray, i, Array.get(array, i));
                        } else {
/* 231 */                   Array.set(newArray, i, scalar);
                        }
                    }
/* 234 */           return newArray;
                }

/* 238 */       private Object getField(Object object, Class<?> clazz, String fieldName) throws IllegalAccessException, NoSuchFieldException {
/* 239 */           Field field = clazz.getDeclaredField(fieldName);
/* 240 */           field.setAccessible(true);
/* 241 */           return field.get(object);
                }

/* 245 */       private void setField(Object object, Class<?> clazz, String fieldName, Object fieldValue) throws IllegalAccessException, NoSuchFieldException {
/* 246 */           Field field = clazz.getDeclaredField(fieldName);
/* 247 */           field.setAccessible(true);
/* 248 */           field.set(object, fieldValue);
                }
            }
