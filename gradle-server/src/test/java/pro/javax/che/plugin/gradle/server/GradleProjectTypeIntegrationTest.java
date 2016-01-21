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

import pro.javax.che.plugin.gradle.Constants;
import pro.javax.che.plugin.gradle.core.GradleProjectManager;
import pro.javax.che.plugin.gradle.core.connection.internal.DefaultProjectConnectionFactory;

import com.google.inject.Provider;

import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.core.rest.HttpJsonRequest;
import org.eclipse.che.api.core.rest.HttpJsonRequestFactory;
import org.eclipse.che.api.core.rest.HttpJsonResponse;
import org.eclipse.che.api.core.rest.shared.dto.Link;
import org.eclipse.che.api.project.server.AttributeFilter;
import org.eclipse.che.api.project.server.DefaultProjectManager;
import org.eclipse.che.api.project.server.Project;
import org.eclipse.che.api.project.server.ProjectManager;
import org.eclipse.che.api.project.server.handlers.ProjectHandlerRegistry;
import org.eclipse.che.api.project.server.type.ProjectTypeDef;
import org.eclipse.che.api.project.server.type.ProjectTypeRegistry;
import org.eclipse.che.api.vfs.server.SystemPathsFilter;
import org.eclipse.che.api.vfs.server.VirtualFileSystemRegistry;
import org.eclipse.che.api.vfs.server.VirtualFileSystemUser;
import org.eclipse.che.api.vfs.server.VirtualFileSystemUserContext;
import org.eclipse.che.api.vfs.server.impl.memory.MemoryFileSystemProvider;
import org.eclipse.che.api.workspace.shared.dto.ProjectConfigDto;
import org.eclipse.che.api.workspace.shared.dto.UsersWorkspaceDto;
import org.eclipse.che.commons.test.SelfReturningAnswer;
import org.eclipse.che.dto.server.DtoFactory;
import org.eclipse.che.ide.ext.java.server.projecttype.JavaProjectType;
import org.eclipse.che.ide.ext.java.server.projecttype.JavaPropertiesValueProviderFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Integration test for Gradle Project type.
 *
 * @author Vlad Zhukovskyi
 */
public class GradleProjectTypeIntegrationTest {
    private static final String workspace    = "my_ws";
    private static final String API_ENDPOINT = "http://localhost:8080/che/api";

    private ProjectManager  pm;
    private HttpJsonRequest httpJsonRequest;

    @Mock
    private Provider<AttributeFilter> filterProvider;
    @Mock
    private AttributeFilter           filter;
    @Mock
    private HttpJsonRequestFactory    httpJsonRequestFactory;
    @Mock
    private HttpJsonResponse          httpJsonResponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(filterProvider.get()).thenReturn(filter);

        final String vfsUser = "dev";
        final Set<String> vfsUserGroups = new LinkedHashSet<>(Collections.singletonList("workspace/developer"));

        final EventService eventService = new EventService();

        VirtualFileSystemRegistry vfsRegistry = new VirtualFileSystemRegistry();
        final MemoryFileSystemProvider memoryFileSystemProvider =
                new MemoryFileSystemProvider(workspace, eventService, new VirtualFileSystemUserContext() {
                    @Override
                    public VirtualFileSystemUser getVirtualFileSystemUser() {
                        return new VirtualFileSystemUser(vfsUser, vfsUserGroups);
                    }
                }, vfsRegistry, SystemPathsFilter.ANY);
        vfsRegistry.registerProvider(workspace, memoryFileSystemProvider);

        Set<ProjectTypeDef> types = new HashSet<>();
        types.add(new JavaProjectType(new JavaPropertiesValueProviderFactory()));
        types.add(new GradleProjectType(new GradleValueProviderFactory(new GradleProjectManager(new DefaultProjectConnectionFactory()))));

        ProjectTypeRegistry ptRegistry = new ProjectTypeRegistry(types);
        ProjectHandlerRegistry handlerRegistry = new ProjectHandlerRegistry(new HashSet<>());

        pm = new DefaultProjectManager(vfsRegistry,
                                       eventService,
                                       ptRegistry,
                                       handlerRegistry,
                                       filterProvider,
                                       API_ENDPOINT,
                                       httpJsonRequestFactory);

        httpJsonRequest = mock(HttpJsonRequest.class, new SelfReturningAnswer());
    }

    @Test
    public void testGetProjectType() throws Exception {
        ProjectTypeDef pt = pm.getProjectTypeRegistry().getProjectType(Constants.PROJECT_TYPE_ID);

        //Assert.assertNotNull(pt);
        Assert.assertTrue(pt.getAttributes().size() > 0);
        Assert.assertTrue(pt.isTypeOf("java"));
    }

    @Test
    public void testGradleProject() throws Exception {
        UsersWorkspaceDto usersWorkspaceMock = mock(UsersWorkspaceDto.class);
        when(httpJsonRequestFactory.fromLink(eq(DtoFactory.newDto(Link.class)
                                                          .withMethod("GET")
                                                          .withHref(API_ENDPOINT + "/workspace/" + workspace))))
                .thenReturn(httpJsonRequest);
        when(httpJsonRequestFactory.fromLink(eq(DtoFactory.newDto(Link.class)
                                                          .withMethod("PUT")
                                                          .withHref(API_ENDPOINT + "/workspace/" + workspace + "/project"))))
                .thenReturn(httpJsonRequest);
        when(httpJsonRequest.request()).thenReturn(httpJsonResponse);
        when(httpJsonResponse.asDto(UsersWorkspaceDto.class)).thenReturn(usersWorkspaceMock);
        final ProjectConfigDto projectConfig = DtoFactory.getInstance().createDto(ProjectConfigDto.class)
                                                         .withName("project")
                                                         .withPath("/myProject")
                                                         .withType(Constants.PROJECT_TYPE_ID);
        when(usersWorkspaceMock.getProjects()).thenReturn(Collections.singletonList(projectConfig));

        Project project = pm.createProject(workspace, "myProject",
                                           DtoFactory.getInstance().createDto(ProjectConfigDto.class)
                                                     .withType(Constants.PROJECT_TYPE_ID),
                                           null);

        Assert.assertEquals(project.getConfig().getType(), Constants.PROJECT_TYPE_ID);
    }
}
