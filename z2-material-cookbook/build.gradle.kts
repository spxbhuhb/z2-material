/*
 * Copyright © 2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    kotlin("multiplatform")
}

group = "hu.simplexion.z2"

kotlin {
    js(IR) {
        browser()
        binaries.executable()

        val targetDirectory = file("$buildDir/processedResources/js/main/")

        tasks.register("z2MaterialExtract") {
            doLast {
                targetDirectory.mkdirs()
                compilations["main"].runtimeDependencyFiles.firstOrNull {
                    "z2-material" in it.name
                }?.let {
                    copy {
                        from(zipTree(it))
                        into(targetDirectory)
                    }
                }
            }
        }

        tasks["compileKotlinJs"].dependsOn("z2MaterialExtract")

    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":z2-material"))
            }
        }
    }
}
