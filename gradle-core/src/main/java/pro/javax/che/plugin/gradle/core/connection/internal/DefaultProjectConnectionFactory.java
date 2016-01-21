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
package pro.javax.che.plugin.gradle.core.connection.internal;

import pro.javax.che.plugin.gradle.core.connection.ProjectConnectionFactory;

import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.internal.consumer.ConnectorServices;
import org.gradle.tooling.internal.consumer.DefaultGradleConnector;

import java.io.File;

/**
 * Default implementation for project connection.
 *
 * @author Vlad Zhukovskyi
 */
public class DefaultProjectConnectionFactory implements ProjectConnectionFactory {

    /** {@inheritDoc} */
    @Override
    public ProjectConnection newConnection(File projectDirectory) {
        DefaultGradleConnector connector = ConnectorServices.createConnector();
        connector.setVerboseLogging(false);
        connector.forProjectDirectory(projectDirectory);

        return connector.connect();
    }
}
