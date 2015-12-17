/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.myrotiuk.validation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * Class<code> MyErrorHandler</code> for handling error depends on type of error 
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public class MyErrorHandler implements ErrorHandler{
    boolean flag=true;
  public void warning(SAXParseException e) {
  System.err.println("warning: "+getLineAddress(e) + 
          "-" + e.getMessage());
 }
 public void error(SAXParseException e) {
     flag=false;
   System.err.println("error: "+(getLineAddress(e) + 
           " - " + e.getMessage()));
 }
 public void fatalError(SAXParseException e) {
     flag=false;
   System.err.println("fatal error: "+getLineAddress(e) + 
           " - " + e.getMessage());
 }
 private String getLineAddress(SAXParseException e) {
   return "Address: "+e.getLineNumber() + " : " + e.getColumnNumber();
 }
}
