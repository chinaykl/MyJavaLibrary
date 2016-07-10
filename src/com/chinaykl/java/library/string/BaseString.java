package com.chinaykl.java.library.string;

public class BaseString {
	public static String[] getDiffString(String[] allStrings) {

		String[] all = new String[allStrings.length];
		int diffNum = 0;
		for (int i = 0; i < allStrings.length; i++) {
			String str = allStrings[i];

			if (str == null) {
				continue;
			}

			for (int j = 0; j < diffNum; j++) {
				if (str.equals(all[j]) == true) {
					break;
				}
				if (j == diffNum - 1) {
					all[diffNum] = str;
					diffNum++;
				}
			}

			if (diffNum == 0) {
				all[diffNum] = str;
				diffNum++;
				continue;
			}
		}

		String[] diff = new String[diffNum];
		for (int i = 0; i < diff.length; i++) {
			diff[i] = all[i];
		}

		return diff;
	}
}
