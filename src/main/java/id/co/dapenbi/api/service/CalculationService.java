package id.co.dapenbi.api.service;

import id.co.dapenbi.api.entity.Employee;
import id.co.dapenbi.api.model.CalculationConfig;
import id.co.dapenbi.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

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
            configs.add(new CalculationConfig("4601", "4700", 0.415629, 11.388539, 10.63747));
            configs.add(new CalculationConfig("4701", "4800", 0.453255, 11.345025, 10.536433));
            configs.add(new CalculationConfig("4801", "4900", 0.494465, 11.298645, 10.445125));
            configs.add(new CalculationConfig("4901", "5000", 0.539632, 11.249236, 10.349736));
            configs.add(new CalculationConfig("5001", "5100", 0.589173, 11.196619, 10.250127));
            configs.add(new CalculationConfig("5101", "5200", 0.643547, 11.140588, 10.146076));
            configs.add(new CalculationConfig("5201", "5300", 0.703267, 11.080909, 10.037297));
            configs.add(new CalculationConfig("5301", "5400", 0.768899, 11.017322, 9.923435));
            configs.add(new CalculationConfig("5401", "5500", 0.841076, 10.949532, 9.804068));
            configs.add(new CalculationConfig("5501", "5504", 0.920503, 10.877217, 9.678717));
            configs.add(new CalculationConfig("5505", "5506", 0.949411, 10.851628, 9.634945));
            configs.add(new CalculationConfig("5507", "5600", 0.963865, 10.838833, 9.613059));
            configs.add(new CalculationConfig("5601", "9900", 1, 10.800015, 9.546809));
        }
        return configs;
    }

    public Map countMP(Employee employee, Double mpsPercent, Double mpsRupiah){
        Map result = new HashMap();
        if (employee == null) {
            result.put("mp", 0);
            result.put("mps", 0);
            return result;
        }

        double mp = employee.getSalary() * getFpr(employee) * getMasaKerja(employee) * 2.5 / 100;
        double mps = 0;
        if (mpsPercent == null && mpsRupiah == null) {
            result.put("mp", mp);
            result.put("mps", mps);
            return result;
        }
        if (mpsPercent != null){
            mps = mpsPercent / 100 * getNs(employee) * 12 * mp;
            mp = mp * (100 - mpsPercent) / 100;
        }

        if (mpsRupiah != null) {
            mps = (mp - mpsRupiah) * getNs(employee) * 12;
            mp = mpsRupiah;
        }
        result.put("mp", Math.round(mp));
        result.put("mps", Math.round(mps));
        return result;
    }

    public Map countMP(String employeeId, Double mpsPercent, Double mpsRupiah) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return countMP(employee, mpsPercent, mpsRupiah);
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
        int usiaPensiun = (c.getMonths() + 1) + c.getYears() * 12;
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
        if (employee.getSex() == MALE && employee.getMarital() == MARRIAGE)
            return cc.getNsMM();
        if (employee.getSex() == MALE && employee.getMarital() == NOT_MARRIAGE)
            return cc.getNsMN();
        if (employee.getSex() == FEMALE)
            return cc.getNsMN();
        return 0;
    }

    private int getMasaKerja(Employee employee) {
        Period c = getPeriod(employee.getStartDate(), employee.getFinishDate());
        int masaKerja = c.getYears() + ((c.getMonths() + 1) > 0 ? 1 : 0);
        return masaKerja > 32 ? 32 : masaKerja;
    }
}
