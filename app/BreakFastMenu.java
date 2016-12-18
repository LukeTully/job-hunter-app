package lukedev.hunter.models;

import android.net.Uri;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.net.URL;
import java.util.List;


@Root(name = "breakfast_menu")
public class BreakFastMenu {
	public List<Food> getFoodList() {
		return foodList;
	}

	@ElementList(inline = true)
	List<Food> foodList;
}