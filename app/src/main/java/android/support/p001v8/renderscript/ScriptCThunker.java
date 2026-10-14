            package android.support.p001v8.renderscript;

            import android.content.res.Resources;
            import android.renderscript.Allocation;
            import android.renderscript.Element;
            import android.renderscript.FieldPacker;
            import android.renderscript.Script;
            import android.renderscript.ScriptC;
            
            
    class ScriptCThunker extends ScriptC {
                private static final String TAG = "ScriptC";

        protected ScriptCThunker(RenderScriptThunker rs, Resources resources, int resourceID) {
            super(rs.f54mN, resources, resourceID);
                }

        Script.KernelID thunkCreateKernelID(int slot, int sig, Element ein, Element eout) {
            Element nein = null;
            Element neout = null;
            if (ein != null) {
                nein = ((ElementThunker) ein).f24mN;
                    }
            if (eout != null) {
                neout = ((ElementThunker) eout).f24mN;
                    }
            return createKernelID(slot, sig, nein, neout);
                }

        void thunkInvoke(int slot) {
            invoke(slot);
                }

        void thunkBindAllocation(Allocation va, int slot) {
            Allocation nva = null;
            if (va != null) {
                nva = ((AllocationThunker) va).f3mN;
                    }
            bindAllocation(nva, slot);
                }

        void thunkSetTimeZone(String timeZone) {
            setTimeZone(timeZone);
                }

        void thunkInvoke(int slot, FieldPacker v) {
            FieldPacker nfp = new FieldPacker(v.getData());
            invoke(slot, nfp);
                }

        void thunkForEach(int slot, Allocation ain, Allocation aout, FieldPacker v) {
            Allocation nin = null;
            Allocation nout = null;
            FieldPacker nfp = null;
            if (ain != null) {
                nin = ((AllocationThunker) ain).f3mN;
                    }
            if (aout != null) {
                nout = ((AllocationThunker) aout).f3mN;
                    }
            if (v != null) {
                nfp = new FieldPacker(v.getData());
                    }
            forEach(slot, nin, nout, nfp);
                }

        void thunkForEach(int slot, Allocation ain, Allocation aout, FieldPacker v, Script.LaunchOptions sc) {
            Script.LaunchOptions lo = null;
            if (sc != null) {
                lo = new Script.LaunchOptions();
               if (sc.getXEnd() > 0) {
                            lo.setX(sc.getXStart(), sc.getXEnd());
                        }
               if (sc.getYEnd() > 0) {
                            lo.setY(sc.getYStart(), sc.getYEnd());
                        }
               if (sc.getZEnd() > 0) {
                            lo.setZ(sc.getZStart(), sc.getZEnd());
                        }
                    }
           Allocation nin = null;
           Allocation nout = null;
           FieldPacker nfp = null;
           if (ain != null) {
               nin = ((AllocationThunker) ain).f3mN;
                    }
           if (aout != null) {
               nout = ((AllocationThunker) aout).f3mN;
                    }
           if (v != null) {
               nfp = new FieldPacker(v.getData());
                    }
           forEach(slot, nin, nout, nfp, lo);
                }

       void thunkSetVar(int index, float v) {
           setVar(index, v);
                }

       void thunkSetVar(int index, double v) {
           setVar(index, v);
                }

       void thunkSetVar(int index, int v) {
           setVar(index, v);
                }

       void thunkSetVar(int index, long v) {
           setVar(index, v);
                }

       void thunkSetVar(int index, boolean v) {
           setVar(index, v);
                }

       void thunkSetVar(int index, BaseObj o) {
           if (o == null) {
               setVar(index, 0);
                    } else {
               setVar(index, o.getNObj());
                    }
                }

       void thunkSetVar(int index, FieldPacker v) {
           FieldPacker nfp = new FieldPacker(v.getData());
           setVar(index, nfp);
                }

       void thunkSetVar(int index, FieldPacker v, Element e, int[] dims) {
           FieldPacker nfp = new FieldPacker(v.getData());
           ElementThunker et = (ElementThunker) e;
           setVar(index, nfp, et.f24mN, dims);
                }

       Script.FieldID thunkCreateFieldID(int slot, Element e) {
           ElementThunker et = (ElementThunker) e;
           return createFieldID(slot, et.getNObj());
                }
            }
