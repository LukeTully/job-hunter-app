
package lukedev.hunter;

import lukedev.hunter.models.Weather;
import rx.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
	@GET("weather?")
	Observable<Weather> getWeather(@Query("q") String city, @Query("APPID") String appID);

}