package id.co.dapenbi.api.service;

import id.co.dapenbi.api.entity.Employee;
import id.co.dapenbi.api.model.CalculationConfig;
import id.co.dapenbi.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CalculationService {

    private static final int FEMALE = 0;
    private static final int MALE = 1;
    private static final int MARRIAGE = 1;
    private static final int NOT_MARRIAGE = 2;
    @Autowired
    private EmployeeRepository employeeRepository;
    private List<CalculationConfig> configs = null;

    private List<CalculationConfig> get() {
        if (configs == null) {
            configs = new ArrayList<>();
            // TODO: 21/05/18 perlu tambahkan data
            configs.add(new CalculationConfig("4601", "4700", 0.415629, 11.388539, 10.63747));
        }
        return configs;
    }

    public double countMP(String employeeId, double mpsPercent, double mpsRupiah) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) return 0;

        double mpTemp = employee.getSalary() * getFpr(employee) * getMasaKerja(employee) * 2.5 / 100;
        if (mpsPercent == 0 && mpsRupiah == 0) return mpTemp;
        if (mpsPercent > 0 && mpsRupiah == 0) {
            return mpTemp * (100 - mpsPercent) / 100;
        }
        if (mpsPercent == 0 && mpsRupiah > 0) {
            return (mpTemp - mpsRupiah) * getNs(employee) * 12;
        }
        double mps = mpsPercent / 100 * getNs(employee) * 12 * mpTemp;
        // TODO: 21/05/18 bagaimana menghitung mp jika ada mps percent dan rupiah
        return 0;
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Period getPeriod(Date a, Date b) {
        LocalDate x = toLocalDate(a);
        LocalDate y = toLocalDate(b);
        return Period.between(x, y);
    }

    private int parse(String s) {
        int y = Integer.parseInt(s.substring(0, 2));
        int m = Integer.parseInt(s.substring(2, 4));
        return m + y * 12;
    }

    private CalculationConfig getCC(Employee employee) {
        Period c = getPeriod(employee.getBirthDate(), employee.getFinishDate());
        int usiaPensiun = c.getMonths() + c.getYears() * 12;
        List<CalculationConfig> conf = get();
        for (CalculationConfig cc : conf) {
            int start = parse(cc.getStartAge());
            int finish = parse(cc.getFinishAge());
            if (usiaPensiun >= start && usiaPensiun <= finish)
                return cc;
        }
        return null;
    }

    private double getFpr(Employee employee) {
        CalculationConfig cc = getCC(employee);
        return cc == null ? 0 : cc.getFpr();
    }

    private double getNs(Employee employee) {
        CalculationConfig cc = getCC(employee);
        if (cc == null) return 0;
        if (employee.getSex() == FEMALE) return 0;
        if (employee.getSex() == MALE && employee.getMarital() == MARRIAGE)
            return cc.getNsMM();
        if (employee.getSex() == MALE && employee.getMarital() == NOT_MARRIAGE)
            return cc.getNsMN();
        return 0;
    }

    private int getMasaKerja(Employee employee) {
        Period c = getPeriod(employee.getStartDate(), employee.getFinishDate());
        int masaKerja = c.getYears() + c.getMonths() > 0 ? 1 : 0;
        return masaKerja > 32 ? 32 : masaKerja;
    }
}
