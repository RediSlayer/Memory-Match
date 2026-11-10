# 🃏 Memory Match Game in Java

A classic 4x4 grid memory card pairing game built from scratch in **Java (Swing)**, featuring dynamic randomization, asynchronous verification delays, and real-time game tracking.

## 🚀 Features
- **Dynamic Shuffling:** Utilizes `ArrayList` and `Collections.shuffle()` to randomize and distribute symbol pairs across the 4x4 matrix on every initialization and restart.
- **Global Timer & Lambdas:** Tracks elapsed time in real-time using `javax.swing.Timer` powered by concise lambda expressions.
- **State Control & Security:** 
  - Prevents double-clicking the same active card.
  - Locks the board temporarily (`esperandoVerificacion`) during mismatch animations so players cannot cheat or spam clicks.
  - Automatically uncovers/hides unmatched cards after a 1.5-second delay using an independent Swing timer.
- **Interactive GUI:** Fully responsive 4x4 grid built with `JFrame`, custom event handling, and conditional reset triggers upon victory.

## 🛠️ Tech Stack
- **Language:** Java
- **UI & Concurrency:** Java Swing (`JFrame`, `JButton`, `javax.swing.Timer`)
- **Data Structures & Algorithms:** 2D Arrays (`char[][]`), `ArrayList`, Shuffling Algorithms (`Collections.shuffle`)
- **Concepts:** Event-Driven Programming, Object-Oriented Programming (OOP), State Management.

## 🎮 How to Run
1. Clone the repository:
   ```bash
   git clone [https://github.com/RediSlayer/memory-match-java.git](https://github.com/RediSlayer/memory-match-java.git)

2. Compile and run the main class
3.   javac MemoryMatch.java
4.   java MemoryMatch
