<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:profilens="http://www.verapdf.org/ValidationProfile">

    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <style type="text/css">
                    tr {
                        text-align:center;
                        vertical-align:middle;
                    }
                </style>
            </head>
            <body>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Rule</th>
                    </tr>
                    <xsl:for-each select="profilens:profile/profilens:rules/profilens:rule">
                        <xsl:variable name="i" select="position()" />
                        <tr>
                            <td style="width: 50px">
                                <xsl:value-of select="$i"/>
                            </td>
                            <td style="width: 100px">
                                <xsl:value-of select="@id"/>
                            </td>
                            <td style="width: 400px" align="left">
                                <xsl:value-of select="profilens:description"/>
                            </td>
                            <td style="width: 200px">
                                <xsl:value-of select="@object"/>
                            </td>
                            <td style="width: 800px">
                                <xsl:value-of select="profilens:test"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>