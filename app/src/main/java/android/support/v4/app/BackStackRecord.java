            package android.support.v4.app;

            import android.support.v4.util.LogWriter;
            import android.util.Log;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 184 */   final class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, Runnable {
                static final int OP_ADD = 1;
                static final int OP_ATTACH = 7;
                static final int OP_DETACH = 6;
                static final int OP_HIDE = 4;
                static final int OP_NULL = 0;
                static final int OP_REMOVE = 3;
                static final int OP_REPLACE = 2;
                static final int OP_SHOW = 5;
                static final String TAG = "FragmentManager";
                boolean mAddToBackStack;
                int mBreadCrumbShortTitleRes;
                CharSequence mBreadCrumbShortTitleText;
                int mBreadCrumbTitleRes;
                CharSequence mBreadCrumbTitleText;
                boolean mCommitted;
                int mEnterAnim;
                int mExitAnim;
                C0003Op mHead;
                final FragmentManagerImpl mManager;
                String mName;
                int mNumOp;
                int mPopEnterAnim;
                int mPopExitAnim;
                C0003Op mTail;
                int mTransition;
                int mTransitionStyle;
                boolean mAllowAddToBackStack = true;
                int mIndex = -1;

                /* JADX INFO: renamed from: android.support.v4.app.BackStackRecord$Op */
/* 185 */       static final class C0003Op {
                    int cmd;
                    int enterAnim;
                    int exitAnim;
                    Fragment fragment;
                    C0003Op next;
                    int popEnterAnim;
                    int popExitAnim;
                    C0003Op prev;
                    ArrayList<Fragment> removed;

/* 186 */           C0003Op() {
                    }
                }

/* 220 */       public String toString() {
/* 221 */           StringBuilder sb = new StringBuilder(128);
/* 222 */           sb.append("BackStackEntry{");
/* 223 */           sb.append(Integer.toHexString(System.identityHashCode(this)));
/* 224 */           if (this.mIndex >= 0) {
/* 225 */               sb.append(" #");
/* 226 */               sb.append(this.mIndex);
                    }
/* 228 */           if (this.mName != null) {
/* 229 */               sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 230 */               sb.append(this.mName);
                    }
/* 232 */           sb.append("}");
/* 233 */           return sb.toString();
                }

/* 236 */       public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 237 */           dump(prefix, writer, true);
                }

/* 240 */       public void dump(String prefix, PrintWriter writer, boolean full) {
                    String cmdStr;
/* 241 */           if (full) {
/* 242 */               writer.print(prefix);
                        writer.print("mName=");
                        writer.print(this.mName);
/* 243 */               writer.print(" mIndex=");
                        writer.print(this.mIndex);
/* 244 */               writer.print(" mCommitted=");
                        writer.println(this.mCommitted);
/* 245 */               if (this.mTransition != 0) {
/* 246 */                   writer.print(prefix);
                            writer.print("mTransition=#");
/* 247 */                   writer.print(Integer.toHexString(this.mTransition));
/* 248 */                   writer.print(" mTransitionStyle=#");
/* 249 */                   writer.println(Integer.toHexString(this.mTransitionStyle));
                        }
/* 251 */               if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
/* 252 */                   writer.print(prefix);
                            writer.print("mEnterAnim=#");
/* 253 */                   writer.print(Integer.toHexString(this.mEnterAnim));
/* 254 */                   writer.print(" mExitAnim=#");
/* 255 */                   writer.println(Integer.toHexString(this.mExitAnim));
                        }
/* 257 */               if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
/* 258 */                   writer.print(prefix);
                            writer.print("mPopEnterAnim=#");
/* 259 */                   writer.print(Integer.toHexString(this.mPopEnterAnim));
/* 260 */                   writer.print(" mPopExitAnim=#");
/* 261 */                   writer.println(Integer.toHexString(this.mPopExitAnim));
                        }
/* 263 */               if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
/* 264 */                   writer.print(prefix);
                            writer.print("mBreadCrumbTitleRes=#");
/* 265 */                   writer.print(Integer.toHexString(this.mBreadCrumbTitleRes));
/* 266 */                   writer.print(" mBreadCrumbTitleText=");
/* 267 */                   writer.println(this.mBreadCrumbTitleText);
                        }
/* 269 */               if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
/* 270 */                   writer.print(prefix);
                            writer.print("mBreadCrumbShortTitleRes=#");
/* 271 */                   writer.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
/* 272 */                   writer.print(" mBreadCrumbShortTitleText=");
/* 273 */                   writer.println(this.mBreadCrumbShortTitleText);
                        }
                    }
/* 277 */           if (this.mHead != null) {
/* 278 */               writer.print(prefix);
                        writer.println("Operations:");
/* 279 */               String innerPrefix = prefix + "    ";
/* 280 */               C0003Op op = this.mHead;
/* 281 */               int num = 0;
/* 282 */               while (op != null) {
                            switch (op.cmd) {
                                case 0:
/* 285 */                           cmdStr = "NULL";
                                    break;
                                case 1:
/* 286 */                           cmdStr = "ADD";
                                    break;
                                case 2:
/* 287 */                           cmdStr = "REPLACE";
                                    break;
                                case 3:
/* 288 */                           cmdStr = "REMOVE";
                                    break;
                                case 4:
/* 289 */                           cmdStr = "HIDE";
                                    break;
                                case 5:
/* 290 */                           cmdStr = "SHOW";
                                    break;
                                case 6:
/* 291 */                           cmdStr = "DETACH";
                                    break;
                                case 7:
/* 292 */                           cmdStr = "ATTACH";
                                    break;
                                default:
/* 293 */                           cmdStr = "cmd=" + op.cmd;
                                    break;
                            }
/* 295 */                   writer.print(prefix);
                            writer.print("  Op #");
                            writer.print(num);
/* 296 */                   writer.print(": ");
                            writer.print(cmdStr);
/* 297 */                   writer.print(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                            writer.println(op.fragment);
/* 298 */                   if (full) {
/* 299 */                       if (op.enterAnim != 0 || op.exitAnim != 0) {
/* 300 */                           writer.print(prefix);
                                    writer.print("enterAnim=#");
/* 301 */                           writer.print(Integer.toHexString(op.enterAnim));
/* 302 */                           writer.print(" exitAnim=#");
/* 303 */                           writer.println(Integer.toHexString(op.exitAnim));
                                }
/* 305 */                       if (op.popEnterAnim != 0 || op.popExitAnim != 0) {
/* 306 */                           writer.print(prefix);
                                    writer.print("popEnterAnim=#");
/* 307 */                           writer.print(Integer.toHexString(op.popEnterAnim));
/* 308 */                           writer.print(" popExitAnim=#");
/* 309 */                           writer.println(Integer.toHexString(op.popExitAnim));
                                }
                            }
/* 312 */                   if (op.removed != null && op.removed.size() > 0) {
                                for (int i = 0; i < op.removed.size(); i++) {
/* 314 */                           writer.print(innerPrefix);
/* 315 */                           if (op.removed.size() == 1) {
/* 316 */                               writer.print("Removed: ");
                                    } else {
/* 318 */                               if (i == 0) {
/* 319 */                                   writer.println("Removed:");
                                        }
/* 321 */                               writer.print(innerPrefix);
                                        writer.print("  #");
                                        writer.print(i);
/* 322 */                               writer.print(": ");
                                    }
/* 324 */                           writer.println(op.removed.get(i));
                                }
                            }
/* 327 */                   op = op.next;
/* 328 */                   num++;
                        }
                    }
                }

/* 332 */       public BackStackRecord(FragmentManagerImpl manager) {
/* 334 */           this.mManager = manager;
                }

                @Override // android.support.v4.app.FragmentManager.BackStackEntry
/* 337 */       public int getId() {
/* 338 */           return this.mIndex;
                }

                @Override // android.support.v4.app.FragmentManager.BackStackEntry
/* 341 */       public int getBreadCrumbTitleRes() {
/* 342 */           return this.mBreadCrumbTitleRes;
                }

                @Override // android.support.v4.app.FragmentManager.BackStackEntry
/* 345 */       public int getBreadCrumbShortTitleRes() {
/* 346 */           return this.mBreadCrumbShortTitleRes;
                }

                @Override // android.support.v4.app.FragmentManager.BackStackEntry
/* 349 */       public CharSequence getBreadCrumbTitle() {
                    return this.mBreadCrumbTitleRes != 0 ? this.mManager.mActivity.getText(this.mBreadCrumbTitleRes) : this.mBreadCrumbTitleText;
                }

                @Override // android.support.v4.app.FragmentManager.BackStackEntry
/* 356 */       public CharSequence getBreadCrumbShortTitle() {
                    return this.mBreadCrumbShortTitleRes != 0 ? this.mManager.mActivity.getText(this.mBreadCrumbShortTitleRes) : this.mBreadCrumbShortTitleText;
                }

/* 363 */       void addOp(C0003Op op) {
/* 364 */           if (this.mHead == null) {
/* 365 */               this.mTail = op;
                        this.mHead = op;
                    } else {
/* 367 */               op.prev = this.mTail;
/* 368 */               this.mTail.next = op;
/* 369 */               this.mTail = op;
                    }
/* 371 */           op.enterAnim = this.mEnterAnim;
/* 372 */           op.exitAnim = this.mExitAnim;
/* 373 */           op.popEnterAnim = this.mPopEnterAnim;
/* 374 */           op.popExitAnim = this.mPopExitAnim;
/* 375 */           this.mNumOp++;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 378 */       public FragmentTransaction add(Fragment fragment, String tag) {
/* 379 */           doAddOp(0, fragment, tag, 1);
/* 380 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 383 */       public FragmentTransaction add(int containerViewId, Fragment fragment) {
/* 384 */           doAddOp(containerViewId, fragment, null, 1);
/* 385 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 388 */       public FragmentTransaction add(int containerViewId, Fragment fragment, String tag) {
/* 389 */           doAddOp(containerViewId, fragment, tag, 1);
/* 390 */           return this;
                }

/* 393 */       private void doAddOp(int containerViewId, Fragment fragment, String tag, int opcmd) {
/* 394 */           fragment.mFragmentManager = this.mManager;
/* 396 */           if (tag != null) {
/* 397 */               if (fragment.mTag != null && !tag.equals(fragment.mTag)) {
/* 398 */                   throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + tag);
                        }
/* 402 */               fragment.mTag = tag;
                    }
/* 405 */           if (containerViewId != 0) {
/* 406 */               if (fragment.mFragmentId != 0 && fragment.mFragmentId != containerViewId) {
/* 407 */                   throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + containerViewId);
                        }
/* 411 */               fragment.mFragmentId = containerViewId;
                        fragment.mContainerId = containerViewId;
                    }
/* 414 */           C0003Op op = new C0003Op();
/* 415 */           op.cmd = opcmd;
/* 416 */           op.fragment = fragment;
/* 417 */           addOp(op);
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 420 */       public FragmentTransaction replace(int containerViewId, Fragment fragment) {
/* 421 */           return replace(containerViewId, fragment, null);
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 424 */       public FragmentTransaction replace(int containerViewId, Fragment fragment, String tag) {
/* 425 */           if (containerViewId == 0) {
/* 426 */               throw new IllegalArgumentException("Must use non-zero containerViewId");
                    }
/* 429 */           doAddOp(containerViewId, fragment, tag, 2);
/* 430 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 433 */       public FragmentTransaction remove(Fragment fragment) {
/* 434 */           C0003Op op = new C0003Op();
/* 435 */           op.cmd = 3;
/* 436 */           op.fragment = fragment;
/* 437 */           addOp(op);
/* 439 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 442 */       public FragmentTransaction hide(Fragment fragment) {
/* 443 */           C0003Op op = new C0003Op();
/* 444 */           op.cmd = 4;
/* 445 */           op.fragment = fragment;
/* 446 */           addOp(op);
/* 448 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 451 */       public FragmentTransaction show(Fragment fragment) {
/* 452 */           C0003Op op = new C0003Op();
/* 453 */           op.cmd = 5;
/* 454 */           op.fragment = fragment;
/* 455 */           addOp(op);
/* 457 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 460 */       public FragmentTransaction detach(Fragment fragment) {
/* 461 */           C0003Op op = new C0003Op();
/* 462 */           op.cmd = 6;
/* 463 */           op.fragment = fragment;
/* 464 */           addOp(op);
/* 466 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 469 */       public FragmentTransaction attach(Fragment fragment) {
/* 470 */           C0003Op op = new C0003Op();
/* 471 */           op.cmd = 7;
/* 472 */           op.fragment = fragment;
/* 473 */           addOp(op);
/* 475 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 478 */       public FragmentTransaction setCustomAnimations(int enter, int exit) {
/* 479 */           return setCustomAnimations(enter, exit, 0, 0);
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 483 */       public FragmentTransaction setCustomAnimations(int enter, int exit, int popEnter, int popExit) {
/* 484 */           this.mEnterAnim = enter;
/* 485 */           this.mExitAnim = exit;
/* 486 */           this.mPopEnterAnim = popEnter;
/* 487 */           this.mPopExitAnim = popExit;
/* 488 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 491 */       public FragmentTransaction setTransition(int transition) {
/* 492 */           this.mTransition = transition;
/* 493 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 496 */       public FragmentTransaction setTransitionStyle(int styleRes) {
/* 497 */           this.mTransitionStyle = styleRes;
/* 498 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 501 */       public FragmentTransaction addToBackStack(String name) {
/* 502 */           if (!this.mAllowAddToBackStack) {
/* 503 */               throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
                    }
/* 506 */           this.mAddToBackStack = true;
/* 507 */           this.mName = name;
/* 508 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 511 */       public boolean isAddToBackStackAllowed() {
/* 512 */           return this.mAllowAddToBackStack;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 515 */       public FragmentTransaction disallowAddToBackStack() {
/* 516 */           if (this.mAddToBackStack) {
/* 517 */               throw new IllegalStateException("This transaction is already being added to the back stack");
                    }
/* 520 */           this.mAllowAddToBackStack = false;
/* 521 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 524 */       public FragmentTransaction setBreadCrumbTitle(int res) {
/* 525 */           this.mBreadCrumbTitleRes = res;
/* 526 */           this.mBreadCrumbTitleText = null;
/* 527 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 530 */       public FragmentTransaction setBreadCrumbTitle(CharSequence text) {
/* 531 */           this.mBreadCrumbTitleRes = 0;
/* 532 */           this.mBreadCrumbTitleText = text;
/* 533 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 536 */       public FragmentTransaction setBreadCrumbShortTitle(int res) {
/* 537 */           this.mBreadCrumbShortTitleRes = res;
/* 538 */           this.mBreadCrumbShortTitleText = null;
/* 539 */           return this;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 542 */       public FragmentTransaction setBreadCrumbShortTitle(CharSequence text) {
/* 543 */           this.mBreadCrumbShortTitleRes = 0;
/* 544 */           this.mBreadCrumbShortTitleText = text;
/* 545 */           return this;
                }

/* 548 */       void bumpBackStackNesting(int amt) {
/* 549 */           if (this.mAddToBackStack) {
/* 552 */               if (FragmentManagerImpl.DEBUG) {
                            Log.v(TAG, "Bump nesting in " + this + " by " + amt);
                        }
/* 555 */               for (C0003Op op = this.mHead; op != null; op = op.next) {
/* 556 */                   if (op.fragment != null) {
                                op.fragment.mBackStackNesting += amt;
/* 558 */                       if (FragmentManagerImpl.DEBUG) {
                                    Log.v(TAG, "Bump nesting of " + op.fragment + " to " + op.fragment.mBackStackNesting);
                                }
                            }
/* 561 */                   if (op.removed != null) {
                                for (int i = op.removed.size() - 1; i >= 0; i--) {
/* 563 */                           Fragment r = op.removed.get(i);
/* 564 */                           r.mBackStackNesting += amt;
/* 565 */                           if (FragmentManagerImpl.DEBUG) {
                                        Log.v(TAG, "Bump nesting of " + r + " to " + r.mBackStackNesting);
                                    }
                                }
                            }
                        }
                    }
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 573 */       public int commit() {
/* 574 */           return commitInternal(false);
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 577 */       public int commitAllowingStateLoss() {
/* 578 */           return commitInternal(true);
                }

/* 581 */       int commitInternal(boolean allowStateLoss) {
/* 582 */           if (this.mCommitted) {
                        throw new IllegalStateException("commit already called");
                    }
/* 583 */           if (FragmentManagerImpl.DEBUG) {
/* 584 */               Log.v(TAG, "Commit: " + this);
/* 585 */               LogWriter logw = new LogWriter(TAG);
/* 586 */               PrintWriter pw = new PrintWriter(logw);
/* 587 */               dump("  ", null, pw, null);
                    }
/* 589 */           this.mCommitted = true;
/* 590 */           if (this.mAddToBackStack) {
/* 591 */               this.mIndex = this.mManager.allocBackStackIndex(this);
                    } else {
/* 593 */               this.mIndex = -1;
                    }
/* 595 */           this.mManager.enqueueAction(this, allowStateLoss);
/* 596 */           return this.mIndex;
                }

                @Override // java.lang.Runnable
/* 599 */       public void run() {
/* 600 */           if (FragmentManagerImpl.DEBUG) {
                        Log.v(TAG, "Run: " + this);
                    }
/* 602 */           if (this.mAddToBackStack && this.mIndex < 0) {
/* 604 */               throw new IllegalStateException("addToBackStack() called after commit()");
                    }
/* 608 */           bumpBackStackNesting(1);
/* 611 */           for (C0003Op op = this.mHead; op != null; op = op.next) {
                        switch (op.cmd) {
                            case 1:
/* 614 */                       Fragment f = op.fragment;
/* 615 */                       f.mNextAnim = op.enterAnim;
/* 616 */                       this.mManager.addFragment(f, false);
                                break;
                            case 2:
/* 619 */                       Fragment f2 = op.fragment;
/* 620 */                       if (this.mManager.mAdded != null) {
                                    for (int i = 0; i < this.mManager.mAdded.size(); i++) {
/* 622 */                               Fragment old = this.mManager.mAdded.get(i);
/* 623 */                               if (FragmentManagerImpl.DEBUG) {
                                            Log.v(TAG, "OP_REPLACE: adding=" + f2 + " old=" + old);
                                        }
/* 625 */                               if (f2 == null || old.mContainerId == f2.mContainerId) {
/* 626 */                                   if (old == f2) {
/* 627 */                                       f2 = null;
/* 627 */                                       op.fragment = null;
                                            } else {
/* 629 */                                       if (op.removed == null) {
/* 630 */                                           op.removed = new ArrayList<>();
                                                }
/* 632 */                                       op.removed.add(old);
/* 633 */                                       old.mNextAnim = op.exitAnim;
/* 634 */                                       if (this.mAddToBackStack) {
/* 635 */                                           old.mBackStackNesting++;
/* 636 */                                           if (FragmentManagerImpl.DEBUG) {
                                                        Log.v(TAG, "Bump nesting of " + old + " to " + old.mBackStackNesting);
                                                    }
                                                }
/* 639 */                                       this.mManager.removeFragment(old, this.mTransition, this.mTransitionStyle);
                                            }
                                        }
                                    }
                                }
/* 644 */                       if (f2 != null) {
/* 645 */                           f2.mNextAnim = op.enterAnim;
/* 646 */                           this.mManager.addFragment(f2, false);
                                }
                                break;
                            case 3:
/* 650 */                       Fragment f3 = op.fragment;
/* 651 */                       f3.mNextAnim = op.exitAnim;
/* 652 */                       this.mManager.removeFragment(f3, this.mTransition, this.mTransitionStyle);
                                break;
                            case 4:
/* 655 */                       Fragment f4 = op.fragment;
/* 656 */                       f4.mNextAnim = op.exitAnim;
/* 657 */                       this.mManager.hideFragment(f4, this.mTransition, this.mTransitionStyle);
                                break;
                            case 5:
/* 660 */                       Fragment f5 = op.fragment;
/* 661 */                       f5.mNextAnim = op.enterAnim;
/* 662 */                       this.mManager.showFragment(f5, this.mTransition, this.mTransitionStyle);
                                break;
                            case 6:
/* 665 */                       Fragment f6 = op.fragment;
/* 666 */                       f6.mNextAnim = op.exitAnim;
/* 667 */                       this.mManager.detachFragment(f6, this.mTransition, this.mTransitionStyle);
                                break;
                            case 7:
/* 670 */                       Fragment f7 = op.fragment;
/* 671 */                       f7.mNextAnim = op.enterAnim;
/* 672 */                       this.mManager.attachFragment(f7, this.mTransition, this.mTransitionStyle);
                                break;
                            default:
/* 675 */                       throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
                        }
                    }
/* 682 */           this.mManager.moveToState(this.mManager.mCurState, this.mTransition, this.mTransitionStyle, true);
/* 685 */           if (this.mAddToBackStack) {
/* 686 */               this.mManager.addBackStackState(this);
                    }
                }

/* 690 */       public void popFromBackStack(boolean doStateMove) {
/* 691 */           if (FragmentManagerImpl.DEBUG) {
/* 692 */               Log.v(TAG, "popFromBackStack: " + this);
/* 693 */               LogWriter logw = new LogWriter(TAG);
/* 694 */               PrintWriter pw = new PrintWriter(logw);
/* 695 */               dump("  ", null, pw, null);
                    }
/* 698 */           bumpBackStackNesting(-1);
/* 701 */           for (C0003Op op = this.mTail; op != null; op = op.prev) {
                        switch (op.cmd) {
                            case 1:
/* 704 */                       Fragment f = op.fragment;
/* 705 */                       f.mNextAnim = op.popExitAnim;
/* 706 */                       this.mManager.removeFragment(f, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
                                break;
                            case 2:
/* 711 */                       Fragment f2 = op.fragment;
/* 712 */                       if (f2 != null) {
/* 713 */                           f2.mNextAnim = op.popExitAnim;
/* 714 */                           this.mManager.removeFragment(f2, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
                                }
/* 718 */                       if (op.removed != null) {
                                    for (int i = 0; i < op.removed.size(); i++) {
/* 720 */                               Fragment old = op.removed.get(i);
/* 721 */                               old.mNextAnim = op.popEnterAnim;
/* 722 */                               this.mManager.addFragment(old, false);
                                    }
                                }
                                break;
                            case 3:
/* 727 */                       Fragment f3 = op.fragment;
/* 728 */                       f3.mNextAnim = op.popEnterAnim;
/* 729 */                       this.mManager.addFragment(f3, false);
                                break;
                            case 4:
/* 732 */                       Fragment f4 = op.fragment;
/* 733 */                       f4.mNextAnim = op.popEnterAnim;
/* 734 */                       this.mManager.showFragment(f4, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
                                break;
                            case 5:
/* 738 */                       Fragment f5 = op.fragment;
/* 739 */                       f5.mNextAnim = op.popExitAnim;
/* 740 */                       this.mManager.hideFragment(f5, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
                                break;
                            case 6:
/* 744 */                       Fragment f6 = op.fragment;
/* 745 */                       f6.mNextAnim = op.popEnterAnim;
/* 746 */                       this.mManager.attachFragment(f6, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
                                break;
                            case 7:
/* 750 */                       Fragment f7 = op.fragment;
/* 751 */                       f7.mNextAnim = op.popEnterAnim;
/* 752 */                       this.mManager.detachFragment(f7, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
                                break;
                            default:
/* 756 */                       throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
                        }
                    }
/* 763 */           if (doStateMove) {
/* 764 */               this.mManager.moveToState(this.mManager.mCurState, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle, true);
                    }
/* 768 */           if (this.mIndex >= 0) {
/* 769 */               this.mManager.freeBackStackIndex(this.mIndex);
/* 770 */               this.mIndex = -1;
                    }
                }

                @Override // android.support.v4.app.FragmentManager.BackStackEntry
/* 774 */       public String getName() {
/* 775 */           return this.mName;
                }

/* 778 */       public int getTransition() {
/* 779 */           return this.mTransition;
                }

/* 782 */       public int getTransitionStyle() {
/* 783 */           return this.mTransitionStyle;
                }

                @Override // android.support.v4.app.FragmentTransaction
/* 786 */       public boolean isEmpty() {
                    return this.mNumOp == 0;
                }
            }
