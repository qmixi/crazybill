package pl.allegro.umk.crazybill.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bill {
    private List<BillPosition> positions;

    public Bill(List<BillPosition> positions) {
        this.positions = positions;
    }

    public List<BillPosition> getPositions() {
        return positions;
    }

    public static BillBuilder builder() {
        return new BillBuilder();
    }

    public static class BillBuilder {
        private List<BillPosition> positions = new ArrayList<>();

        public BillPositionBuilder paidFor(String name, double price) {
            return new BillPositionBuilder(this, name, price);
        }

        public Bill build() {
            return new Bill(positions);
        }

        public class BillPositionBuilder {
            private final BillBuilder billBuilder;
            private final String name;
            private final double price;

            public BillPositionBuilder(BillBuilder billBuilder, String name, double price) {
                this.billBuilder = billBuilder;
                this.name = name;
                this.price = price;
            }

            public BillBuilder by(String... persons) {
                return billBuilder.withPosition(new BillPosition(name, price, Arrays.asList(persons)));
            }
        }

        private BillBuilder withPosition(BillPosition billPosition) {
            positions.add(billPosition);
            return this;
        }
    }
}
