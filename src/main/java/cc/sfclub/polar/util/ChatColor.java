package cc.sfclub.polar.util;

import asia.kala.ansi.AnsiString;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

public class ChatColor {
    public static final Map<Character,AnsiString.Attribute> COLORS = new HashMap<>();
    static {
        COLORS.put('b',AnsiString.Color.LightBlue);
        COLORS.put('a',AnsiString.Color.Green);
        COLORS.put('c',AnsiString.Color.Red);
        COLORS.put('d',AnsiString.Color.Magenta);
        COLORS.put('e',AnsiString.Color.Yellow);
        COLORS.put('r',AnsiString.Color.Reset);
    }

    /**
     * Buggy.
     * @param str
     * @return
     */
    @ApiStatus.Experimental
    public static final String of(String str){
        boolean flag=false;
        AnsiString.Attribute attribute=null;
        StringBuilder sb = new StringBuilder();
        StringBuilder concating=null;
        for(var i = 0;i!=str.length();i++){
            char character=str.charAt(i);
           if(character=='&' && i != str.length()-1){
               char color = str.charAt(i+1);
               if(!COLORS.containsKey(color)){
                   continue;
               }
               if(concating!=null){
                   sb.append(attribute.overlay(concating.toString()));
               }
               i=i+1;
               flag=true;
               attribute=COLORS.get(color);
               concating=new StringBuilder();
               continue;
           }
           if(flag){
               concating.append(character);
           }
           if(i==str.length()-1 && flag){
               sb.append(concating).append("&r");
           }
        }
        return sb.toString();
    }
}
