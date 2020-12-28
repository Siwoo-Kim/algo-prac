import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class CriticalPath {
    private SymbolTable<Job, Double> distTo = new HashTable<>();
    private final Job start, end;
    private static class Job {
        private static final int seed = 17; 
        private int id; // job id
        private double time;    // the amount of time to finish the job
        private Integer[] dependentJobs;

        public Job(int id, double time, Integer[] dependentJobs) {
            checkNotNull(dependentJobs);
            this.id = id;
            this.time = time;
            this.dependentJobs = dependentJobs;
        }

        public Job antiJob() {
            return new Job(-(seed+id), time, dependentJobs);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Job job = (Job) o;
            return id == job.id &&
                    Double.compare(job.time, time) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(-(id + 17), time);
        }

        @Override
        public String toString() {
            return String.format("%d (%.2f)", id, time);
        }
    }
    
    public CriticalPath(Job[] jobs) {
        checkNotNull(jobs);
        SymbolTable<Integer, Job> table = new HashTable<>();
        for (Job job: jobs)
            table.put(job.id, job);
        Digraph<Job> G = new Digraph<>();
        start = new Job(Integer.MIN_VALUE, 0.0, new Integer[0]);
        end = new Job(Integer.MAX_VALUE, 0.0, toArray(table.keys()));
        for (Job job: jobs) {
            G.addEdge(new WeightedEdge<>(start, job, 0.0));
            Job endJob = job.antiJob();
            G.addEdge(new WeightedEdge<>(job, endJob, job.time));
            G.addEdge(new WeightedEdge<>(endJob, end, 0.0));
            for (int depId: job.dependentJobs) {
                Job depJob = table.get(depId);
                G.addEdge(new WeightedEdge<>(endJob, depJob, 0.0));
            }
        }
        if (new DirectedCycle<>(G).hasCycle())
            throw new IllegalArgumentException("failed to scheduling.");
        for (Job job: G.vertices())
            distTo.put(job, Double.NEGATIVE_INFINITY);
        distTo.put(start, 0.0);
        Topological<Job> top = new Topological<>(G, false);
        for (Job job: top.order())
            relax(job, G);
    }

    private void relax(Job job, Digraph<Job> G) {
        for (Edge<Job> e: G.edgeOf(job)) {
            WeightedEdge<Job> we = (WeightedEdge<Job>) e;
            if (distTo.get(e.to()) < distTo.get(job) + we.weight()) {
                distTo.put(e.to(), distTo.get(job) + we.weight());
            }
        }
    }
    
    private Integer[] toArray(Iterable<Integer> iterable) {
        LinkedList<Integer> ll = new LinkedList<>();
        for (int e: iterable)
            ll.addLast(e);
        return ll.toArray(new Integer[0]);
    }
    
    private double cost() {
        return distTo.get(end);
    }

    public static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("jobsPC.txt");
        int N = Integer.parseInt(scanner.nextLine());
        Job[] jobs = new Job[N];
        for (int i=0; i<N; i++) {
            String[] data = scanner.nextLine().split("\\s+");
            double time = Double.parseDouble(data[0]);
            int M = Integer.parseInt(data[1]);
            Integer[] deps = new Integer[M];
            for (int j=0; j<M; j++)
                deps[j] = Integer.parseInt(data[2+j]);
            jobs[i] = new Job(i, time, deps);
        }
        CriticalPath cp = new CriticalPath(jobs);
        System.out.println(cp.cost());
    }
}
