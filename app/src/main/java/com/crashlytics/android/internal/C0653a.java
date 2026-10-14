            package com.crashlytics.android.internal;

            import java.lang.reflect.Method;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 47 */    final class C0653a {

                /* JADX INFO: renamed from: a */
                private static final Map<Class<?>, Map<Class<?>, Method>> f290a = new HashMap();

                /* JADX INFO: renamed from: b */
                private static final Map<Class<?>, Map<Class<?>, Set<Method>>> f291b = new HashMap();

                /* JADX INFO: renamed from: a */
/* 48 */        private static void m158a(Class<?> cls) {
/* 49 */            Map<Class<?>, Set<Method>> map = new HashMap<>();
/* 50 */            Map<Class<?>, Method> map2 = new HashMap<>();
                    for (Method method : cls.getDeclaredMethods()) {
/* 53 */                if (method.isAnnotationPresent(InterfaceC0717l.class)) {
/* 54 */                    Class<?>[] parameterTypes = method.getParameterTypes();
/* 55 */                    if (parameterTypes.length != 1) {
/* 56 */                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires " + parameterTypes.length + " arguments.  Methods must require a single argument.");
                            }
/* 60 */                    Class<?> cls2 = parameterTypes[0];
/* 61 */                    if (cls2.isInterface()) {
/* 62 */                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + cls2 + " which is an interface.  Subscription must be on a concrete class type.");
                            }
/* 66 */                    if ((method.getModifiers() & 1) == 0) {
/* 67 */                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + cls2 + " but is not 'public'.");
                            }
/* 71 */                    Set<Method> hashSet = map.get(cls2);
/* 72 */                    if (hashSet == null) {
/* 73 */                        hashSet = new HashSet<>();
/* 74 */                        map.put(cls2, hashSet);
                            }
/* 76 */                    hashSet.add(method);
                        } else if (method.isAnnotationPresent(InterfaceC0716k.class)) {
/* 78 */                    Class<?>[] parameterTypes2 = method.getParameterTypes();
/* 79 */                    if (parameterTypes2.length != 0) {
/* 80 */                        throw new IllegalArgumentException("Method " + method + "has @Produce annotation but requires " + parameterTypes2.length + " arguments.  Methods must require zero arguments.");
                            }
/* 83 */                    if (method.getReturnType() == Void.class) {
/* 84 */                        throw new IllegalArgumentException("Method " + method + " has a return type of void.  Must declare a non-void type.");
                            }
/* 88 */                    Class<?> returnType = method.getReturnType();
/* 89 */                    if (returnType.isInterface()) {
/* 90 */                        throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + returnType + " which is an interface.  Producers must return a concrete class type.");
                            }
/* 93 */                    if (returnType.equals(Void.TYPE)) {
/* 94 */                        throw new IllegalArgumentException("Method " + method + " has @Produce annotation but has no return type.");
                            }
/* 97 */                    if ((method.getModifiers() & 1) == 0) {
/* 98 */                        throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + returnType + " but is not 'public'.");
                            }
/* 102 */                   if (map2.containsKey(returnType)) {
/* 103 */                       throw new IllegalArgumentException("Producer for type " + returnType + " has already been registered.");
                            }
/* 105 */                   map2.put(returnType, method);
                        } else {
                            continue;
                        }
                    }
/* 109 */           f290a.put(cls, map2);
/* 110 */           f291b.put(cls, map);
                }

                /* JADX INFO: renamed from: a */
/* 114 */       static Map<Class<?>, C0713h> m157a(Object obj) {
/* 115 */           Class<?> cls = obj.getClass();
/* 116 */           HashMap map = new HashMap();
/* 118 */           if (!f290a.containsKey(cls)) {
/* 119 */               m158a(cls);
                    }
/* 121 */           Map<Class<?>, Method> map2 = f290a.get(cls);
/* 122 */           if (!map2.isEmpty()) {
                        for (Map.Entry<Class<?>, Method> entry : map2.entrySet()) {
/* 125 */                   map.put(entry.getKey(), new C0713h(obj, entry.getValue()));
                        }
                    }
/* 129 */           return map;
                }

                /* JADX INFO: renamed from: b */
/* 133 */       static Map<Class<?>, Set<C0712g>> m159b(Object obj) {
/* 134 */           Class<?> cls = obj.getClass();
/* 135 */           HashMap map = new HashMap();
/* 137 */           if (!f291b.containsKey(cls)) {
/* 138 */               m158a(cls);
                    }
/* 140 */           Map<Class<?>, Set<Method>> map2 = f291b.get(cls);
/* 141 */           if (!map2.isEmpty()) {
                        for (Map.Entry<Class<?>, Set<Method>> entry : map2.entrySet()) {
/* 143 */                   HashSet hashSet = new HashSet();
/* 144 */                   Iterator<Method> it = entry.getValue().iterator();
                            while (it.hasNext()) {
/* 145 */                       hashSet.add(new C0712g(obj, it.next()));
                            }
/* 147 */                   map.put(entry.getKey(), hashSet);
                        }
                    }
/* 151 */           return map;
                }
            }
