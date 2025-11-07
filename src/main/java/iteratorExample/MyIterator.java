package iteratorExample;

import java.util.Collection;
import java.util.List;

public class MyIterator {
    private List<Integer> data;
    private int index;

    public MyIterator(List<Integer> data) {
        this.data = data;
        this.index = -1;
    }

    public Integer next() {
        index++;
        return data.get(index);
    }

    public boolean hasNext() {
        try {
            data.get(index + 1);
            return true;
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
