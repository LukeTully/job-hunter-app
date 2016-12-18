package lukedev.hunter;

/**
 * Created by Luke on 11-25-2016.
 */
public class SearchHolder {
    private static SearchHolder ourInstance = new SearchHolder();
    private String lastSearchQuery;
    private String lastSearchUID;
    public static SearchHolder getInstance() {
        return ourInstance;
    }

    private SearchHolder() {
    }

    public String getLastSearchQuery() {
        return lastSearchQuery;
    }

    public void setLastSearchQuery(String lastSearchQuery) {
        this.lastSearchQuery = lastSearchQuery;
    }

    public String getLastSearchUID() {
        return lastSearchUID;
    }

    public void setLastSearchUID(String lastSearchUID) {
        this.lastSearchUID = lastSearchUID;
    }
}
