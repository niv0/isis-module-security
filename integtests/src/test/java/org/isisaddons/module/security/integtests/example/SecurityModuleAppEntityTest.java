package org.isisaddons.module.security.integtests.example;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.security.fixture.dom.ExampleSecuredEntities;
import org.isisaddons.module.security.fixture.dom.ExampleSecuredEntity;
import org.isisaddons.module.security.fixture.scripts.SecurityModuleAppTearDown;
import org.isisaddons.module.security.fixture.scripts.exampleEntities.Bar;
import org.isisaddons.module.security.fixture.scripts.exampleEntities.Baz;
import org.isisaddons.module.security.fixture.scripts.exampleEntities.Bip;
import org.isisaddons.module.security.fixture.scripts.exampleEntities.Bop;
import org.isisaddons.module.security.integtests.SecurityModuleAppIntegTest;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SecurityModuleAppEntityTest extends SecurityModuleAppIntegTest {

    ExampleSecuredEntity entity;

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(
                new SecurityModuleAppTearDown(),
                new Bip(),
                new Bar(),
                new Baz(),
                new Bop()
                );
    }

    @Inject
    ExampleSecuredEntities exampleSecuredEntities;

    @Inject
    IsisJdoSupport isisJdoSupport;

    @Before
    public void setUp() throws Exception {
        final List<ExampleSecuredEntity> all = wrap(exampleSecuredEntities).listAll();
        assertThat(all.size(), is(4));

        entity = all.get(0);
    }

    public static class TODO extends SecurityModuleAppEntityTest {

        @Test
        public void TODO() throws Exception {

        }

    }


}