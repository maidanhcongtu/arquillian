package mhdanh.ejbdoc.arquillian.util;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

import mhdanh.ejbdoc.arquillian.ApplicationConfig;

public class DeploymentFactory {
	
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
