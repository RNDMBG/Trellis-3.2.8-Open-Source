            package android.support.p001v8.renderscript;

            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 51 */    public class ScriptGroup extends BaseObj {
                C0129IO[] mInputs;
                C0129IO[] mOutputs;

                /* JADX INFO: renamed from: android.support.v8.renderscript.ScriptGroup$IO */
/* 52 */        static class C0129IO {
                    Allocation mAllocation;
                    Script.KernelID mKID;

/* 53 */            C0129IO(Script.KernelID s) {
/* 55 */                this.mKID = s;
                    }
                }

/* 58 */        static class ConnectLine {
                    Type mAllocationType;
                    Script.KernelID mFrom;
                    Script.FieldID mToF;
                    Script.KernelID mToK;

/* 59 */            ConnectLine(Type t, Script.KernelID from, Script.KernelID to) {
/* 61 */                this.mFrom = from;
/* 62 */                this.mToK = to;
/* 63 */                this.mAllocationType = t;
                    }

/* 65 */            ConnectLine(Type t, Script.KernelID from, Script.FieldID to) {
/* 67 */                this.mFrom = from;
/* 68 */                this.mToF = to;
/* 69 */                this.mAllocationType = t;
                    }
                }

/* 85 */        static class Node {
                    int dagNumber;
                    Node mNext;
                    Script mScript;
                    ArrayList<Script.KernelID> mKernels = new ArrayList<>();
                    ArrayList<ConnectLine> mInputs = new ArrayList<>();
                    ArrayList<ConnectLine> mOutputs = new ArrayList<>();

/* 86 */            Node(Script s) {
/* 88 */                this.mScript = s;
                    }
                }

/* 93 */        ScriptGroup(int id, RenderScript rs) {
/* 94 */            super(id, rs);
                }

/* 106 */       public void setInput(Script.KernelID s, Allocation a) {
                    for (int ct = 0; ct < this.mInputs.length; ct++) {
/* 108 */               if (this.mInputs[ct].mKID == s) {
/* 109 */                   this.mInputs[ct].mAllocation = a;
/* 110 */                   this.mRS.nScriptGroupSetInput(getID(this.mRS), s.getID(this.mRS), this.mRS.safeID(a));
/* 111 */                   return;
                        }
                    }
/* 114 */           throw new RSIllegalArgumentException("Script not found");
                }

/* 126 */       public void setOutput(Script.KernelID s, Allocation a) {
                    for (int ct = 0; ct < this.mOutputs.length; ct++) {
/* 128 */               if (this.mOutputs[ct].mKID == s) {
/* 129 */                   this.mOutputs[ct].mAllocation = a;
/* 130 */                   this.mRS.nScriptGroupSetOutput(getID(this.mRS), s.getID(this.mRS), this.mRS.safeID(a));
/* 131 */                   return;
                        }
                    }
/* 134 */           throw new RSIllegalArgumentException("Script not found");
                }

/* 142 */       public void execute() {
/* 143 */           this.mRS.nScriptGroupExecute(getID(this.mRS));
                }

/* 182 */       public static final class Builder {
                    private int mKernelCount;
                    private RenderScript mRS;

                    /* JADX INFO: renamed from: mT */
                    private ScriptGroupThunker.Builder f59mT;
                    private ArrayList<Node> mNodes = new ArrayList<>();
                    private ArrayList<ConnectLine> mLines = new ArrayList<>();

/* 183 */           public Builder(RenderScript rs) {
/* 185 */               if (RenderScript.isNative) {
/* 186 */                   this.f59mT = new ScriptGroupThunker.Builder(rs);
                        }
/* 188 */               this.mRS = rs;
                    }

/* 193 */           private void validateCycle(Node target, Node original) {
                        for (int ct = 0; ct < target.mOutputs.size(); ct++) {
/* 195 */                   ConnectLine cl = target.mOutputs.get(ct);
/* 196 */                   if (cl.mToK != null) {
/* 197 */                       Node tn = findNode(cl.mToK.mScript);
/* 198 */                       if (tn.equals(original)) {
/* 199 */                           throw new RSInvalidStateException("Loops in group not allowed.");
                                }
/* 201 */                       validateCycle(tn, original);
                            }
/* 203 */                   if (cl.mToF != null) {
/* 204 */                       Node tn2 = findNode(cl.mToF.mScript);
/* 205 */                       if (tn2.equals(original)) {
/* 206 */                           throw new RSInvalidStateException("Loops in group not allowed.");
                                }
/* 208 */                       validateCycle(tn2, original);
                            }
                        }
                    }

/* 213 */           private void mergeDAGs(int valueUsed, int valueKilled) {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
/* 215 */                   if (this.mNodes.get(ct).dagNumber == valueKilled) {
/* 216 */                       this.mNodes.get(ct).dagNumber = valueUsed;
                            }
                        }
                    }

/* 221 */           private void validateDAGRecurse(Node n, int dagNumber) {
/* 222 */               if (n.dagNumber != 0 && n.dagNumber != dagNumber) {
/* 223 */                   mergeDAGs(n.dagNumber, dagNumber);
/* 239 */                   return;
                        }
/* 227 */               n.dagNumber = dagNumber;
                        for (int ct = 0; ct < n.mOutputs.size(); ct++) {
/* 229 */                   ConnectLine cl = n.mOutputs.get(ct);
/* 230 */                   if (cl.mToK != null) {
/* 231 */                       Node tn = findNode(cl.mToK.mScript);
/* 232 */                       validateDAGRecurse(tn, dagNumber);
                            }
/* 234 */                   if (cl.mToF != null) {
/* 235 */                       Node tn2 = findNode(cl.mToF.mScript);
/* 236 */                       validateDAGRecurse(tn2, dagNumber);
                            }
                        }
                    }

/* 241 */           private void validateDAG() {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
/* 243 */                   Node n = this.mNodes.get(ct);
/* 244 */                   if (n.mInputs.size() == 0) {
/* 245 */                       if (n.mOutputs.size() == 0 && this.mNodes.size() > 1) {
/* 246 */                           throw new RSInvalidStateException("Groups cannot contain unconnected scripts");
                                }
/* 248 */                       validateDAGRecurse(n, ct + 1);
                            }
                        }
/* 251 */               int dagNumber = this.mNodes.get(0).dagNumber;
                        for (int ct2 = 0; ct2 < this.mNodes.size(); ct2++) {
/* 253 */                   if (this.mNodes.get(ct2).dagNumber != dagNumber) {
/* 254 */                       throw new RSInvalidStateException("Multiple DAGs in group not allowed.");
                            }
                        }
                    }

/* 259 */           private Node findNode(Script s) {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
/* 261 */                   if (s == this.mNodes.get(ct).mScript) {
/* 262 */                       return this.mNodes.get(ct);
                            }
                        }
/* 265 */               return null;
                    }

/* 268 */           private Node findNode(Script.KernelID k) {
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
/* 270 */                   Node n = this.mNodes.get(ct);
                            for (int ct2 = 0; ct2 < n.mKernels.size(); ct2++) {
/* 272 */                       if (k == n.mKernels.get(ct2)) {
/* 277 */                           return n;
                                }
                            }
                        }
/* 277 */               return null;
                    }

/* 288 */           public Builder addKernel(Script.KernelID k) {
/* 289 */               if (this.f59mT != null) {
/* 290 */                   this.f59mT.addKernel(k);
                        } else {
/* 294 */                   if (this.mLines.size() != 0) {
/* 295 */                       throw new RSInvalidStateException("Kernels may not be added once connections exist.");
                            }
/* 300 */                   if (findNode(k) == null) {
/* 304 */                       this.mKernelCount++;
/* 305 */                       Node n = findNode(k.mScript);
/* 306 */                       if (n == null) {
/* 308 */                           n = new Node(k.mScript);
/* 309 */                           this.mNodes.add(n);
                                }
/* 311 */                       n.mKernels.add(k);
                            }
                        }
/* 312 */               return this;
                    }

/* 329 */           public Builder addConnection(Type t, Script.KernelID from, Script.FieldID to) {
/* 330 */               if (this.f59mT != null) {
/* 331 */                   this.f59mT.addConnection(t, from, to);
                        } else {
/* 335 */                   Node nf = findNode(from);
/* 336 */                   if (nf == null) {
/* 337 */                       throw new RSInvalidStateException("From script not found.");
                            }
/* 340 */                   Node nt = findNode(to.mScript);
/* 341 */                   if (nt == null) {
/* 342 */                       throw new RSInvalidStateException("To script not found.");
                            }
/* 345 */                   ConnectLine cl = new ConnectLine(t, from, to);
/* 346 */                   this.mLines.add(new ConnectLine(t, from, to));
/* 348 */                   nf.mOutputs.add(cl);
/* 349 */                   nt.mInputs.add(cl);
/* 351 */                   validateCycle(nf, nf);
                        }
/* 352 */               return this;
                    }

/* 369 */           public Builder addConnection(Type t, Script.KernelID from, Script.KernelID to) {
/* 370 */               if (this.f59mT != null) {
/* 371 */                   this.f59mT.addConnection(t, from, to);
                        } else {
/* 375 */                   Node nf = findNode(from);
/* 376 */                   if (nf == null) {
/* 377 */                       throw new RSInvalidStateException("From script not found.");
                            }
/* 380 */                   Node nt = findNode(to);
/* 381 */                   if (nt == null) {
/* 382 */                       throw new RSInvalidStateException("To script not found.");
                            }
/* 385 */                   ConnectLine cl = new ConnectLine(t, from, to);
/* 386 */                   this.mLines.add(new ConnectLine(t, from, to));
/* 388 */                   nf.mOutputs.add(cl);
/* 389 */                   nt.mInputs.add(cl);
/* 391 */                   validateCycle(nf, nf);
                        }
/* 392 */               return this;
                    }

/* 404 */           public ScriptGroup create() {
/* 405 */               if (this.f59mT != null) {
/* 406 */                   return this.f59mT.create();
                        }
/* 409 */               if (this.mNodes.size() == 0) {
/* 410 */                   throw new RSInvalidStateException("Empty script groups are not allowed");
                        }
                        for (int ct = 0; ct < this.mNodes.size(); ct++) {
/* 415 */                   this.mNodes.get(ct).dagNumber = 0;
                        }
/* 417 */               validateDAG();
/* 419 */               ArrayList<C0129IO> inputs = new ArrayList<>();
/* 420 */               ArrayList<C0129IO> outputs = new ArrayList<>();
/* 422 */               int[] kernels = new int[this.mKernelCount];
/* 423 */               int idx = 0;
                        for (int ct2 = 0; ct2 < this.mNodes.size(); ct2++) {
/* 425 */                   Node n = this.mNodes.get(ct2);
/* 426 */                   int ct3 = 0;
                            while (ct3 < n.mKernels.size()) {
/* 427 */                       Script.KernelID kid = n.mKernels.get(ct3);
/* 428 */                       int idx2 = idx + 1;
                                kernels[idx] = kid.getID(this.mRS);
/* 430 */                       boolean hasInput = false;
/* 431 */                       boolean hasOutput = false;
                                for (int ct4 = 0; ct4 < n.mInputs.size(); ct4++) {
/* 433 */                           if (n.mInputs.get(ct4).mToK == kid) {
/* 434 */                               hasInput = true;
                                    }
                                }
                                for (int ct5 = 0; ct5 < n.mOutputs.size(); ct5++) {
/* 438 */                           if (n.mOutputs.get(ct5).mFrom == kid) {
/* 439 */                               hasOutput = true;
                                    }
                                }
/* 442 */                       if (!hasInput) {
/* 443 */                           inputs.add(new C0129IO(kid));
                                }
/* 445 */                       if (!hasOutput) {
/* 446 */                           outputs.add(new C0129IO(kid));
                                }
/* 426 */                       ct3++;
                                idx = idx2;
                            }
                        }
/* 451 */               if (idx != this.mKernelCount) {
/* 452 */                   throw new RSRuntimeException("Count mismatch, should not happen.");
                        }
/* 455 */               int[] src = new int[this.mLines.size()];
/* 456 */               int[] dstk = new int[this.mLines.size()];
/* 457 */               int[] dstf = new int[this.mLines.size()];
/* 458 */               int[] types = new int[this.mLines.size()];
                        for (int ct6 = 0; ct6 < this.mLines.size(); ct6++) {
/* 461 */                   ConnectLine cl = this.mLines.get(ct6);
/* 462 */                   src[ct6] = cl.mFrom.getID(this.mRS);
/* 463 */                   if (cl.mToK != null) {
/* 464 */                       dstk[ct6] = cl.mToK.getID(this.mRS);
                            }
/* 466 */                   if (cl.mToF != null) {
/* 467 */                       dstf[ct6] = cl.mToF.getID(this.mRS);
                            }
/* 469 */                   types[ct6] = cl.mAllocationType.getID(this.mRS);
                        }
/* 472 */               int id = this.mRS.nScriptGroupCreate(kernels, src, dstk, dstf, types);
/* 473 */               if (id == 0) {
/* 474 */                   throw new RSRuntimeException("Object creation error, should not happen.");
                        }
/* 477 */               ScriptGroup sg = new ScriptGroup(id, this.mRS);
/* 478 */               sg.mOutputs = new C0129IO[outputs.size()];
                        for (int ct7 = 0; ct7 < outputs.size(); ct7++) {
/* 480 */                   sg.mOutputs[ct7] = outputs.get(ct7);
                        }
/* 483 */               sg.mInputs = new C0129IO[inputs.size()];
                        for (int ct8 = 0; ct8 < inputs.size(); ct8++) {
/* 485 */                   sg.mInputs[ct8] = inputs.get(ct8);
                        }
/* 488 */               return sg;
                    }
                }
            }
