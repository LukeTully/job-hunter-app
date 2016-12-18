package lukedev.hunter;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Luke on 7-2-2016.
 */
public class XMLFileParser {

	String filePath;
	Context context;

	public XMLFileParser(String filePath, Context context) {
		this.filePath = filePath;
		this.context = context;
	}

	public String getXmlString() {
		String xml = null;
		AssetManager assetManager = context.getAssets();
		try {
			InputStream is = assetManager.open(filePath);
			int length = is.available();
			byte[] data = new byte[length];
			is.read(data);
			xml = new String(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return xml;
	}

}
