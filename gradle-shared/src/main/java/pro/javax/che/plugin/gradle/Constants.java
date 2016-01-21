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
package pro.javax.che.plugin.gradle;

/**
 * Describes shared constants between modules.
 *
 * @author Vlad Zhukovskyi
 */
public interface Constants {
    /**
     * Project Type definitions.
     */
    String  PROJECT_TYPE_ID           = "gradle";
    String  PROJECT_TYPE_DISPLAY_NAME = "Gradle Project";
    boolean PROJECT_TYPE_PRIMARY      = true;
    boolean PROJECT_TYPE_MIXABLE      = false;
    boolean PROJECT_TYPE_PERSISTED    = true;
    String  PROJECT_TYPE_PARENT       = "java";

    /**
     * project Type attributes.
     */
    String PROJECT_TASKS_VAR                     = "gradle.project.tasks";
    String PROJECT_TASKS_VAR_DESCRIPTION         = "Project related tasks.";
    String PROJECT_BUILD_DIR_VAR                 = "gradle.project.build.directory";
    String PROJECT_BUILD_DIR_VAR_DESCRIPTION     = "Project build directory.";
    String PROJECT_PATH_VAR                      = "gradle.project.path";
    String PROJECT_PATH_VAR_DESCRIPTION          = "Project path.";
    String PROJECT_BUILD_SCRIPT_VAR              = "gradle.project.build.script";
    String PROJECT_BUILD_SCRIPT_DESCRIPTION      = "Project build script path.";
    String PROJECT_DESCRIPTION_VAR               = "gradle.project.description";
    String PROJECT_DESCRIPTION_VAR_DESCRIPTION   = "Project description.";
    String PROJECT_SOURCE_DIR_VAR                = "gradle.project.source.directories";
    String PROJECT_SOURCE_DIR_VAR_DESCRIPTION    = "Project source directories.";
    String PROJECT_TEST_DIR_VAR                  = "gradle.project.test.directories";
    String PROJECT_TEST_DIR_VAR_DESCRIPTION      = "Project test directories.";
    String PROJECT_GRADLE_VERSION_VAR            = "gradle.version";
    String PROJECT_GRADLE_VERSION_VAR_DECRIPTION = "Used Gradle version in project.";

    /**
     * Default gradle folder name.
     */
    String GRADLE_PATH = "gradle";

    /**
     * Gradle wrapper folder name, "gradle/wrapper".
     */
    String GRADLE_WRAPPER_PATH = GRADLE_PATH + java.io.File.separator + "wrapper";

    /**
     * Project local property file.
     */
    String GRADLE_WRAPPER_PROPERTIES = "gradle-wrapper.properties";
}
