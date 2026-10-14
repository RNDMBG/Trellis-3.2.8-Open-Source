            package android.support.p001v8.renderscript;

            import android.content.res.Resources;
            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class ScriptC extends Script {
                private static final String TAG = "ScriptC";

        protected ScriptC(int id, RenderScript rs) {
            super(id, rs);
                }

        protected ScriptC(RenderScript rs, Resources resources, int resourceID) {
            super(0, rs);
            if (RenderScript.isNative) {
                RenderScriptThunker rst = (RenderScriptThunker) rs;
                ScriptCThunker s = new ScriptCThunker(rst, resources, resourceID);
                this.f56mT = s;
                    } else {
                int id = internalCreate(rs, resources, resourceID);
                if (id == 0) {
                    throw new RSRuntimeException("Loading of ScriptC script failed.");
                        }
                setID(id);
                    }
                }

        private static synchronized int internalCreate(RenderScript rs, Resources resources, int resourceID) {
                    byte[] pgm;
                    int pgmLength;
                    String resName;
                    String cachePath;
                    try {
                        InputStream is = resources.openRawResource(resourceID);
                        try {
                            try {
                        pgm = new byte[1024];
                        pgmLength = 0;
                                while (true) {
                            int bytesLeft = pgm.length - pgmLength;
                            if (bytesLeft == 0) {
                                byte[] buf2 = new byte[pgm.length * 2];
                                System.arraycopy(pgm, 0, buf2, 0, pgm.length);
                                pgm = buf2;
                                bytesLeft = pgm.length - pgmLength;
                                    }
                            int bytesRead = is.read(pgm, pgmLength, bytesLeft);
                            if (bytesRead > 0) {
                                pgmLength += bytesRead;
                                    } else {
                                is.close();
                               resName = resources.getResourceEntryName(resourceID);
                               cachePath = rs.getApplicationContext().getCacheDir().toString();
                                    }
                                }
                            } catch (Throwable th) {
                                is.close();
                                throw th;
                            }
                        } catch (IOException e) {
                   throw new Resources.NotFoundException();
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
           return rs.nScriptCCreate(resName, cachePath, pgm, pgmLength);
                }
            }
