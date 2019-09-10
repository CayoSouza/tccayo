package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"apkName", "apkVersion", "classAndReferredNames"})
public class Program implements Exportable{

    private String apkName;
    private String apkVersion = "0";
    private List<ClassAndReferredNames> classAndReferredNames;

    public String getApkName() {
        return apkName;
    }

    @XmlElement(name = "name")
    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    @XmlElement(name = "version")
    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public List<ClassAndReferredNames> getClassAndReferredNames() {
        return classAndReferredNames;
    }

    @XmlElement(name = "classes")
    public void setClassAndReferredNames(List<ClassAndReferredNames> classAndReferredNames) {
        this.classAndReferredNames = classAndReferredNames;
    }
}
