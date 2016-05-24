package com.example.nike.diplom_v_11;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.htmlcleaner.TagNode;
/**
 * Created by Nike on 20.05.2016.
 */
public class Tabel {
    List<ArrayList> Rows;
    ArrayList<String> Colums;
    int Max_Sise=0;
    public Tabel(){
        Rows = null;
        Colums = null;
    }

    public Tabel(TagNode tabel,int start){
        int size=0;
        List<String> output = new ArrayList<String>();
        TagNode[] tr = tabel.getElementsByName("tr",true);
        Rows=new ArrayList<>();
        for(int i=start;i<tr.length;i++){
            TagNode[] td = tr[i].getElementsByName("td",true);
            Colums=new ArrayList<String>();
            for(int j=0;j<td.length;j++) {
                String Cell=td[j].getText().toString().replace("\t","")
                        .replace("&nbsp;","").replace("\n","")
                        .replace("\r","");
                while (Cell.contains("  ")){
                    String replace = Cell.replace("  ","");
                    Cell=replace;
                }
                Colums.add(Cell);
            }
            if (td.length>size){size=td.length;}
            Rows.add(Colums);
        }
        Max_Sise=size;
    }

    public void addRow (ArrayList Colums){

        Rows.add(Colums);
    }

    public int getSize(int i){

        return Max_Sise;
    }

    public List getColums(int i){

        return Rows.get(i);
    }

    public Tabel makenormal(){
        for (int i=0;i<this.Rows.size();i++){
            while(this.Rows.get(i).size()<Max_Sise){
                this.Rows.get(i).add("");
            }
        }
        return this;
    }
    public Tabel makeForTimeTabel(){
        for (int i=0;i<this.Rows.size();i++){
            if(this.Rows.get(i).size()==3){
                this.Rows.get(i).add(0,"");
            }
        }
        return this;
    }
    public  Tabel makeForMarksTabel(){
        for (int i=0;i<this.Rows.size();i++){
            if(this.Rows.get(i).size()==Max_Sise-2){
                this.Rows.get(i).add(0,"");
                this.Rows.get(i).add(0,"");
            }
        }
        return this;
    }
}
