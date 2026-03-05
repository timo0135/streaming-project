package com.services.impl;

import com.dtos.UsersDto;
import com.repositories.UsersRepository;
import com.services.UsersService;
import com.mappers.UsersMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implémentation des opérations métier pour la gestion des user.
 * Cette classe suit le principe de Single Responsibility (SOLID).
 */
@Service("usersService")
@Transactional
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    /**
     * Constructeur avec injection des dépendances
     * L'injection par constructeur est préférée à @Autowired car :
     * - Elle rend les dépendances obligatoires
     * - Elle facilite les tests unitaires
     * - Elle permet l'immutabilité
     */
    public UsersServiceImpl(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    /**
     * {@inheritDoc}
     * Cette méthode est transactionnelle par défaut grâce à @Transactional sur la classe
     */
    @Override
    public UsersDto saveUser(UsersDto usersDto) {
        var user = usersMapper.toEntity(usersDto);
        var savedUser = usersRepository.save(user);
        return usersMapper.toDto(savedUser);
    }

    /**
     * {@inheritDoc}
     * Utilisation de la méthode orElseThrow pour une gestion élégante des cas d'erreur
     */
    @Override
    @Transactional(readOnly = true)
    public UsersDto getUserById(Long userId) {
        var user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Le user avec l'ID %d n'existe pas", userId)));
        return usersMapper.toDto(user);
    }

    /**
     * {@inheritDoc}
     * La méthode deleteById ne lève pas d'exception si l'entité n'existe pas
     */
    @Override
    public boolean deleteUser(Long userId) {
        usersRepository.deleteById(userId);
        return true;
    }

    /**
     * {@inheritDoc}
     * Utilisation de l'API Stream pour une transformation fonctionnelle des données
     */
    @Override
    @Transactional(readOnly = true)
    public List<UsersDto> getAllUser() {
        return usersRepository.findAll().stream()
                .map(usersMapper::toDto)
                .toList();
    }
}
