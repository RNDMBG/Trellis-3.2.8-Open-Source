            package com.google.android.gms.internal;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cg */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class AbstractC0995cg {

                /* JADX INFO: renamed from: el */
                private final Runnable f1581el = new AnonymousClass1();

                /* JADX INFO: renamed from: hD */
                private volatile Thread f1582hD;

                /* JADX INFO: renamed from: com.google.android.gms.internal.cg$1, reason: invalid class name */
                class AnonymousClass1 implements Runnable {
                    AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        AbstractC0995cg.m1409a(AbstractC0995cg.this, Thread.currentThread());
                        AbstractC0995cg.this.mo1358ac();
                    }
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Thread m1409a(AbstractC0995cg abstractC0995cg, Thread thread) {
                    abstractC0995cg.f1582hD = thread;
                    return thread;
                }

                /* JADX INFO: renamed from: ac */
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
