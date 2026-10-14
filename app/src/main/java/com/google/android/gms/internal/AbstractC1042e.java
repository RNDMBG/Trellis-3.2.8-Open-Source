            package com.google.android.gms.internal;

            import android.content.Context;
            import android.util.DisplayMetrics;
            import android.view.MotionEvent;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.IOException;
            import java.io.UnsupportedEncodingException;
            import java.nio.ByteBuffer;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.security.SecureRandom;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.e */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class AbstractC1042e implements InterfaceC1015d {

                /* JADX INFO: renamed from: dg */
                protected MotionEvent f1753dg;

                /* JADX INFO: renamed from: dh */
                protected DisplayMetrics f1754dh;

                /* JADX INFO: renamed from: di */
                protected InterfaceC1112j f1755di;

                /* JADX INFO: renamed from: dj */
                private InterfaceC1113k f1756dj;

                protected AbstractC1042e(Context context, InterfaceC1112j interfaceC1112j, InterfaceC1113k interfaceC1113k) {
                    this.f1755di = interfaceC1112j;
                    this.f1756dj = interfaceC1113k;
                    try {
                        this.f1754dh = context.getResources().getDisplayMetrics();
                    } catch (UnsupportedOperationException e) {
                        this.f1754dh = new DisplayMetrics();
                        this.f1754dh.density = 1.0f;
                    }
                }

                /* JADX INFO: renamed from: a */
                private String m1690a(Context context, String str, boolean z) {
                    byte[] bArrM1692c;
                    try {
                        synchronized (this) {
                            try {
                                m1691b();
                                if (z) {
                                    mo1698c(context);
                                } else {
                                    mo1697b(context);
                                }
                                bArrM1692c = m1692c();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return bArrM1692c.length == 0 ? Integer.toString(5) : m1693a(bArrM1692c, str);
                    } catch (UnsupportedEncodingException e) {
                        return Integer.toString(7);
                    } catch (IOException e2) {
                        return Integer.toString(3);
                    } catch (NoSuchAlgorithmException e3) {
                        return Integer.toString(7);
                    }
                }

                /* JADX INFO: renamed from: b */
                private void m1691b() {
                    this.f1756dj.reset();
                }

                /* JADX INFO: renamed from: c */
                private byte[] m1692c() throws IOException {
                    return this.f1756dj.mo2131h();
                }

                @Override // com.google.android.gms.internal.InterfaceC1015d
                /* JADX INFO: renamed from: a */
                public String mo1516a(Context context) {
                    return m1690a(context, (String) null, false);
                }

                @Override // com.google.android.gms.internal.InterfaceC1015d
                /* JADX INFO: renamed from: a */
                public String mo1517a(Context context, String str) {
                    return m1690a(context, str, true);
                }

                /* JADX INFO: renamed from: a */
                String m1693a(byte[] bArr, String str) throws NoSuchAlgorithmException, IOException {
                    byte[] bArrArray;
                    if (bArr.length > 239) {
                        m1691b();
                        m1694a(20, 1L);
                        bArr = m1692c();
                    }
                    if (bArr.length < 239) {
                        byte[] bArr2 = new byte[239 - bArr.length];
                        new SecureRandom().nextBytes(bArr2);
                        bArrArray = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).put(bArr2).array();
                    } else {
                        bArrArray = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).array();
                    }
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(bArrArray);
                    byte[] bArrArray2 = ByteBuffer.allocate(256).put(messageDigest.digest()).put(bArrArray).array();
                    byte[] bArr3 = new byte[256];
                    new C0961b().m1287a(bArrArray2, bArr3);
                    if (str != null && str.length() > 0) {
                        m1696a(str, bArr3);
                    }
                    return this.f1755di.mo1236a(bArr3, true);
                }

                @Override // com.google.android.gms.internal.InterfaceC1015d
                /* JADX INFO: renamed from: a */
                public void mo1518a(int i, int i2, int i3) {
                    if (this.f1753dg != null) {
                        this.f1753dg.recycle();
                    }
                    this.f1753dg = MotionEvent.obtain(0L, i3, 1, i * this.f1754dh.density, i2 * this.f1754dh.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
                }

                /* JADX INFO: renamed from: a */
                protected void m1694a(int i, long j) throws IOException {
                    this.f1756dj.mo2129b(i, j);
                }

                /* JADX INFO: renamed from: a */
                protected void m1695a(int i, String str) throws IOException {
                    this.f1756dj.mo2130b(i, str);
                }

                @Override // com.google.android.gms.internal.InterfaceC1015d
                /* JADX INFO: renamed from: a */
                public void mo1519a(MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 1) {
                        if (this.f1753dg != null) {
                            this.f1753dg.recycle();
                        }
                        this.f1753dg = MotionEvent.obtain(motionEvent);
                    }
                }

                /* JADX INFO: renamed from: a */
                void m1696a(String str, byte[] bArr) throws UnsupportedEncodingException {
                    if (str.length() > 32) {
                        str = str.substring(0, 32);
                    }
                    new C1107gk(str.getBytes(IoUtils.UTF_8)).m2107f(bArr);
                }

                /* JADX INFO: renamed from: b */
                protected abstract void mo1697b(Context context);

                /* JADX INFO: renamed from: c */
                protected abstract void mo1698c(Context context);
            }
