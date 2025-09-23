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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AAD"
include(":app")
include(":features:settings:impl")
include(":features:settings:api")
include(":sdk:storage")
include(":features:news:list:impl")
include(":features:news:list:api")
include(":features:news:worker")
include(":sdk:ui")
include(":content")
