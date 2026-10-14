            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.util.SparseArray;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            
            
    public class Script extends BaseObj {
                private final SparseArray<FieldID> mFIDs;
                private final SparseArray<KernelID> mKIDs;

                
                ScriptCThunker f56mT;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
        android.renderscript.Script getNObj() {
            return this.f56mT;
                }

        public static final class KernelID extends BaseObj {

                    
                    android.renderscript.Script.KernelID f58mN;
                    Script mScript;
                    int mSig;
                    int mSlot;

            KernelID(int id, RenderScript rs, Script s, int slot, int sig) {
                super(id, rs);
                this.mScript = s;
                this.mSlot = slot;
                this.mSig = sig;
                    }
                }

        protected KernelID createKernelID(int slot, int sig, Element ein, Element eout) {
            KernelID k = this.mKIDs.get(slot);
            if (k != null) {
                        return k;
                    }
            RenderScript renderScript = this.mRS;
                    if (RenderScript.isNative) {
                KernelID k2 = new KernelID(0, this.mRS, this, slot, sig);
                if (this.f56mT != null) {
                    k2.f58mN = this.f56mT.thunkCreateKernelID(slot, sig, ein, eout);
                        }
                this.mKIDs.put(slot, k2);
                        return k2;
                    }
            int id = this.mRS.nScriptKernelIDCreate(getID(this.mRS), slot, sig);
            if (id == 0) {
                throw new RSDriverException("Failed to create KernelID");
                    }
            KernelID k3 = new KernelID(id, this.mRS, this, slot, sig);
            this.mKIDs.put(slot, k3);
                    return k3;
                }

       public static final class FieldID extends BaseObj {

                    
                    android.renderscript.Script.FieldID f57mN;
                    Script mScript;
                    int mSlot;

           FieldID(int id, RenderScript rs, Script s, int slot) {
               super(id, rs);
               this.mScript = s;
               this.mSlot = slot;
                    }
                }

       protected FieldID createFieldID(int slot, Element e) {
           RenderScript renderScript = this.mRS;
                    if (RenderScript.isNative) {
               FieldID f = new FieldID(0, this.mRS, this, slot);
               if (this.f56mT != null) {
                   f.f57mN = this.f56mT.thunkCreateFieldID(slot, e);
                        }
               this.mFIDs.put(slot, f);
                        return f;
                    }
           FieldID f2 = this.mFIDs.get(slot);
           if (f2 != null) {
                        return f2;
                    }
           int id = this.mRS.nScriptFieldIDCreate(getID(this.mRS), slot);
           if (id == 0) {
               throw new RSDriverException("Failed to create FieldID");
                    }
           FieldID f3 = new FieldID(id, this.mRS, this, slot);
           this.mFIDs.put(slot, f3);
                    return f3;
                }

       protected void invoke(int slot) {
           if (this.f56mT != null) {
               this.f56mT.thunkInvoke(slot);
                    } else {
               this.mRS.nScriptInvoke(getID(this.mRS), slot);
                    }
                }

       protected void invoke(int slot, FieldPacker v) {
           if (this.f56mT != null) {
               this.f56mT.thunkInvoke(slot, v);
                    } else if (v != null) {
               this.mRS.nScriptInvokeV(getID(this.mRS), slot, v.getData());
                    } else {
               this.mRS.nScriptInvoke(getID(this.mRS), slot);
                    }
                }

       public void bindAllocation(Allocation va, int slot) {
           if (this.f56mT != null) {
               this.f56mT.thunkBindAllocation(va, slot);
               return;
                    }
           this.mRS.validate();
           if (va != null) {
               this.mRS.nScriptBindAllocation(getID(this.mRS), va.getID(this.mRS), slot);
                    } else {
               this.mRS.nScriptBindAllocation(getID(this.mRS), 0, slot);
                    }
                }

       public void setTimeZone(String timeZone) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetTimeZone(timeZone);
               return;
                    }
           this.mRS.validate();
                    try {
               this.mRS.nScriptSetTimeZone(getID(this.mRS), timeZone.getBytes(IoUtils.UTF_8));
                    } catch (UnsupportedEncodingException e) {
               throw new RuntimeException(e);
                    }
                }

       protected void forEach(int slot, Allocation ain, Allocation aout, FieldPacker v) {
           if (this.f56mT != null) {
               this.f56mT.thunkForEach(slot, ain, aout, v);
               return;
                    }
           if (ain == null && aout == null) {
               throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
                    }
           int in_id = 0;
           if (ain != null) {
               in_id = ain.getID(this.mRS);
                    }
           int out_id = 0;
           if (aout != null) {
               out_id = aout.getID(this.mRS);
                    }
           byte[] params = null;
           if (v != null) {
               params = v.getData();
                    }
           this.mRS.nScriptForEach(getID(this.mRS), slot, in_id, out_id, params);
                }

       protected void forEach(int slot, Allocation ain, Allocation aout, FieldPacker v, LaunchOptions sc) {
           if (this.f56mT != null) {
               this.f56mT.thunkForEach(slot, ain, aout, v, sc);
               return;
                    }
           if (ain == null && aout == null) {
               throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
                    }
           if (sc == null) {
               forEach(slot, ain, aout, v);
               return;
                    }
           int in_id = 0;
           if (ain != null) {
               in_id = ain.getID(this.mRS);
                    }
           int out_id = 0;
           if (aout != null) {
               out_id = aout.getID(this.mRS);
                    }
           byte[] params = null;
           if (v != null) {
               params = v.getData();
                    }
           this.mRS.nScriptForEachClipped(getID(this.mRS), slot, in_id, out_id, params, LaunchOptions.access$000(sc), LaunchOptions.access$100(sc), LaunchOptions.access$200(sc), LaunchOptions.access$300(sc), LaunchOptions.access$400(sc), LaunchOptions.access$500(sc));
                }

       Script(int id, RenderScript rs) {
           super(id, rs);
            this.mKIDs = new SparseArray<>();
           this.mFIDs = new SparseArray<>();
                }

       public void setVar(int index, float v) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, v);
                    } else {
               this.mRS.nScriptSetVarF(getID(this.mRS), index, v);
                    }
                }

       public void setVar(int index, double v) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, v);
                    } else {
               this.mRS.nScriptSetVarD(getID(this.mRS), index, v);
                    }
                }

       public void setVar(int index, int v) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, v);
                    } else {
               this.mRS.nScriptSetVarI(getID(this.mRS), index, v);
                    }
                }

       public void setVar(int index, long v) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, v);
                    } else {
               this.mRS.nScriptSetVarJ(getID(this.mRS), index, v);
                    }
                }

       public void setVar(int index, boolean v) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, v);
                    } else {
               this.mRS.nScriptSetVarI(getID(this.mRS), index, v ? 1 : 0);
                    }
                }

       public void setVar(int index, BaseObj o) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, o);
                    } else {
               this.mRS.nScriptSetVarObj(getID(this.mRS), index, o == null ? 0 : o.getID(this.mRS));
                    }
                }

       public void setVar(int index, FieldPacker v) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, v);
                    } else {
               this.mRS.nScriptSetVarV(getID(this.mRS), index, v.getData());
                    }
                }

       public void setVar(int index, FieldPacker v, Element e, int[] dims) {
           if (this.f56mT != null) {
               this.f56mT.thunkSetVar(index, v, e, dims);
                    } else {
               this.mRS.nScriptSetVarVE(getID(this.mRS), index, v.getData(), e.getID(this.mRS), dims);
                    }
                }

       public static class Builder {
                    RenderScript mRS;

           Builder(RenderScript rs) {
               this.mRS = rs;
                    }
                }

       public static class FieldBase {
                    protected Allocation mAllocation;
                    protected Element mElement;

           protected void init(RenderScript rs, int dimx) {
               this.mAllocation = Allocation.createSized(rs, this.mElement, dimx, 1);
                    }

           protected void init(RenderScript rs, int dimx, int usages) {
               this.mAllocation = Allocation.createSized(rs, this.mElement, dimx, usages | 1);
                    }

           protected FieldBase() {
                    }

           public Element getElement() {
               return this.mElement;
                    }

           public Type getType() {
               return this.mAllocation.getType();
                    }

           public Allocation getAllocation() {
               return this.mAllocation;
                    }

           public void updateAllocation() {
                    }
                }

       public static final class LaunchOptions {
                    private int strategy;
                    private int xstart = 0;
                    private int ystart = 0;
                    private int xend = 0;
                    private int yend = 0;
                    private int zstart = 0;
                    private int zend = 0;

           static  int access$000(LaunchOptions x0) {
               return x0.xstart;
                    }

           static  int access$100(LaunchOptions x0) {
               return x0.xend;
                    }

           static  int access$200(LaunchOptions x0) {
               return x0.ystart;
                    }

           static  int access$300(LaunchOptions x0) {
               return x0.yend;
                    }

           static  int access$400(LaunchOptions x0) {
               return x0.zstart;
                    }

           static  int access$500(LaunchOptions x0) {
               return x0.zend;
                    }

           public LaunchOptions setX(int xstartArg, int xendArg) {
               if (xstartArg < 0 || xendArg <= xstartArg) {
                   throw new RSIllegalArgumentException("Invalid dimensions");
                        }
               this.xstart = xstartArg;
               this.xend = xendArg;
               return this;
                    }

           public LaunchOptions setY(int ystartArg, int yendArg) {
               if (ystartArg < 0 || yendArg <= ystartArg) {
                   throw new RSIllegalArgumentException("Invalid dimensions");
                        }
               this.ystart = ystartArg;
               this.yend = yendArg;
               return this;
                    }

           public LaunchOptions setZ(int zstartArg, int zendArg) {
               if (zstartArg < 0 || zendArg <= zstartArg) {
                   throw new RSIllegalArgumentException("Invalid dimensions");
                        }
               this.zstart = zstartArg;
               this.zend = zendArg;
               return this;
                    }

           public int getXStart() {
               return this.xstart;
                    }

           public int getXEnd() {
               return this.xend;
                    }

           public int getYStart() {
               return this.ystart;
                    }

           public int getYEnd() {
               return this.yend;
                    }

           public int getZStart() {
               return this.zstart;
                    }

           public int getZEnd() {
               return this.zend;
                    }
                }
            }
