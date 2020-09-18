Jenkins Job DSL Gradle project 
==============================

An example [Job DSL](https://github.com/jenkinsci/job-dsl-plugin) project that uses Gradle for building and testing.

* Uses Jenkins Test Harness to test the DSL on a real Jenkins
* Includes a Jenkinsfile for use in a multi-branch pipeline job

Forked from @unguiculus/job-dsl-sample, modification include lots of things taken from @sheehan/job-dsl-gradle-example


Testing Job DSL
---------------

```sh
$ ./gradlew test
```

This starts up Jenkins using Jenkins Test Harness and runs the Job DSL scripts in the `jobs` directory. A very simple
Spock test checks that no exception is thrown. Real world setup might want to use some more advanced tests, but this
gives you a starting point for an initial setup. Additionally, the generated job config files are saved to the directory
`build/xml`.


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
* 

---
![http://www.apache.org/licenses/LICENSE-2.0.html](https://img.shields.io/:license-apache--2.0-blue.svg?style=flat[])
