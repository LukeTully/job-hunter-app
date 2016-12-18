package lukedev.hunter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Luke on 9-1-2016.
 */
public class JobPage {

    String externalSiteLink;
    String articleContent;
    Date datePosted;
    String availability;
    String externalSource;
    String terms;

    public JobPage(String externalSiteLink) {
        this.externalSiteLink = externalSiteLink;
    }

    public JobPage() {
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public void setExternalSiteLink(String externalSiteLink) {
        this.externalSiteLink = externalSiteLink;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(datePosted);
            this.datePosted = date;
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getExternalSource() {
        return externalSource;
    }

    public void setExternalSource(String externalSource) {
        this.externalSource = externalSource;
    }

    public String getExternalSiteLink() {
        return externalSiteLink;
    }


}


