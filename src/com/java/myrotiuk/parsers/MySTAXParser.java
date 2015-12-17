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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 * Class<code> MySTAXParser</code> for making instance of STAX parser for
 * parsing XML document
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public class MySTAXParser implements MyParser {

    private Tariff tariff = new Tariff();

    /**
     * Method that takes name of XML document and parses it
     *
     * @param fileName name of XML document
     * @return list of instances that was created from parsed document
     */
    public List<MobileTariff> parse(String fileName) {

        List<MobileTariff> mobTariffs = null;
        MobileTariff mobTariff = null;

        Price price = null;

        CallPrices callPrices = null;

        Parameters parameters = null;

        InputStream input = null;
        try {
            input = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with file" + ex.getMessage());
        }

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        try {
            reader = inputFactory.createXMLStreamReader(input);
        } catch (XMLStreamException ex) {
            System.out.println("Problem with creating XML stream reader" + ex.getMessage());
        }

        mobTariffs = new LinkedList<>();
        tariff.setMobileTariffs(mobTariffs);

        try {
            //check if there is element to handle
            while (reader.hasNext()) {

                //take event that we meet 
                int eventType = reader.next();
                //check if the event is start element
                if (eventType == XMLEvent.START_ELEMENT) {
                    //get name of tag if we wrote getName() it would return qName with name spaces but in switch case we 
                    //just wrote local name (current name of tag) as for tags that don't have namespaces we do not need 
                    //name spaces but for tags with name spaces we need them 
                    //therefore we check local name and for tags with name spaces check uri for that tags
                    String currentElementName = reader.getLocalName();//.getName().toString();

                    switch (currentElementName) {
                        case "mobile-tariff":
                            mobTariff = new MobileTariff();
                            mobTariff.setId(Integer.parseInt(reader.getAttributeValue("", "id")));
                            mobTariffs.add(mobTariff);
                            break;
                        case "name":
                            mobTariff.setName(reader.getElementText());//.getText());
                            break;
                        case "operator":
                            mobTariff.setOperator(reader.getElementText());
                            break;
                        case "price":
                            price = new Price();
                            mobTariff.setPrice(price);
                            break;
                        case "payroll":
                            price.setPayroll(Double.parseDouble(reader.getElementText()));
                            break;
                        case "sms":
                            price.setSms(Double.parseDouble(reader.getElementText()));
                            break;
                        case "call-prices":
                            callPrices = new CallPrices();
                            price.setCallPrices(callPrices);
                            break;
                        case "in-network":
                            if (reader.getNamespaceURI().equals("http://Myrotiuk.com/callPrices")) {
                                callPrices.setInNetwork(Double.parseDouble(reader.getElementText()));
                            }
                            break;
                        case "out-network":
                            if (reader.getNamespaceURI().equals("http://Myrotiuk.com/callPrices")) {
                                callPrices.setOutNetwork(Double.parseDouble(reader.getElementText()));
                            }
                            break;
                        case "to-stationary":
                            if (reader.getNamespaceURI().equals("http://Myrotiuk.com/callPrices")) {
                                callPrices.setToStationary(Double.parseDouble(reader.getElementText()));
                            }
                            break;
                        case "parameters":
                            parameters = new Parameters();
                            mobTariff.setParameters(parameters);
                            break;
                        case "tariffing":
                            parameters.setTariffing(reader.getElementText());
                            break;
                        case "paymentToConnectTariff":
                            parameters.setPaymentToConnectTariff(Double.parseDouble(reader.getElementText()));
                            break;
                    }

                }

            }
        } catch (XMLStreamException ex) {
            System.out.println("There is problem with xml" + ex.getMessage());
        }

        return mobTariffs;
    }

}
