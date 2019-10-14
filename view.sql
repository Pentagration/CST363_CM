-- View

-- using pokedex database 
use pokedex;

-- creating a view of pokemon called pokemon_unbias
-- forcing user to look soley at the number and what 
-- they need then they are provided a ID number of 
-- a unknown pokemon
create view pokemon_unbias as
select poke.number, poke.hp, poke.attack, poke.defense, att.damage
from pokemon poke join attack att
where poke.name = att.name;

-- view database to select the pokemon unbiasely solely on numbers
select *
from pokemon_unbias;

-- Insert the pokemon number picked from above to see what 
-- pokemon type and their moveset. 
select poke.name, poke.type, move.primary_attack, move.secondary_attack
from pokemon as poke join moveset as move join attack as att
where poke.number = move.number and poke.name = att.name and 

poke.number = 116  -- insert pokemon number here to view 



