/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.myrotiuk.validation;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Class<code> MyValidationOfXML</code> for validation of my XML document 
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public final class MyValidationOfXML {

    public static boolean validation(String xmlFile, String xsdFile) {
        DOMParser parser = new DOMParser();
        MyErrorHandler handler = new MyErrorHandler();
        try {

            parser.setErrorHandler(handler);

            parser.setFeature(
                    "http://xml.org/sax/features/validation", true);
            parser.setFeature(
                    "http://apache.org/xml/features/validation/schema", true);
            parser.setFeature(
                    "http://apache.org/xml/features/validation/schema-full-checking",
                    true);
            parser.setProperty(
                    "http://apache.org/xml/properties/schema/external-schemaLocation", xsdFile);

            try {
                parser.parse(xmlFile);
            } catch (SAXException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
        } catch (SAXNotRecognizedException e) {
            e.printStackTrace();
        } catch (SAXNotSupportedException ex) {
            ex.printStackTrace();
        }

        return handler.flag;

    }
}
