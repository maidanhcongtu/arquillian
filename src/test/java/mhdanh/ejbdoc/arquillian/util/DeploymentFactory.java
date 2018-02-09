package mhdanh.ejbdoc.arquillian.util;

import java.io.File;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

import mhdanh.ejbdoc.arquillian.ApplicationConfig;

@ArquillianSuiteDeployment
public class DeploymentFactory {
	
	@Deployment
	@OverProtocol("Servlet 3.0")
	@Cleanup(phase = TestExecutionPhase.BEFORE)
	@UsingDataSet({ 
		"datasets/classStudent.xml",
		"datasets/student.xml",
		"datasets/student2.xml"
	})
	public static WebArchive createFullDeployment()  {
		final WebArchive war = ShrinkWrap.create(WebArchive.class, "ejbdoc_arquillian.war")
				.addAsLibraries(getFiles("com.fasterxml.jackson.datatype:jackson-datatype-jsr310"))
				.addAsLibraries(getFiles("com.google.code.gson:gson"))
				.addPackages(true, ApplicationConfig.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		return war;
	}

	public static File[] getFiles(String artifactName) {
		final PomEquippedResolveStage Pom = Maven.resolver().loadPomFromFile("pom.xml");
		File[] files = Pom.resolve(artifactName).withTransitivity().asFile();
		return files;
	}
	
}
