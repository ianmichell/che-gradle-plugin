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
 * Describes Gradle distribution type.
 *
 * @author Vlad Zhukovskyi
 */
public enum DistributionType {
    BUNDLED,
    DEF_WRAPPED,
    WRAPPED,
    LOCAL;

    public boolean isWrapped() {
        return this == DEF_WRAPPED || this == WRAPPED;
    }
}
