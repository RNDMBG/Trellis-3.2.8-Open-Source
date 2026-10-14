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
            
            
            
   public class C0706b {

                
                public static final String f435a = "default";

                
                private final ConcurrentMap<Class<?>, Set<C0712g>> f436b;

                
                private final ConcurrentMap<Class<?>, C0713h> f437c;

                
                private final String f438d;

                
                private final InterfaceC0718m f439e;

                
                private final InterfaceC0714i f440f;

                
                private final ThreadLocal<ConcurrentLinkedQueue<C0710e>> f441g;

                
                private final ThreadLocal<Boolean> f442h;

                
                private final Map<Class<?>, Set<Class<?>>> f443i;

       public C0706b() {
           this(f435a);
                }

       public C0706b(String str) {
           this(InterfaceC0718m.f458b, str);
                }

       public C0706b(InterfaceC0718m interfaceC0718m) {
           this(interfaceC0718m, f435a);
                }

       public C0706b(InterfaceC0718m interfaceC0718m, String str) {
           this(interfaceC0718m, str, InterfaceC0714i.f456a);
                }

       private C0706b(InterfaceC0718m interfaceC0718m, String str, InterfaceC0714i interfaceC0714i) {
            this.f436b = new ConcurrentHashMap();
            this.f437c = new ConcurrentHashMap();
           this.f441g = new C0708c(this);
           this.f442h = new C0709d(this);
           this.f443i = new HashMap();
           this.f439e = interfaceC0718m;
           this.f438d = str;
           this.f440f = interfaceC0714i;
                }

       public String toString() {
           return "[Bus \"" + this.f438d + "\"]";
                }

                
       public void m340a(Object obj) {
                    CopyOnWriteArraySet copyOnWriteArraySet;
           this.f439e.mo350a(this);
           Map<Class<?>, C0713h> mapMo348a = this.f440f.mo348a(obj);
                    for (Class<?> cls : mapMo348a.keySet()) {
               C0713h c0713h = mapMo348a.get(cls);
               C0713h c0713hPutIfAbsent = this.f437c.putIfAbsent(cls, c0713h);
               if (c0713hPutIfAbsent != null) {
                   throw new IllegalArgumentException("Producer method for type " + cls + " found on type " + c0713h.f452a.getClass() + ", but already registered by type " + c0713hPutIfAbsent.f452a.getClass() + ".");
                        }
               Set<C0712g> set = this.f436b.get(cls);
               if (set != null && !set.isEmpty()) {
                   Iterator<C0712g> it = set.iterator();
                            while (it.hasNext()) {
                       m337a(it.next(), c0713h);
                            }
                        }
                    }
           Map<Class<?>, Set<C0712g>> mapMo349b = this.f440f.mo349b(obj);
                    for (Class<?> cls2 : mapMo349b.keySet()) {
               Set<C0712g> setPutIfAbsent = this.f436b.get(cls2);
               if (setPutIfAbsent == null && (setPutIfAbsent = this.f436b.putIfAbsent(cls2, (copyOnWriteArraySet = new CopyOnWriteArraySet()))) == null) {
                            setPutIfAbsent = copyOnWriteArraySet;
                        }
               setPutIfAbsent.addAll(mapMo349b.get(cls2));
                    }
                    for (Map.Entry<Class<?>, Set<C0712g>> entry : mapMo349b.entrySet()) {
               C0713h c0713h2 = this.f437c.get(entry.getKey());
               if (c0713h2 != null && c0713h2.m345a()) {
                            for (C0712g c0712g : entry.getValue()) {
                       if (!c0713h2.m345a()) {
                                    break;
                                } else if (c0712g.m343a()) {
                           m337a(c0712g, c0713h2);
                                }
                            }
                        }
                    }
                }

                
       private void m337a(C0712g c0712g, C0713h c0713h) {
           Object objM347c = null;
                    try {
               objM347c = c0713h.m347c();
                    } catch (InvocationTargetException e) {
               m339a("Producer " + c0713h + " threw an exception.", e);
                    }
           if (objM347c != null) {
               m338a(objM347c, c0712g);
                    }
                }

                
       public void m341b(Object obj) {
           this.f439e.mo350a(this);
                    for (Map.Entry<Class<?>, C0713h> entry : this.f440f.mo348a(obj).entrySet()) {
               Class<?> key = entry.getKey();
               C0713h c0713h = this.f437c.get(key);
               C0713h value = entry.getValue();
               if (value == null || !value.equals(c0713h)) {
                   throw new IllegalArgumentException("Missing event producer for an annotated method. Is " + obj.getClass() + " registered?");
                        }
               this.f437c.remove(key).m346b();
                    }
                    for (Map.Entry<Class<?>, Set<C0712g>> entry2 : this.f440f.mo349b(obj).entrySet()) {
               Set<C0712g> setM335a = m335a(entry2.getKey());
               Set<C0712g> value2 = entry2.getValue();
               if (setM335a == null || !setM335a.containsAll(value2)) {
                   throw new IllegalArgumentException("Missing event handler for an annotated method. Is " + obj.getClass() + " registered?");
                        }
                        for (C0712g c0712g : setM335a) {
                   if (value2.contains(c0712g)) {
                       c0712g.m344b();
                            }
                        }
               setM335a.removeAll(value2);
                    }
                }

                
       public void mo102c(Object obj) {
                    boolean z;
           this.f439e.mo350a(this);
           Class<?> cls = obj.getClass();
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
           Iterator<Class<?>> it = set.iterator();
                    boolean z2 = false;
                    while (it.hasNext()) {
               Set<C0712g> setM335a = m335a(it.next());
               if (setM335a == null || setM335a.isEmpty()) {
                            z = z2;
                        } else {
                   Iterator<C0712g> it2 = setM335a.iterator();
                            while (it2.hasNext()) {
                       this.f441g.get().offer(new C0710e(obj, it2.next()));
                            }
                   z = true;
                        }
                        z2 = z;
                    }
           if (!z2 && !(obj instanceof C0711f)) {
               mo102c(new C0711f(this, obj));
                    }
           m336a();
                }

                
       private void m336a() {
           if (!this.f442h.get().booleanValue()) {
               this.f442h.set(true);
                        while (true) {
                            try {
                       C0710e c0710ePoll = this.f441g.get().poll();
                       if (c0710ePoll != null) {
                           if (c0710ePoll.f445b.m343a()) {
                               m338a(c0710ePoll.f444a, c0710ePoll.f445b);
                                    }
                                } else {
                                    this.f442h.set(false);
                           return;
                                }
                            } catch (Throwable th) {
                                this.f442h.set(false);
                                throw th;
                            }
                        }
                    }
                }

                
       private static void m338a(Object obj, C0712g c0712g) {
                    try {
               c0712g.m342a(obj);
                    } catch (InvocationTargetException e) {
               m339a("Could not dispatch event: " + obj.getClass() + " to handler " + c0712g, e);
                    }
                }

                
       private Set<C0712g> m335a(Class<?> cls) {
           return this.f436b.get(cls);
                }

                
       private static void m339a(String str, InvocationTargetException invocationTargetException) {
           Throwable cause = invocationTargetException.getCause();
           if (cause != null) {
               throw new RuntimeException(str, cause);
                    }
           throw new RuntimeException(str);
                }
            }
