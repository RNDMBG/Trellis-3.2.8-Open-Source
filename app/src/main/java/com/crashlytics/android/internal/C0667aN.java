            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import java.io.Closeable;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileWriter;
            import org.json.JSONObject;
            
            
            
    public class C0667aN {
        C0667aN() {
                }

                
        public JSONObject m177a() throws Throwable {
                    FileInputStream fileInputStream;
                    JSONObject jSONObject;
                    FileInputStream fileInputStream2 = null;
            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Reading cached settings...");
                    try {
                File file = new File(C0727v.m357a().m370h(), "com.crashlytics.settings.json");
                if (file.exists()) {
                    fileInputStream = new FileInputStream(file);
                            try {
                                try {
                            jSONObject = new JSONObject(C0681ab.m204a(fileInputStream));
                                    fileInputStream2 = fileInputStream;
                                } catch (Exception e) {
                            e = e;
                            C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to fetch cached settings", e);
                            C0681ab.m210a(fileInputStream, "Error while closing settings cache file.");
                                    return null;
                                }
                            } catch (Throwable th) {
                                th = th;
                                fileInputStream2 = fileInputStream;
                                C0681ab.m210a(fileInputStream2, "Error while closing settings cache file.");
                                throw th;
                            }
                        } else {
                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No cached settings found.");
                            jSONObject = null;
                        }
                C0681ab.m210a(fileInputStream2, "Error while closing settings cache file.");
                return jSONObject;
                    } catch (Exception e2) {
                e = e2;
                        fileInputStream = null;
                    } catch (Throwable th2) {
                th = th2;
                        C0681ab.m210a(fileInputStream2, "Error while closing settings cache file.");
                        throw th;
                    }
                }

                
        public void m178a(long j, JSONObject jSONObject) throws Throwable {
                    FileWriter fileWriter;
            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Writing settings to cache file...");
            if (jSONObject != null) {
                FileWriter fileWriter2 = null;
                        try {
                    jSONObject.put("expires_at", j);
                    fileWriter = new FileWriter(new File(C0727v.m357a().m370h(), "com.crashlytics.settings.json"));
                            try {
                                try {
                            fileWriter.write(jSONObject.toString());
                            fileWriter.flush();
                            C0681ab.m210a((Closeable) fileWriter, "Failed to close settings writer.");
                                } catch (Exception e) {
                            e = e;
                            C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to cache settings", e);
                            C0681ab.m210a((Closeable) fileWriter, "Failed to close settings writer.");
                                }
                            } catch (Throwable th) {
                                th = th;
                                fileWriter2 = fileWriter;
                                C0681ab.m210a((Closeable) fileWriter2, "Failed to close settings writer.");
                                throw th;
                            }
                        } catch (Exception e2) {
                    e = e2;
                    fileWriter = null;
                        } catch (Throwable th2) {
                            th = th2;
                            C0681ab.m210a((Closeable) fileWriter2, "Failed to close settings writer.");
                            throw th;
                        }
                    }
                }
            }
