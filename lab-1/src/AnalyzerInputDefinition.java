import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Class for lexical analyzer definition with a specific input format.
 * 
 * @author Truba
 *
 */
public class AnalyzerInputDefinition {

	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));
	private String regularDefinitionInput;
	private String[] parsedRegularDefinition;
	private String name;
	private String value;
	private List<RegularDefinition> listOfRegDefinitions;

	public AnalyzerInputDefinition() {
		this.regularDefinitionInput();
		@SuppressWarnings("unused")
		RegDefResolver resolver = new RegDefResolver(listOfRegDefinitions);
		this.lexicalStateDefinition();
		this.lexicalNameDefinition();
		this.lexicalAnalyzerRulesDefinition();
	}

	public void regularDefinitionInput() {
		while (true) {
			try {
				regularDefinitionInput = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (regularDefinitionInput.startsWith("%")) {
				break;
			}
			parsedRegularDefinition = regularDefinitionInput.split(" ");

			name = parsedRegularDefinition[0].substring(1,
					parsedRegularDefinition[0].length() - 1);
			value = parsedRegularDefinition[1];
			RegularDefinition regularDefinition = new RegularDefinition(name,
					value);
			listOfRegDefinitions.add(regularDefinition);
		}
	}

	public void lexicalStateDefinition() {

	}

	public void lexicalNameDefinition() {

	}

	public void lexicalAnalyzerRulesDefinition() {

	}
}
