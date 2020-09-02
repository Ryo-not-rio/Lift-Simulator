# Lift-Simulator
This program simulates two different types of lift control systems, and enables the user to obtain statististics on each algorithm.

## Program Structure
The User interface
For this project, I started by not having a user interface. However, I realised testing the system and the presentation of the project would improve significantly with a user interface. There are two menus. The main menu where you can run a single simulation with a specified number of floors, rate of people and the algorithm of choice: the baseline or the improved algorithm. The Test menu is the same as the main menu except you can choose to set each parameter to a random value within a range, and the program will do multiple simulations.
A video demo can be found at https://youtu.be/0MC9T00MjS4.

## Classes and Objects
The main loop: Responsible for the loop to run the program.
The Window class: Responsible for creating the canvas to draw the lifts and floors on.
The Test class: Responsible for initializing the number of lifts, floors, people and the algorithm used.
The Building object: Contains all the Floor objects and Lift objects.
The Floor object: Contains all the people waiting on that particular floor.
The lift object: Contains all the people in that lift.
The liftBrain class: Gives instructions to the lift as determined by the chosen algorithm. 
The Object class: Basic structure for all objects that needs to be rendered to the canvas I.e. Floor, Lift People.
A diagram is attached on the next page.

## The Main Simulation Algorithm
This is the algorithm that controls the generation of the building, lifts and people as well as the movement of the lift. When testing, this algorithm gets called over and over.
The basic structure of the program is as follows. Create a building with floors and lifts. Add people to floors at a determined rate. This rate is the chance a person will be added each frame. It can be in a range of 0.1% to 10%. I have found that at 10%, all single lift system gets overloaded. In the GUI, the range for the rate of people is from 1 to a 100 but this is just to avoid having decimal points in the user interface for accessibility.
The Flow chart of the algorithm is attached on the next page. 
As can be seen from the flow chart, the only thing the Lift object is responsible for is the boarding and disembarking of people from the lift. This is because that is the part that never changes with whatever control algorithm is being used. Whatever algorithm is used, if a lift stops at a floor, everyone that needs to get off will get off and everyone that wants to board will get on the lift until the lift is full. Or in other words, only the LiftBrain class needs to be altered in order to implement different control algorithms.
The LiftBrain class provides methods that tells the lift which direction to move to and the next floor to stop. The lift asks for the next move every time it moves up, down or stops and asks for the next floor to stop at only when it has stopped at a floor.
 
## The test algorithm
The program can be set to either testing or non-testing mode. In testing mode, none of the objects are rendered. The number of people to generate per simulation during testing was chosen so the system would be saturated enough to become consistent. As a measurement of the effectiveness of each algorithm, the average number of floors the lift visited before visiting a person was recorded. This value will be referred to as the number of steps in the following sections.
Below is the algorithm used to test and produce statistics for a particular algorithm quickly.

## Control Algorithms
These algorithms are what each lift calls when determining which direction to move next.
### The Baseline algorithm
The Baseline algorithm is the algorithm that controlled the older lifts where the lifts could only change directions when it has reached the top or bottom floors.
 
### Improved algorithm
The improved baseline algorithm is based on the baseline but instead of changing directions only at the top or bottom, it changes directions if it detects there are no more people waiting in the direction the lift is going and there is no more people in the lift wanting to go in that direction. This algorithm improves the baseline algorithm by eliminating the wasted floors when changing directions. Where the baseline algorithm has to go to the top or the bottom to change directions, this algorithm, when detecting that it doesn’t need to go in one direction any longer, it changes direction.
This algorithm was chosen as it is the algorithm that I have noticed in every lift in the common places such as a department store, a mansion, or schools[1]. Exceptions are in places such as skyscrapers or tall structures such as the empire state building which has multiple lifts for different groups of people. Furthermore, I could not find an algorithm that would increase the efficiency of the lift by a significant amount since with a single lift system, any time the lift changes directions in the middle, the people waiting in the lift’s original direction has to wait longer than had the lift not changed directions.
An improvement that can be made to this algorithm is to measure which floor is accessed the most and let the lift return to that floor every time there is no more request to process which is a strategy known as ‘parking’. This will be redundant on this simulation however as the people will be distributed randomly.
In the real world, engineers often use a scale called the ‘pain index’[2] which is a scale of how long people feel they have been waiting instead of how long they have actually been waiting. This scale accounts for the time a person has been in the lift and the time the person has been waiting on the floor and weighs the waiting time more heavily since people feel time longer when they are waiting for the lift according to some research.
 





