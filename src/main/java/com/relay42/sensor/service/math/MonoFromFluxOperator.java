package com.relay42.sensor.service.math;

import java.util.Objects;
import javax.annotation.Nullable;
import reactor.core.Scannable;
import reactor.core.Scannable.Attr;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

abstract class MonoFromFluxOperator<I, O> extends Mono<O> implements Scannable {
    protected final Flux<? extends I> source;

    protected MonoFromFluxOperator(Flux<? extends I> source) {
        this.source = (Flux)Objects.requireNonNull(source);
    }

    @Nullable
    public Object scanUnsafe(Attr key) {
        if (key == Attr.PREFETCH) {
            return 2147483647;
        } else {
            return key == Attr.PARENT ? this.source : null;
        }
    }
}