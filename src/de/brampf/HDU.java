package de.brampf;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class HDU {

    static int CARD_LENGTH = 80;
    
    public LinkedList<HeaderBlock> headerUnit = new LinkedList<>();
    
    public byte[] dataUnit = null;

    public HDU(FileInputStream input) throws Exception {

        readHeader(input);

        String first = this.headerUnit.getFirst().keyword;
        if (!first.equals("SIMPLE") && !first.equals("XTENSION")) {
            System.out.println("First: "+first);
            return;
        }
        
        int bytes = this.headerUnit.size() * 80;
        System.out.println("Read "+bytes+"; Skipping by "+(bytes % 2880));
        input.skip(2880 - (bytes % 2880));

        readData(input);
        bytes = this.dataUnit.length;
        input.skip(2880 - (bytes % 2880));

        System.out.println("- "+this.toString()+"------------------------------------------------------------------------------------------------");
    }


    public void readHeader(FileInputStream input) throws Exception {

        LinkedList<HeaderBlock> blocks = new LinkedList<>();
        final byte[] reader = new byte[CARD_LENGTH];

        boolean end = false;
        while (end == false) {

            input.read(reader, 0, CARD_LENGTH);
            HeaderBlock hb = new HeaderBlock(reader);

            blocks.add(hb);

            if (hb.keyword.equals("END")) {
                end = true;
            } 
        }

        this.headerUnit = blocks;
    }

    public void readData(FileInputStream input) throws Exception {

        int size = dataSize();

        this.dataUnit = input.readNBytes(size);

    }

    public <T> T lookup(String keyword){
        
        Iterator<HeaderBlock> iter = headerUnit.iterator();
        while(iter.hasNext()){
            HeaderBlock block =  iter.next();
            if (block.keyword.toUpperCase().equals(keyword.toUpperCase())) {
                try {
                    return (T)block.value.val;
                } catch (Exception e){
                    return null;
                }
            } 

        }
        return null;
    }

    public <T> T lookup(String keyword, T fallback){
        
        Iterator<HeaderBlock> iter = headerUnit.iterator();
        while(iter.hasNext()){
            HeaderBlock block =  iter.next();
            if (block.keyword.toUpperCase().equals(keyword.toUpperCase())) {
                try {
                    return (T)block.value.val;
                } catch (Exception e){
                    return fallback;
                }
            } 

        }
        return fallback;
    }


    public int dataSize() {

        int size = 0;

        int bitpix = this.lookup("BITPIX");
        int pcount = this.lookup("PCOUNT", 0);
        int gcount = this.lookup("GCOUTN", 1);

        int dim = 1;
        int naxis = this.lookup("NAXIS");
        for(int i = 1;i <= naxis; i++){
            int dimension = this.lookup("NAXIS"+i);
            dim *= dimension;
        }

        size = Math.abs(bitpix) * gcount * (pcount + dim);

        return size;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();

        int bitpix = this.lookup("BITPIX");
        int naxis = this.lookup("NAXIS");

        if (this.headerUnit.getFirst().keyword.equals("SIMPLE")){
            sb.append("PRIME ");
        } else {
            String type = this.lookup("XTENSION");
            String name = this.lookup("EXTNAME");
            
            sb.append(type+" ("+name+") ");
        }

        sb.append(bitpix+"bit");
        for(int i = 1;i <= naxis; i++){
            int dimension = this.lookup("NAXIS"+i);
            sb.append(" x "+dimension);
        }

        return sb.toString();
    }

}
