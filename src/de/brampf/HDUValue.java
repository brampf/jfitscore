package de.brampf;

import org.apache.commons.lang3.StringUtils;

public class HDUValue<Value> {
    
    public final Value val;

    public HDUValue(Value val){
        this.val = val;
    }

    public final static HDUValue<?> parse(String raw) throws Exception {
        String trimmed = raw.trim();


        if (trimmed.startsWith("'")) {
            return new HDUValue<String>(trimmed.substring(1,trimmed.length()-1).trim());

        } else {

            if (trimmed.toUpperCase().equals("T")) {
                return new HDUValue<Boolean>(true);
            }
            if (trimmed.toUpperCase().equals("F")) {
                return new HDUValue<Boolean>(false);
            }

        try {
            int val = Integer.parseInt(trimmed);
            return new HDUValue<Integer>(val);

        } catch (NumberFormatException e){
            // try again
        }

        try {
            float val = Float.parseFloat(trimmed);
            return new HDUValue<Float>(val);

        } catch (NumberFormatException e){
            // try again
        }

        try {
            double val = Double.parseDouble(trimmed);
            return new HDUValue<Double>(val);

        } catch (NumberFormatException e){
            // try again
        }

            throw new Exception("Unable to read value "+trimmed);
        }
    }

    public String toString() {

        if (val instanceof String) {
            return "'"+StringUtils.rightPad(val.toString(), 8, " ")+"'";
        }

        if (val instanceof Boolean) {
            if ((boolean)val){
                return "T";
            } else {
                return "F";
            }
        }

        return val.toString();
    }

}
