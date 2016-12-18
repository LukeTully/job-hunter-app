package lukedev.hunter.models;


import com.orm.SugarRecord;

import java.util.UUID;

/**
 * Created by Luke on 6-14-2016.
 */
public class JobRecord extends SugarRecord {
    String title;
    String summary;
    String uid = UUID.randomUUID().toString();
    String uri;
    String searchuid;
    String description;
    Boolean recruiterflag = false;

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getTermsOfEmployment() {
        return termsOfEmployment;
    }

    public void setTermsOfEmployment(String termsOfEmployment) {
        this.termsOfEmployment = termsOfEmployment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getWatched() {
        return watched;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    String availability;
    String termsOfEmployment;
    String startDate;
    Boolean watched;


    public JobRecord() {

    }

    public JobRecord(String title, String summary) {
        this.title = title;
        this.summary = summary;

    }

    public Boolean getRecruiterflag() {
        return recruiterflag;
    }

    public void setRecruiterflag(Boolean recruiterflag) {
        this.recruiterflag = recruiterflag;
    }

    public String getSearchuid() {
        return searchuid;
    }

    public void setSearchuid(String searchuid) {
        this.searchuid = searchuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public UUID getUid() {
        return UUID.fromString(this.uid);
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
