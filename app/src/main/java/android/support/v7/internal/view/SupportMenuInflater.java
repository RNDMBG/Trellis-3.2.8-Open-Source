            package android.support.v7.internal.view;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.content.res.XmlResourceParser;
            import android.support.v4.internal.view.SupportMenu;
            import android.support.v4.view.ActionProvider;
            import android.support.v4.view.MenuItemCompat;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.menu.MenuItemImpl;
            import android.support.v7.internal.view.menu.MenuItemWrapperICS;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.util.Xml;
            import android.view.InflateException;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.SubMenu;
            import android.view.View;
            import java.io.IOException;
            import java.lang.reflect.Method;
            import org.xmlpull.v1.XmlPullParser;
            import org.xmlpull.v1.XmlPullParserException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 55 */    public class SupportMenuInflater extends MenuInflater {
                private static final String LOG_TAG = "SupportMenuInflater";
                private static final int NO_ID = 0;
                private static final String XML_GROUP = "group";
                private static final String XML_ITEM = "item";
                private static final String XML_MENU = "menu";
                private final Object[] mActionProviderConstructorArguments;
                private final Object[] mActionViewConstructorArguments;
                private Context mContext;
                private Object mRealOwner;
                private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = {Context.class};
                private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;

/* 56 */        static /* synthetic */ Context access$100(SupportMenuInflater x0) {
/* 57 */            return x0.mContext;
                }

/* 56 */        static /* synthetic */ Class[] access$200() {
/* 57 */            return ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
                }

/* 56 */        static /* synthetic */ Object[] access$300(SupportMenuInflater x0) {
/* 57 */            return x0.mActionProviderConstructorArguments;
                }

/* 56 */        static /* synthetic */ Object access$400(SupportMenuInflater x0) {
/* 57 */            return x0.mRealOwner;
                }

/* 56 */        static /* synthetic */ Class[] access$500() {
/* 57 */            return ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
                }

/* 56 */        static /* synthetic */ Object[] access$600(SupportMenuInflater x0) {
/* 57 */            return x0.mActionViewConstructorArguments;
                }

/* 88 */        public SupportMenuInflater(Context context) {
/* 89 */            super(context);
/* 90 */            this.mContext = context;
/* 91 */            this.mRealOwner = context;
/* 92 */            this.mActionViewConstructorArguments = new Object[]{context};
/* 93 */            this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
                }

                @Override // android.view.MenuInflater
/* 107 */       public void inflate(int menuRes, Menu menu) {
/* 108 */           if (!(menu instanceof SupportMenu)) {
/* 109 */               super.inflate(menuRes, menu);
/* 126 */               return;
                    }
/* 113 */           XmlResourceParser parser = null;
                    try {
                        try {
/* 115 */                   parser = this.mContext.getResources().getLayout(menuRes);
/* 116 */                   AttributeSet attrs = Xml.asAttributeSet(parser);
/* 118 */                   parseMenu(parser, attrs, menu);
/* 124 */                   if (parser != null) {
                                parser.close();
                            }
                        } catch (IOException e) {
/* 122 */                   throw new InflateException("Error inflating menu XML", e);
                        } catch (XmlPullParserException e2) {
/* 120 */                   throw new InflateException("Error inflating menu XML", e2);
                        }
                    } catch (Throwable th) {
                        if (parser != null) {
                            parser.close();
                        }
                        throw th;
                    }
                }

/* 133 */       private void parseMenu(XmlPullParser parser, AttributeSet attrs, Menu menu) throws XmlPullParserException, IOException {
/* 134 */           MenuState menuState = new MenuState(menu);
/* 136 */           int eventType = parser.getEventType();
/* 138 */           boolean lookingForEndOfUnknownTag = false;
/* 139 */           String unknownTagName = null;
/* 154 */           do {
/* 143 */               if (eventType == 2) {
/* 144 */                   String tagName = parser.getName();
/* 145 */                   if (tagName.equals("menu")) {
/* 147 */                       eventType = parser.next();
                                break;
                            }
/* 151 */                   throw new RuntimeException("Expecting menu, got " + tagName);
                        }
/* 153 */               eventType = parser.next();
/* 154 */           } while (eventType != 1);
/* 156 */           boolean reachedEndOfMenu = false;
/* 157 */           while (!reachedEndOfMenu) {
                        switch (eventType) {
                            case 1:
/* 205 */                       throw new RuntimeException("Unexpected end of document");
                            case 2:
/* 160 */                       if (!lookingForEndOfUnknownTag) {
/* 164 */                           String tagName2 = parser.getName();
/* 165 */                           if (tagName2.equals(XML_GROUP)) {
/* 166 */                               menuState.readGroup(attrs);
                                    } else if (tagName2.equals(XML_ITEM)) {
/* 168 */                               menuState.readItem(attrs);
                                    } else if (tagName2.equals("menu")) {
/* 171 */                               SubMenu subMenu = menuState.addSubMenuItem();
/* 174 */                               parseMenu(parser, attrs, subMenu);
                                    } else {
/* 176 */                               lookingForEndOfUnknownTag = true;
/* 177 */                               unknownTagName = tagName2;
                                    }
                                }
                                break;
                            case 3:
/* 182 */                       String tagName3 = parser.getName();
/* 183 */                       if (lookingForEndOfUnknownTag && tagName3.equals(unknownTagName)) {
/* 184 */                           lookingForEndOfUnknownTag = false;
/* 185 */                           unknownTagName = null;
                                } else if (tagName3.equals(XML_GROUP)) {
/* 187 */                           menuState.resetGroup();
                                } else if (tagName3.equals(XML_ITEM)) {
/* 191 */                           if (!menuState.hasAddedItem()) {
/* 192 */                               if (MenuState.access$000(menuState) != null && MenuState.access$000(menuState).hasSubMenu()) {
/* 194 */                                   menuState.addSubMenuItem();
                                        } else {
/* 196 */                                   menuState.addItem();
                                        }
                                    }
                                } else if (tagName3.equals("menu")) {
/* 200 */                           reachedEndOfMenu = true;
                                }
                                break;
                        }
/* 208 */               eventType = parser.next();
                    }
                }

/* 217 */       private static class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
                    private static final Class<?>[] PARAM_TYPES = {MenuItem.class};
                    private Method mMethod;
                    private Object mRealOwner;

/* 218 */           public InflatedOnMenuItemClickListener(Object realOwner, String methodName) {
/* 220 */               this.mRealOwner = realOwner;
/* 221 */               Class<?> c = realOwner.getClass();
                        try {
/* 223 */                   this.mMethod = c.getMethod(methodName, PARAM_TYPES);
                        } catch (Exception e) {
/* 225 */                   InflateException ex = new InflateException("Couldn't resolve menu item onClick handler " + methodName + " in class " + c.getName());
/* 228 */                   ex.initCause(e);
/* 229 */                   throw ex;
                        }
                    }

                    @Override // android.view.MenuItem.OnMenuItemClickListener
/* 234 */           public boolean onMenuItemClick(MenuItem item) {
                        try {
/* 235 */                   if (this.mMethod.getReturnType() == Boolean.TYPE) {
                                return ((Boolean) this.mMethod.invoke(this.mRealOwner, item)).booleanValue();
                            }
/* 238 */                   this.mMethod.invoke(this.mRealOwner, item);
                            return true;
                        } catch (Exception e) {
/* 242 */                   throw new RuntimeException(e);
                        }
                    }
                }

/* 251 */       private class MenuState {
                    private static final int defaultGroupId = 0;
                    private static final int defaultItemCategory = 0;
                    private static final int defaultItemCheckable = 0;
                    private static final boolean defaultItemChecked = false;
                    private static final boolean defaultItemEnabled = true;
                    private static final int defaultItemId = 0;
                    private static final int defaultItemOrder = 0;
                    private static final boolean defaultItemVisible = true;
                    private int groupCategory;
                    private int groupCheckable;
                    private boolean groupEnabled;
                    private int groupId;
                    private int groupOrder;
                    private boolean groupVisible;
                    private ActionProvider itemActionProvider;
                    private String itemActionProviderClassName;
                    private String itemActionViewClassName;
                    private int itemActionViewLayout;
                    private boolean itemAdded;
                    private char itemAlphabeticShortcut;
                    private int itemCategoryOrder;
                    private int itemCheckable;
                    private boolean itemChecked;
                    private boolean itemEnabled;
                    private int itemIconResId;
                    private int itemId;
                    private String itemListenerMethodName;
                    private char itemNumericShortcut;
                    private int itemShowAsAction;
                    private CharSequence itemTitle;
                    private CharSequence itemTitleCondensed;
                    private boolean itemVisible;
                    private Menu menu;

/* 252 */           static /* synthetic */ ActionProvider access$000(MenuState x0) {
/* 253 */               return x0.itemActionProvider;
                    }

/* 311 */           public MenuState(Menu menu) {
/* 313 */               this.menu = menu;
/* 315 */               resetGroup();
                    }

/* 318 */           public void resetGroup() {
/* 319 */               this.groupId = 0;
/* 320 */               this.groupCategory = 0;
/* 321 */               this.groupOrder = 0;
/* 322 */               this.groupCheckable = 0;
/* 323 */               this.groupVisible = true;
/* 324 */               this.groupEnabled = true;
                    }

/* 330 */           public void readGroup(AttributeSet attrs) {
/* 331 */               TypedArray a = SupportMenuInflater.access$100(SupportMenuInflater.this).obtainStyledAttributes(attrs, C0084R.styleable.MenuGroup);
/* 333 */               this.groupId = a.getResourceId(1, 0);
/* 334 */               this.groupCategory = a.getInt(3, 0);
/* 336 */               this.groupOrder = a.getInt(4, 0);
/* 337 */               this.groupCheckable = a.getInt(5, 0);
/* 339 */               this.groupVisible = a.getBoolean(2, true);
/* 340 */               this.groupEnabled = a.getBoolean(0, true);
/* 342 */               a.recycle();
                    }

/* 348 */           public void readItem(AttributeSet attrs) {
/* 349 */               TypedArray a = SupportMenuInflater.access$100(SupportMenuInflater.this).obtainStyledAttributes(attrs, C0084R.styleable.MenuItem);
/* 352 */               this.itemId = a.getResourceId(2, 0);
/* 353 */               int category = a.getInt(5, this.groupCategory);
/* 354 */               int order = a.getInt(6, this.groupOrder);
/* 355 */               this.itemCategoryOrder = ((-65536) & category) | (65535 & order);
/* 357 */               this.itemTitle = a.getText(7);
/* 358 */               this.itemTitleCondensed = a.getText(8);
/* 359 */               this.itemIconResId = a.getResourceId(0, 0);
/* 360 */               this.itemAlphabeticShortcut = getShortcut(a.getString(9));
/* 362 */               this.itemNumericShortcut = getShortcut(a.getString(10));
/* 364 */               if (a.hasValue(11)) {
/* 366 */                   this.itemCheckable = a.getBoolean(11, false) ? 1 : 0;
                        } else {
/* 370 */                   this.itemCheckable = this.groupCheckable;
                        }
/* 372 */               this.itemChecked = a.getBoolean(3, false);
/* 373 */               this.itemVisible = a.getBoolean(4, this.groupVisible);
/* 374 */               this.itemEnabled = a.getBoolean(1, this.groupEnabled);
/* 375 */               this.itemShowAsAction = a.getInt(13, -1);
/* 376 */               this.itemListenerMethodName = a.getString(12);
/* 377 */               this.itemActionViewLayout = a.getResourceId(14, 0);
/* 378 */               this.itemActionViewClassName = a.getString(15);
/* 379 */               this.itemActionProviderClassName = a.getString(16);
/* 381 */               boolean hasActionProvider = this.itemActionProviderClassName != null;
/* 382 */               if (hasActionProvider && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
/* 383 */                   this.itemActionProvider = (ActionProvider) newInstance(this.itemActionProviderClassName, SupportMenuInflater.access$200(), SupportMenuInflater.access$300(SupportMenuInflater.this));
                        } else {
/* 387 */                   if (hasActionProvider) {
/* 388 */                       Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                            }
/* 391 */                   this.itemActionProvider = null;
                        }
/* 394 */               a.recycle();
/* 396 */               this.itemAdded = false;
                    }

/* 399 */           private char getShortcut(String shortcutString) {
/* 400 */               if (shortcutString == null) {
                            return (char) 0;
                        }
                        return shortcutString.charAt(0);
                    }

/* 407 */           private void setItem(MenuItem item) {
                        item.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut).setNumericShortcut(this.itemNumericShortcut);
/* 417 */               if (this.itemShowAsAction >= 0) {
/* 418 */                   MenuItemCompat.setShowAsAction(item, this.itemShowAsAction);
                        }
/* 421 */               if (this.itemListenerMethodName != null) {
/* 422 */                   if (SupportMenuInflater.access$100(SupportMenuInflater.this).isRestricted()) {
/* 423 */                       throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                            }
/* 426 */                   item.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(SupportMenuInflater.access$400(SupportMenuInflater.this), this.itemListenerMethodName));
                        }
/* 430 */               if (item instanceof MenuItemImpl) {
                        }
/* 431 */               if (this.itemCheckable >= 2) {
/* 432 */                   if (item instanceof MenuItemImpl) {
/* 433 */                       ((MenuItemImpl) item).setExclusiveCheckable(true);
                            } else if (item instanceof MenuItemWrapperICS) {
/* 435 */                       ((MenuItemWrapperICS) item).setExclusiveCheckable(true);
                            }
                        }
/* 439 */               boolean actionViewSpecified = false;
/* 440 */               if (this.itemActionViewClassName != null) {
/* 441 */                   View actionView = (View) newInstance(this.itemActionViewClassName, SupportMenuInflater.access$500(), SupportMenuInflater.access$600(SupportMenuInflater.this));
/* 443 */                   MenuItemCompat.setActionView(item, actionView);
/* 444 */                   actionViewSpecified = true;
                        }
/* 446 */               if (this.itemActionViewLayout > 0) {
/* 447 */                   if (!actionViewSpecified) {
/* 448 */                       MenuItemCompat.setActionView(item, this.itemActionViewLayout);
                            } else {
/* 451 */                       Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                            }
                        }
/* 455 */               if (this.itemActionProvider != null) {
/* 456 */                   MenuItemCompat.setActionProvider(item, this.itemActionProvider);
                        }
                    }

/* 460 */           public void addItem() {
/* 461 */               this.itemAdded = true;
/* 462 */               setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
                    }

/* 465 */           public SubMenu addSubMenuItem() {
/* 466 */               this.itemAdded = true;
/* 467 */               SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
/* 468 */               setItem(subMenu.getItem());
/* 469 */               return subMenu;
                    }

/* 472 */           public boolean hasAddedItem() {
/* 473 */               return this.itemAdded;
                    }

/* 479 */           private <T> T newInstance(String str, Class<?>[] clsArr, Object[] objArr) {
                        try {
/* 482 */                   return (T) SupportMenuInflater.access$100(SupportMenuInflater.this).getClassLoader().loadClass(str).getConstructor(clsArr).newInstance(objArr);
                        } catch (Exception e) {
/* 484 */                   Log.w(SupportMenuInflater.LOG_TAG, "Cannot instantiate class: " + str, e);
/* 486 */                   return null;
                        }
                    }
                }
            }
