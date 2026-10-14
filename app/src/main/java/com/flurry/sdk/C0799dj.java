            package com.flurry.sdk;

            import java.util.Timer;
            import java.util.TimerTask;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dj */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    class C0799dj {

                /* JADX INFO: renamed from: a */
                private Timer f681a;

                /* JADX INFO: renamed from: b */
                private a f682b;

                /* JADX INFO: renamed from: c */
                private b f683c;

                /* JADX INFO: renamed from: com.flurry.sdk.dj$b */
                public interface b {
                    /* JADX INFO: renamed from: q */
                    void mo695q();
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dj$a */
/* 20 */        class a extends TimerTask {

                    /* JADX INFO: renamed from: b */
                    private b f685b;

/* 21 */            a(b bVar) {
/* 23 */                this.f685b = bVar;
                    }

                    @Override // java.util.TimerTask, java.lang.Runnable
/* 27 */            public void run() {
/* 28 */                C0799dj.this.m717a();
/* 30 */                if (this.f685b != null) {
/* 31 */                    this.f685b.mo695q();
                        }
                    }
                }

/* 39 */        C0799dj(b bVar) {
/* 41 */            this.f683c = bVar;
                }

                /* JADX INFO: renamed from: a */
/* 44 */        public synchronized void m718a(long j) {
                    try {
                        if (m719b()) {
/* 46 */                    m717a();
                        }
/* 49 */                this.f681a = new Timer("FlurrySessionTimer");
/* 50 */                this.f682b = new a(this.f683c);
/* 51 */                this.f681a.schedule(this.f682b, j);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 54 */        public synchronized void m717a() {
                    try {
                        if (this.f681a != null) {
/* 56 */                    this.f681a.cancel();
/* 57 */                    this.f681a = null;
                        }
/* 60 */                this.f682b = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 63 */        public boolean m719b() {
                    return this.f681a != null;
                }
            }
