package com.mix.expressage;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel> {

	@Override
	public int compare(SortModel arg0, SortModel arg1) {  
		 //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序  
        if (arg1.getSortLetters().equals("#")) {  
            return -1;  
        } else if (arg0.getSortLetters().equals("#")) {  
            return 1;  
        } else {  
            return arg0.getSortLetters().compareTo(arg1.getSortLetters());  
        }  
	}

}
