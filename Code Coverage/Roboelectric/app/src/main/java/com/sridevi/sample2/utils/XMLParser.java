package com.sridevi.sample2.utils;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by sridevi on 3/27/15.
 */
public class XMLParser {

    private Context context;

    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_DETAIL = "detail";

    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    public XMLParser() {
    }

    public XMLParser(Context context, String assetURL) {
        this.context = context;
        String xml = getXmlFromAssetUrl(assetURL);
        Document doc = getDomElement(xml);
        NodeList nl = doc.getElementsByTagName(KEY_ITEM);
        for (int i = 0; i < nl.getLength(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            map.put(KEY_TITLE, getValue(e, KEY_TITLE));
            map.put(KEY_DESCRIPTION, getValue(e, KEY_DESCRIPTION));
            map.put(KEY_DETAIL, getValue(e, KEY_DETAIL));
            result.add(map);
        }
    }

    private String getXmlFromAssetUrl(String assetURL){
        String xml = null;
        try {
            InputStream inputStream = context.getAssets().open(assetURL);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            xml = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return xml;
    }


    public String[] getTitleList(){
        String[] entries = new String[result.size()];
        int i = 0;
        for (HashMap<String, String> map : result){
            entries[i] = map.get(KEY_TITLE);
            i++;
        }
        return entries;
    }

    public String[] getDescriptionList(){
        String[] entries = new String[result.size()];
        int i = 0;
        for (HashMap<String, String> map : result){
            entries[i] = map.get(KEY_DESCRIPTION);
            i++;
        }
        return entries;
    }

    public String getDetail(String title){
        for (HashMap<String, String> map : result){
            if(map.get(KEY_TITLE).equals(title)){
                return map.get(KEY_DETAIL);
            }
        }
        return null;
    }

    private Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    private String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    private String getElementValue(Node elem) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
