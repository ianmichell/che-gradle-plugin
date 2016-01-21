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
package pro.javax.che.plugin.gradle.core.connection;

import pro.javax.che.plugin.gradle.core.connection.internal.DefaultProjectConnectionFactory;

import com.google.inject.ImplementedBy;

import org.gradle.tooling.ProjectConnection;

/**
 * Returns new connection for project folder.
 * All implementations of {@code ProjectConnection} are thread-safe, and may be shared by any number of threads.
 *
 * @author Vlad Zhukovskyi
 * @see ProjectConnection
 */
@ImplementedBy(DefaultProjectConnectionFactory.class)
public interface ProjectConnectionFactory {
    /**
     * Returns a long-lived connection to a Gradle project.
     *
     * @param project
     *         project folder
     * @return project connection
     */
    ProjectConnection newConnection(java.io.File project);
}
