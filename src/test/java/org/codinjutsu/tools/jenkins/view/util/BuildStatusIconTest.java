/*
 * Copyright (c) 2013 David Boissier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codinjutsu.tools.jenkins.view.util;

import icons.JenkinsControlIcons;
import org.codinjutsu.tools.jenkins.logic.BuildStatusAggregator;
import org.codinjutsu.tools.jenkins.view.BuildStatusRenderer;
import org.codinjutsu.tools.jenkins.view.DefaultBuildStatusEnumRenderer;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;

import static org.junit.Assert.assertEquals;


public class BuildStatusIconTest {

    private final BuildStatusAggregator aggregatorMock = Mockito.mock(BuildStatusAggregator.class);
    private final BuildStatusRenderer buildStatusRenderer = new DefaultBuildStatusEnumRenderer();

    @Test
    public void noBuildsShouldDisplayGreyIcon() {
        Mockito.when(aggregatorMock.hasNoResults()).thenReturn(true);

        BuildStatusIcon statusIcon = (BuildStatusIcon) BuildStatusIcon.createIcon(aggregatorMock, buildStatusRenderer);
        assertIconEquals("grey.svg", statusIcon.icon);
        assertEquals("No builds", statusIcon.toolTipText);
        assertEquals(0, statusIcon.numberToDisplay);
    }

    @Test
    public void brokenBuildsShouldDisplayRedIcon() {
        Mockito.when(aggregatorMock.hasNoResults()).thenReturn(false);
        Mockito.when(aggregatorMock.getBrokenBuilds()).thenReturn(4);
        Mockito.when(aggregatorMock.getUnstableBuilds()).thenReturn(2);

        BuildStatusIcon statusIcon = (BuildStatusIcon) BuildStatusIcon.createIcon(aggregatorMock, buildStatusRenderer);
        assertIconEquals("red.svg", statusIcon.icon);
        assertEquals("4 broken builds", statusIcon.toolTipText);
        assertEquals(4, statusIcon.numberToDisplay);
    }

    @Test
    public void unstableBuildsShouldDisplayYellowIcon() {
        Mockito.when(aggregatorMock.hasNoResults()).thenReturn(false);
        Mockito.when(aggregatorMock.getBrokenBuilds()).thenReturn(0);
        Mockito.when(aggregatorMock.getUnstableBuilds()).thenReturn(2);

        BuildStatusIcon statusIcon = (BuildStatusIcon) BuildStatusIcon.createIcon(aggregatorMock, buildStatusRenderer);
        assertIconEquals("yellow.svg", statusIcon.icon);
        assertEquals("2 unstable builds", statusIcon.toolTipText);
        assertEquals(2, statusIcon.numberToDisplay);
    }

    @Test
    public void noBrokenBuildsShouldDisplayYellowIcon() {
        Mockito.when(aggregatorMock.hasNoResults()).thenReturn(false);
        Mockito.when(aggregatorMock.getBrokenBuilds()).thenReturn(0);
        Mockito.when(aggregatorMock.getUnstableBuilds()).thenReturn(0);

        BuildStatusIcon statusIcon = (BuildStatusIcon) BuildStatusIcon.createIcon(aggregatorMock, buildStatusRenderer);
        assertIconEquals("blue.svg", statusIcon.icon);
        assertEquals("No broken builds", statusIcon.toolTipText);
        assertEquals(0, statusIcon.numberToDisplay);
    }

    private void assertIconEquals(String expectedIconFilename, Icon actualIcon) {
        assertEquals(JenkinsControlIcons.getIcon(expectedIconFilename).toString(), actualIcon.toString());
    }
}
