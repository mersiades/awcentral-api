package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class MoveJpaService implements MoveService {
//
//    private final MoveRepository moveRepository;
//
//    public MoveJpaService(MoveRepository moveRepository) {
//        this.moveRepository = moveRepository;
//    }
//
//    @Override
//    public Set<Move> findAll() {
//        Set<Move> moves = new HashSet<>();
//        moveRepository.findAll().forEach(moves::add);
//        return moves;
//    }
//
//    @Override
//    public Move findById(String id) {
//        Optional<Move> optionalMove = moveRepository.findById(id);
//        return optionalMove.orElse(null);
//    }
//
//    @Override
//    public Move save(Move move) {
//        return moveRepository.save(move);
//    }
//
//    @Override
//    public void delete(Move move) {
//        moveRepository.delete(move);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        moveRepository.deleteById(id);
//    }
//}
