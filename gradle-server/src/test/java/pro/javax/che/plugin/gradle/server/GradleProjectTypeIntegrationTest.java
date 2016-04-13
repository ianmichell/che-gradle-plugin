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

import com.google.common.io.Files;
import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.core.rest.HttpJsonRequest;
import org.eclipse.che.api.core.rest.HttpJsonRequestFactory;
import org.eclipse.che.api.core.rest.HttpJsonResponse;
import org.eclipse.che.api.project.server.*;
import org.eclipse.che.api.project.server.handlers.ProjectHandlerRegistry;
import org.eclipse.che.api.project.server.importer.ProjectImporter;
import org.eclipse.che.api.project.server.importer.ProjectImporterRegistry;
import org.eclipse.che.api.project.server.type.ProjectTypeDef;
import org.eclipse.che.api.project.server.type.ProjectTypeRegistry;
import org.eclipse.che.api.vfs.VirtualFileFilter;
import org.eclipse.che.api.vfs.impl.file.DefaultFileWatcherNotificationHandler;
import org.eclipse.che.api.vfs.impl.file.FileTreeWatcher;
import org.eclipse.che.api.vfs.impl.file.LocalVirtualFileSystemProvider;
import org.eclipse.che.api.vfs.search.impl.MemoryLuceneSearcherProvider;
import org.eclipse.che.api.workspace.shared.dto.ProjectConfigDto;
import org.eclipse.che.api.workspace.shared.dto.UsersWorkspaceDto;
import org.eclipse.che.api.workspace.shared.dto.WorkspaceConfigDto;
import org.eclipse.che.commons.test.SelfReturningAnswer;
import org.eclipse.che.dto.server.DtoFactory;
import org.eclipse.che.ide.ext.java.server.projecttype.JavaProjectType;
import org.eclipse.che.ide.ext.java.server.projecttype.JavaPropertiesValueProviderFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.javax.che.plugin.gradle.Constants;
import pro.javax.che.plugin.gradle.core.GradleProjectManager;
import pro.javax.che.plugin.gradle.core.connection.internal.DefaultProjectConnectionFactory;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Integration test for Gradle Project type.
 *
 * @author Vlad Zhukovskyi
 */
public class GradleProjectTypeIntegrationTest {
    private static final String workspace = "my_ws";
    private static final String API_ENDPOINT = "http://localhost:8080/che/api";

    private ProjectManager pm;

    private HttpJsonRequest httpJsonRequest;

    @Mock
    private UsersWorkspaceDto usersWorkspaceMock;
    @Mock
    private HttpJsonRequestFactory httpJsonRequestFactory;
    @Mock
    private HttpJsonResponse httpJsonResponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        httpJsonRequest = mock(HttpJsonRequest.class, new SelfReturningAnswer());

        // Mock the endpoints...
        when(httpJsonRequestFactory.fromUrl(eq(API_ENDPOINT + "/workspace/" + workspace))).thenReturn(httpJsonRequest);

        when(httpJsonRequest.useGetMethod()).thenReturn(httpJsonRequest);

        when(httpJsonRequest.request()).thenReturn(httpJsonResponse);

        when(httpJsonResponse.asDto(eq(UsersWorkspaceDto.class))).thenReturn(usersWorkspaceMock);

        // Calls for workspace
        when(usersWorkspaceMock.getId()).thenReturn(workspace);
        when(usersWorkspaceMock.getOwner()).thenReturn("ian");
        when(usersWorkspaceMock.getConfig()).thenReturn(DtoFactory.getInstance().newDto(WorkspaceConfigDto.class));

        // File System
        final File root = Files.createTempDir();
        root.deleteOnExit(); // delete on exit!

        final Set<VirtualFileFilter> filters = new LinkedHashSet<>();
        final MemoryLuceneSearcherProvider searcherProvider = new MemoryLuceneSearcherProvider(filters);
        final LocalVirtualFileSystemProvider fileSystemProvider = new LocalVirtualFileSystemProvider(root, searcherProvider);

        Set<ProjectTypeDef> types = new HashSet<>();
        types.add(new JavaProjectType(new JavaPropertiesValueProviderFactory()));
        types.add(new GradleProjectType(new GradleValueProviderFactory(new GradleProjectManager(new DefaultProjectConnectionFactory()))));

        final EventService eventService = new EventService();
        final ProjectTypeRegistry ptRegistry = new ProjectTypeRegistry(types);
        final ProjectHandlerRegistry handlerRegistry = new ProjectHandlerRegistry(new HashSet<>());
        final WorkspaceHolder workspaceHolder = new WorkspaceHolder(API_ENDPOINT, workspace, httpJsonRequestFactory);

        // Project Registry
        final ProjectRegistry projectRegistry = new ProjectRegistry(workspaceHolder, fileSystemProvider, ptRegistry, handlerRegistry);
        projectRegistry.initProjects();

        // File Tree Watcher
        final FileTreeWatcher fileTreeWatcher = new FileTreeWatcher(root, new HashSet<>(),
                new DefaultFileWatcherNotificationHandler(fileSystemProvider));

        // Import Registry
        final Set<ProjectImporter> importers = new LinkedHashSet<>();
        importers.add(new ZipProjectImporter());
        final ProjectImporterRegistry importerRegistry = new ProjectImporterRegistry(importers);

        pm = new ProjectManager(fileSystemProvider, eventService, ptRegistry, projectRegistry, handlerRegistry, importerRegistry,
                new DefaultFileWatcherNotificationHandler(fileSystemProvider), fileTreeWatcher);

    }

    @Test
    public void testGradleProject() throws Exception {

        when(httpJsonRequestFactory.fromUrl(eq("http://localhost:8080/che/api/workspace/my_ws/project")))
                .thenReturn(httpJsonRequest);
        when(httpJsonRequest.usePostMethod()).thenReturn(httpJsonRequest);
        when(httpJsonRequest.setBody(any(ProjectConfigDto.class))).thenReturn(httpJsonRequest);
        when(httpJsonRequest.request()).thenReturn(httpJsonResponse);
        //when(httpJsonResponse.asDto())

        ProjectConfigDto config = DtoFactory.getInstance().createDto(ProjectConfigDto.class)
                .withType(Constants.PROJECT_TYPE_ID);
        config.setPath("/myProject");
        RegisteredProject project = pm.createProject(config, new HashMap<>());

        Assert.assertEquals(project.getType(), Constants.PROJECT_TYPE_ID);
    }

}
