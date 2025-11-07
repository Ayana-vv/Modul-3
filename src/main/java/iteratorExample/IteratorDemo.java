package iteratorExample;

import org.instancio.Instancio;

import java.util.ArrayList;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {
        MyIterator myIterator = new MyIterator(getDataFromMock());

        while (myIterator.hasNext()) {
            System.out.println(myIterator.next());
        }
    }

    private static List<Integer> getDataFromMock() {
        return Instancio.createList(Integer.class);
    }
}
