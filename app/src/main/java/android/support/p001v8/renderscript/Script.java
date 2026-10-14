            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.util.SparseArray;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class Script extends BaseObj {
                private final SparseArray<FieldID> mFIDs;
                private final SparseArray<KernelID> mKIDs;

                /* JADX INFO: renamed from: mT */
                ScriptCThunker f56mT;

                @Override // android.support.p001v8.renderscript.BaseObj
/* 24 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 25 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.BaseObj
/* 28 */        android.renderscript.Script getNObj() {
/* 29 */            return this.f56mT;
                }

/* 45 */        public static final class KernelID extends BaseObj {

                    /* JADX INFO: renamed from: mN */
                    android.renderscript.Script.KernelID f58mN;
                    Script mScript;
                    int mSig;
                    int mSlot;

/* 46 */            KernelID(int id, RenderScript rs, Script s, int slot, int sig) {
/* 47 */                super(id, rs);
/* 48 */                this.mScript = s;
/* 49 */                this.mSlot = slot;
/* 50 */                this.mSig = sig;
                    }
                }

/* 66 */        protected KernelID createKernelID(int slot, int sig, Element ein, Element eout) {
/* 67 */            KernelID k = this.mKIDs.get(slot);
/* 68 */            if (k != null) {
                        return k;
                    }
/* 74 */            RenderScript renderScript = this.mRS;
                    if (RenderScript.isNative) {
/* 75 */                KernelID k2 = new KernelID(0, this.mRS, this, slot, sig);
/* 76 */                if (this.f56mT != null) {
/* 77 */                    k2.f58mN = this.f56mT.thunkCreateKernelID(slot, sig, ein, eout);
                        }
/* 79 */                this.mKIDs.put(slot, k2);
                        return k2;
                    }
/* 84 */            int id = this.mRS.nScriptKernelIDCreate(getID(this.mRS), slot, sig);
/* 85 */            if (id == 0) {
/* 86 */                throw new RSDriverException("Failed to create KernelID");
                    }
/* 89 */            KernelID k3 = new KernelID(id, this.mRS, this, slot, sig);
/* 91 */            this.mKIDs.put(slot, k3);
                    return k3;
                }

/* 106 */       public static final class FieldID extends BaseObj {

                    /* JADX INFO: renamed from: mN */
                    android.renderscript.Script.FieldID f57mN;
                    Script mScript;
                    int mSlot;

/* 107 */           FieldID(int id, RenderScript rs, Script s, int slot) {
/* 108 */               super(id, rs);
/* 109 */               this.mScript = s;
/* 110 */               this.mSlot = slot;
                    }
                }

/* 126 */       protected FieldID createFieldID(int slot, Element e) {
/* 127 */           RenderScript renderScript = this.mRS;
                    if (RenderScript.isNative) {
/* 128 */               FieldID f = new FieldID(0, this.mRS, this, slot);
/* 129 */               if (this.f56mT != null) {
/* 130 */                   f.f57mN = this.f56mT.thunkCreateFieldID(slot, e);
                        }
/* 132 */               this.mFIDs.put(slot, f);
                        return f;
                    }
/* 135 */           FieldID f2 = this.mFIDs.get(slot);
/* 136 */           if (f2 != null) {
                        return f2;
                    }
/* 140 */           int id = this.mRS.nScriptFieldIDCreate(getID(this.mRS), slot);
/* 141 */           if (id == 0) {
/* 142 */               throw new RSDriverException("Failed to create FieldID");
                    }
/* 145 */           FieldID f3 = new FieldID(id, this.mRS, this, slot);
/* 146 */           this.mFIDs.put(slot, f3);
                    return f3;
                }

/* 156 */       protected void invoke(int slot) {
/* 157 */           if (this.f56mT != null) {
/* 158 */               this.f56mT.thunkInvoke(slot);
                    } else {
/* 162 */               this.mRS.nScriptInvoke(getID(this.mRS), slot);
                    }
                }

/* 171 */       protected void invoke(int slot, FieldPacker v) {
/* 172 */           if (this.f56mT != null) {
/* 173 */               this.f56mT.thunkInvoke(slot, v);
                    } else if (v != null) {
/* 178 */               this.mRS.nScriptInvokeV(getID(this.mRS), slot, v.getData());
                    } else {
/* 180 */               this.mRS.nScriptInvoke(getID(this.mRS), slot);
                    }
                }

/* 190 */       public void bindAllocation(Allocation va, int slot) {
/* 191 */           if (this.f56mT != null) {
/* 192 */               this.f56mT.thunkBindAllocation(va, slot);
/* 202 */               return;
                    }
/* 196 */           this.mRS.validate();
/* 197 */           if (va != null) {
/* 198 */               this.mRS.nScriptBindAllocation(getID(this.mRS), va.getID(this.mRS), slot);
                    } else {
/* 200 */               this.mRS.nScriptBindAllocation(getID(this.mRS), 0, slot);
                    }
                }

/* 204 */       public void setTimeZone(String timeZone) {
/* 205 */           if (this.f56mT != null) {
/* 206 */               this.f56mT.thunkSetTimeZone(timeZone);
/* 216 */               return;
                    }
/* 210 */           this.mRS.validate();
                    try {
/* 212 */               this.mRS.nScriptSetTimeZone(getID(this.mRS), timeZone.getBytes(IoUtils.UTF_8));
                    } catch (UnsupportedEncodingException e) {
/* 214 */               throw new RuntimeException(e);
                    }
                }

/* 227 */       protected void forEach(int slot, Allocation ain, Allocation aout, FieldPacker v) {
/* 228 */           if (this.f56mT != null) {
/* 229 */               this.f56mT.thunkForEach(slot, ain, aout, v);
/* 250 */               return;
                    }
/* 233 */           if (ain == null && aout == null) {
/* 234 */               throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
                    }
/* 237 */           int in_id = 0;
/* 238 */           if (ain != null) {
/* 239 */               in_id = ain.getID(this.mRS);
                    }
/* 241 */           int out_id = 0;
/* 242 */           if (aout != null) {
/* 243 */               out_id = aout.getID(this.mRS);
                    }
/* 245 */           byte[] params = null;
/* 246 */           if (v != null) {
/* 247 */               params = v.getData();
                    }
/* 249 */           this.mRS.nScriptForEach(getID(this.mRS), slot, in_id, out_id, params);
                }

/* 261 */       protected void forEach(int slot, Allocation ain, Allocation aout, FieldPacker v, LaunchOptions sc) {
/* 262 */           if (this.f56mT != null) {
/* 263 */               this.f56mT.thunkForEach(slot, ain, aout, v, sc);
/* 289 */               return;
                    }
/* 267 */           if (ain == null && aout == null) {
/* 268 */               throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
                    }
/* 272 */           if (sc == null) {
/* 273 */               forEach(slot, ain, aout, v);
/* 289 */               return;
                    }
/* 276 */           int in_id = 0;
/* 277 */           if (ain != null) {
/* 278 */               in_id = ain.getID(this.mRS);
                    }
/* 280 */           int out_id = 0;
/* 281 */           if (aout != null) {
/* 282 */               out_id = aout.getID(this.mRS);
                    }
/* 284 */           byte[] params = null;
/* 285 */           if (v != null) {
/* 286 */               params = v.getData();
                    }
/* 288 */           this.mRS.nScriptForEachClipped(getID(this.mRS), slot, in_id, out_id, params, LaunchOptions.access$000(sc), LaunchOptions.access$100(sc), LaunchOptions.access$200(sc), LaunchOptions.access$300(sc), LaunchOptions.access$400(sc), LaunchOptions.access$500(sc));
                }

/* 291 */       Script(int id, RenderScript rs) {
/* 292 */           super(id, rs);
/* 54 */            this.mKIDs = new SparseArray<>();
/* 114 */           this.mFIDs = new SparseArray<>();
                }

/* 301 */       public void setVar(int index, float v) {
/* 302 */           if (this.f56mT != null) {
/* 303 */               this.f56mT.thunkSetVar(index, v);
                    } else {
/* 307 */               this.mRS.nScriptSetVarF(getID(this.mRS), index, v);
                    }
                }

/* 316 */       public void setVar(int index, double v) {
/* 317 */           if (this.f56mT != null) {
/* 318 */               this.f56mT.thunkSetVar(index, v);
                    } else {
/* 322 */               this.mRS.nScriptSetVarD(getID(this.mRS), index, v);
                    }
                }

/* 331 */       public void setVar(int index, int v) {
/* 332 */           if (this.f56mT != null) {
/* 333 */               this.f56mT.thunkSetVar(index, v);
                    } else {
/* 337 */               this.mRS.nScriptSetVarI(getID(this.mRS), index, v);
                    }
                }

/* 346 */       public void setVar(int index, long v) {
/* 347 */           if (this.f56mT != null) {
/* 348 */               this.f56mT.thunkSetVar(index, v);
                    } else {
/* 352 */               this.mRS.nScriptSetVarJ(getID(this.mRS), index, v);
                    }
                }

/* 361 */       public void setVar(int index, boolean v) {
/* 362 */           if (this.f56mT != null) {
/* 363 */               this.f56mT.thunkSetVar(index, v);
                    } else {
/* 367 */               this.mRS.nScriptSetVarI(getID(this.mRS), index, v ? 1 : 0);
                    }
                }

/* 376 */       public void setVar(int index, BaseObj o) {
/* 377 */           if (this.f56mT != null) {
/* 378 */               this.f56mT.thunkSetVar(index, o);
                    } else {
/* 382 */               this.mRS.nScriptSetVarObj(getID(this.mRS), index, o == null ? 0 : o.getID(this.mRS));
                    }
                }

/* 391 */       public void setVar(int index, FieldPacker v) {
/* 392 */           if (this.f56mT != null) {
/* 393 */               this.f56mT.thunkSetVar(index, v);
                    } else {
/* 397 */               this.mRS.nScriptSetVarV(getID(this.mRS), index, v.getData());
                    }
                }

/* 408 */       public void setVar(int index, FieldPacker v, Element e, int[] dims) {
/* 409 */           if (this.f56mT != null) {
/* 410 */               this.f56mT.thunkSetVar(index, v, e, dims);
                    } else {
/* 414 */               this.mRS.nScriptSetVarVE(getID(this.mRS), index, v.getData(), e.getID(this.mRS), dims);
                    }
                }

/* 422 */       public static class Builder {
                    RenderScript mRS;

/* 423 */           Builder(RenderScript rs) {
/* 425 */               this.mRS = rs;
                    }
                }

/* 437 */       public static class FieldBase {
                    protected Allocation mAllocation;
                    protected Element mElement;

/* 438 */           protected void init(RenderScript rs, int dimx) {
/* 439 */               this.mAllocation = Allocation.createSized(rs, this.mElement, dimx, 1);
                    }

/* 442 */           protected void init(RenderScript rs, int dimx, int usages) {
/* 443 */               this.mAllocation = Allocation.createSized(rs, this.mElement, dimx, usages | 1);
                    }

/* 445 */           protected FieldBase() {
                    }

/* 449 */           public Element getElement() {
/* 450 */               return this.mElement;
                    }

/* 453 */           public Type getType() {
/* 454 */               return this.mAllocation.getType();
                    }

/* 457 */           public Allocation getAllocation() {
/* 458 */               return this.mAllocation;
                    }

/* 462 */           public void updateAllocation() {
                    }
                }

/* 469 */       public static final class LaunchOptions {
                    private int strategy;
                    private int xstart = 0;
                    private int ystart = 0;
                    private int xend = 0;
                    private int yend = 0;
                    private int zstart = 0;
                    private int zend = 0;

/* 470 */           static /* synthetic */ int access$000(LaunchOptions x0) {
/* 471 */               return x0.xstart;
                    }

/* 470 */           static /* synthetic */ int access$100(LaunchOptions x0) {
/* 471 */               return x0.xend;
                    }

/* 470 */           static /* synthetic */ int access$200(LaunchOptions x0) {
/* 471 */               return x0.ystart;
                    }

/* 470 */           static /* synthetic */ int access$300(LaunchOptions x0) {
/* 471 */               return x0.yend;
                    }

/* 470 */           static /* synthetic */ int access$400(LaunchOptions x0) {
/* 471 */               return x0.zstart;
                    }

/* 470 */           static /* synthetic */ int access$500(LaunchOptions x0) {
/* 471 */               return x0.zend;
                    }

/* 489 */           public LaunchOptions setX(int xstartArg, int xendArg) {
/* 490 */               if (xstartArg < 0 || xendArg <= xstartArg) {
/* 491 */                   throw new RSIllegalArgumentException("Invalid dimensions");
                        }
/* 493 */               this.xstart = xstartArg;
/* 494 */               this.xend = xendArg;
/* 495 */               return this;
                    }

/* 507 */           public LaunchOptions setY(int ystartArg, int yendArg) {
/* 508 */               if (ystartArg < 0 || yendArg <= ystartArg) {
/* 509 */                   throw new RSIllegalArgumentException("Invalid dimensions");
                        }
/* 511 */               this.ystart = ystartArg;
/* 512 */               this.yend = yendArg;
/* 513 */               return this;
                    }

/* 525 */           public LaunchOptions setZ(int zstartArg, int zendArg) {
/* 526 */               if (zstartArg < 0 || zendArg <= zstartArg) {
/* 527 */                   throw new RSIllegalArgumentException("Invalid dimensions");
                        }
/* 529 */               this.zstart = zstartArg;
/* 530 */               this.zend = zendArg;
/* 531 */               return this;
                    }

/* 540 */           public int getXStart() {
/* 541 */               return this.xstart;
                    }

/* 548 */           public int getXEnd() {
/* 549 */               return this.xend;
                    }

/* 556 */           public int getYStart() {
/* 557 */               return this.ystart;
                    }

/* 564 */           public int getYEnd() {
/* 565 */               return this.yend;
                    }

/* 572 */           public int getZStart() {
/* 573 */               return this.zstart;
                    }

/* 580 */           public int getZEnd() {
/* 581 */               return this.zend;
                    }
                }
            }
