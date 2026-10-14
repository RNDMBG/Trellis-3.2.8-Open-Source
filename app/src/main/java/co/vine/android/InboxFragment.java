            package co.vine.android;

            import android.Manifest;
            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.Activity;
            import android.content.BroadcastReceiver;
            import android.content.ContentUris;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.os.Build;
            import android.content.pm.PackageManager;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ImageView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.Session;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.service.GCMNotificationService;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.ConversationMenuHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.StringAnchorManager;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.widget.ContactsMenuAdapter;
            import co.vine.android.widget.SectionAdapter;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.edisonwang.android.slog.SLog;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
            import com.twitter.android.widget.RefreshableListView;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public class InboxFragment extends BaseCursorListFragment implements View.OnClickListener, SlidingMenu.OnOpenedListener, SlidingMenu.OnClosedListener {
                protected static final int FETCH_FLAG_BOTH = 3;
                protected static final int FETCH_FLAG_FRIENDS = 2;
                protected static final int FETCH_FLAG_INBOX = 1;
                protected static final int FETCH_FLAG_OTHERS = 4;
                protected static final int LOADER_ID_CONTACTS = 3;
                protected static final int LOADER_ID_FRIENDS = 2;
                protected static final int LOADER_ID_INBOX = 1;
                protected static final int LOADER_ID_OTHERS = 4;
                protected static final int VIEW_TYPE_CONTACTS = 3;
                protected static final int VIEW_TYPE_FRIENDS = 2;
                protected static final int VIEW_TYPE_MESSAGES = 1;
                private BaseAdapter mActiveAdapter;
                private ContactsMenuAdapter mAddressAdapter;
                private Cursor mAddressCursor;
                private StringAnchorManager mAnchorManager;
                private int mColor;
                private ArrayList<ContactEntry> mContactEntries;
                private HashMap<Long, ContactEntry> mContactEntryMap;
                private long mCurrentLastId;
                private MessageBoxAdapter mDummyAdapter;
                private ImageView mEmptyImage;
                private TextView mEmptyTitle;
                private View mEmptyView;
                private TextView mEmptyWords;
                protected int mFetchFlags;
                private MenuUsersAdapter mFriendsAdapter;
                private TextView mFriendsText;
                protected MessageBoxAdapter mInboxAdapter;
                private int mInboxCursorCount;
                private boolean mIsAddressBookShowing;
                private boolean mIsEmptyViewAdded;
                private boolean mIsSwitching;
                protected long mMessageCount;
                private MessageBoxAdapter mOtherAdapter;
                private int mOthersCursorCount;
                private TextView mOthersText;
                private SectionAdapter mSectionAdapter;
                private SortMenuContactsTask mSortMenuTask;
                private Typefaces mTypefaces;
                private int mUnReadCount;
                protected long mWaitingToStartId = -1;
                private boolean mLoadingMore = false;
                private final BroadcastReceiver mColorChangedReceiver = new C02321();
                private final Runnable mInvalidateEmptyViewRunnable = new RunnableC02332();
                private final PromptDialogSupportFragment.OnDialogDoneListener mStoreContactDialogDoneListener = new C02343();
                private final Runnable mInvalidateContactEntryRunnable = new RunnableC02354();
                private final OnContactEntryClickedListener mListener = new C02365();

/* 59 */        static /* synthetic */ BaseAdapter access$100(InboxFragment x0) {
/* 60 */            return x0.mActiveAdapter;
                }

/* 59 */        static /* synthetic */ HashMap access$1000(InboxFragment x0) {
/* 60 */            return x0.mContactEntryMap;
                }

/* 59 */        static /* synthetic */ HashMap access$1002(InboxFragment x0, HashMap x1) {
/* 60 */            x0.mContactEntryMap = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ BaseAdapter access$102(InboxFragment x0, BaseAdapter x1) {
/* 60 */            x0.mActiveAdapter = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ ArrayList access$1102(InboxFragment x0, ArrayList x1) {
/* 60 */            x0.mContactEntries = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ void access$1200(InboxFragment x0) {
/* 60 */            x0.invalidateContactEntries();
                }

/* 59 */        static /* synthetic */ boolean access$1300(InboxFragment x0) {
/* 60 */            return x0.mIsAddressBookShowing;
                }

/* 59 */        static /* synthetic */ boolean access$1402(InboxFragment x0, boolean x1) {
/* 60 */            x0.mLoadingMore = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ SectionAdapter access$200(InboxFragment x0) {
/* 60 */            return x0.mSectionAdapter;
                }

/* 59 */        static /* synthetic */ int access$300(InboxFragment x0) {
/* 60 */            return x0.mInboxCursorCount;
                }

/* 59 */        static /* synthetic */ boolean access$400(InboxFragment x0) {
/* 60 */            return x0.mIsEmptyViewAdded;
                }

/* 59 */        static /* synthetic */ boolean access$402(InboxFragment x0, boolean x1) {
/* 60 */            x0.mIsEmptyViewAdded = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ View access$500(InboxFragment x0) {
/* 60 */            return x0.mEmptyView;
                }

/* 59 */        static /* synthetic */ int access$600(InboxFragment x0) {
/* 60 */            return x0.mOthersCursorCount;
                }

/* 59 */        static /* synthetic */ boolean access$700(InboxFragment x0) {
/* 60 */            return x0.mIsSwitching;
                }

/* 59 */        static /* synthetic */ boolean access$702(InboxFragment x0, boolean x1) {
/* 60 */            x0.mIsSwitching = x1;
                    return x1;
                }

/* 59 */        static /* synthetic */ void access$800(InboxFragment x0) {
/* 60 */            x0.invalidateAdapters();
                }

/* 59 */        static /* synthetic */ SortMenuContactsTask access$902(InboxFragment x0, SortMenuContactsTask x1) {
/* 60 */            x0.mSortMenuTask = x1;
                    return x1;
                }


                /* JADX INFO: renamed from: co.vine.android.InboxFragment$1 */
/* 108 */       class C02321 extends BroadcastReceiver {
/* 109 */           C02321() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 112 */           public void onReceive(Context context, Intent intent) {
/* 113 */               int color = Util.getDefaultSharedPrefs(InboxFragment.this.getActivity()).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 116 */               ((ConversationMenuHelper) ((BaseActionBarActivity) InboxFragment.this.getActivity()).getSlidingMenuHelper()).setPersonalizedColor(color);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 123 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 124 */           return createView(inflater, C0257R.layout.friends_menu_list_fragment, container);
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Can't rename method to resolve collision */
                /* JADX WARN: Code duplicated, block: B:30:0x0088  */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 128 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    switch (loader.getId()) {
                        case 1:
/* 162 */                   if (cursor != null) {
/* 163 */                       onLoadMessageBoxComplete(loader.getId(), cursor);
                            }
                            break;
                        case 2:
/* 131 */                   Cursor oldCursor = this.mFriendsAdapter.getCursor();
/* 132 */                   if (oldCursor != null) {
/* 133 */                       oldCursor.unregisterContentObserver(this.mChangeObserver);
                            }
/* 135 */                   if (cursor != null) {
/* 136 */                       cursor.registerContentObserver(this.mChangeObserver);
/* 137 */                       if (cursor.moveToLast()) {
/* 138 */                           this.mCurrentLastId = cursor.getLong(1);
/* 139 */                           cursor.moveToFirst();
                                }
                            }
/* 142 */                   this.mFriendsAdapter.swapCursor(cursor);
/* 143 */                   if (this.mFriendsAdapter.isEmpty() && !isFetched(2)) {
/* 144 */                       fetchContent(3, 2, true);
                            } else {
/* 146 */                       makeSadFaceHeaderView();
/* 147 */                       hideProgress(3);
                            }
                            break;
                        case 3:
/* 168 */                   Cursor oldCursor2 = this.mAddressCursor;
/* 169 */                   if (oldCursor2 != null) {
/* 170 */                       oldCursor2.unregisterContentObserver(this.mChangeObserver);
                            }
/* 172 */                   if (cursor != null) {
/* 173 */                       cursor.registerContentObserver(this.mChangeObserver);
                            }
/* 175 */                   if (cursor != null) {
/* 176 */                       this.mAddressCursor = cursor;
/* 177 */                       if (this.mSortMenuTask == null) {
/* 178 */                           this.mSortMenuTask = new SortMenuContactsTask(this, null);
/* 179 */                           this.mSortMenuTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Cursor[]{this.mAddressCursor});
                                }
                            }
                            break;
                        case 4:
/* 152 */                   this.mUnReadCount = 0;
/* 153 */                   if (cursor != null && cursor.moveToFirst()) {
/* 156 */                       do {
/* 155 */                           this.mUnReadCount += cursor.getInt(4);
/* 156 */                       } while (cursor.moveToNext());
/* 157 */                       cursor.moveToFirst();
                            }
/* 159 */                   invalidateUnreadCount();
/* 162 */                   if (cursor != null) {
/* 163 */                       onLoadMessageBoxComplete(loader.getId(), cursor);
                            }
                            break;
                    }
/* 185 */           SLog.m447d("Load finished: {}.", Integer.valueOf(loader.getId()));
/* 186 */           invalidateEmptyView(false);
/* 187 */           if (loader.getId() != 3) {
/* 188 */               invalidateContactEntries();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.InboxFragment$2 */
/* 190 */       class RunnableC02332 implements Runnable {
/* 191 */           RunnableC02332() {
                    }

                    @Override // java.lang.Runnable
/* 194 */           public void run() {
/* 195 */               if (InboxFragment.access$100(InboxFragment.this) == InboxFragment.access$200(InboxFragment.this)) {
/* 196 */                   if (InboxFragment.access$300(InboxFragment.this) == 0) {
/* 197 */                       if (!InboxFragment.access$400(InboxFragment.this)) {
/* 198 */                           SLog.m446d("Header change: Add.");
/* 199 */                           InboxFragment.access$402(InboxFragment.this, true);
/* 200 */                           InboxFragment.this.mListView.addHeaderView(InboxFragment.access$500(InboxFragment.this));
/* 224 */                           return;
                                }
/* 224 */                       return;
                            }
/* 203 */                   if (InboxFragment.access$400(InboxFragment.this)) {
/* 204 */                       SLog.m446d("Header change: Remove.");
/* 205 */                       InboxFragment.access$402(InboxFragment.this, false);
/* 206 */                       InboxFragment.this.mListView.removeHeaderView(InboxFragment.access$500(InboxFragment.this));
/* 224 */                       return;
                            }
/* 224 */                   return;
                        }
/* 210 */               if (InboxFragment.access$600(InboxFragment.this) == 0) {
/* 211 */                   if (!InboxFragment.access$400(InboxFragment.this)) {
/* 212 */                       SLog.m446d("Header change: Add.");
/* 213 */                       InboxFragment.access$402(InboxFragment.this, true);
/* 214 */                       InboxFragment.this.mListView.addHeaderView(InboxFragment.access$500(InboxFragment.this));
/* 224 */                       return;
                            }
/* 224 */                   return;
                        }
/* 217 */               if (InboxFragment.access$400(InboxFragment.this)) {
/* 218 */                   SLog.m446d("Header change: Remove.");
/* 219 */                   InboxFragment.access$402(InboxFragment.this, false);
/* 220 */                   InboxFragment.this.mListView.removeHeaderView(InboxFragment.access$500(InboxFragment.this));
                        }
                    }
                }

/* 227 */       private synchronized void invalidateEmptyView(boolean immediate) {
                    try {
                        this.mHandler.removeCallbacks(this.mInvalidateEmptyViewRunnable);
/* 229 */               if (immediate) {
/* 230 */                   this.mInvalidateEmptyViewRunnable.run();
                        } else {
/* 232 */                   this.mHandler.postDelayed(this.mInvalidateEmptyViewRunnable, 100L);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 236 */       private void invalidateUnreadCount() {
/* 237 */           String text = null;
/* 238 */           if (this.mUnReadCount > 0) {
/* 239 */               if (this.mUnReadCount > 20) {
/* 240 */                   text = "20+";
                        } else {
/* 242 */                   text = String.valueOf(this.mUnReadCount);
                        }
                    }
/* 245 */           if (this.mOthersText != null) {
/* 246 */               String currentText = this.mOthersText.getText().toString();
/* 247 */               int index = currentText.indexOf("(");
/* 248 */               if (text != null) {
/* 249 */                   TextView textView = this.mOthersText;
                            StringBuilder sb = new StringBuilder();
                            if (index != -1) {
/* 249 */                       currentText = currentText.substring(0, index - 1);
                            }
                            textView.setText(sb.append(currentText).append(" (").append(text).append(") ").toString());
/* 257 */                   return;
                        }
/* 252 */               if (index != -1) {
/* 253 */                   this.mOthersText.setText(currentText.substring(0, index - 1));
                        }
                    }
                }

/* 259 */       public void onLoadMessageBoxComplete(int loaderId, Cursor cursor) {
/* 260 */           MessageBoxAdapter adapter = loaderId == 4 ? this.mOtherAdapter : this.mInboxAdapter;
/* 261 */           int flag = loaderId == 4 ? 4 : 1;
/* 263 */           Cursor oldCursor = adapter.getCursor();
/* 265 */           if (cursor != null) {
/* 266 */               if (loaderId == 4) {
/* 267 */                   this.mOthersCursorCount = cursor.getCount();
                        } else {
/* 269 */                   this.mInboxCursorCount = cursor.getCount();
                        }
/* 271 */               if (oldCursor != null) {
/* 272 */                   oldCursor.unregisterContentObserver(this.mChangeObserver);
                        }
/* 274 */               cursor.registerContentObserver(this.mChangeObserver);
                    }
/* 277 */           adapter.swapCursor(cursor);
/* 278 */           if (adapter.isEmpty()) {
/* 279 */               if (!isFetched(flag)) {
/* 280 */                   fetchContent(3, flag, true);
/* 287 */                   return;
                        } else {
/* 282 */                   showSadface(true);
/* 287 */                   return;
                        }
                    }
/* 285 */           hideProgress(3);
                }

/* 289 */       protected void fetchContent(int fetchType, int fetchMode, boolean silent) {
                    int pageType;
/* 290 */           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 1:
/* 306 */                       pageType = 3;
                                break;
                            case 2:
                            case 4:
/* 302 */                       pageType = 1;
                                break;
                            case 3:
/* 297 */                       pageType = 1;
                                break;
                            default:
/* 310 */                       pageType = 1;
                                break;
                        }
/* 315 */               if (pageType == 3 && fetchMode == 1) {
/* 316 */                   this.mLoadingMore = true;
                        }
/* 319 */               if ((fetchMode & 4) != 0) {
/* 320 */                   setFetched(4);
/* 321 */                   addRequest(this.mAppController.fetchConversations(pageType, false, 2), fetchType);
                        }
/* 325 */               if ((fetchMode & 1) != 0) {
/* 326 */                   setFetched(1);
/* 327 */                   addRequest(this.mAppController.fetchConversations(pageType, false, 1), fetchType);
                        }
/* 330 */               if ((fetchMode & 2) != 0) {
/* 331 */                   setFetched(2);
/* 332 */                   addRequest(this.mAppController.fetchFriends(pageType, 1), fetchType);
                        }
/* 335 */               if (!silent) {
/* 336 */                   showProgress(fetchType);
                        }
                    }
                }

/* 340 */       protected boolean isFetched(int mode) {
                    return (this.mFetchFlags & mode) != 0;
                }

/* 344 */       protected void setFetched(int mode) {
/* 345 */           this.mFetchFlags |= mode;
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 351 */       public void onCreate(Bundle savedInstanceState) {
/* 352 */           super.onCreate(savedInstanceState);
/* 353 */           this.mAppSessionListener = new ConversationMenuSessionListener();
/* 354 */           this.mMessageCount = 10L;
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 358 */       public View createView(LayoutInflater inflater, int id, ViewGroup container) {
/* 359 */           View root = super.createView(inflater, id, container);
/* 360 */           View header = getActivity().getLayoutInflater().inflate(C0257R.layout.menu_header, (ViewGroup) this.mListView, false);
/* 361 */           this.mFriendsText = (TextView) header.findViewById(C0257R.id.friends);
/* 362 */           this.mOthersText = (TextView) header.findViewById(C0257R.id.others);
/* 363 */           this.mListView.addHeaderView(header, null, false);
/* 364 */           View emptyHeader = inflater.inflate(C0257R.layout.vm_friends_empty, (ViewGroup) null);
/* 365 */           this.mEmptyView = emptyHeader;
/* 366 */           this.mEmptyImage = (ImageView) this.mEmptyView.findViewById(C0257R.id.sadface);
/* 367 */           this.mEmptyWords = (TextView) this.mEmptyView.findViewById(C0257R.id.sadwords);
/* 368 */           this.mEmptyTitle = (TextView) this.mEmptyView.findViewById(C0257R.id.sadTitle);
/* 369 */           return root;
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener
/* 373 */       public void onClosed() {
/* 374 */           this.mMessageCount = 10L;
/* 375 */           this.mAppController.clearInboxPageCursors();
/* 376 */           this.mListView.setSelectionFromTop(0, 0);
/* 377 */           FragmentActivity activity = getActivity();
/* 378 */           if (activity != null) {
/* 379 */               restartLoader();
/* 380 */               ConversationMenuHelper helper = (ConversationMenuHelper) ((BaseActionBarActivity) activity).getSlidingMenuHelper();
/* 382 */               helper.toggleContactSearch(false);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.InboxFragment$3 */
/* 385 */       class C02343 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 386 */           C02343() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 389 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
/* 390 */               FragmentActivity activity = InboxFragment.this.getActivity();
/* 391 */               if (activity != null) {
/* 392 */                   boolean enable = which == -1;
/* 393 */                   if (enable) {
                                InboxFragment.this.mAppController.sendAddressBook();
                            }
/* 394 */                   InboxFragment.this.mAppController.updateEnableAddressBook(enable);
/* 395 */                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(activity).edit();
/* 396 */                   editor.putBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, enable).apply();
                        }
                    }
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener
/* 402 */       public void onOpened() {
/* 403 */           Activity a = getActivity();
/* 404 */           if (a != null) {
/* 405 */               a.startService(GCMNotificationService.getClearNotificationIntent(a, 2));
                    }
/* 407 */           if (a instanceof HomeTabActivity) {
/* 408 */               if (!MuteUtil.isMuted(a)) {
/* 409 */                   a.sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_MUTE), CrossConstants.BROADCAST_PERMISSION);
                        }
/* 412 */               AccountManager am = AccountManager.get(a);
/* 413 */               Account account = VineAccountHelper.getActiveAccount(a);
/* 414 */               if (account != null && VineAccountHelper.shouldShowStoreContactsPrompt(am, account)) {
/* 416 */                   PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
/* 418 */                   p.setListener(this.mStoreContactDialogDoneListener);
/* 419 */                   p.setMessage(C0257R.string.store_contacts_desc);
/* 420 */                   p.setTitle(C0257R.string.store_contacts);
/* 421 */                   p.setNegativeButton(C0257R.string.f87no);
/* 422 */                   p.setPositiveButton(C0257R.string.f88ok);
                            try {
/* 424 */                       p.show(((HomeTabActivity) a).getSupportFragmentManager());
/* 425 */                       VineAccountHelper.setDidShowStoreContactsPrompt(am, account);
                            } catch (Exception e) {
/* 427 */                       CrashUtil.logException(e, "Failed to show contact agreement box. ", new Object[0]);
                            }
                        }
                    }
                }

/* 434 */       public void setPersonalizedColor(int profileBackground) {
/* 435 */           this.mColor = profileBackground;
/* 436 */           if (profileBackground == Settings.DEFAULT_PROFILE_COLOR || profileBackground <= 0) {
/* 437 */               profileBackground = 16777215 & getResources().getColor(C0257R.color.vine_green);
                    }
/* 439 */           if (this.mListView != null) {
/* 440 */               ((RefreshableListView) this.mListView).colorizePTR((-16777216) | profileBackground);
                    }
                }

/* 444 */       public boolean isLoadingMore() {
/* 445 */           return this.mLoadingMore;
                }

/* 450 */       public class TabSwicher implements View.OnClickListener, Runnable {
                    public final BaseAdapter targetAdapter;

/* 451 */           public TabSwicher(BaseAdapter target) {
/* 453 */               this.targetAdapter = target;
                    }

                    @Override // android.view.View.OnClickListener
/* 457 */           public void onClick(View v) {
/* 458 */               if (InboxFragment.access$100(InboxFragment.this) != this.targetAdapter && !InboxFragment.access$700(InboxFragment.this)) {
/* 459 */                   InboxFragment.access$702(InboxFragment.this, true);
/* 460 */                   InboxFragment.this.mHandler.removeCallbacks(this);
/* 461 */                   InboxFragment.this.mHandler.postDelayed(this, 200L);
                        }
                    }

                    @Override // java.lang.Runnable
/* 466 */           public void run() {
/* 467 */               InboxFragment.access$102(InboxFragment.this, this.targetAdapter);
/* 468 */               InboxFragment.this.mListView.setAdapter((ListAdapter) InboxFragment.access$100(InboxFragment.this));
/* 469 */               InboxFragment.access$702(InboxFragment.this, false);
/* 470 */               InboxFragment.access$800(InboxFragment.this);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 475 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 476 */           super.onActivityCreated(savedInstanceState);
/* 477 */           this.mTypefaces = Typefaces.get(getActivity());
/* 478 */           int profileColor = Util.getDefaultSharedPrefs(getActivity()).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 479 */           if (profileColor == Settings.DEFAULT_PROFILE_COLOR || profileColor <= 0) {
/* 480 */               profileColor = 16777215 & getResources().getColor(C0257R.color.vine_green);
                    }
/* 482 */           if (this.mInboxAdapter == null) {
/* 483 */               this.mInboxAdapter = new MessageBoxAdapter(getActivity(), this.mAppController, this, 0);
                    }
/* 485 */           if (this.mDummyAdapter == null) {
/* 486 */               this.mDummyAdapter = new MessageBoxAdapter(getActivity(), this.mAppController, this, 0);
                    }
/* 488 */           if (this.mFriendsAdapter == null) {
/* 489 */               this.mFriendsAdapter = new MenuUsersAdapter(getActivity(), this.mAppController, 0);
/* 490 */               this.mFriendsAdapter.setInboxAdapter(this.mInboxAdapter);
                    }
/* 492 */           if (this.mAddressAdapter == null) {
/* 493 */               this.mAddressAdapter = new ContactsMenuAdapter(getActivity());
                    }
/* 495 */           if (this.mOtherAdapter == null) {
/* 496 */               this.mOtherAdapter = new MessageBoxAdapter(getActivity(), this.mAppController, this, 0);
                    }
/* 499 */           this.mSectionAdapter = new SectionAdapter(null, null, new int[]{0, 0, 0}, new BaseAdapter[]{this.mInboxAdapter, this.mFriendsAdapter, this.mAddressAdapter}, C0257R.layout.menu_section_divider, 1);
/* 503 */           this.mFriendsText.setOnClickListener(new TabSwicher(this.mSectionAdapter));
/* 504 */           this.mOthersText.setOnClickListener(new TabSwicher(this.mOtherAdapter));
/* 506 */           this.mActiveAdapter = this.mSectionAdapter;
/* 507 */           this.mListView.setAdapter((ListAdapter) this.mActiveAdapter);
/* 508 */           this.mListView.setOnScrollListener(this);
/* 509 */           ((RefreshableListView) this.mListView).colorizePTR((-16777216) | profileColor);
/* 510 */           this.mAnchorManager = Vine.getUserSectionsAnchorManager(getActivity(), 1);
/* 511 */           invalidateAdapters();
                }

/* 514 */       private void invalidateAdapters() {
/* 515 */           if (this.mActiveAdapter == this.mOtherAdapter) {
/* 516 */               this.mFriendsText.setTextColor(CommonUtil.THIRTY_FIVE_ALPHA_MASK);
/* 517 */               this.mOthersText.setTextColor(-13421773);
/* 518 */               this.mFriendsText.setTypeface(this.mTypefaces.mediumContent);
/* 519 */               this.mOthersText.setTypeface(this.mTypefaces.boldContent);
/* 520 */               this.mCursorAdapter = null;
/* 521 */               this.mEmptyWords.setText(C0257R.string.vm_other_empty);
/* 522 */               this.mEmptyImage.setImageResource(C0257R.drawable.vm_watermark);
/* 523 */               this.mEmptyTitle.setVisibility(8);
/* 524 */               ViewUtil.setBackground(this.mListView, null);
                    } else {
/* 526 */               this.mOthersText.setTextColor(CommonUtil.THIRTY_FIVE_ALPHA_MASK);
/* 527 */               this.mFriendsText.setTextColor(-13421773);
/* 528 */               this.mOthersText.setTypeface(this.mTypefaces.mediumContent);
/* 529 */               this.mFriendsText.setTypeface(this.mTypefaces.boldContent);
/* 530 */               this.mCursorAdapter = this.mFriendsAdapter;
/* 531 */               this.mEmptyTitle.setVisibility(0);
/* 532 */               this.mEmptyWords.setText(C0257R.string.empty_inbox);
/* 533 */               this.mEmptyImage.setImageResource(C0257R.drawable.vm_roundel);
/* 534 */               this.mListView.setBackgroundColor(getResources().getColor(C0257R.color.solid_super_light_gray));
                    }
/* 536 */           invalidateEmptyView(true);
                }

/* 539 */       public void addEntriesToAddressAdapter() {
/* 540 */           if (this.mContactEntries != null) {
/* 541 */               SLog.m447d("Showing address book: {}.", Integer.valueOf(this.mContactEntries.size()));
/* 542 */               this.mIsAddressBookShowing = true;
/* 543 */               if (this.mAddressAdapter.getCount() != this.mContactEntries.size()) {
/* 544 */                   this.mAddressAdapter.clear();
/* 545 */                   this.mAddressAdapter.addAll(this.mContactEntries);
                        }
/* 547 */               makeSadFaceHeaderView();
                    }
                }

/* 549 */       private class SortMenuContactsTask extends SortContactsTask {
/* 550 */           private SortMenuContactsTask() {
                    }

/* 550 */           /* synthetic */ SortMenuContactsTask(InboxFragment x0, C02321 x1) {
/* 551 */               this();
                    }


                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 554 */           protected void onPostExecute(ArrayList<ContactEntry> entries) {
/* 555 */               InboxFragment.access$902(InboxFragment.this, null);
/* 556 */               InboxFragment.access$1002(InboxFragment.this, new HashMap());
                        for (ContactEntry entry : entries) {
/* 558 */                   InboxFragment.access$1000(InboxFragment.this).put(Long.valueOf(entry.contactId), entry);
                        }
/* 560 */               InboxFragment.access$1102(InboxFragment.this, entries);
/* 561 */               InboxFragment.access$1200(InboxFragment.this);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.InboxFragment$4 */
/* 563 */       class RunnableC02354 implements Runnable {
/* 564 */           RunnableC02354() {
                    }

                    @Override // java.lang.Runnable
/* 567 */           public void run() {
/* 568 */               if (InboxFragment.access$100(InboxFragment.this) == InboxFragment.access$200(InboxFragment.this)) {
/* 569 */                   int last = InboxFragment.this.mListView.getLastVisiblePosition();
/* 570 */                   boolean listViewFits = true;
                            try {
/* 572 */                       listViewFits = last == InboxFragment.this.mListView.getCount() + (-1) && InboxFragment.this.mListView.getChildAt(last).getBottom() <= InboxFragment.this.mListView.getHeight();
                            } catch (Exception e) {
/* 574 */                       SLog.m455e("Cannot get listview state.");
                            }
/* 577 */                   boolean isEmpty = false;
                            try {
/* 580 */                       isEmpty = InboxFragment.this.mInboxAdapter.isEmpty();
                            } catch (Exception e2) {
/* 582 */                       CrashUtil.logException(e2);
                            }
/* 585 */                   if (InboxFragment.access$1300(InboxFragment.this) || ((InboxFragment.this.isFetched(1) && isEmpty) || (InboxFragment.this.isFetched(3) && listViewFits))) {
/* 590 */                       InboxFragment.this.addEntriesToAddressAdapter();
                            }
                        }
                    }
                }

/* 596 */       private void invalidateContactEntries() {
/* 597 */           this.mHandler.removeCallbacks(this.mInvalidateContactEntryRunnable);
/* 598 */           this.mHandler.postDelayed(this.mInvalidateContactEntryRunnable, 100L);
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 602 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 2:
/* 606 */                   Uri contentUri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_FRIENDS, this.mAppController.getActiveId());
/* 608 */                   return new CursorLoader(getActivity(), contentUri, VineDatabaseSQL.UsersQuery.PROJECTION, "section_type=1", null, Vine.UserGroupsView.SORT_ORDER_FRIEND_REFRESH_FIRST_THEN_FRIEND_INDEX);
                        case 3:
/* 612 */                   if (!hasContactsPermission()) {
/* 613 */                       return new Loader<Cursor>(getActivity());
                            }
/* 614 */                   return new CursorLoader(getActivity(), ContactEntry.sUri, ContactEntry.sProjection, ContactEntry.sSelection, ContactEntry.sSelectionArgs, ContactEntry.sSortOrder);
                        default:
                            Uri contentUri2 = Vine.InboxView.CONTENT_URI.buildUpon().appendQueryParameter(Vine.InboxView.PARAM_HIDDEN, "0").appendQueryParameter("network_type", String.valueOf(id == 4 ? 2 : 1)).appendQueryParameter(Vine.QUERY_PARAMETER_LIMIT, String.valueOf(this.mMessageCount)).build();
/* 626 */                   return new CursorLoader(getActivity(), contentUri2, VineDatabaseSQL.InboxQuery.PROJECTION, null, null, null);
                    }
                }

                private boolean hasContactsPermission() {
                    if (Build.VERSION.SDK_INT < 23) {
                        return true;
                    }
                    Activity activity = getActivity();
                    return activity != null && activity.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
                }

/* 632 */       public void onInboxCursorClicked(Cursor inboxCursor, View item) {
/* 633 */           if (TextUtils.isEmpty(inboxCursor.getString(15)) || inboxCursor.getInt(14) <= 1) {
/* 635 */               long conversationObjectId = inboxCursor.getLong(1);
/* 637 */               String username = inboxCursor.getString(8);
/* 639 */               boolean amFollowing = inboxCursor.getInt(11) == 1;
/* 640 */               long userId = inboxCursor.getLong(10);
/* 641 */               boolean isUserExternal = inboxCursor.getInt(16) == 1;
/* 642 */               BaseActionBarActivity activity = (BaseActionBarActivity) getActivity();
/* 643 */               startActivity(ConversationActivity.getIntent(activity, conversationObjectId, username, userId, isUserExternal, amFollowing, false));
/* 653 */               return;
                    }
/* 646 */           this.mAppController.retryMessagesInConversationRowId(inboxCursor.getLong(1));
/* 648 */           TextView retryText = (TextView) item.findViewById(C0257R.id.tap_to_retry);
/* 649 */           retryText.setText(C0257R.string.message_sending);
/* 650 */           item.findViewById(C0257R.id.failed_upload).setVisibility(8);
/* 651 */           item.findViewById(C0257R.id.retry_progress).setVisibility(0);
                }

                @Override // android.view.View.OnClickListener
/* 656 */       public void onClick(View view) {
/* 657 */           if (view.getId() == C0257R.id.load_more && !hasPendingRequest(1)) {
/* 659 */               view.findViewById(C0257R.id.load_more_content).setVisibility(8);
/* 660 */               view.findViewById(C0257R.id.progress).setVisibility(0);
/* 661 */               this.mMessageCount += 10;
/* 663 */               restartLoader(1);
/* 664 */               fetchContent(1, 1, true);
                    }
                }

/* 667 */       public class ConversationMenuSessionListener extends AppSessionListener {
/* 668 */           public ConversationMenuSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 672 */           public void onGetMessageInboxComplete(String reqId, int statusCode, String reasonPhrase, int count) {
/* 673 */               PendingRequest req = InboxFragment.this.removeRequest(reqId);
/* 674 */               if (req != null) {
/* 675 */                   InboxFragment.access$1402(InboxFragment.this, false);
/* 676 */                   InboxFragment.this.hideProgress(req.fetchType);
/* 677 */                   InboxFragment.this.onGetMessageInboxComplete(count);
                            switch (statusCode) {
                                case 200:
                                    break;
                                default:
/* 683 */                           InboxFragment.this.hideProgress(3);
/* 684 */                           if (req.fetchType != 3) {
/* 685 */                               Util.showCenteredToast(InboxFragment.this.getActivity(), C0257R.string.error_server);
                                    }
/* 687 */                           SLog.m455e("Error " + statusCode + " - " + reasonPhrase);
                                    break;
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 695 */           public void onGetConversationRowIdComplete(long recipientId, boolean isRecipientExternal, long conversationObjectId, String username, boolean amFollowing) {
                        if (recipientId == InboxFragment.this.mWaitingToStartId) {
/* 697 */                   InboxFragment.this.mWaitingToStartId = -1L;
/* 698 */                   InboxFragment.this.mAppController.clearUnreadMessageCount(conversationObjectId);
/* 699 */                   InboxFragment.this.startActivity(ConversationActivity.getIntent(InboxFragment.this.getActivity(), conversationObjectId, username, recipientId, isRecipientExternal, amFollowing, false));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 705 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 706 */               InboxFragment.this.onPhotoImageLoaded(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 712 */           public void onGetUsersComplete(Session session, String reqId, int statusCode, String reasonPhrase, int count, ArrayList<VineUser> users, int nextPage, int previousPage, long anchor) {
/* 713 */               PendingRequest req = InboxFragment.this.removeRequest(reqId);
/* 714 */               if (req != null) {
/* 715 */                   InboxFragment.this.hideProgress(req.fetchType);
                            switch (statusCode) {
                                case 200:
                                    break;
                                default:
/* 721 */                           InboxFragment.this.hideProgress(3);
/* 722 */                           SLog.m455e("Error:  " + reasonPhrase);
                                    break;
                            }
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 731 */       public void onResume() {
/* 732 */           super.onResume();
/* 733 */           if (this.mInboxAdapter.getCursor() == null) {
/* 734 */               initLoader(1);
                    } else if (this.mInboxAdapter.isEmpty() && !isFetched(1)) {
/* 736 */               fetchContent(3, 1, false);
                    }
/* 738 */           if (this.mOtherAdapter.getCursor() == null) {
/* 739 */               initLoader(4);
                    } else if (this.mOtherAdapter.isEmpty() && !isFetched(4)) {
/* 741 */               fetchContent(3, 4, false);
                    }
/* 743 */           if (this.mFriendsAdapter.getCursor() == null) {
/* 744 */               initLoader(2);
                    } else if (this.mFriendsAdapter.isEmpty() && !isFetched(2)) {
/* 746 */               fetchContent(3, 3, false);
                    }
/* 748 */           if (this.mAddressCursor == null) {
/* 749 */               if (hasContactsPermission()) {
/* 750 */                   initLoader(3);
                            }
                    }
/* 751 */           getActivity().registerReceiver(this.mColorChangedReceiver, Util.COLOR_CHANGED_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 755 */       protected void onListItemClick(ListView l, View v, int position, long id) {
/* 756 */           int position2 = position - this.mListView.getHeaderViewsCount();
/* 757 */           if (position2 >= 0) {
/* 758 */               if (this.mActiveAdapter == this.mSectionAdapter) {
                            switch (this.mSectionAdapter.getItemViewType(position2)) {
                                case 1:
/* 761 */                           onInboxCursorClicked((Cursor) this.mSectionAdapter.getItem(position2), v);
                                    break;
                                case 2:
                                    if (this.mWaitingToStartId == -1) {
/* 766 */                               this.mWaitingToStartId = id;
/* 767 */                               this.mAppController.fetchConversationRowIdFromUserRemoteId(id, 1);
                                    }
                                    break;
                                case 3:
/* 772 */                           this.mListener.onClick(this, this.mContactEntryMap.get(Long.valueOf(id)));
                                    break;
                            }
                        }
/* 777 */               onInboxCursorClicked((Cursor) this.mOtherAdapter.getItem(position2), v);
                    }
                }

/* 782 */       protected void onGetMessageInboxComplete(int count) {
/* 783 */           if (this.mActiveAdapter == this.mSectionAdapter) {
/* 784 */               if (count == 0) {
/* 785 */                   invalidateContactEntries();
                        } else {
/* 787 */                   this.mAddressAdapter.clear();
/* 788 */                   this.mIsAddressBookShowing = false;
/* 789 */                   this.mAddressAdapter.notifyDataSetChanged();
                        }
                    }
/* 792 */           invalidateEmptyView(false);
                }

                /* JADX INFO: renamed from: co.vine.android.InboxFragment$5 */
/* 793 */       class C02365 extends OnContactEntryClickedListener {
/* 794 */           C02365() {
                    }

                    @Override // co.vine.android.OnContactEntryClickedListener
/* 798 */           protected void onContactEntryClicked(ContactEntry entry, VineRecipient recipient, int typeFlag, String value) {
                        if (InboxFragment.this.mWaitingToStartId == -1) {
/* 800 */                   InboxFragment.this.mWaitingToStartId = recipient.contactId;
/* 801 */                   InboxFragment.this.mAppController.fetchConversationRowIdFromSingleRecipient(recipient, 1);
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 808 */       protected void refresh() {
/* 809 */           if (this.mActiveAdapter == this.mSectionAdapter) {
/* 810 */               fetchContent(2, 3, false);
                    } else {
/* 812 */               fetchContent(2, 4, false);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 817 */       protected void onScrollLastItem(Cursor cursor) {
/* 818 */           if (this.mActiveAdapter == this.mSectionAdapter) {
/* 819 */               if (this.mAnchorManager.haveMore()) {
/* 820 */                   fetchContent(1, 2, false);
/* 828 */                   return;
                        }
/* 822 */               if (!this.mIsAddressBookShowing) {
/* 823 */                   addEntriesToAddressAdapter();
                        }
/* 825 */               hideProgress(3);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 831 */       public void onPause() {
/* 832 */           super.onPause();
/* 833 */           getActivity().unregisterReceiver(this.mColorChangedReceiver);
                }

/* 836 */       public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 837 */           if (this.mInboxAdapter != null) {
/* 838 */               this.mInboxAdapter.setUserImages(images);
                    }
/* 840 */           if (this.mFriendsAdapter != null) {
/* 841 */               this.mFriendsAdapter.setUserImages(images);
                    }
/* 843 */           if (this.mOtherAdapter != null) {
/* 844 */               this.mOtherAdapter.setUserImages(images);
                    }
                }
            }
