/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.myrotiuk.parsers;

import com.sun.jmx.snmp.BerDecoder;
import generated.ObjectFactory;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class<code> MyDOMParser</code> for making instance of DOM parser 
 * for parsing XML document 
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public class MyDOMParser implements MyParser {

    private Tariff tariff;

    /**
     * Method that takes name of XML document and parses it 
     * @param fileName name of XML document
     * @return list of instances that was created from parsed document
     */
    public List<Tariff.MobileTariff> parse(String fileName) {
        tariff = new ObjectFactory().createTariff();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setNamespaceAware(true);
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            Element root = doc.getDocumentElement();

           //System.out.println(doc.getDocumentURI());
            List<Tariff.MobileTariff> listOfMobTariffs = new LinkedList<>();
            //take all elements("mobile-tariff") in XML document and parse them
            NodeList mobTariffs = root.getElementsByTagName("mobile-tariff");//NS("http://Myrotiuk.com/defaultNameSpaceTariff",
            
            for(int i = 0; i<mobTariffs.getLength(); i++){
                Element mobTariffElem = (Element) mobTariffs.item(i);
                MobileTariff mobTariff = new MobileTariff();//we can not to set name space "http://Myrotiuk.com/defaultNameSpaceTariff" for tags that in main xml documant as that tags will be taken from main xml
                mobTariff.setId(Integer.parseInt(mobTariffElem.getAttribute("id")));//.getAttributeNS("http://Myrotiuk.com/defaultNameSpaceTariff", "id")));//.getAttribute("id")));
                mobTariff.setName(mobTariffElem.getElementsByTagName("name").item(0).getTextContent());//.getElementsByTagName("name").item(0).getTextContent());
                mobTariff.setOperator(mobTariffElem.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff", "operator").item(0).getTextContent());//getElementsByTagName("operator").item(0).getTextContent());
                mobTariff.setPrice(parsePrice(mobTariffElem.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff", "price")));//.getElementsByTagName("price")));
                mobTariff.setParameters(parseParameters(mobTariffElem.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff", "parameters")));//.getElementsByTagName("parameters")));
                
                listOfMobTariffs.add(mobTariff);
            }
            
            tariff.setMobileTariffs(listOfMobTariffs);
            
        } catch (ParserConfigurationException ex) {
            System.out.println("Problem with getting newDocumentBuilder()" + ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("Problem with parsing file"+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Problem with file"+ex.getMessage());
        }
        
        return tariff.getMobileTariffs();
    }
    
    /**
     * Method for parsing sub tags of MobileTariff tags
     * @param priceNodeList sub tags of MobileTariff nodes
     * @return instance of Price that is sub tag of MobileTarifftag
     */
    private Price parsePrice(NodeList priceNodeList){
        
        Element priceEL = (Element) priceNodeList.item(0);
        
        Price price = new Price();
        
        price.setPayroll(Double.parseDouble(priceEL.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff", "payroll").item(0).getTextContent()));//.getElementsByTagName("payroll").item(0).getTextContent()));
        price.setSms(Double.parseDouble(priceEL.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff", "sms").item(0).getTextContent()));//.getElementsByTagName("sms").item(0).getTextContent()));
        price.setCallPrices(parseCallPrices(priceEL.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff","call-prices")));
        
        return price;        
    }

    /**
     * Method for parsing CallPrices that's sub tag of Price tag 
     * @param callPricesNodeList sub tag of Price tag
     * @return instance of CallPrices
     */
    private CallPrices parseCallPrices(NodeList callPricesNodeList){
        
        Element callPricesEL = (Element) callPricesNodeList.item(0);
        
        CallPrices callPrices = new CallPrices();
        //we have to set uri name space as tags described in diferent location compare to main tags
        callPrices.setInNetwork(Double.parseDouble(callPricesEL.getElementsByTagNameNS("http://Myrotiuk.com/callPrices","in-network").item(0).getTextContent()));
        callPrices.setOutNetwork(Double.parseDouble(callPricesEL.getElementsByTagNameNS("http://Myrotiuk.com/callPrices","out-network").item(0).getTextContent()));
        callPrices.setToStationary(Double.parseDouble(callPricesEL.getElementsByTagNameNS("http://Myrotiuk.com/callPrices","to-stationary").item(0).getTextContent()));
        
        return callPrices;
    }
    
    /**
     * Method for parsing Parameters that is sub tag(node) MobileTarifftag
     * @param parametersNodeList sub tag of MobileTarifftag
     * @return instance of Parameters
     */
    private Parameters parseParameters(NodeList parametersNodeList){
        
        Element parametersEL = (Element) parametersNodeList.item(0);
        
        Parameters param = new Parameters();
        
        param.setTariffing(parametersEL.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff","tariffing").item(0).getTextContent());
        param.setPaymentToConnectTariff(Double.parseDouble(parametersEL.getElementsByTagNameNS("http://Myrotiuk.com/defaultNameSpaceTariff","paymentToConnectTariff").item(0).getTextContent()));
        
        return param;
    }
    
}
