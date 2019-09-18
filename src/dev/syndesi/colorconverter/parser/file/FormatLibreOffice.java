package dev.syndesi.colorconverter.parser.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import dev.syndesi.colorconverter.Color;

/**
 * Class for converting between the internally used color-array and the .soc-fileformat used by Libre Office.
 * @author Syndesi
 * @since 1.0
 */
public class FormatLibreOffice extends FileParser {

	/**
	 * Imports a single .soc file and tries to extract its colors.
	 * @param path The path to the file
	 * @return Returns a list of colors
	 * @throws IOException on IO errors
	 * @throws ParserConfigurationException on XML errors
	 * @throws SAXException on XML errors
	 */
	public Color[] importFile (String path) throws IOException, ParserConfigurationException, SAXException {
		List<Color> colors = new ArrayList<Color>();
		// parse the XML-document
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(path));
		Element root = doc.getDocumentElement();
		// iterate over all color-nodes
		NodeList colorNode = root.getElementsByTagName("draw:color");
		for (int i = 0; i < colorNode.getLength(); i++) {
			try {
				Element node = (Element) colorNode.item(i);
				String title = node.getAttribute("draw:name");
				String color = node.getAttribute("draw:color");
				Color c = new Color();
				c.setRed(Integer.parseInt(color.substring(1, 3), 16));
				c.setGreen(Integer.parseInt(color.substring(3, 5), 16));
				c.setBlue(Integer.parseInt(color.substring(5, 7), 16));
				c.setTitle(title);
				colors.add(c);
			} catch (Exception e) {
				// do not handle faulty color
			}
		}
		return colors.toArray(new Color[colors.size()]);
	}

	/**
	 * Exports an array of colors into a .soc file.
	 * @param pathString the path to the new file, existing files will be overwritten
	 * @param palette the array of colors which should be included in the final file
	 * @throws Exception on errors (io/xml exceptions etc.)
	 */
	public void exportFile (String pathString, Color[] palette) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		// add root-element and it's attributes
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("office:color-table");
		rootElement.setAttribute("xmlns:office", "http://openoffice.org/2000/office");
		rootElement.setAttribute("xmlns:style", "http://openoffice.org/2000/style");
		rootElement.setAttribute("xmlns:text", "http://openoffice.org/2000/text");
		rootElement.setAttribute("xmlns:table", "http://openoffice.org/2000/table");
		rootElement.setAttribute("xmlns:draw", "http://openoffice.org/2000/drawing");
		rootElement.setAttribute("xmlns:fo", "http://www.w3.org/1999/XSL/Format");
		rootElement.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
		rootElement.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
		rootElement.setAttribute("xmlns:meta", "http://openoffice.org/2000/meta");
		rootElement.setAttribute("xmlns:number", "http://openoffice.org/2000/datastyle");
		rootElement.setAttribute("xmlns:svg", "http://www.w3.org/2000/svg");
		rootElement.setAttribute("xmlns:chart", "http://openoffice.org/2000/chart");
		rootElement.setAttribute("xmlns:dr3d", "http://openoffice.org/2000/dr3d");
		rootElement.setAttribute("xmlns:math", "http://www.w3.org/1998/Math/MathML");
		rootElement.setAttribute("xmlns:form", "http://openoffice.org/2000/form");
		rootElement.setAttribute("xmlns:script", "http://openoffice.org/2000/script");
		doc.appendChild(rootElement);
		// add all color-nodes to the root-attribute
		for (int i = 0; i < palette.length; i++) {
			try {
				Element colorNode = doc.createElement("draw:color");
				colorNode.setAttribute("draw:name", palette[i].getTitle());
				String colorString = "#";
				colorString += StringUtils.leftPad(Integer.toHexString(palette[i].getRed() & 0xff), 2, '0');
				colorString += StringUtils.leftPad(Integer.toHexString(palette[i].getGreen() & 0xff), 2, '0');
				colorString += StringUtils.leftPad(Integer.toHexString(palette[i].getBlue() & 0xff), 2, '0');
				colorNode.setAttribute("draw:color", colorString);
				rootElement.appendChild(colorNode);
			} catch (Exception e) {
				// do not handle faulty colors, go directly to the next color
			}
		}
		// export pretty-printed xml-file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(pathString));
		transformer.transform(source, result);
	}
	
}
