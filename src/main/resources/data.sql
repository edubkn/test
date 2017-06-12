INSERT INTO team_entity (id, name) VALUES
  (1, 'Palmeiras'),
  (2, 'Corinthians');

INSERT INTO campaign_entity (id, name, start_date, end_date, team_id) VALUES
  (1, 'Socio-torcedor', SYSDATE, SYSDATE+10, 1),
  (2, 'Avanti', SYSDATE, SYSDATE+11, 1),