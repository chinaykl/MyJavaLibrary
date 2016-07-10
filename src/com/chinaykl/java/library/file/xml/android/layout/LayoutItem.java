package com.chinaykl.java.library.file.xml.android.layout;

import java.util.ArrayList;

public class LayoutItem {

	private LayoutItem mParent;

	private String[] mAttributeValues;
	private static final String[] mAttributeKeys = { "text", "resource-id", "class", "package", "content-desc",
			"bounds", "sizemode", "rendertext", "disptextlength", "disptextheight", "displines", "settedtext",
			"textsize" };

	public static final int TEXT_INDEX = 0;
	public static final int RESOURCEID_INDEX = TEXT_INDEX + 1;
	public static final int CLASS_INDEX = RESOURCEID_INDEX + 1;
	public static final int PACKAGE_INDEX = CLASS_INDEX + 1;
	public static final int CONTENTDESC_INDEX = PACKAGE_INDEX + 1;
	public static final int BOUNDS_INDEX = CONTENTDESC_INDEX + 1;
	public static final int SIZEMODE_INDEX = BOUNDS_INDEX + 1;
	public static final int RENDERTEXT_INDEX = SIZEMODE_INDEX + 1;
	public static final int DISPTEXTLENGTH_INDEX = RENDERTEXT_INDEX + 1;
	public static final int DISPTEXTHEIGHT_INDEX = DISPTEXTLENGTH_INDEX + 1;
	public static final int DISPLINES_INDEX = DISPTEXTHEIGHT_INDEX + 1;
	public static final int SETTEDTEXT_INDEX = DISPLINES_INDEX + 1;
	public static final int TEXTSIZE_INDEX = SETTEDTEXT_INDEX + 1;

	private ArrayList<LayoutItem> mChildren;

	protected void setParent(LayoutItem parent) {

		mParent = parent;
	}

	public boolean isRoot() {

		if (mParent == null) {
			return true;
		}
		return false;
	}

	public boolean isLeave() {
		return getChildrenNum() == 0 ? true : false;
	}

	public static String[] getAttributekeys() {

		return mAttributeKeys;
	}

	public String getAttributeValueByIndex(int index) {

		if ((mAttributeValues == null) || (index < 0) || (index >= mAttributeKeys.length)) {
			return null;
		}
		return mAttributeValues[index];
	}

	protected void setAttributes(String[] attributes) {

		if (attributes.length == mAttributeKeys.length) {
			mAttributeValues = attributes;
		}
	}

	protected void setChildren(ArrayList<LayoutItem> children) {

		if (children != null) {
			mChildren = children;
		}
	}

	public int getChildrenNum() {

		if (mChildren == null) {
			return 0;
		}
		return mChildren.size();
	}

	public LayoutItem getChildByIndex(int index) {

		if ((mChildren == null) || (index >= getChildrenNum()) || (index < 0)) {
			return null;
		}
		return mChildren.get(index);
	}
}
