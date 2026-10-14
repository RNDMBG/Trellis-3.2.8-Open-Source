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
            
            /* JADX INFO: renamed from: com.flurry.sdk.ed */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class C0819ed<T extends AbstractC0845fd> {

                /* JADX INFO: renamed from: a */
                private static final String f763a = C0819ed.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private final C0805dp<Object, T> f764b = new C0805dp<>();

                /* JADX INFO: renamed from: c */
                private final HashMap<T, Object> f765c = new HashMap<>();

                /* JADX INFO: renamed from: d */
                private final HashMap<T, Future<?>> f766d = new HashMap<>();

                /* JADX INFO: renamed from: e */
                private final ThreadPoolExecutor f767e;

                /* JADX INFO: renamed from: a */
/* 24 */        static /* synthetic */ AbstractC0845fd m827a(C0819ed c0819ed, Runnable runnable) {
/* 25 */            return c0819ed.m828a(runnable);
                }

                /* JADX INFO: renamed from: a */
/* 24 */        static /* synthetic */ HashMap m829a(C0819ed c0819ed) {
/* 25 */            return c0819ed.f766d;
                }

                /* JADX INFO: renamed from: a */
/* 24 */        static /* synthetic */ void m830a(C0819ed c0819ed, AbstractC0845fd abstractC0845fd) {
/* 25 */            c0819ed.m831b(abstractC0845fd);
                }

/* 47 */        public C0819ed(String str, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
/* 49 */            this.f767e = new AnonymousClass1(i, i2, j, timeUnit, blockingQueue);
/* 108 */           this.f767e.setRejectedExecutionHandler(new AnonymousClass2());
/* 133 */           this.f767e.setThreadFactory(new ThreadFactoryC0838ew(str, 1));
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ed$1, reason: invalid class name */
/* 48 */        class AnonymousClass1 extends ThreadPoolExecutor {
/* 49 */            AnonymousClass1(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue blockingQueue) {
                        super(i, i2, j, timeUnit, blockingQueue);
                    }

                    @Override // java.util.concurrent.ThreadPoolExecutor
/* 53 */            protected void beforeExecute(Thread thread, Runnable runnable) {
/* 54 */                super.beforeExecute(thread, runnable);
/* 56 */                AbstractC0845fd abstractC0845fdM827a = C0819ed.m827a(C0819ed.this, runnable);
/* 57 */                if (abstractC0845fdM827a != null) {
/* 61 */                    new C13141(abstractC0845fdM827a).run();
                        }
                    }

                    /* JADX INFO: renamed from: com.flurry.sdk.ed$1$1, reason: invalid class name and collision with other inner class name */
/* 59 */            class C13141 extends AbstractRunnableC0844fc {

                        /* JADX INFO: renamed from: a */
                        final /* synthetic */ AbstractC0845fd f769a;

/* 60 */                C13141(AbstractC0845fd abstractC0845fd) {
                            this.f769a = abstractC0845fd;
                        }

                        @Override // com.flurry.sdk.AbstractRunnableC0844fc
                        /* JADX INFO: renamed from: a */
/* 63 */                public void mo576a() {
/* 64 */                    this.f769a.m974j();
                        }
                    }

                    @Override // java.util.concurrent.ThreadPoolExecutor
/* 70 */            protected void afterExecute(Runnable runnable, Throwable th) {
/* 71 */                super.afterExecute(runnable, th);
/* 73 */                AbstractC0845fd abstractC0845fdM827a = C0819ed.m827a(C0819ed.this, runnable);
/* 74 */                if (abstractC0845fdM827a != null) {
                            synchronized (C0819ed.m829a(C0819ed.this)) {
                                try {
/* 79 */                            C0819ed.m829a(C0819ed.this).remove(abstractC0845fdM827a);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
/* 81 */                    C0819ed.m830a(C0819ed.this, abstractC0845fdM827a);
/* 83 */                    new AnonymousClass2(abstractC0845fdM827a).run();
                        }
                    }

                    /* JADX INFO: renamed from: com.flurry.sdk.ed$1$2, reason: invalid class name */
/* 81 */            class AnonymousClass2 extends AbstractRunnableC0844fc {

                        /* JADX INFO: renamed from: a */
                        final /* synthetic */ AbstractC0845fd f771a;

/* 82 */                AnonymousClass2(AbstractC0845fd abstractC0845fd) {
                            this.f771a = abstractC0845fd;
                        }

                        @Override // com.flurry.sdk.AbstractRunnableC0844fc
                        /* JADX INFO: renamed from: a */
/* 85 */                public void mo576a() {
/* 86 */                    this.f771a.m975k();
                        }
                    }

                    @Override // java.util.concurrent.AbstractExecutorService
/* 92 */            protected <V> RunnableFuture<V> newTaskFor(Callable<V> callable) {
/* 93 */                throw new UnsupportedOperationException("Callable not supported");
                    }

                    @Override // java.util.concurrent.AbstractExecutorService
/* 97 */            protected <V> RunnableFuture<V> newTaskFor(Runnable runnable, V v) {
/* 98 */                C0818ec c0818ec = new C0818ec(runnable, v);
                        synchronized (C0819ed.m829a(C0819ed.this)) {
                            try {
/* 101 */                       C0819ed.m829a(C0819ed.this).put((AbstractC0845fd) runnable, c0818ec);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 104 */               return c0818ec;
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ed$2, reason: invalid class name */
/* 106 */       class AnonymousClass2 extends ThreadPoolExecutor.DiscardPolicy {
/* 107 */           AnonymousClass2() {
                    }

                    @Override // java.util.concurrent.ThreadPoolExecutor.DiscardPolicy, java.util.concurrent.RejectedExecutionHandler
/* 110 */           public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
/* 111 */               super.rejectedExecution(runnable, threadPoolExecutor);
/* 113 */               AbstractC0845fd abstractC0845fdM827a = C0819ed.m827a(C0819ed.this, runnable);
/* 114 */               if (abstractC0845fdM827a != null) {
                            synchronized (C0819ed.m829a(C0819ed.this)) {
                                try {
/* 119 */                           C0819ed.m829a(C0819ed.this).remove(abstractC0845fdM827a);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 121 */                   C0819ed.m830a(C0819ed.this, abstractC0845fdM827a);
/* 123 */                   new AnonymousClass1(abstractC0845fdM827a).run();
                        }
                    }

                    /* JADX INFO: renamed from: com.flurry.sdk.ed$2$1, reason: invalid class name */
/* 121 */           class AnonymousClass1 extends AbstractRunnableC0844fc {

                        /* JADX INFO: renamed from: a */
                        final /* synthetic */ AbstractC0845fd f774a;

/* 122 */               AnonymousClass1(AbstractC0845fd abstractC0845fd) {
                            this.f774a = abstractC0845fd;
                        }

                        @Override // com.flurry.sdk.AbstractRunnableC0844fc
                        /* JADX INFO: renamed from: a */
/* 125 */               public void mo576a() {
/* 126 */                   this.f774a.m976l();
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 136 */       public synchronized void m836a(Object obj, T t) {
                    if (obj != null && t != null) {
                        try {
/* 141 */                   m832b(obj, t);
/* 142 */                   this.f767e.submit(t);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 145 */       public synchronized void m835a(Object obj) {
                    if (obj != null) {
                        try {
/* 150 */                   HashSet hashSet = new HashSet();
/* 151 */                   hashSet.addAll(this.f764b.m751a(obj));
/* 152 */                   Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
/* 153 */                       m834a((AbstractC0845fd) it.next());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 157 */       public synchronized void m834a(T t) {
                    Future<?> futureRemove;
                    if (t != null) {
                        try {
                            synchronized (this.f766d) {
                                try {
/* 164 */                           futureRemove = this.f766d.remove(t);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 166 */                   m831b((AbstractC0845fd) t);
/* 168 */                   if (futureRemove != null) {
/* 169 */                       futureRemove.cancel(true);
                            }
/* 172 */                   new AnonymousClass3(t).run();
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ed$3, reason: invalid class name */
/* 170 */       class AnonymousClass3 extends AbstractRunnableC0844fc {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ AbstractC0845fd f776a;

/* 171 */           AnonymousClass3(AbstractC0845fd abstractC0845fd) {
                        this.f776a = abstractC0845fd;
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 174 */           public void mo576a() {
/* 175 */               this.f776a.mo881h();
                    }
                }

                /* JADX INFO: renamed from: b */
/* 204 */       public synchronized long m837b(Object obj) {
                    long size;
                    if (obj != null) {
                        try {
                            size = this.f764b.m751a(obj).size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else {
/* 206 */               size = 0;
                    }
                    return size;
                }

                /* JADX INFO: renamed from: b */
/* 220 */       private synchronized void m832b(Object obj, T t) {
                    try {
                        this.f764b.m754a(obj, t);
/* 222 */               this.f765c.put(t, obj);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 225 */       private synchronized void m831b(T t) {
                    try {
                        m833c(this.f765c.get(t), t);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 229 */       private synchronized void m833c(Object obj, T t) {
                    try {
                        this.f764b.m757b(obj, t);
/* 231 */               this.f765c.remove(t);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 235 */       private T m828a(Runnable runnable) {
/* 238 */           if (runnable instanceof C0818ec) {
/* 240 */               return (T) ((C0818ec) runnable).m826a();
                    }
/* 241 */           if (runnable instanceof AbstractC0845fd) {
/* 242 */               return (T) runnable;
                    }
/* 244 */           C0827el.m885a(6, f763a, "Unknown runnable class: " + runnable.getClass().getName());
/* 236 */           return null;
                }
            }
