package ru.nsu.martynov.model;

public class TestResult {
    private int passed;
    private int failed;
    private int skipped;

    public TestResult(int passed, int failed, int skipped) {
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public boolean allPassed() {
        return failed == 0;
    }

    public int total() {
        return passed + failed + skipped;
    }

    public double passRate() {
        return total() == 0 ? 0 : (double) passed / total();
    }

    @Override
    public String toString() {
        return "Tests: passed=" + passed + ", failed=" + failed + ", skipped=" + skipped;
    }
}
