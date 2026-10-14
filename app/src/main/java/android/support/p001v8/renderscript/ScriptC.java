            package android.support.p001v8.renderscript;

            import android.content.res.Resources;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class ScriptC extends Script {
                private static final String TAG = "ScriptC";

/* 45 */        protected ScriptC(int id, RenderScript rs) {
/* 46 */            super(id, rs);
                }

/* 57 */        protected ScriptC(RenderScript rs, Resources resources, int resourceID) {
/* 58 */            super(0, rs);
/* 60 */            if (RenderScript.isNative) {
/* 61 */                RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 62 */                ScriptCThunker s = new ScriptCThunker(rst, resources, resourceID);
/* 63 */                this.f56mT = s;
                    } else {
/* 67 */                int id = internalCreate(rs, resources, resourceID);
/* 68 */                if (id == 0) {
/* 69 */                    throw new RSRuntimeException("Loading of ScriptC script failed.");
                        }
/* 71 */                setID(id);
                    }
                }

/* 77 */        private static synchronized int internalCreate(RenderScript rs, Resources resources, int resourceID) {
                    byte[] pgm;
                    int pgmLength;
                    String resName;
                    String cachePath;
                    try {
                        InputStream is = resources.openRawResource(resourceID);
                        try {
                            try {
/* 81 */                        pgm = new byte[1024];
/* 82 */                        pgmLength = 0;
                                while (true) {
/* 84 */                            int bytesLeft = pgm.length - pgmLength;
/* 85 */                            if (bytesLeft == 0) {
/* 86 */                                byte[] buf2 = new byte[pgm.length * 2];
/* 87 */                                System.arraycopy(pgm, 0, buf2, 0, pgm.length);
/* 88 */                                pgm = buf2;
/* 89 */                                bytesLeft = pgm.length - pgmLength;
                                    }
/* 91 */                            int bytesRead = is.read(pgm, pgmLength, bytesLeft);
/* 92 */                            if (bytesRead > 0) {
/* 95 */                                pgmLength += bytesRead;
                                    } else {
/* 98 */                                is.close();
/* 104 */                               resName = resources.getResourceEntryName(resourceID);
/* 105 */                               cachePath = rs.getApplicationContext().getCacheDir().toString();
                                    }
                                }
                            } catch (Throwable th) {
                                is.close();
                                throw th;
                            }
                        } catch (IOException e) {
/* 101 */                   throw new Resources.NotFoundException();
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
/* 109 */           return rs.nScriptCCreate(resName, cachePath, pgm, pgmLength);
                }
            }
