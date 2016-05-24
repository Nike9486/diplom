package com.example.nike.diplom_v_11;

import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nike on 23.05.2016.
 */
public class Spisok {
  Map myList;
    public Spisok(){
        myList=new HashMap();
    }

    public Spisok(TagNode allList){
        super();
        TagNode[] options = allList.getElementsByName("option",true);
        for(int i=0;i<options.length;i++){
            myList.put(options[i].getText().toString(),options[i].getAttributeByName("value"));
        }
    }

    public String getValue(String key){

        return myList.get(key).toString();
    }
    public String get(){
        return myList.values().toString();
    }
}
