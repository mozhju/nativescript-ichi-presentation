package cn.ichi.android.presentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mozj on 2018/6/27.
 */

public class MyList<E> {
    private List<E> list = new ArrayList<E>();

    public boolean add(E e) {
        synchronized(list) {
            return list.add(e);
        }
    }

    public E get(int index){
        synchronized(list) {
            if (index > list.size()) {
                return null;
            }
            return list.get(index);
        }
    }

    public void clear() {
        synchronized(list) {
            list.clear();
        }
    }

    public int size() {
        synchronized(list) {
            return list.size();
        }
    }
}
