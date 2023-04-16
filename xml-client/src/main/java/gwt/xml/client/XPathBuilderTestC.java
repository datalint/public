package gwt.xml.client;

import java.util.HashMap;
import java.util.Map;

import static gwt.xml.client.AssertionsC.assertEquals;
import static gwt.xml.shared.XPathBuilder.*;

public class XPathBuilderTestC {
    private static final XPathBuilderTestC instance = new XPathBuilderTestC();

    public static XPathBuilderTestC getInstance() {
        return instance;
    }

    private XPathBuilderTestC() {
    }

    public void testBuild() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("k1", "v1");
        attributes.put("k2", "v2");
        attributes.put("k3", "v3");

        assertEquals("@k1='v1' and @k2='v2' and @k3='v3'", hasAttribute(attributes).build());
        assertEquals("@k1='v1' or @k2='v2' or @k3='v3'", hasAnyAttributes(attributes).build());

        assertEquals("xPath[@idRef='id1']",
                join("xPath", hasAnyAttributeValues("idRef", "id1")).build());
        assertEquals("xPath[@idRef='id1' or @idRef='id2']",
                join("xPath", hasAnyAttributeValues("idRef", "id1", "id2")).build());
        assertEquals("xPath[@idRef='id1' or @idRef='id2' or @idRef='id3']",
                join("xPath", hasAnyAttributeValues("idRef", "id1", "id2", "id3")).build());

        assertEquals("xPath[1]", xPathPredicate("xPath", 1).build());

        assertEquals("*[not(self::modifier)]", allTagsExcept("modifier"));

        assertEquals("domain/@id", path("domain", attr("id")).build());
    }
}
