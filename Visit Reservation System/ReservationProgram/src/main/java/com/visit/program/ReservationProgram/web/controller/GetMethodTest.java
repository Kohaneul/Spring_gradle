package com.visit.program.ReservationProgram.web.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetMethodTest {

    public static void main(String[] args) throws IOException {

//        URL url  = new URL("http://gwa.rapigen-inc.com/gw/userMain.do");
//
//        URLConnection urlCon = url.openConnection();
//
//        Map<String, List<String>> map = urlCon.getHeaderFields();
//
//        Iterator<String> iterator = map.keySet().iterator();
//        List<String> stringList1 = map.get("Set-Cookie");
//        for (String s : stringList1) {
//            System.out.println("s = " + s);
//        }

        String urlPath = "http://gwa.rapigen-inc.com/gw/uat/uia/egovLoginUsr.do";
        String pageContents = "";
        StringBuilder contents = new StringBuilder();

        try{

            URL url = new URL(urlPath);
            URLConnection con = (URLConnection)url.openConnection();
            InputStreamReader reader = new InputStreamReader (con.getInputStream(), "utf-8");

            BufferedReader buff = new BufferedReader(reader);

            while((pageContents = buff.readLine())!=null){
                //System.out.println(pageContents);
                contents.append(pageContents);
                contents.append("\r\n");
            }

            buff.close();

            System.out.println(contents.toString());

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    }



