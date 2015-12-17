/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.myrotiuk.parsers;

import generated.Tariff;
import java.util.List;

/**
 * Interface<code> MyParser</code> has one method that another parsers have to implement
 * for getting list of instances from XML document
 *
 * @version 1.0
 * @author Ivan Myrotiuk
 * @since 12-12-2015
 */
public interface MyParser {
    List<Tariff.MobileTariff> parse(String fileName);
}
