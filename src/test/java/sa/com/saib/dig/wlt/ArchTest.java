package sa.com.saib.dig.wlt;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("sa.com.saib.dig.wlt");

        noClasses()
            .that()
            .resideInAnyPackage("sa.com.saib.dig.wlt.service..")
            .or()
            .resideInAnyPackage("sa.com.saib.dig.wlt.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..sa.com.saib.dig.wlt.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
