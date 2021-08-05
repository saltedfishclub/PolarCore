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
            char character = str.charAt(i);
            if(character == '\\' ){
                boolean skip = i == 0 || str.charAt(i - 1) != '\\'; // 如果前面不是一个 \
                if(skip){
                    sb.append(character);
                }else{
                    continue;
                }
            }
            if (character == '&') {
                boolean skip = i!=0 && str.charAt(i - 1) == '\\'; // 如果前面是一个 \
                if(skip){
                    sb.append(character);
                }else{
                    if(i != str.length()-1){ // 是否是末尾
                        char color = str.charAt(i+1);
                        if(COLORS.containsKey(color)){
                            if(concating!=null){
                                sb.append(attribute.overlay(concating.toString()));
                            }
                            concating=null;
                            attribute=COLORS.get(color);
                            flag=true;
                            i = i+1;
                            continue;
                        }else{
                            sb.append(character);
                        }
                    }else{
                        sb.append(character);
                    }
                }
            }
            if (flag) {
                if(concating==null){
                    concating = new StringBuilder();
                }
                concating.append(character);
            }
            if (concating !=null && i == str.length()-1) {
                sb.append(attribute.overlay(concating.toString()));
            }
        }
        return sb.toString();
    }
}
