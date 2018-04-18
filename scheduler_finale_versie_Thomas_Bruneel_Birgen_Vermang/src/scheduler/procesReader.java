package scheduler;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class procesReader {
	
	procesReader(){
		
	}
	
	
	public Queue<Proces> readIn(String fileName) {
		Queue<Proces> processen = new LinkedList<Proces>();
			try{
				File xmlFile = new File(fileName);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				
				
				//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList processlist = doc.getElementsByTagName("process");

				//System.out.println("----------------------------");

				for (int temp = 0; temp < processlist.getLength(); temp++) {

					Node nNode = processlist.item(temp);

					//System.out.println("\nCurrent Element :" + nNode.getNodeName());

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						int pid=Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent());
						int arrivaltime=Integer.parseInt(eElement.getElementsByTagName("arrivaltime").item(0).getTextContent());
						int servicetime=Integer.parseInt(eElement.getElementsByTagName("servicetime").item(0).getTextContent());
						//System.out.println("pid : " +pid );
						//System.out.println(" arrivaltime: " + arrivaltime);
						//System.out.println("service time : " + servicetime);
						Proces p =new Proces(pid,arrivaltime,servicetime);
						processen.add(p);

					}
				}
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			return processen;
		}




}
