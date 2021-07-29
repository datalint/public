package gwt.xml.shared;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static gwt.xml.shared.XPathBuilder.*;
import static org.junit.Assert.assertEquals;

public class XPathBuilderTest {
	@Test
	public void test() {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("k1", "v1");
		attributes.put("k2", "v2");
		attributes.put("k3", "v3");

		assertEquals("@k1='v1' and @k2='v2' and @k3='v3'", hasAttribute(attributes).build());
		assertEquals("@k1='v1' or @k2='v2' or @k3='v3'", hasAnyAttributes(attributes).build());

		assertEquals("xPath[@idRef='id1']",
				join("xPath", XPathBuilder.hasAnyAttributeValues("idRef", "id1")).build());
		assertEquals("xPath[@idRef='id1' or @idRef='id2']",
				join("xPath", XPathBuilder.hasAnyAttributeValues("idRef", "id1", "id2")).build());
		assertEquals("xPath[@idRef='id1' or @idRef='id2' or @idRef='id3']",
				join("xPath", XPathBuilder.hasAnyAttributeValues("idRef", "id1", "id2", "id3")).build());

		assertEquals("xPath[1]", xPathPredicate("xPath", 1).build());

		assertEquals("*[not(self::modifier)]", XPathBuilder.allTagsExcept("modifier"));
	}
}
