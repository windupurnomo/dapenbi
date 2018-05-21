package id.co.dapenbi.api;

import id.co.dapenbi.api.entity.Employee;
import id.co.dapenbi.api.service.CalculationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

	@Autowired
	private CalculationService calculationService;

	@Test
	public void countMPTest() {
		String a = "14/09/1962";
		String b = "01/02/1984";
		String c = "01/04/2018";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

		Date birth = null;
		Date start = null;
		Date finish = null;
		try {
			birth = format.parse(a);
			start = format.parse(b);
			finish = format.parse(c);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Employee e = new Employee();
		e.setBirthDate(birth);
		e.setStartDate(start);
		e.setFinishDate(finish);
		e.setSex(1);
		e.setSalary(18745000);
		e.setMarital(1);
		e.setName("Windu Purnomo");
		e.setNip("abc");

		Map<String, Long> map = calculationService.countMP(e, 20.0, null);
		assertThat(map.get("mps")).isEqualTo(375997876);
		assertThat(map.get("mp")).isEqualTo(11563295);
	}

}
