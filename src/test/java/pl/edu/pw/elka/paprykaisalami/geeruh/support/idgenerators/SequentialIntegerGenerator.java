package pl.edu.pw.elka.paprykaisalami.geeruh.support.idgenerators;

import java.util.concurrent.atomic.AtomicInteger;

public class SequentialIntegerGenerator implements IdGenerator<Integer> {

    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Integer generateId() {
        return nextId.getAndIncrement();
    }
}
