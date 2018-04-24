package main.utils;


import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    PrintWriter out;

    public Logger(String filePath){
        try{
            out = new PrintWriter(filePath);
            print("LOGGER","LOGGER INITIALIZED");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return(dateFormat.format(date));
    }
    private String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        Date date = new Date();
        return(dateFormat.format(date));
    }

    @Deprecated
    public void printf(String level, String strFormat, Object...objects){
        strFormat = level+":"+getHeader()+strFormat+"\n";
        System.out.printf(strFormat,objects);
        out.printf(strFormat,objects);
    }
    public void print(String level, String str){
        str = level+":"+getHeader()+str;
        System.out.println(str);
        out.println(str);
    }

    @Deprecated
    public void printf(String strFormat, Object...objects){
        printf("INFO",strFormat,objects);
    }

    public void print(String str){
        print("INFO",str);
    }
    private String getHeader(){
        return String.format("\t[DATE:%s - TIME:%s]\t",getDate(),getTime());
    }
    public void printError(Exception e){
        print("DEBUG",e.getClass().getName());
        e.printStackTrace(out);
    }

    public void close(){
        print("LOGGER","LOGGER CLOSED");
        out.close();
    }
}
