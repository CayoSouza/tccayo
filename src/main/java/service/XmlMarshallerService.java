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

    public <T> void marshall(Exportable classAndReferred, Class<T> clazz){
        try {
            File file = new File("C:\\Users\\cayo\\Desktop\\TCCAYO\\tcc\\samples\\cayoTcc\\src\\main\\resources\\classAndReferred.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(classAndReferred, file);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(classAndReferred, sw);
            String xmlStr = sw.toString();

            System.out.println(xmlStr);

        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
