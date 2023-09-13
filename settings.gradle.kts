import java.io.File

pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "cinematic", "keep", "lexi", "neat", "kash", "geo", "kase",
    "kronecker", "symphony", "epsilon-api", "krono", "hormone", "identifier",
    "kommerce", "kollections", "koncurrent", "kommander", "cabinet-api", "snitch"
).forEach { includeBuild("../$it") }

rootProject.name = "sentinel"

includeSubs(base = "sentinel-reception-api", path = "../sentinel/reception", "core")
includeSubs(base = "sentinel-registration-api", path = "../sentinel/registration", "core")
includeSubs(base = "sentinel-enterprise-authentication-api", path = "../sentinel/enterprise/authentication", "core")
includeSubs(base = "sentinel-enterprise-profile-api", path = "../sentinel/enterprise/profile", "core")
