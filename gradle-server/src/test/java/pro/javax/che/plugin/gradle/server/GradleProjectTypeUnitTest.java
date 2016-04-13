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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;

import org.eclipse.che.api.project.server.ProjectApiModule;
import org.eclipse.che.api.project.server.type.ProjectTypeDef;
import org.eclipse.che.api.project.server.type.ProjectTypeRegistry;
import org.eclipse.che.api.vfs.impl.file.FileTreeWatcher;
import org.eclipse.che.ide.ext.java.server.projecttype.JavaProjectType;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_DISPLAY_NAME;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_ID;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_MIXABLE;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_PARENT;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_PERSISTED;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_PRIMARY;

/**
 * Testing Gradle project type registration.
 *
 * @author Vlad Zhukovskyi
 */
public class GradleProjectTypeUnitTest {

    Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

//                install(new ProjectApiModule());

                Multibinder<ProjectTypeDef> projectTypesBinder = Multibinder.newSetBinder(binder(), ProjectTypeDef.class);
                projectTypesBinder.addBinding().to(JavaProjectType.class);
                projectTypesBinder.addBinding().to(GradleProjectType.class);

                bind(ProjectTypeRegistry.class);
            }
        });
    }

    @Test
    public void testShouldVerifyRegisteredProjectType() throws Exception {
        ProjectTypeRegistry registry = injector.getInstance(ProjectTypeRegistry.class);

        ProjectTypeDef projectType = registry.getProjectType(PROJECT_TYPE_ID);

        assertNotNull(projectType);
        assertThat(projectType, new IsInstanceOf(GradleProjectType.class));
        assertEquals(projectType.getId(), PROJECT_TYPE_ID);
        assertEquals(projectType.getDisplayName(), PROJECT_TYPE_DISPLAY_NAME);
        assertEquals(projectType.getParents().size(), 1);
        assertEquals(projectType.getParents().get(0), PROJECT_TYPE_PARENT);
        assertEquals(projectType.isMixable(), PROJECT_TYPE_MIXABLE);
        assertEquals(projectType.isPrimaryable(), PROJECT_TYPE_PRIMARY);
        assertEquals(projectType.isPersisted(), PROJECT_TYPE_PERSISTED);
    }
}
