            package com.google.android.gms.internal;
            
            
            
            public abstract class AbstractC0995cg {

                
                private final Runnable f1581el = new AnonymousClass1();

                
                private volatile Thread f1582hD;

                
                class AnonymousClass1 implements Runnable {
                    AnonymousClass1() {
                    }

                    @Override 
                    public final void run() {
                        AbstractC0995cg.m1409a(AbstractC0995cg.this, Thread.currentThread());
                        AbstractC0995cg.this.mo1358ac();
                    }
                }

                
                static  Thread m1409a(AbstractC0995cg abstractC0995cg, Thread thread) {
                    abstractC0995cg.f1582hD = thread;
                    return thread;
                }

                
                public abstract void mo1358ac();

                public final void cancel() {
                    onStop();
                    if (this.f1582hD != null) {
                        this.f1582hD.interrupt();
                    }
                }

                public abstract void onStop();

                public final void start() {
                    C0996ch.execute(this.f1581el);
                }
            }
