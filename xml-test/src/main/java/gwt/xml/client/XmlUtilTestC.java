package gwt.xml.client;

import gwt.xml.shared.XmlParser;
import gwt.xml.shared.XmlUtil;
import org.w3c.dom.Element;

import static gwt.xml.client.AssertionsC.assertFalse;
import static gwt.xml.client.AssertionsC.assertTrue;

public class XmlUtilTestC {
    private static final XmlUtilTestC instance = new XmlUtilTestC();

    public static XmlUtilTestC getInstance() {
        return instance;
    }

    private XmlUtilTestC() {
    }

    public void testNormalizeSpace() {
        String s1 = "<domains revision=\"0\"><domain id=\"b1_1\" name=\"Tutorial &amp; Showcase Local\" template=\"tutorial\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_2\" name=\"Tutorial &amp; Showcase Cloud\" template=\"tutorial\" authorization=\"owner\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_3\" name=\"Inventory Management Local\" template=\"inventoryManagement\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_4\" name=\"Inventory Management Cloud\" template=\"inventoryManagement\" authorization=\"owner\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain></domains>";
        String s2 = "<domains revision=\"0\">\n" +
                "    <domain id=\"b1_1\" name=\"Tutorial &amp; Showcase Local\" template=\"tutorial\">\n" +
                "        <user id=\"administrator@datalint.com\" authorization=\"administrator\"/>\n" +
                "        <user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/>\n" +
                "        <user id=\"developer@datalint.com\" authorization=\"developer\"/>\n" +
                "        <user id=\"writer@datalint.com\" authorization=\"writer\"/>\n" +
                "        <user id=\"reader@datalint.com\" authorization=\"reader\"/>\n" +
                "    </domain>\n" +
                "    <domain id=\"b1_2\" name=\"Tutorial &amp; Showcase Cloud\" template=\"tutorial\" authorization=\"owner\">\n" +
                "        <user id=\"administrator@datalint.com\" authorization=\"administrator\"/>\n" +
                "        <user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/>\n" +
                "        <user id=\"developer@datalint.com\" authorization=\"developer\"/>\n" +
                "        <user id=\"writer@datalint.com\" authorization=\"writer\"/>\n" +
                "        <user id=\"reader@datalint.com\" authorization=\"reader\"/>\n" +
                "    </domain>\n" +
                "    <domain id=\"b1_3\" name=\"Inventory Management Local\" template=\"inventoryManagement\">\n" +
                "        <user id=\"administrator@datalint.com\" authorization=\"administrator\"/>\n" +
                "        <user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/>\n" +
                "        <user id=\"developer@datalint.com\" authorization=\"developer\"/>\n" +
                "        <user id=\"writer@datalint.com\" authorization=\"writer\"/>\n" +
                "        <user id=\"reader@datalint.com\" authorization=\"reader\"/>\n" +
                "    </domain>\n" +
                "    <domain id=\"b1_4\" name=\"Inventory Management Cloud\" template=\"inventoryManagement\" authorization=\"owner\">\n" +
                "        <user id=\"administrator@datalint.com\" authorization=\"administrator\"/>\n" +
                "        <user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/>\n" +
                "        <user id=\"developer@datalint.com\" authorization=\"developer\"/>\n" +
                "        <user id=\"writer@datalint.com\" authorization=\"writer\"/>\n" +
                "        <user id=\"reader@datalint.com\" authorization=\"reader\"/>\n" +
                "    </domain>\n" +
                "</domains>";

        Element domains1 = XmlParser.parse(s1).getDocumentElement();
        Element domains2 = XmlParser.parse(s2).getDocumentElement();

        assertFalse(domains1.isEqualNode(domains2));

        XmlUtil.normalizeSpace(domains2);
        assertTrue(domains1.isEqualNode(domains2));
    }

    @Deprecated
    public void testEqualsTrue() {
        String s1 = "<domains revision=\"0\"><domain id=\"b1_1\" name=\"Tutorial &amp; Showcase Local\" template=\"tutorial\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_2\" name=\"Tutorial &amp; Showcase Cloud\" template=\"tutorial\" authorization=\"owner\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_3\" name=\"Inventory Management Local\" template=\"inventoryManagement\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_4\" name=\"Inventory Management Cloud\" template=\"inventoryManagement\" authorization=\"owner\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain></domains>";
        String s2 = "<domains revision=\"0\"><domain id=\"b1_1\" name=\"Tutorial &amp; Showcase Local\" template=\"tutorial\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_2\" name=\"Tutorial &amp; Showcase Cloud\" template=\"tutorial\" authorization=\"owner\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_3\" name=\"Inventory Management Local\" template=\"inventoryManagement\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain><domain id=\"b1_4\" name=\"Inventory Management Cloud\" template=\"inventoryManagement\" authorization=\"owner\"><user id=\"administrator@datalint.com\" authorization=\"administrator\"/><user id=\"supervisor@datalint.com\" authorization=\"supervisor\"/><user id=\"developer@datalint.com\" authorization=\"developer\"/><user id=\"writer@datalint.com\" authorization=\"writer\"/><user id=\"reader@datalint.com\" authorization=\"reader\"/></domain></domains>";

        assertTrue(XmlUtil.equals(XmlParser.parse(s1).getDocumentElement(), XmlParser.parse(s2).getDocumentElement()));
    }

    @Deprecated
    public void testEqualsFalse() {
        String s1 = "<record name=\"BURBERRY Dovey T-shirt with logo embroidery\"><table idRef=\"g1\"><row semiId=\"g1r_1\"><column idRef=\"g1c_3\">Burberry</column><column idRef=\"g1c_4\">womensFashion</column><column idRef=\"g1c_1\">COMPOSITION\n" +
                "Dry cleaning: Dry cleaning not possible\n" +
                "Drying instructions: Dry horizontally\n" +
                "Material: 100% cotton\n" +
                "Pattern: Uni\n" +
                "Stretch: Mid-weight material with stretch\n" +
                "Ironing instructions: Iron warm (max. 110º)\n" +
                "Washing instructions: Maximum at 30º\n" +
                "\n" +
                "SPECIFICATIONS\n" +
                "Neckline: Round\n" +
                "Sleeve length: Short sleeve\n" +
                "\n" +
                "FIT\n" +
                "Fit: Loose straight silhouette\n" +
                "Length: 63 cm in size S.\n" +
                "Model and Size: Our model is 180 cm tall and wears size S, this size is normal.\n" +
                "\n" +
                "PRODUCT INFORMATION\n" +
                "Fashion house BURBERRY was founded in 1856 by Thomas Burberry in London. BURBERRY clothing and accessories are often recognizable by the camel-colored pattern with black, red and white stripes; the so-called Haymarket diamond. The brand is known worldwide for its British design.</column><column idRef=\"g1c_10\">XS,S,M,L</column><column idRef=\"g1c_9\">White,Black</column><column idRef=\"g1c_2\">1200.01</column><column idRef=\"g1c_5\">2020-04-08</column><column idRef=\"g1c_8\">2895,4.webp,2899,8.webp,2907,2.webp,2883,1.webp,2903,5.webp,2911,6.webp</column><column idRef=\"g1c_6\">2</column></row></table></record>";
        String s2 = "<record name=\"BURBERRY Dovey T-shirt with logo embroidery\"><table idRef=\"g1\"><row semiId=\"g1r_1\"><column idRef=\"g1c_3\">Burberry</column><column idRef=\"g1c_4\">womensFashion</column><column idRef=\"g1c_1\">COMPOSITION\n" +
                "Dry cleaning: Dry cleaning not possible\n" +
                "Drying instructions: Dry horizontally\n" +
                "Material: 100% cotton\n" +
                "Pattern: Uni\n" +
                "Stretch: Mid-weight material with stretch\n" +
                "Ironing instructions: Iron warm (max. 110º)\n" +
                "Washing instructions: Maximum at 30º\n" +
                "\n" +
                "SPECIFICATIONS\n" +
                "Neckline: Round\n" +
                "Sleeve length: Short sleeve\n" +
                "\n" +
                "FIT\n" +
                "Fit: Loose straight silhouette\n" +
                "Length: 63 cm in size S.\n" +
                "Model and Size: Our model is 180 cm tall and wears size S, this size is normal.\n" +
                "\n" +
                "PRODUCT INFORMATION\n" +
                "Fashion house BURBERRY was founded in 1856 by Thomas Burberry in London. BURBERRY clothing and accessories are often recognizable by the camel-colored pattern with black, red and white stripes; the so-called Haymarket diamond. The brand is known worldwide for its British design.</column><column idRef=\"g1c_10\">XS,S,M,L</column><column idRef=\"g1c_9\">White,Black</column><column idRef=\"g1c_2\">120</column><column idRef=\"g1c_5\">2020-04-08</column><column idRef=\"g1c_8\">2895,4.webp,2899,8.webp,2907,2.webp,2883,1.webp,2903,5.webp,2911,6.webp</column><column idRef=\"g1c_6\">2</column></row></table></record>";

        assertFalse(XmlUtil.equals(XmlParser.parse(s1).getDocumentElement(), XmlParser.parse(s2).getDocumentElement()));
    }
}
