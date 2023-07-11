import java.net.URI

/*
 * Copyright © 2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    kotlin("multiplatform") version "1.8.22" apply false
    kotlin("plugin.serialization") version "1.8.22" apply false
}

allprojects {
    group = "hu.simplexion.z2"
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = URI("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}