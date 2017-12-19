package com.mogobiz.gradle.api

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.gradle.api.tasks.compile.JavaCompile

import org.gradle.api.file.FileTree

import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencySet

class GroovyEclipseCompilerPlugin implements Plugin<Project> {

  void apply(Project project) {
    project.getPlugins().apply("java")

    final sourceCompatibility = 1.7
    final targetCompatibility = 1.7

    final Configuration config = project.configurations.create('groovyEclipseCompiler')
    config.dependencies.add(project.getDependencies().create("org.codehaus.groovy:groovy-eclipse-batch:2.1.8-01"))

//FIXME    project.configurations.getByName('compile').dependencies.add(project.getDependencies().create("org.codehaus.groovy:groovy-all:2.4.10"))

    FileTree groovySources = project.fileTree(dir: 'src/main/groovy').include('**/*.groovy')
    FileTree groovyTestSources = project.fileTree(dir: 'src/test/groovy').include('**/*.groovy')

    def newCompiler = { JavaCompile compile, ArrayList<FileTree> sources ->
      println("groovyEclipseCompiler")
      compile.options.fork = true
      compile.options.forkOptions.executable = 'java'
      compile.options.forkOptions.jvmArgs = ['-cp', project.configurations.groovyEclipseCompiler.asPath, 'org.eclipse.jdt.internal.compiler.batch.Main']
      compile.options.compilerArgs = ['-source', sourceCompatibility, '-target', targetCompatibility, '-nowarn'] + sources*.files.flatten() as String[]
    }

    // Set up the compileJava task
    final FileTree javaSources = project.tasks.compileJava.source
    project.tasks.compileJava.setSource(javaSources.plus(groovySources))
    project.tasks.compileJava.ext.doFirst = newCompiler(project.tasks.compileJava, [groovySources])

    // Set up the compileTestJava task
    final FileTree javaTestSources = project.tasks.compileTestJava.source
    project.tasks.compileTestJava.setSource(javaTestSources.plus(groovyTestSources))
    project.tasks.compileTestJava.ext.doFirst = newCompiler(project.tasks.compileTestJava, [groovyTestSources])
  }
}