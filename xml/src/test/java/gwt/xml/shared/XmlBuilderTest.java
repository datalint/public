package gwt.xml.shared;

import gwt.xml.shared.xml.XmlText;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gwt.xml.shared.XmlBuilder.createXmlElement;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlBuilderTest {
    @Test
    public void testBuild() {
        assertEquals("<div>text</div>", createXmlElement("div", "text").build());
        assertEquals("<div><span/></div>", createXmlElement("div",
                createXmlElement("span")).build());
        assertEquals("<div><span/>text</div>", createXmlElement("div",
                createXmlElement("span"), new XmlText("text")).build());

        List<String> attributes = new ArrayList<>();
        attributes.add("from");
        attributes.add(Long.toString(10));
        attributes.add("to");
        attributes.add(Long.toString(20));

        assertEquals("<div><span from='10' to='20'/></div>", createXmlElement("div",
                createXmlElement("span", attributes)).build());

        assertEquals("<div><span from='10' to='20'><sub/></span></div>", createXmlElement("div",
                createXmlElement("span", attributes, Arrays.asList(createXmlElement("sub")))).build());

        assertEquals("<div><span from='10' to='20'><sub/><sub>subText</sub></span></div>",
                createXmlElement("div", createXmlElement("span", attributes, Arrays.asList(
                        createXmlElement("sub"), createXmlElement("sub", "subText")))).build());

        assertEquals("<div><span from='10' to='20'><sub/></span><sub>subText</sub></div>",
                createXmlElement("div", createXmlElement("span", attributes, Arrays.asList(
                        createXmlElement("sub"))), createXmlElement("sub", "subText")).build());

        assertEquals("<div><span from='10' to='20'/><sub/><sub>subText</sub></div>",
                createXmlElement("div", createXmlElement("span", attributes), createXmlElement(
                        "sub"), createXmlElement("sub", "subText")).build());
    }
}
