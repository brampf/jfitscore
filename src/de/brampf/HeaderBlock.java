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

import java.util.Arrays;

public class HeaderBlock {
    
    public HeaderBlock(byte[] bytes){

        raw = new String(bytes);

        keyword = new String(Arrays.copyOfRange(bytes, 0, 8)).trim();
        String rest = new String(Arrays.copyOfRange(bytes, 10, 79));

        int split = rest.lastIndexOf("/");
        if (split > 0) {
            try {
            value = HDUValue.parse(rest.substring(0, split-1));
            } catch (Exception e){
                System.out.println(e.getLocalizedMessage());
                value = null;
            }


            comment = rest.substring(split+1).trim();
        } else {
            try {
                value = HDUValue.parse(rest);
            } catch (Exception e){
                System.out.println(e.getLocalizedMessage());
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
        sb.append(String.format("%-"+8+"s", keyword));
        
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
