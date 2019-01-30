package sinya.jp.demo_xml_parse;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import sinya.jp.demo_xml_parse.bean.Person;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int PULL = 0;
    private static final int SAX = 1;


    private Context context;
    private TextView title;
    private TextView content;
    private Person person;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case 0:
                    Person p = (Person) msg.obj;
                    if (msg.arg1 == 0) {
                        title.setText("PULL解析");
                    } else {
                        title.setText("SAX解析");
                    }
                    content.setText(p.name + " - " + p.age + " - " + p.work);
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //注意这里的域名要更换成 10.0.2.2
                HttpURLConnectionGet("http://192.168.1.8:8080/QQJY/test/xml_demo.xml", PULL);
                break;

            case R.id.btn2:
                //注意这里的域名要更换成 10.0.2.2
                HttpURLConnectionGet("http://192.168.1.8:8080/QQJY/test/xml_demo.xml", SAX);
                break;

        }
    }

    private void HttpURLConnectionGet(final String str, final int parseType) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;

                try {
                    URL url = new URL(str);

                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {

                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        StringBuilder builder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }

                        Log.i("Sinya", "服务器返回的数据：" + builder.toString());

                        if (parseType == 0) {
                            parseDataByPull(builder.toString());
                        } else {
                            parseDataBySax(builder.toString());
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 使用Pull解析XML数据
     *
     * @param string
     */
    private void parseDataByPull(String string) {

        //从工厂中获取实例，并创建解析对象
        XmlPullParserFactory factory = null;

        try {
            factory = XmlPullParserFactory.newInstance();

            XmlPullParser pullParser = factory.newPullParser();

            //设置文件 与解析格式
            pullParser.setInput(new StringReader(string));

            int event = pullParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                switch (event) {

                    //开始解析
                    case XmlPullParser.START_DOCUMENT:

                        break;

                    //开始解析某个规定的节点
                    case XmlPullParser.START_TAG:

                        if (pullParser.getName().equals("person")) {
                            person = new Person();
                        }

                        if (pullParser.getName().equals("name")) {
                            person.name = pullParser.nextText();
                        } else if (pullParser.getName().equals("age")) {
                            person.age = Integer.parseInt(pullParser.nextText());
                        } else if (pullParser.getName().equals("work")) {
                            person.work = pullParser.nextText();
                        }
                        break;

                    //解析完规定的某个节点时
                    case XmlPullParser.END_TAG:
                        if (pullParser.getName().equals("person")) {

                            Message message = new Message();
                            message.what = 0;
                            message.arg1 = 0;
                            message.obj = person;
                            handler.sendMessage(message);

                        }
                        break;

                    //全部解析完毕
                    case XmlPullParser.END_DOCUMENT:

                        break;
                }

                event = pullParser.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sax解析
     *
     * @param string
     */
    private void parseDataBySax(String string) {
        try {

            //读取传入的字符串数据 变成流
            InputSource source = new InputSource(new StringReader(string));

            //从工厂获取实例，并创建SAX解析的对象、XML读取的对象
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            //设置自定义的handler
            MyXMLHandler xmlHandler = new MyXMLHandler();
            xmlReader.setContentHandler(xmlHandler);

            //开始解析
            xmlReader.parse(source);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MyXMLHandler extends DefaultHandler {

        private String nodeName;

        @Override
        public void startDocument() throws SAXException {
            //Log.i("Sinya", "开始解析-只执行一次");
            person = new Person();
        }

        @Override
        public void endDocument() throws SAXException {
            //Log.i("Sinya", "结束解析-只执行一次");
            //Log.i("Sinya", person.name + " - " + person.age + " - " + person.work);

            Message message = new Message();
            message.what = 0;
            message.obj = person;
            message.arg1 = 1;
            handler.sendMessage(message);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            //Log.i("Sinya", "解析" + localName + "节点");
            nodeName = localName;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

        }


        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {

            if (nodeName.equals("name")) {
                person.name = new String(ch, start, length);
            }

            if (nodeName.equals("age")) {
                person.age = Integer.parseInt(new String(ch, start, length));

            }

            if (nodeName.equals("work")) {
                person.work = new String(ch, start, length);
            }
        }
    }

//    private void HttpClientGet(final String url) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    HttpClient httpClient = new DefaultHttpClient();
//                    HttpGet httpGet = new HttpGet(url);
//
//                    HttpResponse response = httpClient.execute(httpGet);
//                    int responseCode = response.getStatusLine().getStatusCode();
//
//                    if (responseCode == 200) {
//                        HttpEntity entity = response.getEntity();
//                        String result = EntityUtils.toString(entity, "UTF-8");
//
//                        Message message = new Message();
//                        message.what = 1;
//                        message.obj = result;
//                        handler.sendMessage(message);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//    }

}
