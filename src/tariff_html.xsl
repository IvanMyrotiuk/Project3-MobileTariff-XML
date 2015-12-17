<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:myspace="http://Myrotiuk.com/callPrices"
                version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <body>
                <table border="1">
                    <tr>
                        <th>id </th>
                        <th>name </th>
                        <th>operator </th>
                        <td>payroll </td>
                        <td>sms </td>
                        <td>in-network </td>
                        <td>out-network </td>
                        <td>toStationary </td>
                        <td>tariffing </td>
                        <td>paymentToConnectTariff </td>
                    </tr>   
                        <xsl:for-each select="tariff/mobile-tariff">
                        <tr>
                            <td>
                                <xsl:value-of select="@id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="operator"/>
                            </td>
                            <td>
                                <xsl:value-of select="price/payroll"/>
                            </td>
                            <td>
                                <xsl:value-of select = "price/sms"/>
                            </td>
                            <td>
                                <xsl:value-of select = "price/call-prices/myspace:in-network"/>
                            </td>
                            <td>
                                <xsl:value-of select = "price/call-prices/myspace:out-network"/>
                            </td>
                            <td>
                                <xsl:value-of select = "price/call-prices/myspace:to-stationary"/>
                            </td>
                            <td>
                                <xsl:value-of select = "parameters/tariffing"/>
                            </td>
                            <td>
                                <xsl:value-of select = "parameters/paymentToConnectTariff"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                    
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
