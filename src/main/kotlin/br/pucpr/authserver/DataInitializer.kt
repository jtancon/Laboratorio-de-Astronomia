package br.pucpr.authserver

import org.springframework.boot.CommandLineRunner
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class DataInitializer(val jdbcTemplate: JdbcTemplate) : CommandLineRunner {
    override fun run(vararg args: String?) {
        jdbcTemplate.execute("insert into tbl_users (email, name, password, role, id) values ('astro1@gmail.com', 'astro1', 'stars123', 'ADM', 1);")
        jdbcTemplate.execute("insert into tbl_users (email, name, password, role, id) values ('astro2@gmail.com', 'astro2', 'stars123', 'ADM', 2);")
        jdbcTemplate.execute("insert into tbl_users (email, name, password, role, id) values ('astro3@gmail.com', 'astro3', 'stars123', 'ADM', 3);")

        // Categorias de itens para o laboratório de astronomia
        jdbcTemplate.execute("INSERT INTO TBL_COD_GRU_EST (codigo, descricao) VALUES (1, 'Telescópios');")
        jdbcTemplate.execute("INSERT INTO TBL_COD_GRU_EST (codigo, descricao) VALUES (2, 'Instrumentos de Medição');")
        jdbcTemplate.execute("INSERT INTO TBL_COD_GRU_EST (codigo, descricao) VALUES (3, 'Livros e Mapas Estelares');")
        jdbcTemplate.execute("INSERT INTO TBL_COD_GRU_EST (codigo, descricao) VALUES (4, 'Equipamentos Diversos');")

        // Itens para o laboratório de astronomia
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (1, '1', 'Telescópio Newtoniano', 2000.0, 1);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (2, '2', 'Telescópio Refrator', 1500.0, 1);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (3, '3', 'Espectrômetro Portátil', 300.0, 2);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (4, '4', 'Mapa Estelar', 50.0, 3);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (5, '5', 'Livro de Astrofísica', 80.0, 3);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (6, '6', 'Binóculos Astronômicos', 300.0, 1);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (7, '7', 'Camera CCD para Telescópios', 250.0, 2);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (8, '8', 'Globo Celeste', 100.0, 3);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (9, '9', 'Cronômetro de Alta Precisão', 120.0, 2);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (10, '10', 'Espectrógrafo', 500.0, 2);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (11, '11', 'Software de Mapeamento Estelar', 200.0, 3);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (12, '12', 'Detector de Raios Cósmicos', 150.0, 2);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (13, '13', 'Livro sobre Galáxias e Quasares', 90.0, 3);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (14, '14', 'Luneta', 250.0, 1);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (15, '15', 'Computador para Análise de Dados', 1500.0, 4);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (16, '16', 'Lente de Baixa Distância Focal', 120.0, 1);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (17, '17', 'Filtro para Observação Solar', 60.0, 1);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (18, '18', 'Lanterna com Luz Vermelha', 20.0, 4);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (19, '19', 'Anemômetro', 150.0, 2);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (20, '20', 'Conjunto de Lentes para Telescópio', 200.0, 1);")
        jdbcTemplate.execute("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (21, '21', 'Mediador de Polarização de Luz', 250.0, 2);")
    }
}
