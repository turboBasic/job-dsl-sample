// If you want, you can define your seed job in the DSL and create it via the REST API.
// See https://github.com/sheehan/job-dsl-rest-example#rest-api-runner

job('seed') {
    scm {
        github 'turboBasic/job-dsl-sample'
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        gradle 'clean test'
        dsl {
            external 'jobs/**/*Jobs.groovy'
            additionalClasspath 'src/main/groovy'
        }
    }
    publishers {
        archiveJunit 'build/test-results/**/*.xml'
    }
}
