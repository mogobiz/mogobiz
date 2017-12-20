package com.mogobiz.gradle.api

import org.gradle.api.Plugin
import org.gradle.api.Task
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.CompileOptions
import org.gradle.api.tasks.compile.JavaCompile

import org.gradle.api.file.FileTree

import org.gradle.api.artifacts.Configuration

class GroovyEclipseCompilerPlugin implements Plugin<ProjectInternal> {

  @Override
  void apply(ProjectInternal project) {

    project.getPlugins().apply(JavaPlugin.class)

    boolean scala = project.plugins.hasPlugin("scala")

    final Configuration groovy = project.configurations.create('groovyEclipse')
    groovy.dependencies.add(project.getDependencies().create("org.codehaus.groovy:groovy-eclipse-batch:2.1.8-01"))

    final sourceCompatibility = 1.7
    final targetCompatibility = 1.7

    def newCompiler = { CompileOptions options, ArrayList<FileTree> sources ->
      options.fork = true
      options.forkOptions.executable = 'java'
      options.forkOptions.jvmArgs = ['-cp', project.configurations.groovyEclipse.asPath, 'org.eclipse.jdt.internal.compiler.batch.Main']
      options.compilerArgs = ['-source', sourceCompatibility, '-target', targetCompatibility, '-nowarn'] + sources*.files.flatten() as String[]
    }

    // Set up the compileJava task
    final Configuration compile = project.configurations.findByName("compile")
    compile.dependencies.add(project.getDependencies().create("org.codehaus.groovy:groovy-all:2.4.10"))
    def compileGroovyEclipse = project.tasks.create("compileGroovyEclipse", JavaCompile.class)
    FileTree groovySources = project.fileTree(dir: 'src/main/groovy').include('**/*.groovy') as FileTree
    compileGroovyEclipse.setSource(groovySources)
    JavaCompile compileJava = project.tasks.findByName("compileJava") as JavaCompile
    compileGroovyEclipse.setDestinationDir(project.file("build/classes/main"))
    File mainClasspath = project.file('build/classes/main')
    compileGroovyEclipse.setClasspath(project.files([mainClasspath]).plus(compileJava.getClasspath()))
    newCompiler(compileGroovyEclipse.options, [groovySources])
    if(scala){
      compileGroovyEclipse.dependsOn([project.tasks.findByName("compileScala")])
    }
    else {
      compileGroovyEclipse.dependsOn([compileJava])
    }
    Task processResources = project.tasks.findByName("processResources")
    processResources.dependsOn([compileGroovyEclipse])

    // Set up the compileTestJava task
    def compileTestGroovyEclipse = project.tasks.create("compileTestGroovyEclipse", JavaCompile.class)
    FileTree groovyTestSources = project.fileTree(dir: 'src/test/groovy').include('**/*.groovy') as FileTree
    compileTestGroovyEclipse.setSource(groovyTestSources)
    compileTestGroovyEclipse.setDestinationDir(project.file("build/classes/test"))
    JavaCompile compileTestJava = project.tasks.findByName("compileTestJava") as JavaCompile
    File testClasspath = project.file('build/classes/test')
    compileTestGroovyEclipse.setClasspath(project.files([mainClasspath, testClasspath]).plus(compileTestJava.getClasspath()))
    newCompiler(compileTestGroovyEclipse.getOptions(), [groovyTestSources])
    Task processTestResources = project.tasks.findByName("processTestResources")
    processTestResources.dependsOn([compileTestGroovyEclipse])
  }

}