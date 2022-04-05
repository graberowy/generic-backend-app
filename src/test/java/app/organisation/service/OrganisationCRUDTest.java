package app.organisation.service;

import app.organisation.repository.Organisation;
import app.organisation.repository.OrganisationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrganisationCRUDTest {

    private final OrganisationRepository organisationRepository = Mockito.mock(OrganisationRepository.class);
    private final OrganisationCRUD organisationCRUD = new OrganisationCRUD(organisationRepository);

    @Test
    void when_send_request_with_valid_id_then_organisation_with_provided_id_should_be_returned() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some Organisation");
        Mockito.when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));
        //when
        Optional<Organisation> organisationReturned = organisationCRUD.findById(1L);
        //then
        assertEquals("Some Organisation", organisationReturned.get().getName());
        assertEquals(1L, organisationReturned.get().getId());
    }

    @Test
    void when_add_new_organisation_than_organisation_should_be_added_to_repo() {
        //given
        Organisation organisation = new Organisation();
        organisation.setName("Some Organisation");

        Organisation secondOrganisation = new Organisation();
        secondOrganisation.setId(1L);
        secondOrganisation.setName("Some Organisation");

        Mockito.when(organisationRepository.save(organisation)).thenReturn(secondOrganisation);
        //when
        Organisation organisationReturned = organisationCRUD.save(organisation);
        //then
        assertEquals("Some Organisation", organisationReturned.getName());
        assertEquals(1L, organisationReturned.getId());
        Mockito.verify(organisationRepository).save(organisation);
    }

    @Test
    void when_update_existing_organisation_then_data_should_be_updated() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some Organisation");

        Organisation organisationToUpdate = new Organisation();
        organisationToUpdate.setId(1L);
        organisationToUpdate.setName("Organisation UPDATED!");

        Mockito.when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));
        Mockito.when(organisationRepository.save(organisationToUpdate)).thenReturn(organisationToUpdate);
        //when
        Optional<Organisation> organisationUpdated = organisationCRUD.update(organisationToUpdate);
        //then
        assertEquals("Organisation UPDATED!", organisationUpdated.get().getName());
        assertEquals(1L, organisationUpdated.get().getId());
    }

    @Test
    void when_request_find_all_than_all_organisations_should_be_returned() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some Organisation");

        Organisation secondOrganisation = new Organisation();
        secondOrganisation.setId(2L);
        secondOrganisation.setName("Other Organisation");

        List<Organisation> organisationList = new ArrayList<>();
        organisationList.add(organisation);
        organisationList.add(secondOrganisation);

        Mockito.when(organisationRepository.findAll()).thenReturn(organisationList);
        //when
        List<Organisation> organisations = organisationCRUD.findAll();
        //then
        assertFalse(organisations.isEmpty());
        assertEquals(2, organisations.size());
    }

    @Test
    void when_delete_organisation_by_id_then_organisation_should_be_removed() {
        //given
        Organisation organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Some Organisation");

        Mockito.when(organisationRepository.findById(1L)).thenReturn(Optional.of(organisation));
        //when
        organisationCRUD.delete(1L);
        //then
        Mockito.verify(organisationRepository).deleteById(1L);
    }

}