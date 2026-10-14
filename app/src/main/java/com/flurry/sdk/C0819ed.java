            package com.flurry.sdk;

            import com.flurry.sdk.AbstractC0845fd;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.concurrent.BlockingQueue;
            import java.util.concurrent.Callable;
            import java.util.concurrent.Future;
            import java.util.concurrent.RunnableFuture;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            
            
            
    public class C0819ed<T extends AbstractC0845fd> {

                
                private static final String f763a = C0819ed.class.getSimpleName();

                
                private final C0805dp<Object, T> f764b = new C0805dp<>();

                
                private final HashMap<T, Object> f765c = new HashMap<>();

                
                private final HashMap<T, Future<?>> f766d = new HashMap<>();

                
                private final ThreadPoolExecutor f767e;

                
        static  AbstractC0845fd m827a(C0819ed c0819ed, Runnable runnable) {
            return c0819ed.m828a(runnable);
                }

                
        static  HashMap m829a(C0819ed c0819ed) {
            return c0819ed.f766d;
                }

                
        static  void m830a(C0819ed c0819ed, AbstractC0845fd abstractC0845fd) {
            c0819ed.m831b(abstractC0845fd);
                }

        public C0819ed(String str, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
            this.f767e = new AnonymousClass1(i, i2, j, timeUnit, blockingQueue);
           this.f767e.setRejectedExecutionHandler(new AnonymousClass2());
           this.f767e.setThreadFactory(new ThreadFactoryC0838ew(str, 1));
                }

                
        class AnonymousClass1 extends ThreadPoolExecutor {
            AnonymousClass1(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue blockingQueue) {
                        super(i, i2, j, timeUnit, blockingQueue);
                    }

                    @Override 
            protected void beforeExecute(Thread thread, Runnable runnable) {
                super.beforeExecute(thread, runnable);
                AbstractC0845fd abstractC0845fdM827a = C0819ed.m827a(C0819ed.this, runnable);
                if (abstractC0845fdM827a != null) {
                    new C13141(abstractC0845fdM827a).run();
                        }
                    }

                    
            class C13141 extends AbstractRunnableC0844fc {

                        
                        final  AbstractC0845fd f769a;

                C13141(AbstractC0845fd abstractC0845fd) {
                            this.f769a = abstractC0845fd;
                        }

                        @Override 
                        
                public void mo576a() {
                    this.f769a.m974j();
                        }
                    }

                    @Override 
            protected void afterExecute(Runnable runnable, Throwable th) {
                super.afterExecute(runnable, th);
                AbstractC0845fd abstractC0845fdM827a = C0819ed.m827a(C0819ed.this, runnable);
                if (abstractC0845fdM827a != null) {
                            synchronized (C0819ed.m829a(C0819ed.this)) {
                                try {
                            C0819ed.m829a(C0819ed.this).remove(abstractC0845fdM827a);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                    C0819ed.m830a(C0819ed.this, abstractC0845fdM827a);
                    new AnonymousClass2(abstractC0845fdM827a).run();
                        }
                    }

                    
            class AnonymousClass2 extends AbstractRunnableC0844fc {

                        
                        final  AbstractC0845fd f771a;

                AnonymousClass2(AbstractC0845fd abstractC0845fd) {
                            this.f771a = abstractC0845fd;
                        }

                        @Override 
                        
                public void mo576a() {
                    this.f771a.m975k();
                        }
                    }

                    @Override 
            protected <V> RunnableFuture<V> newTaskFor(Callable<V> callable) {
                throw new UnsupportedOperationException("Callable not supported");
                    }

                    @Override 
            protected <V> RunnableFuture<V> newTaskFor(Runnable runnable, V v) {
                C0818ec c0818ec = new C0818ec(runnable, v);
                        synchronized (C0819ed.m829a(C0819ed.this)) {
                            try {
                       C0819ed.m829a(C0819ed.this).put((AbstractC0845fd) runnable, c0818ec);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return c0818ec;
                    }
                }

                
       class AnonymousClass2 extends ThreadPoolExecutor.DiscardPolicy {
           AnonymousClass2() {
                    }

                    @Override 
           public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
               super.rejectedExecution(runnable, threadPoolExecutor);
               AbstractC0845fd abstractC0845fdM827a = C0819ed.m827a(C0819ed.this, runnable);
               if (abstractC0845fdM827a != null) {
                            synchronized (C0819ed.m829a(C0819ed.this)) {
                                try {
                           C0819ed.m829a(C0819ed.this).remove(abstractC0845fdM827a);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   C0819ed.m830a(C0819ed.this, abstractC0845fdM827a);
                   new AnonymousClass1(abstractC0845fdM827a).run();
                        }
                    }

                    
           class AnonymousClass1 extends AbstractRunnableC0844fc {

                        
                        final  AbstractC0845fd f774a;

               AnonymousClass1(AbstractC0845fd abstractC0845fd) {
                            this.f774a = abstractC0845fd;
                        }

                        @Override 
                        
               public void mo576a() {
                   this.f774a.m976l();
                        }
                    }
                }

                
       public synchronized void m836a(Object obj, T t) {
                    if (obj != null && t != null) {
                        try {
                   m832b(obj, t);
                   this.f767e.submit(t);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       public synchronized void m835a(Object obj) {
                    if (obj != null) {
                        try {
                   HashSet hashSet = new HashSet();
                   hashSet.addAll(this.f764b.m751a(obj));
                   Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
                       m834a((AbstractC0845fd) it.next());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       public synchronized void m834a(T t) {
                    Future<?> futureRemove;
                    if (t != null) {
                        try {
                            synchronized (this.f766d) {
                                try {
                           futureRemove = this.f766d.remove(t);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   m831b((AbstractC0845fd) t);
                   if (futureRemove != null) {
                       futureRemove.cancel(true);
                            }
                   new AnonymousClass3(t).run();
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

                
       class AnonymousClass3 extends AbstractRunnableC0844fc {

                    
                    final  AbstractC0845fd f776a;

           AnonymousClass3(AbstractC0845fd abstractC0845fd) {
                        this.f776a = abstractC0845fd;
                    }

                    @Override 
                    
           public void mo576a() {
               this.f776a.mo881h();
                    }
                }

                
       public synchronized long m837b(Object obj) {
                    long size;
                    if (obj != null) {
                        try {
                            size = this.f764b.m751a(obj).size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else {
               size = 0;
                    }
                    return size;
                }

                
       private synchronized void m832b(Object obj, T t) {
                    try {
                        this.f764b.m754a(obj, t);
               this.f765c.put(t, obj);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       private synchronized void m831b(T t) {
                    try {
                        m833c(this.f765c.get(t), t);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       private synchronized void m833c(Object obj, T t) {
                    try {
                        this.f764b.m757b(obj, t);
               this.f765c.remove(t);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       private T m828a(Runnable runnable) {
           if (runnable instanceof C0818ec) {
               return (T) ((C0818ec) runnable).m826a();
                    }
           if (runnable instanceof AbstractC0845fd) {
               return (T) runnable;
                    }
           C0827el.m885a(6, f763a, "Unknown runnable class: " + runnable.getClass().getName());
           return null;
                }
            }
