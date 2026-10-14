            package android.support.p001v8.renderscript;

            import android.content.res.Resources;
            import android.renderscript.Allocation;
            import android.renderscript.Element;
            import android.renderscript.FieldPacker;
            import android.renderscript.Script;
            import android.renderscript.ScriptC;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    class ScriptCThunker extends ScriptC {
                private static final String TAG = "ScriptC";

/* 38 */        protected ScriptCThunker(RenderScriptThunker rs, Resources resources, int resourceID) {
/* 39 */            super(rs.f54mN, resources, resourceID);
                }

/* 44 */        Script.KernelID thunkCreateKernelID(int slot, int sig, Element ein, Element eout) {
/* 45 */            Element nein = null;
/* 46 */            Element neout = null;
/* 47 */            if (ein != null) {
/* 48 */                nein = ((ElementThunker) ein).f24mN;
                    }
/* 50 */            if (eout != null) {
/* 51 */                neout = ((ElementThunker) eout).f24mN;
                    }
/* 53 */            return createKernelID(slot, sig, nein, neout);
                }

/* 57 */        void thunkInvoke(int slot) {
/* 58 */            invoke(slot);
                }

/* 61 */        void thunkBindAllocation(Allocation va, int slot) {
/* 62 */            Allocation nva = null;
/* 63 */            if (va != null) {
/* 64 */                nva = ((AllocationThunker) va).f3mN;
                    }
/* 66 */            bindAllocation(nva, slot);
                }

/* 69 */        void thunkSetTimeZone(String timeZone) {
/* 70 */            setTimeZone(timeZone);
                }

/* 73 */        void thunkInvoke(int slot, FieldPacker v) {
/* 74 */            FieldPacker nfp = new FieldPacker(v.getData());
/* 76 */            invoke(slot, nfp);
                }

/* 79 */        void thunkForEach(int slot, Allocation ain, Allocation aout, FieldPacker v) {
/* 80 */            Allocation nin = null;
/* 81 */            Allocation nout = null;
/* 82 */            FieldPacker nfp = null;
/* 83 */            if (ain != null) {
/* 84 */                nin = ((AllocationThunker) ain).f3mN;
                    }
/* 86 */            if (aout != null) {
/* 87 */                nout = ((AllocationThunker) aout).f3mN;
                    }
/* 89 */            if (v != null) {
/* 90 */                nfp = new FieldPacker(v.getData());
                    }
/* 92 */            forEach(slot, nin, nout, nfp);
                }

/* 96 */        void thunkForEach(int slot, Allocation ain, Allocation aout, FieldPacker v, Script.LaunchOptions sc) {
/* 97 */            Script.LaunchOptions lo = null;
/* 98 */            if (sc != null) {
/* 99 */                lo = new Script.LaunchOptions();
/* 100 */               if (sc.getXEnd() > 0) {
                            lo.setX(sc.getXStart(), sc.getXEnd());
                        }
/* 101 */               if (sc.getYEnd() > 0) {
                            lo.setY(sc.getYStart(), sc.getYEnd());
                        }
/* 102 */               if (sc.getZEnd() > 0) {
                            lo.setZ(sc.getZStart(), sc.getZEnd());
                        }
                    }
/* 105 */           Allocation nin = null;
/* 106 */           Allocation nout = null;
/* 107 */           FieldPacker nfp = null;
/* 108 */           if (ain != null) {
/* 109 */               nin = ((AllocationThunker) ain).f3mN;
                    }
/* 111 */           if (aout != null) {
/* 112 */               nout = ((AllocationThunker) aout).f3mN;
                    }
/* 114 */           if (v != null) {
/* 115 */               nfp = new FieldPacker(v.getData());
                    }
/* 117 */           forEach(slot, nin, nout, nfp, lo);
                }

/* 120 */       void thunkSetVar(int index, float v) {
/* 121 */           setVar(index, v);
                }

/* 123 */       void thunkSetVar(int index, double v) {
/* 124 */           setVar(index, v);
                }

/* 126 */       void thunkSetVar(int index, int v) {
/* 127 */           setVar(index, v);
                }

/* 129 */       void thunkSetVar(int index, long v) {
/* 130 */           setVar(index, v);
                }

/* 132 */       void thunkSetVar(int index, boolean v) {
/* 133 */           setVar(index, v);
                }

/* 136 */       void thunkSetVar(int index, BaseObj o) {
/* 137 */           if (o == null) {
/* 138 */               setVar(index, 0);
                    } else {
/* 141 */               setVar(index, o.getNObj());
                    }
                }

/* 143 */       void thunkSetVar(int index, FieldPacker v) {
/* 144 */           FieldPacker nfp = new FieldPacker(v.getData());
/* 146 */           setVar(index, nfp);
                }

/* 149 */       void thunkSetVar(int index, FieldPacker v, Element e, int[] dims) {
/* 150 */           FieldPacker nfp = new FieldPacker(v.getData());
/* 152 */           ElementThunker et = (ElementThunker) e;
/* 153 */           setVar(index, nfp, et.f24mN, dims);
                }

/* 156 */       Script.FieldID thunkCreateFieldID(int slot, Element e) {
/* 157 */           ElementThunker et = (ElementThunker) e;
/* 158 */           return createFieldID(slot, et.getNObj());
                }
            }
