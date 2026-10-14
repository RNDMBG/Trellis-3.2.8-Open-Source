            package android.support.v7.internal.view.menu;

            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.widget.CompatTextView;
            import android.text.TextUtils;
            import android.text.method.TransformationMethod;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.Toast;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class ActionMenuItemView extends CompatTextView implements MenuView.ItemView, View.OnClickListener, View.OnLongClickListener, ActionMenuView.ActionMenuChildView {
                private static final String TAG = "ActionMenuItemView";
                private boolean mAllowTextWithIcon;
                private boolean mExpandedFormat;
                private Drawable mIcon;
                private MenuItemImpl mItemData;
                private MenuBuilder.ItemInvoker mItemInvoker;
                private int mMinWidth;
                private int mSavedPaddingLeft;
                private CharSequence mTitle;

/* 55 */        public ActionMenuItemView(Context context) {
/* 56 */            this(context, null);
                }

/* 59 */        public ActionMenuItemView(Context context, AttributeSet attrs) {
/* 60 */            this(context, attrs, 0);
                }

/* 63 */        public ActionMenuItemView(Context context, AttributeSet attrs, int defStyle) {
/* 64 */            super(context, attrs, defStyle);
/* 65 */            Resources res = context.getResources();
/* 66 */            this.mAllowTextWithIcon = res.getBoolean(C0084R.bool.abc_config_allowActionMenuItemTextWithIcon);
/* 68 */            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.ActionMenuItemView, 0, 0);
/* 70 */            this.mMinWidth = a.getDimensionPixelSize(0, 0);
/* 72 */            a.recycle();
/* 74 */            setOnClickListener(this);
/* 75 */            setOnLongClickListener(this);
/* 77 */            setTransformationMethod(new AllCapsTransformationMethod());
/* 79 */            this.mSavedPaddingLeft = -1;
                }

                @Override // android.widget.TextView, android.view.View
/* 83 */        public void setPadding(int l, int t, int r, int b) {
/* 84 */            this.mSavedPaddingLeft = l;
/* 85 */            super.setPadding(l, t, r, b);
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 88 */        public MenuItemImpl getItemData() {
/* 89 */            return this.mItemData;
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 92 */        public void initialize(MenuItemImpl itemData, int menuType) {
/* 93 */            this.mItemData = itemData;
/* 95 */            setIcon(itemData.getIcon());
/* 96 */            setTitle(itemData.getTitleForItemView(this));
/* 97 */            setId(itemData.getItemId());
/* 99 */            setVisibility(itemData.isVisible() ? 0 : 8);
/* 100 */           setEnabled(itemData.isEnabled());
                }

                @Override // android.view.View.OnClickListener
/* 103 */       public void onClick(View v) {
/* 104 */           if (this.mItemInvoker != null) {
/* 105 */               this.mItemInvoker.invokeItem(this.mItemData);
                    }
                }

/* 109 */       public void setItemInvoker(MenuBuilder.ItemInvoker invoker) {
/* 110 */           this.mItemInvoker = invoker;
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 113 */       public boolean prefersCondensedTitle() {
/* 114 */           return true;
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 118 */       public void setCheckable(boolean checkable) {
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 122 */       public void setChecked(boolean checked) {
                }

/* 125 */       public void setExpandedFormat(boolean expandedFormat) {
/* 126 */           if (this.mExpandedFormat != expandedFormat) {
/* 127 */               this.mExpandedFormat = expandedFormat;
/* 128 */               if (this.mItemData != null) {
/* 129 */                   this.mItemData.actionFormatChanged();
                        }
                    }
                }

/* 134 */       private void updateTextButtonVisibility() {
                    boolean z = false;
/* 135 */           boolean visible = !TextUtils.isEmpty(this.mTitle);
/* 136 */           if (this.mIcon == null || (this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat))) {
                        z = true;
                    }
/* 139 */           setText(visible & z ? this.mTitle : null);
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 142 */       public void setIcon(Drawable icon) {
/* 143 */           this.mIcon = icon;
/* 144 */           setCompoundDrawablesWithIntrinsicBounds(icon, (Drawable) null, (Drawable) null, (Drawable) null);
/* 146 */           updateTextButtonVisibility();
                }

/* 149 */       public boolean hasText() {
                    return !TextUtils.isEmpty(getText());
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 154 */       public void setShortcut(boolean showShortcut, char shortcutKey) {
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 157 */       public void setTitle(CharSequence title) {
/* 158 */           this.mTitle = title;
/* 160 */           setContentDescription(this.mTitle);
/* 161 */           updateTextButtonVisibility();
                }

                @Override // android.support.v7.internal.view.menu.MenuView.ItemView
/* 164 */       public boolean showsIcon() {
/* 165 */           return true;
                }

                @Override // android.support.v7.internal.view.menu.ActionMenuView.ActionMenuChildView
/* 168 */       public boolean needsDividerBefore() {
                    return hasText() && this.mItemData.getIcon() == null;
                }

                @Override // android.support.v7.internal.view.menu.ActionMenuView.ActionMenuChildView
/* 172 */       public boolean needsDividerAfter() {
/* 173 */           return hasText();
                }

                @Override // android.view.View.OnLongClickListener
/* 177 */       public boolean onLongClick(View v) {
/* 178 */           if (hasText()) {
                        return false;
                    }
/* 183 */           int[] screenPos = new int[2];
/* 184 */           Rect displayFrame = new Rect();
/* 185 */           getLocationOnScreen(screenPos);
/* 186 */           getWindowVisibleDisplayFrame(displayFrame);
/* 188 */           Context context = getContext();
/* 189 */           int width = getWidth();
/* 190 */           int height = getHeight();
/* 191 */           int midy = screenPos[1] + (height / 2);
/* 192 */           int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
/* 194 */           Toast cheatSheet = Toast.makeText(context, this.mItemData.getTitle(), 0);
/* 195 */           if (midy < displayFrame.height()) {
/* 197 */               cheatSheet.setGravity(53, (screenWidth - screenPos[0]) - (width / 2), height);
                    } else {
/* 201 */               cheatSheet.setGravity(81, 0, height);
                    }
/* 203 */           cheatSheet.show();
                    return true;
                }

                @Override // android.widget.TextView, android.view.View
/* 208 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 209 */           boolean textVisible = hasText();
/* 210 */           if (textVisible && this.mSavedPaddingLeft >= 0) {
/* 211 */               super.setPadding(this.mSavedPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
                    }
/* 215 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 217 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 218 */           int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
/* 219 */           int oldMeasuredWidth = getMeasuredWidth();
/* 220 */           int targetWidth = widthMode == Integer.MIN_VALUE ? Math.min(widthSize, this.mMinWidth) : this.mMinWidth;
/* 223 */           if (widthMode != 1073741824 && this.mMinWidth > 0 && oldMeasuredWidth < targetWidth) {
/* 225 */               super.onMeasure(View.MeasureSpec.makeMeasureSpec(targetWidth, 1073741824), heightMeasureSpec);
                    }
/* 229 */           if (!textVisible && this.mIcon != null) {
/* 232 */               int w = getMeasuredWidth();
/* 233 */               int dw = this.mIcon.getIntrinsicWidth();
/* 234 */               super.setPadding((w - dw) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
                    }
                }

/* 239 */       private class AllCapsTransformationMethod implements TransformationMethod {
                    private Locale mLocale;

/* 240 */           public AllCapsTransformationMethod() {
/* 242 */               this.mLocale = ActionMenuItemView.this.getContext().getResources().getConfiguration().locale;
                    }

                    @Override // android.text.method.TransformationMethod
/* 246 */           public CharSequence getTransformation(CharSequence source, View view) {
/* 247 */               if (source != null) {
                            return source.toString().toUpperCase(this.mLocale);
                        }
                        return null;
                    }

                    @Override // android.text.method.TransformationMethod
/* 252 */           public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {
                    }
                }
            }
