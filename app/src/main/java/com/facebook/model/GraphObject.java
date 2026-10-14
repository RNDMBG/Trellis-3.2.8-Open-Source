            package com.facebook.model;

            import com.facebook.FacebookGraphObjectException;
            import com.facebook.internal.Utility;
            import com.facebook.internal.Validate;
            import java.lang.reflect.InvocationHandler;
            import java.lang.reflect.Method;
            import java.lang.reflect.ParameterizedType;
            import java.lang.reflect.Proxy;
            import java.lang.reflect.Type;
            import java.text.ParseException;
            import java.text.SimpleDateFormat;
            import java.util.AbstractList;
            import java.util.Collection;
            import java.util.Date;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Locale;
            import java.util.Map;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
    public interface GraphObject {
                Map<String, Object> asMap();

                <T extends GraphObject> T cast(Class<T> cls);

                JSONObject getInnerJSONObject();

                Object getProperty(String str);

                void removeProperty(String str);

                void setProperty(String str, Object obj);

        public static final class Factory {
                    private static final HashSet<Class<?>> verifiedGraphObjectClasses = new HashSet<>();
                    private static final SimpleDateFormat[] dateFormats = {new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US), new SimpleDateFormat("yyyy-MM-dd", Locale.US)};

            static  GraphObject access$000(Class x0, JSONObject x1) {
                return createGraphObjectProxy(x0, x1);
                    }

            static  Map access$100(JSONObject x0) {
                return createGraphObjectProxyForMap(x0);
                    }

            static  Object access$200(Object x0) {
                return getUnderlyingJSONObject(x0);
                    }

            private Factory() {
                    }

           public static GraphObject create(JSONObject json) {
               return create(json, GraphObject.class);
                    }

           public static <T extends GraphObject> T create(JSONObject jSONObject, Class<T> cls) {
               return (T) createGraphObjectProxy(cls, jSONObject);
                    }

           public static GraphObject create() {
               return create(GraphObject.class);
                    }

           public static <T extends GraphObject> T create(Class<T> cls) {
               return (T) createGraphObjectProxy(cls, new JSONObject());
                    }

           public static boolean hasSameId(GraphObject a, GraphObject b) {
               if (a == null || b == null || !a.asMap().containsKey("id") || !b.asMap().containsKey("id")) {
                            return false;
                        }
               if (a.equals(b)) {
                            return true;
                        }
               Object idA = a.getProperty("id");
               Object idB = b.getProperty("id");
                        return idA != null && idB != null && (idA instanceof String) && (idB instanceof String) && idA.equals(idB);
                    }

           public static <T> GraphObjectList<T> createList(JSONArray array, Class<T> graphObjectClass) {
               return new GraphObjectListImpl(array, graphObjectClass);
                    }

           public static <T> GraphObjectList<T> createList(Class<T> graphObjectClass) {
               return createList(new JSONArray(), graphObjectClass);
                    }

           private static <T extends GraphObject> T createGraphObjectProxy(Class<T> graphObjectClass, JSONObject state) {
               verifyCanProxyClass(graphObjectClass);
               Class[] clsArr = {graphObjectClass};
               GraphObjectProxy graphObjectProxy = new GraphObjectProxy(state, graphObjectClass);
               return (T) Proxy.newProxyInstance(GraphObject.class.getClassLoader(), clsArr, graphObjectProxy);
                    }

           private static Map<String, Object> createGraphObjectProxyForMap(JSONObject state) {
               Class<?>[] interfaces = {Map.class};
               GraphObjectProxy graphObjectProxy = new GraphObjectProxy(state, Map.class);
               Map<String, Object> graphObject = (Map) Proxy.newProxyInstance(GraphObject.class.getClassLoader(), interfaces, graphObjectProxy);
               return graphObject;
                    }

           private static synchronized <T extends GraphObject> boolean hasClassBeenVerified(Class<T> graphObjectClass) {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                        return verifiedGraphObjectClasses.contains(graphObjectClass);
                    }

           private static synchronized <T extends GraphObject> void recordClassHasBeenVerified(Class<T> graphObjectClass) {
                        try {
                            verifiedGraphObjectClasses.add(graphObjectClass);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

           private static <T extends GraphObject> void verifyCanProxyClass(Class<T> graphObjectClass) {
               if (!hasClassBeenVerified(graphObjectClass)) {
                   if (!graphObjectClass.isInterface()) {
                       throw new FacebookGraphObjectException("Factory can only wrap interfaces, not class: " + graphObjectClass.getName());
                            }
                   Method[] methods = graphObjectClass.getMethods();
                            for (Method method : methods) {
                       String methodName = method.getName();
                       int parameterCount = method.getParameterTypes().length;
                       Class<?> returnType = method.getReturnType();
                       boolean hasPropertyNameOverride = method.isAnnotationPresent(PropertyName.class);
                       if (!method.getDeclaringClass().isAssignableFrom(GraphObject.class)) {
                           if (parameterCount == 1 && returnType == Void.TYPE) {
                               if (hasPropertyNameOverride) {
                                   if (Utility.isNullOrEmpty(((PropertyName) method.getAnnotation(PropertyName.class)).value())) {
                                       throw new FacebookGraphObjectException("Factory can't proxy method: " + method.toString());
                                            }
                                        } else if (!methodName.startsWith("set") || methodName.length() <= 3) {
                                   throw new FacebookGraphObjectException("Factory can't proxy method: " + method.toString());
                                        }
                                    } else {
                               if (parameterCount == 0 && returnType != Void.TYPE) {
                                   if (hasPropertyNameOverride) {
                                       if (!Utility.isNullOrEmpty(((PropertyName) method.getAnnotation(PropertyName.class)).value())) {
                                                }
                                            } else if (!methodName.startsWith("get") || methodName.length() <= 3) {
                                            }
                                        }
                               throw new FacebookGraphObjectException("Factory can't proxy method: " + method.toString());
                                    }
                                }
                            }
                   recordClassHasBeenVerified(graphObjectClass);
                        }
                    }

           static <U> U coerceValueToExpectedType(Object obj, Class<U> cls, ParameterizedType parameterizedType) {
               if (obj == null) {
                   return null;
                        }
               Class<?> cls2 = obj.getClass();
               if (cls.isAssignableFrom(cls2)) {
                   return (U) obj;
                        }
               if (cls.isPrimitive()) {
                   return (U) obj;
                        }
               if (GraphObject.class.isAssignableFrom(cls)) {
                   if (JSONObject.class.isAssignableFrom(cls2)) {
                       return (U) createGraphObjectProxy((Class) cls, (JSONObject) obj);
                            }
                   if (GraphObject.class.isAssignableFrom(cls2)) {
                       return (U) ((GraphObject) obj).cast((Class) cls);
                            }
                   throw new FacebookGraphObjectException("Can't create GraphObject from " + cls2.getName());
                        }
               if (Iterable.class.equals(cls) || Collection.class.equals(cls) || List.class.equals(cls) || GraphObjectList.class.equals(cls)) {
                   if (parameterizedType == null) {
                       throw new FacebookGraphObjectException("can't infer generic type of: " + cls.toString());
                            }
                   Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                   if (actualTypeArguments == null || actualTypeArguments.length != 1 || !(actualTypeArguments[0] instanceof Class)) {
                       throw new FacebookGraphObjectException("Expect collection properties to be of a type with exactly one generic parameter.");
                            }
                   Class cls3 = (Class) actualTypeArguments[0];
                   if (JSONArray.class.isAssignableFrom(cls2)) {
                       return (U) createList((JSONArray) obj, cls3);
                            }
                   throw new FacebookGraphObjectException("Can't create Collection from " + cls2.getName());
                        }
               if (String.class.equals(cls)) {
                   if (Double.class.isAssignableFrom(cls2) || Float.class.isAssignableFrom(cls2)) {
                       return (U) String.format("%f", obj);
                            }
                   if (Number.class.isAssignableFrom(cls2)) {
                       return (U) String.format("%d", obj);
                            }
                        } else if (Date.class.equals(cls) && String.class.isAssignableFrom(cls2)) {
                            for (SimpleDateFormat simpleDateFormat : dateFormats) {
                                try {
                           U u = (U) simpleDateFormat.parse((String) obj);
                           if (u != null) {
                               return u;
                                    }
                                } catch (ParseException e) {
                                }
                            }
                        }
               throw new FacebookGraphObjectException("Can't convert type" + cls2.getName() + " to " + cls.getName());
                    }

           static String convertCamelCaseToLowercaseWithUnderscores(String string) {
               return string.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.US);
                    }

           private static Object getUnderlyingJSONObject(Object obj) {
               Class<?> objClass = obj.getClass();
               if (GraphObject.class.isAssignableFrom(objClass)) {
                   GraphObject graphObject = (GraphObject) obj;
                   return graphObject.getInnerJSONObject();
                        }
               if (GraphObjectList.class.isAssignableFrom(objClass)) {
                   GraphObjectList<?> graphObjectList = (GraphObjectList) obj;
                   return graphObjectList.getInnerJSONArray();
                        }
               return obj;
                    }

           private static abstract class ProxyBase<STATE> implements InvocationHandler {
                        private static final String EQUALS_METHOD = "equals";
                        private static final String TOSTRING_METHOD = "toString";
                        protected final STATE state;

               protected ProxyBase(STATE state) {
                   this.state = state;
                        }

               protected final Object throwUnexpectedMethodSignature(Method method) {
                   throw new FacebookGraphObjectException(getClass().getName() + " got an unexpected method signature: " + method.toString());
                        }

               protected final Object proxyObjectMethods(Object proxy, Method method, Object[] args) throws Throwable {
                   String methodName = method.getName();
                   if (methodName.equals(EQUALS_METHOD)) {
                       Object other = args[0];
                       if (other == null) {
                           return false;
                                }
                       InvocationHandler handler = Proxy.getInvocationHandler(other);
                       if (!(handler instanceof GraphObjectProxy)) {
                           return false;
                                }
                       GraphObjectProxy otherProxy = (GraphObjectProxy) handler;
                       return Boolean.valueOf(this.state.equals(otherProxy.state));
                            }
                   if (methodName.equals(TOSTRING_METHOD)) {
                       return toString();
                            }
                   return method.invoke(this.state, args);
                        }
                    }

           private static final class GraphObjectProxy extends ProxyBase<JSONObject> {
                        private static final String CASTTOMAP_METHOD = "asMap";
                        private static final String CAST_METHOD = "cast";
                        private static final String CLEAR_METHOD = "clear";
                        private static final String CONTAINSKEY_METHOD = "containsKey";
                        private static final String CONTAINSVALUE_METHOD = "containsValue";
                        private static final String ENTRYSET_METHOD = "entrySet";
                        private static final String GETINNERJSONOBJECT_METHOD = "getInnerJSONObject";
                        private static final String GETPROPERTY_METHOD = "getProperty";
                        private static final String GET_METHOD = "get";
                        private static final String ISEMPTY_METHOD = "isEmpty";
                        private static final String KEYSET_METHOD = "keySet";
                        private static final String PUTALL_METHOD = "putAll";
                        private static final String PUT_METHOD = "put";
                        private static final String REMOVEPROPERTY_METHOD = "removeProperty";
                        private static final String REMOVE_METHOD = "remove";
                        private static final String SETPROPERTY_METHOD = "setProperty";
                        private static final String SIZE_METHOD = "size";
                        private static final String VALUES_METHOD = "values";
                        private final Class<?> graphObjectClass;

               public GraphObjectProxy(JSONObject state, Class<?> graphObjectClass) {
                   super(state);
                   this.graphObjectClass = graphObjectClass;
                        }

               public String toString() {
                   return String.format("GraphObject{graphObjectClass=%s, state=%s}", this.graphObjectClass.getSimpleName(), this.state);
                        }

                        @Override 
               public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                   Class<?> declaringClass = method.getDeclaringClass();
                   if (declaringClass == Object.class) {
                       return proxyObjectMethods(proxy, method, args);
                            }
                   if (declaringClass == Map.class) {
                       return proxyMapMethods(method, args);
                            }
                   if (declaringClass == GraphObject.class) {
                       return proxyGraphObjectMethods(proxy, method, args);
                            }
                   if (GraphObject.class.isAssignableFrom(declaringClass)) {
                       return proxyGraphObjectGettersAndSetters(method, args);
                            }
                   return throwUnexpectedMethodSignature(method);
                        }

                        
               private final Object proxyMapMethods(Method method, Object[] args) {
                   String methodName = method.getName();
                   if (methodName.equals("clear")) {
                       JsonUtil.jsonObjectClear((JSONObject) this.state);
                                return null;
                            }
                   if (methodName.equals(CONTAINSKEY_METHOD)) {
                       return Boolean.valueOf(((JSONObject) this.state).has((String) args[0]));
                            }
                   if (methodName.equals(CONTAINSVALUE_METHOD)) {
                       return Boolean.valueOf(JsonUtil.jsonObjectContainsValue((JSONObject) this.state, args[0]));
                            }
                   if (methodName.equals(ENTRYSET_METHOD)) {
                       return JsonUtil.jsonObjectEntrySet((JSONObject) this.state);
                            }
                   if (methodName.equals(GET_METHOD)) {
                       return ((JSONObject) this.state).opt((String) args[0]);
                            }
                   if (methodName.equals(ISEMPTY_METHOD)) {
                                return Boolean.valueOf(((JSONObject) this.state).length() == 0);
                            }
                   if (methodName.equals(KEYSET_METHOD)) {
                       return JsonUtil.jsonObjectKeySet((JSONObject) this.state);
                            }
                   if (methodName.equals(PUT_METHOD)) {
                       return setJSONProperty(args);
                            }
                   if (methodName.equals(PUTALL_METHOD)) {
                       Map<String, Object> map = null;
                       if (args[0] instanceof Map) {
                           Map<String, Object> castMap = (Map) args[0];
                           map = castMap;
                                } else if (args[0] instanceof GraphObject) {
                           map = ((GraphObject) args[0]).asMap();
                                }
                       JsonUtil.jsonObjectPutAll((JSONObject) this.state, map);
                                return null;
                            }
                   if (methodName.equals(REMOVE_METHOD)) {
                       ((JSONObject) this.state).remove((String) args[0]);
                                return null;
                            }
                   if (methodName.equals("size")) {
                       return Integer.valueOf(((JSONObject) this.state).length());
                            }
                   if (methodName.equals(VALUES_METHOD)) {
                       return JsonUtil.jsonObjectValues((JSONObject) this.state);
                            }
                   return throwUnexpectedMethodSignature(method);
                        }

                        
               private final Object proxyGraphObjectMethods(Object proxy, Method method, Object[] args) {
                   String methodName = method.getName();
                   if (methodName.equals(CAST_METHOD)) {
                       Class<? extends GraphObject> graphObjectClass = (Class) args[0];
                       if (graphObjectClass == null || !graphObjectClass.isAssignableFrom(this.graphObjectClass)) {
                           return Factory.access$000(graphObjectClass, (JSONObject) this.state);
                                }
                       return proxy;
                            }
                   if (methodName.equals(GETINNERJSONOBJECT_METHOD)) {
                       InvocationHandler handler = Proxy.getInvocationHandler(proxy);
                       GraphObjectProxy otherProxy = (GraphObjectProxy) handler;
                       return otherProxy.state;
                            }
                   if (methodName.equals(CASTTOMAP_METHOD)) {
                       return Factory.access$100((JSONObject) this.state);
                            }
                   if (methodName.equals(GETPROPERTY_METHOD)) {
                       return ((JSONObject) this.state).opt((String) args[0]);
                            }
                   if (methodName.equals(SETPROPERTY_METHOD)) {
                       return setJSONProperty(args);
                            }
                   if (methodName.equals(REMOVEPROPERTY_METHOD)) {
                       ((JSONObject) this.state).remove((String) args[0]);
                       return null;
                            }
                   return throwUnexpectedMethodSignature(method);
                        }

                        
               private final Object proxyGraphObjectGettersAndSetters(Method method, Object[] args) throws JSONException {
                   String methodName = method.getName();
                   int parameterCount = method.getParameterTypes().length;
                   PropertyName propertyNameOverride = (PropertyName) method.getAnnotation(PropertyName.class);
                   String key = propertyNameOverride != null ? propertyNameOverride.value() : Factory.convertCamelCaseToLowercaseWithUnderscores(methodName.substring(3));
                   if (parameterCount == 0) {
                       Object value = ((JSONObject) this.state).opt(key);
                       Class<?> expectedType = method.getReturnType();
                       Type genericReturnType = method.getGenericReturnType();
                       ParameterizedType parameterizedReturnType = null;
                       if (genericReturnType instanceof ParameterizedType) {
                           parameterizedReturnType = (ParameterizedType) genericReturnType;
                                }
                       return Factory.coerceValueToExpectedType(value, expectedType, parameterizedReturnType);
                            }
                   if (parameterCount == 1) {
                       Object value2 = args[0];
                       if (GraphObject.class.isAssignableFrom(value2.getClass())) {
                           value2 = ((GraphObject) value2).getInnerJSONObject();
                                } else if (GraphObjectList.class.isAssignableFrom(value2.getClass())) {
                           value2 = ((GraphObjectList) value2).getInnerJSONArray();
                                } else if (Iterable.class.isAssignableFrom(value2.getClass())) {
                           JSONArray jsonArray = new JSONArray();
                           Iterable<?> iterable = (Iterable) value2;
                                    for (Object o : iterable) {
                               if (GraphObject.class.isAssignableFrom(o.getClass())) {
                                   jsonArray.put(((GraphObject) o).getInnerJSONObject());
                                        } else {
                                   jsonArray.put(o);
                                        }
                                    }
                           value2 = jsonArray;
                                }
                       ((JSONObject) this.state).putOpt(key, value2);
                       return null;
                            }
                   return throwUnexpectedMethodSignature(method);
                        }

                        
               private Object setJSONProperty(Object[] args) {
                   String name = (String) args[0];
                   Object property = args[1];
                   Object value = Factory.access$200(property);
                            try {
                       ((JSONObject) this.state).putOpt(name, value);
                       return null;
                            } catch (JSONException e) {
                       throw new IllegalArgumentException(e);
                            }
                        }
                    }

           private static final class GraphObjectListImpl<T> extends AbstractList<T> implements GraphObjectList<T> {
                        private final Class<?> itemType;
                        private final JSONArray state;

               public GraphObjectListImpl(JSONArray state, Class<?> itemType) {
                   Validate.notNull(state, "state");
                   Validate.notNull(itemType, "itemType");
                   this.state = state;
                   this.itemType = itemType;
                        }

                        @Override 
               public String toString() {
                   return String.format("GraphObjectList{itemType=%s, state=%s}", this.itemType.getSimpleName(), this.state);
                        }

                        @Override 
               public void add(int location, T object) {
                   if (location < 0) {
                       throw new IndexOutOfBoundsException();
                            }
                   if (location < size()) {
                       throw new UnsupportedOperationException("Only adding items at the end of the list is supported.");
                            }
                   put(location, object);
                        }

                        @Override 
               public T set(int location, T object) {
                   checkIndex(location);
                   T result = get(location);
                   put(location, object);
                   return result;
                        }

                        @Override 
               public int hashCode() {
                   return this.state.hashCode();
                        }

                        @Override 
               public boolean equals(Object obj) {
                   if (this == obj) {
                       return true;
                            }
                   if (getClass() != obj.getClass()) {
                       return false;
                            }
                   GraphObjectListImpl<T> other = (GraphObjectListImpl) obj;
                   return this.state.equals(other.state);
                        }

                        @Override 
               public T get(int i) {
                   checkIndex(i);
                   return (T) Factory.coerceValueToExpectedType(this.state.opt(i), this.itemType, null);
                        }

                        @Override 
               public int size() {
                   return this.state.length();
                        }

                        @Override 
               public final <U extends GraphObject> GraphObjectList<U> castToListOf(Class<U> graphObjectClass) {
                   if (GraphObject.class.isAssignableFrom(this.itemType)) {
                                return graphObjectClass.isAssignableFrom(this.itemType) ? (GraphObjectList<U>) this : Factory.createList(this.state, graphObjectClass);
                            }
                   throw new FacebookGraphObjectException("Can't cast GraphObjectCollection of non-GraphObject type " + this.itemType);
                        }

                        @Override 
               public final JSONArray getInnerJSONArray() {
                   return this.state;
                        }

                        @Override 
               public void clear() {
                   throw new UnsupportedOperationException();
                        }

                        @Override 
               public boolean remove(Object o) {
                   throw new UnsupportedOperationException();
                        }

                        @Override 
               public boolean removeAll(Collection<?> c) {
                   throw new UnsupportedOperationException();
                        }

                        @Override 
               public boolean retainAll(Collection<?> c) {
                   throw new UnsupportedOperationException();
                        }

               private void checkIndex(int index) {
                   if (index < 0 || index >= this.state.length()) {
                       throw new IndexOutOfBoundsException();
                            }
                        }

               private void put(int index, T obj) {
                   Object underlyingObject = Factory.access$200(obj);
                            try {
                       this.state.put(index, underlyingObject);
                            } catch (JSONException e) {
                       throw new IllegalArgumentException(e);
                            }
                        }
                    }
                }
            }
