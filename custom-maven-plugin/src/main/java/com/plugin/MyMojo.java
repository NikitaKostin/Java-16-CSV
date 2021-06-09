package com.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "version", defaultPhase = LifecyclePhase.INITIALIZE)
public class MyMojo extends AbstractMojo {

    @Parameter(property = "version.javaVersion")
    String javaVersion;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("System information from custom plugin" +
                "\nJava version from parent: " + javaVersion +
                "\nName of the OS: " + System.getProperty("os.name") +
                "\nVersion of the OS: " +
                System.getProperty("os.version") +
                "\nArchitecture of THe OS: " +
                System.getProperty("os.arch") +
                "\nAvailable processors (cores): " + Runtime.getRuntime().availableProcessors()
        );

    }
}
