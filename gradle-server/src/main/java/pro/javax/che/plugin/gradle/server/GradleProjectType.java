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
package pro.javax.che.plugin.gradle.server;

import org.eclipse.che.api.project.server.type.ProjectTypeDef;

import javax.inject.Inject;
import javax.inject.Singleton;

import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_DISPLAY_NAME;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_ID;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_MIXABLE;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_PARENT;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_PERSISTED;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_PRIMARY;

/**
 * Registers new project type with Gradle id.
 *
 * @author Vlad Zhukovskyi
 */
@Singleton
public class GradleProjectType extends ProjectTypeDef {

    @Inject
    public GradleProjectType() {
        super(PROJECT_TYPE_ID, PROJECT_TYPE_DISPLAY_NAME, PROJECT_TYPE_PRIMARY, PROJECT_TYPE_MIXABLE, PROJECT_TYPE_PERSISTED);

        addParent(PROJECT_TYPE_PARENT);
    }
}
