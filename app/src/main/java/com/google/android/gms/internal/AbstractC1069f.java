            package com.google.android.gms.internal;

            import android.content.Context;
            import android.util.DisplayMetrics;
            import android.view.MotionEvent;
            import com.vandalsoftware.p004io.IoUtils;
            import dalvik.system.DexClassLoader;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.UnsupportedEncodingException;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            
            
            
            public abstract class AbstractC1069f extends AbstractC1042e {

                
                private static Method f1881dk;

                
                private static Method f1882dl;

                
                private static Method f1883dm;

                
                private static Method f1884dn;

                
                private static Method f1885do;

                
                private static Method f1886dp;

                
                private static String f1887dq;

                
                private static C1114l f1888dr;
                private static long startTime = 0;

                
                static boolean f1889ds = false;

                
                static class a extends Exception {
                    public a() {
                    }

                    public a(Throwable th) {
                        super(th);
                    }
                }

                protected AbstractC1069f(Context context, InterfaceC1112j interfaceC1112j, InterfaceC1113k interfaceC1113k) {
                    super(context, interfaceC1112j, interfaceC1113k);
                }

                
                static String m1916a(Context context, InterfaceC1112j interfaceC1112j) throws a {
                    if (f1883dm == null) {
                        throw new a();
                    }
                    try {
                        ByteBuffer byteBuffer = (ByteBuffer) f1883dm.invoke(null, context);
                        if (byteBuffer == null) {
                            throw new a();
                        }
                        return interfaceC1112j.mo1236a(byteBuffer.array(), true);
                    } catch (IllegalAccessException e) {
                        throw new a(e);
                    } catch (InvocationTargetException e2) {
                        throw new a(e2);
                    }
                }

                
                static ArrayList<Long> m1917a(MotionEvent motionEvent, DisplayMetrics displayMetrics) throws a {
                    if (f1884dn == null || motionEvent == null) {
                        throw new a();
                    }
                    try {
                        return (ArrayList) f1884dn.invoke(null, motionEvent, displayMetrics);
                    } catch (IllegalAccessException e) {
                        throw new a(e);
                    } catch (InvocationTargetException e2) {
                        throw new a(e2);
                    }
                }

                
                protected static synchronized void m1918a(String str, Context context, InterfaceC1112j interfaceC1112j) {
                    try {
                        if (!f1889ds) {
                            try {
                                f1888dr = new C1114l(interfaceC1112j, null);
                                f1887dq = str;
                                m1924e(context);
                                startTime = m1923e().longValue();
                                f1889ds = true;
                            } catch (a e) {
                            } catch (UnsupportedOperationException e2) {
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
                static String m1919b(Context context, InterfaceC1112j interfaceC1112j) throws a {
                    if (f1886dp == null) {
                        throw new a();
                    }
                    try {
                        ByteBuffer byteBuffer = (ByteBuffer) f1886dp.invoke(null, context);
                        if (byteBuffer == null) {
                            throw new a();
                        }
                        return interfaceC1112j.mo1236a(byteBuffer.array(), true);
                    } catch (IllegalAccessException e) {
                        throw new a(e);
                    } catch (InvocationTargetException e2) {
                        throw new a(e2);
                    }
                }

                
                private static String m1920b(byte[] bArr, String str) throws a {
                    try {
                        return new String(f1888dr.m2134c(bArr, str), IoUtils.UTF_8);
                    } catch (C1114l.a e) {
                        throw new a(e);
                    } catch (UnsupportedEncodingException e2) {
                        throw new a(e2);
                    }
                }

                
                static String m1921d() throws a {
                    if (f1887dq == null) {
                        throw new a();
                    }
                    return f1887dq;
                }

                
                static String m1922d(Context context) throws a {
                    if (f1885do == null) {
                        throw new a();
                    }
                    try {
                        String str = (String) f1885do.invoke(null, context);
                        if (str == null) {
                            throw new a();
                        }
                        return str;
                    } catch (IllegalAccessException e) {
                        throw new a(e);
                    } catch (InvocationTargetException e2) {
                        throw new a(e2);
                    }
                }

                
                static Long m1923e() throws a {
                    if (f1881dk == null) {
                        throw new a();
                    }
                    try {
                        return (Long) f1881dk.invoke(null, new Object[0]);
                    } catch (IllegalAccessException e) {
                        throw new a(e);
                    } catch (InvocationTargetException e2) {
                        throw new a(e2);
                    }
                }

                
                private static void m1924e(Context context) throws a {
                    try {
                        byte[] bArrM2133b = f1888dr.m2133b(C1116n.getKey());
                        byte[] bArrM2134c = f1888dr.m2134c(bArrM2133b, C1116n.m2135i());
                        File cacheDir = context.getCacheDir();
                        if (cacheDir == null && (cacheDir = context.getDir("dex", 0)) == null) {
                            throw new a();
                        }
                        File fileCreateTempFile = File.createTempFile("ads", ".jar", cacheDir);
                        FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
                        fileOutputStream.write(bArrM2134c, 0, bArrM2134c.length);
                        fileOutputStream.close();
                        DexClassLoader dexClassLoader = new DexClassLoader(fileCreateTempFile.getAbsolutePath(), cacheDir.getAbsolutePath(), null, context.getClassLoader());
                        Class clsLoadClass = dexClassLoader.loadClass(m1920b(bArrM2133b, C1116n.m2136j()));
                        Class clsLoadClass2 = dexClassLoader.loadClass(m1920b(bArrM2133b, C1116n.m2142p()));
                        Class clsLoadClass3 = dexClassLoader.loadClass(m1920b(bArrM2133b, C1116n.m2140n()));
                        Class clsLoadClass4 = dexClassLoader.loadClass(m1920b(bArrM2133b, C1116n.m2146t()));
                        Class clsLoadClass5 = dexClassLoader.loadClass(m1920b(bArrM2133b, C1116n.m2138l()));
                        Class clsLoadClass6 = dexClassLoader.loadClass(m1920b(bArrM2133b, C1116n.m2144r()));
                        f1881dk = clsLoadClass.getMethod(m1920b(bArrM2133b, C1116n.m2137k()), new Class[0]);
                        f1882dl = clsLoadClass2.getMethod(m1920b(bArrM2133b, C1116n.m2143q()), new Class[0]);
                        f1883dm = clsLoadClass3.getMethod(m1920b(bArrM2133b, C1116n.m2141o()), Context.class);
                        f1884dn = clsLoadClass4.getMethod(m1920b(bArrM2133b, C1116n.m2147u()), MotionEvent.class, DisplayMetrics.class);
                        f1885do = clsLoadClass5.getMethod(m1920b(bArrM2133b, C1116n.m2139m()), Context.class);
                        f1886dp = clsLoadClass6.getMethod(m1920b(bArrM2133b, C1116n.m2145s()), Context.class);
                        String name = fileCreateTempFile.getName();
                        fileCreateTempFile.delete();
                        new File(cacheDir, name.replace(".jar", ".dex")).delete();
                    } catch (C1114l.a e) {
                        throw new a(e);
                    } catch (FileNotFoundException e2) {
                        throw new a(e2);
                    } catch (IOException e3) {
                        throw new a(e3);
                    } catch (ClassNotFoundException e4) {
                        throw new a(e4);
                    } catch (NoSuchMethodException e5) {
                        throw new a(e5);
                    } catch (NullPointerException e6) {
                        throw new a(e6);
                    }
                }

                
                static String m1925f() throws a {
                    if (f1882dl == null) {
                        throw new a();
                    }
                    try {
                        return (String) f1882dl.invoke(null, new Object[0]);
                    } catch (IllegalAccessException e) {
                        throw new a(e);
                    } catch (InvocationTargetException e2) {
                        throw new a(e2);
                    }
                }

                @Override 
                
                protected void mo1697b(Context context) {
                    try {
                        try {
                            m1695a(1, m1925f());
                        } catch (IOException e) {
                            return;
                        }
                    } catch (a e2) {
                    }
                    try {
                        m1695a(2, m1921d());
                    } catch (a e3) {
                    }
                    try {
                        m1694a(25, m1923e().longValue());
                    } catch (a e4) {
                    }
                    try {
                        m1695a(24, m1922d(context));
                    } catch (a e5) {
                    }
                }

                @Override 
                
                protected void mo1698c(Context context) {
                    try {
                        try {
                            m1695a(2, m1921d());
                        } catch (a e) {
                        }
                        try {
                            m1695a(1, m1925f());
                        } catch (a e2) {
                        }
                        try {
                            long jLongValue = m1923e().longValue();
                            m1694a(25, jLongValue);
                            if (startTime != 0) {
                                m1694a(17, jLongValue - startTime);
                                m1694a(23, startTime);
                            }
                        } catch (a e3) {
                        }
                        try {
                            ArrayList<Long> arrayListM1917a = m1917a(this.f1753dg, this.f1754dh);
                            m1694a(14, arrayListM1917a.get(0).longValue());
                            m1694a(15, arrayListM1917a.get(1).longValue());
                            if (arrayListM1917a.size() >= 3) {
                                m1694a(16, arrayListM1917a.get(2).longValue());
                            }
                        } catch (a e4) {
                        }
                        try {
                            m1695a(27, m1916a(context, this.f1755di));
                        } catch (a e5) {
                        }
                        try {
                            m1695a(29, m1919b(context, this.f1755di));
                        } catch (a e6) {
                        }
                    } catch (IOException e7) {
                    }
                }
            }
