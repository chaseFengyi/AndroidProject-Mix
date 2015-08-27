package com.mix.expressage;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel> {

	@Override
	public int compare(SortModel arg0, SortModel arg1) {  
		 //������Ҫ��������ListView��������ݸ���ABCDEFG...������  
        if (arg1.getSortLetters().equals("#")) {  
            return -1;  
        } else if (arg0.getSortLetters().equals("#")) {  
            return 1;  
        } else {  
            return arg0.getSortLetters().compareTo(arg1.getSortLetters());  
        }  
	}

}
