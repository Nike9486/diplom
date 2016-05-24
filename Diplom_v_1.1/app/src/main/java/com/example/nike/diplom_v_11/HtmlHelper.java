package com.example.nike.diplom_v_11;

import android.util.Log;
import android.webkit.CookieManager;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.xwalk.core.internal.XWalkCookieManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nike on 02.05.2016.
 */
public class HtmlHelper {
    TagNode rootNode;

    public HtmlHelper(String htmlpage,String cookie) throws IOException{

        HtmlCleaner cleaner = new HtmlCleaner();
        CookieManager cookieManager=CookieManager.getInstance();

        cookieManager.setCookie(htmlpage,"amlbcookie=02");
        cookieManager.setCookie(htmlpage,"NstuSsoToken="+cookie);

        Log.d("HelperLog",cookieManager.getCookie(htmlpage));
        URLConnection connection = new URL(htmlpage).openConnection();
        connection.setRequestProperty("Cookie",cookieManager.getCookie(htmlpage));
        connection.connect();
        InputStream in = new BufferedInputStream(connection.getInputStream());
        rootNode = cleaner.clean(in,"windows-1251");
    }

    List<TagNode> getTabelByClass(String CSSClassName){

        List<TagNode> tabel =new ArrayList<TagNode>();

        TagNode TabelElement[] = rootNode.getElementsByName("table",true);
        for (int i=0; TabelElement!=null && i< TabelElement.length;i++){

            String classtype = TabelElement[i].getAttributeByName("class");
            if (classtype !=null&& classtype.equals(CSSClassName)){
                tabel.add(TabelElement[i]);
            }
        }
        return tabel;
    }
    List<TagNode> getEementById(String ID){

        List<TagNode> Element =new ArrayList<TagNode>();

        TagNode AllElements[] = rootNode.getElementsByName("select",true);
        for (int i=0; AllElements!=null && i< AllElements.length;i++){

            String idtype = AllElements[i].getAttributeByName("id");
            if (idtype !=null&& idtype.equals(ID)){
                Element.add(AllElements[i]);
            }
        }
        return Element;
    }
}
