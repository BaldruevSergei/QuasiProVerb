package org.example.quasiproverb.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class TxtProverbService {

    public static class ProverbBlock {
        public final String original;
        public final List<String> funny;

        public ProverbBlock(String original, List<String> funny) {
            this.original = original;
            this.funny = funny;
        }
    }

    private final List<ProverbBlock> proverbBlocks;
    private final Random random = new Random();

    public TxtProverbService() {
        List<ProverbBlock> tempList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("proverbs.txt")),
                        StandardCharsets.UTF_8))) {

            List<String> block = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (block.size() == 4) {
                        tempList.add(new ProverbBlock(
                                block.get(0),
                                new ArrayList<>(block.subList(1, 4))
                        ));
                    }
                    block.clear();
                } else {
                    block.add(line.trim());
                }
            }

            if (block.size() == 4) {
                tempList.add(new ProverbBlock(
                        block.get(0),
                        new ArrayList<>(block.subList(1, 4))
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении proverbs.txt", e);
        }

        this.proverbBlocks = List.copyOf(tempList);
    }

    public String getRandomOriginalAndOneFunny() {
        if (proverbBlocks.isEmpty()) return "Пословицы пока не загружены.";
        ProverbBlock block = proverbBlocks.get(random.nextInt(proverbBlocks.size()));
        String funny = block.funny.get(random.nextInt(block.funny.size()));
        return "🧠 " + block.original + "\n🤣 " + funny;
    }


    public String getFunnyByOriginalStart(String input) {
        return findByFirstWordMatch(input)
                .map(block -> {
                    String funny = block.funny.get(random.nextInt(block.funny.size()));
                    return "🧠 " + block.original + "\n🤣 " + funny;
                })
                .orElse("Не нашёл такую пословицу. Напиши /proverb или начни с правильного начала.");
    }

    public Optional<ProverbBlock> findByFirstWordMatch(String input) {
        String normalized = input.trim().toLowerCase();

        return proverbBlocks.stream()
                .filter(b -> b.original.toLowerCase().startsWith(normalized))
                .findFirst();
    }
}
