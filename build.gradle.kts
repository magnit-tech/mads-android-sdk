plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

tasks.register<Copy>("installAndroidSdk") {

    val sdkArtifact = file(properties["madsSdk.zip"] ?: "mads.zip")
    val sdkMavenLocal = file("maven/mads")

    from(zipTree(sdkArtifact))
    into(sdkMavenLocal)

    inputs.file(sdkArtifact)
    outputs.dir(sdkMavenLocal)
}
