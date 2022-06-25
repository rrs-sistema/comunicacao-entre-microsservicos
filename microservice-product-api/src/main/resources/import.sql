INSERT INTO tb_category(id, description) VALUES(1000, 'Comic Books');
INSERT INTO tb_category(id, description) VALUES(1002, 'Movies');
INSERT INTO tb_category(id, description) VALUES(1003, 'Books');

INSERT INTO tb_supplier(id, name) VALUES(1000, 'Panini Comics');
INSERT INTO tb_supplier(id, name) VALUES(1002, 'Amazon');

INSERT INTO tb_product(id, name, fK_supplier, fK_category, quantity_available, created_at) VALUES(1000, 'Crise nas Infinitas Terras', 1000, 1000, 10, CURRENT_TIMESTAMP);
INSERT INTO tb_product(id, name, fK_supplier, fK_category, quantity_available, created_at) VALUES(1001, 'Senhor do aneis', 1002, 1003, 10, CURRENT_TIMESTAMP);
INSERT INTO tb_product(id, name, fK_supplier, fK_category, quantity_available, created_at) VALUES(1002, 'Interestelar', 1002, 1002, 10, CURRENT_TIMESTAMP);
INSERT INTO tb_product(id, name, fK_supplier, fK_category, quantity_available, created_at) VALUES(1003, 'Harry Potter e a pedra filosofal', 1002, 1003, 10, CURRENT_TIMESTAMP);