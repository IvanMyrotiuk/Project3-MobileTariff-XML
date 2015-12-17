/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.myrotiuk.parsers;

import generated.Tariff;
import generated.Tariff.MobileTariff;
import generated.Tariff.MobileTariff.Parameters;
import generated.Tariff.MobileTariff.Price;
import generated.Tariff.MobileTariff.Price.CallPrices;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class<code> MySAXParser</code> for making instance of SAX parser for parsing
 * XML document
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public class MySAXParser implements MyParser {

    private Tariff tariff = new Tariff();
    private List<MobileTariff> mobTariffs;
    private MobileTariff currentMobTariff;
    private int current;

    /**
     * Class<code> SAXHandler</code> for making instance of handler 
     *that will be called by SAX parser when that come across some event 
     * start document, start element, characters, end element, end document
     * and SAX parser will call appropriate method for handling this event
     * because SAX event base parser 
     * 
     * @version 1.0
     * @author Ivan Myrotiuk
     * @since 12-12-2015
     */
    private class SAXHandler extends DefaultHandler {

        /**
         * handle start document event
         * @throws SAXException 
         */
        @Override
        public void startDocument() throws SAXException {
            mobTariffs = new LinkedList<>();
            tariff.setMobileTariffs(mobTariffs);
        }

        /**
         * handle start of element where we remember current element that we handle
         * @param uri of concrete tag. For tags that in main XML document uri will be taken from main XML document
         *              but for tags that have name spaces it will be uri from name spaces
         * @param localName current name of tag 
         * @param qName name space:current name of tag
         * @param attributes attributes for current tag
         * @throws SAXException 
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            switch (localName) {//qName will be different for custom tag qName==LocalName but when we will come across namespace qName="mySpace:in_network" so thet we need url for that namespace
                case "tariff":
                    current = 1;
                    break;
                case "mobile-tariff":
                    current = 2;
                    currentMobTariff = new MobileTariff();
                    String idAsString = attributes.getValue("id");
                    currentMobTariff.setId(Integer.parseInt(idAsString));
                    mobTariffs.add(currentMobTariff);//also change into tariff list as in tariff the same list of MobileTariff
                    break;
                case "name":
                    current = 3;
                    break;
                case "operator":
                    current = 4;
                    break;
                case "price":
                    current = 5;
                    Price price = new Price();
                    currentMobTariff.setPrice(price);
                    break;
                case "payroll":
                    current = 7;
                    break;
                case "sms":
                    current = 8;
                    break;
                case "call-prices":
                    current = 9;
                    CallPrices callPrice = new Price.CallPrices();
                    currentMobTariff.getPrice().setCallPrices(callPrice);
                    break;
                case "in-network":
                    if (uri.equals("http://Myrotiuk.com/callPrices")) {
                        current = 10;
                    }
                    break;
                case "out-network":
                    if (uri.equals("http://Myrotiuk.com/callPrices")) {
                        current = 11;
                    }
                    break;
                case "to-stationary":
                    if (uri.equals("http://Myrotiuk.com/callPrices")) {
                        current = 12;
                    }
                    break;
                case "parameters":
                    current = 13;
                    Parameters parameters = new Parameters();
                    currentMobTariff.setParameters(parameters);
                    break;
                case "tariffing":
                    current = 14;
                    break;
                case "paymentToConnectTariff":
                    current = 15;
                    break;
            }

        }

        /**
         * method for handling characters events when parser find this event we check what is start element(tag)
         * and depends on start element we handle event in appropriate way
         * @param buf array of characters
         * @param start start of characters
         * @param length of characters
         * @throws SAXException 
         */
        @Override
        public void characters(char[] buf, int start, int length) throws SAXException {

            String text = new String(buf, start, length);

            switch (current) {
                case 3:
                    currentMobTariff.setName(text);
                    break;
                case 4:
                    currentMobTariff.setOperator(text);
                    break;
                case 7:
                    currentMobTariff.getPrice().setPayroll(Double.parseDouble(text));
                    break;
                case 8:
                    currentMobTariff.getPrice().setSms(Double.parseDouble(text));
                    break;
                case 10:
                    currentMobTariff.getPrice().getCallPrices().setInNetwork(Double.parseDouble(text));
                    break;
                case 11:
                    currentMobTariff.getPrice().getCallPrices().setOutNetwork(Double.parseDouble(text));
                    break;
                case 12:
                    currentMobTariff.getPrice().getCallPrices().setToStationary(Double.parseDouble(text));
                    break;
                case 14:
                    currentMobTariff.getParameters().setTariffing(text);
                    break;
                case 15:
                    currentMobTariff.getParameters().setPaymentToConnectTariff(Double.parseDouble(text));
            }

        }

        /**
         * Method for handling end element when parser come across end element event
         * we make current element equals to 0
         * @param uri
         * @param localName
         * @param qName
         * @throws SAXException 
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
//            switch (qName) {
//                case "mobile-tariff":
//                    mobTariffs.add(currentMobTariff);//also change into tariff list as in tariff the same list of MobileTariff
//                    break;
//            }
            current = 0;
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }

    /**
     * Method that takes name of XML document and parses it 
     * @param fileName name of XML document
     * @return list of instances that was created from parsed document
     */
    public List<MobileTariff> parse(String fileName) {

        SAXHandler handler = new SAXHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(new File(fileName), handler);

        } catch (ParserConfigurationException ex) {
            System.out.println("Problem with parsing" + ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("Problem with parsing" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Problem with file" + ex.getMessage());
        }

        return mobTariffs;//tariff.getMobileTariffs();
    }

}
