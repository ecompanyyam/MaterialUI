package com.yam.ecompany.service.impl;

import com.yam.ecompany.domain.Client;
import com.yam.ecompany.repository.ClientRepository;
import com.yam.ecompany.service.ClientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        log.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getClientName() != null) {
                    existingClient.setClientName(client.getClientName());
                }
                if (client.getLogo() != null) {
                    existingClient.setLogo(client.getLogo());
                }
                if (client.getLogoContentType() != null) {
                    existingClient.setLogoContentType(client.getLogoContentType());
                }
                if (client.getHeight() != null) {
                    existingClient.setHeight(client.getHeight());
                }
                if (client.getWidth() != null) {
                    existingClient.setWidth(client.getWidth());
                }
                if (client.getTaken() != null) {
                    existingClient.setTaken(client.getTaken());
                }
                if (client.getUploaded() != null) {
                    existingClient.setUploaded(client.getUploaded());
                }
                if (client.getDateOfSubmittal() != null) {
                    existingClient.setDateOfSubmittal(client.getDateOfSubmittal());
                }
                if (client.getApprovalStatus() != null) {
                    existingClient.setApprovalStatus(client.getApprovalStatus());
                }
                if (client.getRegistrationNumber() != null) {
                    existingClient.setRegistrationNumber(client.getRegistrationNumber());
                }
                if (client.getDateOfRegistration() != null) {
                    existingClient.setDateOfRegistration(client.getDateOfRegistration());
                }
                if (client.getDateOfExpiry() != null) {
                    existingClient.setDateOfExpiry(client.getDateOfExpiry());
                }
                if (client.getApprovalDocument() != null) {
                    existingClient.setApprovalDocument(client.getApprovalDocument());
                }
                if (client.getApprovalDocumentContentType() != null) {
                    existingClient.setApprovalDocumentContentType(client.getApprovalDocumentContentType());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
