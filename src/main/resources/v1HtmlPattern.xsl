<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:profilens="http://www.verapdf.org/ValidationProfile">

    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <style type="text/css">
                    table {
                        border-collapse: collapse;
                        border-style: ridge;
                        border-width: 3px;
                        text-align:center;
                        vertical-align:middle;
                    }
                </style>
            </head>
            <body>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th width="13%">Name</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Rule</th>
                    </tr>
                    <xsl:for-each select="profilens:profile/rules/rule">
                        <xsl:variable name="i" select="position()"/>
                        <xsl:variable name="specification" select="string(id/@specification)"/>
                        <xsl:variable name="clause" select="string(id/@clause)"/>
                        <xsl:variable name="testNumber" select="string(id/@testNumber)"/>
                        <tr>
                            <td>
                                <xsl:value-of select="$i"/>
                            </td>
                            <td>
                                <xsl:value-of select="concat($specification,'-',$clause,'-t',$testNumber)"/>
                            </td>
                            <td>
                                <xsl:value-of select="description"/>
                            </td>
                            <td>
                                <xsl:value-of select="@object"/>
                            </td>
                            <td>
                                <xsl:value-of select="test"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>