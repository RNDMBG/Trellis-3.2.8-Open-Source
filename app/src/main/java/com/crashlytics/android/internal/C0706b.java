            package com.crashlytics.android.internal;

            import java.lang.reflect.InvocationTargetException;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.Map;
            import java.util.Set;
            import java.util.concurrent.ConcurrentHashMap;
            import java.util.concurrent.ConcurrentLinkedQueue;
            import java.util.concurrent.ConcurrentMap;
            import java.util.concurrent.CopyOnWriteArraySet;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 123 */   public class C0706b {

                /* JADX INFO: renamed from: a */
                public static final String f435a = "default";

                /* JADX INFO: renamed from: b */
                private final ConcurrentMap<Class<?>, Set<C0712g>> f436b;

                /* JADX INFO: renamed from: c */
                private final ConcurrentMap<Class<?>, C0713h> f437c;

                /* JADX INFO: renamed from: d */
                private final String f438d;

                /* JADX INFO: renamed from: e */
                private final InterfaceC0718m f439e;

                /* JADX INFO: renamed from: f */
                private final InterfaceC0714i f440f;

                /* JADX INFO: renamed from: g */
                private final ThreadLocal<ConcurrentLinkedQueue<C0710e>> f441g;

                /* JADX INFO: renamed from: h */
                private final ThreadLocal<Boolean> f442h;

                /* JADX INFO: renamed from: i */
                private final Map<Class<?>, Set<Class<?>>> f443i;

/* 124 */       public C0706b() {
/* 125 */           this(f435a);
                }

/* 133 */       public C0706b(String str) {
/* 134 */           this(InterfaceC0718m.f458b, str);
                }

/* 142 */       public C0706b(InterfaceC0718m interfaceC0718m) {
/* 143 */           this(interfaceC0718m, f435a);
                }

/* 152 */       public C0706b(InterfaceC0718m interfaceC0718m, String str) {
/* 153 */           this(interfaceC0718m, str, InterfaceC0714i.f456a);
                }

/* 162 */       private C0706b(InterfaceC0718m interfaceC0718m, String str, InterfaceC0714i interfaceC0714i) {
/* 92 */            this.f436b = new ConcurrentHashMap();
/* 96 */            this.f437c = new ConcurrentHashMap();
/* 109 */           this.f441g = new C0708c(this);
/* 117 */           this.f442h = new C0709d(this);
/* 450 */           this.f443i = new HashMap();
/* 164 */           this.f439e = interfaceC0718m;
/* 165 */           this.f438d = str;
/* 166 */           this.f440f = interfaceC0714i;
                }

/* 169 */       public String toString() {
/* 170 */           return "[Bus \"" + this.f438d + "\"]";
                }

                /* JADX INFO: renamed from: a */
/* 184 */       public void m340a(Object obj) {
                    CopyOnWriteArraySet copyOnWriteArraySet;
/* 185 */           this.f439e.mo350a(this);
/* 187 */           Map<Class<?>, C0713h> mapMo348a = this.f440f.mo348a(obj);
                    for (Class<?> cls : mapMo348a.keySet()) {
/* 190 */               C0713h c0713h = mapMo348a.get(cls);
/* 191 */               C0713h c0713hPutIfAbsent = this.f437c.putIfAbsent(cls, c0713h);
/* 193 */               if (c0713hPutIfAbsent != null) {
/* 194 */                   throw new IllegalArgumentException("Producer method for type " + cls + " found on type " + c0713h.f452a.getClass() + ", but already registered by type " + c0713hPutIfAbsent.f452a.getClass() + ".");
                        }
/* 198 */               Set<C0712g> set = this.f436b.get(cls);
/* 199 */               if (set != null && !set.isEmpty()) {
/* 200 */                   Iterator<C0712g> it = set.iterator();
                            while (it.hasNext()) {
/* 201 */                       m337a(it.next(), c0713h);
                            }
                        }
                    }
/* 206 */           Map<Class<?>, Set<C0712g>> mapMo349b = this.f440f.mo349b(obj);
                    for (Class<?> cls2 : mapMo349b.keySet()) {
/* 208 */               Set<C0712g> setPutIfAbsent = this.f436b.get(cls2);
/* 209 */               if (setPutIfAbsent == null && (setPutIfAbsent = this.f436b.putIfAbsent(cls2, (copyOnWriteArraySet = new CopyOnWriteArraySet()))) == null) {
                            setPutIfAbsent = copyOnWriteArraySet;
                        }
/* 218 */               setPutIfAbsent.addAll(mapMo349b.get(cls2));
                    }
                    for (Map.Entry<Class<?>, Set<C0712g>> entry : mapMo349b.entrySet()) {
/* 223 */               C0713h c0713h2 = this.f437c.get(entry.getKey());
/* 224 */               if (c0713h2 != null && c0713h2.m345a()) {
                            for (C0712g c0712g : entry.getValue()) {
/* 227 */                       if (!c0713h2.m345a()) {
                                    break;
                                } else if (c0712g.m343a()) {
/* 231 */                           m337a(c0712g, c0713h2);
                                }
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 238 */       private void m337a(C0712g c0712g, C0713h c0713h) {
/* 239 */           Object objM347c = null;
                    try {
/* 241 */               objM347c = c0713h.m347c();
                    } catch (InvocationTargetException e) {
/* 243 */               m339a("Producer " + c0713h + " threw an exception.", e);
                    }
/* 245 */           if (objM347c != null) {
/* 248 */               m338a(objM347c, c0712g);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 257 */       public void m341b(Object obj) {
/* 258 */           this.f439e.mo350a(this);
                    for (Map.Entry<Class<?>, C0713h> entry : this.f440f.mo348a(obj).entrySet()) {
/* 262 */               Class<?> key = entry.getKey();
/* 263 */               C0713h c0713h = this.f437c.get(key);
/* 264 */               C0713h value = entry.getValue();
/* 266 */               if (value == null || !value.equals(c0713h)) {
/* 267 */                   throw new IllegalArgumentException("Missing event producer for an annotated method. Is " + obj.getClass() + " registered?");
                        }
/* 271 */               this.f437c.remove(key).m346b();
                    }
                    for (Map.Entry<Class<?>, Set<C0712g>> entry2 : this.f440f.mo349b(obj).entrySet()) {
/* 276 */               Set<C0712g> setM335a = m335a(entry2.getKey());
/* 277 */               Set<C0712g> value2 = entry2.getValue();
/* 279 */               if (setM335a == null || !setM335a.containsAll(value2)) {
/* 280 */                   throw new IllegalArgumentException("Missing event handler for an annotated method. Is " + obj.getClass() + " registered?");
                        }
                        for (C0712g c0712g : setM335a) {
/* 286 */                   if (value2.contains(c0712g)) {
/* 287 */                       c0712g.m344b();
                            }
                        }
/* 290 */               setM335a.removeAll(value2);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 303 */       public void mo102c(Object obj) {
                    boolean z;
/* 304 */           this.f439e.mo350a(this);
/* 306 */           Class<?> cls = obj.getClass();
                    Set<Class<?>> set = this.f443i.get(cls);
                    if (set == null) {
                        LinkedList linkedList = new LinkedList();
                        HashSet hashSet = new HashSet();
                        linkedList.add(cls);
                        while (!linkedList.isEmpty()) {
                            Class<?> cls2 = (Class) linkedList.remove(0);
                            hashSet.add(cls2);
                            Class<? super Object> superclass = cls2.getSuperclass();
                            if (superclass != null) {
                                linkedList.add(superclass);
                            }
                        }
                        this.f443i.put(cls, hashSet);
                        set = hashSet;
                    }
/* 309 */           Iterator<Class<?>> it = set.iterator();
                    boolean z2 = false;
                    while (it.hasNext()) {
/* 310 */               Set<C0712g> setM335a = m335a(it.next());
/* 312 */               if (setM335a == null || setM335a.isEmpty()) {
                            z = z2;
                        } else {
/* 314 */                   Iterator<C0712g> it2 = setM335a.iterator();
                            while (it2.hasNext()) {
/* 315 */                       this.f441g.get().offer(new C0710e(obj, it2.next()));
                            }
/* 313 */                   z = true;
                        }
                        z2 = z;
                    }
/* 320 */           if (!z2 && !(obj instanceof C0711f)) {
/* 321 */               mo102c(new C0711f(this, obj));
                    }
/* 324 */           m336a();
                }

                /* JADX INFO: renamed from: a */
/* 341 */       private void m336a() {
/* 342 */           if (!this.f442h.get().booleanValue()) {
/* 346 */               this.f442h.set(true);
                        while (true) {
                            try {
/* 349 */                       C0710e c0710ePoll = this.f441g.get().poll();
/* 350 */                       if (c0710ePoll != null) {
/* 351 */                           if (c0710ePoll.f445b.m343a()) {
/* 355 */                               m338a(c0710ePoll.f444a, c0710ePoll.f445b);
                                    }
                                } else {
                                    this.f442h.set(false);
/* 360 */                           return;
                                }
                            } catch (Throwable th) {
                                this.f442h.set(false);
                                throw th;
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 371 */       private static void m338a(Object obj, C0712g c0712g) {
                    try {
/* 372 */               c0712g.m342a(obj);
                    } catch (InvocationTargetException e) {
/* 374 */               m339a("Could not dispatch event: " + obj.getClass() + " to handler " + c0712g, e);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 397 */       private Set<C0712g> m335a(Class<?> cls) {
/* 398 */           return this.f436b.get(cls);
                }

                /* JADX INFO: renamed from: a */
/* 441 */       private static void m339a(String str, InvocationTargetException invocationTargetException) {
/* 442 */           Throwable cause = invocationTargetException.getCause();
/* 443 */           if (cause != null) {
/* 444 */               throw new RuntimeException(str, cause);
                    }
/* 446 */           throw new RuntimeException(str);
                }
            }
