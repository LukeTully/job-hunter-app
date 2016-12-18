package lukedev.hunter.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Luke on 6-14-2016.
 */
@Root(name = "feed")
public class JobList {

	@Attribute(name = "lang", required = false)
	String lang;

	@Element(name = "title")
	String title;
	@Element(name = "id")
	String id;
	@ElementList(inline = true, entry = "link")
	List<String> links;
	@Element(name = "updated", required = false, data=true)
	String updatedDate;
	@Element(name = "author")
	Author author;
	@Element(name = "logo")
	String logoSrc;

	@ElementList(name = "entry", inline = true, entry="entry")
	List<Job> jobList;

	public List<Job> getJobList() {
		return jobList;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}


	public Author getAuthor() {
		return author;
	}

	public String getLogoSrc() {
		return logoSrc;
	}


}
