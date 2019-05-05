package model;

import org.pfsw.tools.cda.base.model.ClassInformation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"selfInformation", "referredClassesInformation"})
public class ClassAndReferredInformation implements Exportable{

    private ClassInformation selfInformation;
    private ClassInformation[] referredClassesInformation;

    public ClassInformation getSelfInformation() {
        return selfInformation;
    }

    @XmlElement
    public void setSelfInformation(ClassInformation selfInformation) {
        this.selfInformation = selfInformation;
    }

    public ClassInformation[] getReferredClassesInformation() {
        return referredClassesInformation;
    }

    @XmlElement
    public void setReferredClassesInformation(ClassInformation[] referredClassesInformation) {
        this.referredClassesInformation = referredClassesInformation;
    }
}
