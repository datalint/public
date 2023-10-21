package gwt.xml.client;

import java.util.Arrays;
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

        assertEquals("@k1='v1' and @k2='v2' and @k3='v3'", hasAttributesAnd(attributes).build());
        assertEquals("@k1='v1' or @k2='v2' or @k3='v3'", hasAttributesOr(attributes).build());

        assertEquals("xPath[@idRef='id1']", xPathHasAnyAttributeValues("xPath", "idRef", "id1").build());
        assertEquals("xPath[@idRef='id1' or @idRef='id2']", xPathHasAnyAttributeValues("xPath", "idRef", "id1",
                "id2").build());
        assertEquals("xPath[@idRef='id1' or @idRef='id2' or @idRef='id3']", xPathHasAnyAttributeValues("xPath",
                "idRef", "id1", "id2", "id3").build());

        assertEquals("xPath[1]", xPathPredicate("xPath", lit(1)).build());

        assertEquals("*[self::grid or self::table]", allTags("grid", "table").build());
        assertEquals("*[not(self::modifier)]", allTagsExcept("modifier").build());

        assertEquals("domain/@id", path("domain", attr("id")).build());

        assertEquals("x//y//z", descendant("x", lit("y"), lit("z")).build());
        assertEquals("x//*", descendantWildcard("x").build());
        assertEquals(".//x", selfDescendant(lit("x")).build());
        assertEquals(".//*", selfDescendantWildcard().build());
        assertEquals(".//*[@id]", selfDescendantWildcard(predicate(attr("id"))).build());
        assertEquals(".//*[@id][@name]", selfDescendantWildcard(predicate(attr("id")), predicate(attr("name")))
                .build());
        assertEquals(".//*[@id]", selfDescendant(all(predicate(attr("id")))).build());
        assertEquals(".//*[@id]//*[@name]", selfDescendant(all(predicate(attr("id"))), all(predicate(
                attr("name")))).build());

        assertEquals("*[@id='x']", hasAnyAttributeValues("id", "x").build());
        assertEquals("*[@id='x' or @id='y']", hasAnyAttributeValues("id", "x", "y").build());
        assertEquals("*[@id='x' or @id='y' or @id='z']", hasAnyAttributeValues("id", "x", "y", "z").build());

        assertEquals("translate(@creationTimestamp,'-T:','')", translateDateTime(attr("creationTimestamp"))
                .build());

        assertEquals(".//*[self::grid or self::table]", selfDescendant(allTags("grid", "table")).build());

        assertEquals("*[@id]", hasAnyAttributeNames("id").build());
        assertEquals("*[@id or @name]", hasAnyAttributeNames("id", "name").build());
        assertEquals("*[@id or @name or @size]", hasAnyAttributeNames("id", "name", "size")
                .build());

        assertEquals("xPath[@name='a']", xPathHasAnyAttributeValues("xPath", "name", "a").build());
        assertEquals("xPath[@name='a']", xPathHasAnyAttributeValues("xPath", "name", Arrays.asList("a"))
                .build());
        assertEquals("xPath[@name='a' or @name='b']", xPathHasAnyAttributeValues("xPath", "name", "a", "b")
                .build());
        assertEquals("xPath[@name='a' or @name='b']", xPathHasAnyAttributeValues("xPath", "name",
                Arrays.asList("a", "b")).build());
        assertEquals("xPath[@name='a' or @name='b' or @name='c']", xPathHasAnyAttributeValues("xPath", "name",
                "a", "b", "c").build());
        assertEquals("xPath[@name='a' or @name='b' or @name='c']", xPathHasAnyAttributeValues("xPath", "name",
                Arrays.asList("a", "b", "c")).build());

        assertEquals("translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')",
                lowerCase(attr("name")).build());

        assertEquals("development", xPathPredicate("development").build());
        assertEquals("development[@status]", xPathPredicate("development", attr("status")).build());
        assertEquals("development[@status][@name]", xPathPredicate("development", attr("status"),
                attr("name")).build());

        assertEquals("@name", hasAttributeNamesOr("name").build());
        assertEquals("@name or @disabled", hasAttributeNamesOr("name", "disabled").build());
        assertEquals("@name=''", hasAttributeValuesOr("name", Arrays.asList("")).build());
        assertEquals("@name='n1'", hasAttributeValuesOr("name", Arrays.asList("n1")).build());
        assertEquals("@name='n1' or @name='n2'", hasAttributeValuesOr("name", Arrays.asList("n1", "n2"))
                .build());
        assertEquals("@name='n1' or @name='n2' or @name='n3'", hasAttributeValuesOr("name", Arrays.
                asList("n1", "n2", "n3")).build());

        assertEquals("xPath[contains('columnIdRefs',@id)]/@id", path(xPathPredicate("xPath",
                contains(quote("columnIdRefs"), attr("id"))), attr("id")).build());

        assertEquals("xPath[last()]", xPathPredicate("xPath", LAST).build());

        assertEquals("ancestor::split", ancestor("split").build());

        assertEquals("xPath/..", pathParent("xPath").build());

        assertEquals("xPath[position()<2]", xPathPredicate("xPath", less(2)).build());

        assertEquals("xPath[position()>1 and position()<=5]", xPathPredicate("xPath",
                and(greater(1), lessEqual(5))).build());

        assertEquals("xPath/@name", path("xPath", attr("name")).build());

        assertEquals("xPath/text()", path("xPath", TEXT).build());

        assertEquals("'excludedTagName'", quote("excludedTagName").build());
        assertEquals("concat(\"excludedTag'\",'\"Name')", quote("excludedTag'\"Name").build());
        assertEquals("concat('excludedTag\"',\"'\",'\"',\"'Name\")", quote("excludedTag\"'\"'Name").build());
        assertEquals("\"excludedTag'Name\"", quote("excludedTag'Name").build());
        assertEquals("'excludedTag\"Name'", quote("excludedTag\"Name").build());
        assertEquals("concat(\"'a\",'\"b',\"'\",'\"c\"',\"''\",'\"')", quote("'a\"b'\"c\"''\"").build());
    }
}
