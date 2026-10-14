            package com.crashlytics.android.internal;

            import java.lang.reflect.Method;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            
            
            
    final class C0653a {

                
                private static final Map<Class<?>, Map<Class<?>, Method>> f290a = new HashMap();

                
                private static final Map<Class<?>, Map<Class<?>, Set<Method>>> f291b = new HashMap();

                
        private static void m158a(Class<?> cls) {
            Map<Class<?>, Set<Method>> map = new HashMap<>();
            Map<Class<?>, Method> map2 = new HashMap<>();
                    for (Method method : cls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(InterfaceC0717l.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1) {
                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires " + parameterTypes.length + " arguments.  Methods must require a single argument.");
                            }
                    Class<?> cls2 = parameterTypes[0];
                    if (cls2.isInterface()) {
                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + cls2 + " which is an interface.  Subscription must be on a concrete class type.");
                            }
                    if ((method.getModifiers() & 1) == 0) {
                        throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + cls2 + " but is not 'public'.");
                            }
                    Set<Method> hashSet = map.get(cls2);
                    if (hashSet == null) {
                        hashSet = new HashSet<>();
                        map.put(cls2, hashSet);
                            }
                    hashSet.add(method);
                        } else if (method.isAnnotationPresent(InterfaceC0716k.class)) {
                    Class<?>[] parameterTypes2 = method.getParameterTypes();
                    if (parameterTypes2.length != 0) {
                        throw new IllegalArgumentException("Method " + method + "has @Produce annotation but requires " + parameterTypes2.length + " arguments.  Methods must require zero arguments.");
                            }
                    if (method.getReturnType() == Void.class) {
                        throw new IllegalArgumentException("Method " + method + " has a return type of void.  Must declare a non-void type.");
                            }
                    Class<?> returnType = method.getReturnType();
                    if (returnType.isInterface()) {
                        throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + returnType + " which is an interface.  Producers must return a concrete class type.");
                            }
                    if (returnType.equals(Void.TYPE)) {
                        throw new IllegalArgumentException("Method " + method + " has @Produce annotation but has no return type.");
                            }
                    if ((method.getModifiers() & 1) == 0) {
                        throw new IllegalArgumentException("Method " + method + " has @Produce annotation on " + returnType + " but is not 'public'.");
                            }
                   if (map2.containsKey(returnType)) {
                       throw new IllegalArgumentException("Producer for type " + returnType + " has already been registered.");
                            }
                   map2.put(returnType, method);
                        } else {
                            continue;
                        }
                    }
           f290a.put(cls, map2);
           f291b.put(cls, map);
                }

                
       static Map<Class<?>, C0713h> m157a(Object obj) {
           Class<?> cls = obj.getClass();
           HashMap map = new HashMap();
           if (!f290a.containsKey(cls)) {
               m158a(cls);
                    }
           Map<Class<?>, Method> map2 = f290a.get(cls);
           if (!map2.isEmpty()) {
                        for (Map.Entry<Class<?>, Method> entry : map2.entrySet()) {
                   map.put(entry.getKey(), new C0713h(obj, entry.getValue()));
                        }
                    }
           return map;
                }

                
       static Map<Class<?>, Set<C0712g>> m159b(Object obj) {
           Class<?> cls = obj.getClass();
           HashMap map = new HashMap();
           if (!f291b.containsKey(cls)) {
               m158a(cls);
                    }
           Map<Class<?>, Set<Method>> map2 = f291b.get(cls);
           if (!map2.isEmpty()) {
                        for (Map.Entry<Class<?>, Set<Method>> entry : map2.entrySet()) {
                   HashSet hashSet = new HashSet();
                   Iterator<Method> it = entry.getValue().iterator();
                            while (it.hasNext()) {
                       hashSet.add(new C0712g(obj, it.next()));
                            }
                   map.put(entry.getKey(), hashSet);
                        }
                    }
           return map;
                }
            }
