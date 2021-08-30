package com.main.service;

import org.apache.commons.lang3.function.TriFunction;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MyCollectors {
    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

    static class MyCollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        MyCollectorImpl(Supplier<A> supplier,
                        BiConsumer<A, T> accumulator,
                        BinaryOperator<A> combiner,
                        Function<A, R> finisher,
                        Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    public static <T, R1, R2, R3, R> Collector<T, ?, R> tripling(Collector<? super T, ?, R1> downstream1,
                                                                 Collector<? super T, ?, R2> downstream2,
                                                                 Collector<? super T, ?, R3> downstream3,
                                                                 TriFunction<? super R1, ? super R2, ? super R3, R> merger) {
        return teeing0(downstream1, downstream2, downstream3, merger);
    }

    private static <T, A1, A2, A3, R1, R2, R3, R>
    Collector<T, ?, R> teeing0(
            Collector<? super T, A1, R1> downstream1,
            Collector<? super T, A2, R2> downstream2,
            Collector<? super T, A3, R3> downstream3,
            TriFunction<? super R1, ? super R2, ? super R3, R> merger
    ) {
        Supplier<A1> c1Supplier = Objects.requireNonNull(downstream1.supplier(), "downstream1 supplier");
        Supplier<A2> c2Supplier = Objects.requireNonNull(downstream2.supplier(), "downstream2 supplier");
        Supplier<A3> c3Supplier = Objects.requireNonNull(downstream3.supplier(), "downstream2 supplier");

        BiConsumer<A1, ? super T> c1Accumulator =
                Objects.requireNonNull(downstream1.accumulator(), "downstream1 accumulator");
        BiConsumer<A2, ? super T> c2Accumulator =
                Objects.requireNonNull(downstream2.accumulator(), "downstream2 accumulator");
        BiConsumer<A3, ? super T> c3Accumulator =
                Objects.requireNonNull(downstream3.accumulator(), "downstream2 accumulator");

        BinaryOperator<A1> c1Combiner = Objects.requireNonNull(downstream1.combiner(), "downstream1 combiner");
        BinaryOperator<A2> c2Combiner = Objects.requireNonNull(downstream2.combiner(), "downstream2 combiner");
        BinaryOperator<A3> c3Combiner = Objects.requireNonNull(downstream3.combiner(), "downstream2 combiner");

        Function<A1, R1> c1Finisher = Objects.requireNonNull(downstream1.finisher(), "downstream1 finisher");
        Function<A2, R2> c2Finisher = Objects.requireNonNull(downstream2.finisher(), "downstream2 finisher");
        Function<A3, R3> c3Finisher = Objects.requireNonNull(downstream3.finisher(), "downstream2 finisher");

        Set<Collector.Characteristics> characteristics;
        Set<Collector.Characteristics> c1Characteristics = downstream1.characteristics();
        Set<Collector.Characteristics> c2Characteristics = downstream2.characteristics();
        Set<Collector.Characteristics> c3Characteristics = downstream3.characteristics();

        if (CH_ID.containsAll(c1Characteristics) || CH_ID.containsAll(c2Characteristics)|| CH_ID.containsAll(c3Characteristics)) {
            characteristics = Collections.emptySet();
        } else {
            EnumSet<Collector.Characteristics> c = EnumSet.noneOf(Collector.Characteristics.class);
            c.addAll(c1Characteristics);
            c.retainAll(c2Characteristics);
            c.retainAll(c3Characteristics);
            c.remove(Collector.Characteristics.IDENTITY_FINISH);
            characteristics = Collections.unmodifiableSet(c);
        }

        class ThreeBox {
            A1 left = c1Supplier.get();
            A2 middle = c2Supplier.get();
            A3 right = c3Supplier.get();

            void add(T t) {
                c1Accumulator.accept(left, t);
                c2Accumulator.accept(middle, t);
                c3Accumulator.accept(right, t);
            }

            ThreeBox combine(ThreeBox other) {
                left = c1Combiner.apply(left, other.left);
                middle = c2Combiner.apply(middle, other.middle);
                right = c3Combiner.apply(right, other.right);
                return this;
            }

            R get() {
                R1 r1 = c1Finisher.apply(left);
                R2 r2 = c2Finisher.apply(middle);
                R3 r3 = c3Finisher.apply(right);
                return merger.apply(r1, r2, r3);
            }
        }

        return new MyCollectorImpl<>(ThreeBox::new, ThreeBox::add, ThreeBox::combine, ThreeBox::get, characteristics);
    }
}
