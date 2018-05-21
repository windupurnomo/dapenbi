package id.co.dapenbi.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Employee {
    @Id
    private String nip;
    private String name;
    private int sex;
    private int marital;
    private Date birthDate;
    private Date startDate;
    private Date finishDate;
    private double salary;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String type;
    @JsonInclude
    @Transient
    private String retireAge;
    @JsonInclude
    @Transient
    private String workAge;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getMarital() {
        return marital;
    }

    public void setMarital(int marital) {
        this.marital = marital;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getType() {
        Period c = getPeriod(getBirthDate(), getFinishDate());
        int usiaPensiun = (c.getMonths() + 1) + c.getYears() * 12;
        if (usiaPensiun <= 46 * 12) return "Pensiun Ditunda";
        else if (usiaPensiun >= 46 * 12 + 1 && usiaPensiun <= 55 * 12 + 11) return "Pensiun Dipercepat";
        else return "Pensiun Normal";
    }

    public String getRetireAge() {
        Period c = getPeriod(getBirthDate(), getFinishDate());
        int month = (c.getMonths() + 1);
        int year = c.getYears();
        return String.format("%02d%02d", year, month);
    }

    public String getWorkAge() {
        Period c = getPeriod(getStartDate(), getFinishDate());
        int month = (c.getMonths() + 1);
        int year = c.getYears();
        return String.format("%02d%02d", year, month);
    }

    private Period getPeriod(Date a, Date b) {
        LocalDate x = a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate y = b.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(x, y);
    }
}
