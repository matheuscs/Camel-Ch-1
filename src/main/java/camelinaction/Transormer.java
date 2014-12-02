package camelinaction;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

public class Transormer {
	public String transformContent(String body) throws Exception {
		String outPut = "";

		// Crio uma fabrica de construtores de documentos
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// Crio o construtor de documentos
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		// Transformo a mensagem recebida em um documento para que possamos
		// iterar sobre os elementos
		Document doc = docBuilder.parse(new InputSource(
				new ByteArrayInputStream(body.getBytes("utf-8"))));
		// identifico os nodes que contem a estrutura desejada de conteudo da
		// mensagem
		NodeList itemTag = doc.getElementsByTagName("item");

		// itero Sobre cada node para extrair as informações das TAGs desejadas
		for (int s = 0; s < itemTag.getLength(); s++) {
			Node fstNode = itemTag.item(s);
			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element fstElmnt = (Element) fstNode;
				NodeList nmElmntLst = fstElmnt.getElementsByTagName("name");
				Element nmElmnt = (Element) nmElmntLst.item(0);
				NodeList nm = nmElmnt.getChildNodes();
				outPut = outPut + ((Node) nm.item(0)).getNodeValue() + ";";

				NodeList amountElmntLst = fstElmnt
						.getElementsByTagName("amount");
				Element amountElmnt = (Element) amountElmntLst.item(0);
				NodeList amount = amountElmnt.getChildNodes();
				outPut = outPut + ((Node) amount.item(0)).getNodeValue() + ";";

				outPut = outPut + "\r\n";
			}
		}

		// Por fim retorno uma string que sera a minha mensagem destino
		return outPut;
	}

}