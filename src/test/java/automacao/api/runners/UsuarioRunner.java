package automacao.api.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:Features",
		glue = "steps",
		plugin = {"pretty", "html:target/cucumber-reports/feature.html"},
		monochrome = true,
		snippets = SnippetType.CAMELCASE,
		tags = "not @Wip and not @Quarentine"
		)
public class UsuarioRunner {

}
