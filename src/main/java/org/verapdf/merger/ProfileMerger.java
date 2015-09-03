package org.verapdf.merger;

import org.apache.log4j.Logger;
import org.verapdf.utils.config.VeraPDFMergerConfig;
import org.verapdf.utils.filter.XMLFileFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Evgeniy Muravitskiy
 */
public final class ProfileMerger {

	private static final Logger logger = Logger.getLogger(ProfileMerger.class);

	public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH-mm-ss'Z'X";

	public static final String PROFILE = "profile";
	public static final String MODEL = "model";
	public static final String NAMESPACE = "xmlns";
	public static final String RULES = "rules";
	public static final String RULE = "rule";
	public static final String VARIABLES = "variables";
	public static final String VARIABLE = "variable";
	public static final String ID = "id";

	private static final String NAME_TAG = "name";
	private static final String DESCRIPTION_TAG = "description";
	private static final String CREATOR_TAG = "creator";
	private static final String CREATED_TAG = "created";
	private static final String HASH_TAG = "hash";

	private static final String HASH_TAG_VALUE = "sha-1 hash code";

	public static void mergeProfiles(VeraPDFMergerConfig config)
			throws ParserConfigurationException, TransformerException, SAXException, IOException {
		List<Node> rules = new ArrayList<>();
		List<Node> variables = new ArrayList<>();

		mergeProfiles(new File(config.getInputPath().getPath()), rules, variables);
		saveAll(config, rules, variables);
	}

	public static void mergeProfiles(File file, List<Node> rules, List<Node> variables)
			throws ParserConfigurationException, IOException, SAXException, TransformerException {
		if (file.isFile()) {
			getRulesAndVariables(file, rules, variables);
		} else if (file.isDirectory()) {
			File[] files = file.listFiles(XMLFileFilter.getInstance());
			if (files != null) {
				for (File currentFile : files) {
					if (currentFile.isDirectory()) {
						mergeProfiles(currentFile, rules, variables);
					} else if (currentFile.isFile()) {
						getRulesAndVariables(currentFile, rules, variables);
					}
				}
			}
		} else {
			logger.error("Incompatible type of input file.");
		}

	}

	private static void getRulesAndVariables(File file, List<Node> rules, List<Node> variables)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = getDocumentBuilder();
		Document document = builder.parse(file);

		getRulesAndVariables(document, rules, variables);
	}

	private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		factory.setIgnoringElementContentWhitespace(true);
		return builder;
	}

	private static void getRulesAndVariables(Document document, List<Node> rules, List<Node> variables)
			throws ParserConfigurationException, SAXException, IOException {
		Node rootNode = document.getDocumentElement();
		if (PROFILE.equals(rootNode.getNodeName())) {
			rootNode.normalize();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				String childNodeName = child.getNodeName();
				switch (childNodeName) {
					case RULES:
						getRules(rules, child);
						break;
					case VARIABLES:
						getVariables(variables, child);
						break;
				}
			}
		}
	}

	private static void getRules(List<Node> rules, Node node) {
		getElements(rules, node, RULE, ID);
	}

	private static void getElements(List<Node> rules, Node node, String expectedNode, String attrName) {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			if (expectedNode.equals(childNode.getNodeName())
					&& !isElementContains(rules, childNode, attrName)) {
				rules.add(childNode);
			}
		}
	}

	private static boolean isElementContains(List<Node> elements, Node element, String attrName) {
		String attribute = element.getAttributes().getNamedItem(attrName).getNodeValue();
		if (attribute != null) {
			for (Node node : elements) {
				String nodeAttr = node.getAttributes().getNamedItem(attrName).getNodeValue();
				if (attribute.equals(nodeAttr)) {
					return true;
				}
			}
			return false;
		} else {
			logger.error("Some node does not contain required attribute. This node will not be added");
			return true;
		}
	}

	private static void getVariables(List<Node> variables, Node node) {
		getElements(variables, node, VARIABLE, NAME_TAG);
	}

	private static void saveAll(VeraPDFMergerConfig config,
								List<Node> rules, List<Node> variables)
								throws ParserConfigurationException, TransformerException {
		DocumentBuilder builder = getDocumentBuilder();
		Document document = builder.newDocument();

		setDocumentRootElement(config, document);

		addDefaultTags(document, config);

		addRulesToDocument(rules, document);

		addVariablesToDocument(variables, document);

		document.normalize();

		transformToXML(config.getOutputPath(), document);
	}

	private static void setDocumentRootElement(VeraPDFMergerConfig config, Document document) {
		Element root = document.createElement(PROFILE);
		document.appendChild(root);

		root.setAttribute(NAMESPACE, config.getNamespace());
		root.setAttribute(MODEL, config.getModel());
	}

	private static void addDefaultTags(Document document, VeraPDFMergerConfig config) {
		addDefaultTag(document, NAME_TAG, config.getNameTagValue());

		addDefaultTag(document, DESCRIPTION_TAG, config.getDescriptionTagValue());

		addDefaultTag(document, CREATOR_TAG, config.getCreatorTagValue());

		Date date = Calendar.getInstance(Locale.US).getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		addDefaultTag(document, CREATED_TAG, formatter.format(date));

		addDefaultTag(document, HASH_TAG, HASH_TAG_VALUE);
	}

	private static void addDefaultTag(Document document, String tagName, String tagValue) {
		Element element = document.createElement(tagName);
		document.getDocumentElement().appendChild(element);
		element.appendChild(document.createTextNode(tagValue));
	}

	private static void addRulesToDocument(List<Node> rules, Document document) {
		addElementsToDocument(rules, document, RULES);
	}

	private static void addElementsToDocument(List<Node> rules, Document document, String rootTag) {
		if (!rules.isEmpty()) {
			Element rulesRoot = document.createElement(rootTag);
			document.getDocumentElement().appendChild(rulesRoot);
			for (Node node : rules) {
				Node buffer = document.importNode(node, true);
				rulesRoot.appendChild(buffer);
			}
		}
	}

	private static void addVariablesToDocument(List<Node> variables, Document document) {
		addElementsToDocument(variables, document, VARIABLES);
	}

	private static void transformToXML(String outputPath, Document document) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(outputPath));

		transformer.transform(source, result);
	}

}
