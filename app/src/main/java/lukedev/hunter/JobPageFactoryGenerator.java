package lukedev.hunter;

import lukedev.hunter.models.JobPage;
import retrofit2.Converter;

/**
 * Created by Luke on 12-18-2016.
 */

public class JobPageFactoryGenerator {
    public static Converter.Factory FACTORY = JobPageAdapter.FACTORY;
    private static final String QUEBECJOBS = "emploiquebec";
    private static final String JOBBANKJOBS = "jobbank";

    // Determine if the default needs to be overriden

    public JobPageFactoryGenerator(String url) {

        if (url.contains(QUEBECJOBS)) {
            FACTORY = QuebecJobPageAdapter.FACTORY;
        } else if (!url.contains(JOBBANKJOBS)){
            FACTORY = ExternalJobPageAdapter.FACTORY;
        }

    }

    public Converter.Factory getFactory(){
        return FACTORY;
    }


}
