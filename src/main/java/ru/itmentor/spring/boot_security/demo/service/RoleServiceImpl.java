package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Set<Role> findAll() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
