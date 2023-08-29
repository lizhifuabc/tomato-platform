package example;

import example.Application;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * Tests to verify the modular structure and generate documentation for the modules.
 *
 * @author Oliver Drotbohm
 */
class ModularityTests {

	ApplicationModules modules = ApplicationModules.of(Application.class);

	@Test
	void verifiesModularStructure() {
		modules.verify();
	}

	@Test
	void createModuleDocumentation() {
		new Documenter(modules).writeDocumentation();
	}

}
