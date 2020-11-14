package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class PlaybookJpaService implements PlaybookService {
//
//    private final PlaybookRepository playbookRepository;
//
//    public PlaybookJpaService(PlaybookRepository playbookRepository) {
//        this.playbookRepository = playbookRepository;
//    }
//
//    @Override
//    public Set<Playbook> findAll() {
//        Set<Playbook> playbooks = new HashSet<>();
//        playbookRepository.findAll().forEach(playbooks::add);
//        return playbooks;
//    }
//
//    @Override
//    public Playbook findById(String id) {
//        Optional<Playbook> optionalPlaybook = playbookRepository.findById(id);
//        return optionalPlaybook.orElse(null);
//    }
//
//    @Override
//    public Playbook save(Playbook playbook) {
//        return playbookRepository.save(playbook);
//    }
//
//    @Override
//    public void delete(Playbook playbook) {
//        playbookRepository.delete(playbook);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        playbookRepository.deleteById(id);
//    }
//
//    @Override
//    public Playbook findByPlaybookType(Playbooks playbookType) {
//        return playbookRepository.findByPlaybookType(playbookType);
//    }
//}
