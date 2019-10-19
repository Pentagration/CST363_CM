-- Database Final Project Queries
-- Marcus Gonzalez, Sergio Quiroz, Colin Reed

-- Description
-- Shows Pokemon and Pokemon information on all Pokemon having Tackle as primary or secondary attack 
SELECT poke.name,poke.type, move.primary_attack as "Primary Attack", move.secondary_attack "Secondary Attack", poke.hp
FROM pokemon poke JOIN moveset move
WHERE poke.number = move.number 
HAVING move.secondary_attack = "Tackle" or move.primary_attack = "Tackle"
ORDER BY poke.name asc;

-- Description
-- Query counts the number of pokemon that have a specific move as their primary or secondary. 
SELECT t1.primary_attack AS attack, t1.count AS primary_attack_count, t2.count AS secondary_attack_count
    FROM
        (SELECT 
            primary_attack, COUNT(*) AS count
        FROM
            pokedex.moveset
        GROUP BY primary_attack
        ORDER BY COUNT(*) DESC) t1
            JOIN
        (SELECT 
            secondary_attack, COUNT(*) AS count
        FROM
            pokedex.moveset
        GROUP BY secondary_attack
        ORDER BY COUNT(*) DESC) t2 
        ON t1.primary_attack = t2.secondary_attack
        WHERE t1.primary_attack = 'Tackle';
        
-- Description
-- Finds Pokemon with attack and defense over set value. In this case 85 and 90
SELECT poke.name, poke.defense, poke.attack
FROM pokemon poke JOIN moveset move
WHERE poke.number = move.number
HAVING poke.defense > 85 AND poke.defense < 90;

-- Description 
-- Query returns information on a specific Pokemon 
SELECT p.name, p.type, p.hp, p.attack, p.defense, m.primary_attack, m.secondary_attack 
FROM pokedex.pokemon p
	JOIN pokedex.moveset m
		ON p.number = m.number
WHERE p.name = "Paras";

-- Description 
-- Query returns all pokemon with specified type and lists their primary and secondary attacks. 
SELECT p.name, m.primary_attack, m.secondary_attack
	FROM pokedex.moveset m
		JOIN pokedex.pokemon p
			ON m.number = p.number
	WHERE
		primary_attack in
			(SELECT name
			FROM pokedex.attack
			WHERE type = "Grass") OR
		secondary_attack IN
			(SELECT name
			FROM pokedex.attack
			WHERE type = "Grass");

CREATE VIEW pokemon_data AS
    SELECT 
        t1.number,
        p.name,
        p.type,
        p.hp,
        p.attack,
        p.defense,
        t1.primary_attack,
        t1.type,
        t1.damage,
        t2.secondary_attack,
        t2.type,
        t2.damage
    FROM
        (SELECT 
            m.number, m.primary_attack, a.type, a.damage
        FROM
            pokedex.moveset m
        JOIN pokedex.attack a ON m.primary_attack = a.name) t1
            JOIN
        (SELECT 
            m.number, m.secondary_attack, a.type, a.damage
        FROM
            pokedex.moveset m
        JOIN pokedex.attack a ON m.secondary_attack = a.name) t2 ON t1.number = t2.number
            JOIN
        pokedex.pokemon p ON t1.number = p.number
    ORDER BY t1.number;