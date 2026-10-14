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
            
            
    public class SupportMenuInflater extends MenuInflater {
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

        static  Context access$100(SupportMenuInflater x0) {
            return x0.mContext;
                }

        static  Class[] access$200() {
            return ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
                }

        static  Object[] access$300(SupportMenuInflater x0) {
            return x0.mActionProviderConstructorArguments;
                }

        static  Object access$400(SupportMenuInflater x0) {
            return x0.mRealOwner;
                }

        static  Class[] access$500() {
            return ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
                }

        static  Object[] access$600(SupportMenuInflater x0) {
            return x0.mActionViewConstructorArguments;
                }

        public SupportMenuInflater(Context context) {
            super(context);
            this.mContext = context;
            this.mRealOwner = context;
            this.mActionViewConstructorArguments = new Object[]{context};
            this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
                }

                @Override 
       public void inflate(int menuRes, Menu menu) {
           if (!(menu instanceof SupportMenu)) {
               super.inflate(menuRes, menu);
               return;
                    }
           XmlResourceParser parser = null;
                    try {
                        try {
                   parser = this.mContext.getResources().getLayout(menuRes);
                   AttributeSet attrs = Xml.asAttributeSet(parser);
                   parseMenu(parser, attrs, menu);
                   if (parser != null) {
                                parser.close();
                            }
                        } catch (IOException e) {
                   throw new InflateException("Error inflating menu XML", e);
                        } catch (XmlPullParserException e2) {
                   throw new InflateException("Error inflating menu XML", e2);
                        }
                    } catch (Throwable th) {
                        if (parser != null) {
                            parser.close();
                        }
                        throw th;
                    }
                }

       private void parseMenu(XmlPullParser parser, AttributeSet attrs, Menu menu) throws XmlPullParserException, IOException {
           MenuState menuState = new MenuState(menu);
           int eventType = parser.getEventType();
           boolean lookingForEndOfUnknownTag = false;
           String unknownTagName = null;
           do {
               if (eventType == 2) {
                   String tagName = parser.getName();
                   if (tagName.equals("menu")) {
                       eventType = parser.next();
                                break;
                            }
                   throw new RuntimeException("Expecting menu, got " + tagName);
                        }
               eventType = parser.next();
           } while (eventType != 1);
           boolean reachedEndOfMenu = false;
           while (!reachedEndOfMenu) {
                        switch (eventType) {
                            case 1:
                       throw new RuntimeException("Unexpected end of document");
                            case 2:
                       if (!lookingForEndOfUnknownTag) {
                           String tagName2 = parser.getName();
                           if (tagName2.equals(XML_GROUP)) {
                               menuState.readGroup(attrs);
                                    } else if (tagName2.equals(XML_ITEM)) {
                               menuState.readItem(attrs);
                                    } else if (tagName2.equals("menu")) {
                               SubMenu subMenu = menuState.addSubMenuItem();
                               parseMenu(parser, attrs, subMenu);
                                    } else {
                               lookingForEndOfUnknownTag = true;
                               unknownTagName = tagName2;
                                    }
                                }
                                break;
                            case 3:
                       String tagName3 = parser.getName();
                       if (lookingForEndOfUnknownTag && tagName3.equals(unknownTagName)) {
                           lookingForEndOfUnknownTag = false;
                           unknownTagName = null;
                                } else if (tagName3.equals(XML_GROUP)) {
                           menuState.resetGroup();
                                } else if (tagName3.equals(XML_ITEM)) {
                           if (!menuState.hasAddedItem()) {
                               if (MenuState.access$000(menuState) != null && MenuState.access$000(menuState).hasSubMenu()) {
                                   menuState.addSubMenuItem();
                                        } else {
                                   menuState.addItem();
                                        }
                                    }
                                } else if (tagName3.equals("menu")) {
                           reachedEndOfMenu = true;
                                }
                                break;
                        }
               eventType = parser.next();
                    }
                }

       private static class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
                    private static final Class<?>[] PARAM_TYPES = {MenuItem.class};
                    private Method mMethod;
                    private Object mRealOwner;

           public InflatedOnMenuItemClickListener(Object realOwner, String methodName) {
               this.mRealOwner = realOwner;
               Class<?> c = realOwner.getClass();
                        try {
                   this.mMethod = c.getMethod(methodName, PARAM_TYPES);
                        } catch (Exception e) {
                   InflateException ex = new InflateException("Couldn't resolve menu item onClick handler " + methodName + " in class " + c.getName());
                   ex.initCause(e);
                   throw ex;
                        }
                    }

                    @Override 
           public boolean onMenuItemClick(MenuItem item) {
                        try {
                   if (this.mMethod.getReturnType() == Boolean.TYPE) {
                                return ((Boolean) this.mMethod.invoke(this.mRealOwner, item)).booleanValue();
                            }
                   this.mMethod.invoke(this.mRealOwner, item);
                            return true;
                        } catch (Exception e) {
                   throw new RuntimeException(e);
                        }
                    }
                }

       private class MenuState {
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

           static  ActionProvider access$000(MenuState x0) {
               return x0.itemActionProvider;
                    }

           public MenuState(Menu menu) {
               this.menu = menu;
               resetGroup();
                    }

           public void resetGroup() {
               this.groupId = 0;
               this.groupCategory = 0;
               this.groupOrder = 0;
               this.groupCheckable = 0;
               this.groupVisible = true;
               this.groupEnabled = true;
                    }

           public void readGroup(AttributeSet attrs) {
               TypedArray a = SupportMenuInflater.access$100(SupportMenuInflater.this).obtainStyledAttributes(attrs, C0084R.styleable.MenuGroup);
               this.groupId = a.getResourceId(1, 0);
               this.groupCategory = a.getInt(3, 0);
               this.groupOrder = a.getInt(4, 0);
               this.groupCheckable = a.getInt(5, 0);
               this.groupVisible = a.getBoolean(2, true);
               this.groupEnabled = a.getBoolean(0, true);
               a.recycle();
                    }

           public void readItem(AttributeSet attrs) {
               TypedArray a = SupportMenuInflater.access$100(SupportMenuInflater.this).obtainStyledAttributes(attrs, C0084R.styleable.MenuItem);
               this.itemId = a.getResourceId(2, 0);
               int category = a.getInt(5, this.groupCategory);
               int order = a.getInt(6, this.groupOrder);
               this.itemCategoryOrder = ((-65536) & category) | (65535 & order);
               this.itemTitle = a.getText(7);
               this.itemTitleCondensed = a.getText(8);
               this.itemIconResId = a.getResourceId(0, 0);
               this.itemAlphabeticShortcut = getShortcut(a.getString(9));
               this.itemNumericShortcut = getShortcut(a.getString(10));
               if (a.hasValue(11)) {
                   this.itemCheckable = a.getBoolean(11, false) ? 1 : 0;
                        } else {
                   this.itemCheckable = this.groupCheckable;
                        }
               this.itemChecked = a.getBoolean(3, false);
               this.itemVisible = a.getBoolean(4, this.groupVisible);
               this.itemEnabled = a.getBoolean(1, this.groupEnabled);
               this.itemShowAsAction = a.getInt(13, -1);
               this.itemListenerMethodName = a.getString(12);
               this.itemActionViewLayout = a.getResourceId(14, 0);
               this.itemActionViewClassName = a.getString(15);
               this.itemActionProviderClassName = a.getString(16);
               boolean hasActionProvider = this.itemActionProviderClassName != null;
               if (hasActionProvider && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                   this.itemActionProvider = (ActionProvider) newInstance(this.itemActionProviderClassName, SupportMenuInflater.access$200(), SupportMenuInflater.access$300(SupportMenuInflater.this));
                        } else {
                   if (hasActionProvider) {
                       Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                            }
                   this.itemActionProvider = null;
                        }
               a.recycle();
               this.itemAdded = false;
                    }

           private char getShortcut(String shortcutString) {
               if (shortcutString == null) {
                            return (char) 0;
                        }
                        return shortcutString.charAt(0);
                    }

           private void setItem(MenuItem item) {
                        item.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut).setNumericShortcut(this.itemNumericShortcut);
               if (this.itemShowAsAction >= 0) {
                   MenuItemCompat.setShowAsAction(item, this.itemShowAsAction);
                        }
               if (this.itemListenerMethodName != null) {
                   if (SupportMenuInflater.access$100(SupportMenuInflater.this).isRestricted()) {
                       throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                            }
                   item.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(SupportMenuInflater.access$400(SupportMenuInflater.this), this.itemListenerMethodName));
                        }
               if (item instanceof MenuItemImpl) {
                        }
               if (this.itemCheckable >= 2) {
                   if (item instanceof MenuItemImpl) {
                       ((MenuItemImpl) item).setExclusiveCheckable(true);
                            } else if (item instanceof MenuItemWrapperICS) {
                       ((MenuItemWrapperICS) item).setExclusiveCheckable(true);
                            }
                        }
               boolean actionViewSpecified = false;
               if (this.itemActionViewClassName != null) {
                   View actionView = (View) newInstance(this.itemActionViewClassName, SupportMenuInflater.access$500(), SupportMenuInflater.access$600(SupportMenuInflater.this));
                   MenuItemCompat.setActionView(item, actionView);
                   actionViewSpecified = true;
                        }
               if (this.itemActionViewLayout > 0) {
                   if (!actionViewSpecified) {
                       MenuItemCompat.setActionView(item, this.itemActionViewLayout);
                            } else {
                       Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                            }
                        }
               if (this.itemActionProvider != null) {
                   MenuItemCompat.setActionProvider(item, this.itemActionProvider);
                        }
                    }

           public void addItem() {
               this.itemAdded = true;
               setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
                    }

           public SubMenu addSubMenuItem() {
               this.itemAdded = true;
               SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
               setItem(subMenu.getItem());
               return subMenu;
                    }

           public boolean hasAddedItem() {
               return this.itemAdded;
                    }

           private <T> T newInstance(String str, Class<?>[] clsArr, Object[] objArr) {
                        try {
                   return (T) SupportMenuInflater.access$100(SupportMenuInflater.this).getClassLoader().loadClass(str).getConstructor(clsArr).newInstance(objArr);
                        } catch (Exception e) {
                   Log.w(SupportMenuInflater.LOG_TAG, "Cannot instantiate class: " + str, e);
                   return null;
                        }
                    }
                }
            }
