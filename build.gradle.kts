import org.jreleaser.model.Active

plugins {
    kotlin("jvm") version "2.1.10"
    id("org.jreleaser") version "1.16.0"
    `java-library`
    `maven-publish`
    signing
}

group = "dev.vicart"
version = "1.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-codec:commons-codec:1.17.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("kotp")
                description.set("Kotlin library for generating and verifying OTP codes")
                url.set("https://github.com/xolider/kotp")
                developers {
                    developer {
                        id.set("xolider")
                        name.set("Clément Vicart")
                    }
                }
                licenses {
                    license {
                        name.set("Apache-2.0 license")
                        url.set("https://raw.githubusercontent.com/xolider/kotp/refs/heads/main/LICENSE")
                    }
                }
                scm {
                    url.set("https://github.com/xolider/kotp")
                    connection.set("scm:git:git://github.com/xolider/kotp.git")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}

signing {
    useInMemoryPgpKeys(file("privkey.asc").readText(), findProperty("sign.passphrase")?.toString())
    sign(publishing.publications["maven"])
}

jreleaser {
    project {
        description.set("Kotlin library for generating and verifying OTP codes")
        copyright.set("© 2025")
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    sign.set(false)
                    active.set(Active.RELEASE)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository("build/staging-deploy")
                    username.set(findProperty("sonatype.username")?.toString())
                    password.set(findProperty("sonatype.password")?.toString())
                }
            }
        }
    }
    release {
        github {
            token.set(findProperty("github.token")?.toString())
        }
    }
}