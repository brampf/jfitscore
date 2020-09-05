/*
 
 Copyright (c) <2020>
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 
 */

package de.brampf;

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
            return "'"+String.format("%-"+8+"", val.toString())+"'";
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
