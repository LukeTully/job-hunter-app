package lukedev.hunter.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="food")
public class Food {
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPrice() {
		return price;
	}

	public String getCalories() {
		return calories;
	}

	@Element(name= "name")
	String name;

	@Element(name="description")
	String description;

	@Element(name="price")
	String price;

	@Element(name="calories")
	String calories;
}

