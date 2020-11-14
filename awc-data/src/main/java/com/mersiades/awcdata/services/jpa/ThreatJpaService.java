package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class ThreatJpaService implements ThreatService {
//
//    private final ThreatRepository threatRepository;
//
//    public ThreatJpaService(ThreatRepository threatRepository) {
//        this.threatRepository = threatRepository;
//    }
//
//    @Override
//    public Set<Threat> findAll() {
//        Set<Threat> threats = new HashSet<>();
//        threatRepository.findAll().forEach(threats::add);
//        return threats;
//    }
//
//    @Override
//    public Threat findById(String id) {
//        Optional<Threat> optionalThreat = threatRepository.findById(id);
//        return optionalThreat.orElse(null);
//    }
//
//    @Override
//    public Threat save(Threat threat) {
//        return threatRepository.save(threat);
//    }
//
//    @Override
//    public void delete(Threat threat) {
//       threatRepository.delete(threat);
//    }
//
//    @Override
//    public void deleteById(String id) {
//       threatRepository.deleteById(id);
//    }
//}
