            package android.support.v4.app;

            import android.os.Bundle;
            import android.support.v4.content.Loader;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 175 */   public abstract class LoaderManager {

                public interface LoaderCallbacks<D> {
                    Loader<D> onCreateLoader(int i, Bundle bundle);

                    void onLoadFinished(Loader<D> loader, D d);

                    void onLoaderReset(Loader<D> loader);
                }

                public abstract void destroyLoader(int i);

                public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

                public abstract <D> Loader<D> getLoader(int i);

                public abstract <D> Loader<D> initLoader(int i, Bundle bundle, LoaderCallbacks<D> loaderCallbacks);

                public abstract <D> Loader<D> restartLoader(int i, Bundle bundle, LoaderCallbacks<D> loaderCallbacks);

/* 176 */       public static void enableDebugLogging(boolean enabled) {
/* 177 */           LoaderManagerImpl.DEBUG = enabled;
                }

/* 183 */       public boolean hasRunningLoaders() {
/* 184 */           return false;
                }
            }
