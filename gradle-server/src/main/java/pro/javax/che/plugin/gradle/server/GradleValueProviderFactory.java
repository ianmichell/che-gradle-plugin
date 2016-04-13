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

import pro.javax.che.plugin.gradle.core.GradleProjectManager;

import com.google.inject.Singleton;

import org.eclipse.che.api.project.server.FolderEntry;
import org.eclipse.che.api.project.server.type.ValueProvider;
import org.eclipse.che.api.project.server.type.ValueProviderFactory;
import org.eclipse.che.api.project.server.type.ValueStorageException;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.GradleProject;
import org.gradle.tooling.model.build.BuildEnvironment;
import org.gradle.tooling.model.idea.IdeaProject;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_BUILD_DIR_VAR;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_BUILD_SCRIPT_VAR;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_DESCRIPTION_VAR;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_GRADLE_VERSION_VAR;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_PATH_VAR;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_SOURCE_DIR_VAR;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TASKS_VAR;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TEST_DIR_VAR;
import static pro.javax.che.plugin.gradle.SourceType.SOURCE;
import static pro.javax.che.plugin.gradle.SourceType.TEST_SOURCE;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.getBuildDirectory;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.getBuildScriptPath;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.getGradleVersion;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.getPath;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.getProjectDescription;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.getProjectSourceDirectories;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.getPublicTasks;
import static pro.javax.che.plugin.gradle.core.GradleProjectManager.isGradleProject;

/**
 * Resolve basic project properties, such as Gradle version, distribution type, project tasks, etc.
 *
 * @author Vlad Zhukovskyi
 */
@Singleton
public class GradleValueProviderFactory implements ValueProviderFactory {

    private GradleProjectManager projectManager;

    @Inject
    public GradleValueProviderFactory(GradleProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public ValueProvider newInstance(FolderEntry projectFolder) {
        return new GradleValueProvider(projectFolder);
    }


    protected class GradleValueProvider implements ValueProvider {

        protected FolderEntry projectFolder;

        public GradleValueProvider(FolderEntry projectFolder) {
            this.projectFolder = projectFolder;
        }

        @Override
        public List<String> getValues(String attributeName) throws ValueStorageException {
            final java.io.File ioFolder = projectFolder.getVirtualFile().toIoFile();

            if (!isGradleProject(ioFolder)) {
                return unmodifiableList(emptyList());
            }

            final ProjectConnection projectConnection = projectManager.getProjectConnection(ioFolder);
            final GradleProject projectModel = projectConnection.getModel(GradleProject.class);
            final IdeaProject ideaProjectModel = projectConnection.getModel(IdeaProject.class);
            final BuildEnvironment buildEnvironmentModel = projectConnection.getModel(BuildEnvironment.class);

            try {

                switch (attributeName) {
                    case PROJECT_TASKS_VAR:
                        return getPublicTasks(projectModel);
                    case PROJECT_BUILD_DIR_VAR:
                        return unmodifiableList(singletonList(getBuildDirectory(projectModel)));
                    case PROJECT_PATH_VAR:
                        return unmodifiableList(singletonList(getPath(projectModel)));
                    case PROJECT_BUILD_SCRIPT_VAR:
                        return unmodifiableList(singletonList(getBuildScriptPath(projectModel)));
                    case PROJECT_DESCRIPTION_VAR:
                        return unmodifiableList(singletonList(getProjectDescription(projectModel)));
                    case PROJECT_SOURCE_DIR_VAR:
                        return getProjectSourceDirectories(ideaProjectModel, SOURCE);
                    case PROJECT_TEST_DIR_VAR:
                        return getProjectSourceDirectories(ideaProjectModel, TEST_SOURCE);
                    case PROJECT_GRADLE_VERSION_VAR:
                        return unmodifiableList(singletonList(getGradleVersion(buildEnvironmentModel)));
                    default:
                        return unmodifiableList(emptyList());
                }

            } finally {
                projectConnection.close();
            }
        }

    }
}
