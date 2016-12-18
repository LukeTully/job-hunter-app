package lukedev.hunter.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Luke on 6-14-2016.
 */
@Root(name = "entry")
public class Job {
	@Element(name = "id")
	String id;
	@Element(name = "link",data=false,required=true)
	Link webPageLink;
	@Element(name = "title")
	String title;
	@Element(name = "updated")
	String updatedDate;
	@Element(name = "summary")
	String summary;

	public String getId() {
		return id;
	}

	public String getWebPageLink() {
		return webPageLink.href;
	}

	public String getTitle() {
		return title;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public String getSummary() {
		return summary;
	}
}
@Root(name="link")
class Link {

	@Attribute(name="rel",required=false)
	String rel;
	@Attribute(name="type")
	String documentType;
	@Attribute(name="href")
	String href;

}