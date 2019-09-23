package service;

import model.ClassAndReferredInformation;
import model.ClassAndReferredNames;
import model.Exportable;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.io.StringWriter;

@Service
public class XmlMarshallerService {

    public <T> void marshall(Exportable classAndReferred, Class<T> clazz, String filePath){
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(classAndReferred, file);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(classAndReferred, sw);
//            String xmlStr = sw.toString();
//
//            System.out.println(xmlStr);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
