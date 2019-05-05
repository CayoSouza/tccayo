package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"selfName", "referredNames"})
public class ClassAndReferredNames implements Exportable {

    private String selfName;
    private List<String> referredNames;

    public String getSelfName() {
        return selfName;
    }

    @XmlElement
    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }

    public List<String> getReferredNames() {
        return referredNames;
    }

    @XmlElement
    public void setReferredNames(List<String> referredNames) {
        this.referredNames = referredNames;
    }
}
