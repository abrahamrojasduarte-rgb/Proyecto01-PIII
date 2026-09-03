package Sistema.logic;

import Sistema.data.data;
import Sistema.data.XmlPersister;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class Service {
    private data Data;
    private Service() {
        try {
            Data = XmlPersister.instance().load();
        } catch (Exception e) {
            Data = new data();
        }
    }

    public void stop() {
        try {
            XmlPersister.instance().store(Data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
