package com.movieapp.markodonovski.moviesapp.klasi;

import java.io.Serializable;

/**
 * Created by markodonovski on 2/19/18.
 */

public class Crew implements Serializable{
   public String department;
   public String job;
   public String name;

    public Crew(String department, String job, String name) {
        this.department = department;
        this.job = job;
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }
}
