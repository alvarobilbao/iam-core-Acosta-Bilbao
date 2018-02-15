package fr.epita.iam.services.configuration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.epita.iam.datamodels.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentitySearchException;
import fr.epita.iam.exceptions.IdentityUpdateException;
import fr.epita.iam.services.dao.IdentityDAO;

public class TestIdentityDAO {
	@Test
	void testCreateAndSearch() throws IdentityCreationException, IdentitySearchException {
		// Given
		final Identity id1 = new Identity();
		id1.setEmail("testUser@tur.com");
		id1.setUid("2345");
		id1.setDisplayName("Test User");
		System.out.println(id1);
		final IdentityDAO dao = new IdentityDAO();
		
		// When
		dao.create(id1);

		// Then
		final List<Identity> resultList = dao.searchAll(id1);
		assertTrue("Couldn't find the identity"+id1,resultList.contains(id1));

//		if (resultList.contains(id1)) {
//			System.out.println("success");
//			System.out.println(resultList.get(resultList.indexOf(id1)));
//		} else {
//			System.out.println("failure");
//		}
				
	}
	
	@Test
	void testUpdateAndSearchById() throws IdentityCreationException, IdentitySearchException, IdentityUpdateException {
		//Given
		final Identity id1 = new Identity();
		id1.setEmail("testUser@tur.com");
		id1.setUid("2345");
		id1.setDisplayName("Test User");
		final IdentityDAO dao = new IdentityDAO();
		dao.create(id1);
		
		id1.setEmail("updatetest@test.com");
		id1.setUid("2347");
		id1.setDisplayName("Updated User");
		
		
		//when
		dao.update(id1);
		
		//then
		final Identity updatedIdentity = dao.searchById(id1.getId());
		
		assertEquals("same uid", "2347", updatedIdentity.getUid());
		assertEquals("same email", "updatetest@test.com", updatedIdentity.getEmail());
		assertEquals("same uid", "Updated User", updatedIdentity.getDisplayName());
	}
}
