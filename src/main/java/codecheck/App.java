package codecheck;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class App {
    public static void main(String[] args) {

    	String param = args[0];

        for (int i = 0, l = args.length; i < l; i++) {
        	if (i != 0) {
            	param = param + "+" + args[i];
        	}
        }

        param = param.replace("+", "%2B");
        param = param.replace("\'", "%27");

        try {
            URL urlObj = new URL("http://challenge-server.code-check.io/api/hash?q=" + param);
            HttpURLConnection http = (HttpURLConnection) urlObj.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            // HTTPレスポンスコード
            final int status = http.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // 通信に成功した
                // テキストを取得する
                final InputStream in = http.getInputStream();

                String encoding = http.getContentEncoding();
                if(null == encoding){
                    encoding = "UTF-8";
                }

                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                // 1行ずつテキストを読み込む
                while((line = bufReader.readLine()) != null) {
                    String[] hash = line.split("\"");
                    System.out.println(hash[7]);
                }
                bufReader.close();
                inReader.close();
                in.close();
            }else{
                System.out.println(status);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
