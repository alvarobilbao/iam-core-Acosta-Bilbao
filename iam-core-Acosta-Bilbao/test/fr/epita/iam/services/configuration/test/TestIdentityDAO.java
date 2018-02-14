package fr.epita.iam.services.configuration.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.services.dao.IdentityDAO;

public class TestIdentityDAO {
	@Test
	void testCreateAndSearch() throws IdentityCreationException, IdentitySearchException {
		// Given
		final Identity id1 = new Identity();
		id1.setEmail("tbr@tbr.com");
		id1.setUid("123");
		id1.setDisplayName("Thomas Broussard");
		System.out.println(id1);
		final IdentityDAO dao = new IdentityDAO();
		
		// When
		dao.create(id1);

		// Then
		final List<Identity> resultList = dao.searchAll(id1);
		assertTrue("Success in search",resultList.contains(id1));
		assertTrue(resultList.get(resultList.indexOf(id1)).toString(),resultList.contains(id1));

//		if (resultList.contains(id1)) {
//			System.out.println("success");
//			System.out.println(resultList.get(resultList.indexOf(id1)));
//		} else {
//			System.out.println("failure");
//		}
				
	}
}
