Darwin Simulator
================

This is a very small Java program that re-creates the classic *Darwin* programming game: a two-dimensional grid world in which several species of “creatures” compete for survival.  Each species is defined by a simple instruction file (an extremely small “assembly language”).  During the simulation the creatures execute one instruction per turn and interact with whatever is in the neighbouring squares – trying to move, turn, infect opponents, etc.  The last species left alive wins.

Project structure
-----------------
• Darwin.java   – entry point and main simulation loop.  
• Species.java  – reads a text file with instructions and keeps the program.  
• Creature.java – runtime state of one creature (position, direction, program counter, etc.).  
• World.java    – a very small generic 2-D grid container.  
