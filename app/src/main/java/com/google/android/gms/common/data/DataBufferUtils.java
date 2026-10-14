            package com.google.android.gms.common.data;

            import com.lifevibes.mediacoder.LVMediaCoderProperties;
            import java.util.ArrayList;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class DataBufferUtils {
                private DataBufferUtils() {
                }

                public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> dataBuffer) {
                    LVMediaCoderProperties.C12536 c12536 = (ArrayList<T>) new ArrayList(dataBuffer.getCount());
                    try {
                        Iterator<E> it = dataBuffer.iterator();
                        while (it.hasNext()) {
                            c12536.add(it.next().freeze());
                        }
                        dataBuffer.close();
                        return c12536;
                    } catch (Throwable th) {
                        dataBuffer.close();
                        throw th;
                    }
                }
            }
