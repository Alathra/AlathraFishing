plugins {
    `java-library`

    id("com.github.johnrengelman.shadow") version "8.1.1" // Shades and relocates dependencies, See https://imperceptiblethoughts.com/shadow/introduction/
    id("xyz.jpenilla.run-paper") version "2.1.0" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0" // Automatic plugin.yml generation
}

group = "com.github.NuclearDonut47.AlathraFishing"
version = "0.1.1-SNAPSHOT"
description = ""

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17)) // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
}

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")

    maven("https://maven.enginehub.org/repo/")

    maven("https://maven.citizensnpcs.co/repo")

    maven("https://jitpack.io/") {
        content {
            includeGroup("com.github.milkdrinkers")
        }
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
    annotationProcessor("org.jetbrains:annotations:24.0.1")
    
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.0-SNAPSHOT")

    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.1.0-SNAPSHOT")

    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT") {
        exclude(group = "*", module = "*")
    }
    
    compileOnly(files("libs/RTUBiomeLib-1.5.2.jar"))

    implementation("com.github.milkdrinkers:colorparser:2.0.0") {
        exclude("net.kyori")
    }
    
    
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
        options.compilerArgs.addAll(arrayListOf("-Xlint:all", "-Xlint:-processing", "-Xdiags:verbose"))
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")

        // Shadow classes
        // helper function to relocate a package into our package
        fun reloc(originPkg: String, targetPkg: String) = relocate(originPkg, "${project.group}.${targetPkg}")

        //reloc("dev.jorel.commandapi", "commandapi")

    }

    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the clicktpa.
        // Your clicktpa's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.20.1")

        // IntelliJ IDEA debugger setup: https://docs.papermc.io/paper/dev/debugging#using-a-remote-debugger
        jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005")
        systemProperty("terminal.jline", false)
        systemProperty("terminal.ansi", true)
    }
}

bukkit {
    // Plugin main class (required)
    main = "${project.group}.AlathraFishing"

    // Plugin Information
    name = project.name // This is what will be displayed in the clicktpa list and log messages.
    prefix = "AlathraFishing" // This is what will be displayed in the log instead of the clicktpa name.
    version =
            "${project.version}" // The current version of the clicktpa. This is shown in clicktpa info messages and server logs.
    description =
            "${project.description}" // A short description of your clicktpa and what it does. This will be shown in the clicktpa info commands.
    authors = listOf("NuclearDonut47")
    contributors = listOf("ShermansWorld", "darksaid98")

    // API version (should be set for 1.13+)
    apiVersion = "1.20"

    // Misc properties from clicktpa.yml (optional)
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.POSTWORLD // STARTUP or POSTWORLD
    softDepend = listOf("Citizens", "RTUBiomeLib")

    commands {
        register("test"){
        }
        register("alathrafishing"){
        }
        register("npcuuid"){
        }
    }
}