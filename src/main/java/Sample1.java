// ===========================================================================
// CONTENT  : CLASS Sample1
// AUTHOR   : M.Duchrow
// VERSION  : 1.0 - 23/03/2014
// HISTORY  :
//  23/03/2014  mdu  CREATED
//
// Copyright (c) 2014, by MDCS. All rights reserved.
// ===========================================================================

// ===========================================================================
// IMPORTS
// ===========================================================================

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.pfsw.text.CommandLineArguments;
import org.pfsw.tools.cda.base.model.ClassInformation;
import org.pfsw.tools.cda.base.model.IAnalyzableElement;
import org.pfsw.tools.cda.base.model.Workset;
import org.pfsw.tools.cda.base.model.util.StringFilter;
import org.pfsw.tools.cda.base.model.workset.ClasspathPartDefinition;
import org.pfsw.tools.cda.base.util.CollectionUtil;
import org.pfsw.tools.cda.core.dependency.analyzer.DependencyAnalyzer;
import org.pfsw.tools.cda.core.dependency.analyzer.model.DependencyInfo;
import org.pfsw.tools.cda.core.init.WorksetInitializer;
import org.pfsw.tools.cda.core.processing.IProgressMonitor;
import org.pfsw.tools.cda.core.processing.WaitingIElementsProcessingResultHandler;
import org.pfsw.util.SysUtil;

/**
 * An example for working with CDA without GUI.
 *
 * @author M.Duchrow
 * @version 2.0
 */
public class Sample1 {
    // =========================================================================
    // CONSTANTS
    // =========================================================================
    private static final CollectionUtil CU = CollectionUtil.instance();
    private static final boolean IS_MONITORING = false;
    private static final String CLASS_TO_ANALYZE = "org.pfsw.tools.cda.core.init.ContainerLookupTask";

    // =========================================================================
    // INSTANCE VARIABLES
    // =========================================================================

    // =========================================================================
    // CLASS METHODS
    // =========================================================================
    public static void main(String[] args) {
        CommandLineArguments commandArgs;
        Sample1 inst;

        inst = new Sample1();
        commandArgs = new CommandLineArguments(args);
        inst.run(commandArgs);
        System.err.flush();
        System.out.flush();
        SysUtil.current().exit(0, 100);
    }

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public Sample1() {
        super();
    }

    // =========================================================================
    // PROTECTED INSTANCE METHODS
    // =========================================================================
    protected void run(CommandLineArguments args) {
        Workset workset;
        String sample;

        // Create a workset with a defined classpath
        workset = this.createWorkset();
        // Load all elements on the claspath and pre-analyze them (might take a while!)
        this.initializeWorkset(workset);

        sample = args.getArgumentValue("-sample");

        if ("1".equals(sample)) {
            this.showClassesMatching(workset, "F.*r$");
        } else if ("2".equals(sample)) {
            this.showDependenciesOf(workset, CLASS_TO_ANALYZE);
        } else if ("3".equals(sample)) {
            this.showAllDependenciesOf(workset, CLASS_TO_ANALYZE);
        } else if ("4".equals(sample)) {
            this.showDependantsOf(workset, CLASS_TO_ANALYZE);
        }
    }

    protected void showClassesMatching(Workset workset, String namePattern) {
        Pattern pattern = Pattern.compile(namePattern);
        ClassNameFinder processor;
        List<ClassInformation> result = new ArrayList<>();
        ClassInformation[] classes;

        processor = new ClassNameFinder(pattern);
        workset.processClassInformationObjects(result, processor);
        System.out.println("Scanned " + processor.getResultData() + " classes.");
        System.out.println("Found " + result.size() + " classes for pattern: " + namePattern);
        classes = CU.asArray(result, ClassInformation.class);
        this.showResult(classes);
    }

    protected void showDependenciesOf(Workset workset, String className) {
        ClassInformation classInfo;
        ClassInformation[] classes;

        // Lookup the class of interest
        classInfo = workset.getClassInfo(className);

        classes = CU.asArray(classInfo.getReferredClasses(), ClassInformation.class);
        System.out.println(classInfo.getName() + " dependes on " + classes.length + " classes:");
        this.showResult(classes);
    }

    protected void showAllDependenciesOf(Workset workset, String className) {
        ClassInformation classInfo;
        ClassInformation[] classes;

        // Lookup the class of interest
        classInfo = workset.getClassInfo(className);

        IAnalyzableElement elementToAnalyze = classInfo;
        DependencyAnalyzer analyzer = new DependencyAnalyzer(elementToAnalyze);
        analyzer.analyze();
        DependencyInfo result = analyzer.getResult();

        classes = result.getAllReferredClasses();
        System.out.println(classInfo.getName() + " dependes on " + classes.length + " classes:");
        this.showResult(classes);
    }

    protected void showDependantsOf(Workset workset, String className) {
        ClassInformation classInfo;
        ClassInformation[] dependants;

        // Lookup the class of interest
        classInfo = workset.getClassInfo(className);

        WaitingIElementsProcessingResultHandler searchHandler = new WaitingIElementsProcessingResultHandler();
        dependants = searchHandler.findDependantsOfClass(classInfo, "01", null, true);
        System.out.println(classInfo.getName() + " has " + dependants.length + " classes depending on it:");
        this.showResult(dependants);
    }

    protected void showResult(ClassInformation[] resultData) {
        for (ClassInformation classInformation : resultData) {
            System.out.println(classInformation.getName());
        }
    }

    protected Workset createWorkset() {
        Workset workset;
        workset = new Workset("Sample1");
        ClasspathPartDefinition partDefinition;

        partDefinition = new ClasspathPartDefinition("example-libs/*.jar");
        workset.addClasspathPartDefinition(partDefinition);
        workset.addIgnoreFilter(new StringFilter("java.*"));
        return workset;
    }

    protected void initializeWorkset(Workset workset) {
        WorksetInitializer wsInitializer;
        IProgressMonitor monitor = null;

        if (IS_MONITORING) {
            monitor = new ConsoleMonitor();
        }

        wsInitializer = new WorksetInitializer(workset);

        // Running with no progress monitor (null) is okay as well.
        wsInitializer.initializeWorksetAndWait(monitor);
    }

}
