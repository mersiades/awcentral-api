package com.mersiades.awcweb.services.impl;

import com.mersiades.awcdata.repositories.*;
import com.mersiades.awcweb.models.SystemMessage;
import com.mersiades.awcweb.services.CypressTestService;
import com.mersiades.awcweb.services.MockDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CypressTestServiceImpl implements CypressTestService {

    private final MockDataService mockDataService;
    private final UserRepository userRepository;
    private final NpcRepository npcRepository;
    private final CharacterRepository characterRepository;
    private final GameRepository gameRepository;
    private final GameRoleRepository gameRoleRepository;

    /**
     * ExecutorService executorService = Executors.newFixedThreadPool(4); // TODO: proper number of threads
     *
     * Future<Integer> future1 = executorService.submit(() -> callService1()); // TODO: proper type and method
     * Future<String> future2 = executorService.submit(() -> callService2()); // TODO: proper type and method
     *
     * executorService.shutdown();
     * executorService.awaitTermination(5, TimeUnit.MINUTES); // TODO: proper timeout
     *
     * Integer result1 = future1.get(); // TODO: proper type
     * String result2 = future2.get(); // TODO: proper type
     *
     * Explanation:
     *
     * above code creates an ExecutorService with 4 threads, to which you can submit all your tasks (i.e. calls to microservices)
     * then you call ExecutorService.shutdown (no more task submissions allowed) and ExecutorService.awaitTermination (wait till all tasks are finished)
     * finally, you call Future.get on all the futures to get the results
     * note that if a task threw an exception during executing, a call to Future.get will throw an ExecutionException that wraps the exception thrown by the task
     */

    @Profile("cypress")
    @Override
    public SystemMessage resetDB() {

        try {
            userRepository.deleteAll();
            npcRepository.deleteAll();
            characterRepository.deleteAll();
            gameRepository.deleteAll();
            gameRoleRepository.deleteAll();

            mockDataService.loadMockData();

            long characterCount = characterRepository.count();
            if (characterCount == 0) {
                mockDataService.loadMockCharacters();
                mockDataService.loadHx();
                mockDataService.loadThreats(); // characterCount is serving as a proxy for threatCount here
                mockDataService.loadNpcs(); // characterCount is serving as a proxy for npcCount here
            }

            return SystemMessage.builder().id(new ObjectId().toString()).successMessage("Database reset").build();
        } catch (Exception exception){
            return SystemMessage.builder().id(new ObjectId().toString()).errorMessage("Database reset failed: " + exception.getMessage()).build();
        }
    }
}
