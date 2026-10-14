            package com.google.android.gms.dynamic;

            import android.os.IBinder;
            import java.lang.reflect.Field;
            
            /* JADX INFO: renamed from: com.google.android.gms.dynamic.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class BinderC0905c<T> extends InterfaceC0904b.a {

                /* JADX INFO: renamed from: mh */
                private final T f1027mh;

                private BinderC0905c(T t) {
                    this.f1027mh = t;
                }

                /* JADX INFO: renamed from: b */
                public static <T> T m1165b(InterfaceC0904b interfaceC0904b) {
                    if (interfaceC0904b instanceof BinderC0905c) {
                        return ((BinderC0905c) interfaceC0904b).f1027mh;
                    }
                    IBinder iBinderAsBinder = interfaceC0904b.asBinder();
                    Field[] declaredFields = iBinderAsBinder.getClass().getDeclaredFields();
                    if (declaredFields.length != 1) {
                        throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
                    }
                    Field field = declaredFields[0];
                    if (field.isAccessible()) {
                        throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
                    }
                    field.setAccessible(true);
                    try {
                        return (T) field.get(iBinderAsBinder);
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException("Could not access the field in remoteBinder.", e);
                    } catch (IllegalArgumentException e2) {
                        throw new IllegalArgumentException("remoteBinder is the wrong class.", e2);
                    } catch (NullPointerException e3) {
                        throw new IllegalArgumentException("Binder object is null.", e3);
                    }
                }

                /* JADX INFO: renamed from: g */
                public static <T> InterfaceC0904b m1166g(T t) {
                    return new BinderC0905c(t);
                }
            }
