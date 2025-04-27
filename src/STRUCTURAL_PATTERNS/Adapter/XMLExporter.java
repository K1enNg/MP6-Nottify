package STRUCTURAL_PATTERNS.Adapter;

import CREATIONAL_PATTERNS.Factory.Appointment;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.text.SimpleDateFormat;

public class XMLExporter {
    public static void exportAppointment(Appointment appointment, String filePath) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // Create root element
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("appointment");
        doc.appendChild(rootElement);

        // Add appointment details
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        Element dateElement = doc.createElement("date");
        dateElement.appendChild(doc.createTextNode(sdf.format(appointment.getDate())));
        rootElement.appendChild(dateElement);

        Element detailsElement = doc.createElement("details");
        detailsElement.appendChild(doc.createTextNode(appointment.getDetails()));
        rootElement.appendChild(detailsElement);

        Element typeElement = doc.createElement("type");
        typeElement.appendChild(doc.createTextNode(appointment.getClass().getSimpleName().replace("Appointment", "")));
        rootElement.appendChild(typeElement);

        Element patientElement = doc.createElement("patient");
        patientElement.appendChild(doc.createTextNode(appointment.patient.getName()));
        rootElement.appendChild(patientElement);

        Element statusElement = doc.createElement("status");
        statusElement.appendChild(doc.createTextNode(appointment.isConfirmed() ? "Confirmed" : "Pending"));
        rootElement.appendChild(statusElement);

        // Write to XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}
