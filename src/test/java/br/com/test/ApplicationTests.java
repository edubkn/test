package br.com.test;

import br.com.test.dto.CampaignDto;
import br.com.test.dto.CustomerDto;
import br.com.test.dto.TeamDto;
import br.com.test.exception.EmailAlreadyRegisteredException;
import br.com.test.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void contextLoads() {
	}

	@Test
	public void insertCampaign_find_delete() {
		String url = "http://localhost:" + port;
		ResponseEntity<CampaignDto> response = restTemplate.getForEntity(url + "/campaign/3", CampaignDto.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        Date now = DateUtil.now();
		CampaignDto campaign = buildCampaign("Campanha 1", 1L, now, DateUtil.plusDays(now, 3));
		ResponseEntity<Void> insertResponse = restTemplate.postForEntity(url + "/campaign",
				campaign, Void.class);
		assertEquals(insertResponse.getStatusCode(), HttpStatus.CREATED);

		ResponseEntity<CampaignDto> findResponse = restTemplate.getForEntity(url + "/campaign/1",
				CampaignDto.class);
		assertEquals(findResponse.getStatusCode(), HttpStatus.OK);

		CampaignDto insertedCampaign = findResponse.getBody();
		assertNotNull(insertedCampaign);

		restTemplate.delete(url + "/campaign/1");
	}

	@Test
	public void insertCampaigns_multiple_compareDates() {
		String url = "http://localhost:" + port;

		Date now = DateUtil.now();

        CampaignDto campaign1 = buildCampaign("Campanha 1", 1L, now, DateUtil.plusDays(now, 4));
		ResponseEntity<Void> insertResponse = restTemplate.postForEntity(url + "/campaign",
				campaign1, Void.class);
		assertEquals(insertResponse.getStatusCode(), HttpStatus.CREATED);

		CampaignDto campaign2 = buildCampaign("Campanha 2", 1L, now, DateUtil.plusDays(now, 3));

		insertResponse = restTemplate.postForEntity(url + "/campaign",
				campaign2, Void.class);
		assertEquals(insertResponse.getStatusCode(), HttpStatus.CREATED);

		CampaignDto campaign3 = buildCampaign("Campanha 3", 1L, now, DateUtil.plusDays(now, 4));

		insertResponse = restTemplate.postForEntity(url + "/campaign",
				campaign3, Void.class);
		assertEquals(insertResponse.getStatusCode(), HttpStatus.CREATED);

		CampaignDto campaign4 = buildCampaign("Campanha 4", 1L, now, DateUtil.plusDays(now, 5));

		insertResponse = restTemplate.postForEntity(url + "/campaign",
				campaign4, Void.class);
		assertEquals(insertResponse.getStatusCode(), HttpStatus.CREATED);

		ResponseEntity<CampaignDto[]> listResponse = restTemplate.getForEntity(url + "/campaign/list",CampaignDto[].class);
		assertEquals(listResponse.getStatusCode(), HttpStatus.OK);

		CampaignDto[] list = listResponse.getBody();
		assertTrue(list.length > 0);

		for (int i = 0; i < list.length - 1; i++) {
			for (int j = i + 1; j < list.length; j++) {
				assertFalse(list[i].getEndDate().compareTo(list[j].getEndDate()) == 0);
			}
		}
	}

	public CampaignDto buildCampaign(String name, Long teamId, Date startDate, Date endDate) {
		return new CampaignDto(null, name, new TeamDto(teamId, null), startDate, endDate);
	}

	@Test
	public void insertCustomer_alreadyRegistered() {
        String url = "http://localhost:" + port + "/customer";

	    CustomerDto customer1 = buildCustomer("Eduardo Bueno", "eduardo.lbueno@outlook.com", Arrays.asList(1, 2));
        ResponseEntity<Long> insertResponse = restTemplate.postForEntity(url, customer1, Long.class);
        assertEquals(insertResponse.getStatusCode(), HttpStatus.CREATED);
        assertEquals(insertResponse.getBody().longValue(), 1L);

        CustomerDto customer2 = buildCustomer("Eduardo Bueno", "eduardo.lbueno@outlook.com", null);
        ResponseEntity<EmailAlreadyRegisteredException> insert2Response = restTemplate.postForEntity(url, customer2, EmailAlreadyRegisteredException.class);
        assertEquals(insert2Response.getStatusCode(), HttpStatus.CONFLICT);
    }

	public CustomerDto buildCustomer(String name, String email, List<Integer> campaignIds) {
        CustomerDto customer = new CustomerDto(null, name, email,
                DateUtil.toDate(LocalDate.of(1990, 7, 29)), new TeamDto(1L, null));
        if (campaignIds != null) {
            customer.setCampaigns(campaignIds.stream()
                    .map(c -> new CampaignDto(c.longValue()))
                    .collect(Collectors.toList()));
        }
		return customer;
	}

}
