package ru.sobse.money_transfer_service.repository;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class CounterImpl implements Counter{
    private final AtomicLong counter;

    public CounterImpl() {
        counter = new AtomicLong(0);
    }

    @Override
    public long incrementAndGet() {
        return counter.incrementAndGet();
    }
}
