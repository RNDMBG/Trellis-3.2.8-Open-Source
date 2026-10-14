            package android.support.p001v8.renderscript;

            import java.util.ArrayList;
            
            
    public class ScriptGroup extends BaseObj {
                C0129IO[] mInputs;
                C0129IO[] mOutputs;

                
        static class C0129IO {
                    Allocation mAllocation;
                    Script.KernelID mKID;

            C0129IO(Script.KernelID s) {
                this.mKID = s;
                    }
                }

        static class ConnectLine {
                    Type mAllocationType;
                    Script.KernelID mFrom;
                    Script.FieldID mToF;
                    Script.KernelID mToK;

            ConnectLine(Type t, Script.KernelID from, Script.KernelID to) {
                this.mFrom = from;
                this.mToK = to;
                this.mAllocationType = t;
                    }

            ConnectLine(Type t, Script.KernelID from, Script.FieldID to) {
                this.mFrom = from;
                this.mToF = to;
                this.mAllocationType = t;
                    }
                }

        static class Node {
                    int dagNumber;
                    Node mNext;
                    Script mScript;
                    ArrayList<Script.KernelID> mKernels = new ArrayList<>();
                    ArrayList<ConnectLine> mInputs = new ArrayList<>();
                    ArrayList<ConnectLine> mOutputs = new ArrayList<>();

            Node(Script s) {
                this.mScript = s;
                    }
                }

        ScriptGroup(int id, RenderScript rs) {
            super(id, rs);
                }

       public void setInput(Script.KernelID s, Allocation a) {
                    for (int ct = 0; ct < this.mInputs.length; ct++) {
               if (this.mInputs[ct].mKID == s) {
                   this.mInputs[ct].mAllocation = a;
                   this.mRS.nScriptGroupSetInput(getID(this.mRS), s.getID(this.mRS), this.mRS.safeID(a));
                   return;
                        }
                    }
           throw new RSIllegalArgumentException("Script not found");
                }

       public void setOutput(Script.KernelID s, Allocation a) {
                    for (int ct = 0; ct < this.mOutputs.length; ct++) {
               if (this.mOutputs[ct].mKID == s) {
                   this.mOutputs[ct].mAllocation = a;
                   this.mRS.nScriptGroupSetOutput(getID(this.mRS), s.getID(this.mRS), this.mRS.safeID(a));
                   return;
                        }
                    }
           throw new RSIllegalArgumentException("Script not found");
                }

       public void execute() {
           this.mRS.nScriptGroupExecute(getID(this.mRS));
                }

       public static final class Builder {
                    private int mKernelCount;
                    private RenderScript mRS;

                    
                    private ScriptGroupThunker.Builder f59mT;
                    private ArrayList<Node> mNodes = new ArrayList<>();
                    private ArrayList<ConnectLine> mLines = new ArrayList<>();

           public Builder(RenderScript rs) {
               if (RenderScript.isNative) {
                   this.f59mT = new ScriptGroupThunker.Builder(rs);
                        }
               this.mRS = rs;
                    }

           private void validateCycle(Node target, Node original) {
                        for (int ct = 0; ct < target.mOutputs.size(); ct++) {
                   ConnectLine cl = target.mOutputs.get(ct);
                   if (cl.mToK != null) {
                       Node tn = findNode(cl.mToK.mScript);
                       if (tn.equals(original)) {
                           throw new RSInvalidStateException("Loops in group not allowed.");
                                }
                       validateCycle(tn, original);
                            }
                   if (cl.mToF != null) {
                       Node tn2 = findNode(cl.mToF.mScript);
                       if (tn2.equals(original)) {
                           throw new RSInvalidStateException("Loops in group not allowed.");
                                }
                       validateCycle(tn2, original);
                            }
                        }
                    }

           private void mergeDAGs(int valueUsed, int valueKilled) {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
                   if (this.mNodes.get(ct).dagNumber == valueKilled) {
                       this.mNodes.get(ct).dagNumber = valueUsed;
                            }
                        }
                    }

           private void validateDAGRecurse(Node n, int dagNumber) {
               if (n.dagNumber != 0 && n.dagNumber != dagNumber) {
                   mergeDAGs(n.dagNumber, dagNumber);
                   return;
                        }
               n.dagNumber = dagNumber;
                        for (int ct = 0; ct < n.mOutputs.size(); ct++) {
                   ConnectLine cl = n.mOutputs.get(ct);
                   if (cl.mToK != null) {
                       Node tn = findNode(cl.mToK.mScript);
                       validateDAGRecurse(tn, dagNumber);
                            }
                   if (cl.mToF != null) {
                       Node tn2 = findNode(cl.mToF.mScript);
                       validateDAGRecurse(tn2, dagNumber);
                            }
                        }
                    }

           private void validateDAG() {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
                   Node n = this.mNodes.get(ct);
                   if (n.mInputs.size() == 0) {
                       if (n.mOutputs.size() == 0 && this.mNodes.size() > 1) {
                           throw new RSInvalidStateException("Groups cannot contain unconnected scripts");
                                }
                       validateDAGRecurse(n, ct + 1);
                            }
                        }
               int dagNumber = this.mNodes.get(0).dagNumber;
                        for (int ct2 = 0; ct2 < this.mNodes.size(); ct2++) {
                   if (this.mNodes.get(ct2).dagNumber != dagNumber) {
                       throw new RSInvalidStateException("Multiple DAGs in group not allowed.");
                            }
                        }
                    }

           private Node findNode(Script s) {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
                   if (s == this.mNodes.get(ct).mScript) {
                       return this.mNodes.get(ct);
                            }
                        }
               return null;
                    }

           private Node findNode(Script.KernelID k) {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
                   Node n = this.mNodes.get(ct);
                            for (int ct2 = 0; ct2 < n.mKernels.size(); ct2++) {
                       if (k == n.mKernels.get(ct2)) {
                           return n;
                                }
                            }
                        }
               return null;
                    }

           public Builder addKernel(Script.KernelID k) {
               if (this.f59mT != null) {
                   this.f59mT.addKernel(k);
                        } else {
                   if (this.mLines.size() != 0) {
                       throw new RSInvalidStateException("Kernels may not be added once connections exist.");
                            }
                   if (findNode(k) == null) {
                       this.mKernelCount++;
                       Node n = findNode(k.mScript);
                       if (n == null) {
                           n = new Node(k.mScript);
                           this.mNodes.add(n);
                                }
                       n.mKernels.add(k);
                            }
                        }
               return this;
                    }

           public Builder addConnection(Type t, Script.KernelID from, Script.FieldID to) {
               if (this.f59mT != null) {
                   this.f59mT.addConnection(t, from, to);
                        } else {
                   Node nf = findNode(from);
                   if (nf == null) {
                       throw new RSInvalidStateException("From script not found.");
                            }
                   Node nt = findNode(to.mScript);
                   if (nt == null) {
                       throw new RSInvalidStateException("To script not found.");
                            }
                   ConnectLine cl = new ConnectLine(t, from, to);
                   this.mLines.add(new ConnectLine(t, from, to));
                   nf.mOutputs.add(cl);
                   nt.mInputs.add(cl);
                   validateCycle(nf, nf);
                        }
               return this;
                    }

           public Builder addConnection(Type t, Script.KernelID from, Script.KernelID to) {
               if (this.f59mT != null) {
                   this.f59mT.addConnection(t, from, to);
                        } else {
                   Node nf = findNode(from);
                   if (nf == null) {
                       throw new RSInvalidStateException("From script not found.");
                            }
                   Node nt = findNode(to);
                   if (nt == null) {
                       throw new RSInvalidStateException("To script not found.");
                            }
                   ConnectLine cl = new ConnectLine(t, from, to);
                   this.mLines.add(new ConnectLine(t, from, to));
                   nf.mOutputs.add(cl);
                   nt.mInputs.add(cl);
                   validateCycle(nf, nf);
                        }
               return this;
                    }

           public ScriptGroup create() {
               if (this.f59mT != null) {
                   return this.f59mT.create();
                        }
               if (this.mNodes.size() == 0) {
                   throw new RSInvalidStateException("Empty script groups are not allowed");
                        }
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
                   this.mNodes.get(ct).dagNumber = 0;
                        }
               validateDAG();
               ArrayList<C0129IO> inputs = new ArrayList<>();
               ArrayList<C0129IO> outputs = new ArrayList<>();
               int[] kernels = new int[this.mKernelCount];
               int idx = 0;
                        for (int ct2 = 0; ct2 < this.mNodes.size(); ct2++) {
                   Node n = this.mNodes.get(ct2);
                   int ct3 = 0;
                            while (ct3 < n.mKernels.size()) {
                       Script.KernelID kid = n.mKernels.get(ct3);
                       int idx2 = idx + 1;
                                kernels[idx] = kid.getID(this.mRS);
                       boolean hasInput = false;
                       boolean hasOutput = false;
                                for (int ct4 = 0; ct4 < n.mInputs.size(); ct4++) {
                           if (n.mInputs.get(ct4).mToK == kid) {
                               hasInput = true;
                                    }
                                }
                                for (int ct5 = 0; ct5 < n.mOutputs.size(); ct5++) {
                           if (n.mOutputs.get(ct5).mFrom == kid) {
                               hasOutput = true;
                                    }
                                }
                       if (!hasInput) {
                           inputs.add(new C0129IO(kid));
                                }
                       if (!hasOutput) {
                           outputs.add(new C0129IO(kid));
                                }
                       ct3++;
                                idx = idx2;
                            }
                        }
               if (idx != this.mKernelCount) {
                   throw new RSRuntimeException("Count mismatch, should not happen.");
                        }
               int[] src = new int[this.mLines.size()];
               int[] dstk = new int[this.mLines.size()];
               int[] dstf = new int[this.mLines.size()];
               int[] types = new int[this.mLines.size()];
                        for (int ct6 = 0; ct6 < this.mLines.size(); ct6++) {
                   ConnectLine cl = this.mLines.get(ct6);
                   src[ct6] = cl.mFrom.getID(this.mRS);
                   if (cl.mToK != null) {
                       dstk[ct6] = cl.mToK.getID(this.mRS);
                            }
                   if (cl.mToF != null) {
                       dstf[ct6] = cl.mToF.getID(this.mRS);
                            }
                   types[ct6] = cl.mAllocationType.getID(this.mRS);
                        }
               int id = this.mRS.nScriptGroupCreate(kernels, src, dstk, dstf, types);
               if (id == 0) {
                   throw new RSRuntimeException("Object creation error, should not happen.");
                        }
               ScriptGroup sg = new ScriptGroup(id, this.mRS);
               sg.mOutputs = new C0129IO[outputs.size()];
                        for (int ct7 = 0; ct7 < outputs.size(); ct7++) {
                   sg.mOutputs[ct7] = outputs.get(ct7);
                        }
               sg.mInputs = new C0129IO[inputs.size()];
                        for (int ct8 = 0; ct8 < inputs.size(); ct8++) {
                   sg.mInputs[ct8] = inputs.get(ct8);
                        }
               return sg;
                    }
                }
            }
