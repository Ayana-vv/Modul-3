package iteratorExample;

import org.instancio.Instancio;

import java.util.List;

public class NotIteratorDemo {
    public static void main(String[] args) {
        List<Integer> list = getDataFromMock();
        for (int i = 0; i < list.size(); i++) {
            if (i == 20) break;;
            System.out.println(list.get(i));
        }
    }

    private static List<Integer> getDataFromMock() {
        return Instancio.createList(Integer.class);
    }
}
