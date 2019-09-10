package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"className", "refNames"})
public class ClassAndReferredNames implements Exportable {

    private String className;
    private List<String> refNames;

    public String getClassName() {
        return className;
    }

    @XmlElement
    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getRefNames() {
        return refNames;
    }

    @XmlElement(name = "refName")
    public void setRefNames(List<String> refNames) {
        this.refNames = refNames;
    }
}
