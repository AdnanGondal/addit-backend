--see data

# --- !Ups

INSERT INTO stories(id, title, url) VALUES (1, 'Voters Overwhelmingly Back Community Broadband in Chicago and Denver', 'https://www.vice.com/en/article/xgzxvz/voters-overwhelmingly-back-community-broadband-in-chicago-and-denver');
INSERT INTO stories(id, title, url) VALUES (2, 'eBird: A crowdsourced bird sighting database', 'https://ebird.org/home');
INSERT INTO stories(id, title, url) VALUES (3, 'Karen Gillan teams up with Lena Headey, Michelle Yeoh, Angela Bassett and Carla Gugino in assassin thriller Gunpowder Milkshake', 'https://www.empireonline.com/movies/news/gunpowder-milk-shake-lena-headey-karen-gillan-exclusive/');
INSERT INTO stories(id, title, url) VALUES (4, 'Pfizers coronavirus vaccine is more than 90 percent effective in first analysis, company reports', 'https://www.cnbc.com/2020/11/09/covid-vaccine-pfizer-drug-is-more-than-90percent-effective-in-preventing-infection.html');

INSERT INTO users(id, email, password_encrypted) VALUES (1, 'alex@sigmalabs.xyz', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');
INSERT INTO users(id, email, password_encrypted) VALUES (2, 'rich@sigmalabs.xyz', '$2y$12$PEmxrth.vjPDazPWQcLs6u9GRFLJvneUkcf/vcXn8L.bzaBUKeX4W');
INSERT INTO users(id, email, password_encrypted) VALUES (3, 'yas@sigmalabs.xyz', '$2a$10$​T4ImbDRHK0L/W8o4LfRp8ObdAw.Wtp1kos8pBIG6nlPCUo1ml8jHi');

INSERT INTO votes(direction, story_id, user_id) VALUES ('up', 1, 1);
INSERT INTO votes(direction, story_id, user_id) VALUES ('up', 1, 2);
INSERT INTO votes(direction, story_id, user_id) VALUES ('down', 1, 3);
INSERT INTO votes(direction, story_id, user_id) VALUES ('down', 3, 2);
INSERT INTO votes(direction, story_id, user_id) VALUES ('up', 4, 1);
INSERT INTO votes(direction, story_id, user_id) VALUES ('down', 4, 2);
INSERT INTO votes(direction, story_id, user_id) VALUES ('down', 4, 3);