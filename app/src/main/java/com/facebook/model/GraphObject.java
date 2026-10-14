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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 88 */    public interface GraphObject {
                Map<String, Object> asMap();

                <T extends GraphObject> T cast(Class<T> cls);

                JSONObject getInnerJSONObject();

                Object getProperty(String str);

                void removeProperty(String str);

                void setProperty(String str, Object obj);

/* 89 */        public static final class Factory {
                    private static final HashSet<Class<?>> verifiedGraphObjectClasses = new HashSet<>();
                    private static final SimpleDateFormat[] dateFormats = {new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US), new SimpleDateFormat("yyyy-MM-dd", Locale.US)};

/* 90 */            static /* synthetic */ GraphObject access$000(Class x0, JSONObject x1) {
/* 91 */                return createGraphObjectProxy(x0, x1);
                    }

/* 90 */            static /* synthetic */ Map access$100(JSONObject x0) {
/* 91 */                return createGraphObjectProxyForMap(x0);
                    }

/* 90 */            static /* synthetic */ Object access$200(Object x0) {
/* 91 */                return getUnderlyingJSONObject(x0);
                    }

/* 97 */            private Factory() {
                    }

/* 109 */           public static GraphObject create(JSONObject json) {
/* 110 */               return create(json, GraphObject.class);
                    }

/* 122 */           public static <T extends GraphObject> T create(JSONObject jSONObject, Class<T> cls) {
/* 123 */               return (T) createGraphObjectProxy(cls, jSONObject);
                    }

/* 133 */           public static GraphObject create() {
/* 134 */               return create(GraphObject.class);
                    }

/* 145 */           public static <T extends GraphObject> T create(Class<T> cls) {
/* 146 */               return (T) createGraphObjectProxy(cls, new JSONObject());
                    }

/* 155 */           public static boolean hasSameId(GraphObject a, GraphObject b) {
/* 156 */               if (a == null || b == null || !a.asMap().containsKey("id") || !b.asMap().containsKey("id")) {
                            return false;
                        }
/* 159 */               if (a.equals(b)) {
                            return true;
                        }
/* 162 */               Object idA = a.getProperty("id");
/* 163 */               Object idB = b.getProperty("id");
                        return idA != null && idB != null && (idA instanceof String) && (idB instanceof String) && idA.equals(idB);
                    }

/* 176 */           public static <T> GraphObjectList<T> createList(JSONArray array, Class<T> graphObjectClass) {
/* 177 */               return new GraphObjectListImpl(array, graphObjectClass);
                    }

/* 188 */           public static <T> GraphObjectList<T> createList(Class<T> graphObjectClass) {
/* 189 */               return createList(new JSONArray(), graphObjectClass);
                    }

/* 192 */           private static <T extends GraphObject> T createGraphObjectProxy(Class<T> graphObjectClass, JSONObject state) {
/* 193 */               verifyCanProxyClass(graphObjectClass);
/* 195 */               Class[] clsArr = {graphObjectClass};
/* 196 */               GraphObjectProxy graphObjectProxy = new GraphObjectProxy(state, graphObjectClass);
/* 199 */               return (T) Proxy.newProxyInstance(GraphObject.class.getClassLoader(), clsArr, graphObjectProxy);
                    }

/* 204 */           private static Map<String, Object> createGraphObjectProxyForMap(JSONObject state) {
/* 205 */               Class<?>[] interfaces = {Map.class};
/* 206 */               GraphObjectProxy graphObjectProxy = new GraphObjectProxy(state, Map.class);
/* 209 */               Map<String, Object> graphObject = (Map) Proxy.newProxyInstance(GraphObject.class.getClassLoader(), interfaces, graphObjectProxy);
/* 212 */               return graphObject;
                    }

/* 215 */           private static synchronized <T extends GraphObject> boolean hasClassBeenVerified(Class<T> graphObjectClass) {
                        try {
                        } catch (Throwable th) {
                            throw th;
                        }
                        return verifiedGraphObjectClasses.contains(graphObjectClass);
                    }

/* 219 */           private static synchronized <T extends GraphObject> void recordClassHasBeenVerified(Class<T> graphObjectClass) {
                        try {
                            verifiedGraphObjectClasses.add(graphObjectClass);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 223 */           private static <T extends GraphObject> void verifyCanProxyClass(Class<T> graphObjectClass) {
/* 224 */               if (!hasClassBeenVerified(graphObjectClass)) {
/* 228 */                   if (!graphObjectClass.isInterface()) {
/* 229 */                       throw new FacebookGraphObjectException("Factory can only wrap interfaces, not class: " + graphObjectClass.getName());
                            }
/* 233 */                   Method[] methods = graphObjectClass.getMethods();
                            for (Method method : methods) {
/* 235 */                       String methodName = method.getName();
/* 236 */                       int parameterCount = method.getParameterTypes().length;
/* 237 */                       Class<?> returnType = method.getReturnType();
/* 238 */                       boolean hasPropertyNameOverride = method.isAnnotationPresent(PropertyName.class);
/* 240 */                       if (!method.getDeclaringClass().isAssignableFrom(GraphObject.class)) {
/* 243 */                           if (parameterCount == 1 && returnType == Void.TYPE) {
/* 244 */                               if (hasPropertyNameOverride) {
/* 247 */                                   if (Utility.isNullOrEmpty(((PropertyName) method.getAnnotation(PropertyName.class)).value())) {
/* 267 */                                       throw new FacebookGraphObjectException("Factory can't proxy method: " + method.toString());
                                            }
                                        } else if (!methodName.startsWith("set") || methodName.length() <= 3) {
/* 267 */                                   throw new FacebookGraphObjectException("Factory can't proxy method: " + method.toString());
                                        }
                                    } else {
/* 254 */                               if (parameterCount == 0 && returnType != Void.TYPE) {
/* 255 */                                   if (hasPropertyNameOverride) {
/* 258 */                                       if (!Utility.isNullOrEmpty(((PropertyName) method.getAnnotation(PropertyName.class)).value())) {
                                                }
                                            } else if (!methodName.startsWith("get") || methodName.length() <= 3) {
                                            }
                                        }
/* 267 */                               throw new FacebookGraphObjectException("Factory can't proxy method: " + method.toString());
                                    }
                                }
                            }
/* 270 */                   recordClassHasBeenVerified(graphObjectClass);
                        }
                    }

/* 276 */           static <U> U coerceValueToExpectedType(Object obj, Class<U> cls, ParameterizedType parameterizedType) {
/* 277 */               if (obj == null) {
/* 278 */                   return null;
                        }
/* 281 */               Class<?> cls2 = obj.getClass();
/* 282 */               if (cls.isAssignableFrom(cls2)) {
/* 284 */                   return (U) obj;
                        }
/* 288 */               if (cls.isPrimitive()) {
/* 291 */                   return (U) obj;
                        }
/* 295 */               if (GraphObject.class.isAssignableFrom(cls)) {
/* 300 */                   if (JSONObject.class.isAssignableFrom(cls2)) {
/* 297 */                       return (U) createGraphObjectProxy((Class) cls, (JSONObject) obj);
                            }
/* 305 */                   if (GraphObject.class.isAssignableFrom(cls2)) {
/* 297 */                       return (U) ((GraphObject) obj).cast((Class) cls);
                            }
/* 311 */                   throw new FacebookGraphObjectException("Can't create GraphObject from " + cls2.getName());
                        }
/* 313 */               if (Iterable.class.equals(cls) || Collection.class.equals(cls) || List.class.equals(cls) || GraphObjectList.class.equals(cls)) {
/* 315 */                   if (parameterizedType == null) {
/* 316 */                       throw new FacebookGraphObjectException("can't infer generic type of: " + cls.toString());
                            }
/* 319 */                   Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
/* 321 */                   if (actualTypeArguments == null || actualTypeArguments.length != 1 || !(actualTypeArguments[0] instanceof Class)) {
/* 323 */                       throw new FacebookGraphObjectException("Expect collection properties to be of a type with exactly one generic parameter.");
                            }
/* 326 */                   Class cls3 = (Class) actualTypeArguments[0];
/* 328 */                   if (JSONArray.class.isAssignableFrom(cls2)) {
/* 331 */                       return (U) createList((JSONArray) obj, cls3);
                            }
/* 334 */                   throw new FacebookGraphObjectException("Can't create Collection from " + cls2.getName());
                        }
/* 336 */               if (String.class.equals(cls)) {
/* 337 */                   if (Double.class.isAssignableFrom(cls2) || Float.class.isAssignableFrom(cls2)) {
/* 340 */                       return (U) String.format("%f", obj);
                            }
/* 342 */                   if (Number.class.isAssignableFrom(cls2)) {
/* 344 */                       return (U) String.format("%d", obj);
                            }
                        } else if (Date.class.equals(cls) && String.class.isAssignableFrom(cls2)) {
                            for (SimpleDateFormat simpleDateFormat : dateFormats) {
                                try {
/* 351 */                           U u = (U) simpleDateFormat.parse((String) obj);
/* 352 */                           if (u != null) {
/* 354 */                               return u;
                                    }
                                } catch (ParseException e) {
                                }
                            }
                        }
/* 363 */               throw new FacebookGraphObjectException("Can't convert type" + cls2.getName() + " to " + cls.getName());
                    }

/* 367 */           static String convertCamelCaseToLowercaseWithUnderscores(String string) {
/* 369 */               return string.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.US);
                    }

/* 372 */           private static Object getUnderlyingJSONObject(Object obj) {
/* 373 */               Class<?> objClass = obj.getClass();
/* 374 */               if (GraphObject.class.isAssignableFrom(objClass)) {
/* 375 */                   GraphObject graphObject = (GraphObject) obj;
/* 376 */                   return graphObject.getInnerJSONObject();
                        }
/* 377 */               if (GraphObjectList.class.isAssignableFrom(objClass)) {
/* 378 */                   GraphObjectList<?> graphObjectList = (GraphObjectList) obj;
/* 379 */                   return graphObjectList.getInnerJSONArray();
                        }
/* 381 */               return obj;
                    }

/* 389 */           private static abstract class ProxyBase<STATE> implements InvocationHandler {
                        private static final String EQUALS_METHOD = "equals";
                        private static final String TOSTRING_METHOD = "toString";
                        protected final STATE state;

/* 390 */               protected ProxyBase(STATE state) {
/* 392 */                   this.state = state;
                        }

/* 396 */               protected final Object throwUnexpectedMethodSignature(Method method) {
/* 397 */                   throw new FacebookGraphObjectException(getClass().getName() + " got an unexpected method signature: " + method.toString());
                        }

/* 401 */               protected final Object proxyObjectMethods(Object proxy, Method method, Object[] args) throws Throwable {
/* 402 */                   String methodName = method.getName();
/* 403 */                   if (methodName.equals(EQUALS_METHOD)) {
/* 404 */                       Object other = args[0];
/* 406 */                       if (other == null) {
/* 407 */                           return false;
                                }
/* 410 */                       InvocationHandler handler = Proxy.getInvocationHandler(other);
/* 411 */                       if (!(handler instanceof GraphObjectProxy)) {
/* 412 */                           return false;
                                }
/* 414 */                       GraphObjectProxy otherProxy = (GraphObjectProxy) handler;
/* 415 */                       return Boolean.valueOf(this.state.equals(otherProxy.state));
                            }
/* 416 */                   if (methodName.equals(TOSTRING_METHOD)) {
/* 417 */                       return toString();
                            }
/* 421 */                   return method.invoke(this.state, args);
                        }
                    }

/* 447 */           private static final class GraphObjectProxy extends ProxyBase<JSONObject> {
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

/* 448 */               public GraphObjectProxy(JSONObject state, Class<?> graphObjectClass) {
/* 449 */                   super(state);
/* 450 */                   this.graphObjectClass = graphObjectClass;
                        }

/* 454 */               public String toString() {
/* 455 */                   return String.format("GraphObject{graphObjectClass=%s, state=%s}", this.graphObjectClass.getSimpleName(), this.state);
                        }

                        @Override // java.lang.reflect.InvocationHandler
/* 459 */               public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 460 */                   Class<?> declaringClass = method.getDeclaringClass();
/* 462 */                   if (declaringClass == Object.class) {
/* 463 */                       return proxyObjectMethods(proxy, method, args);
                            }
/* 464 */                   if (declaringClass == Map.class) {
/* 465 */                       return proxyMapMethods(method, args);
                            }
/* 466 */                   if (declaringClass == GraphObject.class) {
/* 467 */                       return proxyGraphObjectMethods(proxy, method, args);
                            }
/* 468 */                   if (GraphObject.class.isAssignableFrom(declaringClass)) {
/* 469 */                       return proxyGraphObjectGettersAndSetters(method, args);
                            }
/* 472 */                   return throwUnexpectedMethodSignature(method);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
/* 475 */               private final Object proxyMapMethods(Method method, Object[] args) {
/* 476 */                   String methodName = method.getName();
/* 477 */                   if (methodName.equals("clear")) {
/* 478 */                       JsonUtil.jsonObjectClear((JSONObject) this.state);
                                return null;
                            }
/* 480 */                   if (methodName.equals(CONTAINSKEY_METHOD)) {
/* 481 */                       return Boolean.valueOf(((JSONObject) this.state).has((String) args[0]));
                            }
/* 482 */                   if (methodName.equals(CONTAINSVALUE_METHOD)) {
/* 483 */                       return Boolean.valueOf(JsonUtil.jsonObjectContainsValue((JSONObject) this.state, args[0]));
                            }
/* 484 */                   if (methodName.equals(ENTRYSET_METHOD)) {
/* 485 */                       return JsonUtil.jsonObjectEntrySet((JSONObject) this.state);
                            }
/* 486 */                   if (methodName.equals(GET_METHOD)) {
/* 487 */                       return ((JSONObject) this.state).opt((String) args[0]);
                            }
/* 488 */                   if (methodName.equals(ISEMPTY_METHOD)) {
                                return Boolean.valueOf(((JSONObject) this.state).length() == 0);
                            }
/* 490 */                   if (methodName.equals(KEYSET_METHOD)) {
/* 491 */                       return JsonUtil.jsonObjectKeySet((JSONObject) this.state);
                            }
/* 492 */                   if (methodName.equals(PUT_METHOD)) {
/* 493 */                       return setJSONProperty(args);
                            }
/* 494 */                   if (methodName.equals(PUTALL_METHOD)) {
/* 495 */                       Map<String, Object> map = null;
/* 496 */                       if (args[0] instanceof Map) {
/* 498 */                           Map<String, Object> castMap = (Map) args[0];
/* 499 */                           map = castMap;
                                } else if (args[0] instanceof GraphObject) {
/* 501 */                           map = ((GraphObject) args[0]).asMap();
                                }
/* 503 */                       JsonUtil.jsonObjectPutAll((JSONObject) this.state, map);
                                return null;
                            }
/* 505 */                   if (methodName.equals(REMOVE_METHOD)) {
/* 506 */                       ((JSONObject) this.state).remove((String) args[0]);
                                return null;
                            }
/* 508 */                   if (methodName.equals("size")) {
/* 509 */                       return Integer.valueOf(((JSONObject) this.state).length());
                            }
/* 510 */                   if (methodName.equals(VALUES_METHOD)) {
/* 511 */                       return JsonUtil.jsonObjectValues((JSONObject) this.state);
                            }
/* 514 */                   return throwUnexpectedMethodSignature(method);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
/* 517 */               private final Object proxyGraphObjectMethods(Object proxy, Method method, Object[] args) {
/* 518 */                   String methodName = method.getName();
/* 519 */                   if (methodName.equals(CAST_METHOD)) {
/* 521 */                       Class<? extends GraphObject> graphObjectClass = (Class) args[0];
/* 523 */                       if (graphObjectClass == null || !graphObjectClass.isAssignableFrom(this.graphObjectClass)) {
/* 527 */                           return Factory.access$000(graphObjectClass, (JSONObject) this.state);
                                }
/* 543 */                       return proxy;
                            }
/* 528 */                   if (methodName.equals(GETINNERJSONOBJECT_METHOD)) {
/* 529 */                       InvocationHandler handler = Proxy.getInvocationHandler(proxy);
/* 530 */                       GraphObjectProxy otherProxy = (GraphObjectProxy) handler;
/* 531 */                       return otherProxy.state;
                            }
/* 532 */                   if (methodName.equals(CASTTOMAP_METHOD)) {
/* 533 */                       return Factory.access$100((JSONObject) this.state);
                            }
/* 534 */                   if (methodName.equals(GETPROPERTY_METHOD)) {
/* 535 */                       return ((JSONObject) this.state).opt((String) args[0]);
                            }
/* 536 */                   if (methodName.equals(SETPROPERTY_METHOD)) {
/* 537 */                       return setJSONProperty(args);
                            }
/* 538 */                   if (methodName.equals(REMOVEPROPERTY_METHOD)) {
/* 539 */                       ((JSONObject) this.state).remove((String) args[0]);
/* 540 */                       return null;
                            }
/* 543 */                   return throwUnexpectedMethodSignature(method);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
/* 546 */               private final Object proxyGraphObjectGettersAndSetters(Method method, Object[] args) throws JSONException {
/* 547 */                   String methodName = method.getName();
/* 548 */                   int parameterCount = method.getParameterTypes().length;
/* 549 */                   PropertyName propertyNameOverride = (PropertyName) method.getAnnotation(PropertyName.class);
/* 551 */                   String key = propertyNameOverride != null ? propertyNameOverride.value() : Factory.convertCamelCaseToLowercaseWithUnderscores(methodName.substring(3));
/* 555 */                   if (parameterCount == 0) {
/* 557 */                       Object value = ((JSONObject) this.state).opt(key);
/* 559 */                       Class<?> expectedType = method.getReturnType();
/* 561 */                       Type genericReturnType = method.getGenericReturnType();
/* 562 */                       ParameterizedType parameterizedReturnType = null;
/* 563 */                       if (genericReturnType instanceof ParameterizedType) {
/* 564 */                           parameterizedReturnType = (ParameterizedType) genericReturnType;
                                }
/* 567 */                       return Factory.coerceValueToExpectedType(value, expectedType, parameterizedReturnType);
                            }
/* 570 */                   if (parameterCount == 1) {
/* 572 */                       Object value2 = args[0];
/* 575 */                       if (GraphObject.class.isAssignableFrom(value2.getClass())) {
/* 576 */                           value2 = ((GraphObject) value2).getInnerJSONObject();
                                } else if (GraphObjectList.class.isAssignableFrom(value2.getClass())) {
/* 578 */                           value2 = ((GraphObjectList) value2).getInnerJSONArray();
                                } else if (Iterable.class.isAssignableFrom(value2.getClass())) {
/* 580 */                           JSONArray jsonArray = new JSONArray();
/* 581 */                           Iterable<?> iterable = (Iterable) value2;
                                    for (Object o : iterable) {
/* 583 */                               if (GraphObject.class.isAssignableFrom(o.getClass())) {
/* 584 */                                   jsonArray.put(((GraphObject) o).getInnerJSONObject());
                                        } else {
/* 586 */                                   jsonArray.put(o);
                                        }
                                    }
/* 589 */                           value2 = jsonArray;
                                }
/* 591 */                       ((JSONObject) this.state).putOpt(key, value2);
/* 592 */                       return null;
                            }
/* 595 */                   return throwUnexpectedMethodSignature(method);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
/* 598 */               private Object setJSONProperty(Object[] args) {
/* 599 */                   String name = (String) args[0];
/* 600 */                   Object property = args[1];
/* 601 */                   Object value = Factory.access$200(property);
                            try {
/* 603 */                       ((JSONObject) this.state).putOpt(name, value);
/* 607 */                       return null;
                            } catch (JSONException e) {
/* 605 */                       throw new IllegalArgumentException(e);
                            }
                        }
                    }

/* 613 */           private static final class GraphObjectListImpl<T> extends AbstractList<T> implements GraphObjectList<T> {
                        private final Class<?> itemType;
                        private final JSONArray state;

/* 614 */               public GraphObjectListImpl(JSONArray state, Class<?> itemType) {
/* 616 */                   Validate.notNull(state, "state");
/* 617 */                   Validate.notNull(itemType, "itemType");
/* 619 */                   this.state = state;
/* 620 */                   this.itemType = itemType;
                        }

                        @Override // java.util.AbstractCollection
/* 624 */               public String toString() {
/* 625 */                   return String.format("GraphObjectList{itemType=%s, state=%s}", this.itemType.getSimpleName(), this.state);
                        }

                        @Override // java.util.AbstractList, java.util.List
/* 630 */               public void add(int location, T object) {
/* 631 */                   if (location < 0) {
/* 632 */                       throw new IndexOutOfBoundsException();
                            }
/* 633 */                   if (location < size()) {
/* 634 */                       throw new UnsupportedOperationException("Only adding items at the end of the list is supported.");
                            }
/* 637 */                   put(location, object);
                        }

                        @Override // java.util.AbstractList, java.util.List
/* 641 */               public T set(int location, T object) {
/* 642 */                   checkIndex(location);
/* 644 */                   T result = get(location);
/* 645 */                   put(location, object);
/* 646 */                   return result;
                        }

                        @Override // java.util.AbstractList, java.util.Collection, java.util.List
/* 650 */               public int hashCode() {
/* 651 */                   return this.state.hashCode();
                        }

                        @Override // java.util.AbstractList, java.util.Collection, java.util.List
/* 655 */               public boolean equals(Object obj) {
/* 656 */                   if (this == obj) {
/* 657 */                       return true;
                            }
/* 658 */                   if (getClass() != obj.getClass()) {
/* 659 */                       return false;
                            }
/* 662 */                   GraphObjectListImpl<T> other = (GraphObjectListImpl) obj;
/* 663 */                   return this.state.equals(other.state);
                        }

                        @Override // java.util.AbstractList, java.util.List
/* 668 */               public T get(int i) {
/* 669 */                   checkIndex(i);
/* 675 */                   return (T) Factory.coerceValueToExpectedType(this.state.opt(i), this.itemType, null);
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
/* 681 */               public int size() {
/* 682 */                   return this.state.length();
                        }

                        @Override // com.facebook.model.GraphObjectList
/* 686 */               public final <U extends GraphObject> GraphObjectList<U> castToListOf(Class<U> graphObjectClass) {
/* 687 */                   if (GraphObject.class.isAssignableFrom(this.itemType)) {
                                return graphObjectClass.isAssignableFrom(this.itemType) ? (GraphObjectList<U>) this : Factory.createList(this.state, graphObjectClass);
                            }
/* 696 */                   throw new FacebookGraphObjectException("Can't cast GraphObjectCollection of non-GraphObject type " + this.itemType);
                        }

                        @Override // com.facebook.model.GraphObjectList
/* 702 */               public final JSONArray getInnerJSONArray() {
/* 703 */                   return this.state;
                        }

                        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
/* 707 */               public void clear() {
/* 708 */                   throw new UnsupportedOperationException();
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
/* 712 */               public boolean remove(Object o) {
/* 713 */                   throw new UnsupportedOperationException();
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
/* 717 */               public boolean removeAll(Collection<?> c) {
/* 718 */                   throw new UnsupportedOperationException();
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
/* 722 */               public boolean retainAll(Collection<?> c) {
/* 723 */                   throw new UnsupportedOperationException();
                        }

/* 726 */               private void checkIndex(int index) {
/* 727 */                   if (index < 0 || index >= this.state.length()) {
/* 728 */                       throw new IndexOutOfBoundsException();
                            }
                        }

/* 732 */               private void put(int index, T obj) {
/* 733 */                   Object underlyingObject = Factory.access$200(obj);
                            try {
/* 735 */                       this.state.put(index, underlyingObject);
                            } catch (JSONException e) {
/* 737 */                       throw new IllegalArgumentException(e);
                            }
                        }
                    }
                }
            }
