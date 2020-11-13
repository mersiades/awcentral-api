package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class NameJpaService implements NameService {
//
//    private final NameRepository nameRepository;
//
//    public NameJpaService(NameRepository nameRepository) {
//        this.nameRepository = nameRepository;
//    }
//
//    @Override
//    public Set<Name> findAll() {
//        Set<Name> names = new HashSet<>();
//        nameRepository.findAll().forEach(names::add);
//        return names;
//    }
//
//    @Override
//    public Name findById(String id) {
//        Optional<Name> optionalName = nameRepository.findById(id);
//        return optionalName.orElse(null);
//    }
//
//    @Override
//    public Name save(Name name) {
//        return nameRepository.save(name);
//    }
//
//    @Override
//    public void delete(Name name) {
//        nameRepository.delete(name);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        nameRepository.deleteById(id);
//    }
//
//    @Override
//    public Set<Name> findAllByPlaybookType(Playbooks playbookType) {
//        return nameRepository.findAllByPlaybookType(playbookType);
//    }
//
//
//
//
//
//
//
//
//}
