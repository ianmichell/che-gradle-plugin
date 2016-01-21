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

import pro.javax.che.plugin.gradle.SourceType;
import pro.javax.che.plugin.gradle.core.connection.ProjectConnectionFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.project.server.FolderEntry;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.GradleProject;
import org.gradle.tooling.model.GradleTask;
import org.gradle.tooling.model.Launchable;
import org.gradle.tooling.model.Task;
import org.gradle.tooling.model.build.BuildEnvironment;
import org.gradle.tooling.model.idea.IdeaContentRoot;
import org.gradle.tooling.model.idea.IdeaModule;
import org.gradle.tooling.model.idea.IdeaProject;
import org.gradle.tooling.model.idea.IdeaSourceDirectory;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static pro.javax.che.plugin.gradle.Constants.GRADLE_WRAPPER_PATH;
import static pro.javax.che.plugin.gradle.Constants.GRADLE_WRAPPER_PROPERTIES;

/**
 * Gradle manager.
 *
 * @author Vlad Zhukovskyi
 */
@Singleton
public class GradleProjectManager {

    public static final String GRADLEW_PROPERTIES_PATH = GRADLE_WRAPPER_PATH + File.separator + GRADLE_WRAPPER_PROPERTIES;

    private ProjectConnectionFactory connectionFactory;

    @Inject
    public GradleProjectManager(ProjectConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Returns new project connection for specified directory with Gradle sources.
     *
     * @param project
     *         path to project
     * @return instance of {@code ProjectConnection}
     */
    public ProjectConnection getProjectConnection(java.io.File project) {
        return connectionFactory.newConnection(project);
    }

    /**
     * Returns unmodifiable list with public tasks from Gradle project.
     *
     * @param projectModel
     *         gradle project model
     * @return unmodifiable list which contains public tasks
     * @see GradleTask
     * @see Task
     * @see Launchable
     */
    public static List<String> getPublicTasks(GradleProject projectModel) {
        checkNotNull(projectModel);

        List<String> tasks = projectModel.getTasks()
                                         .stream()
                                         .filter(Launchable::isPublic)
                                         .map(Task::getPath)
                                         .collect(Collectors.toList());

        return unmodifiableList(tasks);
    }

    /**
     * Returns build path.
     * Build path is relative according to project.
     *
     * @param projectModel
     *         gradle project model
     * @return build directory path
     */
    public static String getBuildDirectory(GradleProject projectModel) {
        checkNotNull(projectModel);

        final URI absProjectDir = new File(projectModel.getProjectDirectory().getAbsolutePath()).toURI();
        final URI absBuildDir = new File(projectModel.getBuildDirectory().getAbsolutePath()).toURI();

        return absProjectDir.relativize(absBuildDir).getPath();
    }

    /**
     * Returns path in gradle format. i.e. :project_name.
     *
     * @param projectModel
     *         gradle project model
     * @return build directory path
     */
    public static String getPath(GradleProject projectModel) {
        checkNotNull(projectModel);

        return projectModel.getPath();
    }

    /**
     * Returns path to build script, usually '/project_name/build.gradle'.
     *
     * @param projectModel
     *         gradle project model
     * @return build script path
     */
    public static String getBuildScriptPath(GradleProject projectModel) {
        checkNotNull(projectModel);

        final URI absProjectDir = new File(projectModel.getProjectDirectory().getAbsolutePath()).toURI();
        final URI absBuildDir = new File(projectModel.getBuildScript().getSourceFile().getAbsolutePath()).toURI();

        return absProjectDir.relativize(absBuildDir).getPath();
    }

    /**
     * Returns project description.
     *
     * @param projectModel
     *         gradle project model
     * @return project description or empty string
     */
    public static String getProjectDescription(GradleProject projectModel) {
        checkNotNull(projectModel);

        final String description = projectModel.getDescription();

        return nullToEmpty(description);
    }

    /**
     * Returns list of source directoryes by given {@code SourceType}.
     * Directories represented by relative path from project.
     *
     * @param projectModel
     *         gradle project model, should be {@code IdeaProject}
     * @param sourceType
     *         source type to fetch
     * @return unmodifiable list with source direcotories
     */
    public static List<String> getProjectSourceDirectories(IdeaProject projectModel, SourceType sourceType) {
        checkNotNull(projectModel);

        List<String> directories = null;

        for (IdeaModule module : projectModel.getModules()) {
            for (IdeaContentRoot contentRoot : module.getContentRoots()) {
                DomainObjectSet<? extends IdeaSourceDirectory> sourceDirectories = contentRoot.getSourceDirectories();

                switch (sourceType) {
                    case SOURCE:
                        sourceDirectories = contentRoot.getSourceDirectories();
                        break;
                    case TEST_SOURCE:
                        sourceDirectories = contentRoot.getTestDirectories();
                        break;
                    case GENERATED_SOURCE:
                        sourceDirectories = contentRoot.getGeneratedSourceDirectories();
                        break;
                    case GENERATED_TEST_SOURCE:
                        sourceDirectories = contentRoot.getGeneratedTestDirectories();
                        break;
                }

                if (sourceDirectories == null) {
                    continue;
                }

                for (IdeaSourceDirectory directory : sourceDirectories) {
                    if (directories == null) {
                        directories = newArrayList();
                    }

                    final URI absProjectDir = new File(contentRoot.getRootDirectory().getAbsolutePath()).toURI();
                    final URI absBuildDir = new File(directory.getDirectory().getAbsolutePath()).toURI();

                    directories.add(absProjectDir.relativize(absBuildDir).getPath());
                }
            }
        }

        return unmodifiableList(directories != null ? directories : emptyList());
    }

    /**
     * Returns version of Gradle which used for specific project.
     *
     * @param buildEnvironment
     *         build environment
     * @return gradle version
     */
    public static String getGradleVersion(BuildEnvironment buildEnvironment) {
        checkNotNull(buildEnvironment);

        return buildEnvironment.getGradle().getGradleVersion();
    }

    public static java.io.File findWrapperPropertiesFile(FolderEntry projectFolder) {
        java.io.File ioProjectFolder = projectFolder.getVirtualFile().getIoFile();
        java.io.File wrapperPropertiesFile = getGradleWrapperPropertiesFilePath(ioProjectFolder);
        return wrapperPropertiesFile.isFile() ? wrapperPropertiesFile : null;
    }

    public static java.io.File getGradleWrapperPropertiesFilePath(java.io.File projectFolder) {
        return new java.io.File(projectFolder, GRADLEW_PROPERTIES_PATH);
    }
}
