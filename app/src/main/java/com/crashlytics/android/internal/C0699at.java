            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.at */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 441 */   final class C0699at extends InputStream {

                /* JADX INFO: renamed from: a */
                private int f410a;

                /* JADX INFO: renamed from: b */
                private int f411b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ C0696aq f412c;

/* 442 */       /* synthetic */ C0699at(C0696aq c0696aq, C0698as c0698as, byte b) {
/* 443 */           this(c0696aq, c0698as);
                }

/* 446 */       private C0699at(C0696aq c0696aq, C0698as c0698as) {
/* 447 */           this.f412c = c0696aq;
/* 448 */           this.f410a = C0696aq.m266a(c0696aq, c0698as.f408b + 4);
/* 449 */           this.f411b = c0698as.f409c;
                }

                @Override // java.io.InputStream
/* 453 */       public final int read(byte[] buffer, int offset, int length) throws IOException {
/* 454 */           C0696aq.m271a(buffer, "buffer");
/* 455 */           if ((offset | length) < 0 || length > buffer.length - offset) {
/* 456 */               throw new ArrayIndexOutOfBoundsException();
                    }
/* 458 */           if (this.f411b <= 0) {
                        return -1;
                    }
/* 459 */           if (length > this.f411b) {
/* 460 */               length = this.f411b;
                    }
/* 462 */           C0696aq.m274a(this.f412c, this.f410a, buffer, offset, length);
/* 463 */           this.f410a = C0696aq.m266a(this.f412c, this.f410a + length);
/* 464 */           this.f411b -= length;
                    return length;
                }

                @Override // java.io.InputStream
/* 472 */       public final int read() throws IOException {
/* 473 */           if (this.f411b == 0) {
/* 474 */               return -1;
                    }
/* 476 */           C0696aq.m269a(this.f412c).seek(this.f410a);
/* 477 */           int i = C0696aq.m269a(this.f412c).read();
/* 478 */           this.f410a = C0696aq.m266a(this.f412c, this.f410a + 1);
/* 479 */           this.f411b--;
/* 480 */           return i;
                }
            }
