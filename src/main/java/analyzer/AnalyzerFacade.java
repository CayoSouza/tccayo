package analyzer;

import model.ClassAndReferredNames;
import model.Program;
import org.pfsw.tools.cda.base.model.ClassInformation;
import org.pfsw.tools.cda.base.model.GenericClassContainer;
import org.pfsw.tools.cda.base.model.Workset;
import org.pfsw.tools.cda.base.model.workset.ClasspathPartDefinition;
import org.pfsw.tools.cda.core.init.WorksetInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import service.XmlMarshallerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AnalyzerFacade {

    //TODO: configurar spring
    @Autowired
    XmlMarshallerService xmlMarshallerService;

    public void analyze() {

        Workset workset = createEnvironment("Workset1", "C:/Users/cayo/Documents/tcc/samples/cayoTcc/src/main/resources/*.jar");

        Random random = new Random();
        int randomIndex;
        int length = workset.getClassContainers()[0].getPackages().length;
        ClassInformation[] allContainedClasses;

        do {
            allContainedClasses = workset.getClassContainers()[0].getPackages()[random.nextInt(length)].getAllContainedClasses();
            randomIndex = random.nextInt(allContainedClasses.length);
        } while (allContainedClasses[randomIndex].getReferredClasses().size() < 20);

        ClassInformation classInfo = workset.getClassInfo(allContainedClasses[randomIndex].getClassName());
        ClassInformation[] referredClasses = classInfo.getReferredClassesArray();

//        ClassAndReferredInformation classAndReferredInformation = new ClassAndReferredInformation();
//        classAndReferredInformation.setSelfInformation(classInfo);
//        classAndReferredInformation.setReferredClassesInformation(referredClasses);

        ClassAndReferredNames classAndReferredNames = new ClassAndReferredNames();
        classAndReferredNames.setClassName(classInfo.getName());

        List<String> referredNames = Arrays.stream(referredClasses).map(ClassInformation::getClassName).collect(Collectors.toList());

        classAndReferredNames.setRefNames(referredNames);

        XmlMarshallerService xmlMarshallerService = new XmlMarshallerService();
//        xmlMarshallerService.marshall(classAndReferredInformation);
//        xmlMarshallerService.marshall(classAndReferredNames, ClassAndReferredNames.class);
    }

    public void analyze2() {
        Workset workset = createEnvironment("Workset1", "C:\\Users\\cayo\\Desktop\\TCCAYO\\tcc\\samples\\cayoTcc\\src\\main\\resources\\*.jar");

        Program program = new Program();

        /* Para cada apk */
        for (GenericClassContainer gcc : workset.getClassContainers()) {
            String apkName = gcc.getSimpleName();
            program.setApkName(apkName);
            //TODO: add version

            /* Popula a lista de classes e classes referenciadas */
            List<ClassAndReferredNames> classAndRefs = buildClassAndRefList(gcc);
            program.setClassAndReferredNames(classAndRefs);

            /* Salva o XML do apk */
            //TODO: acertar path
            exportToXML(program, "C:\\Users\\cayo\\Desktop\\TCCAYO\\tcc\\samples\\cayoTcc\\src\\main\\resources\\" + apkName.replace(".jar", "") + ".xml");
        }
    }

    private void exportToXML(Program program, String filePath) {
        XmlMarshallerService xmlMarshallerService = new XmlMarshallerService();
        xmlMarshallerService.marshall(program, Program.class, filePath);
    }

    private List<ClassAndReferredNames> buildClassAndRefList(GenericClassContainer gcc) {
        List<ClassAndReferredNames> classAndRefs = new ArrayList<>();

//        for(ClassInformation classInfo : workset.getAllContainedClasses()) {
        for (ClassInformation classInfo : gcc.getAllContainedClasses()) {
            ClassAndReferredNames carn = new ClassAndReferredNames();
            carn.setClassName(classInfo.getClassName());
            List<String> referredNames = classInfo.getReferredClasses().stream().map(ClassInformation::getClassName).collect(Collectors.toList());
            carn.setRefNames(referredNames);
            classAndRefs.add(carn);
        }

        return classAndRefs;
    }

    private Workset createEnvironment(String worksetName, String jarsLocation) {
        Workset workset = new Workset(worksetName);
        ClasspathPartDefinition partDefinition = new ClasspathPartDefinition(jarsLocation);
        workset.addClasspathPartDefinition(partDefinition);
        WorksetInitializer wsInitializer = new WorksetInitializer(workset);
        wsInitializer.initializeWorksetAndWait(null);
        return workset;
    }
}
