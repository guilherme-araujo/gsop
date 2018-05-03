# Graph Simulator of Populations - GSOP

Simulator of population dynamics based on graphs

## Simulation V4

Inputs: 

Replacement rate - rate of population replacement. Percentage of individuals who will die, then same amount of individuals are born

Cycles - Simulation cycles count

Extended Phenotype (Eph) bonus - Bonus given to individuals using an Eph, regardless of type

Eph start ratio - Ratio of individuals who will be born of type "A", all of them with an Eph associated

Eph birth generation chance - Chance of individual of type "A" being born with an Eph associated

Plot density - Number of cycles represented on plot

### Simulation description: 
List of nodes initiated with uuid from graph, then shuffled.

For each node, types "A" and "B" are assigned, according to "Eph start ratio". All individuals of type "A" are given an Eph. Neighbors are added to the GsopNode object as a hash string list.

Time count start

#### Cycles begin

##### Description of cycle

Number of deaths and births calculated

Hashes of nodes (keys of a HashMap) selected for death

For each key, roulette selection of neighbors, based on coeff - will be affected by having or not an Eph

Remove and save Eph of dying individual

Choose neighbor for reproduction and add fitness (count of children) of chosen neighbor

Birth of new node on location of the one who died - replacing it with the type of parent (chosen neighbor) and setting fitness to zero (newborn node had no offspring yet)

Choose neighbor to occupy abandoned Eph, in case it existed - same chances for all neighbors

In case of chosen neighbor not having an associated Eph, attach it to him. Else, the Eph occupied by the node who died will be lost.

#### Cycles end

Time count ends

Number of nodes of each type with and without associated Eph are counted, and detailed string is formed for each node

Simulation result string formed
