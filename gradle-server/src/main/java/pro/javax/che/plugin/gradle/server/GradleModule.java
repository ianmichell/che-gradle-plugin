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

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import org.eclipse.che.api.project.server.type.ProjectTypeDef;
import org.eclipse.che.inject.DynaModule;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * Describes server side component registrations.
 *
 * @author Vlad Zhukovskiy
 */
@DynaModule
public class GradleModule extends AbstractModule {

    @SuppressWarnings("unused")
    private Constants constants;

    /** {@inheritDoc} */
    @Override
    protected void configure() {
        Multibinder<ProjectTypeDef> projectTypeBinder = newSetBinder(binder(), ProjectTypeDef.class);
        projectTypeBinder.addBinding().to(GradleProjectType.class);
    }
}
