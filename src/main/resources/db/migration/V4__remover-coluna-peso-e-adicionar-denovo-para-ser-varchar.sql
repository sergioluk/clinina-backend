ALTER TABLE produtos
  DROP COLUMN peso;

alter table produtos add peso varchar(20) not null;