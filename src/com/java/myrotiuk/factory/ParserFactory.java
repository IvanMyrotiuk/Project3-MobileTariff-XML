/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.myrotiuk.factory;

import com.java.myrotiuk.parsers.MyDOMParser;
import com.java.myrotiuk.parsers.MyParser;
import com.java.myrotiuk.parsers.MySAXParser;
import com.java.myrotiuk.parsers.MySTAXParser;
import com.java.myrotiuk.parsers.TypeParser;

/**
 * Class<code> ParserFactory</code> for making instance of concrete parser 
 * for parsing XML document 
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public class ParserFactory {
    /**
     * Method for getting concrete parser(SAX,DOM or STAX)
     * @param typeParser which parser would you like to choose 
     * @return concrete parser depends on type parser
     */
    public static MyParser getParser(TypeParser typeParser){
        
        switch(typeParser){
            case SAX:
                return new MySAXParser();
            case DOM:
                return new MyDOMParser();
            case STAX:
                return new MySTAXParser();
            default: throw new IllegalArgumentException("Wrong typy of parser:"+typeParser);
        }
    }  
}
