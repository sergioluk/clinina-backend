ALTER TABLE produtos MODIFY COLUMN castrado boolean;
ALTER TABLE produtos DROP COLUMN venda;
ALTER TABLE produtos DROP COLUMN informacao;
ALTER TABLE produtos DROP COLUMN litros;
ALTER TABLE produtos DROP COLUMN categoria;
ALTER TABLE produtos DROP COLUMN imagens;