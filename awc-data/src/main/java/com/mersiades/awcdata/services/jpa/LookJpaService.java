package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class LookJpaService implements LookService {
//
//    private final LookRepository lookRepository;
//
//    public LookJpaService(LookRepository lookRepository) {
//        this.lookRepository = lookRepository;
//    }
//
//    @Override
//    public Set<Look> findAll() {
//        Set<Look> looks = new HashSet<>();
//        lookRepository.findAll().forEach(looks::add);
//        return looks;
//    }
//
//    @Override
//    public Look findById(String id) {
//        Optional<Look> optionalLook = this.lookRepository.findById(id);
//        return optionalLook.orElse(null);
//    }
//
//    @Override
//    public Look save(Look look) {
//        return lookRepository.save(look);
//    }
//
//    @Override
//    public void delete(Look look) {
//        this.lookRepository.delete(look);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        this.lookRepository.deleteById(id);
//    }
//
//    @Override
//    public Set<Look> findAllByPlaybookType(Playbooks playbookType) {
//        return this.lookRepository.findAllByPlaybookType(playbookType);
//    }
//}
