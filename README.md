Jenkins Job DSL Gradle project 
==============================

An example [Job DSL](https://github.com/jenkinsci/job-dsl-plugin) project that uses Gradle for building and testing.

* Uses Jenkins Test Harness to test the DSL on a real Jenkins
* Includes a Jenkinsfile for use in a multi-branch pipeline job

Forked from @unguiculus/job-dsl-sample, modification include lots of things taken from @sheehan/job-dsl-gradle-example

Check out [this presentation](https://www.youtube.com/watch?v=SSK_JaBacE0) for a walkthrough of this example (starts around 14:00). 


## File structure

    .
    ├── src
    │   ├── jobs                    # DSL script files
    │   ├── main
    │   │   ├── groovy              # support classes
    │   │   └── resources
    │   │       ├── jobdsl.gdsl     # IDE language support for VSCode and IDEA
    │   │       └── pipeline.gdsl   
    │   ├── scripts                 # scripts to use with "readFileFromWorkspace"
    │   └── test
    │       └── groovy              # specs
    └── build.gradle                # build file


## JobDsl Script Examples

* [Example 1](src/jobs/example1Jobs.groovy) - shows basic folder/job creation
* [Example 2](src/jobs/example2Jobs.groovy) - shows how to create a set of jobs for each github branch, each in its own folder
* [Example 3](src/jobs/example3Jobs.groovy) - shows how to use the configure block
* [Example 4](src/jobs/example4Jobs.groovy) - shows a way to reuse job definitions for jobs that differ only with a few properties
* [Example 5](src/jobs/example5Jobs.groovy) - shows how to pull out common components into static methods
* [Example 6](src/jobs/example6Jobs.groovy) - shows how to include script resources from the workspace
* [Example 7](src/jobs/example7Jobs.groovy) - shows how to create jobs using builders
* [Example 8](src/jobs/example8Jobs.groovy) - shows how to use DSL extensions provided by other plugins


## Testing

```sh
$ ./gradlew test
```

This starts up Jenkins using Jenkins Test Harness and runs the Job DSL scripts in the `jobs` directory. A very simple
Spock test checks that no exception is thrown. Real world setup might want to use some more advanced tests, but this
gives you a starting point for an initial setup. Additionally, the generated job config files are saved to the directory
`build/xml`.

[JobScriptsSpec](src/test/groovy/com/dslexample/JobScriptsSpec.groovy) 
will loop through all DSL files and make sure they don't throw any exceptions when processed. All XML output files are written to `build/debug-xml`. 
This can be useful if you want to inspect the generated XML before check-in. Plugins providing auto-generated DSL must be added to the build dependencies.

If you prefer to stub the auto-generated DSL, you can use [JobScriptsSpecAlternative](src/test/groovy/com/dslexample/JobScriptsSpecAlternative.groovy),
though you may miss some syntax errors.


## Seed Job

You can create the example seed job via the Rest API Runner (see below) using the pattern `jobs/seed.groovy`.

Or manually create a job with the same structure:

* Invoke Gradle script
   * Use Gradle Wrapper: `true`
   * Tasks: `clean test`
* Process Job DSLs
   * DSL Scripts: `src/jobs/**/*Jobs.groovy`
   * Additional classpath: `src/main/groovy`
* Publish JUnit test result report
   * Test report XMLs: `build/test-results/**/*.xml`

Note that starting with Job DSL 1.60 the "Additional classpath" setting is not available when
[Job DSL script security](https://github.com/jenkinsci/job-dsl-plugin/wiki/Script-Security) is enabled.


## REST API Runner

Note: the REST API Runner does not work with [Automatically Generated DSL](https://github.com/jenkinsci/job-dsl-plugin/wiki/Automatically-Generated-DSL). 

A gradle task is configured that can be used to create/update jobs via the Jenkins REST API, if desired. Normally
a seed job is used to keep jobs in sync with the DSL, but this runner might be useful if you'd rather process the
DSL outside of the Jenkins environment or if you want to create the seed job from a DSL script.

```./gradlew rest -Dpattern=<pattern> -DbaseUrl=<baseUrl> [-Dusername=<username>] [-Dpassword=<password>]```

* `pattern` - ant-style path pattern of files to include. E.g. `src/jobs/*.groovy`
* `baseUrl` - base URL of Jenkins server
* `username` - Jenkins username, if secured
* `password` - Jenkins password or token, if secured


## Other Resources

* [Job DSL API Viewer](https://jenkinsci.github.io/job-dsl-plugin/) - Full Job DSL syntax reference.


## Gradle Plugins
Plugins that automatically apply the functionality laid out in this example repo.
* [Gradle Job DSL Support Plugin](https://github.com/AOEpeople/gradle-jenkins-job-dsl-plugin) - Plugin for easy management of Jenkins Job DSL scripts with Gradle.
* [Gradle Jenkins Job DSL Plugin](https://github.com/heremaps/gradle-jenkins-jobdsl-plugin) - Plugin to manage Jenkins Job DSL projects in a Gradle project.



---
![http://www.apache.org/licenses/LICENSE-2.0.html](https://img.shields.io/:license-apache--2.0-blue.svg?style=flat[])
