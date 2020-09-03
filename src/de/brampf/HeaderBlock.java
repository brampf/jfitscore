package de.brampf;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class HeaderBlock {
    
    public HeaderBlock(byte[] bytes){

        raw = new String(bytes);

        keyword = new String(Arrays.copyOfRange(bytes, 0, 8)).trim();
        String rest = new String(Arrays.copyOfRange(bytes, 11, 79));

        int split = rest.indexOf("/");
        if (split > 0) {
            try {
            value = HDUValue.parse(rest.substring(0, split-1));
            } catch (Exception e){
                value = null;
            }


            comment = rest.substring(split+1).trim();
        } else {
            try {
                value = HDUValue.parse(rest);
            } catch (Exception e){
                value = null;
            }
        }

    }

    public String raw;

    public String keyword;
    public HDUValue<?> value;
    public String comment;

    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.rightPad(keyword, 9, " "));
        
        if (value != null) {
            sb.append("= ");
            sb.append(value.toString());
        }

        if (comment != null){
            sb.append(" / ");
            sb.append(comment);
        }
        return sb.toString();
    }

}
