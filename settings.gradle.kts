pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenLocal {
            content {
                includeGroup("ru.magnit.mads.mobile")
            }
        }
        mavenCentral {
            content {
                if (settings.providers.gradleProperty("madsForceLocal").orNull == "true") {
                    excludeGroup("ru.magnit.mads.mobile")
                }
            }
        }
        google()
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

include(":app")
