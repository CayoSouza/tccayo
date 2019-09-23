package analyzer;

import analyzer.AnalyzerFacade;
import model.ClassAndReferredInformation;
import model.ClassAndReferredNames;
import org.pfsw.tools.cda.base.model.ClassInformation;
import org.pfsw.tools.cda.base.model.ClassPackage;
import org.pfsw.tools.cda.base.model.Workset;
import org.pfsw.tools.cda.base.model.workset.ClasspathPartDefinition;
import org.pfsw.tools.cda.core.init.WorksetInitializer;
import org.pfsw.tools.cda.core.io.workset.WorksetWriter;
import org.pfsw.tools.cda.core.io.workset.WorksetWriterException;
import org.pfsw.tools.cda.ext.export.xml.XmlFileODEMExporter;
import org.pfsw.tools.cda.plugin.export.spi.AModelExporter;
import org.pfsw.tools.cda.xpi.PluginConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import service.XmlMarshallerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        AnalyzerFacade analyzerFacade = new AnalyzerFacade();
        analyzerFacade.analyze2();
    }
//        Workset workset = new Workset("analyzer.Application");
////        ClasspathPartDefinition partDefinition = new ClasspathPartDefinition("C:/Windows.old.000/Users/cayo/Documents/TCC/dex2jar-2.0/test/*.jar");
//        ClasspathPartDefinition partDefinition = new ClasspathPartDefinition("C:/Users/cayo/Documents/tcc/samples/cayoTcc/src/main/resources/*.jar");
//        workset.addClasspathPartDefinition(partDefinition);
//        WorksetInitializer wsInitializer = new WorksetInitializer(workset);
//        wsInitializer.initializeWorksetAndWait(null);
//
//        Random random = new Random();
//        int randomIndex;
//        int length = workset.getClassContainers()[0].getPackages().length;
//        ClassInformation[] allContainedClasses;
//
//        do {
//            allContainedClasses = workset.getClassContainers()[0].getPackages()[random.nextInt(length)].getAllContainedClasses();
//            randomIndex = random.nextInt(allContainedClasses.length);
//        } while (allContainedClasses[randomIndex].getReferredClasses().size() < 20);
//
//        ClassInformation classInfo = workset.getClassInfo(allContainedClasses[randomIndex].getClassName());
//        ClassInformation[] referredClasses = classInfo.getReferredClassesArray();
//
////        ClassAndReferredInformation classAndReferredInformation = new ClassAndReferredInformation();
////        classAndReferredInformation.setSelfInformation(classInfo);
////        classAndReferredInformation.setReferredClassesInformation(referredClasses);
//
//        ClassAndReferredNames classAndReferredNames = new ClassAndReferredNames();
//        classAndReferredNames.setClassName(classInfo.getApkName());
//
//        List<String> referredNames = new ArrayList<>();
//        for (ClassInformation ci: referredClasses) {
//            referredNames.add(ci.getClassName());
//        }
//
//        classAndReferredNames.setRefNames(referredNames);
//
//        XmlMarshallerService xmlMarshallerService = new XmlMarshallerService();
////        xmlMarshallerService.marshall(classAndReferredInformation);
//        xmlMarshallerService.marshall(classAndReferredNames, ClassAndReferredNames.class);
//
//        ClassPackage[] directReferredPackages = classInfo.getDirectReferredPackages();
//
//        WorksetWriter worksetWriter = new WorksetWriter();
//
//        AModelExporter aModelExporter = new XmlFileODEMExporter();
//        PluginConfiguration pluginConfiguration = new PluginConfiguration();
//        pluginConfiguration.set("cda.export.filename", "test");
//        aModelExporter.initialize(pluginConfiguration);
////        aModelExporter.startContainer();
//        try {
//            worksetWriter.saveWorkset(workset);
//        } catch (WorksetWriterException e) {
//            e.printStackTrace();
//        }
//        workset.release();
//
////        ModelExportEngine modelExportEngine = ModelExportEngine.instance();
////        try {
////            modelExportEngine.export(classInfo, null, new AModelExporter() {
////                @Override
////                public String getPluginProvider() {
////                    return null;
////                }
////
////                @Override
////                public String getPluginVersion() {
////                    return null;
////                }
////
////                @Override
////                public boolean initialize(PluginConfiguration pluginConfiguration) {
////                    return false;
////                }
////            });
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }

}
