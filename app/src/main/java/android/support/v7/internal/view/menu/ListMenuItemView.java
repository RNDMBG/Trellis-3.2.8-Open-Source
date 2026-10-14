            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.ViewGroup;
            import android.widget.CheckBox;
            import android.widget.CompoundButton;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.RadioButton;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public class ListMenuItemView extends LinearLayout implements MenuView.ItemView {
                private static final String TAG = "ListMenuItemView";
                private Drawable mBackground;
                private CheckBox mCheckBox;
                private Context mContext;
                private boolean mForceShowIcon;
                private ImageView mIconView;
                private LayoutInflater mInflater;
                private MenuItemImpl mItemData;
                private int mMenuType;
                private boolean mPreserveIconSpacing;
                private RadioButton mRadioButton;
                private TextView mShortcutView;
                private int mTextAppearance;
                private Context mTextAppearanceContext;
                private TextView mTitleView;

/* 62 */        public ListMenuItemView(Context context, AttributeSet attrs, int defStyle) {
/* 63 */            super(context, attrs);
/* 64 */            this.mContext = context;
/* 66 */            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.MenuView, defStyle, 0);
/* 70 */            this.mBackground = a.getDrawable(5);
/* 71 */            this.mTextAppearance = a.getResourceId(1, -1);
/* 73 */            this.mPreserveIconSpacing = a.getBoolean(7, false);
/* 75 */            this.mTextAppearanceContext = context;
/* 77 */            a.recycle();
                }

/* 80 */        public ListMenuItemView(Context context, AttributeSet attrs) {
/* 81 */            this(context, attrs, 0);
                }

                @Override // android.view.View
/* 85 */        protected void onFinishInflate() {
/* 86 */            super.onFinishInflate();
/* 88 */            setBackgroundDrawable(this.mBackground);
/* 90 */            this.mTitleView = (TextView) findViewById(C0084R.id.title);
/* 91 */            if (this.mTextAppearance != -1) {
/* 92 */                this.mTitleView.setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance);
                    }
/* 96 */            this.mShortcutView = (TextView) findViewById(C0084R.id.shortcut);
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 99 */        public void initialize(MenuItemImpl itemData, int menuType) {
/* 100 */           this.mItemData = itemData;
/* 101 */           this.mMenuType = menuType;
/* 103 */           setVisibility(itemData.isVisible() ? 0 : 8);
/* 105 */           setTitle(itemData.getTitleForItemView(this));
/* 106 */           setCheckable(itemData.isCheckable());
/* 107 */           setShortcut(itemData.shouldShowShortcut(), itemData.getShortcut());
/* 108 */           setIcon(itemData.getIcon());
/* 109 */           setEnabled(itemData.isEnabled());
                }

/* 112 */       public void setForceShowIcon(boolean forceShow) {
/* 113 */           this.mForceShowIcon = forceShow;
                    this.mPreserveIconSpacing = forceShow;
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 116 */       public void setTitle(CharSequence title) {
/* 117 */           if (title != null) {
/* 118 */               this.mTitleView.setText(title);
/* 120 */               if (this.mTitleView.getVisibility() != 0) {
/* 121 */                   this.mTitleView.setVisibility(0);
/* 128 */                   return;
                        }
/* 128 */               return;
                    }
/* 124 */           if (this.mTitleView.getVisibility() != 8) {
/* 125 */               this.mTitleView.setVisibility(8);
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 130 */       public MenuItemImpl getItemData() {
/* 131 */           return this.mItemData;
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 134 */       public void setCheckable(boolean checkable) {
                    CompoundButton compoundButton;
                    CompoundButton otherCompoundButton;
/* 135 */           if (checkable || this.mRadioButton != null || this.mCheckBox != null) {
/* 144 */               if (this.mItemData.isExclusiveCheckable()) {
/* 145 */                   if (this.mRadioButton == null) {
/* 146 */                       insertRadioButton();
                            }
/* 148 */                   compoundButton = this.mRadioButton;
/* 149 */                   otherCompoundButton = this.mCheckBox;
                        } else {
/* 151 */                   if (this.mCheckBox == null) {
/* 152 */                       insertCheckBox();
                            }
/* 154 */                   compoundButton = this.mCheckBox;
/* 155 */                   otherCompoundButton = this.mRadioButton;
                        }
/* 158 */               if (checkable) {
/* 159 */                   compoundButton.setChecked(this.mItemData.isChecked());
/* 161 */                   int newVisibility = checkable ? 0 : 8;
/* 162 */                   if (compoundButton.getVisibility() != newVisibility) {
/* 163 */                       compoundButton.setVisibility(newVisibility);
                            }
/* 167 */                   if (otherCompoundButton != null && otherCompoundButton.getVisibility() != 8) {
/* 168 */                       otherCompoundButton.setVisibility(8);
/* 178 */                       return;
                            }
/* 178 */                   return;
                        }
/* 171 */               if (this.mCheckBox != null) {
/* 172 */                   this.mCheckBox.setVisibility(8);
                        }
/* 174 */               if (this.mRadioButton != null) {
/* 175 */                   this.mRadioButton.setVisibility(8);
                        }
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 182 */       public void setChecked(boolean checked) {
                    CompoundButton compoundButton;
/* 183 */           if (this.mItemData.isExclusiveCheckable()) {
/* 184 */               if (this.mRadioButton == null) {
/* 185 */                   insertRadioButton();
                        }
/* 187 */               compoundButton = this.mRadioButton;
                    } else {
/* 189 */               if (this.mCheckBox == null) {
/* 190 */                   insertCheckBox();
                        }
/* 192 */               compoundButton = this.mCheckBox;
                    }
/* 195 */           compoundButton.setChecked(checked);
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 198 */       public void setShortcut(boolean showShortcut, char shortcutKey) {
/* 199 */           int newVisibility = (showShortcut && this.mItemData.shouldShowShortcut()) ? 0 : 8;
/* 202 */           if (newVisibility == 0) {
/* 203 */               this.mShortcutView.setText(this.mItemData.getShortcutLabel());
                    }
/* 206 */           if (this.mShortcutView.getVisibility() != newVisibility) {
/* 207 */               this.mShortcutView.setVisibility(newVisibility);
                    }
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 211 */       public void setIcon(Drawable icon) {
/* 212 */           boolean showIcon = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
/* 213 */           if (showIcon || this.mPreserveIconSpacing) {
/* 217 */               if (this.mIconView != null || icon != null || this.mPreserveIconSpacing) {
/* 221 */                   if (this.mIconView == null) {
/* 222 */                       insertIconView();
                            }
/* 225 */                   if (icon != null || this.mPreserveIconSpacing) {
/* 226 */                       ImageView imageView = this.mIconView;
                                if (!showIcon) {
/* 226 */                           icon = null;
                                }
                                imageView.setImageDrawable(icon);
/* 228 */                       if (this.mIconView.getVisibility() != 0) {
/* 229 */                           this.mIconView.setVisibility(0);
/* 234 */                           return;
                                }
/* 234 */                       return;
                            }
/* 232 */                   this.mIconView.setVisibility(8);
                        }
                    }
                }

                @Override // android.widget.LinearLayout, android.view.View
/* 237 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 238 */           if (this.mIconView != null && this.mPreserveIconSpacing) {
/* 240 */               ViewGroup.LayoutParams lp = getLayoutParams();
/* 241 */               LinearLayout.LayoutParams iconLp = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
/* 242 */               if (lp.height > 0 && iconLp.width <= 0) {
/* 243 */                   iconLp.width = lp.height;
                        }
                    }
/* 246 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }

/* 249 */       private void insertIconView() {
/* 250 */           LayoutInflater inflater = getInflater();
/* 251 */           this.mIconView = (ImageView) inflater.inflate(C0084R.layout.abc_list_menu_item_icon, (ViewGroup) this, false);
/* 253 */           addView(this.mIconView, 0);
                }

/* 256 */       private void insertRadioButton() {
/* 257 */           LayoutInflater inflater = getInflater();
/* 258 */           this.mRadioButton = (RadioButton) inflater.inflate(C0084R.layout.abc_list_menu_item_radio, (ViewGroup) this, false);
/* 261 */           addView(this.mRadioButton);
                }

/* 264 */       private void insertCheckBox() {
/* 265 */           LayoutInflater inflater = getInflater();
/* 266 */           this.mCheckBox = (CheckBox) inflater.inflate(C0084R.layout.abc_list_menu_item_checkbox, (ViewGroup) this, false);
/* 269 */           addView(this.mCheckBox);
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 272 */       public boolean prefersCondensedTitle() {
/* 273 */           return false;
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 276 */       public boolean showsIcon() {
/* 277 */           return this.mForceShowIcon;
                }

/* 280 */       private LayoutInflater getInflater() {
/* 281 */           if (this.mInflater == null) {
/* 282 */               this.mInflater = LayoutInflater.from(this.mContext);
                    }
/* 284 */           return this.mInflater;
                }
            }
