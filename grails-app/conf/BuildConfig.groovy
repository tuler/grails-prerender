grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
	inherits("global") {
	}
	log "warn"
	legacyResolve false
	repositories {
		grailsCentral()
		mavenCentral()
	}
	dependencies {
		runtime 'com.github.greengerong:prerender-java:1.0'
	}

	plugins {
		build(":tomcat:$grailsVersion", ":release:2.2.1", ":rest-client-builder:1.0.3") {
			export = false
		}
		compile ':webxml:1.4.1'
	}
}
