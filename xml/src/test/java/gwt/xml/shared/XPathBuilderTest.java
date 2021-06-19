package gwt.xml.shared;

import org.junit.Test;

import static gwt.xml.shared.XPathBuilder.*;
import static org.junit.Assert.assertEquals;

public class XPathBuilderTest {
	@Test
	public void test() {
		assertEquals("xPath[@idRef='id1']",
				join("xPath", hasAttributeValues("idRef", "id1")).build());
		assertEquals("xPath[@idRef='id1' or @idRef='id2']",
				join("xPath", hasAttributeValues("idRef", "id1", "id2")).build());
		assertEquals("xPath[@idRef='id1' or @idRef='id2' or @idRef='id3']",
				join("xPath", hasAttributeValues("idRef", "id1", "id2", "id3")).build());

		assertEquals("xPath[1]", xPathPredicate("xPath", 1).build());
	}
}
