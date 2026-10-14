            package android.support.p001v8.renderscript;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class BaseObj {
                private boolean mDestroyed;
                private int mID;
                RenderScript mRS;

/* 27 */        BaseObj(int id, RenderScript rs) {
/* 29 */            rs.validate();
/* 30 */            this.mRS = rs;
/* 31 */            this.mID = id;
/* 32 */            this.mDestroyed = false;
                }

/* 35 */        void setID(int id) {
/* 36 */            if (this.mID != 0) {
/* 37 */                throw new RSRuntimeException("Internal Error, reset of object ID.");
                    }
/* 39 */            this.mID = id;
                }

/* 51 */        int getID(RenderScript rs) {
/* 52 */            this.mRS.validate();
/* 53 */            if (RenderScript.isNative) {
/* 55 */                if (getNObj() != null) {
/* 56 */                    return getNObj().hashCode();
                        }
                    }
/* 59 */            if (this.mDestroyed) {
/* 60 */                throw new RSInvalidStateException("using a destroyed object.");
                    }
/* 62 */            if (this.mID == 0) {
/* 63 */                throw new RSRuntimeException("Internal error: Object id 0.");
                    }
/* 65 */            if (rs != null && rs != this.mRS) {
/* 66 */                throw new RSInvalidStateException("using object with mismatched context.");
                    }
/* 68 */            return this.mID;
                }

/* 71 */        android.renderscript.BaseObj getNObj() {
/* 72 */            return null;
                }

/* 75 */        void checkValid() {
/* 76 */            if (this.mID == 0 && getNObj() == null) {
/* 77 */                throw new RSIllegalArgumentException("Invalid object.");
                    }
                }

/* 85 */        protected void finalize() throws Throwable {
/* 86 */            if (!this.mDestroyed) {
/* 87 */                if (this.mID != 0 && this.mRS.isAlive()) {
/* 88 */                    this.mRS.nObjDestroy(this.mID);
                        }
/* 90 */                this.mRS = null;
/* 91 */                this.mID = 0;
/* 92 */                this.mDestroyed = true;
                    }
/* 96 */            super.finalize();
                }

/* 104 */       public synchronized void destroy() {
                    try {
                        if (this.mDestroyed) {
/* 106 */                   throw new RSInvalidStateException("Object already destroyed.");
                        }
/* 108 */               this.mDestroyed = true;
/* 109 */               this.mRS.nObjDestroy(this.mID);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 118 */       public int hashCode() {
/* 119 */           return this.mID;
                }

/* 131 */       public boolean equals(Object obj) {
/* 132 */           if (this == obj) {
                        return true;
                    }
/* 135 */           if (getClass() != obj.getClass()) {
                        return false;
                    }
/* 139 */           BaseObj b = (BaseObj) obj;
                    return this.mID == b.mID;
                }
            }
