package lab9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class Lab9 {
    private static final int ACCOUNT_COUNT = 100;
    private static final int TRANSFER_COUNT = 3_000;

    private Lab9() {
    }

    public static void main(String[] args) throws InterruptedException {
        runBankDemo();
        runProducerConsumerDemo();
    }

    private static void runBankDemo() throws InterruptedException {
        Bank bank = new Bank();
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < ACCOUNT_COUNT; i++) {
            accounts.add(new Account(ThreadLocalRandom.current().nextInt(1_000)));
        }
        int before = bank.totalBalance(accounts);
        List<Thread> transfers = new ArrayList<>();
        for (int i = 0; i < TRANSFER_COUNT; i++) {
            Thread transfer = new Thread(() -> {
                Account from = accounts.get(ThreadLocalRandom.current().nextInt(accounts.size()));
                Account to = accounts.get(ThreadLocalRandom.current().nextInt(accounts.size()));
                bank.transfer(from, to, ThreadLocalRandom.current().nextInt(100));
            });
            transfers.add(transfer);
            transfer.start();
        }
        for (Thread transfer : transfers) {
            transfer.join();
        }
        System.out.println("BANK BEFORE " + before);
        System.out.println("BANK AFTER  " + bank.totalBalance(accounts));
    }

    private static void runProducerConsumerDemo() throws InterruptedException {
        BlockingCircularBuffer<String> generated = new BlockingCircularBuffer<>(32);
        BlockingCircularBuffer<String> translated = new BlockingCircularBuffer<>(32);

        for (int i = 1; i <= 5; i++) {
            startDaemon("producer-" + i, () -> {
                long message = 0;
                while (true) {
                    generated.put("Потік № " + Thread.currentThread().getName()
                            + " згенерував повідомлення " + message++);
                }
            });
        }
        for (int i = 1; i <= 2; i++) {
            startDaemon("translator-" + i, () -> {
                while (true) {
                    String message = generated.take();
                    translated.put("Потік № " + Thread.currentThread().getName()
                            + " переклав повідомлення: " + message);
                }
            });
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(translated.take());
        }
    }

    private static void startDaemon(String name, InterruptibleTask task) {
        Thread thread = new Thread(() -> {
            try {
                task.run();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }, name);
        thread.setDaemon(true);
        thread.start();
    }

    @FunctionalInterface
    private interface InterruptibleTask {
        void run() throws InterruptedException;
    }
}
