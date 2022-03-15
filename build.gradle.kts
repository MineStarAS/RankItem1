plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
}

group = "kr.kro.minestar"
version = "1.0.0"
val plugins = File("C:\\Users\\MineStar\\Desktop\\MC Server folder\\MCserver 1.18.1 - MineFarmWorld\\plugins")

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots"
    }
    maven("https://repo.projecttl.net/repository/maven-public/")
}
