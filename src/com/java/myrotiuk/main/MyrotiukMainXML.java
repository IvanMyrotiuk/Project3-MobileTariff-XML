/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.myrotiuk.main;

import com.java.myrotiuk.factory.ParserFactory;
import com.java.myrotiuk.parsers.MyParser;
import com.java.myrotiuk.parsers.MySAXParser;
import com.java.myrotiuk.parsers.TypeParser;
import com.java.myrotiuk.validation.MyValidationOfXML;
import generated.Tariff;
import java.util.List;

/**
 * Class<code> MyrotiukMainXML</code> consists <i>main()</i> method where is loaded
 * our validation of XML document and making parsing of xML document
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public class MyrotiukMainXML {

    /**
     * Main method
     *
     * @param args parameters that come from console
     */
    public static void main(String[] args) {

        System.out.println("Validation:...");
        boolean resultValidation = MyValidationOfXML.validation("./src/tariff.xml", "./src/tariff.xsd");
        System.out.println("XML is valide :->" + resultValidation);

        String fileName = "./src/tariff.xml";
        MyParser myParser = ParserFactory.getParser(TypeParser.STAX);
        List<Tariff.MobileTariff> mobTariffs = myParser.parse(fileName);
        for (Tariff.MobileTariff mt : mobTariffs) {
            System.out.println(mt);
        }
    }
}
