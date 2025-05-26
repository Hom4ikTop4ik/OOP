package ru.nsu.martynov.loader;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.martynov.dsl.ConfigBaseScript;
import ru.nsu.martynov.model.CourseConfig;

import java.io.File;

public class ConfigLoader {
    public CourseConfig loadConfiguration(File groovyFile) throws Exception {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(ConfigBaseScript.class.getName());

        GroovyShell shell = new GroovyShell(getClass().getClassLoader(), config);
        Script script = shell.parse(groovyFile);
        script.run();

        return ((ConfigBaseScript) script).getCourseConfig();
    }
}
