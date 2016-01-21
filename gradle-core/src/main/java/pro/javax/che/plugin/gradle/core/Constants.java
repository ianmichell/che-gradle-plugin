package pro.javax.che.plugin.gradle.core;

import java.io.File;

import static pro.javax.che.plugin.gradle.Constants.GRADLE_WRAPPER_PROPERTIES;

/**
 * Constants related to Gradle core API.
 *
 * @author Vlad Zhukovskyi
 */
public interface Constants {

    /**
     * Default gradle folder name.
     */
    String GRADLE_PATH = "gradle";

    /**
     * Gradle wrapper folder name, "gradle/wrapper".
     */
    String GRADLE_WRAPPER_PATH = GRADLE_PATH + java.io.File.separator + "wrapper";

    String GRADLEW_PROPERTIES_PATH = GRADLE_WRAPPER_PATH + File.separator + GRADLE_WRAPPER_PROPERTIES;

    /**
     * Gradle build script path.
     */
    String GRADLE_BUILD_SCRIPT_PATH = "build.gradle";

}
