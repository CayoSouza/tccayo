package analyzer;

import model.ClassAndReferredNames;
import model.Program;
import org.pfsw.tools.cda.base.model.ClassInformation;
import org.pfsw.tools.cda.base.model.GenericClassContainer;
import org.pfsw.tools.cda.base.model.Workset;
import org.pfsw.tools.cda.base.model.workset.ClasspathPartDefinition;
import org.pfsw.tools.cda.core.init.WorksetInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.XmlMarshallerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AnalyzerFacade {
    private static final Logger logger = LoggerFactory.getLogger(AnalyzerFacade.class);

    //TODO: configurar spring
//    @Autowired
//    XmlMarshallerService xmlMarshallerService;

    private final static String JARS_LOCATION = "C:\\Users\\cayo\\Desktop\\desktop\\TCCAYO\\tcc\\samples\\cayoTcc\\src\\main\\resources\\jars\\";
    private final static String EXCLUDE_REGEX_LIST = "java.lang.*|java.util.*|android.app.Activity.*|android.app.Fragment.*" +
            "|java.*|javax.*|com.sun.*|org.xml.sax*|org.omg.*|org.w3c.dom.*|android.util.*|android.widget.*" +
            "|android.view.*|android.support.*|android.content.*|android.os.*|android.graphics.*|android.animation.*" +
            "|com.facebook.*|com.google.android.gms.*";

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
        logger.info("Criando workset com os jars fornecidos em: {}", JARS_LOCATION);
        long wsStartTime = System.currentTimeMillis();
        Workset workset = createEnvironment("Workset1", JARS_LOCATION + "*.jar");
        long wsEndTime = System.currentTimeMillis();
        logger.info("Workset carregado em: {}ms", (wsEndTime - wsStartTime));

        Program program = new Program();

        long startTime = System.currentTimeMillis();
        /* Para cada apk */
        for (GenericClassContainer gcc : workset.getClassContainers()) {
            String apkName = gcc.getSimpleName();
            program.setApkName(apkName);
            //TODO: add version

            /* Popula a lista de classes e classes referenciadas */
            logger.info("Criando lista de classes para o apk: {}", apkName);
            List<ClassAndReferredNames> classAndRefs = buildClassAndRefList(gcc);
            long endTime = System.currentTimeMillis();
            logger.info("TEMPO PARA ANALIZAR O APK {}: {}ms", apkName, (endTime - startTime));

            program.setClassAndReferredNames(classAndRefs);

            /* Salva o XML do apk */
            //TODO: acertar path
            logger.info("Salvando lista de classes no arquivo XML na em: {}", JARS_LOCATION);
            exportToXML(program, JARS_LOCATION + apkName.replace(".jar", "") + ".xml");
        }
    }

    private void exportToXML(Program program, String filePath) {
        XmlMarshallerService xmlMarshallerService = new XmlMarshallerService();
        xmlMarshallerService.marshall(program, Program.class, filePath);
    }

    private List<ClassAndReferredNames> buildClassAndRefList(GenericClassContainer gcc) {
        List<ClassAndReferredNames> classAndRefs = new ArrayList<>();

//        for(ClassInformation classInfo : workset.getAllContainedClasses()) {

//        ClassesSearchResult<ClassInformation> classesSearchResult = new ClassesSearchResult<>(
//                gcc.getAllContainedClasses()[0],
//                gcc.getAllContainedClasses()
//        );

        for (ClassInformation classInfo : gcc.getAllContainedClasses()) {
//            logger.info("Iniciando analyzer para: {}", classInfo.getName());
//            DependencyAnalyzer analyzer = new DependencyAnalyzer(classInfo);
//            analyzer.analyze();
//            DependencyInfo result = analyzer.getResult();
//
//            List<ClassInformation> classes = Arrays.asList(result.getAllReferredClasses());

            if (!classInfo.getName().matches(EXCLUDE_REGEX_LIST)) {
                ClassAndReferredNames carn = new ClassAndReferredNames();
                carn.setClassName(classInfo.getClassName());
                List<String> referredNames = classInfo.getReferredClasses().stream()
                        .map(ClassInformation::getClassName)
                        .filter(s -> !s.matches(EXCLUDE_REGEX_LIST))
                        .collect(Collectors.toList());

    //            List<String> referredNames = classes.stream()
    //                    .map(ClassInformation::getClassName)
    //                    .collect(Collectors.toList());
                carn.setRefNames(referredNames);
                classAndRefs.add(carn);
            }

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
