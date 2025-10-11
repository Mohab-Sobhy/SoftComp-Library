package com.softcomp.examples.jobscheduling;

import java.util.List;

public class JobSchedulingApp {
    public static void main(String[] args) {

        // 1- Create some sample jobs

        List<Job> jobs = List.of(
                new Job(1, 4, 10, 1),
                new Job(2, 6, 12, 2),
                new Job(3, 3, 8, 1)
        );

        // 2- Define the fitness function here

        // 3- Configure the Genetic Algorithm (GA)

        // 4- Run the Genetic Algorithm

        // 5- Print the best solution found

    }
}
