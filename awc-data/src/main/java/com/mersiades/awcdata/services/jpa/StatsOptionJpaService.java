package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class StatsOptionJpaService implements StatsOptionService {
//
//    private final StatsOptionRepository statsOptionRepository;
//
//    public StatsOptionJpaService(StatsOptionRepository statsOptionRepository) {
//        this.statsOptionRepository = statsOptionRepository;
//    }
//
//    @Override
//    public Set<StatsOption> findAll() {
//        Set<StatsOption> statsOptions = new HashSet<>();
//        statsOptionRepository.findAll().forEach(statsOptions::add);
//        return statsOptions;
//    }
//
//    @Override
//    public StatsOption findById(String id) {
//        Optional<StatsOption> optionalStatsOption = statsOptionRepository.findById(id);
//        return optionalStatsOption.orElse(null);
//    }
//
//    @Override
//    public StatsOption save(StatsOption statsOption) {
//        return statsOptionRepository.save(statsOption);
//    }
//
//    @Override
//    public void delete(StatsOption statsOption) {
//        statsOptionRepository.delete(statsOption);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        statsOptionRepository.deleteById(id);
//    }
//
//    @Override
//    public Set<StatsOption> findAllByPlaybookType(Playbooks playbookType) {
//        return statsOptionRepository.findAllByPlaybookType(playbookType);
//    }
//}
