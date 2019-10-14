use pokedex; 

-- Description----
-- This query is about trying to figure out if the Pokemon's type
-- is related to hp. As we can see the Pokemon who have the type
-- normal seem to have a higher hp. In the top ten pokemon with 
-- the most hp 5 out of 10 are type normal.
select count(att.type) as "Pokemon" , po.hp, att.type
from attack att join pokemon po
where att.name = po.name 
group by po.hp, att.type
order by po.hp  desc;

-- Description 
-- This Query take the average of all the the pokemon's attack,
-- defense, hp and damage and categorizes them into the pokemons 
-- type respectively. This shows that the rock type scores are on 
-- avg the best. And the Gost type are the worse.  

select poke.type, avg((poke.attack + poke.defense + poke.hp + att.damage)) as Average
from pokemon as poke join attack as att
where poke.name = att.name
group by poke.type 
order by Average desc;

-- Description
-- This query is set to find out the all the Pokemon in the database that 
-- have the attack "tackle" wether in their primary attack or secondary
-- attack. As we can see that there are a varity of types that are able 
-- to learn this attack move. In gerneral since hp is related to level 
-- it seems like many low level pokemon know this move. 
select poke.name,poke.type, move.primary_attack as "Primary Attack", move.secondary_attack "Secondary Attack", poke.hp
from pokemon poke join moveset move
where poke.number = move.number 
having move.secondary_attack = "Tackle" or move.primary_attack = "Tackle"
order by poke.name asc;

-- Description
-- This query shows that even if the pokemon is not known 
-- for its attacking abilitys it still has the potential to deal
-- a good amount of damage if there attack is not effective but 
-- super effective against their oppnent.
-- attack name type and damage. 
select poke.type,move.primary_attack as "Primary Attack",att.damage, poke.attack
from pokemon as poke join moveset as move join attack as att
where poke.number = move.number and poke.name = att.name
having poke.attack < att.damage
order by att.damage desc;


-- Description
-- This query is to get a specific pokemon given the certain
-- parameters. In this case, the parameters in choosing a pokemon
-- is that their attack or defense must be between 85 and 90. Since
-- we are using a union in order to have distinct outputs we can 
-- see that their is only one pokemon that fits the criteria. 

select poke.name, poke.defense, poke.attack
from pokemon poke join moveset move
where poke.number = move.number
having poke.defense > 85 and poke.defense < 90 

union

select poke.name, poke.defense, poke.attack
from pokemon poke join moveset move
where poke.number = move.number 
having poke.attack > 85 and poke.attack < 90 ;




















 
 





