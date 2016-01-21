/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
