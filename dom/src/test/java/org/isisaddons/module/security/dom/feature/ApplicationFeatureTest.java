/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.security.dom.feature;

import com.danhaywood.java.testsupport.coverage.PojoTester;
import com.danhaywood.java.testsupport.coverage.PrivateConstructorTester;
import org.isisaddons.module.security.dom.FixtureDatumFactories;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ApplicationFeatureTest {

    public static class GetContents_and_AddToContents extends ApplicationFeatureTest {

        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void givenPackage_whenAddPackageAndClass() throws Exception {
            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newPackage("com.mycompany"));
            final ApplicationFeatureId packageFeatureId = ApplicationFeatureId.newPackage("com.mycompany.flob");
            final ApplicationFeatureId classFeatureId = ApplicationFeatureId.newClass("com.mycompany.Bar");

            applicationFeature.addToContents(packageFeatureId);
            applicationFeature.addToContents(classFeatureId);

            assertThat(applicationFeature.getContents().size(), is(2));
            assertThat(applicationFeature.getContents(), containsInAnyOrder(packageFeatureId, classFeatureId));
        }

        @Test
        public void givenPackage_whenAddMember() throws Exception {

            expectedException.expect(IllegalStateException.class);

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newPackage("com.mycompany"));
            final ApplicationFeatureId memberFeatureId = ApplicationFeatureId.newMember("com.mycompany.Bar", "foo");

            applicationFeature.addToContents(memberFeatureId);
        }

        @Test
        public void givenClass() throws Exception {

            expectedException.expect(IllegalStateException.class);

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newClass("com.mycompany.Bar"));
            final ApplicationFeatureId classFeatureId = ApplicationFeatureId.newClass("com.mycompany.flob.Bar");

            applicationFeature.addToContents(classFeatureId);
        }

        @Test
        public void givenMember() throws Exception {

            expectedException.expect(IllegalStateException.class);

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newMember("com.mycompany.Bar", "foo"));
            final ApplicationFeatureId classFeatureId = ApplicationFeatureId.newClass("com.mycompany.flob.Bar");

            applicationFeature.addToContents(classFeatureId);
        }

    }

    public static class GetMembers_and_AddToMembers extends ApplicationFeatureTest {

        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void givenPackage() throws Exception {

            expectedException.expect(IllegalStateException.class);

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newPackage("com.mycompany"));
            final ApplicationFeatureId memberFeatureId = ApplicationFeatureId.newMember("com.mycompany.Bar", "foo");

            applicationFeature.addToMembers(memberFeatureId, ApplicationMemberType.PROPERTY);
        }

        @Test
        public void givenClass_whenAddMember() throws Exception {

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newClass("com.mycompany.Bar"));
            final ApplicationFeatureId memberFeatureId = ApplicationFeatureId.newMember("com.mycompany.Bar", "foo");
            final ApplicationFeatureId memberFeatureId2 = ApplicationFeatureId.newMember("com.mycompany.Bar", "boz");

            applicationFeature.addToMembers(memberFeatureId, ApplicationMemberType.PROPERTY);
            applicationFeature.addToMembers(memberFeatureId2, ApplicationMemberType.PROPERTY);

            assertThat(applicationFeature.getProperties().size(), is(2));
            assertThat(applicationFeature.getProperties(), containsInAnyOrder(memberFeatureId, memberFeatureId2));
        }

        @Test
        public void givenClass_whenAddPackage() throws Exception {

            expectedException.expect(IllegalStateException.class);

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newClass("com.mycompany.Bar"));
            final ApplicationFeatureId packageFeatureId = ApplicationFeatureId.newPackage("com.mycompany");

            applicationFeature.addToMembers(packageFeatureId, ApplicationMemberType.PROPERTY);
        }

        @Test
        public void givenClass_whenAddClass() throws Exception {

            expectedException.expect(IllegalStateException.class);

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newClass("com.mycompany.Bar"));
            final ApplicationFeatureId classFeatureId = ApplicationFeatureId.newClass("com.mycompany.Bop");

            applicationFeature.addToMembers(classFeatureId, ApplicationMemberType.PROPERTY);
        }

        @Test
        public void givenMember() throws Exception {

            expectedException.expect(IllegalStateException.class);

            final ApplicationFeature applicationFeature = new ApplicationFeature(ApplicationFeatureId.newMember("com.mycompany.Bar", "foo"));
            final ApplicationFeatureId classFeatureId = ApplicationFeatureId.newClass("com.mycompany.flob.Bar");

            applicationFeature.addToMembers(classFeatureId, ApplicationMemberType.PROPERTY);
        }
    }

    public static class BeanProperties extends ApplicationFeatureTest {

        @Test
        public void exercise() throws Exception {
            PojoTester.relaxed()
                    .withFixture(FixtureDatumFactories.featureIds())
                    .withFixture(FixtureDatumFactories.booleans())
                    .exercise(new ApplicationFeature());
        }
    }

    public static class FunctionsTest extends ApplicationFeatureTest {

        @Test
        public void GET_FQN() throws Exception {
            final ApplicationFeature input = new ApplicationFeature(ApplicationFeatureId.newMember("com.mycompany.Foo#bar"));
            assertThat(ApplicationFeature.Functions.GET_FQN.apply(input), is("com.mycompany.Foo#bar"));
        }
        @Test
        public void GET_ID() throws Exception {
            final ApplicationFeatureId featureId = ApplicationFeatureId.newMember("com.mycompany.Foo#bar");
            final ApplicationFeature input = new ApplicationFeature(featureId);
            assertThat(ApplicationFeature.Functions.GET_ID.apply(input), is(featureId));
        }
    }

    public static class PrivateConstructors extends ApplicationFeatureTest {

        @Test
        public void forFunctions() throws Exception {
            new PrivateConstructorTester(ApplicationFeature.Functions.class).exercise();
        }
        @Test
        public void forPredicates() throws Exception {
            new PrivateConstructorTester(ApplicationFeature.Predicates.class).exercise();
        }
    }



}