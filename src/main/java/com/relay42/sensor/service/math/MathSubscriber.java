package com.relay42.sensor.service.math;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Operators.MonoSubscriber;

abstract class MathSubscriber<T, R> extends MonoSubscriber<T, R> {
    Subscription s;
    boolean done;

    MathSubscriber(CoreSubscriber<? super R> actual) {
        super(actual);
    }

    public void onSubscribe(Subscription s) {
        if (Operators.validate(this.s, s)) {
            this.s = s;
            this.actual.onSubscribe(this);
            s.request(9223372036854775807L);
        }

    }

    public void onNext(T t) {
        if (this.done) {
            Operators.onNextDropped(t, this.actual.currentContext());
        } else {
            try {
                this.updateResult(t);
            } catch (Throwable var3) {
                this.reset();
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, var3, t, this.actual.currentContext()));
            }
        }
    }

    public void onError(Throwable t) {
        if (this.done) {
            Operators.onErrorDropped(t, this.actual.currentContext());
        } else {
            this.done = true;
            this.reset();
            this.actual.onError(t);
        }
    }

    public void onComplete() {
        if (!this.done) {
            this.done = true;
            R r = this.result();
            if (r != null) {
                this.complete(r);
            } else {
                this.actual.onComplete();
            }

        }
    }

    public void cancel() {
        super.cancel();
        this.s.cancel();
    }

    protected abstract void reset();

    protected abstract R result();

    protected abstract void updateResult(T var1);
}